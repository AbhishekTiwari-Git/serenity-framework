package ucc.i.steps.process;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import static org.junit.Assert.*;
import ucc.i.method.aic.AICGET;
import ucc.i.method.aic.AICPOST;
import ucc.i.method.customerprocess.CustomerProcessGET;
import ucc.i.method.literatum.LiteratumGET;
import ucc.i.method.registrationprocess.RegistrationProcessPOST;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

import java.util.HashMap;
import java.util.Map;

public class RegistrationProcSteps {
	public static Response procResp = null;
	public static Response sysResp = null;
	public static Response litResp = null;
	static String end_pt = null;
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	public static Map<String, String> kmap = new HashMap<String, String>();
	/*
	   * keys in kmap:
	   * email -- user's email
	   * eventId -- id of the event
	   */
	
	
	@Steps
	RegistrationProcessPOST registrationPOSTSteps;
	@Steps 
	CustomerProcessGET customerProcGETSteps;
	@Steps
	AICPOST AICPOSTSteps;
	@Steps
	AICGET AICGETSteps;
	@Steps
	LiteratumGET LiteratumGETSteps;

	
	// Files used
	String filePostFullRegisterForEvent = "RegistrationProc_POST_Full_Register.json";
	String filePostUcidRegisterForEvent = "RegistrationProc_POST_Ucid_Register.json";
	String filePostInvalidRegister = "RegistrationProc_POST_Full_InvalidRegister.json";
	String filePostRegisterJoinIC = "RegistrationProc_POST_Join_IC.json";
	String filePostRegisterRegIC = "RegistrationProc_POST_Reg_IC.json";
	String filePostRegisterUser = "RegistrationProc_POST_reg_user.json";
	String filePostRegisterUpdUser = "RegistrationProc_POST_update_user.json";
	String fileRegUserCatalyst = "RegProc_POST_reg_catalyst.json";
	String fileRegUserEVENT = "RegistrationProc_POST_event_reg.json";



	public void create_aic_new_user() throws Exception {
		end_pt = AICPOSTSteps.setEndPoint();
		String email_value = tUtil.AppendTimestamp("automation@example.com");
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.email", email_value);

		sysResp = AICPOSTSteps.createCustomerInAkamai("AIC_Customer.json", end_pt)
				.extract().response();

		String ucid = AICGETSteps.getUUID(sysResp);
		kmap.put("ucid", ucid);
	}

	// =================================================================================================================
	// Status code verification
	// =================================================================================================================
	
	@Then("^request should return status code (.*) with message (.*)$")
    public void request_should_return_status_code_with_message(int sts, String msg) throws Throwable {
        tUtil.verify_msgCode(procResp, msg, sts);
    }
	
	// =================================================================================================================
	// User Registration and Verification
	// =================================================================================================================

	@When("^user sends a POST request to register a user for existing event through Registration Process API$")
    public void user_sends_a_post_request_to_register_a_user_for_existing_event_through_registration_process_api() throws Throwable {
		// If the eventId is null, then we need to retrieve it from Event Process API Steps (assuming eventId is found 
		// through Event Process API
		if (!kmap.containsKey("eventId")) {
			kmap = EventProcSteps.kmap;
		}
		
		String tsEmail = tUtil.AppendTimestamp("automation@example.com");
		jsonUtils.update_JSONValue(filePostFullRegisterForEvent, "email", tsEmail);
		jsonUtils.update_JSONValue(filePostFullRegisterForEvent, "icvEventId", Integer.valueOf(kmap.get("eventId")));

		kmap.put("email", tsEmail);

	    end_pt = registrationPOSTSteps.setEndpointRegistration();
	    procResp = registrationPOSTSteps.registerUser(filePostFullRegisterForEvent, end_pt).extract().response();
    }
	
