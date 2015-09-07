package com.salesforce.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * The ExecutionHelper is the helper class that contains the customized methods
 * for basic Selenium Actions. All the actions in ExecutionHelper class will do
 * explicit waiting for the target elements. Make sure to use actions in
 * ExecutionHelper class instead of the original API to avoid DOM related issue.
 * 
 * 
 * @author Bo Ma
 * @since 2015-09-05
 */
public class ExecutionHelper {
	static Logger logger = Logger.getLogger(ExecutionHelper.class);
	static int timeout = Integer.parseInt(TestBaseInfo.getTimeout());

	/**
	 * An expectation for checking that an element is present on the DOM of a
	 * page. This does not necessarily mean that the element is visible.
	 * 
	 * @param driver
	 * @param by
	 * @param timeout
	 * @return
	 */
	public static WebElement waitForElementPresent(WebDriver driver, By by) {

		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		return element;
	}

	/**
	 * An expectation for checking that an element, known to be present on the
	 * DOM of a page, is visible. Visibility means that the element is not only
	 * displayed but also has a height and width that is greater than 0.
	 * 
	 * @param driver
	 * @param by
	 * @param timeout
	 * @return the element waiting for
	 */
	public static WebElement waitForElementVisible(WebDriver driver, By by) {

		WebDriverWait wait = new WebDriverWait(driver, timeout);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		return element;
	}

	/**
	 * Click on the element when it is ready.
	 * 
	 * @param driver
	 * @param by
	 * @param timeout
	 */
	public static void clickWhenReady(WebDriver driver, By by) {

		WebDriverWait wait = new WebDriverWait(driver, timeout);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
		element.click();
	}

	/**
	 * Select by option text when it is ready.
	 * 
	 * @param driver
	 * @param by
	 * @param text
	 */
	public static void selectWhenReady(WebDriver driver, By by, String text) {
		WebElement element = waitForElementPresent(driver, by);
		new Select(element).selectByVisibleText(text);
	}

	/**
	 * Type into input when the input is ready
	 * 
	 * @param driver
	 * @param by
	 * @param value
	 */
	public static void typeWhenReady(WebDriver driver, By by, String value, boolean toClear) {
		if (toClear)
			waitForElementPresent(driver, by).clear();

		waitForElementPresent(driver, by).sendKeys(value);
	}

	/**
	 * Accept or dismiss alert message
	 * 
	 * @param driver
	 * @param accept
	 *            true if accept the alert, false if dismiss
	 * @param timeout
	 */
	public static void manageAlert(WebDriver driver, boolean accept) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert = driver.switchTo().alert();
		if (accept) {
			alert.accept();
		} else {
			alert.dismiss();
		}
	}

	/**
	 * Check If any alert is present
	 * 
	 * @param driver
	 * @return
	 */
	public static boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}
}
