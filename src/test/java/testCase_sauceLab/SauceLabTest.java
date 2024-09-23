package testCase_sauceLab;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.Base_sauceLab;
import reporting.ExtentManager;
import utility.Constants_sauceLab;


public class SauceLabTest extends Base_sauceLab {
	
	private static final Logger log = LogManager.getLogger(SauceLabTest.class);
	
	
	@Test
	public void loginTest() {
		
		ExtentManager.createReport(testMethodName);
		
		login.login();
		String actualText = login.loginSuccess();
		try {
			
			Assert.assertEquals(actualText,Constants_sauceLab.product_text, "Login not successful");
			log.debug("Login successful");
		}
		catch(Exception e) {
			log.error("Login not successful");
		}	
		
	}
	
	
	@Test
	public void addToCartandConfirmTest() {
		
		ExtentManager.createReport(testMethodName);
		
		login.login();
		product.addToCart();
		boolean orderStatus = product.verifyOrderConfirmation();		
		try {
			
			Assert.assertTrue(orderStatus);
			log.debug("Order placedsuccessfully");
		}
		catch(Exception e) {
			log.error("Error in placing order");
		}	
	}
	
	
	@Test
	public void removeItemTest() {
		
		ExtentManager.createReport(testMethodName);
		
		login.login();
		product.addToCart();
		product.removeItemFromCart();		
		
	}
	
	
	
	@Test
	public void logoutTest() {
		
		ExtentManager.createReport(testMethodName);
		
		login.logout();
		try {
			
		//	Assert.assertEquals(actualText,Constants_sauceLab.product_text, "Login not successful");
			log.debug("Login successful");
		}
		catch(Exception e) {
			log.error("Login not successful");
		}	
		
	}
	

}
