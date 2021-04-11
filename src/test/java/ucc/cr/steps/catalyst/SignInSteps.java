package ucc.cr.steps.catalyst;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.logging.LogEntry;

import ucc.cr.pages.catalyst.ui.SignInPage;
import ucc.i.method.accountexp.AccountExpPOST;
import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignInSteps {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String autoEmail = EnvironmentSpecificConfiguration.from(env_var).getProperty("autoEmail");
	public static Response expResp = null;
	static String end_pt = null;
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	public static Map<String, String> kmap = new HashMap<String, String>();
	static String email = null;

	private String file = "flow_reg_cust_CATALYST.json";
	private String regFile = "registration_qualtoIC_RegisteredUser.json";

	
	
	@Steps
	AccountExpPOST AccExpPOSTSteps;

	@Steps
	SignInPage signInPage;

	@Steps
	CommonFunc commonFunc;

	@Given("Create a test customer with context CATALYST")
	public void createATestCustomerWithContextCATALYST() throws Exception {

		String fName_value = tUtil.AppendTimestamp("firstName");
		String lName_value = tUtil.AppendTimestamp("lastName");
		email = tUtil.AppendTimestamp(autoEmail);

		jsonUtils.update_JSONValue(file, "$.email", email);
		jsonUtils.update_JSONValue(file, "$.firstName", fName_value);
		jsonUtils.update_JSONValue(file, "$.lastName", lName_value);

		end_pt = AccExpPOSTSteps.setEndpointRegisterForEvent();
		expResp = AccExpPOSTSteps.post(file, end_pt).extract().response();
	}

	@Then("User enters own email")
	public void userEntersOwnEmail() {
		commonFunc.waitForLoadPage();
		email = jsonUtils.getFromJSON(file, "email");
		signInPage.inputEmail(email);

	}

	@Then("User enters own password")
	public void userEntersOwnPassword() {
		String password = jsonUtils.getFromJSON(file, "password");
		signInPage.inputPassword(password);
	}

	@And("^registered user sign in on catalyst site$")
	public void regSignIn_Catalyst() {
		commonFunc.waitForLoadPage();
		email = jsonUtils.getFromJSON(regFile, "email");
		String password = jsonUtils.getFromJSON(regFile, "password");
		signInPage.inputEmail(email);
		signInPage.inputPassword(password);
	}

	@When("^user check if object with name (.*) is present under application local storage$")
	public void check_ObjectExits(String localStoreObjName) throws ParseException {
		Assert.assertTrue(signInPage.ifLocalStorageObjectExists(localStoreObjName));
	}

	@Then("^user parse value from local storage object (.*)$")
	public void parseLocalStorageObjectValue(String localStorageObjectName) throws ParseException {
		signInPage.parseLocalStorageObjectValue(localStorageObjectName);
	}

	@And("^user validate onLogin success event callback expecting value (.*)$")
	public void validateOnLoginSuccess_EventCallback(String authStateEventValue) {
		Assert.assertEquals(true, signInPage.validate_EventCallback(authStateEventValue));
	}

	@And("^user validate onLogout success event callback expecting value (.*)$")
	public void validateOnLogoutSuccess_EventCallback(String authStateEventValue) {
		Assert.assertEquals(true, signInPage.validate_EventCallback(authStateEventValue));
	}

	@And("^user submit the sign in$")
	public void submitSignIn() {
		signInPage.submitSignIn();
	}

	@And("User clicks sign in")
	public void userClicksSignIn() {
		signInPage.clickSignIn();
	}

	@When("^user click on sign in link in the header on catalyst site$")
	public void clickSignInLinkInCatalystHeader() {
		signInPage.clickSignInLinkInCatalystHeader();
	}

	@When("^user click on sign in link in the header on myAccount site$")
	public void clickSignInLinkInMyaccountHeader() {
		signInPage.clickSignInLinkInMyAccountHeader();
	}

	@Then("User clicks sign out")
	public void userClicksSignOut() throws InterruptedException {
		signInPage.clickSignOut();
	}

	@Then("Header link sign in is available")
	public void linkSignOutInHeaderAvailable() {
		commonFunc.waitForLoadPage();
		Boolean isAvailbleSing = signInPage.headerSingInAvailable();
		Assert.assertTrue(isAvailbleSing);
	}

	@And("^user check rememberMe (.*)$")
	public void checkUncheckRememberMe(String rememberMe) {
		signInPage.checkUncheckRememberMe(rememberMe);
	}

	@And("^on my account sign in page user check (.*)$")
	public void checkUncheckRememberMe_MyAccount(String rememberMe) {
		signInPage.checkUncheckRememberMe_MyAccount(rememberMe);
	}

	@Then("^user is displayed as signed in the header$")
	public void userDisplayedSignedIn() {
		Assert.assertEquals(true, signInPage.userDisplayedSignedIn());
	}

	@Then("User profile name is not available")
	public void userProfileNameIsNotAvailable() {
		commonFunc.waitForLoadPage();
		// Boolean isAvailbleSing = signInPage.headerSingInAvailable();
		String userProfileName = signInPage.getUserProfileName();
		Assert.assertEquals(userProfileName, "Sign in");
	}

	@And("^user validates if cookie (.*) is not present$")
	public void validateCookie(String cookieName) {
		Assert.assertEquals(false, commonFunc.checkIfCookiePresent(cookieName));
	}
}
