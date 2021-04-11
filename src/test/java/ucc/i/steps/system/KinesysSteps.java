package ucc.i.steps.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import ucc.i.method.accountexp.AccountExpGET;
import ucc.i.method.kinesys.KinesisHelper;
import ucc.i.method.kinesys.KinesysGET;
import ucc.i.method.kinesys.KinesysPATCH;
import ucc.i.method.kinesys.KinesysPOST;
import ucc.i.method.kinesys.KinesysPUT;
import ucc.utils.JsonUtils;
import ucc.utils.KinesysDataTable;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;

public class KinesysSteps {
	public static Response resp=null;
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	public static Map<String,String> kmap = new HashMap<String,String>();
	Boolean flag = false;
	static String Id;
	String file_name = "Kinesys_Post.json";
	static String end_pt = null;
	static String email = null;
	static String alternateId = null;
	static String createdCustomersEmail = null;
	static String patchedCustomersEmail = null;
	private final String KINESYS_PATCH_FILE = "Kinesys_Patch.json";
	static String ukv = null;
	static String uvv = null;
	
	@Steps
	KinesysGET kinesysGETSteps;
	
	@Steps
	KinesysPOST kinesysPOSTSteps;
	
	@Steps
	KinesysPUT kinesysPUTSteps;
	
	@Steps
	KinesysPATCH kinesysPATCHSteps;

	@Steps
	AccountExpGET AccExpGETSteps;
	
	@Steps
	KinesisHelper kinesisHelper;
	
	@Title("Get Request with Panelist ID : {0}")
	@When("^I send a GET request with the valid ID (.*) containing surveys$")
	public void i_send_a_GET_request_with_the_surveys(String arg1) throws URISyntaxException {
		end_pt=kinesysGETSteps.setEndpoint(arg1, "/surveys");
		resp = kinesysGETSteps.getSurveys(end_pt).extract().response();
		
	}
	
	@Title("Get Request with invalid ID surveys : {0}")
	@When("^I send a survey GET request with the invalid ID (.*)$")
	public void i_send_a_GET_request_with_invalid_surveys(String arg2) throws URISyntaxException {
		end_pt=kinesysGETSteps.setEndpoint(arg2, "/surveys");
		resp = kinesysGETSteps.getSurveys(end_pt).extract().response();
		
	}
	
	@Title("Get Request with Panelist ID : {0}")
	@When("^I send a GET request with the ID (.*)$")
	public void i_send_a_GET_request_with_the_ID(String arg1) throws URISyntaxException {
		end_pt=kinesysGETSteps.setEndpoint(arg1);
		resp = kinesysGETSteps.getPanelist(end_pt).extract().response();
		
	}
	
	@When("^I send a GET request to retrieve panelist info using the alternateId$")
    public void i_send_a_get_request_to_retrieve_panelist_info_using_the_alternateid() throws Throwable {
		end_pt=kinesysGETSteps.setEndpoint(Id);
		resp = kinesysGETSteps.getPanelist(end_pt).extract().response();
    }
	
	@Title("Verify Status Code") 
	@Then("^the request should return '200' status$")
	public void verifyStatus() {
		tUtil.verifyStatus(resp , ResponseCode.OK);
	}


	@Title("Checking that response is not null for alternate id, email and id")
	@Then("^Alternate ID and email should not be blank$")
	public void alternate_ID_and_email_should_not_be_blank() {
	    kmap = kinesysGETSteps.store_KinesysValues(resp);
	    flag = kinesysGETSteps.verify_KinsysNotNullValues(kmap);
	    assertTrue(flag);
	}
	
	
	@Title("Get Request with invalid Panelist ID : {0}")
	@When("^I send a GET request with the invalid ID (.*)$")
	public void i_send_a_GET_request_with_the_invalid_ID(String id) throws URISyntaxException {
		end_pt = kinesysGETSteps.setEndpoint(id);
		resp = kinesysGETSteps.getPanelist(end_pt)
				 .extract().response();
	}
	
	@Title("Verify Status Code") 
	@Then("^the request should return '404' status$")
	public void verifySt() {
		tUtil.verifyStatus(resp, ResponseCode.NOT_FOUND);
	}
	
	@Title("Verify Code") 
	@Then("^the request should return '400' status$")
	public void verifyS() {
		tUtil.verifyStatus(resp, ResponseCode.BAD_REQUEST);
	}
	
	@Then("^response should match with the ID (.*) msg (.*) and status (.*)$")
	public void response_should_match_with_the_message(String ID, String msg, int status) {
		kinesysGETSteps.verify_msg(end_pt, msg, status);
		    
	}
	
	@When("^I send a POST request to create a panelist$")
	public void post_panelist() throws Exception {
		jsonUtils.modify_JSONAppendTimestampfunction(file_name, "alternateId", "automation@example.com");
		jsonUtils.modify_JSONAppendTimestampfunction(file_name, "email", "automation@example.com");
		end_pt = kinesysPOSTSteps.setEndpoint();
		resp = kinesysPOSTSteps.createPanelist("Kinesys_Post.json",end_pt)
			   .extract().response();
		kmap = kinesysGETSteps.store_KinesysValues(resp);
		Id = kinesysPOSTSteps.getID(resp);
		
	}
	
