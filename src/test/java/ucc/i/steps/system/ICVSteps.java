package ucc.i.steps.system;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.json.simple.JSONObject;
import org.junit.Assert;
import ucc.i.method.icv.ICVDELETE;
import ucc.i.method.icv.ICVGET;
import ucc.i.method.icv.ICVPOST;
import ucc.i.method.icv.ICVPUT;
import ucc.utils.CucumberUtils.CucumberUtils;
import ucc.utils.ICVConferenceDataTable;
import ucc.utils.TestUtils;

import java.net.URISyntaxException;
import java.util.*;

public class ICVSteps {
	
	
	public static Response resp=null;
	public static Map<String,String> kmap = new HashMap<String,String>();
	public static JSONObject requestParams = new JSONObject();
	Boolean flag = false;
	static String email_value;
	static String end_pt = null;
	TestUtils tUtil = new TestUtils();
	List<String> get_Vals=new ArrayList<String>();
	List<String> get_ConfVals=new ArrayList<String>();
	List<String> get_ConfKeys=new ArrayList<String>();
	static String Id;
	int eventsCount = 0;
	
	@Steps
	ICVDELETE IVSDELETESteps;
	
	@Steps
	ICVPUT IVSPUTSteps;
	
	@Steps
	ICVPOST IVSPOSTSteps;
	
	@Steps
	ICVGET IVSGETSteps;
	
	@Title("POST Request with Customer email : {0}, name : {1}, type : {2}")
	@When("^I send a POST request to ICV with timestamp email (.*) name (.*) and type (.*)$")
	public void i_send_a_POST_request_with_the_timestamp_email(String email, String name, String type) throws URISyntaxException {
	    // Write code here that turns the phrase above into concrete actions
		end_pt = IVSPOSTSteps.setEndpoint();
		email_value = tUtil.AppendTimestamp(email);
		resp = IVSPOSTSteps.createUser(email_value, name, type, end_pt)
				 .extract().response();
		get_Vals.add("email");
		get_Vals.add("name");
		get_Vals.add("type");
		kmap = tUtil.store_jsonValues(resp, get_Vals);
		
		requestParams.put("email", email_value);
		requestParams.put("name", name);
		requestParams.put("type", type);
		
	}
	
	
	@Title("Verify Status Code")
	@Then("^user should be created with 201 status code$")
	public void verifyUsersts() {
		tUtil.verifyStatus(resp, 201);
	}
	
	@Title("Get Request with Customer data email : {0}, name : {1}, type : {2}")
	@Then("^user information returned via GET call should match with email (.*) name (.*) and type (.*)$")
	public void user_information_returned_via_GET_call(String email, String name, String type) throws URISyntaxException {
		end_pt = IVSGETSteps.setEndpoint(email_value);
		resp = IVSGETSteps.getUsers(end_pt)
				.extract().response();
		IVSPOSTSteps.verify_ICVUser(kmap, resp);
	}
	
	@When("^user sends a GET request to retrieve user info with invalid email (.*)$")
    public void user_sends_a_get_request_to_retrieve_user_info_with_invalid_email(String email) throws Throwable {
		end_pt = IVSGETSteps.setEndpoint(email);
		resp = IVSGETSteps.getUsers(end_pt)
				.extract().response();
    }

	
	@Title("POST Request with Customer email : {0}, name : {1}, type : {2}, status : {3}, message : {4}")
	@When("^I send a POST request to ICV with invalid data for either of email (.*) name (.*) and type (.*) having sts (.*) and msg (.*)$")
	public void i_send_a_POST_request_with_the_invalid_timestamp_email(String email, String name, String type, int sts, String msg) throws URISyntaxException {
		end_pt = IVSPOSTSteps.setEndpoint();
		email_value = tUtil.AppendTimestamp(email);
		resp = IVSPOSTSteps.createUserInvalidData(email, name, type, end_pt)
				 .extract().response();
		
	}
	
	@Title("Verify invalid data for Status Code and message") 
	@Then("^I should get appropriate status (.*) and message (.*) for email (.*) name (.*) and type (.*)$")
	public void verifyUserInvalidsts(Integer sts, String msg, String email, String name, String type) {
		//IVSPOSTSteps.verify_ICVmsg(resp, msg, Integer.parseInt(sts));
		tUtil.verifyStatus(resp, sts);
		
	}
	
