package com.salesforce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.salesforce.base.BasePage;
import com.salesforce.utils.ExecutionHelper;
import com.salesforce.utils.ValidationHelper;

/**
 * THe Home page is the global page contains a Tab Set dropdown and a set of
 * tabs corresponding to the Tab Set dropdown selection
 * 
 * @author Bo Ma
 *
 */
public class HomePage extends BasePage {

	// Declare the Home page elements locator
	String homeUrlSegment = this.getPropValue("homeUrlSegment");
	By tabSetDropDownBtn = By.xpath(this.getPropValue("tabSetDropDownBtn"));
	By leadsTab = By.xpath(this.getPropValue("leadsTab"));
	By tabSetDropDownMenu = By.xpath(this.getPropValue("tabSetDropDownMenu"));

	/**
	 * The default constructor. Launch Home Page with default local driver
	 */
	public HomePage() {
	}

	/**
	 * Constructor that allow passing in another driver. Check if on Home page.
	 * 
	 * @param myDriver
	 *            Instantiate page with this driver
	 */
	public HomePage(WebDriver myDriver) {
		super(myDriver);
		if (!isOnHomePage())
			throw new IllegalStateException("This is not the Home page");
	}

	/********************************
	 * Tab Set Dropdown
	 ************************************/

	/**
	 * Select Sales option in Tab Set Dropdown
	 */
	public void selectTabSetSales() {
		selectTabSetDropDownItem("Sales");
	}

	/**
	 * Select Marketing option in Tab Set Dropdown
	 */
	public void selectTabSetMarketing() {
		selectTabSetDropDownItem("Marketing");
	}

	/**
	 * Helper method to select options in Tab Set dropdown
	 * 
	 * @param opt
	 *            The target option
	 */
	private void selectTabSetDropDownItem(String opt) {
		logger.info("Select " + opt + " in Tab Set dropdown.");
		// if the Set Dropdown button does not display the text as option (means
		// the option is already selected), click the dropdown button and then
		// click the option
		if (!ExecutionHelper.waitForElementPresent(driver, tabSetDropDownBtn).getText().equals(opt)) {
			ExecutionHelper.clickWhenReady(driver, tabSetDropDownBtn);
			ExecutionHelper.clickWhenReady(driver,
					By.xpath(this.getPropValue("tabSetDropDownMenu") + "/a[text='" + opt + "']"));
		}
	}

	/******************
	 * Tab Set
	 ***************************************************************/
	/**
	 * Click th Leads tab
	 * 
	 * @return LeadsPage under Leads tab
	 */
	public LeadsPage clickLeadsTab() {
		logger.info("Click Leads Tab.");
		ExecutionHelper.clickWhenReady(driver, leadsTab);
		return new LeadsPage(this.driver);
	}

	/**
	 * Click the Campaigns tab
	 * 
	 * @return Campaigns Page under Campaigns tab
	 */
	public CampaignsPage clickCampaignsTab() {
		logger.info("Click Campaign Tab.");
		ExecutionHelper.clickWhenReady(driver, leadsTab);
		return new CampaignsPage(this.driver);
	}

	/**
	 * Helper method to check if on Home page by checking Tab Set dropdown
	 * present
	 * 
	 * @return true if the Tab Set dropdown present, false if not
	 */
	private boolean isOnHomePage() {
		return ValidationHelper.waitForElementPresent(driver, tabSetDropDownBtn);
	}

}
