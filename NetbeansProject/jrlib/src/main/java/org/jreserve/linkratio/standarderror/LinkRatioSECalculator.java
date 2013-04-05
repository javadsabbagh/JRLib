package org.jreserve.linkratio.standarderror;

import org.jreserve.AbstractCalculationData;
import org.jreserve.linkratio.LinkRatio;
import org.jreserve.scale.RatioScale;
import org.jreserve.triangle.TriangleUtil;
import org.jreserve.triangle.claim.ClaimTriangle;
import org.jreserve.triangle.factor.FactorTriangle;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class LinkRatioSECalculator extends AbstractCalculationData<RatioScale<LinkRatioScaleInput>> implements LinkRatioSE {

    private int developments;
    private double[] values;
    
    public LinkRatioSECalculator(RatioScale<LinkRatioScaleInput> source) {
        super(source);
        doRecalculate();
    }
    
    @Override
    public RatioScale<LinkRatioScaleInput> getSourceLRScales() {
        return source;
    }
    
    @Override
    public LinkRatio getSourceLinkRatios() {
        return source.getSourceInput().getSourceLinkRatios();
    }
    
    @Override
    public FactorTriangle getSourceFactors() {
        return source.getSourceInput().getSourceFactors();
    }
    
    @Override
    public ClaimTriangle getSourceTriangle() {
        return source.getSourceInput().getSourceTriangle();
    }

    @Override
    public int getDevelopmentCount() {
        return developments;
    }

    @Override
    public double getValue(int development) {
        if(development < developments && development >= 0)
            return values[development];
        return Double.NaN;
    }

    @Override
    public double[] toArray() {
        return TriangleUtil.copy(values);
    }

    @Override
    protected void recalculateLayer() {
        doRecalculate();
    }
    
    //Temp fields for calculations, cleared after values are calculated
    private LinkRatio lrk;
    private int accidents;
    
    private void doRecalculate() {
        initState();
        for(int d=0; d<developments; d++)
            values[d] = calculateSE(d);
        clearState();
    }
    
    private void initState() {
        developments = source.getDevelopmentCount();
        values = new double[developments];
        lrk = getSourceLinkRatios();
        accidents = getSourceFactors().getAccidentCount();
    }
    
    private double calculateSE(int development) {
        double scale = source.getValue(development);
        if(Double.isNaN(scale))
            return Double.NaN;
        
        double sw = getSumOfWeights(development);
        if(sw <= 0d)
            return Double.NaN;
        
        return Math.sqrt(Math.pow(scale, 2d) / sw);
    }
    
    private double getSumOfWeights(int development) {
        double sw = 0d;
        for(int a=0; a<accidents; a++) {
            double w = lrk.getWeight(a, development);
            if(!Double.isNaN(w))
                sw += w;
        }
        return sw;
    }
    
    private void clearState() {
        accidents = 0;
        lrk = null;
    }
    
    @Override
    public LinkRatioSECalculator copy() {
        return new LinkRatioSECalculator(source.copy());
    }
}
