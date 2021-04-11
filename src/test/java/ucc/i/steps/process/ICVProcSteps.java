package ucc.i.steps.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import ucc.i.method.customerprocess.CustomerProcessGET;
import ucc.i.method.eventprocess.EventProcessGET;
import ucc.i.method.icvprocess.ICVProcessPOST;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

public class ICVProcSteps {
	public static Response procResp = null;
	static String end_pt = null;
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	public static Map<String, String> kmap = new HashMap<String, String>();
	
	// Files used
	String filePostRegistration = "ICVProc_Registration.json";
	
	@Steps
	ICVProcessPOST icvProcPOSTSteps;
	
	@Steps
	EventProcessGET eventProcGETSteps;
	
	@Steps
	CustomerProcessGET customerProcGETSteps;
	
	// =================================================================================================================
	// User Registration and Verification
	// =================================================================================================================
	
	@When("^user sends a POST request to register new user for an event (.*) with valid data through ICV Process API$")
    public void user_sends_a_post_request_to_register_new_user_for_an_event_with_valid_data_through_icv_process_api(int eventid) throws Throwable {
		String tsEmail = tUtil.AppendTimestamp("automation@example.com");
		jsonUtils.update_JSONValue(filePostRegistration, "icvEventId", eventid);
        jsonUtils.update_JSONValue(filePostRegistration, "email", tsEmail);
        
        kmap.put("email", tsEmail);
        kmap.put("eventId", String.valueOf(eventid));
        
        end_pt = icvProcPOSTSteps.setEndpointRegistration(eventid);
        procResp = icvProcPOSTSteps.registerUser(end_pt, filePostRegistration).extract().response();
    }
	
	@Then("^new user should be registered with 200 status$")
    public void new_user_should_be_registered_with_200_status() throws Throwable {
        tUtil.verify_msgCode(procResp, "Registration Completed successfully", 200);
        
    }
	
	@When("^user sends a GET request to get list of events for which user has registered$")
    public void user_sends_a_get_request_to_get_list_of_events_for_which_user_has_registered() throws Throwable {
		// Getting user's ucid using email
		end_pt = customerProcGETSteps.setEndpointUserByEmail(kmap.get("email"));
		procResp = customerProcGETSteps.getUserInfo(end_pt).extract().response();
		kmap.put("ucid", procResp.jsonPath().getString("uuid"));
		  
		end_pt = eventProcGETSteps.setEndpointUserEvents(kmap.get("ucid"));
		procResp = eventProcGETSteps.get(end_pt).extract().response();
    }
	
	@Then("^the event should appear in the list events$")
    public void the_event_should_appear_in_the_list_events() throws Throwable {
		tUtil.verifyStatus(procResp, 200);
	      
	    // Fields verification 
		JsonPath jsonpathEvaluator = procResp.jsonPath();
		List<Integer> actualLoEventId = jsonpathEvaluator.get("id");
		Assert.assertTrue("The list should not be empty", actualLoEventId.size() > 0);
		Assert.assertTrue(String.format("EventId %s should be in the list", kmap.get("eventId")),
					actualLoEventId.contains(Integer.valueOf(kmap.get("eventId"))));

		List<String> loTitle = jsonpathEvaluator.getList("title");
		List<String> loDate = jsonpathEvaluator.getList("startDate");
		List<String> loHosts = jsonpathEvaluator.getList("hosts");
		List<String> loTimezone = jsonpathEvaluator.getList("timezone");
		List<String> loLandingPage = jsonpathEvaluator.getList("fullDetailsLink");
    }
}
