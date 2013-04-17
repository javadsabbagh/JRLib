package org.jrlib.bootstrap.mcl.pseudodata;

import org.jrlib.bootstrap.AbstractAdjustedResidualTriangle;
import org.jrlib.claimratio.ClaimRatio;
import org.jrlib.claimratio.scale.ClaimRatioScale;
import org.jrlib.claimratio.scale.residuals.CRResidualTriangle;
import org.jrlib.claimratio.scale.residuals.ClaimRatioResiduals;
import org.jrlib.claimratio.scale.residuals.ModifiedCRResidualTriangle;
import org.jrlib.triangle.claim.ClaimTriangle;
import org.jrlib.triangle.ratio.RatioTriangle;

/**
 * Special adjustment for Mcl-Bootstrap. As in the bootstrap
 * only those claim-ratios are sampled, which have a pair 
 * link-ratio, the adjustment factors should be adjusted,
 * to the same value as the adjustment factor for the
 * link ratios.
 * 
 * @author Peter Decsi
 * @version 1.0
 */
public class MclAdjustedClaimRatioResiduals 
    extends AbstractAdjustedResidualTriangle<CRResidualTriangle> 
    implements ModifiedCRResidualTriangle{
    
    public MclAdjustedClaimRatioResiduals(ClaimRatioScale source) {
        super(new ClaimRatioResiduals(source));
    }
    
    public MclAdjustedClaimRatioResiduals(CRResidualTriangle source) {
        super(source);
    }
    
    public CRResidualTriangle getSourceResidualTriangle() {
        return source;
    }

    public ClaimRatioScale getSourceClaimRatioScales() {
        return source.getSourceClaimRatioScales();
    }

    public ClaimRatio getSourceClaimRatios() {
        return source.getSourceClaimRatios();
    }

    public RatioTriangle getSourceRatioTriangle() {
        return source.getSourceRatioTriangle();
    }

    public ClaimTriangle getSourceNumeratorTriangle() {
        return source.getSourceNumeratorTriangle();
    }

    public ClaimTriangle getSourceDenominatorTriangle() {
        return source.getSourceDenominatorTriangle();
    }
    
    @Override
    protected int recalculateP() {
        int accidents = calculateLRAccidentCount();
        int developments = calculateLRDevelopmentCount(0);
        int p = accidents + developments - 1;
        return p<0? 0 : p;
    }

    private int calculateLRAccidentCount() {
        ClaimTriangle n = source.getSourceNumeratorTriangle();
        ClaimTriangle d = source.getSourceDenominatorTriangle();
        
        int accidents = Math.min(n.getAccidentCount(), d.getAccidentCount());
        while(accidents > 0 && !hasFactorInAccident(n, d, accidents-1))
            accidents--;
        
        return accidents;
    }
    
    private boolean hasFactorInAccident(ClaimTriangle n, ClaimTriangle d, int aciddent) {
        int dN = d.getDevelopmentCount(aciddent);
        int nN = n.getDevelopmentCount(aciddent);
        return dN>1 && nN > 1;
    }
    
    @Override
    protected int recalculateN() {
        int accidents = calculateLRAccidentCount();
        int n = 0;
        for(int a=0; a<accidents; a++) {
            int devs = calculateLRDevelopmentCount(a);
            for(int d=0; d<devs; d++)
                if(!Double.isNaN(source.getValue(a, d)))
                    n++;
        }
        return n;
    }
    
    private int calculateLRDevelopmentCount(int accident) {
        ClaimTriangle n = source.getSourceNumeratorTriangle();
        ClaimTriangle d = source.getSourceDenominatorTriangle();
        
        int devs = Math.min(n.getDevelopmentCount(accident), d.getDevelopmentCount(accident));
        return devs==0? devs : devs-1;
    }
}
