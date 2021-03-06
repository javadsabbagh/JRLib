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
package org.jreserve.jrlib.linkratio.curve;

import org.jreserve.jrlib.TestConfig;
import org.jreserve.jrlib.TestData;
import org.jreserve.jrlib.linkratio.LinkRatio;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class PowerLRCurveTest {
    
    private PowerLRCurve pf;

    public PowerLRCurveTest() {
    }

    @Before
    public void setUp() {
        pf = new PowerLRCurve();
    }

    @Test
    public void testFit_Paid() {
        LinkRatio lr = TestData.getLinkRatio(TestData.PAID);
        pf.fit(lr);
        assertEquals(1.13911782, pf.getA(), TestConfig.EPSILON);
        assertEquals(0.51942242, pf.getB(), TestConfig.EPSILON);
    }

    @Test
    public void testFit_Incurred() {
        LinkRatio lr = TestData.getLinkRatio(TestData.INCURRED);
        pf.fit(lr);
        assertEquals(2.15562544, pf.getA(), TestConfig.EPSILON);
        assertEquals(0.30319355, pf.getB(), TestConfig.EPSILON);
    }

    @Test
    public void testGetA() {
        assertEquals(1d, pf.getA(), TestConfig.EPSILON);
    }

    @Test
    public void testGetB() {
        assertEquals(1d, pf.getB(), TestConfig.EPSILON);
    }

    @Test
    public void testGetValue() {
        for(int d=0; d<7; d++)
            assertEquals(1d, pf.getValue(d+1), TestConfig.EPSILON);
    }

    @Test
    public void testEquals() {
        PowerLRCurve pf1 = new PowerLRCurve();
        PowerLRCurve pf2 = null;
        assertFalse(pf1.equals(pf2));
        
        pf2 = new PowerLRCurve();
        assertTrue(pf1.equals(pf2));
        assertTrue(pf2.equals(pf1));
        
        pf1.fit(TestData.getLinkRatio(TestData.PAID));
        pf2.fit(TestData.getLinkRatio(TestData.INCURRED));
        assertTrue(pf1.equals(pf2));
        assertTrue(pf2.equals(pf1));
    }
}
