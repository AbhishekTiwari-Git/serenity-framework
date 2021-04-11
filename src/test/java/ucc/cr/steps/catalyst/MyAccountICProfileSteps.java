package ucc.cr.steps.catalyst;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ucc.cr.pages.catalyst.ui.JoinIC;
import ucc.cr.pages.catalyst.ui.MyAccountICProfile;
import ucc.cr.pages.catalyst.ui.MyAccountPage;
import ucc.cr.pages.catalyst.ui.RegInsighCouncilPage;
import ucc.i.method.aic.AICGET;
import ucc.i.method.icv.ICVGET;
import ucc.i.method.kinesys.KinesysGET;
import ucc.i.method.literatum.LiteratumGET;
import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.RestUtil;
import ucc.utils.TestUtils;

public class MyAccountICProfileSteps {

	static String end_ptAk, end_ptKi, endPt = null;
	static String user_email;
	static String file, pass, url;
	static String altId = "null";
	private static final Logger LOGGER = LoggerFactory.getLogger(RegInsighCouncilPage.class);

	CommonFunc commonFunc = new CommonFunc();
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();

	public static Response sysRespAk, sysRespKi, response = null;
	public static ValidatableResponse respKi, resAk = null;

	@Steps
	MyAccountICProfile myAccountICProfile;

	@Steps
	KinesysGET kinesysGETSteps;

	@Steps
	AICGET AICGETSteps;

	@Steps
	MyAccountPage myAccountPage;

	@Steps
	JoinIC joinIC;

	@Steps
	RegInsighCouncilPage regInsighCouncilPage;

	@Steps
	ICVGET IVSGETSteps;

	@Steps
	LiteratumGET LiteratumGETSteps;

	@Managed
	WebDriver driver;

	@When("^user click on insights council from left panel under summary$")
	public void clickMyAccountICLink() {
		myAccountICProfile.selectICLink();
	}

	@Then("^user should be able to view insights council page with a message (.*)$")
	public void validateICProfile(String message) {
		Assert.assertEquals(myAccountICProfile.validateICProfilePage(), message);
	}

	@And("^Join Now Button should appear with learn more link$")
	public void joinNowBtn() {
		Assert.assertEquals(myAccountICProfile.validateJoinNowBtn(), true);
	}

	@And("^user should not be able to view the IC link on my account page$")
	public void validateICLink() {
		Assert.assertEquals(myAccountICProfile.validateICLink(), false);
	}

	@When("^user joins IC from my account with data:$")
	public void MyAccountProfile_JoinIC(DataTable dt) throws InterruptedException {
		myAccountICProfile.getQuesData(dt);
		myAccountICProfile.submitICform();
	}

	@Then("^selected question details should come up on my account IC profile page$")
	public void validateSelectedQues() {
		myAccountICProfile.setActualValuesICProfile();
		Assert.assertEquals(myAccountICProfile.validateICProfile_Questions(), true);
	}

	@Then("^insights council profile should be displayed without survey$")
	public void validateICProfile() {
		Assert.assertEquals(myAccountICProfile.validateICProfile_withoutSurvey(), true);
	}

	@Then("^insights council profile should be displayed with survey$")
	public void validateICProfile_withSurvey() {
		Assert.assertEquals(myAccountICProfile.validateICProfile_withSurvey(), true);
	}

	@When("^user with survey enters email address (.*) and password (.*)$")
	public void login_surveryUser(String email, String pass) throws InterruptedException {
		myAccountPage.login(email, pass);
	}

	@And("^user is able to view survey$")
	public void surveyLaunched() {
		Assert.assertEquals(myAccountICProfile.surveyLaunched(), true);
	}

	@When("^user edit the IC profile with data:$")
	public void editICProfile(DataTable dt) {
		myAccountICProfile.getQuesData(dt);
		myAccountICProfile.editICProfileQuestions();
	}

	@And("^user validates updates in Akamai for user as per JSON file (.*)$")
	public void validateICProfileChangesInBackend(String fileName) throws Throwable {
		user_email = jsonUtils.getFromJSON(fileName, "email").toString();
		end_ptAk = AICGETSteps.setEndPointEmail(user_email);
		sysRespAk = AICGETSteps.getUserFromAkamai(end_ptAk).extract().response();
		myAccountICProfile.fetchActualValuesAkamai(sysRespAk);
		Assert.assertEquals(myAccountICProfile.validateICProfileChangesInBackend(), true);
		String icMember = sysRespAk.jsonPath().getString("Catalyst.insightsCouncilMember");
		Assert.assertEquals(icMember, "true");
	}

	@And("^user validates updates in Kinesis System API using JSON file (.*)$")
	public void validateICProfileChangesInKinesis(String fileName) throws Throwable {
		url = kinesysGETSteps.kinesys_url;
		RestUtil kinesys = new RestUtil(url);
		altId = myAccountICProfile.getAlternateID();
		end_ptKi = kinesysGETSteps.setEndpoint(altId);
		sysRespKi = kinesysGETSteps.getPanelist(end_ptKi).extract().response();
		myAccountICProfile.fetchActualValuesKinesis(sysRespKi, fileName);
		Assert.assertEquals(myAccountICProfile.validateICProfileChangesInBackend(), true);
	}

	@And("user validate updates in Literatum")
	public void validateLiteratum() {
		 endPt = LiteratumGETSteps.setEndpointIdentity(altId);
		 response = LiteratumGETSteps.get(endPt).extract().response();
		LOGGER.info("===========================================");
		LOGGER.info(response.asString());
		LOGGER.info("===========================================");
		 Assert.assertEquals(myAccountICProfile.validateBackendChanges_Literatum(response),
		 true);
	}

	@When("^user set role specific IC Profile with data:$")
	public void setRole_ICData(DataTable dt) {
		myAccountICProfile.getQuesData(dt);
	}

	@When("^user set place specific IC Profile with data:$")
	public void setPlace_ICData(DataTable dt) {
		myAccountICProfile.getQuesData_place(dt);
	}

	@When("^user click on manage alert link on thankyou pop up$")
	public void clickAlertLink() throws InterruptedException {
		myAccountICProfile.clickAlertLink_thankYouPopUp();		
		String email = jsonUtils.getFromJSON("flow_reg_cust_EMAIL.json", "email");
		pass = myAccountICProfile.getLeadUserAccount();
		myAccountPage.login(email, pass);
		myAccountPage.userAtMyAccountPage();
		myAccountPage.userPersonalInfomration();
	}
	
	@Given("^user set jsonAttributekey (.*), jsonAttributeValue (.*) in jsonfile (.*)$")
	public void setPlace_Anc(String attributeKey, String attributeValue, String file) throws Exception {
		jsonUtils.update_JSONValue(file, attributeKey, attributeValue);
	}
}
