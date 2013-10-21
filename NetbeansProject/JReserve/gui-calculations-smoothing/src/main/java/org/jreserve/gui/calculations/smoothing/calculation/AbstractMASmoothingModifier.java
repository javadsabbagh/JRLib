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
package org.jreserve.gui.calculations.smoothing.calculation;

import java.util.List;
import java.util.Map;
import org.jdom2.Element;
import org.jreserve.gui.wrapper.jdom.JDomUtil;
import org.jreserve.jrlib.triangle.Triangle;
import org.jreserve.jrlib.triangle.smoothing.SmoothingCell;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public abstract class AbstractMASmoothingModifier<T extends Triangle> 
    extends AbstractSmoothingModifier<T> {

    public final static String LENGTH_TAG = "length";
    
    private int length;
    
    public AbstractMASmoothingModifier(List<SmoothingCell> cells, Class<T> clazz, int length) {
        super(cells, clazz);
        
        checkLength(length);
        this.length = length;
        
        checkCells(cells);
    }
    
    private void checkLength(int length) {
        if(length < 2)
            throw new IllegalArgumentException("Length must be at least 2 but was "+length);
    }
    
    private void checkCells(List<SmoothingCell> cells) {
        if(cells.size() < length) {
            String msg = "There must be at least %d cells, but there was only %d!";
            msg = String.format(msg, length, cells.size());
            throw new IllegalArgumentException(msg);
        }
    }
    
    public synchronized int getLength() {
        return length;
    }
    
    public synchronized void setLength(int length) {
        checkLength(length);
        Map preState = createState();
        this.length = length;
        fireChange(preState);
    }
    
    @Override
    public synchronized void getState(Map state) {
        super.getState(state);
        state.put(LENGTH_TAG, length);
    }
    
    @Override
    public synchronized void loadState(Map state) {
        super.setChangeFired(false);
        super.loadState(state);
        super.setChangeFired(true);
        int nl = getInt(state, LENGTH_TAG);
        setLength(nl);
    }

    @Override
    public synchronized void setCells(List<SmoothingCell> cells) {
        checkCells(cells);
        super.setCells(cells);
    }

    @Override
    public Element toXml() {
        Element root = new Element(getRootName());
        JDomUtil.addElement(root, LENGTH_TAG, length);
        root.addContent(cellsToXml());
        return root;
    }
    
    protected abstract String getRootName();
}