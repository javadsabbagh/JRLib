package org.jreserve.jrlib.bootstrap.mack;

import org.jreserve.jrlib.TestConfig;
import org.jreserve.jrlib.TestData;
import org.jreserve.jrlib.bootstrap.EstimateBootstrapper;
import org.jreserve.jrlib.linkratio.LinkRatio;
import org.jreserve.jrlib.linkratio.SimpleLinkRatio;
import org.jreserve.jrlib.linkratio.scale.LinkRatioScale;
import org.jreserve.jrlib.linkratio.scale.SimpleLinkRatioScale;
import org.jreserve.jrlib.linkratio.scale.residuals.AdjustedLinkRatioResiduals;
import org.jreserve.jrlib.linkratio.scale.residuals.LRResidualTriangle;
import org.jreserve.jrlib.triangle.claim.ClaimTriangle;
import org.jreserve.jrlib.util.MathUtil;
import org.jreserve.jrlib.util.random.JavaRandom;
import org.jreserve.jrlib.util.random.Random;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class MackBootstrapSpeedTest {
    
    private final static long SEED = 100;
    private final static int N = 100000;
    private final static double LIMIT = 5d;
    private final static long TIMEOUT = 2L * ((long)LIMIT * 1000L);
    private EstimateBootstrapper<MackBootstrapEstimate> bootstrap;

    @BeforeClass
    public static void setUpClass() {
        org.junit.Assume.assumeTrue("Mack-Bootstrapper speed test skipped...", TestConfig.EXECUTE_SPEED_TESTS);
    }
    
    @Before
    public void setUp() {
        Random rnd = new JavaRandom(SEED);
        
        ClaimTriangle cik = TestData.getCummulatedTriangle(TestData.PAID);
        LinkRatio lrs = new SimpleLinkRatio(cik);
        LinkRatioScale scales = new SimpleLinkRatioScale(lrs); //MinMaxScaleEstimate
        LRResidualTriangle residuals = new AdjustedLinkRatioResiduals(scales);
        
        MackPseudoFactorTriangle pseudoFik = new MackPseudoFactorTriangle(rnd, residuals);
        MackGammaProcessSimulator procSim = new MackGammaProcessSimulator(rnd, scales);
        MackBootstrapEstimate mackEstimate = new MackBootstrapEstimate(lrs, procSim);
        lrs.setSource(pseudoFik);
        bootstrap = new EstimateBootstrapper<MackBootstrapEstimate>(mackEstimate, N);
    }

    @Test(timeout=TIMEOUT)
    public void testSpeed() {
        System.out.println("Begin Mack-Bootstrapper speed test.\n\tTimeout: "+(TIMEOUT/1000));
        
        long begin = System.currentTimeMillis();
        
        bootstrap.run();
        
        long end = System.currentTimeMillis();
        
        double[][] reserves = bootstrap.getReserves();
        int n=0;
        double mean = 0d;
        for(int i=0; i<N; i++) {
            double w = (double) n / (double)(++n);
            double r = MathUtil.sum(reserves[i]);
            mean = w*mean + (1-w) * r;
        }
        
        long dif = end - begin;
        double seconds = (double)dif / 1000d;
        
        System.out.printf("Mack-Bootstrapping %d times took %.3f seconds.%n\t Reserve: %.0f%n", N, seconds, mean);
        assertTrue(
                String.format("Mack-Bootstrapping %d times took %.3f seconds! Limit is %.3f seconds.", N, seconds, LIMIT), 
                seconds <= LIMIT);
    }
}
