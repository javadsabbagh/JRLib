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

package org.jreserve.gui.trianglewidget;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import org.jreserve.gui.localesettings.LocaleSettings;
import org.jreserve.gui.localesettings.LocaleSettings.DecimalFormatter;

/**
 * Default implementation for the {@link TriangleWidgetRenderer TriangleWidgetRenderer}
 * interface.
 * 
 * @author Peter Decsi
 * @version 1.0
 */
public class DefaultTriangleWidgetRenderer extends JLabel implements TriangleWidgetRenderer {
    
    private DecimalFormatter df;
    
    public DefaultTriangleWidgetRenderer() {
        this(null);
    }
    
    public DefaultTriangleWidgetRenderer(DecimalFormatter df) {
        this.df = df==null? LocaleSettings.createDecimalFormat() : null;
        setBorder(createBorder());
        setOpaque(true);
        setBackground(Color.WHITE);
        setHorizontalAlignment(SwingConstants.RIGHT);
    }
    
    private Border createBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 1, Color.BLACK), 
                BorderFactory.createEmptyBorder(0, 2, 0, 2));
    }
    
    public void setDecimalCount(int decimalcount) {
        df.setDecimalCount(decimalcount);
    }
    
    @Override
    public Component getComponent(TriangleWidget widget, double value, int row, int column, boolean selected) {
        setText(format(value));
        return this;
    }

    private String format(double value) {
        try  {
            return df.format(value);
        } catch (Exception ex) {
            return ""+value;
        }
    }
}
