package ucc.cr.pages.catalyst.ui;

import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;
import ucc.utils.CucumberUtils.CucumberUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CRWidgetsPage extends PageObject {

	CommonFunc commonFunc = new CommonFunc();
	CreateAccount CreateAcct = new CreateAccount();
	MyAccountICProfile icProf = new MyAccountICProfile();
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	private static final Logger LOGGER = LoggerFactory.getLogger(CRWidgetsPage.class);
	private static String accPromoCodeUrl = "/reg-accordion.html?promo=";
	private static String pdfPromoCodeUrl = "/pdfpremium.html?promo=";
	private static String accModalUrl = "/reg-accordion-modal.html";
	private static String pdfModalUrl = "/pdfpremiummodal.html";
	private static String eventSignUpModalUrl = "/event-modal.html";
	private static String regCreateAccModalUrl = "/register-modal.html";
	private static String regPromoCodeUrl = "/register.html?promo=";
	private static String nonPromoCodeUrl_reg = "/register.html";
	private static String nonPromoCodeUrl = "/reg-accordion.html";
	private static String nonPromoCodeUrl_Pdf = "/pdfpremium.html";
	private static String gdprFile = "gdprCountryCodeList.json";
	private static String kinesisCodesFile = "Kinesys_ICProfile.json";
	private static String actual, expected, eventPromoValue, specialty;
	private static boolean expec, not_exp, downStreamValue;

	Map<String, String> kMap_exp;
	Map<String, String> kMap_actual = new HashMap<String, String>();

	@FindBy(xpath = "//a[contains(text(),'Take me to my account')]")
	WebElementFacade takeMeToMyAccount;

	@FindBy(xpath = "//a[contains(text(),'MANAGE')]")
	WebElementFacade manageAlertLinkForDisqualified;

	@FindBy(xpath = "//a[@id='create-acct']")
	WebElementFacade eventPromo_cssSite;

	@FindBy(xpath = "//a[@id='event-signup']")
	WebElementFacade eventPromoPdf_cssSite;

	@FindBy(xpath = "//div[@class='header_top-bar_right']//div[@class='userLoginBar_create-account']/a")
	WebElementFacade eventPromo_mainSite;

	@FindBy(xpath = "//div[@class='userLoginBar']//span[contains(text(),'Create Account')]//ancestor::a")
	WebElementFacade eventPromo_catalystSite;

	@FindBy(xpath = "//div[@class='header_top-bar_right']//a[@title='Create Account']/span")
	WebElementFacade createAccountLink_myAccountSite;

	@FindBy(xpath = "//h1[contains(text(),'Thank you!')]")
	WebElementFacade thankYouModal;

	@FindBy(xpath = "//p[contains(text(),'Your account is created. Take a minute to review and update your alerts and communication preferences.')]")
	WebElementFacade thankYouModalMessage;

	@FindBy(xpath = "//button[contains(text(),'BACK TO SITE')]")
	WebElementFacade backToSiteButton;

	@FindBy(xpath = "//h1[contains(text(),'You qualify to join the')]")
	WebElementFacade qualifyToJoinMessage;

	@FindBy(xpath = "//*[starts-with(@id,'uccNoofbeds')]")
	public WebElementFacade uccNoOfbeds;

	@FindBy(xpath = "//*[starts-with(@id,'uccNoofSites')]")
	public WebElementFacade uccNoOfSites;

	@FindBy(xpath = "//*[starts-with(@id,'uccNoofPhysicans')]")
	public WebElementFacade uccNoOfPhysicans;

	@FindBy(xpath = "//input[contains(@placeholder, 'First Name')]//ancestor::div[@class='ucc-input-wrapper required']//following-sibling::div")
	public WebElementFacade fname_error;

	@FindBy(xpath = "//input[contains(@placeholder, 'Last Name')]//ancestor::div[@class='ucc-input-wrapper required']//following-sibling::div")
	public WebElementFacade lname_error;

	@FindBy(xpath = "//label[contains(text(),'Country/Region')]//ancestor::div[@class='ucc-input-wrapper required']//following-sibling::div")
	public WebElementFacade country_error;

	@FindBy(xpath = "//label[contains(text(),'Suffix')]//ancestor::div[@class='ucc-input-wrapper required']//following-sibling::div")
	public WebElementFacade suffix_error;

	@FindBy(xpath = "//label[contains(text(),'Role')]//ancestor::div[@class='ucc-input-wrapper required']//following-sibling::div")
	public WebElementFacade role_error;

	@FindBy(xpath = "//label[contains(text(),'Place')]//ancestor::div[@class='ucc-input-wrapper required']//following-sibling::div")
	public WebElementFacade place_error;

	@FindBy(xpath = "//label[contains(text(),'Name of Organization')]//ancestor::div[@class='ucc-input-wrapper required']//following-sibling::div")
	public WebElementFacade org_error;

	@FindBy(xpath = "//label[contains(text(),'Email Address')]//ancestor::div[@class='ucc-input-wrapper required']//following-sibling::div")
	public WebElementFacade email_error;

	@FindBy(xpath = "//div[contains(text(),'This email address is not available')]")
	public WebElementFacade existingEmail_error;

	@FindBy(xpath = "//input[contains(@placeholder,'Create Password')]//ancestor::div[@class='ucc-input-wrapper required']//following-sibling::div[2]")
	public WebElementFacade password_error;

	@FindBy(xpath = "//div[@class='step current']/span")
	public WebElementFacade step_current;

	@FindBy(xpath = "//div[contains(text(),'Personal Information')]//ancestor::div[@class='header']/div/span")
	public WebElementFacade personalInfostep_completed;

	@FindBy(xpath = "//div[contains(text(),'Professional Information')]//ancestor::div[@class='header']/div/span")
	public WebElementFacade professionalInfostep_completed;

	@FindBy(xpath = "//div[contains(text(),'Personal Information')]//ancestor::div[@class='header active']")
	public WebElementFacade personalInformation;

	@FindBy(xpath = "//div[contains(text(),'Professional Information')]//ancestor::div[@class='header active']")
	public WebElementFacade professionalInformation;

	@FindBy(xpath = "//div[contains(text(),'Account Details')]//ancestor::div[@class='header active']")
	public WebElementFacade accountInformation;

	@FindBy(xpath = "//h1[contains(text(),'Create Account')]")
	public WebElementFacade createAccountText;

	@FindBy(xpath = "//div[contains(@class, 'ucc-widget-accordion')]")
	public WebElementFacade accFormDisplay;

	@FindBy(xpath = "//div[contains(text(),'Personal')]/ancestor::div[contains(@class, 'header')]/following-sibling::div//button")
	public WebElementFacade continueButton1;

	@FindBy(xpath = "//div[contains(text(),'Professional')]/ancestor::div[contains(@class, 'header')]/following-sibling::div//button")
	public WebElementFacade continueButton2;

	@FindBy(xpath = "//*[starts-with(@id, 'uccEmail')]")
	public WebElementFacade enterEmail;

	@FindBy(xpath = "//*[starts-with(@id, 'uccPwd')]")
	public WebElementFacade enterPass;

	@FindBy(xpath = "//button[contains(text(), 'CREATE ACCOUNT')]")
	public WebElementFacade createAccountBtn;

	@FindBy(xpath = "//*[starts-with(@id, 'uccFirstName')]")
	public WebElementFacade enterFirstName;

	@FindBy(xpath = "//*[starts-with(@id, 'uccLastName')]")
	public WebElementFacade enterLastName;

	@FindBy(xpath = "//*[starts-with(@id, 'uccCountry')]")
	public WebElementFacade selectCountry;

	@FindBy(xpath = "//*[starts-with(@id, 'uccSuffix')]")
	public WebElementFacade selectSuffix;

	@FindBy(xpath = "//*[starts-with(@id, 'uccPlaceOfWork')]")
	public WebElementFacade selectPlace;

	@FindBy(xpath = "//*[starts-with(@id, 'uccRole')]")
	public WebElementFacade selectRole;

	@FindBy(xpath = "//*[starts-with(@id, 'uccNameOfOrg')]")
	public WebElementFacade enterOrg;

	@FindBy(xpath = "//*[starts-with(@id, 'uccSpecialty')]")
	public WebElementFacade enterSpecialty;

	@Step("User launches registeration form from widgets css site")
	public void launchCreateAccount() {
		eventPromo_cssSite.waitUntilClickable().click();
	}

	@Step("User launches registeration form from myaccount site")
	public void launchCreateAccount_MyAccount() {
		createAccountLink_myAccountSite.waitUntilClickable().click();
	}

	@Step("User validates if accordian form is present")
	public boolean checkIf_AccordianForm_Present() {
		boolean ifAccordianFormPresent = accFormDisplay.isDisplayed();
		return (ifAccordianFormPresent) ? true : false;
	}

	@Step("User validates if accordian form is present")
	public boolean checkIf_RegularCreateAccountForm_Present() {
		boolean ifRegCreateAccountFormPresent = createAccountText.isCurrentlyVisible();
		return (ifRegCreateAccountFormPresent) ? true : false;
	}

	@Step("user set modal accordian form url")
	public String setModalAccoridanUrl() {
		return accModalUrl;
	}

	@Step("user set modal pdf premium sign up url")
	public String setModalPdfUrl() {
		return pdfModalUrl;
	}

	@Step("user set event sign up modal url")
	public String setModalEventSignUpUrl() {
		return eventSignUpModalUrl;
	}

	public String setModalRegCreateAccountUrl() {
		return regCreateAccModalUrl;
	}

	@Step("Check if promo code included equal to yes/no in Data file and set url's accordingly for accordian style registeration")
	public String setPromoCode(String promoCode, String promoIncluded) {
		return (promoIncluded.equals("yes")) ? accPromoCodeUrl + promoCode : nonPromoCodeUrl;
	}

	@Step("Check if promo code included equal to yes/no in Data file and set url's accordingly for regular style registeration")
	public String setPromoCode_register(String promoCode, String promoIncluded) {
		return (promoIncluded.equals("yes")) ? regPromoCodeUrl + promoCode : nonPromoCodeUrl_reg;
	}

	@Step("check registeration promo code in Akamai")
	public String validatePromo_InAkamai(String promo_included, Response res) {
		return (res.jsonPath().getString("source.source").contains(promo_included)) ? promo_included : "Not Present";
	}

	@Step("Check insights council promo in Akamai")
	public String validateICPromo_InAkamai(String member, Response res, String defaultICPromo) {
		return (member.equals("true")) && (res.jsonPath().getString("source.source").contains(defaultICPromo))
				? defaultICPromo
				: "Not included";
	}

	@Step("Check Audience Segment value in Kinesis")
	public String validateAudienceSegment_Kinesis(String auSeg, Response res) {
		expected = jsonUtils.getFromJSON(kinesisCodesFile, "['audienceSegment']['" + auSeg + "']").toString();
		actual = res.jsonPath().getString("audienceSegment").toString();
		return (expected.equals(actual)) ? auSeg : "Invalid Audience Segment";
	}

	@Step("Check Professional Category in Kinesis")
	public String validateProfessionalCategory_Kinesis(String profCat, Response res) {
		expected = jsonUtils.getFromJSON(kinesisCodesFile, "['professionalcategory']['" + profCat + "']").toString();
		actual = res.jsonPath().getString("professionalCategory").toString();
		return (expected.equals(actual)) ? profCat : "Invalid Professional Category";
	}

	@Step("Check Suffix Value in Kinessi")
	public String validateSuffix_Kinesis(String suffix, Response res) {
		expected = jsonUtils.getFromJSON(kinesisCodesFile, "['clinicaldesignation']['" + suffix + "']").toString();
		actual = res.jsonPath().getString("clinicalDesignation").toString();
		return (expected.equals(actual)) ? suffix : "Invalid Suffix";
	}

	@Step("Validate if user is qualified registered user or Memebr in Literatum")
	public boolean validateQualificationMemberLogic_Literatum(DataTable dt, Response res) {
		actual = res.jsonPath().getString("tag.tag-label").toString();
		kMap_exp = CucumberUtils.convert(dt);
		if (kMap_exp.get("Member").equals("true")) {
			expec = (actual.contains("Member")) ? true : false;
		} else {
			expec = (actual.contains("Qualified")) ? true : false;
			not_exp = (actual.contains("Not Qualified")) ? true : false;
		}
		return (kMap_exp.get("Qualification").equals("true")) ? expec : not_exp;
	}

	@Step("Validate audience segment value in Literatum")
	public String validateAudienceSegment_Literatum(String audienceSegment, Response res) {
		return (actual.contains(audienceSegment)) ? audienceSegment : "Invalid Value";
	}

	@Step("Validate connect subscribe in Literatum")
	public String validateConnectSubscriber_Literatum(String connect, Response res) {
		return (actual.contains(connect)) ? connect : "Not Subscribed";
	}

	@Step("Validate Role in Literatum")
	public String validateRole_Literatum(String role, Response res) {
		return (actual.contains(role)) ? role : "Invalid Value";
	}

	@Step("User navigates to my account page on basis of icJoin")
	public void navigateToMyAccount(String icJoin, String qual) {
		if (icJoin.equals("yes")) {
			icProf.clickAlertLink_thankYouPopUp();
		} else if (qual.equals("true")) {
			commonFunc.clickElement(takeMeToMyAccount);
		} else {
			commonFunc.clickElement(manageAlertLinkForDisqualified);
		}

	}

	public String parseEventPromoValue(String completeEventCode) {
		Pattern pattern = Pattern.compile("\'([^\"]*)\'");
		Matcher match = pattern.matcher(completeEventCode);
		if (match.find()) {
			eventPromoValue = match.group(1);
		}
		LOGGER.info("=====Registeration Modal : mmsWidgets.showRegModal: Promo Code Value======");
		LOGGER.info(eventPromoValue);
		LOGGER.info("=================================");
		return eventPromoValue;
	}

	@Step("User set promo code from js event call on css site if its present")
	public String setPromoCode_FromEventCall_cssSite(String defaultPromo) {
		String completeEventCode = eventPromo_cssSite.getAttribute("onclick").toString();
		return completeEventCode.contains("promoCode") ? parseEventPromoValue(completeEventCode) : defaultPromo;
	}

	@Step("User set pdf premium promo code from js event call on css site if its present")
	public String setPromoCode_FromPdfEventCall_cssSite(String defaultPromo) {
		String completeEventCode = eventPromoPdf_cssSite.getAttribute("onclick").toString();
		return completeEventCode.contains("promoCode") ? parseEventPromoValue(completeEventCode) : defaultPromo;
	}

	@Step("User set promo code from js event call on main site if its present")
	public String setPromoCode_FromEventCall_mainSite(String defaultPromo) {
		String completeEventCode = eventPromo_mainSite.getAttribute("onclick").toString();
		return completeEventCode.contains("promoCode") ? parseEventPromoValue(completeEventCode) : defaultPromo;
	}

	@Step("User set url promo code if its included")
	public String setUrlPromoCode(String promoCode, String included, String defaultPromo) {
		return included.contains("yes") ? promoCode : defaultPromo;
	}

	@Step("User validate downstream process with qualifying or non-qualifying values")
	public boolean validateDownStream_Process(boolean qual) {
		if (qual) {
			downStreamValue = thankYouModalWithJoinIC();
		} else {
			downStreamValue = thankYouModal();
		}
		return downStreamValue;
	}

	@Step("User validates thankyou Modal with Join IC")
	public boolean thankYouModalWithJoinIC() {
		boolean val1 = takeMeToMyAccount.waitUntilClickable().isDisplayed();
		boolean val2 = qualifyToJoinMessage.waitUntilVisible().isDisplayed();
		return (val1 && val2) ? true : false;
	}

	@Step("User validates thankyou Modal for disqualified user")
	public boolean thankYouModal() {
		boolean val = thankYouModal.waitUntilVisible().isDisplayed();
		boolean val2 = thankYouModalMessage.waitUntilVisible().isDisplayed();
		boolean val3 = backToSiteButton.waitUntilVisible().isDisplayed();
		return (val && val2 && val3) ? true : false;
	}

	@Step("User validates gdpr messages")
	public boolean validateGdpr(DataTable dt) {
		kMap_exp = CucumberUtils.convert(dt);
		LOGGER.info("==============GDPR Actual : Map====================");
		LOGGER.info(CreateAcct.kMap.toString());
		LOGGER.info("===================================================");
		LOGGER.info("==============GDPR Expected : Map==================");
		LOGGER.info(kMap_exp.toString());
		LOGGER.info("===================================================");
		return (CreateAcct.kMap.equals(kMap_exp)) ? true : false;
	}

	@Step("User validate Soi, Connect, Remember Opt in Behavior")
	public boolean validateSOIConnect_RememberMe(DataTable dt) {
		kMap_exp.clear();
		kMap_exp = CucumberUtils.convert(dt);
		LOGGER.info("==============SOI/Connect/Remember Actual   : Map==================");
		LOGGER.info(CreateAcct.optIn.toString());
		LOGGER.info("=================================================================");
		LOGGER.info("==============SOI/Connect/Remember Expected : Map===============");
		LOGGER.info(kMap_exp.toString());
		LOGGER.info("================================================================");
		return (CreateAcct.optIn.equals(kMap_exp)) ? true : false;
	}

	@Step("User select question for sites, beds, physicians")
	public void selectQuestion_sites_phy_beds(String beds, String sites, String phy, String place) {

		if (place.equals("HOU") || place.equals("HOC") || place.equals("LTC") || place.equals("HOO")) {
			uccNoOfbeds.waitUntilClickable().selectByValue(beds);
		} else if (place.equals("HSM")) {
			uccNoOfSites.waitUntilClickable().selectByValue(sites);
		} else if (place.equals("PHO")) {
			uccNoOfPhysicans.waitUntilClickable().selectByValue(phy);
		} else {
			LOGGER.info("Please specify place of work value code in data file");
		}
	}

	@Step("Register non cookied lead or non signed in registered user on accordion form")
	public void nonCookied_nonSignedIn_Register(String suffix, String special, String role, String place,
			String orgName, String Country, String fname, String lname) throws InterruptedException {
		specialty = special;
		String user_email = jsonUtils.getFromJSON("flow_reg_cust_EMAIL.json", "email");
		submitPersonalInformation(fname, lname, Country);
		submitProfessionalInformation(suffix, role, place, orgName);
		enterEmail.waitUntilVisible().type(user_email);
		enterPass.waitUntilVisible().type(CreateAcct.pwd);
		commonFunc.clickElement(createAccountBtn);
	}

	@Step("Register non cookied lead or non signed in registered user on create account form (Legacy flow)")
	public void nonCookied_nonSignedIn_Register_LegacyFlow(String suffix, String special, String role, String place,
			String orgName, String Country, String fname, String lname) throws InterruptedException {
		specialty = special;
		String user_email = jsonUtils.getFromJSON("flow_reg_cust_EMAIL.json", "email");
		enterEmail.waitUntilVisible().type(user_email);
		enterPass.waitUntilVisible().type(CreateAcct.pwd);
		enterFirstName.waitUntilVisible().type(fname);
		enterLastName.waitUntilVisible().type(lname);
		selectSuffix.waitUntilVisible().selectByVisibleText(suffix);
		selectPrimarySpeciality(suffix);
		CreateAcct.selectRole(role);
		selectPlace.waitUntilVisible().selectByVisibleText(place);
		enterOrg.waitUntilVisible().type(orgName);
		selectCountry.waitUntilVisible().selectByVisibleText(Country);
		CreateAcct.Button.waitUntilClickable().click();
	}

	@Step("User check prepopulated fields on accordion form")
	public boolean prepopulated_cookied_SignedIn_Register(String suffix, String special, String role, String place,
			String orgName, String country, String fname, String lname) {
		String user_email = jsonUtils.getFromJSON("flow_reg_cust_EMAIL.json", "email");
		boolean section1 = prepopulation_personalInformation(fname, lname, country);
		LOGGER.info("==========================================");
		LOGGER.info("Section 1 Pre-Populated - :"+ section1);
		LOGGER.info("==========================================");
		commonFunc.clickElement(continueButton1);
		professionalInformation.waitUntilVisible();
		boolean section2 = prepopulation_professionalInformation(suffix, role, place, orgName);
		LOGGER.info("==========================================");
		LOGGER.info("Section 2 Pre-Populated - :"+ section2);
		LOGGER.info("==========================================");
		CreateAcct.PrimaryspecialityDropDown.type(special);
		commonFunc.clickElement(continueButton2);
		accountInformation.waitUntilVisible();
		enterEmail.waitUntilVisible();
		boolean emailAddress = enterEmail.getTextValue().equals(user_email);
		LOGGER.info("==========================================");
		LOGGER.info("Email Address Pre-Populated - :"+ emailAddress);
		LOGGER.info("==========================================");
		enterPass.type(CreateAcct.pwd);
		commonFunc.clickElement(createAccountBtn);
		return (section1 && section2 && emailAddress) ? true : false;
	}

	@Step("User check prepopulated fields on regular create account form (Legacy Flow)")
	public boolean prepopulated_cookied_SignedIn_Register_LegacyFlow(String suffix, String special, String role,
			String place, String orgName, String country, String fname, String lname) {
		specialty = special;
		String user_email = jsonUtils.getFromJSON("flow_reg_cust_EMAIL.json", "email");
		boolean section1 = prepopulation_personalInformation(fname, lname, country);
		boolean section2 = prepopulation_professionalInformation(suffix, role, place, orgName);
		selectPrimarySpeciality(suffix);
		boolean emailAddress = enterEmail.getTextValue().equals(user_email);
		enterPass.type(CreateAcct.pwd);
		CreateAcct.Button.waitUntilClickable().click();
		return (section1 && section2 && emailAddress) ? true : false;
	}

	@Step("User validate fields error messages on accoridon style form")
	public boolean validateFiedlsOn_AccordionForm(String suffix, String role, String place, String orgName,
			String Country, String fname, String lname, DataTable dt) throws InterruptedException {
		kMap_exp = CucumberUtils.convert(dt);
		commonFunc.clickElement(continueButton1);
		setPersonalInformation_errorMsg();
		submitPersonalInformation(fname, lname, Country);
		commonFunc.clickElement(continueButton2);
		setProfessioanlInformation_errorMsg();
		submitProfessionalInformation(suffix, role, place, orgName);
		commonFunc.clickElement(createAccountBtn);
		setEmailPassword_errorMsg();
		LOGGER.info("==============Error Validation Messages : Actual : ==================");
		LOGGER.info(kMap_actual.toString());
		LOGGER.info("=====================================================================");
		LOGGER.info("==============Error Validation Messages : Expected : ===============");
		LOGGER.info(kMap_exp.toString());
		LOGGER.info("====================================================================");
		return (kMap_actual.equals(kMap_exp)) ? true : false;
	}

	@Step("User validate fields error messages on create account form")
	public boolean validateFiedlsOn_CreateAccountForm(String suffix, String role, String place, String orgName,
			String Country, String fname, String lname, DataTable dt) throws InterruptedException {
		kMap_exp = CucumberUtils.convert(dt);
		CreateAcct.Button.waitUntilClickable().click();
		setPersonalInformation_errorMsg();
		setProfessioanlInformation_errorMsg();
		setEmailPassword_errorMsg();
		LOGGER.info("==============Error Validation Messages : Actual : ==================");
		LOGGER.info(kMap_actual.toString());
		LOGGER.info("=====================================================================");
		LOGGER.info("==============Error Validation Messages : Expected : ===============");
		LOGGER.info(kMap_exp.toString());
		LOGGER.info("====================================================================");
		return (kMap_actual.equals(kMap_exp)) ? true : false;
	}

	@Step("User validate visual UI Elements Step One")
	public boolean validateVisualUIElements_StepOne(String fname, String lname, String Country) {
		personalInformation.waitUntilVisible();
		boolean stepOne = (step_current.getText()).equals("1");
		submitPersonalInformation(fname, lname, Country);
		return (stepOne) ? true : false;
	}

	@Step("User validate visual UI Elemets Step Two")
	public boolean validateVisualUIElements_StepTwo(String suffix, String role, String place, String orgName)
			throws InterruptedException {
		professionalInformation.waitUntilVisible();
		boolean stepOne_completed = personalInfostep_completed.isCurrentlyEnabled();
		boolean steptwo = step_current.getText().toString().equals("2");
		submitProfessionalInformation(suffix, role, place, orgName);
		return (steptwo && stepOne_completed) ? true : false;
	}

	@Step("User validate visual UI Elemets Step Three")
	public boolean validateVisualUIElements_StepThree() {
		accountInformation.waitUntilVisible();
		boolean steptwo_completed = (professionalInfostep_completed).isCurrentlyEnabled();
		boolean stepThree = step_current.getText().toString().equals("3");
		return (stepThree && steptwo_completed) ? true : false;
	}

	@Step("User validate message on existing email if entered by user on registeration form")
	public String validateEmailMessage() {
		LOGGER.info("=============EMAIL Error Message================");
		LOGGER.info(existingEmail_error.getText().toString());
		LOGGER.info("================================================");
		return (existingEmail_error.getText().toString());
	}

	public void setPersonalInformation_errorMsg() {
		kMap_actual.put("fname_validation", fname_error.getText().toString());
		kMap_actual.put("lname_validation", lname_error.getText().toString());
		kMap_actual.put("country_validation", country_error.getText().toString());
	}

	public void setProfessioanlInformation_errorMsg() {
		kMap_actual.put("Suffix_validation", suffix_error.getText().toString());
		kMap_actual.put("Role_validation", role_error.getText().toString());
		kMap_actual.put("Place_validation", place_error.getText().toString());
		kMap_actual.put("Org_Validation", org_error.getText().toString());
	}

	public void setEmailPassword_errorMsg() {
		kMap_actual.put("Email_Address_Validation", email_error.getText().toString());
		kMap_actual.put("Create_Password_Validation", password_error.getText().toString());
	}

	public void submitPersonalInformation(String fname, String lname, String Country) {
		enterFirstName.waitUntilClickable().sendKeys(fname);
		enterLastName.waitUntilClickable().sendKeys(lname);
		selectCountry.waitUntilVisible().selectByVisibleText(Country);
		commonFunc.clickElement(continueButton1);
	}

	public void submitProfessionalInformation(String suffix, String role, String place, String orgName)
			throws InterruptedException {
		selectSuffix.waitUntilVisible().selectByVisibleText(suffix);
		selectPrimarySpeciality(suffix);
		selectRole.waitUntilVisible().selectByVisibleText(role);
		selectPlace.waitUntilVisible().selectByVisibleText(place);
		enterOrg.waitUntilClickable().sendKeys(orgName);
		commonFunc.clickElement(continueButton2);
	}

	public boolean prepopulation_personalInformation(String fname, String lname, String cont) {
		boolean firstName = enterFirstName.getTextValue().equals(fname);
		boolean lastName = enterLastName.getTextValue().equals(lname);
		boolean country = selectCountry.getSelectedVisibleTextValue().equals(cont);
		return (firstName && lastName && country) ? true : false;
	}

	public boolean prepopulation_professionalInformation(String suff, String rol, String pla, String org) {
		boolean suffix = selectSuffix.getSelectedVisibleTextValue().equals(suff);
		boolean role = selectRole.getSelectedVisibleTextValue().equals(rol);
		boolean place = selectPlace.getSelectedVisibleTextValue().equals(pla);
		boolean orgName = enterOrg.getTextValue().equals(org);
		return (suffix && role && place && orgName) ? true : false;
	}

	public void selectPrimarySpeciality(String suffix) {
		if (suffix.equals("MD") || suffix.equals("DO") || suffix.equals("MBBS")) {
			commonFunc.clickElement(CreateAcct.PrimaryspecialityDropDown);
			enterSpecialty.waitUntilVisible().type(specialty);
		}
	}

	@Step("Check if promo code included equal to yes/no in Data file and set url's accordingly in pdf premium embedded form")
	public String setPromoCode_pdfPremium(String promoCode, String promoIncluded) {
		return (promoIncluded.equals("yes")) ? pdfPromoCodeUrl + promoCode : nonPromoCodeUrl_Pdf;
	}

	public void printPromoValues(String expectedPromo, String actualPromo) {
		LOGGER.info("=============EXPECTED PROMO IN AKAMAI=====================");
		LOGGER.info(expectedPromo);
		LOGGER.info("=====================================================");
		LOGGER.info("=============ACTUAL PROMO IN AKAMAI========================");
		LOGGER.info(actualPromo);
		LOGGER.info("=====================================================");
	}

}
