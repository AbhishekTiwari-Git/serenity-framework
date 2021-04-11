package ucc.i.steps.system;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.assertj.core.api.SoftAssertions;
import org.json.JSONException;
import org.json.JSONObject;
//import org.json.simple.JSONObject;
import org.junit.Assert;

import com.jcraft.jsch.Logger;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.i.method.catalystLiteratum.CatalystLiteratumSystemHelper;
import ucc.i.method.nejmliteratumsystem.NEJMLiteratumSystemGET;
import ucc.i.method.nejmliteratumsystem.NEJMLiteratumSystemPATCH;
import ucc.i.method.nejmliteratumsystem.NEJMLiteratumSystemPOST;
import ucc.i.method.nejmliteratumsystem.utils.NEJMLiteratumSystemHelper;
import ucc.utils.JsonUtils;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;
import ucc.utils.YamlUtils;

public class NEJMLiteratumSystemSteps {

	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
	private static String autoEmail = EnvironmentSpecificConfiguration.from(envVar).getProperty("autoEmail");
	private static Response sysResp = null;
	public static Map<String, String> kmap = new HashMap<String, String>();
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	private static String endPoint = null;
	private String email = null;
	private String ucid = null;
	private String firstName = null;
	private String lastName = null;
	private String updatedValue = null;
	YamlUtils YamlUtil = new YamlUtils();
	private SoftAssertions softAssert = new SoftAssertions();

	@Steps
	NEJMLiteratumSystemPOST nejmLiteratumSystemPOST;

	@Steps
	NEJMLiteratumSystemGET nejmLiteratumSystemGET;

	@Steps
	NEJMLiteratumSystemPATCH nejmLiteratumSystemPATCH;

	@Given("^user creates a person identity using mandatory fields only$")
	public void user_creates_person_identity_using_mandatory_fields_only() throws Exception {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_CreateID_With_MandatoryFields;
		email = tUtil.AppendTimestamp(autoEmail);
		ucid = tUtil.generateRandomUcid(36);
		firstName = tUtil.AppendTimestamp("firstName");
		lastName = tUtil.AppendTimestamp("lastName");

		jsonUtils.update_JSONValue(file, "$.email", email);
		jsonUtils.update_JSONValue(file, "$.ucid", ucid);
		jsonUtils.update_JSONValue(file, "$.firstName", firstName);
		jsonUtils.update_JSONValue(file, "$.lastName", lastName);

		endPoint = nejmLiteratumSystemPOST.setEndpontIdentitiesPerson();
		sysResp = nejmLiteratumSystemPOST.post(file, endPoint).extract().response();

		kmap.put("email", email);
		kmap.put("ucid", ucid);
		kmap.put("firstName", firstName);
		kmap.put("lastName", lastName);
	}

	@Given("^user creates a person identity using a valid random ucid$")
	public void user_creates_person_identity_using_valid_random_ucid() throws Exception {

		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_CreateNewIdentity;
		Map<String, String> map = NEJMLiteratumSystemHelper.updateFile_create_PersonID_random_ucid(file);
		kmap.putAll(map);

		endPoint = nejmLiteratumSystemPOST.setEndpontIdentitiesPerson();
		sysResp = nejmLiteratumSystemPOST.post(file, endPoint).extract().response();
	}

	@When("^I do PATCH call to update the fName lName and email$")
	public void i_do_PATCH_to_update_upFieldupValue() throws Exception {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_PATCH_wholeBody;
		email = tUtil.AppendTimestamp(autoEmail);
		firstName = tUtil.AppendTimestamp("firstName");
		lastName = tUtil.AppendTimestamp("lastName");

		jsonUtils.update_JSONValue(file, "$.email", email);
		jsonUtils.update_JSONValue(file, "$.firstName", firstName);
		jsonUtils.update_JSONValue(file, "$.lastName", lastName);

		endPoint = nejmLiteratumSystemPATCH.setEndpontIdentitiesPersonUCID(ucid);
		sysResp = nejmLiteratumSystemPATCH.patch(file, endPoint).extract().response();
	}

