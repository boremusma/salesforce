package com.salesforce.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * WebDriverGenerator Class applied Singleton Design Pattern to ensure that only
 * one driver object would be live at the same time. Instantiate this class
 * would construct an default webdriver according to user's configuration. The
 * reason to keep settings in a config file is to decouple system parameters
 * from this class.
 * 
 * For the test cases which involved multiple sessions, user can generate one
 * additional driver on Selenium Grid.
 * 
 * @author Bo Ma
 * @since 2015-09-05
 */
public class WebDriverGenerator {

	// A logger wrapped in the webdriver generator level, the logger will follow
	// this class name.
	private static Logger logger = Logger.getLogger(WebDriverGenerator.class);

	// Web driver system info
	private static String gridURL;
	private static String webDriverType;
	private static int timeout;

	// default driver
	private static WebDriver localDriver;
	private static WebDriver gridDriver;

	/**
	 * Static block will read the webdrivergenerator configuration file to set /
	 * up driver config. Timeout, Grid URL and Driver Type will be loaded
	 * /automatically from the property file config/config.webdrivergenerator.
	 **/
	static {
		Properties prop = ConfigureHelper.loadProperties("config.webdrivergenerator");

		gridURL = prop.getProperty("gridURL");
		webDriverType = prop.getProperty("webDriverType");
		// if webdriver type is not given, launch Firefox
		if (webDriverType == null)
			webDriverType = "Firefox";
		timeout = Integer.valueOf(prop.getProperty("webDriverTimeout"));
		logger.info("webDriver info is read from config.webdriver.");
	}

	/**
	 * If the localDriver has not be instantiated, instantiate it, then return
	 * the local driver
	 * 
	 * @return Local WebDriver config from config.webdrivergenerator file
	 */
	public static WebDriver getLocalDriver() {
		if (localDriver == null)
			launchLocalDriver();
		return localDriver;
	}

	/**
	 * If the gridDriver has not be instantiated, instantiate it, then return
	 * the grid driver
	 * 
	 * @param webDriverType
	 *            The type of WebDriver to launch on Selenium Grid, eg. 'IE',
	 *            'Chrome', 'FireFox'. For all other string, a FireFox driver
	 *            would be launched.
	 * @return Local WebDriver config from config.webdrivergenerator file
	 */
	public static WebDriver getGridDriver(String webDriverType) {
		if (gridDriver == null)
			launchGridDriver(webDriverType);
		return gridDriver;

	}

	/**
	 * Close the local WebDriver
	 */
	public static void tearDownLocalWebDriver() {
		if (localDriver != null) {
			localDriver.quit();
			localDriver = null;
		}
		logger.info("Local webDriver is closed.");
	}

	/**
	 * Close the Grid WebDriver
	 */
	public static void tearDownGridWebDriver() {
		if (gridDriver != null) {
			gridDriver.quit();
			gridDriver = null;
		}
		logger.info("Grid webDriver is closed.");
	}

	/**
	 * Lauch local WebDriver according to the config file
	 * config/config.webdrivergenerator
	 * 
	 * @return WebDriver with given timeout and browser type
	 */
	private static void launchLocalDriver() {
		logger.info("Try to launch Local Web Driver: " + webDriverType);

		try {
			// set up local driver
			if (webDriverType.equalsIgnoreCase("IE")) {
				localDriver = new InternetExplorerDriver();
			} else if (webDriverType.equalsIgnoreCase("Chrome")) {
				localDriver = new ChromeDriver();
			} else {
				localDriver = new FirefoxDriver();
			}
			setupDriverTimeout(localDriver);

		} catch (Exception e) {
			logger.fatal("Failed to setup local WebDriver! driver type: " + webDriverType);
		}
	}

	/**
	 * Lauch grid WebDriver according to the config file
	 * config/config.webdrivergenerator
	 * 
	 * @param webDriverType
	 *            The browser time for the Selenium Grid WebDriver, eg. 'IE',
	 *            'Chrome', 'Firefox'. All the other input will set up a Firefox
	 *            Driver.
	 * 
	 * @return WebDriver with given timeout , browser type and Grid URL
	 */
	private static void launchGridDriver(String webDriverType) {
		logger.info("Try to launch Web Driver: " + gridURL + " " + webDriverType);

		// if the webdriver type is null, launch Firefox
		if (webDriverType == null)
			webDriverType = "Firefox";

		if (gridURL != null) {
			try {
				// set up driver on selenium grid
				if (webDriverType.equalsIgnoreCase("Chrome")) {
					gridDriver = new RemoteWebDriver(new URL(gridURL), DesiredCapabilities.chrome());
				} else if (webDriverType.equalsIgnoreCase("IE")) {
					gridDriver = new RemoteWebDriver(new URL(gridURL), DesiredCapabilities.internetExplorer());
				} else {
					gridDriver = new RemoteWebDriver(new URL(gridURL), DesiredCapabilities.firefox());
				}
				setupDriverTimeout(gridDriver);

			} catch (MalformedURLException e) {
				logger.fatal("Failed to launch RemoteWebDriver: on selenium grid " + gridURL + ";   driver type: "
						+ webDriverType);
			}
		}
	}

	/**
	 * Set up the driver implicity wait
	 * 
	 * @param driver
	 */
	private static void setupDriverTimeout(WebDriver driver) {
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		logger.info("WebDriver " + driver + " is set up.\n timeout is set up for " + timeout + " seconds.");
	}
}
