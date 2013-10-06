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

import javax.swing.Icon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.AbstractUndoableEdit;
import org.jreserve.gui.calculations.api.CalculationEvent;
import org.jreserve.gui.calculations.claimtriangle.impl.ClaimTriangleCalculationImpl;
import org.jreserve.gui.misc.eventbus.EventBusListener;
import org.jreserve.gui.misc.eventbus.EventBusManager;
import org.jreserve.jrlib.gui.data.MonthDate;
import org.jreserve.jrlib.gui.data.TriangleGeometry;
import org.openide.awt.UndoRedo;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
@Messages({
    "MSG.GeometryEditorPanel.EndBeforeStart=End date is before the start date!"
})
public class GeometryEditorPanel extends javax.swing.JPanel {
    
    private final static Icon LINK_IMG = ImageUtilities.loadImageIcon("org/jreserve/gui/calculations/claimtriangle/chain.png", false);   //NOI18
    
    private InputListener inputListener = new InputListener();
    private ClaimTriangleCalculationImpl calculation;
    private boolean myChange = false;
    private UndoRedo.Manager undo;
    
    public GeometryEditorPanel() {
        initComponents();
        EventBusManager.getDefault().subscribe(this);
    }

    void setCalculation(ClaimTriangleCalculationImpl calculation) {
        if(calculation != null) {
            this.calculation = calculation;
            TriangleGeometry geometry = calculation.getGeometry();
            myChange = true;
            updateFromGeometry(geometry);
            myChange = false;
            enableControls();
        }
    }
    
    private void updateFromGeometry(final TriangleGeometry geometry) {
        synchronized(geometry) {
            startDateSpinner.setMonthDate(geometry.getStartDate());
            endDateSpinner.setMonthDate(geometry.getEndDate());
            accidentLengthSpinner.setValue(geometry.getAccidentLength());
            symmetricButton.setSelected(geometry.getAccidentLength() == geometry.getDevelopmentLength());
            developmentLengthSpinner.setValue(geometry.getDevelopmentLength());
        }
    }
    
    private void enableControls() {
        startDateSpinner.setEnabled(true);
        endDateSpinner.setEnabled(true);
        accidentLengthSpinner.setEnabled(true);
        symmetricButton.setEnabled(true);
        if(!symmetricButton.isSelected())
            developmentLengthSpinner.setEnabled(true);
    }
    
    void setUndo(UndoRedo.Manager undo) {
        this.undo = undo;
    }
    
    void componentClosed() {
        this.calculation = null;
        this.undo = null;
        EventBusManager.getDefault().unsubscribe(this);
    }
    
    private void updateFromGUI() {
        TriangleGeometry geometry = createGeometry();
        if(geometry != null)
            msgLabel.clearMessage();
        
        final TriangleGeometry cg = calculation.getGeometry();
        synchronized(cg) {
            if(cg.equals(geometry))
                return;
            GeometryEdit edit = new GeometryEdit();
            calculation.setGeometry(geometry);
            edit.editied();
            
            //TODO unduable edit
        }
    }
    
    private TriangleGeometry createGeometry() {
        MonthDate start = startDateSpinner.getMonthDate();
        MonthDate end = endDateSpinner.getMonthDate();
        if(end.before(start)) {
            msgLabel.showError(Bundle.MSG_GeometryEditorPanel_EndBeforeStart());
            return null;
        }
        int al = (Integer) accidentLengthSpinner.getValue();
        int dl = (Integer) developmentLengthSpinner.getValue();
        return new TriangleGeometry(start, end, al, dl);
    }
    