	@When("^I do PATCH call to update using update key (.*) with update value (.*)$")
	public void i_do_PATCH_call_to_update_using_upKey_with_upValue(String updateKey, String updateValue)
			throws Exception {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_PATCH_wholeBody;
		jsonUtils.update_JSONValue(file, "$.email", email);
		jsonUtils.update_JSONValue(file, "$.firstName", firstName);
		jsonUtils.update_JSONValue(file, "$.lastName", lastName);

		updatedValue = tUtil.AppendTimestamp(updateValue);
		jsonUtils.update_JSONValue(file, "$." + updateKey, updatedValue);

		endPoint = nejmLiteratumSystemPATCH.setEndpontIdentitiesPersonUCID(ucid);
		sysResp = nejmLiteratumSystemPATCH.patch(file, endPoint).extract().response();
	}

	@When("user creates a person identity using existing data")
	public void user_creates_person_identity_using_existing_data() {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_CreateNewIdentity;
		endPoint = nejmLiteratumSystemPOST.setEndpontIdentitiesPerson();
		sysResp = nejmLiteratumSystemPOST.post(file, endPoint).extract().response();
	}

	@When("user updates the identity using mandatory fields")
	public void user_updates_the_identity_using_mandatory_fields() throws Exception {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_PATCH_wholeBody;
		jsonUtils.update_JSONValue(file, "$.email", email);
		jsonUtils.update_JSONValue(file, "$.firstName", firstName);
		jsonUtils.update_JSONValue(file, "$.lastName", lastName);

		endPoint = nejmLiteratumSystemPATCH.setEndpontIdentitiesPersonUCID(ucid);
		sysResp = nejmLiteratumSystemPATCH.patch(file, endPoint).extract().response();
	}

	@When("user calls GET identity with ucid")
	public void user_calls_get_identity_with_ucid() {

		endPoint = nejmLiteratumSystemGET.setEndpontIdentitiesUCID(ucid);
		sysResp = nejmLiteratumSystemGET.get(endPoint).extract().response();
	}

	@Then("^I see update key (.*) got updated$")
	public void i_see_updated_key_got_updated_with_updated_value(String updated_key) {

		tUtil.verifyUpdatedValues(sysResp, updated_key, updatedValue);
	}

	@Then("newly created identity should be returned")
	public void newly_created_identity_should_be_returned() {

		tUtil.verifyStatus(sysResp, ResponseCode.OK);

		Assert.assertEquals(firstName, sysResp.jsonPath().getString("firstName"));
		Assert.assertEquals(lastName, sysResp.jsonPath().getString("lastName"));
		Assert.assertEquals(email, sysResp.jsonPath().getString("email"));
	}

	@Then("newly created identity should be returned with fname lname email and tag")
	public void newly_created_identity_should_be_returned_with_fNlNemailTag() {

		tUtil.verifyStatus(sysResp, ResponseCode.OK);

		Assert.assertEquals(firstName, sysResp.jsonPath().getString("firstName"));
		Assert.assertEquals(lastName, sysResp.jsonPath().getString("lastName"));
		Assert.assertEquals(email, sysResp.jsonPath().getString("email"));
		Assert.assertNotNull(sysResp.jsonPath().getString("tag"));
	}

	@Then("I see fname lname and email got updated")
	public void i_see_fname_lname_and_email_got_updated() {

		tUtil.verifyStatus(sysResp, ResponseCode.OK);

		Assert.assertEquals(firstName, sysResp.jsonPath().getString("firstName"));
		Assert.assertEquals(lastName, sysResp.jsonPath().getString("lastName"));
		Assert.assertEquals(email, sysResp.jsonPath().getString("email"));
	}

	@Then("identity should be created")
	public void identity_should_be_created() {

		tUtil.verifyStatus(sysResp, ResponseCode.CREATED);
	}

	@Then("identity should not be created")
	public void identity_should_not_be_created() {

		tUtil.verifyStatus(sysResp, ResponseCode.BAD_REQUEST);
	}

	@Then("newly created identity should be updated")
	public void newly_created_identity_should_be_updated() {

		tUtil.verifyStatus(sysResp, ResponseCode.UPDATED);
	}
	
	@When("I create a person identity using a valid random ucid")
	public void create_prsnIdentity() throws Exception {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_CreateNewIdentity;
		NEJMLiteratumSystemHelper.updateFile_create_prsnIdentity(file);
		
		endPoint = nejmLiteratumSystemPOST.setEndpontIdentitiesPerson();
		sysResp = nejmLiteratumSystemPOST.post(file, endPoint).extract().response();
	}

