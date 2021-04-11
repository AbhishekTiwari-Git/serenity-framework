package ucc.cr.pages.nejm.ui.utils;

import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.CucumberUtils.CucumberUtils;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;

public class NEJRegPageHelper {

	public static Map<String, String> NejHelper_kmap = new HashMap<String, String>();
	public static Map<String, String> kMap_exp;
	private static final Logger LOGGER = LoggerFactory.getLogger(NEJRegPageHelper.class);
	CommonFunc commonFunc = new CommonFunc();
	String user_email;
	JsonUtils jsonUtils = new JsonUtils();
	String alertsMapFile = "specialtyAlertsMapping.json";
	String specialtyCodeList = "specialtyCodeList.json";
	String actualLiteratumValue = "null";

	public boolean akamaiValidations(Response res, DataTable dt) {
		NejHelper_kmap.clear();
		kMap_exp = CucumberUtils.convert(dt);
		NejHelper_kmap.put("Promo", fetchPromo(kMap_exp.get("Promo"), res));
		NejHelper_kmap.put("AuType", fetch("audienceType", res));
		NejHelper_kmap.put("CatAuSeg", fetch("Catalyst.audienceSegment", res));
		NejHelper_kmap.put("CatICQual", fetch("Catalyst.qualifiedForCouncil", res));
		NejHelper_kmap.put("NEJAuSeg", fetch("Nejm.audienceSegment", res));
		NejHelper_kmap.put("NEJSubStatus", fetch("Nejm.subscriptionStatus", res));
		NejHelper_kmap.put("Org", fetch("organizationName", res));
		NejHelper_kmap.put("Place", getPlace(kMap_exp.get("Place"), res));
		NejHelper_kmap.put("PSpecialty", fetchSpecialty(kMap_exp.get("PSpecialty"), res));
		NejHelper_kmap.put("Prof", getProfessionCode(kMap_exp.get("ProfCat"), res));
		NejHelper_kmap.put("ProfCat", fetch("professionalCategoryCode", res));
		NejHelper_kmap.put("SpGrp", getSpGroup(kMap_exp.get("SpGrp"), res));
		NejHelper_kmap.put("OBrand", fetch("originalBrand", res));
		NejHelper_kmap.put("CatICMember", getICMemberInfo(kMap_exp.get("CatICMember"), res));
		NejHelper_kmap.put("YOB", getYearOfBirth(kMap_exp.get("YOB"), res));
		NejHelper_kmap.put("Suffix", getSuffix(kMap_exp.get("Suffix"), res));
		NejHelper_kmap.put("NEJGeInfo", getNejmGeneralInfo(kMap_exp.get("NEJGeInfo"), res));
		NejHelper_kmap.put("NEJTOC", getNejmToc(kMap_exp.get("NEJTOC"), res));
		NejHelper_kmap.put("NEJWResident", getNejmWeeklyResident(kMap_exp.get("NEJWResident"), res));
		NejHelper_kmap.put("SAlerts", getsAlerts(kMap_exp.get("PSpecialty"), kMap_exp.get("SAlerts"), res));
		NejHelper_kmap.put("Role", getRole(kMap_exp.get("Role"), res));
		NejHelper_kmap.put("Country", fetch("countryCode", res));
		NejHelper_kmap.put("StudentType", getStudentTypeCode(kMap_exp.get("ProfCat"), res));

		LOGGER.info("===============Back End Validation : Akamai====================");
		LOGGER.info("*************Actual Values : ********************************");
		LOGGER.info(NejHelper_kmap.toString());
		LOGGER.info("-------------------------------------------------------------");
		LOGGER.info("*************Expected Values : ******************************");
		LOGGER.info(kMap_exp.toString());
		LOGGER.info("--------------------------------------------------------------");
		return NejHelper_kmap.equals(kMap_exp) ? true : false;
	}

	public String checkNull(String attributeType, Response res) {
		return (res.jsonPath().getString(attributeType) == null) ? "null" : res.jsonPath().getString(attributeType);
	}

	public String fetch(String attributeType, Response res) {
		return res.jsonPath().getString(attributeType);
	}

	private String getRole(String role, Response res) {
		return (role.equals("null") ? checkNull("roleCode", res) : fetch("roleCode", res));
	}

	private String getPlace(String place, Response res) {
		return (place.equals("null") ? checkNull("placeOfWorkOrStudyCode", res) : fetch("placeOfWorkOrStudyCode", res));
	}

	private String getSpGroup(String spGroup, Response res) {
		return (spGroup.equals("null") ? checkNull("specialtyGroup", res) : fetch("specialtyGroup", res));
	}

	private String fetchSpecialty(String specialty, Response res) {
		if (!specialty.equals("null")) {
			String code = fetch("primarySpecialtyCode", res);
			return jsonUtils.getFromJSON(specialtyCodeList, code);
		}
		return specialty;
	}

	private String fetchPromo(String promo, Response res) {
		return (fetch("source.source", res).contains(promo)) ? promo : checkNull("source.source", res);
	}

	private String getProfessionCode(String profCategory, Response res) {
		return (profCategory.equals("OTH")) ? res.jsonPath().getString("professionCode")
				: checkNull("professionCode", res);
	}

	private String getStudentTypeCode(String profCategory, Response res) {
		return (profCategory.equals("STU")) ? res.jsonPath().getString("studentTypeCode")
				: checkNull("studentTypeCode", res);
	}

	private String getICMemberInfo(String catICMember, Response res) {
		return (catICMember.equals("null")) ? checkNull("Catalyst.insightsCouncilMember", res)
				: fetch("Catalyst.insightsCouncilMember", res);
	}

	private String getYearOfBirth(String yob, Response res) {
		return (yob.equals("null")) ? checkNull("birthYear", res) : fetch("birthYear", res);
	}

