package com.salesforce.utils;

import java.util.Properties;

import org.apache.log4j.Logger;

public class TestBaseInfo {
	private static Logger logger = Logger.getLogger(TestBaseInfo.class);

	private static String timeout = "";
	private static String baseURL = "";
	private static String userName = "";
	private static String password = "";

	static {
		Properties prop = ConfigureHelper.loadProperties("config.testbaseinfo");
		timeout = prop.getProperty("timeout");
		baseURL = prop.getProperty("baseURL");
		userName = prop.getProperty("userName");
		password = prop.getProperty("password");
		logger.info("Read the config.testbaseinfo file.");
	}

	/**
	 * Get the Explicit wait time for script time out.
	 * 
	 * @return timeout in seconds
	 */
	public static String getTimeout() {
		return timeout;
	}

	/**
	 * Get the base URL to start all the test
	 * 
	 * @return the base URL to start with
	 */
	public static String getBaseURL() {
		return baseURL;
	}

	/**
	 * Get global test user username
	 * 
	 * @return Test user username
	 */
	public static String getUserName() {
		return userName;
	}

	/**
	 * Get the password for the test user
	 * 
	 * @return Test user password
	 */
	public static String getPassword() {
		return password;
	}
}
