// Common functions used between page classes
package generics;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.Base;


public class GenericFunctions extends Base {

	WebElement element;
	private static final Logger log = LogManager.getLogger(GenericFunctions.class);

	// Implicit wait
	public void implicitlyWait(WebDriver driver, Long seconds) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
	}

	// Page load timeout
	public void pageLoadTimeOut(WebDriver driver, Long seconds) {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(seconds));
	}

	// Explicit wait for element to be clickable
	public void waitForElementToBeClickable(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}

	// Explicit wait for element visibility
	public void waitForElementToBeVisible(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	// Fluent wait with polling
	public void fluentWait(WebDriver driver, By by) throws TimeoutException {
		FluentWait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(30))  // Maximum time to wait
				.pollingEvery(Duration.ofMillis(500)) // Polling frequency
				.ignoring(Exception.class);           // Ignore exceptions
		wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(by);
			}
		});
	}

	// Capture screenshot for failed test cases
	public String takeScreenShot(WebDriver driver, String testCaseName, String path, String errorMessage) throws IOException {
		if (driver == null) {
			log.error("Driver is not initialized. Cannot capture screenshot.");
			return null;
		}
		try {
			File srcPath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			int index = errorMessage.indexOf("expected");
			if (index > 0) {
				errorMessage = errorMessage.substring(0, index);
			}
			String destPath = path + "/" + errorMessage + ".jpg";
			File finalDestPath = new File(destPath);
			FileHandler.copy(srcPath, finalDestPath);
			return destPath;
		} catch (IOException exception) {
			exception.printStackTrace();
			return null;
		}
	}

	// Scroll to the bottom of the page
	public void scrollDown(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	// Switch to new tab or window
	public void switchToNewTab(WebDriver driver, String mainWindowHandle) {
		for (String winHandle : driver.getWindowHandles()) {
			if (!winHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(winHandle);
				break;
			}
		}
	}

	// Switch to alert
	public String switchToAlert(WebDriver driver) {
		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText();
		alert.accept();
		return alertText;
	}

	// Click on element
	public void clickOnElement(WebDriver driver, By by) {
		driver.findElement(by).click();
	}

	// Scroll to particular element
	public void scrollToElement(WebDriver driver, By by) throws InterruptedException {
		WebElement element = driver.findElement(by);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		Thread.sleep(500);
	}

	// Autosuggest dropdown for from & to city of rail booking page
	public void waitForAutoSuggest_StationDropdown(WebDriver driver, By by, String value) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		List<WebElement> listOfAutoSuggestStationNames = driver.findElements(by);
		for (WebElement matchedStation : listOfAutoSuggestStationNames) {
			String stationName = matchedStation.findElement(By.className("stn_city_name")).getText();
			if (stationName.equalsIgnoreCase(value)) {
				matchedStation.click();
				break;
			}
		}
	}

	// Autosuggest dropdown for from & to city of bus booking page
	public void waitForAutoSuggest_BusDropdown(WebDriver driver, By by, String value, By classNamePath) {
		List<WebElement> listOfAutoSuggestCityNames = driver.findElements(by);
		for (WebElement matchedCity : listOfAutoSuggestCityNames) {
			String cityName = matchedCity.findElement(classNamePath).getText();
			if (cityName.equalsIgnoreCase(value)) {
				matchedCity.click();
				break;
			}
		}
	}
}
