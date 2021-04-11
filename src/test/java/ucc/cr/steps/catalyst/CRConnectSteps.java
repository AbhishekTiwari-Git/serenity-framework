package ucc.cr.steps.catalyst;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import ucc.cr.pages.catalyst.ui.CRConnectPage;
import ucc.cr.pages.catalyst.ui.CreateAccount;
import ucc.cr.pages.catalyst.ui.EventsPage;
import ucc.cr.pages.catalyst.ui.MyAccountPage;
import ucc.cr.pages.catalyst.ui.PDFPremiumRegPage;
import ucc.i.method.aic.AICGET;
import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

public class CRConnectSteps {

	static String end_ptAk = null;
	static String user_email, pass, file, lead_email, reg_email;
	static String altId = "null";
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String baseUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("myaccount.base.url");

	CommonFunc commonFunc = new CommonFunc();
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();

	public static Response sysRespAk = null;
	public static ValidatableResponse resAk = null;

	@Managed
	CRConnectPage connectEletter;

	@Steps
	CreateAccount createAccount;

	@Steps
	MyAccountPage myAccountPage;
	
	@Steps
	EventsPage eventsPage;
	
	@Steps
	PDFPremiumRegPage pdfPremiumRegPage;

	@Managed
	WebDriver driver;

	@Steps
	AICGET AICGETSteps;

	@When("^user launches the catalyst About page$")
	public void aboutPage() {
		connectEletter.clickAbout();
	}

	@Then("^user click on Stay Informed with NEJM Catalyst Connect$")
	public void catalystConnectLink() {
		connectEletter.clickCatalystConnectLink();
	}

	@When("^user set the gdpr data:$")
	public void setGdprData(DataTable dt) {
		connectEletter.setGdpr_connectSuccessMsg(dt);
	}

	@And("^user checks_unchecks the SOI (.*)$")
	public void check_uncheckSOI(String soiStatus) throws InterruptedException {
		connectEletter.check_Uncheck_SOI(soiStatus);
	}

	@And("^user submit email (.*) on connect eLetter form$")
	public void enterEmail_and_continue(String email) throws Exception {
		user_email = tUtil.AppendTimestamp(email);
		connectEletter.enterEmail_and_continue(user_email);
		jsonUtils.update_JSONValue("flow_reg_cust_EMAIL.json", "email", user_email);
	}

	@When("^user enter connect form data:$")
	public void connectForm(DataTable dt) {
		connectEletter.setFormValues(dt);
	}

	@Then("^user check gdpr messaging (.*) based upon country$")
	public void checkGDPRMessaging(String gdprMessage) {

		Assert.assertEquals(connectEletter.checkGdprMessages(), gdprMessage);
		Assert.assertEquals(connectEletter.checkGdprLinks(), true);
		Assert.assertEquals(connectEletter.checkGdprLinksNavigation(), true);
	}

	@When("^user click on Sign up button$")
	public void connecteLetterSignUp() {
		connectEletter.click_signUp();
	}

	@Then("^thank you message should be displayed$")
	public void checkMessage() {
		Assert.assertEquals(connectEletter.checkMessage(), true);
	}

	@And("^user validates SOI and connect values in Akamai on basis of JSON file (.*)$")
	public void validateSOI_inAkamai(String file) {

		user_email = jsonUtils.getFromJSON(file, "email").toString();
		end_ptAk = AICGETSteps.setEndPointEmail(user_email);
		sysRespAk = AICGETSteps.getUserFromAkamai(end_ptAk).extract().response();
		Assert.assertEquals(connectEletter.validateEmailPreferences_InAkamai(sysRespAk), true);
	}

	@When("^registered user enter email and password on my account page$")
	public void registeredUser_SubmitEmail() throws Exception {
		file = "AccExp_POST_CustRegCat.json";
		user_email = jsonUtils.getFromJSON(file, "email");
		pass = jsonUtils.getFromJSON(file, "password");
		myAccountPage.login(user_email, pass);
		reg_email = user_email;
	}

	@When("^user click on Newsletter link from footer$")
	public void clickNewsLetter_FromFooter() {
		commonFunc.waitForLoadPage();
		connectEletter.newsLetterLink_footer();
	}

	@And("^registered user email is pre populated on connect eLetter form$")
	public void submitPrepopulated_Email() {
		Assert.assertEquals(connectEletter.validatePrepopulatedEmail(user_email), true);
		connectEletter.submitPrepopulatedEmail();
		reg_email = user_email;
	}

	@When("^user submit email on connect eLetter form as per JSON (.*)$")
	public void submitRegisteredEmail_OnConnect(String file) {
		user_email = jsonUtils.getFromJSON(file, "email");
		connectEletter.enterEmail_and_continue(user_email);
	}

	@And("^Script dynamically choose the SOI (.*) for registered user$")
	public void check_uncheckSOI_registeredUser(String soiStatus) throws InterruptedException {
		connectEletter.soiOn_connectDialog(soiStatus);
	}

	@Then("^user check GDPR message (.*) on page one of connect form for cookied lead or registered user$")
	public void checkPrivacyPolicy_MessageGDPR(String gdprMessage) {
		Assert.assertEquals(connectEletter.checkGdprMessage_PrivacyPolicy(), gdprMessage);
	}

	@When("^user closes the thankyou popup$")
	public void closeThankYouPopUp() {
		connectEletter.closeThankYouPopUp();
	}

	@Then("^user refresh the browser$")
	public void refresh_browser_to_saveLeadCookies() throws Throwable {
		String url = commonFunc.getCurrentBrowserUrl();
		commonFunc.Launch_URL(url);
	}

	@And("^lead user email is pre populated on connect eLetter form$")
	public void leadPrepopulated_Email() {
		Assert.assertEquals(connectEletter.validatePrepopulatedEmail(user_email), true);
		lead_email = user_email;
	}

	@Then("lead user should remain cookied")
	public void lead_cookied() {
		Assert.assertEquals(connectEletter.validatePrepopulatedEmail(lead_email), true);
	}

	@Then("Registered user should remain cookied")
	public void reg_cookied() {
		Assert.assertEquals(connectEletter.validatePrepopulatedEmail(reg_email), true);
	}

	@And("Lead is non qualified post connect flow")
	public void non_qualified_lead() {
		assertEquals(sysRespAk.jsonPath().getString("Catalyst.qualifiedForCouncil"), null);
		assertEquals(sysRespAk.jsonPath().getString("Catalyst.insightsCouncilMember"), null);
	}

	       	
	@And("^eletter not cookied lead user validate soi (.*), (.*), (.*) as per JSON file (.*)$")
	public void existingLeadEmail_Soi(boolean soiStatus, boolean soiVisib, boolean connStatus, String file) throws Exception {
		user_email = jsonUtils.getFromJSON(file, "email");
		connectEletter.enterEmail_and_continue(user_email);
		Assert.assertEquals(true, pdfPremiumRegPage.checkSOIVisibility(soiStatus, soiVisib));
		tUtil.putToSession("connectValue", connStatus);
	}

}
