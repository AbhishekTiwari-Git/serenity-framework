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
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.jcraft.jsch.Logger;

import ucc.com.steps.RegisteredOrderSteps;
import ucc.cr.pages.catalyst.ui.CRWidgetsPage;
import ucc.cr.pages.catalyst.ui.CreateAccount;
import ucc.cr.pages.catalyst.ui.MyAccountICProfile;
import ucc.cr.pages.catalyst.ui.utils.CRWidgetsPageHelper;
import ucc.i.method.aic.AICGET;
import ucc.i.method.kinesys.KinesysGET;
import ucc.i.method.literatum.LiteratumGET;
import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.RestUtil;
import ucc.utils.TestUtils;

public class CRWidgetsSteps {

	static String end_ptAk, end_ptKi, end_ptLt = null;
	static String user_email, pass, file, url;
	static String altId, qualCouncil = "null";
	static String ucid, insightsMember = "null";
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String baseUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("widgets.base.url");
	static String AccoridanUrl, registerUrl, promoRegisteration, pdfUrl, promoPdfPremium, eventUrl;

	CommonFunc commonFunc = new CommonFunc();
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();

	public static Response sysRespAk, sysRespKi, sysRespLt = null;
	public static ValidatableResponse resAk = null;

	@Managed
	CRWidgetsPage widgets;

	@Managed
	WebDriver driver;

	@Steps
	CRWidgetsPageHelper widgetHelper;

	@Steps
	CreateAccount CreateAcct;

	@Steps
	MyAccountICProfile myAccountICProfile;

	@Steps
	RegisteredOrderSteps regOrder;

	@Steps
	AICGET AICGETSteps;

	@Steps
	KinesysGET kinesysGETSteps;

	@Steps
	LiteratumGET LiteratumGETSteps;

	@When("^user launches the css widgets url$")
	public void launchWidget_Url() throws Throwable {
		commonFunc.Launch_URL(baseUrl);
	}
	
	@And("^User delete browser cookies$")
	public void deleteAllCookies() {
		commonFunc.clearCookies();
	}

	@When("^user click on create account link on css site$")
	public void launchCreateAccountForm_cssSite() {
		widgets.launchCreateAccount();
	}
	
	@When("^user click on create account link on myaccount site$")
	public void launchCreateAccountForm_MyAccountSite() {
		widgets.launchCreateAccount_MyAccount();
	}

	@And("^user set embedded Accordian and check if promoCode (.*) to be included (.*) in the url$")
	public void setPromoCode_InUrl(String promoCode, String promoIncluded) {
		AccoridanUrl = widgets.setPromoCode(promoCode, promoIncluded);
	}
	
	@And("^user set embedded regular register and check if promoCode (.*) to be included (.*) in the url$")
	public void setPromoCode_Register_InUrl(String promoCode, String promoIncluded) {
		registerUrl = widgets.setPromoCode_register(promoCode, promoIncluded);
	}

	@And("^user set modal Accoridan url$")
	public void setModalAccordianUrl() {
		AccoridanUrl = widgets.setModalAccoridanUrl();
	}
	
	@And("^user set create account regular modal url$")
	public void setModalRegCreateAccountUrl() {
		registerUrl = widgets.setModalRegCreateAccountUrl();
	}


	@When("^user launches the accordion style form on css site$")
	public void launch_embeddedAccordianForm() throws Throwable {
		commonFunc.Launch_URL(baseUrl + AccoridanUrl);
	}
	
	@When("^user launches the regular create account form on css site$")
	public void launch_embeddedRegularForm() throws Throwable {
		commonFunc.Launch_URL(baseUrl + registerUrl);
	}

	@Then("^user is able to view Accordian style registration form$")
	public void checkAccordianForm() {
		Assert.assertTrue(widgets.checkIf_AccordianForm_Present());
	}
	
	@Then("^user is able to view regular style registration form$")
	public void checkRegCreateAccountForm() {
		Assert.assertTrue(widgets.checkIf_RegularCreateAccountForm_Present());
	}

	@And("^user navigates to my account page via manage alert link on thankyou pop up or my account link on congratulations page if icNotJoined(.*) and qual (.*)$")
	public void navigate_myAccount(String icJoined, String qual) {
		widgets.navigateToMyAccount(icJoined, qual);
	}

	@And("^user set value of modal promo code fetched from event call on CSS Site along with default value (.*)$")
	public void setPromoCode_FromEventCall_cssSite(String defaultPromo) {
		promoRegisteration = widgets.setPromoCode_FromEventCall_cssSite(defaultPromo);
	}

	
	@And("^user set value of modal promo code fetched from event call on Main Site along with default value (.*)$")
	public void setPromoCode_FromEventCall_mainSite(String defaultPromo) {
		promoRegisteration = widgets.setPromoCode_FromEventCall_mainSite(defaultPromo);
	}
	
