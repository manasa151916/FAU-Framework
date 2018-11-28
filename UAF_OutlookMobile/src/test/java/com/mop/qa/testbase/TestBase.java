package com.mop.qa.testbase;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.opencv.core.Core;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.mop.qa.Utilities.ExtentUtility;
import com.mop.qa.Utilities.ReadDataSheet;

import io.appium.java_client.AppiumDriver;

public class TestBase {

	public static long startTime;
	public static String startTimeUpdate;
	public static long endTime;
	public static long totalTime;
	public static String totalTimeTaken;
	public static String osType = System.getProperty("os.name");
	public String toolName = "";
	public String localityType="";
	public String platform_name = "";
	public String browser="";
	public String appType = "";
	public String RemoteUrl = "";
	public AppiumDriver appiumDriver;
	public RemoteWebDriver remoteDriver;
	public String udid = null;
	public String appium_port = null;
	public String currentTest = "";
	public ReadDataSheet rds = new ReadDataSheet();
	public String deviceName = null;
	public String startURL = null;
	public String lang=null;

	@BeforeSuite
	public void executeSuite(ITestContext ctx) {
		try {
			ExtentUtility.extent = ExtentUtility.getReporter();
			System.out.println("Before Suite");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void getSuiteName(ISuite ist) {

		try {
			System.out.println("SuiteName" + ist.getName());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@BeforeTest
	public void startTestReport(ITestContext ctx) {

		try {
			System.out.println("Before Test");
			/*if (getPropertyValue("Video_solution").equalsIgnoreCase("Yes"))
				System.loadLibrary(Core.NATIVE_LIBRARY_NAME);*/
			 browser = ctx.getCurrentXmlTest().getParameter("browser");
			toolName = ctx.getCurrentXmlTest().getParameter("toolName");
			String exeType = ctx.getCurrentXmlTest().getParameter("ExecutionType");
			 localityType = ctx.getCurrentXmlTest().getParameter("Locality");
			// platform_name = "";
			RemoteUrl = ctx.getCurrentXmlTest().getParameter("RemoteUrl");
			System.out.println("toolname is " + ctx.getCurrentXmlTest().getParameter("toolName"));
			ExtentUtility.test = ExtentUtility.startTest(ctx.getCurrentXmlTest().getParameter("testname"));
			currentTest = ctx.getCurrentXmlTest().getParameter("testname");
			System.out.println(currentTest + "is Running");
			if (toolName.equalsIgnoreCase("Services")) {
			} else if (toolName.equalsIgnoreCase("Appium")) {
				deviceName = ctx.getCurrentXmlTest().getParameter("deviceName");
				appium_port = getPort();
				appType = ctx.getCurrentXmlTest().getParameter("appType");
				System.out.println("App type is " + ctx.getCurrentXmlTest().getParameter("appType"));
				udid = ctx.getCurrentXmlTest().getParameter("udid");
				platform_name = ctx.getCurrentXmlTest().getParameter("platformName");
				if ((platform_name.equalsIgnoreCase("iOS")) && (appType.equalsIgnoreCase("Web"))) {
					startURL = rds.getValue("DATA", currentTest, "URL");
				}
				if (!localityType.equalsIgnoreCase("Cloud")) {
					startAppiumServer(udid, appium_port,platform_name);
				}
				Thread.sleep(5000);
				initiateDriver(localityType, appium_port, browser, RemoteUrl, platform_name, toolName, appType,
						deviceName, startURL);

			} else {

				System.out.println("browser is selenium" + ctx.getCurrentXmlTest().getParameter("browser"));
				initiateDriver(localityType, appium_port, browser, RemoteUrl, platform_name, toolName, appType,
						deviceName, startURL);

			}

			System.out.println("test name is " + ctx.getCurrentXmlTest().getParameter("testname1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void initiateWebDriverMobile() {
		initiateDriver(localityType, appium_port, browser, RemoteUrl, platform_name, toolName, "web",
				deviceName, startURL);
	}
	
	@AfterTest
	public void afterTest() throws Exception {
		System.out.println("After Test");
		if (toolName.equalsIgnoreCase("Appium")) {
			if (appiumDriver != null) {
				ExtentUtility.extent.endTest(ExtentUtility.getTest());
				Thread.sleep(5000);
				appiumDriver.close();
			}
		} else if (toolName.equalsIgnoreCase("selenium")) {
			if (remoteDriver != null) {
				ExtentUtility.extent.endTest(ExtentUtility.getTest());
				Thread.sleep(5000);
				remoteDriver.close();
			}
		}

	}

	@AfterSuite
	public void finishExecution() throws Exception {

		System.out.println("After suite");
		try {
			if (toolName.equalsIgnoreCase("Appium")) {
				if (appiumDriver != null) {
					appiumDriver.quit();
				}
			} else {
				if (remoteDriver != null) {
					remoteDriver.quit();
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			stopServer();
			ExtentUtility.getReporter().flush();
		}

	}

	public void startAppiumServer(String udid, String port,String platformName) {
		try {
			String chromePort = getPort();
			String wdaport = getPort();
			String bootstrapPort = getPort();
			String nodePath = getPropertyValue("nodePath");
			String appiumPath = getPropertyValue("appiumJSPath");
			CommandLine command = new CommandLine(nodePath);
			command.addArgument(appiumPath, false);
			command.addArgument("--address", false);
			command.addArgument("0.0.0.0");
			command.addArgument("--port", false);
			command.addArgument(port);
			command.addArgument("--session-override", false);
			command.addArgument("--bootstrap-port", false);
			command.addArgument(bootstrapPort);
			command.addArgument("--chromedriver-port", false);
			command.addArgument(chromePort);
			command.addArgument("--chromedriver-executable",false);
			command.addArgument("C://BrowserDrivers//chromedriver_version2.43//chromedriver.exe");
			if(platformName.equalsIgnoreCase("iOS"))
			{
			command.addArgument("--webdriveragent-port", false);
			command.addArgument(wdaport);
			}
			command.addArgument("--udid", false);
			command.addArgument(udid);
			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(1);

			executor.execute(command, resultHandler);
			Thread.sleep(10000);
			System.out.println("Appium server started.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// This will check for free ports
	public String getPort() throws Exception {
		ServerSocket socket = new ServerSocket(0);
		socket.setReuseAddress(true);
		String port = Integer.toString(socket.getLocalPort());
		socket.close();
		return port;
	}

	public void initiateDriver(String localityType, String port, String browser, String RemoteUrl, String platform_name,
			String toolname, String appType, String deviceName, String startURL) {
		try {

			if (toolName.equalsIgnoreCase("Appium")) {
				System.out.println("driver start");
				PageBase pagebaseclass = new PageBase(appiumDriver);
				appiumDriver = pagebaseclass.launchApp(udid, localityType, RemoteUrl, toolname, appType, port,
						platform_name, deviceName, startURL);
			}

			if (toolName.equalsIgnoreCase("selenium")) {
				PageBase pagebaseclass = new PageBase(remoteDriver,lang);
				 String langcode= rds.getValue("DATA",currentTest, "Language Code");
				remoteDriver = pagebaseclass.launchSite(browser, localityType, RemoteUrl,langcode);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// To stop the Servers
	public static void stopServer() {
		try {
			System.out.println("Stop server");
			String filePath = "";
			String filePath1 = "";
			if (System.getProperty("os.name").contains("Win")) {

				filePath = "taskkill /F /IM node.exe";
				Runtime.getRuntime().exec(filePath);
				filePath1 = "taskkill /F /IM chromedriver.exe";
				Runtime.getRuntime().exec(filePath1);
				Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
				Runtime.getRuntime().exec("taskkill /F /IM plugin-container.exe");
				Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
			} else {

				Runtime.getRuntime().exec(new String[] { "bash", "-c", "killall node" });
				Runtime.getRuntime().exec(new String[] { "bash", "-c", "killall chrome" });
				Runtime.getRuntime().exec(new String[] { "bash", "-c", "killall safari" });
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getPropertyValue(String key) throws IOException {
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

	public static void updateProperty(String updateTime, String startTime) {
		try {

			FileInputStream in = new FileInputStream("report.properties");
			Properties props = new Properties();
			props.load(in);
			in.close();

			FileOutputStream out = new FileOutputStream("report.properties");
			props.setProperty("TOTAL_TIME", totalTimeTaken.toString());
			props.setProperty("RUN_STARTED", startTime.toString());
			props.store(out, null);
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
