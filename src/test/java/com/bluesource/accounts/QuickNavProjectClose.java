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

public class QuickNavProjectClose extends WebBaseTest{

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
    public void accountAdminQuickNav() {
    	Header header = new Header(getDriver());
    	Accounts accounts = new Accounts(getDriver());
    	LoginPage loginPage = new LoginPage(getDriver());
    	
    	TestReporter.logStep("Test started");
    	
    	TestReporter.logStep("Login to BlueSource as company.admin");
    	loginPage.AdminLogin();
    	
    	TestReporter.logStep("Click on 'Accounts'");
    	header.navigateAccounts();
    	
    	TestReporter.logStep("click on an account");
    	accounts.clickFirstAccountLink();
   
    	TestReporter.logStep("Click New Project button, fill out form, and submit");
    	accounts.addProject();
    	
    	TestReporter.logStep("Select newly created project from Account overview page");
    	accounts.selectProject("New Project 1");
    	
    	TestReporter.logStep("Add a new role to the project");
    	accounts.createRole();
    	
    	TestReporter.logStep("Select newly created role");
    	
    	
    	TestReporter.logStep("Select an employee to fill the role");
    	
    	
    	TestReporter.logStep("");
    	
    	
    	TestReporter.logStep("");
    	
    	
    	TestReporter.logStep("");
    	
    	
    	TestReporter.logStep("");
    	
    	
    	TestReporter.logStep("");
    
    	
    }
}
