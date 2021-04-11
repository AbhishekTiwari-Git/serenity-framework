package ucc.i.method.panelistprocess;




import java.net.URISyntaxException;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class PanelistProcessPATCH {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PanelistProcessPATCH.class);
	
	TestUtils tUtil = new TestUtils();
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String apiservice = EnvironmentSpecificConfiguration.from(env_var).getProperty("api.base.url");
	static String serviceEndpoint = EnvironmentSpecificConfiguration.from(env_var).getProperty("procpanelist.basePath");
	public static String panelist_url = apiservice + serviceEndpoint + "/api";
	String file_path = env_var.getProperty("json.body.path");

	RestUtil Panelist = new RestUtil(panelist_url);
	
	@SuppressWarnings("unchecked")
	public ValidatableResponse patchProcPanelist(String endpoint, String uk, String uv) throws URISyntaxException {

		JSONObject requestParams = new JSONObject();
		
		if(uk.equalsIgnoreCase("healthSystemAffiliation")){
			boolean bval=Boolean.parseBoolean(uv); 
			requestParams.put(uk, bval);
		}
		else {
		requestParams.put(uk, uv);
		}
		
		return SerenityRest.rest().
				given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.body(requestParams)
				.log().all()
				.when()
				.patch(endpoint)
				.then().log().all();

	}
	
	
	public void verifyUpdatedValues(Response resp, String ukk, String uvv) {
		
		
		if (ukk.equalsIgnoreCase("healthSystemAffiliation")) {
			JsonPath jsonPathEvaluator = resp.jsonPath();
			boolean updatedval = jsonPathEvaluator.getBoolean(ukk);
			boolean bvv = Boolean.parseBoolean(uvv);
			Assert.assertTrue(bvv == updatedval);
		}
		
		else {
		
		JsonPath jsonPathEvaluator = resp.jsonPath();
		String updatedval = jsonPathEvaluator.getString(ukk).trim();
		
		LOGGER.info(updatedval);
		LOGGER.info(uvv.trim());
		
		Assert.assertEquals(uvv.trim(), updatedval);	
		}
			
	}
	
	
	
	public String setalternateIdEndpoint(String alternateID) {
		String endpoint = "/panelists/" + alternateID;
		return endpoint;
	}

}