	@Title("POST Request with invalid endpoint type : {0}")
	@When("^I send a POST request to ICV with following body and type (.*)$")
	public void POST_request_with_the_invalid_type(
			String type, io.cucumber.datatable.DataTable table
	) throws URISyntaxException {

		end_pt = IVSPOSTSteps.setEndpointType(type);
		Map<String, String> values = CucumberUtils.convert(table);
		resp = IVSPOSTSteps.createUserType(
				values.get("email"), values.get("name"),
				values.get("type"), end_pt)
				 .extract().response();
	}
	
	@Title("Verify invalid data for Status Code and message type : {0}, status : {1}, message : {2}") 
	@Then("^for the given type (.*) user should get respective status (.*) and message (.*)$")
	public void verifyUserInvalidTypeSts(String type, Integer sts, String msg) {
//		IVSPOSTSteps.verify_ICVmsg(resp, msg, Integer.parseInt(sts));
		tUtil.verifyStatus(resp, sts);
	}
	
	
	@Title("POST Request with Conference description : {0}, end_date : {1}, name : {2}, start_date : {3}, state : {4}, timezone : {5}")
	@When("^I send a POST request to create a conference with description (.*) end_date (.*) name (.*) start_date (.*) state (.*) and timezone (.*)$")
	public void POST_Conf(String desp, String endDate, String name, String startDate, String state, String tz) throws URISyntaxException {
		end_pt = IVSPOSTSteps.setConferenceEndpoint();
		resp = IVSPOSTSteps.createConference(desp, endDate, name, startDate, state, tz, end_pt)
				 .extract().response();
		Id = IVSPOSTSteps.getID(resp);
		get_ConfVals.add("description");
		get_ConfVals.add("end_date");
		get_ConfVals.add("name");
		get_ConfVals.add("start_date");
		get_ConfVals.add("state");
		get_ConfVals.add("timezone");
		kmap = tUtil.store_jsonValues(resp, get_ConfVals);
		
		
	}
	
	@Title("Verify Status Code")
	@Then("^conference should be created with 201 status code$")
	public void verifyConfSts() {
		tUtil.verifyStatus(resp, 201);
	}
	
	@Title("Get Request with Customer data description : {0}, end_date : {1}, name : {2}, start_date : {3}, state : {4}, timezone : {5}")
	@Then("^GET call should match with the information provided while creating conference$")
	public void information_returned_via_GET_call() throws URISyntaxException {
		end_pt = IVSPOSTSteps.setConferenceEndpoint(Id);
		resp = IVSGETSteps.getUsers(end_pt)
				.extract().response();
		IVSPOSTSteps.verify_ICVConference(kmap, resp);
	}
	
	@Title("Verify invalid data for Status Code and message type : {0}, status : {1}, message : {2}") 
	@Then("^user should get appropriate msg (.*) and status (.*)$")
	public void verifyUserInvalidSts(String msg, String sts) {
		IVSPOSTSteps.verify_ICVmsg(resp, msg, Integer.parseInt(sts));
		
	}
	
	@Title("POST Request with invalid endpoint type : {0}")
	@When("^I send following POST request to ICV with endpoint (.*)$")
	public void POST_invalid_type(String endPT, DataTable table) throws URISyntaxException {
		end_pt = IVSPOSTSteps.setConferenceEndpoint(endPT);
		String description = null;
		String end_date = null;
		String name = null;
		String start_date = null;
		String state = null;
		String timezone = null;
		
				 
		List<ICVConferenceDataTable> values = new ArrayList<ICVConferenceDataTable>();
		values = table.asList(ICVConferenceDataTable.class);
			
		
		for (ICVConferenceDataTable value : values) {
			description = value.description;
			end_date = value.end_date;
			name = value.name;
			start_date = value.start_date;
			state = value.state;
			timezone = value.timezone;
		}
		
		
		resp = IVSPOSTSteps.createConference(description, end_date, name, start_date, state, timezone, end_pt)
				 .extract().response();
		
	}
	
	@Title("Verify invalid data for Status Code and message type : {0}, status : {1}, message : {2}") 
	@Then("^for the given endpoint (.*) user should get respective status (.*) and message (.*)$")
	public void verifyConferenceInvalidTypeSts(String endPT, String sts, String msg) {
		IVSPOSTSteps.verify_ICVmsg(resp, msg, Integer.parseInt(sts));
		
	}
	
