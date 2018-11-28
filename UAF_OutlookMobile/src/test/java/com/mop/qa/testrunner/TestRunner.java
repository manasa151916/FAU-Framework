package com.mop.qa.testrunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.mop.qa.Utilities.ReadDataSheet;
import com.mop.qa.Utilities.TestParameter;


public class TestRunner {
	static List<TestParameter> list;
	public static List<String> testname;
	//public static void main(String args[]) {

	@Test
	public void testRun() {
		try {
			boolean flag = true;
			TestNG testNG = new TestNG();
			ReadDataSheet rds = new ReadDataSheet();
			String toolType = null;
			String executionType = null;

			List<XmlSuite> suites = new ArrayList<XmlSuite>();
			
			//testNG.addExecutionListener(new TestSuite());
			List<List<TestParameter>> testLists = testList();
			XmlSuite suite1 = new XmlSuite();
			XmlSuite suite2 = new XmlSuite();
			suite1.setName("Suite_seq");//sequential

			for (int j = 0; j < testLists.size(); j++) {
				List<XmlTest> tests = new ArrayList<XmlTest>();
				List<XmlTest> tests1 = new ArrayList<XmlTest>();
				List<XmlTest> tests2 = new ArrayList<XmlTest>();
				List<TestParameter> suiteTest = testLists.get(j);
				XmlSuite parasuite = new XmlSuite(); 
				parasuite.setName("cross_para");
				XmlSuite suite = new XmlSuite();
				suite.setName("Suite_para");
				for (int i = 0; i < suiteTest.size(); i++) {
					XmlTest test = new XmlTest(suite);
					List<XmlClass> xmlclass = new ArrayList<XmlClass>();
					test.addParameter("testname", suiteTest.get(i).getTestName());
					test.addParameter("testname1", suiteTest.get(i).getTestName1());
					if(suiteTest.get(i).getToolName().equalsIgnoreCase("Connected Device")){
						if(flag){
						test.addParameter("toolName", suiteTest.get(i).getToolName());
						xmlclass.add(new XmlClass("STBT_Roku.TestRun"));
						test.setXmlClasses(xmlclass);
						tests1.add(test);
						suite1.setTests(tests1);
						System.out.println(suite1);
						flag=false;
						}
					} 
					else{
					xmlclass.add(new XmlClass(suiteTest.get(i).getTestClass()));
					test.setName(suiteTest.get(i).getTestName());
					test.setXmlClasses(xmlclass);
					test.addParameter("testname", suiteTest.get(i).getTestName());
					test.addParameter("testname1", suiteTest.get(i).getTestName1());
					test.addParameter("toolName", suiteTest.get(i).getToolName());
					test.addParameter("browser", suiteTest.get(i).getBrowser());
					test.addParameter("appType", suiteTest.get(i).getAppType());
					test.addParameter("ExecutionType", suiteTest.get(i).getExecutionType());
					test.addParameter("Locality", suiteTest.get(i).getLocality());
					toolType = suiteTest.get(i).getToolName();
					String name1 = suiteTest.get(i).getTestName();
					executionType = suiteTest.get(i).getExecutionType();
					System.out.println("Execution type" + executionType);
					if (suiteTest.get(i).getLocality().equalsIgnoreCase("Grid")||suiteTest.get(i).getLocality().equalsIgnoreCase("Cloud")) {
						test.addParameter("RemoteUrl", suiteTest.get(i).getRemoteUrl());
					}
					if (executionType.equalsIgnoreCase("Parallel")) {
						if (toolType.equalsIgnoreCase("Selenium")) {
							if(Integer.parseInt( suiteTest.get(i).getNo_Of_Instances())<=1){
								tests2.add(test);
							} 
							else if (((i + 1) < suiteTest.size())
									&& (suiteTest.get(i).getTestName1().equals(suiteTest.get(i + 1).getTestName1()))) {
								tests.add(test);
								suite.setParallel("tests");
								suite.setThreadCount(Integer.parseInt(suiteTest.get(i).getNo_Of_Instances()));
							} else {
								tests.add(test);
								suite.setTests(tests);
								suite.setParallel("tests");
								suite.setThreadCount(Integer.parseInt(suiteTest.get(i).getNo_Of_Instances()));
								suites.add(suite);
								tests = new ArrayList<XmlTest>();
								suite = new XmlSuite();
								suite.setName("Suite_para" + i);
							}
						} else if (toolType.equalsIgnoreCase("Appium")) {
							if(Integer.parseInt( suiteTest.get(i).getNo_Of_Instances())<=1){
								test.addParameter("platformName", suiteTest.get(i).getPlatformName());
								test.addParameter("udid", suiteTest.get(i).getUdid());
								test.addParameter("deviceName", suiteTest.get(i).getDeviceName());
								tests2.add(test);
							} 
							else if (((i + 1) < suiteTest.size())
									&& (suiteTest.get(i).getTestName1().equals(suiteTest.get(i + 1).getTestName1()))) {
								test.addParameter("platformName", suiteTest.get(i).getPlatformName());
								test.addParameter("udid", suiteTest.get(i).getUdid());
								test.addParameter("deviceName", suiteTest.get(i).getDeviceName());
								tests.add(test);
								suite.setTests(tests);
								suite.setParallel("tests");
								suite.setThreadCount(tests.size());
							} else {
								test.addParameter("platformName", suiteTest.get(i).getPlatformName());
								test.addParameter("udid", suiteTest.get(i).getUdid());
								test.addParameter("deviceName", suiteTest.get(i).getDeviceName());
								suite.setTests(tests);
								suite.setParallel("tests");
								suite.setThreadCount(Integer.parseInt(suiteTest.get(i).getNo_Of_Instances()));
								suites.add(suite);
								tests = new ArrayList<XmlTest>();
								suite = new XmlSuite();
								suite.setName("Suite_para"+(i+1));
							}
						} else {
							tests.add(test);
						}

					} else if (executionType.equalsIgnoreCase("Sequential")) {
						if (toolType.equalsIgnoreCase("Selenium")) {
							tests1.add(test);
						}
						else if (toolType.equalsIgnoreCase("Appium")) {
							test.addParameter("platformName", suiteTest.get(i).getPlatformName());
							test.addParameter("udid", suiteTest.get(i).getUdid());
							test.addParameter("deviceName", suiteTest.get(i).getDeviceName());
							tests1.add(test);
						} else {
							tests1.add(test);
						}
						suite1.setTests(tests1);
						
					}
				}
				}
				parasuite.setTests(tests2);
				parasuite.setParallel("tests"); 
				suites.add(parasuite); 
				//suite.setTests(tests);
				suites.add(suite1);
				for (XmlSuite s : suites)
					System.out.println(s.toXml());

			}
			testNG.setXmlSuites(suites);
			testNG.run();

		} catch (Exception e) {
			System.out.println("Exception "+e);
			e.printStackTrace();
		}

	}

