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
package org.jreserve.jrlib.claimratio;

import org.jreserve.jrlib.TestConfig;
import org.jreserve.jrlib.TestData;
import org.jreserve.jrlib.linkratio.LinkRatio;
import org.jreserve.jrlib.linkratio.curve.DefaultLinkRatioSmoothing;
import org.jreserve.jrlib.linkratio.curve.LinkRatioSmoothingSelection;
import org.jreserve.jrlib.linkratio.curve.UserInputLRCurve;
import org.jreserve.jrlib.triangle.claim.ClaimTriangle;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Peter Decsi
 */
public class LrCrExtrapolationTest {

    private final static double[] EXPECTED = {
        Double.NaN, 1.18629052, 1.14764342, 1.11755801, 1.10071707, 
        1.09343379, 1.08549400, 1.07195718, 1.00840921
    };
    
    private LrCrExtrapolation estimate;
    private ClaimRatio cr;

    @Before
    public void setUp() {
        ClaimTriangle paid = TestData.getCummulatedTriangle(TestData.PAID);
        ClaimTriangle incurred = TestData.getCummulatedTriangle(TestData.INCURRED);
        
        LinkRatio lrPaid = createLinkRatio(paid, 1.05, 1.02);
        LinkRatio lrIncurred = createLinkRatio(incurred, 1.03, 1.01);
        cr = new ClaimRatioCalculator(incurred, paid);
        estimate = new LrCrExtrapolation(lrIncurred, lrPaid);
    }
    
    private LinkRatio createLinkRatio(ClaimTriangle cik, double t1, double t2) {
        LinkRatioSmoothingSelection lrs = new DefaultLinkRatioSmoothing(cik);
        lrs.setDevelopmentCount(9);
        UserInputLRCurve tail = new UserInputLRCurve();
        tail.setValue(7, t1);
        tail.setValue(8, t2);
        lrs.setMethod(tail , 7, 8);
        return lrs;
    }

    @Test
    public void testFit() {
        estimate.fit(cr);
        
        int devs = EXPECTED.length;
        for(int d=0; d<devs; d++)
            assertEquals(EXPECTED[d], estimate.getValue(d), TestConfig.EPSILON);
    }
}