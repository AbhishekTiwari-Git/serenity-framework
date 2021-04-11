package ucc.com.steps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.com.pages.ui.ComCart;
import ucc.com.pages.ui.ComCheckout;
import ucc.com.pages.ui.ComOrderConfirmation;
import ucc.com.pages.ui.ComProductDetails;
import ucc.com.pages.ui.ComStore;
import ucc.cr.pages.catalyst.ui.CreateLead;
import ucc.i.method.accountexp.AccountExpGET;
import ucc.i.method.acs.ACSGET;
import ucc.i.method.aic.AICGET;
import ucc.i.method.icv.ICVGET;
import ucc.i.method.literatum.LiteratumGET;

import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;
import ucc.utils.JsonUtils;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;

public class GuestOrderSteps {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String eml = EnvironmentSpecificConfiguration.from(env_var).getProperty("autoEmail");
	public static Response expResp = null;
	static String TestEmail = null;	
	static String pwd = EnvironmentSpecificConfiguration.from(env_var).getProperty("password");
	static String storeTitle = EnvironmentSpecificConfiguration.from(env_var).getProperty("storePageTitle");
	static String end_pt = null;
	static String ucid = null;
	static String itemNumber = null;
	static String verifyProceedToCheckout = null;
	static String errorMessageOnStorePage = null;
	static String noItemMessage = null;
	static String productName = null;
	public static Response aicResp = null;
	public static Response icvResp = null;
	public static Response acsResp = null;
	public static Response literRespIdentity = null;
	public static Response literRespLicense = null;
	public static Map<String, String> kmap = new HashMap<String, String>();
	private static List<Object> GDPRCountries = new ArrayList<Object>();
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	private static final Logger LOGGER = LoggerFactory.getLogger(GuestOrderSteps.class);
	static String StorePagePartialURL = EnvironmentSpecificConfiguration.from(env_var).getProperty("store.page.partial.url");
	public static Map<String, String> Confirmationmap = new HashMap<String, String>();

	@Steps
	CommonFunc comFun;

	@Steps
	ComStore comStore;

	@Steps
	ComProductDetails comProductDetails;

	@Steps
	ComCart comCart;

	@Steps
	ComCheckout comCheckout;

	@Steps
	ComOrderConfirmation comOrder;

	@Steps
	HomePage homepage;

	@Steps
	LiteratumGET LiteratumGETSteps;

	@Steps
	ICVGET IVSGETSteps;

	@Steps
	AICGET AICGETSteps;

	@Steps
	ACSGET ACSGETSteps;

	@Steps
	AccountExpGET AccExpGETSteps;
	
	@Steps
	CreateLead LEAD;
	
	@Steps
	ConfirmationPriceVerifyHelper PriceVerify;

	@Managed
	WebDriver driver;
	
	
    
	

	@Given("user is on content page")
	public void user_is_on_Catalyst_WebSite() throws Throwable {

		LOGGER.info("user is on content page");
		homepage.launchHomePage();
		kmap.put("email", tUtil.AppendTimestamp(eml));
		
	}
	
	@Given("^User is on Store page having URL with Promo code (.*)$")
	public void user_is_on_Store_WebSite_Homepage(String Promocode) throws Throwable {
		String StorePageURLwithPromo = StorePagePartialURL + Promocode;
		LOGGER.info("user is on store page");
		comFun.clearCookies();
		comFun.Launch_URL(StorePageURLwithPromo);	
		LOGGER.info("store page launched");	
		kmap.put("email", tUtil.AppendTimestamp(eml));

	}

	@When("user clicks on Subscribe button")
	public void user_click_on_subscribe_button() throws Throwable {

		LOGGER.info("user clicks on Subscribe button");
		homepage.clicksubscribe();
	}

	@When("user hovers over Journal link")
	public void user_hovers_Journal() throws Throwable {

		homepage.HoverJournal();
	}

	@When("user click subscribe link in Journal")
	public void user_clicks_Subscribe_in_Journal() throws Throwable {

		homepage.ClickJournalSubscribe();
	}

