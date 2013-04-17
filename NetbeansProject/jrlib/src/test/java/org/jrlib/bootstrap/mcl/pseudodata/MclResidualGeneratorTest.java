package org.jrlib.bootstrap.mcl.pseudodata;

import org.jrlib.bootstrap.mcl.pseudodata.MclResidualCell;
import org.jrlib.bootstrap.mcl.pseudodata.MclResidualGenerator;
import org.jrlib.bootstrap.mcl.pseudodata.MclResidualBundle;
import org.jrlib.TestConfig;
import org.jrlib.TestData;
import org.jrlib.bootstrap.FixedRandom;
import org.jrlib.claimratio.ClaimRatio;
import org.jrlib.claimratio.SimpleClaimRatio;
import org.jrlib.claimratio.scale.ClaimRatioScale;
import org.jrlib.claimratio.scale.SimpleClaimRatioScale;
import org.jrlib.claimratio.scale.residuals.CRResidualTriangle;
import org.jrlib.claimratio.scale.residuals.ClaimRatioResiduals;
import org.jrlib.linkratio.LinkRatio;
import org.jrlib.linkratio.SimpleLinkRatio;
import org.jrlib.linkratio.scale.LinkRatioScale;
import org.jrlib.linkratio.scale.SimpleLinkRatioScale;
import org.jrlib.linkratio.scale.residuals.LRResidualTriangle;
import org.jrlib.linkratio.scale.residuals.LinkRatioResiduals;
import org.jrlib.triangle.claim.ClaimTriangle;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class MclResidualGeneratorTest {

    private final static double[] PAID_LR =
        {-0.53544715,  0.07687310, -0.88318295, -0.32853221};
    
    private final static double[] PAID_CR =
        {-1.33089143, -1.80917390, -1.72446110, -1.73477463};
    
    private final static double[] INCURRED_LR =
        {-0.38904405, -0.42505847,  1.12758752,  0.46441317};
    
    private final static double[] INCURRED_CR = 
        { 1.39958504,  1.86536889,  1.77570549,  1.75243837};
    
    private final static int[][] INDICES = {
        {0, 1, 2, 3, 0, 1, 2},
        {3, 0, 1, 2, 3, 0},
        {1, 2, 3, 0, 1},
        {2, 3, 0, 1},
        {2, 3, 0},
        {1, 2},
        {3}
    };
    
    private MclResidualBundle bundle;
    private MclResidualGenerator residuals;
    
    @Before
    public void setUp() {
        ClaimTriangle paid = TestData.getCummulatedTriangle(TestData.PAID);
        ClaimTriangle incurred = TestData.getCummulatedTriangle(TestData.INCURRED);
        
        LRResidualTriangle paidLR = createLRResiduals(paid);
        CRResidualTriangle paidCR = createCRResiduals(incurred, paid);
        LRResidualTriangle incurredLR = createLRResiduals(incurred);
        CRResidualTriangle incurredCR = createCRResiduals(paid, incurred);
        
        bundle = new MclResidualBundle(paidLR, paidCR, incurredLR, incurredCR);
        residuals = new MclResidualGenerator(new FixedRandom(), bundle);
    }
    
    private LRResidualTriangle createLRResiduals(ClaimTriangle cik) {
        LinkRatio lrs = new SimpleLinkRatio(cik);
        LinkRatioScale scales = new SimpleLinkRatioScale(lrs);
        return new LinkRatioResiduals(scales);
    }
    
    private CRResidualTriangle createCRResiduals(ClaimTriangle numerator, ClaimTriangle denominator) {
        ClaimRatio crs = new SimpleClaimRatio(numerator, denominator);
        ClaimRatioScale scales = new SimpleClaimRatioScale(crs);
        return new ClaimRatioResiduals(scales);
    }

    @Test
    public void testGetValue() {
        int accidents = bundle.getAccidentCount();
        for(int a=0; a<accidents; a++) {
            int devs = bundle.getDevelopmentCount(a);
            for(int d=0; d<devs; d++) {
                int index = INDICES[a][d];
                MclResidualCell cell = residuals.getCell(a, d);
                assertEquals(PAID_LR[index], cell.getPaidLRResidual(), TestConfig.EPSILON);
                assertEquals(PAID_CR[index], cell.getPaidCRResidual(), TestConfig.EPSILON);
                assertEquals(INCURRED_LR[index], cell.getIncurredLRResidual(), TestConfig.EPSILON);
                assertEquals(INCURRED_CR[index], cell.getIncurredCRResidual(), TestConfig.EPSILON);                
            }
        }
    }
}