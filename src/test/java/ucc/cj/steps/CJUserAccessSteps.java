package ucc.cj.steps;

import java.util.logging.Logger;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import ucc.cj.pages.ui.CJUserAccessPage;
import ucc.com.pages.ui.ComCheckout;
import ucc.cr.pages.catalyst.ui.CreateAccount;
import ucc.cr.pages.catalyst.ui.CreateLead;
import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;

public class CJUserAccessSteps {

	Logger LOGGER = Logger.getLogger(CJUserAccessPage.class.getName());

	@Managed
	WebDriver driver;

	@Managed
	CJUserAccessPage useraccess;

	@Steps
	HomePage homepage;

	@Steps
	CommonFunc ComFun;

	@Steps
	ComCheckout comcheckout;

	@Steps
	CreateAccount createacc;

	@Steps
	CreateLead LEAD;

	// Lead User NOT from Institution tries to access In Depth Articles

	@When("^user click on any InDepth link and article$")
	public void click_indeptharticle() throws InterruptedException {
		LOGGER.info("Verify and Click on InDepth link and Article: ");
		useraccess.click_indepthlink();
		useraccess.click_indepthdarticle();
	}

	// Anonymous User NOT from Institution tries to access Case Study
	@When("^Anonymous User click the Case Study article$")
	public void click_casestudyarticle() throws InterruptedException {
		LOGGER.info("Anonymous User click the Case Studies link and Article");
		useraccess.click_casestudies();
		useraccess.click_casestudyarticle();
	}

	@Then("^user able to see subscribe now button and subscribe message (.*)$")
	public void verify_subscribe_button_and_message(String Subscribemessage) {
		LOGGER.info("Check that the Subscribe button and Message is Displayed:  " + useraccess.check_subscribeBtn());
		Assert.assertEquals(useraccess.check_subscriptionmessage(), Subscribemessage);
	}

	// Insights Council NOT from Institution tries to access In Depth Articles

	@Then("the user click on go to homepage link")
	public void the_user_click_on_go_to_homepage_link() {

		useraccess.click_Go_to_homepage();
	}

	// Registered User from Institution tries to access In Depth
	@Given("^user is on article page URL (.*)$")
	public void user_is_on_any_article_page_URL(String URL) throws Throwable {
		ComFun.clearCookies();
		ComFun.Launch_URL(URL);
	}

}
