package org.jreserve.factor.linkratio;

import org.jreserve.factor.DevelopmentFactors;
import org.jreserve.factor.FactorTriangle;
import org.jreserve.triangle.Triangle;
import org.jreserve.triangle.TriangleUtil;
import org.jreserve.util.AbstractMethodSelection;
        
/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class DefaultLinkRatioSelection extends AbstractMethodSelection<FactorTriangle, LinkRatioMethod> implements LinkRatioSelection {
    
    private int developments;
    private double[] values;
    
    public DefaultLinkRatioSelection(Triangle triangle) {
        this(new DevelopmentFactors(triangle), null);
    }
    
    public DefaultLinkRatioSelection(Triangle triangle, LinkRatioMethod defaultMethod) {
        this(new DevelopmentFactors(triangle), defaultMethod);
    }
    
    public DefaultLinkRatioSelection(FactorTriangle source) {
        this(source, null);
    }
    
    public DefaultLinkRatioSelection(FactorTriangle source, LinkRatioMethod defaultMethod) {
        super(source, defaultMethod==null? new WeightedAverageLRMethod() : defaultMethod);
        developments = (source==null)? 0 : source.getDevelopmentCount();
        doRecalculate();
    }
    
    @Override
    public FactorTriangle getSourceFactors() {
        return source;
    }

    @Override
    public void setDefaultMethod(LinkRatioMethod defaultMethod) {
        if(defaultMethod == null)
            defaultMethod = new WeightedAverageLRMethod();
        super.setDefaultMethod(defaultMethod);
    }
    
    @Override
    public int getDevelopmentCount() {
        return source==null? 0 : developments;
    }
    
    @Override
    protected void recalculateLayer() {
        doRecalculate();
    }
    
    private void doRecalculate() {
        super.fitMethods();
        values = super.getFittedValues(developments);
    }
    
    @Override
    public double getValue(int development) {
        if(withinBound(development))
            return values[development];
        return Double.NaN;
    }
    
    private boolean withinBound(int development) {
        return development >= 0 &&
               development < developments;
    }
    
    @Override
    public double[] toArray() {
        return TriangleUtil.copy(values);
    }
    
    @Override
    public double getMackAlpha(int development) {
        if(withinBound(development))
            return getMethod(development).getMackAlpha();
        return Double.NaN;
    }
    
    @Override
    public double getWeight(int accident, int development) {
        LinkRatioMethod method = getMethod(development);
        return method.getWeight(accident, development);
    }
}