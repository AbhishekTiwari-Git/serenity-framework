package ucc.i.method.panelistprocess;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class PanelistProcessGET {

	TestUtils tUtil = new TestUtils();
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String apiservice = EnvironmentSpecificConfiguration.from(env_var).getProperty("api.base.url");
	static String serviceEndpoint = EnvironmentSpecificConfiguration.from(env_var).getProperty("procpanelist.basePath");
	public static String panelist_url = apiservice + serviceEndpoint + "/api";
	String file_path = env_var.getProperty("json.body.path");

	RestUtil Panelist = new RestUtil(panelist_url);

	@Step
	public ValidatableResponse getProcPanelist(String endpoint) throws URISyntaxException {

		return SerenityRest.rest().
				given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.log().all()
				.when()
				.get(endpoint)
				.then().log().all();

	}

	
	public Map<String, String> store_PanelistValues(Response resp) {

		JsonPath jsonPathEvaluator = resp.jsonPath();
		String alternateId = jsonPathEvaluator.getString("alternateId");
		String email = jsonPathEvaluator.getString("email");

		HashMap<String, String> kinesysValues = new HashMap<String, String>();
		kinesysValues.put("alternateId", alternateId.trim());
		kinesysValues.put("email", email.trim());

		System.out.println("Stored Values " + kinesysValues);

		return kinesysValues;

	}

	
	public void verifyPanelistValues(Response resp, Map<String, String> kmap) {

		List<String> vals = new ArrayList<String>();
		Map<String, String> retrvValues = new HashMap<String, String>();
		retrvValues = kmap;
		
		JsonPath jsonPathEvaluator = resp.jsonPath();
		String alternateID = jsonPathEvaluator.getString("alternateId");
		String email = jsonPathEvaluator.getString("email");
		
		vals.add(alternateID);
		vals.add(email);
		
		System.out.println("retrivedStored Values " + kmap);
		System.out.println("response Values " + vals);

		assertThat(vals, containsInAnyOrder(retrvValues.get("alternateId"), retrvValues.get("email")));

	}

	@Step
	public String setEndpoint(String alternateID) {
		String endpoint = "/panelists/" + alternateID;
		return endpoint;
	}

}
