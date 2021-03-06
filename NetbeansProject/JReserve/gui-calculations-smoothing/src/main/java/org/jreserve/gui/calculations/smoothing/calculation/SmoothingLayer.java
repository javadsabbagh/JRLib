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

import java.awt.Color;
import java.awt.Component;
import org.jreserve.gui.calculations.api.modification.DefaultColor;
import org.jreserve.gui.trianglewidget.DefaultTriangleLayer;
import org.jreserve.gui.trianglewidget.DefaultTriangleWidgetRenderer;
import org.jreserve.gui.trianglewidget.TriangleWidget;
import org.jreserve.jrlib.CalculationData;
import org.jreserve.jrlib.triangle.SmoothedTriangle;
import org.jreserve.jrlib.triangle.Triangle;
import org.jreserve.jrlib.triangle.smoothing.SmoothingCell;
import org.jreserve.jrlib.vector.SmoothedVector;
import org.jreserve.jrlib.vector.Vector;
import org.jreserve.jrlib.vector.VectorTriangle;
import org.jreserve.jrlib.vector.smoothing.SmoothingIndex;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
@DefaultColor.Registrations({
    @DefaultColor.Registration(
        id="smoothing.notapplied",
        displayName = "#LBL.SmoothingLayer.Color.NotApplied",
        background = "99CCFF"
    ),
    @DefaultColor.Registration(
        id="smoothing.applied",
        displayName = "#LBL.SmoothingLayer.Color.Applied",
        background = "6699FF"
    )
})
@Messages({
    "LBL.SmoothingLayer.Color.NotApplied=Smoothing, not applied",
    "LBL.SmoothingLayer.Color.Applied=Smoothing, applied"
})
class SmoothingLayer extends DefaultTriangleLayer {
    
    private final static String ID_NOT_APPLIED = "smoothing.notapplied"; //NOI18
    private final static String ID_APPLIED = "smoothing.applied"; //NOI18
    private final static Color BACKGROUND = DefaultColor.getBackground(ID_NOT_APPLIED);
    private final static Color FOREGROUND = DefaultColor.getForeground(ID_NOT_APPLIED);
    private final static Color APPLIED_BACKGROUND = DefaultColor.getBackground(ID_APPLIED);
    private final static Color APPLIED_FOREGROUND = DefaultColor.getForeground(ID_APPLIED);

    private static Triangle getTriangle(CalculationData data) {
        if(data instanceof Triangle) {
            return (Triangle) data;
        } else if(data instanceof Vector) {
            return new VectorTriangle((Vector)data);
        } else {
            String msg = "'%s' is not a Triangle neither a Vector!";
            msg = String.format(msg, data);
            throw new IllegalArgumentException(msg);
        }
    }
    
    private SmoothingCell[] cells;
    
    SmoothingLayer(CalculationData data, String name) {
        super(getTriangle(data), name, AbstractSmoothingModifier.ICON);
        super.setCellRenderer(new Renderer());
        createCells(data);
    }
    
    private void createCells(CalculationData data) {
        if(data instanceof SmoothedTriangle)
            cells = ((SmoothedTriangle)data).getSmoothing().getSmoothingCells();
        else if(data instanceof SmoothedVector) {
            createCells((SmoothedVector) data);
        } else {
            throw new IllegalArgumentException("Not a vector or data!");
        }
    }
    
    private void createCells(SmoothedVector vector) {
        SmoothingIndex[] indices = vector.getSmoothing().getSmoothingCells();
        int size = indices.length;
        boolean isAccident = vector.isAccident();
        
        cells = new SmoothingCell[size];
        for(int i=0; i<size; i++)
            cells[i] = createCell(indices[i], isAccident);
    }
    
    private SmoothingCell createCell(SmoothingIndex index, boolean isAccident) {
        return isAccident?
            new SmoothingCell(index.getIndex(), 0, index.isApplied()) :
            new SmoothingCell(0, index.getIndex(), index.isApplied());
    }
    
    @Override
    public boolean rendersCell(int accident, int development) {
        for(SmoothingCell cell : cells)
            if(cell.equals(accident, development))
                return true;
        return false;
    }
    
    private class Renderer extends DefaultTriangleWidgetRenderer {

        @Override
        public Component getComponent(TriangleWidget widget, double value, int accident, int development, boolean selected) {
            super.getComponent(widget, value, accident, development, selected);
            if(!selected) {
                boolean applied = isApplied(accident, development);
                setBackground(applied? APPLIED_BACKGROUND : BACKGROUND);
                setForeground(applied? APPLIED_FOREGROUND : FOREGROUND);
            }
            return this;
        }
        
        private boolean isApplied(int accident, int development) {
            for(SmoothingCell cell : cells)
                if(cell.equals(accident, development))
                    return cell.isApplied();
            return false;
        }
    }
}
