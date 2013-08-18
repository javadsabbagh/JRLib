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
package org.jreserve.gui.excel.dataimport;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.jreserve.gui.excel.template.dataimport.DataImportTemplate;
import org.jreserve.gui.excel.template.dataimport.DataImportTemplates;
import org.jreserve.gui.misc.utils.notifications.FileDialog;
import org.jreserve.gui.misc.utils.widgets.TextPrompt;
import org.jreserve.gui.poi.ExcelFileFilter;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
@Messages({
    "LBL.ExcelTemplateImportVisualPanel1.Name=Select Template",
    "LBL.ExcelTemplateImportVisualPanel1.FileDialogTitle=Select Excel File",
    "LBL.ExcelTemplateImportVisualPanel1.PathPrompt=Select input file"
})
class ExcelTemplateImportVisualPanel1 extends javax.swing.JPanel {
    private final static String SEARCH_IMG = "org/jreserve/gui/misc/utils/search.png";   //NOI18
    
    private final ExcelTemplateImportWizardPanel1 controller;
    private final InputListener inputListener = new InputListener();
    
    ExcelTemplateImportVisualPanel1(ExcelTemplateImportWizardPanel1 controller) {
        this.controller = controller;
        initComponents();
    }
    
    @Override
    public String getName() {
        return Bundle.LBL_ExcelTemplateImportVisualPanel1_Name();
    }
    
    String getPath() {
        return pathText.getText();
    }
    
    DataImportTemplate getTemplate() {
        return (DataImportTemplate) templateCombo.getSelectedItem();
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

        pathLabel = new javax.swing.JLabel();
        pathText = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        templateLabel = new javax.swing.JLabel();
        templateCombo = new javax.swing.JComboBox();
        filler = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(pathLabel, org.openide.util.NbBundle.getMessage(ExcelTemplateImportVisualPanel1.class, "ExcelTemplateImportVisualPanel1.pathLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(pathLabel, gridBagConstraints);

        pathText.setText(null);
        TextPrompt.createStandard(Bundle.LBL_ExcelTemplateImportVisualPanel1_PathPrompt(), pathText);
        pathText.getDocument().addDocumentListener(inputListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(pathText, gridBagConstraints);

        browseButton.setIcon(ImageUtilities.loadImageIcon(SEARCH_IMG, false));
        org.openide.awt.Mnemonics.setLocalizedText(browseButton, org.openide.util.NbBundle.getMessage(ExcelTemplateImportVisualPanel1.class, "ExcelTemplateImportVisualPanel1.browseButton.text")); // NOI18N
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        add(browseButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(templateLabel, org.openide.util.NbBundle.getMessage(ExcelTemplateImportVisualPanel1.class, "ExcelTemplateImportVisualPanel1.templateLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        add(templateLabel, gridBagConstraints);

        templateCombo.setModel(new DefaultComboBoxModel(DataImportTemplates.getDefault().getTemplates().toArray()));
        templateCombo.setRenderer(new TemplateRenderer());
        templateCombo.addActionListener(inputListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        add(templateCombo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(filler, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        File f = FileDialog.openFile(new ExcelFileFilter(), Bundle.LBL_ExcelTemplateImportVisualPanel1_FileDialogTitle());
        if(f != null)
            pathText.setText(f.getAbsolutePath());
    }//GEN-LAST:event_browseButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.Box.Filler filler;
    private javax.swing.JLabel pathLabel;
    private javax.swing.JTextField pathText;
    private javax.swing.JComboBox templateCombo;
    private javax.swing.JLabel templateLabel;
    // End of variables declaration//GEN-END:variables

    private class InputListener implements DocumentListener, ActionListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            changed();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            changed();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            changed();
        }
        
        private void changed() {
            controller.changed();
        }
    }
    
    private static class TemplateRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if(value instanceof DataImportTemplate) 
                value = ((DataImportTemplate)value).getName();
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