	@When("^user sends a POST request to register a user for event (.*) through Registration Process API$")
	public void user_sends_a_post_request_to_register_a_user_for_event_through_event_process_api(String eventid) throws Throwable {
		String tsEmail = tUtil.AppendTimestamp("automation@example.com");
		jsonUtils.update_JSONValue(filePostFullRegisterForEvent, "email", tsEmail);
		jsonUtils.update_JSONValue(filePostFullRegisterForEvent, "icvEventId", eventid);

		kmap.put("email", tsEmail);
		kmap.put("eventId", eventid);

	    end_pt = registrationPOSTSteps.setEndpointRegistration();
	    procResp = registrationPOSTSteps.registerUser(filePostFullRegisterForEvent, end_pt).extract().response();
	}
	
	@When("^user sends a POST request to register for event (.*) with ucid through Registration Process API$")
    public void user_sends_a_post_request_to_register_for_event_with_ucid_through_registration_process_api(String anothereventid) throws Throwable {
		// Getting user's ucid using email
		end_pt = customerProcGETSteps.setEndpointUserByEmail(kmap.get("email"));
		procResp = customerProcGETSteps.getUserInfo(end_pt).extract().response();
		kmap.put("ucid", procResp.jsonPath().getString("uuid"));
		
		jsonUtils.update_JSONValue(filePostUcidRegisterForEvent, "icvEventId", anothereventid);
		jsonUtils.update_JSONValue(filePostUcidRegisterForEvent, "ucId", kmap.get("ucid"));
		
		kmap.put("eventId", anothereventid);

	    end_pt = registrationPOSTSteps.setEndpointRegistration();
	    procResp = registrationPOSTSteps.registerUser(filePostUcidRegisterForEvent, end_pt).extract().response();
    }
	
	@When("^user sends a POST request to register for another event with ucid through Registration Process API$")
    public void user_sends_a_post_request_to_register_for_another_event_with_ucid_through_registration_process_api() throws Throwable {
		jsonUtils.update_JSONValue(filePostUcidRegisterForEvent, "icvEventId", Integer.valueOf(kmap.get("eventId")));
		jsonUtils.update_JSONValue(filePostUcidRegisterForEvent, "ucId", kmap.get("ucid"));

		end_pt = registrationPOSTSteps.setEndpointRegistration();
		procResp = registrationPOSTSteps.registerUser(filePostUcidRegisterForEvent, end_pt).extract().response();
    }
	
	@When("^user sends a POST request to register a user with same payload for event (.*) through Registration Process API$")
    public void user_sends_a_post_request_to_register_a_user_with_same_payload_for_event_through_registration_process_api(String anothereventid) throws Throwable {
		jsonUtils.update_JSONValue(filePostFullRegisterForEvent, "icvEventId", anothereventid);

	    end_pt = registrationPOSTSteps.setEndpointRegistration();
	    procResp = registrationPOSTSteps.registerUser(filePostFullRegisterForEvent, end_pt).extract().response();
    }
	
	@When("^user sends a POST request to register a user with same payload for another event through Registration Process API$")
    public void user_sends_a_post_request_to_register_a_user_with_same_payload_for_another_event_through_registration_process_api() throws Throwable {
		jsonUtils.update_JSONValue(filePostFullRegisterForEvent, "icvEventId", Integer.valueOf(kmap.get("eventId")));

	    end_pt = registrationPOSTSteps.setEndpointRegistration();
	    procResp = registrationPOSTSteps.registerUser(filePostFullRegisterForEvent, end_pt).extract().response();
    }
	
	@When("^user sends a POST request to register invalid user for event with data (.*) for field (.*) through Registration Process API$")
    public void user_sends_a_post_request_to_register_invalid_user_for_event_with_data_for_field_through_registration_process_api(String value, String field) throws Throwable {
        String savedValue = jsonUtils.getFromJSON(filePostInvalidRegister, field);
        
        jsonUtils.update_JSONValue(filePostInvalidRegister, field, value);
        
        end_pt = registrationPOSTSteps.setEndpointRegistration();
	    procResp = registrationPOSTSteps.registerUser(filePostInvalidRegister, end_pt).extract().response();
	    
	    jsonUtils.update_JSONValue(filePostInvalidRegister, field, savedValue);
    }
	