	@When("user sends a POST request to create an inid using existing data")
	public void user_creates_inid_using_existing_data() throws Exception {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Create_New_inid;
		jsonUtils.update_JSONValue(file, "inid", tUtil.generateRandomInid());
		endPoint = nejmLiteratumSystemPOST.setEndpontIdentitiesInstitution();
		sysResp = nejmLiteratumSystemPOST.post(file, endPoint).extract().response();
		tUtil.putToSession("inid", sysResp.jsonPath().get("inid"));
	}
	
	@When("^user sends a POST call in nejm literatum with (.*) and (.*) to create an inid$")
	public void create_new_inid(String key, String value) throws Exception {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Create_New_inid;
		NEJMLiteratumSystemHelper.POST_updateFile_create_inid_nejm(file, key, value);
		endPoint = nejmLiteratumSystemPOST.setEndpontIdentitiesInstitution();
		sysResp = nejmLiteratumSystemPOST.post(file, endPoint).extract().response();
		tUtil.putToSession("inid", sysResp.jsonPath().get("inid"));
	}
	
	@Then("inid should be created in nejm literatum with status code 201")
	public void verify_created_inid() {
		tUtil.verifyStatus(sysResp, ResponseCode.CREATED);
		Assert.assertEquals("INID missing from the response!", tUtil.getFromSession("inid"),
				sysResp.jsonPath().get("inid").toString());
	}
	
	@When("I send a GET call in nejm literatum call to fetch the inid info")
	public void GET_inid_nejm() throws InterruptedException {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Create_New_inid;
		String expAdd1 = jsonUtils.getFromJSON(file, "$.address..address1").replaceAll("\\[", "").replaceAll("\\]", "");
		String expAdd2 = jsonUtils.getFromJSON(file, "$.address..address2").replaceAll("\\[", "").replaceAll("\\]", "");
		int timeOutMin = 20;
		endPoint = nejmLiteratumSystemGET.setEndpointInstitutionIdentities(tUtil.getFromSession("inid").toString());
		do {
			System.out.println("Waiting for request NEJM Literatum System to get finished");
			TimeUnit.SECONDS.sleep(5);
			sysResp = nejmLiteratumSystemGET.get(endPoint).extract().response();
			tUtil.verifyStatus(sysResp, ResponseCode.OK);
			if ((sysResp.jsonPath().get("address.address1[0]").toString().equals(expAdd1)
					&& (sysResp.jsonPath().get("address.address2[0]").toString().equals(expAdd2)))) {
				break;
			}
			timeOutMin--;

		} while (timeOutMin != 0);
	}
	
	@Then("I should verify fetched institution identity for nejm literatum with status code 200")
	public void verify_fetched_inid_nejm() {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Create_New_inid;
		String expAdd1 = jsonUtils.getFromJSON(file, "$.address..address1").replaceAll("\\[", "").replaceAll("\\]", "");
		String expAdd2 = jsonUtils.getFromJSON(file, "$.address..address2").replaceAll("\\[", "").replaceAll("\\]", "");
		softAssert.assertThat(sysResp.statusCode()).isEqualTo(ResponseCode.OK);
		softAssert.assertThat(sysResp.jsonPath().getString("inid")).isEqualTo(tUtil.getFromSession("inid"));
		softAssert.assertThat(sysResp.jsonPath().getString("address.address1[0]")).isEqualTo(expAdd1);
		softAssert.assertThat(sysResp.jsonPath().getString("address.address2[0]")).isEqualTo(expAdd2);
		softAssert.assertAll();
	}
	
	@When("^I do a PATCH call in nejm literatum to update (.*) with a value (.*) in an institution identity$")
	public void PATCH_to_update_institution_identity(String key, String value) throws Exception {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Create_New_inid;
		NEJMLiteratumSystemHelper.PATCH_updateFile_create_inid_nejm(file, key, value);
		endPoint = nejmLiteratumSystemPATCH.setEndpointUpdateInid(tUtil.getFromSession("inid").toString());
		sysResp = nejmLiteratumSystemPATCH.patch(file, endPoint).extract().response();
	}
	
	@Then("inid info in nejm literatum should be updated with status code 204")
	public void verify_updated_institution_identity() {
		tUtil.verifyStatus(sysResp, ResponseCode.UPDATED);
	}
	
