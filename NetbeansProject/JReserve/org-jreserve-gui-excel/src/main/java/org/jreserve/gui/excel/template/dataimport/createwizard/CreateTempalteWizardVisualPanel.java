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
package org.jreserve.gui.excel.template.dataimport.createwizard;

import java.io.File;
import java.util.Collections;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.jreserve.gui.data.api.ImportUtil;
import org.jreserve.gui.excel.ExcelFileFilter;
import org.jreserve.gui.misc.utils.notifications.BubbleUtil;
import org.jreserve.gui.misc.utils.notifications.FileDialog;
import org.jreserve.gui.misc.utils.widgets.TextPrompt;
import org.openide.util.ChangeSupport;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Task;
import org.openide.util.TaskListener;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
@Messages({
    "LBL.CreateTempalteWizardVisualPanel.Prompt.File=Select file",
    "LBL.CreateTempalteWizardVisualPanel.Prompt.Name=Template name",
    "LBL.CreateTempalteWizardVisualPanel.FileDialog.Title=Select Excel File",
    "LBL.CreateTempalteWizardVisualPanel.DataType.Triangle=Triangle",
    "LBL.CreateTempalteWizardVisualPanel.DataType.Vector=Vector",
    "LBL.CreateTempalteWizardVisualPanel.SoruceType.Table=Table",
    "LBL.CreateTempalteWizardVisualPanel.SoruceType.Triangle=Triangle",
    "MSG.CreateTempalteWizardVisualPanel.Excel.ReadError=Unable to read Excel file!"
})
class CreateTempalteWizardVisualPanel extends javax.swing.JPanel {
    private final static String IMG_SEARCH = "org/jreserve/gui/misc/utils/search.png";   //NOI18
    
    private final ChangeSupport cs = new ChangeSupport(this);
    private final InputListener inputListener = new InputListener();
    
    CreateTempalteWizardVisualPanel() {
        initComponents();
        table.addChangeListener(inputListener);
    }

    String getTemplateName() {
        return nameText.getText();
    }
    
    List<TemplateRow> getTempalteRows() {
        return table.getRows();
    }
    
    void addChangeListener(ChangeListener cl) {
        cs.addChangeListener(cl);
    }
    
    void removeChangeListener(ChangeListener cl) {
        cs.removeChangeListener(cl);
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

        fileLabel = new javax.swing.JLabel();
        fileText = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        nameLabel = new javax.swing.JLabel();
        nameText = new javax.swing.JTextField();
        itemsLabel = new javax.swing.JLabel();
        table = new org.jreserve.gui.excel.template.dataimport.createwizard.ImportTemplateItemTable();
        pBar = new javax.swing.JProgressBar();

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(fileLabel, org.openide.util.NbBundle.getMessage(CreateTempalteWizardVisualPanel.class, "CreateTempalteWizardVisualPanel.fileLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(fileLabel, gridBagConstraints);

        fileText.setEditable(false);
        fileText.setText(null);
        TextPrompt.createStandard(Bundle.LBL_CreateTempalteWizardVisualPanel_Prompt_File(), fileText);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(fileText, gridBagConstraints);

        browseButton.setIcon(ImageUtilities.loadImageIcon(IMG_SEARCH, false));
        org.openide.awt.Mnemonics.setLocalizedText(browseButton, org.openide.util.NbBundle.getMessage(CreateTempalteWizardVisualPanel.class, "CreateTempalteWizardVisualPanel.browseButton.text")); // NOI18N
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
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        add(browseButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(nameLabel, org.openide.util.NbBundle.getMessage(CreateTempalteWizardVisualPanel.class, "CreateTempalteWizardVisualPanel.nameLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 5);
        add(nameLabel, gridBagConstraints);

        nameText.setText(null);
        TextPrompt.createStandard(Bundle.LBL_CreateTempalteWizardVisualPanel_Prompt_Name(), nameText);
        nameText.getDocument().addDocumentListener(inputListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 5);
        add(nameText, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(itemsLabel, org.openide.util.NbBundle.getMessage(CreateTempalteWizardVisualPanel.class, "CreateTempalteWizardVisualPanel.itemsLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(itemsLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(table, gridBagConstraints);

        pBar.setVisible(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(pBar, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        File f = FileDialog.openFile(new ExcelFileFilter(), Bundle.LBL_CreateTempalteWizardVisualPanel_FileDialog_Title());
        if(f != null)
            updateFromFile(f);
    }//GEN-LAST:event_browseButtonActionPerformed
    
    private void updateFromFile(File file) {
        fileText.setText(file.getAbsolutePath());
        if(nameText.getDocument().getLength() == 0)
            nameText.setText(getFileName(file));
        readExcel(file);
    }
    
    private String getFileName(File file) {
        String name = file.getName();
        int index = name.lastIndexOf('.');
        if(index >= 0)
            name = name.substring(0, index);
        return name;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JTextField fileText;
    private javax.swing.JLabel itemsLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameText;
    private javax.swing.JProgressBar pBar;
    private org.jreserve.gui.excel.template.dataimport.createwizard.ImportTemplateItemTable table;
    // End of variables declaration//GEN-END:variables

    private void readExcel(File file) {
        setProcessRunning(true);
        final ExcelNameReader reader = new ExcelNameReader(file);
        Task task = ImportUtil.getRP().create(reader);
        task.addTaskListener(new TaskListener() {
            @Override
            public void taskFinished(Task task) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            table.setNames(reader.getNames());
                        } catch (Exception ex) {
                            BubbleUtil.showException(Bundle.MSG_CreateTempalteWizardVisualPanel_Excel_ReadError(), ex);
                            table.setNames(Collections.EMPTY_LIST);
                        } finally {
                            setProcessRunning(false);
                        }
                    }
                });
            }
        });
        ImportUtil.getRP().execute(task);
    }

    private void setProcessRunning(boolean running) {
        pBar.setVisible(running);
        pBar.setIndeterminate(running);
        browseButton.setEnabled(!running);
        fileText.setEnabled(!running);
        nameText.setEnabled(!running);
        table.setEnabled(!running);
    }
    
    private class InputListener implements DocumentListener, ChangeListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            fireChange();
        }
        
        private void fireChange() {
            cs.fireChange();
        }
        
        @Override
        public void removeUpdate(DocumentEvent e) {
            fireChange();
        }
        
        @Override
        public void changedUpdate(DocumentEvent e) {
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            fireChange();
        }
    }
}
