package ucc.i.method.nejmliteratumprocess;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.ValidatableResponseOptions;

import java.io.File;

import org.json.simple.JSONObject;

import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;
import ucc.utils.YamlUtils;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class NEJMLiteratumProcessPOST {

	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
	private String filePath = envVar.getProperty("json.body.path");
	private static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(envVar)
			 .getProperty("api.base.url");
	private static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(envVar)
			 .getProperty("nejmLiteratumProcess.basePath");
	private static String nejmLiteratumProcessURL = webserviceEndpoint + serviceEndpoint + "/api";
	TestUtils tUtils = new TestUtils();
	YamlUtils YamlUtil = new YamlUtils();
	
	public ValidatableResponse post(String file_name, String endpoint) {
		String path = filePath + "/" + file_name;
		File file = new File(path);

		RestUtil.setBaseURI(nejmLiteratumProcessURL);

		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.body(file)
				.post(endpoint)
				.then()
				.log().all();
	}
	
	public ValidatableResponse provideInstitutionalLicense(String endPoint, String inid) {
		JSONObject requestParams = new JSONObject();
		requestParams.put("inid",inid );
		requestParams.put("institutionType","INS");
		
		RestUtil.setBaseURI(nejmLiteratumProcessURL);

		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.body(requestParams)
				.post(endPoint)
				.then()
				.log().all();
	}
	
	public ValidatableResponse provideInstitutionalLicense_brocker_api(String endPoint, String inid, String prdct_code) {
		JSONObject requestParams = new JSONObject();
		requestParams.put("inid",inid );
		requestParams.put("institutionType","INS");
		requestParams.put("productCode",prdct_code);
		
		RestUtil.setBaseURI(nejmLiteratumProcessURL);

		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.body(requestParams)
				.post(endPoint)
				.then()
				.log().all();
	}
	
	public ValidatableResponse createPersonID(String endPoint, String ucid) {
		JSONObject requestParams = new JSONObject();
		requestParams.put("ucid",ucid );
		
		RestUtil.setBaseURI(nejmLiteratumProcessURL);

		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.body(requestParams)
				.post(endPoint)
				.then()
				.log().all();
	}

	public String setEndpontLicenses() {
		String endpoint = "/licenses";
		return endpoint;
	}

	public String setEndpontIdentityACSsubscription() {
		String endpoint = "/identity/acs-subscription";
		return endpoint;
	}

	public String setEndpointIdentityPerson() {
		String endpoint = "/identity/person";
		return endpoint;
	}

	public String setEndpointinstitutionaladminlicense(String ucid) {
		String endpoint = "identity/person/" + ucid + "/institutional-admin-activate";
		return endpoint;
	}	
}
