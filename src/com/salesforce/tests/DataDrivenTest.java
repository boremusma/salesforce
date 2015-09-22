package com.salesforce.tests;

import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.salesforce.base.BaseTestCase;
import com.salesforce.pages.CampaignsPage;
import com.salesforce.pages.HomePage;
import com.salesforce.pages.LeadsPage;
import com.salesforce.pages.LoginPage;
import com.salesforce.utils.ScreenShotHelper;
import com.salesforce.utils.SpreadsheetData;
import com.salesforce.utils.WebDriverGenerator;
import com.salesforce.utils.TestBaseInfo;

/**
 * 
 * @author Bo Ma
 * @since 2015-09-21
 */

@RunWith(Parameterized.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DataDrivenTest extends BaseTestCase {
	@Rule
	public ErrorCollector collector= new ErrorCollector();
	
	static LoginPage loginP;
	static HomePage homeP;
	static LeadsPage leadsP;
	static CampaignsPage campaignP;

	// Fields
	String leadName = "";
	String campaignName = "";

	public DataDrivenTest(String leadName, String campaignName) {
		this.leadName = leadName;
		this.campaignName = campaignName;

	}

	@SuppressWarnings("rawtypes")
	@Parameters
	public static Collection spreadsheetData() throws IOException {
		InputStream spreadsheet = new FileInputStream(
				"config/tests/DataDrivenTest.xls");
		return new SpreadsheetData(spreadsheet).getData();
	}
	
	@Before
	public void init() {
		try {
			loginP = new LoginPage();
		} catch (Exception e) {
			logger.error(e.getMessage());
			ScreenShotHelper.shotEntirePage(
			WebDriverGenerator.getLocalDriver(), className,
			name.getMethodName());
			Assert.fail(e.getMessage());
		}
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
			ScreenShotHelper.shotEntirePage(
					WebDriverGenerator.getLocalDriver(), className,
					name.getMethodName());
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void test_b_mainTest() {
		try {
			homeP = loginP.login(TestBaseInfo.getUserName(), TestBaseInfo.getPassword());
			homeP.selectTabSetSales();
			leadsP = homeP.clickLeadsTab();
			leadsP.selectAllLeadsView();
			leadsP.clickDeleteTheTableRow(leadName, true);
			collector.checkThat("The Lead has been deleted and cannot be located though the whole table.",
				leadsP.isLeadNotDisplayInTable(leadName),equalTo(true));
		} catch (Exception e) {
			logger.error(e.getMessage());
			ScreenShotHelper.shotEntirePage(
					WebDriverGenerator.getLocalDriver(), className,
					name.getMethodName());
			Assert.fail(e.getMessage());
		}
	}
}
