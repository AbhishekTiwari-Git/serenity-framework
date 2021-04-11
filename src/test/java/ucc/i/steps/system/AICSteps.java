package ucc.i.steps.system;

import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.ant.taskdefs.condition.IsFalse;
import org.jruby.RubyProcess.Sys;
import org.json.simple.JSONObject;
import org.junit.Assert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.i.method.aic.AICGET;
import ucc.i.method.aic.AICPATCH;
import ucc.i.method.aic.AICPOST;
import ucc.i.method.aic.AICPUT;
import ucc.i.steps.experience.AccountExpSteps;
import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;


public class AICSteps {
	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
	private static String autoEmail =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("autoEmail");
	public static Response resp = null;
	public static Map<String,String> kmap = new HashMap<String,String>();
	public static JSONObject requestParams = new JSONObject();
	Boolean flag = false;
	static String Id;
	int numOfAlternateId;
	static String end_pt = null;
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	List<String> get_Keys=new ArrayList<String>();
	List<String> get_Vals=new ArrayList<String>();
	List<String> key = new ArrayList<String>();
	List<String> value = new ArrayList<String>();
	static String uuid;
	static String token;
	static String email_value;
	static String firstName;
	static String lastName;
	
	
	
	@Steps
	AICPATCH AICPATCHSteps;
	
	@Steps
	AICPOST AICPOSTSteps;
	
	@Steps
	AICGET AICGETSteps;
	
	@Steps
	AICPUT AICPUTSteps;
	
	@Steps
	AccountExpSteps ACCExpSteps;
	
	@Steps
	CommonFunc commonFunc;
	
	
	
	String filePutAlternateID = "AIC_Put_AlternateID.json";
	String filePostNewCustomer = "AIC_Customer.json";
	String filePutActivatePassword = "AIC_PUT_ActivatePassword.json";
	String createUserWithNewlyAddedFields = "AIC_SYS/createUserWithNewlyAddedFields.json";
	String createUserWithOnlyEmail = "AIC_SYS/createUserWithOnlyEmail.json";
	String updateUserWithNewlyAddedFields = "AIC_SYS/updateUserWithNewlyAddedFields.json";
	String leadUser = "AIC_SYS/leadUser.json";
	String subscriberUser = "AIC_SYS/subscriberUser.json";
	String file_name = "AIC_PATCH.json";
	String AIC_put_users_uuid_events = "AIC_PUT_users_uuid_events.json";
	String AIC_PUT_regToEvent_withNoUUID = "AIC_PUT_regToEvent_withNoUUID.json";
	String AIC_PUT_withNoUUID = "AIC_PUT_withNoUUID.json";
	String patch_users_ucid = "AIC_SYS/patch_users_ucid.json";
	String createUser = "AIC_SYS/createUser.json";

	
	
    @Then("I see the all tags in the response and there is no duplicate of searches")
    public void i_see_the_all_tags_in_the_response_and_there_is_no_duplicate_of_searches() {

    	Assert.assertEquals((String) tUtil.getFromSession("name"), resp.jsonPath().get("searches[0].name"));
		Assert.assertEquals((String) tUtil.getFromSession("description"), resp.jsonPath().get("searches[0].description"));
		Assert.assertEquals((String) tUtil.getFromSession("tags1"), resp.jsonPath().get("searches[0].tags[0]"));
		Assert.assertEquals((String) tUtil.getFromSession("tags2"), resp.jsonPath().get("searches[0].tags[1]"));
    }
    
	@Then("I see the all tags in the response with empty tags")
	public void i_see_the_all_tags_in_the_response_with_empty_tags() {

		Assert.assertEquals((String) tUtil.getFromSession("name"), resp.jsonPath().get("searches[0].name"));
		Assert.assertNull(resp.jsonPath().get("searches[0].description"));
		Assert.assertEquals((String) tUtil.getFromSession("tags1"), resp.jsonPath().get("searches[0].tags[0]"));
	}
	
	@Then("I see the all tags in the response")
	public void i_see_the_all_tags_in_the_response() {

		Assert.assertEquals((String) tUtil.getFromSession("name"), resp.jsonPath().get("searches[0].name"));
		Assert.assertEquals((String) tUtil.getFromSession("description"), resp.jsonPath().get("searches[0].description"));
		Assert.assertEquals((String) tUtil.getFromSession("tags1"), resp.jsonPath().get("searches[0].tags[0]"));
		Assert.assertEquals((String) tUtil.getFromSession("tags2"), resp.jsonPath().get("searches[0].tags[1]"));
	}
	
	@When("I do GET call to aic for  saved_items searches for the user")
	public void i_do_get_call_to_aic_for_saved_items_searches_for_the_user() {

		end_pt = AICGETSteps.setEndPointToSavedSearches((String)tUtil.getFromSession("ucid"));
		resp = AICGETSteps.get(end_pt)
				.extract().response();
		
		tUtil.putToSession("response", resp);
	}
	
	@Then("I see the the ucid, email, fname, lname are correct")
	public void i_see_the_the_ucid_email_fname_lname_are_correct() {

		Assert.assertEquals((String) tUtil.getFromSession("ucid"), resp.jsonPath().get("uuid"));
		Assert.assertEquals((String) tUtil.getFromSession("email"), resp.jsonPath().get("email"));
		Assert.assertEquals((String) tUtil.getFromSession("firstName"), resp.jsonPath().get("firstName"));
		Assert.assertEquals((String) tUtil.getFromSession("lastName"), resp.jsonPath().get("lastName"));
	}
	
	@Then("I see user has alternateID for ACS")
	public void i_see_user_has_alternate_id_for_acs() {

		Assert.assertTrue(resp.jsonPath().get("alternateID").toString().contains("ACS"));
	}
	
	@Then("I see NEJM.subscriptionStatus is subscriber")
	public void i_see_nejm_subscription_status_is_subscriber() {

		Assert.assertEquals("SUBSCRIBER", resp.jsonPath().get("Nejm.subscriptionStatus"));
	}
	
	@Then("I see user has mediaAccess and institutionalAdmin field in the response")
	public void i_see_user_has_media_access_and_institutional_admin_field_in_the_response() {

		tUtil.responseContainsTheString(resp, "mediaAccess");
		tUtil.responseContainsTheString(resp, "institutionalAdmin");
		
//		Assert.assertNotNull(resp.jsonPath().get("Nejm.mediaAccess"));
//		Assert.assertNotNull(resp.jsonPath().getString("Nejm.institutionalAdmin"));
	}
	
