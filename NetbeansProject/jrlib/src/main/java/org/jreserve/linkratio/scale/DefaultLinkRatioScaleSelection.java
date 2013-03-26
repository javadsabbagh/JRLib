package org.jreserve.linkratio.scale;

import org.jreserve.triangle.factor.FactorTriangle;
import org.jreserve.linkratio.LinkRatio;
import org.jreserve.triangle.claim.ClaimTriangle;
import org.jreserve.triangle.TriangleUtil;
import org.jreserve.util.AbstractMethodSelection;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class DefaultLinkRatioScaleSelection extends AbstractMethodSelection<LinkRatioScale, LinkRatioScaleEstimator> implements LinkRatioScaleSelection {
    
    private int developments;
    private double[] values;
    
    public DefaultLinkRatioScaleSelection(LinkRatio lrs) {
        this(new LinkRatioScaleCaclulator(lrs), null);
    }
    
    public DefaultLinkRatioScaleSelection(LinkRatioScale source) {
        this(source, source.getDevelopmentCount(), null);
    }
    
    public DefaultLinkRatioScaleSelection(LinkRatioScale source, LinkRatioScaleEstimator defaultMethod) {
        this(source, source.getDevelopmentCount(), defaultMethod);
    }
    
    public DefaultLinkRatioScaleSelection(LinkRatioScale source, int developmentCount, LinkRatioScaleEstimator defaultMethod) {
        super(source, defaultMethod==null? new EmptyLinkRatioScaleEstimator() : defaultMethod);
        this.developments = developmentCount<0? 0 : developmentCount;
        doRecalculate();
    }
    
    private DefaultLinkRatioScaleSelection(DefaultLinkRatioScaleSelection original) {
        super(original);
        this.developments = original.developments;
        doRecalculate();
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
    public void setDefaultMethod(LinkRatioScaleEstimator defaultMethod) {
        if(defaultMethod == null)
            defaultMethod = new EmptyLinkRatioScaleEstimator();
        super.setDefaultMethod(defaultMethod);
    }
    
    @Override
    public int getDevelopmentCount() {
        return developments;
    }
    
    @Override
    protected void recalculateLayer() {
        doRecalculate();
    }
    
    private void doRecalculate() {
        developments = (source==null)? 0 : source.getDevelopmentCount();
        super.fitMethods();
        values = super.getFittedValues(developments);
    }
    
    @Override
    public double getValue(int development) {
        if(development < 0)
            return Double.NaN;
        if(development < developments)
            return values[development];
        return 1d;
    }
    
    @Override
    public double[] toArray() {
        return TriangleUtil.copy(values);
    }
    
    @Override
    public DefaultLinkRatioScaleSelection copy() {
        return new DefaultLinkRatioScaleSelection(this);
    }
}