package com.orasi.bluesource;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Checkbox;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class EmployeePage {
	private OrasiDriver driver = null;
		
	/**Page Elements**/
	@FindBy(xpath = "//tr[1]//a[@class='glyphicon glyphicon-pencil']") Button btnEditFirstProject;
	@FindBy(xpath = "//div[@id='panel_body_1']//table") Webtable tblProjectInfo;
	@FindBy(xpath = "//button[@data-target='#modal_1']") Button btnEditGeneral;
	@FindBy(xpath = "//div//a[contains(text(),'Deactivate Employee')]") Button btnDeactivateEmployee;
	@FindBy(xpath = "//div[@class='panel-heading']//a[contains(text(),'Deactivate')]") Button btnDeactivate;
	
	@FindBy(linkText = "Manage") private Link lnkManage;
	
	
	@FindBy(xpath = "//h5[contains(text(),'Current Timesheet')]/../div/div[@class='form-container']/form/div[@class='scrollable']/table") private Webtable tblCurrentTimeSheet;
	@FindBy(xpath = "//input[@class='time-entry-btn time-entry-submit-button btn btn-primary btn-sm']") private Button btnSubmitCurrentTimeSheet;
	@FindBy(xpath = "//div[@id='exception-area']") private Element elmExceptions;
	@FindBy(xpath = "//button[@id='cancelExceptions']") private Button btnExceptionCancel;
	@FindBy(xpath = "//button[@id='confirmExceptions']") private Button btnExceptionConfirm;

	
	/**Constructor**/
	public EmployeePage(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/
	public void clickFirstEditProjectButton(){
		btnEditFirstProject.click();
	}

	public boolean verifyProjectAssign(String strProject) {
		// verify project is in project column
		// get project column
		Integer intColumn = tblProjectInfo.getColumnWithCellText("Project", 1);
		Integer intRow = tblProjectInfo.getRowWithCellText(strProject, intColumn);
		
		if (strProject.equals(tblProjectInfo.getCellData(intRow, intColumn))) {
			return true;
		} else {
			return false;
		}
	}

	public boolean verifyStartDate(String strStartDate, String strProject) {
		Integer intProjectColumn = tblProjectInfo.getColumnWithCellText("Project", 1);
		Integer intProjectRow = tblProjectInfo.getRowWithCellText(strProject, intProjectColumn);
		
		Integer intStartDateColumn = tblProjectInfo.getColumnWithCellText("Start Date", 1);
		
		if (strStartDate.equals(tblProjectInfo.getCellData(intProjectRow, intStartDateColumn))){
			return true;
		} else {
			return false;
		}
	}

	public void editGeneralInfo() {
		btnEditGeneral.click();
		
	}		
	
	public void clickDeactivateEmployee() {
		btnDeactivateEmployee.click();
	}
	
	public void clickDeactivate(){
		btnDeactivate.click();
	}
	
	public void clickManage() {
		lnkManage.syncVisible(5);
		lnkManage.jsClick();
	}
	
	public void submitCurrentTimeSheet() {
		btnSubmitCurrentTimeSheet.syncVisible(5);
		btnSubmitCurrentTimeSheet.jsClick();
	}
	
	/**
	 * Fills the current time sheet with less than 40 hours for the week
	 * 
	 * @author Christopher Batts
	 */
	public void fillTimeSheetLessThanFortyHours() {
		List<Element> hourFields = tblCurrentTimeSheet.findElements(By.xpath("//input[@class='time-field time-entry-hour-fields ']"));
		int count = 0;
		for (Element element : hourFields) {
			count ++;
			if(count < 6) {
				element.sendKeys("7");
			}
		}
	}
	
	/**
	 * Verifies that exceptions pop-up is displayed. Returns true if displayed, false otherwise.
	 * @return 
	 * @author Christopher Batts
	 */
	public boolean verifyTimeSheetExceptions() {
		return elmExceptions.syncVisible(5,false);
	}
	
	public void clickConfirm() {
		btnExceptionConfirm.syncVisible(5);
		btnExceptionConfirm.jsClick();
	}
	

}