    @EventBusListener(forceEDT = true)
    public void calculationChanged(CalculationEvent.Change evt) {
        if(this.calculation == evt.getCalculationProvider()) {
            myChange = true;
            updateFromGeometry(calculation.getGeometry());
            myChange = false;
        }
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

        intervalLabel = new javax.swing.JLabel();
        intervalSeparatorLabel = new javax.swing.JLabel();
        startDateSpinner = new org.jreserve.gui.trianglewidget.geometry.MonthDateSpinner();
        endDateSpinner = new org.jreserve.gui.trianglewidget.geometry.MonthDateSpinner();
        startLabel = new javax.swing.JLabel();
        endLabel = new javax.swing.JLabel();
        accidentLabel = new javax.swing.JLabel();
        developmentLabel = new javax.swing.JLabel();
        lengthLabel = new javax.swing.JLabel();
        accidentLengthSpinner = new javax.swing.JSpinner();
        symmetricButton = new javax.swing.JToggleButton();
        developmentLengthSpinner = new javax.swing.JSpinner();
        msgLabel = new org.jreserve.gui.misc.utils.widgets.MsgLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));

        setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));
        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(intervalLabel, org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.intervalLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 10, 5);
        add(intervalLabel, gridBagConstraints);

        intervalSeparatorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(intervalSeparatorLabel, org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.intervalSeparatorLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(intervalSeparatorLabel, gridBagConstraints);

        startDateSpinner.setToolTipText(org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.startDateSpinner.toolTipText")); // NOI18N
        startDateSpinner.setEnabled(false);
        startDateSpinner.addChangeListener(inputListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(startDateSpinner, gridBagConstraints);

        endDateSpinner.setToolTipText(org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.endDateSpinner.toolTipText")); // NOI18N
        endDateSpinner.setEnabled(false);
        endDateSpinner.addChangeListener(inputListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(endDateSpinner, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(startLabel, org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.startLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        add(startLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(endLabel, org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.endLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        add(endLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(accidentLabel, org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.accidentLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        add(accidentLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(developmentLabel, org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.developmentLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        add(developmentLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(lengthLabel, org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.lengthLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 20, 5);
        add(lengthLabel, gridBagConstraints);

        accidentLengthSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(12), Integer.valueOf(1), null, Integer.valueOf(1)));
        accidentLengthSpinner.setToolTipText(org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.accidentLengthSpinner.toolTipText")); // NOI18N
        accidentLengthSpinner.setEnabled(false);
        accidentLengthSpinner.addChangeListener(inputListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 5);
        add(accidentLengthSpinner, gridBagConstraints);

        symmetricButton.setIcon(LINK_IMG);
        symmetricButton.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(symmetricButton, null);
        symmetricButton.setToolTipText(org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.symmetricButton.toolTipText")); // NOI18N
        symmetricButton.setEnabled(false);
        symmetricButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                symmetricButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 5);
        add(symmetricButton, gridBagConstraints);

        developmentLengthSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(12), Integer.valueOf(1), null, Integer.valueOf(1)));
        developmentLengthSpinner.setToolTipText(org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.developmentLengthSpinner.toolTipText")); // NOI18N
        developmentLengthSpinner.setEnabled(false);
        developmentLengthSpinner.addChangeListener(inputListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 5);
        add(developmentLengthSpinner, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(msgLabel, org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.msgLabel.text")); // NOI18N
        msgLabel.clearMessage();
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(msgLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(filler1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void symmetricButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_symmetricButtonActionPerformed
        if(symmetricButton.isSelected()) {
            developmentLengthSpinner.setValue(accidentLengthSpinner.getValue());
            developmentLengthSpinner.setEnabled(false);
        } else {
            developmentLengthSpinner.setEnabled(true);
        }
    }//GEN-LAST:event_symmetricButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel accidentLabel;
    private javax.swing.JSpinner accidentLengthSpinner;
    private javax.swing.JLabel developmentLabel;
    private javax.swing.JSpinner developmentLengthSpinner;
    private org.jreserve.gui.trianglewidget.geometry.MonthDateSpinner endDateSpinner;
    private javax.swing.JLabel endLabel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel intervalLabel;
    private javax.swing.JLabel intervalSeparatorLabel;
    private javax.swing.JLabel lengthLabel;
    private org.jreserve.gui.misc.utils.widgets.MsgLabel msgLabel;
    private org.jreserve.gui.trianglewidget.geometry.MonthDateSpinner startDateSpinner;
    private javax.swing.JLabel startLabel;
    private javax.swing.JToggleButton symmetricButton;
    // End of variables declaration//GEN-END:variables
    
    private class InputListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            if(!myChange)
                updateFromGUI();
        }
    }
    
    private class GeometryEdit extends AbstractUndoableEdit {
        
        private TriangleGeometry preState;
        private TriangleGeometry postState;
        
        GeometryEdit() {
            preState = copyCalculation();
        }
        
        private TriangleGeometry copyCalculation() {
            TriangleGeometry original = calculation.getGeometry();
            MonthDate start = original.getStartDate();
            MonthDate end = original.getEndDate();
            int al = original.getAccidentLength();
            int dl = original.getDevelopmentLength();
            return new TriangleGeometry(start, end, al, dl);
        }
        
        void editied() {
            postState = copyCalculation();
            if(undo != null)
                undo.undoableEditHappened(new UndoableEditEvent(GeometryEditorPanel.this, this));
        }
        
        @Override
        public void undo() {
            super.undo();
            calculation.setGeometry(preState);
        }
        
        @Override
        public void redo() {
            super.redo();
            calculation.setGeometry(postState);
        }
    }
}
