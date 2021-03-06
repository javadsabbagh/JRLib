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
package org.jreserve.dummy.claimtriangle.edtior;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class InputDataEditorPanel extends javax.swing.JPanel {
    
    private final static String DATE_FORMAT = "yyyy-MM";
    private final static DateFormat DF = new SimpleDateFormat(DATE_FORMAT);
    
    private SpinnerDateModel startDateModel;
    private SpinnerDateModel endDateModel;

    /**
     * Creates new form InputDataEditorPanel
     */
    public InputDataEditorPanel() {
        initSpinnerModels();
        initComponents();
    }
    
    private void initSpinnerModels() {
        Calendar calendar = initCalendar();
        Date endNow = calendar.getTime();
        calendar.add(Calendar.YEAR, -8);
        Date startNow = calendar.getTime();
        
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.add(Calendar.YEAR, -92);
        Date minDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 200);
        Date maxDate = calendar.getTime();
        
        
        startDateModel = new SpinnerDateModel(startNow, minDate, maxDate, Calendar.MONTH);
        endDateModel = new SpinnerDateModel(endNow, minDate, maxDate, Calendar.MONTH);
    }
    
    private Calendar initCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(2004, Calendar.JANUARY, 1);
        return calendar;
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

        sourceLabel = new javax.swing.JLabel();
        sourceCombo = new javax.swing.JComboBox();
        startLabel = new javax.swing.JLabel();
        startSpinner = new javax.swing.JSpinner();
        endLabel = new javax.swing.JLabel();
        endSpinner = new javax.swing.JSpinner();
        rightFiller = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        bottomFiller = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));

        setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(sourceLabel, org.openide.util.NbBundle.getMessage(InputDataEditorPanel.class, "InputDataEditorPanel.sourceLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        add(sourceLabel, gridBagConstraints);

        sourceCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "APC Paid", "APC Incurred", "APC NoC" }));
        sourceCombo.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        add(sourceCombo, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(startLabel, org.openide.util.NbBundle.getMessage(InputDataEditorPanel.class, "InputDataEditorPanel.startLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        add(startLabel, gridBagConstraints);

        startSpinner.setModel(startDateModel);
        startSpinner.setEditor(new javax.swing.JSpinner.DateEditor(startSpinner, DATE_FORMAT));
        startSpinner.setPreferredSize(new java.awt.Dimension(100, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        add(startSpinner, gridBagConstraints);
        disableEditor(startSpinner);

        org.openide.awt.Mnemonics.setLocalizedText(endLabel, org.openide.util.NbBundle.getMessage(InputDataEditorPanel.class, "InputDataEditorPanel.endLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        add(endLabel, gridBagConstraints);

        endSpinner.setModel(endDateModel);
        endSpinner.setEditor(new JSpinner.DateEditor(endSpinner, DATE_FORMAT));
        endSpinner.setPreferredSize(new java.awt.Dimension(100, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        add(endSpinner, gridBagConstraints);
        disableEditor(endSpinner);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(rightFiller, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        add(bottomFiller, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler bottomFiller;
    private javax.swing.JLabel endLabel;
    private javax.swing.JSpinner endSpinner;
    private javax.swing.Box.Filler rightFiller;
    private javax.swing.JComboBox sourceCombo;
    private javax.swing.JLabel sourceLabel;
    private javax.swing.JLabel startLabel;
    private javax.swing.JSpinner startSpinner;
    // End of variables declaration//GEN-END:variables

    
    private void disableEditor(JSpinner spinner) {
        JFormattedTextField field = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        Color bg = field.getBackground();
        field.setEditable(false);
        field.setBackground(bg);
    }
}
