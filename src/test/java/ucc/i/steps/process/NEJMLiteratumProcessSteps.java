package ucc.i.steps.process;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//import org.json.simple.JSONObject;
import org.junit.Assert;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONObject;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.i.method.nejmliteratumprocess.NEJMLiteratumProcessPOST;
import ucc.i.method.nejmliteratumsystem.NEJMLiteratumSystemGET;
import ucc.i.method.nejmliteratumsystem.NEJMLiteratumSystemPOST;
import ucc.i.steps.experience.AccountExpSteps;
import ucc.i.steps.system.NEJMLiteratumSystemJsonBodyFile;
import ucc.utils.JsonUtils;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;
import ucc.utils.YamlUtils;

public class NEJMLiteratumProcessSteps {

	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
	private static String autoEmail = EnvironmentSpecificConfiguration.from(envVar).getProperty("autoEmail");
	private static Response procResp = null;
	private static Response sysResp = null;
	public static Map<String, String> kmap = new HashMap<String, String>();
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	private static String endPoint = null;
	private String email = null;
	private String ucid = null;
	private String firstName = null;
	private String lastName = null;
	AccountExpSteps accountExpSteps = new AccountExpSteps();
	private SoftAssertions softAssert = new SoftAssertions();
	private boolean procRespTagLabel = false;
	private boolean procRespTagCode = false;
	private boolean procRespTagSetCode = false;
	YamlUtils YamlUtil = new YamlUtils();

	@Steps
	NEJMLiteratumProcessPOST nejmLiteratumProcessPOST;

	@Steps
	NEJMLiteratumSystemPOST nejmLiteratumSystemPOST;

	@Steps
	NEJMLiteratumSystemGET nejmLiteratumSystemGET;

	@When("I do POST to licenses NEJMLiteratumProc with the created user")
	public void i_do_post_to_licences_nejm_literatum_proc_with_the_registered_user() throws Exception {
		String file = NEJMLiteratumProcJsonBodyFile.NEJM_License;
		jsonUtils.update_JSONValue(file, "ucid", (String) tUtil.getFromSession("ucid"));

		endPoint = nejmLiteratumProcessPOST.setEndpontLicenses();
		procResp = nejmLiteratumProcessPOST.post(file, endPoint).extract().response();

		tUtil.putToSession("response", procResp);
	}

	@When("I do POST to licenses NEJMLiteratumProc with the created user without ucid in the payload")
	public void i_do_post_to_licenses_nejm_literatum_proc_with_the_created_user_without_ucid_in_the_payload()
			throws Exception {
		String file = NEJMLiteratumProcJsonBodyFile.NEJM_License;
		jsonUtils.update_JSONValue(file, "ucid", "");

		endPoint = nejmLiteratumProcessPOST.setEndpontLicenses();
		procResp = nejmLiteratumProcessPOST.post(file, endPoint).extract().response();

		tUtil.putToSession("response", procResp);
	}

	@When("I GET the licences user using ucid from NEJMLiteratumSys")
	public void i_get_the_user_using_ucid_from_nejm_literatum_sys() {
		endPoint = nejmLiteratumSystemGET.setEndpontIdentitiesUCIDLicences((String) tUtil.getFromSession("ucid"));
		sysResp = nejmLiteratumSystemGET.get(endPoint).extract().response();
	}

	@Then("I see 200 and licence in the response")
	public void i_see_and_licence_in_the_response() {
		tUtil.verifyStatus(sysResp, ResponseCode.OK);

		Assert.assertNotNull(sysResp.jsonPath().get("[0].offerCode"));
	}

	@When("I POST identity to NEJMLiteratumSys for the RegisteredUser")
	public void i_post_identity_to_nejm_literatum_sys_for_the_registered_user() throws Exception {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_CreateID_With_MandatoryFields;
		jsonUtils.update_JSONValue(file, "$.ucid", accountExpSteps.kmap.get("ucid"));
		jsonUtils.update_JSONValue(file, "$.email", accountExpSteps.kmap.get("email"));
		jsonUtils.update_JSONValue(file, "$.firstName", accountExpSteps.kmap.get("firstName"));
		jsonUtils.update_JSONValue(file, "$.lastName", accountExpSteps.kmap.get("lastName"));

		endPoint = nejmLiteratumSystemPOST.setEndpontIdentitiesPerson();
		procResp = nejmLiteratumSystemPOST.post(file, endPoint).extract().response();
	}

