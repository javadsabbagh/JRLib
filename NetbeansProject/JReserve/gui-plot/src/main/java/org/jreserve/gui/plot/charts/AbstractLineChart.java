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
package org.jreserve.gui.plot.charts;

import java.awt.BasicStroke;
import java.awt.Paint;
import java.util.List;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jreserve.gui.plot.ChartType;
import org.jreserve.gui.plot.ColorGenerator;
import org.jreserve.gui.plot.PlotEntry;
import org.jreserve.gui.plot.PlotFormat;
import org.jreserve.gui.plot.PlotSerie;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class AbstractLineChart extends AbstractChart {
    
    private List<PlotSerie> series;
    private double minValue = Double.NaN;
    private double maxValue = Double.NaN;
    
    public AbstractLineChart(PlotFormat format, List<PlotSerie> series) {
        this(ChartType.LINE, format, series);
    }
    
    public AbstractLineChart(ChartType type, PlotFormat format, List<PlotSerie> series) {
        super(type, format);
        this.series = series;
    }
    
    @Override
    protected DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        for(PlotSerie serie : series) {
            Comparable rowKey = serie.getKey();
            int size = serie.getSize();
            for(int i=0; i<size; i++) {
                PlotEntry entry = serie.getEntry(i);
                double v = entry.getValue();
                updateBounds(v);
                ds.addValue(v, rowKey, entry.getKey());
            }
        }
        return ds;
    }
    
    private void updateBounds(double v) {
        if(Double.isNaN(minValue) || minValue > v) minValue = v;
        if(Double.isNaN(maxValue) || maxValue < v) maxValue = v;
    }
    
    @Override
    protected void formatPlot(Plot plot) {
        CategoryPlot cPlot = (CategoryPlot) plot;
        cPlot.setBackgroundPaint(format.getBackgroundColor());
        
        cPlot.setRangeGridlinesVisible(true);
        cPlot.setRangeGridlinePaint(format.getGridColor());
        cPlot.setRangeGridlineStroke(new BasicStroke(3));
        cPlot.setDomainGridlinesVisible(true);
        cPlot.setDomainGridlinePaint(format.getGridColor());
        cPlot.setDomainGridlineStroke(new BasicStroke(3));
        
        formatSeries((LineAndShapeRenderer) cPlot.getRenderer());
        formatRangeAxis(getMinValue(), getMaxValue());
    }
    
    protected void formatSeries(LineAndShapeRenderer renderer) {
        renderer.setBaseShapesVisible(true);
        renderer.setDrawOutlines(true);
        renderer.setUseFillPaint(true);
        renderer.setBaseStroke(new BasicStroke(2));
        
        int count = ((CategoryDataset) dataset).getRowCount();
        ColorGenerator colors = format.getColors();
        for(int i=0; i<count; i++) {
            Paint color = colors.nextColor();
            renderer.setSeriesPaint(i, color);
            renderer.setSeriesFillPaint(i, color);
        }
    }	
    
    protected double getMinValue() {
        return minValue;
    }
    
    protected double getMaxValue() {
        return maxValue;
    }
}
