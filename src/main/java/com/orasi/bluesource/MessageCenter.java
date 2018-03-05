package com.orasi.bluesource;

import java.util.NoSuchElementException;

import org.openqa.selenium.support.FindBy;

import com.orasi.web.OrasiDriver;
import com.orasi.web.webelements.Button;
import com.orasi.web.webelements.Element;
import com.orasi.web.webelements.Label;
import com.orasi.web.webelements.Link;
import com.orasi.web.webelements.Webtable;
import com.orasi.web.webelements.impl.internal.ElementFactory;

public class MessageCenter {
	private OrasiDriver driver = null;
	
	/**Page Elements**/
	//@FindBy(xpath = "//*[@id='notification_modal']/div/div") private Element elePopOver;
	@FindBy(className = "modal-content") private Element elePopOver;
	@FindBy(xpath = "//*[@id='notification_modal']/div/div/div[1]/button") private Element elmClose;
	@FindBy(xpath = "/html/body/header/div/nav/ul/li[3]") private Link lblMessageCenterTab;
	@FindBy(xpath = "/html/body/header/div/nav/ul/li[2]/a") private Link lblotherMessageCenterTab;
	@FindBy(linkText = "Admin") private Label lblAdminTab;
	@FindBy(id = "message_center_count") private Label lblMessageCount;
	@FindBy(linkText = "View Full Screen") private Button btnViewFull;
	@FindBy(xpath = "//*[@id='content']/h5") private Label lblMessageHeader;
	@FindBy(xpath = "//*[@id='content']/div[2]/div[3]") private Label lblMessageTableHead;
	@FindBy(xpath = "//*[@id='panel_body_2']/div/div/table") private Webtable tabMessageCenter;
	@FindBy(tagName = "table") private Webtable tabByXpath;
	@FindBy(xpath = "//*[@id=\"message_center\"]/div/div/div/div/div/div[3]/a[1]") private Link lnkApprove;
	
	/**Constructor**/
	public MessageCenter(OrasiDriver driver){
		this.driver = driver;
		ElementFactory.initElements(driver, this);
	}
	
	/**Page Interactions**/

	/**
	 * This method checks for the Message Center
	 * @author Paul
	 */
	public boolean messageCenterIsLoaded(){
		return elePopOver.isDisplayed();		
	}
	
	/**
	 * This method closes the Message Center
	 * @author Paul
	 */
	public void closeMessageCenter(){
		boolean messageCenterIsLoaded = messageCenterIsLoaded();
    	
    	if (messageCenterIsLoaded){
    		elmClose.click();
    	}
	}
	
	/*
	 * MessageCenter try / catch statement to 
	 * Checking that the messageCenter popOver is present
	 * if present close the messageCenter
	 * else print to user that the center is not present
	 * @author: Daniel Smith
	 */
	public void check_if_messageCenter_Open()
	{
		
		boolean modal_open = elePopOver.isDisplayed();
			
		if (modal_open == true)
		   {
		   		elmClose.click();
		   		System.out.println("Message center has been closed");
		   }
		else
			System.out.println("Message Center is not present");
	}
	
	/*
	 * Open message center if it is not currently open
	 * author: Daniel Smith
	 */
	public void messageCenter_linkClick_withAdminTab()
	{
		
			boolean adminTab = lblAdminTab.isDisplayed();
			System.out.println("Output from adminTab: " + adminTab);
			
			if(lblAdminTab.isDisplayed() == true)
			{
				lblMessageCenterTab.click();
				System.out.println("Selected by xpath");
			}
	}
	public void messageCenter_linkClick_NoAdminTab()
	{
		lblotherMessageCenterTab.click();
		System.out.println("Alternate selection ");
	}
	
	/*
	 * Check the count for messageCenter
	 * author: Daniel Smith
	 */
	public void messageCount()
	{
		if(lblMessageCount.isDisplayed() == true)
		{
			System.out.println("Number requests waiting in message Center: " + lblMessageCount.getText());
		}
		else
			System.out.println("Message center has no pending requests. ");
	}
	
	/*
	 * Click on the message center view full screen button
	 * author: Daniel Smith
	 */
	public void view_full_btnClick()
	{
		btnViewFull.click();
	}
	
	/*
	 * Get the text of the header of the full screen message center page
	 * author: Daniel Smith
	 */
	public void getText_viewFull()
	{
		System.out.println("Output of header: " + lblMessageHeader.getText());
	}
	
	/*
	 * Get the text header of the table if present
	 * author: Daniel Smith
	 */
	public void getText_tableHead()
	{
		System.out.println("Output from table header: " + lblMessageTableHead.getText());
	}
	
	/*
	 * Get the current table count
	 * author: Daniel Smith
	 */
	public int getTableCount()
	{
		int count = 0;
		
		if (tabMessageCenter.isDisplayed() == true)
		{
			count = tabMessageCenter.getRowCount();
			
			System.out.println("Table has " + count + " rows!");
		}
		else if (tabByXpath.isDisplayed() == true)
			{
				count = tabByXpath.getRowCount();
				System.out.println("Table has " + count + " rows!");
			}
		else
			System.out.println("No table is present");
		
		return count;
	}
	
	public void clickApprove() {
		lnkApprove.jsClick();
	}
	
}