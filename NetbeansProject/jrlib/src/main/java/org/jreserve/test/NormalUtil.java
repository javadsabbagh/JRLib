package org.jreserve.test;

import static java.lang.Math.*;

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
class NormalUtil {
    
    private final static double sqrt2PI = sqrt(2d * PI);
    private final static double DELTA = 0.0000000001;
    private final static double LOWER_BOUND = -8d;
    private final static double UPPER_BOUND = 8d;
    
    static double normDF(double x) {
        return exp(-x * x / 2d) / sqrt2PI;
    }
    
    static double normDF(double x, double mean, double sigma) {
        return normDF((x - mean) / sigma) / sigma;
    }
    
    static double normCDF(double x) {
        if(x < LOWER_BOUND) return 0d;
        if(x > UPPER_BOUND) return 1d;
        double s = x;
        double t = 0;
        double b = x;
        double q = x * x;
        double i=1;
        while(s != t)  {
            t = s;
            i += 2d;
            b *= (q/i);
            s = t+b;
        }
        return 0.5 + s * exp(-0.5 * q - 0.91893853320467274178);
    }
    
    static double normCDF(double x, double mean, double sigma) {
        return normCDF((x-mean) / sigma);
    }
    
    static double invNormCDF(double x) {
        return invNormCDF(x, DELTA, LOWER_BOUND, UPPER_BOUND);
    }
    
    static double invNormCDF(double x, double delta, double min, double max) {
        double middle = min + (max - min) / 2d;
        if(abs(max - min) < delta)
            return middle;
        return normCDF(middle) > x? 
                invNormCDF(x, delta, min, middle) :
                invNormCDF(x, delta, middle, max);
    }
    
    static double invNormCDF(double x, double mean, double sigma) {
        double result = invNormCDF(x);
        return result * sigma + mean;
    }
    
    private NormalUtil() {
    }
}
