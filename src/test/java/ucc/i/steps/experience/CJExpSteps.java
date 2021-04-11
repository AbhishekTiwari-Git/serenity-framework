package ucc.i.steps.experience;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import ucc.i.method.cjexp.CJExpGET;
import ucc.i.method.cjexp.CJExpPOST;
import ucc.i.method.eventprocess.EventProcessGET;
import ucc.i.method.icv.ICVGET;
import ucc.i.steps.experience.AccountExpSteps;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

public class CJExpSteps {
	public static Response expResp = null;
	public static Response procResp = null;
	public static Response sysResp = null;
	static String end_pt = null;
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	public static Map<String, String> kmap = new HashMap<String, String>();

	@Steps
	CJExpGET cjExpGETSteps;
	@Steps
	CJExpPOST cjExpPOSTSteps;

	@Steps
	EventProcessGET eventProcGETSteps;

	@Steps
	ICVGET icvGETSteps;

	// Files used
	String fileValidateToken = "CJExp_POST_Validate_Token.json";

	// =================================================================================================================
	// Status code and message verification
	// =================================================================================================================

	@Then("^the following error message (.*) with status code (.*) should be displayed$")
	public void the_following_error_message_with_status_code_should_be_displayed(String msg, int sts) throws Throwable {
		tUtil.verify_msgCode(expResp, msg, sts);
	}

	// =================================================================================================================
	// Event Retrieval and Verification
	// =================================================================================================================

	@When("^user sends a GET request to retrieve the event details through CJ Experience API$")
	public void user_sends_a_get_request_to_retrieve_the_event_details_through_cj_experience_api() throws Throwable {
		end_pt = cjExpGETSteps.setEndpointEvents();
		expResp = cjExpGETSteps.getEvent(end_pt).extract().response();
	}

	@When("^user sends a GET request with parameter (.*) to retrieve the events details through CJ Experience API$")
	public void user_sends_a_get_request_with_parameter_to_retrieve_the_events_details_through_cj_experience_api(
			String param) throws Throwable {
		end_pt = cjExpGETSteps.setEndpointEvents();
		expResp = cjExpGETSteps.getEvent(end_pt, param).extract().response();
	}

	@Then("^details of event should be returned with 200 status$")
	public void details_of_event_should_be_returned_with_200_status() throws Throwable {
		tUtil.verifyStatus(expResp, 200);

		JsonPath jsonpathEvaluator = expResp.jsonPath();
		List<Integer> loEventId = jsonpathEvaluator.get("id");
		System.out.println(loEventId);
		Assert.assertTrue("There should be only one event", loEventId.size() == 1);
		kmap.put("eventId", String.valueOf(loEventId.get(0)));

		// Verifying the fields such as title, hosts, startDate, links
		// Keys that should be present in event info
		String[] arrayKeys = { "title", "startDate", "hosts", "links" };
		List<String> loKeys = Arrays.asList(arrayKeys);

		// Verification of fields existence
		for (String k : loKeys) {
			Assert.assertTrue(!jsonpathEvaluator.getList(k).contains(null));
		}
	}

	@Then("^details of (.*) events should be returned with 200 status$")
	public void details_of_events_should_be_returned_with_200_status(int num) throws Throwable {
		tUtil.verifyStatus(expResp, 200);

		JsonPath jsonpathEvaluator = expResp.jsonPath();
		List<Integer> loEventId = jsonpathEvaluator.get("id");
		Assert.assertTrue("There should be " + num + " event(s)", loEventId.size() == num);

		// Verifying the fields such as title, hosts, startDate, links
		// Keys that should be present in event info
		String[] arrayKeys = { "title", "startDate", "hosts", "links" };
		List<String> loKeys = Arrays.asList(arrayKeys);

		// Verification of fields existence
		for (String k : loKeys) {
			Assert.assertTrue(!jsonpathEvaluator.getList(k).contains(null));
		}
		
    }
	
	@And("^one random eventId should be chosen$")
    public void one_random_eventid_should_be_chosen() throws Throwable {
		JsonPath jsonpathEvaluator = expResp.jsonPath();
		List<Integer> loEventId = jsonpathEvaluator.get("id");
		
		Random rand = new Random();
		kmap.put("eventId", String.valueOf(loEventId.get(rand.nextInt(loEventId.size()))));
    }

	@When("^user sends a GET request to retrieve the event details through Event Process API$")
	public void user_sends_a_get_request_to_retrieve_the_event_details_through_event_process_api() throws Throwable {
		procResp = eventProcGETSteps.get(eventProcGETSteps.setEndpointEventById(kmap.get("eventId"))).extract()
				.response();
	}

	@Then("^event details received from Experience and Process layers should match$")
	public void event_details_received_from_experience_and_process_layers_should_match() throws Throwable {
		JsonPath jsonpathEvaluator = expResp.jsonPath();
		int expId = (int) jsonpathEvaluator.getList("id").get(0);
		String expTitle = (String) jsonpathEvaluator.getList("title").get(0);
		String expLink = (String) jsonpathEvaluator.getList("links.details").get(0);

		jsonpathEvaluator = procResp.jsonPath();
		int procId = jsonpathEvaluator.getInt("id");
		String procTitle = jsonpathEvaluator.getString("title");
		String procLink = jsonpathEvaluator.getString("fullDetailsLink");

		Assert.assertEquals(String.format("The event id should be %d but got %d", expId, procId), expId, procId);
		Assert.assertEquals(String.format("The event title should be %s but got %s", expTitle, procTitle), expTitle,
				procTitle);
		Assert.assertEquals(String.format("The event link should be %s but got %s", expLink, procLink), expLink,
				procLink);
	}

