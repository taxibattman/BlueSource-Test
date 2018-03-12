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

public class AccountsQuickNavAccountList extends WebBaseTest{

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
    public void testAccountsQuickNavAccountList() {
    	LoginPage loginPage = new LoginPage(getDriver());
    	Header header = new Header(getDriver());
    	Accounts accounts = new Accounts(getDriver());
    	
    	TestReporter.logStep("Test started");
    	
    	TestReporter.logStep("Login to BlueSource as company.admin");
    	loginPage.AdminLogin();
    	
    	TestReporter.logStep("Navigate to the Accounts page");
    	header.navigateAccounts();

    	
    	TestReporter.logStep("Find all the accounts that have projects");
    	List <String> accountNames = accounts.getAccountsWithProjects();
    	
    	TestReporter.logStep("Open QuickNav");
    	accounts.clickQuickNav();
    	
    	TestReporter.logStep("Verify that all accounts with a project are displayed in the accounts list in QuickNav");
    	TestReporter.assertTrue(accounts.verifyAccountsQuickNavDisplay(accountNames),"Verify all accounts with projects appear in QuickNav");
    	
    	TestReporter.logStep("Close QuickNav");
    	accounts.closeQuickNav();
    	
    	TestReporter.logStep("Logout");
    	header.clickLogout();
    
    
    }
}
