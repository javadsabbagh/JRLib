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

import org.jreserve.gui.calculations.claimtriangle.ClaimTriangleEvent;
import org.jreserve.gui.calculations.claimtriangle.impl.ClaimTriangleCalculationImpl;
import org.jreserve.gui.calculations.claimtriangle.impl.DataSourceController;
import org.jreserve.gui.data.api.DataEvent;
import org.jreserve.gui.data.api.DataSource;
import org.jreserve.gui.data.api.DataSourceObjectProvider;
import org.jreserve.gui.misc.eventbus.EventBusListener;
import org.jreserve.gui.misc.eventbus.EventBusManager;
import org.jreserve.gui.misc.utils.dataobject.ProjectObjectLookup;
import org.jreserve.gui.misc.utils.widgets.CommonIcons;
import org.jreserve.gui.misc.utils.widgets.EmptyIcon;
import org.netbeans.api.project.Project;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
@Messages({
    "MSG.DataSourceEditorPanel.DataSource.NoProvider=Project can not contain storages!",
    "MSG.DataSourceEditorPanel.DataSource.Empty=Field 'Storage' is empty!",
    "MSG.DataSourceEditorPanel.DataSource.NotFound=Storage not found!"
})
class DataSourceEditorPanel extends javax.swing.JPanel {

    private ClaimTriangleCalculationImpl calculation;
    private DataSourceObjectProvider dop;
    private ProjectObjectLookup pol;
    
    DataSourceEditorPanel() {
        initComponents();
        EventBusManager.getDefault().subscribe(this);
        showError(Bundle.MSG_DataSourceEditorPanel_DataSource_NoProvider());
    }
    
    void componentClosed() {
        EventBusManager.getDefault().unsubscribe(this);
    }
    
    void setCalculation(ClaimTriangleCalculationImpl calculation) {
        this.calculation = calculation;
        Project project = calculation.getProject();
        pol = project == null? null : project.getLookup().lookup(ProjectObjectLookup.class);
        dop = project == null? null : project.getLookup().lookup(DataSourceObjectProvider.class);
        
        if(dop != null) {
            sourceText.setEnabled(true);
            browseButton.setEnabled(true);
            
            DataSource ds = calculation.getDataSource();
            sourceText.setText(ds==null? null : ds.getPath());
            showError(null);
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

        sourceLabel = new javax.swing.JLabel();
        sourceText = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        errLabel = new javax.swing.JLabel();
        filler = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));

        setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));
        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(sourceLabel, org.openide.util.NbBundle.getMessage(DataSourceEditorPanel.class, "DataSourceEditorPanel.sourceLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(sourceLabel, gridBagConstraints);

        sourceText.setText(null);
        sourceText.setEnabled(false);
        sourceText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sourceTextActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(sourceText, gridBagConstraints);

        browseButton.setIcon(CommonIcons.search());
        org.openide.awt.Mnemonics.setLocalizedText(browseButton, org.openide.util.NbBundle.getMessage(DataSourceEditorPanel.class, "DataSourceEditorPanel.browseButton.text")); // NOI18N
        browseButton.setEnabled(false);
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

        errLabel.setForeground(java.awt.Color.red);
        errLabel.setIcon(EmptyIcon.EMPTY_16);
        org.openide.awt.Mnemonics.setLocalizedText(errLabel, " ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(errLabel, gridBagConstraints);
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
        DataSource ds = DataSourceController.selectOne(dop);
        if(ds != null)
            calculation.setDataSource(ds);
    }//GEN-LAST:event_browseButtonActionPerformed

    private void sourceTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sourceTextActionPerformed
        String path = sourceText.getText();
        if(path == null || path.length() == 0) {
            showError("");
            return;
        }
        
        DataSource ds = pol.lookupOne(path, DataSource.class);
        if(ds == null) {
            showError("");
            return;
        }
        
        calculation.setDataSource(ds);
    }//GEN-LAST:event_sourceTextActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JLabel errLabel;
    private javax.swing.Box.Filler filler;
    private javax.swing.JLabel sourceLabel;
    private javax.swing.JTextField sourceText;
    // End of variables declaration//GEN-END:variables

    @EventBusListener(forceEDT = true)
    public void dataSourceChanged(ClaimTriangleEvent.SourceChange evt) {
        if(calculation == evt.getCalculationProvider())
            sourceText.setText(calculation.getDataSource().getPath());
    }
    
    @EventBusListener(forceEDT = true)
    public void dataSourcePathChanged(DataEvent.Renamed evt) {
        DataSource ds = evt.getDataSource();
        if(ds == calculation.getDataSource())
            sourceText.setText(ds.getPath());
    }
    
    private void showError(String msg) {
        if(msg == null) {
            errLabel.setIcon(EmptyIcon.EMPTY_16);
            errLabel.setText(null);
        } else {
            errLabel.setIcon(CommonIcons.error());
            errLabel.setText(msg);
        }
    }
}