	@When("user clicks on footer Subscribe link")
	public void user_click_on_footer_subscribe_link() throws Throwable {

		homepage.clickFootersubscribe();
	}

	@Then("store page should open")
	public void verify_store_page() throws Throwable {

		LOGGER.info("store page should open");
		Assert.assertTrue(driver.getTitle().equalsIgnoreCase(storeTitle));
		
	}

	@When("^user clicks on image of product (.*)$")
	public void user_clicksTo_image(String product) throws InterruptedException {

		LOGGER.info("user clicks on image of product");
		if (product.contains("monthly")) {
			comStore.ClickToMonthlySubscriptionImage();
		} else {
			comStore.ClickToAnnualSubscriptionImage();
		}
	}

	@Then("^product (.*) detail page should open and check the price with productPriceMonthly (.*) productPriceAnnual (.*)$")
	public void verify_product_page(String product, String productPriceMonthly, String productPriceAnnual)
			throws Throwable {

		LOGGER.info("product page should open");
		comProductDetails.verify_product_details_page(product, productPriceMonthly, productPriceAnnual);
	}

	@When("user clicks on Add To Cart button on DETAILS page")
	public void user_click_on_AddToCart_button() throws Throwable {

		LOGGER.info("user clicks on ADD TO CART button on DETAILS page");
		comProductDetails.Click_Add_To_Cart_button_onDetailsPage();
	}
	
	

	@Then("^left widget (.*) shows Monthly Subscription duration (.*),Monthly Price (.*), Monthly  Subscription button (.*), Learn more link (.*)$")
	public void verify_monthly_widget(String left_widget_Str, String monduration, String monprice, String monbuttontext,
			String monLearnmore) throws Throwable {

		Map<String, String> Monthly_Widget_Actual = comStore.Monthly_Subscription_Verification();
		Assert.assertEquals(left_widget_Str, Monthly_Widget_Actual.get("Monthly_linktext"));
		Assert.assertEquals(monduration, Monthly_Widget_Actual.get("Monthly_duration"));
		Assert.assertEquals(monprice, Monthly_Widget_Actual.get("Monthly_price"));
		Assert.assertEquals(monbuttontext, Monthly_Widget_Actual.get("Monthly_Buttontext"));
		Assert.assertEquals(monLearnmore, Monthly_Widget_Actual.get("Monthly_LearnMore"));
		LOGGER.info("Verified Monthly Widget on store page");
	}
	
	@Then("^left widget (.*) shows Monthly Price (.*)$")
	public void verify_monthly_price(String left_widget_Str,String monprice) throws Throwable {

		Map<String, String> Monthly_Widget_Actual = comStore.Monthly_Subscription_price_Verification_pricing_page();
		Assert.assertEquals(monprice, Monthly_Widget_Actual.get("Monthly_price"));		
		LOGGER.info("Verified Monthly subscription price on store page");
	}
	
	@Then("^right widget (.*) shows Annual Price (.*)$")
	public void verify_annual_price(String right_widget_Str,String annualprice)  throws Throwable {			

		Map<String, String> Annual_Widget_Actual = comStore.Annual_Subscription_price_Verification_pricing_page();
		Assert.assertEquals(annualprice, Annual_Widget_Actual.get("Annual_price"));	
		LOGGER.info("Verified Annual subscription price on store page");
	}
		
