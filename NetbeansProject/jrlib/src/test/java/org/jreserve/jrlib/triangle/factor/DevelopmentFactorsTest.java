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
package org.jreserve.jrlib.triangle.factor;

import static org.jreserve.jrlib.TestConfig.EPSILON;
import org.jreserve.jrlib.triangle.claim.ClaimTriangle;
import org.jreserve.jrlib.triangle.claim.CummulatedClaimTriangle;
import org.jreserve.jrlib.triangle.claim.InputClaimTriangle;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class DevelopmentFactorsTest {

    private final static double[][] INCREMENTIAL = {
        {26373, 1173, 14, 4 , 2, 11, 0, 0},
        {27623, 1078, 19, 11, 8, 0 , 0},
        {30908, 1299, 27, 17, 2, 1},
        {31182, 1392, 46, 2 , 1},
        {32855, 1754, 46, 15, },
        {37661, 1634, 54},
        {38160, 1696},
        {40194}
    };
    
    private final static double[][] expectedIncremential = {
        {0.04447731, 0.01193521, 0.28571429, 0.50000000, 5.50000000, 0.00000000, Double.NaN},
        {0.03902545, 0.01762523, 0.57894737, 0.72727273, 0.00000000, Double.NaN},
        {0.04202795, 0.02078522, 0.62962963, 0.11764706, 0.50000000},
        {0.04464114, 0.03304598, 0.04347826, 0.50000000},
        {0.05338609, 0.02622577, 0.32608696},	
        {0.04338706, 0.03304774},		
        {0.04444444}			
    };
    
    private final static double[][] expectedCummulated = {
        {1.04447731, 1.00050824, 1.00014514, 1.00007256, 1.00039904, 1.00000000, 1.00000000},
        {1.03902545, 1.00066200, 1.00038301, 1.00027844, 1.00000000, 1.00000000},
        {1.04202795, 1.00083833, 1.00052739, 1.00006201, 1.00003100},
        {1.04464114, 1.00141217, 1.00006131, 1.00003065},
        {1.05338609, 1.00132913, 1.00043284},
        {1.04338706, 1.00137422},
        {1.04444444}
    };
    
    
    private ClaimTriangle source;
    private DevelopmentFactors incrementialFactors;
    private DevelopmentFactors cummulatedFactors;
    
    public DevelopmentFactorsTest() {
    }

    @Before
    public void setUp() {
        source = new InputClaimTriangle(INCREMENTIAL);
        incrementialFactors = new DevelopmentFactors(source);
        ClaimTriangle cummulated = new CummulatedClaimTriangle(source);
        cummulatedFactors = new DevelopmentFactors(cummulated);
    }

    @Test
    public void testGetAccidentCount() {
        assertEquals(source.getAccidentCount()-1, incrementialFactors.getAccidentCount());
    }

    @Test
    public void testGetDevelopmentCount_0args() {
        assertEquals(source.getDevelopmentCount()-1, incrementialFactors.getDevelopmentCount());
    }

    @Test
    public void testGetDevelopmentCount_int() {
        int accidents = incrementialFactors.getAccidentCount();
        for(int a=0; a<accidents; a++)
            assertEquals(source.getDevelopmentCount(a)-1, incrementialFactors.getDevelopmentCount(a));
    }

    @Test
    public void testRecalculateLayer_Incremential() {
        double[][] found = incrementialFactors.toArray();
        int size = expectedIncremential.length;
        assertEquals(size, found.length);
        for(int a=0; a<size; a++)
            assertArrayEquals(expectedIncremential[a], found[a], EPSILON);
    }

    @Test
    public void testRecalculateLayer_Cummulated() {
        double[][] found = cummulatedFactors.toArray();
        int size = expectedCummulated.length;
        assertEquals(size, found.length);
        for(int a=0; a<size; a++)
            assertArrayEquals(expectedCummulated[a], found[a], EPSILON);
    }
}
