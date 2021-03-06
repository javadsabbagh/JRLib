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
package org.jreserve.jrlib;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jreserve.jrlib.linkratio.LinkRatio;
import org.jreserve.jrlib.linkratio.SimpleLinkRatio;
import org.jreserve.jrlib.triangle.claim.ClaimTriangle;
import org.jreserve.jrlib.triangle.claim.CummulatedClaimTriangle;
import org.jreserve.jrlib.triangle.claim.InputClaimTriangle;
import org.jreserve.jrlib.triangle.factor.DevelopmentFactors;
import org.jreserve.jrlib.triangle.factor.FactorTriangle;

/**
 * This utility class provides easy access to the test data stored
 * in the "/org/jrlib/testdata" directory.
 * 
 * @author Peter Decsi
 * @version 1.0
 */
public class TestData {
    private final static String DIR = "/org/jreserve/jrlib/testdata/";
    public final static String INCURRED     = DIR + "apc_incurred.csv";
    public final static String PAID         = DIR + "apc_paid.csv";
    public final static String NoC          = DIR + "apc_noc.csv";
    public final static String EXPOSURE     = DIR + "apc_exposure.csv";
    public final static String MACK_DATA    = DIR + "mack_data.csv";
    public final static String MACK_DATA2   = DIR + "mack_data2.csv";
    public final static String MACK_DATA3   = DIR + "mack_data3.csv";
    public final static String Q_INCURRED   = DIR + "q_incurred.csv";
    public final static String Q_PAID       = DIR + "q_paid.csv";
    public final static String TAYLOR_ASHE  = DIR + "taylor_ashe.csv";
    public final static String MCL_INCURRED   = DIR + "mcl_incurred.csv";
    public final static String MCL_PAID       = DIR + "mcl_paid.csv";
    
    private final static Map<String, double[][]> CAHCE = new HashMap<String, double[][]>();
    
    public static LinkRatio getLinkRatio(String path) {
        return new SimpleLinkRatio(getDevelopmentFactors(path));
    }
    
    public static FactorTriangle getDevelopmentFactors(String path) {
        return new DevelopmentFactors(getCummulatedTriangle(path));
    }
    
    public static ClaimTriangle getCummulatedTriangle(String path) {
        return new CummulatedClaimTriangle(getTriangle(path));
    }
    
    public static ClaimTriangle getTriangle(String path) {
        return new InputClaimTriangle(getCachedMatrix(path));
    }
    
    public static double[][] getCachedMatrix(String path) {
        double[][] result = CAHCE.get(path);
        if(result == null) {
            result = readMatrix(path);
            CAHCE.put(path, result);
        }
        return result;
    }
    
    private static double[][] readMatrix(String path) {
        BufferedReader reader = null;
        try {
            List<double[]> values = new ArrayList<double[]>();
            reader = new BufferedReader(new InputStreamReader(TestData.class.getResourceAsStream(path)));
            String line;
            while((line = reader.readLine()) != null)
                if(!line.startsWith("#"))
                    values.add(lineToDouble(line));
            
            int size = values.size();
            double[][] result = new double[size][];
            for(int i=0; i<size; i++)
                result[i] = values.get(i);
            return result;
            
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if(reader != null)
                try {reader.close();} catch (Exception ex) {}
        }
    }
    
    private static double[] lineToDouble(String line) {
        String[] lineArr = line.split(",");
        int size = lineArr.length;
        double[] result = new double[size];
        
        for(int i=0; i<size; i++)
            result[i] = Double.parseDouble(lineArr[i].trim());
        return result;
    }
    
    public static double[] getCachedVector(String path) {
        return getCachedMatrix(path)[0];
    }
}