	@When("I do POST to AIC for the user")
	public void i_do_post_to_aic_for_the_user() 
			throws Exception {
    	
    	jsonUtils.update_JSONValue(createUser, "email", (String) tUtil.getFromSession("email"));
    	jsonUtils.update_JSONValue(createUser, "firstName", (String) tUtil.getFromSession("firstName"));
    	jsonUtils.update_JSONValue(createUser, "lastName", (String) tUtil.getFromSession("lastName"));
    	
    	end_pt = AICPOSTSteps.setEndPoint();
    	resp = AICPOSTSteps.createCustomerInAkamai(createUser, end_pt)
    			.extract().response();
    	
    	tUtil.putToSession("response", resp);
	}
	
	@Then("^I see the uuid in the response$")
	public void i_see_the_uuid_in_response() {
		
		if(resp.getStatusCode()!=ResponseCode.CREATED){
			email_value = (String) tUtil.getFromSession("email");
			Get_request_with_valid_email();
			tUtil.verifyStatus(resp, ResponseCode.OK);
		}
		
		uuid = AICPOSTSteps.getUUID(resp);
		tUtil.putToSession("ucid", uuid);
	}
	
	@When("I do GET to AIC for change_identities from invalidDate")
	public void i_do_get_to_aic_for_change_identities_from_invalid_date() {
		end_pt = AICGETSteps.setEndpointFrom(tUtil.generateRandomUcid(10));
		resp = AICGETSteps.get(end_pt)
				.extract().response();
		
		tUtil.putToSession("response", resp);
	}
	
	@When("I do GET to AIC for change_identities from yesterday")
	public void i_do_get_to_aic_for_change_identities_from_yesterday() {
		end_pt = AICGETSteps.setEndpointFrom(tUtil.getYesterdayDateString());
		resp = AICGETSteps.get(end_pt)
				.extract().response();
		
		tUtil.putToSession("response", resp);
	}

	@Then("I see mediaAccess and institutionalAdmin  fields in the response for users")
	public void i_see_media_access_field_in_the_response_for_users() {
	    Assert.assertTrue(resp.jsonPath().get().toString().contains("mediaAccess"));
	    Assert.assertTrue(resp.jsonPath().get().toString().contains("institutionalAdmin"));
	}
	
	@When("I do PATCH call through AIC for the user using ucid")  //QAUTO-4267 - PATCH for registered user via AIC
	public void i_do_patch_call_through_aic_for_the_user_using_ucid() 
			throws Exception {
		
		email_value = tUtil.AppendTimestamp(autoEmail);
		firstName = tUtil.AppendTimestamp("firstName");
		lastName = tUtil.AppendTimestamp("lastName");
		
		jsonUtils.update_JSONValue(patch_users_ucid, "ucid", uuid);
		jsonUtils.update_JSONValue(patch_users_ucid, "email", email_value);
		jsonUtils.update_JSONValue(patch_users_ucid, "firstName", firstName);
		jsonUtils.update_JSONValue(patch_users_ucid, "lastName", lastName);
		
		end_pt = AICPATCHSteps.setEndpointUserID(uuid);
		resp = AICPATCHSteps.updateUser(patch_users_ucid, end_pt)
				.extract().response();
		
		tUtil.putToSession("response", resp);
	}
	
	@When("I do PATCH call to aic sys api using invalid ucid of the user") 
	public void i_do_patch_call_to_aic_sys_api_using_invalid_ucid_of_the_user() 
			throws URISyntaxException {
		// will generate error, that's why we didn't update the json file

		uuid = tUtil.generateRandomUcid(36);
		end_pt = AICPATCHSteps.setEndpointUserID(uuid);
		resp = AICPATCHSteps.updateUser(patch_users_ucid, end_pt)
				.extract().response();
		
		tUtil.putToSession("response", resp);
	}
	
	@Then("I see nejmInstitutionalAdmin nejmMediaAccess fields got updated")
	public void i_see_nejm_institutional_admin_nejm_media_access_fields_got_updated() {

		resp = (Response)tUtil.getFromSession("response");
		
		Assert.assertEquals("REGULAR", resp.jsonPath().getString("Nejm.mediaAccess"));
    	Assert.assertFalse(resp.jsonPath().getBoolean("Nejm.institutionalAdmin"));
	}
	
