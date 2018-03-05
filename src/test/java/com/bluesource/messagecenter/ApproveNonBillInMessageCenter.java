package com.bluesource.messagecenter;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.orasi.bluesource.EmployeePage;
import com.orasi.bluesource.Header;
import com.orasi.bluesource.LoginPage;
import com.orasi.bluesource.MessageCenter;
import com.orasi.bluesource.ProjectEmployees;
import com.orasi.bluesource.ReportedTimesSummary;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataProviders.ExcelDataProvider;
import com.orasi.web.WebBaseTest;

public class ApproveNonBillInMessageCenter extends WebBaseTest{
	

	// ************* *
	// Data Provider
	// **************
	@DataProvider(name = "messageCenter_data", parallel=true)
	public Object[][] scenarios() {
			return new ExcelDataProvider("/testdata/blueSource_Users.xlsx", "Sheet1").getTestData();
	}
	
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
    
    @Test(groups = {"smoke"})
    public void testApproveNonBillInMessageCenter() {
    	LoginPage loginPage = new LoginPage(getDriver());
    	Header header = new Header(getDriver());
    	MessageCenter message = new MessageCenter(getDriver());
    	EmployeePage empPage = new EmployeePage(getDriver());
    	ReportedTimesSummary rtSummary = new ReportedTimesSummary(getDriver());
    	ProjectEmployees projEmp = new ProjectEmployees(getDriver());
    	
    	TestReporter.logStep("Test started");
    	TestReporter.logStep("Login to bluesource as an employee with non-billable role on an open project");
    	loginPage.LoginWithCredentials("steven.barnes", "123");
    	
    	
    	TestReporter.logStep("Submit a timesheet");
    	empPage.clickManage();
    	rtSummary.addTimeSheetToCurrentPeriod();
    	rtSummary.fillTimeSheet();
    	rtSummary.submitTimeSheet();
    	
    	TestReporter.logStep("logout");
    	
    	header.clickLogout();
    	
    	
    	TestReporter.logStep("login as the user's manager");
    	loginPage.LoginWithCredentials("sherri.collins", "123");
    	
    	
    	TestReporter.logStep("approve the timesheet in Message Center");
    	header.clickMessageCenter();
    	message.clickApprove();
    	message.closeMessageCenter();
    	
    	
    	TestReporter.logStep("click the employees button");
    	header.clickEmployees();
    	
    	
    	TestReporter.logStep("find the employee that submitted the time sheet");
    	projEmp.selectEmployee("Steven Barnes");
    	
    	TestReporter.logStep("Hover over Approved status and verify that approved by message appears");
    	TestReporter.assertTrue(rtSummary.checkApprovedByMessage("Bench", "Sherri Collins"), "Verify that Approved By message displays");
    	
    	
    	
    	
    }
}
