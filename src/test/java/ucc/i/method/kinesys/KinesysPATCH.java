package ucc.i.method.kinesys;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.junit.Assert;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.ResponseCode;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class KinesysPATCH {

	@JsonIgnoreProperties(ignoreUnknown = true)

	TestUtils tUtil = new TestUtils();
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("api.base.url");
	static String serviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("kinesys.basePath");
	public static String kinesys_url = webserviceEndpoint + serviceEndpoint + "/api";
	String file_path = env_var.getProperty("json.body.path");
	static String tuV;
	static String email_value;
	private final String KINESYS_PATCH_FILE = "Kinesys_Patch.json";
	RestUtil kinesys = new RestUtil(kinesys_url);

	public void verify_PATCHmsg(Response resp, String msg, int code) {

		@SuppressWarnings("rawtypes")
		ResponseBody body = resp.getBody();
		String bodyStringValue = body.asString();
		Assert.assertTrue("The response should contain message: " + msg + " but found " + bodyStringValue,
				bodyStringValue.contains(msg));
		Assert.assertEquals(resp.getStatusCode(), code);
		System.out.println("Successfully matched updated panelist with email...!!");
	}

	public ValidatableResponse updatePanelistAllFields(String file_name, String endpoint) throws URISyntaxException {
		
		String path = file_path + "/" + file_name;
		File file = new File(path);

		
		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.body(file)
				.log().all()
				.patch(endpoint)
				.then()
				.log().all();
		
	}

	@SuppressWarnings("unchecked")
	public ValidatableResponse patchSysPanelist(String endpoint, String uK, String uV) throws URISyntaxException {

		
		JSONObject requestParams = new JSONObject();
		requestParams.put(uK, uV);

		
		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.body(requestParams)
				.log().all()
				.when()
				.patch(endpoint)
				.then()
				.log().all();
		
	}

	@SuppressWarnings("unchecked")
	public ValidatableResponse patchSysPanelist(String endpoint, String uK, Integer uV) throws URISyntaxException {

		
		JSONObject requestParams = new JSONObject();
		requestParams.put(uK, uV);

		
		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.body(requestParams)
				.log().all()
				.when()
				.patch(endpoint)
				.then()
				.log().all();
		
	}

	public void verifyUpdatedValues(Response resp, String ukk, String uvv) {

		JsonPath jsonPathEvaluator = resp.jsonPath();
		String updatedval = jsonPathEvaluator.getString(ukk).trim();

		Assert.assertEquals(uvv.trim(), updatedval);
		
	}

	public ValidatableResponse updatePanelistEmailOnly(String email, String endpoint) throws URISyntaxException {

		JSONObject requestParams = new JSONObject();
		

		requestParams.put("email", email);

		
		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.body(requestParams)
				.log().all()
				.patch(endpoint)
				.then()
				.log().all();
		
	}

	public ValidatableResponse updatePanelistSomeFields(Integer audienceSegment, Integer clinicalDesignation,
			Integer hsAffiliation, String endpoint) throws URISyntaxException {

		JSONObject requestParams = new JSONObject();

		
		requestParams.put("audienceSegment", audienceSegment);
		requestParams.put("clinicalDesignation", clinicalDesignation);
		requestParams.put("hsAffiliation", hsAffiliation);

		
		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.body(requestParams)
				.log().all()
				.patch(endpoint)
				.then()
				.log().all();
		
	}

	public ValidatableResponse updateEmailThatIsAlreadyExist(String email, String endpoint) throws URISyntaxException {


		JSONObject requestParams = new JSONObject();

		requestParams.put("email", email);

		
		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.body(requestParams)
				.log().all()
				.patch(endpoint)
				.then()
				.log().all();
		
	}

	public String getID(Response resp) {
		JsonPath jsonPathEvaluator = resp.jsonPath();
		String Id = jsonPathEvaluator.getString("alternateId");
		return Id;
	}

	public void verify_panelist_SomeDataPoints(String audienceSegment, String clinicalDesignation, String hsAffiliation,
			Response resp) {
		String expectedAudienceSeg = audienceSegment;
		String expectedClinicalDes = clinicalDesignation;
		String expectedHSAffiliation = hsAffiliation;

		JsonPath jsonpathEvaluator = resp.jsonPath();

		String actualAudienceSeg = jsonpathEvaluator.getString("audienceSegment");
		String actualClinicalDes = jsonpathEvaluator.getString("clinicalDesignation");
		String actualHSAffiliation = jsonpathEvaluator.getString("hsAffiliation");

		Assert.assertEquals(actualAudienceSeg, expectedAudienceSeg);
		Assert.assertEquals(actualClinicalDes, expectedClinicalDes);
		Assert.assertEquals(actualHSAffiliation, expectedHSAffiliation);
	}

	public void verify_panelist_AllDataPoints(Response resp) throws IOException {
		String path = file_path + "/" + KINESYS_PATCH_FILE;
		File file = new File(path);
		String exampleRequest = FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);

		ObjectMapper om = new ObjectMapper();
		try {
			Map<String, Object> m1 = (Map<String, Object>) (om.readValue(exampleRequest, Map.class));
			Map<String, Object> m2 = (Map<String, Object>) (om.readValue(resp.getBody().asString(), Map.class));
			if (m2.containsKey("alternateId")) {
				m2.remove("alternateId");
			}
			if (m2.containsKey("id")) {
				m2.remove("id");
			}
			if (m2.containsKey("hsAffiliation")) {
				m2.remove("hsAffiliation");
			}
			if (m1.containsKey("hsAffiliation")) {
				m1.remove("hsAffiliation");
			}

			assertTrue(m1.equals(m2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verify_updatedEmail(Response resp, String createdCustomersEmail, String patchedCustomersEmail) {
		assertEquals(resp.getStatusCode(), ResponseCode.OK);
		assertEquals(patchedCustomersEmail, resp.jsonPath().get("email"));
	}

	public String setEndpoint(String alternateID) {
		String endpoint = "/panelists/" + alternateID;
		return endpoint;
	}
}