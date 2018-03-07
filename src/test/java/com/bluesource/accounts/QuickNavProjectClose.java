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
    	accounts.clickAccountLink("Basic Account");
   
    	TestReporter.logStep("Click New Project button, fill out form, and submit");
    	accounts.addProject("New Project 1", "08182018", "08182019");
    	
    	TestReporter.logStep("Select newly created project from Account overview page");
    	accounts.selectProject("New Project 1");
    	
    	TestReporter.logStep("Add a new role to the project");
    	accounts.createRole("Project Manager", 25);
    	
    	TestReporter.logStep("Select newly created role");
    	accounts.selectNewRole("Project Manager");
    	
    	TestReporter.logStep("Click '+Assign Employee' button");
    	accounts.clickAssignEmployee();
    	accounts.assignEmployeeToProject();
    	
    	TestReporter.logStep("Click Quick Nav button");
    	TestReporter.assertTrue(accounts.verifyQuickNavButtonIsVisible(), "Verify Quick Nav button appears");
    	accounts.clickQuickNav();
    	TestReporter.assertTrue(accounts.verifyQuickNavCloseButtonIsVisible(), "Verify Quick Nav opens and close button is displayed");
    	
    	TestReporter.logStep("Verify newly created project is displayed in Quick Nav");
    	TestReporter.assertTrue(accounts.verifyNewProjectIsDisplayed("New Project 1"), "Verify new project is displayed in Quick Nav");
    	
    	TestReporter.logStep("Navigate back to newly created project page");
    	accounts.closeQuickNav();
    	header.navigateAccounts();
    	accounts.clickAccountLink("Basic Account");
    	accounts.selectProject("New Project 1");
    	
    	TestReporter.logStep("Close the project");
    	accounts.clickProjectOptions();
    	accounts.clickEditProject();
    	accounts.closeProject();
    	
    	TestReporter.logStep("Verify that project no longer displays in Quick Nav");
    	TestReporter.assertTrue(accounts.verifyQuickNavButtonIsVisible(), "Verify Quick Nav button appears");
    	accounts.clickQuickNav();
    	TestReporter.assertTrue(accounts.verifyQuickNavCloseButtonIsVisible(), "Verify Quick Nav opens and close button is displayed");
    	TestReporter.assertFalse(accounts.verifyNewProjectIsDisplayed("New Project 1"), "Verify new project is no longer displayed in Quick Nav");
    	
    
    	
    }
}