	@Then("^user registration for the event should be completed with status code 201$")
	public void user_registration_for_the_event_should_be_completed_with_status_code_201() throws Throwable {
		tUtil.verify_msgCode(procResp, "Registration completed successfully !!!", 201);
	}

	@When("^I do a POST call apply to Insights Council$")
	public void iDoAPOSTCallApplyToInsightsCouncil() throws Exception {
		String file_name = "RegistrationProc_POST_Reg_IC.json";
		String tsEmail = tUtil.AppendTimestamp("automation@example.com");
		jsonUtils.update_JSONValue(file_name, "email", tsEmail);

		end_pt = registrationPOSTSteps.setEndpointRegistrations();
		procResp = registrationPOSTSteps.registerUser(file_name, end_pt).extract().response();

		kmap.put("ucid", procResp.jsonPath().getString("ucid"));
	}

	@Then("^I see status as 201 created with ucid in message$")
	public void iSeeStatusAsCreatedWithUcidInMessage() {
		tUtil.verifyStatus(procResp, 201);
		assertNotNull(procResp.jsonPath().getString("ucid"));
		assertNotNull(procResp.jsonPath().getString("context"));
	}

	@When("^I do a POST call to Join Insights Council with no password$")
	public void iDoAPOSTCallToJoinInsightsCouncilWithNoPassword() throws Exception {
		String token = generateToken();
		jsonUtils.remove_JSONPath(filePostRegisterJoinIC, "$.password");
		jsonUtils.update_JSONValue(filePostRegisterJoinIC, "ucid", kmap.get("ucid"));

		end_pt = registrationPOSTSteps.setEndpointRegistrations();
		procResp = registrationPOSTSteps.registerUserToken(filePostRegisterJoinIC, end_pt, token)
				.extract().response();

		jsonUtils.add_JSONPathJsonValue(filePostRegisterJoinIC, "$.password", "password1234");
	}

	private String generateToken() {
		sysResp = AICPOSTSteps.createToken(kmap.get("ucid"))
				.extract().response();
		return AICPOSTSteps.getToken(sysResp);
	}

	@When("^I do a POST call to Join Insights Council with password$")
	public void iDoAPOSTCallToJoinInsightsCouncilWithPassword() throws Exception {
		String token = generateToken();
		jsonUtils.update_JSONValue(filePostRegisterJoinIC, "ucid", kmap.get("ucid"));
		end_pt = registrationPOSTSteps.setEndpointRegistrations();
		procResp = registrationPOSTSteps.registerUserToken(filePostRegisterJoinIC, end_pt, token)
				.extract().response();
	}


	@Then("^I see status as 400 code$")
	public void iSeeStatusAsCode() {
		tUtil.verifyStatus(procResp, 400);
	}

	@When("^I send a GET request to get Literatum identities$")
	public void iSendAGETRequestToGetLiteratumIdentities() {
		end_pt = LiteratumGETSteps.setEndpointIdentity(kmap.get("ucid"));

		litResp = LiteratumGETSteps.get(end_pt)
				.extract().response();
	}

	@Then("^I see status code 200$")
	public void iSeeStatusCode() {
		tUtil.verifyStatus(litResp, 200);
	}

	@When("^User sends a POST request to register user for catalyst with valid data$")
	public void userSendsAPOSTRequestToRegisterUserForCatalystWithValidData() throws Exception {

		String tsEmail = tUtil.AppendTimestamp("automation@example.com");
		String firstName = tUtil.AppendTimestamp("firstName");
		String lastName = tUtil.AppendTimestamp("lastName");
		jsonUtils.update_JSONValue(filePostRegisterUser, "email", tsEmail);
		jsonUtils.update_JSONValue(filePostRegisterUser, "firstName", firstName);
		jsonUtils.update_JSONValue(filePostRegisterUser, "lastName", lastName);

		end_pt = registrationPOSTSteps.setEndpointRegistrations();
		procResp = registrationPOSTSteps.registerUser(filePostRegisterUser, end_pt).extract().response();

		kmap.put("ucid", procResp.jsonPath().getString("ucid"));
	}

