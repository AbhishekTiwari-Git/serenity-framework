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
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import ucc.com.pages.ui.ComCheckout;
import ucc.com.pages.ui.ComPaybill;
import ucc.com.pages.ui.ComPaymentConfirmation;
import ucc.com.pages.ui.ComRenew;
import ucc.com.pages.ui.ComStore;
import ucc.cr.pages.catalyst.ui.MyAccountPage;
import ucc.cr.pages.catalyst.ui.SignInPage;
import ucc.i.method.accountexp.AccountExpGET;
import ucc.i.method.acs.ACSGET;
import ucc.i.method.aic.AICGET;
import ucc.i.method.icv.ICVGET;
import ucc.i.method.literatum.LiteratumGET;
import ucc.i.steps.experience.AccountExpSteps;

import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;
import static ucc.com.steps.ExistingUserSigninHelper.*;

public class ExistingUserSigninSteps {

	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	private static final Logger LOGGER = LoggerFactory.getLogger(ExistingUserSigninSteps.class);
	private String RegQualtoICRUserjson  = "registration_qualtoIC_RegisteredUser.json";
	public static Map<String, String> kmap = new HashMap<String, String>();
	public static Response aicResp = null;
	public static Response icvResp = null;
	public static Response acsResp = null;
	public static Response literRespIdentity = null;
	public static Response literRespLicense = null;
	public static Map<String, String> Confirmationmap = new HashMap<String, String>();
	static String endPt = null;
	static String ucid = null;

	public static Response expResp = null;
	static String TestEmail = null;
	
	

	@Steps
	CommonFunc comFun;

	@Steps
	ComStore comStore;

	@Steps
	HomePage homepage;

	@Steps
	SignInPage Signinpage;

	@Steps
	ComRenew comRenew;

	@Steps
	MyAccountPage myaccountpage;
	
	@Steps
	ComCheckout comCheckout;
	
	@Steps
	AccountExpSteps AccountExpStep;
	
	@Steps
	ComPaybill comPaybill;	

	@Managed
	WebDriver driver;
	
	@Steps
	LiteratumGET literatumGETSteps;

	@Steps
	ICVGET icvGETSteps;

	@Steps
	AICGET aicGETSteps;

	@Steps
	ACSGET acsGETSteps;

	@Steps
	AccountExpGET AccExpGETSteps;	
	
	@Steps
	ComPaymentConfirmation comPayment;	
	
	
	

	@Given("^user with existing account is on content page$")
	public void user_is_on_Catalyst_WebSite() throws Throwable {

		homepage.launchHomePage();
		LOGGER.info("Home page launched");
	}

	@And("^user can see the sign in link on top right side of the store page$")
	public void user_see_Sign_in_link() throws Throwable {

		Assert.assertTrue(comStore.Verify_signin_link_on_store_page());
		LOGGER.info("Sign in link verified");

	}

	@When("^user clicks on the Sign in link$")
	public void user_clicks_sign_in_button() throws Throwable {

		comStore.Click_signin_link_on_store_page();
		LOGGER.info("Sign in link clicked");
	}

	@Then("^Sign in Page modal opens$")
	public void verify_signin_modal_is_displayed() throws Throwable {

		Assert.assertTrue(Signinpage.SignInPagedisplay());
		LOGGER.info("Verifying sign in modal opened");
	}

	@When("^user enters invalid combination of emailid (.*) and password (.*)$")
	public void Enter_invalid_email_password(String emailid, String password) throws Throwable {
	
		Signinpage.inputEmail(emailid);
		Signinpage.inputPassword(password);
		LOGGER.info("Entering user credentials");
	}

	@When("^user enters valid combination of emailid (.*) and password (.*)$")
	public void Enter_valid_email_password(String emailid, String password) throws Throwable {

		Signinpage.inputEmail(emailid);
		Signinpage.inputPassword(password);
		LOGGER.info("Entering user credentials");
	}
	
	@When("^existing user enters valid password in the password tab$")
	public void Enter_existinguser_valid_password_on_password_tab() throws Throwable {
		
		Signinpage.inputPassword(jsonUtils.getFromJSON(RegQualtoICRUserjson, "password"));
		LOGGER.info("Entering user credentials");
	}
		

