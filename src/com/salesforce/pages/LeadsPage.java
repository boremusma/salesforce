package com.salesforce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.salesforce.base.BasePage;
import com.salesforce.utils.ExecutionHelper;
import com.salesforce.utils.ValidationHelper;

/**
 * Leads Page --> This is the Leads Tab page under Sales.
 * 
 * @author Bo Ma
 * @since 2015-09-05
 */
public class LeadsPage extends BasePage {

	// Declare locators
	By addToCampaignBtn = By.xpath(this.getPropValue("addToCampaignBtn"));
	By viewSelector = By.xpath(this.getPropValue("viewSelector"));
	By viewSelectorGoBtn = By.xpath(this.getPropValue("viewSelectorGoBtn"));
	By activeNextPageBtn = By.xpath(this.getPropValue("activeNextPageBtn"));

	/**
	 * The default constructor. Launch Leads Page with default local driver
	 */
	public LeadsPage() {
	}

	/**
	 * Constructor that allow passing in another driver. Check if on Leads page.
	 * 
	 * @param myDriver
	 *            Instantiate page with this driver
	 */
	public LeadsPage(WebDriver myDriver) {
		super(myDriver);
		if (!isOnLeadsPage())
			throw new IllegalStateException("This is not the Leads page");
	}

	/******************************
	 * Methods for View Selector
	 ***************************/

	/**
	 * Select the "All Leads" in View Dropdown
	 */
	public void selectAllLeadsView() {
		selectViewDropdown("All Leads");
	}

	/**
	 * Select the View Dropdown with certain option
	 * 
	 * @param opt
	 *            "All Leads, My Leads, New Leads, Pardot Leads, Partner Leads - Unassigned, Recently Viewed Leads"
	 */
	private void selectViewDropdown(String opt) {
		logger.info("Select the View Filter Dropdown option " + opt);
		ExecutionHelper.selectWhenReady(driver, viewSelector, opt);
		ExecutionHelper.clickWhenReady(driver, viewSelectorGoBtn);
		ExecutionHelper.waitForElementPresent(driver, addToCampaignBtn);
	}

	/******************************
	 * Methods for Active Leads Table
	 ***********************/
	/**
	 * Click the check mark on the first column of a lead according to the full
	 * name.
	 * 
	 * @param fullName
	 *            The full name of the lead to check
	 */
	public void checkMarkTheTableRow(String fullName) {
		logger.info("Check the lead " + fullName + " in leads table.");
		String xpath = actionLeadsTableRow(fullName, "//input[@type='checkbox']");
		By checkbox = By.xpath(xpath);
		if (isLeadDisplayInTable(fullName))
			ExecutionHelper.clickWhenReady(driver, checkbox);
	}

	/**
	 * Click the "Del" of a lead entry. Confirm deletion is optional
	 * 
	 * @param fullName
	 *            The full name of the lead to delete
	 * @param accept
	 *            true if confirm to delete the lead, false if dismiss deletion
	 *            action
	 */
	public void clickDeleteTheTableRow(String fullName, boolean accept) {
		logger.info("Click the Del button for lead " + fullName);
		String xpath = actionLeadsTableRow(fullName, "//span[text()='Del']");
		By delete = By.xpath(xpath);
		if (isLeadDisplayInTable(fullName)) {
			ExecutionHelper.clickWhenReady(driver, delete);
			ExecutionHelper.manageAlert(driver, accept);
			if (accept)
				logger.info("Deletion Comfirmed.");
			else
				logger.info("Deletion Abort.");
		}
	}

	/**
	 * Click Add to Campaign Button, this will jump to Campaigns Tab page.
	 * 
	 * @return Campaigns Tab page
	 */
	public CampaignsPage clickAddToCampaignButton() {
		logger.info("Click Add To Campaign button.");
		ExecutionHelper.clickWhenReady(driver, addToCampaignBtn);
		return new CampaignsPage(this.driver);
	}

	/**
	 * Search for entire leads entries table, if not find, click next page
	 * button and search again
	 * 
	 * @param fullName
	 *            The target lead full name
	 * @return true if the lead is found, false if not found though the entire
	 *         table
	 */
	public boolean isLeadDisplayInTable(String fullName) {
		logger.info(
				"Start to search for lead " + fullName + ". Will spend few seconds on each page until click 'Next'");
		String xpath = actionLeadsTableRow(fullName, "");
		By row = By.xpath(xpath);
		while (true) {
			if (ValidationHelper.waitForElementVisible(driver, row)) {
				logger.info("Lead " + fullName + " found.");
				return true;
			}
			if (ValidationHelper.waitForElementPresent(driver, activeNextPageBtn))
				ExecutionHelper.clickWhenReady(driver, activeNextPageBtn);
			else
				break;
		}
		logger.info("Lead " + fullName + " not found.");
		return false;
	}

	/**
	 * Search for entire leads entries table, if not find, click next page
	 * button and search again
	 * 
	 * @param fullName
	 *            The target lead full name
	 * @return true if the lead is found, false if not found though the entire
	 *         table
	 */
	public boolean isLeadNotDisplayInTable(String fullName) {
		logger.info(
				"Start to search for lead " + fullName + ". Will spend few seconds on each page until click 'Next'");
		String xpath = actionLeadsTableRow(fullName, "");
		By row = By.xpath(xpath);
		while (true) {
			if (!ValidationHelper.waitForElementInvisible(driver, row)) {
				logger.info("Lead " + fullName + " can still be found in table.");
				return false;
			}
			if (ValidationHelper.waitForElementPresent(driver, activeNextPageBtn))
				ExecutionHelper.clickWhenReady(driver, activeNextPageBtn);
			else
				break;
		}
		logger.info("Lead " + fullName + " not found in table.");
		return true;
	}

	/**
	 * Helper method to generate the lead entry elements xpath, eg. checkbox,
	 * del, edit,
	 * 
	 * @param fullName
	 *            The full name of the lead entry
	 * @param actionPartialXpath
	 *            The xpath segment for different row element
	 * @return The full xpath for an element in target lead's row.
	 */
	private String actionLeadsTableRow(String fullName, String actionPartialXpath) {
		String xpath = "//tr[td[div[a[span[text()='" + fullName + "']]]]]" + actionPartialXpath;
		return xpath;
	}

	/**
	 * Check if on Leads Tab page by checking the Lead View Selector display
	 * 
	 * @return true if Leads View Selector displayed, false if not
	 */
	private boolean isOnLeadsPage() {
		return ValidationHelper.waitForElementPresent(driver, viewSelector);
	}
}
