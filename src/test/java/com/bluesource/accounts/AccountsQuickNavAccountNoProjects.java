package com.bluesource.accounts;

import java.util.List;

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

public class AccountsQuickNavAccountNoProjects extends WebBaseTest{

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
    public void testAccountsQuickNavAccountNoProjects() {
    	LoginPage loginPage = new LoginPage(getDriver());
    	Header header = new Header(getDriver());
    	Accounts accounts = new Accounts(getDriver());
    	
    	
    	TestReporter.logStep("Test started");
    	
    	TestReporter.logStep("Login to BlueSource as company.admin");
    	loginPage.AdminLogin();
    	
    	TestReporter.logStep("Navigate to accounts page");
    	header.navigateAccounts();
    	
    	TestReporter.logStep("Get all accounts without projects");
    	List<String> accountNames = accounts.getAccountsWithoutProjects();
    	
    	TestReporter.logStep("Open Quick Nav");
    	accounts.clickQuickNav();
    	
    	TestReporter.logStep("Verify accounts don't appear in quick nav");
    	TestReporter.assertFalse(accounts.verifyAccountsQuickNavNoDisplay(accountNames), "Verify account do not appear in Quick Nav");
    	
    	TestReporter.logStep("Close Quick Nav");
    	accounts.closeQuickNav();
    	
    	TestReporter.logStep("logout");
    	header.clickLogout();
    
    }
}
