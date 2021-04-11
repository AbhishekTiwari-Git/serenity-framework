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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyAccountICProfile extends PageObject {

	CommonFunc commonFunc = new CommonFunc();
	JoinIC icFunc = new JoinIC();
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	String message, orgVal, affVal, netVal, dollarVal, yobVal, stateVal, passVal, vendorVal, role, stateName,
			alternateID, KinesisVal, akamaiVal, fiNm, place, bedsVal, sitesVal, phyVal;
	private static final Logger LOGGER = LoggerFactory.getLogger(MyAccountICProfile.class);
	public static Map<String, String> kMap_exp;
	public static Map<String, String> kMap_actual = new HashMap<String, String>();
	public static String backEnd = null;

	@FindBy(xpath = "//*[starts-with(@class,'side-nav-list')]//a[starts-with(@title,'NEJM Catalyst Insights Council')]")
	WebElementFacade icLink;

	@FindBy(xpath = "//*[starts-with(@class,'side-nav-list')]//a[starts-with(@title,'Profile')]")
	WebElementFacade profileLink;

	@FindBy(xpath = "//h2[contains(text(),'Insights Council')]")
	WebElementFacade icProfileTitle;

	@FindBy(xpath = "//p[contains(text(),'Have a voice. Join other health care leaders effecting change, shaping tomorrow.')]")
	WebElementFacade icNotJoinedProfileMessage;

	@FindBy(xpath = "//a[contains(text(),'Join Now')]")
	WebElementFacade joinNowBtn;

	@FindBy(xpath = "//a[contains(text(),'Learn More')]")
	WebElementFacade learnMoreLink;

	@FindBy(xpath = "//button[contains(text(),'Join')]")
	WebElementFacade joinBtn;

	@FindBy(xpath = "//h1[contains(text(),'Thank you!')]")
	WebElementFacade thankYouPopUp;

	@FindBy(xpath = "//a[contains(text(),'Manage Profile and Alerts')]")
	WebElementFacade manageProfileAlertsLink;

	@FindBy(xpath = "//b[contains(text(),'Type of Organization')]/ancestor::td/following-sibling::td")
	WebElementFacade myAccountOrgValue;

	@FindBy(xpath = "//b[contains(text(),'Health System Affiliation')]/ancestor::td/following-sibling::td")
	WebElementFacade healthSystemAffiliationValue;

	@FindBy(xpath = "//b[contains(text(),'Net Patient Revenue')]/ancestor::td/following-sibling::td")
	WebElementFacade netPatientRevenueValue;

	@FindBy(xpath = "//b[contains(text(),'Buying Involvement')]/ancestor::td/following-sibling::td")
	WebElementFacade buyingInvolvementValue;

	@FindBy(xpath = "//b[contains(text(),'Buying Influence')]/ancestor::td/following-sibling::td")
	WebElementFacade buyingInfluenceValue;

	@FindBy(xpath = "//b[contains(text(),'Size of Organization')]/ancestor::td/following-sibling::td")
	WebElementFacade sizeOfOrganization;

	@FindBy(xpath = "//b[contains(text(),'Year of Birth')]/ancestor::td/following-sibling::td")
	WebElementFacade yearOfBirthValue;

	@FindBy(xpath = "//b[contains(text(),'State')]/ancestor::td/following-sibling::td")
	WebElementFacade stateValue;
	
	@FindBy(xpath = "//b[contains(text(),'Phone Number')]")
	WebElementFacade phoneNumber;	

	@FindBy(xpath = "//h3[contains(text(),'Insights Council Profile')]")
	WebElementFacade insightsCouncilProfile;

	@FindBy(xpath = "//h2[contains(text(),'Summary')]")
	WebElementFacade myAccountSummaryPage;

	@FindBy(xpath = "//a[contains(text(),'Institution Administration Center')]")
	WebElementFacade institutionAdministrationLink;

	@FindBy(xpath = "//img[@alt='NEJM Group logo']")
	WebElementFacade nejmGroupLogo;

	@FindBy(xpath = "//p[contains(text(),'Thank you for your participation in the NEJM Catalyst Insights Council.')]")
	WebElementFacade noSurveymsg;

	@FindBy(xpath = "//span[contains(text(),'Edit')]")
	WebElementFacade icProfileEditLink;

	@FindBy(xpath = "//div[@class='surveys']/div[1]//a[contains(text(),'Take Survey')]")
	WebElementFacade takeSurveyLink;

	@FindBy(xpath = "//h1[contains(text(),'It seems you have already entered this survey.')]")
	WebElementFacade surveyNotPresent;

	@FindBy(xpath = "//button[contains(text(),'Save')]")
	WebElementFacade saveChanges;

	@FindBy(xpath = "//span[contains(text(),'Your changes have been saved')]")
	WebElementFacade personalSavedMessage;

	@FindBy(xpath = "//label[contains(text(),'Type of Organization')]/ancestor::div/following-sibling::div/span")
	WebElementFacade orgDropDown;

	@FindBy(xpath = "//label[contains(text(),'Health System Affiliation')]/ancestor::div/following-sibling::div/span")
	WebElementFacade healthSystemAffiliationDropDown;

	@FindBy(xpath = "//label[contains(text(),'Net Patient Revenue')]/ancestor::div/following-sibling::div/span")
	WebElementFacade netPaitentRevenueDropDown;

	@FindBy(xpath = "//label[contains(text(),'Buying Influence')]/ancestor::div/following-sibling::div/span")
	WebElementFacade buyingInfluenceDropDown;

	@FindBy(xpath = "//label[contains(text(),'Year of Birth')]/ancestor::div/following-sibling::div/span")
	WebElementFacade yearOfBirthDropDown;

	@FindBy(xpath = "//label[contains(text(),'State')]/ancestor::div/following-sibling::div/span")
	WebElementFacade stateDropDown;

	@FindBy(xpath = "//*[starts-with(@value,'businessNeed')]")
	WebElementFacade businessNeedCheckBox;

	@FindBy(xpath = "//*[starts-with(@value,'createStrategy')]")
	WebElementFacade strategyCheckBox;

	@FindBy(xpath = "//*[starts-with(@value,'evaluateVendors')]")
	WebElementFacade evaluateVendorCheckBox;

	@FindBy(xpath = "//*[starts-with(@value,'shortList')]")
	WebElementFacade shortlistProductsCheckBox;

	@FindBy(xpath = "//*[starts-with(@value,'recommendSolution')]")
	WebElementFacade recommendSpecifyCheckBox;

	@FindBy(xpath = "//*[starts-with(@value,'authorizePurchase')]")
	WebElementFacade authorizeApproveCheckBox;

	@FindBy(xpath = "//*[starts-with(@value,'none')]")
	WebElementFacade noneOfTheAboveCheckBox;

	@FindBy(xpath = "//span[contains(text(),'For profit') and contains(@class,'jcf-option')]")
	WebElementFacade chooseForProfit;

	@FindBy(xpath = "//span[contains(text(),'Non-profit') and contains(@class,'jcf-option')]")
	WebElementFacade chooseForNonProfit;

	@FindBy(xpath = "//span[contains(text(),'Yes') and contains(@class,'jcf-option')]")
	WebElementFacade chooseYes;

	@FindBy(xpath = "//span[contains(text(),'No') and contains(@class,'jcf-option')]")
	WebElementFacade chooseNo;

	@FindBy(xpath = "//span[contains(text(),'More than $5 billion') and contains(@class,'jcf-option')]")
	WebElementFacade chooseMoreThan5Billion;

	@FindBy(xpath = "//span[contains(text(),'$1 billion to $4.9 billion') and contains(@class,'jcf-option')]")
	WebElementFacade choose1To5Billion;

	@FindBy(xpath = "//span[contains(text(),'$500 million to $999.9 million') and contains(@class,'jcf-option')]")
	WebElementFacade choose500To1000Million;

	@FindBy(xpath = "//span[contains(text(),'$100 million to $499.9 million') and contains(@class,'jcf-option')]")
	WebElementFacade choose100To500Million;

	@FindBy(xpath = "//span[contains(text(),'$10 million to $99.9 million') and contains(@class,'jcf-option')]")
	WebElementFacade choose10To100Million;

	@FindBy(xpath = "//span[contains(text(),'$9.9 million or less') and contains(@class,'jcf-option')]")
	WebElementFacade choose10MillionOrLess;

	@FindBy(xpath = "//span[contains(text(),'None of the above') and contains(@class,'jcf-option')]")
	WebElementFacade chooseNoneOfTheAbove;

	@FindBy(xpath = "//span[contains(text(),'None of the above')]")
	WebElementFacade chooseNoneOfTheAboveCheckBox;

	@FindBy(xpath = "//span[contains(text(),'$500 million+') and contains(@class,'jcf-option')]")
	WebElementFacade choose500Million;

	@FindBy(xpath = "//span[contains(text(),'$25 million to $99.9 million') and contains(@class,'jcf-option')]")
	WebElementFacade choose25to100Million;

	@FindBy(xpath = "//span[contains(text(),'$10 million to $24.9 million') and contains(@class,'jcf-option')]")
	WebElementFacade choose10to25Million;

	@FindBy(xpath = "//span[contains(text(),'$1 million to $9.9 million') and contains(@class,'jcf-option')]")
	WebElementFacade choose1to10Million;

	@FindBy(xpath = "//span[contains(text(),'$100,000 to $999,999') and contains(@class,'jcf-option')]")
	WebElementFacade choose100000to999999;

	@FindBy(xpath = "//span[contains(text(),'Under $100,000') and contains(@class,'jcf-option')]")
	WebElementFacade chooseUnder100000;

	@FindBy(xpath = "//span[contains(text(),'2001') and contains(@class,'jcf-option')]")
	WebElementFacade yearVal1;

	@FindBy(xpath = "//span[contains(text(),'2002') and contains(@class,'jcf-option')]")
	WebElementFacade yearVal2;

	@FindBy(xpath = "//span[contains(text(),'1996') and contains(@class,'jcf-option')]")
	WebElementFacade yearVal3;

	@FindBy(xpath = "//span[contains(text(),'1989') and contains(@class,'jcf-option')]")
	WebElementFacade yearVal4;

	@FindBy(xpath = "//span[contains(text(),'1956') and contains(@class,'jcf-option')]")
	WebElementFacade yearVal5;

	@FindBy(xpath = "//span[contains(text(),'Alabama') and contains(@class,'jcf-option')]")
	WebElementFacade chooseAlabama;

	@FindBy(xpath = "//span[contains(text(),'Massachusetts') and contains(@class,'jcf-option')]")
	WebElementFacade chooseMassachusetts;

	@FindBy(xpath = "//span[contains(text(),'Alaska') and contains(@class,'jcf-option')]")
	WebElementFacade chooseAlaska;

	@FindBy(xpath = "//span[contains(text(),'Indiana') and contains(@class,'jcf-option')]")
	WebElementFacade chooseIndiana;

	@FindBy(xpath = "//span[contains(text(),'Texas') and contains(@class,'jcf-option')]")
	WebElementFacade chooseTexas;
		
	
	@Step("User click on insights council link from left panel under summary")
	public void selectICLink() {
		profileLink.waitUntilVisible();
		icLink.waitUntilClickable().click();
	}

	@Step("Check user is successfully re-directed to My Account Insights Council Profile Page")
	public String validateICProfilePage() {
		boolean title = icProfileTitle.waitUntilVisible().isDisplayed();
		boolean msg = icNotJoinedProfileMessage.waitUntilVisible().isDisplayed();
		message = icNotJoinedProfileMessage.waitUntilVisible().getText();
		String invalidmessage = "No Message Found";
		return ((title && msg) ? message : invalidmessage);
	}

	@Step("Validate that Join Now Button Appears when IC Qualified user visits My Account Insights Council Profile Page")
	public boolean validateJoinNowBtn() {
		boolean btn = joinNowBtn.waitUntilClickable().isDisplayed();
		boolean learnMore = learnMoreLink.waitUntilClickable().isDisplayed();
		return ((btn && learnMore) ? true : false);
	}

	@Step("Validate that if user is non qualified then insights council profile page is not displayed")
	public boolean validateICLink() {
		boolean link = icLink.isCurrentlyVisible();
		return ((link) ? true : false);
	}

	@Step("Fetch question values Common")
	public void fetchQuestionsCommon() {
		dollarVal = kMap_exp.get("dollarQues");
		netVal = kMap_exp.get("netPaitentQues");
		stateName = kMap_exp.get("stateName");
		yobVal = kMap_exp.get("year");
		passVal = kMap_exp.get("createPass");
		orgVal = kMap_exp.get("orgQues");
		affVal = kMap_exp.get("affliationQues");
		stateVal = kMap_exp.get("state");
		vendorVal = kMap_exp.get("vendorQues");
	}

	@Step("Fetch Question Values from data table in to kMap")
	public void getQuesData(DataTable dt) {
		kMap_exp = CucumberUtils.convert(dt);
		fetchQuestionsCommon();
		role = kMap_exp.get("role");
	}

	@Step("Fetch Question Values on basis of Place from data table in to kMap")
	public void getQuesData_place(DataTable dt) {
		kMap_exp = CucumberUtils.convert(dt);
		fetchQuestionsCommon();
		place = kMap_exp.get("place");
		bedsVal = kMap_exp.get("bedsQues");
		sitesVal = kMap_exp.get("sitesQues");
		phyVal = kMap_exp.get("physicianQues");
	}

	@Step("User click on join now button and submits joins form on My Account IC Profile Page")
	public void submitICform() throws InterruptedException {
		commonFunc.clickElement(joinNowBtn);
		icFunc.selectQuestions_Common(orgVal, affVal, netVal, dollarVal, yobVal, stateVal, passVal, vendorVal, role);
		commonFunc.clickElement(joinBtn);
		thankYouPopUp.waitUntilVisible();
		manageProfileAlertsLink.waitUntilClickable().click();
		profileLink.waitUntilVisible();
		commonFunc.waitForLoadPage();
		commonFunc.clickElement(icLink);
		icProfileTitle.waitUntilVisible();
		commonFunc.scroll_Mousehover(icProfileTitle);
		insightsCouncilProfile.waitUntilVisible();
		phoneNumber.waitUntilVisible();
	}

	@Step("Set actual values of My Account IC Profile Page in kMap")
	public void setActualValuesICProfile() {
		kMap_actual.clear();
		setBuyingInfluence();
		setNetPaitent();
		setStateName();
		setYearOfBirth();
		setPass();
		setOrgActual();
		setAffiliation();
		setStateCode();
		setBuyingInvolvement();
		setRole_place();
		String roleVa = (role == null) ? "null" : role;

		if(roleVa.contentEquals("null"))
		{
			setBeds_sites_phy();
		}
		
//		switch (roleVa) {
//		case "null": // this case check questions based upon place.
//			
//			break;
//		default:
//		}
	}

	@Step("Validate submissions of join form on My Account IC Profile Page")
	public boolean validateICProfile_Questions() {
		LOGGER.info("==============IC Profile : TEST RESULTS========================");
		LOGGER.info("=================Actual Values=================================");
		LOGGER.info(kMap_actual.toString());
		LOGGER.info("***************************************************************");
		LOGGER.info("=================Expected Values===============================");
		LOGGER.info(kMap_exp.toString());
		LOGGER.info("---------------------------------------------------------------");
		return (kMap_actual.equals(kMap_exp)) ? true : false;
	}

	@Step("Set Actual value of Organization Question on My Account IC Profile Page in to Map")
	public void setOrgActual() {
		kMap_actual.put("orgQues", myAccountOrgValue.waitUntilVisible().getText());
	}

	@Step("Set Affiliation Question on My Account IC Profile Page in to Map")
	public void setAffiliation() {
		kMap_actual.put("affliationQues", healthSystemAffiliationValue.waitUntilVisible().getText());
	}

	@Step("Set Net Paitent Question on My Account IC Profile Page in to Map")
	public void setNetPaitent() {
		kMap_actual.put("netPaitentQues", netPatientRevenueValue.waitUntilVisible().getText());
	}

	@Step("Set Buying Involvement (Vendor) Question on My Account IC Profile Page in to Map")
	public void setBuyingInvolvement() {
		String val = null;
		String roleVa = (role == null) ? "null" : role;
		switch (roleVa) {
		case "CLI":
			val = !buyingInfluenceValue.isCurrentlyVisible() ? "Not Present" : "Present";
			kMap_actual.put("vendorQues", val);
			break;

		case "OTH":
			val = !buyingInfluenceValue.isCurrentlyVisible() ? "Not Present" : "Present";
			kMap_actual.put("vendorQues", val);
			break;

		case "null":
			kMap_actual.put("vendorQues", buyingInvolvementValue.waitUntilVisible().getText());
			break;

		default:
			kMap_actual.put("vendorQues", buyingInvolvementValue.waitUntilVisible().getText());
		}
	}

	@Step("Set Beds Question on My Account IC Profile Page in to Map")
	public void setBeds_sites_phy() {
		String val = null;
		switch (place) {

		case "HOU":
			String bed = sizeOfOrganization.getText().replace("Beds: ", "");
			val = !sizeOfOrganization.isCurrentlyVisible() ? "Not Present" : bed;
			kMap_actual.put("bedsQues", val);
			kMap_actual.put("sitesQues", "Not Present");
			kMap_actual.put("physicianQues", "Not Present");
			break;

		case "HOC":
			bed = sizeOfOrganization.getText().replace("Beds: ", "");
			val = !sizeOfOrganization.isCurrentlyVisible() ? "Not Present" : bed;
			kMap_actual.put("bedsQues", val);
			kMap_actual.put("sitesQues", "Not Present");
			kMap_actual.put("physicianQues", "Not Present");
			break;

		case "HSM":
			String sites = sizeOfOrganization.getText().replace("Sites: ", "");
			val = !sizeOfOrganization.isCurrentlyVisible() ? "Not Present" : sites;
			kMap_actual.put("bedsQues", "Not Present");
			kMap_actual.put("sitesQues", val);
			kMap_actual.put("physicianQues", "Not Present");
			break;

		case "LTC":
			bed = sizeOfOrganization.getText().replace("Beds: ", "");
			val = !sizeOfOrganization.isCurrentlyVisible() ? "Not Present" : bed;
			kMap_actual.put("bedsQues", val);
			kMap_actual.put("sitesQues", "Not Present");
			kMap_actual.put("physicianQues", "Not Present");
			break;

		case "HOO":
			bed = sizeOfOrganization.getText().replace("Beds: ", "");
			val = !sizeOfOrganization.isCurrentlyVisible() ? "Not Present" : bed;
			kMap_actual.put("bedsQues", val);
			kMap_actual.put("sitesQues", "Not Present");
			kMap_actual.put("physicianQues", "Not Present");
			break;

		case "PHO":
			String phy = sizeOfOrganization.getText().replace("Physicians: ", "");
			val = !sizeOfOrganization.isCurrentlyVisible() ? "Not Present" : phy;
			kMap_actual.put("bedsQues", "Not Present");
			kMap_actual.put("sitesQues", "Not Present");
			kMap_actual.put("physicianQues", phy);
			break;

		default:
			val = !sizeOfOrganization.isCurrentlyVisible() ? "Not Present" : "Present";
			kMap_actual.put("bedsQues", val);
			kMap_actual.put("sitesQues", val);
			kMap_actual.put("physicianQues", val);

		}
	}

	@Step("Set Buying Influence (Dollar) Question on My Account IC Profile Page in to Map")
	public void setBuyingInfluence() {
		String val = null;

		String roleVa = (role == null) ? "null" : role;
		switch (roleVa) {

		case "CLI":
			val = !buyingInfluenceValue.isCurrentlyVisible() ? "Not Present" : "Present";
			kMap_actual.put("dollarQues", val);
			break;

		case "OTH":
			val = !buyingInfluenceValue.isCurrentlyVisible() ? "Not Present" : "Present";
			kMap_actual.put("dollarQues", val);
			break;

		case "null":
			kMap_actual.put("dollarQues", buyingInfluenceValue.waitUntilVisible().getText());
			break;

		default:
			kMap_actual.put("dollarQues", buyingInfluenceValue.waitUntilVisible().getText());
		}

	}

	@Step("Set Year of Birth on My Account IC Profile Page in to Map")
	public void setYearOfBirth() {
		kMap_actual.put("year", yearOfBirthValue.waitUntilVisible().getText());
	}

	@Step("Set State Name on My Account IC Profile Page in to Map")
	public void setStateName() {

		switch (stateVal) {
		case "null":
			String val = !stateValue.isCurrentlyVisible() ? "null" : "state drop down appearing";
			kMap_actual.put("stateName", val);
			break;

		default:
			kMap_actual.put("stateName", stateValue.waitUntilVisible().getText());
		}

	}

	@Step("Set Actual State Code As Per State Appearing on My Account IC Profile Page in to Map")
	public void setStateCode() {
		switch (stateVal) {
		case "null":
			kMap_actual.put("state", "null");
			break;

		default:
			String stateName = stateValue.waitUntilVisible().getText();

			switch (stateName) {

			case "Massachusetts":
				kMap_actual.put("state", "MA");
				break;

			case "Alabama":
				kMap_actual.put("state", "AL");
				break;

			case "Alaska":
				kMap_actual.put("state", "AK");
				break;

			case "Texas":
				kMap_actual.put("state", "TX");
				break;

			case "Indiana":
				kMap_actual.put("state", "IN");
				break;

			default:
				LOGGER.info("By Default - State Code Added to Actual Values Map is of Massachusetts");
				kMap_actual.put("state", "MA");
			}
		}
	}

	@Step("Set Pass on My Account IC Profile Page")
	public void setPass() {
		kMap_actual.put("createPass", passVal);
	}

	@Step("Validate Role or place on My Account IC Profile Page")
	public void setRole_place() {

		String role_place = (role == null) ? "null" : role; 
		switch (role_place) {
		case "null":
			kMap_actual.put("place", place);
			break;

		default:
			kMap_actual.put("role", role);
		}
	}

	@Step("Check user is able to view IC Profile page with questions details and without survey")
	public boolean validateICProfile_withoutSurvey() {
		boolean profile = insightsCouncilProfile.waitUntilVisible().isDisplayed();
		boolean noSurvey = noSurveymsg.waitUntilVisible().isDisplayed();
		return (profile && noSurvey) ? true : false;
	}

	@Step("Check user is able to view IC Profile page with questions details and with event survey")
	public boolean validateICProfile_withSurvey() {
		boolean profile = insightsCouncilProfile.waitUntilVisible().isDisplayed();
		boolean Survey = takeSurveyLink.waitUntilVisible().isDisplayed();
		return (profile && Survey) ? true : false;
	}

	@Step("User should be able to view the survey and survey already entered message should not be displayed")
	public boolean surveyLaunched() {
		takeSurveyLink.waitUntilClickable().click();
		commonFunc.switchToSecondWindow();
		boolean surveyLaunched = surveyNotPresent.isCurrentlyVisible();
		return !surveyLaunched ? true : false;
	}

	@Step("Check user is able to edit or update IC Profile page")
	public void editICProfileQuestions() {
		icProfileEditLink.waitUntilClickable().click();
		selectProfileValues(orgVal, affVal, netVal, dollarVal, yobVal, stateName, vendorVal);
		commonFunc.clickElement(saveChanges);
		personalSavedMessage.waitUntilVisible();
	}

	@Step("User select profile values")
	public void selectProfileValues(String orgVal, String affVal, String netVal, String dollarVal, String yobVal,
			String stateName, String vendorVal) {
		myAcc_selectOrgValue(orgVal);
		myAcc_selectAffliation(affVal);
		myAcc_selectNetPaitent(netVal);
		myAcc_selectDollar(dollarVal);
		myAcc_selectYob(yobVal);
		myAcc_selectState(stateName);
		myAcc_selectVendor(vendorVal);
	}

	@Step("User select Org Question value on MyAccount Insights Council Profile Page")
	public void myAcc_selectOrgValue(String orgVal) {
		switch (orgVal) {
		case "For profit":
			orgDropDown.waitUntilClickable().click();
			chooseForProfit.waitUntilClickable().click();
			break;

		case "Non-profit":
			orgDropDown.waitUntilClickable().click();
			chooseForNonProfit.waitUntilClickable().click();
			break;

		default:
			LOGGER.info("No Value Specified : Please specify value for Type of Organization");
		}
	}

	@Step("User select Health System Affliation Question value on MyAccount Insights Council Profile Page")
	public void myAcc_selectAffliation(String affVal) {
		switch (affVal) {
		case "Yes":
			healthSystemAffiliationDropDown.waitUntilClickable().click();
			chooseYes.waitUntilClickable().click();
			break;

		case "No":
			healthSystemAffiliationDropDown.waitUntilClickable().click();
			chooseNo.waitUntilClickable().click();
			break;

		default:

			LOGGER.info("No Value Specified : Please specify value for Health System Affiliation");
		}
	}

	@Step("User select Net Paitent Question value on MyAccount Insights Council Profile Page")
	public void myAcc_selectNetPaitent(String netVal) {
		switch (netVal) {
		case "More than $5 billion":
			netPaitentRevenueDropDown.waitUntilClickable().click();
			chooseMoreThan5Billion.waitUntilClickable().click();
			break;

		case "$1 billion to $4.9 billion":
			netPaitentRevenueDropDown.waitUntilClickable().click();
			choose1To5Billion.waitUntilClickable().click();
			break;

		case "$500 million to $999.9 million":
			netPaitentRevenueDropDown.waitUntilClickable().click();
			choose500To1000Million.waitUntilClickable().click();
			break;

		case "$100 million to $499.9 million":
			netPaitentRevenueDropDown.waitUntilClickable().click();
			choose100To500Million.waitUntilClickable().click();
			break;

		case "$10 million to $99.9 million":
			netPaitentRevenueDropDown.waitUntilClickable().click();
			choose10To100Million.waitUntilClickable().click();
			break;

		case "$9.9 million or less":
			netPaitentRevenueDropDown.waitUntilClickable().click();
			choose10MillionOrLess.waitUntilClickable().click();
			break;

		case "None of the above":
			netPaitentRevenueDropDown.waitUntilClickable().click();
			chooseNoneOfTheAbove.waitUntilClickable().click();
			break;

		default:
			LOGGER.info("No Value Specified : Please specify value for Net Patient Revenue");
		}
	}

	@Step("User select Dollar Question value on MyAccount Insights Council Profile Page")
	public void myAcc_selectDollar(String dollarVal) {
		switch (dollarVal) {
		case "$500 million+":
			buyingInfluenceDropDown.waitUntilClickable().click();
			choose500Million.waitUntilClickable().click();
			break;

		case "$100 million to $499.9 million":
			buyingInfluenceDropDown.waitUntilClickable().click();
			choose100To500Million.waitUntilClickable().click();
			break;

		case "$25 million to $99.9 million":
			buyingInfluenceDropDown.waitUntilClickable().click();
			choose25to100Million.waitUntilClickable().click();
			break;

		case "$10 million to $24.9 million":
			buyingInfluenceDropDown.waitUntilClickable().click();
			choose10to25Million.waitUntilClickable().click();
			break;

		case "$1 million to $9.9 million":
			buyingInfluenceDropDown.waitUntilClickable().click();
			choose1to10Million.waitUntilClickable().click();
			break;

		case "$100,000 to $999,999":
			buyingInfluenceDropDown.waitUntilClickable().click();
			choose100000to999999.waitUntilClickable().click();
			break;

		case "Under $100,000":
			buyingInfluenceDropDown.waitUntilClickable().click();
			chooseUnder100000.waitUntilClickable().click();
			break;

		case "None of the above":
			buyingInfluenceDropDown.waitUntilClickable().click();
			chooseNoneOfTheAbove.waitUntilClickable().click();
			break;

		default:
			LOGGER.info("No Value Specified : Please specify value for Buying Influence");
		}
	}

	@Step("User select year of birth value on MyAccount Insights Council Profile Page")
	public void myAcc_selectYob(String yobVal) {
		switch (yobVal) {

		case "2001":
			yearOfBirthDropDown.waitUntilClickable().click();
			yearVal1.waitUntilClickable().click();
			break;

		case "2002":
			yearOfBirthDropDown.waitUntilClickable().click();
			yearVal2.waitUntilClickable().click();
			break;

		case "1996":
			yearOfBirthDropDown.waitUntilClickable().click();
			yearVal3.waitUntilClickable().click();
			break;

		case "1989":
			yearOfBirthDropDown.waitUntilClickable().click();
			yearVal4.waitUntilClickable().click();
			break;

		case "1956":
			yearOfBirthDropDown.waitUntilClickable().click();
			yearVal5.waitUntilClickable().click();
			break;

		default:
			yearOfBirthDropDown.waitUntilClickable().click();
			yearVal1.waitUntilClickable().click();
		}
	}

	@Step("User select state Name on MyAccount Insights Council Profile Page")
	public void myAcc_selectState(String stateName) {
		switch (stateName) {

		case "Alabama":
			stateDropDown.waitUntilClickable().click();
			chooseAlabama.waitUntilClickable().click();
			break;

		case "Alaska":
			stateDropDown.waitUntilClickable().click();
			chooseAlaska.waitUntilClickable().click();
			break;

		case "Massachusetts":
			stateDropDown.waitUntilClickable().click();
			chooseMassachusetts.waitUntilClickable().click();
			break;

		case "Texas":
			stateDropDown.waitUntilClickable().click();
			chooseTexas.waitUntilClickable().click();
			break;

		case "Indiana":
			stateDropDown.waitUntilClickable().click();
			chooseIndiana.waitUntilClickable().click();
			break;

		default:
			stateDropDown.waitUntilClickable().click();
			chooseMassachusetts.waitUntilClickable().click();
		}
	}

	@Step("User select Vendor Question Value on MyAccount Insights Council Profile Page")
	public void myAcc_selectVendor(String vendorVal) {
		switch (vendorVal) {

		case "Determine the business need":
			removePreselectedCheckbox();
			businessNeedCheckBox.waitUntilEnabled();
			commonFunc.clickElement(businessNeedCheckBox);
			break;

		case "Create the strategy and determine the solution":
			removePreselectedCheckbox();
			strategyCheckBox.waitUntilEnabled();
			commonFunc.clickElement(strategyCheckBox);
			break;

		case "Evaluate vendors that can provide the solution":
			removePreselectedCheckbox();
			evaluateVendorCheckBox.waitUntilEnabled();
			commonFunc.clickElement(evaluateVendorCheckBox);
			break;

		case "Create the short list of products/vendors to review":
			removePreselectedCheckbox();
			shortlistProductsCheckBox.waitUntilEnabled();
			commonFunc.clickElement(shortlistProductsCheckBox);
			break;

		case "Recommend/specify the brands/vendors for purchase":
			removePreselectedCheckbox();
			recommendSpecifyCheckBox.waitUntilEnabled();
			commonFunc.clickElement(recommendSpecifyCheckBox);
			break;

		case "Authorize/approve the purchase of the solution":
			removePreselectedCheckbox();
			authorizeApproveCheckBox.waitUntilEnabled();
			commonFunc.clickElement(authorizeApproveCheckBox);
			break;

		case "None of the above":
			removePreselectedCheckbox();
			chooseNoneOfTheAboveCheckBox.waitUntilEnabled();
			commonFunc.clickElement(chooseNoneOfTheAboveCheckBox);
			break;

		default:
			LOGGER.info("No Value Specified : Please specify value for Buying Involvement");
		}
	}

	@Step("Remove pre-selected checkbox for vendor")
	public void removePreselectedCheckbox() {
		chooseNoneOfTheAboveCheckBox.waitUntilClickable().click();
	}

	@Step("User validates changes in Akamai")
	public boolean validateICProfileChangesInBackend() {
		LOGGER.info("=======================Actual Values================================");
		LOGGER.info(kMap_actual.toString());
		LOGGER.info("********************************************************************");
		LOGGER.info("======================Expected Values===============================");
		LOGGER.info(kMap_exp.toString());
		LOGGER.info("=====================================================================");
		return (kMap_actual.equals(kMap_exp)) ? true : false;
	}

	@Step("Set Values of Akamai - Present - Post Update/Edit on My Account Profile Page in a Map")
	public void setActualValuesAkamai(Response sysResp) {
		backEnd = "Akamai";
		alternateID = sysResp.jsonPath().getString("uuid");
		dollarQues_akamai(sysResp);
		kMap_actual.put("dollarQues", akamaiVal);
		kMap_actual.put("netPaitentQues", sysResp.jsonPath().getString("Catalyst.netPatientRevenue"));
		stateName_backend(sysResp);
		kMap_actual.put("stateName", akamaiVal);
		kMap_actual.put("year", sysResp.jsonPath().getString("birthYear"));
		kMap_actual.put("createPass", passVal);
		kMap_actual.put("orgQues", sysResp.jsonPath().getString("Catalyst.profitStatus"));
		healthSystemAffliation(sysResp);
		kMap_actual.put("affliationQues", akamaiVal);
		stateCd_backend(sysResp);
		kMap_actual.put("state", akamaiVal);
		buyingInvolvement(sysResp);
		kMap_actual.put("vendorQues", akamaiVal);
		place_Role_backend(sysResp);
		sites_phy_beds_backend(sysResp);
		LOGGER.info("=======================TEST RESULTS================================");
		LOGGER.info("================BACKEND VALIDATION : AKAMAI========================");
	}

	@Step("Fetch Akamai Values")
	public void fetchActualValuesAkamai(Response sysResp) {
		setActualValuesAkamai(sysResp);
	}

	@Step("Fetch Kinesis Values")
	public void fetchActualValuesKinesis(Response Resp, String fileName) throws Throwable {
		fiNm = fileName;
		setActualValuesKinesis(Resp);
	}

	@Step("Set Values of Kinesis - Present - Post Update/Edit on My Account Profile Page in a Map")
	public void setActualValuesKinesis(Response Resp) {
		backEnd = "Kinesis";
		dollarQues_Kinesis(Resp);
		kMap_actual.put("dollarQues", KinesisVal);
		netPaitentQues_backend(Resp);
		kMap_actual.put("netPaitentQues", KinesisVal);
		stateName_backend(Resp);
		kMap_actual.put("stateName", KinesisVal);
		kMap_actual.put("year", Resp.jsonPath().getString("yearOfBirth"));
		kMap_actual.put("createPass", passVal);
		org_backend(Resp);
		kMap_actual.put("orgQues", KinesisVal);
		healthSystemAffliation(Resp);
		kMap_actual.put("affliationQues", KinesisVal);
		stateCd_backend(Resp);
		kMap_actual.put("state", KinesisVal);
		buyingInvolvement(Resp);
		kMap_actual.put("vendorQues", KinesisVal);
		place_Role_backend(Resp);
		sites_phy_beds_backend(Resp);
		LOGGER.info("=======================TEST RESULTS================================");
		LOGGER.info("================BACKEND VALIDATION : KINESIS========================");
	}

	@Step("User check state code in Backend")
	public void stateCd_backend(Response sysResp) {

		switch (backEnd) {

		case "Akamai":
			String resAk = (sysResp.jsonPath().getString("Catalyst.state") == null) ? "null" : stateVal;
			akamaiVal = (resAk.equals(stateVal)) ? stateVal : "(Failed - Value for : " + resAk + ")";
			break;

		case "Kinesis":
			String exp_resKi = jsonUtils.getFromJSON(fiNm, "['state']['" + stateName + "']").toString();
			String actual_reski = sysResp.jsonPath().getString("state").toString();
			KinesisVal = (exp_resKi.equals(actual_reski)) ? stateVal
					: "(Fail - Incorrect stateCd Appearing for : " + stateName + ")";
			break;
		}

	}

	@Step("User check dollar ques in Akamai")
	public void dollarQues_akamai(Response resp) {
		switch (dollarVal) {
		case "Not Present":
			akamaiVal = "Not Present";
			break;
		default:
			akamaiVal = resp.jsonPath().getString("Catalyst.buyingInfluence");
		}
	}

	@Step("User check dollar ques in Kinesis")
	public void dollarQues_Kinesis(Response sysResp) {

		switch (dollarVal) {
		case "$500 million+":
			KinesisVal = "1";
			break;

		case "$100 million to $499.9 million":
			KinesisVal = "2";
			break;

		case "$25 million to $99.9 million":
			KinesisVal = "3";
			break;

		case "$10 million to $24.9 million":
			KinesisVal = "4";
			break;

		case "$1 million to $9.9 million":
			KinesisVal = "5";
			break;

		case "$100,000 to $999,999":
			KinesisVal = "6";
			break;

		case "Under $100,000":
			KinesisVal = "7";
			break;

		case "None of the above":
			KinesisVal = "8";
			break;

		case "Not Present":
			KinesisVal = "0";
			break;

		default:
			LOGGER.info("No Value Specified : Please specify value for Buying Influence in Data file");
		}
		String exp_resKi = KinesisVal;
		String actual_reski = sysResp.jsonPath().getString("amountProductServices").toString();
		KinesisVal = (exp_resKi.equals(actual_reski)) ? dollarVal : "(Failed - Value for : " + dollarVal + ")";
	}

	@Step("User check net-Paitent ques in Backend")
	public void netPaitentQues_backend(Response sysResp) {
		String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['netPatientRevenue']['" + netVal + "']").toString();
		String actual_reski = sysResp.jsonPath().getString("netPatientRevenue").toString();
		KinesisVal = (exp_resKi.equals(actual_reski)) ? netVal : "(Failed - Value for : " + netVal + ")";
	}

	@Step("User check place or role in backend")
	public void place_Role_backend(Response resp) {
		String role_place = (role == null) ? "null" : role; 
		switch (role_place) {
		case "null":

			switch (backEnd) {
			case "Akamai":
				kMap_actual.put("place", resp.jsonPath().getString("placeOfWorkOrStudyCode"));
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['organizationType']['" + place + "']").toString();
				String actual_reski = resp.jsonPath().getString("organizationType").toString();
				kMap_actual.put("place",
						((exp_resKi.equals(actual_reski)) ? place : "(Failed - Value for : " + place + ")"));
				break;
			}
			break;

		default:

			switch (backEnd) {
			case "Akamai":
				kMap_actual.put("role", resp.jsonPath().getString("roleCode"));
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['title']['" + role + "']").toString();
				String actual_reski = resp.jsonPath().getString("title").toString();
				KinesisVal = (exp_resKi.equals(actual_reski)) ? role : "(Failed - Value for : " + role + ")";

				break;
			}

		}
	}

	@Step("Check questions for beds, Sites and physician in backend")
	public void sites_phy_beds_backend(Response resP) {
		String placeValue = (place == null) ? "null" : place;
		switch (placeValue) {

		case "HOU":
			switch (backEnd) {
			case "Akamai":
				kMap_actual.put("bedsQues", resP.jsonPath().getString("Catalyst.hospitalSize"));
				kMap_actual.put("sitesQues", "Not Present");
				kMap_actual.put("physicianQues", "Not Present");
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['hospitalSize']['" + bedsVal + "']").toString();
				String actual_reski = resP.jsonPath().getString("hospitalSize").toString();
				kMap_actual.put("bedsQues",
						((exp_resKi.equals(actual_reski)) ? bedsVal : "(Failed - Value for : " + bedsVal + ")"));
				kMap_actual.put("sitesQues", "Not Present");
				kMap_actual.put("physicianQues", "Not Present");
				break;
			}

			break;

		case "HOC":

			switch (backEnd) {
			case "Akamai":
				kMap_actual.put("bedsQues", resP.jsonPath().getString("Catalyst.hospitalSize"));
				kMap_actual.put("sitesQues", "Not Present");
				kMap_actual.put("physicianQues", "Not Present");
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['hospitalSize']['" + bedsVal + "']").toString();
				String actual_reski = resP.jsonPath().getString("hospitalSize").toString();
				kMap_actual.put("bedsQues",
						((exp_resKi.equals(actual_reski)) ? bedsVal : "(Failed - Value for : " + bedsVal + ")"));
				kMap_actual.put("sitesQues", "Not Present");
				kMap_actual.put("physicianQues", "Not Present");
				break;
			}

			break;

		case "HSM":

			switch (backEnd) {
			case "Akamai":
				kMap_actual.put("bedsQues", "Not Present");
				kMap_actual.put("sitesQues", resP.jsonPath().getString("Catalyst.healthSystemSize"));
				kMap_actual.put("physicianQues", "Not Present");
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['healthSystemSize']['" + sitesVal + "']").toString();
				String actual_reski = resP.jsonPath().getString("healthSystemSize").toString();
				kMap_actual.put("bedsQues", "Not Present");
				kMap_actual.put("sitesQues",
						((exp_resKi.equals(actual_reski)) ? sitesVal : "(Failed - Value for : " + sitesVal + ")"));
				kMap_actual.put("physicianQues", "Not Present");
				break;
			}

			break;

		case "LTC":

			switch (backEnd) {
			case "Akamai":
				kMap_actual.put("bedsQues", resP.jsonPath().getString("Catalyst.hospitalSize"));
				kMap_actual.put("sitesQues", "Not Present");
				kMap_actual.put("physicianQues", "Not Present");
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['hospitalSize']['" + bedsVal + "']").toString();
				String actual_reski = resP.jsonPath().getString("hospitalSize").toString();
				kMap_actual.put("bedsQues",
						((exp_resKi.equals(actual_reski)) ? bedsVal : "(Failed - Value for : " + bedsVal + ")"));
				kMap_actual.put("sitesQues", "Not Present");
				kMap_actual.put("physicianQues", "Not Present");
				break;
			}

			break;

		case "HOO":

			switch (backEnd) {
			case "Akamai":
				kMap_actual.put("bedsQues", resP.jsonPath().getString("Catalyst.hospitalSize"));
				kMap_actual.put("sitesQues", "Not Present");
				kMap_actual.put("physicianQues", "Not Present");
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['hospitalSize']['" + bedsVal + "']").toString();
				String actual_reski = resP.jsonPath().getString("hospitalSize").toString();
				kMap_actual.put("bedsQues",
						((exp_resKi.equals(actual_reski)) ? bedsVal : "(Failed - Value for : " + bedsVal + ")"));
				kMap_actual.put("sitesQues", "Not Present");
				kMap_actual.put("physicianQues", "Not Present");
				break;
			}

			break;

		case "PHO":

			switch (backEnd) {
			case "Akamai":
				kMap_actual.put("bedsQues", "Not Present");
				kMap_actual.put("sitesQues", "Not Present");
				kMap_actual.put("physicianQues", resP.jsonPath().getString("Catalyst.physicianOrgSize"));
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['sizePhysOrg']['" + phyVal + "']").toString();
				String actual_reski = resP.jsonPath().getString("sizePhysOrg").toString();
				kMap_actual.put("bedsQues", "Not Present");
				kMap_actual.put("sitesQues", "Not Present");
				kMap_actual.put("physicianQues",
						((exp_resKi.equals(actual_reski)) ? phyVal : "(Failed - Value for : " + phyVal + ")"));
				break;
			}

			break;

		case "null":
			LOGGER.info("Backend Validation Completed for Role");
			break;

		default:

			kMap_actual.put("bedsQues", "Not Present");
			kMap_actual.put("sitesQues", "Not Present");
			kMap_actual.put("physicianQues", "Not Present");
		}
	}

	@Step("User check organization Ques value in backend")
	public void org_backend(Response sysResp) {
		String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['profitStatus']['" + orgVal + "']").toString();
		String actual_reski = sysResp.jsonPath().getString("profitStatus").toString();
		KinesisVal = (exp_resKi.equals(actual_reski)) ? orgVal : "(Failed - Value for : " + orgVal + ")";
	}

	@Step("User check State Name in Backend")
	public void stateName_backend(Response sysResp) {
		String resAk = "null";
		switch (stateName) { // nested switch

		case "Alabama":

			switch (backEnd) {
			case "Akamai":
				resAk = sysResp.jsonPath().getString("Catalyst.state");
				akamaiVal = (resAk.equals("AL")) ? stateName : "(Failed - Value for : " + resAk + ")";
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['state']['" + stateName + "']").toString();
				String actual_reski = sysResp.jsonPath().getString("state").toString();
				KinesisVal = (exp_resKi.equals(actual_reski)) ? stateName : "(Failed - Value for : " + stateName + ")";
				break;
			}

			break;

		case "Alaska":

			switch (backEnd) {

			case "Akamai":
				resAk = sysResp.jsonPath().getString("Catalyst.state");
				akamaiVal = (resAk.equals("AK")) ? stateName : "(Failed - Value for : " + resAk + ")";
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['state']['" + stateName + "']").toString();
				String actual_reski = sysResp.jsonPath().getString("state").toString();
				KinesisVal = (exp_resKi.equals(actual_reski)) ? stateName : "(Failed - Value for : " + stateName + ")";
			}
			break;

		case "Massachusetts":

			switch (backEnd) {
			case "Akamai":
				resAk = sysResp.jsonPath().getString("Catalyst.state");
				akamaiVal = (resAk.equals(stateVal)) ? stateName : "(Failed - Value for : " + resAk + ")";
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['state']['" + stateName + "']").toString();
				String actual_reski = sysResp.jsonPath().getString("state").toString();
				KinesisVal = (exp_resKi.equals(actual_reski)) ? stateName : "(Failed - Value for : " + stateName + ")";
				break;
			}

			break;

		case "Texas":

			switch (backEnd) {
			case "Akamai":
				resAk = sysResp.jsonPath().getString("Catalyst.state");
				akamaiVal = (resAk.equals("TX")) ? stateName : "(Failed - Value for : " + resAk + ")";
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['state']['" + stateName + "']").toString();
				String actual_reski = sysResp.jsonPath().getString("state").toString();
				KinesisVal = (exp_resKi.equals(actual_reski)) ? stateName : "(Failed - Value for : " + stateName + ")";
				break;
			}
			break;

		case "Indiana":

			switch (backEnd) {
			case "Akamai":
				resAk = sysResp.jsonPath().getString("Catalyst.state");
				akamaiVal = (resAk.equals("IN")) ? stateName : "(Failed - Value for : " + resAk + ")";
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['state']['" + stateName + "']").toString();
				String actual_reski = sysResp.jsonPath().getString("state").toString();
				KinesisVal = (exp_resKi.equals(actual_reski)) ? stateName : "(Failed - Value for : " + stateName + ")";
				break;
			}
			break;

		default:
			switch (backEnd) {
			case "Akamai":
				resAk = ((sysResp.jsonPath().getString("Catalyst.state")) == null) ? "null" : "Not Null";
				akamaiVal = (resAk.equals(stateName)) ? stateName : "(Failed - Value for : " + resAk + ")";
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['state']['" + stateName + "']").toString();
				String actual_reski = sysResp.jsonPath().getString("state").toString();
				KinesisVal = (exp_resKi.equals(actual_reski)) ? stateName : "(Failed - Value for : " + stateName + ")";
				break;
			}
		}
	}

	@Step("User check Health System Affliation values in Backend")
	public void healthSystemAffliation(Response sysResp) {
		String resAk = "null";
		switch (affVal) {

		case "Yes":

			switch (backEnd) {

			case "Akamai":
				resAk = sysResp.jsonPath().getString("Catalyst.healthSystemAffiliation");
				akamaiVal = (resAk.equals("true")) ? affVal : "(Failed - Value for : " + resAk + ")";
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['hsAffiliation']['" + affVal + "']").toString();
				String actual_reski = sysResp.jsonPath().getString("hsAffiliation").toString();
				KinesisVal = (exp_resKi.equals(actual_reski)) ? affVal : "(Failed - Value for : " + affVal + ")";
				break;
			}

			break;

		case "No":

			switch (backEnd) {

			case "Akamai":
				resAk = sysResp.jsonPath().getString("Catalyst.healthSystemAffiliation");
				akamaiVal = (resAk.equals("false")) ? affVal : "(Failed - Value for : " + resAk + ")";
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['hsAffiliation']['" + affVal + "']").toString();
				String actual_reski = sysResp.jsonPath().getString("hsAffiliation").toString();
				KinesisVal = (exp_resKi.equals(actual_reski)) ? affVal : "(Failed - Value for : " + affVal + ")";
				break;
			}

			break;

		default:

			LOGGER.info("No Value Specified : Please specify value for Health System Affiliation");
		}
	}

	@Step("User check buying involvement values in Backend")
	public void buyingInvolvement(Response sysResp) {
		String resAk = "null";
		switch (vendorVal) {

		case "Determine the business need":

			switch (backEnd) {

			case "Akamai":
				resAk = sysResp.jsonPath().getString("Catalyst.buyingInvolvement.businessNeed");
				akamaiVal = (resAk.equals("true")) ? vendorVal : "(Failed - Value for : " + resAk + ")";
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "['vendorSelection']['" + vendorVal + "']").toString();
				String actual_reski = sysResp.jsonPath().getString("vendorSelection").toString();
				KinesisVal = (exp_resKi.equals(actual_reski)) ? vendorVal : "(Failed - Value for : " + vendorVal + ")";
				break;
			}

			break;

		case "Create the strategy and determine the solution":

			switch (backEnd) {

			case "Akamai":
				resAk = sysResp.jsonPath().getString("Catalyst.buyingInvolvement.createStrategy");
				akamaiVal = (resAk.equals("true")) ? vendorVal : "(Failed - Value for : " + resAk + ")";
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['vendorSelection']['" + vendorVal + "']").toString();
				String actual_reski = sysResp.jsonPath().getString("vendorSelection").toString();
				KinesisVal = (exp_resKi.equals(actual_reski)) ? vendorVal : "(Failed - Value for : " + vendorVal + ")";
				break;
			}

			break;

		case "Evaluate vendors that can provide the solution":
			switch (backEnd) {

			case "Akamai":
				resAk = sysResp.jsonPath().getString("Catalyst.buyingInvolvement.evaluateVendors");
				akamaiVal = (resAk.equals("true")) ? vendorVal : "(Failed - Value for : " + resAk + ")";
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['vendorSelection']['" + vendorVal + "']").toString();
				String actual_reski = sysResp.jsonPath().getString("vendorSelection").toString();
				KinesisVal = (exp_resKi.equals(actual_reski)) ? vendorVal : "(Failed - Value for : " + vendorVal + ")";
				break;
			}
			break;

		case "Create the short list of products/vendors to review":
			switch (backEnd) {

			case "Akamai":
				resAk = sysResp.jsonPath().getString("Catalyst.buyingInvolvement.shortList");
				akamaiVal = (resAk.equals("true")) ? vendorVal : "(Failed - Value for : " + resAk + ")";
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['vendorSelection']['" + vendorVal + "']").toString();
				String actual_reski = sysResp.jsonPath().getString("vendorSelection").toString();
				KinesisVal = (exp_resKi.equals(actual_reski)) ? vendorVal : "(Failed - Value for : " + vendorVal + ")";
				break;
			}

			break;

		case "Recommend/specify the brands/vendors for purchase":

			switch (backEnd) {

			case "Akamai":
				resAk = sysResp.jsonPath().getString("Catalyst.buyingInvolvement.recommendSolution");
				akamaiVal = (resAk.equals("true")) ? vendorVal : "(Failed - Value for : " + resAk + ")";
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['vendorSelection']['" + vendorVal + "']").toString();
				String actual_reski = sysResp.jsonPath().getString("vendorSelection").toString();
				KinesisVal = (exp_resKi.equals(actual_reski)) ? vendorVal : "(Failed - Value for : " + vendorVal + ")";
				break;
			}
			break;

		case "Authorize/approve the purchase of the solution":
			switch (backEnd) {

			case "Akamai":
				resAk = sysResp.jsonPath().getString("Catalyst.buyingInvolvement.authorizePurchase");
				akamaiVal = (resAk.equals("true")) ? vendorVal : "(Failed - Value for : " + resAk + ")";
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['vendorSelection']['" + vendorVal + "']").toString();
				String actual_reski = sysResp.jsonPath().getString("vendorSelection").toString();
				KinesisVal = (exp_resKi.equals(actual_reski)) ? vendorVal : "(Failed - Value for : " + vendorVal + ")";
				break;
			}
			break;

		case "None of the above":
			switch (backEnd) {

			case "Akamai":
				akamaiVal = vendorVal;
				break;

			case "Kinesis":
				String exp_resKi = jsonUtils.getFromJSON(fiNm, "$['vendorSelection']['" + vendorVal + "']").toString();
				String actual_reski = sysResp.jsonPath().getString("vendorSelection").toString();
				KinesisVal = (exp_resKi.equals(actual_reski)) ? vendorVal : "(Failed - Value for : " + vendorVal + ")";

				break;
			}
			break;

		case "Not Present":
			switch (backEnd) {

			case "Akamai":
				String val = sysResp.jsonPath().getString("Catalyst.buyingInvolvement.authorizePurchase");
				akamaiVal = (val == null) ? vendorVal : "(Failed - Value for : " + val + ")";
				break;

			case "Kinesis":
				String exp_resKi = "7";
				String actual_reski = sysResp.jsonPath().getString("vendorSelection").toString();
				KinesisVal = (exp_resKi.equals(actual_reski)) ? vendorVal : "(Failed - Value for : " + vendorVal + ")";
				break;
			}

			break;

		default:
			LOGGER.info(
					"No Value Specified : Please specify Vendor Question value in Data file for Validation in BackEnd");
		}

	}

	@Step("Validate values in Literatum")
	public boolean validateBackendChanges_Literatum(Response respIdentity) {
		LOGGER.info("=======================TEST RESULTS===================================");
		LOGGER.info("================BACKEND VALIDATION : LITERATUM========================");
		int n = 0;
		List<String> Actualtaglist = respIdentity.jsonPath().getList("tag.tag-set-code");
		for (int i = 0; i < Actualtaglist.size(); i++) {
			if (Actualtaglist.get(i).equalsIgnoreCase("catalyst-audience-type")) {
				break;
			}
			n += 1;
		}

		boolean userType = respIdentity.jsonPath().getList("tag.tag-label").get(n).equals("Registered User");
		int p = 0;
		for (int j = 0; j < Actualtaglist.size(); j++) {
			if (Actualtaglist.get(j).equalsIgnoreCase("catalyst-insights-council")) {
				break;
			}
			p += 1;
		}

		boolean icMember = respIdentity.jsonPath().getList("tag.tag-label").get(p).equals("Member");
		LOGGER.info("=======================Actual Values================================");
		LOGGER.info("Registered User:" + userType);
		LOGGER.info("********************************************************************");
		LOGGER.info("======================Expected Values===============================");
		LOGGER.info("Insights Council Member:" + icMember);
		LOGGER.info("=====================================================================");
		return (userType && icMember) ? true : false;
	}

	@Step("Get Alternate ID")
	public String getAlternateID() {
		return alternateID;
	}

	@Step("Click on Manage Alert link")
	public void clickAlertLink_thankYouPopUp() {
		manageProfileAlertsLink.waitUntilClickable().click();
	}

	@Step("Lead user account details")
	public String getLeadUserAccount() {
		return passVal;
	}

}
