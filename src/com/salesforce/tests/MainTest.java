package com.salesforce.tests;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.salesforce.base.BaseTestCase;
import com.salesforce.pages.CampaignsPage;
import com.salesforce.pages.HomePage;
import com.salesforce.pages.LeadsPage;
import com.salesforce.pages.LoginPage;
import com.salesforce.utils.TestBaseInfo;
import com.salesforce.utils.WebDriverGenerator;

/**
 * @author Bo Ma
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainTest extends BaseTestCase {
	static LoginPage loginP;
	static HomePage homeP;
	static LeadsPage leadsP;
	static CampaignsPage campaignP;

	String leadName = this.getPropValue("leadName");
	String campaignName = this.getPropValue("campaignName");

	@Before
	public void init() {
		loginP = new LoginPage();
	}

	@Test
	public void test_a_bonusTest() {
		try {

			homeP = loginP.login(TestBaseInfo.getUserName(), TestBaseInfo.getPassword());
			homeP.selectTabSetSales();
			leadsP = homeP.clickLeadsTab();
			leadsP.selectAllLeadsView();
			leadsP.checkMarkTheTableRow(leadName);
			campaignP = leadsP.clickAddToCampaignButton();
			leadsP = campaignP.lookedUpCampaign(campaignName);

		} catch (Exception e) {
			logger.error(e.getMessage());
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void test_b_mainTest() {
		homeP = loginP.login(TestBaseInfo.getUserName(), TestBaseInfo.getPassword());
		homeP.selectTabSetSales();
		leadsP = homeP.clickLeadsTab();
		leadsP.selectAllLeadsView();
		leadsP.clickDeleteTheTableRow(leadName, true);
		Assert.assertTrue("The Lead has been deleted and cannot be located though the whole table.",
				leadsP.isLeadNotDisplayInTable(leadName));
	}

	@AfterClass
	public static void shutdown() {
		WebDriverGenerator.tearDownLocalWebDriver();

	}
}
