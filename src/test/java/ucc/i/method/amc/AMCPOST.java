package ucc.i.method.amc;

import java.io.File;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import org.json.simple.JSONObject;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class AMCPOST {
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("api.base.url");

	static String serviceSystemEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("amc.basePath");
	static String serviceProcessEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("amc.procPath");

	public static String AMC_system_url = webserviceEndpoint+serviceSystemEndpoint+"/api";
	public static String AMC_process_url = webserviceEndpoint+serviceProcessEndpoint+"/api";

	TestUtils tUtil = new TestUtils();
	
	String file_path = env_var.getProperty("json.body.path");
	RestUtil AMC = new RestUtil(AMC_system_url);

	public ValidatableResponse pushEventInAdobe(String eventTyp, String eml, String extId, String ctxCt, String endpoint) {
		
		JSONObject requestParams = new JSONObject();
		requestParams.put("eventType", eventTyp);
		requestParams.put("email", eml);
		requestParams.put("externalId", extId);
		requestParams.put("ctxContent", ctxCt);
    					
    	return	SerenityRest.rest()
    		.given()
    		.spec(ReuseableSpecifications.getGenericExpRequestSpec()).log().all()
    		.when()
    		.body(requestParams)
    		//.log().all()
    		.post(endpoint)
    		.then()
    		.log().all();
    	
	}
	
	
	
	public String setEndPoint() {
		
		String endpoint = "/event";
		return endpoint;
	}

	public ValidatableResponse postProcessEvent(String file_name) {

		File file = new File(file_path+"/"+file_name);
		String PATH = "/event";

		RestUtil.setBaseURI(AMC_process_url);

		return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.body(file)
				.post(PATH)
				.then()
				.log().all();

	}

}