	private String getSuffix(String suffix, Response res) {
		return (suffix.equals("null")) ? checkNull("suffix", res) : fetch("suffix", res);
	}

	private String getNejmWeeklyResident(String nejWResident, Response res) {
		return (nejWResident.equals("null")) ? checkNull("emailPreferences.nejmWeeklyResidentBriefing.optIn", res)
				: fetch("emailPreferences.nejmWeeklyResidentBriefing.optIn", res);
	}

	private String getNejmToc(String nejWResident, Response res) {
		return (nejWResident.equals("null")) ? checkNull("emailPreferences.nejmTOC.optIn", res)
				: fetch("emailPreferences.nejmTOC.optIn", res);
	}

	private String getNejmGeneralInfo(String nejWResident, Response res) {
		return (nejWResident.equals("null")) ? checkNull("emailPreferences.nejmGeneralInformation.optIn", res)
				: fetch("emailPreferences.nejmGeneralInformation.optIn", res);
	}

	private String getsAlerts(String specialty, String sAlert, Response res) {
		if (!specialty.equals("null")) {
			String sAlertType = jsonUtils.getFromJSON(alertsMapFile, "$['" + specialty + "']");
			String emailPref = "emailPreferences" + "." + sAlertType + "." + "optIn";
			LOGGER.info("------------------------------------------------------------------");
			LOGGER.info("Validating Alerts for Specialty in Akamai : " + specialty);
			LOGGER.info("Alerts for Specialty : " + specialty + "is->" + sAlertType);
			LOGGER.info("------------------------------------------------------------------");
			LOGGER.info("Value for " + sAlertType + " Alert in Akamai : "
					+ fetch("emailPreferences." + sAlertType + ".optIn", res));
			return (sAlert.contains("null")) ? checkNull(emailPref, res) : fetch(emailPref, res);
		}
		return specialty;
	}

	public boolean nejmLiteratumValidations(Response res, DataTable dt) {
		NejHelper_kmap.clear();
		kMap_exp = CucumberUtils.convert(dt);
		NejHelper_kmap.put("NEJAuSeg", validateAudienceSegment_Literatum(kMap_exp.get("NEJAuSeg"), res));
		NejHelper_kmap.put("UserStatus", validateUserStatus_Literatum(kMap_exp.get("UserStatus"), res));
		NejHelper_kmap.put("ProfCat", validateProfCat_Literatum(kMap_exp.get("ProfCat"), res));
		NejHelper_kmap.put("Role", validateRole_Literatum(kMap_exp.get("Role"), res));
		NejHelper_kmap.put("Place", validatePlace_Literatum(kMap_exp.get("Place"), res));
		NejHelper_kmap.put("SpecialtyGroup", validateLtSpecialtyGroup(kMap_exp.get("SpecialtyGroup"), res));

		LOGGER.info("===============Back End Validation : NEJM Literatum====================");
		LOGGER.info("*******************Actual Values : *****************************");
		LOGGER.info(NejHelper_kmap.toString());
		LOGGER.info("----------------------------------------------------------------");
		LOGGER.info("===============Expected Values : ===========================");
		LOGGER.info(kMap_exp.toString());
		LOGGER.info("----------------------------------------------------------------");
		return NejHelper_kmap.equals(kMap_exp) ? true : false;
	}

	private String validateAudienceSegment_Literatum(String auSeg, Response res) {
		actualLiteratumValue = res.jsonPath().getString("tag.tag-code").toString();
		return actualLiteratumValue.contains(auSeg.toLowerCase()) ? auSeg : "null";
	}

	private String validateUserStatus_Literatum(String userStatus, Response res) {
		actualLiteratumValue = res.jsonPath().getString("tag.tag-label").toString();
		return actualLiteratumValue.contains(userStatus) ? userStatus : "null";
	}

	private String validateProfCat_Literatum(String ProfCat, Response res) {
		actualLiteratumValue = res.jsonPath().getString("tag.tag-code").toString();
		return actualLiteratumValue.contains(ProfCat.toLowerCase()) ? ProfCat : "null";
	}

	private String validateRole_Literatum(String role, Response res) {
		actualLiteratumValue = res.jsonPath().getString("tag.tag-code").toString();
		return actualLiteratumValue.contains(role.toLowerCase()) ? role : "null";
	}

	private String validatePlace_Literatum(String place, Response res) {
		actualLiteratumValue = res.jsonPath().getString("tag.tag-code").toString();
		return actualLiteratumValue.contains(place.toLowerCase()) ? place : "null";
	}

	private String validateLtSpecialtyGroup(String sGroup, Response res) {
		actualLiteratumValue = res.jsonPath().getString("tag.tag-code").toString();
		return actualLiteratumValue.contains(sGroup.toLowerCase()) ? sGroup : "null";
	}
	
	public boolean akamaiValidations_SOIConnect(Response res, DataTable dt) {
		NejHelper_kmap.clear();
		kMap_exp = CucumberUtils.convert(dt);
		NejHelper_kmap.put("SOI", checkNull("emailPreferences.catalystSOI.optIn", res));
		NejHelper_kmap.put("Connect", checkNull("emailPreferences.catalystConnect.optIn", res));
		LOGGER.info("===============Back End Validation : Email Preferences Akama============");
		LOGGER.info("*******************Actual Values : *****************************");
		LOGGER.info(NejHelper_kmap.toString());
		LOGGER.info("----------------------------------------------------------------");
		LOGGER.info("===============Expected Values : ===========================");
		LOGGER.info(kMap_exp.toString());
		LOGGER.info("----------------------------------------------------------------");
		return NejHelper_kmap.equals(kMap_exp) ? true : false;
	}
	
}
