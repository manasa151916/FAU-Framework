package com.mop.qa.pageobject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

import com.mop.qa.Utilities.ExtentUtility;
import com.mop.qa.Utilities.ReadDataSheet;
import com.mop.qa.testbase.PageBase;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
 


public class Microsoft_Mobile extends PageBase {

	public static org.openqa.selenium.Point location = null;
	public static float playx = 0;
	public static float playy = 0;
	public static org.openqa.selenium.Dimension size = null;
	 ReadDataSheet rds= new ReadDataSheet();

	public Microsoft_Mobile(RemoteWebDriver remoteDriver) {
		super(remoteDriver,"");
	}

	public Microsoft_Mobile(AppiumDriver appiumDriver) {
		super(appiumDriver);
	}

	
	@FindBy(xpath = "//android.widget.ImageButton[@content-desc='Message actions']")
	private WebElement options;
	
	@FindBy(xpath = "//android.widget.Button[@text='New Issue']")
	private WebElement addins;
	
	@FindBy(xpath = "(//android.widget.Button[@content-desc='Login'])[1]")
	private WebElement jiraLogin;
	
	@FindBy(xpath = "(//android.widget.Button[@content-desc='Login'])")
	private List<WebElement> jiraLogin1;
	
	@FindBy(xpath = "//android.widget.EditText[@resource-id='login-form-username']")
	private WebElement username;
	
	@FindBy(xpath = "//android.widget.EditText[@resource-id='login-form-password']")
	private WebElement password;
	
	@FindBy(xpath = "//android.widget.Button[@resource-id='login-form-submit']")
	private WebElement login;
	
	@FindBy(xpath = "//android.widget.Button[@content-desc='Allow']")
	private WebElement jiraAllow;
	
	@FindBy(id = "com.microsoft.office.outlook.dawg:id/addin_task_name")
	private WebElement ticketID;
	
	@FindBy(xpath = "//android.widget.Button[@content-desc='Create Issue']")
	private WebElement createIssueBtn;
	
	@FindBy(className = "android.widget.Spinner")
	private List<WebElement> jiradropdown;
	
	
	@FindBy(className = "android.widget.EditText")
	private List<WebElement> jiraEditText;
	
	String value = "";
	
	//Page Object class for browser login
	@FindBy(id = "login-form-username")

	private WebElement loginTxtFld;

	/*@FindBy(xpath = "//android.widget.EditText[@resource-id='login-form-password']")

	private WebElement passwordTxtFld;*/

	@FindBy(id = "login-form-password")
	private WebElement passwordTxtFld;
	
	@FindBy(xpath ="//android.widget.CheckBox[@content-desc='Remember my login on this computer']")
	private WebElement Rememberchkbox;
	
	@FindBy(id = "login-form-submit")
	private WebElement loginBtn;
	
   
	@FindBy(css = "a.header-button.flyout-button")
	private WebElement menuBtn;

	@FindBy(xpath = "//a[text()='Reported by Me']")
	private WebElement reportedByMeTab;
    
   
	@FindBy(xpath = "(//li[@class='issue-entry'])[1]")
	private WebElement selectMail;
    
    @FindBy(xpath = "//div[@class='main-fields-summary']/h2")
	private WebElement validateMail;

    @FindBy(xpath = "//*[@id='summary-val']")
	private WebElement textValid;

	@FindBy(xpath = "//*[@id='header-details-user-fullname']")
	private WebElement logOut;

	@FindBy(xpath = "//*[@id='log_out']")
	private WebElement logOutBtn;
	
	String jiraSummary ;
	String jiraSummaryText;
	
