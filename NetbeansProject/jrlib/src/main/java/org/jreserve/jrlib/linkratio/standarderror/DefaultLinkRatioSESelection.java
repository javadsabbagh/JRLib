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

import org.jreserve.jrlib.linkratio.LinkRatio;
import org.jreserve.jrlib.linkratio.scale.LinkRatioScale;
import org.jreserve.jrlib.linkratio.scale.LinkRatioScaleInput;
import org.jreserve.jrlib.triangle.claim.ClaimTriangle;
import org.jreserve.jrlib.triangle.factor.FactorTriangle;
import org.jreserve.jrlib.util.method.AbstractMethodSelection;

/**
 * DefaultLinkRatioSESelection allows to estimate the standard error of 
 * link-ratios with different methods for different development periods.
 * 
 * @see LinkRatioSE
 * @see LinkRatioSECalculator
 * @author Peter Decsi
 * @version 1.0
 */
public class DefaultLinkRatioSESelection extends AbstractMethodSelection<LinkRatioSE, LinkRatioSEFunction> implements LinkRatioSESelection {
    
    private int developments;
    private double[] values;

    /**
     * Creates an instance for the given source, with an instance of 
     * {@link DefaultLinkRatioSEFunction DefaultLinkRatioSEFunction} as
     * default method.
     * 
     * @throws NullPointerException if `source` is null.
     */
    public DefaultLinkRatioSESelection(LinkRatioScale source) {
        this(new LinkRatioSECalculator(source), null);
    }
    
    /**
     * Creates an instance for the given source, with the given method used as 
     * default. If `defaultMethod` is null, 
     * {@link DefaultLinkRatioSEFunction DefaultLinkRatioSEFunction} is used.
     * 
     * @throws NullPointerException if `source` is null.
     */
    public DefaultLinkRatioSESelection(LinkRatioScale source, LinkRatioSEFunction defaultMethod) {
        this(new LinkRatioSECalculator(source), null);
    }

    /**
     * Creates an instance for the given source, with an instance of 
     * {@link DefaultLinkRatioSEFunction DefaultLinkRatioSEFunction} as
     * default method.
     * 
     * @throws NullPointerException if `source` is null.
     */
    public DefaultLinkRatioSESelection(LinkRatioSE source) {
        this(source, null);
    }
    
    /**
     * Creates an instance for the given source, with the given method used as 
     * default. If `defaultMethod` is null, 
     * {@link DefaultLinkRatioSEFunction DefaultLinkRatioSEFunction} is used.
     * 
     * @throws NullPointerException if `source` is null.
     */
    public DefaultLinkRatioSESelection(LinkRatioSE source, LinkRatioSEFunction defaultMethod) {
        super(source, defaultMethod==null? new DefaultLinkRatioSEFunction() : defaultMethod);
        this.developments = source.getLength();
        doRecalculate();
    }
    
    @Override
    public boolean isAccident() {
        return false;
    }
    
    @Override
    public void setDefaultMethod(LinkRatioSEFunction defaultMethod) {
        if(defaultMethod == null)
            defaultMethod = new DefaultLinkRatioSEFunction();
        super.setDefaultMethod(defaultMethod);
    }

    @Override
    public LinkRatioScale getSourceLRScales() {
        return source.getSourceLRScales();
    }
    
    @Override
    public LinkRatioScaleInput getSourceLrScaleInput() {
        return source.getSourceLrScaleInput();
    }
    
    @Override
    public LinkRatio getSourceLinkRatios() {
        return source.getSourceLinkRatios();
    }
    
    @Override
    public FactorTriangle getSourceFactors() {
        return source.getSourceFactors();
    }
    
    @Override
    public ClaimTriangle getSourceTriangle() {
        return source.getSourceTriangle();
    }

    @Override
    public int getLength() {
        return developments;
    }
 
    @Override
    public double getValue(int development) {
        if(development < 0 || development >= developments)
            return Double.NaN;
        return values[development];
    }

    @Override
    public double[] toArray() {
        double[] copy = new double[developments];
        System.arraycopy(values, 0, copy, 0, developments);
        return copy;
    }
    
    @Override
    protected void recalculateLayer() {
        doRecalculate();
    }
    
    private void doRecalculate() {
        developments = (source==null)? 0 : source.getLength();
        super.fitMethods();
        values = super.getFittedValues(developments);
    }
}
