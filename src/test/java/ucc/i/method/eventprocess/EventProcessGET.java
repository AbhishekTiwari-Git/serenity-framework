package ucc.i.method.eventprocess;

import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.junit.Assert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class EventProcessGET {
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
      .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
      .getProperty("eventProcess.basePath");
	public static String EventProcess_url = webserviceEndpoint + serviceEndpoint + "/api";
	TestUtils tUtil = new TestUtils();
	RestUtil EPA = new RestUtil(EventProcess_url);

	public ValidatableResponse get(String endpoint) {
		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.get(EventProcess_url + endpoint)
				.then()
				.log().all();
	}
  
	public String setEndpointUserEvents(String ucid) {
		String endpoint = String.format("/users/%s/events", ucid);
		return endpoint;
	}
	
	public String setEndpointEventById(String eventId) {
		String endpoint = String.format("/events/%s", eventId);
		return endpoint;
	}
	
	public String setEndpointAllEvents() {
		String endpoint = "/events";
		return endpoint;
	}
	
	// Verification of several essential fields representing an event
	// If System API  response is passed, then it should be passed as second response
	public void verifyUserEvents(Response r1, Response r2, boolean sysPresent) {
		System.out.println(r1.getBody().asString());
		if (new JSONArray(r1.getBody().asString()).isEmpty() && new JSONArray(r2.getBody().asString()).isEmpty()) {
			System.out.println("Both responses are empty lists");
		} else {
			// Verification of id, title, fullDetailsLink and startDate
			JsonPath jsonpathEvaluator = r1.jsonPath();
			List<Integer> r1LoId = jsonpathEvaluator.getList("id");
			List<String> r1LoTitle = jsonpathEvaluator.getList("title");
			List<String> r1LoLinks = jsonpathEvaluator.getList("fullDetailsLink");
			List<String> r1LoDate = jsonpathEvaluator.getList("startDate");
			
			jsonpathEvaluator = r2.jsonPath();
			List<Integer> r2LoId = jsonpathEvaluator.getList("id");
			List<String> r2LoTitle = (sysPresent) ? jsonpathEvaluator.getList("name") : jsonpathEvaluator.getList("title");
			List<String> r2LoLinks = (sysPresent) ? jsonpathEvaluator.getList("eventUrl") : jsonpathEvaluator.getList("fullDetailsLink");
			List<String> r2LoDate = jsonpathEvaluator.getList("startDate");
			
			Assert.assertTrue("The list of ids should be the same", r1LoId.containsAll(r2LoId));
			Assert.assertTrue("The list of titles should be the same", r1LoTitle.containsAll(r2LoTitle));
			Assert.assertTrue("The list of links should be the same", r1LoLinks.containsAll(r2LoLinks));
			Assert.assertTrue("The list of dates should be the same", r1LoDate.containsAll(r2LoDate));
			
			// Alternative: sort and then check for equality
			Collections.sort(r1LoId);
			Collections.sort(r2LoId);
			Assert.assertEquals("The list of ids should be the same", r1LoId, r2LoId);
			
			Collections.sort(r1LoTitle);
			Collections.sort(r2LoTitle);
			Assert.assertEquals("The list of titles should be the same", r1LoTitle, r2LoTitle);
			
			Collections.sort(r1LoLinks);
			Collections.sort(r2LoLinks);
			Assert.assertEquals("The list of links should be the same", r1LoLinks, r2LoLinks);
			
			Collections.sort(r1LoDate);
			Collections.sort(r2LoDate);
			Assert.assertEquals("The list of dates should be the same", r1LoDate, r2LoDate);
		}
	}
	
	// Verification of several essential fields representing an event
	// If System API  response is passed, then it should be passed as second response
	public void verifyEvents(Response r1, Response r2, boolean sysPresent) {
		JsonPath jsonpathEvaluator = r1.jsonPath();
		
		// Verifying id, title, startDate and full link
		int r1Id = jsonpathEvaluator.getInt("id");
		String r1Title = jsonpathEvaluator.getString("title");
		String r1Link = jsonpathEvaluator.getString("fullDetailsLink");
		String r1Date = jsonpathEvaluator.getString("startDate");
		
		jsonpathEvaluator = r2.jsonPath();
		int r2Id = jsonpathEvaluator.getInt("id");
		String r2Title = (sysPresent) ? jsonpathEvaluator.getString("name") : jsonpathEvaluator.getString("title");
		String r2Link = (sysPresent) ? jsonpathEvaluator.getString("eventUrl") : jsonpathEvaluator.getString("fullDetailsLink");
		String r2Date = jsonpathEvaluator.getString("startDate");
		
		Assert.assertEquals(String.format("The event id should be %d but got %d", r1Id, r2Id),
				r1Id, r2Id);
		Assert.assertEquals(String.format("The event title should be %s but got %s", r1Title, r2Title),
				r1Title, r2Title);
		Assert.assertEquals(String.format("The event link should be %s but got %s", r1Link, r2Link),
				r1Link, r2Link);
		Assert.assertEquals(String.format("The event date should be %s but got %s", r1Date, r2Date),
				r1Date, r2Date);
		
	}
  
}
