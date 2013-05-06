package org.jreserve.jrlib.triangle.smoothing;

import org.jreserve.jrlib.vector.smoothing.HarmonicMovingAverageMethod;

/**
 * Smoothes the input by replacing the values by
 * their harmonic moving average.
 * 
 * If `l` is the length of the moving average then the smoothed 
 * value s[i] is:
 *      s[i] = 1/(1/x[i-l+1]+ ...+ 1/x[l]) , for i %gt;= l-1, and
 *      s[i] = x[i]                        , for i &lt; l-1
 * where `x[i]` is the input value at the ith position.
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class HarmonicMovingAverage extends AbstractVectorSmoothing {

    /**
     * Creates an instance for the given cells and length for the moving 
     * average.
     * 
     * @throws NullPointerException if `cells` or one of it's element is null.
     * @throws IllegalArgumentException if `length` is less then 1.
     */
    public HarmonicMovingAverage(SmoothingCell[] cells, int length) {
        super(cells, new HarmonicMovingAverageMethod(length));
    }
}