	@Then("^The request should return 201 status$")
	public void theRequestShouldReturnStatus() {
		tUtil.verifyStatus(procResp, 201);
	}

	@When("^I make a get request to get details of user$")
	public void iMakeAGetRequestToGetDetailsOfUser() {
		end_pt = AICGETSteps.setEndpointUserID(kmap.get("ucid"));
		sysResp = AICGETSteps.getUserFromAkamai(end_pt)
				.extract().response();
	}

	@Then("^audience-type should be set to LEAD$")
	public void audienceTypeShouldBeSetToLEAD() {
		JsonPath jsonPathEvaluator = sysResp.jsonPath();
		String audienceType = jsonPathEvaluator.getString("audienceType");
		assertEquals("LEAD", audienceType);
	}

	@When("^User sends a POST request to register user for catalyst with invalid data$")
	public void userSendsAPOSTRequestToRegisterUserForCatalystWithInvalidData() throws Exception {
		String tsEmail = tUtil.AppendTimestamp("automationexample.com");
		String firstName = tUtil.AppendTimestamp("firstName");
		String lastName = tUtil.AppendTimestamp("lastName");
		jsonUtils.update_JSONValue(filePostRegisterUser, "email", tsEmail);
		jsonUtils.update_JSONValue(filePostRegisterUser, "firstName", firstName);
		jsonUtils.update_JSONValue(filePostRegisterUser, "lastName", lastName);

		end_pt = registrationPOSTSteps.setEndpointRegistrations();
		procResp = registrationPOSTSteps.registerUser(filePostRegisterUser, end_pt).extract().response();

		kmap.put("ucid", procResp.jsonPath().getString("ucid"));
		jsonUtils.update_JSONValue(filePostRegisterUser, "email", "automation@example.com");
	}

	@Then("^the response should contain appropriate status <sts> and message <msg>$")
	public void theResponseShouldContainAppropriateStatusStsAndMessageMsg() {
	}

	@When("^User sends a POST request to update user in catalyst with valid data$")
	public void userSendsAPOSTRequestToUpdateUserInCatalystWithValidData() throws Exception {
		String password = tUtil.AppendTimestamp("password");
		jsonUtils.update_JSONValue(filePostRegisterUpdUser, "ucid", kmap.get("ucid"));
		jsonUtils.update_JSONValue(filePostRegisterUpdUser, "passwored", password);

		end_pt = registrationPOSTSteps.setEndpointRegistrations();
		procResp = registrationPOSTSteps.registerUser(filePostRegisterUpdUser, end_pt)
				.extract().response();
	}

	@When("^I do a POST call to Join Insights Council without token$")
	public void iDoAPOSTCallToJoinInsightsCouncilWithoutToken() throws Exception {
		String tsEmail = tUtil.AppendTimestamp("automation@example.com");
		jsonUtils.update_JSONValue(filePostRegisterRegIC, "email", tsEmail);
		jsonUtils.update_JSONValue(filePostRegisterRegIC, "ucid", kmap.get("ucid"));

		end_pt = registrationPOSTSteps.setEndpointRegistrations();
		procResp = registrationPOSTSteps.registerUser(filePostRegisterRegIC, end_pt)
				.extract().response();
	}

	@Then("^error message should be returned in response$")
	public void errorMessageShouldBeReturnedInResponse() {
		JsonPath jsonPathEvaluator = procResp.jsonPath();
		String message = jsonPathEvaluator.getString("message");
		assertEquals("Header akamai-auth-token is required when audience type of user is not LEAD", message);
	}

