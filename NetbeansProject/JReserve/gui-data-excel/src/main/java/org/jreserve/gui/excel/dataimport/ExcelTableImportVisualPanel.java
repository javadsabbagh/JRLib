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

import java.io.File;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import org.apache.poi.ss.util.CellReference;
import org.jreserve.gui.poi.ExcelFileFilter;
import org.jreserve.gui.excel.ReferenceComboModel;
import org.jreserve.gui.excel.ReferenceComboRenderer;
import org.jreserve.gui.misc.utils.notifications.BubbleUtil;
import org.jreserve.gui.poi.read.PoiUtil;
import org.jreserve.gui.poi.read.ReferenceUtil;
import org.jreserve.gui.misc.utils.notifications.FileDialog;
import org.jreserve.gui.misc.utils.tasks.TaskUtil;
import org.jreserve.gui.misc.utils.widgets.TextPrompt;
import org.jreserve.gui.poi.read.TableFactory;
import org.jreserve.jrlib.gui.data.DataEntry;
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
    "LBL.ExcelTableImportVisualPanel.Name=Excel Settings",
    "LBL.ExcelTableImportVisualPanel.FileDialogTitle=Select Excel File",
    "LBL.ExcelTableImportVisualPanel.PathPrompt=Select input file",
    "LBL.ExcelTableImportVisualPanel.ReferencePrompt=Select range"
})
class ExcelTableImportVisualPanel extends javax.swing.JPanel {
    private final static String REFRESH_IMG = "org/jreserve/gui/misc/utils/refresh.png";   //NOI18
    private final static String SEARCH_IMG = "org/jreserve/gui/misc/utils/search.png";   //NOI18

    private final ExcelTableImportWizardPanel controller;
    private final ReferenceComboModel referenceModel = new ReferenceComboModel();
    private final ExcelTableModel tableModel = new ExcelTableModel();
    private ReferenceUtil refUtil;
    private JTextComponent referenceText;
    private boolean isVector = false;
    
    ExcelTableImportVisualPanel(ExcelTableImportWizardPanel controller) {
        this.controller = controller;
        initComponents();
        TextPrompt.createStandard(Bundle.LBL_ExcelTableImportVisualPanel_ReferencePrompt(), referenceText);
    }
    
    @Override
    public String getName() {
        return Bundle.LBL_ExcelTableImportVisualPanel_Name();
    }
    
    String getFilePath() {
        return pathText.getText();
    }
    
    void setVector(boolean isVector) {
        this.isVector = isVector;
        tableModel.setVector(isVector);
    }
    
    boolean isVector() {
        return isVector;
    }
    
    ReferenceUtil getReferenceUtil() {
        return refUtil;
    }
    
