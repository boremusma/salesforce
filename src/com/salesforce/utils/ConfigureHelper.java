package com.salesforce.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * ConfigureHelper class contains method to read local configuration files.
 * 
 * @author Bo Ma
 * @since 2015-09-05
 * 
 * @version 1.0
 * 
 */
public class ConfigureHelper {
	private static Logger logger = Logger.getLogger(ConfigureHelper.class);

	/**
	 * Load all properties from configuration file to memory
	 * 
	 * @param filePath
	 *            The file path under config/ directory
	 * @return Property The loaded property file
	 */
	public static Properties loadProperties(String filePath) {
		InputStream input = null;
		Properties prop = new Properties();

		try {
			// load the config file into property object
			input = new FileInputStream("config/" + filePath);
			prop.load(input);
			logger.info("config/" + filePath + " is loaded.");

		} catch (FileNotFoundException e) {
			// catch exception if there is no such file, it's ok since the
			// config file is not mandatory
			logger.warn("config/" + filePath + " does NOT exist!\n");
		} catch (IOException e) {
			// catch exception if the config file cannot be read, provide error
			// log
			logger.error("Fail to open: config/" + filePath, e);
		} finally {
			try {
				// close the config file
				if (input != null) {
					input.close();
					logger.info("config/" + filePath + " is closed.");
				}
			} catch (IOException e) {
				// catch exception if the config file cannot be closed, provide
				// error log
				logger.error("Fail to close: config/" + filePath, e);
			}
		}

		return prop;
	}

}