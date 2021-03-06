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
package org.jreserve.jrlib.vector.smoothing;

import org.jreserve.jrlib.TestConfig;
import org.jreserve.jrlib.triangle.smoothing.ArithmeticMovingAverage;
import org.jreserve.jrlib.triangle.smoothing.SmoothingCell;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class ArithmeticMovingAverageMethodTest {

    private final static double[] INPUT = {
        4873558, 5130849, 5945610, 6632221, 
        7020974, 8275452, 9000367, 9511539
    };
    
    private double[] input;
    private ArithmeticMovingAverageMethod smoothing;
    
    public ArithmeticMovingAverageMethodTest() {
    }
    
    @Before
    public void setUp() {
        int size = INPUT.length;
        input = new double[size];
        System.arraycopy(INPUT, 0, input, 0, size);
    }
    
    @Test
    public void testSmooth_Ma3() {
        smoothing = new ArithmeticMovingAverageMethod(3);
        double[] expected = new double[] {
            4873558.000000000, 5130849.000000000, 5316672.3333333333, 5902893.3333333333, 
            6532935.000000000, 7309549.000000000, 8098931.000000000, 8929119.3333333333
        };
        smoothing.smooth(input);
        assertArrayEquals(expected, input, TestConfig.EPSILON);
    }
        
    @Test
    public void testSmooth_Ma5() {
        smoothing = new ArithmeticMovingAverageMethod(5);
        double[] expected = new double[] {
            4873558.000, 5130849.000, 5945610.000, 6632221.000, 
            5920642.400, 6601021.200, 7374924.800, 8088110.600
        };
        smoothing.smooth(input);
        assertArrayEquals(expected, input, TestConfig.EPSILON);
    }

    @Test
    public void testSmooth_Ma9() {
        smoothing = new ArithmeticMovingAverageMethod(9);
        smoothing.smooth(input);
        assertArrayEquals(INPUT, input, TestConfig.EPSILON);
    }
}
