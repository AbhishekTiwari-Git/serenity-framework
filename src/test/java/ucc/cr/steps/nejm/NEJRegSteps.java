package ucc.cr.steps.nejm;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import java.io.IOException;

import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import ucc.cr.pages.nejm.ui.NEJRegPage;
import ucc.cr.pages.nejm.ui.utils.NEJRegPageHelper;
import ucc.i.method.aic.AICGET;
import ucc.i.method.nejmliteratumsystem.NEJMLiteratumSystemGET;
import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

public class NEJRegSteps {

	static String end_ptAk = null;
	static String user_email, pass, file;
	static String altId = "null";
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String widgetsBaseUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("widgets.base.url");
	static String nejmBaseUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("nejm.base.url");
	static String nejmWidgetUrl;
	String catalystRegUser = "registration_qualtoIC_RegisteredUser.json";

	CommonFunc commonFunc = new CommonFunc();
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();

	public static Response sysRespAk, sysRespLt = null;
	static String end_ptLt = null;

	@Managed
	WebDriver driver;

	@Steps
	NEJRegPage NejPage;

	@Steps
	NEJRegPageHelper NejHelper;

	@Steps
	AICGET AICGETSteps;

	@Steps
	NEJMLiteratumSystemGET nejmLiteratumSystemGET;

	@Given("^user set nejm form on widget page as per url (.*)$")
	public void setPromoCode_InUrl(String formType) {
		nejmWidgetUrl = NejPage.setNejmWidgetsUrl(formType);
	}
	
	@Given("^user set nejm group reg form on widget page as per url (.*)$")
	public void setNejGroup_InUrl(String formType) {
		nejmWidgetUrl = NejPage.setNejmGroupWidgetsUrl(formType);
	}

	@When("^user launches the nejm form on css site$")
	public void launch_embeddedAccordianForm() throws Throwable {
		commonFunc.Launch_URL(widgetsBaseUrl + nejmWidgetUrl);
	}

	@Then("^nejm create account form is displayed$")
	public void validateNejForm() {
		Assert.assertEquals(true, NejPage.validateNejForm());
	}

	@When("^user submit details on the nejm form using data:$")
	public void submitNejmRegDetails(DataTable dt) throws InterruptedException {
		NejPage.submitNejRegDetails(dt);
	}

	@And("^user check country is prepopulated while submitting the form$")
	public void checkPrepopulation() {
		Assert.assertEquals(true, (boolean) tUtil.getFromSession("CountryPrepopulation"));
	}

	@Then("^nejm thank you confirmation page is displayed with success (.*) and continue reading link (.*)$")
	public void validateAccCreation(String expectedMsg, boolean linkVal) throws InterruptedException {
		Assert.assertEquals(true, NejPage.validateThankYouPopUp());
		Assert.assertEquals(expectedMsg, NejPage.validateThankYouContent());
		Assert.assertEquals(linkVal, NejPage.validateContinueReadingLink());
	}

	@When("^user select manage alerts link on nejm thankyou modal$")
	public void selectAlertsLink() {
		NejPage.selectAlertsLink();
	}

	@Then("^nejm user is redirected to my account site$")
	public void validateRedirection() {
		Assert.assertEquals(true, NejPage.validateRedirection());
	}

	@When("^user fetches uuid of the user using email id from akamai$")
	public void fetchUUID() {
		end_ptAk = AICGETSteps.setEndPointEmail((String) tUtil.getFromSession("Email"));
		sysRespAk = AICGETSteps.getUserFromAkamai(end_ptAk).extract().response();
		tUtil.putToSession("uuid", sysRespAk.jsonPath().getString("uuid"));
	}

	@Then("^user validate nejm profile in Akamai using data:$")
	public void nejmAkamaiValidation(DataTable dt) {
		end_ptAk = AICGETSteps.setEndpointUserID((String) tUtil.getFromSession("uuid"));
		sysRespAk = AICGETSteps.getUserFromAkamai(end_ptAk).extract().response();
		Assert.assertEquals(true, NejHelper.akamaiValidations(sysRespAk, dt));
	}

	@And("^user validate tags value in nejm literatum using data:$")
	public void nejmLiteratumValidaton(DataTable dt) {
		end_ptLt = nejmLiteratumSystemGET.setEndpontIdentitiesUCID((String) tUtil.getFromSession("uuid"));
		sysRespLt = nejmLiteratumSystemGET.get(end_ptLt).extract().response();
		Assert.assertEquals(true, NejHelper.nejmLiteratumValidations(sysRespLt, dt));
	}

	@Given("^cookied lead user is created using data:$")
	public void cookiedLeadUser(DataTable dt) throws Throwable {
		NejPage.cookiedLeadUser(dt);
	}

