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
package org.jreserve.jrlib.linkratio.standarderror;

import org.jreserve.jrlib.linkratio.standarderror.LogLinearRatioSEFunction;
import org.jreserve.jrlib.linkratio.standarderror.LinkRatioSECalculator;
import org.jreserve.jrlib.linkratio.standarderror.LinkRatioSE;
import org.jreserve.jrlib.TestConfig;
import org.jreserve.jrlib.TestData;
import org.jreserve.jrlib.linkratio.LinkRatio;
import org.jreserve.jrlib.linkratio.SimpleLinkRatio;
import org.jreserve.jrlib.linkratio.scale.LinkRatioScale;
import org.jreserve.jrlib.linkratio.scale.SimpleLinkRatioScale;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class LogLinearRatioSEFunctionTest {

    private final static double[] EXPECTED = {
        1.95905199, 0.47955194, 0.13293869, 0.06596653, 
        0.03911156, 0.02437721, 0.01555010, 0.01029200
    };
    
    private LogLinearRatioSEFunction sef;
    private LinkRatioSE ses;

    public LogLinearRatioSEFunctionTest() {
    }

    @Before
    public void setUp() {
        LinkRatio lrs = new SimpleLinkRatio(TestData.getCummulatedTriangle(TestData.MACK_DATA));
        LinkRatioScale scales = new SimpleLinkRatioScale(lrs);
        ses = new LinkRatioSECalculator(scales);
        sef = new LogLinearRatioSEFunction();
    }

    @Test
    public void testFit() {
        sef.fit(ses);
        assertEquals(-1.325732323, sef.getIntercept(), TestConfig.EPSILON);
        assertEquals(-0.409135714, sef.getSlope(), TestConfig.EPSILON);
        
        int devs = EXPECTED.length;
        for(int d=0; d<devs; d++)
            assertEquals(EXPECTED[d], sef.getValue(d), TestConfig.EPSILON);
        assertEquals(Double.NaN, sef.getValue(devs), TestConfig.EPSILON);
    }

}
