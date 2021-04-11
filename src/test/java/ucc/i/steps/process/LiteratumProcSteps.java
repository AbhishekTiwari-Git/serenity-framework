package ucc.i.steps.process;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ucc.i.method.aic.AICGET;
import ucc.i.method.aic.AICPOST;
import ucc.i.method.literatum.LiteratumGET;
import ucc.i.method.literatum.LiteratumPOST;
import ucc.i.method.literatumprocess.CatalystliteratumprocessPOST;
import ucc.i.method.literatumprocess.LiteratumProcessPOST;
import ucc.i.method.nejmliteratumsystem.NEJMLiteratumSystemGET;
import ucc.i.steps.experience.AccountExpSteps;
import ucc.utils.CucumberUtils.CucumberUtils;
import ucc.utils.JsonUtils;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class LiteratumProcSteps {

	private static Response resp = null;
	private static Response procResp = null;
	private static Response sysResp = null;
	private static Map<String, String> kmap = new HashMap<String, String>();
	private static String endPt = null;
	private TestUtils tUtil = new TestUtils();
	private JsonUtils jsonUtils = new JsonUtils();
	private static String ucid;
	private static String emailValue;
	private static Response literRespIdentity = null;
	private static Response literRespLicense = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(LiteratumProcSteps.class);
	
	@Steps
	CatalystliteratumprocessPOST literatumCatalystProcPOSTSteps;

	@Steps
	LiteratumGET literatumGETSteps;
	
	@Steps
	LiteratumPOST literatumPOSTSteps;

	@Steps
	NEJMLiteratumSystemGET nejmLiteratumSystemGET;

	@Steps
	LiteratumProcessPOST literatumProcessPOST;

	@Steps
	AccountExpSteps accountExpSteps;

	@Steps
	AICPOST AICPOSTSteps;

    @Steps
	AICGET AICGETSteps;

	private final String file_name = "CatalystLiteratum_Post_ACS_Subscription_withall_mandatory_fields.json";
	private final String fileNameMandatoryFields =
		"CatalystLiteratum_Post_ACS_Subscription_withall_mandatory_fields.json";
	private final String fileNameMissingMandatoryFields =
		"CatalystLiteratum_Post_ACS_Subscription_missing mandatory_field.json";
	private final String fileNameMissingMandatoryFieldsIncorrectUcid =
		"CatalystLiteratum_Post_ACS_Subscription_withall_mandatory_fields_incorrect_ucid.json";
	private final String licences = "LiteratumProc/licences.json";
	private final String licencesCatalyst = "LiteratumProc/licencesCatalyst.json";
	private final String payMyBillCatalyst = "LiteratumProc/payMyBillCatalyst.json";
	private final String payMyBillNEJM = "LiteratumProc/payMyBillNEJM.json";
    private final String filePostLicense = "Literatum_Post_License.json";


	@Then("^I should see error because LEAD user is not qualified for this call$")
	public void verifyCatLiteratumRespcode500() {
		tUtil.verifyStatus(resp, ResponseCode.INTERNAL_ERROR);
	}

	@When("I do a GET call with ucid to Literatum System API to get the information about the user")
	public void i_do_a_get_call_with_ucid_to_literatum_system_api_to_get_the_information_about_the_user() {
	    endPt = literatumGETSteps.setEndpointIdentity(ucid);
	    resp = literatumGETSteps.get(endPt)
	    		.extract().response();
	}

	@When("I see 200 as status code and tags field in the response body along with email fname and lname of the user")
	public void iSee200asStatusCodeAndTagsFieldInTheResponseBodyAlongWithEmailFnameAndLnameOfTheUser() {

		tUtil.verifyStatus(resp, ResponseCode.OK);

		Assert.assertNotNull(resp.jsonPath().get("tag"));
		Assert.assertEquals(accountExpSteps.kmap.get("email"), resp.jsonPath().get("email"));
		Assert.assertEquals(accountExpSteps.kmap.get("firstName"), resp.jsonPath().get("firstName"));
		Assert.assertEquals(accountExpSteps.kmap.get("lastName"), resp.jsonPath().get("lastName"));
	}

	@When("I do POST call to Literatum Process API licences endpoint for NEJM")
	public void i_do_post_call_to_literatum_process_api_licences_endpoint_for_nejm() throws Exception {
		ucid = accountExpSteps.kmap.get("ucid");
	    jsonUtils.update_JSONValue(licences, "ucid", ucid);

	    endPt = literatumProcessPOST.setEndpointToLicenses();
	    resp = literatumProcessPOST.post(licences, endPt)
	    		.extract().response();
	}

	@When("I do POST call to Literatum Process API licences endpoint for Catalyst")
	public void i_do_post_call_to_literatum_process_api_licences_endpoint_for_catalyst() throws Exception {
		ucid = accountExpSteps.kmap.get("ucid");
	    jsonUtils.update_JSONValue(licencesCatalyst, "ucid", ucid);

	    endPt = literatumProcessPOST.setEndpointToLicenses();
	    resp = literatumProcessPOST.post(licencesCatalyst, endPt)
	    		.extract().response();
	}

	@When("I do a POST call to Literatum Process API paymybill endpoint using ucid for Catalyst")
	public void i_do_a_post_call_to_literatum_process_api_paymybill_endpoint_using_ucid_for_catalyst() {
		ucid = accountExpSteps.kmap.get("ucid");

		endPt = literatumProcessPOST.setEndpointIndentityPayMyBill(ucid);
		resp = literatumProcessPOST.post(payMyBillCatalyst, endPt)
	    		.extract().response();
	}

	@When("I do a POST call to Literatum Process API paymybill endpoint using ucid for NEJM")
	public void i_do_a_post_call_to_literatum_process_api_paymybill_endpoint_using_ucid_for_nejm() {
		ucid = accountExpSteps.kmap.get("ucid");

		endPt = literatumProcessPOST.setEndpointIndentityPayMyBill(ucid);
		resp = literatumProcessPOST.post(payMyBillNEJM, endPt)
	    		.extract().response();
	}

	@Then("I see the user got updated in Literatum with paid status")
	public void i_see_the_user_got_updated_in_literatum_with_paid_status() {

		tUtil.verifyStatus(resp, ResponseCode.OK);
		Assert.assertEquals(resp.jsonPath().get("tag[0]['tag-code']"), "paid");
	}

	@When("I do a GET call with ucid to NEJM Literatum System API to get the information about the user")
	public void i_do_a_get_call_with_ucid_to_nejm_literatum_system_api_to_get_the_information_about_the_user() {
		endPt = nejmLiteratumSystemGET.setEndpontIdentitiesUCID(ucid);
		resp = nejmLiteratumSystemGET.get(endPt)
				.extract().response();
	}

	@Then("I see the user got updated in NEJM Literatum with paid status")
	public void i_see_the_user_got_updated_in_nejm_literatum_with_paid_status() {

		tUtil.verifyStatus(resp, ResponseCode.OK);
		Assert.assertEquals(resp.jsonPath().get("tag[7]['tag-code']"), "activepaidsub");
	}

	@Title("POST Request to Create or update a person and license information at Literatum")
	@When("^User send a POST request to Create or update a person and license information at Literatum$")
	public void postRequestWithTheLiteratumAcssubscriptionWithAllMandatoryFields() {
		endPt = literatumCatalystProcPOSTSteps.setEndpointidentityAcsSubscription();
		resp = literatumCatalystProcPOSTSteps.post(fileNameMandatoryFields, endPt).extract().response();
	}

	@Title("Verify Status Code 201")
	@Then("^User should receive 201 Response code$")
	public void verifyCatLiteratumRespcode201() {
		tUtil.verifyStatus(resp, ResponseCode.CREATED);
	}

	@Title("POST Request to Create or update a person and license information"
			+ " at Literatum - missing mandatory attribute")
	@When("^User send a POST request to Create or update a person and license information"
			+ " at Literatum with not all mandatory attributes$")
	public void postRequestWithTheLiteratumAcssubscriptionMissingMandatoryField() {
		endPt = literatumCatalystProcPOSTSteps.setEndpointidentityAcsSubscription();
		resp = literatumCatalystProcPOSTSteps.post(fileNameMissingMandatoryFields, endPt).extract().response();
	}

	@Title("POST Request to Create or update a person and license information at Literatum with incorrect ucid")
	@When("^User send a POST request to Create or update"
			+ " a person and license information at Literatum with incorrect ucid$")
	public void postRequestWithTheLiteratumAcssubscriptionWithAllMandatoryFieldsIncorrectUcid() {
		endPt = literatumCatalystProcPOSTSteps.setEndpointidentityAcsSubscription();
		resp = literatumCatalystProcPOSTSteps.post(fileNameMissingMandatoryFieldsIncorrectUcid, endPt).extract()
				.response();
	}

	@Title("Verify Status Code 400")
	@Then("^User should receive 400 Response code$")
	public void verifycatliteratumrespcode400() {
		tUtil.verifyStatus(resp, ResponseCode.BAD_REQUEST);
	}

	@Title("Verify Status Code 404")
	@Then("^User should receive 404 Response code$")
	public void verifyCatLiteratumRespcode404() {
		tUtil.verifyStatus(resp, ResponseCode.NOT_FOUND);
	}

	@When("user calls Literatum to check for tags and license")
	public void userCallsLiteratumTagsLicense() throws InterruptedException {

		ucid = jsonUtils.getFromJSON(file_name, "ucid");
		kmap.put("ucid", jsonUtils.getFromJSON(file_name, "ucid"));
		kmap.put("email", jsonUtils.getFromJSON(file_name, "email"));
		kmap.put("firstname", jsonUtils.getFromJSON(file_name, "firstName"));
		kmap.put("lastname", jsonUtils.getFromJSON(file_name, "lastName"));
		LOGGER.info("Waiting for async request to get finished");
		TimeUnit.SECONDS.sleep(3);

		endPt = literatumGETSteps.setEndpointIdentity(ucid);
		literRespIdentity = literatumGETSteps.get(endPt)
				.assertThat().statusCode(ResponseCode.OK)
				.extract().response();

		endPt = literatumGETSteps.setEndpointLicenses(ucid);
		literRespLicense = literatumGETSteps.get(endPt)
				.assertThat().statusCode(ResponseCode.OK)
				.extract().response();

	}

	@Then("tag and license info should be present in Literatum for the respective user")
	public void licenseAndTagInformationShouldExistInLiteratumSystem() {
		literatumGETSteps.Verify_license_and_tag_information(kmap, literRespIdentity );

	}

	@Then("User send a request to identity person")
	public void userSendARequestToIndentityPerson() {
		ucid = jsonUtils.getFromJSON(fileNameMandatoryFields, "ucid");
		endPt = literatumCatalystProcPOSTSteps.setEndpointIndentityPerson();
		resp = literatumCatalystProcPOSTSteps.postPerson(endPt, ucid)
				.extract().response();
	}

	@When("^I do POST call to indetity paymybill$")
	public void whenIDoPOSTCallToIndetityPaymybill() {
		String fileName = "LiteratumProcess/Literatum_POST_paymybill.json";
		String sessionUcid = (String) tUtil.getFromSession("ucid");
		endPt = literatumCatalystProcPOSTSteps
				.setEndpointIndentityPayMyBill(sessionUcid);
		resp = literatumCatalystProcPOSTSteps.post(fileName, endPt)
				.extract().response();
		tUtil.putToSession("response", resp);
	}

	@When("I do POST call to indetity paymybill and sku is miss")
	public void iDoPOSTCallToIndetityPaymybillAndSkuIsMiss() throws Exception {
		String fileName = "LiteratumProcess/Literatum_POST_paymybill.json";
		String sessionUcid = (String) tUtil.getFromSession("ucid");
		jsonUtils.remove_JSONPath(fileName, "sku");

		endPt = literatumCatalystProcPOSTSteps.setEndpointIndentityPayMyBill(sessionUcid);
		resp = literatumCatalystProcPOSTSteps.post(fileName, endPt)
				.extract().response();

		jsonUtils.add_JSONPathJsonValue(fileName, "sku", "CAT-ANL-DFT");
	}

	@When("I do POST call to literatum licence and sku is miss")
	public void iDoPOSTCallToLiteratumLicenceAndSkuIsMiss() throws Exception {
		String fileName = "LiteratumProcess/Literatum_POST_licenses.json";
		String sessionUcid = (String) tUtil.getFromSession("ucid");
		jsonUtils.remove_JSONPath(fileName, "$.licenses[0].sku");
		jsonUtils.update_JSONValue(fileName, "$.ucid", sessionUcid);

		endPt = literatumCatalystProcPOSTSteps.setEndpointLicenses();
		resp = literatumCatalystProcPOSTSteps.post(fileName, endPt)
				.extract().response();

		jsonUtils.add_JSONPathJsonValue(fileName, "$.licenses[0].sku", "CAT-ANL-DFT");
	}

	@When("I do POST call to literatum licence and access period is miss")
	public void iDoPOSTCallToLiteratumLicenceAndAccessPeriodIsMiss() throws Exception {
		String fileName = "LiteratumProcess/Literatum_POST_licenses.json";
		String sessionUcid = (String) tUtil.getFromSession("ucid");
		jsonUtils.remove_JSONPath(fileName, "$.licenses[0].accessPeriod");
		jsonUtils.update_JSONValue(fileName, "$.ucid", sessionUcid);

		endPt = literatumCatalystProcPOSTSteps.setEndpointLicenses();
		resp = literatumCatalystProcPOSTSteps.post(fileName, endPt)
				.extract().response();

		jsonUtils.add_JSONPathJsonValue(fileName, "$.licenses[0].accessPeriod", "M-0001");
	}

	@When("I do POST call to literatum licence")
	public void iDoPOSTCallToLiteratumLicence() throws Exception {
		String fileName = "LiteratumProcess/Literatum_POST_licenses.json";
		String sessionUcid = (String) tUtil.getFromSession("ucid");
		jsonUtils.update_JSONValue(fileName, "$.ucid", sessionUcid);

		endPt = literatumCatalystProcPOSTSteps.setEndpointLicenses();
		resp = literatumCatalystProcPOSTSteps.post(fileName, endPt)
				.extract().response();

		tUtil.putToSession("response", resp);
	}

	@When("I do POST call to literatum licence with accessPeriod")
	public void iDoPOSTCallToLiteratumLicenceWithAccessPeriod(final DataTable dataTable) throws Exception {
		String fileName = "LiteratumProcess/Literatum_POST_licenses.json";
		Map<String, String> row = CucumberUtils.convert(dataTable);
		String sessionUcid = (String) tUtil.getFromSession("ucid");
		jsonUtils.update_JSONValue(fileName, "$.ucid", sessionUcid);
		jsonUtils.update_JSONValue(fileName, "$.licenses[0].accessPeriod", row.get("accessPeriod"));

		endPt = literatumCatalystProcPOSTSteps.setEndpointLicenses();
		resp = literatumCatalystProcPOSTSteps.post(fileName, endPt)
				.extract().response();

		tUtil.putToSession("response", resp);
	}

	@When("User send a POST request to literatum identity person")
	public void userSendAPOSTRequestToLiteratumIdentityPerson() throws Exception {
		String fileName = "LiteratumProcess/Literatum_POST_person.json";
		String sessionUcid = (String) tUtil.getFromSession("ucid");
		jsonUtils.update_JSONValue(fileName, "$.ucid", sessionUcid);

		endPt = literatumCatalystProcPOSTSteps.setEndpointIndentityPerson();
		resp = literatumCatalystProcPOSTSteps.post(fileName, endPt)
				.extract().response();
		tUtil.putToSession("response", resp);
	}

	@When("^User send a POST request to System AIC with timestamp email (.*) password (.*) firstname (.*) lastName (.*) suffix (.*) and roleCode (.*)$")
	public void userSendAPOSTRequestToSystemAIC(String email, String password, String firstName, String lastName, String suffix, String roleCode) throws Exception {
		endPt = AICPOSTSteps.setEndPoint();
		emailValue = tUtil.AppendTimestamp(email);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.email", emailValue);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.password", password);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.firstName", firstName);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.lastName", lastName);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.suffix", suffix);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.roleCode", roleCode);

		sysResp = AICPOSTSteps.createCustomerInAkamai("AIC_Customer.json", endPt)
			.extract().response();

		ucid = AICGETSteps.getUUID(sysResp);
	}

	@Then("^User should get user uuid in response$")
	public void userShouldGetUserUuidInResponse() {
		ucid = AICGETSteps.getUUID(sysResp);
		assertNotNull(ucid);
	}

	@When("^User send a POST request to create a person identity in Literatum with ucid$")
	public void userSendAPOSTRequestToSystemAICToCreateAPersonIdentityInLiteratumWithUcid() throws Exception {
		String file_name = "LiteratumProc_Identiry.json";
		jsonUtils.update_JSONValue(file_name, "ucid", ucid);

		endPt = literatumProcessPOST.setEndpointIdentityPerson();

		procResp = literatumProcessPOST.postWithHeader(file_name, endPt, "catalyst")
			.extract().response();
	}

	@Then("^Process Literatum identity should be created$")
	public void processLiteratumIdentityShouldBeCreatedWithStatusCode() {
		tUtil.verifyStatus(procResp, ResponseCode.CREATED);
	}

	@When("^User send a POST request to assign license in Literatum$")
	public void userSendAPOSTRequestToAssignLicenseInLiteratum() throws Exception {
		String file_name = "LiteratumProc_Identiry_License.json";
		jsonUtils.update_JSONValue(file_name, "ucid", ucid);

		endPt = literatumProcessPOST.setEndpointIdentityPerson();

		procResp = literatumProcessPOST.post(file_name, endPt)
			.extract().response();
	}

	@Then("^Process Literatum license should be assigned$")
	public void processLiteratumLicenseShouldBeAssigned() {
		tUtil.verifyStatus(procResp, 200);
	}

	@When("^User send a POST request to create a person identity in Literatum with invalid ucid$")
	public void userSendAPOSTRequestToCreateAPersonIdentityInLiteratumWithInvalidUcid() throws Exception {
		String file_name = "LiteratumProc_Identiry.json";
		jsonUtils.update_JSONValue(file_name, "ucid", tUtil.generateRandomUcid(30));

		endPt = literatumProcessPOST.setEndpointIdentityPerson();

		procResp = literatumProcessPOST.post(file_name, endPt)
			.extract().response();
	}

	@Then("^Literatum response should contain appropriate status (.*)$")
	public void literatumResponseShouldContainAppropriateStatusSts(int sts) {
		tUtil.verifyStatus(procResp, sts);
	}

	@When("^I send a GET request to System Literatum identity$")
	public void iSendAGETRequestToSystemLiteratumIdentity() {
		endPt = literatumGETSteps.setEndpointIdentity(ucid);

		sysResp = literatumGETSteps.get(endPt)
			.extract().response();
	}

	@Then("^catalyst-role tag should be set for user with appropriate value$")
	public void catalystRoleTagShouldBeSetForUserWithAppropriateValue() {
		String actualRole = jsonUtils.getFromJSON("AIC_Customer.json", "$.roleCode");
		JsonPath jsonpathEvaluator = sysResp.jsonPath();
		List<String> TagCode = jsonpathEvaluator.getList("tag.tag-code");
		assertTrue(TagCode.contains(actualRole));
	}

	@When("^User send a POST request to assign license to created person identity$")
	public void userSendAPOSTRequestToAssignLicenseToCreatedPersonIdentity() throws Exception {
		String uniqCode = tUtil.AppendTimestamp("code");
		jsonUtils.update_JSONValue(filePostLicense, "code", uniqCode);
		jsonUtils.update_JSONValue(filePostLicense, "ucid", ucid);

		endPt = literatumPOSTSteps.setEndpointLicenses();

		sysResp = literatumPOSTSteps.post(filePostLicense, endPt)
			.extract().response();
	}

	@Then("^User send a POST request to assign license to created person identity with same payload$")
	public void userSendAPOSTRequestToAssignLicenseToCreatedPersonIdentityWithSamePayload() throws Exception {
		endPt = literatumPOSTSteps.setEndpointLicenses();

		sysResp = literatumPOSTSteps.post(filePostLicense, endPt)
			.extract().response();
	}

	@Then("^System Literatum response should contain appropriate status (.*)$")
	public void systemLiteratumResponseShouldContainAppropriateStatusSts(int sts) {
		tUtil.verifyStatus(sysResp, sts);
	}

	@Then("^catalyst-audience-type tag should be set for user with appropriate value$")
	public void catalystAudienceTypeTagShouldBeSetForUserWithAppropriateValue() {
		endPt = AICGETSteps.setEndpointUserID(ucid);
		Response resp = AICGETSteps.getUserFromAkamai(endPt)
			.extract().response();
		JsonPath jsonPath = resp.jsonPath();
		String actualType = jsonPath.getString("audienceType");

		JsonPath jsonpathEvaluator = sysResp.jsonPath();
		List<String> TagCode = jsonpathEvaluator.getList("tag.tag-code");
		assertTrue(TagCode.contains(
			actualType
				.replace(" ", "-")
				.toLowerCase()
			)
		);
	}
}
