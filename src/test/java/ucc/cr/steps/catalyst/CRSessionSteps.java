package ucc.cr.steps.catalyst;

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
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import ucc.cr.pages.catalyst.ui.CRSessionPage;
import ucc.i.method.aic.AICGET;
import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

public class CRSessionSteps {

	static String end_ptAk = null;
	static String user_email, pass, file, lead_email, reg_email;
	static String altId = "null";
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String baseUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("myaccount.base.url");
	static String authTokenUrl;

	CommonFunc commonFunc = new CommonFunc();
	JsonUtils jsonUtils = new JsonUtils();

	public static Response sysRespAk = null;
	public static ValidatableResponse resAk = null;

	@Managed
	CRSessionPage sso;

	@Steps
	HomePage homepage;

	@Managed
	WebDriver driver;

	@Steps
	AICGET AICGETSteps;
	
	@Given("user closes any old session of browser")
	public void closeSession(){
		commonFunc.DriverClose();
	}

	@When("^user check if local storage object  with name (.*) is present under application local storage$")
	public void check_JainrainCaptureProfileData_ObjectExits(String localStoreObjName) throws ParseException {
		Assert.assertTrue(sso.ifLocalStorageObjectExists(localStoreObjName));
	}

	@Then("^user parse key with name (.*) value from local storage object$")
	public void parseUccLastUpdateDateValue(String key) {
		sso.parseUccLastUpdateDateValue(key);
	}

	@And("^user validates the value of key with name uccLastUpdateDate matches the time user was created$")
	public void validate_uccLastUpdatedDate() {
		Assert.assertTrue(sso.validate_uccLastUpdateDate());
	}

	@And("^user validates uccLastUpdatedDate value in Akamai$")
	public void uccLastUpdatedDate_inAkamai() throws java.text.ParseException {
		file = "registration_qualtoIC_RegisteredUser.json";
		user_email = jsonUtils.getFromJSON(file, "email").toString();
		end_ptAk = AICGETSteps.setEndPointEmail(user_email);
		sysRespAk = AICGETSteps.getUserFromAkamai(end_ptAk).extract().response();
		String uuid = sysRespAk.jsonPath().getString("uuid").toString();
		end_ptAk = AICGETSteps.setEndpointUserID(uuid);
		sysRespAk = AICGETSteps.getUserFromAkamai(end_ptAk).extract().response();
		Assert.assertTrue(sso.validate_uccLastUpdateDate_InAkamai(sysRespAk));
	}

	@When("^user launches catalyst JL site in the same window$")
	public void navigateToCJHomePage() throws Throwable {
		String cjUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("catalyst.base.url");
		commonFunc.Launch_URL(cjUrl);
	}

	@When("^user delete cookie with name (.*) from application cookies$")
	public void deleteConnectSidCookie(String cookieName) {
		commonFunc.deleteCookieWithName(cookieName);

	}

	@And("^user refreshes my account page$")
	public void refreshMyAccountPage() throws Throwable {
		commonFunc.Launch_URL(baseUrl);
	}

	@And("user parse sso auth token url on the basis of Environment")
	public void parseSSOAuthTokenUrl() {
		authTokenUrl = sso.parseSSOAuthTokenUrl();
	}

	@When("user launches the authToken url")
	public void launchSSOAuthTokenUrl_OnUI() throws Throwable {
		commonFunc.Launch_URL(authTokenUrl);
	}

	@And("user is automatically signed in")
	public void automaticSignIn() {
		Assert.assertTrue(sso.automaticSignIn());
	}

}
