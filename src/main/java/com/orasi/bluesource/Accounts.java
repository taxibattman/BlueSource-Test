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

public class Accounts {
	private OrasiDriver driver = null;

	/** Page Elements **/
	@FindBy(xpath = "//*[@id='resource-content']/div[2]/p")
	private Element elmNumberPages;
	@FindBy(xpath = "//a[contains(text(),'Account Name')]/../../../..")
	private Webtable tblAccounts;
	@FindBy(id = "preference_resources_per_page")
	private Listbox lstAccountPerPage;
	@FindBy(linkText = "Industry")
	private Link lnkIndustry;
	@FindBy(linkText = "Accounts")
	private Link lnkAccountsTab;
	@FindBy(xpath = "//button[@data-target='#modal_3']")
	private Button btnAssignEmployee;
	@FindBy(xpath = "//th[contains(text(),'Status')]/../../..")
	private Webtable tblProjects;
	@FindBy(xpath = "//button[@data-target='#modal_1']")
	private Button btnAddAccount;
	@FindBy(xpath = "//input[@id='account_name']")
	private Textbox txtAccountName;
	@FindBy(xpath = "//select[@id='account_industry_id']")
	private Listbox lstIndustry;
	@FindBy(xpath = "//input[@value='Create Account']")
	private Button btnCreateAccount;
	@FindBy(xpath = "//*[@id=\"accordion\"]/div/div[5]/div/button[2]")
	private Button btnEditAccount;
	@FindBy(xpath = "//div[@class='btn btn-secondary btn-xs quick-nav']")
	private Button btnQuickNav;
	@FindBy(xpath = "//a[contains(@ng-bind, 'n + 1')]")
	private List<Button> btnPages;
	@FindBy(xpath = "//div[@class='btn btn-xs btn-default pull-right']")
	private Button btnCloseQuickNav;

