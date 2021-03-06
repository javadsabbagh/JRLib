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
package org.jreserve.jrlib.linkratio.standarderror;

import org.jreserve.jrlib.util.method.AbstractLinearRegression;

/**
 * The LogLinearRatioSEFunction models the logarithm of the ratio of the 
 * standard errors and scale parameters with a linear model.
 * 
 * The formula to estimate the standard error for development period `d` is:
 *      se(d) ~ lr(d) * exp(a + b * t)
 * where:
 * -   `t = d + 1`
 * -   `lr(d)` is the link-ratio for development period `d`
 * -   `a, b` are the estimated intercept and slope
 * 
 * @author Peter Decsi
 * @version 1.0
 */
public class LogLinearRatioSEFunction extends AbstractLinearRegression<LinkRatioSE> implements LinkRatioSEFunction {

    private double[] lrs;
    private int developments;
    
    @Override
    protected double[] getYs(LinkRatioSE source) {
        lrs = source.getSourceLinkRatios().toArray();
        
        developments = source.getLength();
        double[] y = new double[developments];
        for(int d=0; d<developments; d++)
            y[d] = getY(d, source);
        
        return y;
    }
    
    private double getY(int development, LinkRatioSE ses) {
        double lr = lrs[development]; //lrs.getValue(development);
        if(Double.isNaN(lr) || lr <= 0d) return Double.NaN;
        
        double se = ses.getValue(development);
        return Math.log(se / lr);
    }

    @Override
    protected double[] getXs(LinkRatioSE source) {
        return super.getXOneBased(source.getLength());
    }

    @Override
    public double getValue(int development) {
        if(0 > development || development >= developments)
            return Double.NaN;
        
        double lr = lrs[development];
        double x = (double) (development + 1);
        return lr * Math.exp(intercept + slope * x);
    }
    
    @Override
    public boolean equals(Object o) {
        return (o instanceof LogLinearRatioSEFunction);
    }
    
    @Override
    public int hashCode() {
        return LogLinearRatioSEFunction.class.hashCode();
    }
    
    @Override
    public String toString() {
        if(Double.isNaN(slope) || Double.isNaN(intercept))
            return "LogLinearRatioSEFunction [NaN]";
        return String.format(
                "LogLinearRatioSEFunction [lr * exp(%.5f + %.5f * t)]", 
                intercept, slope);
    }
}