	@When("I send a POST request in nejm literatum to Create and assign license to an institution identity")
	public void create_assign_institution_license() throws Exception {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Institution_License;
		NEJMLiteratumSystemHelper.POST_updateFile_create_inst_license_nejm(file);
		endPoint = nejmLiteratumSystemPOST.setEndpointLicenses();
		sysResp = nejmLiteratumSystemPOST.postInstitutionLicense(file, endPoint).extract().response();
	}
	
	@Then("License should be created and assigned to an institution identity in nejm literatum with status code 201")
	public void verify_license_shouldBe_created() {
		tUtil.verifyStatus(sysResp, ResponseCode.CREATED);
	}
	
	@When("I send a GET request in nejm literatum to fetch licenses assigned to an Institution identity")
	public void fetch_institution_license_nejm() {
		endPoint = nejmLiteratumSystemGET.setEndpointInstitutionLicense(tUtil.getFromSession("inid").toString());
		sysResp = nejmLiteratumSystemGET.get(endPoint).extract().response();
	}
	
	@Then("I should verify the assigned license to an Institution identity in nejm literatum with status code 200")
	public void verify_fetched_institution_license() throws JSONException, IOException {
		JSONObject codeInfo = new JSONObject(YamlUtil.getValueFromYml("nejm_litrtm_prdctCodes.yml", "/" + "ProductCodes"));
		String offerCode = codeInfo.getString("Institution Offer Code");
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Institution_License;
		String actUniqCode = sysResp.getBody().jsonPath().getString("code[0]");
		softAssert.assertThat(sysResp.statusCode()).isEqualTo(ResponseCode.OK);
		softAssert.assertThat(actUniqCode).isEqualTo(jsonUtils.getFromJSON(file, "$.code"));
		softAssert.assertThat(sysResp.jsonPath().getString("offerCode[0]")).isEqualTo(offerCode);
		softAssert.assertAll();
	}
	
	@When("I send a PATCH request in nejm literatum to update the assigned institution identity license")
	public void update_institution_license_nejm() throws Exception {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Institution_License;
		NEJMLiteratumSystemHelper.PATCH_updateFile_create_inst_license_nejm(file);
		endPoint = nejmLiteratumSystemPATCH.setEndpointLicenses();

		sysResp = nejmLiteratumSystemPATCH.patchInstitutionLicense(file, endPoint).extract().response();
	}
	
	@Then("I should verify that institution license in nejm literatum should be updated with status code 204")
	public void verify_institution_license_shouldBe_updated() {
		tUtil.verifyStatus(sysResp, ResponseCode.UPDATED);
	}
	
	@When("I send a GET request in nejm literatum to fetch the updated licenses assigned to an Institution identity")
	public void fetch_updated_inst_license() throws InterruptedException {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Institution_License;
		int timeOutMin = 12;
		endPoint = nejmLiteratumSystemGET.setEndpointInstitutionLicense(tUtil.getFromSession("inid").toString());
		do {
			System.out.println("Waiting for request NEJM Literatum System to get finished");
			TimeUnit.SECONDS.sleep(5);
			sysResp = nejmLiteratumSystemGET.get(endPoint).extract().response();
			tUtil.verifyStatus(sysResp, ResponseCode.OK);
			if ((sysResp.jsonPath().getString("orderNumber[0]").equals(jsonUtils.getFromJSON(file, "orderNumber")))) {
				break;
			}
			timeOutMin--;

		} while (timeOutMin != 0);
	}
	
	@Then("I should verify the assigned license to an Institution identity in nejm literatum should be updated with status code 200")
	public void verify_fetched_updated_inst_license_response() {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Institution_License;
		softAssert.assertThat(sysResp.statusCode()).isEqualTo(ResponseCode.OK);
		softAssert.assertThat(sysResp.jsonPath().getString("orderNumber[0]"))
				.isEqualTo(jsonUtils.getFromJSON(file, "orderNumber"));
		softAssert.assertThat(sysResp.jsonPath().getString("startDate[0]"))
				.isEqualTo(jsonUtils.getFromJSON(file, "startDate").substring(0, 10));
		softAssert.assertThat(sysResp.jsonPath().getString("endDate[0]"))
				.isEqualTo(jsonUtils.getFromJSON(file, "endDate").substring(0, 10));
		softAssert.assertThat(sysResp.jsonPath().getString("expDate[0]"))
				.isEqualTo(jsonUtils.getFromJSON(file, "expDate").substring(0, 10));
		softAssert.assertAll();
	}
	
