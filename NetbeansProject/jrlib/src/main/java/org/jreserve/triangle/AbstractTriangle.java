package org.jreserve.triangle;

import org.jreserve.AbstractCalculationData;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
public abstract class AbstractTriangle<T extends Triangle> extends AbstractCalculationData<T> implements Triangle {

    protected AbstractTriangle(T source) {
        super(source);
    }

    protected AbstractTriangle() {
    }


    protected boolean withinBounds(int accident) {
        return accident >= 0 &&
               accident < getAccidentCount();
    }
    
    @Override
    public double getValue(Cell cell) {
        return getValue(cell.getAccident(), cell.getDevelopment());
    }
    
    protected boolean withinBounds(int accident, int development) {
        return withinBounds(accident) &&
               development >= 0 &&
               development < getDevelopmentCount(accident);
    }

    @Override
    public double[][] toArray() {
        int accidents = getAccidentCount();
        double[][] result = new double[accidents][];
        for(int a=0; a<accidents; a++)
            result[a] = toArray(a);
        return result;
    }
    
    private double[] toArray(int accident) {
        int devs = getDevelopmentCount(accident);
        double[] result = new double[devs];
        for(int d=0; d<devs; d++)
            result[d] = getValue(accident, d);
        return result;
    }    
}
