package com.bluesource.admin;

import org.openqa.selenium.NoSuchElementException;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.EmployeePage;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.TimesheetLocksPage;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.web.WebBaseTest;

public class TimesheetLockDisplay extends WebBaseTest{
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
	 public void admin_lock_timesheet_test() {
		 TestReporter.logStep("Login with admin permission");
		 LoginPage loginPage = new LoginPage(getDriver());
		 loginPage.Login("ADMIN"); 
		 TestReporter.logStep("Navigate to the Timesheet Locks page");
		 Header header = new Header(getDriver());
		 header.navigateTimesheetLocks();
		 TestReporter.logStep("Select a month from dropdown and accept the popup message");
		 TimesheetLocksPage timesheetLocksPage = new TimesheetLocksPage(getDriver());
		 String month = "October";
		 timesheetLocksPage.selectMonth(month);
		 timesheetLocksPage.createLock();
		 TestReporter.assertTrue(timesheetLocksPage.verifyLockCreateAlert(), "Verify that the timesheet lock was created and the alert was displayed");
		 TestReporter.logStep("Clean up after the test by unlocking the locked month");
		 timesheetLocksPage.cleanAfterTest(month);
	 }
}
