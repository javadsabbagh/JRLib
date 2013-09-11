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

package org.jreserve.gui.data.binary;

import javax.swing.Action;
import org.jreserve.gui.data.api.wizard.AbstractCreateDataSourceAction;
import org.jreserve.gui.data.api.wizard.AbstractDataSourceWizardIterator;
import org.jreserve.gui.misc.utils.actions.RibbonRegistration;
import org.jreserve.gui.misc.utils.actions.RibbonRegistrations;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
@ActionID(
    category = "File",
    id = "org.jreserve.gui.data.binary.CreateBinaryDataSourceAction"
)
@ActionRegistration(
    displayName = "#LBL.CreateBinaryDataSourceAction.Name",
    lazy = false
)
@RibbonRegistrations({
    @RibbonRegistration(
        path="Ribbon/TaskPanes/Edit/Data/NewDataSource", 
        position=100, 
        priority=RibbonRegistration.Priority.MEDIUM)
})
@Messages({
    "LBL.CreateBinaryDataSourceAction.Name=Binary Storage",
    "LBL.CreateBinaryDataSourceAction.WizardTitle=Create Binary Storage"
})
public class CreateBinaryDataSourceAction extends AbstractCreateDataSourceAction {
    
    @StaticResource private final static String ICON_16 = "org/jreserve/gui/data/binaryprovider/binary.png";   //NOI18
    
    public CreateBinaryDataSourceAction() {
        this(Utilities.actionsGlobalContext());
    }
    
    private CreateBinaryDataSourceAction(Lookup lkp) {
        super(lkp);
        putValue(NAME, Bundle.LBL_CreateBinaryDataSourceAction_Name());
        putValue(SMALL_ICON, ImageUtilities.loadImageIcon(ICON_16, false));
    }

    @Override
    public Action createContextAwareInstance(Lookup actionContext) {
        return new CreateBinaryDataSourceAction(actionContext);
    }

    @Override
    protected AbstractDataSourceWizardIterator createIterator() {
        return new BinaryDataSourceWizardIterator();
    }

    @Override
    protected String getWizardTitle() {
        return Bundle.LBL_CreateBinaryDataSourceAction_WizardTitle();
    }
}