	@When("^I send a POST request to create a panelist with alternateId (.*)$")
    public void i_send_a_post_request_to_create_a_panelist_with_alternateid(String alternateId) throws Throwable {
		jsonUtils.modify_JSONAppendTimestampfunction(file_name, "alternateId", alternateId);
		jsonUtils.modify_JSONAppendTimestampfunction(file_name, "email", "automation@example.com");
		end_pt = kinesysPOSTSteps.setEndpoint();
		resp = kinesysPOSTSteps.createPanelist("Kinesys_Post.json",end_pt)
			   .extract().response();
		kmap = kinesysGETSteps.store_KinesysValues(resp);
		Id = kinesysPOSTSteps.getID(resp);
    }
	
	@Then("^panelist should be created$")
	public void panelist_should_be_created() {
		assertEquals(resp.getStatusCode(), 201);
		
		// verify following fields are present in response
		String[] keys = {"alternateId", "email", "id"};
		List<String> loKeys = Arrays.asList(keys);
		
		for (String k : loKeys) {
			Assert.assertNotNull(resp.jsonPath().get(k));
		}
	}


	@Then("^Alternate ID and email returned from GET call should match with created panelist$")
	public void alternate_ID_and_email_returned_should_match_with_created_panelist() {
		end_pt=kinesysPOSTSteps.setEndpoint(Id);
		kinesysPOSTSteps.verify_Panelist(kmap, end_pt);
	}

	@Then("^id should not be blank$")
	public void id_should_not_be_blank() {
		kinesysGETSteps.verify_KinsysNotNullValues(kmap);
	}
	
	@Then("^returned response should have atleast one survey$")
	public void returned_response_should_have_atleast_one_survey() {
		tUtil.verifyStatus(resp, ResponseCode.OK);
		kinesysGETSteps.verify_Survey(resp);
	}

	
	@When("^I send a PUT request for newly created alternate Id and timestamped email with update key (.*) and update value (.*)$")
	public void put_panelist(String update_Key, String update_Value) throws Exception {
		end_pt = kinesysPUTSteps.setEndpoint();
		
		resp = kinesysPUTSteps.updatePanelist(kmap, "id", update_Key, update_Value, end_pt)
			   .assertThat().statusCode(204)
			   .extract().response();
		
		
	}
	
	@Then("^PUT call Status is 204 and GET call should return the panelist with updated email$")
	public void match_with_updated_panelist() {
		end_pt = kinesysPOSTSteps.setEndpoint(Id);
		kinesysPUTSteps.verify_UpdatedPanelist(end_pt);
	}


	@When("^I send a PUT request with invalid update data (.*) (.*) (.*)$")
	public void put_InvalidPanelist(String id, String email, String subscriber) throws Exception {
		end_pt = kinesysPUTSteps.setEndpoint();
		
		resp = kinesysPUTSteps.updatePanelist(id, email, subscriber, end_pt)
			   .extract().response();
				
	}

	
	@Then("^user should receive valid error message (.*) and status (.*)$")
	public void verify_msg(String msg, int code) {
		tUtil.verify_msgCode(resp, msg, code);
	}



	@Title("PUT Request with invalid endpoint type : {0}")
	@When("^I send a PUT request with endpoint (.*) for following data for Kinesys$")
	public void POST_request_with_the_invalid_type(String tpe, DataTable table) throws URISyntaxException {
		end_pt = kinesysPUTSteps.setEndpoint(tpe);
		String id = null;
		String email = null;
		String subscribed = null;
		
				 
		List<KinesysDataTable> values = new ArrayList<KinesysDataTable>();
		values = table.asList(KinesysDataTable.class);
			
		
		for (KinesysDataTable value : values) {
           		email =	value.email;
           		id = value.id;
           		subscribed = value.subscribed;
		}
		
		
		resp = kinesysPUTSteps.updatePanelist(id, email, subscribed, end_pt)
				 .extract().response();
		
	}


	@When("^User send a POST request to opt-in confirmation mail with no body$")
	public void userSendAPOSTRequestToOptInConfirmationMailWithNoBody() throws URISyntaxException {
		String json_body="{}";
		end_pt = kinesysPOSTSteps.setEndpointOptIn();
		resp = kinesysPOSTSteps.postOptIn(json_body, end_pt).extract().response();
	}

	@When("^User send a POST request to opt-in confirmation mail with invalid email (.*)$")
	public void userSendAPOSTRequestToOptInConfirmationMailWithInvalidData(String email) throws URISyntaxException {
		String json_body="{\"email\": \"" + email + "\"}";
		end_pt = kinesysPOSTSteps.setEndpointOptIn();
		resp = kinesysPOSTSteps.postOptIn(json_body, end_pt).extract().response();
	}

