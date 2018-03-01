package com.orasi.bluesource;

import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class Calendar {
	
	private OrasiDriver driver = null;
	
	@FindBy(linkText = "Calendar") private Link lnkCalendarLink;
	@FindBy(xpath = "//*[@id=\"calendar-params\"]/div[2]") private Button btnProjectFilter;
	
	public Calendar(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	/*
	 * Check that the calendar link is present 
	 * Click the calendar link located in the nav bar
	 * author: Daniel Smith
	 */
	
	public void click_calendar_link(String username)
	{
		
		if (lnkCalendarLink.isDisplayed() == true)
		{
			//click on the calendar link from the navigation menu
			lnkCalendarLink.click();
			System.out.println("Calendar being displayed for " + username);
		}
		else
			System.out.println("Calendar link not found for " + username);
	}
	
	public boolean verifyProjectFilterIsDisplayed() {
		btnProjectFilter.syncVisible(5);
		return btnProjectFilter.isDisplayed();
	}
	
	public void clickProjectFilter() {
		btnProjectFilter.syncVisible(5);
		btnProjectFilter.click();
	}

}