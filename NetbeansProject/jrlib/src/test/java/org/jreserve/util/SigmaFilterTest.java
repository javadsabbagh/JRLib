package org.jreserve.util;

import static org.jreserve.JRLibTestSuite.EPSILON;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Peter Decsi
 */
public class SigmaFilterTest {

    private final static double[] DATA = {
        1.21380200, 1.02595959, 1.00242243, 1.00094901
    };
    
    private final static boolean[] TRESHOLD_1 = {
        true, false, false, false
    };
    
    private final static boolean[] TRESHOLD_0_5 = {
        true, false, true, true
    };
    
    private SigmaFilter filter;
    
    public SigmaFilterTest() {
    }

    @Before
    public void setUp() {
        filter = new SigmaFilter();
    }

    @Test
    public void testConstructor() {
        assertEquals(SigmaFilter.DEFAULT_TRESHOLD, filter.getTreshold(), EPSILON);
        
        filter = new SigmaFilter(-1d);
        assertEquals(0d, filter.getTreshold(), EPSILON);
        
        filter = new SigmaFilter(5d);
        assertEquals(5d, filter.getTreshold(), EPSILON);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructor_NaN() {
        filter = new SigmaFilter(Double.NaN);
    }

    @Test
    public void testSetTreshold() {
        filter.setTreshold(-1d);
        assertEquals(0d, filter.getTreshold(), EPSILON);
        
        filter.setTreshold(1d);
        assertEquals(1d, filter.getTreshold(), EPSILON);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSetTreshold_NaN() {
        filter.setTreshold(Double.NaN);
    }

    @Test
    public void testFilter() {
        filter = new SigmaFilter(1d);
        boolean[] found = filter.filter(DATA);
        assertEquals(TRESHOLD_1.length, found.length);
        for(int i=0; i<found.length; i++)
            assertEquals("For treshold 1.5, index "+i+".", TRESHOLD_1[i], found[i]);
        
        filter.setTreshold(0.5);
        found = filter.filter(DATA);
        assertEquals(TRESHOLD_0_5.length, found.length);
        for(int i=0; i<found.length; i++)
            assertEquals("For treshold 1, index "+i+".", TRESHOLD_0_5[i], found[i]);
    }
}