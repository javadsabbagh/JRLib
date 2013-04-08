package org.jrlib.linkratio.standarderror;

import org.jrlib.Copyable;
import org.jrlib.linkratio.LinkRatio;
import org.jrlib.linkratio.scale.LinkRatioScale;
import org.jrlib.triangle.claim.ClaimTriangle;
import org.jrlib.triangle.factor.FactorTriangle;
import org.jrlib.vector.Vector;

/**
 * Calculates the standard error of a link-ratio for a given development period.
 * 
 * @author Peter Decsi
 * @version 1.0
 */
public interface LinkRatioSE extends Vector, Copyable<LinkRatioSE> {
    
    /**
     * Returns Mack's sigma parameters, used for the calculation.
     */
    public LinkRatioScale getSourceLRScales();
    
    /**
     * Returns the LinkRatios used for this calculation.
     */
    public LinkRatio getSourceLinkRatios();
    
    /**
     * Returns the development factors used for this calculation.
     */
    public FactorTriangle getSourceFactors();
    
    /**
     * Returns the claims used for this calculation.
     */
    public ClaimTriangle getSourceTriangle();
}
