package saucelabpages;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import base.Base_sauceLab;
import generics.GenericFunctions;
import utility.Constants_sauceLab;

public class ProductSearch extends Base_sauceLab {
	
	private GenericFunctions generics = new GenericFunctions();
	private static final Logger log = LogManager.getLogger(ProductSearch.class);
	
	private static final String productSortDropdown_xpath = "//select[@class='product_sort_container']";
	private static final String productContainer_id = "inventory_container";
	private static final String productContainerPrice_xpath = "//div[@class='inventory_list']//div[1]//div[2]//div[2]//div[1]";
	private static final String addToCartBtn_id = "add-to-cart-sauce-labs-onesie";
	private static final String cartImg_xpath = "//span[@class='shopping_cart_badge']";
	private static final String checkoutBtn_id = "checkout";
	private static final String productName_xpath = "//div[text()='Sauce Labs Backpack']";
	private static final String firstName_id = "first-name";
	private static final String lastName_id = "last-name";
	private static final String postalCode_id = "postal-code";
	private static final String continueBtn_id = "continue";
	private static final String finishBtn_id = "finish";
	private static final String confirmOrderText_xpath = "//h2[@class='complete-header']";
	private static final String removeBtn_id = "remove-sauce-labs-backpack";
	
	public By getproductSort_Dropdown() {
	    return By.xpath(productSortDropdown_xpath);
	}
	
	
	public By getproductContainer() {
	    return By.id(productContainer_id);
	}
	
	
	public By getproductContainerPrice() {
	    return By.xpath(productContainerPrice_xpath);
	}
	
	
	public By getaddToCart_Btn() {
	    return By.id(addToCartBtn_id);
	}
	
	public By getproductName() {
	    return By.xpath(productName_xpath);
	}

	
	public By getcart_Img() {
	    return By.xpath(cartImg_xpath);
	}
	
	public By getcheckoutBtn() {
	    return By.id(checkoutBtn_id);
	}
	
	
	public By getfirstName() {
	    return By.id(firstName_id);
	}
	
	
	public By getlastName() {
	    return By.id(lastName_id);
	}
	
	
	public By getpostalCode() {
	    return By.id(postalCode_id);
	}
	
	
	public By getcontinueBtn() {
	    return By.id(continueBtn_id);
	}
	
	
	public By getfinishBtn() {
	    return By.id(finishBtn_id);
	}
	
	
	public By getconfirmOrderText() {
	    return By.xpath(confirmOrderText_xpath);
	}
	
	
	public By getremoveBtn() {
	    return By.id(removeBtn_id);
	}
	
	
	
	public void addToCart() {
		
		String productName = driver.findElement(getproductName()).getText();
		if(productName.equalsIgnoreCase(Constants_sauceLab.productItemToPurchase)) {
			generics.clickOnElement(driver, getaddToCart_Btn());
		}
		else {
			log.error(productName + "not found");
		}
		
		generics.clickOnElement(driver, getcart_Img());
		generics.clickOnElement(driver, getcheckoutBtn());
		
		driver.findElement(getfirstName()).sendKeys("firstname");
		driver.findElement(getlastName()).sendKeys("lastname");
		driver.findElement(getpostalCode()).sendKeys("4226");
		
		generics.clickOnElement(driver, getcontinueBtn());
		generics.clickOnElement(driver, getfinishBtn());
	}
	
	
	public void removeItemFromCart() {
		
		generics.clickOnElement(driver, getremoveBtn());
		
	}
	
	
	public boolean verifyOrderConfirmation() {
		String confirmOrderText = driver.findElement(getconfirmOrderText()).getText();
		if(confirmOrderText.equals(Constants_sauceLab.confirmOrderText)) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
