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

package org.jreserve.dummy.claimtriangle.edtior.trianglemodel;

import org.jreserve.jrlib.triangle.Triangle;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class VerticalCalendarModel extends AbstractTriangleModel {

    public VerticalCalendarModel() {
    }

    public VerticalCalendarModel(Triangle triangle) {
        super(triangle);
    }

    @Override
    protected int getAccidentIndex(int row, int column) {
        return column;
    }

    @Override
    protected int getDevelopmentIndex(int row, int column) {
        return row;
    }

    @Override
    public int getRowCount() {
        return triangle.getDevelopmentCount();
    }

    @Override
    public int getColumnCount() {
        return triangle.getAccidentCount();
    }

    @Override
    public String getRowTitle(int row) {
        return ""+(row+1);
    }

    @Override
    public String getColumnTitle(int column) {
        return ""+(1997+column)+"-01";
    }

}
