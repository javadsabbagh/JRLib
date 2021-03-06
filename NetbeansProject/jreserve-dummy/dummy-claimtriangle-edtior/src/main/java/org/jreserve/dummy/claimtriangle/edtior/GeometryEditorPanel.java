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
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;

/**
 *
 * @author Peti
 */
public class GeometryEditorPanel extends javax.swing.JPanel {
    
    /**
     * Creates new form GeometryEditorPanel
     */
    public GeometryEditorPanel() {
        initComponents();
    }
    
    private void disableEditor(JSpinner spinner) {
        JFormattedTextField field = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
        Color bg = field.getBackground();
        field.setEditable(false);
        field.setBackground(bg);
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

        lengthLabel = new javax.swing.JLabel();
        countLabel = new javax.swing.JLabel();
        accidentLabel = new javax.swing.JLabel();
        accidentSpinner = new javax.swing.JSpinner();
        accidentCountText = new javax.swing.JTextField();
        developmentLabel = new javax.swing.JLabel();
        developmentSpinner = new javax.swing.JSpinner();
        developmentCountText = new javax.swing.JTextField();
        accDevLabel = new javax.swing.JLabel();
        accDevText = new javax.swing.JTextField();
        rightFiller = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        bottomFiller = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));

        setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setLayout(new java.awt.GridBagLayout());

        lengthLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(lengthLabel, org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.lengthLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        add(lengthLabel, gridBagConstraints);

        countLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(countLabel, org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.countLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        add(countLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(accidentLabel, org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.accidentLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        add(accidentLabel, gridBagConstraints);

        accidentSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(12), Integer.valueOf(1), null, Integer.valueOf(1)));
        accidentSpinner.setPreferredSize(new java.awt.Dimension(50, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        add(accidentSpinner, gridBagConstraints);
        disableEditor(accidentSpinner);

        accidentCountText.setEditable(false);
        accidentCountText.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        accidentCountText.setText(org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.accidentCountText.text")); // NOI18N
        accidentCountText.setOpaque(false);
        accidentCountText.setPreferredSize(new java.awt.Dimension(50, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        add(accidentCountText, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(developmentLabel, org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.developmentLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        add(developmentLabel, gridBagConstraints);

        developmentSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(12), Integer.valueOf(1), null, Integer.valueOf(1)));
        developmentSpinner.setPreferredSize(new java.awt.Dimension(50, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        add(developmentSpinner, gridBagConstraints);
        disableEditor(developmentSpinner);

        developmentCountText.setEditable(false);
        developmentCountText.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        developmentCountText.setText(org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.developmentCountText.text")); // NOI18N
        developmentCountText.setOpaque(false);
        developmentCountText.setPreferredSize(new java.awt.Dimension(50, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        add(developmentCountText, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(accDevLabel, org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.accDevLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        add(accDevLabel, gridBagConstraints);

        accDevText.setEditable(false);
        accDevText.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        accDevText.setText(org.openide.util.NbBundle.getMessage(GeometryEditorPanel.class, "GeometryEditorPanel.accDevText.text")); // NOI18N
        accDevText.setOpaque(false);
        accDevText.setPreferredSize(new java.awt.Dimension(50, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        add(accDevText, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(rightFiller, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        add(bottomFiller, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel accDevLabel;
    private javax.swing.JTextField accDevText;
    private javax.swing.JTextField accidentCountText;
    private javax.swing.JLabel accidentLabel;
    private javax.swing.JSpinner accidentSpinner;
    private javax.swing.Box.Filler bottomFiller;
    private javax.swing.JLabel countLabel;
    private javax.swing.JTextField developmentCountText;
    private javax.swing.JLabel developmentLabel;
    private javax.swing.JSpinner developmentSpinner;
    private javax.swing.JLabel lengthLabel;
    private javax.swing.Box.Filler rightFiller;
    // End of variables declaration//GEN-END:variables
}