	@Then("I see fname lname email got updated")
	public void i_see_fname_lname_email_got_updated() {
	    
		Assert.assertEquals(uuid, resp.jsonPath().get("uuid"));
		Assert.assertEquals(email_value, resp.jsonPath().get("email"));
		Assert.assertEquals(firstName, resp.jsonPath().get("firstName"));
		Assert.assertEquals(lastName, resp.jsonPath().get("lastName"));
	}
	
	
	@When("user sends a GET request with uuid to get the updated user")
	public void user_sends_a_get_request_with_uuid_to_get_the_updated_user() {
		
		end_pt = AICGETSteps.setEndpointUserID(uuid);
		resp = AICGETSteps.getUserFromAkamai(end_pt)
				.extract().response();
		
		tUtil.putToSession("response", resp);
	}
	
	
	@When("I do PATCH call to users_ucid to update user's fname lname and email")
	public void i_do_patch_call_to_users_ucid_to_update_user_s_fname_lname_and_email() throws Exception {
		
		email_value = tUtil.AppendTimestamp(autoEmail);
		firstName = tUtil.AppendTimestamp("firstName");
		lastName = tUtil.AppendTimestamp("lastName");
		
		jsonUtils.updateJSONFileValues(updateUserWithNewlyAddedFields, email_value, firstName, lastName);
		
		end_pt = AICPATCHSteps.setEndpointUserID(uuid);
		resp = AICPATCHSteps.updateUser(updateUserWithNewlyAddedFields, end_pt)
			   .extract().response();
	}
	
	
	@When("I do POST call to AIC Sys API users endpoint to create Registered User with only email")
	public void i_do_post_call_to_aic_sys_api_users_endpoint_to_create_a_user_with_only_email() throws Exception {
	    
		email_value = tUtil.AppendTimestamp(autoEmail);
		jsonUtils.update_JSONValue(createUserWithOnlyEmail, "$.email", email_value);
		
		end_pt = AICPOSTSteps.setEndPoint();
	    resp = AICPOSTSteps.createCustomerInAkamai(createUserWithOnlyEmail, end_pt)
	    		.extract().response();
	    
	    uuid = resp.jsonPath().get("uuid");
	    
	    tUtil.putToSession("response", resp);
	}
	
	
	@When("I do POST call to AIC Sys API users endpoint to create LEAD User")
	public void i_do_post_call_to_aic_sys_api_users_endpoint_to_create_LEAD_user() throws Exception {
		
		email_value = tUtil.AppendTimestamp(autoEmail);
		firstName = tUtil.AppendTimestamp("firstName");
		lastName = tUtil.AppendTimestamp("lastName");

		jsonUtils.updateJSONFileValues(leadUser, email_value, firstName, lastName);
		
		end_pt = AICPOSTSteps.setEndPoint();
	    resp = AICPOSTSteps.createCustomerInAkamai(leadUser, end_pt)
	    		.extract().response();
	    
	    uuid = resp.jsonPath().get("uuid");
	    
	    kmap.put("email", email_value);
	    kmap.put("firstName", firstName);
	    kmap.put("lastName", lastName);
	    kmap.put("ucid", uuid);
	    
	    tUtil.putToSession("response", resp);
	}
	
	
	@When("I do POST call to AIC Sys API users endpoint to create SUBSCRIBER")
	public void i_do_post_call_to_aic_sys_api_users_endpoint_to_create_Subscriber() throws Exception {
		
		email_value = tUtil.AppendTimestamp(autoEmail);
		firstName = tUtil.AppendTimestamp("firstName");
		lastName = tUtil.AppendTimestamp("lastName");
		
		jsonUtils.updateJSONFileValues(subscriberUser, email_value, firstName, lastName);

		end_pt = AICPOSTSteps.setEndPoint();
	    resp = AICPOSTSteps.createCustomerInAkamai(subscriberUser, end_pt)
	    		.extract().response();
	    
	    uuid = resp.jsonPath().get("uuid");
	    
	    kmap.put("email", email_value);
	    kmap.put("firstName", firstName);
	    kmap.put("lastName", lastName);
	    kmap.put("ucid", uuid);
	    
	    tUtil.putToSession("response", resp);
	}
	
	
	@When("I do POST call to AIC Sys API users endpoint to create Registered User")
	public void i_do_post_call_to_aic_sys_api_users_endpoint_to_create_Registered_User() throws Exception {
		
		email_value = tUtil.AppendTimestamp(autoEmail);
		firstName = tUtil.AppendTimestamp("firstName");
		lastName = tUtil.AppendTimestamp("lastName");
		
		jsonUtils.updateJSONFileValues(createUserWithNewlyAddedFields, email_value, firstName, lastName);
		
		end_pt = AICPOSTSteps.setEndPoint();
	    resp = AICPOSTSteps.createCustomerInAkamai(createUserWithNewlyAddedFields, end_pt)
	    		.extract().response();
	    
	    uuid = resp.jsonPath().get("uuid");
	    
	    kmap.put("email", email_value);
	    kmap.put("firstName", firstName);
	    kmap.put("lastName", lastName);
	    kmap.put("ucid", uuid);
	    
	    tUtil.putToSession("response", resp);
	}
	
	
	@Then("I see SUBSCRIBER's email and uuid in response")
	public void i_see_SUBSCRIBER_user_email_and_uuid_in_response() {
		
		Assert.assertEquals("REGISTERED USER", resp.jsonPath().get("audienceType"));
		Assert.assertEquals("SUBSCRIBER", resp.jsonPath().get("Catalyst.subscriptionStatus"));
		
		Assert.assertEquals(uuid, resp.jsonPath().get("uuid"));
		Assert.assertEquals(email_value, resp.jsonPath().get("email"));
		Assert.assertEquals(firstName, resp.jsonPath().get("firstName"));
		Assert.assertEquals(lastName, resp.jsonPath().get("lastName"));
	}
	
	
	@Then("I see LEAD user's email and uuid in response")
	public void i_see_LEAD_user_email_and_uuid_in_response() {
		
		Assert.assertEquals("LEAD", resp.jsonPath().get("audienceType"));
		Assert.assertEquals("NONE", resp.jsonPath().get("Catalyst.subscriptionStatus"));
		
		Assert.assertEquals(uuid, resp.jsonPath().get("uuid"));
		Assert.assertEquals(email_value, resp.jsonPath().get("email"));
		Assert.assertEquals(firstName, resp.jsonPath().get("firstName"));
		Assert.assertEquals(lastName, resp.jsonPath().get("lastName"));
	}

	
	@Then("I see Registered User's fname lname email and uuid in response")
	public void i_see_newly_created_user_s_fname_lname_email_and_uuid_in_response() {
		
		Assert.assertEquals("REGISTERED USER", resp.jsonPath().get("audienceType"));
		Assert.assertEquals("NONE", resp.jsonPath().get("Catalyst.subscriptionStatus"));
		
		Assert.assertEquals(uuid, resp.jsonPath().get("uuid"));
		Assert.assertEquals(email_value, resp.jsonPath().get("email"));
		Assert.assertEquals(firstName, resp.jsonPath().get("firstName"));
		Assert.assertEquals(lastName, resp.jsonPath().get("lastName"));
	}
	
	
	@Then("I see Registered User's email and uuid in response")
	public void i_see_newly_created_user__email_and_uuid_in_response() {
		
		Assert.assertEquals("REGISTERED USER", resp.jsonPath().get("audienceType"));
		
		Assert.assertEquals(uuid, resp.jsonPath().get("uuid"));
		Assert.assertEquals(email_value, resp.jsonPath().get("email"));
	}

	
	@When("^I send a PATCH request to update data of user from Akamai with following uuid$")
	public void patch_update_User() throws Exception {
		
		end_pt = AICPATCHSteps.setEndpointUserID(uuid);
		
		email_value = tUtil.AppendTimestamp(autoEmail);
		jsonUtils.update_JSONValue("AIC_Update_PATCH.json", "$.email", email_value);
		
		resp = AICPATCHSteps.updateUser("AIC_Update_PATCH.json",end_pt)
			   .extract().response();
	}
	
	
	@When("^User sends a PATCH request to update data with following uuid and invalid data for multiple fields$")
	public void patch_update_User_invalid_data() throws Exception {
		
		end_pt = AICPATCHSteps.setEndpointUserID(uuid);

		resp = AICPATCHSteps.updateUser("AIC_Patch_invalid.json",end_pt)
			   .extract().response();
		
	}

	
	@When("^User sends a PATCH request update data from Akamai with empty body and following uuid$")
	public void patch_update_User_with_empty_body() throws Exception {
		
		end_pt = AICPATCHSteps.setEndpointUserID(uuid);
		resp = AICPATCHSteps.PatchRequestWithEmptyBody(end_pt)
			   .extract().response();
		
	}
	
	
	@Title("Verify Status Code and message for invalid Data") 
	@Then("^user should get respective status (.*) and message (.*)$")
	public void verifyUserInvalidTypeSts(int sts, String msg) {
		
		AICPATCHSteps.verify_ICVmsg(resp, msg, sts);
		
	}
	
	
	@Title("Verify Status Code and message for invalid Data") 
	@Then("^user should get respective status (.*) and combined message (.*) for all invalid fields$")
	public void verifyUserInvalidTypeStsForMultipleFields(int sts, String msg) {
		
		AICPATCHSteps.verify_ICVmsg(resp, msg, sts);
		
	}
	
	
	@When("^User sends a PATCH request to update data with following uuid and invalid fieldValue (.*) for field (.*)$")
	public void PATCH_request_to_update_data_with_invalid_fieldValue_for_field(String fieldValue, String field) throws URISyntaxException{

		end_pt = AICPATCHSteps.setEndpointUserID(uuid);

		resp = AICPATCHSteps.updateUser(field, fieldValue, end_pt)
			   .extract().response();
	}
	

