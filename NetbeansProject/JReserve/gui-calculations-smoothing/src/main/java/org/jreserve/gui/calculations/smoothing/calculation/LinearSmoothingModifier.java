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
import org.jreserve.jrlib.CalculationData;
import org.jreserve.jrlib.triangle.smoothing.LinearRegressionSmoothing;
import org.jreserve.jrlib.triangle.smoothing.SmoothingCell;
import org.jreserve.jrlib.vector.smoothing.AbstractSmoothing;
import org.jreserve.jrlib.vector.smoothing.LinearRegressionSmoothingMethod;
import org.jreserve.jrlib.vector.smoothing.VectorSmoothing;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
@Messages({
    "LBL.LinearSmoothingModifier.Name=Linear Regression",
    "# {0} - hasIntercept",
    "# {1} - cells",
    "LBL.LinearSmoothingModifier.Description=Linear Regression [hasIntercept={0}], [{1}]",
    "LBL.LinearSmoothingModifier.ProgressName=Linear Smoothing"
})
public abstract class LinearSmoothingModifier<C extends CalculationData> 
    extends AbstractRegressionSmoothingModifier<C> {

    public final static String ROOT_TAG = "linearRegressionSmoothing";
    
    public LinearSmoothingModifier(List<SmoothingCell> cells, Class<C> clazz, boolean hasIntercept) {
        super(cells, clazz, hasIntercept);
    }
    
    @Override
    protected String getRootTag() {
        return ROOT_TAG;
    }
    
    @Override
    protected String getDisplayName() {
        return Bundle.LBL_LinearSmoothingModifier_Name();
    }

    @Override
    public String getDescription() {
        return Bundle.LBL_LinearSmoothingModifier_Description(hasIntercept(), getCellsAsString());
    }

    protected final LinearRegressionSmoothing createSmoothing() {
        return new LinearRegressionSmoothing(getCellsAsArray(), hasIntercept());
    }

    protected final VectorSmoothing createVectorSmoothing() {
        return new AbstractSmoothing(
                getCellsAsIndices(), 
                new LinearRegressionSmoothingMethod(hasIntercept()));
    }
    
    @Override
    protected String getUpdatePHTitle() {
        return Bundle.LBL_LinearSmoothingModifier_ProgressName();
    }
}
