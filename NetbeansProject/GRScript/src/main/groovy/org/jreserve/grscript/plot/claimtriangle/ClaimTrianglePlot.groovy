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

package org.jreserve.grscript.plot.claimtriangle

import org.jreserve.jrlib.triangle.claim.ClaimTriangle
import org.jreserve.grscript.plot.PlotFormat
/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
class ClaimTrianglePlot {
	
    static Map createPlot(ClaimTriangle triangle, PlotFormat format) {
        [
            "Accident periods": new AccidentPeriodPlot(triangle, format).buildChart()       ,
            "Development periods":new DevelopmentPeriodPlot(triangle, format).buildChart()  , 
            "Calendar periods":new CalendarPeriodPlot(triangle, format).buildChart()
        ]
    }
}

