package com.bluesource.employees;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.EmployeePage;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.ReportedTimesSummary;
import com.orasi.utils.TestReporter;
import com.orasi.web.PageLoaded;
import com.orasi.web.WebBaseTest;

public class EnteringPastPTOShouldRefreshTimesheetExceptions extends WebBaseTest{

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
    public void testEnteringPastPTOShouldRefreshTimesheetExceptions() {
    	LoginPage loginPage = new LoginPage(getDriver());
    	Header header = new Header(getDriver());
    	EmployeePage ePage = new EmployeePage(getDriver());
    	ReportedTimesSummary rtSummary = new ReportedTimesSummary(getDriver());
    	
    	TestReporter.logStep("Test started");
    	
    	TestReporter.logStep("Login to BlueSource");
    	loginPage.LoginWithCredentials("stephen.washington", "123");
    	
    	TestReporter.logStep("Fill timesheet with less than 40 hours");
    	ePage.fillTimeSheetLessThanFortyHours();
    	
    	TestReporter.logStep("Submit timesheet");
    	ePage.submitCurrentTimeSheet();
    	
    	TestReporter.logStep("Verify exceptions appear");
    	TestReporter.assertTrue(ePage.verifyTimeSheetExceptions(), "Verify exceptions appear");
    	
    	TestReporter.logStep("Click 'Confirm' button");
    	ePage.clickConfirm();
    	
    	PageLoaded.isDomComplete(getDriver(), 5);
    	
    	TestReporter.logStep("Click 'Manage' button");
    	ePage.clickManage();
    	
    	TestReporter.logStep("Verify exceptions appear above submitted timesheet");
    	TestReporter.assertTrue(rtSummary.verifyExceptionsExist(), "Verify exceptions are displayed");
    	
    	TestReporter.logStep("Click the 'Recall' button of the timesheet that was just logged");
    	rtSummary.clickRecallOnTimeSheetWithExceptions();
    	
    	TestReporter.logStep("Click 'Edit' button");
    	rtSummary.clickEditOnTimeSheetWithExceptions();
    	
    	TestReporter.logStep("Edit timesheet to contain 8 hours per day");
    	rtSummary.editTimeSheetToFortyHours();
    	
    	TestReporter.logStep("Click 'Submit' button");
    	rtSummary.submitEditedTimeSheet();
    	
    	TestReporter.logStep("Verify timesheet is refreshed and exceptions are no longer displayed");
    	TestReporter.assertTrue(rtSummary.verifyTimeSheetRefresh(), "Verify timesheet is refreshed");
    	TestReporter.assertFalse(rtSummary.verifyExceptionsExist(), "Verify exceptions have disappeared");
    	
    	TestReporter.logStep("Logout");
    	header.clickLogout();
    
    }
}
