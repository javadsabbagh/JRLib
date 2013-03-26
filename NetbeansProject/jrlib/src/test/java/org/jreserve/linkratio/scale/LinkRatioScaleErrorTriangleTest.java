package org.jreserve.linkratio.scale;

import org.jreserve.linkratio.scale.LinkRatioScale;
import org.jreserve.linkratio.scale.DefaultLinkRatioScaleSelection;
import org.jreserve.linkratio.scale.LinkRatioScaleErrorTriangle;
import org.jreserve.linkratio.scale.LinkRatioScaleMinMaxEstimator;
import org.jreserve.linkratio.scale.LinkRatioScaleSelection;
import org.jreserve.JRLibTestUtl;
import org.jreserve.TestData;
import org.jreserve.linkratio.LinkRatio;
import org.jreserve.linkratio.SimpleLinkRatio;
import org.jreserve.linkratio.smoothing.DefaultLinkRatioSmoothing;
import org.jreserve.linkratio.smoothing.LinkRatioFunction;
import org.jreserve.linkratio.smoothing.LinkRatioSmoothingSelection;
import org.jreserve.linkratio.smoothing.UserInputLRFunction;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Peter Decsi
 */
public class LinkRatioScaleErrorTriangleTest {
    
    private final static double[][] ERRORS = {
        {-0.53544715,  0.07687310, -0.88318295, -0.32853221,  0.75165510,  0.70651452, 0.00000000},
        {-0.53148094, -0.62744360, -0.37511590,  0.55345506,  0.46949846, -0.70769855},
        {-0.31361544, -0.61993017,  0.99245419,  1.03628787, -1.10208249},
        {-0.86407166,  1.51326125, -0.97735913, -1.22957779},
        { 2.13203521,  0.80608679,  1.06727679},
        {-0.15558769, -1.12975932},
        { 0.12676049}
    };
    
    private static LinkRatioScale PAID_SCALES;

    private LinkRatioScaleErrorTriangle errors;
    
    public LinkRatioScaleErrorTriangleTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        LinkRatio lr = new SimpleLinkRatio(TestData.getCummulatedTriangle(TestData.PAID));
        LinkRatioSmoothingSelection smoothing = new DefaultLinkRatioSmoothing(lr);
        smoothing.setDevelopmentCount(9);
        LinkRatioFunction tail = createTail(1.05, 1.02);
        smoothing.setMethod(tail, 7);
        smoothing.setMethod(tail, 8);
        
        LinkRatioScaleSelection scales = new DefaultLinkRatioScaleSelection(smoothing);
        scales.setMethod(new LinkRatioScaleMinMaxEstimator(), 6, 7, 8);
        PAID_SCALES = scales;
    }
    
    private static LinkRatioFunction createTail(double lr7, double lr8) {
        UserInputLRFunction tail = new UserInputLRFunction();
        tail.setValue(8, lr8);
        tail.setValue(8, lr8);
        return tail;
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        PAID_SCALES = null;
    }

    @Before
    public void setUp() {
        errors = new LinkRatioScaleErrorTriangle(PAID_SCALES);
    }

    @Test
    public void testGetAccidentCount() {
        assertEquals(ERRORS.length, errors.getAccidentCount());
    }

    @Test
    public void testGetDevelopmentCount_0args() {
        assertEquals(ERRORS[0].length, errors.getDevelopmentCount());
    }

    @Test
    public void testGetDevelopmentCount_int() {
        int accidents = ERRORS.length;
        for(int a=0; a<accidents; a++)
            assertEquals(ERRORS[a].length, errors.getDevelopmentCount(a));
    }

    @Test
    public void testRecalculateLayer() {
        int accidents = ERRORS.length;
        
        assertEquals(Double.NaN, errors.getValue(-1, 0), JRLibTestUtl.EPSILON);
        for(int a=0; a<accidents; a++) {
            int devs = ERRORS[a].length;
            assertEquals(Double.NaN, errors.getValue(a, -1), JRLibTestUtl.EPSILON);
            
            for(int d=0; d<devs; d++)
                assertEquals("At ["+a+"; "+d+"]", ERRORS[a][d], errors.getValue(a, d), JRLibTestUtl.EPSILON);
            assertEquals(Double.NaN, errors.getValue(a, devs), JRLibTestUtl.EPSILON);
        }
    }
}