	@When("^I do a POST call to Join Insights Council with incorrect token$")
	public void iDoAPOSTCallToJoinInsightsCouncilWithIncorrectToken() throws Exception {
		String tsEmail = tUtil.AppendTimestamp("automation@example.com");
		jsonUtils.update_JSONValue(filePostRegisterRegIC, "email", tsEmail);
		jsonUtils.update_JSONValue(filePostRegisterJoinIC, "ucid", kmap.get("ucid"));

		end_pt = registrationPOSTSteps.setEndpointRegistrations();
		procResp = registrationPOSTSteps.registerUserToken(filePostRegisterRegIC, end_pt, "incorrect-token")
				.extract().response();
	}

	@Given("^User sends a POST request to register user for catalyst$")
	public void userSendsAPOSTRequestToRegisterUserForCatalyst() throws Exception {
//		create_aic_new_user();

		String tsEmail = tUtil.AppendTimestamp("automation@example.com");
		String firstName = tUtil.AppendTimestamp("firstName");
		String lastName = tUtil.AppendTimestamp("lastName");
		jsonUtils.update_JSONValue(filePostRegisterUser, "email", tsEmail);
		jsonUtils.update_JSONValue(filePostRegisterUser, "firstName", firstName);
		jsonUtils.update_JSONValue(filePostRegisterUser, "lastName", lastName);
//		jsonUtils.update_JSONValue(filePostRegisterUser, "ucid", kmap.get("ucid"));

		end_pt = registrationPOSTSteps.setEndpointRegistrations();
		procResp = registrationPOSTSteps.registerUser(filePostRegisterUser, end_pt).extract().response();

		kmap.put("ucid", procResp.jsonPath().getString("ucid"));
	}

	@Then("^invalid akamai-auth-token message for user should be returned in response$")
	public void invalidAkamaiAuthTokenMessageForUserShouldBeReturnedInResponse() {
		String message = procResp.jsonPath().getString("message");
		assertEquals(message, "Invalid akamai-auth-token for user " + kmap.get("ucid"));
	}

	@When("^I do a POST call to Join Insights Council with token$")
	public void iDoAPOSTCallToJoinInsightsCouncilWithToken() throws Exception {
		String tsEmail = tUtil.AppendTimestamp("automation@example.com");
		jsonUtils.update_JSONValue(filePostRegisterJoinIC, "email", tsEmail);
		jsonUtils.update_JSONValue(filePostRegisterJoinIC, "$.ucid", kmap.get("ucid"));
		String token = generateToken();

		end_pt = registrationPOSTSteps.setEndpointRegistrations();
		procResp = registrationPOSTSteps.registerUserToken(filePostRegisterJoinIC, end_pt, "token")
				.extract().response();
	}

	@When("^I do a GET request to System API get user info$")
	public void iDoAGETRequestToSystemAPIGetUserInfo() {
		String end_pt = AICGETSteps.setEndpointUserID(kmap.get("ucid"));
		sysResp = AICGETSteps.get(end_pt).extract().response();
	}

	@Then("^the insightCoucilMemer value should be set to true$")
	public void theInsightCoucilMemerValueShouldBeSetToTrue() {
		Boolean is_member = sysResp.jsonPath().get("Catalyst.insightsCouncilMember");
		assertTrue(is_member);
	}

	@Given("^Create a user with unique UUID to System API$")
	public void created_new_user_with_unique_uuid() throws Exception {
		end_pt = AICPOSTSteps.setEndPoint();
		String email_value = tUtil.AppendTimestamp("automation@example.com");
		String lastName = tUtil.AppendTimestamp("lastName");
		String firstName = tUtil.AppendTimestamp("firstName");
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.email", email_value);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.lastName", lastName);
		jsonUtils.update_JSONValue("AIC_Customer.json", "$.firstName", firstName);

		sysResp = AICPOSTSteps.createCustomerInAkamai("AIC_Customer.json", end_pt)
				.extract().response();

