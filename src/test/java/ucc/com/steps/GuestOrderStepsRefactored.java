package ucc.com.steps;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import io.cucumber.datatable.DataTable;
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
import ucc.utils.YamlUtils;
import ucc.utils.CucumberUtils.CucumberUtils;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;

public class GuestOrderStepsRefactored {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String eml = EnvironmentSpecificConfiguration.from(env_var).getProperty("autoEmail");
	public static Response expResp = null;
	static String TestEmail = null;
	static String pwd = EnvironmentSpecificConfiguration.from(env_var).getProperty("password");
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
	TestUtils tUtil = new TestUtils();	
	static String StorePagePartialURL = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("store.page.partial.url");
	public static Map<String, String> Confirmationmap = new HashMap<String, String>();
	public static Map<String, String> row = new HashMap<String, String>();
	private SoftAssertions softAssert = new SoftAssertions();
	JsonUtils jsonUtils = new JsonUtils();
	YamlUtils YamlUtil = new YamlUtils();
	static DecimalFormat df = new DecimalFormat("#.00");

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

	@Given("^User is on Catalyst Home Page$")
	public void user_on_Catalyst_homepage() throws Throwable {

		homepage.launchHomePage();
		comFun.waitForLoadPage();

	}

	@Given("^user clicks subscribe button from header$")
	public void user_click_header_subscribe() throws Throwable {

		homepage.clicksubscribe();

	}
	
	@Given("^user clicks subscribe button in footer$")
	public void user_click_footer_subscribe() throws Throwable {
		
		homepage.clickFootersubscribe();

	}
	
	@Given("^User clicks subscribe button via Journal section$")
	public void user_click_journal_subscribe() throws Throwable {

		homepage.HoverJournal();
		homepage.ClickJournalSubscribe();

	}

	@When("^user selects Monthly or Annual subscription for anonymous order$")

	public void user_verifies_product_price_clicks_subscribeNow_button(DataTable dataTable) throws Throwable {

		row = CucumberUtils.convert(dataTable);
		comStore.Subscription_Price_Verification(driver.getTitle(), row);
		comStore.Click_subscribenow_button(row);		

	}

	@Then("^valid item should be added in a cart$")
	public void item_verification_on_cart_page(DataTable dataTable) throws Throwable {

		row = CucumberUtils.convert(dataTable);		
		comCart.Verify_Cart_Price(row);

	}

	@When("^user checkout the given subscription$")
	public void user_checkout_Subscriptions(DataTable dataTable) throws Throwable {
		row = CucumberUtils.convert(dataTable);
		comCart.Click_ProceedCheckout_button();
		kmap.put("email", tUtil.AppendTimestamp(eml));
		kmap.put("firstname", tUtil.AppendTimestamp("fname"));
		kmap.put("lastname", tUtil.AppendTimestamp("lname"));
        comCheckout.Enter_checkout_details(kmap,row);
		
	}

	@Then("^the required assertion related to guest checkout passes$")
	public void Order_Total_Verification(DataTable dataTable) throws Throwable {
		row = CucumberUtils.convert(dataTable);
		comCheckout.checkout_page_assertions(row);
		kmap.put("Order Total", row.get("Expected Order Total"));
	}

	@When("^user passes values from UI$")
	public void userpassesUIvalue() throws InterruptedException {
		end_pt = AccExpGETSteps.setEndpointCustomers();
		expResp = AccExpGETSteps.getUserByEmail(end_pt, kmap.get("email")).extract().response();
		tUtil.verifyStatus(expResp, ResponseCode.OK);
		tUtil.putToSession("ucid", expResp.jsonPath().getString("ucId"));
		tUtil.putToSession("expResp", expResp);
		tUtil.putToSession("email", kmap.get("email"));
		tUtil.putToSession("firstName", kmap.get("firstname"));
		tUtil.putToSession("lastName", kmap.get("lastname"));
	}

	@Then("^order confirmation page should display confirmation message with correct prices$")
	public void verify_confirmation_page_messages(DataTable dataTable) throws Throwable {
		row = CucumberUtils.convert(dataTable);
		comOrder.Order_Confirmation_Message_Price_Verification(row);

	}
	
	/**
	 * This Step is providing entry point for store page
	 * @param EntryPoint
	 * @throws Throwable
	 * @author mshinde
	 * @date 24-Mar-2021
	 */
	
	@Given("^user clicks subscribe (.*)$")
	public void user_clicks_entrypoint(String EntryPoint) throws Throwable {

		homepage.ClickonSubscribe(EntryPoint);

	}
	
	/**
	 * This Step is to select country and product on store page
	 * @param Choicecountry and Subscriptiontype
	 * @throws InterruptedException
	 * @author mshinde
	 * @date 24-Mar-2021
	 */
	
	@Then("^users selects country (.*) and Product (.*) on store page$")
	public void Country_selection_ChoicePage(String Choicecountry, String Subscriptiontype) throws InterruptedException, IOException  {
		comStore.CountrySelect(Choicecountry);		
		softAssert.assertThat(comStore.StorePagePriceVerification()).isTrue();		
		comStore.ProdSelect(Subscriptiontype);

	}
	
	/**
	 * This Step is to validate details on cart page
	 * @param none
	 * @throws IOException, InterruptedException
	 * @author mshinde
	 * @date 24-Mar-2021
	 */
	