	@When("^catalyst lead user submit details on Nejm Reg form using data:$")
	public void submitCatalystLeadUser(DataTable dt) throws Throwable {
		NejPage.submitPrePopulatedNejForm(dt);
	}

	@When("^user fetch email step one design details of NEJM multi Reg form using data:$")
	public void fetchStepOne_DesignDetails(DataTable dt) throws IOException {
		NejPage.fetchStepOne_DesignDetails(dt);
	}

	@When("^user fetch country step two design details of NEJM multi Reg form using (.*)$")
	public void fetchStepTwo_DesignDetails(String country) {
		NejPage.fetchStepTwo_DesignDetails(country);
	}

	@When("^user fetch professional category step three design details of NEJM multi Reg form using data:$")
	public void fetchStepThree_DesignDetails(DataTable dt) {
		NejPage.fetchStepThree_DesignDetails(dt);
	}

	@When("^user fetch final step design details of NEJM multi Reg form using data:$")
	public void fetchFinalStep_DesignDetails(DataTable dt) throws InterruptedException {
		NejPage.fetchFinalStep_DesignDetails(dt);
	}

	@Then("^design of nejm reg multi step form should be as per approved styling:$")
	public void validateNejmCssStyle_Design(DataTable dt) {
		NejPage.printStep(dt);
		Assert.assertEquals(true, NejPage.validateDesign(dt));
	}

	@When("^non cookied lead user submit details on nejm reg form using data:$")
	public void submitNonCookiedLeadUserDetails(DataTable dt) throws InterruptedException {
		NejPage.submitNejReg_NonCookied_Details(dt, user_email);
	}

	@When("^user set visibility of drop downs on step third on basis of professional category:$")
	public void setDropDownVisibility(DataTable dt) {
		NejPage.setDropDownVisibility(dt);
	}

	@Then("^user validate drop down visibility as per data:$")
	public void validateDropDownVisibility(DataTable dt) {
		Assert.assertEquals(true, NejPage.validateVisibility(dt));
	}

	@When("^catalyst registered user sign in on (.*) NEJM Multi step registration form")
	public void catalystRegUserSignIn_NejmReg(String formType) {
		user_email = jsonUtils.getFromJSON(catalystRegUser, "email");
		pass = jsonUtils.getFromJSON(catalystRegUser, "password");
		NejPage.catalystRegUserSignIn_NejmReg(user_email, pass, formType);
	}

	@Then("^catalyst registered user is able to sign in on nejm multi reg form:$")
	public void validateCatalystRegUserSignIn_NejmReg(DataTable dt) {
		Assert.assertEquals(true, NejPage.validateCatalystRegUserSignIn_NejmReg(dt));
	}

	@And("^user validate Nejm SOI checkbox visibility (.*) and checked (.*) as per country$")
	public void validateNejm_SOICheckboxVisibility(String expVisibility, String expCheckedStatus) {
		Assert.assertEquals(expVisibility, NejPage.validateNejm_SOICheckboxVisibility(expVisibility));
		Assert.assertEquals(expCheckedStatus, NejPage.validateNejm_SOICheckbox_CheckedStatus(expCheckedStatus));
	}
	
	@And("^user validate Nejm catalyst connect checkbox visibility (.*) and checked (.*) as per country$")
	public void validateNejm_CatConnectCheckboxVisibility(String expVisibility, String expCheckedStatus) {
		Assert.assertEquals(expVisibility, NejPage.validateNejm_ConnectCheckboxVisibility(expVisibility));
		Assert.assertEquals(expCheckedStatus, NejPage.validateNejm_ConnectCheckbox_CheckedStatus(expCheckedStatus));
	}


	@When("^user submit details with email preferences on the nejm form:$")
	public void submitNejm_WithEmailPref(DataTable dt) throws InterruptedException {
		NejPage.submitNejm_WithEmailPref(dt);
	}

	@And("^user navigate to nejm site and wait until signed in to finish lazy load$")
	public void navigateToNejm() throws Throwable {
		commonFunc.Launch_URL(nejmBaseUrl);
		commonFunc.waitForLoadPage();
		Assert.assertEquals(true, NejPage.validateUserSignedIn());
	}

	@And("^user validate Nejm ETOC checkbox visibility (.*) and checked (.*) as per country$")
	public void validateNejm_ETOCCheckboxVisibility(String expVisibility, String expCheckedStatus) {
		Assert.assertEquals(expVisibility, NejPage.validateNejm_ETOCCheckboxVisibility(expVisibility));
		Assert.assertEquals(expCheckedStatus, NejPage.validateNejm_ETOCCheckbox_CheckedStatus(expCheckedStatus));
	}

