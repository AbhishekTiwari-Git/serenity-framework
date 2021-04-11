package ucc.cr.pages.catalyst.ui;

import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import ucc.pages.ui.CommonFunc;
import ucc.utils.TestUtils;
import ucc.utils.CucumberUtils.CucumberUtils;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CRConnectPage extends PageObject {

	CommonFunc commonFunc = new CommonFunc();
	TestUtils tUtil = new TestUtils();

	private static final Logger LOGGER = LoggerFactory.getLogger(CRConnectPage.class);
	public static Map<String, String> kMap_exp;
	public static Map<String, String> kMap_actual = new HashMap<String, String>();
	public static String country, isGdprCountry, message, soi_ConnectPopUpValue, gdprMsg, user_email, pass, soiAk,
			connectAk;
	public static boolean soi, msg, terms, privacy, nejmTerms, nejmPrivacy, akamaiVal_Soi, akamaiVal_Connect;
	private static String createAccountForm = null;

	@FindBy(xpath = "//ul[@class='main-menu_list']//span[contains(text(),'About')]")
	WebElementFacade aboutLink;

	@FindBy(xpath = "//a[contains(text(),'Stay Informed with NEJM Catalyst Connect')]")
	WebElementFacade connectLink;

	@FindBy(xpath = "//*[starts-with(@id,'uccEmail')]")
	WebElementFacade inputEmail;

	@FindBy(xpath = "//button[contains(text(),'CONTINUE')]")
	WebElementFacade continueBtn;

	@FindBy(xpath = "//*[starts-with(@id,'uccNameOfOrg')]")
	WebElementFacade nameOfOrg;

	@FindBy(xpath = "//*[starts-with(@id,'uccCountry')]")
	WebElementFacade countryDropDown;

	@FindBy(xpath = "//*[starts-with(@id,'uccFirstName')]")
	WebElementFacade firstName;

	@FindBy(xpath = "//*[starts-with(@id,'uccLastName')]")
	WebElementFacade lastName;

	@FindBy(xpath = "//h1[contains(text(),'Thank you')]")
	WebElementFacade thankYouTitle;

	@FindBy(xpath = "//p[contains(text(),'You are now signed up for the NEJM Catalyst Connect email newsletter.')]")
	WebElementFacade connectThankYouMessage;

	@FindBy(xpath = "//div[contains(text(),'Stay on top of the most ')]")
	WebElementFacade connectPopUpHeading;

	@FindBy(xpath = "//button[contains(text(),'SIGN UP')]")
	WebElementFacade signUp;

	@FindBy(xpath = "//span[contains(text(),'Email me information about services or special offers from NEJM Catalyst.')]")
	WebElementFacade soiCheckbox;

	@FindBy(xpath = "//h1[contains(text(),'Stay Informed with NEJM Catalyst Connect')]")
	WebElementFacade stayInformedWithConnect;

	@FindBy(css = "input[name=uccSoi]:checked[type=checkbox]")
	WebElementFacade soiActual;

	@FindBy(xpath = "//p[contains(text(),'You can unsubscribe from emails at any time')]")
	WebElementFacade gdprMessaging;

	@FindBy(xpath = "//div[@class='ucc-gdpr-msg steps']/p[contains(text(),'Please review our')]")
	WebElementFacade gdprMessaging_PrivacyPolicy;

	@FindBy(xpath = "//div[@class='ucc-gdpr-msg lastStep']//a[contains(text(),'Terms')]")
	WebElementFacade gdprTermsLink;

	@FindBy(xpath = "//div[@class='ucc-gdpr-msg lastStep']//a[contains(text(),'Privacy Policy')]")
	WebElementFacade gdprPrivacyPolicyLink;

	@FindBy(xpath = "//h1[contains(text(),'Terms of Use')]")
	WebElementFacade gdprTermsNejmPage;

	@FindBy(xpath = "//h1[contains(text(),'Privacy Policy')]")
	WebElementFacade gdprPrivacyNejmPage;

	@FindBy(xpath = "//a[contains(text(),'Newsletter')]")
	WebElementFacade newsLetterLink;

	@FindBy(xpath = "//div[contains(@id,'ucc-widgets-msg-modal') and contains(@aria-hidden, 'false')]//button")
	WebElementFacade closeThankyouPopUp;

	@Step("Click About Page on NEJM Catalyst Site")
	public void clickAbout() {
		aboutLink.waitUntilClickable().click();
	}

	@Step("Click on Stay Informed with NEJM Catalyst Connect")
	public void clickCatalystConnectLink() {

		connectLink.waitUntilVisible();
		commonFunc.waitForLoadPage();
		commonFunc.clickElement(connectLink);
	}

	@Step("User set gdpr and success message values")
	public void setGdpr_connectSuccessMsg(DataTable dt) {
		kMap_exp = CucumberUtils.convert(dt);
		isGdprCountry = kMap_exp.get("gdpr");
		message = kMap_exp.get("message");
	}

	@Step("User enter email on connect form and click on Continue")
	public void enterEmail_and_continue(String email) {
		connectPopUpHeading.waitUntilVisible();
		stayInformedWithConnect.waitUntilVisible();
		inputEmail.waitUntilClickable().clear();
		inputEmail.sendKeys(email);
		commonFunc.clickElement(continueBtn);
	}

	@Step("User enter connect form values")
	public void setFormValues(DataTable dt) {

		kMap_exp = CucumberUtils.convert(dt);
		firstName.waitUntilClickable().clear();
		firstName.sendKeys(kMap_exp.get("fName"));
		lastName.waitUntilClickable().clear();
		lastName.sendKeys(kMap_exp.get("fName"));
		nameOfOrg.waitUntilClickable().clear();
		nameOfOrg.sendKeys(kMap_exp.get("nameOfOrg"));
		country = kMap_exp.get("Country");
		countryDropDown.waitUntilClickable().selectByValue(country);
	}

	@Step("Gdpr dynamic function for soi checked/unchecked")
	public void gdprDynamicCheck(String soiStatus) {

		switch (soiStatus) {

		case "checked":

			if (isGdprCountry.equals("yes") && soi_ConnectPopUpValue.equals("unchecked")) {
				commonFunc.clickElement(soiCheckbox);
				soi_ConnectPopUpValue = "checked";
				LOGGER.info(
						"For GDPR Country By Default SOI is Unchecked But we're Making it  : " + soi_ConnectPopUpValue);
			}

			break;

		case "unchecked":

			if (isGdprCountry.equals("yes")) {
				LOGGER.info("For GDPR Country - SOI : " + soi_ConnectPopUpValue);
			}

			break;

		default:
			LOGGER.info("Please specify the soi status in data file");
		}
	}

	@Step("Function for dynamicall checking & setting up SOI pop up when checked/unchecked is desired SOI Value")
	public void soiOn_connectDialog(String soiStatus) {

		soi = soiActual.isPresent();
		if (soiStatus.equals("checked")) {
			soi_ConnectPopUpValue = (soi) ? "checked" : "unchecked";
			LOGGER.info("By Default SOI is : " + soi_ConnectPopUpValue);

		} else {
			soi_ConnectPopUpValue = (soi) ? "checked" : "unchecked";
			if (soi_ConnectPopUpValue.equals("checked")) {
				commonFunc.clickElement(soiCheckbox);
				soi_ConnectPopUpValue = "unchecked";
			}
			LOGGER.info("User Update SOI To : " + soi_ConnectPopUpValue);
		}

	}

	@Step("User marks or unmarks the SOI checkbox on connect eLetter form")
	public void check_Uncheck_SOI(String soiStatus) {
		commonFunc.scroll_Mousehover(soiCheckbox);
		soiOn_connectDialog(soiStatus);
		gdprDynamicCheck(soiStatus);
	}

	@Step("User click on Sign up button")
	public void click_signUp() {

		commonFunc.clickElement(signUp);
	}

	@Step("User check GDPR messaging on basis of country selected")
	public String checkGdprMessages() {

		if (isGdprCountry.equals("yes")) {
			gdprMsg = gdprMessaging.waitUntilVisible().getText();
			msg = gdprMessaging.isDisplayed();
		} else {
			msg = gdprMessaging.isCurrentlyVisible();
		}
		LOGGER.info("=========GDPR Message=====================");
		LOGGER.info(gdprMsg);
		LOGGER.info("==========================================");

		return (msg) ? gdprMsg : "Gdpr message will not appear";
	}

	@Step("User check GDPR links on basis of country selected")
	public boolean checkGdprLinks() {

		if (isGdprCountry.equals("yes")) {
			terms = gdprTermsLink.waitUntilClickable().isDisplayed();
			privacy = gdprPrivacyPolicyLink.waitUntilClickable().isDisplayed();
		}

		else {
			terms = !gdprTermsLink.isCurrentlyVisible();
			privacy = !gdprPrivacyPolicyLink.isCurrentlyVisible();

		}

		return (terms && privacy) ? true : false;
	}

	@Step("User check GDPR links Navigation to expected NEJM Pages")
	public boolean checkGdprLinksNavigation() {

		nejmTerms = !gdprTermsNejmPage.isCurrentlyVisible();
		nejmPrivacy = !gdprPrivacyNejmPage.isCurrentlyVisible();

		if (isGdprCountry.equals("yes")) {
			gdprTermsLink.waitUntilClickable().click();
			commonFunc.switchToSecondWindow();
			nejmTerms = gdprTermsNejmPage.waitUntilVisible().isDisplayed();
			commonFunc.DriverClose();
			commonFunc.switchToOriginalWindow();
			gdprPrivacyPolicyLink.waitUntilClickable().click();
			commonFunc.switchToSecondWindow();
			nejmPrivacy = gdprPrivacyNejmPage.waitUntilVisible().isDisplayed();
			commonFunc.switchToOriginalWindow();
		}

		return (nejmTerms && nejmPrivacy) ? true : false;
	}

	@Step("Check Thank You Message Upon Connect eLetter Sign Up")
	public boolean checkMessage() {
		boolean title = thankYouTitle.waitUntilVisible().isDisplayed();
		boolean message = connectThankYouMessage.waitUntilVisible().isDisplayed();
		return (title && message) ? true : false;
	}

	@Step("Check in Akamai the value of Connect and SOI")
	public boolean validateEmailPreferences_InAkamai(Response res) {

		LOGGER.info("======SOI & Connect Values in AKAMAI FOR --> CASE When SOI :" + soi_ConnectPopUpValue);
		if (soi_ConnectPopUpValue.equals("checked")) {

			soiAk = res.jsonPath().getString("emailPreferences.catalystSOI.optIn").toString();
			connectAk = res.jsonPath().getString("emailPreferences.catalystConnect.optIn");
			akamaiVal_Soi = soiAk.equals("true") ? true : false;
			akamaiVal_Connect = connectAk.equals("true") ? true : false;
		}

		else {
			soiAk = res.jsonPath().getString("emailPreferences.catalystSOI.optIn");
			connectAk = res.jsonPath().getString("emailPreferences.catalystConnect.optIn");
			akamaiVal_Soi = (soiAk == null || soiAk.equals("false")) ? true : false;
			akamaiVal_Connect = connectAk.equals("true") ? true : false;
		}

		LOGGER.info("Catalyst SOI --> " + soiAk);
		LOGGER.info("Catalyst Connect --> " + connectAk);
		LOGGER.info("=========================================");

		return (akamaiVal_Soi && akamaiVal_Connect) ? true : false;
	}

	@Step("use click on news letter link from footer")
	public void newsLetterLink_footer() {
		aboutLink.waitUntilPresent();
		newsLetterLink.waitUntilPresent();
		commonFunc.clickElement(newsLetterLink);
	}

	@Step("user submits pre-populated email")
	public void submitPrepopulatedEmail() {
		commonFunc.clickElement(continueBtn);
	}

	@Step("User check GDPR Privacy policy messaging on basis of country selected for lead/registered cookied user")
	public String checkGdprMessage_PrivacyPolicy() {

		if (isGdprCountry.equals("yes")) {
			gdprMsg = gdprMessaging_PrivacyPolicy.waitUntilVisible().getText();
			msg = gdprMessaging_PrivacyPolicy.isDisplayed();
			LOGGER.info("============GDPR Privacy Policy Message===");
			LOGGER.info(gdprMsg);
			LOGGER.info("===========================================");
		} else {
			msg = gdprMessaging_PrivacyPolicy.isCurrentlyVisible();
		}

		return (msg) ? gdprMsg : "Gdpr message will not appear";
	}

	@Step("User closes the thank you pop up of connect eLetter")
	public void closeThankYouPopUp() {
		commonFunc.clickElement(closeThankyouPopUp);
	}

	@Step("User validate pre-populated email")
	public boolean validatePrepopulatedEmail(String email) {
		LOGGER.info("===========Pre-Populated Email Value : Expected======================");
		LOGGER.info(email);
		LOGGER.info("======================================================");
		String prePopulatedEmail = inputEmail.getTextValue();
		LOGGER.info("===========Pre-Populated Email Value : Actual========================");
		LOGGER.info(prePopulatedEmail);
		LOGGER.info("======================================================");
		return (prePopulatedEmail.equals(email) ? true : false);
	}

}
