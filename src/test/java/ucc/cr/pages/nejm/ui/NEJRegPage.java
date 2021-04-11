package ucc.cr.pages.nejm.ui;

import io.cucumber.datatable.DataTable;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.cr.pages.catalyst.ui.CRConnectPage;
import ucc.cr.pages.catalyst.ui.CRWidgetsPage;
import ucc.cr.pages.catalyst.ui.EventsPage;
import ucc.cr.pages.catalyst.ui.MyAccountPage;
import ucc.cr.pages.catalyst.ui.PDFPremiumRegPage;
import ucc.cr.pages.catalyst.ui.RegInsighCouncilPage;
import ucc.cr.pages.catalyst.ui.SignInPage;
import ucc.pages.ui.CommonFunc;
import ucc.utils.CssUtils;
import ucc.utils.TestUtils;
import ucc.utils.CucumberUtils.CucumberUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NEJRegPage extends PageObject {

	CommonFunc commonFunc = new CommonFunc();
	MyAccountPage myAcc = new MyAccountPage();
	TestUtils tUtil = new TestUtils();
	CssUtils cUtil = new CssUtils();
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String nejmBaseUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("nejm.base.url");
	static String nejmReturnParam = EnvironmentSpecificConfiguration.from(env_var).getProperty("nejm.returnParam");
	static String widgetBaseUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("widgets.base.url");
	static String myAccountBaseUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("myaccount.base.url");
	static String pdfBaseUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("pdf.base.url");
	static String storeBaseUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("store.choice.page.url");
	static String catalystBaseUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("catalyst.base.url");
	String user_email;
	public static Map<String, String> nejCssMap = new HashMap<String, String>();
	private static final Logger LOGGER = LoggerFactory.getLogger(NEJRegPage.class);
	public static Map<String, String> kMap;
	public static Map<String, String> kMap_actual = new HashMap<String, String>();
	private static String nejEmbedded = "/nejm-reg.html?domain=nejm";
	private static String nejGroupEmbedded = "/nejm-group-reg.html?domain=nejm";
	private static String returnUrl = "&returnUrl=";
	private static String nejModal = "/nejm-reg-modal.html?domain=nejm";
	private static String nejGroupModal = "/nejm-group-reg-modal.html";
	CRConnectPage connect = new CRConnectPage();
	PDFPremiumRegPage pdf = new PDFPremiumRegPage();
	RegInsighCouncilPage iC = new RegInsighCouncilPage();
	CRWidgetsPage widgets = new CRWidgetsPage();
	SignInPage signIn = new SignInPage();
	String nCss = "NejmCss/nejmMultiRegFormStyles.json";
	String tYMCss = "NejmCss/nejmThankYouModalStyles.json";
	String tYECss = "NejmCss/nejmThankYouEmbeddedStyles.json";
	String mainErr = "mainErrorMsg";
	String family = "font-family";
	String size = "font-size";
	String color = "color";
	String bgColor = "background-color";
	String plcHolder = "placeholder";
	String curs = "cursor";
	String vAlign = "vertical-align";

	@FindBy(xpath = "//h1[contains(text(),'Create Account')]")
	WebElementFacade createAccHeader;

	@FindBy(xpath = "//*[starts-with(@id,'create-acct')]")
	WebElementFacade createAccLinkWidgetSite;

	@FindBy(xpath = "//*[starts-with(@id,'uccEmail')]")
	WebElementFacade inputEmail;

	@FindBy(xpath = "//*[starts-with(@class,'ucc-form-wizard-step') and not(contains(@class,'ucc-form-wizard-step closed'))]//span")
	WebElementFacade continueBtn;

	@FindBy(xpath = "//*[starts-with(@id,'uccCountry')]")
	WebElementFacade selectCountry;

	@FindBy(xpath = "//*[starts-with(@id,'uccProfessionalCategory')]")
	WebElementFacade selectProfCategory;

	@FindBy(xpath = "//*[starts-with(@name,'uccSpecialty')]")
	WebElementFacade selectSpecialty;

	@FindBy(xpath = "//*[starts-with(@name,'uccRole')]")
	WebElementFacade selectRole;

	@FindBy(xpath = "//*[starts-with(@name,'uccPlaceOfWork')]")
	WebElementFacade selectPlace;

	@FindBy(xpath = "//*[starts-with(@name,'uccNameOfOrg')]")
	WebElementFacade inputOrg;

	@FindBy(xpath = "//*[starts-with(@name,'uccFirstName')]")
	WebElementFacade inputFirstName;

	@FindBy(xpath = "//*[starts-with(@name,'uccLastName')]")
	WebElementFacade inputLastName;

	@FindBy(xpath = "//*[starts-with(@name,'uccPwd')]")
	WebElementFacade inputPassword;

	@FindBy(xpath = "//*[starts-with(text(),'CREATE ACCOUNT')]")
	WebElementFacade createAccount;

	@FindBy(xpath = "//*[starts-with(text(),'You have successfully created your account')]")
	WebElementFacade createAccSuccessMsg;

	@FindBy(xpath = "//*[starts-with(text(),'Thank you!')]")
	WebElementFacade thankYouHeading;

	@FindBy(xpath = "//*[starts-with(text(),'CONTINUE')]")
	WebElementFacade continueReading;

	@FindBy(xpath = "//*[starts-with(text(),'MANAGE PROFILE')]")
	WebElementFacade manageAlerts;

	@FindBy(css = ".ucc-modal .ucc-modal-content")
	WebElementFacade nejmModal;

	@FindBy(css = ".ucc-widgets-nejm .form-header h1")
	WebElementFacade nejmheader;

	@FindBy(xpath = "//*[starts-with(text(),'Already')]")
	WebElementFacade nejmParagraph;

	@FindBy(xpath = "//*[starts-with(@class,'ucc-stepper-index') and contains(text(),'1')]")
	WebElementFacade stepOneProgressBar;

	@FindBy(xpath = "//*[starts-with(@class,'ucc-stepper-index') and contains(text(),'2')]")
	WebElementFacade stepTwoProgressBar;

	@FindBy(xpath = "//*[starts-with(@class,'ucc-stepper-index') and contains(text(),'3')]")
	WebElementFacade stepThreeProgressBar;

	@FindBy(xpath = "//*[starts-with(@class,'ucc-stepper-index') and contains(text(),'4')]")
	WebElementFacade finalStepProgressBar;

	@FindBy(css = ".ucc-form-container input.ucc-input, .ucc-form-container select.ucc-input")
	WebElementFacade inputEmailDesign;

	@FindBy(xpath = "//p[contains(text(),'Please review our')]/a[contains(text(),'Privacy Policy')]")
	WebElementFacade privacyPolicy;

	@FindBy(xpath = "//*[starts-with(@class,'ucc-form-wizard-step') and not(contains(@class,'ucc-form-wizard-step closed'))]//button")
	WebElementFacade btnContainer;

	@FindBy(xpath = "//*[starts-with(@id,'uccCountry')]/ancestor::div/label")
	WebElementFacade countryRegionLabel;

	@FindBy(xpath = "//*[starts-with(text(),'Please enter your correct')]")
	WebElementFacade mainErrorMsg;

	@FindBy(xpath = "//*[starts-with(text(),'Your email address is not a valid format')]")
	WebElementFacade invalidEmailFormatMsg;

	@FindBy(xpath = "//*[starts-with(text(),'Please select your country/region')]")
	WebElementFacade blankCountrySelectionMsg;

	@FindBy(xpath = "//*[starts-with(text(),'Please select professional category')]")
	WebElementFacade blankProfCatMsg;

	@FindBy(xpath = "//*[starts-with(text(),'Please select title')]")
	WebElementFacade blankRoleMsg;

	@FindBy(xpath = "//*[starts-with(text(),'Please select place of work or study')]")
	WebElementFacade blankPlaceMsg;

	@FindBy(xpath = "//*[starts-with(text(),'Please enter name of organization')]")
	WebElementFacade blankOrgMsg;

	@FindBy(xpath = "//div[@class='ucc-js-password-weak']")
	WebElementFacade weakPassword;

	@FindBy(xpath = "//div[@class='ucc-js-password-weak']/label")
	WebElementFacade weakPassIndicatorLabel;

	@FindBy(xpath = "//div[@class='ucc-js-password-medium']")
	WebElementFacade mediumPassword;

	@FindBy(xpath = "//div[@class='ucc-js-password-medium']/label")
	WebElementFacade mediumPassIndicatorLabel;

	@FindBy(xpath = "//div[@class='ucc-js-password-strong']")
	WebElementFacade strongPassword;

	@FindBy(xpath = "//div[@class='ucc-js-password-strong']/label")
	WebElementFacade strongPassIndicatorLabel;

	@FindBy(xpath = "//span[@title='Show Password']")
	WebElementFacade showPassword;

	@FindBy(xpath = "//*[starts-with(text(),'Remember me')]")
	WebElementFacade rememberMeCheckbox;

	@FindBy(xpath = "//*[starts-with(text(),'Email me information')]")
	WebElementFacade nejmSoiCheckbox;

	@FindBy(xpath = "//*[starts-with(text(),'Sign up for the weekly NEJM Catalyst Connect')]")
	WebElementFacade nejmCatalystConnectCheckbox;

	@FindBy(xpath = "//p[contains(text(),'You can unsubscribe from emails')]")
	WebElementFacade unsubscribeLanguage;

	@FindBy(xpath = "//p[contains(text(),'You can unsubscribe from emails')]")
	WebElementFacade personalInfoLanguage;

	@FindBy(xpath = "//a[contains(text(),'Terms')]")
	WebElementFacade termsLink;

	@FindBy(xpath = "//*[starts-with(text(),'Please enter your first name')]")
	WebElementFacade fNameErrorMsg;

	@FindBy(xpath = "//*[starts-with(text(),'Please enter your last name')]")
	WebElementFacade lNameErrorMsg;

	@FindBy(xpath = "//*[starts-with(text(),'Please enter your password')]")
	WebElementFacade passwordErrorMsg;

	@FindBy(xpath = "//*[starts-with(@id,'uccFirstName')]")
	WebElementFacade firstName;

	@FindBy(xpath = "//*[starts-with(@id,'uccLastName')]")
	WebElementFacade lastName;

	@FindBy(xpath = "//*[starts-with(@id,'uccSuffix')]")
	WebElementFacade suffixDropDown;

	@FindBy(xpath = "//*[starts-with(@id,'uccRole')]")
	WebElementFacade roleDropDown;

	@FindBy(xpath = "//*[starts-with(@id,'uccPlaceOfWork')]")
	WebElementFacade placeOfWorkDropDown;

	@FindBy(xpath = "//*[starts-with(@id,'uccNameOfOrg')]")
	WebElementFacade nameOfOrg;

	@FindBy(xpath = "//*[starts-with(@id,'uccCountry')]")
	WebElementFacade countryDropDown;

	@FindBy(xpath = "//button[contains(text(),'Register')]")
	WebElementFacade RegisterBtn;

	@FindBy(xpath = "//p[contains(text(),'You are now registered for the Web event.')]")
	WebElementFacade eventRegSuccessMsg;

	@FindBy(xpath = "//a[contains(text(),'Event')]")
	WebElementFacade EventsLink;

	@FindBy(xpath = "//*[starts-with(@id,'uccEmail')]")
	WebElementFacade LoginEmail;

	@FindBy(xpath = "//*[starts-with(@id,'uccStudentType')]")
	WebElementFacade selectStudentType;

	@FindBy(xpath = "//*[starts-with(@id,'uccProfession') and not(contains(@id, 'uccProfessionalCategory'))]")
	WebElementFacade selectProfession;

	@FindBy(xpath = "//h1[contains(text(),'Thank you')]")
	WebElementFacade thankYouTitle;

	@FindBy(xpath = "//button[contains(text(), 'Continue')]")
	WebElementFacade ContinueButton;

	@FindBy(xpath = "//h1[contains(text(),'Congratulations!')]")
	WebElementFacade textOnPage;

	@FindBy(xpath = "//h1[contains(text(),'Thank you')]")
	WebElementFacade pdfTextOnPage;

	@FindBy(xpath = "//*[@id='signIn']")
	WebElementFacade nejmSignInPopUp;

	@FindBy(xpath = "//*[@id='capture_signIn_signInEmailAddress']")
	WebElementFacade nejmSignInEmail_Input;

	@FindBy(xpath = "//*[@id='capture_signIn_currentPassword']")
	WebElementFacade nejmSignInPassword_Input;

	@FindBy(xpath = "//*[starts-with(@id,'janrainModal')]//button")
	WebElementFacade signInBtn;

	@FindBy(xpath = "//a[contains(text(),'Welcome')]")
	WebElementFacade welcomeMsg;

	@FindBy(xpath = "//a[contains(text(),'Sign Out')]")
	WebElementFacade signOutLink;

	@FindBy(xpath = "//ul[@class='g-nejm-group__user-tools']/li//a[@class='signin-header']")
	WebElementFacade signInLinkNejmSite;

	@FindBy(xpath = "//a[contains(text(),'Sign In')]")
	WebElementFacade signInLink_cssSite;

	@FindBy(css = "input[name=uccNejmSoi]:checked[type=checkbox]")
	List<WebElementFacade> nejmSoiChecked;

	@FindBy(css = "input[name=uccNejmETOC]:checked[type=checkbox]")
	List<WebElementFacade> nejmWTOCChecked;
	
	@FindBy(css = "input[name=uccNejmCC]:checked[type=checkbox]")
	List<WebElementFacade> nejmCatalystConnectChecked;

	@FindBy(xpath = "//span[contains(text(),'Stay informed')]")
	WebElementFacade nejmWTocCheckbox;

	@FindBy(xpath = "//div[@class='notloggedin']//span[contains(text(),'Sign In')]")
	WebElementFacade storeSignIn;

	@FindBy(xpath = "//span[contains(text(),'Welcome')]")
	WebElementFacade welcomeEmailHeader_StoreSite;

	@FindBy(xpath = "//a[contains(text(),'Welcome')]")
	WebElementFacade welcomeEmailHeader_CssSite;

	@FindBy(xpath = "//a[contains(text(),'Get Free Access Now')]")
	WebElementFacade getFreeAccessNowLink_NejmSite;

	@FindBy(xpath = "//span[@aria-title='User account']")
	WebElementFacade nejmUserHeaderMenu;

	@FindBy(xpath = "//span[@aria-title='User account']//a[contains(text(),'Sign Out')]")
	WebElementFacade nejmSiteSignOutLink;

	@FindBy(xpath = "//b[contains(text(),'Email Address')]//ancestor::td/following-sibling::td")
	WebElementFacade accInfo_emailadd;

	@FindBy(xpath = "//a[contains(text(),'BACK TO SITE')]")
	WebElementFacade backToSiteBtn;

	@FindBy(xpath = "//div[contains(@class,'ucc-modal') and contains(@aria-hidden,'false')]//h1[starts-with(text(),'Thank you!')]")
	WebElementFacade thankYouPopUpHeading;

	@FindBy(xpath = "//div[@aria-hidden='false']//button[@class='ucc-modal-close']")
	WebElementFacade closeBtnThankYouPopUp;

	@FindBy(xpath = "//*[starts-with(text(),'Your current alerts include')]")
	WebElementFacade currentAlertMsg;

	@FindBy(xpath = "//*[starts-with(text(),'Weekly Table of Contents')]")
	WebElementFacade weeklyTocList;

	@FindBy(xpath = "//*[starts-with(text(),'General Information')]")
	WebElementFacade nejmGeneralInfo;

	@FindBy(xpath = "//*[starts-with(text(),'Pediatrics')]")
	WebElementFacade pediatrics;

	@FindBy(xpath = "//a[@aria-label='NEJM Logo']")
	WebElementFacade nejmLogo;

	@FindBy(xpath = "//ul[@class='g-nejm-group__user-tools']//span[@class='a-user-icon']")
	WebElementFacade userIcon;

	@Step("Check and launch modal or embedded nejm multi step registration form on css site")
	public String setNejmWidgetsUrl(String formType) {
		return (formType.equals("embedded")) ? getNejEm(nejmBaseUrl, nejmReturnParam) : nejModal;
	}

	@Step("Check and launch modal or embedded nejm group multi step registration form on css site")
	public String setNejmGroupWidgetsUrl(String formType) {
		return (formType.equals("embedded")) ? getNejEmGroup(nejmBaseUrl, nejmReturnParam) : nejGroupModal;
	}

	@Step("User check if Nejm Registration page is displayed")
	public boolean validateNejForm() {
		return createAccHeader.waitUntilVisible().isDisplayed() ? true : false;
	}

	@Step("User submit details on multi step registration form")
	public void submitNejRegDetails(DataTable dt) throws InterruptedException {
		kMap = CucumberUtils.convert(dt);
		checkFormType(kMap.get("formType"));
		commonFunc.waitForLoadPage();
		submitEmailandContinue(kMap.get("Email"));
		tUtil.putToSession("CountryPrepopulation", checkPrePopulation(kMap.get("Country")));
		submitCountryandContinue(kMap.get("Country"));
		submitStepThirdandContinue(kMap);
		submitFinalStep(kMap);
	}

	@Step("User validate thank you message content")
	public String validateThankYouContent() {
		return createAccSuccessMsg.waitUntilVisible().getText();
	}

	@Step("User validate thankyou pop up")
	public boolean validateThankYouPopUp() throws InterruptedException {
		waitForPrepopulation(thankYouHeading);
		boolean thankYou = thankYouHeading.waitUntilVisible().isDisplayed();
		boolean alerts = manageAlerts.waitUntilVisible().isDisplayed();
		return (thankYou && alerts) ? true : false;
	}

	@Step("User validate continue reading link appears only for embedded form")
	public boolean validateContinueReadingLink() {
		return continueReading.isCurrentlyVisible();
	}

	@Step("User validate redirection")
	public boolean validateRedirection() {
		commonFunc.waitForLoadPage();
		return myAcc.userAtMyAccountPage();
	}

	private String getNejEm(String baseUrl, String returnParam) {
		return (nejEmbedded + returnUrl + baseUrl + returnParam);
	}

	private String getNejEmGroup(String baseUrl, String returnParam) {
		return (nejGroupEmbedded + returnUrl + baseUrl + returnParam);
	}

	public void checkFormType(String formType) {
		if (formType.equals("modal")) {
			commonFunc.clickElement(createAccLinkWidgetSite);
		}
	}

	public void submitEmailandContinue(String email) {
		user_email = tUtil.AppendTimestamp(email);
		LOGGER.info("User register email on Nejm Form: " + user_email);
		inputEmail_Continue(user_email);
		tUtil.putToSession("Email", user_email);
	}

	public void submitCountryandContinue(String country) {
		selectCountry.waitUntilClickable().selectByValue(country);
		commonFunc.clickElement(continueBtn);
	}

	public boolean checkPrePopulation(String country) throws InterruptedException {
		waitForPrepopulation(selectCountry); // waiting until country gets prepopulated on basis of IP Address
		LOGGER.info("Expected Country Prepopulation : " + country);
		LOGGER.info("Country Prepopulated : " + selectCountry.waitUntilEnabled().getValue());
		return selectCountry.getValue().equals(country) ? true : false;
	}

	public void waitForPrepopulation(WebElementFacade element) throws InterruptedException {

		int timeOutMin = 12;
		do {
			LOGGER.info("Waiting for Value to be prepopulated in Element : - " + element);
			TimeUnit.SECONDS.sleep(5);

			String fieldVal = element.getValue();

			if (fieldVal != null) {
				break;
			}
			timeOutMin--;
		} while (timeOutMin != 0);
	}

	public void submitStepThirdandContinue(Map<String, String> map) {
		selectProfCategory.waitUntilClickable().selectByValue(map.get("ProfCat"));
		selectSpecialty(map.get("ProfCat"), map.get("Specialty"));
		selectStudentType(map.get("ProfCat"), map.get("StudentType"));
		selectProfession(map.get("ProfCat"), map.get("Profession"));
		selectRole(map.get("Role"), map.get("ProfCat"));
		selectPlace.waitUntilClickable().selectByValue(map.get("Place"));
		inputOrg.waitUntilClickable().clear();
		inputOrg.type(map.get("Org"));
		commonFunc.clickElement(continueBtn);
	}

	public void submitFinalStep(Map<String, String> map) throws InterruptedException {
		inputFirstName.waitUntilClickable().clear();
		inputFirstName.type(map.get("FName"));
		inputLastName.waitUntilClickable().clear();
		inputLastName.type(map.get("LName"));
		inputPassword.waitUntilClickable().clear();
		inputPassword.type(map.get("Pass"));
		createAccount.waitUntilClickable().click();
	}

	public void selectSpecialty(String profCat, String specialty) {
		if (profCat.equals("PHY") || profCat.equals("RES")) {
			selectSpecialtyVal(specialty);
		}
	}

	public void selectAlertsLink() {
		manageAlerts.waitUntilClickable().click();
	}

	public void selectRole(String role, String profCat) {
		if (!(profCat.equals("STU") || profCat.equals("RES"))) {
			selectRole.waitUntilClickable().selectByValue(role);
		}
	}

	public void selectProfession(String profCat, String profession) {
		if (profCat.equals("OTH")) {
			selectProfession.waitUntilClickable().selectByValue(profession);
		}
	}

	public void selectStudentType(String profCat, String studentType) {
		if (profCat.equals("STU")) {
			selectStudentType.waitUntilClickable().selectByValue(studentType);
		}
	}

	public void selectSpecialtyVal(String specialtyVal) {
		WebElementFacade selectSpVal = find(ByXPath.xpath("//div[@data-result='" + specialtyVal + "']"));
		selectSpecialty.waitUntilClickable().click();
		selectSpVal.waitUntilClickable().click();
	}

	/*
	 * Create cookied Lead user using any C&R Flow
	 */
	public void cookiedLeadUser(DataTable dt) throws Throwable {
		kMap_actual.clear();
		kMap_actual = CucumberUtils.convert(dt);
		user_email = tUtil.AppendTimestamp(kMap_actual.get("Email"));
		String flow = kMap_actual.get("crFlow");
		LOGGER.info("User Submit Email on C&R Flow - " + flow + " : -" + user_email);
		performFlow(flow, dt);
		tUtil.putToSession("Email", user_email);
	}

	private void performFlow(String flow, DataTable dt) throws Throwable {
		switch (flow) {

		case "Events":
			launchEventSignUp_EnterLeadEmail();
			submitCatalystInitialForm();
			commonFunc.clickElement(RegisterBtn);
			eventRegSuccessMsg.waitUntilVisible();
			break;
		case "Connect":
			launchConnect_EnterLeadEmail();
			connect.setFormValues(dt);
			connect.click_signUp();
			thankYouTitle.waitUntilVisible();
			break;
		case "Insights Council":
			launchInsightCouncil_EnterLeadEmail();
			submitCatalystInitialForm();
			ContinueButton.waitUntilClickable().click();
			textOnPage.waitUntilVisible();
			break;
		case "Pdf Premium":
			launchPdfPremium_EnterLeadEmail();
			submitCatalystInitialForm();
			pdf.click_download();
			pdfTextOnPage.waitUntilVisible();
			break;

		default:
			LOGGER.info("Please specify the C&R flow in data file");
		}
	}

	private void submitCatalystInitialForm() {
		firstName.waitUntilClickable().type(kMap_actual.get("fName"));
		lastName.waitUntilClickable().type(kMap_actual.get("lName"));
		suffixDropDown.waitUntilClickable().selectByValue(kMap_actual.get("suffix"));
		roleDropDown.waitUntilClickable().selectByValue(kMap_actual.get("role"));
		placeOfWorkDropDown.waitUntilClickable().selectByValue(kMap_actual.get("place"));
		nameOfOrg.waitUntilClickable().type(kMap_actual.get("nameOfOrg"));
		countryDropDown.waitUntilClickable().selectByValue(kMap_actual.get("Country"));
	}

	private void launchEventSignUp_EnterLeadEmail() throws Throwable {
		commonFunc.Launch_URL(widgetBaseUrl + widgets.setModalEventSignUpUrl());
		EventsLink.waitUntilClickable().click();
		commonFunc.verifyLinkActive(widgetBaseUrl + widgets.setModalEventSignUpUrl());
		LoginEmail.waitUntilClickable().type(user_email);
		commonFunc.clickElement(RegisterBtn);
	}

	private void launchPdfPremium_EnterLeadEmail() throws Throwable {
		pdf.LaunchUrl();
		commonFunc.waitForLoadPage();
		commonFunc.verifyLinkActive(pdfBaseUrl);
		pdf.enter_email_click_button(user_email);
	}

	private void launchInsightCouncil_EnterLeadEmail() throws Throwable {
		commonFunc.Launch_URL(catalystBaseUrl);
		iC.hoverICLink();
		iC.selectApplyToJoin();
		commonFunc.switchToSecondWindow();
		commonFunc.waitForLoadPage();
		iC.enter_email_click_button(user_email);
	}

	private void launchConnect_EnterLeadEmail() throws Throwable {
		commonFunc.Launch_URL(catalystBaseUrl);
		commonFunc.waitForLoadPage();
		connect.newsLetterLink_footer();
		commonFunc.verifyLinkActive(catalystBaseUrl);
		connect.enterEmail_and_continue(user_email);
	}

	/*
	 * Submit lead user details on NEJ Reg for any C&R flow
	 */
	public void submitPrePopulatedNejForm(DataTable dt) throws Throwable {
		kMap_actual.clear();
		kMap_actual = CucumberUtils.convert(dt);
		checkFormType(kMap_actual.get("formType"));
		String flow = kMap_actual.get("crFlow");
		if (flow.equals("Events") || flow.equals("Insights Council") || flow.equals("Pdf Premium")) {
			submitPrePopulatedEmail_Country();
			waitForRolePlacePrepopulation();
			submitConditionalFields(kMap_actual.get("ProfCat"), kMap_actual.get("Specialty"), kMap_actual.get("Role"));
			checkSOI_ETOC();
			submitFinalPage(kMap_actual.get("Pass"));

		} else if (flow.equals("Connect")) {
			submitPrePopulatedEmail_Country();
			selectPlace.waitUntilClickable().selectByValue(kMap_actual.get("Place"));
			submitConditionalFields(kMap_actual.get("ProfCat"), kMap_actual.get("Specialty"), kMap_actual.get("Role"));
			checkSOI_ETOC();
			submitFinalPage(kMap_actual.get("Pass"));
		} else {
			LOGGER.info("Specify the C&R flow type to be submitted on NEJM Reg Form");
		}

	}

	private void submitPrePopulatedEmail_Country() throws InterruptedException {
		waitForPrepopulation(inputEmail);
		continueBtn.click();
		waitForPrepopulation(selectCountry);
		continueBtn.waitUntilClickable().click();
	}

	private void waitForRolePlacePrepopulation() throws InterruptedException {
		waitForPrepopulation(selectRole);
		waitForPrepopulation(selectPlace);
	}

	private void submitConditionalFields(String profCat, String specialty, String role) throws InterruptedException {
		waitForPrepopulation(inputOrg);
		selectProfCategory.waitUntilClickable().selectByValue(profCat);
		selectSpecialty(profCat, specialty);
		selectRole.waitUntilClickable().selectByValue(role);
		continueBtn.waitUntilClickable().click();
	}

	private void submitFinalPage(String password) throws InterruptedException {
		waitForPrepopulation(inputFirstName);
		waitForPrepopulation(inputLastName);
		inputPassword.waitUntilClickable().type(password);
		createAccount.waitUntilClickable().click();
	}

	public void printStep(DataTable dt) {
		kMap = CucumberUtils.convert(dt);
		LOGGER.info("==========Design Validation : NEJM Reg Step - " + kMap.get("Step") + "=============");
		nejCssMap.put("Step", kMap.get("Step"));
	}

	public boolean validateDesign(DataTable dt) {
		kMap = CucumberUtils.convert(dt);
		LOGGER.info("*************Actual Values : ********************************");
		LOGGER.info(nejCssMap.toString());
		LOGGER.info("-------------------------------------------------------------");
		LOGGER.info("*************Expected Values : ******************************");
		LOGGER.info(kMap.toString());
		LOGGER.info("--------------------------------------------------------------");
		return nejCssMap.equals(kMap) ? true : false;
	}

	public void fetchStepOne_DesignDetails(DataTable dt) throws IOException {
		kMap = CucumberUtils.convert(dt);
		nejCssMap.clear();
		checkFormType(kMap.get("formType"));
		cUtil.cssValidator(nejCssMap, nCss, nejmheader, "Header", family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, nejmParagraph, "Paragraph", family, size);
		cUtil.cssValidator(nejCssMap, nCss, stepOneProgressBar, "stepProgressBar", curs, bgColor, family, vAlign);
		cUtil.cssValidator(nejCssMap, nCss, btnContainer, "continueBtn", family, size, color, bgColor);
		cUtil.cssValidator(nejCssMap, nCss, privacyPolicy, "privacyPolicy", family, size, color);
		cUtil.cssValidatorA(nejCssMap, nCss, inputEmailDesign, "inputEmail", plcHolder, bgColor, color, size, family);
		submitEmailandContinue(kMap.get("invalidEmail"));
		cUtil.cssValidator(nejCssMap, nCss, mainErrorMsg, mainErr, family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, invalidEmailFormatMsg, "EmailErrorMsg", family, size, color);
		submitEmailandContinue(kMap.get("validEmail"));
	}

	public void fetchStepTwo_DesignDetails(String country) {
		nejCssMap.clear();
		cUtil.cssValidator(nejCssMap, nCss, nejmheader, "Header", family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, nejmParagraph, "Paragraph", family, size);
		cUtil.cssValidator(nejCssMap, nCss, stepTwoProgressBar, "stepProgressBar", curs, bgColor, family, vAlign);
		stepTwoProgressBar.click();
		cUtil.cssValidator(nejCssMap, nCss, btnContainer, "continueBtn", family, size, color, bgColor);
		cUtil.cssValidator(nejCssMap, nCss, privacyPolicy, "privacyPolicy", family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, selectCountry, "CountryDropDown", family, size, color);
		cUtil.cssValidatorT(nejCssMap, nCss, countryRegionLabel, "CountryLabel", "text", family, size, color);
		submitCountryandContinue(""); // to check validation message design selecting blank country
		cUtil.cssValidator(nejCssMap, nCss, mainErrorMsg, mainErr, family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, blankCountrySelectionMsg, "CountryErrorMsg", family, size, color);
		submitCountryandContinue(country);
	}

	public void fetchStepThree_DesignDetails(DataTable dt) {
		kMap = CucumberUtils.convert(dt);
		nejCssMap.clear();
		cUtil.cssValidator(nejCssMap, nCss, nejmheader, "Header", family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, nejmParagraph, "Paragraph", family, size);
		cUtil.cssValidator(nejCssMap, nCss, stepThreeProgressBar, "stepProgressBar", curs, bgColor, family, vAlign);
		stepThreeProgressBar.click();
		cUtil.cssValidator(nejCssMap, nCss, btnContainer, "continueBtn", family, size, color, bgColor);
		cUtil.cssValidator(nejCssMap, nCss, privacyPolicy, "privacyPolicy", family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, selectProfCategory, "ProfCatDropDown", family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, selectRole, "roleDropDown", family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, selectPlace, "placeDropDown", family, size, color);
		cUtil.cssValidatorA(nejCssMap, nCss, inputOrg, "inputOrg", plcHolder, family, size, color);
		commonFunc.clickElement(continueBtn);
		cUtil.cssValidator(nejCssMap, nCss, mainErrorMsg, mainErr, family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, blankProfCatMsg, "ProfCatErrorMsg", family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, blankRoleMsg, "RoleErrorMsg", family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, blankPlaceMsg, "PlaceErrorMsg", family, size, color);
		submitStepThirdandContinue(kMap);
	}

	public void fetchFinalStep_DesignDetails(DataTable dt) throws InterruptedException {
		kMap = CucumberUtils.convert(dt);
		nejCssMap.clear();
		cUtil.cssValidator(nejCssMap, nCss, nejmheader, "Header", family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, nejmParagraph, "Paragraph", family, size);
		cUtil.cssValidator(nejCssMap, nCss, finalStepProgressBar, "stepProgressBar", curs, bgColor, family, vAlign);
		cUtil.cssValidator(nejCssMap, nCss, inputFirstName, "FirstName", family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, inputLastName, "LastName", family, size, color);
		finalStepProgressBar.click();
		cUtil.cssValidator(nejCssMap, nCss, inputPassword, "createPassword", family, size);
		inputPassword.waitUntilClickable().type(kMap.get("Weak Pass")); // Weak password Indicator
		cUtil.cssValidator(nejCssMap, nCss, weakPassword, "weakPassword", family, size);
		inputPassword.waitUntilClickable().type(kMap.get("Medium Pass")); // Medium password Indicator
		cUtil.cssValidator(nejCssMap, nCss, mediumPassword, "mediumPassword", family, size);
		inputPassword.waitUntilClickable().type(kMap.get("Strong Pass")); // Strong password Indicator
		cUtil.cssValidator(nejCssMap, nCss, strongPassword, "strongPassword", family, size);
		inputPassword.clear();
		cUtil.cssValidator(nejCssMap, nCss, showPassword, "showPassword", curs, size, color);
		cUtil.cssValidator(nejCssMap, nCss, btnContainer, "createAccountBtn", family, size, color, bgColor);
		cUtil.cssValidator(nejCssMap, nCss, rememberMeCheckbox, "rememberMeCheckbox", family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, nejmSoiCheckbox, "nejmSoiCheckbox", family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, unsubscribeLanguage, "unsubscribeLanguage", family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, personalInfoLanguage, "personalInfoLanguage", family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, termsLink, "termsLink", family, size, color);
		createAccount.waitUntilClickable().click();
		cUtil.cssValidator(nejCssMap, nCss, mainErrorMsg, "mainErrorMsg", family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, fNameErrorMsg, "fNameError", family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, lNameErrorMsg, "lNameError", family, size, color);
		cUtil.cssValidator(nejCssMap, nCss, passwordErrorMsg, "passwordError", family, size, color);
		submitFinalStep(kMap);
	}

	@Step("Non Cookied lead User submit details on multi step registration form")
	public void submitNejReg_NonCookied_Details(DataTable dt, String email) throws InterruptedException {
		kMap = CucumberUtils.convert(dt);
		checkFormType(kMap.get("formType"));
		commonFunc.waitForLoadPage();
		LOGGER.info("User enters non-cookied user email on Nejm Form: " + (String) tUtil.getFromSession("Email"));
		inputEmail_Continue((String) tUtil.getFromSession("Email"));
		tUtil.putToSession("CountryPrepopulation", checkPrePopulation(kMap.get("Country")));
		submitCountryandContinue(kMap.get("Country"));
		submitStepThirdandContinue(kMap);
		submitFinalStep(kMap);
	}

	private void inputEmail_Continue(String email) {
		inputEmail.waitUntilClickable().clear();
		inputEmail.type(email);
		continueBtn.waitUntilClickable().click();
	}

	@Step("User set visibility value in session for drop down on step third page of nejm reg")
	public void setDropDownVisibility(DataTable dt) {
		kMap = CucumberUtils.convert(dt);
		checkFormType(kMap.get("formType"));
		commonFunc.waitForLoadPage();
		submitEmailandContinue(kMap.get("Email"));
		submitCountryandContinue(kMap.get("Country"));
		setDropDownVisibilityStepThird(kMap);
	}

	private void setDropDownVisibilityStepThird(Map<String, String> map) {
		selectProfCategory.waitUntilClickable().selectByValue(map.get("ProfCat"));
		tUtil.putToSession("Professional Category", map.get("ProfCat"));
		kMap_actual.clear();
		kMap_actual.put("Profession", String.valueOf(selectProfession.isCurrentlyVisible()));
		kMap_actual.put("StudentType", String.valueOf(selectStudentType.isCurrentlyVisible()));
		kMap_actual.put("PrimarySpecialty", String.valueOf(selectSpecialty.isCurrentlyVisible()));
		kMap_actual.put("Role", String.valueOf(selectRole.isCurrentlyVisible()));
		kMap_actual.put("Place", String.valueOf(selectPlace.isCurrentlyVisible()));
	}

	@Step("User validate drop down visibility on basis on professional category on step third page of Nejm reg")
	public boolean validateVisibility(DataTable dt) {
		kMap = CucumberUtils.convert(dt);
		LOGGER.info("===============VISIBILITY OF DROP DOWN ON STEP 3 NEJM REG=================");
		LOGGER.info("===============When Professional Category is :=================");
		LOGGER.info((String) tUtil.getFromSession("Professional Category"));
		return validateMaps();
	}

	@Step("Catalyst registered user sign in on NEJM Reg")
	public void catalystRegUserSignIn_NejmReg(String email, String pass, String formType) {
		checkFormType(formType);
		inputEmail_Continue(email);
		commonFunc.waitForLoadPage();
		kMap_actual.clear();
		nejmSignInPopUp.waitUntilVisible();
		kMap_actual.put("Sign_In_PopUp_Displayed", String.valueOf(nejmSignInPopUp.isCurrentlyVisible()));
		kMap_actual.put("Email_Pre-Populated", nejmSignInEmail_Input.getValue().equals(email) ? "true" : "false");
		nejmSignInPassword_Input.type(pass);
		signInBtn.waitUntilClickable().click();
		commonFunc.waitForLoadPage();
		commonFunc.switchToDefaultContent();
		kMap_actual.put("SuccessFul_Sign_In", String.valueOf(checkWelcomeMsgORSignOutLink(formType)));
	}

	private boolean checkWelcomeMsgORSignOutLink(String formType) {
		if (formType.equals("modal")) {
			welcomeMsg.waitUntilVisible();
		}
		signOutLink.waitUntilVisible();
		return (welcomeMsg.isCurrentlyVisible() || signOutLink.isCurrentlyVisible()) ? true : false;
	}

	@Step("validate catalyst registered user is able to sign in via NEJM Reg form")
	public boolean validateCatalystRegUserSignIn_NejmReg(DataTable dt) {
		kMap = CucumberUtils.convert(dt);
		LOGGER.info("=======Catalyst Registered User Sign In On NEJM REG=====");
		return validateMaps();
	}

	private boolean validateMaps() {
		LOGGER.info("==============Actual Values=============================");
		LOGGER.info(kMap_actual.toString());
		LOGGER.info("==============Expected Values===========================");
		LOGGER.info(kMap.toString());
		LOGGER.info("=========================================================");
		return kMap_actual.equals(kMap) ? true : false;
	}

	@Step("Submit NEJM Reg form along with email preferences")
	public void submitNejm_WithEmailPref(DataTable dt) throws InterruptedException {
		kMap = CucumberUtils.convert(dt);
		checkFormType(kMap.get("formType"));
		commonFunc.waitForLoadPage();
		submitEmailandContinue(kMap.get("Email"));
		tUtil.putToSession("CountryPrepopulation", checkPrePopulation(kMap.get("Country")));
		submitCountryandContinue(kMap.get("Country"));
		submitStepThirdandContinue(kMap);
		checkSOI_ETOC();
		selectETOC(Boolean.parseBoolean(kMap.get("SelectETOC")), kMap.get("Country"));
		submitFinalStep(kMap);
	}

	private void checkSOI_ETOC() {

		tUtil.putToSession("NEJMCatalystConnect-CheckedStatus",
				String.valueOf(elementPresence(nejmCatalystConnectChecked)));
		tUtil.putToSession("NEJMCatalystConnect-CheckboxVisibility",
				String.valueOf(nejmCatalystConnectCheckbox.isCurrentlyVisible()));
		tUtil.putToSession("NEJMSoi-CheckedStatus", String.valueOf(elementPresence(nejmSoiChecked)));
		tUtil.putToSession("NEJMSoi-CheckboxVisibility", String.valueOf(nejmSoiCheckbox.isCurrentlyVisible()));
		tUtil.putToSession("NEJMWTOC-CheckedStatus", String.valueOf(elementPresence(nejmWTOCChecked)));
		tUtil.putToSession("NEJMWTOC-CheckboxVisibility", String.valueOf(nejmWTocCheckbox.isCurrentlyVisible()));
	}

	private boolean elementPresence(List<WebElementFacade> element) {
		return (element.size() > 0) ? true : false;
	}

	@Step("Validate NEJM SOI Checkbox visibility per selected country")
	public String validateNejm_SOICheckboxVisibility(String expectedVisibility) {
		LOGGER.info("===============EXPECTED NEJM SOI CHECKBOX - VISIBILITY : ");
		LOGGER.info(expectedVisibility);
		LOGGER.info("===============ACTUAL NEJM SOI CHECKBOX - VISIBILITY : ");
		LOGGER.info((String) tUtil.getFromSession("NEJMSoi-CheckboxVisibility"));
		return (String) tUtil.getFromSession("NEJMSoi-CheckboxVisibility");
	}

	@Step("Validate NEJM Catalyst Connect Checkbox checked Status as per selected country")
	public String validateNejm_ConnectCheckbox_CheckedStatus(String expectedCheckedStatus) {
		LOGGER.info("===============EXPECTED NEJM Catalyst Connect CHECKBOX - CHECKED STATUS : ");
		LOGGER.info(expectedCheckedStatus);
		LOGGER.info("===============ACTUAL NEJM Catalyst Connect - CHECKED STATUS : ");
		LOGGER.info((String) tUtil.getFromSession("NEJMCatalystConnect-CheckedStatus"));
		return (String) tUtil.getFromSession("NEJMCatalystConnect-CheckedStatus");
	}

	@Step("Validate NEJM Catalyst Connect Checkbox visibility per selected country")
	public String validateNejm_ConnectCheckboxVisibility(String expectedVisibility) {
		LOGGER.info("===============EXPECTED NEJM Catalyst Connect CHECKBOX - VISIBILITY : ");
		LOGGER.info(expectedVisibility);
		LOGGER.info("===============ACTUAL NEJM Catalyst Connect CHECKBOX - VISIBILITY : ");
		LOGGER.info((String) tUtil.getFromSession("NEJMCatalystConnect-CheckboxVisibility"));
		return (String) tUtil.getFromSession("NEJMCatalystConnect-CheckboxVisibility");
	}

	@Step("Validate NEJM SOI Checkbox checked Status as per selected country")
	public String validateNejm_SOICheckbox_CheckedStatus(String expectedCheckedStatus) {
		LOGGER.info("===============EXPECTED NEJM SOI CHECKBOX - CHECKED STATUS : ");
		LOGGER.info(expectedCheckedStatus);
		LOGGER.info("===============ACTUAL NEJM SOI CHECKBOX - CHECKED STATUS : ");
		LOGGER.info((String) tUtil.getFromSession("NEJMSoi-CheckedStatus"));
		return (String) tUtil.getFromSession("NEJMSoi-CheckedStatus");
	}

	@Step("Validate NEJM ETOC Checkbox visibility per selected country")
	public String validateNejm_ETOCCheckboxVisibility(String expectedVisibility) {
		LOGGER.info("===============EXPECTED NEJM ETOC CHECKBOX - VISIBILITY : ");
		LOGGER.info(expectedVisibility);
		LOGGER.info("===============ACTUAL NEJM ETOC CHECKBOX - VISIBILITY : ");
		LOGGER.info((String) tUtil.getFromSession("NEJMWTOC-CheckboxVisibility"));
		return (String) tUtil.getFromSession("NEJMWTOC-CheckboxVisibility");
	}

	@Step("Validate NEJM ETOC Checkbox checked Status as per selected country")
	public String validateNejm_ETOCCheckbox_CheckedStatus(String expectedCheckedStatus) {
		LOGGER.info("===============EXPECTED NEJM ETOC CHECKBOX - CHECKED STATUS : ");
		LOGGER.info(expectedCheckedStatus);
		LOGGER.info("===============ACTUAL NEJM ETOC CHECKBOX - CHECKED STATUS : ");
		LOGGER.info((String) tUtil.getFromSession("NEJMWTOC-CheckedStatus"));
		return (String) tUtil.getFromSession("NEJMWTOC-CheckedStatus");
	}

	@Step("Validate user is signed in on NEJM Site")
	public boolean validateUserSignedIn() {
		commonFunc.waitForLoadPage();
		return userIcon.waitUntilVisible().isCurrentlyVisible();
	}

	@Step("User select ETOC Checkbox on basis of Country")
	public void selectETOC(boolean selectETOC, String country) {
		if (selectETOC && !country.equals("USA")) {
			nejmWTocCheckbox.waitUntilClickable().click();
		}
	}

	@Step("user sign in on store site")
	public void signInOnStoreSite(String email, String pass) throws Throwable {
		commonFunc.Launch_URL(storeBaseUrl);
		storeSignIn.waitUntilClickable().click();
		signIn.inputEmail(email);
		signIn.inputPassword(pass);
		signIn.submitSignIn();
		commonFunc.waitForLoadPage();
	}

	@Step("User validate sso functionality via redirecting from store site to nejm and css site")
	public boolean validateSSO_NEJM_CSSSite() throws Throwable {
		welcomeEmailHeader_StoreSite.waitUntilPresent();
		commonFunc.Launch_URL(widgetBaseUrl);
		welcomeEmailHeader_CssSite.waitUntilPresent();
		boolean cssSiteUserLoggedIn = signInLink_cssSite.isCurrentlyVisible();
		commonFunc.Launch_URL(nejmBaseUrl);
		commonFunc.waitForLoadPage();
		signInLinkNejmSite.waitUntilNotVisible();
		boolean nejmSiteUserLoggedIn = signInLinkNejmSite.isCurrentlyVisible();
		return (!cssSiteUserLoggedIn && !nejmSiteUserLoggedIn) ? true : false;
	}

	@Step("User validate nejm progress bar functionality")
	public boolean validateProgressBar(DataTable dt) throws InterruptedException {
		kMap = CucumberUtils.convert(dt);
		checkFormType(kMap.get("formType"));
		commonFunc.waitForLoadPage();
		submitEmailandContinue(kMap.get("Email"));
		submitCountryandContinue(kMap.get("Country"));
		submitStepThirdandContinue(kMap);
		stepThreeProgressBar.click();
		boolean step3 = selectProfCategory.isCurrentlyVisible();
		stepTwoProgressBar.click();
		boolean step2 = selectCountry.isCurrentlyVisible();
		stepOneProgressBar.click();
		boolean step1 = inputEmail.isCurrentlyVisible();
		return (step1 && step2 && step3) ? true : false;
	}

	@Step("User click on continue reading link in thank you pop up")
	public void clickContinueReadingLink_OnThankYouPopUp() {
		continueReading.waitUntilClickable().click();
		commonFunc.waitForLoadPage();
	}

	@Step("User validate article access")
	public boolean validateArticleAccess() {
		return getFreeAccessNowLink_NejmSite.isCurrentlyVisible();
	}

	@Step("Nejm User sign in on my account page")
	public void myAccountSignIn(String email, String pass) throws Throwable {
		commonFunc.Launch_URL(myAccountBaseUrl);
		myAcc.login(email, pass);
	}

	@Step("User sign out of nejm site")
	public void signOutNejm() {
		nejmUserHeaderMenu.waitUntilClickable().click();
		nejmSiteSignOutLink.waitUntilClickable().click();
	}

	@Step("User sign in on nejm site")
	public void nejmSignIn(String email, String pass) throws Throwable {
		signInLinkNejmSite.waitUntilClickable().click();
		myAcc.login(email, pass);
	}

	@Step("validate new user email address in my account site")
	public String validateNewEmailAddress_MyAccount() throws Throwable {
		commonFunc.launchNewBrowserTab_SameWindow();
		commonFunc.Launch_URL(myAccountBaseUrl);
		commonFunc.waitForLoadPage();
		return accInfo_emailadd.waitUntilVisible().getText();
	}

	@Step("User navigate to catalyst site in new tab")
	public void navigateCatalyst() throws Throwable {
		commonFunc.launchNewBrowserTab_SameWindow();
		commonFunc.Launch_URL(catalystBaseUrl);
	}

	@Step("User click on back to site button on nejm thank you pop up")
	public void clickBackToSiteBtn() {
		LOGGER.info("Clicking --> BACK TO SITE Button on Thank You Pop Up");
		backToSiteBtn.waitUntilClickable().click();
	}

	@Step("User validate if thankyou modal window is closed or not")
	public boolean validateThankYouModalWindow() {
		LOGGER.info("Thank You Modal Visibility - " + thankYouPopUpHeading.isCurrentlyVisible());
		return thankYouPopUpHeading.isCurrentlyVisible() ? true : false;
	}

	@Step("User click on close button on thank you pop up")
	public void clickCloseBtn_ThankYouPopUp() {
		LOGGER.info("Clicking --> 'X' icon on Thank You Pop Up");
		commonFunc.clickElement(closeBtnThankYouPopUp);
	}

	@Step("User fetches design details for thank you modal")
	public void fetchDesign_ThankYouModal(String formType) {
		nejCssMap.clear();

		if (formType.equals("modal")) { // for modal thank you confirmation
			validateThankYouConfirmationDesign(tYMCss);
			cUtil.cssValidator(nejCssMap, tYMCss, backToSiteBtn, "BackToSiteBtn", family, size, color, bgColor);
			nejCssMap.put("ContinueReadingBtn", "false");
		} else {
			validateThankYouConfirmationDesign(tYECss); // for embedded thank you confirmation
			cUtil.cssValidator(nejCssMap, tYECss, continueReading, "ContinueReadingBtn", family, size, color, bgColor);
			nejCssMap.put("BackToSiteBtn", "false");
		}

	}

	private void validateThankYouConfirmationDesign(String file) {
		cUtil.cssValidator(nejCssMap, file, thankYouTitle, "Title", family, size, color);
		cUtil.cssValidator(nejCssMap, file, createAccSuccessMsg, "CRSuccessMessage", family, size, color);
		cUtil.cssValidator(nejCssMap, file, currentAlertMsg, "CurrentAlertsMessage", family, size, color);
		cUtil.cssValidator(nejCssMap, file, weeklyTocList, "WeeklyTOCBullet", family, size, color);
		cUtil.cssValidator(nejCssMap, file, manageAlerts, "ManageAlertsBtn", family, size, color, bgColor);
	}

	@Step("User click outside of gray area of thank you pop up")
	public void clickOutsideThankYouPopUp() {
		commonFunc.clickElement(createAccLinkWidgetSite);
	}

	@Step("User validate alert details")
	public boolean validateAlertDetails(DataTable dt) {
		kMap = CucumberUtils.convert(dt);
		kMap_actual.clear();
		LOGGER.info("Alerts on Nejm Reg - Thank You Confirmation Modal : ");
		LOGGER.info("====================================================");
		kMap_actual.put("Weekly TOC", String.valueOf(weeklyTocList.isCurrentlyVisible()));
		kMap_actual.put("Pediatrics", String.valueOf(pediatrics.isCurrentlyVisible()));
		kMap_actual.put("General Information", String.valueOf(nejmGeneralInfo.isCurrentlyVisible()));
		LOGGER.info("===============Actual Values=======================");
		LOGGER.info(kMap_actual.toString());
		LOGGER.info("---------------------------------------------------");
		LOGGER.info("===============Expected Values=======================");
		LOGGER.info(kMap.toString());
		LOGGER.info("---------------------------------------------------");
		return (kMap.equals(kMap_actual)) ? true : false;
	}

	@Step("User validate nejm logo on thank you modal")
	public boolean validateThankYouLogo() {
		LOGGER.info("===================================================================");
		LOGGER.info("NEJM LOGO On THANK YOU CONFIRMATION" + " : " + nejmLogo.isCurrentlyVisible());
		return nejmLogo.isCurrentlyVisible();
	}
}
