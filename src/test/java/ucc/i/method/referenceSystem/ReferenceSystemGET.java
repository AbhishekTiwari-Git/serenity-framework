package ucc.i.method.referenceSystem;

import java.net.URISyntaxException;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.ReuseableSpecifications;

public class ReferenceSystemGET {
	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("api.base.url");
	static String serviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("reference.basePath");
	
	public static String ReferenceSystem_url = webserviceEndpoint+serviceEndpoint+"/api";
	
	ReferenceSystemHelper referenceSystemHelper = new ReferenceSystemHelper();
	
	String file_path = env_var.getProperty("json.body.path");
	static String expJson = null;
	

	public ValidatableResponse getFields(String fields_fieldName) throws URISyntaxException{
		
		String endpt;
		
		if (fields_fieldName.equals("fields")) {
			endpt = ReferenceSystem_url + "/fields";
		} else {
			endpt = ReferenceSystem_url + "/fields/" + fields_fieldName;
		}
		
		
		return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.get(endpt)
				.then()
				.log().all();
				
	}
	
	public ValidatableResponse get(String endpoint) throws URISyntaxException{
		
		endpoint = ReferenceSystem_url + endpoint;

		return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.get(endpoint)
				.then()
				.log().all();
	}
	
	public String setEndpointTo_fieldsPrimarySpecialties () {
		String endpoint = "/fields/primary-specialities";
		return endpoint;
	}
	
	public String setEndpointTo_fieldsCanadaProvinces () {
		String endpoint = "/fields/canada/provinces";
		return endpoint;
	}
	
	public String setEndpointTo_fields_institutionalType () {
		String endpoint = "/fields/institutionalType";
		return endpoint;
	}

	public String setEndpointTo_invalidField () {
		String endpoint = "/fields/invalid";
		return endpoint;
	}
	
}
