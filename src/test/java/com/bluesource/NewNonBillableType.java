package com.bluesource;

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
import com.orasi.web.WebBaseTest;

public class NewNonBillableType extends WebBaseTest{
	// ************* *
	// Data Provider
	// **************
	/*@DataProvider(name = "login", parallel=true)
	public Object[][] scenarios() {
	return new ExcelDataProvider("/excelsheets/blueSource_Users.xlsx", "Data").getTestData();
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
	 public void testNewNonBillableType() {
		 LoginPage loginPage = new LoginPage(getDriver());
		 Header header = new Header(getDriver());
		 EmployeePage ePage = new EmployeePage(getDriver());
		 ReportedTimesSummary rtSummary = new ReportedTimesSummary(getDriver());
		 
		 TestReporter.logStep("Test started");
		 
		 TestReporter.logStep("Login to BlueSource");
		 loginPage.LoginWithCredentials("sherri.collins", "123");
		 
		 
		 TestReporter.logStep("Click manage in Project info section");
		 ePage.clickManage();
		 
		 TestReporter.logStep("Select a timesheet");
		 rtSummary.clickEdit();
		 
		 
		 TestReporter.logStep("Click 'Add Non Billable' button");
		 rtSummary.clickAddNonBill();
		 
		 TestReporter.logStep("Verify 'Customer Related' is a value in the dropdown");
		 rtSummary.verifyNonBillOption("Customer Related");
		 
		 TestReporter.logStep("Select 'Customer Related'");
		 rtSummary.selectNonBillOption("Customer Related");
		 
		 TestReporter.logStep("Enter a number of hours. Enter comments in comment box. Click 'Ok'.");
		 rtSummary.fillTimeSheet();
		 rtSummary.submitComment();
		 
		 TestReporter.logStep("Click 'Save'");
		 rtSummary.clickSave();
		 
		 TestReporter.logStep("Click 'Submit'");
		 rtSummary.clickEdit();
		 rtSummary.clickSubmit();

	 }
}
