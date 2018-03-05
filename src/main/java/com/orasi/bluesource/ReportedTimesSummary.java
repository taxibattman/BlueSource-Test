5package com.orasi.bluesource;

import java.util.ResourceBundle;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import com.orasi.utils.Constants;
import com.orasi.utils.Sleeper;
import com.orasi.utils.TestReporter;
import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Textbox;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class ReportedTimesSummary {
	private OrasiDriver driver = null;
	
	/**Page Elements**/
	@FindBy(xpath = "//div[contains(@class,'time-back')]") private Button btnBack;
		
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
	
}