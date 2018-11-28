package com.mop.qa.pageobject;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import com.mop.qa.testbase.PageBase;


public class Outlook_Login_Page extends PageBase{
	
	public Outlook_Login_Page(RemoteWebDriver remoteDriver) {
		super(remoteDriver,"");
	}

	public Outlook_Login_Page(AppiumDriver appiumDriver) {
		super(appiumDriver);
	}

	
	@FindBy(xpath="//android.widget.Button[@text='Get Started']")
	public WebElement GetStartedBtn ; 
	
	@FindBy(name="Hide")
	WebElement hidebtn;
	
	//@FindBy(id="com.microsoft.office.outlook.dawg:id/sso_next_step_button")
	//WebElement emailskipbtn;
	
	@FindBy(name ="Skip")
	WebElement emailskipbtn;
	@FindBy(className = "android.widget.EditText")
	public WebElement Email ; 
	
	@FindBy(xpath = "//android.widget.Button[@text='Continue']")
	public WebElement ContinueBtn ; 
	
	@FindBy(className = "android.widget.EditText")
	public WebElement password ; 
	
	@FindBy(xpath = "//android.widget.Button[@content-desc='Sign in']")
	public WebElement SigninBtn ; 
	
	@FindBy(name= "Skip")
	public WebElement SkipBtn ;
	
	@FindBy(name="Accounts")
	public WebElement AcPgTitle ;
	
		
	public void login(String mail, String pswd) throws Exception   {
		System.out.println("login1ststep");
		if(elementIsEnabled(hidebtn)) {
			click(hidebtn,"hide butn appered and clicked");
		}
		click(GetStartedBtn,"Getstarted button is clicked");
		
		if(elementIsEnabled(emailskipbtn)) {
			click(emailskipbtn,"email selection skipped");
		}else System.out.println("Emailskip  didnt appear");
		
		
		// waiting for the element to appear 		
		waitForVisibilityOfElement(Email);	
		Email.sendKeys(mail);
		System.out.println("email enterted");
		waitForVisibilityOfElement(ContinueBtn);	
		click(ContinueBtn,"Email entered and Continue button clikced");
		System.out.println("continue1 is clicked");
		waitForVisibilityOfElement(password);
		password.sendKeys(pswd);
		System.out.println("pswd is entered");
		
		appiumDriver.navigate().back();
		
		waitForVisibilityOfElement(SigninBtn);
		Thread.sleep(5000);
		click(SigninBtn,"Signin button is clicked");
		System.out.println("signin cliecked is entered");
		Thread.sleep(3000);
		/*waitForVisibilityOfElement(SkipBtn);
		click(SkipBtn,"first skip")
		Thread.sleep(1000);
		waitForVisibilityOfElement(SkipBtn);
		click(SkipBtn,"second skip")*/
		}
	
	public String ValidateLoginPageTitle() {
		return appiumDriver.getTitle();

}
}