	@Then("^Guest user should be able to navigate to the cart page and should be able to validate the desired Price and Currency Symbol for Subtotal,Order Total, Price, Free Gift if applicable on the page$")
    public void user_should_be_able_to_navigate_to_the_cart_page_and_should_be_able_to_validate_the_desired_price_and_currency_symbol_for_subtotal_order_total_price_free_gift_if_applicable_and_term_type_on_the_page() throws IOException, InterruptedException {
              
		softAssert.assertThat(comCart.Verify_Cart_Page()).isTrue();
		if (comStore.Product_Type.equalsIgnoreCase("product-monthly")) {
			softAssert.assertThat(comCart.verifyCartPricingData((String)tUtil.getFromSession("ExpectedMonthlyPriceWithCurrency"))).isTrue();
			softAssert.assertThat(comCart.verifySubTtlPricingData((String)tUtil.getFromSession("ExpectedMonthlyPriceWithCurrency"))).isTrue();
			softAssert.assertThat(comCart.verifyOrdTtlPagePricingData((String)tUtil.getFromSession("ExpectedMonthlyPriceWithCurrency"))).isTrue();

		} else {
			softAssert.assertThat(comCart.verifyCartPricingData((String)tUtil.getFromSession("ExpectedYearlyPriceWithCurrency"))).isTrue();
			softAssert.assertThat(comCart.verifySubTtlPricingData((String)tUtil.getFromSession("ExpectedYearlyPriceWithCurrency"))).isTrue();
			softAssert.assertThat(comCart.verifyOrdTtlPagePricingData((String)tUtil.getFromSession("ExpectedYearlyPriceWithCurrency"))).isTrue();
			
		}
		
		softAssert.assertAll();
        softAssert = tUtil.resetSoftAssert(softAssert);
		comCart.Click_ProceedCheckout_button();	
	                 

    }
	
	@When("^guest user checkout the given subscription$")
	public void guest_user_checkout_Subscriptions(DataTable dataTable) throws Throwable {
		row = CucumberUtils.convert(dataTable);	
		
		tUtil.putToSession("email", tUtil.AppendTimestamp(eml));
		tUtil.putToSession("firstName", tUtil.AppendTimestamp("fname"));
		tUtil.putToSession("lastName", tUtil.AppendTimestamp("lname"));
		
		comCheckout.Enter_Personal_Info_Refactored(kmap, row);
		JSONObject Country = new JSONObject(YamlUtil.getValueFromYml("price.yml", "/" + (row.get("country_code"))));
	       String ExpectedMonthlyPrice = jsonUtils.convertToUTF8(String.valueOf(df.format(Country.getDouble("monthly"))));
	       String ExpectedYearlyPrice = jsonUtils.convertToUTF8(String.valueOf(df.format(Country.getDouble("yearly"))));
	       String CurrencySymbol = jsonUtils.getFromJSONInUTF8("Currency/Currency_Code.json", "$['" + (row.get("country_code")) + "'].symbol");
	       String ExpectedMonthlyPriceWithCurrency = CurrencySymbol + ExpectedMonthlyPrice;
		   String ExpectedYearlyPriceWithCurrency = CurrencySymbol + ExpectedYearlyPrice;
	       tUtil.putToSession("ExpectedMonthlyPriceWithCurrency", ExpectedMonthlyPriceWithCurrency);
		   tUtil.putToSession("ExpectedYearlyPriceWithCurrency", ExpectedYearlyPriceWithCurrency);
			
			if (((String)tUtil.getFromSession("Product_Type")).equalsIgnoreCase("product-monthly"))
            {
		        comCheckout.Enter_billing_details_refactored(kmap, row);
	        	softAssert.assertThat(comCheckout.checkout_page_cartprice_assert((String)tUtil.getFromSession("ExpectedMonthlyPriceWithCurrency"))).isTrue();	
		        softAssert.assertThat(comCheckout.checkout_page_tax_assert(Country)).isTrue();		       
               
            } else
            {
            	comCheckout.Enter_billing_details_refactored(kmap, row);
	        	softAssert.assertThat(comCheckout.checkout_page_cartprice_assert((String)tUtil.getFromSession("ExpectedYearlyPriceWithCurrency"))).isTrue();	
		        softAssert.assertThat(comCheckout.checkout_page_tax_assert(Country)).isTrue();		        
            }
			softAssert.assertThat(comCheckout.checkout_page_total_assert(CurrencySymbol,Country)).isTrue();	
	}
	
	@Then("^order confirmation page should display confirmation message with correct prices and messages$")
	public void Order_confirmation_page_verify() throws Throwable {
		
		softAssert.assertThat(comOrder.Order_Confirmation_Page_Verify()).isTrue();
		softAssert.assertAll();
        softAssert = tUtil.resetSoftAssert(softAssert);

	}
	
	@When("^Guest user passes values from UI$")
	public void GuestuserpassesUIvalue() throws InterruptedException {
		end_pt = AccExpGETSteps.setEndpointCustomers();
		expResp = AccExpGETSteps.getUserByEmail(end_pt,(String)tUtil.getFromSession("email")).extract().response();
		tUtil.verifyStatus(expResp, ResponseCode.OK);
		tUtil.putToSession("ucid", expResp.jsonPath().getString("ucId"));
		tUtil.putToSession("expResp", expResp);
	}

}