	@When("^User send a POST request to opt-in confirmation mail (.*) with invalid endpoint (.*)$")
	public void userSendAPOSTRequestToOptInConfirmationMailWithInvalidEndpoint(String email, String endpoint) throws URISyntaxException {
		String json_body="{\"email\": \"" + email + "\"}";
		end_pt = kinesysPOSTSteps.setEndpointOptIn();
		String invalid_endpoint = end_pt.concat(endpoint);
		resp = kinesysPOSTSteps.postOptIn(json_body, invalid_endpoint).extract().response();
	}
	
	@When("^I do a PATCH call to alternateId to update_Key (.*) and update_Value (.*)$")
	public void iDoApatchCallToUpdateTheAllDatapoints(String update_Key, String update_Value) throws Exception {
		
		alternateId = resp.jsonPath().get("alternateId");
		end_pt = kinesysPATCHSteps.setEndpoint(alternateId);
		
		kinesisHelper.patch_allFields(end_pt, update_Value, update_Key);
		
		ukv = kinesisHelper.helper_ukv;
		uvv = kinesisHelper.helper_uvv;
		kmap = kinesisHelper.helper_kmap;
		Id = kinesisHelper.helper_Id;
		
		tUtil.putToSession("response", kinesisHelper.helper_resp);
	}

	@Then("updated values should be returned")
	public void verifyUpdatedValues() {
		kinesysPATCHSteps.verifyUpdatedValues(resp, ukv, uvv);
	}

	@When("^I do a PATCH call with alternateID to update email (.*) only$")
	public void iDoApatchCallToCustomerToUpdateEmail(String email) throws Exception {
		createdCustomersEmail = resp.jsonPath().get("email");
		patchedCustomersEmail = "updated" + tUtil.AppendTimestamp(email);
		alternateId = resp.jsonPath().get("alternateId");
		end_pt = kinesysPATCHSteps.setEndpoint(alternateId);
		kinesysPATCHSteps.updatePanelistEmailOnly(patchedCustomersEmail, end_pt);
	}

	@When("^I do a PATCH call to alternateId update audienceSegment (.*), clinicalDesignation (.*), hsAffiliation (.*)$")
	public void iDoApatchCallToCustomerToUpdateSomeFields(Integer audienceSegment, Integer clinicalDesignation,
			Integer hsAffiliation) throws Exception {
		alternateId = resp.jsonPath().get("alternateId");
		end_pt = kinesysPATCHSteps.setEndpoint(alternateId);
		resp = kinesysPATCHSteps.updatePanelistSomeFields(audienceSegment, clinicalDesignation, hsAffiliation, end_pt)
				.extract().response();
	}

	@Then("^the request should return 400$")
	public void returnShouldbe400() {
		assertEquals(resp.getStatusCode(), ResponseCode.BAD_REQUEST);

	}

	@Then("^I see the panelist email got updated$")
	public void iSeeThePanelistEmailGotUpdated() {
		kinesysPATCHSteps.verify_updatedEmail(resp, createdCustomersEmail, patchedCustomersEmail);
	}

	@Then("^I see all datapoints got updated$")
	public void iSeeAllDatapointsGotUpdated() throws Exception {
		assertEquals(resp.getStatusCode(), ResponseCode.OK);
		kinesysPATCHSteps.verify_panelist_AllDataPoints(resp);
	}

	@Then("^I see audienceSegment (.*), clinicalDesignation (.*), hsAffiliation (.*) got updated$")
	public void iSeeSomeDatapointsGotUpdated(String audienceSegment, String clinicalDesignation, String hsAffiliation)
			throws URISyntaxException {
		assertEquals(resp.getStatusCode(), ResponseCode.OK);
		kinesysPATCHSteps.verify_panelist_SomeDataPoints(audienceSegment, clinicalDesignation, hsAffiliation, resp);
	}

	@Then("^request should return 201 status$")
	public void verifySt201() {
		assertEquals(resp.getStatusCode(), ResponseCode.CREATED);

		String[] keys = { "alternateId" };
		List<String> loKeys = Arrays.asList(keys);

		for (String k : loKeys) {
			Assert.assertNotNull(resp.jsonPath().get(k));
		}
	}

	@Then("^User should get respective code (.*) and message (.*)$")
	public void iSeeTheUsersAllFieldsGotUpdatedInKinesys(int code, String msg) {
		kinesysPATCHSteps.verify_PATCHmsg(resp, msg, code);
	}
	
	@Then("^the request should return '200' status with alternateId$")
	public void verifyStatuswithAlternateId() {
		tUtil.verifyStatus(resp, ResponseCode.OK);
		String[] keys = { "alternateId" };
		List<String> loKeys = Arrays.asList(keys);

		for (String k : loKeys) {
			Assert.assertNotNull(resp.jsonPath().get(k));
		}
	}
	
	@Then("^request should return 200 status$")
	public void verifySt200() {
		assertEquals(resp.getStatusCode(), ResponseCode.OK);

		String[] keys = { "alternateId" };
		List<String> loKeys = Arrays.asList(keys);

		for (String k : loKeys) {
			Assert.assertNotNull(resp.jsonPath().get(k));
		}
	}
}