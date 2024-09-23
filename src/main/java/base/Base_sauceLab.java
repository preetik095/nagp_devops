package base;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import reporting.ExtentManager;
import saucelabpages.LoginPage;
import saucelabpages.ProductSearch;
import utility.ConfigManager_Sauce;

public class Base_sauceLab {
	
	
	private static final String BASE_URL = ConfigManager_Sauce.getInstance().getString("baseUrl");
	private static final String BROWSER_NAME = ConfigManager_Sauce.getInstance().getString("browser");
	protected static final String PAGE_LOAD_TIMEOUT = ConfigManager_Sauce.getInstance().getString("pageLoadTimeOut");
	protected static final String IMPLICITLY_WAIT = ConfigManager_Sauce.getInstance().getString("implicitlyWait");
	protected static WebDriver driver;
	protected static String testMethodName;
	
	
	protected long pageLoadTimeOut = Long.valueOf(PAGE_LOAD_TIMEOUT);
	protected long implicitlyWait = Long.valueOf(IMPLICITLY_WAIT);
	
	protected static final Logger log = LogManager.getLogger(Base_sauceLab.class);
	
	
	public LoginPage login;
	public ProductSearch product;
	
	// Method to initialize WebDriver
	@BeforeSuite(alwaysRun=true)
	public WebDriver Setup() throws IOException {

		// Initialize WebDriver based on browserName
		switch (BROWSER_NAME.toLowerCase()) {
			case "chrome":
				driver = initializeChromeDriver();
				break;
			case "firefox":
				driver = initializeFirefoxDriver();
				break;
			case "ie":
				driver = initializeIEDriver();
				break;
			default:
				throw new IllegalArgumentException("Invalid browser name: " + BROWSER_NAME);
		}

		// Set WebDriver configurations
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeOut));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitlyWait));
		
		return driver;
	}

	//initialize page classes objects
	@BeforeClass
	public void initializeObjects() {
		login = new LoginPage();
		product = new ProductSearch();
	}

	//launch url
	@BeforeMethod
	public void openURL() {
		driver.get(BASE_URL);
		log.info("Base URL launched: "+ BASE_URL);
		
	}
	
	
	@BeforeMethod(alwaysRun=true)
    public void getMethodName(Method method) {
     
    	log.info("Generating testmethod name");
    	testMethodName = method.getName();
    	
    } 
	
	 @AfterMethod
	 public void getResult(ITestResult result) throws Exception{
		ExtentManager.getResult(result);
	 } 
	    
	    
	 @AfterTest
	 public void endReport() {
		 ExtentManager.generateReport();
	 } 
	    
	 
	// Method to close the browser
	 @AfterSuite(alwaysRun=true)
	 public void closeBrowser() {
		if (driver != null) {
			driver.quit();
			log.info("Quit the driver session");
		}
	}
	 
	 
	 
	// Method to initialize ChromeDriver
	private WebDriver initializeChromeDriver() {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications", "--remote-allow-origins=*", "--disable-popup-blocking", "--disable-infobars");
			options.setBrowserVersion("123");
			WebDriver chromeDriver = new ChromeDriver(options);
			log.info("Chrome driver initialized");
			return chromeDriver;
		}


	// Method to initialize FirefoxDriver
	private WebDriver initializeFirefoxDriver() {
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("dom.webnotifications.enabled", false);
			FirefoxOptions options = new FirefoxOptions();
			WebDriver firefoxDriver = new FirefoxDriver(options);
			log.info("Firefox driver initialized");
			return firefoxDriver;
		}


	// Method to initialize InternetExplorerDriver
	private WebDriver initializeIEDriver() {
			InternetExplorerOptions options = new InternetExplorerOptions().requireWindowFocus();
			WebDriver ieDriver = new InternetExplorerDriver(options);
			log.info("Internet Explorer driver initialized");
			return ieDriver;
		}

}
