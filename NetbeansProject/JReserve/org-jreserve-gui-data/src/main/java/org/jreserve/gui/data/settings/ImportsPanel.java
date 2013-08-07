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
package org.jreserve.gui.data.settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import org.jreserve.gui.data.inport.ImportDataProviderAdapter;
import org.jreserve.gui.data.inport.ImportDataProviderRegistry;
import org.jreserve.gui.data.api.SaveType;
import org.jreserve.gui.misc.utils.widgets.WidgetUtils;

final class ImportsPanel extends javax.swing.JPanel {

    private final ImportsOptionsPanelController controller;
    private final InputListener inputListener = new InputListener();
    
    ImportsPanel(ImportsOptionsPanelController controller) {
        this.controller = controller;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        providerLabel = new javax.swing.JLabel();
        providerCombo = new javax.swing.JComboBox();
        saveTypeLabel = new javax.swing.JLabel();
        saveTypeCombo = new javax.swing.JComboBox();
        cummulatedLabel = new javax.swing.JLabel();
        cummulatedCheck = new javax.swing.JCheckBox();
        filler = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));

        setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));
        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(providerLabel, org.openide.util.NbBundle.getMessage(ImportsPanel.class, "ImportsPanel.providerLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(providerLabel, gridBagConstraints);

        providerCombo.setModel(new DefaultComboBoxModel(ImportDataProviderRegistry.getAdapters().toArray()));
        providerCombo.setRenderer(WidgetUtils.displayableListRenderer());
        providerCombo.addActionListener(inputListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        add(providerCombo, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(saveTypeLabel, org.openide.util.NbBundle.getMessage(ImportsPanel.class, "ImportsPanel.saveTypeLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(saveTypeLabel, gridBagConstraints);

        saveTypeCombo.setModel(new DefaultComboBoxModel(SaveType.values()));
        saveTypeCombo.setRenderer(WidgetUtils.displayableListRenderer());
        saveTypeCombo.addActionListener(inputListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        add(saveTypeCombo, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(cummulatedLabel, org.openide.util.NbBundle.getMessage(ImportsPanel.class, "ImportsPanel.cummulatedLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        add(cummulatedLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(cummulatedCheck, null);
        cummulatedCheck.addActionListener(inputListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        add(cummulatedCheck, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(filler, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    void load() {
        saveTypeCombo.setSelectedItem(ImportSettings.getSaveType());
        providerCombo.setSelectedItem(ImportSettings.getImportDataProvider());
        cummulatedCheck.setSelected(ImportSettings.isImportCummulated());
    }

    void store() {
        ImportSettings.setSaveType((SaveType) saveTypeCombo.getSelectedItem());
        ImportSettings.setImportDataProvider((ImportDataProviderAdapter) providerCombo.getSelectedItem());
        ImportSettings.setImportCummulated(cummulatedCheck.isSelected());
    }

    boolean valid() {
        // TODO check whether form is consistent and complete
        return true;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cummulatedCheck;
    private javax.swing.JLabel cummulatedLabel;
    private javax.swing.Box.Filler filler;
    private javax.swing.JComboBox providerCombo;
    private javax.swing.JLabel providerLabel;
    private javax.swing.JComboBox saveTypeCombo;
    private javax.swing.JLabel saveTypeLabel;
    // End of variables declaration//GEN-END:variables

    private class InputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.changed();
        }
    }
}