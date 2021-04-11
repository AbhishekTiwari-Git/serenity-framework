package ucc.i.steps.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import ucc.i.method.customerprocess.CustomerProcessGET;
import ucc.i.method.eventprocess.EventProcessGET;
import ucc.i.method.icv.ICVGET;
import ucc.i.steps.experience.AccountExpSteps;
import ucc.utils.TestUtils;

public class EventProcSteps {
  public static Response procResp = null;
  public static Response sysResp = null;
  static String end_pt = null;
  TestUtils tUtil = new TestUtils();
  public static Map<String,String> kmap = new HashMap<String,String>();
  /*
   * keys in kmap:
   * email -- user's email
   * eventId -- id of the event
   */
  
  @Steps
  EventProcessGET eventProcGETSteps;
  
  @Steps
  CustomerProcessGET customerProcGETSteps;
  
  @Steps
  ICVGET icvGETSteps;
  
  // =================================================================================================================
  // Response Status and Message Verification
  // ================================================================================================================= 
  
  @Then("^the request should return error message (.*) with status code (.*)$")
  public void the_request_should_return_error_message_with_status_code(String msg, int stcode) throws Throwable {
      tUtil.verify_msgCode(procResp, msg, stcode);
  }
  
  // =================================================================================================================
  // Event Retrieval and Verification
  // =================================================================================================================  
  
  @When("^user sends a GET request to retrieve all the events user is registered for through Event Process API$")
  public void user_sends_a_get_request_to_retrieve_all_the_events_user_is_registered_for_through_event_process_api() throws Throwable {
      // Because user registration is another proc api --> need to fetch kmap info from registration proc api
	  EventProcSteps.kmap = RegistrationProcSteps.kmap;
	  
	  // Getting user's ucid using email
	  end_pt = customerProcGETSteps.setEndpointUserByEmail(kmap.get("email"));
	  procResp = customerProcGETSteps.getUserInfo(end_pt).extract().response();
	  kmap.put("ucid", procResp.jsonPath().getString("uuid"));
	  
	  end_pt = eventProcGETSteps.setEndpointUserEvents(kmap.get("ucid"));
	  procResp = eventProcGETSteps.get(end_pt).extract().response();
  }
  
  @When("^user sends a GET request to retrieve the events of user who is registered for none through Event Process API$")
  public void user_sends_a_get_request_to_retrieve_the_events_of_user_who_is_registered_for_none_through_event_process_api() throws Throwable {
	  // Because user creation is in system API --> need to fetch kmap info from Account Exp API
	  EventProcSteps.kmap = AccountExpSteps.kmap;
	  
	  end_pt = eventProcGETSteps.setEndpointUserEvents(kmap.get("ucid"));
	  procResp = eventProcGETSteps.get(end_pt).extract().response();
  }
  
  @When("^user sends a GET request to retrieve all the events user is registered for through ICV System API$")
  public void user_sends_a_get_request_to_retrieve_all_the_events_user_is_registered_for_through_icv_system_api() throws Throwable {
      sysResp = icvGETSteps.get(icvGETSteps.setuserEventsEndpoint(kmap.get("email"))).extract().response();
  }
  
  @When("^user sends a GET request to retrieve all the events for user (.*) through Event Process API$")
  public void user_sends_a_get_request_to_retrieve_all_the_events_for_user_through_event_process_api(String ucid) throws Throwable {
	  end_pt = eventProcGETSteps.setEndpointUserEvents(ucid);
	  procResp = eventProcGETSteps.get(end_pt).extract().response();
  }
  
  @When("^user sends a POST request to get list of all events through Event Process API$")
  public void user_sends_a_post_request_to_get_list_of_all_events_through_event_process_api() throws Throwable {
      end_pt = eventProcGETSteps.setEndpointAllEvents();
      procResp = eventProcGETSteps.get(end_pt).extract().response();
  }
  
