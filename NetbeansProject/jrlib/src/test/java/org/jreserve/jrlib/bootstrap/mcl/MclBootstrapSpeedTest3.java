package org.jreserve.jrlib.bootstrap.mcl;

import org.jreserve.jrlib.TestData;
import org.jreserve.jrlib.bootstrap.mack.MackNormalProcessSimulator;
import org.jreserve.jrlib.bootstrap.mack.MackProcessSimulator;
import org.jreserve.jrlib.bootstrap.mcl.pseudodata.MclPseudoData;
import org.jreserve.jrlib.bootstrap.mcl.pseudodata.MclResidualBundle;
import org.jreserve.jrlib.bootstrap.util.BootstrapUtil;
import org.jreserve.jrlib.bootstrap.util.HistogramData;
import org.jreserve.jrlib.bootstrap.util.HistogramDataFactory;
import org.jreserve.jrlib.claimratio.ClaimRatio;
import org.jreserve.jrlib.claimratio.LrCrExtrapolation;
import org.jreserve.jrlib.claimratio.SimpleClaimRatio;
import org.jreserve.jrlib.claimratio.scale.ClaimRatioScale;
import org.jreserve.jrlib.claimratio.scale.SimpleClaimRatioScale;
import org.jreserve.jrlib.claimratio.scale.residuals.AdjustedClaimRatioResiduals;
import org.jreserve.jrlib.claimratio.scale.residuals.CRResidualTriangle;
import org.jreserve.jrlib.estimate.mcl.MclCalculationBundle;
import org.jreserve.jrlib.estimate.mcl.MclCorrelation;
import org.jreserve.jrlib.estimate.mcl.MclEstimateBundle;
import org.jreserve.jrlib.linkratio.LinkRatio;
import org.jreserve.jrlib.linkratio.SimpleLinkRatio;
import org.jreserve.jrlib.linkratio.scale.LinkRatioScale;
import org.jreserve.jrlib.linkratio.scale.SimpleLinkRatioScale;
import org.jreserve.jrlib.linkratio.scale.residuals.AdjustedLinkRatioResiduals;
import org.jreserve.jrlib.linkratio.scale.residuals.LRResidualTriangle;
import org.jreserve.jrlib.triangle.claim.ClaimTriangle;
import org.jreserve.jrlib.triangle.ratio.DefaultRatioTriangle;
import org.jreserve.jrlib.triangle.ratio.RatioTriangle;
import org.jreserve.jrlib.util.random.JavaRandom;
import org.jreserve.jrlib.util.random.Random;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class MclBootstrapSpeedTest3 {
    private final static long SEED = 100;
    private final static int N = 10000;
    private final static double LIMIT = 30d;
    private final static long TIMEOUT = 2L * ((long)LIMIT * 1000L);
    
    private MclBootstrapper bootstrap;
    private double paidMean;
    private double incurredMean;
    
    @BeforeClass
    public static void setUpClass() {
        //org.junit.Assume.assumeTrue("Mcl-Bootstrapper3 speed test skipped...", TestConfig.EXECUTE_SPEED_TESTS);
    }

    @Before
    public void setUp() {
        ClaimTriangle paidCik = TestData.getCummulatedTriangle(TestData.MCL_PAID);
        ClaimTriangle incurredCik = TestData.getCummulatedTriangle(TestData.MCL_INCURRED);

        LinkRatio paidLr = new SimpleLinkRatio(paidCik);
        LinkRatio incurredLr = new SimpleLinkRatio(incurredCik);
        
        LinkRatioScale paidScales = new SimpleLinkRatioScale(paidLr);
        LinkRatioScale incurredScales = new SimpleLinkRatioScale(incurredLr);
        
        LRResidualTriangle paidLrResiduals     = createLrResiduals(paidScales);
        LRResidualTriangle incurredLrResiduals = createLrResiduals(incurredScales);
        CRResidualTriangle paidCrResiduals     = createCrResiduals(incurredLr, paidLr);
        CRResidualTriangle incurredCrResiduals = createCrResiduals(paidLr, incurredLr);
        
        createMeans(paidLrResiduals, paidCrResiduals, incurredLrResiduals, incurredCrResiduals);
        createBs(paidLrResiduals, paidCrResiduals, incurredLrResiduals, incurredCrResiduals);
    }    
    
    private LRResidualTriangle createLrResiduals(LinkRatioScale scales) {
        return new AdjustedLinkRatioResiduals(scales);
    }
    
    private CRResidualTriangle createCrResiduals(LinkRatio lrN, LinkRatio lrD) {
        ClaimRatio crs = createClaimRatio(lrN, lrD);
        ClaimRatioScale scales = new SimpleClaimRatioScale(crs);
        return new AdjustedClaimRatioResiduals(scales);
    }

    private ClaimRatio createClaimRatio(LinkRatio numerator, LinkRatio denominator) {
        RatioTriangle ratios = new  DefaultRatioTriangle(numerator.getSourceTriangle(), denominator.getSourceTriangle());
        LrCrExtrapolation method = new LrCrExtrapolation(numerator, denominator);
        return new SimpleClaimRatio(ratios, method);
    }
    
    private void createMeans(LRResidualTriangle paidLr, CRResidualTriangle paidCr, LRResidualTriangle incurredLr, CRResidualTriangle incurredCr) {
        MclCorrelation paidLambda = new MclCorrelation(paidLr, paidCr);
        MclCorrelation incurredLambda = new MclCorrelation(incurredLr, incurredCr);
        MclCalculationBundle calcBundle = new MclCalculationBundle(paidLambda, incurredLambda);
        MclEstimateBundle eb = new MclEstimateBundle(calcBundle, false);
        paidMean = eb.getPaidEstimate().getReserve();
        incurredMean = eb.getIncurredEstimate().getReserve();
    }
    
    private void createBs(LRResidualTriangle paidLr, CRResidualTriangle paidCr, LRResidualTriangle incurredLr, CRResidualTriangle incurredCr) {
        Random rnd = new JavaRandom(SEED);
        MackProcessSimulator ps = new MackNormalProcessSimulator(rnd, paidLr.getSourceLinkRatioScales());
        MackProcessSimulator is = new MackNormalProcessSimulator(rnd, incurredLr.getSourceLinkRatioScales());
        
        MclResidualBundle resBundle = new MclResidualBundle(paidLr, paidCr, incurredLr, incurredCr);
        MclPseudoData pseudoData = new MclPseudoData(rnd, resBundle);
        
        MclBootstrapEstimateBundle estimate = new MclBootstrapEstimateBundle(pseudoData, ps, is);
        bootstrap = new MclBootstrapper(estimate, N);
    }

    @Test//(timeout=TIMEOUT)
    public void testSpeed() {
        System.out.println("Begin MCL-Bootstrapper2 speed test.\n\tTimeout: "+(TIMEOUT/1000));
        
        long begin = System.currentTimeMillis();
        bootstrap.run();
        long dif = System.currentTimeMillis() - begin;
        double seconds = (double)dif / 1000d;
        assertTrue(
                String.format("MCL-Bootstrapping2 %d times took %.3f seconds! Limit is %.3f seconds.", N, seconds, LIMIT), 
                seconds <= LIMIT);
        
        double bsPaidMean = BootstrapUtil.getMeanTotalReserve(bootstrap.getPaidReserves());
        double bsIncurredMean = BootstrapUtil.getMeanTotalReserve(bootstrap.getIncurredReserves());
        printHistogram(bootstrap.getPaidReserves(), paidMean);
        printHistogram(bootstrap.getIncurredReserves(), incurredMean);
        
        String msg = "MCL-Bootstrapping2 %d times took %.3f seconds.%n";
        msg += "\tPaid-reserve:%n\t\tBootstrap: %.0f%n\t\tOriginal: %.0f%n";
        msg += "\tIncurred-reserve:%n\t\tBootstrap: %.0f%n\t\tOriginal: %.0f%n";
        System.out.printf(msg, N, seconds, bsPaidMean, paidMean, bsIncurredMean, incurredMean);
    }
    
    private void printHistogram(double[][] reserves, double mean) {
        double[] total = BootstrapUtil.getTotalReserves(reserves);
        BootstrapUtil.scaleAdjustment(total, mean);
        double firstUpper = 1850000;
        double interval = 60000;
        //HistogramData data = new HistogramDataFactory(total).setIntervals(firstUpper, interval).buildData();
        HistogramData data = new HistogramDataFactory(total).setIntervalCount(50).buildData();
        
        int count = data.getIntervalCount();
        System.out.println("Interval\tLower\tUpper\tCount");
        String fromat = "%d\t%f\t%f\t%d%n";
        for(int i=0; i<count; i++)
            System.out.printf(fromat, i, data.getLowerBound(i), data.getUpperBound(i), data.getCount(i));
    }    
    
}