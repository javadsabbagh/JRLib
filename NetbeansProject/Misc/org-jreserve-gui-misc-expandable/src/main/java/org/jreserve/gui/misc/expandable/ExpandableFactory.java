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
package org.jreserve.gui.misc.expandable;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import org.jreserve.gui.misc.expandable.registration.ExpandableElementLayerRegistrationLoader;
import org.jreserve.gui.misc.expandable.view.ExpandableView;
import org.openide.util.Lookup;
import org.openide.util.Utilities;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class ExpandableFactory {

    private final static int SCROLL_INCREMENT = 20;

    public static JComponent createPanel(String mimeType, boolean withContext) {
        Lookup context = withContext ? Utilities.actionsGlobalContext() : null;
        List<ExpandableElementDescription> elements = new ExpandableElementLayerRegistrationLoader(mimeType, context).getValues();
        return createPanel(elements.toArray(new ExpandableElementDescription[elements.size()]));
    }

    public static JComponent createPanel(ExpandableElementDescription[] elements) {
        return new ExpandableView(elements);
    }

    public static JScrollPane createScrollPanel(JComponent component) {
        JScrollPane scroll = new JScrollPane(component);
        scroll.getVerticalScrollBar().setUnitIncrement(SCROLL_INCREMENT);
        scroll.getVerticalScrollBar().setBlockIncrement(SCROLL_INCREMENT);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.addComponentListener(new ResizeListener(scroll, component));
        return scroll;
    }

    private static class ResizeListener extends ComponentAdapter {

        private boolean firstCall = true;
        private JScrollPane scroll;
        private JComponent component;
        
        private ResizeListener(JScrollPane scroll, JComponent component) {
            this.scroll = scroll;
            this.component = component;
        }
        
        @Override
        public void componentResized(ComponentEvent e) {
            if (firstCall) {
                firstCall = false;
            } else {
                setContentSize();
            }
        }

        private void setContentSize() {
            int barWidth = scroll.getHorizontalScrollBar().getWidth();
            int width = scroll.getWidth() - barWidth;
            int height = component.getPreferredSize().height;
            component.setPreferredSize(new Dimension(width, height));
            scroll.revalidate();
        }
    }
}
