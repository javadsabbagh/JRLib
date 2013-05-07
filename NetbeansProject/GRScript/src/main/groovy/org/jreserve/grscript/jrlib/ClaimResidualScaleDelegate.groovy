
package org.jreserve.grscript.jrlib

import org.jreserve.grscript.FunctionProvider
import org.jreserve.jrlib.bootstrap.odp.residuals.OdpResidualTriangle
import org.jreserve.jrlib.bootstrap.odp.scale.ConstantOdpResidualScale
import org.jreserve.jrlib.bootstrap.odp.scale.DefaultOdpResidualScaleSelection
import org.jreserve.jrlib.bootstrap.odp.scale.OdpResidualScale
import org.jreserve.jrlib.bootstrap.odp.scale.VariableOdpResidualScale
import org.jreserve.jrlib.bootstrap.odp.scale.UserInputOdpSMethod
import org.jreserve.jrlib.bootstrap.odp.scale.SimpleOdpRSEstimate
import org.jreserve.jrlib.bootstrap.odp.scale.EmptyOdpResidualScale
import org.jreserve.jrlib.bootstrap.odp.scale.OdpRSMethod

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
class ClaimResidualScaleDelegate implements FunctionProvider {
    
    void initFunctions(Script script, ExpandoMetaClass emc) {
        emc.constantScale << this.&constantScale
        emc.variableScale << this.&variableScale
    }
    
    OdpResidualScale constantScale(OdpResidualTriangle residuals) {
        return new ConstantOdpResidualScale(residuals)
    }
    
    OdpResidualScale constantScale(OdpResidualTriangle residuals, double scale) {
        int length = residuals.getDevelopmentCount()
        UserInputOdpSMethod method = new UserInputOdpSMethod()
        for(int d=0; d<length; d++)
            method.setValue(d, scale)
        OdpResidualScale scales = new EmptyOdpResidualScale(residuals)
        return new DefaultOdpResidualScaleSelection(scales, method)
    }
    
    OdpResidualScale variableScale(OdpResidualTriangle residuals) {
        return new VariableOdpResidualScale(residuals)
    }
    
    OdpResidualScale variableScale(OdpResidualTriangle residuals, Closure cl) {
        Builder builder = new Builder(residuals)
        cl.delegate = builder
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl()
        return builder.scales
    }
    
    private class Builder extends AbstractMethodSelectionBuilder<OdpRSMethod> {
        private DefaultOdpResidualScaleSelection scales
        
        Builder(OdpResidualTriangle residuals) {
            OdpResidualScale s = new VariableOdpResidualScale(residuals)
            scales = new DefaultOdpResidualScaleSelection(s)
        }
    
        void fixed(int index, double value) {
            OdpRSMethod estimator = getCachedMethod(UserInputOdpSMethod.class) {new UserInputOdpSMethod()}
            ((UserInputOdpSMethod)estimator).setValue(index, value)
            scales.setMethod(estimator, index)
        }
    
        void fixed(Map map) {
            map.each {index, value -> fixed(index, value)}
        }        
    }
}

