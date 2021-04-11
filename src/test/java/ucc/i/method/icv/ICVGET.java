package ucc.i.method.icv;


import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.hamcrest.Matchers;
import org.junit.Assert;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

//import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ICVGET {
	
	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("icv.basePath");
	public static String icvUrl = webserviceEndpoint+serviceEndpoint+"/api";     
	TestUtils tUtil = new TestUtils();
	RestUtil ICV = new RestUtil(icvUrl);
	 public static Response icvResponse = null;
	 static String end_pt = null;


	
	public ValidatableResponse getUsers(String endpoint) throws URISyntaxException{
				
	return	SerenityRest.rest()
		.given()
		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
		.when()
		.log().all()
		.get(icvUrl + endpoint)
		.then().log().all();
				
	}
	
	
	public ValidatableResponse getEventbyId(String endpoint) throws URISyntaxException{
		
		return	SerenityRest.rest()
			.given()
			.spec(ReuseableSpecifications.getGenericExpRequestSpec())
			.when()
			.log().all()
			.get(icvUrl + endpoint)
			.then().log().all();
				
		}
	

	 public int get_ConfQAId(Response resp) {
		 
		 JsonPath jsonPathEvaluator = resp.jsonPath();
	     List<String> name = jsonPathEvaluator.getList("name");
	     List<Integer> id = jsonPathEvaluator.getList("id");
	     Assert.assertTrue("There are no conferences", id.size() > 0);
    	 // Get Response Body as String 
	     int index = 0;
	     int retval = id.get(0);
//	     Random rand = new Random();
//	     retval = rand.nextInt(id.size());

	     return retval;
    	    	   
}  
	 
		public ValidatableResponse getEvents(String endpoint, String state) throws URISyntaxException{
			
			return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.queryParam("state", state)
				.get(endpoint)
				.then();
				//.log().all();
			    			
			}

		public ValidatableResponse getAllEvents(String endpoint) throws URISyntaxException {
			
			return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.get(endpoint)
				.then();
				//.log().all();
			    			
			}
		
	public void verifyEventsState(Response resp, String state) throws Exception{
		boolean result = true;
		List<String> invalidStateList = new ArrayList<String>();
		List<String> idList = resp.jsonPath().getList("state");
		for(int i = 0 ; i < idList.size(); i++) {
			if(!idList.get(i).equals(state)) {
				invalidStateList.add(idList.get(i));
				result = false;
			}
		}
		Assert.assertTrue("The events contains " + invalidStateList + "as state which is not equal to" + state , result);
		
	}
	
	
	public void verifyValidState(Response resp) {

		boolean result = true;
		boolean innerResult;
		List<String> idList = resp.jsonPath().getList("state");
		
		String[] stateList = { "live", "upcoming", "archive", "hidden" };
		List<String> invalidStateList = new ArrayList<String>();
              
		for(int i = 0 ; i < idList.size(); i++) {
			innerResult = false;
			for(int j = 0; j < stateList.length; j++) {
				
				if( idList.get(i).equals(stateList[j])) {
					innerResult = true;
					break;
				}
			}
			
			if(!innerResult) {
				result = false;
				invalidStateList.add(idList.get(i));
			}

		}
		Assert.assertTrue("The response contains invalid states for events "+ invalidStateList, result);
	}
	
	
	public void verifyUniqueEvents(Response resp) {
		List<Integer> idList = resp.jsonPath().getList("id");
		List<Integer> MultipleEventList = new ArrayList<Integer>();
		boolean result = true;
		
		for (int i = 0; i < idList.size() - 1; i++) {
			for(int j = i+1; j < idList.size(); j++) {
				
				if(idList.get(i) == idList.get(j)) {
					result = false;
					MultipleEventList.add(idList.get(i));
					break;
				}

			}
		}
		
		Assert.assertTrue("The following ids are present multiples time in respone " + MultipleEventList, result);
	}

	public int getEventsCountForState(Response resp, String state) {
		List<String> stateList = resp.jsonPath().getList("state");
		int stateCount = 0;
		
		for(int i = 0; i < stateList.size(); i++) {
			
			if(stateList.get(i).equals(state)) {
				stateCount++;
			}

		}
		return stateCount;
	}
	
	public void verifyEqualNumberOfEvents(Response resp, String state, int count) {
		boolean result = false;
		int stateCount = getEventsCountForState(resp, state);
		if(stateCount == count ) {
			result = true;
		}
		
		Assert.assertTrue("The count for the " + state + " did not match in both request",result);
	}
	
	
	public void verifyEmptyResp(String endpoint) {
		
		SerenityRest.rest()
		.given()
		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
		.when()
		.log().all()
		.get(endpoint)
		.then()
		.body("", Matchers.empty())
		.log().all();
	}
	
	
	public ValidatableResponse get(String endpoint) throws URISyntaxException {

		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.get(icvUrl + endpoint)
				.then()
				.log().all();

	}
	
	public String setEndpointForEvents() {
		String endpoint = "/conferences";
		return endpoint;
	}

	public String setuserEventsEndpoint(String eml) {
		String endpoint = "/users/" + eml + "/conferences";
		return endpoint;
	}

	public String setEndpoint() {
		String endpoint = "/users";
		return endpoint;
	}

	public String setEndpoint(String eml) {
		String endpoint = "/users/" + eml;
		return endpoint;
	}

	public String setEndpointType(String eml) {
		String endpoint = "/users/" + eml + "/type";
		return endpoint;
	}
	
	public String setEndpointEventById(String eventId) {
		String endpoint = "/conferences/" + eventId;
		return endpoint;
	}
	
	
	/*public void Verify_ICV_User_Type() {
		Assert.assertEquals(icvResponse.jsonPath().getString("type"), "subscriber");
	}*/
	  
}