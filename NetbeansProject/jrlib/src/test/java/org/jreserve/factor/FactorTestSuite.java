package org.jreserve.factor;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Peter Decsi
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    DevelopmentFactorsTest.class,
    MinLRMethodTest.class,
    MaxLRMethodTest.class,
    AverageLRMethodTest.class,
    WeightedAverageLRMethodTest.class,
    org.jreserve.factor.curve.CurvesTestSuite.class
})
public class FactorTestSuite {
}