	@Then("^cart page should open and display  cartPrice (.*),amount (.*), orderTotal (.*)$")
	public void cartPageOpenAndVerifiyPrice(String cartprice,String amount, String ordertotal) throws Throwable {

		LOGGER.info("Then I verify price on cart page");
		
		Assert.assertEquals(cartprice, comCart.VerifyCartPrice());
		Assert.assertEquals(amount, comCart.VerifySubtotal());
		Assert.assertEquals(ordertotal, comCart.VerifyOrderTotal());
		
	}
	@Then("^right widget (.*) shows Annual Subscription duration (.*),Annual  Price (.*), Annual  Subscription button (.*), Learn more link (.*) and BEST VALUE FREE GIFT Banner (.*)$")
	public void verify_annual_widget(String right_widget_Str, String annualduration, String annualprice,
			String annualbuttontext, String AnnualLearnmore, String freegift) throws Throwable {

		Map<String, String> Annual_Widget_Actual = comStore.Annual_Subscription_Verification();
		Assert.assertEquals(right_widget_Str, Annual_Widget_Actual.get("Annual_linktext"));
		Assert.assertEquals(annualduration, Annual_Widget_Actual.get("Annual_duration"));
		Assert.assertEquals(annualprice, Annual_Widget_Actual.get("Annual_price"));
		Assert.assertEquals(annualbuttontext, Annual_Widget_Actual.get("Annual_Buttontext"));
		Assert.assertEquals(AnnualLearnmore, Annual_Widget_Actual.get("Annual_LearnMore"));
		Assert.assertEquals(freegift, Annual_Widget_Actual.get("Annual_FreeGift"));
		LOGGER.info("Verified Annual Widget on store page");
	}

	@When("user clicks on Subscribe Now button of Monthly subscription")
	public void user_clicks_Monthly_subscribeNow() throws Throwable {

		LOGGER.info("user clicks on Subscribe Now button of Monthly subscription");
		comStore.Click_Monthly_subscribenow_button();
	}

	@When("user clicks on Subscribe Now button of Annual subscription")
	public void user_clicks_Annual_subscribeNow() throws Throwable {

		comStore.Click_Annual_subscribenow_button();
	}

	@Then("cart page should open")
	public void cart_page_open() throws Throwable {

		LOGGER.info("cart page should open");
		Assert.assertTrue(comCart.Verify_Cart_Page());
	}

	@And("^verifies readMoreButton, productPrice (.*) , productImage, subscription_name (.*)$")
	public void verifies_readMoreButton_productPrice_productImage_subscriptionName(String productPrice,
			String subscriptionname) throws Throwable {

		LOGGER.info("verifies readMoreButton, productPrice , productImage, subscription_name ");
		comProductDetails.verify_readMoreButton_productPrice_productImage_subscriptionName(productPrice,
				subscriptionname);
	}

	@Then("^cart page should open and display product image, subscription_name (.*), term_Duration (.*), "
			+ "cartPrice (.*), amount (.*), orderTotal (.*), Proceed to checkout button, "
			+ "privacy_security_message (.*) , satisfactionGuaranteedMessage (.*)$")
	public void cartPageOpenAndVerifiesThings(String subscription_name, String termduration, String cartprice,
			String amount, String ordertotal, String privacySecMesg, String satisfactionGuarMesg) throws Throwable {

		LOGGER.info("Then I verify pImg, subN, term, cPrice, amount, oTtl, PtCbutton, PsMesg, satGuaMesg");
		Assert.assertTrue(comCart.VerifyProductName().contains(subscription_name));
		Assert.assertTrue(comCart.VerifyTermDuration().contains(termduration));
		Assert.assertEquals(cartprice, comCart.VerifyCartPrice());
		Assert.assertEquals(amount, comCart.VerifySubtotal());
		Assert.assertEquals(ordertotal, comCart.VerifyOrderTotal());
		Assert.assertTrue(comCart.yourInfoSecureMesg().contains(privacySecMesg));
		Assert.assertTrue(comCart.yourSafeMesg().equals(satisfactionGuarMesg));
	}

	@When("I click on the Go back link")
	public void clickToGoBackLink() throws Throwable {

		LOGGER.info("click on the Go back link");
		comCart.Click_GoBack_button();
	}

	@When("I click to Cart icon on store page")
	public void clickToCartIcon() throws InterruptedException {

		LOGGER.info("click to Cart icon on store page");
		comStore.ClickToCartIcon();
	}

	@Then("I should see item in cart as 1")
	public void itemInCartIs1() throws InterruptedException {

		LOGGER.info("I should see item in cart as 1");
		comStore.AssertItemNumber();
	}

