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
package org.jreserve.gui.data.csv.input.table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumnModel;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.jreserve.gui.data.csv.CsvFileFilter;
import org.jreserve.gui.data.api.inport.ImportUtil;
import org.jreserve.gui.data.csv.input.PreviewReader;
import org.jreserve.gui.data.csv.input.PreviewRenderer;
import org.jreserve.gui.data.csv.input.PreviewTableModel;
import org.jreserve.gui.data.csv.settings.CsvImportSettings;
import org.jreserve.gui.localesettings.LocaleSettings;
import org.jreserve.gui.misc.utils.notifications.FileDialog;
import org.jreserve.gui.misc.utils.widgets.TextPrompt;
import org.jreserve.gui.misc.utils.widgets.WidgetUtils;
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
    "LBL.CsvTableImportVisualPanel.FilePrompt=Select csv file...",
    "LBL.CsvTableImportVisualPanel.Title=CSV Settings",
    "LBL.CsvTableImportVisualPanel.FileChooser.Title=Select CSV File"
})
public class CsvTableImportVisualPanel extends javax.swing.JPanel {
    
    private final static String REFRESH_IMG = "org/jreserve/gui/misc/utils/refresh.png";   //NOI18
    private final static int MIN_PREVIEW_COLUMN_WIDTH = 65;
    
    private PreviewRenderer renderer;
    private final CsvTableImportWizardPanel panel;
    private JTextComponent separatorEditor;
    private JTextComponent dateFormatEditor;
    private final InputListener inputListener = new InputListener();
    private final PreviewTableModel prevModel = new PreviewTableModel();
    
    public CsvTableImportVisualPanel(CsvTableImportWizardPanel panel) {
        this.panel = panel;
        initComponents();
        updateExmapleDate();
    }
    
    @Override
    public String getName() {
        return Bundle.LBL_CsvTableImportVisualPanel_Title();
    }
    
    private void updateExmapleDate() {
        try {
            String str = dateFormatEditor.getText();
            SimpleDateFormat sdf = new SimpleDateFormat(str);
            dateExampleText.setText(sdf.format(new Date()));
        } catch (Exception ex) {
            dateExampleText.setText(null);
        }
    }
    
    void startProgressBar() {
        setProgressing(true);
    }
    
    private void setProgressing(boolean running) {
        pBar.setIndeterminate(running);
        pBar.setVisible(running);
        fileText.setEnabled(!running);
        fileButton.setEnabled(!running);
        columnHeaderCheck.setEnabled(!running);
        rowHeaderCheck.setEnabled(!running);
        cellsQuotedCheck.setEnabled(!running);
        cellSeparatorCombo.setEnabled(!running);
        decimalSeparatorText.setEnabled(!running);
        dateFormatCombo.setEnabled(!running);
    }
    
    void stopProgressBar() {
        setProgressing(false);
    }
    
    String getCsvPath() {
        return fileText.getText();
    }
    
    boolean hasColumnHeader() {
        return columnHeaderCheck.isSelected();
    }
    
    boolean hasRowHeader() {
        return rowHeaderCheck.isSelected();
    }
    
    boolean isCellsQuoted() {
        return cellsQuotedCheck.isSelected();
    }
    
    String getCellSeparator() {
        return separatorEditor.getText();
    }
    
    String getDecimalSeparator() {
        return decimalSeparatorText.getText();
    }
    
