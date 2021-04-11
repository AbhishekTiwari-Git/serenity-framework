package ucc.cr.pages.catalyst.ui;

import io.restassured.response.Response;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.cr.pages.catalyst.ui.RegInsighCouncilPage;
import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;

import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JoinIC extends PageObject {

	public static Response sysResp = null;
	public static int fieldSize = 5;
	private static final Logger LOGGER = LoggerFactory.getLogger(RegInsighCouncilPage.class);
	private static boolean Value1, Value2, Value3, Value4, Value5, Value6, Value7, Value8, yoB, stateField, title,
			phoneNumber, password, errorMessage;
	public static HashMap<Integer, String> applyform_quesBeds = new HashMap<Integer, String>();
	public static HashMap<Integer, String> applyform_quesSites = new HashMap<Integer, String>();
	public static HashMap<Integer, String> applyform_quesPhy = new HashMap<Integer, String>();
	public static HashMap<Integer, String> applyform_quesVendor = new HashMap<Integer, String>();
	public static HashMap<Integer, String> applyform_quesDollar = new HashMap<Integer, String>();
	public static HashMap<Integer, String> applyform_quesVendor_expected = new HashMap<Integer, String>();
	public static HashMap<Integer, String> applyform_quesDollar_expected = new HashMap<Integer, String>();
	public static HashMap<Integer, String> applyform_quesBeds_expected = new HashMap<Integer, String>();
	public static HashMap<Integer, String> applyform_quesSites_expected = new HashMap<Integer, String>();
	public static HashMap<Integer, String> applyform_quesPhy_expected = new HashMap<Integer, String>();
	public static HashMap<Integer, String> MyAccountICMember_expected = new HashMap<Integer, String>();
	public static HashMap<Integer, String> typeOfOrg_actual = new HashMap<Integer, String>();
	public static HashMap<Integer, String> typeOfOrg_expected = new HashMap<Integer, String>();
	public static HashMap<Integer, String> healthSystemAffliation_actual = new HashMap<Integer, String>();
	public static HashMap<Integer, String> healthSystemAffliation_expected = new HashMap<Integer, String>();
	public static HashMap<Integer, String> netPaitentRevenue_actual = new HashMap<Integer, String>();
	public static HashMap<Integer, String> netPaitentRevenue_expected = new HashMap<Integer, String>();
	public static HashMap<Integer, String> selectingVendor_actual = new HashMap<Integer, String>();
	public static HashMap<Integer, String> selectingVendor_expected = new HashMap<Integer, String>();
	public static HashMap<Integer, String> estimatedDollarAmount_actual = new HashMap<Integer, String>();
	public static HashMap<Integer, String> estimatedDollarAmount_expected = new HashMap<Integer, String>();
	public static HashMap<Integer, String> createPass_actual = new HashMap<Integer, String>();
	public static HashMap<Integer, String> createPass_expected = new HashMap<Integer, String>();
	public static HashMap<Integer, String> phoneNumberField_actual = new HashMap<Integer, String>();
	public static HashMap<Integer, String> phoneNumberField_expected = new HashMap<Integer, String>();
	public static HashMap<Integer, String> titleField_actual = new HashMap<Integer, String>();
	public static HashMap<Integer, String> titleField_expected = new HashMap<Integer, String>();
	public static HashMap<Integer, String> stateField_actual = new HashMap<Integer, String>();
	public static HashMap<Integer, String> stateField_expected = new HashMap<Integer, String>();
	public static HashMap<Integer, String> yoB_actual = new HashMap<Integer, String>();
	public static HashMap<Integer, String> yoB_expected = new HashMap<Integer, String>();

	RegInsighCouncilPage regInsighCouncilPage = new RegInsighCouncilPage();
	HomePage homepage = new HomePage();

	@FindBy(xpath = "//div[contains(@class,' ucc-form-field-descr') and contains(text(),'beds')]")
	WebElementFacade quesBeds;

	@FindBy(xpath = "//div[contains(@class,' ucc-form-field-descr') and contains(text(),'sites')]")
	WebElementFacade quesSites;

	@FindBy(xpath = "//div[contains(@class,' ucc-form-field-descr') and contains(text(),'physicians')]")
	WebElementFacade quesPhysicians;

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

	@FindBy(xpath = "//h1[contains(text(),'Congratulations!')]")
	WebElementFacade textOnPage;

	@FindBy(xpath = "//button[contains(text(), 'Continue')]")
	WebElementFacade ContinueButton;

	@FindBy(xpath = "//*[starts-with(@id,'uccOrganization')]")
	WebElementFacade organizationQues;

	@FindBy(xpath = "//*[starts-with(@id,'uccHealthAffiliation')]")
	WebElementFacade healthAffliationQues;

	@FindBy(xpath = "//*[starts-with(@id,'uccNetpatientRevenue')]")
	WebElementFacade uccNetPatientRevenueQues;

	@FindBy(xpath = "//span[contains(text(),'Determine the business need')]")
	WebElementFacade vendorsQues;

	@FindBy(xpath = "//*[starts-with(@id,'uccestimatedDollor')]")
	WebElementFacade dollarQues;

	@FindBy(xpath = "//*[starts-with(@id,'uccYearofbirth')]")
	WebElementFacade yearOfBirth;

	@FindBy(xpath = "//*[starts-with(@id,'uccState')]")
	WebElementFacade state;

	@FindBy(xpath = "//*[starts-with(@id,'uccPwd')]")
	WebElementFacade createPassword;

	@FindBy(xpath = "//*[starts-with(@id,'uccPhone')]")
	WebElementFacade phoneNumberOptional;

	@FindBy(xpath = "//*[starts-with(@id,'uccTitle')]")
	WebElementFacade titleOptional;

	@FindBy(xpath = "//*[starts-with(@id,'uccNoofbeds')]")
	WebElementFacade uccNoOfbeds;

	@FindBy(xpath = "//*[starts-with(@id,'uccNoofSites')]")
	WebElementFacade uccNoOfSites;

	@FindBy(xpath = "//*[starts-with(@id,'uccNoofPhysicans')]")
	WebElementFacade uccNoOfPhysicans;

	@FindBy(xpath = "//button[contains(text(),'Join')]")
	WebElementFacade joinBtn;

	@FindBy(xpath = "//div[contains(text(),'Please indicate your involvement in selecting vendors')]")
	WebElementFacade questionVendor;

	@FindBy(xpath = "//div[contains(text(),'What is the estimated dollar amount')]")
	WebElementFacade questionDollar;

	@FindBy(xpath = "//span[contains(text(),'Determine the business need')]")
	WebElementFacade quesVendorValue1;

	@FindBy(xpath = "//span[contains(text(),'Create the strategy and determine the solution')]")
	WebElementFacade quesVendorValue2;

	@FindBy(xpath = "//span[contains(text(),'Evaluate vendors that can provide the solution')]")
	WebElementFacade quesVendorValue3;

	@FindBy(xpath = "//span[contains(text(),'Create the short list of products/vendors to review')]")
	WebElementFacade quesVendorValue4;

	@FindBy(xpath = "//span[contains(text(),'Recommend/specify the brands/vendors for purchase')]")
	WebElementFacade quesVendorValue5;

	@FindBy(xpath = "//span[contains(text(),'Authorize/approve the purchase of the solution')]")
	WebElementFacade quesVendorValue6;

	@FindBy(xpath = "//span[contains(text(),'None of the above')]")
	WebElementFacade quesVendorValue7;

	@FindBy(xpath = "//div[contains(text(),'Please select type of organization')]")
	WebElementFacade orgQuesErrorMessage;

	@FindBy(xpath = "//div[contains(text(),'Please select health system affiliation')]")
	WebElementFacade affiliationQuesErrorMessage;

	@FindBy(xpath = "//div[contains(text(),'Please select net patient revenue')]")
	WebElementFacade netPaitentQuesErrorMessage;

	@FindBy(xpath = "//div[contains(text(),'Please select at least one option')]")
	WebElementFacade vendorQuesErrorMessage;

	@FindBy(xpath = "//div[contains(text(),'Please select the estimated dollar amount')]")
	WebElementFacade dollarQuesErrorMessage;

	@FindBy(xpath = "//div[contains(text(),'Please select Year of Birth')]")
	WebElementFacade yOBQuesErrorMessage;

	@FindBy(xpath = "//div[contains(text(),'Please select your State')]")
	WebElementFacade stateQuesErrorMessage;

	@FindBy(xpath = "//div[contains(text(),'Please enter your password')]")
	WebElementFacade createPasswordErrorMessage;

	@FindBy(xpath = "//div[contains(text(),'Please select number of sites')]")
	WebElementFacade siteQuesErrorMessage;

	@FindBy(xpath = "//div[contains(text(),'Please select number of beds')]")
	WebElementFacade bedsQuesErrorMessage;

	@FindBy(xpath = "//div[contains(text(),'Please select Number of Physicans')]")
	WebElementFacade physicianQuesErrorMessage;

	@FindBy(xpath = "//h1[contains(text(),'Thank you!')]")
	WebElementFacade thankYouPopUp;

	@FindBy(xpath = "//a[contains(text(),'Manage Profile and Alerts')]")
	WebElementFacade manageProfileAlertsLink;

	@FindBy(xpath = "//a[contains(text(),'Go to homepage')]")
	WebElementFacade goToHomePageLink;

	@Step("Enter values on Second Page of Apply Now Form")
	public void enterCommonValues(String fname, String lname, String suffix, String country) {
		firstName.waitUntilClickable().sendKeys(fname);
		lastName.waitUntilClickable().sendKeys(lname);
		suffixDropDown.waitUntilClickable().selectByValue(suffix);
		nameOfOrg.waitUntilClickable().sendKeys("ORG");
		countryDropDown.waitUntilClickable().selectByValue(country);

	}

	@Step("Enter value to questions on Third Page of Apply Now Form - once qualified ")
	public void selectQuestions_Common(String orgVal, String affVal, String netVal, String dollarVal, String yobVal,
			String stateVal, String passVal, String vendorVal, String role_place) {
		organizationQues.waitUntilClickable().selectByValue(orgVal);
		healthAffliationQues.waitUntilClickable().selectByValue(affVal);
		uccNetPatientRevenueQues.waitUntilClickable().selectByValue(netVal);
		yearOfBirth.waitUntilClickable().selectByValue(yobVal);

		if (state.isCurrentlyVisible()) {
			state.waitUntilClickable().selectByValue(stateVal);
		}
		LOGGER.info("State drop down will only appear for United States");

		if (!passVal.equals("null")) {
			createPassword.waitUntilClickable().sendKeys(passVal);
		}
		LOGGER.info("Create Password field will not appear for Registered User");

		selectVendor_dollarQues(role_place, dollarVal, vendorVal);
	}

	@Step("Select vendor dollar questions dynamically based upon either place of work or Role")
	public void selectVendor_dollarQues(String role_place, String dollarVal, String vendorVal) {

		switch (role_place) {
		case "CLI":

			String place = questionVendor.isCurrentlyVisible() ? "CLI" : "null";

			if (place.equals("CLI")) {
				dollarQues.waitUntilClickable().selectByValue(dollarVal);
				selectVendor(vendorVal);
			}
			LOGGER.info("Vendor Question does not appear when role is Clinic");
			LOGGER.info("Dollar Question does not appear when role is Other");

			break;

		case "OTH":
			LOGGER.info("Vendor Question does not appear when role is Clinic");
			LOGGER.info("Dollar Question does not appear when role is Other");
			break;

		default:
			dollarQues.waitUntilClickable().selectByValue(dollarVal);
			selectVendor(vendorVal);
		}
	}

	@Step("Select vendor question")
	public void selectVendor(String vendorVal) {

		switch (vendorVal) {

		case "":
			LOGGER.info("Blank Value for Vendor Question");
			break;

		case "Determine the business need":
			quesVendorValue1.waitUntilClickable().click();
			break;

		case "Create the strategy and determine the solution":
			quesVendorValue2.waitUntilClickable().click();
			break;

		case "Evaluate vendors that can provide the solution":
			quesVendorValue3.waitUntilClickable().click();
			break;

		case "Create the short list of products/vendors to review":
			quesVendorValue4.waitUntilClickable().click();
			break;

		case "Recommend/specify the brands/vendors for purchase":
			quesVendorValue5.waitUntilClickable().click();
			break;

		case "Authorize/approve the purchase of the solution":
			quesVendorValue6.waitUntilClickable().click();
			break;

		case "None of the above":
			quesVendorValue7.waitUntilClickable().click();
			break;

		default:

			LOGGER.info("Incorrect checkbox value specified in data file for Vendor question");
		}

	}

	@Step("validate questions on insights council form on the basis of selected place of work")
	public void validateQuestions(String place, String message) {
		ContinueButton.waitUntilClickable().click();
		waitForTextToAppear(message);
		if ((quesBeds.isCurrentlyVisible() == true) || (quesSites.isCurrentlyVisible() == true)
				|| (quesPhysicians.isCurrentlyVisible() == true)) {
			applyform_quesBeds.put(1, "yes");
			applyform_quesSites.put(1, "yes");
			applyform_quesPhy.put(1, "yes");
		}

		else {
			applyform_quesBeds.put(1, "no");
			applyform_quesSites.put(1, "no");
			applyform_quesPhy.put(1, "no");
		}
	}

	@Step("Print actual values - if question for dollar and vendor is present or not")
	public void printActualValues_dollarVendor(String role) {
		LOGGER.info("==========================TEST RESULTS=========================");
		LOGGER.info("Test completed for role" + " : " + role);
		LOGGER.info("===============================================================");
		LOGGER.info(
				"============Apply Now Form - Question for Vendor & Dollar (Actual Values MAP)===================================");
		LOGGER.info(
				"Question for Vendor -- Please indicate your involvement in selecting vendors and buying products and services");
		LOGGER.info(applyform_quesVendor.get(1));
		LOGGER.info("--------------------------------");
		LOGGER.info("Question for Dollar -- What is the estimated dollar amount of products");
		LOGGER.info(applyform_quesDollar.get(1));
	}

	@Step("Print actual values - if question for beds, site and phy is present or not")
	public void printActualValues(String place) {
		LOGGER.info("==========================TEST RESULTS=========================");
		LOGGER.info("Test completed for place of Work" + " : " + place);
		LOGGER.info("===============================================================");
		LOGGER.info(
				"============Apply Now Form - Question for Beds, Site & Physician (Actual Values MAP)===================================");
		LOGGER.info("Question for No of Beds");
		LOGGER.info(applyform_quesBeds.get(1));
		LOGGER.info("--------------------------------");
		LOGGER.info("Question for No of Sites");
		LOGGER.info(applyform_quesSites.get(1));
		LOGGER.info("--------------------------------");
		LOGGER.info("Question for Physician");
		LOGGER.info(applyform_quesPhy.get(1));
		LOGGER.info("--------------------------------");
	}

	@Step("Select place of work with other details like first name, last name, suffix on join form")
	public void selectPlaceofWork(String place, String fname, String lname, String suffix, String role, String country,
			String message) {
		enterCommonValues(fname, lname, suffix, country);
		roleDropDown.waitUntilClickable().selectByValue(role);
		switch (place) // nested switch
		{
		case "CLI": // When Place of Work Selected is clinic
			placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
			validateQuestions("Clinic", message);
			break;

		case "HOU": // When Place of Work Selected is Hospital, University or
					// Teaching
			placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
			validateQuestions("Hospital, University or Teaching", message);
			uccNoOfbeds.waitUntilClickable().selectByValue("1-50");
			break;

		case "COL": // When Place of Work Selected is College or University
			placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
			validateQuestions("College or University", message);
			break;

		case "MSP": // When Place of Work Selected is Medical School Program
			placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
			validateQuestions("Medical School Program", message);
			break;

		case "HOC": // When Place of Work Selected is Hospital Community
			placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
			validateQuestions("Hospital Community", message);
			uccNoOfbeds.waitUntilClickable().selectByValue("51-199");
			break;

		case "HSM": // When Place of Work Selected is Health System
			placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
			validateQuestions("Health System", message);
			uccNoOfSites.waitUntilClickable().selectByValue("1-5");
			break;

		case "REA": // When Place of Work Selected is Research Facility
			placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
			validateQuestions("Research Facility", message);
			break;

		case "LTC": // When Place of Work Selected is Long-term Care/SNF
			placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
			validateQuestions("Long-term care/SNF", message);
			uccNoOfbeds.waitUntilClickable().selectByValue("1000-1999");
			break;

		case "GOV": // When Place of Work Selected is Government Agency
			placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
			validateQuestions("Government Agency", message);
			break;

		case "HOO": // When Place of Work Selected is Hospital, Other
			placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
			validateQuestions("Hospital, Other", message);
			uccNoOfbeds.waitUntilClickable().selectByValue("2000+");
			break;

		case "HMO": // When Place of Work Selected is Health/Plan/HMO/Insurer
			placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
			validateQuestions("Health/Plan/HMO/Insurer", message);
			break;

		case "PHO":
			placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
			validateQuestions("Physician Organization", message);
			uccNoOfPhysicans.waitUntilClickable().selectByValue("10-25");
			break;

		case "ANC":
			placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
			validateQuestions("Ancillary, Allied Provider (Pharmacy, Home Health Lab, Rehab, Post-Acute, etc.)",
					message);
			break;

		case "PHC":
			placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
			validateQuestions("Pharmaceutical Company", message);
			break;

		case "TEV":
			placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
			validateQuestions("Technology/Equipment Vendor or Supplier", message);
			break;

		case "OTH":
			placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
			validateQuestions("Technology/Equipment Vendor or Supplier", message);
			break;

		case "PRS":
			placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
			validateQuestions("Professional Services (Consulting, Financial, Legal, etc.)", message);
			break;

		default:

			LOGGER.info("Place of Work Not Specified, Please specify Place of work in data file");
		}

	}

	@Step("Select Role with other details like place of work, first name, last name, suffix on join form")
	public void selectRole(String role, String fname, String lname, String suffix, String place, String country,
			String message) {
		enterCommonValues(fname, lname, suffix, country);
		placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
		switch (role) // nested switch
		{
		case "CEO": // When Role Selected is CEO/President/Board Member
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("CEO/President/Board Member", message);
			break;

		case "CSU": // When Role Selected is C-Suite
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("C-Suite", message);
			break;

		case "VP": // When Role Selected is VP/Director
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("VP/Director", message);
			break;

		case "MGR": // When Role Selected is Manager
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("Manager", message);
			break;

		case "BEO": // When Role Selected is Business Executive – Other
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("Business Executive – Other", message);
			break;

		case "CMO": // When Role Selected is Chief Medical Officer
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("Chief Medical Officer", message);
			break;

		case "VMA": // When Role Selected is VP of Medical Affairs
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("VP of Medical Affairs", message);
			break;

		case "CCS": // When Role Selected is Chief or Chair Service/Department
					// (i.e., Orthopedics, Cardiology, etc.)
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("Chief or Chair Service/Department (i.e., Orthopedics, Cardiology, etc.)",
					message);
			break;

		case "AC": // When Role Selected is Associate Chief or Vice of Chair
					// Service/Department
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("Associate Chief or Vice of Chair Service/Department", message);
			break;

		case "CSL": // When Role Selected is Chief of Service Line (i.e.,
					// Patient safety, Quality assurance, etc.)
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("Chief of Service Line (i.e., Patient safety, Quality assurance, etc.)",
					message);
			break;

		case "VSL": // When Role Selected is VP, Director, Manager of Service
					// Line
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("VP, Director, Manager of Service Line", message);
			break;

		case "DMS": // When Role Selected is Dean of Medical School
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("Dean of Medical School", message);
			break;

		case "PRF": // When Role Selected is Program/Residency/Fellowship
					// Director
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("Program/Residency/Fellowship Director", message);
			break;

		case "CLI": // When Role Selected is Clinician
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("Clinician", message);
			break;

		case "CON": // When Role Selected is Consultant
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("Consultant", message);
			break;

		case "RSD": // When Role Selected is Resident
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("Resident", message);
			break;

		case "STU": // When Role Selected is Student
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("Student", message);
			break;

		case "EDU": // When Role Selected is Educator
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("Educator", message);
			break;

		case "RES": // When Role Selected is Researcher
			roleDropDown.waitUntilClickable().selectByValue(role);
			validateQuestions_AsPerRole("Researcher", message);
			break;

		case "OTH": // When Role Selected is Other
			roleDropDown.waitUntilClickable().selectByValue(role);
			suffixDropDown.waitUntilClickable().selectByValue(suffix);
			validateQuestions_AsPerRole("Other", message);
			break;

		default:

			LOGGER.info("Role Not Specified, Please specify Role in data file");
		}

	}

	@Step("Validate questions on insights council form on the basis of selected Role")
	public void validateQuestions_AsPerRole(String role, String message) {
		ContinueButton.waitUntilClickable().click();
		waitForTextToAppear(message);
		if ((questionVendor.isCurrentlyVisible() == true) || (questionDollar.isCurrentlyVisible() == true)) {
			applyform_quesVendor.put(1, "yes");
			applyform_quesDollar.put(1, "yes");
		}

		else {
			applyform_quesVendor.put(1, "no");
			applyform_quesDollar.put(1, "no");
		}
	}

	@Step("Print expected values - if question for vendor and dollar is present or not")
	public void printExpectedValues_dollarVendor(String quesPresent) {
		if (quesPresent.equalsIgnoreCase("yes")) {
			applyform_quesVendor_expected.put(1, "yes");
			applyform_quesDollar_expected.put(1, "yes");
		} else {
			applyform_quesVendor_expected.put(1, "no");
			applyform_quesDollar_expected.put(1, "no");
		}

		LOGGER.info(
				"============Apply Now Form - Question for Vendors & Dollar (Expected Values MAP)===================================");
		LOGGER.info("Question for Vendor");
		LOGGER.info(applyform_quesVendor_expected.get(1));
		LOGGER.info("--------------------------------");
		LOGGER.info("Question for Dollar");
		LOGGER.info(applyform_quesDollar_expected.get(1));
		LOGGER.info("--------------------------------");

	}

	@Step("Print expected values - if question for beds, site and phy is present or not")
	public void printExpectedValues(String quesPresent) {
		if (quesPresent.equalsIgnoreCase("yes")) {
			applyform_quesBeds_expected.put(1, "yes");
			applyform_quesSites_expected.put(1, "yes");
			applyform_quesPhy_expected.put(1, "yes");
		}

		else {
			applyform_quesBeds_expected.put(1, "no");
			applyform_quesSites_expected.put(1, "no");
			applyform_quesPhy_expected.put(1, "no");
		}

		LOGGER.info(
				"============Apply Now Form - Question for Beds, Site & Physician (Expected Values MAP)===================================");
		LOGGER.info("Question for No of Beds");
		LOGGER.info(applyform_quesBeds_expected.get(1));
		LOGGER.info("--------------------------------");
		LOGGER.info("Question for No of Sites");
		LOGGER.info(applyform_quesSites_expected.get(1));
		LOGGER.info("--------------------------------");
		LOGGER.info("Question for Physician");
		LOGGER.info(applyform_quesPhy_expected.get(1));
		LOGGER.toString();
		LOGGER.info("--------------------------------");

	}

	@Step("Compare Questions Maps of Beds, Sites and Physician (Expected Vs Actual) Values")
	public String compareValues() {
		if ((applyform_quesBeds.equals(applyform_quesBeds_expected))
				&& (applyform_quesSites.equals(applyform_quesSites_expected))
				&& (applyform_quesPhy.equals(applyform_quesPhy_expected))) {
			return applyform_quesBeds_expected.get(1);
		}
		return applyform_quesBeds.get(1);
	}

	@Step("Compare Questions Maps of Vendor and Dollar (Expected Vs Actual) Values")
	public String compareValues_vendorDollar() {
		if ((applyform_quesVendor.equals(applyform_quesVendor_expected))
				&& (applyform_quesDollar.equals(applyform_quesDollar_expected))) {
			return applyform_quesVendor_expected.get(1);
		}
		return applyform_quesVendor.get(1);
	}

	@Step("Join Insights Council if required")
	public void joinInsightsCouncil(String iCJoin) {
		if (iCJoin.equals("yes")) {
			joinBtn.waitUntilClickable().click();
		} else {
			LOGGER.info("User Qualified Insights Council but didn't joined");
		}

	}

	@Step("Check Question Fields on IC Form")
	public void checkQuestionsFieldsICForm() {
		validateQuesOrg();
		validateQuesAffliation();
		validateQuesNetPatient();
		validateQuesVendors();
		validateQuesDollars();
	}

	@Step("Check Common Fields on IC Form")
	public void checkCommonFieldsICForm() {

		yearOfBirthDropDown();
		stateDropDown();
		titleField();
		phoneNumberField();
		createPasswordField();
	}

	@Step("Checking correct drop down values being displayed for Question - Which best describes your type of organization?")
	public void validateQuesOrg() {
		organizationQues.waitUntilClickable().click();
		Value1 = organizationQues.selectByValue("For profit").isDisplayed();
		Value2 = organizationQues.selectByValue("Non-profit").isDisplayed();
		typeOfOrg_expected.put(1, "For Profit");
		typeOfOrg_expected.put(2, "Non-profit");

		if (Value1 == true && Value2 == true) {
			typeOfOrg_actual.put(1, "For Profit");
			typeOfOrg_actual.put(2, "Non-profit");
		}

		else {
			typeOfOrg_actual.put(1, "For Profit");
			typeOfOrg_actual.put(1, "Non Profit");
		}
	}

	@Step("Checking correct drop down values being displayed for Question - Is your organization affiliated with a Health System?")
	public void validateQuesAffliation() {
		healthAffliationQues.waitUntilClickable().click();
		Value1 = healthAffliationQues.selectByValue("Yes").isDisplayed();
		Value2 = healthAffliationQues.selectByValue("No").isDisplayed();
		healthSystemAffliation_expected.put(1, "Yes");
		healthSystemAffliation_expected.put(2, "No");
		if (Value1 == true && Value2 == true) {
			healthSystemAffliation_actual.put(1, "Yes");
			healthSystemAffliation_actual.put(2, "No");
		}

		else {
			healthSystemAffliation_actual.put(1, "Value: Yes --> Not Appearing");
			healthSystemAffliation_actual.put(2, "Value: No --> Not Appearing");
		}
	}

	@Step("Checking correct drop down values being displayed for Question - What is the estimated net patient revenue of your combined facilities?")
	public void validateQuesNetPatient() {
		uccNetPatientRevenueQues.waitUntilClickable().click();
		Value1 = uccNetPatientRevenueQues.selectByValue("More than $5 billion").isDisplayed();
		Value2 = uccNetPatientRevenueQues.selectByValue("$1 billion to $4.9 billion").isDisplayed();
		Value3 = uccNetPatientRevenueQues.selectByValue("$500 million to $999.9 million").isDisplayed();
		Value4 = uccNetPatientRevenueQues.selectByValue("$100 million to $499.9 million").isDisplayed();
		Value5 = uccNetPatientRevenueQues.selectByValue("$10 million to $99.9 million").isDisplayed();
		Value6 = uccNetPatientRevenueQues.selectByValue("$9.9 million or less").isDisplayed();
		Value7 = uccNetPatientRevenueQues.selectByValue("None of the above").isDisplayed();
		if (Value1 == true && Value2 == true && Value3 == true && Value4 == true && Value5 == true && Value6 == true
				&& Value7 == true) {
			netPaitentRevenue_actual.put(1, "More than $5 billion");
			netPaitentRevenue_actual.put(2, "$1 billion to $4.9 billion");
			netPaitentRevenue_actual.put(3, "$500 million to $999.9 million");
			netPaitentRevenue_actual.put(4, "$100 million to $499.9 million");
			netPaitentRevenue_actual.put(5, "$10 million to $99.9 million");
			netPaitentRevenue_actual.put(6, "$9.9 million or less");
			netPaitentRevenue_actual.put(7, "None of the above");
		}

		else {
			netPaitentRevenue_actual.put(1, "Value : More than $5 billion --> Not Appearing");
			netPaitentRevenue_actual.put(2, "Value : $1 billion to $4.9 billion --> Not Appearing");
			netPaitentRevenue_actual.put(3, "Value : $500 million to $999.9 million --> Not Appearing");
			netPaitentRevenue_actual.put(4, "Value : $100 million to $499.9 million --> Not Appearing");
			netPaitentRevenue_actual.put(5, "Value : $10 million to $99.9 million --> Not Appearing");
			netPaitentRevenue_actual.put(6, "Value : $9.9 million or less --> Not Appearing");
			netPaitentRevenue_actual.put(7, "Value : None of the above --> Not Appearing");
		}
		netPaitentRevenue_expected.put(1, "More than $5 billion");
		netPaitentRevenue_expected.put(2, "$1 billion to $4.9 billion");
		netPaitentRevenue_expected.put(3, "$500 million to $999.9 million");
		netPaitentRevenue_expected.put(4, "$100 million to $499.9 million");
		netPaitentRevenue_expected.put(5, "$10 million to $99.9 million");
		netPaitentRevenue_expected.put(6, "$9.9 million or less");
		netPaitentRevenue_expected.put(7, "None of the above");

	}

	@Step("Checking correct drop down values being displayed for Question - Please indicate your involvement in selecting vendors and buying products and services")
	public void validateQuesVendors() {
		Value1 = quesVendorValue1.waitUntilClickable().isDisplayed();
		Value2 = quesVendorValue2.waitUntilClickable().isDisplayed();
		Value3 = quesVendorValue3.waitUntilClickable().isDisplayed();
		Value4 = quesVendorValue4.waitUntilClickable().isDisplayed();
		Value5 = quesVendorValue5.waitUntilClickable().isDisplayed();
		Value6 = quesVendorValue6.waitUntilClickable().isDisplayed();
		Value7 = quesVendorValue7.waitUntilClickable().isDisplayed();

		if (Value1 == true && Value2 == true && Value3 == true && Value4 == true && Value5 == true && Value6 == true
				&& Value7 == true) {
			selectingVendor_actual.put(1, "Determine the business need");
			selectingVendor_actual.put(2, "Create the strategy and determine the solution");
			selectingVendor_actual.put(3, "Evaluate vendors that can provide the solution");
			selectingVendor_actual.put(4, "Create the short list of products/vendors to review");
			selectingVendor_actual.put(5, "Recommend/specify the brands/vendors for purchase");
			selectingVendor_actual.put(6, "Authorize/approve the purchase of the solution");
			selectingVendor_actual.put(7, "None of the above");
		}

		else {
			selectingVendor_actual.put(1, "Value : Determine the business need --> Not Appearing");
			selectingVendor_actual.put(2, "Value : Create the strategy and determine the solution --> Not Appearing");
			selectingVendor_actual.put(3, "Value : Evaluate vendors that can provide the solution --> Not Appearing");
			selectingVendor_actual.put(4,
					"Value : Create the short list of products/vendors to review --> Not Appearing");
			selectingVendor_actual.put(5,
					"Value : Recommend/specify the brands/vendors for purchase --> Not Appearing");
			selectingVendor_actual.put(6, "Value : Authorize/approve the purchase of the solution --> Not Appearing");
			selectingVendor_actual.put(7, "Value : None of the above --> Not Appearing");
		}

		selectingVendor_expected.put(1, "Determine the business need");
		selectingVendor_expected.put(2, "Create the strategy and determine the solution");
		selectingVendor_expected.put(3, "Evaluate vendors that can provide the solution");
		selectingVendor_expected.put(4, "Create the short list of products/vendors to review");
		selectingVendor_expected.put(5, "Recommend/specify the brands/vendors for purchase");
		selectingVendor_expected.put(6, "Authorize/approve the purchase of the solution");
		selectingVendor_expected.put(7, "None of the above");

	}

	@Step("Checking correct drop down values being displayed for Question - What is the estimated dollar amount of products and services purchases that you influence at your or organization?")
	public void validateQuesDollars() {
		dollarQues.waitUntilClickable().click();
		Value1 = dollarQues.selectByValue("$500 million+").isDisplayed();
		Value2 = dollarQues.selectByValue("$100 million to $499.9 million").isDisplayed();
		Value3 = dollarQues.selectByValue("$25 million to $99.9 million").isDisplayed();
		Value4 = dollarQues.selectByValue("$10 million to $24.9 million").isDisplayed();
		Value5 = dollarQues.selectByValue("$1 million to $9.9 million").isDisplayed();
		Value6 = dollarQues.selectByValue("$100,000 to $999,999").isDisplayed();
		Value7 = dollarQues.selectByValue("Under $100,000").isDisplayed();
		Value8 = dollarQues.selectByValue("None of the above").isDisplayed();

		if (Value1 == true && Value2 == true && Value3 == true && Value4 == true && Value5 == true && Value6 == true
				&& Value7 == true && Value8 == true) {
			estimatedDollarAmount_actual.put(1, "$500 million+");
			estimatedDollarAmount_actual.put(2, "$100 million to $499.9 million");
			estimatedDollarAmount_actual.put(3, "$25 million to $99.9 million");
			estimatedDollarAmount_actual.put(4, "$10 million to $24.9 million");
			estimatedDollarAmount_actual.put(5, "$1 million to $9.9 million");
			estimatedDollarAmount_actual.put(6, "$100,000 to $999,999");
			estimatedDollarAmount_actual.put(7, "Under $100,000");
			estimatedDollarAmount_actual.put(8, "None of the above");
		}

		else {
			estimatedDollarAmount_actual.put(1, "Value : $500 million+ ---> Not Appearing");
			estimatedDollarAmount_actual.put(2, "Value : $100 million to $499.9 million --> Not Appearing");
			estimatedDollarAmount_actual.put(3, "Value : $25 million to $99.9 million --> Not Appearing");
			estimatedDollarAmount_actual.put(4, "Value : $10 million to $24.9 million --> Not Appearing");
			estimatedDollarAmount_actual.put(5, "Value : $1 million to $9.9 million --> Not Appearing");
			estimatedDollarAmount_actual.put(6, "Value : $100,000 to $999,999 --> Not Appearing");
			estimatedDollarAmount_actual.put(7, "Value : Under $100,000 --> Not Appearing");
			estimatedDollarAmount_actual.put(8, "Value : None of the above --> Not Appearing");
		}
		estimatedDollarAmount_expected.put(1, "$500 million+");
		estimatedDollarAmount_expected.put(2, "$100 million to $499.9 million");
		estimatedDollarAmount_expected.put(3, "$25 million to $99.9 million");
		estimatedDollarAmount_expected.put(4, "$10 million to $24.9 million");
		estimatedDollarAmount_expected.put(5, "$1 million to $9.9 million");
		estimatedDollarAmount_expected.put(6, "$100,000 to $999,999");
		estimatedDollarAmount_expected.put(7, "Under $100,000");
		estimatedDollarAmount_expected.put(8, "None of the above");
	}

	@Step("Check if Enter Password Field is appearing on Join IC Form")
	public void createPasswordField() {
		password = createPassword.waitUntilClickable().isDisplayed();
		createPass_expected.put(1, "Appearing");
		if (password == true) {
			createPass_actual.put(1, "Appearing");
		} else {
			createPass_actual.put(1, "Create Password Field - Not Appearing");
		}

	}

	@Step("Check if Enter Password Field is appearing on Join IC Form")
	public void phoneNumberField() {
		phoneNumber = phoneNumberOptional.waitUntilClickable().isDisplayed();
		phoneNumberField_expected.put(1, "Appearing");
		if (phoneNumber == true) {
			phoneNumberField_actual.put(1, "Appearing");
		} else {
			phoneNumberField_actual.put(1, "Phone Number (Optional) Field Not Appearing");
		}

	}

	@Step("Check if Title(Optional) Field is appearing on Join IC Form")
	public void titleField() {
		title = titleOptional.waitUntilClickable().isDisplayed();
		titleField_expected.put(1, "Appearing");
		if (title == true) {
			titleField_actual.put(1, "Appearing");
		} else {
			titleField_actual.put(1, "Title Field (Optional) -- Not Appearing");
		}

	}

	@Step("Check if State dropDown appearing on Join IC Form When Country >>> United States")
	public void stateDropDown() {
		stateField = state.waitUntilClickable().isDisplayed();
		stateField_expected.put(1, "Appearing");
		if (stateField == true) {
			stateField_actual.put(1, "Appearing");
		} else {
			stateField_actual.put(1, "State Drop Down Not - Appearing");
		}

	}

	@Step("Check if Year of Birth drop down appearing on Join IC Form")
	public void yearOfBirthDropDown() {
		yoB = yearOfBirth.waitUntilClickable().isDisplayed();
		yoB_expected.put(1, "Appearing");
		if (yoB == true) {
			yoB_actual.put(1, "Appearing");
		} else {
			yoB_actual.put(1, "Year of Birth Drop Down Not Appearing");
		}
	}

	@Step("Validate Question Fields on IC Form")
	public boolean validateQuestionFieldsICForm() {
		boolean map1 = compareOrganizationMaps();
		boolean map2 = compareHealthSystemAffliationMaps();
		boolean map3 = compareNetPaitentRevenueMaps();
		boolean map4 = compareVendorMaps();
		boolean map5 = compareDollarMaps();

		if (map1 == true && map2 == true && map3 == true && map4 == true && map5 == true) {
			return true;
		}
		return false;

	}

	@Step("Validate Common fields Values on Join IC form")
	public boolean validateCommonFieldsICForm() {
		boolean map1 = comparePasswordMaps();
		boolean map2 = comparePhoneNumberFieldsMaps();
		boolean map3 = compareTitleFieldsMaps();
		boolean map4 = compareStateFieldsMaps();
		boolean map5 = compareyOBMaps();
		if (map1 == true && map2 == true && map3 == true && map4 == true && map5 == true) {
			return true;
		}
		return false;

	}

	@Step("Compare Organization Maps Actual Vs Expected")
	public boolean compareOrganizationMaps() {
		if (healthSystemAffliation_actual.equals(healthSystemAffliation_actual)) {
			return true;
		}
		return false;
	}

	@Step("Compare Health System Affliation Maps Actual Vs Expected")
	public boolean compareHealthSystemAffliationMaps() {
		if (healthSystemAffliation_actual.equals(healthSystemAffliation_actual)) {
			return true;
		}
		return false;
	}

	@Step("Compare Net Paitent Revenue Maps Actual Vs Expected")
	public boolean compareNetPaitentRevenueMaps() {
		if (netPaitentRevenue_actual.equals(netPaitentRevenue_expected)) {
			return true;
		}
		return false;
	}

	@Step("Compare Vendors Questions Values Maps Actual Vs Expected")
	public boolean compareVendorMaps() {
		if (selectingVendor_actual.equals(selectingVendor_expected)) {
			return true;
		}
		return false;
	}

	@Step("Compare Estimated Dollar Questions Values Maps Actual Vs Expected")
	public boolean compareDollarMaps() {
		if (estimatedDollarAmount_actual.equals(estimatedDollarAmount_expected)) {
			return true;
		}
		return false;
	}

	@Step("Compare Password Maps Actual Vs Expected")
	public boolean comparePasswordMaps() {
		if (createPass_actual.equals(createPass_expected)) {
			return true;
		}
		return false;
	}

	@Step("Compare Phone Number Fields Actual Vs Expected")
	public boolean comparePhoneNumberFieldsMaps() {
		if (phoneNumberField_actual.equals(phoneNumberField_expected)) {
			return true;
		}
		return false;
	}

	@Step("Compare Title Fields Actual Vs Expected")
	public boolean compareTitleFieldsMaps() {
		if (titleField_actual.equals(titleField_expected)) {
			return true;
		}
		return false;
	}

	@Step("Compare State Fields Actual Vs Expected")
	public boolean compareStateFieldsMaps() {
		if (stateField_actual.equals(stateField_expected)) {
			return true;
		}
		return false;
	}

	@Step("Compare Year of Birth Actual Vs Expected")
	public boolean compareyOBMaps() {
		if (yoB_actual.equals(yoB_expected)) {
			return true;
		}
		return false;
	}

	@Step("Click on Join Button")
	public void clickJoinbutton() {
		joinBtn.waitUntilClickable().click();
	}

	@Step("Check Validation Messages appears if Organization question drop down value is not selected")
	public String checkOrgErrorMessage() {

		errorMessage = orgQuesErrorMessage.waitUntilClickable().isDisplayed();
		if (errorMessage == true) {
			return orgQuesErrorMessage.getText();
		}
		return "Error message not displayed";
	}

	@Step("Check Validation Messages appears if Affliation question drop down value is not selected")
	public String checkAffliationErrorMessage() {
		errorMessage = affiliationQuesErrorMessage.waitUntilClickable().isDisplayed();
		if (errorMessage == true) {
			return affiliationQuesErrorMessage.getText();
		}
		return "Error message is not displayed";
	}

	@Step("Check Validation Messages appears if Net Patient question drop down value is not selected")
	public String checkNetErrorMessage() {
		errorMessage = netPaitentQuesErrorMessage.waitUntilClickable().isDisplayed();
		if (errorMessage == true) {
			return netPaitentQuesErrorMessage.getText();
		}
		return "Error message is not displayed";
	}

	@Step("Check Validation Messages appears if estimated dollar amount question drop down value is not selected")
	public String checkdollarErrorMessage() {
		errorMessage = dollarQuesErrorMessage.waitUntilClickable().isDisplayed();
		if (errorMessage == true) {
			return dollarQuesErrorMessage.getText();
		}
		return "Error message is not displayed";
	}

	@Step("Check Validation Messages appears if Year of Birth drop down value is not selected")
	public String checkyobErrorMessage() {
		errorMessage = yOBQuesErrorMessage.waitUntilClickable().isDisplayed();
		if (errorMessage == true) {
			return yOBQuesErrorMessage.getText();
		}
		return "Error message is not displayed";
	}

	@Step("Check Validation Messages appears if State drop down value is not selected")
	public String checkstateErrorMessage() {
		errorMessage = stateQuesErrorMessage.waitUntilClickable().isDisplayed();
		if (errorMessage == true) {
			return stateQuesErrorMessage.getText();
		}
		return "Error message is not displayed";
	}

	@Step("Check Validation Messages appears if Create Password Field is left blank")
	public String checkpassErrorMessage() {
		errorMessage = createPasswordErrorMessage.waitUntilClickable().isDisplayed();
		if (errorMessage == true) {
			return createPasswordErrorMessage.getText();
		}
		return "Error message is not displayed";
	}

	@Step("Check Validation Messages appears if Vendor question check box value is not selected")
	public String checkvendorErrorMessage() {
		errorMessage = vendorQuesErrorMessage.waitUntilClickable().isDisplayed();
		if (errorMessage == true) {
			return vendorQuesErrorMessage.getText();
		}
		return "Error message is not displayed";
	}

	@Step("Check Validation Message appears if Sites question drop down value is not selected")
	public String checksiteErrorMessage() {
		errorMessage = siteQuesErrorMessage.isCurrentlyVisible();
		if (errorMessage == true) {
			return siteQuesErrorMessage.getText();
		}
		return "null";
	}

	@Step("Check Validation Message appears if No of Beds question drop down value is not selected")
	public String checkBedsErrorMessage() {
		errorMessage = bedsQuesErrorMessage.isCurrentlyVisible();
		if (errorMessage == true) {
			return bedsQuesErrorMessage.getText();
		}
		return "null";
	}

	@Step("Check Validation Messages appears if No of Physicians question drop down value is not selected")
	public String checkPhyErrorMessage() {
		errorMessage = physicianQuesErrorMessage.isCurrentlyVisible();
		if (errorMessage == true) {
			return physicianQuesErrorMessage.getText();
		}
		return "null";
	}

	public String checkErrorMessages(String orgError, String sitesError, String bedsError, String phyError,
			String affliationError, String netError, String dollarError, String yobError, String stateError,
			String passError, String vendorError) {

		String err1 = checkOrgErrorMessage();
		String err2 = checksiteErrorMessage();
		String err3 = checkBedsErrorMessage();
		String err4 = checkPhyErrorMessage();
		String err5 = checkAffliationErrorMessage();
		String err6 = checkNetErrorMessage();
		String err7 = checkdollarErrorMessage();
		String err8 = checkyobErrorMessage();
		String err9 = checkstateErrorMessage();
		String err10 = checkpassErrorMessage();
		String err11 = checkvendorErrorMessage();
		if (err1.equals(orgError) && err2.equals(sitesError) && err3.equals(bedsError) && err4.equals(phyError)
				&& err5.equals(affliationError) && err6.equals(netError) && err7.equals(dollarError)
				&& err8.equals(yobError) && err9.equals(stateError) && err10.equals(passError)
				&& (err11.equals(vendorError))) {
			return "true";
		}
		return "false";
	}

	@Step("Print Question Fields Maps")
	public void printQuestionsFieldsMaps() {

		LOGGER.info(
				"============Questions Drop Down, Check Box Values & Input fields (Actual - MAP)===================================");
		LOGGER.info(typeOfOrg_actual.toString());
		LOGGER.info(healthSystemAffliation_actual.toString());
		LOGGER.info(netPaitentRevenue_actual.toString());
		LOGGER.info(selectingVendor_actual.toString());
		LOGGER.info(estimatedDollarAmount_actual.toString());
		LOGGER.info("Create Password Field Appearing : " + createPass_actual.toString());
		LOGGER.info("Phone Number Field Appearin :" + phoneNumberField_actual.toString());
		LOGGER.info("Title Field Appearing :" + titleField_actual.toString());
		LOGGER.info("State Drop Down Appearing :" + stateField_actual.toString());
		LOGGER.info("Year of Birth Drop Down Appearing :" + yoB_actual.toString());
		LOGGER.info(
				"*******************************************************************************************************************");
		LOGGER.info(
				"============Questions Drop Down, Check Box Values & Input fields (Expected - MAP)===================================");
		LOGGER.info(typeOfOrg_actual.toString());
		LOGGER.info(healthSystemAffliation_expected.toString());
		LOGGER.info(netPaitentRevenue_expected.toString());
		LOGGER.info(selectingVendor_expected.toString());
		LOGGER.info(estimatedDollarAmount_expected.toString());
		LOGGER.info("Create Password Field Should Be : " + createPass_expected.toString());
		LOGGER.info("Phone Number Field Should Be :" + phoneNumberField_expected.toString());
		LOGGER.info("Title Field Should Be :" + titleField_expected.toString());
		LOGGER.info("State Drop Down Should Be :" + stateField_expected.toString());
		LOGGER.info("Year of Birth Drop Down Should Be :" + yoB_expected.toString());
		LOGGER.info(
				"---------------------------------------------------------------------------------------------------------------------");
	}

	@Step("Validate thankyou or almost there popup on join IC form if user joins insights council")
	public boolean joinInsightsCouncil_thankyouPopUp(String iCJoin) {

		if (!iCJoin.equals("no")) {
			return (thankYouPopUp.isDisplayed() && manageProfileAlertsLink.isDisplayed()
					&& goToHomePageLink.isDisplayed()) ? true : false;
		}
		LOGGER.info("User Qualified Insights Council but didn't joined");
		return true;
	}
}
