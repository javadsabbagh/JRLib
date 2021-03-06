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
package org.jreserve.jrlib.estimate;

import org.jreserve.jrlib.TestConfig;
import org.jreserve.jrlib.TestData;
import org.jreserve.jrlib.linkratio.LinkRatio;
import org.jreserve.jrlib.linkratio.curve.DefaultLinkRatioSmoothing;
import org.jreserve.jrlib.linkratio.curve.LinkRatioSmoothingSelection;
import org.jreserve.jrlib.linkratio.curve.UserInputLRCurve;
import org.jreserve.jrlib.triangle.claim.ClaimTriangle;
import org.jreserve.jrlib.vector.InputVector;
import org.jreserve.jrlib.vector.Vector;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class BornhuetterFergusonEstimateTest {

    private final static double[] LOSS_RATIOS = {
        16.74578457, 16.74578457, 16.74578457, 16.74578457,
        16.74578457, 16.74578457, 16.74578457, 16.74578457
    };
    
    private final static double[][] EXPECTED = {
        {4873559.00000000,  5726628.00000000,  5643478.00000000,  5703210.00000000,  5787091.00000000,  5858937.00000000,  5862561.00000000,  5791898.00000000,  6067702.66670453},
        {5130849.00000000,  6098121.00000000,  6177774.00000000,  6165459.00000000,  6292000.00000000,  6304102.00000000,  6304337.00000000,  6227424.94673880,  6542630.05228160},
        {5945611.00000000,  7525656.00000000,  7557815.00000000,  7377335.00000000,  7442120.00000000,  7418866.00000000,  7421142.86811285,  7334616.66226264,  7689223.00406202},
        {6632221.00000000,  7861102.00000000,  7532937.00000000,  7528468.00000000,  7525868.00000000,  7550582.57885041,  7553112.43060243,  7456972.26673837,  7850979.04737602},
        {7020974.00000000,  8688586.00000000,  8712864.00000000,  8674967.00000000,  8763326.99129776,  8790583.99010593,  8793374.09090461,  8687343.86689307,  9121882.62295494},
        {8275453.00000000,  9867326.00000000,  9932304.00000000,  9883419.23149309,  9983995.98896414, 10015021.57552490, 10018197.43925270,  9897507.33993983, 10392125.98394730},
        {9000368.00000000, 10239888.00000000, 10190541.05921510, 10137886.58171260, 10246219.23368750, 10279637.33301500, 10283058.10088020, 10153061.08313540, 10685821.83988350},
        {9511539.00000000, 11394535.69850380, 11341462.03543610, 11284831.04770860, 11401345.07226010, 11437286.93442750, 11440966.04154580, 11301151.54018340, 11874146.83532500}
    };
    
    private final static double[] RESERVES = {
        275804.66670453, 238293.05228160, 270357.00406202, 325111.04737602, 
        446915.62295494, 459821.98394729, 445933.83988348, 2362607.83532500 
    };
    
    private BornhuetterFergusonEstimate estiamte;
    private LinkRatio lrs;
    private Vector exposure;
    private Vector lossRatios;

    public BornhuetterFergusonEstimateTest() {
    }

    @Before
    public void setUp() {
        lrs = createLrs();
        exposure = new InputVector(TestData.getCachedVector(TestData.EXPOSURE));
        lossRatios = new InputVector(LOSS_RATIOS);
        estiamte = new BornhuetterFergusonEstimate(lrs, exposure, lossRatios);
    }
    
    private LinkRatio createLrs() {
        ClaimTriangle cik = TestData.getCummulatedTriangle(TestData.INCURRED);
        LinkRatioSmoothingSelection smoothing = new DefaultLinkRatioSmoothing(cik);
        smoothing.setDevelopmentCount(8);
        smoothing.setMethod(new UserInputLRCurve(7, 1.05), 7);
        return smoothing;
    }

    @Test
    public void testGetObservedDevelopmentCount() {
        ClaimTriangle cik = lrs.getSourceFactors().getSourceTriangle();
        int accidents = estiamte.getDevelopmentCount();
        for(int a=0; a<accidents; a++)
            assertEquals(cik.getDevelopmentCount(a), estiamte.getObservedDevelopmentCount(a));
    }

    @Test
    public void testRecalculateLayer() {
        int accidents = EXPECTED.length;
        int developments = EXPECTED[0].length;
        
        assertEquals(accidents, estiamte.getAccidentCount());
        assertEquals(developments, estiamte.getDevelopmentCount());
        
        assertEquals(Double.NaN, estiamte.getValue(0, -1), TestConfig.EPSILON);
        assertEquals(Double.NaN, estiamte.getValue(-1, 0), TestConfig.EPSILON);
        for(int a=0; a<accidents; a++)
            for(int d=0; d<developments; d++)
                assertEquals(EXPECTED[a][d], estiamte.getValue(a, d), TestConfig.EPSILON);
        assertEquals(Double.NaN, estiamte.getValue(0, developments), TestConfig.EPSILON);
        assertEquals(Double.NaN, estiamte.getValue(accidents, 0), TestConfig.EPSILON);
        
        assertEquals(Double.NaN, estiamte.getReserve(-1), TestConfig.EPSILON);
        for(int a=0; a<accidents; a++)
            assertEquals(RESERVES[a], estiamte.getReserve(a), TestConfig.EPSILON);
        assertEquals(Double.NaN, estiamte.getReserve(accidents), TestConfig.EPSILON);
    }
}