	@When("I send a POST request in nejm literatum to Create and assign license to a person identity")
	public void create_assign_person_license_nejm() throws Exception {
		String file = NEJMLiteratumSystemJsonBodyFile. NEJM_Person_License;
		NEJMLiteratumSystemHelper.POST_updateFile_create_person_license_nejm(file);
		endPoint = nejmLiteratumSystemPOST.setEndpointLicenses();
		sysResp = nejmLiteratumSystemPOST.postPersonLicense(file, endPoint).extract().response();
	}
	
	@Then("License should be created and assigned to a person identity in nejm literatum with status code 201")
	public void verify_Person_license_shouldBe_created() {
		tUtil.verifyStatus(sysResp, ResponseCode.CREATED);
	}
	
	@When("I send a GET request in nejm literatum to fetch licenses assigned to a person identity")
	public void fetch_person_license_nejm() {
		endPoint = nejmLiteratumSystemGET.setEndpointPersonLicences(tUtil.getFromSession("ucid").toString());
		sysResp = nejmLiteratumSystemGET.get(endPoint).extract().response();
	}
	
	@Then("I should verify the assigned license to a person identity in nejm literatum with status code 200")
	public void verify_fetched_person_license() throws JSONException, IOException {
		JSONObject codeInfo = new JSONObject(YamlUtil.getValueFromYml("nejm_litrtm_prdctCodes.yml", "/" + "ProductCodes"));
		String offerCode = codeInfo.getString("Person Offer Code");
		String file = NEJMLiteratumSystemJsonBodyFile. NEJM_Person_License;
		String actUniqCode = sysResp.getBody().jsonPath().getString("code[0]");
		softAssert.assertThat(sysResp.statusCode()).isEqualTo(ResponseCode.OK);
		softAssert.assertThat(actUniqCode).isEqualTo(jsonUtils.getFromJSON(file, "$.code"));
		softAssert.assertThat(sysResp.jsonPath().getString("offerCode[0]")).isEqualTo(offerCode);
		softAssert.assertAll();
	}
	
	@When("I send a PATCH request in nejm literatum to update the assigned person identity license")
	public void update_person_license_nejm() throws Exception {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Person_License;
		NEJMLiteratumSystemHelper.PATCH_updateFile_create_person_license_nejm(file);
		endPoint = nejmLiteratumSystemPATCH.setEndpointLicenses();

		sysResp = nejmLiteratumSystemPATCH.patchPersonLicense(file, endPoint).extract().response();
	}
	
	@Then("I should verify that person license in nejm literatum should be updated with status code 204")
	public void verify_person_license_shouldBe_updated() {
		tUtil.verifyStatus(sysResp, ResponseCode.UPDATED);
	}
	
	@When("I send a GET request in nejm literatum to fetch the updated licenses assigned to a person identity")
	public void fetch_updated_person_license() throws InterruptedException {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Person_License;
		int timeOutMin = 12;
		endPoint = nejmLiteratumSystemGET.setEndpointPersonLicences(tUtil.getFromSession("ucid").toString());
		do {
			System.out.println("Waiting for request NEJM Literatum System to get finished");
			TimeUnit.SECONDS.sleep(5);
			sysResp = nejmLiteratumSystemGET.get(endPoint).extract().response();
			tUtil.verifyStatus(sysResp, ResponseCode.OK);
			if ((sysResp.jsonPath().getString("orderNumber[0]").equals(jsonUtils.getFromJSON(file, "orderNumber")))) {
				break;
			}
			timeOutMin--;

		} while (timeOutMin != 0);
	}
	
	@Then("I should verify the assigned license to a person identity in nejm literatum should be updated with status code 200")
	public void verify_fetched_updated_person_license_response() {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Person_License;
		softAssert.assertThat(sysResp.statusCode()).isEqualTo(ResponseCode.OK);
		softAssert.assertThat(sysResp.jsonPath().getString("orderNumber[0]"))
				.isEqualTo(jsonUtils.getFromJSON(file, "orderNumber"));
		softAssert.assertThat(sysResp.jsonPath().getString("startDate[0]"))
				.isEqualTo(jsonUtils.getFromJSON(file, "startDate").substring(0, 10));
		softAssert.assertThat(sysResp.jsonPath().getString("endDate[0]"))
				.isEqualTo(jsonUtils.getFromJSON(file, "endDate").substring(0, 10));
		softAssert.assertThat(sysResp.jsonPath().getString("expDate[0]"))
				.isEqualTo(jsonUtils.getFromJSON(file, "expDate").substring(0, 10));

		softAssert.assertAll();
	}
	
