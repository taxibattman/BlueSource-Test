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

public class AddDailyTotalHoursOnTimeReportScreen extends WebBaseTest{
	

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
    public void testAddDailyTotalHoursOnTimeReportScreen() {
    	LoginPage loginPage = new LoginPage(getDriver());
    	Header header = new Header(getDriver());
    	ReportedTimesSummary rtSummary = new ReportedTimesSummary(getDriver());
    	EmployeePage ePage = new EmployeePage(getDriver());
    	
    	TestReporter.logStep("Login to BlueSource");
    	loginPage.LoginWithCredentials("steven.barnes", "123");
    	
    	TestReporter.logStep("Click Manage button");
    	ePage.clickManage();
    	
    	TestReporter.logStep("Verify daily totals are accurate");
    	TestReporter.assertTrue(rtSummary.verifyAllTotalHours(),"Verify daily totals are accurate");
    	
    	TestReporter.logStep("Logout");
    	header.clickLogout();
    	
    }

}
