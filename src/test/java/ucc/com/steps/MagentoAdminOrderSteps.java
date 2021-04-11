package ucc.com.steps;


import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
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
import ucc.com.pages.ui.MagentoAdminHomePage;
import ucc.com.pages.ui.MagentoAdminNewOrderPage;
import ucc.com.pages.ui.MagentoAdminOrdersPage;
import ucc.com.pages.ui.MagentoAdminSigninPage;
import ucc.com.pages.ui.MicrosoftSignInEmail;
import ucc.com.pages.ui.MicrosoftSignInPassword;
import ucc.i.method.accountexp.AccountExpGET;
import ucc.i.method.acs.ACSGET;
import ucc.i.method.aic.AICGET;
import ucc.i.method.literatum.LiteratumGET;

import ucc.pages.ui.CommonFunc;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;

public class MagentoAdminOrderSteps {

	@Steps
	MagentoAdminSigninPage magentoAdmin;

	@Steps
	MicrosoftSignInEmail microsoftEmail;

	@Steps
	MicrosoftSignInPassword microsoftPwd;

	@Steps
	MagentoAdminHomePage magentoHomePage;

	@Steps
	MagentoAdminOrdersPage magentoOrderPage;

	@Steps
	MagentoAdminNewOrderPage magentoNewOrder;

	@Steps
	AccountExpGET AccExpGETSteps;

	@Steps
	LiteratumGET LiteratumGETSteps;

	@Steps
	AICGET AICGETSteps;

	@Steps
	ACSGET ACSGETSteps;
	
	@Steps
	CommonFunc comFun;

	@Managed
	WebDriver driver;
	
	@Steps
    LiteratumGET literatumGETSteps;

	private static final Logger LOGGER = LoggerFactory.getLogger(MagentoAdminOrderSteps.class);
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String eml = EnvironmentSpecificConfiguration.from(env_var).getProperty("autoEmail");
	public static Map<String, String> kmap = new HashMap<String, String>();
	static String TestEmail = null;
	static String Fname = null;
	static String Lname = null;
	TestUtils tUtil = new TestUtils();
	static String end_pt = null;
	public static Response expResp = null;
	static String ucid = null;
	public static Response literRespIdentity = null;
	public static Response literRespLicense = null;
	public static Response aicResp = null;
	public static Response acsResp = null;
	static String baseUrl =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("magento.admin.url");
	private static String endPt = null;

	
	@Given("^user is on Magento Admin site$")
	public void user_is_on_MagentoAdmin_WebSite() throws Throwable {

		comFun.clearCookies();
		comFun.Launch_URL(baseUrl);	
		LOGGER.info("Magento Admin Site launched");		
	}

	@When("^User clicks on Login via Identity Provider$")
	public void user_Logs_in_via_identity_provider() throws Throwable {
		magentoAdmin.click_login_via_identity_provider();
		LOGGER.info("User clicked on Login via Identity Provider");
	}

	@When("^user enters email address on the Microsoft login email page$")
	public void user_enters_emailaddress_on_microsft_email_page() throws Throwable {
		microsoftEmail.enter_email_id();
		LOGGER.info("user entered email address on the Microsoft login email page");
	}

	@When("^user enters password on the Microsoft login password page$")
	public void user_enters_password_on_microsft_password_page() throws Throwable {

		microsoftPwd.enter_password();
		LOGGER.info("user enters password on the Microsoft login password page");
	}

	@Then("^Magento site opens$")
	public void Magento_site_opens() throws Throwable {
		
		LOGGER.info("Magento site opened");
	}

	@When("^user clicks on Sales tile on right left side of the page$")
	public void user_clicks_sales_tile() throws Throwable {
		magentoHomePage.click_sales_option();
		LOGGER.info("user clicked on Sales tile on right left side of the page");
	}

	@When("^user clicks on Orders from the  options available$")
	public void user_clicks_Orders_option() throws Throwable {
		magentoHomePage.click_orders_option();
		LOGGER.info("user clicked on Orders from the  options available");
	}

