package ucc.cr.steps.catalyst;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ucc.cr.pages.catalyst.ui.JoinIC;
import ucc.cr.pages.catalyst.ui.MyAccountPage;
import ucc.cr.pages.catalyst.ui.RegInsighCouncilPage;
import ucc.cr.pages.catalyst.ui.utils.MyAccountPageHelper;
import ucc.i.method.aic.AICGET;
import ucc.i.method.kinesys.KinesysGET;
import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.RestUtil;
import ucc.utils.TestUtils;
import ucc.utils.CucumberUtils.CucumberUtils;
import io.cucumber.datatable.DataTable;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.SplittableRandom;

public class MyAccountSteps {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String baseUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("myaccount.base.url");
	static String autoEmail = EnvironmentSpecificConfiguration.from(env_var).getProperty("autoEmail");
	static String catalystUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("catalyst.base.url");

	CommonFunc commonFunc = new CommonFunc();
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();

	public static Response sysResp, sysRespKi = null;
	static String end_pt, end_ptKi = null;
	static String file, user_email, pass, ICMember, url, altId;
	static String audienceTypeAkamai, pCategoryAkamai, qualifiedCounsilAkamai, icMemberAkamai;
	public static HashMap<Integer, String> akamaiAudienceSegment = new HashMap<Integer, String>();
	public static HashMap<Integer, String> akamaiProfessionalCategory = new HashMap<Integer, String>();
	public static HashMap<Integer, String> akamaiQualifiedForCounsil = new HashMap<Integer, String>();
	public static HashMap<Integer, String> akamaiICMember = new HashMap<Integer, String>();
	private String regFile = "registration_qualtoIC_RegisteredUser.json";
	private String fileRegCust = "flow_reg_cust_CATALYST.json";
	private String updateUserWithNewlyAddedFields = "AIC_Customer.json";
	public static Map<String, String> kMap = new HashMap<String, String>();
	private static final Logger LOGGER = LoggerFactory.getLogger(MyAccountPage.class);

	@Steps
	MyAccountPage myAccountPage;

	@Steps
	RegInsighCouncilPage iC;

	@Steps
	MyAccountPageHelper helper;

	@Steps
	KinesysGET kinesysGETSteps;

	@Steps
	AICGET aicGETSteps;

	@Managed
	WebDriver driver;

	@Given("^user launches my account url$")
	public void userLaunchMyAccount() throws Throwable {
		myAccountPage.myAccountUrl();
	}

	@When("^IC qualified user enters email and password on my account form$")
	public void validLogin() throws InterruptedException {
		ICMember = "null";
		file = "registration_qualtoIC_RegisteredUser.json";
		user_email = jsonUtils.getFromJSON(file, "email");
		pass = jsonUtils.getFromJSON(file, "password");
		myAccountPage.login(user_email, pass);
	}

	@When("^Not qualified user sign in on my account form$")
	public void notQualifiedUser_Login() throws InterruptedException {
		ICMember = "null";
		file = "flow_reg_non_qual_cust_EMAIL.json";
		user_email = jsonUtils.getFromJSON(file, "email");
		pass = jsonUtils.getFromJSON(file, "password");
		myAccountPage.login(user_email, pass);
	}

	@When("^IC joined user enters email and password on my account form$")
	public void validLoginICJoined() throws InterruptedException {
		ICMember = "true";
		file = "registration_qualtoIC_RegisteredUser.json";
		user_email = jsonUtils.getFromJSON(file, "email");
		pass = jsonUtils.getFromJSON(file, "password");
		myAccountPage.login(user_email, pass);
	}

	@Then("^user is signed in to my account page$")
	public void myAccountPage() {
		Assert.assertTrue(myAccountPage.userAtMyAccountPage());
	}

	@And("^personal information is displayed$")
	public void personalInformation() {
		myAccountPage.userPersonalInfomration();
	}

