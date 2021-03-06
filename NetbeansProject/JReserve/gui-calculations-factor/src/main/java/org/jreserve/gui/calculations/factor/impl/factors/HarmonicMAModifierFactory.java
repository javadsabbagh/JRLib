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
import org.jdom2.Element;
import org.jreserve.gui.calculations.api.modification.CalculationModifier;
import org.jreserve.gui.calculations.api.modification.CalculationModifierFactory;
import org.jreserve.gui.calculations.smoothing.calculation.HarmonicMASmoothingModifier;
import org.jreserve.gui.calculations.smoothing.calculation.SmoothingModifierUtil;
import org.jreserve.gui.wrapper.jdom.JDomUtil;
import org.jreserve.jrlib.triangle.factor.FactorTriangle;
import org.jreserve.jrlib.triangle.smoothing.SmoothingCell;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
@CalculationModifierFactory.Registration(
    category = FactorTriangleCalculationImpl.CATEGORY,
    rootName = HarmonicMASmoothingModifier.ROOT_TAG,
    position = 500
)
public class HarmonicMAModifierFactory implements CalculationModifierFactory<FactorTriangle> {

    @Override
    public CalculationModifier<FactorTriangle> fromXml(Element root) throws Exception {
        int length = JDomUtil.getExistingInt(root, HarmonicMASmoothingModifier.LENGTH_TAG);
        List<SmoothingCell> cells = SmoothingModifierUtil.readCells(root);
        return new HarmonicMAModifier(cells, length);
    }
}