	@And("^each event details received from Experience and Process layers should match$")
	public void each_event_details_received_from_experience_and_process_layers_should_match() throws Throwable {
		JsonPath jsonpathEvaluator = expResp.jsonPath();
		List<Integer> loEventId = jsonpathEvaluator.get("id");
		List<String> loTitle = jsonpathEvaluator.get("title");
		List<String> loLink = jsonpathEvaluator.get("links.details");
		for (int i = 0; i < loEventId.size(); i++) {
			int expId = loEventId.get(i);
			String expTitle = (String) loTitle.get(i);
			String expLink = (String) loLink.get(i);

			procResp = eventProcGETSteps.get(eventProcGETSteps.setEndpointEventById(String.valueOf(expId))).extract()
					.response();

			jsonpathEvaluator = procResp.jsonPath();
			int procId = jsonpathEvaluator.getInt("id");
			String procTitle = jsonpathEvaluator.getString("title");
			String procLink = jsonpathEvaluator.getString("fullDetailsLink");

			Assert.assertEquals(String.format("The event id should be %d but got %d", expId, procId), expId, procId);
			Assert.assertEquals(String.format("The event title should be %s but got %s", expTitle, procTitle), expTitle,
					procTitle);
			Assert.assertEquals(String.format("The event link should be %s but got %s", expLink, procLink), expLink,
					procLink);
		}

	}

	@When("^user sends a GET request to retrieve the event details through ICV System API$")
	public void user_sends_a_get_request_to_retrieve_the_event_details_through_icv_system_api() throws Throwable {
		sysResp = icvGETSteps.getEventbyId(icvGETSteps.setEndpointEventById(kmap.get("eventId"))).extract().response();
	}

	@Then("^event details received from Process and System layers should match$")
	public void event_details_received_from_process_and_system_layers_should_match() throws Throwable {
		eventProcGETSteps.verifyEvents(procResp, sysResp, true);
	}

	@And("^each event details received from Process and System layers should match$")
	public void each_event_details_received_from_process_and_system_layers_should_match() throws Throwable {
		JsonPath jsonpathEvaluator = expResp.jsonPath();
		for (Object eventID : jsonpathEvaluator.getList("id")) {
			int id = (int) eventID;

			procResp = eventProcGETSteps.get(eventProcGETSteps.setEndpointEventById(String.valueOf(id))).extract()
					.response();
			sysResp = icvGETSteps.getEventbyId(icvGETSteps.setEndpointEventById(String.valueOf(id))).extract()
					.response();

			eventProcGETSteps.verifyEvents(procResp, sysResp, true);
		}
	}

	// =================================================================================================================
	// Event Retrieval and Verification
	// =================================================================================================================

	@When("^user sends a POST request to validate the access token$")
	public void user_sends_a_post_request_to_validate_the_access_token() throws Throwable {
		// As step definition is in AccountExpSteps, we retrieve the values from there
		kmap = AccountExpSteps.kmap;

		jsonUtils.update_JSONValue(fileValidateToken, "token", kmap.get("accessToken"));
		jsonUtils.update_JSONValue(fileValidateToken, "ucid", kmap.get("ucid"));

		end_pt = cjExpPOSTSteps.setEndpointAccessToken();
		expResp = cjExpPOSTSteps.validateToken(end_pt, fileValidateToken).extract().response();
	}

	@When("^user sends a POST request to validate the access token with ucid (.*)$")
	public void user_sends_a_post_request_to_validate_the_access_valid_token_with_ucid(String ucid) throws Throwable {
		// As step definition is in AccountExpSteps, we retrieve the values from there
		kmap = AccountExpSteps.kmap;

		jsonUtils.update_JSONValue(fileValidateToken, "token", kmap.get("accessToken"));
		jsonUtils.update_JSONValue(fileValidateToken, "ucid", ucid);

		end_pt = cjExpPOSTSteps.setEndpointAccessToken();
		expResp = cjExpPOSTSteps.validateToken(end_pt, fileValidateToken).extract().response();
	}

	@When("^user sends a POST request to validate the access token (.*) with ucid$")
	public void user_sends_a_post_request_to_validate_the_access_token_with_valid_ucid(String token) throws Throwable {
		// As step definition is in AccountExpSteps, we retrieve the values from there
		kmap = AccountExpSteps.kmap;

		jsonUtils.update_JSONValue(fileValidateToken, "token", token);
		jsonUtils.update_JSONValue(fileValidateToken, "ucid", kmap.get("ucid"));

		end_pt = cjExpPOSTSteps.setEndpointAccessToken();
		expResp = cjExpPOSTSteps.validateToken(end_pt, fileValidateToken).extract().response();
	}

	@Then("^response should return message valid as (.*) with status 200$")
	public void response_should_return_message_valid_as_with_status_200(String valid) throws Throwable {
		String expected = "\"valid\": " + valid;
		tUtil.verify_msgCode(expResp, expected, 200);
	}

}
