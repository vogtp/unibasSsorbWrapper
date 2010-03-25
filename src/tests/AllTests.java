package tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for SSORB");
		//$JUnit-BEGIN$
		suite.addTestSuite(ServiceTest.class);
		suite.addTestSuite(DomainHelperTest.class);
		suite.addTestSuite(ModelAccessTest.class);
		suite.addTestSuite(RelationHelperTest.class);
		//$JUnit-END$
		return suite;
	}

}