	@And("^user set default value of modal promo code on Catalyst Site (.*)$")
	public void setPromoCode_FromEventCall_catalystSite(String defaultPromo) {
		promoRegisteration = defaultPromo;
	}


	@And("^user set value of embedded promo code (.*) on basis if its included (.*) along with default value (.*)$")
	public void fetchPromoCode_FromUrl(String promoCode, String included, String defaultPromo) {
		promoRegisteration = widgets.setUrlPromoCode(promoCode, included, defaultPromo);
		tUtil.putToSession("PromoValueExpected", promoRegisteration);
	}
	
	@When("^user validates downstream process completion as per qualification (.*)$")
	public void validateDownStream_Process(boolean qualification) {
		Assert.assertEquals(true, widgets.validateDownStream_Process(qualification));
	}

	@When("^user check gdpr messages on registration form using data:$")
	public void validateGdpr_message_Accordian(DataTable dt) {
		Assert.assertEquals(true, widgets.validateGdpr(dt));
	}

	@And("^user check SOI Connect Remember me optin behavior as per data:$")
	public void validateConnectSOI_RememberMe(DataTable dt) {
		Assert.assertEquals(true, widgets.validateSOIConnect_RememberMe(dt));
	}

	@And("^user refreshes the page$")
	public void refreshMyAccountPage() throws Throwable {
		commonFunc.refreshBrowser();
	}

	@And("^user updates email for backend validation of registeration Join IC data$")
	public void updateEmail() throws Throwable {
		user_email = regOrder.kmap.get("email");
		jsonUtils.update_JSONValue("flow_reg_cust_EMAIL.json", "email", user_email);
	}

	@And("^user select beds (.*), sites (.*), physician (.*), questions for selective place (.*)$")
	public void selectQuestions_sites_phy_beds(String beds, String sites, String phy, String place) {
		widgets.selectQuestion_sites_phy_beds(beds, sites, phy, place);
	}

	@And("^user check backend values in Akamai using data:$")
	public void validateRegisterationDetails_Akamai(DataTable dt) {
		user_email = regOrder.kmap.get("email");
		end_ptAk = AICGETSteps.setEndPointEmail(user_email);
		sysRespAk = AICGETSteps.getUserFromAkamai(end_ptAk).extract().response();
		Assert.assertEquals(true, widgetHelper.akamaiValidations(sysRespAk, dt, promoRegisteration));
		altId = sysRespAk.jsonPath().getString("uuid");
	}

	@And("^user on basis of membership (.*) check backend values in Kinesis using data:$")
	public void validateRegisterationDetails_kinesis(String member, DataTable dt) throws Throwable {
		url = kinesysGETSteps.kinesys_url;
		RestUtil kinesys = new RestUtil(url);
		end_ptKi = kinesysGETSteps.setEndpoint(altId);
		sysRespKi = kinesysGETSteps.getPanelist(end_ptKi).extract().response();
		Assert.assertEquals(true, widgetHelper.kinesisValidations(member, sysRespKi, dt));
	}

	@And("^user check backend values in Literatum using data:$")
	public void validateTagsInLiteratum(DataTable dt) throws Exception {
		end_ptLt = LiteratumGETSteps.setEndpointIdentity(altId);
		sysRespLt = LiteratumGETSteps.get(end_ptLt).extract().response();
		Assert.assertEquals(true, widgets.validateQualificationMemberLogic_Literatum(dt, sysRespLt));
		Assert.assertEquals(true, widgetHelper.literatumValidations(sysRespLt, dt));
	}

	@And("^user validate fields (.*), (.*), (.*), (.*), (.*), (.*), (.*) error messages on accordion style registration form expecting data:$")
	public void validateFieldsOn_AccordionForm(String suffix, String role, String place, String orgName, String Country,
			String fname, String lname, DataTable dt) throws InterruptedException {
		Assert.assertEquals(true,
				widgets.validateFiedlsOn_AccordionForm(suffix, role, place, orgName, Country, fname, lname, dt));
	}
	
	@And("^user validate fields (.*), (.*), (.*), (.*), (.*), (.*), (.*) error messages on regular create account form expecting data:$")
	public void validateFieldsOn_CreateAccountForm(String suffix, String role, String place, String orgName, String Country,
			String fname, String lname, DataTable dt) throws InterruptedException {
		Assert.assertEquals(true,
				widgets.validateFiedlsOn_CreateAccountForm(suffix, role, place, orgName, Country, fname, lname, dt));
	}

