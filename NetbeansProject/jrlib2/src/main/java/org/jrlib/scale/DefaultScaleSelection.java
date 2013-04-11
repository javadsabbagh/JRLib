package org.jrlib.scale;

import org.jrlib.triangle.TriangleUtil;
import org.jrlib.util.AbstractMethodSelection;

/**
 * DefaultLinkRatioSelection allows to use different 
 * {@link ScaleEstimator ScaleEstimators} for different development
 * periods.
 *
 * @author Peter Decsi
 * @version 1.0
 */
public class DefaultScaleSelection<T extends ScaleInput>extends AbstractMethodSelection<Scale<T>, ScaleEstimator<T>> implements ScaleSelection<T> {
    
    private int length;
    private double[] values;
    
    /**
     * Creates an instance for the given source, and method.
     * 
     * @see DefaultScaleCalculator
     * @see #DefaultScaleSelection(Scale, ScaleEstimator) 
     * @throws NullPointerException if `source` or `method` is null.
     */
    public DefaultScaleSelection(T source, ScaleEstimator<T> method) {
        this(new DefaultScaleCalculator<T>(source), method);
    }
    
    /**
     * Creates an instance for the given source and method, with the 
     * original length taken from the source.
     * 
     * @see #DefaultScaleSelection(Scale, int, ScaleEstimator) 
     * @throws NullPointerException if `source` or `method` is null.
     */
    public DefaultScaleSelection(Scale<T> source, ScaleEstimator<T> method) {
        this(source, source.getLength(), method);
    }
    
    /**
     * Creates an instance for the given source, method and length.
     * 
     * @see #DefaultScaleSelection(Scale, int, ScaleEstimator) 
     * @throws NullPointerException if `source` or `method` is null.
     */
    public DefaultScaleSelection(Scale<T> source, int length, ScaleEstimator<T> method) {
        super(source, method);
        this.length = length<0? 0 : length;
        doRecalculate();
    }
    
    /**
     * Contructor that copies it's state from the supplied
     * original instance.
     */
    protected DefaultScaleSelection(DefaultScaleSelection<T> original) {
        super(original.source.copy(), original);
        length = original.length;
        if(length > 0)
            values = TriangleUtil.copy(original.values);
    }

    @Override
    public T getSourceInput() {
        return source.getSourceInput();
    }
    
    @Override
    public int getLength() {
        return length;
    }
    
    /**
     * Sets the number of development periods. If the 
     * input value is les then 0, then 0 will be used insted.
     * 
     * Calling this method recalculates this instance and 
     * fires a change event.
     */
    protected void setLength(int length) {
        this.length = (length<0)? 0 : length;
        doRecalculate();
        fireChange();
    }
    
    @Override
    protected void recalculateLayer() {
        doRecalculate();
    }
    
    private void doRecalculate() {
        length = (source==null)? 0 : source.getLength();
        super.fitMethods();
        values = super.getFittedValues(length);
    }
    
    @Override
    public double getValue(int development) {
        if(development < 0)
            return Double.NaN;
        if(development < length)
            return values[development];
        return 1d;
    }
    
    @Override
    public double[] toArray() {
        return TriangleUtil.copy(values);
    }
    
    @Override
    public DefaultScaleSelection<T> copy() {
        return new DefaultScaleSelection(this);
    }
}