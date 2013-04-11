package org.jrlib.linkratio;

import java.util.HashMap;
import java.util.Map;
import org.jrlib.ChangeCounter;
import org.jrlib.TestConfig;
import org.jrlib.TestData;
import org.jrlib.triangle.factor.FactorTriangle;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class DefaultLinkRatioSelectionTest {

    private ChangeCounter listener;
    private FactorTriangle factors;
    private DefaultLinkRatioSelection lr;
    
    public DefaultLinkRatioSelectionTest() {
    }

    @Before
    public void setUp() {
        factors = TestData.getDevelopmentFactors(TestData.PAID);
        lr = new DefaultLinkRatioSelection(factors);
        
        listener = new ChangeCounter();
        lr.addChangeListener(listener);
    }

    @Test
    public void testGetDefaultMethod() {
        assertTrue(lr.getDefaultMethod() instanceof WeightedAverageLRMethod);
    }

    @Test
    public void testSetDefaultMethod() {
        LinkRatioMethod method = new MinLRMethod();
        lr.setDefaultMethod(method);
        assertTrue(lr.getDefaultMethod() instanceof MinLRMethod);
        
        lr.setMethod(null, 0);
        assertTrue(lr.getMethod(0) instanceof MinLRMethod);
    }

    @Test
    public void testSetMethod() {
        LinkRatioMethod method = new MinLRMethod();
        lr.setMethod(method, 1);
        assertEquals(method, lr.getMethod(1));
        assertEquals(1, listener.getChangeCount());
        
        
        lr.setMethod(null, 1);
        assertTrue(lr.getMethod(1) instanceof WeightedAverageLRMethod);
        assertEquals(2, listener.getChangeCount());
    }

    @Test
    public void testSetMethods() {
        Map<Integer, LinkRatioMethod> methods = new HashMap<Integer, LinkRatioMethod>();
        methods.put(1, new MinLRMethod());
        methods.put(2, new MaxLRMethod());
        methods.put(3, new AverageLRMethod());
        lr.setMethods(methods);
        assertEquals(1, listener.getChangeCount());
        
        assertTrue(lr.getMethod(1) instanceof MinLRMethod);
        assertTrue(lr.getMethod(2) instanceof MaxLRMethod);
        assertTrue(lr.getMethod(3) instanceof AverageLRMethod);
    }

    @Test
    public void testGetMethod() {
        LinkRatioMethod defaultMethod = lr.getDefaultMethod();
        
        int devs = factors.getDevelopmentCount();
        for(int d=0; d<devs; d++)
            assertEquals(defaultMethod, lr.getMethod(d));
        assertEquals(defaultMethod, lr.getMethod(devs));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetMethod_MinDev() {
        lr.getMethod(-1);
    }

    @Test
    public void testGetDevelopmentCount() {
        int expected = factors.getDevelopmentCount();
        int found = lr.getLength();
        assertEquals(expected, found);
    }
    
    @Test
    public void testGetDevelopmentCount_Tail() {
        int expected = lr.getLength();
        UserInputLRMethod uiLR = new UserInputLRMethod();
        uiLR.setValue(expected, 1.05);
        lr.setMethod(uiLR, expected);
        assertEquals(expected, lr.getLength());
    }

    @Test
    public void testGetValue() {
        Map<Integer, LinkRatioMethod> methods = new HashMap<Integer, LinkRatioMethod>();
        methods.put(1, new MinLRMethod());
        methods.put(2, new MaxLRMethod());
        methods.put(3, new AverageLRMethod());
        lr.setMethods(methods);
        double[] expected = {
            1.24694402, 1.00906323, 1.01671665, 1.00965883, 
            1.00347944, 1.00335199, 1.00191164
        };
        assertArrayEquals(expected, lr.toArray(), TestConfig.EPSILON);
    }
}