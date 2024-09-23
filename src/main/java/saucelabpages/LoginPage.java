package saucelabpages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import base.Base_sauceLab;
import generics.GenericFunctions;
import utility.ConfigManager;
import utility.ConfigManager_Sauce;

public class LoginPage extends Base_sauceLab {
	
	private GenericFunctions generics = new GenericFunctions();
	private static final Logger log = LogManager.getLogger(LoginPage.class);
	
	private static final String username_id = "user-name";
	private static final String password_id = "password";
	private static final String loginBtn_id = "login-button";
	private static final String product_text_title_xpath = "//span[text()='Products']";
	private static final String logoutSideBar_xpath = "//button[@id='react-burger-menu-btn']";
	private static final String logoutBtn_id = "logout_sidebar_link";
	
	private static final String USER_NAME = ConfigManager_Sauce.getInstance().getString("userName");
	private static final String PASSWORD = ConfigManager_Sauce.getInstance().getString("password");
	
	public By getUsername_input() {
	    return By.id(username_id);
	}
	
	
	public By getPassword_input() {
	    return By.id(password_id);
	}
	
	
	public By getLogin_button() {
	    return By.id(loginBtn_id);
	}
	
	
	public By getproduct_text() {
	    return By.id(product_text_title_xpath);
	}
	
	
	public By getlogoutSideBar() {
	    return By.id(logoutSideBar_xpath);
	}
	
	
	public By getlogout_Btn() {
	    return By.id(logoutBtn_id);
	}
	
	public void login() {
		driver.findElement(getUsername_input()).sendKeys(USER_NAME);
		driver.findElement(getPassword_input()).sendKeys(PASSWORD);
		
		generics.clickOnElement(driver, getLogin_button());
	}
	
	
	public String loginSuccess() {
		String text = driver.findElement(getproduct_text()).getText();
		return text;
	}
	
	
	
	public void logout() {
		generics.clickOnElement(driver, getlogoutSideBar());
		generics.clickOnElement(driver, getLogin_button());
	}
}
