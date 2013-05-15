package org.jreserve.grscript.jrlib

import org.jreserve.grscript.FunctionProvider
import org.jreserve.grscript.util.MapUtil
import org.jreserve.jrlib.bootstrap.odp.scale.OdpResidualScale
import org.jreserve.jrlib.bootstrap.odp.scaledresiduals.OdpScaledResidualTriangle
import org.jreserve.jrlib.bootstrap.odp.scaledresiduals.AdjustedOdpScaledResidualTriangle
import org.jreserve.jrlib.bootstrap.odp.scaledresiduals.DefaultOdpScaledResidualTriangle
import org.jreserve.jrlib.bootstrap.odp.scaledresiduals.OdpScaledResidualTriangleCorrection

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
class ScaledClaimResidualDelegate implements FunctionProvider {
    
    private MapUtil mapUtil = MapUtil.getInstance();
    
    void initFunctions(Script script, ExpandoMetaClass emc) {
        emc.residuals    << this.&residuals
        emc.adjust       << this.&adjust
        emc.exclude      << this.&exclude
    }
    
    OdpScaledResidualTriangle residuals(OdpResidualScale scales) {
        return residuals(scales, false)
    }
    
    OdpScaledResidualTriangle residuals(OdpResidualScale scales, boolean adjust) {
        return adjust?
            new AdjustedOdpScaledResidualTriangle(scales) :
            new DefaultOdpScaledResidualTriangle(scales)
    }
    
    OdpScaledResidualTriangle adjust(OdpScaledResidualTriangle residuals) {
        return new AdjustedOdpScaledResidualTriangle(residuals)
    }
    
    OdpScaledResidualTriangle exclude(OdpScaledResidualTriangle residuals, Map map) {
        int accident = mapUtil.getAccident(map)
        int development = mapUtil.getDevelopment(map)
        return this.exclude(residuals, accident, development)
    }
    
    OdpScaledResidualTriangle exclude(OdpScaledResidualTriangle residuals, int accident, int development) {
        return new OdpScaledResidualTriangleCorrection(residuals, accident, development, Double.NaN)
    }
	
    OdpScaledResidualTriangle residuals(OdpResidualScale scales, Closure cl) {
        Builder builder = new Builder(scales, this)
        cl.delegate = builder
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl()
        return builder.residuals
    }
    
    private class Builder {
        private OdpScaledResidualTriangle residuals
        private ScaledClaimResidualDelegate delegate
        
        Builder(OdpResidualScale scales, ScaledClaimResidualDelegate delegate) {
            this.residuals = new DefaultOdpScaledResidualTriangle(scales)
            this.delegate = delegate
        }

        void adjust() {
            residuals = delegate.adjust(residuals)
        }
        
        void exclude(int accident, int development) {
            residuals = delegate.exclude(residuals, accident, development)
        }
        
        void exclude(Map map) {
            residuals = delegate.exclude(residuals, map)            
        }
    }
}