	@Then("^the data for user should be updated with 204 status$")
	public void user_data_should_be_updated() {
		
		tUtil.verifyStatus(resp, 204);
	}
	
	
	@Title("Verify Status Code")
	@Then("^User should be created with (.*) status code$")
	public void user_should_be_created_with_status_code(int sts) {
		tUtil.verifyStatus(resp, sts);
	}
	
	
	
	@Title("Verify Status Code") 
	@Then("^the request should return '204' status$")
	public void verifyStatus() {
		tUtil.verifyStatus(resp, 204);
	}
	
	
	@When("^User send a POST request to AIC with timestamp email (.*) password (.*) firstname (.*) lastName (.*) suffix (.*) and roleCode (.*)$")
	public void POST_request_to_create_customer_valid_data(String email, String password, String firstName, String lastName, String suffix, String roleCode) throws Exception {
		
		end_pt = AICPOSTSteps.setEndPoint();
		email_value = tUtil.AppendTimestamp(email);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.email", email_value);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.password", password);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.firstName", firstName);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.lastName", lastName);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.suffix", suffix);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.roleCode", roleCode);
		
		resp = AICPOSTSteps.createCustomerInAkamai("AIC_Customer.json", end_pt)
				.extract().response();
		
		
		String[] Keys = { "email", "passowrd", "firstName","lastName", "suffix", "roleCode" };
		String[] values = {email_value, password, firstName, lastName, suffix, roleCode};
		key = Arrays.asList( Keys );
		value = Arrays.asList( values );
		
		kmap = tUtil.store_jsonValues(key, value);

		uuid = AICGETSteps.getUUID(resp);
		requestParams.put("uuid", uuid);
		
		tUtil.putToSession("response", resp);
	}


	@Then("^User should get uuid in response$")
	public void user_should_get_uuid_in_response() {
	    uuid = AICPOSTSteps.getUUID(resp);
	}

	
	@Then("^User information returned via GET call using uuid should match with email (.*) password (.*) firstname (.*) lastName (.*) suffix (.*) and roleCode (.*)$")
	public void user_information_returned_via_GET_call_using_uuid_should_match(String email, String password, String firstname, String lastname, String suffix, String roleCode) {
		
		end_pt = AICGETSteps.setEndpointUserID(uuid);
		resp = AICGETSteps.getUserFromAkamai(end_pt)
				.extract().response();
		
	    AICPOSTSteps.verifyCustomerData(kmap, resp);
	    
	}
	
	
	@When("^User sends a POST request to create customer with timestamp email (.*)$")
	public void POST_request_to_create_customer_with_email(String email) throws Exception {
		
		end_pt = AICPOSTSteps.setEndPoint();
		email_value = tUtil.AppendTimestamp(email);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.email", email_value);
		
		resp = AICPOSTSteps.createCustomerInAkamai("AIC_Customer.json", end_pt)
				.extract().response();
		
	}

	
	@Then("^User Sends a Post request to create customer again with same email (.*)$")
	public void Post_request_to_create_customer_with_used_email(String email) throws Exception {

		end_pt = AICPOSTSteps.setEndPoint();
		
		resp = AICPOSTSteps.createCustomerInAkamai("AIC_Customer.json", end_pt)
				.extract().response();
		
	}
	
	
	@When("^User sends a POST request to create customer with invalid data for any of the email (.*) firstname (.*) lastName (.*) suffix (.*) and roleCode (.*)$")
	public void POST_request_to_create_customer_with_invalid_data(String email, String firstname, String lastname, String suffix, String roleCode) throws Exception {
	    
		end_pt = AICPOSTSteps.setEndPoint();
		System.out.println(email+  firstname+ lastname+ suffix+ roleCode);
		
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.email", email);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.firstName", firstname);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.lastName", lastname);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.suffix", suffix);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.roleCode", roleCode);
		
		resp = AICPOSTSteps.createCustomerInAkamai("AIC_Customer.json", end_pt)
				.extract().response();
	}
	
	@When("^user sends a GET call with newly created uuid$")
	public void GET_call_using_uuid_should_match() {
		
		end_pt = AICGETSteps.setEndpointUserID(uuid);
		resp = AICGETSteps.getUserFromAkamai(end_pt)
				.extract().response();
		
		tUtil.putToSession("response", resp);
	}
	
	@Then("^GET call with uuid should match with email (.*) password (.*) firstname (.*) lastName (.*) suffix (.*) and roleCode (.*)$")
	public void using_uuid_should_match(String email, String password, String firstname, String lastname, String suffix, String roleCode) {
		
		end_pt = AICGETSteps.setEndpointUserID(uuid);
		resp = AICGETSteps.getUserFromAkamai(end_pt)
				.extract().response();
		
	    AICPOSTSteps.verifyCustomerData(kmap, resp);
	    
	}
	
	@When("^user sends a GET call with invalid uuid (.*)$")
	public void uuid_should_match(String unique_id) {
		
		end_pt = AICGETSteps.setEndpointUserID(unique_id);
		resp = AICGETSteps.getUserFromAkamai(end_pt)
				.extract().response();
		
	}
	
	
	@Then("^user receives appropriate msg (.*) and sts (.*)$")
	public void invalidTypeStsForMultipleFields(String msg, int sts) {
		
		AICPATCHSteps.verify_ICVmsg(resp, msg, sts);
		
	}
	
	@When("^User send a PUT request to register user event with uuid of newly created user and following data$")
	public void PUT_request_to_register_user_event_with_valid_uuid() throws URISyntaxException {

	    end_pt = AICPUTSteps.setEndPointEvents(uuid);
	    resp = AICPUTSteps.put_withJSON(AIC_put_users_uuid_events, end_pt)
	    		.extract().response();
	}
	
	@When("^User send a PUT request to register user event with invalid uuid$")
	public void PUT_request_to_register_user_event_with_invalid_uuid() throws URISyntaxException {
		
		uuid = tUtil.AppendTimestamp(tUtil.generateRandomUcid(36));
		
	    end_pt = AICPUTSteps.setEndPointEvents(uuid);
	    resp = AICPUTSteps.put_withJSON(AIC_PUT_regToEvent_withNoUUID, end_pt)
	    		.extract().response();
	}
	
	
	@Given("^user generates a token from Akamai$")
	public void generateToken() {
		
		uuid = AICPOSTSteps.getUUID(resp);
		resp = AICPOSTSteps.createToken(uuid)
				.extract().response();
		
		token = AICPOSTSteps.getToken(resp);
	}
	
	@When("^user sends a GET request to generate access token against uuid in AIC$")
    public void user_sends_a_get_request_to_generate_access_token_against_uuid_in_aic() throws Throwable {
		end_pt = AICGETSteps.setEndpointAccessToken(uuid);
		resp = AICGETSteps.get(end_pt).extract().response();
    }
	
	@When("^user sends a GET request to generate access token with invalid uuid (.*)$")
    public void user_sends_a_get_request_to_generate_access_token_with_invalid_uuid(String uuid) throws Throwable {
		end_pt = AICGETSteps.setEndpointAccessToken(uuid);
		resp = AICGETSteps.get(end_pt).extract().response();
    }
	
	
	@When("^user sends a POST call to authenticate with token and uuid$")
	public void authenticate() {
		
		end_pt = AICPOSTSteps.setTokenEndPoint();
		resp = AICPOSTSteps.authenticate(uuid, token, end_pt)
				.extract().response();
		
	}
	
	@Then("^accessToken should be returned with 200 status$")
    public void accesstoken_should_be_returned_with_200_status() throws Throwable {
        tUtil.verifyStatus(resp, 200);
        
        token = resp.jsonPath().getString("accessToken");
    }
	
	@Then("^different accessToken should be returned with 200 status$")
    public void different_accesstoken_should_be_returned_with_200_status() throws Throwable {
		tUtil.verifyStatus(resp, 200);
		
		String newToken = resp.jsonPath().getString("accessToken");
		Assert.assertNotEquals(token, newToken);
		
    }
	
	@Then("^for Authentication User should get 200 response with valid (.*) status (.*)$")
	public void tokenValidation(String msg, int sts) {
		
		AICPATCHSteps.verify_ICVmsg(resp, msg, sts);
		
	}
	
	@Then("^the response should be 200 with token being valid$")
    public void the_response_should_be_200_with_token_being_valid() throws Throwable {
        tUtil.verifyStatus(resp, 200);
        
        Assert.assertTrue(resp.jsonPath().getBoolean("valid"));
    }
	
	@When("^user sends a POST call to authenticate with invalid token (.*) and uuid (.*)$")
	public void authenticate_invalidData(String token, String uuid) {
		
		
		end_pt = AICPOSTSteps.setTokenEndPoint();
		resp = AICPOSTSteps.authenticate(uuid, token, end_pt)
				.extract().response();
		
	}
	
	@When("^User send a PUT request to register user event with no uuid and following data$")
	public void PUT_request_to_register_user_event_with_no_uuid() throws URISyntaxException {
		
		uuid = null;
		
	    end_pt = AICPUTSteps.setEndPointEvents(uuid);
	    resp = AICPUTSteps.put_withJSON(AIC_PUT_withNoUUID, end_pt)
	    		.extract().response();
	}
	
	@Title("Verify Status Code and message for invalid Data") 
	@Then("^User should get following status and message$")
	public void verifyUserInvalidTypeSts(DataTable table) {
		
		String msg = null;
		int sts = 0;
		
		List<List<String>> values = table.asLists();
		msg = values.get(1).get(1);
		sts= Integer.parseInt(values.get(1).get(0));
		
		AICPOSTSteps.verify_AICmsg(resp, msg, sts);
		
	}
	
	@When("^User send a PUT request to register user event with uuid of newly created user eventID (.*) name (.*) and registrationDate (.*)$")
	public void PUT_request_to_register_user_event_with_eventID_name_and_registrationDate(String eventID, String name, String regDate) {
	    
		end_pt = AICPUTSteps.setEndPointEvents(uuid);
	    resp = AICPUTSteps.registrUserEvent(end_pt, eventID, name, regDate)
	    		.extract().response();
	    
	    String[] Keys = { "eventID", "name", "registrationDate"};
		String[] values = {eventID, name, regDate};
		
		key = Arrays.asList( Keys );
		value = Arrays.asList( values );
		
		kmap = tUtil.store_jsonValues(key, value);
		
	}
	

	@Then("^updated information returned via GET call for newly created user should match to respective eventID (.*) name (.*) and registrationDate (.*)$")
	public void updated_information_returned_via_GET_call_should_match_to_respective_eventID_testid_name_testEvent_and_registrationDate(String eventID, String name, String registrationDate) throws ParseException {
	    
		end_pt = AICGETSteps.setEndpointUserID(uuid);
		
		Response resp = AICGETSteps.getUserFromAkamai(end_pt).
				extract().response();
		
		AICPUTSteps.veriyRegisterEventInfo(kmap, resp);
		
	}	
	
	@When("^User sends a PUT request to register user event for newly created user with same details for eventID (.*) name (.*) and registrationDate (.*)$")
	public void PUT_request_to_register_user_event_with_same_details(String eventID, String name, String regDate) {
	    
		end_pt = AICPUTSteps.setEndPointEvents(uuid);
	    resp = AICPUTSteps.registrUserEvent(end_pt, eventID, name, regDate)
	    		.extract().response();
	    
	}

	@Then("^event should be registered again with '204' status$")
	public void event_should_be_registered_again_with_status() {

		tUtil.verifyStatus(resp, 204);
		
	}
	
	@When("^User send a PUT request to register user event with uuid of newly created user and data only for fieldname1 (.*) fieldvalue1 (.*) and fieldname2 (.*) and fieldvaue2 (.*)$")
	public void user_send_a_PUT_request_to_register_user_event_without_all_required_fields(String fieldname1, String fieldvalue1, String fieldname2, String fieldvalue2) {
		
		end_pt = AICPUTSteps.setEndPointEvents(uuid);
		resp = AICPUTSteps.registerUserEventWithoutRequiredFields(end_pt, fieldname1, fieldvalue1, fieldname2, fieldvalue2)
			.extract().response();
		
	}
	
	@When("^user sends a GET call to AIC to get details of user with invalid email (.*)$")
	public void Get_request_with_invalid_email(String email) {
		
		end_pt = AICGETSteps.setEndPointEmail(email);
		resp = AICGETSteps.getUserFromAkamai(end_pt)
				.extract().response();
		
	}
	
	@When("^user sends a GET request to get details of newly created user with email$")
	public void Get_request_with_valid_email() {
		
		end_pt = AICGETSteps.setEndPointEmail(email_value);
		resp = AICGETSteps.getUserFromAkamai(end_pt)
				.extract().response();
		
	}

	@When("^User send a GET request to pull changes from akamai for date from (.*) to date (.*)$")
	public void user_send_a_GET_request_to_pull_changes_from_akamai_for_date_from_to_date(String from, String to) {
		requestParams.put("from", from);
		requestParams.put("to", to);
		resp = AICGETSteps.getChanges(from, to)
				.extract().response();
	}
	
	@Title("Verify Status Code") 
	@Then("^the request should return 200 status$")
	public void verifyResponseStatus() {
		tUtil.verifyStatus(resp);
	}
	
	@When("^User send a POST request to AIC with timestamp email (.*) password (.*) firstname (.*) lastName (.*) suffix (.*) roleCode (.*) and audienceType (.*)$")
	public void POST_request_to_create_customer_valid_data_and_audienceType(String email, String password, String firstName, String lastName, String suffix, String roleCode, String audienceType) throws Exception {
		
		end_pt = AICPOSTSteps.setEndPoint();
		email_value = tUtil.AppendTimestamp(email);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.email", email_value);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.password", password);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.firstName", firstName);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.lastName", lastName);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.suffix", suffix);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.roleCode", roleCode);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.audienceType", audienceType);
		
		resp = AICPOSTSteps.createCustomerInAkamai("AIC_Customer.json", end_pt)
				.extract().response();
		
		get_Keys.add("audienceType");
		get_Vals.add(audienceType);
		System.out.println(audienceType);

	}
	
	@Then("^User information returned via GET call using email should match with uuid and audienceType (.*)$")
	public void user_information_returned_via_GET_call_using_uuid_should_match(String audienceType) {
		
		end_pt = AICGETSteps.setEndpointUserID(uuid);
		resp = AICGETSteps.getUserFromAkamai(end_pt)
				.extract().response();
		
		get_Keys.add("uuid");
		get_Vals.add(uuid);
		get_Keys.add("audienceType");
		get_Vals.add(audienceType);
		kmap = tUtil.store_jsonValues(get_Keys, get_Vals);
		
	    AICGETSteps.verifyUcidAndAudienceType(kmap, resp);
	    
	}

	@Then("^response should contains the newly created used which falls in the date range$")
	public void response_should_contains_the_newly_created_used_which_falls_in_the_date_range() {
		String email = jsonUtils.getFromJSON("AIC_Customer.json", "email");

		List<Map<String, String>> changes = resp.jsonPath().get();
		List list_changes = changes.stream()
				.filter((Map<String, String> m) -> m.get("email").equals(email))
				.collect(Collectors.toList());

		Assert.assertEquals(list_changes.size(), 1);
	}

	@Then("^User should get the list of changed identities in response$")
	public void user_should_get_the_list_of_changed_identities_in_response() {

		List<Map<String, String>> changes = resp.jsonPath().get();
		Assert.assertTrue(changes instanceof List);
	}


	@Then("^updated date for all user should be between the range provided for query$")
	public void updated_date_for_all_user_should_be_between_the_range_provided_for_query() {

		List<Map<String, String>> changes = resp.jsonPath().get();
		for (Map<String, String> change : changes) {
			String pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS +0000";
			DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);

			LocalDate date_updated = LocalDate.parse(change.get("dateUpdated"), format);
			LocalDate from_date = LocalDate.parse((CharSequence) requestParams.get("from"));
			LocalDate to_date = LocalDate.parse((CharSequence) requestParams.get("to"));

			Assert.assertTrue(date_updated.isAfter(from_date) && date_updated.isBefore(to_date));
		}
	}


	@When("^User sends a PATCH request to update data with following uuid (.*) and invalid fieldValue (.*) for field (.*)$")
	public void PATCH_request_to_update_data_with_valid_fieldValue_for_field(String uuid, String fieldValue, String field) throws URISyntaxException{

		end_pt = AICPATCHSteps.setEndpointUserID(uuid);
		resp = AICPATCHSteps.updateUser(field, fieldValue, end_pt)
				.extract().response();
	}


	@When("^User send a GET request to pull changes from Akamai fromDate to currentDate$")
	public void user_send_a_GET_request_to_pull_changes_from_Akamai_from_date_to_tomorrow() {
		
		String from = commonFunc.getCustomDateTime("yyyy-MM-dd'T'HH:mm:ss", "yesterday");
		String to = commonFunc.getCustomDateTime("yyyy-MM-dd'T'HH:mm:ss", "to");
		resp = AICGETSteps.getChanges(from, to)
				.extract().response();
	}
	
	@When("^User send a GET request to pull changes from akamai from yesterday$")
	public void user_send_a_GET_request_to_pull_changes_from_Akamai_from_yesterday() {

		String yesterday = commonFunc.getCustomDateTime("yyyy-MM-dd'T'HH:mm:ss","yesterday");
		resp = AICGETSteps.getChanges(yesterday, null)
				.extract().response();
	}
	
	@When("^User send a GET request to pull changes from akamai for createdDate and lastUpdated is the same$")
	public void user_send_a_GET_request_to_pull_changes_from_Akamai_from_sameDates() {

		String today = commonFunc.getCustomDateTime("yyyy-MM-dd'T'HH:mm:ss","to");
		resp = AICGETSteps.getChanges(today, today)
				.extract().response();
	}
	
	@When("^User send a GET request to pull changes from akamai for toDate falls under fromDate$")
	public void user_send_a_GET_request_to_pull_changes_from_Akamai_for_toDate_fallsUnder_fromDate() {

		String today = commonFunc.getCustomDateTime("yyyy-MM-dd'T'HH:mm:ss","to");
		String yesteday  = commonFunc.getCustomDateTime("yyyy-MM-dd'T'HH:mm:ss","yesterday");
		resp = AICGETSteps.getChanges(today, yesteday)
				.extract().response();
	}

	@When("^User should get the list of changed identities in response and the user with uuid should be present in the list and sizeShouldBe zero$")
	public void user_should_get_the_list_of_changed_identities_in_response_and_the_user_with_uuid_present_in_the_list_sizeShouldBeZero() {

		uuid = (String) requestParams.get("uuid");

		List<Map<String, String>> changes = resp.jsonPath().get();
		List list_changes = changes.stream()
				.filter((Map<String, String> m) -> m.get("uuid").equals(uuid))
				.collect(Collectors.toList());

		Assert.assertEquals(list_changes.size(), 0);
	}
	
	@When("^User should get the list of changed identities in response and the user with uuid should be present in the list$")
	public void user_should_get_the_list_of_changed_identities_in_response_and_the_user_with_uuid_present_in_the_list() {

		uuid = (String) requestParams.get("uuid");

		List<Map<String, String>> changes = resp.jsonPath().get();
		List list_changes = changes.stream()
				.filter((Map<String, String> m) -> m.get("uuid").equals(uuid))
				.collect(Collectors.toList());

		Assert.assertEquals(list_changes.size(), 1);
	}


	@And("^User createdDate and lastUpdated date are same$")
	public void user_createdDate_and_lastUpdated_date_are_same() {

		String createdDate = AICPOSTSteps.getDateCreated(resp);
		String updatedDate = AICPOSTSteps.getDateUpdated(resp);

		Assert.assertEquals(createdDate, updatedDate);
	}

	@And("^User should get the list of changed identities in response and the user uuid should be present in the list$")
	public void user_should_get_list_changed_identities_in_response_and_user_should_be_present_in_list() {
		uuid = (String) requestParams.get("uuid");

		List<Map<String, String>> changes = resp.jsonPath().get();
		List list_changes = changes.stream()
				.filter((Map<String, String> m) -> m.get("uuid").equals(uuid))
				.collect(Collectors.toList());

		Assert.assertEquals(list_changes.size(), 1);
	}

	@When("^User send a GET request to pull changes from akamai from date (.*)$")
	public void user_send_a_GET_request_to_pull_changes_from_akamai_from_date(String from) {
		resp = AICGETSteps.getChanges(from, null).extract().response();
	}

	@Given("^Created new user with unique UUID$")
	public void created_new_user_with_unique_uuid() throws Exception {

		end_pt = AICPOSTSteps.setEndPoint();
		email_value = tUtil.AppendTimestamp("automation@example.com");
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.email", email_value);

		resp = AICPOSTSteps.createCustomerInAkamai("AIC_Customer.json", end_pt)
				.extract().response();

		uuid = AICGETSteps.getUUID(resp);
		requestParams.put("uuid", uuid);
		kmap.put("email", email_value);
	}

	@When("^User send a GET request get details of all fields$")
	public void userSendAGETRequestGetDetailsOfAllFields() {
		resp = AICGETSteps.getFields(null)
				.extract().response();
	}

	@When("^Field details should be returned in response$")
	public void fieldDetailsShouldBeReturnedInResponse() {

		Map<String, List> map = resp.jsonPath().get();
		List<Map<String , String>> fields = map.get("items");
		Assert.assertTrue(fields.size() > 0);
		fields.forEach(m -> {
			Assert.assertFalse(m.get("name").isEmpty());
			Assert.assertFalse(m.values().isEmpty());
		});
	}

	@When("^User send a GET request with fieldname (.*)$")
	public void userSendAGETRequestWithValidFieldname(String field) {
		resp = AICGETSteps.getFields(field)
				.extract().response();
	}

	@When("^Field should be present in response$")
	public void fieldShouldBePresentInResponse() {
		Map<String, List> map = resp.jsonPath().get();
		List field = map.get("items");

		Assert.assertFalse(field.isEmpty());
	}
	
