package ucc.cj.steps;

import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import ucc.cj.pages.ui.CJSubscriberAccessPage;

import ucc.com.pages.ui.ComCheckout;
import ucc.cr.pages.catalyst.ui.CreateAccount;
import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;

public class CJSubscriberAccessSteps {

	Logger LOGGER = Logger.getLogger(CJSubscriberAccessPage.class.getName());

	@Managed
	WebDriver driver;

	@Managed
	CJSubscriberAccessPage subscribeacc;

	@Steps
	HomePage homepage;

	@Steps
	CommonFunc ComFun;

	@Steps
	CreateAccount createacc;

	@Steps
	ComCheckout comcheckout;

	// Not At Institution_Subscriber Access Validation

	@When("^User is on the Home Page of Catalyst Journal (.*)$")
	public void enter_catalysturl(String url) throws Throwable {
		LOGGER.info("Enter Catalyst Journal home page URL");
		ComFun.Launch_URL(url);
	}

	@And("^user click on any InDepth article link$")
	public void click_indepthlink() throws InterruptedException {
		LOGGER.info("Click on any Indepth Article link");
		subscribeacc.click_indeptharticlelink();
	}

	@Then("^Subscribe Now field should not be displayed$")
	public void check_subscribenowfield() {
		LOGGER.info("Check Subscribe Now field not getting displayed");
		subscribeacc.check_subscribenowfield();
	}
}
