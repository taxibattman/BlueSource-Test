package com.orasi.bluesource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
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
		
		Actions action = new Actions(driver);
		WebElement x = tblTimeSheetSubTable.getCell(row, column);
		WebElement y = x.findElement(By.xpath("//div[@class = 'approval-status underline']"));
		WebElement z = tblTimeSheetSubTable.findElement(By.xpath("//div[@class = 'tooltip fade top in']"));
		action.moveToElement(y).pause(5).moveToElement(z).pause(5).build().perform();
		
		
		return tblTimeSheetSubTable.findElement(By.xpath("//div[@class = 'tooltip fade top in']")).isDisplayed();
	}
	
	public boolean verifyTotalHours(String day) {
		Webtable tblSubTimeSheets = driver.findWebtable(By.xpath("//div[contains(text(),'"+day+"')]/../../../../.."));

		int col = tblSubTimeSheets.getColumnWithCellText(day, 2);
		int totalRow = tblSubTimeSheets.getRowWithCellText("Total:");
		
		List<Element> dateHours = tblSubTimeSheets.findElements(By.xpath("//div[contains(text(),'"+day+"')]/.."));
		double total = 0;
		double hour = 0;
		for (Element element : dateHours) {
			String text = element.getText();
			if(text.contains("\n")) {
				int b = text.indexOf("\n");
				text = text.substring(0, b);
				hour = Double.parseDouble(text);
				total = total + hour;
			} else {
				hour = 0;
				total = total + hour;
			}	
		}
		
		String tot = Double.toString(total);
		int totalCol = tblSubTimeSheets.getColumnWithCellText(tot, totalRow);
		
		if(tblSubTimeSheets.getCellData(totalRow, totalCol).equals(tot)) {	
			return true;
		}
			return false;
	}
	
	
	
	public boolean verifyAllTotalHours() {
		LocalDate today = LocalDateTime.now().toLocalDate();
		LocalDate Mon,Tues,Wed,Thurs,Fri,Sat,Sun;
		String Monday = " ",Tuesday = " ",Wednesday = " ",Thursday = " ",Friday = " ",Saturday = " ",Sunday = " ";

		switch (today.getDayOfWeek()) {
		case MONDAY:
			Tues = today.plusDays(1);	
			Wed = today.plusDays(2);
			Thurs = today.plusDays(3);
			Fri = today.plusDays(4);
			Sat = today.plusDays(5);
			Sun = today.plusDays(6);
			Monday = today.getMonthValue()+"/"+today.getDayOfMonth();
			Tuesday = Tues.getMonthValue()+"/"+Tues.getDayOfMonth();
			Wednesday = Wed.getMonthValue()+"/"+Wed.getDayOfMonth();
			Thursday = Thurs.getMonthValue()+"/"+Thurs.getDayOfMonth();
			Friday = Fri.getMonthValue()+"/"+Fri.getDayOfMonth();
			Saturday = Sat.getMonthValue()+"/"+Sat.getDayOfMonth();
			Sunday = Sun.getMonthValue()+"/"+Sun.getDayOfMonth();
			break;
		case TUESDAY:
			Mon = today.minusDays(1);
			Wed = today.plusDays(1);
			Thurs = today.plusDays(2);
			Fri = today.plusDays(3);
			Sat = today.plusDays(4);
			Sun = today.plusDays(5);
			Monday = Mon.getMonthValue()+"/"+Mon.getDayOfMonth();
			Tuesday = today.getMonthValue()+"/"+today.getDayOfMonth();
			Wednesday = Wed.getMonthValue()+"/"+Wed.getDayOfMonth();
			Thursday = Thurs.getMonthValue()+"/"+Thurs.getDayOfMonth();
			Friday = Fri.getMonthValue()+"/"+Fri.getDayOfMonth();
			Saturday = Sat.getMonthValue()+"/"+Sat.getDayOfMonth();
			Sunday = Sun.getMonthValue()+"/"+Sun.getDayOfMonth();
			break;
		case WEDNESDAY:
			Mon = today.minusDays(2);
			Tues = today.minusDays(1);
			Thurs = today.plusDays(1);
			Fri = today.plusDays(2);
			Sat = today.plusDays(3);
			Sun = today.plusDays(4);
			Monday = Mon.getMonthValue()+"/"+Mon.getDayOfMonth();
			Tuesday = Tues.getMonthValue()+"/"+Tues.getDayOfMonth();
			Wednesday = today.getMonthValue()+"/"+today.getDayOfMonth();
			Thursday = Thurs.getMonthValue()+"/"+Thurs.getDayOfMonth();
			Friday = Fri.getMonthValue()+"/"+Fri.getDayOfMonth();
			Saturday = Sat.getMonthValue()+"/"+Sat.getDayOfMonth();
			Sunday = Sun.getMonthValue()+"/"+Sun.getDayOfMonth();
			break;
		case THURSDAY:
			Mon = today.minusDays(3);
			Tues = today.minusDays(2);
			Wed = today.minusDays(1);
			Fri = today.plusDays(1);
			Sat = today.plusDays(2);
			Sun = today.plusDays(3);
			Monday = Mon.getMonthValue()+"/"+Mon.getDayOfMonth();
			Tuesday = Tues.getMonthValue()+"/"+Tues.getDayOfMonth();
			Wednesday = Wed.getMonthValue()+"/"+Wed.getDayOfMonth();
			Thursday = today.getMonthValue()+"/"+today.getDayOfMonth();
			Friday = Fri.getMonthValue()+"/"+Fri.getDayOfMonth();
			Saturday = Sat.getMonthValue()+"/"+Sat.getDayOfMonth();
			Sunday = Sun.getMonthValue()+"/"+Sun.getDayOfMonth();
			break;
		case FRIDAY:
			Mon = today.minusDays(4);
			Tues = today.minusDays(3);
			Wed = today.minusDays(2);
			Thurs = today.minusDays(1);
			Sat = today.plusDays(1);
			Sun = today.plusDays(2);
			Monday = Mon.getMonthValue()+"/"+Mon.getDayOfMonth();
			Tuesday = Tues.getMonthValue()+"/"+Tues.getDayOfMonth();
			Wednesday = Wed.getMonthValue()+"/"+Wed.getDayOfMonth();
			Thursday = Thurs.getMonthValue()+"/"+Thurs.getDayOfMonth();
			Friday = today.getMonthValue()+"/"+today.getDayOfMonth();
			Saturday = Sat.getMonthValue()+"/"+Sat.getDayOfMonth();
			Sunday = Sun.getMonthValue()+"/"+Sun.getDayOfMonth();
			break;
		case SATURDAY:
			Mon = today.minusDays(5);
			Tues = today.minusDays(4);
			Wed = today.minusDays(3);
			Thurs = today.minusDays(2);
			Fri = today.minusDays(1);
			Sun = today.plusDays(1);
			Monday = Mon.getMonthValue()+"/"+Mon.getDayOfMonth();
			Tuesday = Tues.getMonthValue()+"/"+Tues.getDayOfMonth();
			Wednesday = Wed.getMonthValue()+"/"+Wed.getDayOfMonth();
			Thursday = Thurs.getMonthValue()+"/"+Thurs.getDayOfMonth();
			Friday = Fri.getMonthValue()+"/"+Fri.getDayOfMonth();
			Saturday = today.getMonthValue()+"/"+today.getDayOfMonth();
			Sunday = Sun.getMonthValue()+"/"+Sun.getDayOfMonth();
			break;
		case SUNDAY:
			Mon = today.minusDays(6);
			Tues = today.minusDays(5);
			Wed = today.minusDays(4);
			Thurs = today.minusDays(3);
			Fri = today.minusDays(2);
			Sat = today.minusDays(1);
			Monday = Mon.getMonthValue()+"/"+Mon.getDayOfMonth();
			Tuesday = Tues.getMonthValue()+"/"+Tues.getDayOfMonth();
			Wednesday = Wed.getMonthValue()+"/"+Wed.getDayOfMonth();
			Thursday = Thurs.getMonthValue()+"/"+Thurs.getDayOfMonth();
			Friday = Fri.getMonthValue()+"/"+Fri.getDayOfMonth();
			Saturday = Sat.getMonthValue()+"/"+Sat.getDayOfMonth();
			Sunday = today.getMonthValue()+"/"+today.getDayOfMonth();
			break;
		default:
			break;
		}
		
		return verifyTotalHours(Monday) && verifyTotalHours(Tuesday) && 
			   verifyTotalHours(Wednesday) && verifyTotalHours(Thursday) && 
			   verifyTotalHours(Friday);
	}
}