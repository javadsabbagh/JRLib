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
package org.jreserve.jrlib.scale;

import org.jreserve.jrlib.TestConfig;
import org.jreserve.jrlib.TestData;
import org.jreserve.jrlib.linkratio.LinkRatio;
import org.jreserve.jrlib.linkratio.SimpleLinkRatio;
import org.jreserve.jrlib.linkratio.scale.LinkRatioScaleInput;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class MinMaxScaleEstimatorTest {
    
    private final static double[] EXPECTED = {
        111.67022649, 55.88282502, 27.96528879, 19.10349518, 
         16.57903797, 17.16892279,  0.05132606
    };
    
    private DefaultScaleCalculator<LinkRatioScaleInput> source;
    private MinMaxScaleEstimator<LinkRatioScaleInput> estimate;

    @Before
    public void setUp() {
        LinkRatio lr = new SimpleLinkRatio(TestData.getCummulatedTriangle(TestData.INCURRED));
        LinkRatioScaleInput input = new LinkRatioScaleInput(lr);
        source = new DefaultScaleCalculator<LinkRatioScaleInput>(input);
        estimate = new MinMaxScaleEstimator();
    }

    @Test
    public void testFit() {
        estimate.fit(source);
        
        int length = EXPECTED.length;
        assertEquals(Double.NaN, estimate.getValue(-1), TestConfig.EPSILON);
        for(int i=0; i<length; i++)
            assertEquals(EXPECTED[i], estimate.getValue(i), TestConfig.EPSILON);
    }
}