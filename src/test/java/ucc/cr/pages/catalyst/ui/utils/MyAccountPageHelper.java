package ucc.cr.pages.catalyst.ui.utils;

import ucc.cr.pages.catalyst.ui.CRWidgetsPage;
import ucc.cr.pages.catalyst.ui.MyAccountPage;
import ucc.cr.pages.catalyst.ui.RegInsighCouncilPage;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;
import ucc.utils.CucumberUtils.CucumberUtils;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;

public class MyAccountPageHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyAccountPageHelper.class);
	static TestUtils tUtil = new TestUtils();
	static JsonUtils jsonUtils = new JsonUtils();
	static String file;
	MyAccountPage myacc = new MyAccountPage();
	String roleFile = "roleCodeList.json";
	String placeFile = "placeCodeList.json";
	String countryFile = "countryCodeList.json";
	String specialtyFile = "specialtyCodeList.json";
	String kiFile = "Kinesys_ICProfile.json";

	public Map<String, String> setRegApiPersonalInfoDetailsInJsontoMap() {
		Map<String, String> myAccExp = new HashMap<String, String>();
		file = "registration_qualtoIC_RegisteredUser.json";
		myAccExp.put("Name", jsonUtils.getFromJSON(file, "firstName") + " "
				+ jsonUtils.getFromJSON(file, "lastName") + " "
				+ jsonUtils.getFromJSON(file, "suffix"));
		myAccExp.put("Role", getInfo(file, "role", roleFile));
		myAccExp.put("Name of Organization", jsonUtils.getFromJSON(file, "nameOfOrganization"));
		myAccExp.put("Place of Work or Study", getInfo(file, "placeOfWorkOrStudy", placeFile));
		myAccExp.put("Country/Region", getInfo(file, "country", countryFile));
		myAccExp.put("Specialty", getInfo(file, "specialty", specialtyFile));
		return myAccExp;
	}

	public Map<String, String> setpersonalInfo_ActualOnMyAccount() {
		Map<String, String> myAccActual = new HashMap<String, String>();
		myAccActual.put("Name", myacc.personalInfo_Name.waitUntilVisible().getText());
		myAccActual.put("Role", myacc.personalInfo_Role.waitUntilVisible().getText());
		myAccActual.put("Name of Organization", myacc.personalInfo_Org.getText());
		myAccActual.put("Place of Work or Study", myacc.personalInfo_place.waitUntilVisible().getText());
		myAccActual.put("Country/Region", myacc.personalInfo_country.waitUntilVisible().getText());
		myAccActual.put("Specialty", myacc.personalInfo_specialty.waitUntilVisible().getText());
		return myAccActual;
	}

	private String getInfo(String mainJsonFile, String personalInfoType, String infoFile) {

		String getAccPersonalInfo = jsonUtils.getFromJSON(mainJsonFile, personalInfoType);
		return jsonUtils.getFromJSON(infoFile, getAccPersonalInfo);

	}

	public Map<String, String> setAkamai(Response resp) {
		Map<String, String> akamaiMap = new HashMap<String, String>();
		akamaiMap.put("Name", backend(resp, "firstName") + " " + backend(resp, "lastName") + " "+ backend(resp, "suffix"));
		akamaiMap.put("Role", jsonUtils.getFromJSON(roleFile, backend(resp, "roleCode")));
		akamaiMap.put("Name of Organization", backend(resp, "organizationName"));
		akamaiMap.put("Place of Work or Study", jsonUtils.getFromJSON(placeFile, backend(resp, "placeOfWorkOrStudyCode")));
		akamaiMap.put("Country/Region", jsonUtils.getFromJSON(countryFile, backend(resp, "countryCode")));
		akamaiMap.put("Specialty", jsonUtils.getFromJSON(specialtyFile, backend(resp, "primarySpecialtyCode")));
		tUtil.putToSession("Place of Work or Study", backend(resp, "placeOfWorkOrStudyCode"));
		tUtil.putToSession("Country/Region", backend(resp, "countryCode"));
		LOGGER.info("====================Akamai Validation===================");
		return akamaiMap;
	}

	private String backend(Response sysResp, String attributeKey) {
		return sysResp.jsonPath().getString(attributeKey).toString();

	}

	private String getKinesis(String file, String mainAttribute, String subAtrribute, String kinesisCodeActual) {
		String kinesisExpectedCode = jsonUtils.getFromJSON(file, "$['" + mainAttribute + "']['" + subAtrribute + "']").toString();
		return (kinesisCodeActual.equals(kinesisExpectedCode)) ? subAtrribute : "Incorrect Value";
	}

	public Map<String, String> setKinesis(Response resp) {
		Map<String, String> kinesisMap = new HashMap<String, String>();
		String suffix = getKinesis(kiFile, "clinicaldesignation", (String) tUtil.getFromSession("Suffix"),backend(resp, "clinicalDesignation"));
		kinesisMap.put("Name", backend(resp, "firstName") + " " + backend(resp, "lastName")+ " "+ suffix);
		kinesisMap.put("Role", getKinesis(kiFile, "title", (String) tUtil.getFromSession("Role"),backend(resp, "title")));
		kinesisMap.put("Name of Organization", backend(resp, "organizationName"));
		String placeCd = getKinesis(kiFile, "organizationType", (String) tUtil.getFromSession("Place of Work or Study"), backend(resp, "organizationType"));
		kinesisMap.put("Place of Work or Study", jsonUtils.getFromJSON(placeFile, placeCd));
		String ctrCd = getKinesis(kiFile, "country", (String) tUtil.getFromSession("Country/Region") , backend(resp, "country"));
		kinesisMap.put("Country/Region", jsonUtils.getFromJSON(countryFile, ctrCd));
		kinesisMap.put("Specialty", (String) tUtil.getFromSession("Primary Specialty"));
		LOGGER.info("====================Kinesis Validation===================");
		return kinesisMap;
	
	}
	
	public Map<String, String> setPersonalInfo_Error(){
		Map<String, String> errorMap = new HashMap<String, String>();
		errorMap.put("fname", myacc.fnameErrorMsg.waitUntilVisible().getText());
		errorMap.put("lname", myacc.lnameErrorMsg.waitUntilVisible().getText());
		errorMap.put("suffix", myacc.suffixErrorMsg.waitUntilVisible().getText());
		errorMap.put("role", myacc.roleErrorMsg.waitUntilVisible().getText());
		errorMap.put("org", myacc.orgErrorMsg.waitUntilVisible().getText());
		errorMap.put("place", myacc.placeErrorMsg.waitUntilVisible().getText());
		errorMap.put("country", myacc.countryErrorMsg.waitUntilVisible().getText());
		return errorMap;
	}
	
}
