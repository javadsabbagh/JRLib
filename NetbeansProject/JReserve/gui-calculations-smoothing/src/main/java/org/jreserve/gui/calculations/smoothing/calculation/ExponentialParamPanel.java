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

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
class ExponentialParamPanel extends javax.swing.JPanel {

    private final ExponentialSmoothingDialogController controller;
    
    ExponentialParamPanel(ExponentialSmoothingDialogController controller) {
        this.controller = controller;
        initComponents();
        msgLabel.showError(null);
    }
    
    void setAlpha(String alpha) {
        alphaText.setText(alpha);
    }
    
    String getAlpha() {
        return alphaText.getText();
    }
    
    void showError(String msg) {
        if(msg == null)
            msgLabel.clearMessage();
        else
            msgLabel.showError(msg);
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

        alphaLabel = new javax.swing.JLabel();
        alphaText = new javax.swing.JTextField();
        filler = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        msgLabel = new org.jreserve.gui.misc.utils.widgets.MsgLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 0, 10));
        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(alphaLabel, org.openide.util.NbBundle.getMessage(ExponentialParamPanel.class, "ExponentialParamPanel.alphaLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 5);
        add(alphaLabel, gridBagConstraints);

        alphaText.setColumns(10);
        alphaText.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        alphaText.setText(null);
        alphaText.getDocument().addDocumentListener(new TextListener());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_TRAILING;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
        add(alphaText, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(filler, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(msgLabel, org.openide.util.NbBundle.getMessage(ExponentialParamPanel.class, "ExponentialParamPanel.msgLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(msgLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel alphaLabel;
    private javax.swing.JTextField alphaText;
    private javax.swing.Box.Filler filler;
    private org.jreserve.gui.misc.utils.widgets.MsgLabel msgLabel;
    // End of variables declaration//GEN-END:variables

    private class TextListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            controller.changed();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            controller.changed();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    }
}