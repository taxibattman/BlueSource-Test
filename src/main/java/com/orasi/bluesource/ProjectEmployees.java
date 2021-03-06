package com.orasi.bluesource;

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

public class ProjectEmployees {
	private OrasiDriver driver = null;
	private ResourceBundle userCredentialRepo = ResourceBundle.getBundle(Constants.USER_CREDENTIALS_PATH);
	
	/**Page Elements**/
	
		
	/**Constructor**/
	public ProjectEmployees(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}

	/**Page Interactions**/
	
	public boolean VerifyProjectEmployeesPage() {
		String strUrl;
		
		strUrl = driver.getCurrentUrl();
		if (strUrl.contains("project_employees")) {
			return true;
		} else {
			return false;
		}
		
	}
	
}