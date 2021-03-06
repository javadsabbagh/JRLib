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
package org.jreserve.gui.misc.expandable.view;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import org.jreserve.gui.misc.expandable.ExpandableContainerHandler;
import org.jreserve.gui.misc.expandable.ExpandableElementDescription;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewFactory;
import org.netbeans.spi.navigator.NavigatorLookupPanelsPolicy;
import org.openide.awt.UndoRedo;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

/**
 *
 * @author Peter Decsi
 */
abstract class AbstractExpandableContainerHandler implements ExpandableContainerHandler {
    
    private final static NavigatorLookupPanelsPolicy NAVIGATOR_LOOKUP_POLICY = new NavigatorLookupPanelsPolicy() {
        @Override
        public int getPanelsPolicy() {
            return NavigatorLookupPanelsPolicy.LOOKUP_HINTS_ONLY;
        }
    };
    
    private final static int UNDO_LIMIT = 30;
    
    private ExpandableElementDescription[] elements;
    private JComponent component;
    private ExpandableElementDescription selected;
    private ExtendableLookup lookup;
    private UndoRedo.Manager undoRedo;
    
    protected AbstractExpandableContainerHandler(ExpandableElementDescription[] elements) {
        this.elements = elements;
        this.lookup = new ExtendableLookup();
        this.lookup.updateLookups();
    }
    
    @Override
    public Lookup getLookup() {
        return lookup;
    }
    
    @Override
    public UndoRedo.Manager getUndoRedo() {
        if(undoRedo == null) {
            undoRedo = new UndoRedo.Manager();
            undoRedo.setLimit(UNDO_LIMIT);
        }
        return undoRedo;
    }
    
    @Override
    public JComponent getComponent() {
        if(component == null) {
            this.component = createComponent();
        }
        return component;
    }
    
    protected abstract JComponent createComponent();
    
    @Override
    public ExpandableElementDescription[] getElements() {
        return elements;
    }
    
    @Override
    public void navigateTo(ExpandableElementDescription description) {
    }

    @Override
    public void componentOpened() {
        for(ExpandableElementDescription description : elements)
            description.getElement().componentOpened();
    }

    @Override
    public void componentClosed() {
        for(ExpandableElementDescription description : elements)
            description.getElement().componentClosed();
    }

    @Override
    public void componentShowing() {
        for(ExpandableElementDescription description : elements)
            description.getElement().componentShowing();
    }

    @Override
    public void componentHidden() {
        for(ExpandableElementDescription description : elements)
            description.getElement().componentHidden();
    }

    @Override
    public void componentActivated() {
        for(ExpandableElementDescription description : elements)
            description.getElement().componentActivated();
    }

    @Override
    public void componentDeactivated() {
        for(ExpandableElementDescription description : elements)
            description.getElement().componentDeactivated();
    }
    
    @Override
    public CloseOperationState canCloseElement() {
        List<CloseOperationState> states = getInvalidStates();
        return states.isEmpty()?
                CloseOperationState.STATE_OK :
                createCompositeState(states);
    }
    
    private List<CloseOperationState> getInvalidStates() {
        List<CloseOperationState> states = new ArrayList<CloseOperationState>(elements.length);
        for(ExpandableElementDescription description : elements) {
            CloseOperationState state = description.getElement().canCloseElement();
            if(CloseOperationState.STATE_OK != state)
                states.add(state);
        }
        return states;
    }
    
    private CloseOperationState createCompositeState(List<CloseOperationState> states) {
        StringBuilder id = new StringBuilder();
        StateAction proceed = new StateAction();
        StateAction discard = new StateAction();
        for(CloseOperationState state : states) {
            if(id.length() > 0)
                id.append(" | ");
            id.append(state.getCloseWarningID());
            
            proceed.actions.add(state.getProceedAction());
            discard.actions.add(state.getDiscardAction());
        }
        
        return MultiViewFactory.createUnsafeCloseState(id.toString(), proceed, discard);
    }
    
    
    protected abstract JComponent getComponentFor(ExpandableElementDescription description);
    
    @Override
    public void setSelected(ExpandableElementDescription description) {
        this.selected = description;
        lookup.updateLookups();
    }
    
    private class ExtendableLookup extends ProxyLookup {
        private Lookup fixed;
        
        public ExtendableLookup() {
            fixed = Lookups.fixed(
                    AbstractExpandableContainerHandler.this, 
                    ExpandableNavigatorPanel.LOOKUP_HINT,
                    NAVIGATOR_LOOKUP_POLICY);
        }
        
        void updateLookups() {
            int size = elements.length;
            Lookup[] lkps = new Lookup[size+2];
            lkps[0] = selected==null? Lookup.EMPTY : selected.getElement().getLookup();
            lkps[1] = fixed;
            for(int i=0; i<size; i++) 
                lkps[i+2] = elements[i].getElement().getGlobalLookup();
            setLookups(lkps);
        }
    }    
    
    private class StateAction extends AbstractAction {
        
        private List<Action> actions = new ArrayList<Action>();
        
        @Override
        public void actionPerformed(ActionEvent e) {
            for(Action action : actions)
                if(action != null)
                    action.actionPerformed(e);
        }
    }
}