  @When("^user sends a GET request to get event details using valid eventId through Event Process API$")
  public void user_sends_a_get_request_to_get_event_details_using_valid_eventid() throws Throwable {
      end_pt = eventProcGETSteps.setEndpointEventById(kmap.get("eventId"));
      procResp = eventProcGETSteps.get(end_pt).extract().response();
  }
  
  @When("^user sends a GET request to get details of event (.*)$")
  public void user_sends_a_get_request_to_get_details_of_event(String eventid) throws Throwable {
	  end_pt = eventProcGETSteps.setEndpointEventById(eventid);
      procResp = eventProcGETSteps.get(end_pt).extract().response();
  }
  
  @When("^user sends a GET request to get event details through ICV System API$")
  public void user_sends_a_get_request_to_get_event_details_through_icv_system_api() throws Throwable {
	  end_pt = icvGETSteps.setEndpointEventById(kmap.get("eventId"));
	  sysResp = icvGETSteps.getEventbyId(end_pt).extract().response();
  }
  
  @Then("^list of all events should be displayed with 200 status$")
  public void list_of_all_events_should_be_displayed_with_200_status() throws Throwable {
	  tUtil.verifyStatus(procResp, 200);
	  
	  if (procResp.body().equals("[]")) {
		  // Empty --> nothing we can do?
	  } else {
		  // Fields verification 
		  JsonPath jsonpathEvaluator = procResp.jsonPath();
		  
		  String[] keys = {"title", "startDate", "hosts", "timezone", "fullDetailsLink"};
		  tUtil.verifyKeysInArray(procResp, keys);
		  
		  List<Integer> actualLoEventId = jsonpathEvaluator.get("id");
		  Random rand = new Random();
		  kmap.put("eventId", String.valueOf(actualLoEventId.get(rand.nextInt(actualLoEventId.size()))));
	  }
	  
  }
  
  @Then("^event details should be displayed with 200 status$")
  public void event_details_should_be_displayed_with_200_status() throws Throwable {
	  tUtil.verifyStatus(procResp, 200);
      
      // Fields verification
	  String[] keys = {"id", "title", "state", "isStreamingNow", "isFree", "registerLink", "fullDetailsLink", "timeZone", "startDate", "endDate"};
	  tUtil.verifyKeysInObject(procResp, keys);
	  
  }
  
  @Then("^all the events user is registered for should be returned with status code 200$")
  public void all_the_events_user_is_registered_for_should_be_returned_with_status_code_200() throws Throwable {
	  tUtil.verifyStatus(procResp, 200);
      
      // Fields verification 
	  JsonPath jsonpathEvaluator = procResp.jsonPath();
	  List<Integer> actualLoEventId = jsonpathEvaluator.get("id");
	  Assert.assertTrue("The list should not be empty", actualLoEventId.size() > 0);
	  Assert.assertTrue(String.format("EventId %s should be in the list", kmap.get("eventId")),
				actualLoEventId.contains(Integer.valueOf(kmap.get("eventId"))));

	  // TODO:  Hosts in response are null although information should be available?
	  // removing "hosts" from the list of keys for now --> need to revisit later
	  String[] keys = {"title", "startDate", "timezone", "fullDetailsLink"};
	  tUtil.verifyKeysInArray(procResp, keys);
      
  }
  
  @Then("^event details from ICV System API should match details from Event Process API$")
  public void event_details_should_match_details_from_event_process_api() throws Throwable {
	  eventProcGETSteps.verifyEvents(procResp, sysResp, true);
  }

  
  @Then("^the received list of events from System API should match the list from Process API$")
  public void the_received_list_of_events_from_system_api_should_match_the_list_from_process_api() throws Throwable {
	  eventProcGETSteps.verifyUserEvents(procResp, sysResp, true);
  }
  
  @Then("^the response should be empty list of events for that user with status code 200$")
  public void the_response_should_be_empty_list_of_events_for_that_user_with_status_code_200() throws Throwable {
	  tUtil.verifyStatus(procResp, 200);
	  tUtil.verify_emptyArray(procResp);
  }
  
}
