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
package org.jreserve.grscript.jrlib

import org.jreserve.grscript.FunctionProvider
import org.jreserve.jrlib.scale.MinMaxScaleEstimator
import org.jreserve.jrlib.scale.ScaleExtrapolation
import org.jreserve.jrlib.scale.UserInputScaleEstimator
import org.jreserve.jrlib.scale.ScaleEstimator
import org.jreserve.jrlib.claimratio.scale.ClaimRatioScale
import org.jreserve.jrlib.claimratio.scale.ClaimRatioScaleInput
import org.jreserve.jrlib.claimratio.scale.DefaultClaimRatioScaleSelection
import org.jreserve.jrlib.claimratio.scale.SimpleClaimRatioScale
import org.jreserve.jrlib.claimratio.ClaimRatio
import org.jreserve.grscript.AbstractDelegate

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
class ClaimRatioScaleDelegate extends AbstractDelegate {
    
    private ScaleEstimatorFactory estimators = ScaleEstimatorFactory.getInstance()
    
    void initFunctions(Script script, ExpandoMetaClass emc) {
        super.initFunctions(script, emc)
        emc.scale    << this.&scale
    }
    
    ClaimRatioScale scale(ClaimRatio crs) {
        return scale(crs, "minmax")
    }
    
    ClaimRatioScale scale(ClaimRatio crs, String type) {
        ScaleEstimator estimator = estimators.createEstimator(type);
        return new SimpleClaimRatioScale(crs, estimator)
    }
    
    ClaimRatioScale scale(ClaimRatio lrs, Closure cl) {
        Builder builder = new Builder(lrs)
        cl.delegate = builder
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl()
        return builder.scales
    }
	
    class Builder extends AbstractMethodSelectionBuilder<ScaleEstimator> {
    
        DefaultClaimRatioScaleSelection scales;
    
        Builder(ClaimRatio crs) {
            scales = new DefaultClaimRatioScaleSelection(crs)
        }
    
        void minMax(int index) {
            ScaleEstimator estimator = getCachedMethod(MinMaxScaleEstimator.class) {new MinMaxScaleEstimator()}
            scales.setMethod(estimator, index)
        }
    
        void minMax(Collection indices) {
            indices.each(){minMax(it)}
        }
    
        void logLinear(int index) {
            ScaleEstimator estimator = getCachedMethod(ScaleExtrapolation.class) {new ScaleExtrapolation()}
            scales.setMethod(estimator, index)
        }
    
        void logLinear(Collection indices) {
            indices.each(){logLinear(it)}
        }
    
        void fixed(int index, double value) {
            ScaleEstimator estimator = getCachedMethod(UserInputScaleEstimator.class) {new UserInputScaleEstimator()}
            ((UserInputScaleEstimator)estimator).setValue(index, value)
            scales.setMethod(estimator, index)
        }
    
        void fixed(Map map) {
            map.each {index, value -> fixed(index, value)}
        }
    }
}
