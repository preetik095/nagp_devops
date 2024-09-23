Introduction
This README file provides instructions to be followed for setup and execution.

Prerequisites:
1. Java 8 and above
2. TestNG latest version
3. Maven 
4. IDE - Eclipse/IntelliJ IDEA

Setup Instructions
1. Clone/download the repository: Download the code to your local machine and import into your IDE either eclipse or IntelliJ IDEA.
2. Install Dependencies: Install TestNG and Selenium WebDriver dependencies from dependency management tool such as Maven or Gradle. Other dependencies like below:
	a. log4j-core & log4j-api - for logging 
	b. extentreports - for reporting
3. WebDriver Configuration: Download the appropriate WebDriver executable for your browser (Chrome, Firefox, IE, etc.) and configure it in your project.
4. Config Data & Test Data: Prepare the config.properties file for configuration details & testData.proprties for reading test data required to test application.
5. Test Cases: Create TestNG page classes and test classes for various functionalities of the application.

Framework Structure
1. Base package - contains the base class having all the configuration
2. Generics package - contains the common function class 
3. Listeners package - contains the listeners classes for reporting and failed test cases re-execution
4. Pages package - contains all page classes of the application
5. Reporting package - contains the reporting class
6. Utility package - contains the utility classes
7. TestCases package - contains all the test cases of the page classes

Execution Steps
A. Run Tests: Execute the test suite using testng.xml. You can run the entire suite or specific test classes depending on your requirements.

B. Analyze Results: After test execution, review the Extent report generated under extentReports folder along with the screenshots for failed cases.

C. Logging & Debugging: If any test cases fail, all the logs are generated in console as well as in log file under logs folder.

D. Run Tests from command line: 
1. Run the SeleniumAssignment.bat file to run the entire suite from command line
2. Go to project directory using the command cd <project directory>
	mvn compile test --> run this command to execute the test from command line

E. Retry failed cases: 
1. If any test cases faile, testng-failed.xml file gets generated for failed test cases under test-output folder. 
  Update the path of the testng-failed.xml file into the pom.xml like below and run the SeleniumAssignment.bat file for failed cases or just type mvn test in command line:
  <configuration>
    				<suiteXmlFiles>
        				<suiteXmlFile>src/test/resources/testng.xml</suiteXmlFile>
        			<!-- <suiteXmlFile>test-output/testng-failed.xml</suiteXmlFile> -->	
    				</suiteXmlFiles>
        			<properties>
            			<property>
							<name>suitethreadpoolsize</name>
							<value>2</value>
            			</property>
        			</properties>     
  </configuration>  
  
2. Another way for failed cases re-execution is create testng.xml file, add the RetryListener class path and mention the failed classes. Execute the testng.xml file for failed cases.
<suite name="Suite">
	<listeners>
		<listener class-name="listeners.RetryListener" />
	</listeners>
  <!-- <test thread-count="5" name="Test"> -->
  <test name="HomePageTest">
    <classes>
      <class name="testCases.HomePageTest"/>
    </classes>
  </test> <!-- Test -->
  </suite> <!-- Suite -->     
