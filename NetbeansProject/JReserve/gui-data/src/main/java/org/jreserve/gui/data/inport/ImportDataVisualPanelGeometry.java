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
package org.jreserve.gui.data.inport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.Icon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jreserve.gui.misc.utils.widgets.EmptyIcon;
import org.jreserve.gui.trianglewidget.DefaultTriangleWidgetRenderer;
import org.jreserve.gui.trianglewidget.TriangleWidgetRenderer;
import org.jreserve.gui.trianglewidget.model.TriangleLayer;
import org.jreserve.jrlib.gui.data.DataEntry;
import org.jreserve.jrlib.gui.data.MonthDate;
import org.jreserve.jrlib.gui.data.TriangleGeometry;
import org.jreserve.jrlib.triangle.AbstractTriangle;
import org.jreserve.jrlib.triangle.Triangle;
import org.openide.util.NbBundle.Messages;
import org.openide.util.NbPreferences;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
@Messages({
    "LBL.ImportDataVisualPanelGeometry.Title=Geometry"
})
public class ImportDataVisualPanelGeometry extends javax.swing.JPanel {
    
    private final static String KEY_START_DATE = "ImportDataVisualPanelGeometry.start.date";
    private final static String KEY_STEP_ACCIDENT = "ImportDataVisualPanelGeometry.step.accident";
    private final static String KEY_STEP_DEVELOPMENT = "ImportDataVisualPanelGeometry.step.development";
    
    private final InputListener inputListener = new InputListener();
    private Layer layer = new Layer();
    
    public ImportDataVisualPanelGeometry() {
        initComponents();
        loadPreferences();
        updateGeometry();
    }
    
    private void loadPreferences() {
        Preferences prefs = NbPreferences.forModule(ImportDataVisualPanelGeometry.class);
        String sdText = prefs.get(KEY_START_DATE, null);
        startDateSpinner.setMonthDate(sdText==null? new MonthDate() : new MonthDate(sdText));
        accidentStepSpinner.setMonthCount(prefs.getInt(KEY_STEP_ACCIDENT, 1));
        developmentStepSpinner.setMonthCount(prefs.getInt(KEY_STEP_DEVELOPMENT, 1));
    }
    
    public void storePreferences() {
        Preferences prefs = NbPreferences.forModule(ImportDataVisualPanelGeometry.class);
        prefs.put(KEY_START_DATE, startDateSpinner.getMonthDate().toString());
        prefs.putInt(KEY_STEP_ACCIDENT, accidentStepSpinner.getMonthCount());
        prefs.putInt(KEY_STEP_DEVELOPMENT, developmentStepSpinner.getMonthCount());
    }
    
    @Override
    public String getName() {
        return Bundle.LBL_ImportDataVisualPanelGeometry_Title();
    }
    
    public List<DataEntry> createEntries() {
        if(layer.triangle == null)
            return Collections.EMPTY_LIST;
        List<DataEntry> result = new ArrayList<DataEntry>();
        
        MonthDate start = startDateSpinner.getMonthDate();
        int aStep = accidentStepSpinner.getMonthCount();
        int dStep = developmentStepSpinner.getMonthCount();
        
        int accidents = layer.triangle.getAccidentCount();
        for(int a=0; a<accidents; a++) {
            int devs = layer.triangle.getDevelopmentCount(a);
            for(int d=0; d<devs; d++) {
                MonthDate aDate = start.addMonth(aStep*a);
                MonthDate dDate = aDate.addMonth(dStep*d);
                double value = layer.triangle.getValue(a, d);
                result.add(new DataEntry(aDate, dDate, value));
            }
        }
        return result;
    }
    
    public void setTriangleValues(double[][] values) {
        layer.triangle = new ArrayTriangle(values);
        triangleWidget.setLayers(layer);
    }
        
    private void updateGeometry() {
        TriangleGeometry geometry = createGeometry();
        triangleWidget.setTriangleGeometry(geometry);
    }
    