		kmap.put("ucid", sysResp.jsonPath().getString("uuid"));
		kmap.put("email", email_value);
		kmap.put("lastName", lastName);
		kmap.put("firstName", firstName);
	}

	@When("^I sends a POST request to update created user in catalyst$")
	public void userSendsAPOSTRequestToUpdateCreatedUserInCatalyst() throws Exception {
		String firstName = tUtil.AppendTimestamp("firstName");
		String lastName = tUtil.AppendTimestamp("lastName");
		jsonUtils.update_JSONValue(fileRegUserCatalyst, "firstName", firstName);
		jsonUtils.update_JSONValue(fileRegUserCatalyst, "lastName", lastName);
		jsonUtils.add_JSONPathJsonValue(fileRegUserCatalyst, "ucid", kmap.get("ucid"));

		end_pt = registrationPOSTSteps.setEndpointRegistrations();
		procResp = registrationPOSTSteps.registerUser(fileRegUserCatalyst, end_pt).extract().response();

		jsonUtils.remove_JSONPath(fileRegUserCatalyst, "ucid");
	}

	@When("^I sends a POST request to register user for catalyst$")
	public void iSendsAPOSTRequestToRegisterUserForCatalyst() throws Exception {
		String email_value = tUtil.AppendTimestamp("automation@example.com");
		jsonUtils.update_JSONValue(fileRegUserCatalyst, "email", kmap.get("email"));
		jsonUtils.update_JSONValue(fileRegUserCatalyst, "firstName", kmap.get("firstName"));
		jsonUtils.update_JSONValue(fileRegUserCatalyst, "lastName", kmap.get("lastName"));

		end_pt = registrationPOSTSteps.setEndpointRegistrations();
		procResp = registrationPOSTSteps.registerUser(fileRegUserCatalyst, end_pt).extract().response();
	}

	@When("^user sends a POST request to register for event with ucid through Registration Process API$")
	public void userSendsAPOSTRequestToRegisterForEventWithUcidThroughRegistrationProcessAPI() throws Exception {
		jsonUtils.update_JSONValue(filePostUcidRegisterForEvent, "icvEventId", 1);
		jsonUtils.update_JSONValue(filePostUcidRegisterForEvent, "ucId", kmap.get("ucid"));

		end_pt = registrationPOSTSteps.setEndpointRegistration();
		procResp = registrationPOSTSteps.registerUser(filePostUcidRegisterForEvent, end_pt).extract().response();
	}

	@When("^I do a POST call to register user with context EVENT$")
	public void iDoAPOSTCallToRegisterUserWithContextEVENT() throws Exception {
		String email_value = tUtil.AppendTimestamp("automation@example.com");
		String lastName = tUtil.AppendTimestamp("lastName");
		String firstName = tUtil.AppendTimestamp("firstName");
		jsonUtils.update_JSONValue(fileRegUserEVENT, "email", email_value);
		jsonUtils.update_JSONValue(fileRegUserEVENT, "firstName", firstName);
		jsonUtils.update_JSONValue(fileRegUserEVENT, "lastName", lastName);

		end_pt = registrationPOSTSteps.setEndpointRegistration();
		procResp = registrationPOSTSteps.registerUser(fileRegUserEVENT, end_pt).extract().response();
	}

	@When("^I do a POST call to register user with context EVENT with invalid data$")
	public void iDoAPOSTCallToRegisterUserWithContextEVENTWithInvalidData() throws Exception {
		String email_value = tUtil.AppendTimestamp("automation@example.com");
		String lastName = tUtil.AppendTimestamp("lastName");
		String firstName = tUtil.AppendTimestamp("firstName");
		jsonUtils.update_JSONValue(fileRegUserEVENT, "email", "");
		jsonUtils.update_JSONValue(fileRegUserEVENT, "firstName", firstName);
		jsonUtils.update_JSONValue(fileRegUserEVENT, "lastName", lastName);

		end_pt = registrationPOSTSteps.setEndpointRegistration();
		procResp = registrationPOSTSteps.registerUser(fileRegUserEVENT, end_pt).extract().response();
		jsonUtils.update_JSONValue(fileRegUserEVENT, "email", email_value);
	}
}
