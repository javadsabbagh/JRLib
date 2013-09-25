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
package org.jreserve.gui.calculations.claimtriangle.editor;

import java.awt.Component;
import javax.swing.BorderFactory;
import org.jreserve.gui.calculations.api.CalculationEvent;
import org.jreserve.gui.calculations.claimtriangle.impl.ClaimTriangleCalculationImpl;
import org.jreserve.gui.calculations.claimtriangle.impl.ClaimTriangleDataObject;
import org.jreserve.gui.misc.eventbus.EventBusListener;
import org.jreserve.gui.misc.eventbus.EventBusManager;
import org.jreserve.gui.misc.expandable.AbstractExpandableElement;
import org.jreserve.gui.misc.expandable.ExpandableElement;
import org.jreserve.gui.trianglewidget.TriangleWidgetPanel;
import org.openide.loaders.DataObject;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
@ExpandableElement.Registration(
    displayName = "#LBL.LayerEditor.Title",
    mimeType = ClaimTriangleDataObject.MIME_TYPE,
    position = 300,
    prefferedID = "org.jreserve.gui.calculations.claimtriangle.editor.LayerEditor",
    background = "#COLOR.LayerEditor.Background",
    iconBase = "org/jreserve/gui/calculations/claimtriangle/triangle.png"
)
@Messages({
    "LBL.LayerEditor.Title=Triangle",
    "COLOR.LayerEditor.Background=43C443"
})
public class LayerEditor extends AbstractExpandableElement {
    
    private TriangleWidgetPanel panel;
    private ClaimTriangleCalculationImpl calculation;
    private final Lookup lkp;
    private final InstanceContent ic = new InstanceContent();
    
    public LayerEditor() {
        this(Lookup.EMPTY);
    }
    
    public LayerEditor(Lookup context) {
        calculation = context.lookup(ClaimTriangleCalculationImpl.class);
        DataObject obj = context.lookup(DataObject.class);
        Lookup oLkp = obj==null? Lookup.EMPTY : obj.getLookup();
        
        if(calculation==null)
            ic.add(calculation);
        lkp = new ProxyLookup(oLkp, new AbstractLookup(ic));
        EventBusManager.getDefault().subscribe(this);
    }

    @Override
    public Lookup getLookup() {
        return lkp;
    }
    
    @Override
    protected Component createVisualComponent() {
        panel = new TriangleWidgetPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        panel.setFocusable(true);
        if(calculation != null) {
            panel.setTriangleGeometry(calculation.getGeometry());
            panel.setLayers(calculation.createLayers());
        }
        ic.add(panel.createCopiable());
        return panel;
    }
    
    @EventBusListener(forceEDT = true)
    public void calculationChanged(CalculationEvent.Change evt) {
        if(panel!=null && calculation == evt.getCalculationProvider()) {
            panel.setTriangleGeometry(calculation.getGeometry());
            panel.setLayers(calculation.createLayers());
        }
    }

    @Override
    public void componentClosed() {
        EventBusManager.getDefault().unsubscribe(this);
        super.componentClosed();
    }
}