	@When("^audience type clinician user updates (.*), (.*) on personal information with expected audience segment and other values (.*), (.*), (.*), (.*) being displayed$")
	public void audienceTypeClinician(String role, String place, String asegment, String profCategory,
			String qualifiedCouncil, String suffix) {
		myAccountPage.audienceTypeClinician(user_email, role, place, asegment, profCategory, qualifiedCouncil, ICMember,
				suffix);
		commonFunc.waitForLoadPage();
		Assert.assertEquals(myAccountPage.isSaveChangesPwdSuccessMessage(), true);
		// ***********Fetching values from Akamai
		end_pt = aicGETSteps.setEndPointEmail(user_email);
		sysResp = aicGETSteps.getUserFromAkamai(end_pt).extract().response();
		audienceTypeAkamai = sysResp.jsonPath().getString("Catalyst.audienceSegment");
		pCategoryAkamai = sysResp.jsonPath().getString("professionalCategoryCode");
		qualifiedCounsilAkamai = sysResp.jsonPath().getString("Catalyst.qualifiedForCouncil");
		icMemberAkamai = sysResp.jsonPath().getString("Catalyst.insightsCouncilMember");
		// *************Setting up Values fetched from Akamai in a Map
		akamaiAudienceSegment.put(1, audienceTypeAkamai);
		akamaiProfessionalCategory.put(1, pCategoryAkamai);
		akamaiQualifiedForCounsil.put(1, qualifiedCounsilAkamai);
		akamaiICMember.put(1, icMemberAkamai);
	}

	@Then("^changes are saved on my account and in Akamai for clinician user$")
	public void validateChanges() {
		myAccountPage.printMaps();
		// =======================Validating MAPS===================================
		assert (MyAccountPage.MyAccountAudienceSegment).equals(akamaiAudienceSegment);
		assert (MyAccountPage.MyAccountProfessionalCategory).equals(akamaiProfessionalCategory);
		assert (MyAccountPage.MyAccountQualifiedForCounsil).equals(akamaiQualifiedForCounsil);
		assert (MyAccountPage.MyAccountICMember).equals(akamaiICMember);
	}

	@When("^User try update password with incorrect data$")
	public void userTryUpdatePassword() {
		String oldPwd = jsonUtils.getFromJSON(fileRegCust, "password");

		myAccountPage.isEditPwdBtn();
		System.out.println(myAccountPage.isEditPwdBtn());
		myAccountPage.click_edit_pwd_btn();

		myAccountPage.inputOldPassword(oldPwd);
		myAccountPage.inputNewPassword("123456");
		myAccountPage.inputConfirmPassword("123456");
	}

	@Then("Error password message is visible")
	public void errorPasswordMessageIsVisible() {
		Assert.assertTrue(myAccountPage.newPwdErrorMsgIsVisible());
	}

	@When("User try update password with correct length")
	public void userTryUpdatePasswordWithCorrectLength() {
		String oldPwd = jsonUtils.getFromJSON(fileRegCust, "password");

		int lenPwd = new SplittableRandom().nextInt(8, 129);
		String newPwd = tUtil.generateRandomUcid(lenPwd);

		userClickEditPasswordButton();

		myAccountPage.inputOldPassword(oldPwd);
		myAccountPage.inputNewPassword(newPwd);
		myAccountPage.inputConfirmPassword(newPwd);
	}

	@Then("^User clicks button Save Changes$")
	public void userClicksButtonSaveChanges() {
		myAccountPage.saveChangesPwdClick();
	}

	@And("User sees Save Changes success message")
	public void userSeesSaveChangesSuccessMessage() {
		Assert.assertTrue(myAccountPage.isSaveChangesPwdSuccessMessage());
	}

	@Then("Error password messages are visible")
	public void errorPasswordMessagesAreVisible() {
		Assert.assertTrue(myAccountPage.newPwdErrorMsgIsVisible());
		Assert.assertTrue(myAccountPage.oldPwdErrorMsgIsVisible());
		Assert.assertTrue(myAccountPage.confirmErrorMsgIsVisible());
	}

	@When("User click edit password button")
	public void userClickEditPasswordButton() {
		myAccountPage.isEditPwdBtn();
		System.out.println(myAccountPage.isEditPwdBtn());
		myAccountPage.click_edit_pwd_btn();
	}

	@When("^Launch My Account Sign In page$")
	public void launchMyAccountSignInPage() throws Throwable {
		commonFunc.Launch_URL(baseUrl);
	}

	@And("user wait for page to load completely")
	public void waitForMyAccountPageLoad() {
		commonFunc.waitForLoadPage();
	}

	@When("^user launches my account site in same browser new tab$")
	public void launchMyAccount_NewTab() throws Throwable {
		commonFunc.launchNewBrowserTab_SameWindow();
		commonFunc.Launch_URL(baseUrl);
	}

	@And("^user switch back to previous tab of same browser$")
	public void switchToPreviousTab_SameBrowser() {
		commonFunc.switchToOriginalWindow();
	}

