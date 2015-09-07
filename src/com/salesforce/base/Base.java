package com.salesforce.base;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.salesforce.utils.ConfigureHelper;

/**
 * The abstract Base class contains Logger and Configuration file read help
 * method to be used accross pages and test cases
 * 
 * @author Bo Ma
 *
 */
public abstract class Base {
	protected Logger logger = Logger.getLogger(this.getClass());
	protected String className = this.getClass().getCanonicalName();
	protected String configFileName = className.substring(className.lastIndexOf("salesforce.") + 11).trim();
	private Properties prop = ConfigureHelper.loadProperties(getConfigFileName());

	/**
	 * Helper method to load property file automatically to the Base
	 * 
	 * @return
	 */
	private String getConfigFileName() {
		int index = configFileName.lastIndexOf(".");
		String str = new StringBuilder(configFileName).replace(index, index + 1, "/").toString();
		return str;
	}

	/**
	 * Get property for each test case: property name is the same as the test
	 * case class name;
	 * 
	 * @param name
	 * @return
	 */
	public String getPropValue(String name) {
		String rtn = prop.getProperty(name.trim());
		logger.info(name + " = " + rtn);
		return rtn;
	}

}
