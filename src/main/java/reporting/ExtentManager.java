//extent report generation class

package reporting;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.common.io.Files;

import generics.GenericFunctions;

public class ExtentManager {

	 static ExtentReports extentReports;
	 static ExtentSparkReporter extentSparkReporter;
	 static ExtentTest extentTest;
	 static GenericFunctions generics = new GenericFunctions();
	 static WebDriver driver;
	 static Date date;
	 static String currentTestResult;
	 static String reportPathWithTimeStamp;
	 static String screenshotsFolderPath;
	 static File file;
	 private static Logger log = LogManager.getLogger(ExtentManager.class);;
	 
	 
	//configuration for extent report
	public static void createReport(String title) {
		
		if(extentReports == null) {
			
			extentReports = new ExtentReports();
			date = new Date();
			
			//create currentTestResult report dir
			try {
				
				currentTestResult = System.getProperty("user.dir")+"/extentReports"+"/CurrentTestResult";
				file = new File(currentTestResult);	
				
				if(file.exists()) {
					
					//call archive report method
					archivedReport(currentTestResult);
				}
				else {
					
					//else create directory
					file.mkdir();
				}
				
			
				//check if the folder is having data
				if(Files.isDirectory() != null) {
						
					reportPathWithTimeStamp = currentTestResult + "/CurrentTestResult_"+ date.toString().replaceAll(":", "-");
										
					screenshotsFolderPath = reportPathWithTimeStamp+"/Screenshots/";
					
					file = new File(screenshotsFolderPath);	
					
					file.mkdirs();   //create dynamic report folder name + screenshot folder
					
					extentSparkReporter = new ExtentSparkReporter(reportPathWithTimeStamp);
					extentSparkReporter.config().setReportName("Automation Test Report");
					extentSparkReporter.config().setTheme(Theme.STANDARD);
					extentSparkReporter.config().setDocumentTitle("Automation Test Report");
					extentSparkReporter.config().setEncoding("uft-8");
					extentSparkReporter.config().setTimeStampFormat("dd-MM-yyyy HH:mm:ss");
					
					extentReports.setSystemInfo("Environment", "Testing");
					extentReports.setSystemInfo("Author", "QA Team");
					
					extentReports.attachReporter(extentSparkReporter);
				}	
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
			extentTest = extentReports.createTest(title);
		}
	

	    //archive current test result to archieve folder
		public static void archivedReport(String currentTestResultPath) {
			
			String archivedTestResultsPath = System.getProperty("user.dir") + "/extentReports/ArchivedTestResults";

	        File currentTestResultDir = new File(currentTestResultPath);
	        File archivedTestResultsDir = new File(archivedTestResultsPath);

	        try {
	        	
	        	// Check if CurrentTestResult directory is not empty
		        if (currentTestResultDir.isDirectory() && currentTestResultDir.list().length > 0) {
		            
		        	// Ensure that ArchivedTestResults directory exists
		            if (!archivedTestResultsDir.exists()) {
		                
		            	archivedTestResultsDir.mkdirs();
		            }

		            // Move files from CurrentTestResult to ArchivedTestResults
		            File[] filesToMove = currentTestResultDir.listFiles();
		            if (filesToMove != null) {
		                for (File file : filesToMove) {
		                    try {
		                    	
		                     	if(file != null && file.exists()) {                  	
		                     		Files.move(file, new File(archivedTestResultsDir, file.getName()));
		                     	}
		                    } catch (IOException e) {
		                        e.printStackTrace();
		                    }
		                }
		            }

		            log.info("Files moved successfully to "+archivedTestResultsPath+" directory.");
		        } else {
		        	log.info(currentTestResultPath+" directory is empty.");
		        }
	        	
	        } catch(Exception e) {
	        	log.error("Directory not found: " + e.getMessage());
	        }
	        
	    }
		
		
		//report test result
	    public static void getResult(ITestResult result) throws Exception {
	    
	    	if (result.getStatus() == ITestResult.FAILURE) {
			
	    		//calling capture screenshot method
	    		String screeshotPath = generics.takeScreenShot(driver, result.getMethod().getMethodName(), screenshotsFolderPath, result.getThrowable().getMessage());
			
	    		//MarkupHelper is used to display the output in different colors
	    		extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case FAILED", ExtentColor.RED));
	    		extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case FAILED", ExtentColor.RED));					
			
	    		//appending screenshot to the report
				if(screeshotPath!=null)
	    		extentTest.addScreenCaptureFromPath(screeshotPath);
			
			}
			else if(result.getStatus() == ITestResult.SKIP){
				
				extentTest.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case SKIPPED", ExtentColor.ORANGE));
			}
			else if(result.getStatus() == ITestResult.SUCCESS)
			{
				extentTest.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
			}
		
		}

		
		//generate extent report
		public static void generateReport() {
			extentReports.flush();
			log.debug("Extent report generated");
		}

}
