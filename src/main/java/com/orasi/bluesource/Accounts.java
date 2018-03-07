package com.orasi.bluesource;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.util.Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.orasi.utils.Randomness;
import com.orasi.utils.TestReporter;
import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class Accounts<E> {
	private OrasiDriver driver = null;
	
	
	/**Page Elements**/
	@FindBy(xpath = "//*[@id='resource-content']/div[2]/p") private Element elmNumberPages;
	@FindBy(xpath = "//*[@id=\"resource-content\"]/div[1]/table/tbody") private Webtable tblAccounts;
	@FindBy(id = "preference_resources_per_page") private Listbox lstAccountPerPage;
	@FindBy(linkText = "Industry") private Link lnkIndustry;
	@FindBy(linkText = "Accounts") private Link lnkAccountsTab;
	@FindBy(xpath = "//button[contains(text(),'Assign Employee')]") private Button btnAssignEmployee;
	@FindBy(xpath = "//div[@id='panel_body_1']//table") private Webtable tblProjects;
	@FindBy(xpath = "//button[@data-target='#modal_1']") private Button btnAddAccount;
	@FindBy(xpath = "//input[@id='account_name']") private Textbox txtAccountName;
	@FindBy(xpath = "//select[@id='account_industry_id']") private Listbox lstIndustry;
	@FindBy(xpath = "//input[@value='Create Account']") private Button btnCreateAccount;
	@FindBy(xpath = "//*[@id=\"accordion\"]/div/div[5]/div/button[2]") private Button btnEditAccount;
	@FindBy(css = "div.btn.btn-secondary.btn-xs.quick-nav") private Button btnQuickNav;
	@FindBy(xpath = "//a[contains(@ng-bind, 'n + 1')]") private List<Button> btnPages;
	@FindBy(xpath = "//*[@id=\"project-list\"]/div/div[1]/div") private Button btnCloseQuickNav;
	
	
	@FindBy(xpath = "//button[contains(text(),'New Project')]") private Button btnNewProject;
	@FindBy(id = "project_name") private Textbox txtProjectName;
	@FindBy(id = "project_start_date") private Textbox txtProjectStart;
	@FindBy(id = "project_end_date") private Textbox txtProjectEnd;
	@FindBy(xpath = "//input[@value=\"Create Project\"]") private Button btnCreateProject;
	@FindBy(xpath = "//button[contains(text(),'New Role')]") private Button btnNewRole;
	@FindBy(xpath = "//div[@data-off-label=\"Non-Billable\"]") private Button btnBill;
	@FindBy(id = "role_role_type_id") private Listbox lstRoleType;
	@FindBy(id = "role_name") private Textbox txtRoleName;
	@FindBy(id = "role_max_resources") private Textbox txtMaxResources;
	@FindBy(xpath = "//input[@value=\"Create Role\"]") private Button btnCreateRole;
	@FindBy(xpath = "//th[contains(text(),'Role')]/../../..") private Webtable tblProjectRoles;
	@FindBy(id = "filled_role_employee_id") private Listbox lstAssignEmployee;
	@FindBy(xpath = "//input[@value=\"Create Filled role\"]") private Button btnCreateFilledRole;
	@FindBy(xpath = "//ul[@class=\"list-group list-group-hover\"]") private Element elmQuickNavProjectList;
	@FindBy(xpath = "//div[@class='fa fa-bars']/..") private Button btnProjectOptions;
	@FindBy(xpath = "//span[@class='glyphicon glyphicon-pencil menu-icon']/../../..") private Element elmProjectOptionList;
	@FindBy(linkText = "Close Project") private Link lnkCloseProject;
	
	/**Constructor**/
	public Accounts(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	/**Page Interactions**/

	/*
	 * Click on accounts tab 
	 * Make sure that the correct page loads
	 * author: Daniel Smith
	 */
	public void click_accounts_tab(String username)
	{
		if (lnkAccountsTab.isDisplayed() == true)
		{
			System.out.println(username +" has account permissions");
			lnkAccountsTab.click();
		}
		
	}
	
	
	/**
	 * This method gets the number of rows on the Accounts page
	 * @author Paul
	 */
	public int getAccountsTableRows() {
		int rowCount = 0;
		try
		{
			rowCount = tblAccounts.getRowCount();
		}
		catch(NullPointerException e)
		{
			System.out.println("Null pointer exception, no accounts found  \n" + e.getLocalizedMessage());
		}
		return (rowCount - 1);
	}

	/**
	 * This method gets the number of Accounts reported by the page
	 * @author Paul
	 * @return 
	 */
	public Integer getNumberOfAccounts() {
		String str;
		String delims;
		String[] tokens = null;
		Integer lastItem = null;
		
		try
		{
			tblAccounts.syncEnabled(3);
			str = elmNumberPages.getText();
			delims = "[ ]";
			tokens = str.split(delims);
			lastItem = tokens.length;
			return Integer.valueOf(tokens[lastItem-1]);
		}
		catch(NoSuchElementException e)
		{
			System.out.println("No such exception, no accounts found \n" + e.getLocalizedMessage());
		}
		return lastItem;
		
	}
	
	/*
	 * Change the number showing for accounts per page to 100
	 * author: Daniel Smith
	 */
	public void accountsPerPage()
	{
		try
		{
			lstAccountPerPage.selectValue("100");
		}
		catch(NoSuchElementException e)
		{
			System.out.println("No such element found.  \n" + e.getLocalizedMessage());
		}
		catch(UndeclaredThrowableException e)
		{
			System.out.println("Undeclared throwable exception  \n" + e.getLocalizedMessage());
		}
		
	}
	
	/*
	 * Sort accounts table by industry
	 * @author: Daniel Smith
	 */
	public void sort_by_industry()
	{
		if(lnkIndustry.isDisplayed() == true)
		{
			System.out.println("Link 'Industry' is present");
			lnkIndustry.click();
		}
		else
			System.out.println("Link 'Industry' not on current page");
	
	}
	
	public void clickAccountLink(String strAccount){
		String xpathExpression;
		xpathExpression = "//td//a[contains(text(),'" + strAccount + "')]";
		Link lnkAccount = driver.findLink(By.xpath(xpathExpression));
		lnkAccount.click();
	}
	
	public void clickProjectLink(String strProject){
		/*String xpathExpression;
		xpathExpression = "//td//a[contains(text(),'" + strProject + "')]";
		Link lnkProject = driver.findLink(By.xpath(xpathExpression));
		lnkProject.syncEnabled(5,true);
		lnkProject.click();*/
		
		// verify project is in project column
		// get project column
		Integer intColumn = 1;
		Integer intRow = tblProjects.getRowWithCellText(strProject, intColumn);
		tblProjects.findElement(By.linkText(strProject)).click();
		
		//tblProjects.clickCell(intRow, intColumn);
	}
	
	public Element verifyProjectLink(String strProject){
		Integer intColumn = 1;
		Integer intRow = tblProjects.getRowWithCellText(strProject, intColumn);
		
		tblProjects.findElement(By.linkText(strProject)).click();
		Element eleProject = tblProjects.findElement(By.linkText(strProject));
		
		return eleProject;
		
	}
	
	public void clickSubprojectLink(String strSubProject){
		String xpathExpression;
		xpathExpression = "//td//a[contains(text(),'" + strSubProject + "')]";
		Link  lnkSubProject = driver.findLink(By.xpath(xpathExpression));
		lnkSubProject.syncEnabled(5,true);
		lnkSubProject.click();
	}
	
	public void clickRoleLink(String strRole){
		String xpathExpression;
		xpathExpression = "//td//a[contains(text(),'" + strRole + "')]";
		Link lnkRole = driver.findLink(By.xpath(xpathExpression));
		lnkRole.syncEnabled(5,true);
		lnkRole.click();
		PageLoaded.isDomComplete(driver, 5);
	}
	
	public void clickAssignEmployee(){
		PageLoaded.isDomComplete(driver, 5);
		btnAssignEmployee.syncEnabled(5,true);
		btnAssignEmployee.click();
	}

	public void assignEmployee(String strAccount, String strProject, String strSubProject, String strRole, String strEmployeeName) {
		FilledRoleForm filledRoleForm = new FilledRoleForm(driver);
		
		clickAccountLink(strAccount);
		
		clickProjectLink(strProject);
		
		clickSubprojectLink(strSubProject);
		
		clickRoleLink(strRole);
		
		filledRoleForm.selectEmployee(strEmployeeName);
		
	}
	
	public void clickAddAccount(){
		btnAddAccount.click();
	}
	
	public void setAccountNameTextbox(String strAccountName){
		txtAccountName.set(strAccountName);
	}
	
	public void selectIndustry(String strIndustry){
		lstIndustry.select(strIndustry);
	}
	
	public void clickCreateAccount(){
		btnCreateAccount.click();
	}
	
	public boolean verifyAccountLink(String strAccountName){
		String xpathExpression;
		
		xpathExpression = "//td//a[contains(text(),'" + strAccountName + "')]";
		
		Link lnkAccount = driver.findLink(By.xpath(xpathExpression));
		
		return lnkAccount.isDisplayed();
	}
	
	public String createAccount(){
		clickAddAccount();
		
		PageLoaded.isDomComplete(driver, 5);
		
		String strAccountName = Randomness.randomAlphaNumeric(10);
		
		setAccountNameTextbox(strAccountName);
		
		selectIndustry("Other");
		
		clickCreateAccount();
		
		//clickAccountLink(strAccountName);
		
		return strAccountName;
		
	}
	
	/**
	 * Checks if the add account button is visible.
	 * 
	 * @return <code>true</code> if the add account button is visible, 
	 * <code>false</code> otherwise.
	 * @author Darryl Papke
	 */
	public boolean verifyAddButtonIsVisible() {
		return btnAddAccount.syncVisible(3, false);
	}
	
	/**
	 * Checks if the edit account button is visible.
	 * 
	 * @return <code>true</code> if the edit account button is visible, 
	 * <code>false</code> otherwise.
	 * @author Darryl Papke
	 */
	public boolean verifyEditButtonIsVisible() {
		return btnEditAccount.syncVisible(3, false);
	}
	
	/**
	 * Clicks the first account link in the accounts table.
	 * 
	 * @author Darryl Papke
	 */
	public void clickFirstAccountLink() {
		tblAccounts.getCell(2, 1).findElement(By.cssSelector("a[class='ng-binding']")).click();
	}
	
	/**
	 * Checks if the Quick Nav button is displayed.
	 * 
	 * @return <code>true</code> if the Quick Nav button is visible, 
	 * <code>false</code> otherwise.
	 * @author Darryl Papke
	 */
	public boolean verifyQuickNavButtonIsVisible() {
		return btnQuickNav.syncVisible(5, true);
	}
	
	/**
	 * Goes through each page of accounts and checks if the Quick Nav button
	 * is visible on each page.
	 * 
	 * @return <code>true</code> if the Quick Nav button is on each page, 
	 * <code>false</code> otherwise.
	 * @author Darryl Papke
	 */
	public boolean verifyQuickNavButtonEachPage() {
		boolean answer = false;
		PageLoaded.isDomComplete(driver, 5);
		for(Button page : btnPages) {
			page.syncEnabled(5);
			page.click();
			answer = verifyQuickNavButtonIsVisible();
			if(!verifyQuickNavButtonIsVisible()) {
				return false;
			}
		}
		return answer;
	}
	
	/**
	 * Clicks the Quick Nav button.
	 * 
	 * @author Darryl Papke
	 */
	public void clickQuickNav() {
		btnQuickNav.syncVisible(5);
		btnQuickNav.click();
	}

	/**
	 * Clicks the close quick nav button.
	 * 
	 * @author Darryl Papke
	 */
	public void closeQuickNav() {
		PageLoaded.isDomComplete(driver, 5);
		btnCloseQuickNav.click();
	}
	
	/**
	 * Checks if the Quick Nav close button is visible.
	 * 
	 * @return <code>true</code> if the Quick Nav close button is 
	 * visible, <code>false</code> otherwise.
	 * @author Darryl Papke
	 */
	public boolean verifyQuickNavCloseButtonIsVisible() {
		PageLoaded.isDomComplete(driver, 5);
		return btnCloseQuickNav.syncVisible(3, false);
	}
	
	/**
	 * Clicks on a cell in the Accounts table from given coordinates.
	 * 
	 * @param row Desired row in which to search for a particular cell
	 * @param column Desired column in which to find the cell
	 * @author Darryl Papke
	 */
	public void selectCell(int row, int column) {
		tblAccounts.clickCell(row, column);
		PageLoaded.isDomComplete(driver, 1);
	}
	
	public String reformatDate(String oldDate) {
		String year = oldDate.substring(0, 4);
		String month = oldDate.substring(5, 7);
		String day = oldDate.substring(8);
				
		String newDate = month+"/"+day+"/"+year;
		return newDate;
	}
	
	/**
	 * Clicks on New Project, fills out required fields and submits form
	 * 
	 * 
	 * @param projName
	 * @param startDate String of 8 numerical characters (i.e. 04062018)
	 * @param endDate String of 8 numerical characters (i.e. 04062018)
	 * @author Christopher Batts
	 */
	public void addProject(String projName, String startDate, String endDate) {
		btnNewProject.click();
		txtProjectName.syncVisible(5);
		txtProjectName.sendKeys(projName);
		txtProjectStart.syncVisible(5);
		txtProjectStart.sendKeys(startDate);
		txtProjectEnd.syncVisible(5);
		txtProjectEnd.sendKeys(endDate);
		btnCreateProject.syncVisible(5);
		btnCreateProject.click();
	}
	
	/**
	 * Clicks a project link in the projects table under an account.
	 * 
	 * @param String Name of project to select
	 * @author Christopher Batts
	 */
	public void selectProject(String projName) {
		PageLoaded.isDomComplete(driver, 5);
		tblProjects.findElement(By.linkText(projName)).syncVisible(5);
		tblProjects.findElement(By.linkText(projName)).click();
	}
	
	/**
	 * Clicks the New Role button under a project
	 * 
	 * @author Christopher Batts
	 */
	public void clickNewRole() {
		btnNewRole.syncVisible(5);
		btnNewRole.click();
	}
	
	/**
	 * Clicks the Billable/Non-Billable toggle
	 * 
	 * @author Christopher Batts
	 */
	public void toggleBill() {
		btnBill.syncVisible(5);
		btnBill.click();
	}
	
	/**
	 * Checks state of Billable/Non-Billable toggle. Returns true if Billable is displayed, otherwise returns false.
	 * 
	 * @return Boolean 
	 * @author Christopher Batts
	 */
	public boolean isBillOn() {
		return !(btnBill.getAttribute("class").contains("off"));
	}
	
	/**
	 * Clicks the +New Role button, fills out required fields, and submits the form.
	 * 
	 * @param roleType String, name of role to select from list
	 * @param maxResources Integer, less than of equal to 30
	 * 
	 * @author Christopher Batts
	 */
	public void createRole(String roleType, int maxResources) {
		clickNewRole();
		if(isBillOn() == true) {
			toggleBill();
		}	
		lstRoleType.syncVisible(5);
		lstRoleType.select(roleType);
		txtMaxResources.sendKeys(Integer.toString(maxResources));
		btnCreateRole.click();
	}
	
	/**
	 * Selects a role by link text from the Project Roles table
	 * 
	 * @param String Role name to be selected
	 * @author Christopher Batts
	 */
	public void selectNewRole(String roleName) {
		tblProjectRoles.findElement(By.linkText(roleName)).click();
	}
	
	/**
	 * Fills out employee assignment form and submits
	 * 
	 * @author Christopher Batts
	 */
	public void assignEmployeeToProject() {
		lstAssignEmployee.syncVisible(5);
		lstAssignEmployee.select("test 123");
		btnCreateFilledRole.click();
	}
	
	public boolean verifyNewProjectIsDisplayed(String projectName) {
		return elmQuickNavProjectList.findElement(By.linkText(projectName)).syncVisible(5,false);
	}
	/**
	 * Clicks options drop down menu on a project page
	 * 
	 * @author Christopher Batts
	 */
	public void clickProjectOptions() {
		btnProjectOptions.syncVisible(5);
		btnProjectOptions.click();
	}
	
	/**
	 * Clicks 'Edit Project' link
	 * 
	 * @author Christopher Batts
	 */
	public void clickEditProject() {
		elmProjectOptionList.findElement(By.partialLinkText("Edit Project")).syncVisible(5);
		elmProjectOptionList.findElement(By.partialLinkText("Edit Project")).click();
	}
	
	/**
	 * Clicks 'Close Project' button
	 * 
	 * @author Christopher Batts
	 */
	public void closeProject() {
		lnkCloseProject.syncVisible(5);
		lnkCloseProject.click();
		driver.switchTo().alert().accept();
	}
}
