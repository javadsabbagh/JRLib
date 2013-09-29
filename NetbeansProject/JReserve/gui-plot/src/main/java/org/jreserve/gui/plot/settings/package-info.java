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
@OptionsPanelController.ContainerRegistration(
        categoryName = "#LBL.PlotSettings.Title",
        iconBase = "org/jreserve/gui/plot/icons/bar_chart32.png",
        id = "PlotSettings",
        position = 700
)
@Messages({
    "LBL.PlotSettings.Title=Plots"
})
package org.jreserve.gui.plot.settings;

import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.NbBundle.Messages;
