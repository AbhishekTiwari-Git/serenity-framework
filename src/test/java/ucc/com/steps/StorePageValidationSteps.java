package ucc.com.steps;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.en.Then;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import ucc.com.pages.ui.ComStore;
import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;

public class StorePageValidationSteps {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StorePageValidationSteps.class);
	
	@Steps
	CommonFunc comFun;
	
	@Steps
	HomePage homepage;
	
	@Steps
	ComStore comStore;

	@Managed
	WebDriver driver;
	
	
	@Then("there is a unique eight character promo code in the store page url")
	public void there_is_a_unique_eight_character_promo_code_in_the_store_page_url() throws Throwable {
	    String url = driver.getCurrentUrl();
	    
	    if (!url.contains("promo=")) {
	    	throw new Exception("URL has no promo code.");
	    }
	    
	    String[] urlSplit = url.split("=");
	    String promoCode = urlSplit[1];
	    Assert.assertTrue("Lacking a proper eight character promo code.", promoCode.length() == 8);
	    
	    LOGGER.info("there is a unique eight character promo code in the store page url");
	}

	@Then("^user sees the Product page and along the bottom of the screen the SATISFACTION GUARANTEED message (.*) and submessage (.*) are displayed$")
	public void user_sees_the_Product_page_and_along_the_bottom_of_the_screen_the_SATISFACTION_GUARANTEED_message_and_submessage_are_displayed(String SGtext, String SGsubtext) {
		Assert.assertTrue("Satisfaction top text not displayed.", comStore.is_satisfactionTextTopDisplayed());
		Assert.assertTrue("Satisfaction top text is incorrect.", SGtext.equals(comStore.satisfactionTextTop()));
		
		Assert.assertTrue("Satisfaction bottom text not displayed.", comStore.is_satisfactionTextBottomDisplayed());
		Assert.assertTrue("Satisfaction bottom text is incorrect.", SGsubtext.equals(comStore.satisfactionTextBottom()));
		
		LOGGER.info("user sees the SATISFACTION GUARANTEED message and submessage");
	}

	@Then("^user sees the BEST VALUE FREE GIFT message (.*) is displayed on the annual product image$")
	public void user_sees_the_best_value_free_gift_message_best_value_free_gift_is_displayed_on_the_annual_product_image(String BVFGtext) {
		Assert.assertTrue("BEST VALUE + FREE GIFT not displayed.", comStore.is_bestValueFreeGiftDisplayed());
		Assert.assertTrue("BEST VALUE + FREE GIFT text is incorrect.", BVFGtext.equals(comStore.bestValueFreeGift()));
		
		LOGGER.info("user sees the BEST VALUE FREE GIFT message");
	}

	@Then("^user can see the INCLUDES section (.*) under the monthly product image$")
	public void user_can_see_the_INCLUDES_section_under_the_monthly_product_image(String monthlyIncludes) {
		Assert.assertTrue("Monthly INCLUDES list not displayed.", comStore.is_monthlyIncludesMesgDisplayed());
		String monthlyWebOneLine = comStore.monthlyIncludesMesg().replaceAll("\n", "");
		String monthlyInputFixed = monthlyIncludes.replace("�", "–");
		Assert.assertTrue("Monthly INCLUDES lists do not match.", monthlyInputFixed.equals(monthlyWebOneLine));
		
		LOGGER.info("user sees the INCLUDES list under the monthly product image");
	}

	@Then("^user can see the INCLUDES section (.*) under annual product image$")
	public void user_can_see_the_INCLUDES_section_under_annual_product_image(String annualIncludes) {
		Assert.assertTrue("Annual INCLUDES list not displayed.", comStore.is_annualIncludesMesgDisplayed());
		String annualWebOneLine = comStore.annualIncludesMesg().replaceAll("\n", "");
		String annualInputFixed = annualIncludes.replace("�", "–");
		Assert.assertTrue("Annual INCLUDES lists do not match.", annualInputFixed.equals(annualWebOneLine));
		
		LOGGER.info("user sees the INCLUDES list under the annual product image");
	}
	
}
