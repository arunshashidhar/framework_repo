package delta.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import generics.Excel;
import generics.Property;
import generics.Utility;

public class DeltaDriver extends BaseDriver{
	public String browser;
	public String hubURL;
	@BeforeMethod
	public void launchApp(XmlTest xmltest) throws FileNotFoundException, IOException{
		browser = xmltest.getParameter("browser");
		hubURL = xmltest.getParameter("hubURL");
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setBrowserName(browser);
		dc.setPlatform(Platform.ANY);
		driver = new RemoteWebDriver(new URL(hubURL),dc);
		
//		if(browser.equals("chrome"))
//		{
//			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
//			driver= new ChromeDriver();
//		}
//		else
//		{
//			driver= new FirefoxDriver();
//		}
		String appURL = Property.getPropertyValue(configPptPath, "URL");
		String TimeOut = Property.getPropertyValue(configPptPath, "TimeOut");
//		driver = new FirefoxDriver();
		driver.manage().window().maximize();	
		driver.get(appURL);
		driver.manage().timeouts().implicitlyWait(Long.parseLong(TimeOut), TimeUnit.SECONDS);
	}
	@Test(dataProvider = "getScenarios")
	public void testScenarios(String scenariosSheet, String executeStatus){		
		System.out.println(scenariosSheet);
		System.out.println(executeStatus);
		testReport = eReport.startTest(browser + "_" + scenariosSheet);
		if(executeStatus.equalsIgnoreCase("yes"))
		{
			int rc = Excel.getRowCount(scenariosPath, scenariosSheet);
			for(int i = 1; i<=rc;i++)
			{
				String description = Excel.getCellValue(scenariosPath, scenariosSheet, i, 0);
				String action = Excel.getCellValue(scenariosPath, scenariosSheet, i, 1);
				String input1 = Excel.getCellValue(scenariosPath, scenariosSheet, i, 2);
				String input2 = Excel.getCellValue(scenariosPath, scenariosSheet, i, 3);
				testReport.log(LogStatus.INFO, "description:"+description+" action:"+action+" input1:"+input1+" input2:"+input2);
				KeyWord.executeKeyWord(driver, action, input1, input2);	
//				Assert.fail();
			}
		}
		else
		{
			testReport.log(LogStatus.SKIP, "Execution status is 'NO'");
			throw new SkipException("Skipping this exception");
		}
	}
	@AfterMethod
	public void quitApp(ITestResult test){
		if(test.getStatus()==2)
		{
			String pImage=Utility.getPageScreenShot(driver, imageFolderPath);
			String p = testReport.addScreenCapture("."+pImage);
			testReport.log(LogStatus.FAIL, "Page screen shot:" +p);
		}
		driver.close();
		eReport.endTest(testReport);
	}
}