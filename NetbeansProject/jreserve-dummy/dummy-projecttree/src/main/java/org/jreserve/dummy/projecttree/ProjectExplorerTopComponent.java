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
package org.jreserve.dummy.projecttree;

import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.jreserve.dummy.projecttree//ProjectExplorer//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "ProjectExplorerTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "org.jreserve.dummy.projecttree.ProjectExplorerTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ProjectExplorerAction",
        preferredID = "ProjectExplorerTopComponent")
@Messages({
    "CTL_ProjectExplorerAction=ProjectExplorer",
    "CTL_ProjectExplorerTopComponent=ProjectExplorer Window",
    "HINT_ProjectExplorerTopComponent=This is a ProjectExplorer window"
})
public final class ProjectExplorerTopComponent extends TopComponent implements ExplorerManager.Provider {
    
    private final static ExplorerManager em = new ExplorerManager();
    
    public ProjectExplorerTopComponent() {
        initComponents();
        setName(Bundle.CTL_ProjectExplorerTopComponent());
        setToolTipText(Bundle.HINT_ProjectExplorerTopComponent());
        em.setRootContext(NodeFactory.createRootNode());
        associateLookup(ExplorerUtils.createLookup(em, getActionMap()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        projectView = new org.openide.explorer.view.BeanTreeView();

        setLayout(new java.awt.BorderLayout());

        projectView.setRootVisible(false);
        add(projectView, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.openide.explorer.view.BeanTreeView projectView;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }
}