	@When("^user clicks on Create New Orders button$")
	public void user_click_create_new_order() throws Throwable {
		magentoOrderPage.click_create_new_order_button();
		LOGGER.info("user clicked on Create New Orders button");
	}

	@When("^user clicks on Create New Customer button$")
	public void user_clicks_new_customer_button() throws Throwable {
		magentoNewOrder.click_create_new_customer_button();
		LOGGER.info("user clicked on Create New Customer button");
	}

	@When("^click on Add Products button$")
	public void user_click_add_products() throws Throwable {
		magentoNewOrder.click_Add_Products_button();
		LOGGER.info("clicked on Add Products button");
	}

	@When("^click on the checkbox in front of the Free Gift item$")
	public void user_click_checkbox_free_gift_item() throws Throwable {
		magentoNewOrder.click_Free_gift_checkbox();
		LOGGER.info("clicked on the checkbox in front of the Free Gift item");
	}

	@When("^click on the checkbox in front of the Monthly subscription item$")
	public void user_click_checkbox_monthly_subscription() throws Throwable {
		magentoNewOrder.click_monthly_subscription();
		LOGGER.info("clicked on the checkbox in front of the Monthly Subscription");
	}
	
	@When("^click on the checkbox in front of the paid Monthly subscription item$")
	public void user_click_checkbox_paid_monthly_subscription() throws Throwable {
		magentoNewOrder.click_paid_monthly_subscription();
		LOGGER.info("clicked on the checkbox in front of the paid Monthly Subscription");
	}
	
	@When("^click on the checkbox in front of the paid Annual subscription item$")
	public void user_click_checkbox_paid_annual_subscription() throws Throwable {
		magentoNewOrder.click_paid_annual_subscription();
		LOGGER.info("clicked on the checkbox in front of the paid Monthly Subscription");
	}

	@When("^click on the checkbox in front of the Annual Subscription item$")
	public void user_click_checkbox_annual_subscription() throws Throwable {
		magentoNewOrder.click_annual_subscription();
		LOGGER.info("clicked on the checkbox in front of the Annual Subscription");
	}
	
	@When("^clicks on radio button for term$")
	public void user_click_radio_button_for_subscription_term() throws Throwable {
		magentoNewOrder.click_term_of_selected_product();
		LOGGER.info("clicked on the radio buttton for term of subscription");
	}
	
	@When("^clicks on radio button for term for comp monthly order$")
	public void user_click_radio_button_for_subscription_term_comp_monthly_order() throws Throwable {
		magentoNewOrder.click_term_of_selected_product_Comp_Monthly();
		LOGGER.info("clicked on the radio buttton for term of subscription for comp monthly");
	}
	
	@When("^clicks on radio button for term for comp annual order$")
	public void user_click_radio_button_for_subscription_term_comp_annual_order() throws Throwable {
		magentoNewOrder.click_term_of_selected_product_Comp_Annual();
		LOGGER.info("clicked on the radio buttton for term of subscription for comp monthly");
	}
	
	@When("^clicks on radio button for 1 year term$")
	public void user_click_radio_button_for_subscription_1Year_term() throws Throwable {
		magentoNewOrder.click_term_of_selected_product_1Year();
		LOGGER.info("clicked on the radio buttton for term of subscription");
	}
	
	@When("^clicks on OK button for custom options$")
	public void user_click_ok_button_for_custom_options() throws Throwable {
		magentoNewOrder.click_ok_button_of_custom_options();		
		LOGGER.info("clicked on the OK button of customs options");
	}
    
	@When("^clicks on OK button for custom options of paid subscription$")
	public void user_click_ok_button_for_custom_options_paid_subscription() throws Throwable {
		magentoNewOrder.click_ok_button_of_custom_options_paid_subscription();		
		LOGGER.info("clicked on the OK button of customs options");
	}
	@When("^click on Add Product to Order button$")
	public void user_clicks_add_product_to_order() throws Throwable {
		magentoNewOrder.click_add_selected_product_to_order();
		LOGGER.info("clicked on Add Product to Order button");
	}

