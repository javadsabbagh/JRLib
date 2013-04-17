package org.jrlib.estimate.mcl;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jrlib.AbstractMultiSourceCalculationData;
import org.jrlib.CalculationData;
import org.jrlib.MutableSource;
import org.jrlib.triangle.claim.ClaimTriangle;
import org.jrlib.triangle.ratio.RatioTriangleInput;

/**
 * This class handles input for the Munich Chain-Ladder calculations. Basically
 * the method uses four imput calculations:
 * -   paid link-ratios
 * -   incurred link-ratios
 * -   paid/incurred ratios
 * -   incurred/paid ratios
 * 
 * Simply feeding these inputs to a calculation would result upon unnecessary
 * recalculations in the following situation:
 * -   One calls `recalculate` on the Munich Chain-Ladder calculation. This 
 *     would recalculate all input and all claim triangles would be recalculated
 *     3 times.
 * -   One of the underlying claim triangles change. In this situation the
 *     Munich Chain-Ladder calculation would be notified 3 times.
 *
 * To address these issues, this class introduces proxy claim triangles, which
 * interrupts the update and recalculate calls for the four sub calculations. 
 * If needed the class recalculates it's input manually.
 * 
 * @see ClaimTriangleProxy
 * @author Peter Decsi
 * @version 1.0
 */
public class MclCalculationBundle extends AbstractMultiSourceCalculationData<ClaimTriangle> {
    
    private final static int PAID = 0;
    private final static int INCURRED = 1;
    
    private static ClaimTriangle extractTriangle(MclCorrelation correlation) {
        MclCorrelationInput input = correlation.getSourceInput();
        return input.getSourceLinkRatioResiduals().getSourceTriangle();
    }
    
    private CorrelationListener correlationListener = new CorrelationListener();
    private boolean myChange = false;
    
    private ClaimTriangleProxy paidProxy;
    private ClaimTriangle paid;
    private MclCorrelation paidCorrelation;
    private ClaimTriangleProxy incurredProxy;
    private ClaimTriangle incurred;
    private MclCorrelation incurredCorrelation;
    
    public MclCalculationBundle(MclCorrelation paidCorrelation, MclCorrelation incurredCorrelation) {
        super(extractTriangle(paidCorrelation), extractTriangle(incurredCorrelation));
        this.paidCorrelation = paidCorrelation;
        this.incurredCorrelation = incurredCorrelation;
        initSources();
    }
    
    private void initSources() {
        initTriangles();
        setSourceForDevelopmentFactors();
        setSourceForClaimRatios();
        addCorrelationListeners();
    }
    
    private void initTriangles() {
        paid = sources[PAID];
        paidProxy = new ClaimTriangleProxy(paid);
        incurred = sources[INCURRED];
        incurredProxy = new ClaimTriangleProxy(incurred);
    }
    
    private void setSourceForDevelopmentFactors() {
        setSource(paidCorrelation.getSourceFactors(), paidProxy);
        setSource(incurredCorrelation.getSourceFactors(), incurredProxy);
    }
    
    private <E, T extends CalculationData & MutableSource<? super E>> void setSource(T calculation, E source) {
        boolean eventsFired = calculation.isEventsFired();
        calculation.setEventsFired(false);
        calculation.setSource(source);
        calculation.setEventsFired(eventsFired);
    }
    
    private void setSourceForClaimRatios() {
        RatioTriangleInput input = new RatioTriangleInput(incurredProxy, paidProxy);
        setSource(paidCorrelation.getSourceRatioTriangle(), input);
        input = new RatioTriangleInput(paidProxy, incurredProxy);
        setSource(incurredCorrelation.getSourceRatioTriangle(), input);
    }
    
    private void addCorrelationListeners() {
        paidCorrelation.addChangeListener(correlationListener);
        incurredCorrelation.addChangeListener(correlationListener);
    }
    
    public MclCorrelation getSourcePaidCorrelation() {
        return paidCorrelation;
    }
    
    public MclCorrelation getSourceIncurredCorrelation() {
        return incurredCorrelation;
    }
    
    @Override
    protected void recalculateLayer() {
        paidCorrelation.recalculate();
        incurredCorrelation.recalculate();
    }
    
    @Override
    public void detach() {
        super.detach();
        paidCorrelation.detach();
        incurredCorrelation.detach();
    }
    
    private class CorrelationListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            if(!myChange)
                fireChange();
        }
    }
}
//                                 +----------------+
//                                 |   mclBundle    |
//                                 +----------------+
//                               __________|____________
//                              |                        |
//                      +---------------+      +-----------------+
//                      |  pCorrSource  |      |  iCorrSource    |
//                      +---------------+      +-----------------+
//                              |                        |
//                      +---------------+      +-----------------+
//                      |  pCorrSource  |      |  iCorrSource    |
//                      +---------------+      +-----------------+
//           _____________|     |                       |    |________________
//          |                   |                       |                     |
// +----------------+   +---------------+      +-----------------+   +----------------+
// |  pCrResidals   |   |  pLrResidals  |      |  iCrResidals    |   |  iLrResidals   |
// +----------------+   +---------------+      +-----------------+   +----------------+
//          |                   |                       |                     |
// +----------------+   +---------------+      +-----------------+   +----------------+
// |    pCrScale    |   |    pLrScale   |      |     iCrScale    |   |    iLrScale    |
// +----------------+   +---------------+      +-----------------+   +----------------+
//          |                   |                       |                     |
// +----------------+   +---------------+      +-----------------+   +----------------+
// | pCr: ClaimRatio|   | pLr: LossRatio|      | iCr: ClaimRatio |   | iLr: LossRatio |
// +----------------+   +---------------+      +-----------------+   +----------------+
//        |   |                  |                   |        |                |
//        |   |          +---------------+           |        |       +----------------+
//        |   |          | pLr: LossRatio|           |        |       | iLr: LossRatio |
//        |   |          +---------------+           |        |       +----------------+
//        |   |________________|_________________    |        |            /
//        \_________           |         ________\___|        |           /
//                  \          |        /         \           |          /
//         +------------------------------+      +---------------------------------+
//         | paidProxy:ClaimTriangleProxy |      | inurredProxy:ClaimTriangleProxy |
//         +------------------------------+      +---------------------------------+
//                      |                                       |
//            +--------------------+                 +------------------------+
//            | paid:ClaimTriangle |                 | incurred:ClaimTriangle |
//            +--------------------+                 +------------------------+