	@When("^user sign in on store site using email from json file (.*)$")
	public void signInStoreSite(String file) throws Throwable {
		user_email = jsonUtils.getFromJSON(file, "email");
		pass = jsonUtils.getFromJSON(file, "password");
		NejPage.signInOnStoreSite(user_email, pass);
	}

	@And("^validate SSO by navigating to nejm and cssjs site$")
	public void validateSSO_NEJM_CSSSite() throws Throwable {
		Assert.assertEquals(true, NejPage.validateSSO_NEJM_CSSSite());
	}

	@Then("^user validate progress bar functionality using data:$")
	public void validateProgressBar(DataTable dt) throws InterruptedException {
		Assert.assertEquals(true, NejPage.validateProgressBar(dt));
	}

	@When("^user click on continue reading link on thank you pop up$")
	public void selectContinueReadingLink() {
		NejPage.clickContinueReadingLink_OnThankYouPopUp();
	}

	@Then("^user is redirected to nejm site with full access to article content$")
	public void validateArticleAccessNejmSite() {
		Assert.assertEquals(false, NejPage.validateArticleAccess());
	}

	@When("^user sign in on my account site using email from json file (.*)$")
	public void myAccountSignIn(String file) throws Throwable {
		user_email = jsonUtils.getFromJSON(file, "email");
		pass = jsonUtils.getFromJSON(file, "password");
		NejPage.myAccountSignIn(user_email, pass);
	}

	@And("^user sign out of nejm site$")
	public void signOutNejm() {
		NejPage.signOutNejm();
	}

	@And("^user sign in on nejm site using email from json file (.*)$")
	public void signInNejmSite(String file) throws Throwable {
		user_email = jsonUtils.getFromJSON(file, "email");
		pass = jsonUtils.getFromJSON(file, "password");
		NejPage.nejmSignIn(user_email, pass);
	}

	@Then("^user navigate to my account site and validate new email address as per (.*)$")
	public void checkMyAccountEmailAddress(String file) throws Throwable {
		user_email = jsonUtils.getFromJSON(file, "email");
		Assert.assertEquals(user_email, NejPage.validateNewEmailAddress_MyAccount());
	}

	@When("^user navigate to catalyst site in new tab$")
	public void navigateCatalyst() throws Throwable {
		NejPage.navigateCatalyst();
	}

	@And("^user launches nejm site$")
	public void launchNejmSite() throws Throwable {
		commonFunc.Launch_URL(nejmBaseUrl);
		commonFunc.waitForLoadPage();
	}

	@When("^user click on back to site button on nejm thank you pop up$")
	public void clickBackToSiteBtn() {
		NejPage.clickBackToSiteBtn();
	}

	@Then("^user validate thank you modal is closed$")
	public void validateThankYouModalWindow() {
		Assert.assertEquals(false, NejPage.validateThankYouModalWindow());
	}

	@When("^user click on close button on thankyou pop up$")
	public void clickCloseBtn() {
		NejPage.clickCloseBtn_ThankYouPopUp();
	}

	@When("^user fetches design details for nejm thankyou confirmation using form (.*)$")
	public void fetchDesign_ThankYouModal(String formType) {
		NejPage.fetchDesign_ThankYouModal(formType);
	}

	@Then("^design of nejm reg thank you modal should be as per approved styling:$")
	public void validateDesign_ThankYouModal(DataTable dt) {
		Assert.assertEquals(true, NejPage.validateDesign(dt));
	}

	@When("^user click on outside gray area of thank you pop$")
	public void clickOutsideThankYouPopUp() {
		NejPage.clickOutsideThankYouPopUp();
	}

	@Then("^validate thank you confirmation modal is not closed")
	public void validateIfThankYouPopUpClosed() {
		Assert.assertEquals(true, NejPage.validateThankYouModalWindow());
	}
	
	@And("^user validate alert details on thank you confirmation on basis of data:$")
	public void validateAlertDetails(DataTable dt) {
		Assert.assertEquals(true, NejPage.validateAlertDetails(dt));
	}
	
	@And("^user validate that NEJM logo is not displayed on thank you confirmation$")
	public void validateNejmLogo_ThankYouConfirmation() {
		Assert.assertEquals(false, NejPage.validateThankYouLogo());
	}
	
	@And("^user validates common email preference catalyst SOI and connect values in Akamai using data:$")
	public void validateCatalystEmailPref_NejmGroup(DataTable dt) {
		Assert.assertEquals(true, NejHelper.akamaiValidations_SOIConnect(sysRespAk, dt));
	}
}