	@When("^clicks on Sign In button$")
	public void User_clicks_sign_in_button() throws Throwable {

		Signinpage.clickStoreSignIn();
		LOGGER.info("Clicking sign in button");

	}

	@When("^User clicks sign in link on Catalyst Home page$")
	public void User_clicks_sign_in_button_on_Catalyst_Homepage() throws Throwable {

		homepage.clickSignInLink();
		LOGGER.info("Clicking sign in button on Catalyst home page");

	}

	@When("^User clicks on Renew link on Catalyst Home page$")
	public void User_clicks_Renew_link_button_on_Catalyst_Homepage() throws Throwable {

		homepage.clickRenewLink();
		LOGGER.info("Clicking Renew link on Catalyst home page");

	}

	@Then("^user sees his name (.*) on left hand side of man icon on the page$")
	public void User_sees_name_beside_usericon(String username) throws Throwable {

		Assert.assertEquals(username, comStore.Retrieve_signed_username());
		LOGGER.info("Clicking sign in button");

	}

	@Then("^user sees his name (.*) on left hand side of man icon$")
	public void User_sees_name_beside_usericon_on_Homepage(String Username) throws Throwable {

		Assert.assertEquals(homepage.RetrieveUserName(), Username);
		LOGGER.info("Retrieving user name");

	}
	
	@Then("^paybill user sees his name (.*) on left hand side of man icon$")
	public void paybill_User_sees_name_beside_usericon_on_Homepage(String Username) throws Throwable {

		Assert.assertEquals(comPaybill.Get_signedin_customer_name(), Username);
		String name[] = Username.split(" ", 2);		
//		StoreExpSteps.kmap.put("fname", name[0]);
//		StoreExpSteps.kmap.put("lname", name[1]);		
		LOGGER.info("Retrieving user name on paybill checkout page");

	}

	@Then("^user sees the message (.*)$")
	public void user_sees_error_message(String InvalidloginMessage) throws Throwable {

		Assert.assertEquals(Signinpage.InvalidSigninMessage(), InvalidloginMessage);
		LOGGER.info("Verifying error message for invalid credentials");
	}

	@Then("^user sees Renew page$")
	public void user_sees_Renew_page() throws Throwable {

		Assert.assertEquals(comRenew.Verify_Renew_Page(), "Renew");
		LOGGER.info("Verifying Renew page");
	}

	@Then("^Renew Sign in Page modal opens$")
	public void verify_Renew_signin_modal_is_displayed() throws Throwable {

		Assert.assertEquals(comRenew.Verify_Renew_SignIn_Page(), "Sign In");
		LOGGER.info("Verifying Renew sign is modal opened");
	}

	@When("^On Renew Paybill SignIn modal, user enters valid combination of emailid (.*) and password (.*)$")
	public void RenewPaybill_Page_Enter_valid_email_password(String emailid, String password) throws Throwable {
		
		comRenew.Enter_Email_ID(emailid);
		comRenew.Enter_Password(password);
		kmap.put("email",emailid);
//		StoreExpSteps.emailUser = emailid;
//		StoreExpSteps.kmap.put("email", emailid);		
		LOGGER.info("Entering user credentials on Renew/Paybill page");
	}

	@When("^clicks on Sign In button on Renew Paybill signin modal$")
	public void User_clicks_sign_in_button_on_Renew_Paybill_signin_modal() throws Throwable {

		comRenew.Click_Signin();
		LOGGER.info("Clicking sign in button");

	}

	@When("^User clicks on PayBill link on Catalyst Home page$")
	public void User_clicks_PayBill_link_button_on_Catalyst_Homepage() throws Throwable {

		homepage.clickPayBillLink();
		LOGGER.info("Clicking Renew link on Catalyst home page");

	}

	@Then("^user sees PayBill page$")
	public void user_sees_PayBill_page() throws Throwable {

		Assert.assertEquals(comRenew.Verify_PayBill_Page(), "Pay Your Bill");
		LOGGER.info("Verifying PayBill page");
	}

	@When("^User hovers over his name$")
	public void user_hover_his_name() throws Throwable {

		homepage.HoverUserName();
		LOGGER.info("Hovering on user page");
	}

