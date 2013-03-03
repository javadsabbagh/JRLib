package org.jreserve.factor.linkratio;

import org.jreserve.JRLibTestSuite;
import org.jreserve.TestData;
import org.jreserve.factor.DevelopmentFactors;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Peter Decsi
 */
public class MaxLRMethodTest {
        
    private final static double[] EXPECTED = {
        1.32749558,
        1.02595957,
        1.01671671,
        1.01683530,
        1.00480590,
        1.00659039,
        1.00191166
    };

    private DevelopmentFactors factors;

    public MaxLRMethodTest() {
    }

    @Before
    public void setUp() {
        factors = new DevelopmentFactors(TestData.getCummulatedTriangle(TestData.PAID));
    }

    @Test
    public void testGetLinkRatios() {
        MaxLRMethod lrs = new MaxLRMethod();
        lrs.fit(factors);
        
        int length = EXPECTED.length;
        assertEquals(Double.NaN, lrs.getValue(-1), JRLibTestSuite.EPSILON);
        for(int d=0; d<length; d++)
            assertEquals(EXPECTED[d], lrs.getValue(d), JRLibTestSuite.EPSILON);
        assertEquals(Double.NaN, lrs.getValue(length), JRLibTestSuite.EPSILON);
    }
    
    @Test
    public void testGetMackAlpha() {
        MaxLRMethod lrs = new MaxLRMethod();
        assertEquals(1d, lrs.getMackAlpha(), JRLibTestSuite.EPSILON);
    }
}