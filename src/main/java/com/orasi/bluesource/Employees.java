package com.orasi.bluesource;

import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

import com.orasi.utils.Constants;
import com.orasi.utils.Sleeper;
import com.orasi.utils.TestReporter;
import com.orasi.utils.dataHelpers.personFactory.Person;
import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.exceptions.OptionNotInListboxException;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;

import com.orasi.web.webelements.impl.internal.ElementFactory;

public class Employees {
	private OrasiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);
	
	/**Page Elements**/
	@FindBy(xpath = "//button[@data-target='#modal_1']") private Button btnAdd;
	@FindBy(xpath = "//input[@value='Create Employee']") private Button btnCreateEmployee;
	@FindBy(id = "employee_username") private Textbox txtEmployeeUsername;
	@FindBy(id = "employee_first_name") private Textbox txtEmployeeFirst;
	@FindBy(id = "employee_last_name") private Textbox txtEmployeeLast;
	@FindBy(xpath = "//*[@id='resource-content']/div[1]/table") private Webtable tblEmployees;
	@FindBy(tagName = "p") private Label lblAmountInTable;
	@FindBy(xpath = "//*[@id='accordion']/div/div[3]/h4/a") private Button btnManage;
	@FindBy(xpath = "//div//input[@id='search-bar']") private Textbox txtEmployeeSearch;
	@FindBy(xpath = "//*[@id=\"employee_account_permission\"]") private Listbox lstAccountPermission;
	
	/**Constructor**/
	public Employees(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/
	public void employeeSearch(String strSearch){
		txtEmployeeSearch.set(strSearch);
	}
	
	/**
	 * This method clicks the Add Employees page
	 * @author Paul
	 */
	public void clickAddEmployee() {
		btnAdd.syncEnabled(5,true);
		btnAdd.click();		
	}
	
	/**
	 * This method completes the Add Employee modal
	 * @author Paul
	 * @param lastname 
	 * @param firstname 
	 * @param username 
	 */
	public void addEmployeeModal(String username, String firstname, String lastname) {
		
		//complete text fields
		completeRequiredFields(username,firstname,lastname);
		
		//click Create Employee
		clickCreateEmployee();
	}
	
	/**
	 * Use the Person class to complete the employee modal
	 * to add a new employee. 
	 * @param person
	 */
	public void addEmployeeModal(Person person) {
		
		//complete text fields
		completeRequiredFields(person.getUsername(),person.getFirstName(), person.getLastName());
		
		//click Create Employee
		clickCreateEmployee();
	}
	
	/**
	 * This method completes only the required fields to create an employee
	 * @author Paul
	 */
	private void completeRequiredFields(String username,String firstname,String lastname) {
		txtEmployeeUsername.syncEnabled(5,true);
		
		txtEmployeeUsername.set(username);
		txtEmployeeFirst.set(firstname);
		txtEmployeeLast.set(lastname);
	}

	/**
	 * This method clicks the Create Employee button
	 * @author Paul
	 */
	public void clickCreateEmployee() {
		btnCreateEmployee.click();
	}
	
	/*
	 * Get the number of rows that the table has
	 * author: Daniel Smith
	 */
	public int tableCount(int rows, int columns)
	{
		rows = tblEmployees.getRowCount();
		columns = tblEmployees.getColumnCount(1);
		
		System.out.println("Number of rows: " + rows);
		System.out.println("Number of columns: " + columns);
		
		return tableCount(rows, columns);
	}
	
	/**
	 * This method returns the values found in the row containing the employee's username
	 * @author Paul
	 */
	public String getEmployeeInfo(String username,String category) {
		int row = tblEmployees.getRowWithCellText(username);
		int column = tblEmployees.getColumnWithCellText(category);
		
		System.out.println("Row with " + username + " is: " + row);
		System.out.println("Column with " + category + " is: " + column);
		
		return tblEmployees.getCellData(row, column);
	}
	
	/*
	 * Check that the employees table is present
	 * @author: Daniel Smith
	 */
	public boolean employees_table_check() throws InterruptedException
	{
		//hard wait for the page to load 
		Sleeper.sleep(1000);
		
		boolean empTable = tblEmployees.isDisplayed();
		
		//Check that the employees table is present
		if(empTable == true)
		{
			TestReporter.assertEquals(empTable, true, "Table is present");
		}
		else if(empTable == false)
		{
			System.out.println("User is not logged in.. ");
		}
		else
		{
			System.out.println("User is not found or invalid entry");
		}
		return empTable;
	}
	
	/*
	 * Get the number located at the bottom of the table 
	 * showing the total amount of entries
	 * @author: Daniel Smith
	 */
	public void get_table_total()
	{
		System.out.println("Number of entries: " + lblAmountInTable.getText());
	}
	
	/*
	 * Get the information of a certain cell
	 * @author: Daniel Smith
	 */
	public String cell_information(int get_row, int get_column)
	{
		String cell_information = tblEmployees.getCellData(get_row, get_column);
		
		System.out.println("Data in the cell: " + cell_information);
		
		return cell_information;
	}
	
	/*
	 * Select an employee 
	 * @author: Daniel Smith
	 */
	public void select_employee(Integer row, Integer column)
	{
		String message = "On the employee information page.";
		String failMessage = "Button 'Manage' is not found\n";
				
		tblEmployees.clickCell(row, column);

		if(btnManage.isDisplayed() == true)
		{
			TestReporter.assertTrue(btnManage.isDisplayed(), message);
		}
		else
		{
			TestReporter.assertFalse(btnManage.isDisplayed(), failMessage);
		}
	}
	
	public int findEmployeeInTable(String strUsername){
		Integer intRow = null;
		String strLastName = strUsername.substring(strUsername.indexOf(".") + 1);
		
		//find row of lastname
		//intRow = tblEmployees.getRowWithCellText(strLastName);
		for (int i = 1; i < tblEmployees.getRowCount(); i++) {
			if (tblEmployees.getCellData(i, 2).equalsIgnoreCase(strLastName)) {
				intRow = i;
				break;
			}
		}
		return intRow;
	}
	
	public void selectEmployeeByUsername(String strUsername){
		//select_employee(findEmployeeInTable(strUsername),2);
		tblEmployees.syncVisible(5,true);
		//strUsername.replace(" ", ".");
		String strLastName = strUsername.substring(strUsername.indexOf(".") + 1).toUpperCase();
		String xpathexpression = "//a[contains(text(),'" + strLastName + "')]";
		
		Link lnkEmployee = driver.findLink(By.xpath(xpathexpression));
		lnkEmployee.focus();
		lnkEmployee.click();
	}
	
	public void selectEmployeeByName(String strName){
		String xpathexpression = "//a[contains(text(),'" + strName + "')]";
		Link lnkEmployee = driver.findLink(By.xpath(xpathexpression));
		
		lnkEmployee.focus();
		lnkEmployee.click();
	}
	
	public void selectProjectEmployee(){
		selectEmployeeByUsername(userCredentialRepo.getString("PROJECT_USER"));
	}
		
	/*
	 * Checks that the add button is present on the current screen
	 * author: Daniel Smith
	 */
	public void check_add_button()
	{
		if (btnAdd.isDisplayed() == true)
		{
			System.out.println("Add button is present. You can add an employee");
		}
		else if (btnAdd.isDisplayed() == false)
		{
			System.out.println("Add button is not seen on this page.");
		}
		else
			System.out.println("Error finding the add button on the current page.");
	}

	public void VerifyProjectEmployeesPage() {
		// TODO Auto-generated method stub
		
	}

	public void CreateBasicUser(String strUserName, String strFirstName, String strLastName) {
		clickAddEmployee();
		
		addEmployeeModal(strUserName, strFirstName, strLastName);
		
	}
	
	public void DeactivateUser(String strUserName){
		
	}

	public boolean verifyEmployeeByName(String strName) {
		String xpathexpression = "//a[contains(text(),'" + strName + "')]";
		Link lnkEmployee = driver.findLink(By.xpath(xpathexpression));
		
		lnkEmployee.focus();
		boolean blnFoundEmployee = lnkEmployee.isDisplayed();
		return blnFoundEmployee;
		
	}
	
	/**
	 * Checks that a given option is selectable in the Account Permission drop down
	 * in the add employee modal and selects it if possible.
	 * 
	 * @param strOption the Account Permission role you would like to check for.
	 * @return <code>true</code> if the account permission option provided
	 * by the user is available, <code>false</code> otherwise.
	 * @author Darryl Papke
	 */
	public boolean checkAccountPermissionOption(String strOption) {
		try{
			lstAccountPermission.select(strOption);
			return true;
		}
		catch (OptionNotInListboxException e){
			return false;
		}
			
	}
	
	public void clickManage() {
		btnManage.click();
	}

}