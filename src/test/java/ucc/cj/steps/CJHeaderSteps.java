package ucc.cj.steps;

import java.util.logging.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import ucc.cj.pages.ui.CJHeaderPage;
import ucc.com.pages.ui.ComCheckout;
import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;

public class CJHeaderSteps {

	private static final Logger LOGGER = Logger.getLogger(CJHeaderSteps.class.getName());

	@Managed
	WebDriver driver;

	@Managed
	CJHeaderPage header;

	@Steps
	HomePage homepage;

	@Steps
	CommonFunc ComFun;

	@Steps
	ComCheckout comcheckout;

	// Validate the header section "SIGN IN >"dropdown when user is NOT signed for
	// Create Account flow
	@When("^User mouse hover on Sign In$")
	public void mouse_hover_on_SignIn() {
		LOGGER.info("Mouse hover on SignIn field");
		header.verify_mousehover_signin();
	}

	@Then("^dropdown gets open having value Sign in,OpenAthensShibboleth,Create Account$")
	public void dropdown_having_value_Signin_OpenAthensShibboleth_CreateAccount() {
		LOGGER.info("Check SignIn,OpenAthens/Shibboleth and Create Account fields are present");
		Assert.assertTrue(header.verify_SignIn_OpenAthensShibboleth_CreateAccount_present());

	}

	@Then("^Create Account page and header text (.*) should gets displayed$")
	public void display_createaccount_page(String HeaderText) {
		LOGGER.info("Check Create Account page and pop up Header text message");
		Assert.assertTrue(header.verify_createaccount_page());
		Assert.assertEquals(HeaderText, header.check_createaccount_headermessage());
	}

	// Validate the OpenAthens / Shibboleth Sign In Page_User click on "Login via
	// OpenAthens"
	@When("^user click on OpenAthens/Shibboleth$")
	public void click_openathens_shibboleth() {
		LOGGER.info("Mouse hover on SignIn and Click on OpenAthens/Shibboleth link");
		header.verify_mousehover_signin();
		header.click_openathens_shibboleth();
	}

	@And("^user check OpenAthens / Shibboleth Sign In page (.*) and click on Login via OpenAthens$")
	public void check_openathens_header_and_click_on_login_via_openathens(String pageheader) {
		LOGGER.info("Check OpenAthens header and click on Login Via OpenAthens Link");
		Assert.assertEquals(pageheader, header.check_openathens_shibboleth_header());
		header.click_login_via_openathes();
	}

	@And("^User enters Username (.*) and Password (.*) and Click on SignIn button$")
	public void enter_username_and_password(String username, String password) {
		LOGGER.info("Enter Username,Password and Clcik on SignIn Button");
		header.enter_email_or_username_and_password(username, password);

	}

	@Then("^Access message (.*) should gets displayed$")
	public void check_access_message(String message) {
		LOGGER.info("Check the OpenAthens page Access Message");
		Assert.assertEquals(message, header.check_accessmessage());
	}

	// Validate the header section "Follow Us" Dropdown

	@When("User mouse hover on Follow Us")
	public void user_mouse_hover_on_follow_us() {
		LOGGER.info("User mouse hover on Follow Us");
		header.user_mouse_hover_on_follow_us();
	}

	@Then("dropdown gets open having value Facebook, Twitter, Instagram, YouTube, LinkedIn")
	public void dropdown_gets_open_having_value_facebook_twitter_instagram_you_tube_linked_in() {
		LOGGER.info("dropdown gets open having value Facebook, Twitter, Instagram, YouTube, LinkedIn");
		Assert.assertTrue(header.dropdown_gets_open_having_value_facebook_twitter_instagram_you_tube_linked_in());
	}

	// Validate the header section "Events" Dropdown menu

	@When("User mouse hover on Events")
	public void user_mouse_hover_on_events() {
		LOGGER.info("User mouse hover on Events");
		header.user_mouse_hover_on_events();
	}

	@Then("verify the dropdown values")
	public void verify_the_dropdown_values() {
		LOGGER.info("verify the dropdown values");
		Assert.assertTrue(header.verify_the_dropdown_values());
	}

	// Validate the header section "INSIGHTS COUNCIL" dropdown menu

	@When("User mouse hover on INSIGHTS COUNCIL")
	public void user_mouse_hover_on_insights_council() {
		LOGGER.info("user mouse hover on INSIGHTS COUNCIL");
		header.user_mouse_hover_on_insights_council();
	}

	@Then("verify the dropdown all values")
	public void verify_the_dropdown_all_values() {
		LOGGER.info("verify the dropdown all values");
		Assert.assertTrue(header.verify_the_Insights_Council_dropdown_values());

	}

	// Validate the header section "About" dropdown menu

	@Then("user hover mouse on About and verify all drop down values")
	public void user_hover_mouse_on_about_and_verify_all_drop_down_values() {
		header.user_mouse_hover_on_about();
		Assert.assertTrue(header.verify_the_about_dropdown_all_values());
	}

	// Validate the header section "Topic" dropdown menu
	
	@Then("user hover mouse on Topic and verify all drop down values")
	public void user_hover_mouse_on_topic_and_verify_all_drop_down_values() {
	   header.user_mouse_hover_on_Topics();
	   Assert.assertTrue(header.verify_the_Topics_dropdown_all_values());
		
	}
}
