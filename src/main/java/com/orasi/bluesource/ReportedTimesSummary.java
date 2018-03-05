package com.orasi.bluesource;

import java.util.List;
import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
	@FindBy(css = "span.approval-section.edit-icon.glyphicon.glyphicon-pencil") private Button btnEdit;
	@FindBy(xpath = "//*[@id=\"edit_employee_260\"]/div[2]") private Button btnAddNonBill;
	@FindBy(id = "comment") private Textbox txtComment;
	@FindBy(xpath = "//div[@class = 'btn btn-primary btn-xs submit-comment']") private Button btnSubmitComment;
	@FindBy(xpath = "//input[@class = 'time-entry-btn time-entry-save-button btn btn-default btn-sm']") private Button btnSave;
	@FindBy(xpath = "//input[@class = 'time-entry-btn time-entry-submit-button btn btn-primary btn-sm']") private Button btnSubmit;
	
	
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
	}
	
	public void fillTimeSheet() {
		try {
		PageLoaded.isDomComplete(driver, 5);
		lstNonBillType.syncVisible(5);
		}catch (Exception e) {
			System.out.println("Element not found");
		}
		List<Element> entryFields = tblTimeEntry.findElements(By.xpath("//input[contains(@class, 'time-entry-hour-fields')]"));
		int count = 0;
		for(WebElement x : entryFields) {
			count ++; 
			if(count == 11 && x.isEnabled()) {
				 x.sendKeys("8");
				 x.click();
				 txtComment.sendKeys("update");
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
		
		Actions action = new Actions(driver);
		WebElement x = tblTimeSheetSubTable.getCell(row, column);
		WebElement y = x.findElement(By.xpath("//div[@class = 'approval-status underline']"));
		WebElement z = tblTimeSheetSubTable.findElement(By.xpath("//div[@class = 'tooltip fade top in']"));
		action.moveToElement(y).pause(5).moveToElement(z).pause(5).build().perform();
		
		
		return tblTimeSheetSubTable.findElement(By.xpath("//div[@class = 'tooltip fade top in']")).isDisplayed();
	}
	
	public void clickEdit() {
		btnEdit.syncVisible(5);
		btnEdit.jsClick();
	}
	
	public void clickAddNonBill() {
		btnAddNonBill.syncVisible(5);
		btnAddNonBill.click();
	}
	
	/**
	 * Determines whether an option is in the Non-bill type based the role name.
	 * @param roleName String the name of the role being searched for
	 * @return boolean Returns true if match is found, false otherwise.
	 * @author Christopher Batts
	 */
	
	public boolean verifyNonBillOption(String roleName) {
		List<WebElement> eList = lstNonBillType.getOptions();
		for(WebElement elem : eList) {
			if(elem.getText().equals(roleName)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Selects a Non-bill type based the role name.
	 * @param roleName String the name of the role to select
	 * @author Christopher Batts
	 */
	public void selectNonBillOption(String roleName) {
		lstNonBillType.select(roleName);
	}
	
	public void submitComment() {
		btnSubmitComment.click();
	}
	
	public void clickSave() {
		btnSave.jsClick();
	}
	
	public void clickSubmit() {
		btnSubmit.jsClick();
	}
}