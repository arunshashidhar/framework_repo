package delta.main;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.xml.XmlTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import generics.Excel;

public class BaseDriver implements AutomationConstants{
	public WebDriver driver;
	public static ExtentReports eReport;
	public ExtentTest testReport;
	@DataProvider
	public String[][] getScenarios()
	{	
		int rc = Excel.getRowCount(controllerPath, controllerSheet);
		String[][] data = new String[rc][2];
		for(int i = 1; i<=rc;i++)
		{
			String ScenarioName = Excel.getCellValue(controllerPath, controllerSheet, i, 0);
			String ExecutionStatus = Excel.getCellValue(controllerPath, controllerSheet, i, 1);
			data[i-1][0] = ScenarioName;
			data[i-1][1] = ExecutionStatus;
		}
		return data;
	}
	@BeforeSuite
	public void initFrameWork(XmlTest xmltest)
	{	
//		String browser = xmltest.getParameter("browser");
		eReport = new ExtentReports(reportFilePath);
		
	}
	@AfterSuite
	public void endFrameWork(){
		eReport.flush();
	}
}

//@DataProvider
//public String[][] getScenarios()
//{
//	String[][] data = new String[2][1];
//	data[0][0] = "Scenario1";
//	data[1][0] = "Scenario2";
//	return data;
//}
//String ScenarioName = Excel.getCellValue(controllerPath, controllerSheet, 1, 0);
//String ExecutionStatus = Excel.getCellValue(controllerPath, controllerSheet, 1, 1);
//String[][] data = new String[1][2];
//
//data[0][0] = ScenarioName;
//data[0][1] = ExecutionStatus;