	@When("I POST identity to NEJMLiteratumSys for the Lead user")
	public void i_post_identity_to_nejm_literatum_sys_for_the_lead_user() throws Exception {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_CreateID_With_MandatoryFields;
		jsonUtils.update_JSONValue(file, "$.ucid", accountExpSteps.kmap.get("ucid"));
		jsonUtils.update_JSONValue(file, "$.email", accountExpSteps.kmap.get("email"));

		endPoint = nejmLiteratumSystemPOST.setEndpontIdentitiesPerson();
		procResp = nejmLiteratumSystemPOST.post(file, endPoint).extract().response();
	}

	@When("I do POST to identity ACSsubscription endpoint for the Lead user")
	public void i_do_post_to_identity_ac_ssubscription_endpoint_for_the_lead_user() throws Exception {
		String file = NEJMLiteratumProcJsonBodyFile.NEJM_identity;
		jsonUtils.update_JSONValue(file, "$.ucid", kmap.get("ucid"));

		endPoint = nejmLiteratumProcessPOST.setEndpontIdentityACSsubscription();
		procResp = nejmLiteratumProcessPOST.post(file, endPoint).extract().response();

		tUtil.putToSession("response", procResp);
	}

	@When("I do POST to identity ACSsubscription endpoint for the registeredUser")
	public void i_do_post_to_identity_ac_ssubscription_endpoint_for_the_registered_user() throws Exception {
		String file = NEJMLiteratumProcJsonBodyFile.NEJM_identity;
		jsonUtils.update_JSONValue(file, "$.ucid", kmap.get("ucid"));

		endPoint = nejmLiteratumProcessPOST.setEndpontIdentityACSsubscription();
		procResp = nejmLiteratumProcessPOST.post(file, endPoint).extract().response();

		tUtil.putToSession("response", procResp);
	}

	@When("I do GET for the user to NEJMLiteratumSys identities using ucid")
	public void i_do_get_for_the_user_to_nejm_literatum_sys_identities_using_ucid() {
		endPoint = nejmLiteratumSystemGET.setEndpontIdentitiesUCID(kmap.get("ucid"));
		procResp = nejmLiteratumSystemGET.get(endPoint).extract().response();

		tUtil.putToSession("response", procResp);
	}

	@Then("I see 200 and the user in response with tag field")
	public void i_see_and_the_user_in_response_with_tag_field() {
		tUtil.verifyStatus(procResp, ResponseCode.OK);

		Assert.assertNotNull(procResp.jsonPath().get("tag"));
	}

	@Then("I see 201 and the user in NEJMLiteratumSys api")
	public void i_see_and_the_user_in_nejm_literatum_sys_api() {
		tUtil.verifyStatus(procResp, ResponseCode.CREATED);

		Assert.assertNotNull(procResp.jsonPath().get("ucid"));

		kmap.put("ucid", procResp.jsonPath().get("ucid"));
	}

	@When("User sends a POST request to create person identity to NEJMLiteratum")
	public void POST_request_toCreate_Person_Identity_to_NEJMLitratum() throws Exception {
		endPoint = nejmLiteratumProcessPOST.setEndpointIdentityPerson();
		procResp = nejmLiteratumProcessPOST.createPersonID(endPoint, tUtil.getFromSession("ucid").toString()).extract()
				.response();
	}

	@Then("person identity should be created with status code 201")
	public void person_identity_should_be_created() {
		tUtil.verifyStatus(procResp, ResponseCode.CREATED);
	}

	@When("user sends a POST call to provide person an institutional license")
	public void POST_request_toProvide_Person_institutional_license() throws Exception {
		endPoint = nejmLiteratumProcessPOST
				.setEndpointinstitutionaladminlicense(tUtil.getFromSession("ucid").toString());
		procResp = nejmLiteratumProcessPOST
				.provideInstitutionalLicense(endPoint, tUtil.getFromSession("inid").toString()).extract().response();
	}

	@Then("The Person should get linked to the institutional license with status code 201")
	public void person_got_linked_to_institutionalLicense() {
		tUtil.verifyStatus(procResp, ResponseCode.CREATED);
	}