	public void selectMail(String mailTitle) throws Exception {
		
		List<WebElement> mail=appiumDriver.findElements(By.xpath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[1]"));
		ExtentUtility.getTest().log(LogStatus.PASS, "Open outlook",
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		System.out.println(mail.size());
		int i;
		for(i=0;i<mail.size();i++) {
			
			if (mail.get(i).getText().equalsIgnoreCase(mailTitle))
			{
				System.out.println(mail.get(i).getText());
				
				ExtentUtility.getTest().log(LogStatus.PASS, "Open mail "+mail.get(i).getText(),
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
				mail.get(i).click();
				
				Thread.sleep(5000);
				click(options,"Open options");
				Thread.sleep(5000);
				click(addins,"Click Addins");
				Thread.sleep(5000);
				System.out.println(jiraLogin1.size());
				
				break;
				}
				else if (i == mail.size()-1) {
			
			try {
				ExtentUtility.getTest().log(LogStatus.PASS, "Scroll to get the valid mail",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
				//get the screen coordinates
				size = appiumDriver.manage().window().getSize();
				int starty = (int) (size.height * 0.80);
				int endy = (int) (size.height * 0.20);
				int startx = size.width / 2;
				System.out.println("starty = " + starty + " ,endy = " + endy + " , startx = " + startx);
				appiumDriver.swipe(startx, starty, startx, endy, 3000);
				Thread.sleep(2000);
			
				mail=appiumDriver.findElements(By.xpath("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.TextView[1]"));
				i=0;
				Thread.sleep(5000);
				
			} 
			catch (Exception e) {
				
				ExtentUtility.getTest().log(LogStatus.FAIL, "Exception Occurred",
						e.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
				
			}
	
		}

		}
}
	public void jiraLogin(String usrname,String pwd) throws Exception {
		if(jiraLogin1.size()!=0)
		{
		jiraLogin.click();
		Thread.sleep(5000);
		
		username.sendKeys(usrname);
		
		Thread.sleep(4000);
		appiumDriver.navigate().back();
		password.sendKeys(pwd);
		click(Rememberchkbox,"Click on the remember checkbox");		
		Thread.sleep(4000);
		click(login,"Login button is Clicked");
		
		Thread.sleep(4000);
		click(jiraAllow,"Allow is Selected");
		}
	}
	
	
	public String createIssue() throws Exception {
		Thread.sleep(20000);
	System.out.println(jiradropdown.size());
	String jiraCategory = jiradropdown.get(0).getAttribute("name").toString();
	if(!jiraCategory.contains("Contoso Project"))
	{
		click(jiradropdown.get(0),"Select Project");
		Thread.sleep(2000);
		appiumDriver.findElement(By.xpath("//android.view.View[@content-desc='Contoso Project']")).click();;
	}
	Thread.sleep(2000);
	jiradropdown = appiumDriver.findElements(By.className("android.widget.Spinner"));
	System.out.println(jiradropdown.size());
	String jiraBugType = jiradropdown.get(1).getAttribute("name");
	if(!jiraBugType.contains("Bug"))
	{
		click(jiradropdown.get(1),"Select Category");
		Thread.sleep(2000);
		appiumDriver.findElement(By.xpath("//android.view.View[@content-desc='Bug']")).click();
	}
	
	
	System.out.println(jiraEditText.size());
	 jiraSummary = jiraEditText.get(0).getAttribute("name").toString();
	
	
	System.out.println(jiraEditText.size());
	String jiraDescription = jiraEditText.get(1).getAttribute("name").toString();
	System.out.println(jiraDescription);
	
	click(createIssueBtn,"Create Issue");
	return jiraSummary;
	}
	public void validateIssue() throws Exception {
		Thread.sleep(9000);
		String ticket_ID = ticketID.getText();
		if(ticket_ID.contains("created successfully")) {
			ExtentUtility.getTest().log(LogStatus.PASS, "Issue created succesfully "+ticket_ID,
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			
			
		}
	}
	

	public void enterURL(String url) throws Exception {

		enterUrl(url);

		Thread.sleep(5000);

		enterText(loginTxtFld, "Microsoft", "Entering text");

		Thread.sleep(3000);

		enterText(passwordTxtFld, "test", "Entering text");
		
		click(loginBtn, "Login Button");

		Thread.sleep(5000);
		
		clickByJse(menuBtn, "MENU Button");

		Thread.sleep(3000);
		
		clickByJse(reportedByMeTab, "Reported By Me Tab");

		Thread.sleep(3000);

		click(selectMail, "Select mail");

		Thread.sleep(3000);
		
			
		}
		
	public String getJiraDescription() throws Exception
		{
			String textValid = getText(validateMail,"Validate Mail Text");
			System.out.println(textValid);
			
				
				return textValid;
		}
		
	public boolean validateJiraDescription(String description,String jiraDescription) throws Exception
	{
		boolean  flag= false;
		if(description.equals(jiraDescription))
		{
			ExtentUtility.getTest().log(LogStatus.PASS, "Created Issue has been validated in JIRA "+jiraDescription,
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			System.out.println("---------Text Validated for given TicketID---------");
			flag = true;
		}
		else
		{
			ExtentUtility.getTest().log(LogStatus.FAIL, "Created Issue has been validated in JIRA "+jiraDescription,
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			System.out.println("---------Text Validated for given TicketID---------");
			
		}
		return flag;
	}
}
