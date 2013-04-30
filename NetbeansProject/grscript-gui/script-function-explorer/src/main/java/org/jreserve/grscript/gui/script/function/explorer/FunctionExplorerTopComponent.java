package org.jreserve.grscript.gui.script.function.explorer;

import java.util.ArrayList;
import java.util.List;
import org.jreserve.grscript.gui.script.function.explorer.nodes.FunctionFolderNode;
import org.jreserve.grscript.gui.script.functions.FunctionFolder;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.jreserve.grscript.gui.script.function.explorer//FunctionExplorer//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "FunctionExplorerTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "org.jreserve.grscript.gui.script.function.explorer.FunctionExplorerTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_FunctionExplorerAction",
        preferredID = "FunctionExplorerTopComponent")
@Messages({
    "CTL_FunctionExplorerAction=Function Explorer",
    "CTL_FunctionExplorerTopComponent=Functions",
    "HINT_FunctionExplorerTopComponent=Browse installed functions..."
})
public final class FunctionExplorerTopComponent extends TopComponent implements ExplorerManager.Provider {

    private final static ExplorerManager em = new ExplorerManager();
    
    private Result<FunctionItem> result;
    private FunctionListener listener;
    
    public FunctionExplorerTopComponent() {
        initComponents();
        setName(Bundle.CTL_FunctionExplorerTopComponent());
        setToolTipText(Bundle.HINT_FunctionExplorerTopComponent());
        initExplorerManager();
        initLookupResult();
    }
    
    private void initExplorerManager() {
        em.setRootContext(new FunctionFolderNode(FunctionFolder.getRoot()));
        associateLookup(ExplorerUtils.createLookup(em, getActionMap()));
    }

    private void initLookupResult() {
        result = getLookup().lookupResult(FunctionItem.class);
        listener = new FunctionListener();
        result.addLookupListener(listener);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        treeView = new org.openide.explorer.view.BeanTreeView();
        descScroll = new javax.swing.JScrollPane();
        descText = new javax.swing.JTextPane();

        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(0.9);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        treeView.setRootVisible(false);
        jSplitPane1.setTopComponent(treeView);

        descText.setEditable(false);
        descText.setContentType("text/html"); // NOI18N
        descScroll.setViewportView(descText);

        jSplitPane1.setRightComponent(descScroll);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane descScroll;
    private javax.swing.JTextPane descText;
    private javax.swing.JSplitPane jSplitPane1;
    private org.openide.explorer.view.BeanTreeView treeView;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
    }

    @Override
    public void componentClosed() {
    }

    void writeProperties(java.util.Properties p) {
    }

    void readProperties(java.util.Properties p) {
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }
    
    private class FunctionListener implements LookupListener {

        @Override
        public void resultChanged(LookupEvent le) {
            List<FunctionItem> items = new ArrayList<FunctionItem>(result.allInstances());
            if(!items.isEmpty()) {
                FunctionItem item = items.get(items.size()-1);
                descText.setText(item.getDescription());
            }
        }    
    }
}
