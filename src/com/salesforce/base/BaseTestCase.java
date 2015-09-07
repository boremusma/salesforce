package com.salesforce.base;

import org.apache.log4j.Logger;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * The abstract super class for all the test cases to extend. This class
 * contains help method to read configuration file if there is one has the same
 * name as the test case. The Juint test watcher is override to provide test
 * pass/fail count.
 * 
 * @author Bo Ma
 *
 */
public abstract class BaseTestCase extends Base {
	protected Logger logger = Logger.getLogger(this.getClass());

	// Test Watch overwrite variables
	private static int count = 0;
	private static int passCount = 0;
	private static int failCount = 0;

	public static String getCount() {
		return String.valueOf(count);
	}

	public static String getPassCount() {
		return String.valueOf(passCount);
	}

	public static String getFailCount() {
		return String.valueOf(failCount);
	}

	@Rule
	public TestName name = new TestName();

	@Rule
	public TestWatcher watchman = new TestWatcher() {

		@Override
		protected void starting(Description d) {
			count++;
		}

		@Override
		protected void failed(Throwable e, Description d) {

			logger.error("FAILED: " + d.getClassName() + "." + d.getMethodName() + " \n Error Info:" + e.getMessage());

			failCount++;
		}

		@Override
		protected void succeeded(Description d) {

			logger.info("PASSED: " + d.getClassName() + "." + d.getMethodName());
			passCount++;
		}
	};

}
