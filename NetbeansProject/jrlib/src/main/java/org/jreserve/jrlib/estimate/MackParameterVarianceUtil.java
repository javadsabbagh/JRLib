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
package org.jreserve.jrlib.estimate;

import org.jreserve.jrlib.linkratio.LinkRatio;
import org.jreserve.jrlib.linkratio.standarderror.LinkRatioSE;
import org.jreserve.jrlib.triangle.claim.ClaimTriangle;

/**
 * Utility class to calculate the parameter variance, according to Mack's method.
 * 
 * @see "Mack [1993]: Measuring the Variability of Chain Ladder Reserve Estimates"
 * @author Peter Decsi
 * @version 1.0
 */
class MackParameterVarianceUtil {

    private LinkRatioSE lrSE;
    private double[][] estimates;
    private LinkRatio lrs;
    private ClaimTriangle cik;
    private int accidents;
    
    private double[] paramSDs;
    private double paramSD;

    MackParameterVarianceUtil(LinkRatioSE lrSE, double[][] estimates) {
        initState(lrSE, estimates);
        calculate();
        clearState();
    }
    
    private void initState(LinkRatioSE lrSE, double[][] estimates) {
        this.lrSE = lrSE;
        this.lrs = lrSE.getSourceLinkRatios();
        this.cik = lrs.getSourceTriangle();
        this.accidents = cik.getAccidentCount();
        this.estimates = estimates;
        this.paramSDs = new double[accidents];
        this.paramSD = 0d;
    }
    
    private void calculate() {
        double[] pvm = calculatePVM();
        int estimateDev = lrSE.getLength();
        
        for(int a=0; a<accidents; a++) {
            for(int d=0; d<accidents; d++) {
                double m = (a < d)? pvm[a] : pvm[d];
                double v = estimates[a][estimateDev] * estimates[d][estimateDev] * m;
                paramSD += v;
            }
            
            double sd = pvm[a];
            paramSDs[a] = (sd < 0d)? Double.NaN : estimates[a][estimateDev] * Math.sqrt(sd);
        }
        
        paramSD = (paramSD < 0d)? Double.NaN : Math.sqrt(paramSD);
    }
    
    private double[] calculatePVM() {
        double sum = 0d;
        int lastIndex = lrSE.getLength();
        double[] pvm = new double[accidents];
        
        for(int a=0; a<accidents; a++) {
            int firstIndex = cik.getDevelopmentCount(a) - 1;
            for(int d=firstIndex; d<lastIndex; d++) {
                double lr = lrs.getValue(d);
                double se = lrSE.getValue(d);
                sum += (lr == 0d)? Double.NaN : Math.pow(se / lr, 2);
            }
            lastIndex = firstIndex;
            pvm[a] = sum;
        }
        
        return pvm;
    }
    
    private void clearState() {
        this.lrSE = null;
        this.lrs = null;
        this.cik = null;
        this.estimates = null;
    }
    
    double[] getParameterSDs() {
        return paramSDs;
    }
    
    double getParameterSD() {
        return paramSD;
    }
}