	public XmlSuite getXmlSuite() {
		XmlSuite suite = new XmlSuite();
		suite.setName("TestSuite");
		return suite;
	}

	public static List<List<TestParameter>> testList() {

		try {
			List<List<TestParameter>> testRunner = new ArrayList<List<TestParameter>>();
			FileInputStream fis = new FileInputStream("./TestRunnerUAF.xls");
			HSSFWorkbook wb = new HSSFWorkbook(fis);
			boolean flag = true;
			String[] platform = null;
			String[] browser = null;
			testname = new ArrayList<String>();
			int totalSheets = wb.getNumberOfSheets()-1;

			for (int i = 0; i < totalSheets; i++) {
				HSSFSheet sheet = wb.getSheetAt(i);

				list = new ArrayList<TestParameter>();
				for (int count = 1; count <= sheet.getLastRowNum(); count++) {

					HSSFRow row = sheet.getRow(count);
					//String iteration = row.getCell(11).toString();
					//int iterations = Integer.parseInt(iteration.substring(0, iteration.indexOf(".")));
					if (row.getCell(2).toString().equalsIgnoreCase("Yes")) {
						if(!row.getCell(6).toString().equalsIgnoreCase("Connected Device")){//Get browsername an platform name
							 browser = row.getCell(9).toString().split("-");
							 platform = row.getCell(8).toString().split("-"); 
						}
						int count1 = 1;
						for (int l = 0; l < count1 ; l++) {//count as no of instances
							TestParameter test = new TestParameter();
							test.setTestName1(row.getCell(0).toString());
							String name = row.getCell(0).toString();
							test.setTestName(name + "-Run" + (l+1));
							if(row.getCell(6).toString().equalsIgnoreCase("Connected Device")){
								test.setToolName(row.getCell(6).toString());
								test.setTestName(name + "-Run" + (l+1));
								test.setTestClass(row.getCell(1).toString());
								testname.add(row.getCell(1).toString());
								System.out.println(testname);
							
							}
							else{
							test.setTestClass(row.getCell(1).toString());
							test.setExecutionType(row.getCell(3).toString());
							test.setNo_Of_Instances(row.getCell(4).toString());
							if (Integer.parseInt(test.getNo_Of_Instances()) > count1) {
								count1 = Integer.parseInt(test.getNo_Of_Instances());
							}
							test.setLocality(row.getCell(5).toString());
							test.setToolName(row.getCell(6).toString());
							test.setAppType(row.getCell(7).toString());
							if (test.getToolName().equalsIgnoreCase("Appium")) {
								if (platform.length < Integer.parseInt(test.getNo_Of_Instances())) {
									test.setPlatformName(platform[0]);
								} else {
									test.setPlatformName(platform[l]);
								}
								String udid[] = row.getCell(10).toString().split("-");
								test.setUdid(udid[l]);
								String deviceNames[] = row.getCell(12).toString().split("-");
								test.setDeviceName(deviceNames[l]);
							}
							if (row.getCell(5).toString().equalsIgnoreCase("Grid")||row.getCell(5).toString().equalsIgnoreCase("Cloud")) {
								test.setRemoteUrl(row.getCell(11 + l).toString());
							}
							if (browser.length < Integer.parseInt(test.getNo_Of_Instances())) {
								test.setBrowser(browser[0]);
							} else {
								test.setBrowser(browser[l]);
							}
							}
							list.add(test);
						}
						System.out.println("testnames"+list.size()+"Get"+list.get(0));
					}
				}
				testRunner.add(list);
			}
			return testRunner;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}


	public static String getAppProperties(String key) throws IOException {
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

}

