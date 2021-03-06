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
package org.jreserve.jrlib.claimratio.scale.residuals;

import org.jreserve.jrlib.TestConfig;
import org.jreserve.jrlib.TestData;
import org.jreserve.jrlib.claimratio.ClaimRatio;
import org.jreserve.jrlib.claimratio.ClaimRatioCalculator;
import org.jreserve.jrlib.claimratio.scale.ClaimRatioScale;
import org.jreserve.jrlib.claimratio.scale.SimpleClaimRatioScale;
import org.jreserve.jrlib.triangle.claim.ClaimTriangle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Peter Decsi
 */
public class ClaimRatioResidualsTest {

    private final static double[][] EXPECTED = {
        {-1.33089143, -1.80917390, -1.72446110, -1.73477463, -1.34141175, -0.95840097, -0.70536960, 0.00000000},
        {-0.66745059, -0.57102319,  0.05194833,  0.70711037,  1.03845917,  1.03757548,  0.70883970},
        {-1.18758856, -0.29273228,  0.21038152,  0.00328244, -0.06770761, -0.07003361},
        {-0.21723010,  0.31651779, -0.40077902,  0.21503292,  0.34297629},
        { 0.14074161, -0.09213231,  0.21948460,  0.66655980},
        { 0.20104705,  0.66479279,  1.33059294},
        { 1.47963872,  1.32834815},
        { 1.03736365}
    };
    
    private ClaimRatioResiduals residuals;

    @Before
    public void setUp() {
        ClaimTriangle paid = TestData.getCummulatedTriangle(TestData.PAID);
        ClaimTriangle incurred = TestData.getCummulatedTriangle(TestData.INCURRED);
        ClaimRatio ratios = new ClaimRatioCalculator(incurred, paid);
        ClaimRatioScale scales = new SimpleClaimRatioScale(ratios);
        residuals = new ClaimRatioResiduals(scales);
    }

    @Test
    public void testRecalculate() {
        int accidents = EXPECTED.length;
        assertEquals(accidents, residuals.getAccidentCount());
        
        assertEquals(Double.NaN, residuals.getValue(-1, 0), TestConfig.EPSILON);
        for(int a=0; a<accidents; a++) {
            int devs = EXPECTED[a].length;
            assertEquals(devs, residuals.getDevelopmentCount(a));
            assertEquals(Double.NaN, residuals.getValue(a, -1), TestConfig.EPSILON);
            
            for(int d=0; d<devs; d++)
                assertEquals(EXPECTED[a][d], residuals.getValue(a, d), TestConfig.EPSILON);
            
            assertEquals(Double.NaN, residuals.getValue(a, devs), TestConfig.EPSILON);
        }
        assertEquals(Double.NaN, residuals.getValue(accidents, 0), TestConfig.EPSILON);
    }
}