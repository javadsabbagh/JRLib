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
package org.jreserve.jrlib.util.method;

import org.jreserve.jrlib.TestConfig;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class AbstractLinearRegressionTest {

    private final static double[] Y = {1.5, 2.0, 2.5, 3.0, 3.5, 4.0};
    private final static double[] X = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0};
    
    private AbstractLinearRegressionImpl lr;
    
    public AbstractLinearRegressionTest() {
    }

    @Before
    public void setUp() {
        lr = new AbstractLinearRegressionImpl();
    }

    @Test
    public void testSetExcluded() {
        assertArrayEquals(Y, lr.getYs(null), TestConfig.EPSILON);
        
        int index = 2;
        lr.setExcluded(index, true);
        assertTrue(lr.isExcluded(index));
        
        double[] y = lr.getYs(null);
        lr.excludeValues(y);
        for(int i=0; i<Y.length; i++) {
            if(i == index)
                assertTrue(Double.isNaN(y[i]));
            else
                assertEquals(Y[i], y[i], TestConfig.EPSILON);
        }
        
        lr.setExcluded(index, false);
        assertFalse(lr.isExcluded(index));
        y = lr.getYs(null);
        lr.excludeValues(y);
        assertArrayEquals(Y, y, TestConfig.EPSILON);
    }

    @Test
    public void testFit() {
        lr.fit(null);
        assertEquals(1.0, lr.intercept, TestConfig.EPSILON);
        assertEquals(0.5, lr.slope, TestConfig.EPSILON);
    }

    @Test
    public void testGetXZeroBased() {
        int length = 10;
        double[] x = lr.getXZeroBased(length);
        assertEquals(length, x.length);
        for(int i=0; i<length; i++)
            assertEquals((double)i, x[i], TestConfig.EPSILON);
    }

    @Test
    public void testGetXOneBased() {
        int length = 10;
        double[] x = lr.getXOneBased(length);
        assertEquals(length, x.length);
        for(int i=0; i<length; i++)
            assertEquals((double)(i+1), x[i], TestConfig.EPSILON);
    }

    public class AbstractLinearRegressionImpl extends AbstractLinearRegression<Object> {

        //y = 1 + 0.5 * x
        @Override
        public double[] getYs(Object source) {
            double[] y = new double[Y.length];
            System.arraycopy(Y, 0, y, 0, Y.length);
            return y;
        }

        @Override
        public double[] getXs(Object source) {
            double[] x = new double[X.length];
            System.arraycopy(X, 0, x, 0, X.length);
            return x;
        }

        @Override
        public double getValue(int index) {
            double x = (double) (index+1);
            return intercept + slope * x;
        }

        public SelectableMethod<Object> copy() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