	@Then("I see no items in shopping cart")
	public void noItemsInCart() throws InterruptedException {

		LOGGER.info("no items in shopping cart");
		noItemMessage = comProductDetails.noItemMessage();
		Assert.assertTrue(noItemMessage.contains("no item"));
	}

	@When("user click on Trash Icon")
	public void clickToTrashIcon() throws InterruptedException {

		LOGGER.info("user click on Trash Icon");
		comProductDetails.ClickToTrashIcon();
	}

	@When("click on Ok button")
	public void clickTo_OK_button() throws InterruptedException {

		LOGGER.info("click on Ok button");
		comProductDetails.ClickToOKdelete();
	}

	@When("user clicks on Proceed to checkout button")
	public void user_clicks_proceed_to_checkout() throws Throwable {

		comCart.Click_ProceedCheckout_button();
	}

	@Then("checkout page should open")
	public void checkout_page_open() throws Throwable {

		boolean CheckoutPageDisplay = comCheckout.Verify_Checkout_Page();
		Assert.assertTrue(CheckoutPageDisplay);

	}

	@When("^user enters email address, First name, Last name, suffix (.*), Primary Specialty (.*) , Role (.*), Name of organization (.*), country (.*), street address (.*), city (.*), state province (.*), Zip Postal code (.*), desired password in create password , confirm password$")
	public void user_enter_Personal_details(String suffix, String specialty, String role, String organization,
			String country, String address, String city, String state, String Zip) throws Throwable {
				
		kmap.put("firstname", tUtil.AppendTimestamp("fname"));
		kmap.put("lastname", tUtil.AppendTimestamp("lname"));	
		comCheckout.Enter_Personal_Information(kmap.get("email"),kmap.get("firstname"),kmap.get("lastname"), suffix, specialty, role, organization, country,
				address, city, state, Zip);	
		kmap.put("state", state);
		kmap.put("country", country);
		

	}

	@When("user selects Credit Card with Renewal Option")
	public void user_CC_With_Renewal_Option() throws Throwable {

		comCheckout.Select_CCwithAutoRenewal_Option();
	}

	@When("user selects Send Invoice Option")
	public void user_Send_Invoice_Option() throws Throwable {

		comCheckout.Select_Send_Invoice_Option();
	}

	@When("^user enters credit card number (.*), Expiration Month (.*), Expiration Year(.*), card verification number (.*)$")
	public void user_enter_creditcard_details(String CCnumber, String CCMonth, String CCExpyear, String CCcvv)
			throws Throwable {

		comCheckout.Enter_CC_Details(kmap.get("state"), CCnumber, CCMonth, CCExpyear, CCcvv);
		LOGGER.info("Entered credit card information");
	}

	@When("clicks on Place order button")
	public void clicks_place_order() throws Throwable {

		comCheckout.Click_PlaceOrder_button();
	}

	@Then("^user should see order confirmation message (.*)$")
	public void user_navigatesto_confirmation_page(String order_confirmation_message) throws Throwable {
		String OrderConfirmation = comOrder.Order_Confirmation_message();
		Assert.assertEquals(order_confirmation_message, OrderConfirmation);
		comFun.DriverClose();
		set_store_kmap_values();
	}
	
	@Then("^order confirmation page should display confirmation message (.*), Order details price (.*), Order Amount subtotal (.*), Order amount tax (.*) and Order total amount paid (.*)$")
	public void verify_confirmation_page_messages(String order_confirmation_message, String order_details_price, String order_Amount_subtotal,String order_amount_tax, String order_total_amount_paid ) throws Throwable {
		
		PriceVerify.verify_pice_confirmation_page(order_confirmation_message,order_details_price,order_Amount_subtotal,order_amount_tax,order_total_amount_paid);
		comFun.DriverClose();
		set_store_kmap_values();
		
	}
	