	@When("^click on My Account Link$")
	public void click_myaccount_link() throws Throwable {

		homepage.ClickMyAccountLink();
		LOGGER.info("clicking MyAccount link");
	}

	@Then("^user sees My Account page$")
	public void Verify_myaccount_page() throws Throwable {
		myaccountpage.userAtMyAccountPage();
		LOGGER.info("Verifying display of MyAccount page");
	}

	@When("^User scrolls down and clicks on Renew link$")
	public void Click_Renew_link() throws Throwable {
		myaccountpage.ClickFooterRenewLink();
		LOGGER.info("Clicking Renew link on footer MyAccount page");
	}

	@When("^User scrolls down and clicks on PayBill link$")
	public void Click_Paybill_link() throws Throwable {
		myaccountpage.ClickFooterPaybillLink();
		LOGGER.info("Clicking Paybill link on footer MyAccount page");
	}

	@When("^User clicks on PayBill link$")
	public void Click_PayBill_link() throws Throwable {
		myaccountpage.ClickPayBillLink();
		LOGGER.info("Clicking Paybill link on MyAccount page");
	}

	@When("^user clicks on username$")
	public void Click_username() throws Throwable {
		comRenew.Click_username();
		LOGGER.info("Clicking Username on Renew page");
	}

	@When("^clicks on Log out link$")
	public void Click_logout_link() throws Throwable {
		comRenew.Click_Logout_link();
		LOGGER.info("Clicking logout link");
	}

	@When("^user enters valid existing emailid$")
	public void Enter_existing_email() throws Throwable {

		// comFun.getLocalStorageObjectValue();
		comCheckout.Enter_existing_email(jsonUtils.getFromJSON(RegQualtoICRUserjson, "email"));
		RegisteredOrderSteps.kmap.put("email", (jsonUtils.getFromJSON(RegQualtoICRUserjson, "email")));
		LOGGER.info("Entering existing email");
	}

	@Then("^existing user message (.*) is displayed$")
	public void existing_user_message(String existusermsg) throws Throwable {
		Assert.assertEquals(existusermsg, comCheckout.verify_existing_user_message());
		LOGGER.info("Existing user message displayed as expected");
	}

	@When("^user clicks on Sign In button$")
	public void user_click_Signin_button() throws Throwable {
		comCheckout.click_signin_button();
		LOGGER.info("clicked signin button");
	}

	@When("^user enters valid password (.*)$")
	public void user_enters_valid_password(String password) throws Throwable {
		comCheckout.Enter_existing_password(password);
		LOGGER.info("entered existing password");
	}

	@When("^clicks on Sign In submit button on signin modal$")
	public void user_click_signin_submit_button_on_signin_modal() throws Throwable {
		comCheckout.click_signin_button_signin_modal();
		LOGGER.info("Clicking signin submit button");
	}

	@Then("^user sees his name on left hand side of man icon on the page$")
	public void verify_user_name() throws Throwable {
		String Username = jsonUtils.getFromJSON(RegQualtoICRUserjson, "firstName") + " "
				+ jsonUtils.getFromJSON(RegQualtoICRUserjson, "lastName");
		Assert.assertEquals(Username, comCheckout.Get_signedin_customer_name());
		LOGGER.info("verified user name");
	}

	@Then("^user sees no amount due message (.*)$")
	public void verify_no_amount_due_message(String no_payment_due_message) throws Throwable {
		Assert.assertEquals(no_payment_due_message, comPaybill.Verify_PayBill_No_Payment_Due_Message());
		
		LOGGER.info("verified No Payment Due message");
	}
	
	@Then("^user sees Pay Your Bill message (.*)$")
	public void PayBillSICheckoutHeader(String pay_your_bill_message) throws Throwable {
		Assert.assertEquals(pay_your_bill_message, comPaybill.Verify_Pay_Your_Bill_Message());
		
		LOGGER.info("pay your bill for send invoice users message");
	}
	