	@Title("PUT Request with Conference description : {0}, end_date : {1}, name : {2}, start_date : {3}, state : {4}, timezone : {5}")
	@When("^I send a PUT request to newly created conference with description (.*) end_date (.*) name (.*) start_date (.*) state (.*) and timezone (.*) with Update type (.*) and update value (.*)$")
	public void put_Conf(String desp, String endDate, String name, String startDate, String state, String tz, String uK, String uV) throws URISyntaxException {
		end_pt = IVSPOSTSteps.setConferenceEndpoint(Id);
		resp = IVSPUTSteps.updateConference(desp, endDate, name, startDate, state, tz, end_pt, uK, uV)
				 .extract().response();
		
		String[] Keys = { "description", "end_date", "name","start_date", "state", "timezone" };
		String[] values = {desp, endDate, name, startDate, state, tz};
		get_ConfKeys = Arrays.asList( Keys );
		get_ConfVals = Arrays.asList( values );
		kmap = tUtil.store_jsonValues(get_ConfKeys, get_ConfVals);
	
	}
	
	@Title("Verify update Status Code")
	@Then("^conference should be updated with 204 status code$")
	public void verifyConfUpdateSts() {
		tUtil.verifyStatus(resp, 204);
	}
	
	@Title("Get Request to verify with update Key : {0}, Update value : {1}")
	@Then("^GET call should match with the information provided while updating conference with update type (.*) and update value (.*)$")
	public void information_via_GET_call(String uK, String uV) throws URISyntaxException {
		end_pt = IVSPOSTSteps.setConferenceEndpoint(Id);
		resp = IVSGETSteps.getUsers(end_pt)
				.extract().response();
	
		IVSPUTSteps.verify_ICVConference(kmap, resp, uK, uV);
	}
	
	@Title("Get Request with Customer data description : {0}, end_date : {1}, name : {2}, start_date : {3}, state : {4}, timezone : {5}")
	@Then("^I send a GET request to retrive latest test conference$")
	public void GET_call() throws URISyntaxException {
		end_pt = IVSPOSTSteps.setConferenceEndpoint();
		resp = IVSGETSteps.getUsers(end_pt)
				.extract().response();
	    Id = String.valueOf((IVSGETSteps.get_ConfQAId(resp)));
	    
	}
	
	@Title("Storing values")
	@And("^user stored the retrieved conference details$")
	public void store_ConfDetails() throws URISyntaxException {
		
	    end_pt = IVSPOSTSteps.setConferenceEndpoint(Id);
	   
	
	    resp = IVSGETSteps.getEventbyId(end_pt)
	    		.extract().response();
	   
	   kmap = IVSPOSTSteps.store_ICVConferenceId(resp);
		
	}
	
	@Title("PUT Request with Conference description : {0}, end_date : {1}, name : {2}, start_date : {3}, state : {4}, timezone : {5}")
	@When("^update a conference with PUT request having invalid data description (.*) end_date (.*) name (.*) start_date (.*) state (.*) and timezone (.*)$")
	public void put_Conf_invalidData(String desp, String endDate, String name, String startDate, String state, String tz) throws URISyntaxException {
	    // Write code here that turns the phrase above into concrete actions
		end_pt = IVSPOSTSteps.setConferenceEndpoint(Id);
	//	email_value = tUtil.AppendTimestamp(email);
		resp = IVSPUTSteps.updateConference(desp, endDate, name, startDate, state, tz, end_pt)
				 .extract().response();
	
	}
	
	@Title("PUT Request with invalid endpoint type : {0}")
	@When("^I send following PUT request to ICV with endpoint (.*)$")
	public void PUT_invalid_type(String endPT, DataTable table) throws URISyntaxException {
	    // Write code here that turns the phrase above into concrete actions
		end_pt = IVSPOSTSteps.setConferenceEndpoint(endPT);
		String description = null;
		String end_date = null;
		String name = null;
		String start_date = null;
		String state = null;
		String timezone = null;
		
				 
		List<ICVConferenceDataTable> values = new ArrayList<ICVConferenceDataTable>();
		values = table.asList(ICVConferenceDataTable.class);
			
		
		for (ICVConferenceDataTable value : values) {
			description = value.description;
			end_date = value.end_date;
			name = value.name;
			start_date = value.start_date;
			state = value.state;
			timezone = value.timezone;
		}
		
		
		resp = IVSPUTSteps.updateConference(description, end_date, name, start_date, state, timezone, end_pt)
				 .extract().response();
		
	}
	