	public void set_store_kmap_values() {
		
		end_pt = AccExpGETSteps.setEndpointCustomers();
		expResp = AccExpGETSteps.getUserByEmail(end_pt, kmap.get("email")).extract().response();				
		tUtil.verifyStatus(expResp, ResponseCode.OK);		
		kmap.put("ucid", expResp.jsonPath().getString("ucId"));
//		StoreExpSteps.kmap.put("fname", kmap.get("firstname"));		
//		StoreExpSteps.kmap.put("lname", kmap.get("lastname"));	
//		StoreExpSteps.ucid = kmap.get("ucid");
//		StoreExpSteps.emailUser = kmap.get("email");
//		StoreExpSteps.kmap.put("email", kmap.get("email"));	
	}

	@Then("^errorMessageAlreadyHaveSubscription (.*) should be displayed on top of this page$")
	public void errorMessage(String errorMessageAlreadyHaveSubscription) throws InterruptedException {

		LOGGER.info("verifying error message contains already ");
		comProductDetails.VerifyErrorMessage();
	}
	
	@Then("user sees NEJM Catalsyt Logo on left side of the page header")
	public void user_sees_NEJM_Catalsyt_Logo_on_left_side_of_the_page_header() {

		LOGGER.info("user sees NEJM Catalsyt Logo on left side of the page header");
		Assert.assertTrue(comStore.isThere_nejmCatalystLogo());
	}

	@Then("User sees signIn link and black shadow and mini cart icon on right side of the page")
	public void user_sees_signIn_link_and_black_shadow_and_mini_cart_icon_on_right_side_of_the_page() {

		LOGGER.info("User sees signIn link and black shadow and mini cart icon on right side of the page");
		Assert.assertTrue(comStore.signInButtonClickable());
		Assert.assertTrue(comStore.UserblackShadow());
		Assert.assertTrue(comStore.is_miniCartIconDisplayed());
	}

	@Then("^user sees the banner_add (.*) and transformingMesg (.*) and image along with it$")
	public void user_sees_the_banner_add_banner_add_and_image_along_with_it(String banner_add,
			String transformingMesg) {

		LOGGER.info("user sees the banner_add <banner_add> and image along with it");
		Assert.assertTrue(comStore.freeGiftWithAnnualSubscriptionMesg().equals(banner_add));
		Assert.assertTrue(comStore.transformingHealthCareDeliveryMesg().equals(transformingMesg));
		Assert.assertTrue(comStore.freeGiftImageDisplayed());
	}
	
	@When("^User enters email and click Continue button$")
	public void userEntersEmail() {
		LEAD.enter_email_click_button(kmap.get("email"));
	}
	
	@Then("^initialize list of GDPR countries$")
	public void initialize_list_of_gdpr_countries() throws IOException {
		String jsonCountries = jsonUtils.getFromJSON("GDPR_Countries_List.json", "countries");
		LOGGER.info("Getting countries from JSON: " + jsonCountries);
		jsonCountries = jsonCountries.replace("[", "");
		jsonCountries = jsonCountries.replace("]", "");
		String[] arrayCountries = jsonCountries.split(",");
		GDPRCountries = Arrays.asList(arrayCountries);
		
		LOGGER.info("List of GDPR Countries is of length: " + Integer.toString(GDPRCountries.size()));
		LOGGER.info("GDPR Countries: " + GDPRCountries.toString());
		
	}

	@Then("^email me and stay informed checkboxes should be checked or unchecked based on GDPR status of billing country (.*)$")
	public void email_me_and_stay_informed_checkboxes_should_be_checked_or_unchecked_based_on_GDPR_status_of_billing_country(String BillingCountry) {
	    if (GDPRCountries.contains(BillingCountry)) {
	    	LOGGER.info(BillingCountry + " is a GDPR country.");
	    	Assert.assertFalse("Both checkboxes should be unchecked.", comCheckout.are_EmailInfoCheckboxesChecked());
	    	LOGGER.info("Checkboxes are unchecked as expected.");
	    } else {
	    	LOGGER.info(BillingCountry + " is not a GDPR country.");
	    	Assert.assertTrue("Both checkboxes should be checked.", comCheckout.are_EmailInfoCheckboxesChecked());
	    	LOGGER.info("Checkboxes are checked as expected.");
	    }
	}
}