package pl.edu.agh;

import android.test.InstrumentationTestRunner;
import android.test.suitebuilder.TestSuiteBuilder;
import junit.framework.TestSuite;

/**
 * Created by Magda on 2014-12-12.
 */
public class TourTripMobileInstrumentationTestRunner extends InstrumentationTestRunner {

	@Override
	public TestSuite getTestSuite() {
//		return super.getTestSuite();
		return new TestSuiteBuilder(getClass()).includeAllPackagesUnderHere().build();
	}

	@Override
	public TestSuite getAllTests() {
//		return super.getAllTests();
		return getTestSuite();
	}
}