    String getReference() {
        return referenceText.getText();
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
        referenceLabel = new javax.swing.JLabel();
        referenceCombo = new javax.swing.JComboBox();
        tableLabel = new javax.swing.JLabel();
        refreshButton = new javax.swing.JButton();
        tableScroll = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        pBar = new javax.swing.JProgressBar();

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(pathLabel, org.openide.util.NbBundle.getMessage(ExcelTableImportVisualPanel.class, "ExcelTableImportVisualPanel.pathLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(pathLabel, gridBagConstraints);

        pathText.setEditable(false);
        pathText.setText(null);
        pathText.setFocusable(false);
        TextPrompt.createStandard(Bundle.LBL_ExcelTableImportVisualPanel_PathPrompt(), pathText);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(pathText, gridBagConstraints);

        browseButton.setIcon(ImageUtilities.loadImageIcon(SEARCH_IMG, false));
        org.openide.awt.Mnemonics.setLocalizedText(browseButton, org.openide.util.NbBundle.getMessage(ExcelTableImportVisualPanel.class, "ExcelTableImportVisualPanel.browseButton.text")); // NOI18N
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        add(browseButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(referenceLabel, org.openide.util.NbBundle.getMessage(ExcelTableImportVisualPanel.class, "ExcelTableImportVisualPanel.referenceLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 30, 5);
        add(referenceLabel, gridBagConstraints);

        referenceCombo.setEditable(true);
        referenceCombo.setModel(referenceModel);
        referenceCombo.setEnabled(false);
        referenceCombo.setRenderer(new ReferenceComboRenderer());
        referenceText = (JTextComponent) referenceCombo.getEditor().getEditorComponent();
        referenceText.getDocument().addDocumentListener(new InputListener());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 30, 5);
        add(referenceCombo, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(tableLabel, org.openide.util.NbBundle.getMessage(ExcelTableImportVisualPanel.class, "ExcelTableImportVisualPanel.tableLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        add(tableLabel, gridBagConstraints);

        refreshButton.setIcon(ImageUtilities.loadImageIcon(REFRESH_IMG, false));
        org.openide.awt.Mnemonics.setLocalizedText(refreshButton, org.openide.util.NbBundle.getMessage(ExcelTableImportVisualPanel.class, "ExcelTableImportVisualPanel.refreshButton.text")); // NOI18N
        refreshButton.setEnabled(false);
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        add(refreshButton, gridBagConstraints);

        tableScroll.setPreferredSize(new java.awt.Dimension(350, 150));

        table.setModel(tableModel);
        table.setDefaultRenderer(ExcelCell.class, new ExcelCellTableRenderer());
        table.setShowGrid (true);
        tableScroll.setViewportView(table);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        add(tableScroll, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(pBar, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        File f = FileDialog.openFile(new ExcelFileFilter(), Bundle.LBL_ExcelTableImportVisualPanel_FileDialogTitle());
        referenceCombo.setEnabled(f != null);
        if(f != null)
            updateFromFile(f);
    }//GEN-LAST:event_browseButtonActionPerformed

    private void updateFromFile(File f) {
        pathText.setText(f.getAbsolutePath());
        readExcel(f);
    }
    
    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        refreshTable();
    }//GEN-LAST:event_refreshButtonActionPerformed
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JProgressBar pBar;
    private javax.swing.JLabel pathLabel;
    private javax.swing.JTextField pathText;
    private javax.swing.JComboBox referenceCombo;
    private javax.swing.JLabel referenceLabel;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTable table;
    private javax.swing.JLabel tableLabel;
    private javax.swing.JScrollPane tableScroll;
    // End of variables declaration//GEN-END:variables

    private void refreshTable() {
        setProcessRunning(true);
        CellReference ref = refUtil.toCellReference(referenceText.getText());
        File file = new File(pathText.getText());
        final PoiUtil.Task<List<DataEntry>> reader = PoiUtil.createTask(file, ref, createTableFactory());
        Task task = TaskUtil.getRP().create(reader);
        task.addTaskListener(new TaskListener() {
            @Override
            public void taskFinished(Task task) {
                List<DataEntry> entries = null;
                try {
                    entries = reader.get();
                } catch (Exception ex) {
                    BubbleUtil.showException(ex);
                } finally {
                    tableModel.setEntries(entries);
                    setProcessRunning(false);
                }
            }
        });
        TaskUtil.getRP().execute(task);
    }
    
    private TableFactory<List<DataEntry>> createTableFactory() {
        return isVector?
                new VectorDataEntryTableReader() :
                new TriangleDataEntryTableReader();
    }

    
    private void readExcel(File file) {
        setProcessRunning(true);
        final PoiUtil.Task<ReferenceUtil> reader = PoiUtil.getReferenceUtilTask(file);
        Task task = TaskUtil.getRP().create(reader);
        task.addTaskListener(new TaskListener() {
            @Override
            public void taskFinished(Task task) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            refUtil = null;
                            refUtil = reader.get();
                        } catch (Exception ex) {
                            //TODO show error
                        } finally {
                            referenceModel.update(refUtil);
                            setProcessRunning(false);
                        }
                    }
                });
            }
        });
        TaskUtil.getRP().execute(task);
    }
    
    private void setProcessRunning(boolean running) {
        pBar.setVisible(running);
        pBar.setIndeterminate(running);
        browseButton.setEnabled(!running);
        referenceCombo.setEnabled(!running);
        checkRefreshEnabled();
    }
    
    private void checkRefreshEnabled() {
        refreshButton.setEnabled(isRefreshable());
    }
        
    private boolean isRefreshable() {
        if(refUtil == null)
            return false;
        String ref = referenceText.getText();
        if(ref == null || ref.length() == 0)
            return false;
        return refUtil.isReferenceValid(ref);
    }
    
    private class InputListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            update(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            update(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
        
        private void update(DocumentEvent e) {
            checkRefreshEnabled();
            controller.changed();

            if(shouldRefreshTable(e))
                refreshTable();
        }
        
        private boolean shouldRefreshTable(DocumentEvent e) {
            return refUtil != null &&
                   referenceText.getDocument() == e.getDocument() &&
                   refUtil.getNames().contains(referenceText.getText());
        }
    }
}
