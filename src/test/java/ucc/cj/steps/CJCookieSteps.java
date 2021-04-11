package ucc.cj.steps;

import java.util.logging.Logger;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import ucc.cj.pages.ui.CJCookiePage;
import ucc.pages.ui.CommonFunc;

public class CJCookieSteps {

	@Steps
	CJCookiePage CookiePage;

	@Steps
	CommonFunc comFun;

	@Managed
	WebDriver driver;

	Logger LOGGER = Logger.getLogger(CJCookiePage.class.getName());

	@Then("^verify the cookies notice Message (.*) and click on close icon$")
	public void verify_the_cookies_notice_message_and_click_on_close_icon(String message) {
		LOGGER.info("Verfiy the cookies message");
		Assert.assertTrue(comFun.validateText_InPageSource(message));
		CookiePage.verify_the_cookies_notice_message_and_click_on_close_icon();
	}

	// OCLC Login

	@Given("^User is on OCLC (.*) Page$")
	public void user_is_on_oclc_page(String URL) throws Throwable {
		LOGGER.info("User is on OCLC Page");
		comFun.Launch_URL(URL);
	}

	@And("^User enter UserName (.*) Password (.*) and click on login button$")
	public void user_enter_user_name_passwordand_click_on_login_button(String UserName, String Password) {
		LOGGER.info("Enter username password and click");
		CookiePage.user_enter_user_name_passwordand_click_on_login_button(UserName, Password);

	}
	

}