	@And("^user validate accordion style registration form Visual UI elements (.*), (.*), (.*), (.*), (.*), (.*), (.*) step wise$")
	public void validateVisualUIElements(String suffix, String role, String place, String orgName, String Country,
			String fname, String lname) throws InterruptedException {
		Assert.assertEquals(true, widgets.validateVisualUIElements_StepOne(fname, lname, Country));
		Assert.assertEquals(true, widgets.validateVisualUIElements_StepTwo(suffix, role, place, orgName));
		Assert.assertEquals(true, widgets.validateVisualUIElements_StepThree());
	}

	@When("^user enters non cookied lead user or non signed in email with values (.*), (.*), (.*), (.*), (.*), (.*), (.*), (.*) and register$")
	public void nonCookied_nonSignedIn_Register(String suffix, String specialty, String role, String place,
			String orgName, String Country, String fname, String lname) throws InterruptedException {
		widgets.nonCookied_nonSignedIn_Register(suffix, specialty, role, place, orgName, Country, fname, lname);
	}
	
	@When("^user enters non cookied lead user or non signed in email with values (.*), (.*), (.*), (.*), (.*), (.*), (.*), (.*) and register on create account form$")
	public void nonCookied_nonSignedIn_Register_LegacyFlow(String suffix, String specialty, String role, String place,
			String orgName, String Country, String fname, String lname) throws InterruptedException {
		widgets.nonCookied_nonSignedIn_Register_LegacyFlow(suffix, specialty, role, place, orgName, Country, fname, lname);
	}

	@Then("^application should display validation message on email (.*)$")
	public void validateEmailMessage(String expectedMessage) {
		Assert.assertEquals(expectedMessage, widgets.validateEmailMessage());
	}

	@When("^user check prepopulated fields and register using values (.*), (.*), (.*), (.*), (.*), (.*), (.*), (.*)$")
	public void prepopulated_cookied_SignedIn_Register(String suffix, String speciality, String role, String place, String orgName, String country, String fname, String lname) {
		Assert.assertEquals(true, widgets.prepopulated_cookied_SignedIn_Register(suffix, speciality, role, place, orgName, country, fname, lname));
	}
	
	@When("^user check prepopulated fields on legacyFlow and register using values (.*), (.*), (.*), (.*), (.*), (.*), (.*), (.*)$")
	public void prepopulated_cookied_SignedIn_Register_LegacyFlow(String suffix, String speciality, String role, String place, String orgName, String country, String fname, String lname) {
		Assert.assertEquals(true, widgets.prepopulated_cookied_SignedIn_Register_LegacyFlow(suffix, speciality, role, place, orgName, country, fname, lname));
	}
	
	@And("^user set embedded pdf premium and check if promoCode (.*) to be included (.*) in the url$")
	public void setPromoCode_InPDFUrl(String promoCode, String promoIncluded) {
		pdfUrl = widgets.setPromoCode_pdfPremium(promoCode, promoIncluded);
	}

	@When("^user launches the pdf premium form on css site$")
	public void launch_PDFForm() throws Throwable {
		commonFunc.Launch_URL(baseUrl + pdfUrl);
	}
	
	@And("^user set pdf premium sign up modal url$")
	public void setModalPdfUrl() {
		pdfUrl = widgets.setModalPdfUrl();
	}
	
	@And("^user set event sign up modal url$")
	public void setModalEventSignUpUrl() {
		eventUrl = widgets.setModalEventSignUpUrl();
	}
	
	@When("^user launches the event sign up form on css site$")
	public void launch_EventSignUpForm() throws Throwable {
		commonFunc.Launch_URL(baseUrl + eventUrl);
	}

	       
	@And("^user set value of pdf premium modal promo code fetched from event call on CSS Site along with default value (.*)$")
	public void setPdfPremiumEvent_PromoCode(String defaultPromo) {
		promoPdfPremium = widgets.setPromoCode_FromPdfEventCall_cssSite(defaultPromo);
		tUtil.putToSession("PromoValueExpected", promoPdfPremium);
	}
	
	@And("^user validate pdf premium promo code in Akamai for embedded or modal form as per JSON (.*)$")
	public void validatePdfPromoAkamai(String file) {
		String user_email = jsonUtils.getFromJSON(file, "email");
		end_ptAk = AICGETSteps.setEndPointEmail(user_email);
		sysRespAk = AICGETSteps.getUserFromAkamai(end_ptAk).extract().response();
		String expectedPromo = (String) tUtil.getFromSession("PromoValueExpected");
		widgets.printPromoValues(expectedPromo, sysRespAk.jsonPath().getString("source.source"));
		Assert.assertTrue(sysRespAk.jsonPath().getString("source.source").contains(expectedPromo));
	}

}