	@When("user sends a GET call to fetch the person identity linked with {int} institutions")
	public void i_get_the_person_identity(Integer instCount) throws InterruptedException {
		int timeOutMin = 12;
		endPoint = nejmLiteratumSystemGET.setEndpointIdentity(tUtil.getFromSession("ucid").toString());
		do {
			System.out.println("Waiting for request Nejm Literatum System to get finished");
			TimeUnit.SECONDS.sleep(5);
			procResp = nejmLiteratumSystemGET.get(endPoint).extract().response();
			tUtil.verifyStatus(procResp, ResponseCode.OK);
			List<Map<String, String>> respMaps = procResp.jsonPath().getList("tag");
			for (Map<String, String> map : respMaps) {
				procRespTagLabel = (map.containsValue("Yes"));
				procRespTagCode = (map.containsValue("y"));
				procRespTagSetCode = map.containsValue("institutionadminindicator");
				if ((procRespTagLabel && procRespTagCode) && procRespTagSetCode) {
					break;
				}
			}
			if ((procResp.jsonPath().get("related-identity") != null) && procRespTagLabel
					&& (procResp.jsonPath().getList("related-identity").size() == instCount)) {
				break;
			}
			timeOutMin--;

		} while (timeOutMin != 0);
	}
	
	@When("user sends a GET call to fetch the multiple person identities linked to single inid")
	public void i_get_the_prsnIds_linked_toAnInid() throws InterruptedException {
		endPoint = nejmLiteratumSystemGET.setEndpointInstitutionIdentities(tUtil.getFromSession("inid").toString());
		procResp = nejmLiteratumSystemGET.get(endPoint).extract().response();
	}

	@Then("I verify tags for institutional admin indicator and related-identity with status code 200")
	public void verify_institnal_adminIndicator_and_relatedIdentity_tags() {
		softAssert.assertThat(procRespTagLabel).isTrue();
		softAssert.assertThat(procRespTagCode).isTrue();
		softAssert.assertThat(procRespTagSetCode).isTrue();
		softAssert.assertThat(procResp.jsonPath().get("related-identity.related-identity-type").toString()).isEqualTo("[admined-org]");
		softAssert.assertThat(procResp.jsonPath().get("related-identity.id-value").toString()).isNotNull();

		softAssert.assertAll();
	}

	@Then("I verify respective related-identity should get linked to a multiple inid with status code 200")
	public void verify_related_identity_linked_to_multiple_inid() {
		softAssert.assertThat(procResp.jsonPath().getList("related-identity").size()).isEqualTo(2);
		softAssert.assertThat(procRespTagLabel).isTrue();
		softAssert.assertThat(procRespTagCode).isTrue();
		softAssert.assertThat(procRespTagSetCode).isTrue();

		softAssert.assertAll();
	}

	@When("user sends a GET call to fetch the institution identities")
	public void i_get_the_institution_identities() throws InterruptedException {
		endPoint = nejmLiteratumSystemGET.setEndpointInstitutionIdentities(tUtil.getFromSession("inid").toString());
		procResp = nejmLiteratumSystemGET.get(endPoint).extract().response();
	}

	@Then("I verify respective related-identities should get linked to a single inid with status code 200")
	public void verify_multiple_related_identities_linked_to_single_inid() {
		softAssert.assertThat(procResp.getStatusCode()).isEqualTo(ResponseCode.OK);
		softAssert.assertThat(procResp.jsonPath().getList("related-identity").size()).isEqualTo(2);
		softAssert.assertThat(procResp.jsonPath().get("inid").toString()).isEqualTo(tUtil.getFromSession("inid"));
		softAssert.assertAll();
	}
	
	@When("^user sends a POST call for Nejm Literatum process api to provide person an institutional license with product code (.*)$")
	public void POST_request_in_nejmLitertm_for_instnalAdmin_activate(String prdct_code) throws Exception {
		JSONObject codeInfo = new JSONObject(YamlUtil.getValueFromYml("nejm_litrtm_prdctCodes.yml", "/" + "ProductCodes"));
		String code = codeInfo.getString(prdct_code);
		endPoint = nejmLiteratumProcessPOST
				.setEndpointinstitutionaladminlicense(tUtil.getFromSession("ucid").toString());
		procResp = nejmLiteratumProcessPOST
				.provideInstitutionalLicense_brocker_api(endPoint, tUtil.getFromSession("inid").toString(), code).extract().response();
	}
	
	@Then("The Person should get linked to the institutional license with status code 201 for NEJM Literatum proc brocker api")
	public void person_got_linked_to_institutionalLicense_brocker() {
		tUtil.verifyStatus(procResp, ResponseCode.CREATED);
	}
}
