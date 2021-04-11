package ucc.cr.steps.catalyst;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import ucc.cr.pages.catalyst.ui.CreateLead;
import ucc.i.method.aic.AICGET;
import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;
import ucc.utils.TestUtils;

import java.util.List;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
import static org.junit.Assert.assertEquals;

public class LeadSteps {

	TestUtils tUtil=new TestUtils();
	public static Response sysResp = null;
	static String end_pt = null;
	private String user_email;

	@Steps
	CreateLead LEAD;
	
	@Steps
	HomePage homepage;

	@Steps
	CommonFunc commonFunc;

	@Steps
	AICGET aicGETSteps;

	@Managed
	WebDriver driver;

	@Given("^User opens Catalyst WebSite$")
	public void user_is_on_Catalyst_WebSite() throws Throwable {
		homepage.launchHomePage();
	}

	@Then("^User sees modal window with text (.*)$")
	public void account_created(String message) throws Throwable {
		assertEquals(LEAD.account_created(), message);
//		homepage.closeHomePage();

	}

	@Then("^User clicks to link Newsletter$")
	public void userClicksToLinkNewsletter() throws InterruptedException {
		LEAD.click_newsletter();
	}

	@When("^User enters email (.*) and click button$")
	public void userEntersEmail(String email) {
		user_email = tUtil.AppendTimestamp(email);
		LEAD.enter_email_click_button(user_email);
	}

	@When("^User enters email$")
	public void userEnterdefaultEmail() {
		user_email = commonFunc.getConfEmail();
		LEAD.enter_email(user_email);
	}
	
	@When("^User enters email and click button$")
	public void userEnterdefaultEmailClickButton() {
		user_email = commonFunc.getConfEmail();
		LEAD.enter_email_click_button(user_email);
	}
	
	
	
	@When("^User enters firstName, lastName, country (.*) and organization and click button$")
	public void userEntersFirstNameLastNameCountryAndOrganization(String country) throws Exception {
		LEAD.UserCreation(country);
	}
	
	@When("^user clicks on the close icon of the thank you message$")
	public void userCloseThankYouModalWindow() throws Throwable {
		LEAD.close_modal_window();
	}

	@Then("^I check alternateID and audienceType for created user$")
	public void iCheckUserAlternateIDAndAudienceType() {
		end_pt = aicGETSteps.setEndPointEmail(user_email);
		sysResp = aicGETSteps.getUserFromAkamai(end_pt).extract().response();

		List listID = sysResp.jsonPath().getList("alternateID");
		String type = sysResp.jsonPath().getString("audienceType");
		Assert.assertEquals(listID.size(), 0);
		Assert.assertEquals(type, "LEAD");
	}

	@And("I check user email in email field")
	public void iCheckUserEmailInEmailField() throws InterruptedException, ParseException {
		LEAD.click_newsletter();
		String email = LEAD.get_email();

		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		String profileData = (String) js.executeScript(String.format(
				"return window.localStorage.getItem('%s');", "akamaiProfileData"));
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(profileData);

		Assert.assertEquals(json.get("email"), email);
	}
}