    String getDateFormat() {
        return dateFormatEditor.getText();
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
        fileButton = new javax.swing.JButton();
        hasColumnHeaderLabel = new javax.swing.JLabel();
        columnHeaderCheck = new javax.swing.JCheckBox();
        hasRowHeaderLabel = new javax.swing.JLabel();
        rowHeaderCheck = new javax.swing.JCheckBox();
        cellsQuotedLabel = new javax.swing.JLabel();
        cellsQuotedCheck = new javax.swing.JCheckBox();
        cellSeparatorLabel = new javax.swing.JLabel();
        cellSeparatorCombo = new javax.swing.JComboBox();
        decimalSeparatorLabel = new javax.swing.JLabel();
        decimalSeparatorText = new javax.swing.JTextField();
        dateFormatLabel = new javax.swing.JLabel();
        dateFormatCombo = new javax.swing.JComboBox();
        dateExampleText = new javax.swing.JLabel();
        previewHeader = new javax.swing.JPanel();
        previewLabel = new javax.swing.JLabel();
        lineSpinner = new javax.swing.JSpinner();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        refreshButton = new javax.swing.JButton();
        previewScroll = new javax.swing.JScrollPane();
        previewTable = new javax.swing.JTable();
        pBar = new javax.swing.JProgressBar();

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(fileLabel, org.openide.util.NbBundle.getMessage(CsvTableImportVisualPanel.class, "CsvTableImportVisualPanel.fileLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(fileLabel, gridBagConstraints);

        fileText.setText(null);
        fileText.getDocument().addDocumentListener(inputListener);
        TextPrompt.createStandard(Bundle.LBL_CsvTableImportVisualPanel_FilePrompt(), fileText);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(fileText, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(fileButton, org.openide.util.NbBundle.getMessage(CsvTableImportVisualPanel.class, "CsvTableImportVisualPanel.fileButton.text")); // NOI18N
        fileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        add(fileButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(hasColumnHeaderLabel, org.openide.util.NbBundle.getMessage(CsvTableImportVisualPanel.class, "CsvTableImportVisualPanel.hasColumnHeaderLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(hasColumnHeaderLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(columnHeaderCheck, null);
        columnHeaderCheck.setSelected(CsvImportSettings.hasColumnHeaders());
        columnHeaderCheck.addActionListener(inputListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(columnHeaderCheck, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(hasRowHeaderLabel, org.openide.util.NbBundle.getMessage(CsvTableImportVisualPanel.class, "CsvTableImportVisualPanel.hasRowHeaderLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(hasRowHeaderLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(rowHeaderCheck, null);
        rowHeaderCheck.setSelected(CsvImportSettings.hasRowHeaders());
        rowHeaderCheck.addActionListener(inputListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(rowHeaderCheck, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(cellsQuotedLabel, org.openide.util.NbBundle.getMessage(CsvTableImportVisualPanel.class, "CsvTableImportVisualPanel.cellsQuotedLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(cellsQuotedLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(cellsQuotedCheck, null);
        cellsQuotedCheck.setSelected(CsvImportSettings.cellsQuoted());
        cellsQuotedCheck.addActionListener(inputListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(cellsQuotedCheck, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(cellSeparatorLabel, org.openide.util.NbBundle.getMessage(CsvTableImportVisualPanel.class, "CsvTableImportVisualPanel.cellSeparatorLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(cellSeparatorLabel, gridBagConstraints);

        cellSeparatorCombo.setEditable(true);
        cellSeparatorCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cellSeparatorCombo.setModel(new DefaultComboBoxModel(CsvImportSettings.getCellSeparators()));
        cellSeparatorCombo.setSelectedItem(CsvImportSettings.getCellSeparator());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(cellSeparatorCombo, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(decimalSeparatorLabel, org.openide.util.NbBundle.getMessage(CsvTableImportVisualPanel.class, "CsvTableImportVisualPanel.decimalSeparatorLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(decimalSeparatorLabel, gridBagConstraints);

        WidgetUtils.oneCharacterInput(decimalSeparatorText);
        decimalSeparatorText.setText(null);
        separatorEditor = (JTextComponent) cellSeparatorCombo.getEditor().getEditorComponent();
        separatorEditor.getDocument().addDocumentListener(inputListener);
        decimalSeparatorText.setText(""+LocaleSettings.getDecimalSeparator());
        decimalSeparatorText.getDocument().addDocumentListener(inputListener);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(decimalSeparatorText, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(dateFormatLabel, org.openide.util.NbBundle.getMessage(CsvTableImportVisualPanel.class, "CsvTableImportVisualPanel.dateFormatLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 5);
        add(dateFormatLabel, gridBagConstraints);

        dateFormatCombo.setEditable(true);
        dateFormatCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "yyyy-MM", "yyyy-MM-dd", "MM-yyyy", "dd-MM-yyyy", " " }));
        dateFormatCombo.setModel(new DefaultComboBoxModel(LocaleSettings.getDateFormats()));
        dateFormatEditor = (JTextComponent) dateFormatCombo.getEditor().getEditorComponent();
        dateFormatEditor.getDocument().addDocumentListener(inputListener);
        dateFormatCombo.setSelectedItem(LocaleSettings.getDateFormat());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 5);
        add(dateFormatCombo, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(dateExampleText, null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 5);
        add(dateExampleText, gridBagConstraints);

        previewHeader.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(previewLabel, org.openide.util.NbBundle.getMessage(CsvTableImportVisualPanel.class, "CsvTableImportVisualPanel.previewLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        previewHeader.add(previewLabel, gridBagConstraints);

        lineSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
        lineSpinner.setValue(CsvImportSettings.getPreviewLines());
        ((JSpinner.DefaultEditor)lineSpinner.getEditor()).getTextField().setColumns(4);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        previewHeader.add(lineSpinner, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.weightx = 1.0;
        previewHeader.add(filler1, gridBagConstraints);

        refreshButton.setIcon(ImageUtilities.loadImageIcon(REFRESH_IMG, false));
        org.openide.awt.Mnemonics.setLocalizedText(refreshButton, org.openide.util.NbBundle.getMessage(CsvTableImportVisualPanel.class, "CsvTableImportVisualPanel.refreshButton.text")); // NOI18N
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        previewHeader.add(refreshButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(previewHeader, gridBagConstraints);

        previewScroll.setPreferredSize(new java.awt.Dimension(300, 150));

        previewTable.setModel(prevModel);
        previewTable.setShowGrid(true);
        previewTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        renderer = new PreviewRenderer();
        previewTable.setDefaultRenderer(String.class, renderer);
        previewScroll.setViewportView(previewTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        add(previewScroll, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(pBar, gridBagConstraints);
        pBar.setVisible(false);
    }// </editor-fold>//GEN-END:initComponents

    private void fileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileButtonActionPerformed
        File f = FileDialog.openFile(CsvFileFilter.getDefault(), Bundle.LBL_CsvTableImportVisualPanel_FileChooser_Title());
        if(f != null) {
            fileText.setText(f.getAbsolutePath());
            refreshButtonActionPerformed(null);
        }
    }//GEN-LAST:event_fileButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        refreshButton.setEnabled(false);
        final PreviewReader reader = new PreviewReader(getCsvFile(), (Integer)lineSpinner.getValue());
        Task task = ImportUtil.getRP().create(reader);
        task.addTaskListener(new TaskListener() {
            @Override
            public void taskFinished(Task task) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        refreshButton.setEnabled(true);
                        if(prevModel != null) {
                            prevModel.setHasColumnTitles(columnHeaderCheck.isSelected());
                            prevModel.setCellSeparator(separatorEditor.getText());
                            prevModel.setLines(reader.readLines());
                            setPreviewColumnWidths();
                        }
                    }
                });
            }
        });
        ImportUtil.getRP().execute(task);
    }//GEN-LAST:event_refreshButtonActionPerformed
    
    private void setPreviewColumnWidths() {
        TableColumnModel columns = previewTable.getColumnModel();
        int count = columns.getColumnCount();
        for(int i=0; i<count; i++)
            columns.getColumn(i).setMinWidth(MIN_PREVIEW_COLUMN_WIDTH );
    }
    
    private File getCsvFile() {
        String path = fileText.getText();
        if(path==null || path.length()==0)
            return null;
        return new File(path);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cellSeparatorCombo;
    private javax.swing.JLabel cellSeparatorLabel;
    private javax.swing.JCheckBox cellsQuotedCheck;
    private javax.swing.JLabel cellsQuotedLabel;
    private javax.swing.JCheckBox columnHeaderCheck;
    private javax.swing.JLabel dateExampleText;
    private javax.swing.JComboBox dateFormatCombo;
    private javax.swing.JLabel dateFormatLabel;
    private javax.swing.JLabel decimalSeparatorLabel;
    private javax.swing.JTextField decimalSeparatorText;
    private javax.swing.JButton fileButton;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JTextField fileText;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel hasColumnHeaderLabel;
    private javax.swing.JLabel hasRowHeaderLabel;
    private javax.swing.JSpinner lineSpinner;
    private javax.swing.JProgressBar pBar;
    private javax.swing.JPanel previewHeader;
    private javax.swing.JLabel previewLabel;
    private javax.swing.JScrollPane previewScroll;
    private javax.swing.JTable previewTable;
    private javax.swing.JButton refreshButton;
    private javax.swing.JCheckBox rowHeaderCheck;
    // End of variables declaration//GEN-END:variables
    
    private class InputListener implements ActionListener, DocumentListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if(source == columnHeaderCheck)
                prevModel.setHasColumnTitles(columnHeaderCheck.isSelected());
            else if(source == rowHeaderCheck) {
                renderer.setHasRowHeaders(rowHeaderCheck.isSelected());
                previewTable.repaint();
            }
            fireChange();
        }
        
        private void fireChange() {
            panel.panelChanged();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            documentChange(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            documentChange(e);
        }
        
        private void documentChange(DocumentEvent e) {
            Document d = e.getDocument();
            if(d == dateFormatEditor.getDocument())
                updateExmapleDate();
            else if(d == separatorEditor.getDocument())
                prevModel.setCellSeparator(separatorEditor.getText());
            fireChange();
        }
        
        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    }
}