	@When("I do a POST call in nejm literatum to create a Person identity and link to the institution")
	public void create_identities_person_nejm() throws Exception {
		String file = NEJMLiteratumSystemJsonBodyFile. NEJM_Create_PersonIdentity;
		NEJMLiteratumSystemHelper.POST_updateFile_create_person_identity_nejm(file);
		endPoint = nejmLiteratumSystemPOST.setEndpontIdentitiesPerson();
		sysResp = nejmLiteratumSystemPOST.post(file, endPoint).extract().response();
	}
	
	@Then("I verify that Person should be created in nejm literatum with status code 201")
	public void verify_persnId_create() {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Create_PersonIdentity;
		softAssert.assertThat(sysResp.statusCode()).isEqualTo(ResponseCode.CREATED);
		softAssert.assertThat(sysResp.jsonPath().getString("ucid")).isEqualTo(jsonUtils.getFromJSON(file, "ucid"));
		softAssert.assertAll();
	}
	
	@When("I do a GET call in nejm literatum to fetch the person identities")
	public void GET_created_persnId() {
		String file = NEJMLiteratumSystemJsonBodyFile. NEJM_Create_PersonIdentity;
		endPoint = nejmLiteratumSystemGET.setEndpointPerson(jsonUtils.getFromJSON(file, "ucid"));
		sysResp = nejmLiteratumSystemGET.get(endPoint).extract().response();
	}
	
	@Then("I should verify the fetched Person identity details in nejm literatum")
	public void verify_fetched_personId_details() {
		softAssert.assertThat(sysResp.jsonPath().get("related-identity.related-identity-type").toString())
				.isEqualTo("[admined-org]");
		softAssert.assertThat(sysResp.jsonPath().get("related-identity.id-value").toString()).isNotNull();
		softAssert.assertAll();
	}
	
	@When("I do a PATCH call in nejm literatum to update the person identities")
	public void PATCH_update_personID() throws Exception {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Create_PersonIdentity;
		NEJMLiteratumSystemHelper.PATCH_updateFile_create_person_identity_nejm(file);
		endPoint = nejmLiteratumSystemPATCH.setEndpontIdentitiesPersonUCID(jsonUtils.getFromJSON(file, "ucid"));
		sysResp = nejmLiteratumSystemPATCH.patch(file, endPoint).extract().response();
	}
	
	@Then("I should verify that person identities in nejm literatum should be updated with status code 204")
	public void verify_updated_person_identities() {
		tUtil.verifyStatus(sysResp, ResponseCode.UPDATED);
	}
	
	@When("I do a GET call in nejm literatum to fetch the Updated person identities")
	public void fetch_updated_person_identities() throws InterruptedException {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Create_PersonIdentity;
		int timeOutMin = 25;
		endPoint = nejmLiteratumSystemGET.setEndpointPerson(jsonUtils.getFromJSON(file, "ucid"));
		do {
			System.out.println("Waiting for request NEJM Literatum System to get finished");
			TimeUnit.SECONDS.sleep(5);
			sysResp = nejmLiteratumSystemGET.get(endPoint).extract().response();
			tUtil.verifyStatus(sysResp, ResponseCode.OK);
			if ((sysResp.jsonPath().get("firstName").toString()).equals(jsonUtils.getFromJSON(file, "firstName"))
					&& (sysResp.jsonPath().get("lastName").toString())
							.equals(jsonUtils.getFromJSON(file, "lastName"))) {
				break;
			}
			timeOutMin--;

		} while (timeOutMin != 0);
	}
	
	@Then("I should verify fetched updated person identity details in nejm literatum with status code 200")
	public void verify_fetched_updated_personId_details() {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Create_PersonIdentity;
		softAssert.assertThat(sysResp.jsonPath().get("firstName").toString())
				.isEqualTo(jsonUtils.getFromJSON(file, "firstName"));
		softAssert.assertThat(sysResp.jsonPath().get("lastName").toString())
				.isEqualTo(jsonUtils.getFromJSON(file, "lastName"));
		softAssert.assertAll();
	}

}