// =================================================================================================================
    
    @Given("^user with email (.*) exists$")
    public void user_with_email_exists(String email) throws Throwable {
        end_pt = AICPOSTSteps.setEndPoint();
        
        jsonUtils.modify_JSONAppendTimestampfunction(filePostNewCustomer, "email", email);
        // jsonUtils.update_JSONValue(filePostNewCustomer, "$.email", tUtil.AppendTimestamp(email));
        resp = AICPOSTSteps.createCustomerInAkamai(filePostNewCustomer, end_pt)
        		.extract().response();
        
        AICSteps.uuid = resp.jsonPath().getString("uuid");
    }
	
    @When("^user sends a AIC PUT request to add alternateID to user with IDType (.*) and IDValue (.*)$")
    public void user_sends_a_aic_put_request_to_add_alternateid_to_user_with_idtype_and_idvalue(String idtype, String idvalue) throws Throwable {
        end_pt = AICPUTSteps.setEndpointAlternateID(AICSteps.uuid);
        
        jsonUtils.update_JSONValue(filePutAlternateID, "$.IDType", idtype);
        jsonUtils.update_JSONValue(filePutAlternateID, "$.IDValue", idvalue);
        
        resp = AICPUTSteps.putAlternateID(filePutAlternateID, end_pt).extract().response();
    }
    
    @When("^user sends a AIC PUT request with the same IDType (.*) and IDValue (.*)$")
    public void user_sends_a_aic_put_request_with_the_same_idtype_and_idvalue(String idtype, String idvalue) throws Throwable {
        user_sends_a_aic_put_request_to_add_alternateid_to_user_with_idtype_and_idvalue(idtype, idvalue);
    }
    
    @When("^user sends a AIC PUT request with the same IDType (.*) but diff IDValue (.*)$")
    public void user_sends_a_aic_put_request_with_the_same_idtype_but_diff_idvalue(String idtype, String idvaluediff) throws Throwable {
        user_sends_a_aic_put_request_to_add_alternateid_to_user_with_idtype_and_idvalue(idtype, tUtil.AppendTimestamp(idvaluediff));
    }
    
    @When("^user sends a GET call with uuid (.*)$")
    public void user_sends_a_get_call_with_uuid(String uuid) throws Throwable {
    	end_pt = AICGETSteps.setEndpointUserID(uuid);
		resp = AICGETSteps.getUserFromAkamai(end_pt)
				.extract().response();
    }
    
    @When("^user sends a GET call with created uuid$")
    public void user_sends_a_get_call_with_created_uuid() throws Throwable {
    	end_pt = AICGETSteps.setEndpointUserID(AICSteps.uuid);
		resp = AICGETSteps.getUserFromAkamai(end_pt)
				.extract().response();
    }

    
    @Then("^the PUT request should return status code (.*)$")
    public void the_put_request_should_return_status_code(int stcodeput) throws Throwable {
        tUtil.verifyStatus(resp, stcodeput);
    }
    
    @Then("^AIC user should be created with (\\d+) status code$")
    public void aic_user_should_be_created_with_status_code(int stcodepost) throws Throwable {
        tUtil.verifyStatus(resp, stcodepost);
    }
    
    @Then("^the request should return status code (.*) with error message (.*)$")
    public void the_request_should_return_status_code_with_error_message(int stcodeput, String msg) throws Throwable {
        tUtil.verify_msgCode(resp, msg, stcodeput);
    }
    
    @Then("^newly added alternateID should be present in user info$")
    public void newly_added_alternateid_should_be_present_in_user_info() throws Throwable {
        JsonPath jsonpathevaluator = resp.jsonPath();
        
        List<String> loIdType = jsonpathevaluator.getList("alternateID.IDType");
        List<String> loIdValue = jsonpathevaluator.getList("alternateID.IDValue");
        
        String expectedIdValue = jsonUtils.getFromJSON(filePutAlternateID, "$.IDValue");
        String expectedIdType = jsonUtils.getFromJSON(filePutAlternateID, "$.IDType");
        
        Assert.assertEquals(loIdType.size(), loIdValue.size());
        numOfAlternateId = loIdType.size();
        
        boolean found = false;
        
        for (int i = 0; i < loIdValue.size(); i++) {
        	// Found idvalue in the pair
        	if (loIdValue.get(i).equals(expectedIdValue)) {
        		if (loIdType.get(i).equals(expectedIdType)) {
        			// Found corresponding iftype of the pair
        			found = true;
        		}
        	}
        }
        
        Assert.assertTrue(found);
        
    }
    
    @Then("^the number of alternateIDs should be the same$")
    public void the_number_of_alternateids_should_be_the_same() throws Throwable {
        Assert.assertEquals(resp.jsonPath().getList("alternateID.IDType").size(), numOfAlternateId);
        newly_added_alternateid_should_be_present_in_user_info();
    }

	@When("^I do a PUT request to AIC to activatePassword$")
	public void iDoAPUTRequestToAICToActivatePassword() throws Exception {
		jsonUtils.update_JSONValue(filePutActivatePassword, "$.email", kmap.get("email"));
		end_pt = AICPUTSteps.setEndPointActivatePassword();
		resp = AICPUTSteps.put(filePutActivatePassword, end_pt)
				.extract().response();
	}

	@Then("^I see status as 200 with body as null$")
	public void iSeeStatusAsWithBodyAsNull() {
		tUtil.verifyStatus(resp);
		Assert.assertEquals("", resp.body().print());
	}

	@When("^I do a PUT request to activatePassword with incorrect email$")
	public void iDoAPUTRequestToActivatePasswordWithIncorrectEmail() throws Exception {
		email_value = jsonUtils.getFromJSON(filePutActivatePassword, "$.email");
		jsonUtils.update_JSONValue(filePutActivatePassword, "$.email", "automationexample.com");
		end_pt = AICPUTSteps.setEndPointActivatePassword();
		resp = AICPUTSteps.put(filePutActivatePassword, end_pt)
				.extract().response();

		jsonUtils.update_JSONValue(filePutActivatePassword, "$.email", email_value);
	}

	@When("^I do a PUT request to activatePassword with empty email$")
	public void iDoAPUTRequestToActivatePasswordWithEmptyEmail() throws Exception {
		email_value = jsonUtils.getFromJSON(filePutActivatePassword, "$.email");
		jsonUtils.update_JSONValue(filePutActivatePassword, "$.email", "");
		end_pt = AICPUTSteps.setEndPointActivatePassword();
		resp = AICPUTSteps.put(filePutActivatePassword, end_pt)
				.extract().response();

		jsonUtils.update_JSONValue(filePutActivatePassword, "$.email", email_value);
	}

	@When("^I do a PUT request to activatePassword when locale is missing$")
	public void iDoAPUTRequestToActivatePasswordWhenLocaleIsMissing() throws Exception {
		jsonUtils.remove_JSONPath(filePutActivatePassword, "$.locale");
		end_pt = AICPUTSteps.setEndPointActivatePassword();
		resp = AICPUTSteps.put(filePutActivatePassword, end_pt)
				.extract().response();

		jsonUtils.add_JSONPathJsonValue(filePutActivatePassword, "$.locale", "en-US");
	}
	
	
	@When("I call sys API AIC based upon excel values")
	public void callSysAICChangeIdentities() {
		String from = null;
		String to = null;

		try {
			String filename = "ExcelFile.xlsx" ;
			FileInputStream inputStream = new FileInputStream(new File(filename));
			XSSFWorkbook wrkBook = new XSSFWorkbook(inputStream);
			XSSFSheet mySheet = wrkBook.getSheetAt(0);

			XSSFRow firstRow = mySheet.getRow(0);
			from = firstRow.getCell(2).getStringCellValue();

			int last = mySheet.getLastRowNum();
			XSSFRow lastRow = mySheet.getRow(last);
			to = lastRow.getCell(2).getStringCellValue();

			wrkBook.close();
			System.out.println("Successfully called APi based upon current Todate and from date from excel");
		} catch ( Exception ex ) {
			System.out.println(ex);
		}

		String endpt = AICGETSteps.setEndpointFromTo(from, to);
		resp = AICGETSteps.getChangeIdentities(from, to, endpt).extract().response();
		resp.jsonPath();
	}
	
	@Then("^identities should match with the data available in excel$")
	public void VerifySysAICChangeIdentities() {
		
		Set<Map<String, String>> oSet = new LinkedHashSet<>();
		oSet = ACCExpSteps.orderSet;
		AICGETSteps.verifyIdentities(oSet,resp);
		
	}
	
	@Then("Verify '400' status$")
	public void verify400() {
		tUtil.verifyStatus(resp, ResponseCode.BAD_REQUEST);
		
	}


	@When("I do PATCH call via AIC for the user using ucid")
	public void i_do_patch_call_via_aic_for_the_user_using_ucid() throws Exception {
		String patch_users_ucid = "AIC_SYS/patch_users_ucid.json";
		uuid = (String)tUtil.getFromSession("ucid");
//		email_value = tUtil.AppendTimestamp((String)tUtil.getFromSession("email"));
//		firstName = tUtil.AppendTimestamp((String)tUtil.getFromSession("firstName"));
//		lastName = tUtil.AppendTimestamp((String)tUtil.getFromSession("lastName"));

		email_value = (String)tUtil.getFromSession("email");
		firstName = (String)tUtil.getFromSession("firstName");
		lastName = (String)tUtil.getFromSession("lastName");
		jsonUtils.update_JSONValue(patch_users_ucid, "ucid", uuid);
		jsonUtils.update_JSONValue(patch_users_ucid, "email", email_value);
		jsonUtils.update_JSONValue(patch_users_ucid, "firstName", firstName);
		jsonUtils.update_JSONValue(patch_users_ucid, "lastName", lastName);
		end_pt = AICPATCHSteps.setEndpointUserID(uuid);
		resp = AICPATCHSteps.updateUser(patch_users_ucid, end_pt).extract().response();
		tUtil.putToSession("response", resp);
	}
}
	
