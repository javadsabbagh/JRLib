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
package org.jreserve.jrlib.triangle;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class TriangleGeometryTest {

    private final static int ACCIDENTS = 4;
    private final static int DEVELOPMENTS = 8;
    private final static int ACCIDENT_LENGTH = 2;
    
    private TriangleGeometry geometry;
    
    public TriangleGeometryTest() {
    }

    @Before
    public void setUp() {
        geometry = new TriangleGeometry(ACCIDENTS, DEVELOPMENTS, ACCIDENT_LENGTH);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testConstructor_NegativeAccidents() {
        geometry = new TriangleGeometry(-1, 0, 1);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testConstructor_NegativeDevelopments() {
        geometry = new TriangleGeometry(0, -1, 1);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testConstructor_LesThenOnePeriodLength() {
        geometry = new TriangleGeometry(0, 0, 0);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testConstructor_WellAccidentNoDevelopment() {
        geometry = new TriangleGeometry(2, 0, 1);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testConstructor_EmptyAccidents() {
        geometry = new TriangleGeometry(3, 8, 4);
    }
    
    @Test
    public void testGetParameters() {
        assertEquals(ACCIDENTS, geometry.getAccidents());
        assertEquals(DEVELOPMENTS, geometry.getDevelopments());
        assertEquals(ACCIDENT_LENGTH, geometry.getAccidentLength());
    }

    @Test
    public void testEquals_Object() {
        Object o = null;
        assertFalse(geometry.equals(o));
        
        o = new Object();
        assertFalse(geometry.equals(o));
    }

    @Test
    public void testEquals_TriangleGeometry() {
        assertTrue(geometry.equals(geometry));
        TriangleGeometry o = null;
        assertFalse(geometry.equals(o));
        
        o = new TriangleGeometry(ACCIDENTS, DEVELOPMENTS, ACCIDENT_LENGTH);
        assertTrue(geometry.equals(o));
        assertTrue(o.equals(geometry));
        
        o = new TriangleGeometry(ACCIDENTS, DEVELOPMENTS, ACCIDENT_LENGTH - 1);
        assertFalse(geometry.equals(o));
        assertFalse(o.equals(geometry));
        
        o = new TriangleGeometry(ACCIDENTS, DEVELOPMENTS - 1, ACCIDENT_LENGTH);
        assertFalse(geometry.equals(o));
        assertFalse(o.equals(geometry));
        
        o = new TriangleGeometry(ACCIDENTS - 1, DEVELOPMENTS, ACCIDENT_LENGTH);
        assertFalse(geometry.equals(o));
        assertFalse(o.equals(geometry));
    }

    @Test
    public void testToString() {
        String expected = 
            "TriangleGeometry ["+
                ACCIDENTS+"; "+DEVELOPMENTS+"; "+
                ACCIDENT_LENGTH+"]";
        assertEquals(expected, geometry.toString());
    }

}
