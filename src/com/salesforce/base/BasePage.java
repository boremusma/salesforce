package com.salesforce.base;

import org.openqa.selenium.WebDriver;

import com.salesforce.utils.WebDriverGenerator;

/**
 * Abstract super PageObject, this class extends abstract class Base to get
 * logger and config settings
 * 
 * @author Bo Ma
 *
 */
public abstract class BasePage extends Base {
	protected WebDriver driver;

	/**
	 * Default constructor will launch a default local webdriver driver
	 */
	protected BasePage() {
		this.driver = WebDriverGenerator.getLocalDriver();
	}

	/**
	 * Constructor launches a customized driver
	 * 
	 * @param myDriver
	 */
	protected BasePage(WebDriver myDriver) {
		this.driver = myDriver;
	}

}