    public TriangleGeometry createGeometry() {
        MonthDate start = startDateSpinner.getMonthDate();
        MonthDate end = new MonthDate(Integer.MAX_VALUE, 11);
        int aLength = accidentStepSpinner.getMonthCount();
        int dLength = developmentStepSpinner.getMonthCount();
        
        if(start==null || aLength<1 || dLength<1)
            return null;
        return new TriangleGeometry(start, end, aLength, dLength);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        triangleWidgetPanel1 = new org.jreserve.gui.trianglewidget.TriangleWidgetPanel();
        startDateLabel = new javax.swing.JLabel();
        startDateSpinner = new org.jreserve.gui.trianglewidget.geometry.MonthDateSpinner();
        stepsLabel = new javax.swing.JLabel();
        accidentStepLabel = new javax.swing.JLabel();
        accidentStepSpinner = new org.jreserve.gui.trianglewidget.geometry.PeriodStepSpinner();
        developmentStepLabel = new javax.swing.JLabel();
        developmentStepSpinner = new org.jreserve.gui.trianglewidget.geometry.PeriodStepSpinner();
        triangleWidget = new org.jreserve.gui.trianglewidget.TriangleWidgetPanel();

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(startDateLabel, org.openide.util.NbBundle.getMessage(ImportDataVisualPanelGeometry.class, "ImportDataVisualPanelGeometry.startDateLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(startDateLabel, gridBagConstraints);

        startDateSpinner.addChangeListener(inputListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        add(startDateSpinner, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(stepsLabel, org.openide.util.NbBundle.getMessage(ImportDataVisualPanelGeometry.class, "ImportDataVisualPanelGeometry.stepsLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        add(stepsLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(accidentStepLabel, org.openide.util.NbBundle.getMessage(ImportDataVisualPanelGeometry.class, "ImportDataVisualPanelGeometry.accidentStepLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 10, 5);
        add(accidentStepLabel, gridBagConstraints);

        accidentStepSpinner.addChangeListener(inputListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        add(accidentStepSpinner, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(developmentStepLabel, org.openide.util.NbBundle.getMessage(ImportDataVisualPanelGeometry.class, "ImportDataVisualPanelGeometry.developmentStepLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 40, 5);
        add(developmentStepLabel, gridBagConstraints);

        developmentStepSpinner.addChangeListener(inputListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 40, 0);
        add(developmentStepSpinner, gridBagConstraints);

        triangleWidget.setCummulatedControlVisible(false);
        triangleWidget.setLayerControlVisible(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(triangleWidget, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel accidentStepLabel;
    private org.jreserve.gui.trianglewidget.geometry.PeriodStepSpinner accidentStepSpinner;
    private javax.swing.JLabel developmentStepLabel;
    private org.jreserve.gui.trianglewidget.geometry.PeriodStepSpinner developmentStepSpinner;
    private javax.swing.JLabel startDateLabel;
    private org.jreserve.gui.trianglewidget.geometry.MonthDateSpinner startDateSpinner;
    private javax.swing.JLabel stepsLabel;
    private org.jreserve.gui.trianglewidget.TriangleWidgetPanel triangleWidget;
    private org.jreserve.gui.trianglewidget.TriangleWidgetPanel triangleWidgetPanel1;
    // End of variables declaration//GEN-END:variables

    private class Layer implements TriangleLayer {
        
        private DefaultTriangleWidgetRenderer renderer = new DefaultTriangleWidgetRenderer();
        private Triangle triangle = null;
        
        @Override
        public String getDisplayName() {
            return "Input Data";
        }

        @Override
        public Icon getIcon() {
            return EmptyIcon.EMPTY_16;
        }

        @Override
        public Triangle getTriangle() {
            return triangle;
        }

        @Override
        public boolean rendersCell(int accident, int development) {
            return true;
        }

        @Override
        public TriangleWidgetRenderer getCellRenderer() {
            return renderer;
        }
    }
    
    private class ArrayTriangle extends AbstractTriangle {
        
        private final double[][] values;
        private final int maxDev;
        
        private ArrayTriangle(double[][] values) {
            this.values = org.jreserve.jrlib.triangle.TriangleUtil.copy(values);
            int dev = 0;
            for(double[] row : values)
                if(dev < row.length)
                    dev = row.length;
            maxDev = dev;
        }
        
        @Override
        protected boolean withinBounds(int accident) {
            return (0 <= accident && accident < values.length);
        }

        @Override
        protected void recalculateLayer() {
        }

        @Override
        public int getAccidentCount() {
            return values.length;
        }

        @Override
        public int getDevelopmentCount() {
            return maxDev;
        }

        @Override
        public int getDevelopmentCount(int accident) {
            return withinBounds(accident)? values[accident].length : 0;
        }

        @Override
        public double getValue(int accident, int development) {
            return withinBounds(accident, development)?
                    values[accident][development] :
                    Double.NaN;
        }
    }
    
    private class InputListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            updateGeometry();
        }
    }
}
