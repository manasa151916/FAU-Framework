
package com.mop.qa.testbase;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mop.qa.Utilities.ExtentUtility;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.NetworkConnectionSetting;
import io.appium.java_client.ScrollsTo;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.proxy.CaptureType;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class PageBase {

	public RemoteWebDriver remoteDriver;
	@SuppressWarnings("rawtypes")
	public AppiumDriver appiumDriver;
	protected  String toolName;
	protected static String appType;
	TestBase t;
	public static BrowserMobProxy proxy;
	public String lang;
	public String cname;
	public HSSFWorkbook wb;
	public HSSFSheet ws;
	ChromeOptions options;
	
	

	@SuppressWarnings("rawtypes")
	public PageBase(AppiumDriver driver) {
		this.appiumDriver = driver;
		PageFactory.initElements(appiumDriver, this);
		toolName = "Appium";
		TestBase testBaseObj = new TestBase();
		appType = testBaseObj.appType;

	}

	public PageBase(RemoteWebDriver driver, String lang) {
		this.remoteDriver = driver;
		this.lang= lang;
		PageFactory.initElements(remoteDriver, this);
		toolName = "Selenium";

	}
	
	public void getClassName(String simpleName) {
		// TODO Auto-generated method stub
		this.cname = simpleName;
	}
	
	

	public PageBase() {
		try {
			TestBase testBaseObj = new TestBase();
			if (testBaseObj.toolName.equalsIgnoreCase("selenium")) {
				this.remoteDriver = remoteDriver;
				PageFactory.initElements(remoteDriver, this);
				toolName = "Selenium";

			} else if (testBaseObj.toolName.equalsIgnoreCase("appium")) {
				this.appiumDriver = appiumDriver;
				PageFactory.initElements(appiumDriver, this);
				toolName = "Appium";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int count3 = 1, imagewidth1, imageheight1, imagewidth2, imageheight2;
	public static int n = 0;
	public String text1 = null;
	public String tool = null, webBrowser = null, chromeDriverPath = null, fireFoxDriverPath = null,
			IEDriverPath = null, deviceName = null, appName = null, appiumPort = null, 
			appPackage = null, appActivity = null, Android_Appium_Server_Path = null, appiumPort_Ios = null,
			devicePlatformName_Ios = null, deviceVersion_Ios = null, device_UDID = null, platformName = null,
			applicationPath = null, appiumURL = null, ParentWinhadleMob = null, ParentWinhadle = null,
			mobileCloud = null,bundleId=null;
	
	
	//To launch app in Appium
	@SuppressWarnings("rawtypes")
	public AppiumDriver launchApp(String udid, String locality, String url, String toolname, String appType,
			String port, String platformName,String deviceName, String startURL) throws Exception {
			System.out.println("platformName"+platformName);
			System.out.println("appType"+appType);
		tool = toolName;
		mobileCloud = getAppProperties("mobileCloud");
		bundleId=getAppProperties("bundleId");
		if (locality.equalsIgnoreCase("Hub")) {
			if (tool.equalsIgnoreCase("Appium")) {
				DesiredCapabilities capabilities = new DesiredCapabilities();
				String bmpValue=getAppProperties("BrowserMobProxy");
						
				if ((platformName.equalsIgnoreCase("iOS")) && (appType.equalsIgnoreCase("Native"))) {

					if (System.getProperty("os.name").contains("Win")) {
						appiumURL = "http://127.0.0.1:" + port + "/wd/hub";
					} else {
						appiumURL = "http://0.0.0.0:" + port + "/wd/hub";
					}
					
					capabilities.setCapability("deviceName", deviceName);
					capabilities.setCapability("appium-version", "1.0");
					capabilities.setCapability("automationName", "XCUITest");
					capabilities.setCapability("platformName", platformName);
					capabilities.setCapability("udid", udid);
					capabilities.setCapability("bundleId", bundleId);
					capabilities.setCapability("app", applicationPath);
					if (appiumDriver == null)
						appiumDriver = null;
						remoteDriver = (AppiumDriver) new IOSDriver(new URL(appiumURL), capabilities);

				} else if ((platformName.equalsIgnoreCase("Android")) && (appType.equalsIgnoreCase("Native"))) {

					System.out.println("Android Native");
					appName = getAppProperties("appName");
					appPackage = getAppProperties("appPackage");
					appActivity = getAppProperties("appActivity");
					if (System.getProperty("os.name").contains("Win")) {
						appiumURL = "http://127.0.0.1:" + port + "/wd/hub";
					} else {
						appiumURL = "http://0.0.0.0:" + port + "/wd/hub";
					}
					capabilities.setCapability("appium-version", "1.0");
					capabilities.setCapability("app", appName);
					capabilities.setCapability("platformName", platformName);
					capabilities.setCapability("deviceName", deviceName);
					capabilities.setCapability("udid", udid);
					capabilities.setCapability("appPackage", appPackage);
					capabilities.setCapability("appActivity", appActivity);
						capabilities.setCapability("noReset",true);
						capabilities.setCapability("noSign",true);
					
					appiumDriver = new AndroidDriver(new URL(appiumURL), capabilities);
					appiumDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				}

				else if ((platformName.equalsIgnoreCase("Android")) && (appType.equalsIgnoreCase("Web"))) {
					
					System.out.println("Android Browser");

					if (System.getProperty("os.name").contains("Win")) {
						appiumURL = "http://127.0.0.1:" + port + "/wd/hub";
					} else {
						appiumURL = "http://127.0.0.1:" + port + "/wd/hub";
					}
					capabilities =DesiredCapabilities.android();
					capabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
					capabilities.setCapability("newCommandTimeout", 300);
					capabilities.setCapability("appium-version", "1.0");
					capabilities.setCapability("platformName", platformName);
					capabilities.setCapability("deviceName", deviceName);
					capabilities.setCapability("udid", udid);
					if(bmpValue.equalsIgnoreCase("Yes"))
					{
						proxy = getProxyServer();
						Proxy seleniumProxy = getSeleniumProxy(proxy);
						capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
					}
				  
					appiumDriver = new AndroidDriver(new URL(appiumURL), capabilities);
					appiumDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				}

				else if ((platformName.equalsIgnoreCase("iOS")) && (appType.equalsIgnoreCase("Web"))) {
					System.out.println("iOS Browser");
					if (System.getProperty("os.name").contains("Win")) {
						appiumURL = "http://127.0.0.1:" + port + "/wd/hub";
					} else {
						appiumURL = "http://127.0.0.1:" + port + "/wd/hub";
					}

					DesiredCapabilities cap = new DesiredCapabilities();
					if(bmpValue.equalsIgnoreCase("Yes"))
					{
						proxy = getProxyServer();
						Proxy seleniumProxy = getSeleniumProxy(proxy);
						cap.setCapability(CapabilityType.PROXY, seleniumProxy);
					}
				  
					cap.setCapability("deviceName", deviceName);
					cap.setCapability("automationName", "XCUITest");
					cap.setCapability("browserName", "Safari");
					cap.setCapability("platformName", platformName);
					cap.setCapability("udid", udid);
					cap.setCapability("newCommandTimeout", 20000);
					cap.setCapability("safariInitialUrl", startURL.trim());
					appiumDriver = new IOSDriver(new URL(appiumURL), cap);
					appiumDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				
				}
			}
		} else if (locality.equalsIgnoreCase("Grid")) {
			if (!url.equalsIgnoreCase("NA")) {
				if (tool.equalsIgnoreCase("Appium")) {
					if ((platformName.equalsIgnoreCase("iOS")) && (appType.equalsIgnoreCase("Native"))) {

						if (System.getProperty("os.name").contains("Win")) {
							appiumURL = "http://" + url + port + "/wd/hub";
						} else {
							appiumURL = "http://0.0.0.0:" + port + "/wd/hub";
						}
						DesiredCapabilities capabilities = new DesiredCapabilities();

						capabilities.setCapability("appium-version", "1.0");
						capabilities.setCapability("platformName", platformName);
						capabilities.setCapability("deviceName", deviceName);
						capabilities.setCapability("app", applicationPath);
						capabilities.setCapability("bundleId", bundleId);
						capabilities.setCapability("automationName", "XCUITest");
						capabilities.setCapability("udid", udid);
						if (appiumDriver == null)
							appiumDriver = null;

						remoteDriver = (AppiumDriver) new IOSDriver(new URL(appiumURL), capabilities);

					} else if ((platformName.equalsIgnoreCase("Android")) && (appType.equalsIgnoreCase("Native"))) {

						System.out.println("Android Native");

						appName = getAppProperties("appName");
						appPackage = getAppProperties("appPackage");
						appActivity = getAppProperties("appActivity");
						if (System.getProperty("os.name").contains("Win")) {
							appiumURL = "http://" + url + port + "/wd/hub";

						} else {
							appiumURL = "http://0.0.0.0:" + port + "/wd/hub";
						}

						DesiredCapabilities capabilities = new DesiredCapabilities();
						capabilities.setCapability("appium-version", "1.0");
						capabilities.setCapability("app", appName);
						capabilities.setCapability("platformName", platformName);
						capabilities.setCapability("deviceName", deviceName);
						capabilities.setCapability("udid", udid);
						capabilities.setCapability("appPackage", appPackage);
						capabilities.setCapability("appActivity", appActivity);
						appiumDriver = new AndroidDriver(new URL(url), capabilities);
						// }
						appiumDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					}

					else if ((platformName.equalsIgnoreCase("Android")) && (appType.equalsIgnoreCase("Web"))) {

						if (System.getProperty("os.name").contains("Win")) {
							appiumURL = "http://" + url + port + "/wd/hub";
						} else {
							appiumURL = "http://0.0.0.0:" + port + "/wd/hub";

						}
						DesiredCapabilities capabilities = new DesiredCapabilities();
						capabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
						capabilities.setCapability("newCommandTimeout", "300");
						capabilities.setCapability("appium-version", "1.0");
						capabilities.setCapability("platformName", platformName);
						capabilities.setCapability("deviceName", deviceName);
						capabilities.setCapability("udid", udid);

						appiumDriver = new AndroidDriver(new URL(url), capabilities);
						appiumDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					}

					else if ((platformName.equalsIgnoreCase("iOS")) && (appType.equalsIgnoreCase("Web"))) {
						System.out.println("iOS Browser");

						if (System.getProperty("os.name").contains("Win")) {
							appiumURL = "http://" + url + port + "/wd/hub";
						} else {
							appiumURL = "http://0.0.0.0:" + port + "/wd/hub";
						}

						DesiredCapabilities cap = new DesiredCapabilities();
						cap.setCapability("deviceName", deviceName);
						cap.setCapability("automationName", "XCUITest");
						cap.setCapability("browserName", "Safari");
						cap.setCapability("platformName", platformName);
						cap.setCapability("udid", udid);
						cap.setCapability("newCommandTimeout", 20000);
						cap.setCapability("safariInitialUrl", startURL.trim());
						

						if (appiumDriver == null) {
							appiumDriver = new IOSDriver(new URL(appiumURL), cap);

						}
						appiumDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
					}
				}
			}
		}
		
		else if(locality.equalsIgnoreCase("Cloud")){
			Thread.sleep(20000);
			System.out.println("Mobile Cloud Execution Started");
			String testobjectKey=getAppProperties("testobjectKey");
			String URL = getAppProperties("CloudURL");
			if (platformName.equalsIgnoreCase("Android")) {
			System.out.println("Android Cloud Device");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("testobjectApiKey",testobjectKey);
			// Dynamic device allocation of an iPhone 7, running iOS 10.3 device
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("platformVersion", "6.0.1");
			capabilities.setCapability("deviceName", deviceName);
			
			appiumDriver  = new AndroidDriver(new URL(URL), capabilities);
			Thread.sleep(3000);
			}else if(platformName.equalsIgnoreCase("iOS")){ 
			  DesiredCapabilities capabilities = new DesiredCapabilities();
			      capabilities.setCapability("testobjectApiKey", testobjectKey);
			      capabilities.setCapability("platformName", "iOS");
			      capabilities.setCapability("platformVersion", "10.0");
			      capabilities.setCapability("deviceName", deviceName);
			      capabilities.setCapability("browserName", "Safari");
			      // Set Appium end point
			      System.out.println("Before Driver Application");
			      appiumDriver  = new IOSDriver(new URL(URL), capabilities);
			      Thread.sleep(10000);
			      }
			}

		return appiumDriver;
	}

	//To launch Site in Selenium
	public RemoteWebDriver launchSite(String browser, String locality, String url,String langcode) throws Exception {
		 options = new ChromeOptions();
		String bmpValue=getAppProperties("BrowserMobProxy");
		 if (locality.equalsIgnoreCase("Hub")) {
			if (browser.equalsIgnoreCase("chrome")) {
				if(bmpValue.equalsIgnoreCase("Yes"))
				{
					proxy = getProxyServer();
					Proxy seleniumProxy = getSeleniumProxy(proxy);
					//options.setCapability(CapabilityType.PROXY, seleniumProxy);
				}
				chromeDriverPath = getAppProperties("chromeDriverPath");
				System.setProperty("webdriver.chrome.driver", chromeDriverPath);
				options.addArguments("--disable-extensions");
				options.addArguments("--test-type");
				options.addArguments("start-maximized");
				if(!(langcode=="en")) {
					options.addArguments("--lang="+langcode);
				}
				
				
				remoteDriver = new ChromeDriver(options);
				System.out.println("Chrome started");
			} else if (browser.equalsIgnoreCase("firefox")) {/*
				FirefoxOptions capabilities = null;
				fireFoxDriverPath = getAppProperties("fireFoxDriverPath");
				System.setProperty("webdriver.gecko.driver", fireFoxDriverPath);
				if(bmpValue.equalsIgnoreCase("Yes"))
				{
					proxy = getProxyServer();
					Proxy seleniumProxy = getSeleniumProxy(proxy);
					capabilities= new FirefoxOptions();
					capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
					remoteDriver = new FirefoxDriver(capabilities);
									
				}
				else
				{
					remoteDriver = new FirefoxDriver();
				}
				remoteDriver.manage().window().maximize();

			*/} else if (browser.equalsIgnoreCase("IE")) {/*
				InternetExplorerOptions caps=new InternetExplorerOptions();
				if(bmpValue.equalsIgnoreCase("Yes"))
				{
					proxy = getProxyServer();
					Proxy seleniumProxy = getSeleniumProxy(proxy);
					caps.setCapability(CapabilityType.PROXY, seleniumProxy);
									
				}
				caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				caps.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
				IEDriverPath = getAppProperties("ieDriverPath");
				System.setProperty("webdriver.ie.driver", IEDriverPath);
				remoteDriver = new InternetExplorerDriver(caps);
				remoteDriver.manage().window().maximize();
			*/}
		} else if (locality.equalsIgnoreCase("Grid")) {
			if (!url.equalsIgnoreCase("NA")) {
				System.out.println("remote started");
				if (browser.equalsIgnoreCase("chrome")) {
					String NodeURL;
					NodeURL = url;
					chromeDriverPath = getAppProperties("chromeDriverPath");
					System.setProperty("webdriver.chrome.driver", chromeDriverPath);
					DesiredCapabilities caps = DesiredCapabilities.chrome();
					caps.setBrowserName("chrome");
					caps.setPlatform(Platform.ANY);
					remoteDriver = new RemoteWebDriver(new URL(NodeURL), caps);
					remoteDriver.manage().window().maximize();

				}else if (browser.equalsIgnoreCase("firefox")) {
					String NodeURL;
					NodeURL = url;
					DesiredCapabilities capa = DesiredCapabilities.firefox();
					capa.setBrowserName("firefox");
					capa.setPlatform(Platform.ANY);
					remoteDriver = new RemoteWebDriver(new URL(NodeURL), capa);
					remoteDriver.manage().window().maximize();
				} else if (browser.equalsIgnoreCase("IE")) {
					String NodeURL;
					NodeURL = url;
					DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
					caps.setBrowserName("internet explorer");
					caps.setPlatform(Platform.ANY);
					remoteDriver = new RemoteWebDriver(new URL(NodeURL), caps);
					remoteDriver.manage().window().maximize();

				}
			}

			else {
				String mobileCloud = getAppProperties("mobileCloud");
				// Desktop Cloud in QPass Cloud
				if (mobileCloud.equalsIgnoreCase("No")) {
					DesiredCapabilities capabilities = DesiredCapabilities.firefox();
					capabilities.setPlatform(Platform.MAC);
					capabilities.setVersion("41");
					String userName=getAppProperties("QuserName");
					String password=getAppProperties("Qpassword");
					String packagename=getAppProperties("packagename");
					String servicerequestid=getAppProperties("userName");
					String Url=getAppProperties("URL");
					capabilities.setCapability("username",userName );
					capabilities.setCapability("password", password);
					capabilities.setCapability("packagename", packagename);
					capabilities.setCapability("servicerequestid", servicerequestid);
					remoteDriver = new RemoteWebDriver(new URL(Url),
							capabilities);
				}
				// Mobile Cloud in Perfecto Cloud
				else {
					String mobileHost = getAppProperties("mobileCloudHost");
					DesiredCapabilities capabilities = new DesiredCapabilities("mobileChrome", "", Platform.ANY);
					String userName=getAppProperties("PCuserName");
					String password=getAppProperties("PCpassword");
					String deviceName=getAppProperties("PCdeviceName");
					capabilities.setCapability("user", userName);
					capabilities.setCapability("password", password);
					capabilities.setCapability("deviceName", deviceName);
					//setExecutionIdCapability(capabilities, "https://mobiletestlab.cognizant.com");
					remoteDriver = new RemoteWebDriver(
							new URL("https://" + mobileHost + "/nexperience/perfectomobile/wd/hub"), capabilities);
				}
			}

		}
		else if(locality.equalsIgnoreCase("Cloud")){
			System.out.println("Cloud Desktop Execution started");
			DesiredCapabilities caps = DesiredCapabilities.chrome();
			caps.setCapability(CapabilityType.BROWSER_NAME, "chrome");
			caps.setCapability(CapabilityType.VERSION, 60);
			caps.setCapability(CapabilityType.PLATFORM, "Vista");
			String userName=getAppProperties("UserName");
			String password=getAppProperties("Password");
			caps.setCapability("username",userName );
			caps.setCapability("accesskey", password);
			remoteDriver = new RemoteWebDriver(new URL(url), caps);
			remoteDriver.manage().window().maximize();
			}
		return remoteDriver;
	}

	//To get random Port Number
	public String getPort() throws Exception {
		ServerSocket socket = new ServerSocket(0);
		socket.setReuseAddress(true);
		String port = Integer.toString(socket.getLocalPort());
		socket.close();
		return port;
	}
	
	//To set the Proxy for BrowserMob Proxy
	  public BrowserMobProxy getProxyServer() throws IOException {
	        BrowserMobProxyServer proxy = new BrowserMobProxyServer();
	        String port=getAppProperties("BMPport");
	        proxy.setTrustAllServers(true);
	        proxy.setHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
	        proxy.start(Integer.parseInt(port));
	        return proxy;
	    }

		//To create an instance in the specified Proxy 
	  public static Proxy getSeleniumProxy(BrowserMobProxy proxyServer) throws UnknownHostException {
		  Proxy seleniumProxy =  ClientUtil.createSeleniumProxy(proxyServer);
	       return seleniumProxy;
	    }

	public void checkVideoPlaying(String videoPlayerPath) throws Exception {
		try
		{
			Thread.sleep(1000);
			takeScreenshotVideo("Image1", videoPlayerPath);
			Thread.sleep(3000);
			takeScreenshotVideo("Image2", videoPlayerPath);

			String file1 = "VideoComparison\\Image1.png";
			String file2 = "VideoComparison\\Image2.png";

			processImage(file1, file2);
			ExtentUtility.getTest().log(LogStatus.PASS, "Check Video Playing successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, "Check Video Playing unsuccessful",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		}

	}

	public void takeScreenshotVideo(String screenshotName, String videoPlayerPath) {

		try {
			WebElement ele = remoteDriver.findElement(By.xpath(videoPlayerPath));
			if (ele.isDisplayed()) {
				File screen = (File) ((TakesScreenshot) remoteDriver).getScreenshotAs(OutputType.FILE);

				int ImageWidth = ele.getSize().getWidth();
				int ImageHeight = ele.getSize().getHeight();
				Point point = ele.getLocation();
				int xcord = point.getX();
				int ycord = point.getY();
				BufferedImage img = ImageIO.read(screen);
				BufferedImage dest = img.getSubimage(xcord, ycord, ImageWidth, ImageHeight);
				ImageIO.write(dest, "png", screen);
				FileUtils.copyFile(screen, new File("VideoComparison\\" + screenshotName + ".png"));
				
			}
		} catch (Exception e) {

			try {
				System.out.println("e" + e);
				// rg.logException("Taking Screenshots Fails", e);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			e.printStackTrace();
		}
	}

	public ArrayList<Integer> getResolution(WebElement videoPlayerPathWE) {

		ArrayList<Integer> size = new ArrayList<Integer>();
		try {
			if (videoPlayerPathWE.isDisplayed()) {
				int ImageWidth = videoPlayerPathWE.getSize().getWidth();
				int ImageHeight = videoPlayerPathWE.getSize().getHeight();
				size.add(ImageWidth);
				size.add(ImageHeight);

			}
		} catch (Exception e) {

			try {
				// rg.logException("Taking Screenshots Fails", e);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			e.printStackTrace();
		}

		return size;
	}

	public void waitForVisibilityOfElement(String xpath) throws Exception {

		try {

			switch (toolName) {

			case "Appium":

				WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);

				wait.until(ExpectedConditions.elementToBeClickable(appiumDriver.findElement(By.xpath(xpath))));

				break;

			case "Selenium":

				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 120, 500);

				waitSelenium.until(ExpectedConditions.presenceOfElementLocated((By.xpath(xpath))));

				break;
				

			}
			

		} catch (Exception exc) {

			exc.printStackTrace();

		}

	}

	public void takeScreenshot(String screenshotName, String videoPlayerPath) {

		try {
			WebElement ele = remoteDriver.findElement(By.xpath(videoPlayerPath));
			if (ele.isDisplayed()) {
				File screen = (File) ((TakesScreenshot) remoteDriver).getScreenshotAs(OutputType.FILE);

				int ImageWidth = ele.getSize().getWidth();
				int ImageHeight = ele.getSize().getHeight();
				Point point = ele.getLocation();
				int xcord = point.getX();
				int ycord = point.getY();
				BufferedImage img = ImageIO.read(screen);
				BufferedImage dest = img.getSubimage(xcord, ycord, ImageWidth, ImageHeight);
				ImageIO.write(dest, "png", screen);
				FileUtils.copyFile(screen, new File("PhotoPassScreenshots/" + screenshotName + ".png"));
			}
		} catch (Exception e) {

			try {
				ExtentUtility.getTest().log(LogStatus.FAIL, e + " Taking Screenshots Fails ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
				throw new Exception(e);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			e.printStackTrace();
		}
	}

	public void takeFullScreenshot(String screenshotName) {

		try {
			File screen = (File) ((TakesScreenshot) remoteDriver).getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(screen, new File("PhotoPassScreenshots/" + screenshotName + ".png"));

		} catch (Exception e) {

			try {
				ExtentUtility.getTest().log(LogStatus.FAIL, e + " Taking Screenshots Fails ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
				throw new Exception(e);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			e.printStackTrace();
		}
	}

	public String takeScreenShot() throws IOException {
		Calendar cal = Calendar.getInstance();
		long s = cal.getTimeInMillis();
		File screen = null;
		try {
			switch (toolName) {
			case "Appium":
				screen = (File) ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.FILE);
				break;
			case "Selenium":
				screen = (File) ((TakesScreenshot) remoteDriver).getScreenshotAs(OutputType.FILE);
				break;
			}
			FileUtils.copyFile(screen,
					new File("ReportGenerator/" + ExtentUtility.reportFolder + "/Screenshots/image" + s + ".png"));
		} catch (Exception e) {
			System.out.println(e);
		}

		return (new File("ReportGenerator//" + ExtentUtility.reportFolder + "//Screenshots//image" + s + ".png")
				.getAbsolutePath());

	}

	public void saveImage(String screenshotName, String videoPlayerPath) throws Exception {
		WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 120, 250);
		waitSelenium.until(ExpectedConditions.visibilityOf(remoteDriver.findElement(By.xpath(videoPlayerPath))));
		String s1 = remoteDriver.findElement(By.xpath(videoPlayerPath)).getAttribute("src");
		URL url1 = new URL(s1);
		RenderedImage image1 = ImageIO.read(url1);
		ImageIO.write(image1, "png", new File("PhotoPassScreenshots/" + screenshotName + ".png"));
	}

	public void processImage(String file1, String file2) throws Exception {

		try {
			Image image1 = Toolkit.getDefaultToolkit().getImage(file1);
			Image image2 = Toolkit.getDefaultToolkit().getImage(file2);

			PixelGrabber grab1 = new PixelGrabber(image1, 0, 0, -1, -1, false);
			PixelGrabber grab2 = new PixelGrabber(image2, 0, 0, -1, -1, false);

			int[] data1 = null;

			if (grab1.grabPixels()) {
				int width = grab1.getWidth();
				int height = grab1.getHeight();
				data1 = new int[width * height];
				data1 = (int[]) grab1.getPixels();
			}

			int[] data2 = null;

			if (grab2.grabPixels()) {
				int width = grab2.getWidth();
				int height = grab2.getHeight();
				data2 = new int[width * height];
				data2 = (int[]) grab2.getPixels();
			}

			boolean result = java.util.Arrays.equals(data1, data2);

			if (result == false) {
				System.out.println("Result = Video is playing - PASS ");

			} else {
				System.out.println("Result = Video is not Playing - FALSE");
				ExtentUtility.getTest().log(LogStatus.FAIL, " Result = Video is not Playing - FALSE ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
				throw new Exception(" Result = Video is not Playing - FALSE ");

			}

		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public void validateImage(String imageName1, String imageName2) throws Exception {

		try {
			String file1 = "PhotoPassScreenshots/" + imageName1 + "" + ".png";
			String file2 = "PhotoPassScreenshots/" + imageName2 + "" + ".png";
			Image image1 = Toolkit.getDefaultToolkit().getImage(file1);
			Image image2 = Toolkit.getDefaultToolkit().getImage(file2);

			PixelGrabber grab1 = new PixelGrabber(image1, 0, 0, -1, -1, false);
			PixelGrabber grab2 = new PixelGrabber(image2, 0, 0, -1, -1, false);

			int[] data1 = null;

			if (grab1.grabPixels()) {
				int width = grab1.getWidth();
				int height = grab1.getHeight();
				data1 = new int[width * height];
				data1 = (int[]) grab1.getPixels();
			}

			int[] data2 = null;

			if (grab2.grabPixels()) {
				int width = grab2.getWidth();
				int height = grab2.getHeight();
				data2 = new int[width * height];
				data2 = (int[]) grab2.getPixels();
			}

			boolean result = java.util.Arrays.equals(data1, data2);

			if (result) {
				System.out.println("Result = Image validation - Pass ");
			} else {
				System.out.println("Result = Image validation - Fail ");

			}

		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public void validateSlideImage(String imageName1, String imageName2) throws Exception {

		try {
			String file1 = "PhotoPassScreenshots/" + imageName1 + "" + ".png";
			String file2 = "PhotoPassScreenshots/" + imageName2 + "" + ".png";
			Image image1 = Toolkit.getDefaultToolkit().getImage(file1);
			Image image2 = Toolkit.getDefaultToolkit().getImage(file2);

			PixelGrabber grab1 = new PixelGrabber(image1, 0, 0, -1, -1, false);
			PixelGrabber grab2 = new PixelGrabber(image2, 0, 0, -1, -1, false);

			int[] data1 = null;

			if (grab1.grabPixels()) {
				int width = grab1.getWidth();
				int height = grab1.getHeight();
				data1 = new int[width * height];
				data1 = (int[]) grab1.getPixels();
			}

			int[] data2 = null;

			if (grab2.grabPixels()) {
				int width = grab2.getWidth();
				int height = grab2.getHeight();
				data2 = new int[width * height];
				data2 = (int[]) grab2.getPixels();
			}

			boolean result = java.util.Arrays.equals(data1, data2);

			if (result) {
				System.out.println("Result = Slide Show Image validation - Fail ");

			} else {

				System.out.println("Result = Slide Show Image validation - Pass ");
			}

		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	public String getAppProperties(String key) throws IOException {
		String value = "";
		try {

			FileInputStream fileInputStream = new FileInputStream("data.properties");
			Properties property = new Properties();
			property.load(fileInputStream);

			value = property.getProperty(key);

			fileInputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;

	}

	public void hideKeyboard() throws Exception {
		appiumDriver.hideKeyboard();
		System.out.println("back over");
	}

	public void enterUrl(String url) throws Exception {
		try {
			System.out.println(toolName);
			switch (toolName) {
		
			case "Appium":

				appiumDriver.get(url);
				
				break;
			case "Selenium":
				remoteDriver.get(url);
				assertTrue("Application URL " + url + " has been launched", true);
				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, "Url "+url+"has been launched sucessfully",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, "Url"+url+"is not launched successfully ",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

			exc.printStackTrace();

		}
	}
	
	/*public void enterUrlWithCode(String url, String LangCode) throws Exception {
		try {
			System.out.println(toolName);
			switch (toolName) {
		
			case "Appium":

				appiumDriver.get(url);
				
				break;
			case "Selenium":
				System.out.println("+++++++++++++"+LangCode);
				if(!(LangCode).equals("en")) {
				options.addArguments("--lang="+LangCode);
				remoteDriver = new ChromeDriver(options);
				remoteDriver.get(url);
				assertTrue("Application URL " + url + " has been launched", true);
				}else {
					remoteDriver.get(url);
					assertTrue("Application URL " + url + " has been launched", true);
				}
				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, "Url "+url+"has been launched sucessfully",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, "Url"+url+"is not launched successfully ",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

			exc.printStackTrace();

		}
	}*/

	public void clickPoint(WebElement e, String elementName) throws Exception {
		try
		{
		int xx = e.getLocation().x;
		int yy = e.getLocation().y;

		System.out.println("X Position : " + xx);
		System.out.println("Y Position : " + yy);
		clickCoordinates(xx, yy);
		ExtentUtility.getTest().log(LogStatus.PASS,elementName+ "Point is clicked",
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

	} catch (Exception exc) {
		ExtentUtility.getTest().log(LogStatus.FAIL,elementName+  "not found",
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		exc.printStackTrace();

	}
	}

	public void clickSave(WebElement e, String elementName) throws Exception {
		try {
			switch (toolName) {

			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
				wait.until(ExpectedConditions.elementToBeClickable(e));
				break;
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 500);
				waitSelenium.until(ExpectedConditions.elementToBeClickable(e));
				break;
			}
			Thread.sleep(3000);
			e.click();
			ExtentUtility.getTest().log(LogStatus.PASS, "Click on Element " + elementName + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, elementName + " not found",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(elementName + " not found");

		}

	}

	public void mouseOverClick(WebElement source, String elementName, WebElement target, String elementName1) throws Exception {
		try {
			switch (toolName) {

			case "Appium":
				 
		        Actions action = new Actions(appiumDriver);
		        action.moveToElement(source).build().perform();
		        target.click();
				Thread.sleep(3000);
				break;
			case "Selenium":
				Actions action1 = new Actions(remoteDriver);
		        action1.moveToElement(source).build().perform();
		        target.click();
				Thread.sleep(3000);
				break;
			}

			ExtentUtility.getTest().log(LogStatus.PASS, "Click on Element " + elementName1 + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, elementName1 + " not found",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(elementName1 + " not found");

		}

	}
	
	public void click(WebElement e, String elementName) throws Exception {
		try {
			switch (toolName) {

			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 80, 500);
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(e)));
				
				break;
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 80, 500);
				waitSelenium.until(ExpectedConditions.elementToBeClickable(e));
				break;
			}

			e.click();
		
			ExtentUtility.getTest().log(LogStatus.PASS, "Click on Element " + elementName + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, elementName + " not found",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(elementName + " not found");

		}

	}

	public void clickByJse(WebElement e, String elementName) throws Exception {
		try {
			switch (toolName) {

			case "Appium":
				JavascriptExecutor jse = (JavascriptExecutor) appiumDriver;
				jse.executeScript("arguments[0].click();", e);
				Thread.sleep(3000);
				break;
			case "Selenium":
				JavascriptExecutor jse1 = (JavascriptExecutor) remoteDriver;
				jse1.executeScript("arguments[0].click();", e);
				break;
			}
		

			ExtentUtility.getTest().log(LogStatus.PASS, "Click on Element " + elementName + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		} catch (Exception exc) {
			exc.printStackTrace();
			ExtentUtility.getTest().log(LogStatus.FAIL, elementName + " not found",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(elementName + " not found");

		}

	}
	
	

	public void clickWithoutSS(WebElement e) throws Exception {
		try {
			switch (toolName) {

			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
				wait.until(ExpectedConditions.visibilityOf(e));
				break;
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 250);
				waitSelenium.until(ExpectedConditions.elementToBeClickable(e));
				break;
			}
			e.click();
			ExtentUtility.getTest().log(LogStatus.PASS, "Click on Element " + e + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		} catch (Exception exc) {
			exc.printStackTrace();
			ExtentUtility.getTest().log(LogStatus.FAIL, e + " not found",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(e + " not found");

		}

	}

	public void clickHiddentElement(WebElement e, String elementName) throws Exception {
		try {
			switch (toolName) {

			case "Appium":
				JavascriptExecutor executor = (JavascriptExecutor) appiumDriver;
				executor.executeScript("arguments[0].click();", e);
				break;
			case "Selenium":
				JavascriptExecutor executor1 = (JavascriptExecutor) remoteDriver;
				executor1.executeScript("arguments[0].click();", e);
				break;
			}

			ExtentUtility.getTest().log(LogStatus.PASS, "Click on Element " + e + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		} catch (Exception exc) {
			exc.printStackTrace();
			ExtentUtility.getTest().log(LogStatus.FAIL, elementName + " not found",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(elementName + " not found");

		}

	}

	public void navigateToToC(String strToCLayer, String strToCItem) throws IOException {
		try {
			switch (toolName) {

			case "Selenium":
				WebElement testElement = remoteDriver.findElement(By.cssSelector(
						"div[id='tocItemContainer" + strToCLayer + "'] > div > div > div[title='" + strToCItem + "']"));
				if (!testElement.isDisplayed()) {
					scrollTo(remoteDriver, testElement);
				}
				testElement.click();
				break;
			case "Appium":
				WebElement testElement1 = appiumDriver.findElement(By.cssSelector(
						"div[id='tocItemContainer" + strToCLayer + "'] > div > div > div[title='" + strToCItem + "']"));
				if (!testElement1.isDisplayed()) {
					scrollTo(appiumDriver, testElement1);
				}
				testElement1.click();
				break;

			}
			ExtentUtility.getTest().log(LogStatus.PASS, "Successfully navigated to the Element",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		} catch (Exception e) {
			ExtentUtility.getTest().log(LogStatus.FAIL, "Error in navigating to the Element",
					e.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			System.out.println(e.getMessage());
		}
	}

	public List<WebElement> getList_ByClassName(WebElement element, String byValue) {
		try {
			switch (toolName) {
			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 30, 500);
				wait.until(ExpectedConditions.visibilityOf(element));
				break;
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 250);
				waitSelenium.until(ExpectedConditions.visibilityOf(element));
				break;

			}

		} catch (Exception exc) {

		}
		try {
			if (element.isDisplayed()) {
				List<WebElement> getList = element.findElements(By.className(byValue));

				return getList;
			} else
				return null;
		} catch (Exception exc) {

			return null;

		}
	}

	public void takeScreenshot_Native(WebElement ele, String screenshotName) throws IOException {
		if (ele.isDisplayed()) {
			File screen = (File) ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.FILE);

			int ImageWidth = ele.getSize().getWidth();
			int ImageHeight = ele.getSize().getHeight();
			Point point = ele.getLocation();
			int xcord = point.getX();
			int ycord = point.getY();
			BufferedImage img = ImageIO.read(screen);
			BufferedImage dest = img.getSubimage(xcord, ycord, ImageWidth, ImageHeight);
			ImageIO.write(dest, "png", screen);
			FileUtils.copyFile(screen, new File("NativeApp_Screenshots/" + screenshotName + ".png"));
		}
	}

	public void scrollTo(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("document.getElementById('container id').scrollTop += 250;", "");
	}

	public String getCurrentUrl() throws Exception {
		String url = null;
		try {
			switch (toolName) {

			case "Appium":
				url = appiumDriver.getCurrentUrl();
				break;
			case "Selenium":
				url = remoteDriver.getCurrentUrl();
				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, "Sucess!!Got the Current Url",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		} catch (Exception exc) {
			exc.printStackTrace();
			ExtentUtility.getTest().log(LogStatus.FAIL, exc + "Exception on getting Current Url ",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc);

		}
		return url;

	}

	@SuppressWarnings("unused")
	public String fetchContentFromWebUI(String strCss) throws Exception {
		String strText = "";
		switch (toolName) {
		case "Selenium":
			System.out.println("Fetch content from web page");
			WebElement element = remoteDriver.findElement(By.cssSelector(strCss));
			WebElement testElement = remoteDriver.findElement(By.cssSelector("p[id^='p26']>span[id='word1']"));
			System.out.println("Font face/family in eBook for the text - The Language of Reaching Out: "
					+ testElement.getCssValue("font-family"));
			String UIfontface = testElement.getCssValue("font-family");
			assertTrue("Font face/family in eBook for the text - The Language of Reaching Out: "
					+ testElement.getCssValue("font-family"), true);
			System.out.println("Font size in eBook for the text - The Language of Reaching Out: "
					+ testElement.getCssValue("font-size"));
			String UIfontSize = testElement.getCssValue("font-size");
			assertTrue("Font size in eBook for the text - The Language of Reaching Out: "
					+ testElement.getCssValue("font-size"), true);
			strText = element.getText();
			break;
		case "Appium":
			System.out.println("Fetch content from web page");
			WebElement element1 = appiumDriver.findElement(By.cssSelector(strCss));
			WebElement testElement1 = appiumDriver.findElement(By.cssSelector("p[id^='p26']>span[id='word1']"));
			System.out.println("Font face/family in eBook for the text - The Language of Reaching Out: "
					+ testElement1.getCssValue("font-family"));
			UIfontface = testElement1.getCssValue("font-family");
			assertTrue("Font face/family in eBook for the text - The Language of Reaching Out: "
					+ testElement1.getCssValue("font-family"), true);
			System.out.println("Font size in eBook for the text - The Language of Reaching Out: "
					+ testElement1.getCssValue("font-size"));
			UIfontSize = testElement1.getCssValue("font-size");
			assertTrue("Font size in eBook for the text - The Language of Reaching Out: "
					+ testElement1.getCssValue("font-size"), true);
			strText = element1.getText();
			break;
		}
		return strText;
	}

	public void clickByCSS(String e, String text) throws Exception {
		try {
			switch (toolName) {
			case "Appium":

				break;
			case "Selenium":
				remoteDriver.findElementByCssSelector(e).click();
				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, "Click on Element " + e + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		} catch (Exception exc) {
			exc.printStackTrace();
			ExtentUtility.getTest().log(LogStatus.FAIL, exc + "Exception on clicking webelement",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc);

		}

	}

	public void clickWithoutWait(WebElement e, String elementName) throws Exception {
		try {

			switch (toolName) {

			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
				wait.until(ExpectedConditions.visibilityOf(e));
				break;
			case "Selenium":
				break;
			}
			e.click();
			ExtentUtility.getTest().log(LogStatus.PASS, "Click on Element " + e + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		} catch (Exception exc) {
			exc.printStackTrace();
			ExtentUtility.getTest().log(LogStatus.FAIL, exc + "Exception on clicking webelement",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc);

		}

	}

	public void clickWithoutWait(String xpath, String elementName) throws Exception {
		try {

			switch (toolName) {

			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
				wait.until(ExpectedConditions.visibilityOf(appiumDriver.findElementByXPath(xpath)));
				appiumDriver.findElementByXPath(xpath).click();
				break;
			case "Selenium":
				Thread.sleep(3000);
				remoteDriver.findElementByXPath(xpath).click();
				break;
			}

			ExtentUtility.getTest().log(LogStatus.PASS, "Click on Element " + elementName + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		} catch (Exception exc) {
			exc.printStackTrace();
			ExtentUtility.getTest().log(LogStatus.FAIL, exc + "Exception on clicking webelement",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc);
		}

	}

	public void clickLinkTest(String xpath, String elementName) throws Exception {
		try {
			switch (toolName) {

			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
				appiumDriver.findElementByXPath(xpath).click();
				break;
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 500);

				waitSelenium.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

				remoteDriver.findElementByLinkText(xpath).click();
				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, "Click on Element " + elementName + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, exc + "Exception on clicking webelement",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc);
		}
	}

	public void click(String xpath, String elementName) throws Exception {
		try {
			switch (toolName) {

			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
				appiumDriver.findElementByXPath(xpath).click();
				break;
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 500);

				waitSelenium.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

				remoteDriver.findElementByXPath(xpath).click();
				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, "Click on Element " + elementName + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, exc + "Exception on clicking webelement",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc);
		}
	}

	public void clickbyid(String id, String elementName) throws Exception {
		try {
			switch (toolName) {
			case "Appium":
				appiumDriver.findElementById(id).click();
				break;
			case "Selenium":
				remoteDriver.findElementById(id).click();
				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, "Click on Element " + elementName + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		} catch (Exception exc) {

			exc.printStackTrace();
			ExtentUtility.getTest().log(LogStatus.FAIL, exc + "Exception on clicking webelement",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc);

		}
	}

	public void clickbyClassName(String className, String elementName) throws Exception {
		try {
			switch (toolName) {
			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(className)));
				appiumDriver.findElementByClassName(className).click();

				break;
			case "Selenium":
				remoteDriver.findElementByClassName(className).click();
				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, "Click on Element " + elementName + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		} catch (Exception exc) {

			exc.printStackTrace();
			ExtentUtility.getTest().log(LogStatus.FAIL, exc + "Exception on clicking webelement",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc);

		}
	}

	public void clickByElementName(String name, String elementName) throws Exception {

		try {
			switch (toolName) {
			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
				wait.until(ExpectedConditions.elementToBeClickable(By.name(name)));
				appiumDriver.findElementByName(name).click();
				break;
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 500);
				waitSelenium.until(ExpectedConditions.elementToBeClickable(By.name(name)));
				remoteDriver.findElementByName(name).click();
				break;
			}

			ExtentUtility.getTest().log(LogStatus.PASS, "Click on Element " + elementName + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		} catch (Exception exc) {

			ExtentUtility.getTest().log(LogStatus.FAIL, exc + "Exception on clicking webelement",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc);

		}
	}

	public void clickAlert() throws Exception {

		try {
			switch (toolName) {
			case "Appium":
				Alert a2 = remoteDriver.switchTo().alert();
				a2.accept();
				break;
			case "Selenium":
				Alert a1 = remoteDriver.switchTo().alert();
				a1.accept();

				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, "Click on Alert  successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		} catch (Exception exc) {
			exc.printStackTrace();

			ExtentUtility.getTest().log(LogStatus.FAIL, exc + "Exception on clicking webelement",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc);

		}
	}

	public void dragAndDrop(WebElement e1, WebElement e2) throws Exception {

		try {
			switch (toolName) {
			case "Appium":
				Actions action = new Actions(appiumDriver);
				action.dragAndDrop(e1, e2).perform();

				break;
			case "Selenium":
				Actions action1 = new Actions(remoteDriver);
				action1.dragAndDrop(e1, e2).perform();

				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, "Succesfully Draged and Dropped on element"+e1+" to "+e2,
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		} catch (Exception exc) {
			exc.printStackTrace();

			ExtentUtility.getTest().log(LogStatus.FAIL, exc + "Exception on Drag element",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc);

		}
	}

	public String getPageTitle() throws Exception {

		String pageTitle = "";
		if (toolName.equalsIgnoreCase("Selenium")) {
			pageTitle = remoteDriver.getTitle();
		} else {
			Thread.sleep(5000);
			pageTitle = appiumDriver.getTitle();

		}

		return pageTitle;

	}

	public String getText(WebElement e, String elementName) throws Exception {

		switch (toolName) {
		case "Appium":
			WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
			wait.until(ExpectedConditions.visibilityOf(e));
			break;
		case "Selenium":
			WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 500);
			waitSelenium.until(ExpectedConditions.visibilityOf(e));
			break;
		}
		String text = e.getText();
		ExtentUtility.getTest().log(LogStatus.PASS, "Get the Text for" + elementName + " successful",
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		return text;

	}

	public String getValue(WebElement e, String elementName) throws Exception {

		switch (toolName) {
		case "Appium":
			WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
			wait.until(ExpectedConditions.visibilityOf(e));
			break;
		case "Selenium":
			WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 500);
			waitSelenium.until(ExpectedConditions.visibilityOf(e));
			break;
		}

		String text = e.getAttribute("value");
		ExtentUtility.getTest().log(LogStatus.PASS, "Get the Value for" + elementName + " successful",
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		return text;

	}

	public String getText(WebElement e) throws Exception {
		switch (toolName) {
		case "Appium":
			WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
			wait.until(ExpectedConditions.visibilityOf(e));
			break;
		case "Selenium":
			WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 500);
			waitSelenium.until(ExpectedConditions.visibilityOf(e));
			break;
		}

		String text = e.getText().trim();
		ExtentUtility.getTest().log(LogStatus.PASS, "Get the Text for" + e + " successful",
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		return text;
	}

	public String getText(String xpath) throws Exception {
		String text = null;
		;
		switch (toolName) {
		case "Appium":
			WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
			wait.until(ExpectedConditions.visibilityOf(appiumDriver.findElementByXPath(xpath)));
			text = appiumDriver.findElementByXPath(xpath).getText();

			break;
		case "Selenium":

			JavascriptExecutor jse = (JavascriptExecutor) remoteDriver;
			WebElement element = remoteDriver.findElement(By.xpath(xpath));
			text = jse.executeScript("return arguments[0].text", element).toString();
			break;
		}
		ExtentUtility.getTest().log(LogStatus.PASS, "Get the Text for" + xpath + " successful",
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		return text;
	}

	public String getAttributeValue(WebElement e, String attribute) throws Exception {

		switch (toolName) {
		case "Appium":
			WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
			wait.until(ExpectedConditions.visibilityOf(e));
			break;
		case "Selenium":
			WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 500);
			waitSelenium.until(ExpectedConditions.visibilityOf(e));
			break;
		}

		String text = e.getAttribute(attribute);
		ExtentUtility.getTest().log(LogStatus.PASS, "Get the Attribute Value for" + e + " successful",
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		return text;

	}

	public void clickMultipleButtons(WebElement tab, WebElement pause, String elementName) throws Exception {

		try {
			Thread.sleep(20000);
			if (elementIsDisplayed(pause, "pausebutton")) {
				pause.click();
			} else {
				tab.click();
				pause.click();
			}
			Thread.sleep(10000);

			ExtentUtility.getTest().log(LogStatus.PASS, "Click on Element " + elementName + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		} catch (Exception exc) {
			exc.printStackTrace();
			ExtentUtility.getTest().log(LogStatus.FAIL, exc + "Exception on clicking webelement",
					exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc);
		}

	}

	public void switchToCurrentWindowTitle() throws InterruptedException, IOException {
		try {

			switch (toolName) {
			case "Appium":
				Thread.sleep(10000);
				for (String winHandle : appiumDriver.getWindowHandles()) {
					appiumDriver.switchTo().window(winHandle);
				}

				break;
			case "Selenium":
				for (String winHandle : remoteDriver.getWindowHandles()) {
					remoteDriver.switchTo().window(winHandle);
					Thread.sleep(5000);
				}
				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, "Switched to the Current Window ",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		}

		catch (org.openqa.selenium.NoSuchWindowException exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, "Failed in Switching to the Current Window ",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			exc.printStackTrace();
		}

	}

	public String sendPostRequest(String apiName, String cookie, String payload) {
		StringBuffer jsonString = new StringBuffer();

		try {
			URL url = new URL(
					"https://disneydev7.service-now.com/api/x_wadm_wdpr_cast_c/v1/wdpr_cp_svc_castchoir/" + apiName);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Cookie", cookie);
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			writer.write(payload);
			writer.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				jsonString.append(line);
			}
			br.close();
			connection.disconnect();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return jsonString.toString();
	}
	
	
	public StringBuffer getServiceResponse(String serviceUrl) throws Exception {
		String output = null;
		StringBuffer outputResponse = null;
		try {
			Thread.sleep(5000);
			URL url = new URL(serviceUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("source-appl-id", "6");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("Accept-Resolution", "thumb, medium, high");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			} else {

			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			System.out.println("Response from Server .... \n");
			@SuppressWarnings("resource")
			PrintWriter out = new PrintWriter("Response/" + "response.txt");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				if (output.contains("mediaThumb")) {
				}
				if (output.contains("width")) {
				}

				if (output.contains("height")) {
				}
				out.println(output);
			}

			conn.disconnect();
		} catch (Exception ex) {
		}

		return outputResponse;

	}

	public void switchToWindowTitle() throws Exception {

		try {

			switch (toolName) {
			case "Appium":
				Thread.sleep(10000);
				ParentWinhadleMob = appiumDriver.getWindowHandle();
				for (String winHandle : appiumDriver.getWindowHandles()) {
					appiumDriver.switchTo().window(winHandle);
				}

				break;
			case "Selenium":
				ParentWinhadle = remoteDriver.getWindowHandle();
				for (String winHandle : remoteDriver.getWindowHandles()) {
					remoteDriver.switchTo().window(winHandle);
					
				}
				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, "Switched to the Current Window ",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		}

		catch (org.openqa.selenium.NoSuchWindowException exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, "Failed in Switching to the Current Window ",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			exc.printStackTrace();
		}
	}

	public void switchToParentWindowTitle() throws Exception {

		try {

			switch (toolName) {
			case "Appium":
				Thread.sleep(10000);
				appiumDriver.close();
				appiumDriver.switchTo().window(ParentWinhadleMob);

				break;
			case "Selenium":
				remoteDriver.close();
				remoteDriver.switchTo().window(ParentWinhadle);
				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, "Switched to the Parent Window ",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		}

		catch (org.openqa.selenium.NoSuchWindowException exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, "Failed in Switching to the Parent Window ",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			exc.printStackTrace();
		}
	}

	public void selectOPtionByVisibleText(WebElement e, String text) throws InterruptedException, IOException {
	try
	{
		Thread.sleep(5000);
		e.click();
		Select sl = new Select(e);
		sl.selectByVisibleText(text);
		ExtentUtility.getTest().log(LogStatus.PASS, "Selected Option by Visible Text "+e,
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
	}

	catch (org.openqa.selenium.NoSuchWindowException exc) {
		ExtentUtility.getTest().log(LogStatus.FAIL, e+"not found",
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		exc.printStackTrace();
	}

	}

	public void selectOPtionByIndex(WebElement e, int value) throws InterruptedException, IOException {
		try
		{
		Thread.sleep(3000);
		Select sl = new Select(e);
		sl.selectByIndex(value);
		ExtentUtility.getTest().log(LogStatus.PASS, "Selected Option by Index "+e,
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
	}

	catch (org.openqa.selenium.NoSuchWindowException exc) {
		ExtentUtility.getTest().log(LogStatus.FAIL, e+"not found",
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		exc.printStackTrace();
	}

	}

	public void switchToFrame(String frameId) throws Exception {

		try {

			switch (toolName) {
			case "Appium":
				Thread.sleep(5000);
				appiumDriver.switchTo().frame(frameId);

				break;
			case "Selenium":

				remoteDriver.switchTo().frame(frameId);
				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, "Switched to Frame"+frameId,
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		}

		catch (org.openqa.selenium.NoSuchWindowException exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, frameId+"not found",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			exc.printStackTrace();
		}

	}

	public void switchToFrame(int frameId) throws Exception {

		try {

			switch (toolName) {
			case "Appium":
				Thread.sleep(5000);
				appiumDriver.switchTo().frame(frameId);

				break;
			case "Selenium":

				remoteDriver.switchTo().frame(frameId);
				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, "Switched to Frame"+frameId,
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		}

		catch (org.openqa.selenium.NoSuchWindowException exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, frameId+"not found",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			exc.printStackTrace();
		}

	}

	public void switchToParentFrame() throws Exception {

		try {

			switch (toolName) {
			case "Appium":
				Thread.sleep(5000);
				appiumDriver.switchTo().parentFrame();

				break;
			case "Selenium":

				remoteDriver.switchTo().parentFrame();
				break;

			}
			ExtentUtility.getTest().log(LogStatus.PASS, "Switched to Parent Frame",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		}

		catch (org.openqa.selenium.NoSuchWindowException exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, "Parent Frame not found",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			exc.printStackTrace();
		}

	}

	public String getParentWindow() throws Exception {
		String parentWindow = null;
		if (toolName.equalsIgnoreCase("Appium")) {
			parentWindow = appiumDriver.getWindowHandle();
		} else if (toolName.equalsIgnoreCase("Selenium")) {
			parentWindow = remoteDriver.getWindowHandle();
		}
		return parentWindow;
	}

	public void switchToParentWindow(String parentWindow) throws Exception {
		try
		{
		if (toolName.equalsIgnoreCase("Appium")) {
			appiumDriver.close();
			appiumDriver.switchTo().window(parentWindow);
		} else if (toolName.equalsIgnoreCase("Selenium")) {
			remoteDriver.close();
			remoteDriver.switchTo().window(parentWindow);
		}
		ExtentUtility.getTest().log(LogStatus.PASS, "Switched to Parent Window"+parentWindow,
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
	}

	catch (org.openqa.selenium.NoSuchWindowException exc) {
		ExtentUtility.getTest().log(LogStatus.FAIL, "Parent Window not found",
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		exc.printStackTrace();
	}

	}

	public WebElement getElement(String xpath) throws Exception {
		switch (toolName) {
		case "Appium":
			WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
			wait.until(ExpectedConditions.visibilityOf(appiumDriver.findElementByXPath(xpath)));
			WebElement we = appiumDriver.findElementByXPath(xpath);
			return we;
		case "Selenium":
			WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 500);
			waitSelenium.until(ExpectedConditions.visibilityOf(remoteDriver.findElementByXPath(xpath)));
			WebElement weSelenium = remoteDriver.findElementByXPath(xpath);
			return weSelenium;
		}

		ExtentUtility.getTest().log(LogStatus.PASS, " Get text on webelement successful",
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		return null;

	}

	public boolean verifyText(WebElement e, String value) throws Exception {
		switch (toolName) {
		case "Appium":
			WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
			wait.until(ExpectedConditions.visibilityOf(e));
			break;
		case "Selenium":
			WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 500);
			waitSelenium.until(ExpectedConditions.visibilityOf(e));
			break;
		}
		if (e.getText().contains(value)) {
			ExtentUtility.getTest().log(LogStatus.PASS, "  Verified Element successful ",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			return true;
		} else {
			ExtentUtility.getTest().log(LogStatus.FAIL, "Exception on Verified webelement",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			return false;
		}

	}

	public void enterText(WebElement element, String cred, String elementName) throws Exception {
		try {
			switch (toolName) {
			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
				wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
				break;
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 500);
				waitSelenium.until(ExpectedConditions.visibilityOf(element));
				break;
			}

			element.clear();
			element.sendKeys(cred);
			ExtentUtility.getTest().log(LogStatus.PASS, " Enter text in " + elementName + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		} catch (Exception exc) {

			ExtentUtility.getTest().log(LogStatus.FAIL, "Enter text failed",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc + " Exception on Verified webelement");

		}

	}

	public void assertTrue(String message) throws Exception {
		ExtentUtility.getTest().log(LogStatus.PASS, message,
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
	}

	public void assertFalse(String message) throws Exception {
		ExtentUtility.getTest().log(LogStatus.FAIL, message,
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		throw new Exception(message);
	}

	public boolean navToSubMenu(String subMenu) throws Exception {
		try {
			switch (toolName) {

			case "Appium":
				System.out.println("Navigate to" + subMenu + "menu.");
				appiumDriver.findElement(By.cssSelector("div[title='" + subMenu + "']")).click();
				System.out.println("Navigated to sub-menu '" + subMenu + "'");
				break;
			case "Selenium":
				System.out.println("Navigate to" + subMenu + "menu.");
				remoteDriver.findElement(By.cssSelector("div[title='" + subMenu + "']")).click();
				System.out.println("Navigated to sub-menu '" + subMenu + "'");
				break;
			}

		} catch (Exception exc) {
			System.out.println("Unable to navigate to the sub menu, due to - " + exc.getMessage());
			exc.printStackTrace();
			return false;
		}
		return true;
	}

	public void switchFrame() throws IOException {
		try
		{
		if (remoteDriver.toString().contains("chrome")) {
			remoteDriver.switchTo().frame(2);
		} else {
			remoteDriver.switchTo().frame(0);
		}
		ExtentUtility.getTest().log(LogStatus.PASS, " Switched to Frame successfully",
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
	} catch (Exception exc) {

		ExtentUtility.getTest().log(LogStatus.FAIL, "Failed in switching to Frame",
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

	}
	}

	public void clearSystemCache() throws Exception {
		try {
			switch (toolName) {
			case "Appium":
				appiumDriver.manage().deleteAllCookies();
				break;
			case "Selenium":
				remoteDriver.manage().deleteAllCookies();
				break;
			}
		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, " Clear Cookies ",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc + " Clear Cookies ");
		}

	}

	public boolean elementIsDisplayed(WebElement e, String ElementName) throws Exception {
		try {
			switch (toolName) {
			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
				wait.until(ExpectedConditions.visibilityOf(e));
				break;
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 250);
				waitSelenium.until(ExpectedConditions.visibilityOf(e));
				break;

			}
			ExtentUtility.getTest().log(LogStatus.PASS, " Element "+ElementName+"is displayed ",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			return true;
			
		} catch (Exception exc) {

			ExtentUtility.getTest().log(LogStatus.FAIL, ElementName+"not found",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			return false;

		}

	}

	public void isDisplayed(WebElement e, String ElementName) throws Exception {
		try {
			switch (toolName) {
			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 30, 500);
				wait.until(ExpectedConditions.visibilityOf(e));
				break;
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 30, 500);
				waitSelenium.until(ExpectedConditions.visibilityOf(e));
				break;

			}
			ExtentUtility.getTest().log(LogStatus.PASS, ElementName + " is displayed",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, exc.toString(),
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc);
		}
	}

	public boolean elementIsDisplayed(WebElement e) throws Exception {
		try {
			switch (toolName) {
			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 30, 500);
				wait.until(ExpectedConditions.visibilityOf(e));
				break;
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 30, 500);
				waitSelenium.until(ExpectedConditions.visibilityOf(e));
				break;

			}
			ExtentUtility.getTest().log(LogStatus.PASS, " Element "+e+"is displayed ",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			return true;
			
		} catch (Exception exc) {

			ExtentUtility.getTest().log(LogStatus.FAIL, e+"not found",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			return false;
		}

	}

	public boolean elementIsEnabled(WebElement e) throws Exception {
		try {
			switch (toolName) {
			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 30, 500);
				wait.until(ExpectedConditions.visibilityOf(e));
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 500);
				waitSelenium.until(ExpectedConditions.visibilityOf(e));
			}
			ExtentUtility.getTest().log(LogStatus.PASS, " Element "+e+"is enabled ",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			return true;
		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, " Get element visibilty failed ",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			return false;
		}
	}

	public boolean elementIsDisplayed(String xpath, String ElementName) throws Exception {

		try {
			switch (toolName) {
			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 30, 500);
				wait.until(ExpectedConditions.visibilityOf(appiumDriver.findElementByXPath(xpath)));
				if (appiumDriver.findElementByXPath(xpath).isDisplayed()) {
					ExtentUtility.getTest().log(LogStatus.PASS, ElementName + " is displayed",
							ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

				}
				break;
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 30, 500);
				waitSelenium.until(ExpectedConditions.visibilityOf(remoteDriver.findElementByXPath(xpath)));
				if (remoteDriver.findElementByXPath(xpath).isDisplayed()) {
					ExtentUtility.getTest().log(LogStatus.PASS, ElementName + " is displayed",
							ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
				}
				break;
			}
			return true;
		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, " Element visibilty failed ",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			return false;

		}
		

	}

	public boolean elementIsDisplayed(String xpath) throws Exception {

		try {
			switch (toolName) {
			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 30, 500);
				wait.until(ExpectedConditions.visibilityOf(appiumDriver.findElementByXPath(xpath)));

				break;
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 30, 500);
				waitSelenium.until(ExpectedConditions.visibilityOf(remoteDriver.findElementByXPath(xpath)));

				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, xpath + " is displayed",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, xpath + " not found",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			return false;

		}
		return true;

	}

	public boolean elementIsDisplayedByName(String name) throws Exception {

		try {
			switch (toolName) {
			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 30, 500);
				wait.until(ExpectedConditions.visibilityOf(appiumDriver.findElementByName(name)));
				break;
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 500);
				waitSelenium.until(ExpectedConditions.visibilityOf(remoteDriver.findElementByName(name)));
				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, name + " is displayed",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, name + " not found",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			exc.printStackTrace();
			return false;
		}
		return true;

	}

	@SuppressWarnings("unchecked")
	public void scroll(String key) throws Exception {
		try
		{
		switch (toolName) {
		case "Appium":
			for (int i = 0;; i++) {
				boolean shouldBreak = false;

				List<WebElement> listObject = appiumDriver.findElements(By.name("dropdownViewCell_" + i + ""));
				while ((listObject.size()) == 0)
					break;

				for (WebElement wb : listObject) {

					if ((wb.getText().equalsIgnoreCase(key))) {
						wb.click();
						shouldBreak = true;
						break;
					}

				}
				if (shouldBreak)
					break;

			}

		case "Selenium":
			for (int i = 0;; i++) {
				boolean shouldBreak = false;

				List<WebElement> listObject = remoteDriver.findElements(By.name("dropdownViewCell_" + i + ""));
				while ((listObject.size()) == 0)
					break;

				for (WebElement wb : listObject) {

					if ((wb.getText().equalsIgnoreCase(key))) {
						wb.click();
						shouldBreak = true;
						break;
					}

				}
				if (shouldBreak)
					break;

			}

		}
		ExtentUtility.getTest().log(LogStatus.PASS, "Scrolled to Particular Element"+key,
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		}
		catch(Exception e)
		{
			ExtentUtility.getTest().log(LogStatus.FAIL, "Failed!!Scrolling to Particular Element"+key,
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		}

	}

	@SuppressWarnings("serial")
	public void clickCoordinates(final int x, final int y) throws IOException {
		try
		{
			appiumDriver.executeScript("mobile: tap", new HashMap<String, Integer>() {
				{
					put("tapCount", (int) 1);
					put("touchCount", (int) 1);
					put("duration", (int) 0.5);
					put("x", x);
					put("y", y);
				}
			});
			ExtentUtility.getTest().log(LogStatus.PASS, "Clicked Coordinates"+x+","+y+"successfully",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			}
			catch(Exception e)
			{
				ExtentUtility.getTest().log(LogStatus.FAIL, "Failed!!Clicking Coordinates"+x+","+y,
						ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			}
		}

	public void keyBoardActions(String text) {
		switch (toolName) {
		case "Appium":
			if (text.equalsIgnoreCase("return"))
				appiumDriver.findElementByName(text).click();
			else {
				for (int i = 0; i < text.length(); i++) {
					String alp = text.substring(i, i + 1);
					appiumDriver.findElementByName(alp).click();
				}
			}
		case "Selenium":
			if (text.equalsIgnoreCase("return"))
				remoteDriver.findElementByName(text).click();
			else {
				for (int i = 0; i < text.length(); i++) {
					String alp = text.substring(i, i + 1);
					remoteDriver.findElementByName(alp).click();
				}
			}
		}

	}

	@SuppressWarnings("rawtypes")
	public void scrollToExact(String key) throws Exception {
		try {
			switch (toolName) {
			case "Appium":
				((ScrollsTo) appiumDriver).scrollToExact(key);
				break;
			case "Selenium":
				((ScrollsTo) remoteDriver).scrollToExact(key);
				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, "scroll to element " + "" + key + "" + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, "Exception on scroll to element" + key,
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc + "Exception on scroll to element" + key);
		}

	}

	public void scrollPage(String side, int key) throws Exception {
		try {
			switch (toolName) {
			case "Appium":

				break;
			case "Selenium":
				JavascriptExecutor jse = (JavascriptExecutor) remoteDriver;
				if (side.equalsIgnoreCase("Up"))
					jse.executeScript("scroll(0, " + key + ");");
				
				else {
					int key1 = (-key);
					jse.executeScript("scroll(0, " + key1 + ");");
				}
				break;
			}
			
			  ExtentUtility.getTest().log(LogStatus.PASS, "scroll to element  successful",ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			 
		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, " Exception on scroll to element ",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc + "Get element visibilty failed ");

		}

	}

	// Nfr scenarios
	public void accessNotification() {
		@SuppressWarnings("rawtypes")
		AndroidDriver android = (AndroidDriver) this.appiumDriver;
		android.openNotifications();

	}

	public void setDataConnection(boolean enable) {
		// TODO Auto-generated method stub
		if (this.appiumDriver instanceof AndroidDriver) {
			@SuppressWarnings("rawtypes")
			AndroidDriver android = (AndroidDriver) this.appiumDriver;
			NetworkConnectionSetting setting = android.getNetworkConnection();
			setting.dataEnabled();
			android.setNetworkConnection(setting);
			System.out.println("Current Status of data network:" + setting.dataEnabled());
		}
	}

	@SuppressWarnings("rawtypes")
	public void setAirplaneConnection(boolean enable) {
		// TODO Auto-generated method stub
		if (this.appiumDriver instanceof AndroidDriver) {
			AndroidDriver android = (AndroidDriver) this.appiumDriver;
			NetworkConnectionSetting setting = android.getNetworkConnection();

			setting.setAirplaneMode(true);

			android.setNetworkConnection(setting);
		}
	}

	public void setWifiConnection(boolean enable) {
		// TODO Auto-generated method stub
		System.out.println("before +++++");
		if (this.appiumDriver instanceof AndroidDriver) {
			System.out.println("inside +++++");
			@SuppressWarnings("rawtypes")
			AndroidDriver android = (AndroidDriver) this.appiumDriver;
			NetworkConnectionSetting setting = android.getNetworkConnection();
			setting.setWifi(enable);
			android.setNetworkConnection(setting);
			System.out.println("Current Status of wifi:" + setting.wifiEnabled());
		}
	}

	public void keyboardActions(WebElement e, Keys key) throws IOException {

		try {
			switch (toolName) {

			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
				wait.until(ExpectedConditions.visibilityOf(e));
				break;
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 60, 500);
				waitSelenium.until(ExpectedConditions.visibilityOf(e));
				waitSelenium.until(ExpectedConditions.elementToBeClickable(e));
				break;
			}
			e.sendKeys(key);
			ExtentUtility.getTest().log(LogStatus.PASS, "Keys"+key+" Pressed successfully",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, "Exception on pressing key" + key,
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		}

	}

	public void dragAndDropElement(String dragFromXpath, String dragToXpath, int xOffset, int yOffset)
			throws Exception {
		try
		{
		WebElement dragFrom = remoteDriver.findElementByXPath(dragFromXpath);
		WebElement dragTo = remoteDriver.findElementByXPath(dragToXpath);
		System.out.println(
				"dragFrom =" + dragFrom + " dragTo = " + dragTo + "xOffset = " + xOffset + " yOffset =" + yOffset);
		Robot robot = new Robot();
		robot.setAutoDelay(500);
		robot.mouseMove(200, 200);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		org.openqa.selenium.Dimension fromSize = dragFrom.getSize();
		org.openqa.selenium.Dimension toSize = dragTo.getSize();
		int xCentreFrom = fromSize.width / 2;
		int yCentreFrom = fromSize.height / 2;
		int xCentreTo = toSize.width / 2;
		int yCentreTo = toSize.height / 2;
		Point toLocation = dragTo.getLocation();
		Point fromLocation = dragFrom.getLocation();
		System.out.println(fromLocation.toString());
		toLocation.x += xOffset + xCentreTo;
		toLocation.y += yOffset + yCentreTo;
		fromLocation.x += xOffset + xCentreFrom;
		fromLocation.y += yOffset + yCentreFrom;
		System.out.println(fromLocation.toString());
		robot.mouseMove(fromLocation.x, fromLocation.y);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(((toLocation.x - fromLocation.x) / 2) + fromLocation.x,
				((toLocation.y - fromLocation.y) / 2) + fromLocation.y);
		for (double i = (toLocation.x / 2); i < toLocation.x;) {
			robot.mouseMove((int) i, toLocation.y);
			i = i + 10;
		}
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		ExtentUtility.getTest().log(LogStatus.PASS, " Drag element successful",
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		}
		catch(Exception e)
		{
			ExtentUtility.getTest().log(LogStatus.FAIL, " Drag element unsuccessful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		}

	}

	public void scrollTo(String xpath, String element) throws Exception {
		try {
			((JavascriptExecutor) remoteDriver).executeScript("arguments[0].scrollIntoView();",
					remoteDriver.findElement(By.xpath(xpath)));

			ExtentUtility.getTest().log(LogStatus.PASS, "Scroll to element " + "" + element + "" + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, "Exception on scroll to element" + element,
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc + "Exception on scroll to element" + element);
		}

	}

	public void scrollTo(WebElement e, String element) throws Exception {
		try {
			((JavascriptExecutor) remoteDriver).executeScript("arguments[0].scrollIntoView();", e);
			ExtentUtility.getTest().log(LogStatus.PASS, "Scroll to element " + "" + element + "" + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, "Exception on scroll to element" + element,
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc + "Exception on scroll to element" + element);

		}

	}

	public void moveToElement(String xpath1, String xpath2, String elementName) throws Exception {
		Actions actions;
		try {

			switch (toolName) {

			case "Appium":
				actions = new Actions(appiumDriver);
				actions.moveToElement(appiumDriver.findElementByXPath(xpath1)).build().perform();
				break;
			case "Selenium":
				actions = new Actions(remoteDriver);
				actions.moveToElement(remoteDriver.findElementByXPath(xpath1))
						.moveToElement(remoteDriver.findElementByXPath(xpath2)).build().perform();
				actions.click().build().perform();
				break;
			}

			ExtentUtility.getTest().log(LogStatus.PASS, "Move to element " + "" + elementName + "" + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		} catch (Exception exc) {

			ExtentUtility.getTest().log(LogStatus.FAIL, "Exception on Move to element" + elementName,
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc + "Exception on Move to element" + elementName);

		}
	}

	public void moveToElement(WebElement element, String elementName) throws Exception {
		Actions actions;
		try {

			switch (toolName) {

			case "Appium":
				actions = new Actions(appiumDriver);
				actions.moveToElement(element).build().perform();
				break;
			case "Selenium":
				actions = new Actions(remoteDriver);
				actions.moveToElement(element).build().perform();
				break;
			}

			ExtentUtility.getTest().log(LogStatus.PASS, "Move to element " + "" + element + "" + " successful",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		} catch (Exception exc) {
			ExtentUtility.getTest().log(LogStatus.FAIL, "Exception on Move to element" + elementName,
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc + "Exception on Move to element" + elementName);
		}
	}

	public void assertTrue(String msg, boolean cond) throws Exception {
		if (cond) {
			ExtentUtility.getTest().log(LogStatus.PASS, msg,
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		} else {
			ExtentUtility.getTest().log(LogStatus.FAIL, msg,
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(msg);
		}
	}


	public void assertEquals(String message, String expected, String actual) throws Exception {
		if (expected == null && actual == null) {
			ExtentUtility.getTest().log(LogStatus.PASS,
					message + "...Excepted is :" + expected + " and actual is :" + actual + " is displayed",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

			return;
		}
		if (expected != null && expected.equals(actual)) {
			ExtentUtility.getTest().log(LogStatus.PASS,
					message + "...Excepted is :" + expected + " and actual is :" + actual + " is displayed",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			return;
		}
		ExtentUtility.getTest().log(LogStatus.FAIL,
				"Strings are not matched...Excepted is :" + expected + " but actual is :" + actual,
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		throw new Exception("Strings are not matched...Excepted is :" + expected + " but actual is :" + actual);
	}

	public void assertEquals(String message, double expected, double actual, double delta) throws Exception {
		if (Double.compare(expected, actual) == 0) {
			return;
		}
		if (!(Math.abs(expected - actual) <= delta)) {
			ExtentUtility.getTest().log(LogStatus.FAIL, message + actual,
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(message);
		}
	}

	public void clickEscape() {
		Actions action = new Actions(remoteDriver);
		action.sendKeys(Keys.ESCAPE);

	}

	public void assertEquals(String message, long expected, long actual) throws Exception {
		if (new Long(expected) != null && new Long(expected).equals(new Long(actual))) {
			return;
		}
		ExtentUtility.getTest().log(LogStatus.FAIL, message + actual,
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
		throw new Exception(message);
	}

	public void waitForPageLoad() {
		switch (toolName) {

		case "Appium":
			WebDriverWait wait = new WebDriverWait(appiumDriver, 60 * 15);
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver wdriver) {
					return ((JavascriptExecutor) appiumDriver).executeScript("return document.readyState")
							.equals("complete");
				}
			});
			break;
		case "Selenium":
			WebDriverWait wait1 = new WebDriverWait(remoteDriver, 60 * 15);
			wait1.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver wdriver) {
					return ((JavascriptExecutor) remoteDriver).executeScript("return document.readyState")
							.equals("complete");
				}
			});
			break;

		}
	}

	public void waitFor(int wait_time) throws InterruptedException {
		Thread.sleep(wait_time * 1000);
	}

	public void waitForVisibilityOfElement(WebElement e) throws Exception {
		try {
			switch (toolName) {

			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
				wait.until(ExpectedConditions.visibilityOf(e));
				break;
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 100, 500);
				waitSelenium.until(ExpectedConditions.visibilityOf(e));
				break;
			}

		} catch (Exception exc) {
			exc.printStackTrace();
			ExtentUtility.getTest().log(LogStatus.FAIL, "Exception on waiting for webelement",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc + "Exception on waiting for webelement");
		}
	}

	public void waitForInvisibilityOfElement(String xpath) throws Exception {

		try {
			switch (toolName) {

			case "Appium":
				WebDriverWait wait = new WebDriverWait(appiumDriver, 60, 500);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
				break;
			case "Selenium":
				WebDriverWait waitSelenium = new WebDriverWait(remoteDriver, 100, 500);
				waitSelenium.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
				break;
			}

		} catch (Exception exc) {
			exc.printStackTrace();
			ExtentUtility.getTest().log(LogStatus.FAIL, "Exception on waiting for webelement",
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			throw new Exception(exc + "Exception on waiting for webelement");

		}
	}

	@SuppressWarnings("rawtypes")
	public void doubleClickOnString(WebElement element, String strNextWord) throws AWTException, IOException {
		try
		{
		JavascriptExecutor js = null;
		final String JS_GET_WORD_RECT = "var ele=arguments[0], word=arguments[1], rg=document.createRange();   "
				+ "for(var c=ele.firstChild, i; c; c=c.nextSibling){                     "
				+ "  if(c.nodeType != 3 || (i=c.nodeValue.indexOf(word)) < 0) continue;  "
				+ "  rg.setStart(c, i); rg.setEnd(c, i + word.length);                   "
				+ "  var r = ele.getBoundingClientRect(), rr = rg.getClientRects()[0];   "
				+ "  return { left: (rr.left-r.left) | 0, top: (rr.top-r.top) | 0,       "
				+ "           width: rr.width | 0, height: rr.height | 0 };              " + "};";

		switch (toolName) {

		case "Appium":
			js = (JavascriptExecutor) appiumDriver;
			break;

		case "Selenium":
			js = (JavascriptExecutor) remoteDriver;
			break;
		}
		Map rect = (Map) js.executeScript(JS_GET_WORD_RECT, element, strNextWord);

		// Define a relative click point for the previous word "below"
		Long offset_x = (long) rect.get("left") - (long) rect.get("width") / 2;
		Long offset_y = (long) rect.get("top") + (long) rect.get("height") / 2;

		System.out.println(offset_x.intValue());
		System.out.println(offset_y.intValue());

		switch (toolName) {

		case "Appium":
			// Double click the word
			System.out.println("before press");
			new Actions(appiumDriver).moveToElement(element, offset_x.intValue(), offset_y.intValue()).doubleClick()
					.build().perform();

			System.out.println("after press");

			break;

		case "Selenium":
			// Double click the word
			new Actions(remoteDriver).moveToElement(element, offset_x.intValue(), offset_y.intValue()).doubleClick()
					.perform();
			break;
		}
		ExtentUtility.getTest().log(LogStatus.PASS, "Double Click on element " + element+ " successful",
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
	} catch (Exception exc) {

		ExtentUtility.getTest().log(LogStatus.FAIL, "Double Click on element" + element+"unsucessful",
				ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

	}

	}

	public void waitForAjax(int timeoutInSeconds) {

		try {
			if (remoteDriver instanceof JavascriptExecutor) {
				JavascriptExecutor jsDriver = (JavascriptExecutor) remoteDriver;
				Boolean ajaxCondtn = false;
				for (int i = 0; i < timeoutInSeconds; i++) {
					for (int j = 0; j < 20; j++) {
						try {

							ajaxCondtn = (Boolean) jsDriver.executeScript("return window.jQuery != undefined");

							if (ajaxCondtn)
								break;
							else
								Thread.sleep(1000);
						} catch (Exception e) {

						}
					}

					if (!ajaxCondtn)
						continue;
					Object numberOfAjaxConnections = jsDriver.executeScript("return jQuery.active");

					if (numberOfAjaxConnections instanceof Long) {
						Long n = (Long) numberOfAjaxConnections;

						if (n.longValue() == 0L)
							break;
					}
					Thread.sleep(1000);
				}
			} else {
				System.out.println("Web driver: " + remoteDriver + " cannot execute javascript");
			}
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	public void WaitForAjax() throws InterruptedException {
		Thread.sleep(3000);
		JavascriptExecutor executor = (JavascriptExecutor) remoteDriver;
		if ((Boolean) executor.executeScript("return window.jQuery != undefined")) {
			while (!(Boolean) executor.executeScript("return jQuery.active == 0")) {
				Thread.sleep(1000);
			}
		}
		return;
	}
	// ============================================OpenCV Methods=========================================
	
	
		public static Mat image = null;
		public static Mat image1 = null;
		public static Mat image2 = null;

		public void Match( Mat img2, org.openqa.selenium.Point location, Dimension size)
				throws IOException, InterruptedException {
			@SuppressWarnings("unused")
			int l = 0;
			int k = 0;
			int match = 0;
			img2=Imgcodecs.imread("D:\\Black.png", Imgcodecs.CV_LOAD_IMAGE_COLOR);
			for (k = 0; k < 10; k++) {
				System.out.println("the function");
				String filename = "D:\\error1.png";
				File src = ((TakesScreenshot) remoteDriver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(src, new File(filename));
				System.out.println(filename);
				Thread.sleep(2000);
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				System.out.println("face detect");

				double left = location.x;
				double top = location.y;
				double bottom = location.y + size.getHeight()-100;
				double right = location.x + size.getWidth()-130;
				
				
				Rect faceDetections = new Rect((int) left, (int) top , (int) right, (int) bottom);
				Mat img = new Mat(img1, faceDetections);


				filename = "D:\\match1.png";
				Imgcodecs.imwrite("D:\\match1.png", img);
				
				Mat img3 = Imgcodecs.imread("D:\\error.png", Imgcodecs.CV_LOAD_IMAGE_COLOR);
			
				FeatureDetector fd = FeatureDetector.create(FeatureDetector.BRISK);
				final MatOfKeyPoint keyPointsLarge = new MatOfKeyPoint();
				final MatOfKeyPoint keyPointsSmall = new MatOfKeyPoint();
				fd.detect(img3, keyPointsLarge);
				fd.detect(img2, keyPointsSmall);


				Mat descriptorsLarge = new Mat();
				Mat descriptorsSmall = new Mat();

				DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.BRISK);
				extractor.compute(img3, keyPointsLarge, descriptorsLarge);
				extractor.compute(img2, keyPointsSmall, descriptorsSmall);


				MatOfDMatch matches = new MatOfDMatch();

				DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMINGLUT);
				matcher.match(descriptorsLarge, descriptorsSmall, matches);

			

				MatOfDMatch matchesFiltered = new MatOfDMatch();

				List<DMatch> matchesList = matches.toList();
				List<DMatch> bestMatches = new ArrayList<DMatch>();

				Double max_dist = 0.0;
				Double min_dist = 100.0;

				for (int j = 0; j < matchesList.size(); j++) {
					Double dist = (double) matchesList.get(j).distance;

					if (dist < min_dist && dist != 0) {
						min_dist = dist;
					}

					if (dist > max_dist) {
						max_dist = dist;
					}

				}


				if (min_dist > 50) {
					System.out.println("No match found");
					
				}

				double threshold = 3 * min_dist;
				double threshold2 = 2 * min_dist;

				if (threshold > 75) {
					threshold = 75;
				} else if (threshold2 >= max_dist) {
					threshold = min_dist * 1.1;
				} else if (threshold >= max_dist) {
					threshold = threshold2 * 1.4;
				}

				System.out.println("Threshold : " + threshold);

				for (int i = 0; i < matchesList.size(); i++) {
					Double dist = (double) matchesList.get(i).distance;

					if (dist < threshold) {
						bestMatches.add(matches.toList().get(i));
						
					}
				}

				matchesFiltered.fromList(bestMatches);

				System.out.println("matchesFiltered.size() : " + matchesFiltered.size());

				if (matchesFiltered.rows() >= 1) {
					System.out.println("match found");
					match = 1;
					ExtentUtility.getTest().log(LogStatus.PASS, " Result =  Match Is Found ",
							ExtentUtility.getTest().addScreenCapture(takeScreenShot(appiumDriver)));
					break;
					
				} else {
					System.out.println("no match found");
					
				}

			}
			if (match == 0) {
				ExtentUtility.getTest().log(LogStatus.FAIL, " Result = No Match Is Found ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot(appiumDriver)));
				l++;
			}
			
		}

		public void AppDetect_VideoPlayDesktop(org.openqa.selenium.Point location, Dimension size)
				throws IOException, InterruptedException {
			int ret = 0;
			for (int i = 0; i < 4; i++) {
				// ret=0;
				String filename = "D:\\error.png";
				File file1=null;
				switch (toolName) {
				
				case "Appium":
					file1 = (File) ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.FILE);
					break;
				case "Selenium":
					file1 = (File) ((TakesScreenshot) remoteDriver).getScreenshotAs(OutputType.FILE);
					break;
				}
				FileUtils.copyFile(file1, new File(filename));
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				
				double left = location.x;
				double top = location.y;
				double bottom = location.y + size.getHeight()-100;
				double right = location.x + size.getWidth()-130;
				Rect faceDetections = new Rect((int) left, (int) top , (int) right, (int) bottom );
				Mat img = new Mat(img1, faceDetections);
				Imgcodecs.imwrite("D:\\error.png", img);
				ret = checkmotion(img);
			}
			if (ret == 0) {
				System.out.println("Video is not playing");
				ExtentUtility.getTest().log(LogStatus.FAIL, " Result = Video is not Playing - FALSE ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			} else {
				System.out.println("Video is playing");
				ExtentUtility.getTest().log(LogStatus.PASS, "  Video is  Playing  ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			}
			image = null;
			image1 = null;
			image2 = null;
		}
		
		
		public void AppDetect_VideoPause_Desktop(org.openqa.selenium.Point location, Dimension size)
				throws IOException, InterruptedException {
			int ret = 0;
			for (int i = 0; i < 6; i++) {
				// ret=0;
				String filename = "D:\\error.png";
				File file1=null;
				switch (toolName) {
				
				case "Appium":
					file1 = (File) ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.FILE);
					break;
				case "Selenium":
					file1 = (File) ((TakesScreenshot) remoteDriver).getScreenshotAs(OutputType.FILE);
					break;
				}
				FileUtils.copyFile(file1, new File(filename));
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				double left = location.x;
				double top = location.y;
				double bottom = location.y + size.getHeight()-100;
				double right = location.x + size.getWidth()-130;
				
				
				Rect faceDetections = new Rect((int) left, (int) top , (int) right, (int) bottom);
				Mat img = new Mat(img1, faceDetections);
				
				ret = checkmotion(img);
			}
			if (ret == 0) {
				System.out.println("Video is not playing");
				ExtentUtility.getTest().log(LogStatus.PASS, " Video is not Playing After clicking on the Pause button",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			} else {
				System.out.println("Video is playing");
				ExtentUtility.getTest().log(LogStatus.FAIL, "  Video is  Playing After clicking on the Pause button ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			}
			image = null;
			image1 = null;
			image2 = null;
		}

		
		public void AppDetect_VideoPause_Mobile(org.openqa.selenium.Point location, Dimension size)
				throws IOException, InterruptedException {
			int ret = 0;
			for (int i = 0; i < 6; i++) {
				// ret=0;
				String filename = "D:\\error.png";
				File file1=null;
				switch (toolName) {
				
				case "Appium":
					file1 = (File) ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.FILE);
					break;
				case "Selenium":
					file1 = (File) ((TakesScreenshot) remoteDriver).getScreenshotAs(OutputType.FILE);
					break;
				}
				FileUtils.copyFile(file1, new File(filename));
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				double left = location.x;
				double top = location.y;
				double bottom = location.y + size.getHeight()-100;
				double right = location.x + size.getWidth()-130;
				
				
				Rect faceDetections = new Rect((int) left, (int) top , (int) right, (int) bottom);
				Mat img = new Mat(img1, faceDetections);
				
				ret = checkmotion(img);
			}
			if (ret == 0) {
				System.out.println("Video is not playing");
				ExtentUtility.getTest().log(LogStatus.PASS, " Video is not Playing After clicking on the Pause button",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			} else {
				System.out.println("Video is playing");
				ExtentUtility.getTest().log(LogStatus.FAIL, "  Video is  Playing After clicking on the Pause button ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			}
			image = null;
			image1 = null;
			image2 = null;
		}
		
		
		
		public ArrayList<String> getimageloc(Mat templ) throws IOException{
			@SuppressWarnings("unused")
			int match = 0;
			@SuppressWarnings({ "rawtypes", "unchecked" })
			ArrayList<String> list=new ArrayList();
			
			org.opencv.core.Point loc = null;
			for (int k = 0; k < 20; k++) {
				String filename = "D:\\error1.png";
				File file1=null;
				switch (toolName) {
				
				case "Appium":
					file1 = (File) ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.FILE);
					break;
				case "Selenium":
					file1 = (File) ((TakesScreenshot) remoteDriver).getScreenshotAs(OutputType.FILE);
					break;
				}
				FileUtils.copyFile(file1, new File(filename));
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				
				
				System.out.println("Running Template Matching");
				
				/*double left = location.x;
				double top = location.y;
				double bottom = location.y + size.getHeight();
				double right = location.x + size.getWidth();
				 */
				
				int result_cols = img1.cols() - templ.cols() + 1;
				int result_rows = img1.rows() - templ.rows() + 1;
				Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);

			
				Imgcodecs.imwrite("D:\\templatewe"+k+".png", img1);
				String match_method = "Imgproc.TM_CCOEFF_NORMED";
				
				Imgproc.matchTemplate(img1, templ, result, Imgproc.TM_CCOEFF_NORMED);
				
				Imgproc.threshold(result, result, 0.5, 1.0, Imgproc.THRESH_TOZERO);
				
				MinMaxLocResult mmr = Core.minMaxLoc(result);

				org.opencv.core.Point matchLoc;
				if (match_method == "Imgproc.TM_SQDIFF" || match_method == "Imgproc.TM_SQDIFF_NORMED") {
					matchLoc = mmr.minLoc;
				} else {
					matchLoc = mmr.maxLoc;
				}
				System.out.println(matchLoc);
				System.out.println(mmr.maxVal);
				double threashhold = 0.70;
				if (mmr.maxVal > threashhold) {
					System.out.println(mmr.maxVal);
					System.out.println("match Found");
					loc=mmr.maxLoc;
					 list=getPoints(loc);
					break;
					
				}
				
			}
			return list;
		}
		public boolean  CheckImageinVideo(Mat templ,double d) throws IOException{
			
			boolean status = false;
			@SuppressWarnings("unused")
			org.opencv.core.Point loc = null;
			for (int k = 0; k < d*10; k++) {
				String filename = "D:\\error1.png";
				
				File file1=null;
				switch (toolName) {
				
				case "Appium":
					file1 = (File) ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.FILE);
					break;
				case "Selenium":
					file1 = (File) ((TakesScreenshot) remoteDriver).getScreenshotAs(OutputType.FILE);
					break;
				}
				FileUtils.copyFile(file1, new File(filename));
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
				int result_cols = img1.cols() - templ.cols() + 1;
				int result_rows = img1.rows() - templ.rows() + 1;
				Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
				String match_method = "Imgproc.TM_CCOEFF_NORMED";
				
				Imgproc.matchTemplate(img1, templ, result, Imgproc.TM_CCOEFF_NORMED);
				
				Imgproc.threshold(result, result, 0.5, 1.0, Imgproc.THRESH_TOZERO);
				
				MinMaxLocResult mmr = Core.minMaxLoc(result);

				@SuppressWarnings("unused")
				org.opencv.core.Point matchLoc;
				if (match_method == "Imgproc.TM_SQDIFF" || match_method == "Imgproc.TM_SQDIFF_NORMED") {
					matchLoc = mmr.minLoc;
				} else {
					matchLoc = mmr.maxLoc;
				}
				System.out.println(mmr.maxVal);
				double threashhold = 0.90;
				if (mmr.maxVal >=threashhold) {
					
					System.out.println("match Found");
					loc=mmr.maxLoc;
					 status=true;
					break;
					
				}
				
			}
			return status;
		}
		
		public String extract_Text(org.openqa.selenium.Point location, Dimension size)
				throws IOException, TesseractException {
			StringBuffer result=new StringBuffer();
			for (int k = 0; k <3; k++) {
				String filename = "D:\\error1.png";
				File file1=null;
				switch (toolName) {
				
				case "Appium":
					file1 = (File) ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.FILE);
					break;
				case "Selenium":
					file1 = (File) ((TakesScreenshot) remoteDriver).getScreenshotAs(OutputType.FILE);
					break;
				}
				
				FileUtils.copyFile(file1, new File(filename));
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				

				double left = location.x;
				double top = location.y;
				double bottom = location.y + size.getHeight()+500;
				double right = location.x + size.getWidth()+700;
				
				Rect faceDetections = new Rect((int) left, (int) top, (int) right , (int) bottom );
				Mat img2 = new Mat(img1, faceDetections);

				System.out.println(img2.height()+img2.width());
				Mat imgGray = new Mat();
				Imgproc.cvtColor(img2, imgGray, Imgproc.COLOR_BGR2GRAY);

				Mat imgGaussianBlur = new Mat();
				Imgproc.GaussianBlur(imgGray, imgGaussianBlur, new Size(3, 3), 0);

				
				Imgcodecs.imwrite("preprocess/adaptive_threshold.png", img2);

				File imageFile = new File("preprocess/adaptive_threshold.png");
				//System.out.println("tesseract");
				ITesseract instance = new Tesseract();
				instance.setLanguage("eng");

				 result.append(instance.doOCR(imageFile))  ;
			
			}
			
			String Text=result.toString();
			return Text;
		}
		
		@SuppressWarnings("serial")
		public void clickCoordinates(final int x, final int y,String element) throws IOException {
			try{
			switch (toolName) {
			case "Appium":
				appiumDriver.executeScript("mobile: tap", new HashMap<String, Integer>() {
					{
						put("tapCount", (int) 1);
						put("touchCount", (int) 1);
						put("duration", (int) 0.5);
						put("x", x);
						put("y", y);
					}
				});
				break;
			case "Selenium":
				remoteDriver.executeScript("mobile: tap", new HashMap<String, Integer>() {
					{
						put("tapCount", (int) 1);
						put("touchCount", (int) 1);
						put("duration", (int) 0.5);
						put("x", x);
						put("y", y);
					}
				});
				break;
			}
			ExtentUtility.getTest().log(LogStatus.PASS, "Clicked successfully on " + element ,
					ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			}catch(Exception e){
				ExtentUtility.getTest().log(LogStatus.FAIL, "Exception on clciking on element" + element,
						ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			}
			
		}
		
		public String extract_Text_with_coordinates(int left,int top,int bottom,int right ,int timeout)
				throws IOException, TesseractException {
			StringBuffer result=new StringBuffer();
			for (int k = 0; k <1*timeout; k++) {
				String filename = "D:\\error1.png";
				File file1=null;
				switch (toolName) {
				
				case "Appium":
					file1 = (File) ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.FILE);
					break;
				case "Selenium":
					file1 = (File) ((TakesScreenshot) remoteDriver).getScreenshotAs(OutputType.FILE);
					break;
				}
				
				FileUtils.copyFile(file1, new File(filename));
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
				
				
				Rect faceDetections = new Rect((int) left, (int) top, (int) bottom , (int) right );
				Mat img2 = new Mat(img1, faceDetections);

				Mat imgGaussianBlur = new Mat();
				Imgproc.GaussianBlur(img2, imgGaussianBlur, new Size(3, 3), 0);

				
				Imgcodecs.imwrite("preprocess/adaptive_threshold.png", imgGaussianBlur);

				File imageFile = new File("preprocess/adaptive_threshold.png");
				Tesseract instance = new Tesseract();
				instance.setLanguage("eng");
				
				 result.append(instance.doOCR(imageFile))  ;
			}
			
			String Text=result.toString();
			return Text;
		}
		
		public String extract_Text_Desktop(org.openqa.selenium.Point location, Dimension size)
				throws IOException, TesseractException {
			StringBuffer result=new StringBuffer();
			for (int k = 0; k <3; k++) {
				String filename = "D:\\error1.png";
				File file1=null;
				switch (toolName) {
				
				case "Appium":
					file1 = (File) ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.FILE);
					break;
				case "Selenium":
					file1 = (File) ((TakesScreenshot) remoteDriver).getScreenshotAs(OutputType.FILE);
					break;
				}
				
				FileUtils.copyFile(file1, new File(filename));
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
				
				double left = location.x;
				double top = location.y;
				double bottom = location.y + size.getHeight()-100;
				double right = location.x + size.getWidth()-130;
				
				Rect faceDetections = new Rect((int) left, (int) top, (int) right , (int) bottom );
				Mat img2 = new Mat(img1, faceDetections);


				Mat imgGaussianBlur = new Mat();
				Imgproc.GaussianBlur(img2, imgGaussianBlur, new Size(3, 3), 0);

				
				Imgcodecs.imwrite("preprocess/adaptive_threshold.png", imgGaussianBlur);

				File imageFile = new File("preprocess/adaptive_threshold.png");
				//System.out.println("tesseract");
				ITesseract instance = new Tesseract();
				instance.setLanguage("eng");

				 result.append(instance.doOCR(imageFile))  ;
			
			}
			
			String Text=result.toString();
			return Text;
		}
		
		@SuppressWarnings("unused")
		public boolean  CheckImageinVideo(Mat templ,double d,org.openqa.selenium.Point location, Dimension size) throws IOException{
			
			boolean status = false;
			org.opencv.core.Point loc = null;
			for (int k = 0; k < d*10; k++) {
				String filename = "D:\\errorss.png";
				
				File file1=null;
				switch (toolName) {
				
				case "Appium":
					file1 = (File) ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.FILE);
					break;
				case "Selenium":
					file1 = (File) ((TakesScreenshot) remoteDriver).getScreenshotAs(OutputType.FILE);
					break;
				}
				FileUtils.copyFile(file1, new File(filename));
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				
			
				
				System.out.println("Running Template Matching");
				double left = location.x;
				double top = location.y;
				double bottom = location.y + size.getHeight()-100;
				double right = location.x + size.getWidth()-130;
				Rect faceDetections = new Rect((int) left, (int) top , (int) right, (int) bottom );
				 img1 = new Mat(img1, faceDetections);
				int result_cols = img1.cols() - templ.cols() + 1;
				int result_rows = img1.rows() - templ.rows() + 1;
				Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
				String match_method = "Imgproc.TM_CCOEFF_NORMED";
				Imgproc.matchTemplate(img1, templ, result, Imgproc.TM_CCOEFF_NORMED);
				Imgproc.threshold(result, result, 0.5, 1.0, Imgproc.THRESH_TOZERO);
				MinMaxLocResult mmr = Core.minMaxLoc(result);
				org.opencv.core.Point matchLoc;
				if (match_method == "Imgproc.TM_SQDIFF" || match_method == "Imgproc.TM_SQDIFF_NORMED") {
					matchLoc = mmr.minLoc;
				} else {
					matchLoc = mmr.maxLoc;
				}
				double threashhold = 0.8;
				if (mmr.maxVal > threashhold) {
					loc=mmr.maxLoc;
					 status=true;
					break;
					
				}
				
			}
			 if (status) {
		            ExtentUtility.getTest().log(LogStatus.INFO, " Video is Buffering ",
		                         ExtentUtility.getTest().addScreenCapture(takeScreenShot(remoteDriver)));
		     } else {
		            System.out.println(" No match Found");
		            ExtentUtility.getTest().log(LogStatus.INFO, "No Buffering of Video",
		                         ExtentUtility.getTest().addScreenCapture(takeScreenShot(remoteDriver)));
		     }
			return status;
		}
		
		public void dragAndDropThumb(WebElement e1 ,int n) throws Exception {

			try {
				switch (toolName) {
				case "Appium":
					Actions action = new Actions(appiumDriver);
					action.dragAndDropBy(e1, n,0).perform();

					break;
				case "Selenium":
					Actions action1 = new Actions(remoteDriver);
					action1.dragAndDropBy(e1, n, 0).perform();

					break;
				}
				ExtentUtility.getTest().log(LogStatus.PASS,  "Drag element Successfully",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot()));

			} catch (Exception exc) {
				exc.printStackTrace();

				ExtentUtility.getTest().log(LogStatus.FAIL, exc + "Exception on Drag element",
						exc.toString() + ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
				throw new Exception(exc);

			}}

		
		
		
		
		public int checkmotion(Mat img) {
			int mistakes = 0;
			if (image == null) {
				image = img;
			} else if (image1 == null) {
				image1 = img;
			} else if (image2 == null) {
				image2 = img;
			}

			if ((image != null) && (image1 != null) && (image2 != null)) {
				Mat t_minus = image;
				Mat t = image1;
				Mat t_plus = image2;
				Mat dst = new Mat();
				Mat dst1 = new Mat();
				Core.absdiff(t_minus, t, dst);
				Core.absdiff(t, t_plus, dst1);
				Mat reshaped = dst.reshape(1);
				Mat reshaped1 = dst1.reshape(1);
				mistakes = Core.countNonZero(reshaped) + Core.countNonZero(reshaped1);
				reshaped.release();
				dst.release();
				dst1.release();
			}
			return mistakes;
		}

		public void AppDetect_VideoPlay(org.openqa.selenium.Point location, Dimension size)
				throws IOException, InterruptedException {
			int ret = 0;
			for (int i = 0; i < 6; i++) {
				String filename = "D:\\error.png";
				File file1=null;
				switch (toolName) {
				
				case "Appium":
					file1 = (File) ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.FILE);
					break;
				case "Selenium":
					file1 = (File) ((TakesScreenshot) remoteDriver).getScreenshotAs(OutputType.FILE);
					break;
				}
				FileUtils.copyFile(file1, new File(filename));
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				Rect faceDetections = new Rect(34,343,1020,550);
				Mat img = new Mat(img1, faceDetections);
				Imgcodecs.imwrite("D:\\error3.png", img);
				ret = checkmotion(img);
			}
			if (ret == 0) {
				System.out.println("Video is not playing");
				ExtentUtility.getTest().log(LogStatus.FAIL, "  Video is not Playing - FALSE ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			} else {
				System.out.println("Video is playing");
				ExtentUtility.getTest().log(LogStatus.PASS, "  Video is  Playing - TRUE ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			}
			image = null;
			image1 = null;
			image2 = null;
		}
		
		public void AppDetect_VideoPause(org.openqa.selenium.Point location, Dimension size)
				throws IOException, InterruptedException {
			int ret = 0;
			for (int i = 0; i < 6; i++) {
				
				String filename = "D:\\error.png";
				File file1=null;
				switch (toolName) {
				
				case "Appium":
					file1 = (File) ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.FILE);
					break;
				case "Selenium":
					file1 = (File) ((TakesScreenshot) remoteDriver).getScreenshotAs(OutputType.FILE);
					break;
				}
				FileUtils.copyFile(file1, new File(filename));
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				//Rect faceDetections = new Rect((int) left, (int) top, (int) right, (int) bottom );
				Rect faceDetections = new Rect(34,343,1020,550);
				Mat img = new Mat(img1, faceDetections);
				Imgcodecs.imwrite("D:\\error2.png", img);
				ret = checkmotion(img);
			}
			if (ret == 0) {
				System.out.println("Video is not playing");
				ExtentUtility.getTest().log(LogStatus.PASS, " Video is not Playing After clicking the Pause icon ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			} else {
				System.out.println("Video is playing");
				ExtentUtility.getTest().log(LogStatus.FAIL, "Video is  Playing After clicking the Pause icon ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot()));
			}
			image = null;
			image1 = null;
			image2 = null;
		}

		
		@SuppressWarnings({ "rawtypes", "unused" })
		public void matchimage(AppiumDriver driver, Mat templ, org.openqa.selenium.Point location, Dimension size)
				throws IOException {
			try{
			int match = 0;
			for (int k = 0; k < 20; k++) {
				String filename = "D:\\error1.png";
				File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(src, new File(filename));
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				System.out.println("Running Template Matching");
				
				int result_cols = img1.cols() - templ.cols() + 1;
				int result_rows = img1.rows() - templ.rows() + 1;
				Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);


				String match_method = "Imgproc.TM_CCOEFF_NORMED";
				Imgproc.matchTemplate(img1, templ, result, Imgproc.TM_CCOEFF_NORMED);
				Imgcodecs.imwrite("D:\\templatewe"+k+".png", img1);
				Imgproc.threshold(result, result, 0.5, 1.0, Imgproc.THRESH_TOZERO);
				MinMaxLocResult mmr = Core.minMaxLoc(result);

				org.opencv.core.Point matchLoc;
				if (match_method == "Imgproc.TM_SQDIFF" || match_method == "Imgproc.TM_SQDIFF_NORMED") {
					matchLoc = mmr.minLoc;
				} else {
					matchLoc = mmr.maxLoc;
				}
				System.out.println(mmr.minLoc);
				System.out.println(mmr.maxLoc);
				System.out.println(mmr.maxVal);
				double threashhold = 0.70;
				if (mmr.maxVal > threashhold) {
					System.out.println(mmr.maxVal);
					System.out.println("match Found");
					match++;
					break;
					
				}
			}
			if (match == 1) {
				ExtentUtility.getTest().log(LogStatus.PASS, " Result = Match Is Found ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot(driver)));
			} else {
				System.out.println(" NO match Found");
				ExtentUtility.getTest().log(LogStatus.FAIL, " Result = No Match Is Found ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot(driver)));
			}}catch(Exception e){
				System.out.println(e);
			}

		}

		public void Appmatchimage(WebDriver driver, Mat templ, org.openqa.selenium.Point location, Dimension size)
				throws IOException {

			int match = 0;
			for (int k = 0; k < 5; k++) {
				String filename = "D:\\match2.png";
				File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(src, new File(filename));
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				

				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				System.out.println("Running Template Matching__");
				
				double left = location.x;
				double top = location.y;
				double bottom = location.y + size.getHeight()-100;
				double right = location.x + size.getWidth()-130;
				
				System.out.println("face detedffffff");
				int result_cols = img1.cols() - templ.cols() + 1;
				int result_rows = img1.rows() - templ.rows() + 1;
				Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
				System.out.println("face deted");
				Rect faceDetections = new Rect((int) left, (int) top , (int) right, (int) bottom );
				Mat img2 = new Mat(img1, faceDetections);

				System.out.println("face deted");
				String match_method = "Imgproc.TM_CCOEFF_NORMED";
				Imgproc.matchTemplate(img2, templ, result, Imgproc.TM_CCOEFF_NORMED);
				Imgproc.threshold(result, result, 0.5, 1.0, Imgproc.THRESH_TOZERO);
				MinMaxLocResult mmr = Core.minMaxLoc(result);
				System.out.println("face deted111");
				org.opencv.core.Point matchLoc;
				if (match_method == "Imgproc.TM_SQDIFF" || match_method == "Imgproc.TM_SQDIFF_NORMED") {
					matchLoc = mmr.minLoc;
				} else {
					matchLoc = mmr.maxLoc;
				}
				System.out.println(matchLoc);
				System.out.println(mmr.maxVal);
				double threashhold = 0.70;
				if (mmr.maxVal > threashhold) {
					System.out.println(mmr.maxVal);
					System.out.println("match Found");
					match=1;
					break;
					
				}
				
				
			}
			if (match == 1) {
				ExtentUtility.getTest().log(LogStatus.PASS, " Result = Match Is Found ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot(driver)));
			} else {
				System.out.println(" NO match Found");
				ExtentUtility.getTest().log(LogStatus.FAIL, " Result = No Match Is Found ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot(driver)));
			}
		}
		
		public void Appmatchimage(WebDriver driver, Mat templ,Mat temp2, org.openqa.selenium.Point location, Dimension size)
				throws IOException {

			int match = 0;
			for (int k = 0; k < 5; k++) {
				String filename = "D:\\match2.png";
				File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(src, new File(filename));
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				

				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				System.out.println("Running Template Matching__");
				
				double left = location.x;
				double top = location.y;
				double bottom = location.y + size.getHeight()-100;
				double right = location.x + size.getWidth()-130;
				
				int result_cols = img1.cols() - templ.cols() + 1;
				int result_rows = img1.rows() - templ.rows() + 1;
				Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
				System.out.println("face deted");
				Rect faceDetections = new Rect((int) left, (int) top , (int) right, (int) bottom );
				Mat img2 = new Mat(img1, faceDetections);

				System.out.println("face deted");
				String match_method = "Imgproc.TM_CCOEFF_NORMED";
				Imgproc.matchTemplate(img2, templ, result, Imgproc.TM_CCOEFF_NORMED);
				Imgproc.threshold(result, result, 0.5, 1.0, Imgproc.THRESH_TOZERO);
				MinMaxLocResult mmr = Core.minMaxLoc(result);
				System.out.println("face deted111");
				org.opencv.core.Point matchLoc;
				if (match_method == "Imgproc.TM_SQDIFF" || match_method == "Imgproc.TM_SQDIFF_NORMED") {
					matchLoc = mmr.minLoc;
				} else {
					matchLoc = mmr.maxLoc;
				}
				System.out.println(matchLoc);
				System.out.println(mmr.maxVal);
				double threashhold = 0.70;
				if (mmr.maxVal > threashhold) {
					System.out.println(mmr.maxVal);
					System.out.println("match Found");
					match=1;
					break;
					
				}
			}
			if (match == 1) {
				ExtentUtility.getTest().log(LogStatus.PASS, " Result = Match Is Found ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot(driver)));
			} else {
				System.out.println(" NO match Found");
				ExtentUtility.getTest().log(LogStatus.FAIL, " Result = No Match Is Found ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot(driver)));
			}
		}
		
		
		public void BlackScreen_Match(org.openqa.selenium.Point location, Dimension size)
				throws IOException {
			Mat templ= Imgcodecs.imread("D:\\Black_Screen.png", Imgcodecs.CV_LOAD_IMAGE_COLOR);
			int match = 0;
			for (int k = 0; k < 20; k++) {
				String filename = "D:\\match2.png";
				File src = ((TakesScreenshot) remoteDriver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(src, new File(filename));
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
				System.out.println("Running Template Matching");
				
				double left = location.x;
				double top = location.y;
				double bottom = location.y + size.getHeight()-100;
				double right = location.x + size.getWidth()-130;
				Rect faceDetections = new Rect((int) left, (int) top , (int) right, (int) bottom );
				Mat img2 = new Mat(img1, faceDetections);
				Imgcodecs.imwrite("D:\\screen"+k+".png", img2);
				System.out.println("face detedffffff");
				int result_cols = img2.cols() - templ.cols() + 1;
				int result_rows = img2.rows() - templ.rows() + 1;
				Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
				System.out.println("face deted");
				
				System.out.println("face deted");
				String match_method = "Imgproc.TM_CCOEFF_NORMED";
				Imgproc.matchTemplate(img2, templ, result, Imgproc.TM_CCOEFF_NORMED);
				Imgproc.threshold(result, result, 0.5, 1.0, Imgproc.THRESH_TOZERO);
				MinMaxLocResult mmr = Core.minMaxLoc(result);
				System.out.println("face deted111");
				org.opencv.core.Point matchLoc;
				if (match_method == "Imgproc.TM_SQDIFF" || match_method == "Imgproc.TM_SQDIFF_NORMED") {
					matchLoc = mmr.minLoc;
				} else {
					matchLoc = mmr.maxLoc;
				}
				System.out.println(matchLoc);
				System.out.println(mmr.maxVal);
				double threashhold = 0.70;
				if (mmr.maxVal > threashhold) {
					System.out.println(mmr.maxVal);
					System.out.println("BlackScreen Found");
					match=1;
					break;
					
				}
			}
			if (match == 1) {
				ExtentUtility.getTest().log(LogStatus.PASS, " Result = Black Screen Is Found ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot(remoteDriver)));
			} else {
				System.out.println(" NO match Found");
				ExtentUtility.getTest().log(LogStatus.FAIL, " Result = No Black Screen Is Found ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot(remoteDriver)));
			}
		}
		public ArrayList<String> getPoints(org.opencv.core.Point loc2){
			String point=""+loc2;
			String[] split=point.split("}");
			System.out.println(split[0]+"--");
			String[] split1=split[0].split("}");
			System.out.println(split1[0]+"--");
			String[] coords=split1[0].split(",");
			System.out.println(coords[0]+"---"+coords[1]);
			String x=coords[0].substring(1);
			String y=coords[1].replace(" ", "");
			System.out.println(x);
			System.out.println(y);
			ArrayList<String> list=new ArrayList<>();
			list.add(x);
			list.add(y);
			return list;
		}

		@SuppressWarnings("rawtypes")
		public void getScreenShot(AppiumDriver driver) throws IOException{
			String filename = "D:\\error1.png";

			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File(filename));

		
		}
		public void Detect_VideoPlay(WebDriver driver, org.openqa.selenium.Point location, Dimension size)
				throws IOException, InterruptedException {
			int ret = 0;
			for (int i = 0; i < 6; i++) {
				// ret=0;
				String filename = "D:\\error.png";
				File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(src, new File(filename));
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				double left = location.x;
				double top = location.y;
				double bottom = location.y + size.getHeight() - 60;
				double right = location.x + size.getWidth() - 140;
				CascadeClassifier faceDetector = new CascadeClassifier();
				faceDetector.load("C:/opencv-2.4.10/opencv/sources/data/haarcascades/haarcascade_frontalface_alt.xml");
				Rect faceDetections = new Rect((int) left, (int) top, (int) right, (int) bottom);
				Mat img = new Mat(img1, faceDetections);
				filename = "D:\\player" + i + ".png";
				Imgcodecs.imwrite("D:\\player" + i + ".png", img);
				Thread.sleep(3000);
				Mat img2 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				ret = checkmotion(img2);
			}
			if (ret == 0) {
				System.out.println("Video is not playing");
				ExtentUtility.getTest().log(LogStatus.FAIL, " Result = Video is not Playing - FALSE ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot(driver)));
			} else {
				System.out.println("Video is playing");
				ExtentUtility.getTest().log(LogStatus.PASS, " Result = Video is  Playing - TRUE ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot(driver)));
			}
			image = null;
			image1 = null;
			image2 = null;
		}

		public void Detect_VideoPause(WebDriver driver, org.openqa.selenium.Point location, Dimension size)
				throws IOException, InterruptedException {
			int ret = 0;
			for (int i = 0; i < 6; i++) {
				String filename = "D:\\error.png";
				File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(src, new File(filename));
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				double left = location.x;
				double top = location.y;
				double bottom = location.y + size.getHeight() - 60;
				double right = location.x + size.getWidth() - 140;
				CascadeClassifier faceDetector = new CascadeClassifier();
				faceDetector.load("C:/opencv-2.4.10/opencv/sources/data/haarcascades/haarcascade_frontalface_alt.xml");
				Rect faceDetections = new Rect((int) left, (int) top, (int) right, (int) bottom);
				Mat img = new Mat(img1, faceDetections);
				filename = "D:\\player" + i + ".png";
				Imgcodecs.imwrite("D:\\player" + i + ".png", img);
				Thread.sleep(3000);
				Mat img2 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				ret = checkmotion(img2);
			}
			if (ret == 0) {
				System.out.println("Video is not playing");
				ExtentUtility.getTest().log(LogStatus.PASS, " Result = Video is not Playing - FALSE ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot(driver)));
			} else {
				System.out.println("Video is playing");
				ExtentUtility.getTest().log(LogStatus.FAIL, " Result = Video is  Playing - TRUE ",
						ExtentUtility.getTest().addScreenCapture(takeScreenShot(driver)));
			}
			image = null;
			image1 = null;
			image2 = null;
		}

		public String takeScreenShot(WebDriver driver) throws IOException {
			Calendar cal = Calendar.getInstance();
			long s = cal.getTimeInMillis();
			File screen = null;
			screen = (File) ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(screen, new File("D:/" + ExtentUtility.reportFolder + "/Screenshots/image" + s + ".png"));

			return (new File("D://" + ExtentUtility.reportFolder + "//Screenshots//image" + s + ".png").getAbsolutePath());

		}
		@SuppressWarnings("rawtypes")
		public void AppMatch(WebElement element, AppiumDriver driver, Mat img2, org.openqa.selenium.Point location,
				Dimension size) throws IOException, InterruptedException {
			int  k = 0;
			for (k = -0; k < 10; k++) {
				String filename = "D:\\error1.png";

				File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(src, new File(filename));
				Mat img1 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				
				Rect faceDetections = new Rect(46,650 , 50, 50 );
				Mat img = new Mat(img1, faceDetections);

				filename = "D:\\match1.png";
				Imgcodecs.imwrite("D:\\match1.png", img);
				Thread.sleep(1000);
				Mat img3 = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
				
				FeatureDetector fd = FeatureDetector.create(FeatureDetector.BRISK);
				final MatOfKeyPoint keyPointsLarge = new MatOfKeyPoint();
				final MatOfKeyPoint keyPointsSmall = new MatOfKeyPoint();
				fd.detect(img3, keyPointsLarge);
				fd.detect(img2, keyPointsSmall);


				Mat descriptorsLarge = new Mat();
				Mat descriptorsSmall = new Mat();

				DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.BRISK);
				extractor.compute(img3, keyPointsLarge, descriptorsLarge);
				extractor.compute(img2, keyPointsSmall, descriptorsSmall);

				MatOfDMatch matches = new MatOfDMatch();

				DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMINGLUT);
				matcher.match(descriptorsLarge, descriptorsSmall, matches);

			

				MatOfDMatch matchesFiltered = new MatOfDMatch();

				List<DMatch> matchesList = matches.toList();
				List<DMatch> bestMatches = new ArrayList<DMatch>();

				Double max_dist = 0.0;
				Double min_dist = 100.0;

				for (int j = 0; j < matchesList.size(); j++) {
					Double dist = (double) matchesList.get(j).distance;

					if (dist < min_dist && dist != 0) {
						min_dist = dist;
					}

					if (dist > max_dist) {
						max_dist = dist;
					}

				}


				if (min_dist > 50) {
					System.out.println("No match found");
					
				}

				double threshold = 3 * min_dist;
				double threshold2 = 2 * min_dist;

				if (threshold > 75) {
					threshold = 75;
				} else if (threshold2 >= max_dist) {
					threshold = min_dist * 1.1;
				} else if (threshold >= max_dist) {
					threshold = threshold2 * 1.4;
				}

				System.out.println("Threshold : " + threshold);

				for (int i = 0; i < matchesList.size(); i++) {
					Double dist = (double) matchesList.get(i).distance;

					if (dist < threshold) {
						bestMatches.add(matches.toList().get(i));
						
					}
				}

				matchesFiltered.fromList(bestMatches);

				System.out.println("matchesFiltered.size() : " + matchesFiltered.size());

				if (matchesFiltered.rows() >= 1) {

				
					System.out.println("match found");
					ExtentUtility.getTest().log(LogStatus.PASS, " Result =  Match Is Found ",
							ExtentUtility.getTest().addScreenCapture(takeScreenShot(driver)));
					break;
					
				} else {
					System.out.println("no match found");
					
				}

			}
			
		}
		
}
