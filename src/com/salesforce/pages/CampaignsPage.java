package com.salesforce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.salesforce.base.BasePage;
import com.salesforce.utils.ExecutionHelper;
import com.salesforce.utils.ValidationHelper;

/**
 * The Campaigns Tab page
 * 
 * @author Bo Ma
 * @since 2015-09-06
 *
 */
public class CampaignsPage extends BasePage {

	/**
	 * The default constructor. Launch Campaigns Page with default local driver
	 */
	public CampaignsPage() {

	}

	/**
	 * Constructor that allow passing in another driver. Check if on Campaigns
	 * page.
	 * 
	 * @param myDriver
	 *            Instantiate page with this driver
	 */
	public CampaignsPage(WebDriver myDriver) {
		super(myDriver);
		if (!isOnCampaignsPage())
			throw new IllegalStateException("This is not the Campaigns page");
		logger.info("Launch a Campaign Page.");

	}

	/**************************
	 * Campaigns Main Page
	 ********************************/
	By campaignsPageHeader = By.xpath(this.getPropValue("pageHeader"));
	By campaignLookUpBtn = By.xpath(this.getPropValue("campaignLookUpBtn"));
	By addToCampaignBtn = By.xpath(this.getPropValue("addToCampaignBtn"));
	By doneBtn = By.xpath(this.getPropValue("doneBtn"));
	By memberStatusSelect = By.xpath(this.getPropValue("memberStatusSelect"));

	/**
	 * Click Campaign Lookup Button
	 */
	public void clickCampaignLookUpBtn() {
		logger.info("Click Campaign Lookup Button.");
		ExecutionHelper.clickWhenReady(driver, campaignLookUpBtn);
	}

	/**
	 * Click Add To Campaign button
	 */
	public void clickAddToCampaignBtn() {
		// wait for the member status select filled to be able to successful
		// proceed to the next step.
		ExecutionHelper.waitForElementPresent(driver, memberStatusSelect);
		logger.info("Click Add To Campaign button.");
		ExecutionHelper.clickWhenReady(driver, addToCampaignBtn);
	}

	/**
	 * Click Done button
	 * 
	 * @return Leads Page jumped back to
	 */
	public LeadsPage clickDoneBtn() {
		logger.info("Click Done button.");
		ExecutionHelper.clickWhenReady(driver, doneBtn);
		return new LeadsPage(this.driver);
	}

	/**
	 * Compound Method to perform Campaign look up full process.
	 * 
	 * @param campaign
	 *            The Campaign which will be added with a lead
	 * @return The Lead page which has Add To Campaign option
	 */
	public LeadsPage lookedUpCampaign(String campaign) {
		// save the current window for later use
		String winHandlePrev = driver.getWindowHandle();
		// click Campaign lookup button
		clickCampaignLookUpBtn();
		// check all the two browser windows in current driver, switch to the
		// new popup one
		for (String win : driver.getWindowHandles()) {
			if (!win.equals(winHandlePrev)) {
				logger.info("Switch to the popup window.");
				driver.switchTo().window(win);
			}
		}
		// on the popup window, go into the search frame to perform search
		driver.switchTo().frame(ExecutionHelper.waitForElementPresent(driver, By.xpath("//frame[@id='searchFrame']")));
		selectCampaignViewScopeAllCampaigns();
		fillInCampaignSearchTextField(campaign);
		clickCampaignGoBtn();
		// on the popup window, leave the search frame and go into results frame
		// to click the search result
		driver.switchTo().parentFrame();
		driver.switchTo().frame(ExecutionHelper.waitForElementPresent(driver, By.xpath("//frame[@id='resultsFrame']")));
		clickCampaignSearchResult(campaign);
		logger.info("Popup window closed, switch back to Campaing page main window.");
		// switch back to the main campaign page
		driver.switchTo().window(winHandlePrev);
		// wait for value import finished and finish Add To Campaign process
		clickAddToCampaignBtn();
		return clickDoneBtn();
	}

	/**************************
	 * Campaigns Lookup Popup Page
	 ************************/
	By campaignScopeSelector = By.xpath(this.getPropValue("campaignScopeSelector"));
	By campaignScopeSearchTextField = By.xpath(this.getPropValue("campaignScopeSearchTextField"));
	By campaignScopeGoBtn = By.xpath(this.getPropValue("campaignScopeGoBtn"));

	/**
	 * Select All Campaigns in the View Scope Dropdown on Lookup popup
	 */
	public void selectCampaignViewScopeAllCampaigns() {
		selectCampaignViewScope("All Campaigns");
	}

	/**
	 * Fill in text to be search to find a Campaign to assign to lead
	 * 
	 * @param text
	 *            The search text which expected a Campaign return
	 */
	public void fillInCampaignSearchTextField(String text) {
		logger.info("Fill in " + text + " to search in Campaign search field.");
		ExecutionHelper.typeWhenReady(driver, campaignScopeSearchTextField, text, true);
	}

	/**
	 * Click search campaign Go! button
	 */
	public void clickCampaignGoBtn() {
		logger.info("Click search campaign Go! button.");
		ExecutionHelper.clickWhenReady(driver, campaignScopeGoBtn);
	}

	/**
	 * Click the Campaign Search Result entry in the search result table on
	 * lookup popup
	 * 
	 * @param campaignName
	 *            The click target Campaign name link
	 */
	public void clickCampaignSearchResult(String campaignName) {
		logger.info("Click " + campaignName + " in the search result table.");
		By record = By.xpath("//div[@class='listRelatedObject lookupBlock']//a[text()='" + campaignName + "']");
		ExecutionHelper.clickWhenReady(driver, record);
	}

	/**
	 * Helper method to select Campaign View Scope on lookup popup window
	 * 
	 * @param opt
	 *            The View option to select
	 */
	private void selectCampaignViewScope(String opt) {
		logger.info("Select the Campaign View Scope option " + opt);
		ExecutionHelper.selectWhenReady(driver, campaignScopeSelector, opt);
	}

	/**
	 * Check if on Campaign page by check the Campaigns page header
	 * 
	 * @return true if Campaigns page header display, false if not
	 */
	private boolean isOnCampaignsPage() {
		return ValidationHelper.waitForElementPresent(driver, campaignsPageHeader);
	}
}