	@Title("PUT Request with user update Key : {0}, update Value : {1}")
	@When("^I send a PUT request to newly created user with updateKey (.*) and updateValue (.*)$")
	public void put_User(String uK, String uV) throws URISyntaxException {
		end_pt = IVSPOSTSteps.setEndpoint(email_value);
		if(uK.equalsIgnoreCase("email")) {
  		  uV = tUtil.AppendTimestamp(uV);
  		  email_value = uV;
  	  }
	
		resp = IVSPUTSteps.updateUser(requestParams, uK, uV, end_pt)
				 .extract().response();
		
		
	}
	
	
	@Title("Get Request with Customer data updateKey : {0}, updateValue : {1}")
	@Then("^updated information returned via GET call should match with updateKey (.*) and updateValue (.*)$")
	public void Information_returned_via_GET_call(String uK, String uV) throws URISyntaxException {
	    // Write code here that turns the phrase above into concrete actions
		end_pt = IVSGETSteps.setEndpoint(email_value);
		resp = IVSGETSteps.getUsers(end_pt)
				.extract().response();
	
		IVSPUTSteps.verify_ICVUser(kmap, resp, uK, uV, email_value);
	}
	
	@Title("Get Request with Customer data email : {0}, name : {1}, type : {2}")
	@When("^I send a PUT request to newly created user with email (.*) name (.*) type (.*)$")
	public void invalidData_PUT(String e, String n, String t) throws URISyntaxException {
		end_pt = IVSGETSteps.setEndpoint(email_value);
		requestParams.put("email", e);
		requestParams.put("name", n);
		requestParams.put("type", t);
		resp = IVSPUTSteps.updateUser(requestParams, end_pt)
				 .extract().response();
	
	}
	
	@Title("POST Request with Customer data")
	@When("^I send a POST request to ICV with following data$")
	public void POST_request_with_the_timestamp_email(DataTable dataTable) throws URISyntaxException {
		end_pt = IVSPOSTSteps.setEndpoint();
		Map<String, String> values = CucumberUtils.convert(dataTable);

		email_value = tUtil.AppendTimestamp(values.get("email"));
		
		resp = IVSPOSTSteps.createUserType(
				email_value,
				values.get("name"), values.get("type"),
				end_pt)
				 .extract().response();	
	}
	
	@Title("POST Request with invalid endpoint type : {0}")
	@When("^I send a PUT request to ICV with following body and type (.*)$")
	public void PUT_request_with_the_invalid_type(String type, DataTable table) throws URISyntaxException {
		end_pt = IVSPOSTSteps.setEndpointType(type);
		Map<String, String> values = CucumberUtils.convert(table);

		requestParams.put("email", values.get("email"));
		requestParams.put("name", values.get("name"));
		requestParams.put("type", values.get("type"));
		
		resp = IVSPUTSteps.updateUser(requestParams, values.get("type"))
				 .extract().response();
		
	}
	
	@Title("PUT Request with user update Key : {0}, update Value : {1}")
	@When("^I send a PUT type request to newly created user with updateKey (.*) and updateValue (.*)$")
	public void put_UserType(String uK, String uV) throws URISyntaxException {
		end_pt = IVSGETSteps.setEndpointType(email_value);
		if(uK.equalsIgnoreCase("email")) {
  		  uV = tUtil.AppendTimestamp(uV);
  		  email_value = uV;
  	  }
	
		resp = IVSPUTSteps.updateUser(requestParams, uK, uV, end_pt)
				 .extract().response();
		
		
	}
	
	@Title("Get Request with Customer data email : {0}, name : {1}, type : {2}")
	@When("^I send a PUT type request to newly created user with email (.*) name (.*) type (.*)$")
	public void invalidData_PUTType(String e, String n, String t) throws URISyntaxException {
		end_pt = IVSGETSteps.setEndpointType(email_value);
		requestParams.put("email", e);
		requestParams.put("name", n);
		requestParams.put("type", t);
		resp = IVSPUTSteps.updateUser(requestParams, end_pt)
				 .extract().response();
	
	}
	
	@Title("Get Request with Customer data")
	@When("^I send a GET request with conference id$")
	public void GET_conf() throws URISyntaxException {
		end_pt = IVSPOSTSteps.setConferenceEndpoint(Id);
		resp = IVSGETSteps.getUsers(end_pt)
				.extract().response();

	}
	
	@Title("Verify Status Code")
	@Then("^user should get 200 status$")
	public void verifySts11() {
		tUtil.verifyStatus(resp, 200);
	}
	
	@Title("Get Request with Conference id : {0}")
	@When("^I send a GET request with conference id (.*)$")
	public void GET_conf_invalid(String conf_id) throws URISyntaxException {
		end_pt = IVSPOSTSteps.setConferenceEndpoint(conf_id);
		resp = IVSGETSteps.getUsers(end_pt)
				.extract().response();

	}
	
