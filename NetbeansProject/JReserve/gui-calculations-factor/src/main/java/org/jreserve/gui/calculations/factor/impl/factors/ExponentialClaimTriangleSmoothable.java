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
package org.jreserve.gui.calculations.factor.impl.factors;

import java.util.List;
import org.jreserve.gui.calculations.smoothing.Smoothable;
import org.jreserve.gui.calculations.smoothing.calculation.ExponentialSmoothable;
import org.jreserve.gui.calculations.smoothing.calculation.ExponentialSmoothingDialogController;
import org.jreserve.gui.calculations.smoothing.dialog.SmoothDialogController;
import org.jreserve.gui.trianglewidget.model.TriangleSelection;
import org.jreserve.jrlib.gui.data.TriangleGeometry;
import org.jreserve.jrlib.triangle.Cell;
import org.jreserve.jrlib.triangle.factor.FactorTriangle;
import org.jreserve.jrlib.triangle.smoothing.SmoothingCell;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
@Smoothable.Registration(
    category = FactorTriangleCalculationImpl.CATEGORY,
    displayName = "#LBL.ExponentialSmoothable.Name",
    position = 100
)
@Messages({
    "LBL.ExponentialSmoothable.Name=Exponential Smoothing"
})
public class ExponentialClaimTriangleSmoothable extends ExponentialSmoothable<FactorTriangle> {
    
    @Override
    protected FactorTriangleCalculationImpl getCalculation(Lookup context) {
        return context.lookup(FactorTriangleCalculationImpl.class);
    }

    @Override
    protected SmoothDialogController<FactorTriangle> createController(Lookup context) {
        FactorTriangleCalculationImpl calc = getCalculation(context);
        TriangleGeometry geometry = calc.getSource().getGeometry();
        
        TriangleSelection selection = context.lookup(TriangleSelection.class);
        List<Cell> cells = selection.getCells();
        FactorTriangle triangle = (FactorTriangle) selection.getTriangle();
        
        return new DialogController(triangle, geometry, cells);
    }
    
    static class DialogController extends ExponentialSmoothingDialogController<FactorTriangle> {

        DialogController(FactorTriangle triangle, TriangleGeometry geometry, List<Cell> cells) {
            super(triangle, geometry, cells);
        }
        
        @Override
        public ExponentialModifier createModifier() {
            List<SmoothingCell> cells = createCells();
            double alpha = getAlpha();
            return new ExponentialModifier(cells, alpha);
        }
    }
}