	@And("^user switch to second window of same browser$")
	public void switchToSecondWindow_SameBrowser() {
		commonFunc.switchToSecondWindow();
	}

	@And("^user update firstName (.*), last name (.*) and suffix (.*) in personal information$")
	public void updateFname_Lname(String fname, String lname, String suffixByValue) {
		myAccountPage.selectPerInfoEditBtn();
		myAccountPage.updateFn_Ln(fname, lname);
		myAccountPage.selectHiddenDropDownVal(suffixByValue, "Suffix");
		myAccountPage.saveChanges();
	}

	@And("^user firsName and lastName should not be updated$")
	public void fName_LName_NotUpdated() {
		file = "flow_reg_EMAIL.json";
		String fName = jsonUtils.getFromJSON(file, "firstName");
		String lName = jsonUtils.getFromJSON(file, "lastName");
		Assert.assertTrue(myAccountPage.validateFnameLname(fName, lName));
	}

	@And("^registered user sign in on myAccount site$")
	public void regSignIn_Catalyst() {
		commonFunc.waitForLoadPage();
		String email = jsonUtils.getFromJSON(regFile, "email");
		String password = jsonUtils.getFromJSON(regFile, "password");
		myAccountPage.inputEmail(email);
		myAccountPage.inputPassword(password);
	}

	@And("^user validate correct email is displayed under account information section$")
	public void validateEmail_AccountInformation() {
		file = "registration_qualtoIC_RegisteredUser.json";
		user_email = jsonUtils.getFromJSON(file, "email");
		Assert.assertTrue(myAccountPage.validateEmail_AccountInformation(user_email));
	}

	@And("^user validate details in personal information section$")
	public void validatePersonalInformation() {
		Map<String, String> kMap_exp = new HashMap<String, String>();
		Map<String, String> kMap_Actual = new HashMap<String, String>();
		kMap_exp = helper.setRegApiPersonalInfoDetailsInJsontoMap();
		kMap_Actual = helper.setpersonalInfo_ActualOnMyAccount();
		Assert.assertTrue(myAccountPage.validatePersonalInformation(kMap_exp, kMap_Actual));
	}

	@When("^user update email (.*) in account information$")
	public void updateEmail_AccInfo(String email) {
		user_email = tUtil.AppendTimestamp(email);
		myAccountPage.updateEmail_AccInfo(user_email);
	}

	@Then("^user validate email is updated to new email address$")
	public void validateEmail() {
		Assert.assertTrue(myAccountPage.validateUpdatedEmail(user_email));
	}

	@When("^user update myaccount personal information role, place, org, country, specialty using data:$")
	public void updateRle_PlcCtr_OrgSpec(DataTable dt) {
		kMap.clear();
		myAccountPage.selectPerInfoEditBtn();
		kMap = myAccountPage.updateRle_PlcCtr_OrgSpec(dt);
		myAccountPage.saveChanges();
	}

	@Then("^user validate updated information on my account page$")
	public void validateUpdateInformation() {
		Map<String, String> kMap_Actual = new HashMap<String, String>();
		kMap_Actual = helper.setpersonalInfo_ActualOnMyAccount();
		Assert.assertTrue(myAccountPage.validatePersonalInformation(kMap, kMap_Actual));

	}

	@And("^user validate my account personal information update in Akamai$")
	public void validatePersonalInfoAkamai() {
		end_pt = aicGETSteps.setEndPointEmail(user_email);
		sysResp = aicGETSteps.getUserFromAkamai(end_pt).extract().response();
		Map<String, String> akamaiExp = new HashMap<String, String>();
		akamaiExp = helper.setpersonalInfo_ActualOnMyAccount();
		Assert.assertTrue(myAccountPage.validatePersonalInformation(akamaiExp, helper.setAkamai(sysResp)));
		altId = sysResp.jsonPath().getString("uuid");
	}

	@And("^user validate my account personal information update in Kinesis$")
	public void validatePersonalInfoKinesis() throws URISyntaxException {
		url = kinesysGETSteps.kinesys_url;
		RestUtil kinesys = new RestUtil(url);
		end_ptKi = kinesysGETSteps.setEndpoint(altId);
		sysRespKi = kinesysGETSteps.getPanelist(end_ptKi).extract().response();
		Map<String, String> kinesisExp = new HashMap<String, String>();
		kinesisExp = helper.setpersonalInfo_ActualOnMyAccount();
		helper.setKinesis(sysRespKi);
		Assert.assertTrue(myAccountPage.validatePersonalInformation(kinesisExp, helper.setKinesis(sysRespKi)));
	}

