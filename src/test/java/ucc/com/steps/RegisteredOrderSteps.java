package ucc.com.steps;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.cucumber.java.en.Then;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.com.pages.ui.ComCart;
import ucc.com.pages.ui.ComCheckout;
import ucc.com.pages.ui.ComOrderConfirmation;
import ucc.com.pages.ui.ComRenew;
import ucc.com.pages.ui.ComStore;
import ucc.cr.pages.catalyst.ui.CreateAccount;
import ucc.cr.pages.catalyst.ui.MyAccountPage;
import ucc.cr.pages.catalyst.ui.SignInPage;
import ucc.i.method.accountexp.AccountExpGET;
import ucc.i.method.acs.ACSGET;
import ucc.i.method.aic.AICGET;
import ucc.i.method.icv.ICVGET;
import ucc.i.method.literatum.LiteratumGET;

import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;
import io.cucumber.java.en.And;

public class RegisteredOrderSteps {
	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String eml = EnvironmentSpecificConfiguration.from(env_var).getProperty("autoEmail");
	public static Response expResp = null;
	static String TestEmail = null;	
	static String end_pt = null;
	static String ucid = null;
	public static Response aicResp = null;
	public static Response icvResp = null;
	public static Response acsResp = null;
	public static Response literRespIdentity = null;
	public static Response literRespLicense = null;
	public static Map<String, String> kmap = new HashMap<String, String>();
	public static Map<String, String> Confirmationmap = new HashMap<String, String>();
	static String BillingState = null;
	static String BillingInfo = null;
	TestUtils tUtil = new TestUtils();
	static String pwd =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("password");	
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredOrderSteps.class);	
	
	
	@Steps
	CommonFunc comFun;
	
	@Steps
	HomePage homepage;
		
	@Steps
	CreateAccount CreateAcct;
	
	@Steps
	ComStore comStore;
	
	@Steps
	ComCart comCart;
	
	@Steps
	ComCheckout comCheckout;
	
	@Steps
	ComOrderConfirmation comOrder;
	
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
	MyAccountPage myaccountpage;
	
	@Steps
	SignInPage Signinpage;
	
	@Steps
	ComRenew comRenew;
	
	@Steps
	ConfirmationPriceVerifyHelper PriceVerify;
	
	
	@When("^user enters valid emailid and password on the signin modal$")
	public void Registered_existinguser_valid_id_and_password_() throws Throwable {
		
		Signinpage.inputEmail(kmap.get("email"));
		Signinpage.inputPassword(pwd);
		LOGGER.info("Entering user credentials");
	}
	
	@Given("^user is on catalyst Home page$")
	public void user_is_on_Catalyst_WebSite() throws Throwable {

		homepage.launchHomePage();
	}
	
	@When("^user clicks on Create Account link$")
	public void user_clicks_CreateAccount_link() throws Throwable {
		
		CreateAcct.click_create_account();
		LOGGER.info("Clicked Create Account");
	}
    
	@Then("^Create Account Widget should open$")
	public void Create_Account_Page_Verification() throws Throwable {
        		 
		 String ActualHeader = CreateAcct.Verify_Create_Account_Page();		 
		 Assert.assertEquals("Create Account", ActualHeader);
	}
	
	@When("^user enters email address, desired password in create password, First name, Last name, suffix (.*), Primary Specialty (.*) , Role (.*), Place of Work (.*), Name of organization (.*), country (.*) And user clicks on Register or create account button$")
	public void user_fill_account_details(String suffix, String specialty, String role, String PlaceofWork, String organization,
			String country) throws Throwable {
		
		kmap.put("email", tUtil.AppendTimestamp(eml));
		kmap.put("firstname", tUtil.AppendTimestamp("fname"));
		kmap.put("lastname", tUtil.AppendTimestamp("lname"));			
		CreateAcct.Enter_User_all_Details(kmap.get("email"),kmap.get("firstname"),kmap.get("lastname"),suffix, specialty, role, PlaceofWork, organization, country);
		kmap.put("state", BillingState);
		kmap.put("country", country);
		
		
	}
	
	@When("^user clicks on Register button$")
	public void click_Register_button() throws Throwable {
		
		CreateAcct.Click_Register_Button();
		
	}
	
	@Then("^Insight council form should open with success message (.*)$")
	public void Insight_Council_form_Verification(String registration_success_message) throws Throwable {
		String ActualInsight = CreateAcct.getSuccessText();
		Assert.assertEquals(registration_success_message, ActualInsight);
	}
	
	@When("^user clicks on close button on Insight council form$")
	public void Close_Insight_Council_Page() throws Throwable {
        
		CreateAcct.Close_Insight_Council_form();
		
	}
	
	@When("^user select Insightorganization (.*), Health system (.*), net patient revenue (.*), estimated dollar amount (.*), Year of Birth (.*), InsightState (.*)$")
	public void enter_Insight_Council_info(String TypeofOrg, String HealthAffirm, String NetRev, 
   		 String EstDolr, String Yob, String Sob) throws Throwable {
        
		CreateAcct.fillDataToJoin(TypeofOrg,HealthAffirm,NetRev,EstDolr,Yob,Sob); 		
				
	}
	
	@When("^click on Join button$")
	public void click_Insight_join_button() throws Throwable {
        
		CreateAcct.clickBtn();  		 
		
	}
	
	@When("^Click on close button of Thank you Message box$")
    public void click_ThankYou_msg_close_button() throws Throwable {
        
		CreateAcct.ICThankyouMsgClose();  		 
		
	}
	
	
	@Then("^user name should be displayed near user icon$")
	public void Verify_SignedIn_User_name() throws Throwable {
        
		String ActualUserName = homepage.VerifyUserName();
		String ExpectedUserName = kmap.get("firstname") + " " + kmap.get("lastname");		
		Assert.assertEquals(ExpectedUserName, ActualUserName);
		
	}
	
	@When("^user clicks on Subscribe button on catalyst homepage$")
	public void user_click_on_subscribe_button() throws Throwable {
		
		homepage.clickLoginsubscribe();

	}
	
	@Then("^store page should get opened$")
	public void verify_store_page() throws Throwable {

		boolean DisplayStorePage = comStore.Verify_store_page();
		Assert.assertTrue(DisplayStorePage);

	}

	@When("^user clicks on Subscribe Now button of Monthly subscription product$")
	public void user_clicks_Monthly_subscribeNow() throws Throwable {
		
		comStore.Click_Monthly_subscribenow_button();
	}
	
	@When("^user clicks on Subscribe Now button of Annual subscription product$")
	public void user_clicks_Annual_subscribeNow() throws Throwable {
		
		comStore.Click_Annual_subscribenow_button();
	}
	
	@Then("^cart page should get opened$")
	public void cart_page_open() throws Throwable {

		boolean DisplaycartPage = comCart.Verify_Cart_Page();
		Assert.assertTrue(DisplaycartPage);
	}
	
	@When("^user clicks on Proceed to checkout button on cart page$")
	public void user_clicks_proceed_to_checkout() throws Throwable {

		comCart.Click_ProceedCheckout_button();
	}

	@Then("^checkout page should get opened$")
	public void checkout_page_open() throws Throwable {

		boolean CheckoutPageDisplay = comCheckout.Verify_Checkout_Page_Billing_address();
		Assert.assertTrue(CheckoutPageDisplay);

	}
	
	@Then("^Order Total should be displayed equal to expected Order Total (.*)$")
	public void Order_Total_Verification(String Expected_Order_Total) throws Throwable {

		String Actual_Order_Total = comCheckout.Verify_Order_Total();
		Assert.assertEquals(Expected_Order_Total, Actual_Order_Total);
		kmap.put("Order Total", Expected_Order_Total);
	}
	
	@Then("^Order Total displayed should be equal to expected Order Total amount after VAT (.*)$")
	public void Order_Total_Verification_after_VAT(String Expected_Order_Total_after_VAT_Num) throws Throwable {

		String Actual_Order_Total = comCheckout.Verify_Order_Total();
		Assert.assertEquals(Expected_Order_Total_after_VAT_Num, Actual_Order_Total);
	}
	
	
	@When("^user enters Billing country (.*), street address (.*), city (.*), state province (.*), Zip Postal code (.*)$")
	public void user_enters_billing_address(String billingcountry, String address,String billcity, String State, String Zip) throws Throwable {		
		
		comCheckout.Enter_Billing_Address(billingcountry,address,billcity,State,Zip);
		BillingState = State;
		BillingInfo = "BILLING INFORMATION" + "\n" + kmap.get("firstname") + " " + kmap.get("lastname") + "\n" + address + "\n"+ billcity + ", "	+ State + ", " + Zip; 
				     
		
	}
	
	@When("^Registered user enters valid combination of emailid and password$")
	public void Registered_User_enter_valid_email_password() throws Throwable {
        
		comRenew.Enter_Email_ID(kmap.get("email"));
		comRenew.Enter_Password(pwd);		
		LOGGER.info("Entering user credentials");
	}
	
	@When("^existing user enters his/her valid emailid$")
	public void Existing_User_email() throws Throwable {
		
		//comFun.getLocalStorageObjectValue();
		comCheckout.Enter_existing_email(kmap.get("email"));
		LOGGER.info("Entering existing email");
	}
	
	@When("^existing user enters his/her valid password$")
	public void Existing_User_password() throws Throwable {
		
		//comFun.getLocalStorageObjectValue();
		Signinpage.inputPassword(pwd);		
		LOGGER.info("Entering existing password");
	}
	@When("^user selects Credit Card with Automatic Renewal Option$")
	public void user_CC_With_Renewal_Option() throws Throwable {

		comCheckout.Select_CCwithAutoRenewal_Option();

	}
	
	@When("^user selects Send Invoice Option on checkout page$")
	public void user_Send_Invoice_Option() throws Throwable {

		comCheckout.Select_Send_Invoice_Option();

	}
	
	
	
	
	@Then("^order confirmation page should open with order confirmation message (.*)$")
	public void user_navigatesto_confirmation_page(String order_confirmation_message) throws Throwable {
		String OrderConfirmation = comOrder.Order_Confirmation_message();
		Assert.assertEquals(order_confirmation_message, OrderConfirmation);
		
		comFun.DriverClose();
		set_store_kmap_values();
		
			
	}
	
	@Then("^order confirmation page should display order confirmation message (.*), Order details name (.*), Subscription Type (.*), Order details price (.*), Order details term (.*), Order details payment method (.*), Order Amount subtotal (.*), Order amount tax (.*),  Order total amount paid (.*) and Billing country name (.*)$")
	public void verify_confirmation_page_messages(String order_confirmation_message,String order_details_name, String Subscription_type,String order_details_price,  
			String order_details_term, String order_details_payment_method, String order_Amount_subtotal,
			String order_amount_tax, String order_total_amount_paid, String billing_country_name) throws Throwable {
		
		String OrderConfirmation = comOrder.Order_Confirmation_message();
		Assert.assertEquals(order_confirmation_message, OrderConfirmation);		
		Confirmationmap = comOrder.Order_Confirmation_Page_Details();
		Assert.assertEquals(order_details_name + " — " + Subscription_type, Confirmationmap.get("orderdetailname"));
		Assert.assertEquals(order_details_price, Confirmationmap.get("orderdetailprice"));
		Assert.assertEquals(order_details_term, Confirmationmap.get("term"));
		Assert.assertEquals(order_details_payment_method, Confirmationmap.get("paymentmethod"));
		Assert.assertEquals(order_Amount_subtotal, Confirmationmap.get("subtotal"));
		Assert.assertEquals(order_amount_tax, Confirmationmap.get("tax"));
		Assert.assertEquals(order_total_amount_paid, Confirmationmap.get("amountpaid"));
		BillingInfo = BillingInfo + "\n" + billing_country_name;
		Assert.assertEquals(BillingInfo, Confirmationmap.get("billinginfo"));
		set_store_kmap_values();
		
		//comFun.DriverClose();
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
	@When("^on checkout page user enters credit card number (.*), Expiration Month (.*), Expiration Year (.*), cvv (.*)$")
	public void user_enter_creditcard_details_checkout(String CCnumber, String CCMonth, String CCExpyear, String CCcvv) throws Throwable {

		comCheckout.Enter_CC_Details(BillingState, CCnumber, CCMonth, CCExpyear, CCcvv);

	}
	
	@When("^user enters VAT number (.*)$")
	public void user_enter_VAT_number(String VAT) throws Throwable  {
		
		comCheckout.Enter_VAT_number(VAT);

	}
	
	
	
	
	
	
	@When("^user clicks on View Receipt link$")
	public void click_view_receipt() throws Throwable {
		
		comOrder.Click_View_Receipt();
		
	}
	
	@Then("^receipt (.*) should open in new tab$")
	public void receipt_open_verification(String url) throws Throwable {
		
		Assert.assertTrue(comOrder.verify_View_Receipt().contains(url));
		
	}
	@When("^user clicks Download free gift$")
	public void click_Download_Free_Gift() throws Throwable {
		
		comOrder.Download_Free_Gift();
		
	}
	
	@When("^user clicks on Manage You Email Profile$")
	public void click_Manage_email_profile() throws Throwable {
		
		comOrder.Click_Manage_Email_Profile();
		
	}
	
	
	
	@Then("^user sees My Account page and close the browser$")
	public void Verify_myaccount_page() throws Throwable {
		myaccountpage.userAtMyAccountPage();
		comFun.DriverClose();
		LOGGER.info("Verifying display of MyAccount page");
	}
	
	@When("^user clicks on Go to latest issue$")
	public void click_go_to_latest_issue() throws Throwable {
		
		comOrder.Click_latest_issue();
		
	}
	
	@Then("^user sees Catalyst latest issue (.*)$")
	public void user_sees_latest_issue(String latestissueurl) throws Throwable {
		
		Assert.assertTrue(comOrder.get_latest_issue().contains(latestissueurl));
		comFun.DriverClose();
		
	}
	
	@Then("^registered user sees his full name on left hand side of man icon on the page$")
	public void verify_user_name() throws Throwable {
		String Username = kmap.get("firstname") + " " + kmap.get("lastname");				
		Assert.assertEquals(Username, comCheckout.Get_signedin_customer_name());
		LOGGER.info("verified user name");
	}
	
	@Then("^order confirmation page for registered user should display order confirmation message (.*), Order details price (.*), Order Amount subtotal (.*), Order amount tax (.*) and Order total amount paid (.*)$")
	public void verify_confirmation_page_messages(String order_confirmation_message, String order_details_price, String order_Amount_subtotal,String order_amount_tax, String order_total_amount_paid ) throws Throwable {
		PriceVerify.verify_pice_confirmation_page(order_confirmation_message,order_details_price,order_Amount_subtotal,order_amount_tax,order_total_amount_paid);
		comFun.DriverClose();				
	}
	
	@And("^user signs out$")
	public void verify_confirmation_page_messages() throws InterruptedException
	{
		homepage.ClickUserSignOut();
		comFun.driverQuit();
	}
	
	@When("registered user passes values from UI to API")
	public void user_passes_UI_values_toAPI() throws InterruptedException {
		end_pt = AccExpGETSteps.setEndpointCustomers();
		expResp = AccExpGETSteps.getUserByEmail(end_pt, kmap.get("email")).extract().response();
		tUtil.verifyStatus(expResp, ResponseCode.OK);
		tUtil.putToSession("ucid", expResp.jsonPath().getString("ucId"));
		tUtil.putToSession("expResp", expResp);
		tUtil.putToSession("email", kmap.get("email"));
		tUtil.putToSession("firstName", kmap.get("firstname"));
		tUtil.putToSession("lastName", kmap.get("lastname"));
	}
	

}
