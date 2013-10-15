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
import org.jreserve.jrlib.triangle.Triangle;
import org.jreserve.jrlib.triangle.smoothing.SmoothingCell;
import org.openide.util.NbBundle.Messages;
import org.jreserve.jrlib.triangle.smoothing.ArithmeticMovingAverage;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
@Messages({
    "LBL.ArithmeticMASmoothingModifier.Name=Arithmetic MA",
    "# {0} - length",
    "# {1} - cells",
    "LBL.ArithmeticMASmoothingModifier.Description=Arithmetic MA [length={0}], [{1}]"
})
public abstract class ArithmeticMASmoothingModifier<T extends Triangle>
    extends AbstractMASmoothingModifier<T> {
    
    public final static String ROOT_TAG = "arithmeticMovingAverage";
    
    public ArithmeticMASmoothingModifier(List<SmoothingCell> cells, Class<T> clazz, int length) {
        super(cells, clazz, length);
    }

    @Override
    protected String getRootName() {
        return ROOT_TAG;
    }

    @Override
    public String getDescription() {
        return Bundle.LBL_ArithmeticMASmoothingModifier_Description(getLength(), getCellsAsString());
    }

    @Override
    protected String getDisplayName() {
        return Bundle.LBL_ArithmeticMASmoothingModifier_Name();
    }

    protected final ArithmeticMovingAverage createSmoothing() {
        return new ArithmeticMovingAverage(getCellsAsArray(), getLength());
    }
}
