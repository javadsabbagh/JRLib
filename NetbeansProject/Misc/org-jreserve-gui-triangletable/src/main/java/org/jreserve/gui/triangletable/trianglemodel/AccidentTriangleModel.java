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
package org.jreserve.gui.triangletable.trianglemodel;

import org.jreserve.jrlib.triangle.Triangle;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class AccidentTriangleModel extends AbstractTriangleModel {

    public AccidentTriangleModel() {
        super();
    }

    public AccidentTriangleModel(TitleModel horizontalTitles, TitleModel verticalTitles) {
        super(horizontalTitles, verticalTitles);
    }

    public AccidentTriangleModel(Triangle triangle) {
        super(triangle);
    }

    public AccidentTriangleModel(Triangle triangle, TitleModel horizontalTitles, TitleModel verticalTitles) {
        super(triangle, horizontalTitles, verticalTitles);
    }

    @Override
    public int getRowCount() {
        return triangle == null ? 0 : triangle.getDevelopmentCount();
    }

    @Override
    public int getColumnCount() {
        return triangle == null ? 0 : triangle.getAccidentCount();
    }

    @Override
    public int getRowIndex(int accident, int development) {
        return development;
    }

    @Override
    public int getColumnIndex(int accident, int development) {
        return accident;
    }

    @Override
    public int getDevelopmentIndex(int row, int column) {
        return row;
    }

    @Override
    public int getAccidentIndex(int row, int column) {
        return column;
    }
}
