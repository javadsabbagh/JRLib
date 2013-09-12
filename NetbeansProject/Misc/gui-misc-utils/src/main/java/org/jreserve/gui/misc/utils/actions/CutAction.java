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
package org.jreserve.gui.misc.utils.actions;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import org.jreserve.gui.misc.utils.notifications.BubbleUtil;
import org.netbeans.api.annotations.common.StaticResource;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
@ActionID(
    category = "Edit",
    id = "org.jreserve.gui.misc.utils.actions.CutAction"
)
@ActionRegistration(
    lazy = false,
    displayName = "#CTL_CutAction"
)
@Messages({
    "CTL_CutAction=Cut",
    "MSG.CutAction.Error=Cut to clipboard failed!"
})
public class CutAction extends AbstractContextAwareAction {

    @StaticResource private final static String IMG_16 = "org/jreserve/gui/misc/utils/cut.png"; //NOI18
    @StaticResource private final static String IMG_32 = "org/jreserve/gui/misc/utils/cut32.png";   //NOI18
    private final static Logger logger = Logger.getLogger(CutAction.class.getName());
    
    private ClipboardUtil.Cutable cutable;
    private final Clipboard cb;
    
    public CutAction() {
        this(Utilities.actionsGlobalContext());
    }

    public CutAction(Lookup context) {
        super(context, ClipboardUtil.Cutable.class);
        setDisplayName(Bundle.CTL_CutAction());
        setSmallIconPath(IMG_16);
        setLargeIconPath(IMG_32);
        
        cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        if(cb == null)
            logger.warning("SystemClipboard not found! CopyAction will be disabled!");
    }
    
    @Override
    protected boolean shouldEnable(Lookup context) {
        if(cb == null)
            return false;
        cutable = context.lookup(ClipboardUtil.Cutable.class);
        return cutable != null && cutable.canCut();
    }

    @Override
    protected void performAction(ActionEvent evt) {
        try {
            Transferable t = cutable.clipboardCut();
            
            if(t != null)
                cb.setContents(t, new StringSelection(""));
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Unable to cut from: "+cutable, ex);
            BubbleUtil.showException(Bundle.MSG_CutAction_Error(), ex);
        }
    }

    @Override
    public Action createContextAwareInstance(Lookup actionContext) {
        return new CutAction();
    }
}
