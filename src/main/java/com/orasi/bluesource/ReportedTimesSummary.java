package com.orasi.bluesource;

import java.util.List;
import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.orasi.utils.Constants;
import com.orasi.utils.Sleeper;
import com.orasi.utils.TestReporter;
import com.orasi.web.OrasiDriver;
import com.orasi.web.PageLoaded;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Listbox;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class ReportedTimesSummary {
	private OrasiDriver driver = null;
	
	/**Page Elements**/
	@FindBy(xpath = "//div[contains(@class,'time-back')]") private Button btnBack;
	@FindBy(xpath = "//*[@id=\"content\"]/div[6]/table") private Webtable tblTimeSheets;
	@FindBy(xpath = "//*[@id=\"time-entry-table\"]") private Webtable tblTimeEntry;
	@FindBy(xpath = "//select[@id = 'flavor']") private Listbox lstNonBillType;
	@FindBy(xpath = "//*[@id=\"edit_employee_252\"]/div[4]/input[2]") private Button btnSubmitTimeSheet; 
	@FindBy(xpath = "//*[@id=\"content\"]/div[6]/table/tbody/tr[3]/td/table") private Webtable tblTimeSheetSubTable;
	
	/**Constructor**/
	public ReportedTimesSummary(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/
	

	public void clickBackbutton() {
		// TODO Auto-generated method stub
		btnBack.click();
	}
	
	public void addTimeSheetToCurrentPeriod() {
		tblTimeSheets.syncVisible(5);
		PageLoaded.isDomComplete(driver, 5);
		tblTimeSheets.findElement(By.xpath("//a[@class='add-resource-btn btn btn-default btn-xs pull-right']")).click();
	}
	
	public void submitTimeSheet() {
//		PageLoaded.isDomComplete(driver, 5);
		btnSubmitTimeSheet.syncVisible(5,true);
		btnSubmitTimeSheet.jsClick();
		System.out.println("bloop");
	}
	
	public void fillTimeSheet() {
		try {
		PageLoaded.isDomComplete(driver, 5);
		lstNonBillType.syncVisible(5);
		}catch (Exception e) {
			System.out.println("Element not found");
		}
		lstNonBillType.select("Bench");
		List<Element> entryFields = tblTimeEntry.findElements(By.xpath("//input[contains(@class, 'time-entry-hour-fields')]"));
		int count = 0;
		for(WebElement x : entryFields) {
			count ++; 
			if(count < 6 && x.isEnabled()) {
				 x.sendKeys("8");
			 }	 
		}
	}
	/**
	 * Hovers over 'approved' message on the row with the given role name
	 * 
	 * @param String name of the role the time was submitted for
	 * @param String name of approver
	 * @return boolean True if message appears, false otherwise.
	 */
	public boolean checkApprovedByMessage(String role, String approver) {
		int row = tblTimeSheetSubTable.getRowWithCellText(role);
		int column = tblTimeSheetSubTable.getColumnWithCellText("Approved");
		
		tblTimeSheetSubTable.getCell(row, column).findElement(By.linkText("Approved")).focus();
		return tblTimeSheetSubTable.findElement(By.xpath("//*[@id=\"content\"]/div[6]/table/tbody/tr[3]/td/table/tbody/tr[2]/td[11]/div[2]/div[2]")).isDisplayed();
	}
	
}