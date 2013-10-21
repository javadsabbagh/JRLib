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
package org.jreserve.gui.calculations.smoothing.calculation;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
class RegressionParamPanel extends javax.swing.JPanel {
    
    private final AbstractRegressionDialogController controller;
    
    RegressionParamPanel(AbstractRegressionDialogController controller) {
        this.controller = controller;
        initComponents();
    }

    boolean hasIntercept() {
        return interceptCheck.isSelected();
    }
    
    void setHasIntercept(boolean hasIntercept) {
        interceptCheck.setSelected(hasIntercept);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        interceptLabel = new javax.swing.JLabel();
        interceptCheck = new javax.swing.JCheckBox();

        setLayout(new java.awt.BorderLayout(5, 0));

        org.openide.awt.Mnemonics.setLocalizedText(interceptLabel, org.openide.util.NbBundle.getMessage(RegressionParamPanel.class, "RegressionParamPanel.interceptLabel.text")); // NOI18N
        add(interceptLabel, java.awt.BorderLayout.LINE_START);

        org.openide.awt.Mnemonics.setLocalizedText(interceptCheck, null);
        interceptCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                interceptCheckActionPerformed(evt);
            }
        });
        add(interceptCheck, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void interceptCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_interceptCheckActionPerformed
        controller.changed();
    }//GEN-LAST:event_interceptCheckActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox interceptCheck;
    private javax.swing.JLabel interceptLabel;
    // End of variables declaration//GEN-END:variables
}