	@When("^Magento user enters email address, First name, Last name, suffix (.*), Primary Specialty (.*) , Role (.*), Name of organization (.*), country (.*), street address (.*), city (.*), state province (.*), Zip Postal code (.*)$")
	public void user_enter_Personal_details(String suffix, String specialty, String role, String organization,
			String country, String address, String city, String state, String Zip) throws Throwable {
		LOGGER.info("Value from excel are " + suffix + specialty + role + organization);
		
			
		kmap.put("email", tUtil.AppendTimestamp(eml));
		kmap.put("firstname", tUtil.AppendTimestamp("fname"));
		kmap.put("lastname", tUtil.AppendTimestamp("lname"));			
		magentoNewOrder.Enter_Magento_order_details(kmap.get("email"),kmap.get("firstname"),kmap.get("lastname"), suffix, specialty, role, organization,
				country, address, city, state, Zip);
		kmap.put("state", state);
		kmap.put("country", country);	

		LOGGER.info("Entered order details");

	}
	
	@And("^Magento user clicks on Get available payment method$")
	public void user_clicks_payment_method() throws Throwable {
		magentoNewOrder.click_payment_method();
		LOGGER.info("clicked payment method");
		
	}
	
	@And("^Magento user selects Send Invoice in Payment and Shipping Information$")
	public void user_clicks_Send_Invoice_radiobutton() throws Throwable {
		magentoNewOrder.click_send_invoice_in_payment_shipping_information();
		LOGGER.info("clicked send invoice button");
		
	}

	@And("^user clicks on Submit order button$")
	public void user_clicks_submit_order_button() throws Throwable {
		magentoNewOrder.click_submit_order_button();
		LOGGER.info("clicked submit Order button");
		
	}

	@Then("^user sees  order created (.*) message$")
	public void user_sees_order_created_message(String OrdercreatedMessage) throws Throwable {

		Assert.assertEquals(OrdercreatedMessage, magentoNewOrder.verify_order_created_message());

	}

	@And("^Order total due is 0 (.*)$")
	public void order_total_due_check(String totaldue) throws Throwable {

		Assert.assertEquals(totaldue, magentoNewOrder.verify_order_total_due());
		comFun.DriverClose();	
		end_pt = AccExpGETSteps.setEndpointCustomers();
		expResp = AccExpGETSteps.getUserByEmail(end_pt, kmap.get("email")).extract().response();				
		tUtil.verifyStatus(expResp, ResponseCode.OK);		
		kmap.put("ucid", expResp.jsonPath().getString("ucId"));
//		StoreExpSteps.kmap.put("fname", kmap.get("firstname"));		
//		StoreExpSteps.kmap.put("lname", kmap.get("lastname"));	
//		StoreExpSteps.ucid = kmap.get("ucid");
//		StoreExpSteps.emailUser = kmap.get("email");
//		StoreExpSteps.kmap.put("email", kmap.get("email"));	
		 endPt = literatumGETSteps.setEndpointIdentity(kmap.get("ucid"));
	        literRespIdentity = literatumGETSteps.get(endPt)
	                .extract().response();	       
	}
	
	@When("^user passes values from UI for Magento user$")
	public void userpassesUIvalue() throws InterruptedException {
	    driver.close();
	    end_pt = AccExpGETSteps.setEndpointCustomers();
		expResp = AccExpGETSteps.getUserByEmail(end_pt, kmap.get("email")).extract().response();
		tUtil.verifyStatus(expResp, ResponseCode.OK);
		tUtil.putToSession("ucid", expResp.jsonPath().getString("ucId"));
		tUtil.putToSession("expResp", expResp);
		tUtil.putToSession("email", kmap.get("email"));
		tUtil.putToSession("fname", kmap.get("firstname"));
		tUtil.putToSession("lname", kmap.get("lastname"));
	}
	

}