	/** Constructor **/
	public Accounts(OrasiDriver driver) {
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/** Page Interactions **/

	/*
	 * Click on accounts tab Make sure that the correct page loads author: Daniel
	 * Smith
	 */
	public void click_accounts_tab(String username) {
		if (lnkAccountsTab.isDisplayed() == true) {
			System.out.println(username + " has account permissions");
			lnkAccountsTab.click();
		}

	}

	/**
	 * This method gets the number of rows on the Accounts page
	 * 
	 * @author Paul
	 */
	public int getAccountsTableRows() {
		int rowCount = 0;
		try {
			rowCount = tblAccounts.getRowCount();
		} catch (NullPointerException e) {
			System.out.println("Null pointer exception, no accounts found  \n" + e.getLocalizedMessage());
		}
		return (rowCount - 1);
	}

	/**
	 * This method gets the number of Accounts reported by the page
	 * 
	 * @author Paul
	 * @return
	 */
	public Integer getNumberOfAccounts() {
		String str;
		String delims;
		String[] tokens = null;
		Integer lastItem = null;

		try {
			tblAccounts.syncEnabled(3);
			str = elmNumberPages.getText();
			delims = "[ ]";
			tokens = str.split(delims);
			lastItem = tokens.length;
			return Integer.valueOf(tokens[lastItem - 1]);
		} catch (NoSuchElementException e) {
			System.out.println("No such exception, no accounts found \n" + e.getLocalizedMessage());
		}
		return lastItem;

	}

	/*
	 * Change the number showing for accounts per page to 100 author: Daniel Smith
	 */
	public void accountsPerPage() {
		try {
			lstAccountPerPage.selectValue("100");
		} catch (NoSuchElementException e) {
			System.out.println("No such element found.  \n" + e.getLocalizedMessage());
		} catch (UndeclaredThrowableException e) {
			System.out.println("Undeclared throwable exception  \n" + e.getLocalizedMessage());
		}

	}

	/*
	 * Sort accounts table by industry
	 * 
	 * @author: Daniel Smith
	 */
	public void sort_by_industry() {
		if (lnkIndustry.isDisplayed() == true) {
			System.out.println("Link 'Industry' is present");
			lnkIndustry.click();
		} else
			System.out.println("Link 'Industry' not on current page");

	}

	public void clickAccountLink(String strAccount) {
		String xpathExpression;
		xpathExpression = "//td//a[contains(text(),'" + strAccount + "')]";
		Link lnkAccount = driver.findLink(By.xpath(xpathExpression));
		lnkAccount.click();
	}

	public void clickProjectLink(String strProject) {
		/*
		 * String xpathExpression; xpathExpression = "//td//a[contains(text(),'" +
		 * strProject + "')]"; Link lnkProject =
		 * driver.findLink(By.xpath(xpathExpression)); lnkProject.syncEnabled(5,true);
		 * lnkProject.click();
		 */

		// verify project is in project column
		// get project column
		Integer intColumn = 1;
		Integer intRow = tblProjects.getRowWithCellText(strProject, intColumn);
		tblProjects.findElement(By.linkText(strProject)).click();

		// tblProjects.clickCell(intRow, intColumn);
	}

	public Element verifyProjectLink(String strProject) {
		Integer intColumn = 1;
		Integer intRow = tblProjects.getRowWithCellText(strProject, intColumn);

		tblProjects.findElement(By.linkText(strProject)).click();
		Element eleProject = tblProjects.findElement(By.linkText(strProject));

		return eleProject;

	}

	public void clickSubprojectLink(String strSubProject) {
		String xpathExpression;
		xpathExpression = "//td//a[contains(text(),'" + strSubProject + "')]";
		Link lnkSubProject = driver.findLink(By.xpath(xpathExpression));
		lnkSubProject.syncEnabled(5, true);
		lnkSubProject.click();
	}

	public void clickRoleLink(String strRole) {
		String xpathExpression;
		xpathExpression = "//td//a[contains(text(),'" + strRole + "')]";
		Link lnkRole = driver.findLink(By.xpath(xpathExpression));
		lnkRole.syncEnabled(5, true);
		lnkRole.click();
		PageLoaded.isDomComplete(driver, 5);
	}

	public void clickAssignEmployee() {
		btnAssignEmployee.syncEnabled(5, true);
		btnAssignEmployee.click();
	}

	public void assignEmployee(String strAccount, String strProject, String strSubProject, String strRole,
			String strEmployeeName) {
		FilledRoleForm filledRoleForm = new FilledRoleForm(driver);

		clickAccountLink(strAccount);

		clickProjectLink(strProject);

		clickSubprojectLink(strSubProject);

		clickRoleLink(strRole);

		filledRoleForm.selectEmployee(strEmployeeName);

	}

	public void clickAddAccount() {
		btnAddAccount.click();
	}

	public void setAccountNameTextbox(String strAccountName) {
		txtAccountName.set(strAccountName);
	}

	public void selectIndustry(String strIndustry) {
		lstIndustry.select(strIndustry);
	}

	public void clickCreateAccount() {
		btnCreateAccount.click();
	}

	public boolean verifyAccountLink(String strAccountName) {
		String xpathExpression;

		xpathExpression = "//td//a[contains(text(),'" + strAccountName + "')]";

		Link lnkAccount = driver.findLink(By.xpath(xpathExpression));

		return lnkAccount.isDisplayed();
	}

	public String createAccount() {
		clickAddAccount();

		PageLoaded.isDomComplete(driver, 5);

		String strAccountName = Randomness.randomAlphaNumeric(10);

		setAccountNameTextbox(strAccountName);

		selectIndustry("Other");

		clickCreateAccount();

		// clickAccountLink(strAccountName);

		return strAccountName;

	}

	/**
	 * Checks if the add account button is visible.
	 * 
	 * @return <code>true</code> if the add account button is visible,
	 *         <code>false</code> otherwise.
	 * @author Darryl Papke
	 */
	public boolean verifyAddButtonIsVisible() {
		return btnAddAccount.syncVisible(3, false);
	}

	/**
	 * Checks if the edit account button is visible.
	 * 
	 * @return <code>true</code> if the edit account button is visible,
	 *         <code>false</code> otherwise.
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
	 *         <code>false</code> otherwise.
	 * @author Darryl Papke
	 */
	public boolean verifyQuickNavButtonIsVisible() {
		return btnQuickNav.syncVisible(5, true);
	}

	/**
	 * Goes through each page of accounts and checks if the Quick Nav button is
	 * visible on each page.
	 * 
	 * @return <code>true</code> if the Quick Nav button is on each page,
	 *         <code>false</code> otherwise.
	 * @author Darryl Papke
	 */
	public boolean verifyQuickNavButtonEachPage() {
		boolean answer = false;
		PageLoaded.isDomComplete(driver, 5);
		for (Button page : btnPages) {
			page.syncEnabled(5);
			page.click();
			answer = verifyQuickNavButtonIsVisible();
			if (!verifyQuickNavButtonIsVisible()) {
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
	 * @return <code>true</code> if the Quick Nav close button is visible,
	 *         <code>false</code> otherwise.
	 * @author Darryl Papke
	 */
	public boolean verifyQuickNavCloseButtonIsVisible() {
		PageLoaded.isDomComplete(driver, 5);
		return btnCloseQuickNav.syncVisible(3, false);
	}

	/**
	 * Clicks on a cell in the Accounts table from given coordinates.
	 * 
	 * @param row
	 *            Desired row in which to search for a particular cell
	 * @param column
	 *            Desired column in which to find the cell
	 * @author Darryl Papke
	 */
	public void selectCell(int row, int column) {
		tblAccounts.clickCell(row, column);
		PageLoaded.isDomComplete(driver, 1);
	}

	public void clickAccountsTab() {
		lnkAccountsTab.syncVisible(5);
		lnkAccountsTab.click();
	}

	/**
	 * Traverses all accounts and determines if they have any projects. If a project
	 * exists, the name of the account is added to a list of Strings.
	 * 
	 * @return List<String>, a list of all account names with any projects
	 * @author Christopher Batts
	 */
	public List<String> getAccountsWithProjects() {
		List<String> accountNames = new ArrayList<String>();
		int numAccounts = tblAccounts.getRowCount();
		//go through every account and check for projects
		for (int i = 2; i < numAccounts; i++) {
			PageLoaded.isDomComplete(driver, 5);
			//find account link, get text, and click
			Element account = tblAccounts.findElement(By.xpath("//a[@class='ng-binding']/../../../tr[" + i + "]/td/a"));
			String name = account.getText();
			account.click();
			PageLoaded.isDomComplete(driver,5);
			tblProjects.syncVisible(5);
			//display hidden projects

			tblProjects.findElement(By.xpath("//th[contains(text(),'Status')]/../../../../div")).click();
			//get row count of projects table
			PageLoaded.isDomComplete(driver,5);
			int numProjects = tblProjects.getRowCount();
			//find number of hidden projects and subtract from row count
			int hidProj = findNumHiddenProjects();
			numProjects -= hidProj;
			//if project count is still greater than 0, then traverse all non-hidden projects
			if (numProjects > 0) {
				boolean foundRole = true;
				//traverse remaining projects and click
				for (int k = 1; k < numProjects + 1; k++) {
					PageLoaded.isDomComplete(driver,5);
					tblProjects.findElement(By.xpath("//th[contains(text(),'Status')]/../../../../div")).click();
					PageLoaded.isDomComplete(driver,5);
					tblProjects.findElement(By.xpath("//tbody/tr["+k+"]")).syncVisible(5);
					Element project = tblProjects.findElement(By.xpath("//tbody/tr[" + k + "]"));
					if(!(project.getAttribute("class").equals("sub-project"))) {
						driver.findElement(By.xpath("//th[contains(text(),'Status')]/../../../tbody/tr[" + k + "]/td/a")).click();
						//find Roles table and get row count
						PageLoaded.isDomComplete(driver,5);
						driver.findWebtable(By.xpath("//th[contains(text(),'Rate')]/../../..")).syncVisible(5);
						Webtable tblRoles = driver.findWebtable(By.xpath("//th[contains(text(),'Rate')]/../../.."));
						int numRoles = tblRoles.getRowCount();
						//if row count of Roles is greater than 0 return true, otherwise return false
						if (numRoles > 0) {
							foundRole &= true;
						} else {
							foundRole &= false;
						}
					}
					driver.findLink(By.xpath("//div[@class='breadcrumbs']/a[2]")).click();
					PageLoaded.isDomComplete(driver,5);
				}
				//if all projects have roles then add account name to list of Strings
				if (foundRole == true) {
					accountNames.add(name);
				}

			}
			PageLoaded.isDomComplete(driver,5);
			clickAccountsTab();
		}
		return accountNames;
	}

	/**
	 * Given a list of account names, this returns true if all accounts appear in
	 * QuickNav. Returns false otherwise.
	 * 
	 * @param names,
	 *            a list of account names with projects
	 * @return
	 * @author Christopher Batts
	 */
	public boolean verifyAccountsQuickNavDisplay(List<String> names) {
		boolean foundAll = true;
		for (String string : names) {
			foundAll &= driver
					.findElement(By.xpath("//div[@class='quick-nav-group']/a[contains(text(),'" + string + "')]"))
					.isDisplayed();
		}
		return true;
	}

	public int findNumHiddenProjects() {
		List<Element> list = tblProjects.findElements(By.xpath("//div[@class='pull-right btn-link view-closed']/span"));
		String s = "";
		if (list.size() > 1) {
			Element x = tblProjects.findElement(By.xpath("//div[@class='pull-right btn-link view-closed']/span[2]"));
			String str = x.getText();
			if (!(str.isEmpty())) {
				int index = str.indexOf("closed") - 1;
				s = str.substring(0, index);
			} else {
				return 0;
			}
		}
		return Integer.parseInt(s);
	}
}

