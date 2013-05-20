package org.jreserve.grscript

import org.jreserve.grscript.FunctionProvider
import org.jreserve.grscript.input.CsvReader

/**
 *
 * @author Peter Decsi
 * @version 1.0
 */
class TestDataDelegate implements FunctionProvider {
    
    private final static String APC_PAID = "apc_paid.csv";
    private final static String APC_INCURRED = "apc_incurred.csv";
    private final static String APC_EXPOSURE = "apc_exposure.csv";
    private final static String APC_NOC = "apc_noc.csv";
    private final static String MCL_PAID = "mcl_paid.csv";
    private final static String MCL_INCURRED = "mcl_incurred.csv";
    private final static String DATA_HOME = "/org/jreserve/grscript/input/";
    
    private static String getPath(String dataName) {
        File file = new File(DATA_HOME + dataName);
        return file.getAbsolutePath();
    }
    
    private CsvReader reader
    
    TestDataDelegate() {
        reader = new CsvReader()
        reader.setColumnSeparator(',')
    }
    
    @Override
    void initFunctions(Script script, ExpandoMetaClass emc) {
        emc.apcPaid     = this.&apcPaid
        emc.apcIncurred = this.&apcIncurred
        emc.apcNoC      = this.&apcNoC
        emc.apcExposure = this.&apcExposure
        emc.mclPaid     = this.&mclPaid
        emc.mclIncurred = this.&mclIncurred
    }
    
    def apcPaid() {
        load(APC_PAID)
    }
    
    private double[][] load(String name) {
        InputStreamReader isReader = null
        
        try {
            InputStream is = TestDataDelegate.class.getResourceAsStream(DATA_HOME+name)
            isReader = new InputStreamReader(is)
            return reader.read(isReader)
        } finally {
            if(isReader != null)
                isReader.close()
        }
    }
    
    def apcIncurred() {
        load(APC_INCURRED)
    }
    
    def apcNoC() {
        load(APC_NOC)
    }
    
    def apcExposure() {
        double[][] exposure = load(APC_EXPOSURE)
        exposure[0]
    }
    
    def mclPaid() {
        load(MCL_PAID)
    }
    
    def mclIncurred() {
        load(MCL_INCURRED)
    }
}

