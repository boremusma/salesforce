package com.salesforce.tests;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.salesforce.base.BaseTestCase;
import com.salesforce.utils.WebDriverGenerator;

/**
 * The Test Suite to call test cases
 * 
 * @author Bo Ma
 * @since 2015-09-06
 * @version 1.0
 * 
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({ MainTest.class })
public class TestSuiteMain {

	private static Logger logger = Logger.getLogger(TestSuiteMain.class);

	/**
	 * Tear down test environment: Close WebDriver
	 **/
	@AfterClass
	public static void tearDown() throws Exception {

		// 1. Close WebDriver
		WebDriverGenerator.tearDownLocalWebDriver();

		logger.info("Total test case running:" + BaseTestCase.getCount());
		logger.info("Total Failed:" + BaseTestCase.getFailCount());
		logger.info("Total Succeeded:" + BaseTestCase.getPassCount());

	}
}
