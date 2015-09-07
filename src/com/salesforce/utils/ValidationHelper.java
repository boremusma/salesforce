package com.salesforce.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ValidationHelper {

	static Logger logger = Logger.getLogger(ValidationHelper.class);
	static int timeout = Integer.parseInt(TestBaseInfo.getTimeout());

	/**
	 * An expectation for checking that an element is present on the DOM of a
	 * page. This does not necessarily mean that the element is visible.
	 * 
	 * @param driver
	 * @param by
	 * @param timeout
	 * @return true if the element is present in timeout scope, false if not
	 */
	public static boolean waitForElementPresent(WebDriver driver, By by) {

		WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			return true;
		} catch (Exception ElementNotFoundException) {
			logger.warn("Element with xpath " + by.toString() + " is not present in " + timeout + "seconds.");
			return false;
		}
	}

	/**
	 * An expectation for checking that an element, known to be present on the
	 * DOM of a page, is visible. Visibility means that the element is not only
	 * displayed but also has a height and width that is greater than 0.
	 * 
	 * @param driver
	 * @param by
	 * @param timeout
	 * @return true if the element becomes visible in the timeout scope, false
	 *         if not
	 */
	public static boolean waitForElementVisible(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return true;
		} catch (Exception ElementNotFoundException) {
			logger.warn("Element with xpath " + by.toString() + " is not visible in " + timeout + "seconds.");
			return false;
		}
	}

	/**
	 * An expectation for checking that an element, known to be present on the
	 * DOM of a page, is invisible. Visibility means that the element is not
	 * only displayed but also has a height and width that is greater than 0.
	 * 
	 * @param driver
	 * @param by
	 * @param timeout
	 * @return true if the element becomes visible in the timeout, false if not
	 */
	public static boolean waitForElementInvisible(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
			return true;
		} catch (Exception ElementNotFoundException) {
			logger.warn(
					"Element with xpath " + by.toString() + " does not become invisible in " + timeout + "seconds.");
			return false;
		}
	}

	/**
	 * Check if the driver has certain URL segment in the current url
	 * 
	 * @param driver
	 * @param regex
	 * @return true if the current url contains given segment, false if not
	 */
	public static boolean checkUrl(WebDriver driver, String regex) {
		boolean rtn = false;
		String url = driver.getCurrentUrl();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(url);
		if (m.find()) {
			rtn = true;
		}
		return rtn;
	}

}
