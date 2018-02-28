package com.orasi.bluesource;

import javax.wsdl.Message;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class Header {
	private OrasiDriver driver = null;
	
	/**Page Elements**/
	@FindBy(linkText = "Accounts") private Link lnkAccounts;
	@FindBy(linkText = "Logout") private Link lnkLogout;
	@FindBy(xpath = "//li[contains(.,'Employees')]/a") private Link lnkEmployees;
	@FindBy(xpath = "//a[contains(text(),'Project')]") private Link lnkProjemployees;
	@FindBy(xpath = "//a[contains(text(),'Project')]//..//..//..//following-sibling::a") private Link lnkEmployeeSelector;
	@FindBy(linkText = "Admin") private Link lnkAdmin;
	@FindBy(linkText = "Role Types") private Link lnkRoleTypes;
	
	/**Constructor**/
	public Header(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	/**Page Interactions**/

	/**
	 * This method navigates to Accounts page
	 * @author Paul
	 */
	public void navigateAccounts(){
		
		boolean accountBool = lnkAccounts.isDisplayed();
		
		//Displaying the boolean current status
		System.out.println("Output from account bool: " + accountBool);
		
		//Some users do not have the accounts permissions so the tab isnt present
		try
		{
			if(accountBool == true)
			{
				lnkAccounts.click();
				System.out.println("This user has account permissions");
			}
			else if (accountBool == false)
			{
				System.out.println("The user does not have account permissions");
			}
		}
		catch(NoSuchElementException e)
		{
			System.out.println("Element can not be found for accounts tab \n" + e.getLocalizedMessage());
		}
	}

	/**
	 * This method navigates to Employees page
	 * @author Paul
	 */
	public void navigateEmployees() {
		MessageCenter messageCenter = new MessageCenter(driver);
		messageCenter.closeMessageCenter();
		lnkEmployees.click();		
	}
	
	public void navigateProjectEmployees() {
		MessageCenter messageCenter = new MessageCenter(driver);
		messageCenter.closeMessageCenter();
		lnkEmployeeSelector.click();
		lnkProjemployees.click();
		
	}
	
	public void navigateLogout() {
		MessageCenter messageCenter = new MessageCenter(driver);
		messageCenter.closeMessageCenter();
		lnkLogout.click();		
	}
	
	/**
	 * This method navigates to Role Types page
	 * @author Christopher Batts
	 */
	public void navigateRoleTypes() {
		MessageCenter messageCenter = new MessageCenter(driver);
		messageCenter.closeMessageCenter();
		lnkAdmin.click();
		lnkRoleTypes.click();
	}

}