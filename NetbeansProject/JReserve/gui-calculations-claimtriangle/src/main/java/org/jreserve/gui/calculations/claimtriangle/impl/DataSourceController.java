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

package org.jreserve.gui.calculations.claimtriangle.impl;

import org.jreserve.gui.data.api.DataSource;
import org.jreserve.gui.data.api.NamedDataSourceProvider;
import org.jreserve.gui.misc.namedcontent.DefaultContentChooserController;
import org.jreserve.gui.misc.namedcontent.NamedContent;
import org.jreserve.gui.misc.namedcontent.NamedContentUtil;
import org.jreserve.jrlib.gui.data.DataType;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
@Messages({
    "LBL.DataSourceController.Title=Select Storage"
})
public class DataSourceController extends DefaultContentChooserController {

    public static DataSource selectOne(NamedDataSourceProvider dop) {
        return NamedContentUtil.userSelect(new DataSourceController(dop), DataSource.class);
    }
    
    private DataSourceController(NamedDataSourceProvider dop) {
        super(dop, DataSource.class, Bundle.LBL_DataSourceController_Title());
    }

    @Override
    public boolean acceptsContent(NamedContent content) {
        DataSource ds = content.getLookup().lookup(DataSource.class);
        return ds != null &&
               DataType.TRIANGLE == ds.getDataType();
    }
    
    
}
