package com.mop.qa.test.bvt;

import org.testng.annotations.Test;

import com.mop.qa.Utilities.ExtentUtility;
import com.mop.qa.Utilities.ReadDataSheet;
import com.mop.qa.pageobject.*;

import com.mop.qa.testbase.TestBase;
import com.relevantcodes.extentreports.LogStatus;


public class Test_MSOutlook_Mobile extends TestBase {
	@Test
		public void MSOutlook_Mobile() throws Exception  {
		 try{
			 //Reading the inputs from excel sheet
			 ReadDataSheet rds= new ReadDataSheet();
			 String url= rds.getValue("DATA", currentTest, "URL");
			 String usrname= rds.getValue("DATA", currentTest, "UserName");
			 String upwd= rds.getValue("DATA", currentTest, "Password");
			 String searchTerm= rds.getValue("DATA", currentTest, "Search");
			 String email = rds.getValue("DATA", currentTest, "email");
			 String emailpwd = rds.getValue("DATA", currentTest, "EmailPassword");
			 //Creating object for the page objects
			 Microsoft_Mobile msout = new Microsoft_Mobile(appiumDriver);
			 Outlook_Login_Page loginObj = new Outlook_Login_Page(appiumDriver);
			 loginObj.login(email,emailpwd);
			 
			 //Opening the required mail
			 msout.selectMail(searchTerm);
			 //Logging into JIRA from Outlook app
			 msout.jiraLogin(usrname,upwd);
			 //Getting the JIRA description after creating a issue
			 String description = msout.createIssue();
			 //Validating whether the issue has been created
			 msout.validateIssue();
			 //Loggin in Jira login in chrome browser
			 initiateWebDriverMobile();
			 Microsoft_Mobile chromebr = new Microsoft_Mobile(appiumDriver);
			 chromebr.enterURL(url);
			 
			String jiraDescription =  chromebr.getJiraDescription();
			boolean jiraStatus = chromebr.validateJiraDescription(description,jiraDescription);
			
			 
		 }catch(Exception e){
			 System.out.println(e);
		 }
	 }
	
	
	 
}
