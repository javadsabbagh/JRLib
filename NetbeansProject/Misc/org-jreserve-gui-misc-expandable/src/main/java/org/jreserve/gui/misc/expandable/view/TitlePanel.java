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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
class TitlePanel extends JPanel {
    
    private final static int OUTTER_ARCH = 8;
    private final static int INNER_ARCH = 5;
    private final static int VERTICAL_MARGIN = 2;
    private final static int HORIZONTAL_MARGIN = 2;
    private final static float HIGHT_PART = 0.4f;
    
    final static Color INNER_BORDER = new Color(255, 255, 255, 100);
    final static Color OUTTER_BORDER = new Color(50, 50, 50, 200);
    
    private Color color1;
    private Color color2;
    
    private GradientPaint topPaint;
    
    TitlePanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    TitlePanel(LayoutManager layout) {
        super(layout);
    }

    TitlePanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public TitlePanel() {
    }

    @Override
    public void paint(Graphics g) {
        if (g instanceof Graphics2D) {
            paintBackground((Graphics2D) g);
            super.paintComponents(g);
        } else {
            super.paint(g);
        }
    }

    private void paintBackground(Graphics2D g) {
        int h = getHeight();
        int w = getWidth();
        initPaints(h);
        
        g = (Graphics2D) g.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w, h, OUTTER_ARCH, OUTTER_ARCH);
        g.clip(r2d);
        g.setPaint(topPaint);
        g.fillRoundRect(0, 0, w, h, OUTTER_ARCH, OUTTER_ARCH);
        
        g.setColor(ExpandableBorder.INNER_BORDER);
        g.drawLine(0, h-2, w, h-2);
        g.setColor(ExpandableBorder.OUTTER_BORDER);
        g.drawLine(0, h-1, w, h-1);
    }
    
    private void initPaints(float height) {
        initColors();
        float yTop = height * HIGHT_PART;
	topPaint = new GradientPaint(0f, 0f, color1, 0f, yTop, color2);
    }
    
    private void initColors() {
        if(color1 == null) {
            color2 = getBackground();
            color1 = shiftColor(color2, 2);
        }
    }
    
    private Color shiftColor(Color input, double factor) {
        int r = getComponent(input.getRed(), factor);
        int g = getComponent(input.getGreen(), factor);
        int b = getComponent(input.getBlue(), factor);
        int a = input.getAlpha(); //getComponent(input.getAlpha(), 1d/factor);
        return new Color(r, g, b, a);
    }
    
    private int getComponent(int input, double factor) {
        return (int) Math.min(255, Math.max(0, input * factor));
    }
    
    @Override
    public void setBackground(Color color) {
        color1 = null;
        color2 = null;
        super.setBackground(color);
    }
    
        
    @Override
    public Dimension getPreferredSize() {
        Dimension size = getLayout().preferredLayoutSize(this);
        int w = size.width + 2 * HORIZONTAL_MARGIN;
        int h = size.height + 2 * VERTICAL_MARGIN;
        return new Dimension(w, h);
    }

}