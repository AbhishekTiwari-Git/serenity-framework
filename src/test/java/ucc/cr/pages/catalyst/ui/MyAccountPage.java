package ucc.cr.pages.catalyst.ui;

import io.restassured.response.Response;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.cr.steps.catalyst.MyAccountSteps;
import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;
import ucc.utils.CucumberUtils.CucumberUtils;
import io.cucumber.datatable.DataTable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By.ByXPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyAccountPage extends PageObject {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String baseUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("myaccount.base.url");
	public static Response sysResp = null;
	public static HashMap<Integer, String> MyAccountAudienceSegment = new HashMap<Integer, String>();
	public static HashMap<Integer, String> MyAccountProfessionalCategory = new HashMap<Integer, String>();
	public static HashMap<Integer, String> MyAccountQualifiedForCounsil = new HashMap<Integer, String>();
	public static HashMap<Integer, String> MyAccountICMember = new HashMap<Integer, String>();
	private static final Logger LOGGER = LoggerFactory.getLogger(MyAccountPage.class);
	CommonFunc commonFunc = new CommonFunc();
	JoinIC joinIc = new JoinIC();
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	public static Map<String, String> kMap;

	@FindBy(xpath = "//*[starts-with(@id,'capture_signInEmbedded_signInEmailAddress')]")
	WebElementFacade emailId;

	@FindBy(xpath = "//*[starts-with(@id,'capture_signInEmbedded_currentPassword')]")
	WebElementFacade Password;

	@FindBy(xpath = "//div[@class='widget-placeholder']//form[@name='signInForm']//button[contains(text(),'Sign In')]")
	WebElementFacade SignIn;

	@FindBy(xpath = "//h1[contains(text(),'My Account')]")
	WebElementFacade myAccountPageHeading;

	@FindBy(xpath = "//b[contains(text(),'Role')]//ancestor::td//following-sibling::td")
	WebElementFacade Role;

	@FindBy(xpath = "//b[contains(text(),'Place of Work or Study')]//ancestor::td//following-sibling::td")
	WebElementFacade PlaceofWork;

	@FindBy(xpath = "//b[contains(text(),'Specialty')]//ancestor::td//following-sibling::td[contains(text(),'Allergy')]")
	WebElementFacade Specialty;

	@FindBy(xpath = "//a[@id='btnEditPersonal']/span[contains(text(),'Edit')]")
	WebElementFacade personalInformationEditBtn;

	@FindBy(xpath = "//*[starts-with(@name,'roleCode')]/following-sibling::span")
	WebElementFacade openRoleDropDown;

	@FindBy(xpath = "//span[contains(text(),'CEO/President/Board Member')]")
	WebElementFacade ceoPresidentBoardMember;

	@FindBy(xpath = "//span[contains(text(),'C-Suite')]")
	WebElementFacade cSuite;

	@FindBy(xpath = "//span[contains(text(),'VP/Director')]")
	WebElementFacade vpDirector;

	@FindBy(xpath = "//span[contains(text(),'Manager')]")
	WebElementFacade manager;

	@FindBy(xpath = "//span[contains(text(),'Consultant')]")
	WebElementFacade consultant;

	@FindBy(xpath = "//span[contains(text(),'Business Executive – Other')]")
	WebElementFacade businessExecutive;

	@FindBy(xpath = "//span[contains(text(),'Chief Medical Officer')]")
	WebElementFacade chiefMedicalOfficer;

	@FindBy(xpath = "//span[contains(text(),'VP of Medical Affairs')]")
	WebElementFacade vpOfMedicalAffairs;

	@FindBy(xpath = "//span[contains(text(),'Chief or Chair Service/Department')]")
	WebElementFacade chiefOfChairService;

	@FindBy(xpath = "//span[contains(text(),'Associate Chief or Vice of Chair Service/Department')]")
	WebElementFacade associateChief;

	@FindBy(xpath = "//span[contains(text(),'Chief of Service Line')]")
	WebElementFacade chiefOfServiceLine;

	@FindBy(xpath = "//span[contains(text(),'VP, Director, Manager of Service Line')]")
	WebElementFacade vpDirectorManagerOfServiceLine;

	@FindBy(xpath = "//span[contains(text(),'Dean of Medical School')]")
	WebElementFacade deanOfMedicalSchool;

	@FindBy(xpath = "//span[contains(text(),'Program/Residency/Fellowship Director')]")
	WebElementFacade programResidencyFellowship;

	@FindBy(xpath = "//span[contains(text(),'Clinician')]")
	WebElementFacade clinician;

	@FindBy(xpath = "//span[contains(text(),'Student')]")
	WebElementFacade student;

	@FindBy(xpath = "//span[contains(text(),'Educator')]")
	WebElementFacade educator;

	@FindBy(xpath = "//span[contains(text(),'Researcher')]")
	WebElementFacade researcher;

	@FindBy(xpath = "//span[contains(text(),'Other') and not(contains(text(),'Business'))]")
	WebElementFacade other;

	@FindBy(xpath = "//span[contains(text(),'Resident')]")
	WebElementFacade resident;

	@FindBy(xpath = "//button[@id='savePersonalBox']")
	WebElementFacade saveChanges;

	@FindBy(xpath = "//span[contains(text(),'Your changes have been saved.')]")
	WebElementFacade saveChangesSuccesMessage;

	@FindBy(xpath = "//*[starts-with(@name,'placeWorkStudy')]/following-sibling::span")
	WebElementFacade openPlaceOfWorkDropDown;

	@FindBy(xpath = "//*[starts-with(@name,'countryCode')]/following-sibling::span")
	WebElementFacade openCountryDropDown;

	@FindBy(xpath = "//*[starts-with(@name,'suffixCode')]/following-sibling::span")
	WebElementFacade openSuffixDropDown;

	@FindBy(xpath = "//*[starts-with(@name,'primarySpecialtyCode')]/following-sibling::span")
	WebElementFacade openSpecialtyDropDown;

	@FindBy(xpath = "//span[contains(text(),'Urgent Care Medicine') and contains(@class,'jcf-option')]")
	WebElementFacade specialtyUrgenCare;

	@FindBy(xpath = "//span[contains(text(),'MPH') and contains(@class,'jcf-option')]")
	WebElementFacade suffixMPH;

	@FindBy(xpath = "//span[contains(text(),'PA-C') and contains(@class,'jcf-option')]")
	WebElementFacade suffixPAC;

	@FindBy(xpath = "//span[contains(text(),'NP/ApN') and contains(@class,'jcf-option')]")
	WebElementFacade suffixNP;

	@FindBy(xpath = "//span[contains(text(),'RN') and contains(@class,'jcf-option')]")
	WebElementFacade suffixRN;

	@FindBy(xpath = "//span[contains(text(),'PhD') and contains(@class,'jcf-option') and not(contains(text(),'MD'))]")
	WebElementFacade suffixPhD;

	@FindBy(xpath = "//span[contains(text(),'MD/PhD') and contains(@class,'jcf-option')]")
	WebElementFacade suffixMdPhD;

	@FindBy(xpath = "//span[contains(text(),'MBBS') and contains(@class,'jcf-option')]")
	WebElementFacade suffixMBBS;

	@FindBy(xpath = "//span[contains(text(),'Ancillary')]")
	WebElementFacade placeofWorkAncillary;

	@FindBy(xpath = "//span[contains(text(),'Clinic') and contains(@class,'jcf-option')]")
	WebElementFacade placeofWorkClinic;

	@FindBy(xpath = "//span[contains(text(),'College') and contains(@class,'jcf-option')]")
	WebElementFacade placeofWorkCollege;

	@FindBy(xpath = "//span[contains(text(),'Government Agency') and contains(@class,'jcf-option')]")
	WebElementFacade placeofWorkGovernmentAgency;

	@FindBy(xpath = "//span[contains(text(),'Health Plan/HMO/Insurer') and contains(@class,'jcf-option')]")
	WebElementFacade placeofWorkHealthPlan;

	@FindBy(xpath = "//span[contains(text(),'Hospital, Community') and contains(@class,'jcf-option')]")
	WebElementFacade placeofWorkHospitalCommunity;

	@FindBy(xpath = "//span[contains(text(),'Hospital, Other') and contains(@class,'jcf-option')]")
	WebElementFacade placeofWorkHospitalOther;

	@FindBy(xpath = "//span[contains(text(),'Hospital, University or Teaching') and contains(@class,'jcf-option')]")
	WebElementFacade placeofWorkHospitalUniversityTeaching;

	@FindBy(xpath = "//span[contains(text(),'Health System') and contains(@class,'jcf-option')]")
	WebElementFacade placeofWorkHealthSystem;

	@FindBy(xpath = "//span[contains(text(),'Long-term Care/SNF') and contains(@class,'jcf-option')]")
	WebElementFacade placeofWorkLongTermCare;

	@FindBy(xpath = "//span[contains(text(),'Medical School Program') and contains(@class,'jcf-option')]")
	WebElementFacade placeofWorkMedicalSchoolProgram;

	@FindBy(xpath = "//span[contains(text(),'Physician Organization') and contains(@class,'jcf-option')]")
	WebElementFacade placeofWorkPhysicianOrganizaton;

	@FindBy(xpath = "//span[contains(text(),'Research Facility') and contains(@class,'jcf-option')]")
	WebElementFacade placeofWorkResearchFacility;

	@FindBy(xpath = "//span[contains(text(),'Other') and contains(@class,'jcf-option') and not(contains(text(),'Hospital'))]")
	WebElementFacade placeofWorkOther;

	@FindBy(xpath = "//span[contains(text(),'Pharmaceutical Company') and contains(@class,'jcf-option')]")
	WebElementFacade placeofWorkPharma;

	@FindBy(xpath = "//span[contains(text(),'Professional Services') and contains(@class,'jcf-option')]")
	WebElementFacade placeofWorkProfServices;

	@FindBy(xpath = "//span[contains(text(),'Technology/Equipment Vendor or Supplier') and contains(@class,'jcf-option')]")
	WebElementFacade placeofWorkTechEquipment;

	@FindBy(xpath = "//a[@id='btnEditPassword']")
	WebElementFacade btnEditPwd;

	@FindBy(xpath = "//input[@id='oldPassword']")
	WebElementFacade oldPassword;

	@FindBy(xpath = "//input[@id='newPassword']")
	WebElementFacade newPassword;

	@FindBy(xpath = "//input[@id='confirmPassword']")
	WebElementFacade confirmPassword;

	@FindBy(xpath = "//div[@id='newPassword_validate']")
	WebElementFacade newPwdValidate;

	@FindBy(xpath = "//div[@id='oldPassword_validate']")
	WebElementFacade oldPwdValidate;

	@FindBy(xpath = "//div[@id='confirmPassword_validate']")
	WebElementFacade confirmPwdValidate;

	@FindBy(xpath = "//button[@id='mySubmit']")
	WebElementFacade saveChangesPwd;

	@FindBy(xpath = "//a[contains(text(),'Renew')]")
	WebElementFacade MyAccountFooterRenewLink;

	@FindBy(xpath = "//a[contains(text(),'Pay Bill')]")
	WebElementFacade MyAccountFooterPaybillLink;

	@FindBy(xpath = "//a[@class='cat-btn_tertiary']")
	WebElementFacade MyAccountPayBillLink;

	@FindBy(xpath = "//div[@class='cat-form_cell']/input[@id='firstName']")
	WebElementFacade fname;

	@FindBy(xpath = "//div[@class='cat-form_cell']/input[@id='lastName']")
	WebElementFacade lname;

	@FindBy(xpath = "//div[@class='cat-form_cell']/select[@id='suffixCode']")
	WebElementFacade suffix;

	@FindBy(xpath = "//input[@id='capture_signIn_signInEmailAddress']")
	WebElementFacade emailAddress;

	@FindBy(xpath = "//input[@id='capture_signIn_currentPassword']")
	WebElementFacade password;

	@FindBy(xpath = "//b[contains(text(),'Email Address')]//ancestor::td/following-sibling::td")
	WebElementFacade accInfo_emailadd;

	@FindBy(xpath = "//b[contains(text(),'Name') and not(contains(text(),'Organization'))]//ancestor::td/following-sibling::td")
	public WebElementFacade personalInfo_Name;

	@FindBy(xpath = "//b[contains(text(),'Role')]//ancestor::td/following-sibling::td")
	public WebElementFacade personalInfo_Role;

	@FindBy(xpath = "//b[contains(text(),'Name of Organization')]//ancestor::td/following-sibling::td")
	public WebElementFacade personalInfo_Org;

	@FindBy(xpath = "//b[contains(text(),'Place of Work or Study')]//ancestor::td/following-sibling::td")
	public WebElementFacade personalInfo_place;

	@FindBy(xpath = "//b[contains(text(),'Country/Region')]//ancestor::td/following-sibling::td")
	public WebElementFacade personalInfo_country;

	@FindBy(xpath = "//b[contains(text(),'Specialty')]//ancestor::td/following-sibling::td")
	public WebElementFacade personalInfo_specialty;

	@FindBy(xpath = "//a[@id='btnEditEmail']")
	public WebElementFacade btnEditEmail;

	@FindBy(xpath = "//input[@id='newEmail']")
	public WebElementFacade inputNewEmail;

	@FindBy(xpath = "//input[@id='confirmEmail']")
	public WebElementFacade inputconfirmNewEmail;

	@FindBy(xpath = "//button[@id='saveEmail']")
	WebElementFacade saveChanges_Email;

	@FindBy(xpath = "//*[starts-with(@maxlength,'120')]")
	WebElementFacade inputOrganization;

	@FindBy(xpath = "//div[@id='emailForm_error']/span")
	WebElementFacade emailMainErrorMsg;

	@FindBy(xpath = "//div[contains(text(),'Your Email Address is not a valid format')]")
	WebElementFacade formatErrorMsg;

	@FindBy(xpath = "//button[@id='saveEmail']/following-sibling::button")
	WebElementFacade cancelEmail;

	@FindBy(xpath = "//label[contains(text(),'Confirm New Password')]/ancestor::div[@class='cat-form_row']/following-sibling::div//button[contains(text(),'Cancel')]")
	WebElementFacade cancelPwd;

	@FindBy(xpath = "//label[contains(text(),'Confirm New Password')]/ancestor::div[@class='cat-form_row']/following-sibling::div//button[contains(text(),'Save')]")
	WebElementFacade saveChanges_Pwd;

	@FindBy(xpath = "//div[@id='oldPassword_validate']")
	WebElementFacade oldPwdErrorMsg;

	@FindBy(xpath = "//div[@id='firstName_validate']")
	public WebElementFacade fnameErrorMsg;

	@FindBy(xpath = "//div[@id='lastName_validate']")
	public WebElementFacade lnameErrorMsg;

	@FindBy(xpath = "//div[@id='suffixCode_validate']")
	public WebElementFacade suffixErrorMsg;

	@FindBy(xpath = "//div[@id='roleCode_validate']")
	public WebElementFacade roleErrorMsg;

	@FindBy(xpath = "//*[starts-with(text(),'Please enter Name of Organization')]")
	public WebElementFacade orgErrorMsg;

	@FindBy(xpath = "//div[@id='placeWorkStudy_validate']")
	public WebElementFacade placeErrorMsg;

	@FindBy(xpath = "//div[@id='countryCode_validate']")
	public WebElementFacade countryErrorMsg;

	@FindBy(xpath = "//*[starts-with(@id,'newEmail_validate')]")
	WebElementFacade inValidNewEmailMsg;

	@FindBy(xpath = "//div[@id='signIn']//button[contains(text(),'Sign In')]")
	WebElementFacade submitSignIn;

	@FindBy(xpath = "//*[starts-with(@id,'uccOrganization')]")
	WebElementFacade organizationQues;

	@FindBy(xpath = "//*[starts-with(@id,'uccHealthAffiliation')]")
	WebElementFacade healthAffliationQues;

	@FindBy(xpath = "//*[starts-with(@id,'uccNetpatientRevenue')]")
	WebElementFacade uccNetPatientRevenueQues;

	@FindBy(xpath = "//span[contains(text(),'Determine the business need')]")
	WebElementFacade vendorsQues;

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

	@FindBy(xpath = "//h1[contains(text(),'You qualify to join the ')]")
	WebElementFacade qualifyToJoinMsg;

	@Step("Launch My Account URL")
	public void myAccountUrl() throws Throwable {
		commonFunc.Launch_URL(baseUrl);
	}

	@Step("Login to the myAccount")
	public void login(String email, String pass) throws InterruptedException {
		emailId.waitUntilVisible();
		emailId.waitUntilClickable().type(email);
		Password.waitUntilClickable().type(pass);
		commonFunc.clickElement(SignIn);

	}

	@Step("User is on My Account Page")
	public boolean userAtMyAccountPage() {
		boolean checkHead = myAccountPageHeading.waitUntilVisible().isDisplayed();
		return (checkHead) ? true : false;
	}

	@Step("User Personal Information is displayed")
	public void userPersonalInfomration() {
		Role.waitUntilVisible().isDisplayed();
		PlaceofWork.waitUntilVisible().isDisplayed();
	}

	@Step("Creating Method to Set Map Values for My Account and Akamai")
	public void ValidateMyAccount(String roleName, String place, String email, String expectedASeg,
			String expectedProCat, String expectedQualifiedCouncil, String expectedICM, String expectedSuffix) {
		System.out.println(
				"************Starting Tests for - " + roleName + "And" + place + "******************************");
		// *******Setting up values in a Map, these values are expected in
		// Akamai for this case***********

		MyAccountICMember.put(1, null);
		MyAccountQualifiedForCounsil.put(1, "false");
		MyAccountAudienceSegment.put(1, expectedASeg);
		MyAccountProfessionalCategory.put(1, expectedProCat);

		if (expectedQualifiedCouncil.equalsIgnoreCase("TRUE")) {
			MyAccountQualifiedForCounsil.put(1, "true");
		}

		if (expectedICM.equals("true")) {
			MyAccountICMember.put(1, "true");
		}

		commonFunc.clickElement(saveChanges);
	}

	@Step("Audience Type Clinician User updates personal information")
	public void audienceTypeClinician(String email, String role, String place, String asegment, String profCategory,
			String qualifiedCouncil, String ICMember, String suffix) {
		personalInformationEditBtn.waitUntilClickable().click();
		openRoleDropDown.waitUntilClickable().click();
		switch (role) // nested switch
		{

		case "CEO":
			ceoPresidentBoardMember.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkAncillary.waitUntilClickable().click();
			openSpecialtyDropDown.waitUntilClickable().click();
			specialtyUrgenCare.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "C-Suite":
			cSuite.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkCollege.waitUntilClickable().click();
			openSpecialtyDropDown.waitUntilClickable().click();
			specialtyUrgenCare.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "VP/Director":
			vpDirector.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkGovernmentAgency.waitUntilClickable().click();
			openSpecialtyDropDown.waitUntilClickable().click();
			specialtyUrgenCare.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "Manager":
			manager.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkHealthPlan.waitUntilClickable().click();
			openSpecialtyDropDown.waitUntilClickable().click();
			specialtyUrgenCare.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "Consultant":
			consultant.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkHospitalCommunity.waitUntilClickable().click();
			openSpecialtyDropDown.waitUntilClickable().click();
			specialtyUrgenCare.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "Business Executive":
			businessExecutive.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkHospitalOther.waitUntilClickable().click();
			openSpecialtyDropDown.waitUntilClickable().click();
			specialtyUrgenCare.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "CMO":
			chiefMedicalOfficer.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkHospitalUniversityTeaching.waitUntilClickable().click();
			openSpecialtyDropDown.waitUntilClickable().click();
			specialtyUrgenCare.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "VP of Medical Affairs":
			vpOfMedicalAffairs.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkHealthSystem.waitUntilClickable().click();
			openSpecialtyDropDown.waitUntilClickable().click();
			specialtyUrgenCare.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "Chief or Chair Service":
			chiefOfChairService.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkLongTermCare.waitUntilClickable().click();
			openSpecialtyDropDown.waitUntilClickable().click();
			specialtyUrgenCare.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "Associate Chief":
			associateChief.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkMedicalSchoolProgram.waitUntilClickable().click();
			openSpecialtyDropDown.waitUntilClickable().click();
			specialtyUrgenCare.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "Chief of Service Line":
			chiefOfServiceLine.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkPhysicianOrganizaton.waitUntilClickable().click();
			openSpecialtyDropDown.waitUntilClickable().click();
			specialtyUrgenCare.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "VP Director":
			vpDirectorManagerOfServiceLine.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkResearchFacility.waitUntilClickable().click();
			openSpecialtyDropDown.waitUntilClickable().click();
			specialtyUrgenCare.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "Dean of Medical School":
			deanOfMedicalSchool.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkCollege.waitUntilClickable().click();
			openSpecialtyDropDown.waitUntilClickable().click();
			specialtyUrgenCare.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "Program Residency":
			programResidencyFellowship.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkCollege.waitUntilClickable().click();
			openSpecialtyDropDown.waitUntilClickable().click();
			specialtyUrgenCare.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "Clinician":
			clinician.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkCollege.waitUntilClickable().click();
			openSpecialtyDropDown.waitUntilClickable().click();
			specialtyUrgenCare.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "Resident":
			resident.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkCollege.waitUntilClickable().click();
			openSpecialtyDropDown.waitUntilClickable().click();
			specialtyUrgenCare.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "Student":
			student.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkCollege.waitUntilClickable().click();
			openSpecialtyDropDown.waitUntilClickable().click();
			specialtyUrgenCare.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "Educator":
			educator.waitUntilClickable().click();
			openSuffixDropDown.waitUntilClickable().click();
			suffixMPH.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkCollege.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "Researcher":
			researcher.waitUntilClickable().click();
			openSuffixDropDown.waitUntilClickable().click();
			suffixPAC.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkCollege.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "Other":
			other.waitUntilClickable().click();
			openSuffixDropDown.waitUntilClickable().click();
			suffixNP.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkCollege.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		default:

			System.out.println("No Role Specified, Now Checking Place of Work");

		}

		switch (place) // nested switch
		{
		case "Other":
			other.waitUntilClickable().click();
			openSuffixDropDown.waitUntilClickable().click();
			suffixRN.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkOther.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "Pharma":
			other.waitUntilClickable().click();
			openSuffixDropDown.waitUntilClickable().click();
			suffixPhD.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkPharma.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "ProfServices":
			other.waitUntilClickable().click();
			openSuffixDropDown.waitUntilClickable().click();
			suffixMdPhD.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkProfServices.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		case "TechVendor":
			other.waitUntilClickable().click();
			openSuffixDropDown.waitUntilClickable().click();
			suffixMBBS.waitUntilClickable().click();
			openPlaceOfWorkDropDown.waitUntilClickable().click();
			placeofWorkTechEquipment.waitUntilClickable().click();
			openSpecialtyDropDown.waitUntilClickable().click();
			specialtyUrgenCare.waitUntilClickable().click();
			ValidateMyAccount(role, place, email, asegment, profCategory, qualifiedCouncil, ICMember, suffix);
			break;

		default:

			System.out.println("***************No Place of Work Specified*******");

		}
	}

	@Step("Validate Correct values being displayed")
	public void printMaps() {
		// My Account Map - With Expected Values of Audience Segment,
		// Professional Catefory and Qualified for Council
		System.out
				.println("=======================My ACCOUNT (Expected Values MAP)===================================");
		System.out.println("Audience Segment - Expected");
		System.out.println(MyAccountAudienceSegment);
		System.out.println("--------------------------------");
		System.out.println("Professioanl Category - Expected");
		System.out.println(MyAccountProfessionalCategory);
		System.out.println("--------------------------------");
		System.out.println("Qualified For Council - Expected");
		System.out.println(MyAccountQualifiedForCounsil);
		System.out.println("--------------------------------");
		System.out.println("Insights Counsil Memebr (true/false) - Expected");
		System.out.println(MyAccountICMember);
		System.out.println("--------------------------------");

		// Akamai Map - With Actual Values of Audience Segment, Professional
		// Catefory and Qualified for Council
		System.out.println("=======================(AKAMAI Actual Values MAP)===================================");
		System.out.println("Audience Segment - Actual");
		System.out.println(MyAccountSteps.akamaiAudienceSegment);
		System.out.println("--------------------------------");
		System.out.println("Professioanl Category - Actual");
		System.out.println(MyAccountSteps.akamaiProfessionalCategory);
		System.out.println("--------------------------------");
		System.out.println("Qualified For Council - Actual");
		System.out.println(MyAccountSteps.akamaiQualifiedForCounsil);
		System.out.println("--------------------------------");
		System.out.println("Insights Counsil Memebr (true/false) - Actual");
		System.out.println(MyAccountSteps.akamaiICMember);
		System.out.println("--------------------------------");
	}

	public void inputOldPassword(String oldPwd) {
		oldPassword.waitUntilClickable().type(oldPwd);
	}

	public void inputNewPassword(String newPwd) {
		newPassword.waitUntilClickable().type(newPwd);
	}

	public void inputConfirmPassword(String confirmPwd) {
		confirmPassword.waitUntilClickable().type(confirmPwd);
	}

	public boolean isEditPwdBtn() {
		return btnEditPwd.waitUntilClickable().isClickable();
	}

	public void click_edit_pwd_btn() {
		btnEditPwd.waitUntilClickable().click();
	}

	public boolean newPwdErrorMsgIsVisible() {
		return newPwdValidate.waitUntilPresent().isVisible();
	}

	public boolean oldPwdErrorMsgIsVisible() {
		return oldPwdValidate.waitUntilPresent().isVisible();
	}

	public boolean confirmErrorMsgIsVisible() {
		return confirmPwdValidate.waitUntilPresent().isVisible();
	}

	public void saveChangesPwdClick() {
		saveChangesPwd.waitUntilClickable().click();
	}

	public boolean isSaveChangesPwdSuccessMessage() {
		return saveChangesSuccesMessage.waitUntilPresent().isVisible();
	}

	@Step("Click Footer Renew link")
	public void ClickFooterRenewLink() throws InterruptedException {
		commonFunc.scrollAndClickElement(MyAccountFooterRenewLink);

	}

	@Step("Click Footer Paybill link")
	public void ClickFooterPaybillLink() throws InterruptedException {
		commonFunc.scrollAndClickElement(MyAccountFooterPaybillLink);

	}

	@Step("Click PayBill link")
	public void ClickPayBillLink() throws InterruptedException {
		commonFunc.scrollAndClickElement(MyAccountPayBillLink);

	}

	@Step("update firstname and last name in my account personal information")
	public void updateFn_Ln(String updatedFName, String updatedLName) {
		fname.waitUntilClickable().clear();
		lname.waitUntilClickable().clear();
		fname.type(updatedFName);
		lname.type(updatedLName);
		tUtil.putToSession("fName", updatedFName);
		tUtil.putToSession("lName", updatedLName);
	}

	@Step("validate first name and last name on my account page")
	public boolean validateFnameLname(String expFname, String expLname) {
		personalInformationEditBtn.waitUntilClickable().click();
		LOGGER.info("=======================EXPECTED FNAME=====================");
		LOGGER.info(expFname);
		LOGGER.info("==========================================================");
		LOGGER.info("=======================EXPECTED LNAME=====================");
		LOGGER.info(expLname);
		LOGGER.info("==========================================================");
		LOGGER.info("=======================ACTUAL FNAME=====================");
		LOGGER.info(fname.getTextValue());
		LOGGER.info("==========================================================");
		LOGGER.info("========================ACTUAL LNAME=======================");
		LOGGER.info(lname.getTextValue());
		LOGGER.info("==========================================================");
		return (expFname.equals(fname.getTextValue()) && expLname.equals(lname.getTextValue())) ? true : false;
	}

	public void inputEmail(String email) {
		emailAddress.clear();
		emailAddress.waitUntilClickable().type(email);
	}

	public void inputPassword(String pwd) {
		password.clear();
		password.waitUntilClickable().type(pwd);

	}

	@Step("Validate email in my account information section")
	public boolean validateEmail_AccountInformation(String expEmail) {

		LOGGER.info("================Expected - Email in Account Information==========");
		LOGGER.info(expEmail);
		LOGGER.info("=================================================================");
		String accountInformation_email = accInfo_emailadd.waitUntilVisible().getText();
		LOGGER.info("================Actual - Email in Account Information==========");
		LOGGER.info(accountInformation_email);
		LOGGER.info("=================================================================");
		return (expEmail.equals(accountInformation_email)) ? true : false;
	}

	@Step("Validate details like firsname, lastname, suffix, organization, role, place, country & specialty in persoanl information section")
	public boolean validatePersonalInformation(Map<String, String> kMapExp, Map<String, String> kMapActual) {
		LOGGER.info("================Actual - Personal Information==========");
		LOGGER.info(kMapActual.toString());
		LOGGER.info("=======================================================");
		LOGGER.info("================Expected - Personal Information==========");
		LOGGER.info(kMapExp.toString());
		LOGGER.info("=========================================================");
		return (kMapActual.equals(kMapExp)) ? true : false;

	}

	@Step("User update email in Account informaiton")
	public void updateEmail_AccInfo(String newEmail) {
		btnEditEmail.waitUntilClickable().click();
		inputNewEmail.waitUntilClickable().type(newEmail);
		inputconfirmNewEmail.waitUntilClickable().type(newEmail);
		commonFunc.clickElement(saveChanges_Email);
	}

	@Step("User validate updated email in Account Information")
	public boolean validateUpdatedEmail(String updatedEmail) {
		LOGGER.info("===========EXPECTED EMAIL AFTER UPDATE/EDIT=================");
		LOGGER.info(updatedEmail);
		LOGGER.info("=================================================");
		LOGGER.info("===========ACTUAL EMAIL AFTER UPDATE/EDIT===================");
		LOGGER.info(accInfo_emailadd.waitUntilVisible().getText());
		LOGGER.info("============================================================");
		return (updatedEmail.equals(accInfo_emailadd.waitUntilVisible().getText())) ? true : false;

	}

	@Step("user update role, place, org, country, specialty in my account")
	public Map<String, String> updateRle_PlcCtr_OrgSpec(DataTable dt) {
		Map<String, String> kMapInfo = new HashMap<String, String>();
		kMapInfo = CucumberUtils.convert(dt);
		selectHiddenDropDownVal(kMapInfo.get("Role"), "Role");
		selectHiddenDropDownVal(kMapInfo.get("Place of Work or Study"), "Place of Work or Study");
		selectHiddenDropDownVal(kMapInfo.get("Country/Region"), "Country/Region");
		selectHiddenDropDownVal(kMapInfo.get("Specialty"), "Primary Specialty");
		inputOrganization.waitUntilClickable().clear();
		inputOrganization.type(kMapInfo.get("Name of Organization"));
		
		kMapInfo.put("Name", (String) tUtil.getFromSession("fName") + " " + (String) tUtil.getFromSession("lName") + " "
				+ (String) tUtil.getFromSession("Suffix"));
		return kMapInfo;
	}

	/*
	 * Below function select value from my account personal information dropdown's
	 * These drop down are hidden so select by value or visible text is not possible
	 * Hidden select Drop down - suffix, role, place, specialty, country
	 */
	public void selectHiddenDropDownVal(String hiddenDropDownVal, String keyAttribute) {
		WebElementFacade selectDropDown = find(ByXPath
				.xpath("//label[contains(text(),'" + keyAttribute + "')]/ancestor::div/following-sibling::div/span"));
		WebElementFacade hiddenWebElement = find(ByXPath
				.xpath("//span[contains(text(),'" + hiddenDropDownVal + "') and contains(@class,'jcf-option')]"));
		selectDropDown.waitUntilClickable().click();
		hiddenWebElement.waitUntilClickable().click();
		tUtil.putToSession(keyAttribute, hiddenDropDownVal);
	}

	public void selectPerInfoEditBtn() {
		commonFunc.clickElement(personalInformationEditBtn);
	}

	public void saveChanges() {
		commonFunc.clickElement(saveChanges);
	}

	public void saveChanges_pwd() {
		commonFunc.clickElement(saveChanges_Pwd);
	}

	@Step("User validate message on entering wrong email")
	public boolean validateWrongEmailMsg(String expMainMsg, String expFormatErrorMsg) {
		LOGGER.info("=======EMAIL Error Messgae : Actual==============");
		LOGGER.info(emailMainErrorMsg.waitUntilVisible().getText());
		LOGGER.info(formatErrorMsg.waitUntilVisible().getText());
		LOGGER.info("=================================================");
		LOGGER.info("=======EMAIL Error Message : Expected============");
		LOGGER.info(expMainMsg);
		LOGGER.info(expFormatErrorMsg);
		LOGGER.info("=================================================");
		return (emailMainErrorMsg.getText().equals(expMainMsg) && formatErrorMsg.getText().equals(expFormatErrorMsg))
				? true
				: false;
	}

	public void clickCancelEmailBtn() {
		cancelEmail.waitUntilClickable().click();
	}

	public void clickCancelPwdBtn() {
		cancelPwd.waitUntilClickable().click();
	}

	@Step("User validate error message for old password being invalid")
	public boolean validateOldPwdErrorMsg(String oldPwdErrorMsgExp) {
		LOGGER.info("=======Old PwdError Messgae : Actual==============");
		LOGGER.info(oldPwdErrorMsg.waitUntilVisible().getText());
		LOGGER.info("=================================================");
		LOGGER.info("=======Old PwdError Messgae : Expected==============");
		LOGGER.info(oldPwdErrorMsgExp);
		LOGGER.info("====================================================");
		return oldPwdErrorMsg.waitUntilVisible().getText().equals(oldPwdErrorMsgExp) ? true : false;
	}

	@Step("User update blank value in role, place, org, country and specialty")
	public void updateBlankValues(DataTable dt) {
		Map<String, String> kMapInfo = new HashMap<String, String>();
		kMapInfo = CucumberUtils.convert(dt);
		selectHiddenDropDownVal(kMapInfo.get("role"), "Role");
		inputOrganization.clear();
		selectHiddenDropDownVal(kMapInfo.get("place"), "Place of Work or Study");
		selectHiddenDropDownVal(kMapInfo.get("country"), "Country/Region");
	}

	@Step("User validate AIC updates on my account")
	public boolean validateAICUpdates_OnMyAccount(String file) {
		tUtil.putToSession("firstName", jsonUtils.getFromJSON(file, "firstName"));
		personalInformationEditBtn.waitUntilClickable().click();
		String actualFname = fname.getTextValue();
		return tUtil.getFromSession("firstName").equals(actualFname) ? true : false;
	}

	@Step("User validate existing email update by editing on my account information")
	public boolean existingEmailValidation(String existingMsg) {
		LOGGER.info("=============================================");
		LOGGER.info("Adding Existing Email under Edit New Email");
		LOGGER.info("Getting Validation Message : "+ inValidNewEmailMsg.waitUntilVisible().getText());
		return (inValidNewEmailMsg.getText().equals(existingMsg)) ? true : false;
	}

	public void submitPassAndSignIn(String pass) {
		password.waitUntilPresent().type(pass);
		submitSignIn.waitUntilClickable().click();
		qualifyToJoinMsg.waitUntilVisible();
	}

	public void joinIC(DataTable dt) throws InterruptedException {
		kMap = CucumberUtils.convert(dt);
		TimeUnit.SECONDS.sleep(3); // will remove this timeout once UCC-3000 is resolved.
		qualifyToJoinMsg.waitUntilVisible();
		organizationQues.waitUntilClickable().selectByValue(kMap.get("OrgQues"));
		healthAffliationQues.waitUntilClickable().selectByValue(kMap.get("AffQues"));
		uccNetPatientRevenueQues.waitUntilClickable().selectByValue(kMap.get("NetQues"));
		yearOfBirth.waitUntilClickable().selectByValue(kMap.get("Yob"));

		if (state.isCurrentlyVisible()) {
			state.waitUntilPresent().selectByValue(kMap.get("State"));
		}
		LOGGER.info("Create Password field will not appear for Registered User");
		joinIc.selectVendor_dollarQues(kMap.get("Role_Place"), kMap.get("DollarVal"), kMap.get("VendorVal"));
		joinBtn.waitUntilClickable().click();
		joinIc.thankYouPopUp.isDisplayed();
	}
}
