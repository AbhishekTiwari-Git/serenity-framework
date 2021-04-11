package ucc.cr.steps.catalyst;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Logger;

import ucc.cr.pages.catalyst.ui.CRConnectPage;
import ucc.cr.pages.catalyst.ui.PDFPremiumRegPage;
import ucc.cr.pages.catalyst.ui.SignInPage;
import ucc.i.method.aic.AICGET;
import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;
import io.cucumber.datatable.DataTable;

public class PDFPremiumSteps {

	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	public static Response sysResp = null;
	static String end_pt = null;
	private String user_email;

	@Managed
	WebDriver driver;

	@Steps
	CommonFunc commonFunc;

	@Steps
	PDFPremiumRegPage pdfPremiumRegPage;

	@Steps
	AICGET AICGETSteps;

	@Steps
	CRConnectPage connectEletter;

	@Given("^user launches pdf premium store site URL$")
	public void iLaunchTheApplicationURL() throws Throwable {
		pdfPremiumRegPage.LaunchUrl();
	}

	@When("^user enter email address (.*) and click Continue button$")
	public void iEnterEmailAddressClickCountinueButton(String email) throws Exception {
		user_email = tUtil.AppendTimestamp(email);
		pdfPremiumRegPage.enter_email_click_button(user_email);
	}

	@Then("^user enter firstName, lastName, (.*), (.*), (.*), (.*), (.*)$")
	public void iEnterFirstNameLastNameSuffixRolePlaceOrgNameCountry(String suffix, String role, String place,
			String orgName, String country) {
		pdfPremiumRegPage.fill_user_data(suffix, role, place, orgName, country);
	}

	@And("^click button Download Now$")
	public void clickButtonDownloadNow() {
		pdfPremiumRegPage.click_download();
	}

	@Then("^I see text (.*) on page$")
	public void iSeeTextMessageOnPage(String text) {
		assertEquals(text, pdfPremiumRegPage.get_text_on_page());
	}

	@When("^I click modal link$")
	public void iClickModalLink() {
		pdfPremiumRegPage.click_modal();
	}

	@Then("^I click Sign Up$")
	public void iClickSignUp() {
		pdfPremiumRegPage.click_signup();
	}

	@And("^Modal container is visible$")
	public void modalContainerIsVisible() {
		assertTrue(pdfPremiumRegPage.is_modal_visible());
	}

	@When("^I click embedded link$")
	public void iClickEmbeddedLink() {
		pdfPremiumRegPage.click_embedded();
	}

	@Then("^I see email address in input field$")
	public void iSeeEmailAddressInInputField() {
		String email_input = pdfPremiumRegPage.get_email_input_text();
		assertEquals(user_email, email_input);
	}

	@When("^I click Not you link$")
	public void iClickNotYouLink() {
		pdfPremiumRegPage.click_notyou();
	}

	@Then("^Cookies should cleaned and is signed-out$")
	public void cookiesShouldCleanedAndIsSignedOut() throws ParseException {
		assertEquals(commonFunc.getSimpleLocalStorageObjectValue("mmsAuthState"), "anonymous");
		assertEquals(commonFunc.getSimpleLocalStorageObjectValue("akamaiProfileData"), null);
	}

	@And("^user validate soi selection (.*) and visibility (.*)$")
	public void checkSOIVisibility(boolean soiCheckedStatus, boolean soiVisibility) {
		Assert.assertEquals(true, pdfPremiumRegPage.checkSOIVisibility(soiCheckedStatus, soiVisibility));
	}

	@And("^user validate connect selection (.*) and visibility (.*)$")
	public void checkConnectVisibility(boolean connCheckedStatus, boolean connectVisibility) {
		Assert.assertEquals(true, pdfPremiumRegPage.checkConnectVisibility(connCheckedStatus, connectVisibility));
	}

	@When("^user select soi (.*) checkbox and connect (.*) checkbox$")
	public void selectSoi_ConnectCheckbox(boolean soiStatus, boolean connectStatus) throws InterruptedException {
		pdfPremiumRegPage.selectSoi_ConnectCheckBox(soiStatus, connectStatus);
	}

	@When("^user click on continue button$")
	public void clickContinueBtn() {
		pdfPremiumRegPage.clickContinueBtn();
	}

	@And("^user validates preferences in Akamai on basis of JSON file (.*)$")
	public void validateEmailPrefrencesAkamai(String file) {
		end_pt = AICGETSteps.setEndPointEmail(jsonUtils.getFromJSON(file, "email"));
		sysResp = AICGETSteps.getUserFromAkamai(end_pt).extract().response();
		Assert.assertEquals(true, pdfPremiumRegPage.validatePreferencesAkamai(sysResp));
	}

	@Then("^user email address should be prepopulated on basis of JSON file (.*)$")
	public void emailPrepopulation(String file) {
		Assert.assertEquals(true, connectEletter.validatePrepopulatedEmail(jsonUtils.getFromJSON(file, "email")));
	}

	@When("^user enters existing email address as per JSON file (.*)")
	public void existingSignIn(String file) throws Exception {
		pdfPremiumRegPage.enter_email_click_button(jsonUtils.getFromJSON(file, "email"));
	}

	@When("^user launches the site (.*)$")
	public void launchUrl(String url) throws Throwable {
		commonFunc.Launch_URL(url);
	}

	@When("^user click on Download Pdf link$")
	public void clickDownloadPdf() {
		pdfPremiumRegPage.clickDownloadPdf();
	}

	@Then("^user is able to view downloaded Pdf having text (.*)$")
	public void validateLaunchedPdf(String expectedText) throws Throwable {
		Assert.assertEquals(true, pdfPremiumRegPage.validatePDfUrl(commonFunc.getCurrentBrowserUrl(), expectedText));
	}
	
	@Then("^user is switch to second window and able to view downloaded Pdf having text (.*)$")
	public void validateLaunchedPdfInSecondWindow(String expectedText) throws Throwable {
		commonFunc.switchSecondTab();
		Assert.assertEquals(true, pdfPremiumRegPage.validatePDfUrl(commonFunc.getCurrentBrowserUrl(), expectedText));
	}


}