	@Title("Delete request by email")
	@When("^I send a DELETE request to delete a user by email$")
	public void delete_UserType() throws URISyntaxException {
		end_pt = IVSGETSteps.setEndpoint(email_value);
			
		resp = IVSDELETESteps.deleteUser(end_pt)
				.extract().response();
		
	}
	
	@Title("Verify Status Code")
	@Then("^user should be deleted with 204 status code$")
	public void verifyUserDelSts() {
		tUtil.verifyStatus(resp, 204);
	}
	
	@Title("Get Request with deleted email")
	@When("^I send a GET request by email$")
	public void GET_User_email() throws URISyntaxException {
		end_pt = IVSGETSteps.setEndpoint(email_value);
		resp = IVSGETSteps.getUsers(end_pt)
				.extract().response();

	}
	
	
	@Title("Verify Status Code")
	@Then("^user should get 404 status$")
	public void verifyUserSts() {
		tUtil.verifyStatus(resp, 404);
	}
	
	@Title("Delete request by email : {0}")
	@When("^I send a DELETE request with email (.*)$")
	public void delete_UserType(String email) throws URISyntaxException {
		end_pt = IVSGETSteps.setEndpoint(email);
		resp = IVSDELETESteps.deleteUser(end_pt)
				.extract().response();
		
	}
	
	@Title("Verify Status Code")
	@Then("^the request returns status '200'$")
	public void verifyStatus() {
		tUtil.verifyStatus(resp);
	}
	
	@When("^User send a GET request to ICV to get details of events with state (.*)$")
	public void send_a_GET_request_to_ICV_to_get_details_of_events_with_state(String state)
			throws URISyntaxException {
		end_pt = IVSGETSteps.setEndpointForEvents();
		resp = IVSGETSteps.getEvents(end_pt, state).extract().response();
	}
	
	@When("^the response should contain events only with respective state (.*)$")
	public void response_should_contain_events_only_with_respective_state(String state) throws Exception{
		IVSGETSteps.verifyEventsState(resp, state);
	}
	
	@Then("^for the given state (.*) user should get respective status (.*) and message (.*)$")
	public void user_should_get_respective_status_and_message(String state, int sts, String msg) {
		tUtil.verifyStatus(resp, sts);
	}

	@When("^User send a GET request to ICV to get details of all events$")
	public void GET_request_to_ICV_to_get_details_of_all_events() throws URISyntaxException{
		end_pt = IVSGETSteps.setEndpointForEvents();
		resp = IVSGETSteps.getAllEvents(end_pt).extract().response();
	}
	
	@Then("^list of all events in ICV should be displayed$")
  public void list_of_all_events_in_icv_should_be_displayed() throws Throwable {
      String[] keys = {"name", "description", "hosts", "eventUrl", "startDate"};
      List<String> loKeys = Arrays.asList(keys);
      
      JsonPath jsonpathEvaluator = resp.jsonPath();
      // Verify every event has the mentioned fields
      for (String k : loKeys) {
        Assert.assertNotNull(jsonpathEvaluator.getList(k));
      }
      
      // Store the random conference id
      List<Integer> loEventId = jsonpathEvaluator.getList("id");
      Random rand = new Random();
      kmap.put("eventId", String.valueOf(loEventId.get(rand.nextInt(loEventId.size()))));
      
  }
	
	@Then("^the response should contain events only with valid state$")
	public void check_events_only_with_valid_state() {
		
		IVSGETSteps.verifyValidState(resp);
		
	}
	
	@Then("^the response should not contain duplicate event$")
	public void response_not_contain_duplicate_event() {
		IVSGETSteps.verifyUniqueEvents(resp);
	}
	
	@When("^user sends a POST request to ICV to register user with email for an event with conference_id (.*)$")
	public void POST_request_to_register_user_with_email_for_event_with_conference_id(String id) throws URISyntaxException{
		end_pt = IVSPOSTSteps.registerConferenceEndpoint(id);
	    resp = IVSPOSTSteps.registerUser(kmap.get("email"), end_pt).extract().response();
	}
	
	@When("^user sends a POST request to ICV to register user with email for an existing event$")
	public void user_sends_a_post_request_to_icv_to_register_user_with_email_for_an_existing_event() throws Throwable {
		end_pt = IVSPOSTSteps.registerConferenceEndpoint(kmap.get("eventId"));
		resp = IVSPOSTSteps.registerUser(kmap.get("email"), end_pt).extract().response();
	}

