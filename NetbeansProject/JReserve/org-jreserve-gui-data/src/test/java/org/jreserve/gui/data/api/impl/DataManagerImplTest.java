/*
 *  Copyright (C) 2013, Peter Decsi.
 * 
 *  This library is free software: you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public 
 *  License as published by the Free Software Foundation, either 
 *  version 3 of the License, or (at your option) any later version.
 * 
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jreserve.gui.data.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jreserve.gui.data.api.DataCategory;
import org.jreserve.gui.misc.eventbus.EventBusListener;
import org.jreserve.gui.misc.eventbus.EventBusManager;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.netbeans.api.project.Project;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileUtil;
import org.openide.util.Lookup;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class DataManagerImplTest {
    
    private Project project;
    private DataManagerImpl manager;
    
    public DataManagerImplTest() {
    }
    
    @Before
    public void setUp() throws IOException {
        project = new MockProject();
        FileObject file = project.getProjectDirectory();
        FileObject folder = file.createFolder("Data");
        folder.createFolder("child2");
        folder = folder.createFolder("child1");
        folder.createData("source_1", "jds");
        folder.createData("source_2", "jds");
        folder.createFolder("child1");
        
        manager = new DataManagerImpl(project);
    }

    @Test
    public void testGetProject() {
        assertTrue(project == manager.getProject());
    }

    @Test
    public void testGetCategory() {
        DataCategoryImpl root = manager.getCategory("Data");
        assertEquals("Data", root.getName());
        
        root = manager.getCategory("");
        assertEquals("Data", root.getName());
        
        root = manager.getCategory(null);
        assertEquals("Data", root.getName());
        
        assertEquals("child1", manager.getCategory("Data/child1").getName());
        assertEquals("child1", manager.getCategory("Data/child1/child1").getName());
        assertEquals("child2", manager.getCategory("Data/child2").getName());
        
        assertNull(manager.getCategory("Data/child1/child2"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetCategory_IllegalPath() {
        manager.getCategory("Data/child2/");
    }

    @Test
    public void testGetDataSource() {
        assertEquals("source_1", manager.getDataSource("Data/child1/source_1").getName());
        assertEquals("source_2", manager.getDataSource("Data/child1/source_2").getName());
    
        assertNull(manager.getDataSource("Data/soruce_1"));
        assertNull(manager.getDataSource("Data/child1"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetDataSource_IllegalPath() {
        manager.getDataSource("Data/child2/");
    }
    
    @Test
    public void testDelete() throws IOException, InterruptedException {
        DataCategory child1 = manager.getCategory("Data/child1");
        DeleteListener listener = new DeleteListener();
        EventBusManager.getDefault().subscribe(listener);
        manager.deleteDataItem(child1);
        Thread.sleep(100);
        
        List<DeleteEntry> entries  = listener.getEntries();
        assertEquals(4, entries.size());
        DeleteEntry entry = entries.get(0);
        assertEquals("Data/child1/child1", entry.path);
        assertFalse(entry.isRoot);
        
        entry = entries.get(1);
        assertEquals("Data/child1/source_1", entry.path);
        assertFalse(entry.isRoot);
        
        entry = entries.get(2);
        assertEquals("Data/child1/source_2", entry.path);
        assertFalse(entry.isRoot);
        
        entry = entries.get(3);
        assertEquals("Data/child1", entry.path);
        assertTrue(entry.isRoot);
    }
    
    private static class DeleteListener {
        private final List<DeleteEntry> entries = new ArrayList<DeleteEntry>();
        
        @EventBusListener
        public void deleted(DataEvent.DataItemDeletedEvent evt) {
            synchronized(entries) {
                entries.add(new DeleteEntry(evt.getDataItem().getPath(), evt.isRootDelete()));
            }
        }
        
        public List<DeleteEntry> getEntries() {
            synchronized(entries) {
                return new ArrayList<DeleteEntry>(entries);
            }
        }
    }
    
    private static class DeleteEntry {
        private final String path;
        private final boolean isRoot;

        private DeleteEntry(String path, boolean isRoot) {
            this.path = path;
            this.isRoot = isRoot;
        }
    }
    
    private static class MockProject implements Project {
        
        private final FileSystem fileSys = FileUtil.createMemoryFileSystem();
        private final FileObject projectRoot = fileSys.getRoot();
        
        private MockProject() {
        }
        
        @Override
        public FileObject getProjectDirectory() {
            return projectRoot;
        }

        @Override
        public Lookup getLookup() {
            return Lookup.EMPTY;
        }
    
    }
}