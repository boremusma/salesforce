package com.salesforce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.salesforce.base.BasePage;
import com.salesforce.utils.ExecutionHelper;
import com.salesforce.utils.TestBaseInfo;
import com.salesforce.utils.ValidationHelper;

/**
 * Salesforce Enterprise Login Page.
 * 
 * @author Bo Ma
 *
 */
public class LoginPage extends BasePage {

	// Define the By for the element on page
	By userNameField = By.xpath(this.getPropValue("userNameField"));
	By passwordField = By.xpath(this.getPropValue("passwordField"));
	By submitBtn = By.xpath(this.getPropValue("submitBtn"));
	By errorMsg = By.xpath(this.getPropValue("errorMsg"));
	String baseUrl = this.getPropValue("baseUrl");

	/**
	 * The default constructor. Launch Login Page with default local driver
	 */
	public LoginPage() {
	}

	/**
	 * Constructor that allow passing in another driver. Check Url to confirm on
	 * login page.
	 * 
	 * @param myDriver
	 */
	public LoginPage(WebDriver myDriver) {
		super(myDriver);
		if (!isOnLoginPage())
			throw new IllegalStateException("This is not the login page");
	}

	/**
	 * Start LoginPage by get Login Page Url.
	 */
	public void typeLoginPageUrl() {
		String loginUrl = TestBaseInfo.getBaseURL();
		logger.info("Direct to Login Url " + loginUrl);
		driver.get(loginUrl);
	}

	/**
	 * Fill in the username field on Login page
	 * 
	 * @param userName
	 *            Login username
	 */
	public void fillInUserNameField(String userName) {
		logger.info("Fill in Login Page UserName field with value " + userName);
		ExecutionHelper.typeWhenReady(driver, userNameField, userName, true);
	}

	/**
	 * Fill in the password field on Login page
	 * 
	 * @param password
	 *            Login password
	 */
	public void fillInPasswordField(String password) {
		logger.info("Fill in Login Page Password field with value " + password);
		ExecutionHelper.typeWhenReady(driver, passwordField, password, true);
	}

	/**
	 * Click submit Login button and expect successful login
	 * 
	 * @return The new Home page direct to
	 */
	public HomePage submitLogin() {
		logger.info("Click Submit button and expect to jump to HomePage.");
		clickLoginBtn();
		return new HomePage(this.driver);
	}

	/**
	 * Click submit Login button and expect unsuccessful login
	 * 
	 * @return The current Login page
	 */
	public LoginPage submitInvalidLogin() {
		logger.info("Click Submit button and expect to stay on Login Page.");
		clickLoginBtn();
		return new LoginPage(this.driver);
	}

	/**
	 * Check if login error message display on Login page
	 * 
	 * @return true if error message displayed, false if no error message
	 *         displayed
	 */
	public boolean isErrorMsgDisplay() {
		return ValidationHelper.waitForElementVisible(driver, errorMsg);
	}

	/**
	 * Composed Login action, fill-in username, password and click submit
	 * button. Expect successful login
	 * 
	 * @param userName
	 *            Valid username
	 * @param password
	 *            Valid password to the username
	 * @return The new Home page after login
	 */
	public HomePage login(String userName, String password) {
		typeLoginPageUrl();
		fillInUserNameField(userName);
		fillInPasswordField(password);
		return submitLogin();
	}

	/**
	 * The help method to click submit button
	 */
	private void clickLoginBtn() {
		ExecutionHelper.clickWhenReady(driver, submitBtn);

	}

	/**
	 * Check if driver is on Login page by checking url
	 * 
	 * @return true if the url is Login page url, false if not
	 */
	private boolean isOnLoginPage() {
		return ValidationHelper.checkUrl(driver, baseUrl);
	}
}
