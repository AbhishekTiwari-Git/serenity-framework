package ucc.cr.steps.catalyst;

import org.junit.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import ucc.cr.pages.catalyst.ui.CRRememberMePage;
import ucc.pages.ui.CommonFunc;
import ucc.utils.TestUtils;

public class CRRememberMeSteps {

	CommonFunc commonFunc = new CommonFunc();
	TestUtils tUtil = new TestUtils();

	public static Response sysRespAk, sysRespKi, sysRespLt = null;
	public static ValidatableResponse resAk = null;
	public static String actualExpiry = null;

	@Steps
	CRRememberMePage rememberMe;

	@And("^user save current session along with cookies$")
	public void fetch_Store_Cookies() {
		commonFunc.saveBrowserSession_Cookies();
	}

	@And("^user add previous session cookies to current new session$")
	public void launchBrowser_RememberMeCookie() {
		commonFunc.addSavedSessionCookiesToBrowser();
	}

	@When("^user fetches remember me cookie (.*) expiry date$")
	public void fetchRememberMeCookie_Expiry(String cookieName) {
		actualExpiry = commonFunc.getCookieExpiryDate(cookieName, "yyyy-MM-dd", "UTC");
	}

	@When("^user fetches remember me unchecked cookie (.*) expiry date$")
	public void fetchRememberMeCookieUnchecked_Expiry(String cookieName) {
		actualExpiry = commonFunc.getSessionCookieExpiry(cookieName);
	}

	@Then("^user validate cookie expiry date when remember me is checked$")
	public void validateCookieExpiryDate_remChecked() {
		Assert.assertEquals(true, rememberMe.validateCookieExpiryDate_RememberMeChecked(actualExpiry));

	}

	@Then("^user validate cookie expiry date when remember me is unchecked$")
	public void validateCookieExpiryDate__remUnChecked() {
		Assert.assertEquals(true, rememberMe.validateCookieExpiryDate_RememberMeUnChecked(actualExpiry));
	}

	@And("^user closes the cookied browser$")
	public void closeBrowser() {
		commonFunc.DriverClose();
	}
}
