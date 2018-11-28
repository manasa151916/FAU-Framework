package com.mop.qa.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.mop.qa.testbase.PageBase;

import io.appium.java_client.AppiumDriver;

public class Evernote_Page extends PageBase {
	
	public Evernote_Page(RemoteWebDriver remoteDriver) {
		super(remoteDriver,"");
	}

	public Evernote_Page(AppiumDriver appiumDriver) {
		super(appiumDriver);
	}

	/* page Objects*/
	
	//Sign in page
	
	@FindBy(xpath = "")
	private WebElement Signinbtn;
	
	@FindBy(xpath = "")
	private WebElement email;
	
	@FindBy(xpath = "")
	private WebElement password;
	
	@FindBy(xpath = "")
	private WebElement loginbtn;
	
	
	//Evernote home page
	
	@FindBy(xpath = "")
	private WebElement Savebtn;
	
	//Actions or methods
	
	
}
