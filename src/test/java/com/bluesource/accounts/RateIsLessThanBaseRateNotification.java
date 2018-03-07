package com.bluesource.accounts;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Accounts;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class RateIsLessThanBaseRateNotification extends WebBaseTest{

	// **************
	// Data Provider
	// **************
	/*@DataProvider(name = "accounts_industry", parallel=true)
	public Object[][] scenarios() {
		return new ExcelDataProvider("/testdata/blueSource_Users.xlsx", "Sheet1").getTestData();
	}*/
	
	@BeforeMethod
    @Parameters({ "runLocation", "browserUnderTest", "browserVersion",
    	"operatingSystem", "environment" })
    public void setup(@Optional String runLocation, String browserUnderTest,
	    String browserVersion, String operatingSystem, String environment) {
    	setApplicationUnderTest("BLUESOURCE");
		setBrowserUnderTest(browserUnderTest);
		setBrowserVersion(browserVersion);
		setOperatingSystem(operatingSystem);
		setRunLocation(runLocation);
		setEnvironment(environment);
		setThreadDriver(true);
		testStart("");
	}
    
    @AfterMethod
    public void close(ITestContext testResults){
    	endTest("TestAlert", testResults);
    }
    
    @Test(groups = {"smoke"} )
    public void testRateIsLessThanBaseRateNotification() {
    	LoginPage loginPage = new LoginPage(getDriver());
    	Header header = new Header(getDriver());
    	Accounts accounts = new Accounts(getDriver());
    	
    	TestReporter.logStep("Login to BlueSource as company.admin");
    	loginPage.AdminLogin();
    	
    	TestReporter.logStep("Navigate to accounts page");
    	header.navigateAccounts();
    	
    	TestReporter.logStep("Click an account");
    	accounts.clickAccountLink("Basic Account");
    	
    	TestReporter.logStep("Click on a project");
    	accounts.clickProjectLink("Test Project");
    	
    	
    	TestReporter.logStep("Click '+New Role' button. Select a billable role. Input a rate lower than base rate. Verify Alert is displayed");
    	
    	TestReporter.assertTrue(accounts.verifyLowRateAlert("Automation Test Engineer (SAP)",25),"Verify low rate alert is displayed");
    	
    	TestReporter.logStep("Close pop up");
    	accounts.closeAddRolePopup();
    	
    	TestReporter.logStep("Logout of BlueSource");
    	header.clickLogout();
    	
    	
    	
    	
    	
    }
}