	@Then("^the user should be registered for the event$")
	public void verifyRegisterStatus() {
		tUtil.verifyStatus(resp, 204);
	}
	
	@When("^User sends a POST request to ICV to register user for event with following email and invalid conference_id (.*)$")
	public void POST_request_to_register_user_for_event_with_invalid_conference_id(String id, DataTable table) throws URISyntaxException {

		Map<String, String> values = CucumberUtils.convert(table);
		
		end_pt = IVSPOSTSteps.registerConferenceEndpoint(id);
		String emailValue = tUtil.AppendTimestamp(values.get("email"));
		resp = IVSPOSTSteps.registerUser(emailValue, end_pt).extract().response();
	}

	@Then("^for the given conference_id (.*) user should get respective status (.*) and message (.*)$")
	public void check_message_for_invalide_conference_id(String cid, int sts, String msg) {
		
		IVSPOSTSteps.verify_ICVmsg(resp, msg, sts);
	}
	
	@When("^User sends a POST request to ICV to register user for event with following conference id and invalid email (.*)$")
	public void POST_request_to_register_user_for_event_with_invalid_email(String email, DataTable table) throws URISyntaxException {
		String id = null;
		 
		List<String> values =  table.asList(String.class);
		id = values.get(1);
		
		end_pt = IVSPOSTSteps.registerConferenceEndpoint(id);
		resp = IVSPOSTSteps.registerUser(email, end_pt).extract().response();
	    
	}
	

	@Then("^for the given email (.*) user should get respective status (.*) and message (.*)$")
	public void check_message_for_invalid_email(String email, int sts, String msg) {
		tUtil.verifyStatus(resp, sts);
	}

	@And("^User get the count of events for respective state (.*)$")
	public void get_the_count_of_events_for_state(String state) {
		eventsCount = IVSGETSteps.getEventsCountForState(resp, state);
	}

	@Then("^the count of events for state (.*) should match for both request$")
	public void match_count_for_both_request(String state) {
		IVSGETSteps.verifyEqualNumberOfEvents(resp, state, eventsCount);
	}
	
	@Title("User is registered to the conference")
	@When("^user is registered to the conference via POST method$")
	public void register_userConf() throws URISyntaxException {
		end_pt = IVSPOSTSteps.registerConferenceEndpoint(Id);
		
		resp = IVSPOSTSteps.registerUser(email_value, end_pt)
				 .extract().response();
	
	}
	
	@Title("verify retrived conference")
	@Then("^user's events retrived by GET call should match with the event user is registered$")
	public void verify_userConf() throws URISyntaxException {
		end_pt = IVSGETSteps.setuserEventsEndpoint(email_value);
		
		resp = IVSGETSteps.getEventbyId(end_pt)
				 .extract().response();
		
		IVSPOSTSteps.verify_ICVConference(kmap, resp);
	
	}
	
	@Title("verify retrived conference")
	@And("^no user event should be returned via GET call$")
	public void blank_userConf() throws URISyntaxException {
	    end_pt = IVSGETSteps.setuserEventsEndpoint(email_value);
		
		IVSGETSteps.verifyEmptyResp(end_pt);
	
	}
	
	
	@Title("verify error codes and messages")
	@When("^user sends an invalid data (.*) to retrieve the event$")
	public void invalid_userConf(String data) throws URISyntaxException {
		end_pt = IVSGETSteps.setuserEventsEndpoint(data);
		
		resp = IVSGETSteps.getEventbyId(end_pt)
				 .extract().response();
	
	}
	
	@When("^I send a GET request to retrieve the events user is registered for$")
    public void i_send_a_get_request_to_retrieve_the_events_user_is_registered_for() throws Throwable {
		end_pt = IVSGETSteps.setuserEventsEndpoint(email_value);

		resp = IVSGETSteps.getEventbyId(end_pt).extract().response();
    }
	
	@Then("^user should be registered for event (.*)$")
    public void user_should_be_registered_for_event(int cid) throws Throwable {
        Assert.assertTrue(resp.jsonPath().getList("id").contains(cid));
    }
	
	@Then("^that event should be in the list of registered events$")
    public void that_event_should_be_in_the_list_of_registered_events() throws Throwable {
		Assert.assertTrue(resp.jsonPath().getList("id").contains(Integer.valueOf(kmap.get("eventId"))));
    }

	@Then("^response should be with (.*) status$")
	public void responseShouldBeWithStsStatus(int sts) {
		tUtil.verifyStatus(resp, sts);
	}
}