	@When("^user try to update email in wrong format (.*)$")
	public void updateEmail_IncorrectFormat(String invalidEmail) {
		myAccountPage.updateEmail_AccInfo(invalidEmail);
	}

	@Then("^Email Validation (.*) message and (.*) should appear$")
	public void validateIncorrectEmail(String formatErrorMsg, String mainMsg) {
		Assert.assertTrue(myAccountPage.validateWrongEmailMsg(mainMsg, formatErrorMsg));
		myAccountPage.clickCancelEmailBtn();
	}

	@And("^user validate error messages for invalid new and confirm new password$")
	public void validateNewOldPasswordMessages() {
		Assert.assertTrue(myAccountPage.newPwdErrorMsgIsVisible());
		myAccountPage.clickCancelPwdBtn();
	}

	@When("^User try to update new password (.*) and (.*) with invalid old password (.*)$")
	public void updateOldPassword(String newPwd, String confirmPwd, String invalidOldPwd) {
		userClickEditPasswordButton();
		myAccountPage.inputOldPassword(invalidOldPwd);
		myAccountPage.inputNewPassword(newPwd);
		myAccountPage.inputConfirmPassword(confirmPwd);
		Assert.assertTrue(myAccountPage.confirmErrorMsgIsVisible());
		myAccountPage.inputConfirmPassword(newPwd);
		myAccountPage.saveChanges_pwd();
	}

	@Then("^validation message for old password is displayed (.*)$")
	public void validateOldPwdMessgae(String oldPwdErrorMsg) {
		Assert.assertTrue(myAccountPage.validateOldPwdErrorMsg(oldPwdErrorMsg));
		myAccountPage.clickCancelPwdBtn();
	}

	@When("^user update blank personal info using data:$")
	public void setBlankPersonalInf(DataTable dt) {
		myAccountPage.updateBlankValues(dt);
		myAccountPage.saveChanges();
	}

	@Then("^appropriate validation message appears on my account personal information:$")
	public void validateErrorMessage_PersonalInfo(DataTable dt) {
		Map<String, String> ExpErrorMap = new HashMap<String, String>();
		Map<String, String> actualErrorMap = new HashMap<String, String>();
		ExpErrorMap = CucumberUtils.convert(dt);
		actualErrorMap = helper.setPersonalInfo_Error();
		LOGGER.info("========Error Message Assertion Results========");
		LOGGER.info(ExpErrorMap.toString());
		LOGGER.info(actualErrorMap.toString());
		Assert.assertEquals(ExpErrorMap, actualErrorMap);
	}

	@When("^user update my account summary using AIC and login$")
	public void updateMyAccountUsingAkamai() throws InterruptedException {
		user_email = jsonUtils.getFromJSON(updateUserWithNewlyAddedFields, "email");
		pass = jsonUtils.getFromJSON(updateUserWithNewlyAddedFields, "password");
		myAccountPage.login(user_email, pass);
	}

	@And("^AIC updated data reflect correctly on My Account Summary Section$")
	public void validateMyAccountSummary() {
		Assert.assertEquals(true, myAccountPage.validateAICUpdates_OnMyAccount(updateUserWithNewlyAddedFields));
	}

	@When("^user update existing email (.*) in account information$")
	public void updateExistingEmail_AccInfo(String email) {
		myAccountPage.updateEmail_AccInfo(email);
	}

	@Then("^existing Email Validation (.*) message should appear$")
	public void existingEmailValidation(String exitingEmValidationMsg) {
		Assert.assertEquals(true, myAccountPage.existingEmailValidation(exitingEmValidationMsg));
		myAccountPage.clickCancelEmailBtn();
	}

	@And("^registered user joins insights council using data:$")
	public void joinIC(DataTable dt) throws Throwable {
		ICMember = "true";
		user_email = jsonUtils.getFromJSON(regFile, "email");
		pass = jsonUtils.getFromJSON(regFile, "password");
		commonFunc.Launch_URL(catalystUrl);
		commonFunc.waitForLoadPage();
		iC.hoverICLink();
		iC.selectApplyToJoin();
		commonFunc.switchToSecondWindow();
		commonFunc.waitForLoadPage();
		iC.enter_email_click_button(user_email);
		myAccountPage.submitPassAndSignIn(pass);
		myAccountPage.joinIC(dt);
	}
}
