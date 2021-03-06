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
package org.jreserve.jrlib.estimate.mcl;

import org.jreserve.jrlib.AbstractCalculationData;
import org.jreserve.jrlib.claimratio.ClaimRatio;
import org.jreserve.jrlib.claimratio.scale.ClaimRatioScale;
import org.jreserve.jrlib.estimate.AbstractEstimate;
import org.jreserve.jrlib.estimate.Estimate;
import org.jreserve.jrlib.linkratio.LinkRatio;
import org.jreserve.jrlib.linkratio.scale.LinkRatioScale;
import org.jreserve.jrlib.triangle.TriangleUtil;
import org.jreserve.jrlib.triangle.claim.ClaimTriangle;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class MclEstimateBundle extends AbstractCalculationData<MclEstimateInput> {
    
    private EstimateProxy paidProxy;
    private EstimateProxy incurredProxy;
    private PaidIncurredEstimateProxy paidIncurredProxy;
    
    protected int accidents;
    protected int developments;
    protected int[] devIndices;
    protected double[][] paidValues;
    protected double[][] incurredValues;
    
    public MclEstimateBundle(MclCorrelation paidCorrelation, MclCorrelation incurredCorrelation) {
        this(new MclCalculationBundle(paidCorrelation, incurredCorrelation));
    }
    
    public MclEstimateBundle(MclEstimateInput source) {
        super(source);
        paidProxy = new EstimateProxy(true);
        incurredProxy = new EstimateProxy(false);
        paidIncurredProxy = new PaidIncurredEstimateProxy();
        doRecalculate();
    }
    
    public int getAccidentCount() {
        return accidents;
    }
    
    public int getDevelopmentCount() {
        return developments;
    }
    
    public int getObservedDevelopmentCount(int accident) {
        return (0<=accident && accident<accidents)?
                devIndices[accident] :
                0;
    }
    
    public Estimate getPaidEstimate() {
        return paidProxy;
    }
    
    public Estimate getIncurredEstimate() {
        return incurredProxy;
    }
    
    public Estimate getIncurredPaidEstimate() {
        return paidIncurredProxy;
    }
    
    @Override
    protected void recalculateLayer() {
        doRecalculate();
    }

    //TEMP
    private ClaimTriangle paid;
    private LinkRatio paidLR;
    private double[] paidLSPR;
    private ClaimRatio paidRatio;

    private ClaimTriangle incurred;
    private LinkRatio incurredLR;
    private double[] incurredLSPR;
    private ClaimRatio incurredRatio;

    private void doRecalculate() {
        initCalculationState();
        fillTriangles();
        setProxyState();
        clearCalculationState();
    }
    
    private void initCalculationState() {
        initDimensions();
        paidLSPR = calculateLSPR(source.getSourcePaidCorrelation());
        incurredLSPR = calculateLSPR(source.getSourceIncurredCorrelation());
        paidRatio = source.getSourcePaidCorrelation().getSourceClaimRatios();
        incurredRatio = source.getSourceIncurredCorrelation().getSourceClaimRatios();
    }
    
    private void initDimensions() {
        paidLR = source.getSourcePaidCorrelation().getSourceLinkRatios();
        incurredLR = source.getSourceIncurredCorrelation().getSourceLinkRatios();
        
        int pD = paidLR.getLength();
        int iD = incurredLR.getLength();
        developments = Math.min(pD, iD) + 1;
        
        paid = paidLR.getSourceTriangle();
        incurred = incurredLR.getSourceTriangle();
        int pA = paid.getAccidentCount();
        int iA = incurred.getAccidentCount();
        accidents = Math.min(pA, iA);
        
        devIndices = new int[accidents];
        for(int a=0; a<accidents; a++) {
            pD = paid.getDevelopmentCount(a);
            iD = paid.getDevelopmentCount(a);
            devIndices[a] = Math.min(pD, iD);
        }
    }
    
    private double[] calculateLSPR(MclCorrelation corr) {
        double lambda = corr.getValue();
        LinkRatioScale sigma = corr.getSourceLinkRatioScaless();
        ClaimRatioScale rho = corr.getSourceClaimRatioScales();
        
        double[] lspr = new double[developments-1];
        for(int d=0; d<(developments-1); d++)
            lspr[d] = lambda * sigma.getValue(d) / rho.getValue(d);
        return lspr;
    }
    
    private void fillTriangles() {
        initValues();
        for(int a=0; a<accidents; a++) {
            for(int d=0; d<developments; d++) {
                paidValues[a][d] = calculatePaid(a, d);
                incurredValues[a][d] = calculateIncurred(a, d);
            }
        }
    }
    
    private void initValues() {
        paidValues = new double[accidents][developments];
        incurredValues = new double[accidents][developments];
    }
    
    protected double calculateIncurred(int a, int d) {
        if(d == 0 || d < incurred.getDevelopmentCount(a))
            return incurred.getValue(a, d);
        
        int pD = d - 1;
        double iPrev = incurredValues[a][pD];
        double pPrev = paidValues[a][pD];
        double iLr = incurredLR.getValue(pD);
        double rPrev = incurredRatio.getValue(pD);
        return iPrev * (iLr + incurredLSPR[pD] * (pPrev/iPrev - rPrev));
    }
    
    protected double calculatePaid(int a, int d) {
        if(d == 0 || d < paid.getDevelopmentCount(a))
            return paid.getValue(a, d);
        
        int pD = d - 1;
        double iPrev = incurredValues[a][pD];
        double pPrev = paidValues[a][pD];
        double pLr = paidLR.getValue(pD);
        double rPrev = paidRatio.getValue(pD);
        return pPrev * (pLr + paidLSPR[pD] * (iPrev/pPrev - rPrev));
    }
    
    private void clearCalculationState() {
        paid = null;
        paidLR = null;
        paidLSPR = null;
        paidRatio = null;
        incurred = null;
        incurredLR = null;
        incurredLSPR = null;
        incurredRatio = null;
    }
    
    private void setProxyState() {
        paidProxy.recalculateLayer();
        incurredProxy.recalculateLayer();
        paidIncurredProxy.recalculateLayer();
    }
    
    private class EstimateProxy extends AbstractEstimate<MclEstimateBundle> {
        
        private boolean isPaid;
        
        private EstimateProxy(boolean isPaid) {
            super(MclEstimateBundle.this);
            this.isPaid = isPaid;
        }
        
        @Override
        protected void recalculateLayer() {
            super.accidents = MclEstimateBundle.this.accidents;
            super.developments = MclEstimateBundle.this.developments;
            super.values = isPaid? paidValues : incurredValues;
        }

        @Override
        public int getObservedDevelopmentCount(int accident) {
            return (0 <= accident && accident < accidents)?
                    devIndices[accident] : 
                    0;
        }
    }
    
    private class PaidIncurredEstimateProxy extends AbstractEstimate<MclEstimateBundle> {
        
        @Override
        protected void recalculateLayer() {
            super.accidents = MclEstimateBundle.this.accidents;
            super.developments = MclEstimateBundle.this.developments;
            super.values = TriangleUtil.copy(incurredValues);
            
            for(int a=0; a<accidents; a++)
                copyIncurred(a);
        }
        
        private void copyIncurred(int accident) {
            int from = devIndices[accident];
            if(from < developments) {
                double[] input = paidValues[accident];
                double[] target = super.values[accident];
                int length = developments - from;
                System.arraycopy(input, from, target, from, length);
            }
        }

        public int getObservedDevelopmentCount(int accident) {
            return (0 <= accident && accident < accidents)?
                    devIndices[accident] : 
                    0;
        }
    
    }
}