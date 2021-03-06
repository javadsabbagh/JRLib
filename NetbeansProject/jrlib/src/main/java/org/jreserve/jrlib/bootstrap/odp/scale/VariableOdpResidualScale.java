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
package org.jreserve.jrlib.bootstrap.odp.scale;

import org.jreserve.jrlib.bootstrap.odp.residuals.AdjustedOdpResidualTriangle;
import org.jreserve.jrlib.bootstrap.odp.residuals.OdpResidualTriangle;
import org.jreserve.jrlib.linkratio.LinkRatio;
import org.jreserve.jrlib.triangle.claim.ClaimTriangle;
import org.jreserve.jrlib.vector.AbstractVector;

/**
 * Calculates a separate scale factor for all development periods
 * in the residual triangle.
 * The formula used is:
 *                   sum(r(a,d)^2)
 *      scale(d)^2 = -------------
 *                       N(d)
 * where:
 * -   `r(a,d)` is the residual for accident period `a` and development period `d`.
 * -   `N(d)` is the number of non NaN residuals in development period `d`.
 * 
 * @author Peter Decsi
 * @version 1.0
 */
public class VariableOdpResidualScale extends AbstractVector<OdpResidualTriangle> implements OdpResidualScale {

    private double[] values;
    
    /**
     * Creates a new instance for the given source. The instance
     * will use an {@link AdjustedOdpResidualTriangle AdjustedOdpResidualTriangle}
     * as a residual triangle.
     * 
     * @throws NullPointerException if `source` is null.
     */
    public VariableOdpResidualScale(LinkRatio source) {
        this(new AdjustedOdpResidualTriangle(source));
    }

    /**
     * Creates a new instance for the given source.
     * 
     * @throws NullPointerException if `source` is null.
     */
    public VariableOdpResidualScale(OdpResidualTriangle source) {
        super(source);
        doRecalculate();
    }
    
    @Override
    public OdpResidualTriangle getSourceOdpResidualTriangle() {
        return source;
    }
    
    @Override
    public LinkRatio getSourceLinkRatios() {
        return source.getSourceLinkRatios();
    }
    
    @Override 
    public ClaimTriangle getSourceTriangle() {
        return source.getSourceTriangle();
    }
    
    @Override
    public double getFittedValue(int accident, int development) {
        return source.getFittedValue(accident, development);
    }
    
    @Override
    public double[][] toArrayFittedValues() {
        return source.toArrayFittedValues();
    }

    @Override
    public double getValue(int development) {
        return withinBonds(development)? values[development] : Double.NaN;
    }

    @Override
    protected void recalculateLayer() {
        doRecalculate();
    }

    private void doRecalculate() {
        length = source.getDevelopmentCount();
        values = new double[length];
        for(int d=0; d<length; d++)
            values[d] = calculateScale(d);
    }

    private double calculateScale(int development) {
        int accidents = source.getAccidentCount();
        int n = 0;
        double sRs = 0d;
        for(int a=0; a<accidents; a++) {
            double r = source.getValue(a, development);
            if(!Double.isNaN(r)) {
                sRs += r*r;
                n++;
            }
        }
        
        return n==0? Double.NaN : Math.sqrt(sRs / (double)n);
    }
}
