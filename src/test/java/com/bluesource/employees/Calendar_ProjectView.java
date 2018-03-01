package com.bluesource.employees;

import org.openqa.selenium.Keys;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.Calendar;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.utils.TestReporter;
import com.orasi.web.WebBaseTest;

public class Calendar_ProjectView extends WebBaseTest{
	// ************* *
	// Data Provider
	// **************
	/*@DataProvider(name = "login", parallel=true)
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
			testStart("Deactivate_Employee_Verify_Future_Assignments_Destroyed");
	}
	
	 @AfterMethod
	    public void close(ITestContext testResults){
	    	endTest("TestAlert", testResults);
	    }
	 
	 @Test(groups = {"smoke"})
	 public void testCalendarProjectView() {
		 LoginPage loginPage = new LoginPage(getDriver());
		 Header header = new Header(getDriver());
		 Calendar calendar = new Calendar(getDriver());
		 
		 
		 TestReporter.logStep("Test started");
		 
		 TestReporter.logStep("Login as a Timsheet Approver");
		 loginPage.LoginWithCredentials("alyssa.russell", "123");
		 
		 TestReporter.logStep("Navigate to Calendar page");
		 header.navigateCalendar();
		 
		 TestReporter.logStep("Verify project filter is displayed");
		 TestReporter.assertTrue(calendar.verifyProjectFilterIsDisplayed(), "Verify project filter is displayed");
		 
		 TestReporter.logStep("click 'Project'");
		 calendar.clickProjectFilter();
		 
		 
	 }
}