	@Then("^not eligible message (.*), account information message (.*), back to nejm message (.*)$")
	public void verify_not_eligible_message(String not_eligible_message, String account_info_message, String back_to_nejm_message) throws Throwable {
		
		String Paybill_no_eligible_complete_message = not_eligible_message + "\n\n" + account_info_message + "\n\n" + back_to_nejm_message + "\n\n";			
				
		Assert.assertEquals(Paybill_no_eligible_complete_message,comPaybill.Verify_PayBill_Not_Eligible_Message());
		
		LOGGER.info("verified not eligible message" + comPaybill.Verify_PayBill_Not_Eligible_Message());
	}
	
	@Then("^not eligible message (.*), subscribe message (.*), account information message (.*), back to nejm message (.*)$")
	public void verify_not_eligible_message_group_2(String not_eligible_message, String account_info_message, String back_to_nejm_message, String subscribe_message) throws Throwable {
		
		String Paybill_no_eligible_complete_message = not_eligible_message + "\n\n" + account_info_message + "\n\n" + subscribe_message + "\n\n" + back_to_nejm_message + "\n\n";			
				
		Assert.assertEquals(Paybill_no_eligible_complete_message,comPaybill.Verify_PayBill_Not_Eligible_Message2());
		
		LOGGER.info("verified not eligible message"+ comPaybill.Verify_PayBill_Not_Eligible_Message2());
	}
	
	@And("^user sees Billing address section (.*)$")
	public void verify_billing_address_message(String Billing_address_message) throws Throwable {
		Assert.assertEquals(Billing_address_message, comPaybill.Verify_Billing_Address_section_displayed());
		
		LOGGER.info("verified Billing Address message");
	}
	
	@And("^user sees Payment Method section (.*)$")
	public void verify_payment_method_section(String payment_method_message) throws Throwable {
		Assert.assertEquals(payment_method_message, comPaybill.Verify_payment_method_section_displayed());
		
		LOGGER.info("verified payment method message");
	}
	
	@And("^user sees Credit Card option (.*)$")
	public void verify_Creadit_card_option(String payment_method_message) throws Throwable {
		Assert.assertEquals(payment_method_message, comPaybill.Verify_credit_card_option_displayed());
		
		LOGGER.info("verified Credit Card option");
	}
	
	
	@When("^on paybill checkout page user enters credit card number (.*), Expiration Month (.*), Expiration Year (.*), cvv (.*)$")
	public void user_enter_creditcard_details_checkout(String CCnumber, String CCMonth, String CCExpyear, String CCcvv) throws Throwable {

		comPaybill.Enter_CC_details(CCnumber, CCMonth, CCExpyear, CCcvv);

	}
	
	@And("^user clicks on Submit Payment button$")
	public void click_submit_payment_button() throws Throwable {
		comPaybill.clicking_submit_payment_button();
		
		LOGGER.info("clicked submit payment button");
	}
	
	
	@Then("^payment confirmation page should display payment confirmation message (.*), Order details name (.*), Subscription Type (.*), Order details price (.*), Order details term (.*), Order details payment method (.*), Order Amount subtotal (.*), Order amount tax (.*),  Order total amount paid (.*) and Billing country name (.*)$")
	public void verify_payment_confirmation_page_messages(String payment_confirmation_message,String order_details_name, String Subscription_type,String order_details_price,  
			String order_details_term, String order_details_payment_method, String order_Amount_subtotal,
			String order_amount_tax, String order_total_amount_paid, String billing_country_name) throws Throwable {
		
		String OrderConfirmation = comPayment.Order_Confirmation_message();		
		Confirmationmap = comPayment.Order_Confirmation_Page_Details();
		expResp = verify_payment_confirmation_messages(payment_confirmation_message,order_details_name,Subscription_type,order_details_price,order_details_term,order_details_payment_method,order_Amount_subtotal,
				order_amount_tax,order_total_amount_paid,billing_country_name,kmap.get("email"),OrderConfirmation,Confirmationmap);	
		
				
		ucid = expResp.jsonPath().getString("ucId");
		kmap.put("ucid", ucid);
//		StoreExpSteps.ucid = ucid;
				
	}
	
	@When("^user clicks on Visit My Account Profile$")
	public void click_Visit_My_Account() throws Throwable {
		
		comPayment.Click_Visit_My_Account();
		
	}
	
	
}
