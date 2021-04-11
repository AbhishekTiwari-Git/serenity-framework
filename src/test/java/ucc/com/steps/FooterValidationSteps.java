package ucc.com.steps;


import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Managed;
import io.cucumber.java.en.Then;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;

import ucc.com.pages.ui.ComCart;
import ucc.com.pages.ui.ComCheckout;
import ucc.com.pages.ui.ComOrderConfirmation;
import ucc.com.pages.ui.ComStore;
import ucc.pages.ui.CommonFunc;

import java.time.Year;
import java.util.ArrayList;

import org.junit.Assert;

public class FooterValidationSteps {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FooterValidationSteps.class);
	
	@Steps
	CommonFunc comFun;
	
	@Steps
	ComStore comStore;
	
	@Steps
	ComCart comCart;
	
	@Steps
	ComCheckout comCheckout;
	
	@Steps
	ComOrderConfirmation comOrdConf;

	@Managed
	WebDriver driver;

	// store page steps
	
	@Then("user sees NEJM Catalyst logo on the top left of the store page")
	public void user_sees_nejm_catalyst_logo_on_the_top_left_of_the_store_page() {
	    Assert.assertTrue("NEJM Catalyst logo is not visible on the store page.", comStore.isThere_nejmCatalystLogo());
	    LOGGER.info("NEJM Catalyst logo is visible on the store page.");
	}

	@Then("user sees NEJM Group logo on the bottom right of the store page")
	public void user_sees_nejm_group_logo_on_the_bottom_right_of_the_store_page() {
	    Assert.assertTrue("NEJM Group logo is not visible on the store page.", comStore.isThere_nejmGroupCopyrightLogo());
	    LOGGER.info("NEJM Group logo is visible on the store page.");
	}

	@Then("^user sees NEJM Group product text (.*) on bottom left of the store page$")
	public void user_sees_nejm_group_product_text_abc_on_bottom_left_of_the_store_page(String inputText) {
	    Assert.assertEquals("Expected NEJM Group product text is not visible on the store page.", inputText, comStore.get_nejmGroupProductText());
	    LOGGER.info("Expected NEJM Group product text is visible on the store page.");
	}

	@Then("user can see the copyright year set to current year at the bottom of the store page")
	public void user_can_see_the_copyright_year_set_to_current_year_at_the_bottom_of_the_store_page() {
	    String copyText = comStore.get_nejmGroupCopyrightText();
	    String currentYear = Year.now().toString();
	    Assert.assertTrue("Copyright year is not set to the current year on the store page.", copyText.contains(currentYear));
	    LOGGER.info("Copyright year is set to the current year on the store page.");
	}

	@Then("user can open Terms of Use page in new tab from store page")
	public void user_can_open_terms_of_use_page_in_new_tab_from_store_page() throws Throwable {
		comStore.click_termsOfUseLink();
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(1));
	    
	    comFun.Verify_the_current_Title("NEJM Group");
	    Assert.assertEquals("The incorrect page has been opened.", "Terms of Use", comStore.get_termsOfUsePageHeaderText());
	    LOGGER.info("User can open Terms of Use page in new tab from store page.");
	    
	    driver.close();
	    driver.switchTo().window(tabs.get(0));
	}

	@Then("user can open Privacy Policy page in new tab from store page")
	public void user_can_open_privacy_policy_page_in_new_tab_from_store_page() throws Throwable {
		comStore.click_privacyPolicyLink();
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(1));
	    
	    comFun.Verify_the_current_Title("NEJM Group");
	    Assert.assertEquals("The incorrect page has been opened.", "Privacy Policy", comStore.get_privacyPolicyPageHeaderText());
	    LOGGER.info("User can open Privacy Policy page in new tab from store page.");
	    
	    driver.close();
	    driver.switchTo().window(tabs.get(0));
	}
	
	// cart page steps

	@Then("user sees NEJM Catalyst logo on the top left of the cart page")
	public void user_sees_nejm_catalyst_logo_on_the_top_left_of_the_cart_page() {
		Assert.assertTrue("NEJM Catalyst logo is not visible on the cart page.", comCart.isThere_nejmCatalystLogo());
	    LOGGER.info("NEJM Catalyst logo is visible on the cart page.");
	}

	@Then("user sees NEJM Group logo on the bottom right of the cart page")
	public void user_sees_nejm_group_logo_on_the_bottom_right_of_the_cart_page() {
		Assert.assertTrue("NEJM Group logo is not visible on the cart page.", comCart.isThere_nejmGroupCopyrightLogo());
	    LOGGER.info("NEJM Group logo is visible on the cart page.");
	}

	@Then("^user sees NEJM Group product text (.*) on bottom left of the cart page$")
	public void user_sees_nejm_group_product_text_abc_on_bottom_left_of_the_cart_page(String inputText) {
		Assert.assertEquals("Expected NEJM Group product text is not visible on the cart page.", inputText, comCart.get_nejmGroupProductText());
	    LOGGER.info("Expected NEJM Group product text is visible on the cart page.");
	}

	@Then("user can see the copyright year set to current year at the bottom of the cart page")
	public void user_can_see_the_copyright_year_set_to_current_year_at_the_bottom_of_the_cart_page() {
		String copyText = comCart.get_nejmGroupCopyrightText();
	    String currentYear = Year.now().toString();
	    Assert.assertTrue("Copyright year is not set to the current year on the cart page.", copyText.contains(currentYear));
	    LOGGER.info("Copyright year is set to the current year on the cart page.");
	}

	@Then("user can open Terms of Use page in new tab from cart page")
	public void user_can_open_terms_of_use_page_in_new_tab_from_cart_page() throws Throwable {
		comCart.click_termsOfUseLink();
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(1));
	    
	    comFun.Verify_the_current_Title("NEJM Group");
	    Assert.assertEquals("The incorrect page has been opened.", "Terms of Use", comCart.get_termsOfUsePageHeaderText());
	    LOGGER.info("User can open Terms of Use page in new tab from cart page.");
	    
	    driver.close();
	    driver.switchTo().window(tabs.get(0));
	}

	@Then("user can open Privacy Policy page in new tab from cart page")
	public void user_can_open_privacy_policy_page_in_new_tab_from_cart_page() throws Throwable {
		comCart.click_privacyPolicyLink();
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(1));
	    
	    comFun.Verify_the_current_Title("NEJM Group");
	    Assert.assertEquals("The incorrect page has been opened.", "Privacy Policy", comCart.get_privacyPolicyPageHeaderText());
	    LOGGER.info("User can open Privacy Policy page in new tab from cart page.");
	    
	    driver.close();
	    driver.switchTo().window(tabs.get(0));
	}
	
	// checkout page steps

	@Then("user sees NEJM Catalyst logo on the top left of the checkout page")
	public void user_sees_nejm_catalyst_logo_on_the_top_left_of_the_checkout_page() {
		Assert.assertTrue("NEJM Catalyst logo is not visible on the checkout page.", comCheckout.isThere_nejmCatalystLogo());
	    LOGGER.info("NEJM Catalyst logo is visible on the checkout page.");
	}

	@Then("user sees NEJM Group logo on the bottom right of the checkout page")
	public void user_sees_nejm_group_logo_on_the_bottom_right_of_the_checkout_page() {
		Assert.assertTrue("NEJM Group logo is not visible on the checkout page.", comCheckout.isThere_nejmGroupCopyrightLogo());
	    LOGGER.info("NEJM Group logo is visible on the checkout page.");
	}

	@Then("^user sees NEJM Group product text (.*) on bottom left of the checkout page$")
	public void user_sees_nejm_group_product_text_abc_on_bottom_left_of_the_checkout_page(String inputText) {
		Assert.assertEquals("Expected NEJM Group product text is not visible on the checkout page.", inputText, comCheckout.get_nejmGroupProductText());
	    LOGGER.info("Expected NEJM Group product text is visible on the checkout page.");
	}

	@Then("user can see the copyright year set to current year at the bottom of the checkout page")
	public void user_can_see_the_copyright_year_set_to_current_year_at_the_bottom_of_the_checkout_page() {
		String copyText = comCheckout.get_nejmGroupCopyrightText();
	    String currentYear = Year.now().toString();
	    Assert.assertTrue("Copyright year is not set to the current year on the checkout page.", copyText.contains(currentYear));
	    LOGGER.info("Copyright year is set to the current year on the checkout page.");
	}

	@Then("user can open Terms of Use page in new tab from checkout page")
	public void user_can_open_terms_of_use_page_in_new_tab_from_checkout_page() throws Throwable {
		comCheckout.click_termsOfUseLink();
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(1));
	    
	    comFun.Verify_the_current_Title("NEJM Group");
	    Assert.assertEquals("The incorrect page has been opened.", "Terms of Use", comCheckout.get_termsOfUsePageHeaderText());
	    LOGGER.info("User can open Terms of Use page in new tab from checkout page.");
	    
	    driver.close();
	    driver.switchTo().window(tabs.get(0));
	}

	@Then("user can open Privacy Policy page in new tab from checkout page")
	public void user_can_open_privacy_policy_page_in_new_tab_from_checkout_page() throws Throwable {
		comCheckout.click_privacyPolicyLink();
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(1));
	    
	    comFun.Verify_the_current_Title("NEJM Group");
	    Assert.assertEquals("The incorrect page has been opened.", "Privacy Policy", comCheckout.get_privacyPolicyPageHeaderText());
	    LOGGER.info("User can open Privacy Policy page in new tab from checkout page.");
	    
	    driver.close();
	    driver.switchTo().window(tabs.get(0));
	}
	
	// order confirmation page steps

	@Then("user sees NEJM Catalyst logo on the top left of the order confirmation page")
	public void user_sees_nejm_catalyst_logo_on_the_top_left_of_the_order_confirmation_page() {
		Assert.assertTrue("NEJM Catalyst logo is not visible on the order confirmation page.", comOrdConf.isThere_nejmCatalystLogo());
	    LOGGER.info("NEJM Catalyst logo is visible on the order confirmation page.");
	}

	@Then("user sees NEJM Group logo on the bottom right of the order confirmation page")
	public void user_sees_nejm_group_logo_on_the_bottom_right_of_the_order_confirmation_page() {
		Assert.assertTrue("NEJM Group logo is not visible on the order confirmation page.", comOrdConf.isThere_nejmGroupCopyrightLogo());
	    LOGGER.info("NEJM Group logo is visible on the order confirmation page.");
	}

	@Then("^user sees NEJM Group product text (.*) on bottom left of the order confirmation page$")
	public void user_sees_nejm_group_product_text_abc_on_bottom_left_of_the_order_confirmation_page(String inputText) {
		Assert.assertEquals("Expected NEJM Group product text is not visible on the order confirmation page.", inputText, comOrdConf.get_nejmGroupProductText());
	    LOGGER.info("Expected NEJM Group product text is visible on the order confirmation page.");
	}

	@Then("user can see the copyright year set to current year at the bottom of the order confirmation page")
	public void user_can_see_the_copyright_year_set_to_current_year_at_the_bottom_of_the_order_confirmation_page() {
		String copyText = comOrdConf.get_nejmGroupCopyrightText();
	    String currentYear = Year.now().toString();
	    Assert.assertTrue("Copyright year is not set to the current year on the order confirmation page.", copyText.contains(currentYear));
	    LOGGER.info("Copyright year is set to the current year on the order confirmation page.");
	}

	@Then("user can open Terms of Use page in new tab from order confirmation page")
	public void user_can_open_terms_of_use_page_in_new_tab_from_order_confirmation_page() throws Throwable {
		comOrdConf.click_termsOfUseLink();
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(1));
	    
	    comFun.Verify_the_current_Title("NEJM Group");
	    Assert.assertEquals("The incorrect page has been opened.", "Terms of Use", comOrdConf.get_termsOfUsePageHeaderText());
	    LOGGER.info("User can open Terms of Use page in new tab from order confirmation page.");
	    
	    driver.close();
	    driver.switchTo().window(tabs.get(0));
	}

	@Then("user can open Privacy Policy page in new tab from order confirmation page")
	public void user_can_open_privacy_policy_page_in_new_tab_from_order_confirmation_page() throws Throwable {
		comOrdConf.click_privacyPolicyLink();
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	    driver.switchTo().window(tabs.get(1));
	    
	    comFun.Verify_the_current_Title("NEJM Group");
	    Assert.assertEquals("The incorrect page has been opened.", "Privacy Policy", comOrdConf.get_privacyPolicyPageHeaderText());
	    LOGGER.info("User can open Privacy Policy page in new tab from order confirmation page.");
	    
	    driver.close();
	    driver.switchTo().window(tabs.get(0));
	}
	
	// other
	
	@Then("^order confirmation page opens with confirmation message (.*) without closing the driver after$")
	public void order_confirmation_page_opens_with_confirmation_message_thank_you_for_your_order_without_closing_the_driver_after(String order_confirmation_message) throws Throwable {
		String OrderConfirmation = comOrdConf.Order_Confirmation_message();
		Assert.assertEquals("Incorrect order confirmation message.", order_confirmation_message, OrderConfirmation);
	}
	
}
