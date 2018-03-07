package com.orasi.bluesource;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class TimesheetLocksPage {
	private OrasiDriver driver = null;

	/** Page Elements **/
	@FindBy(xpath = "//select[@id='date_month']") private Listbox boxMonths;
	@FindBy(xpath = "//input[@class = 'form-control btn btn-primary']") private Button btnCreateLock;
	@FindBy(xpath = "//*[@id='notification-area']") private Element alert;

	/** Constructor **/
	public TimesheetLocksPage(OrasiDriver driver) {
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/** Page Interactions **/
	/**
	 * Selects the given month from the dropdown list
	 * 
	 * @author Zach Apple
	 * @param month
	 *            the month to select from the dropdown
	 */
	public void selectMonth(String month) {
		boxMonths.syncVisible(20);
		boxMonths.select(month);
	}

	/**
	 * Clicks the "Create Lock" button, and accepts the warning message
	 * 
	 * @author Zach Apple
	 */
	public void createLock() {
		btnCreateLock.click();
		driver.switchTo().alert().accept();
	}

	/**
	 * Checks if the green alert is shown with the message "Timesheet Lock Created
	 * 
	 * @author Zach Apple
	 * @return
	 */
	public boolean verifyLockCreateAlert() {
		return alert.isDisplayed() && alert.getText().contains("Timesheet Lock Created");
	}
	
	public void cleanAfterTest(String month) {
		driver.findElement(By.xpath("//td[contains(text(), '"+month+"')]/../td/a")).click();
		driver.switchTo().alert().accept();
	}
}
