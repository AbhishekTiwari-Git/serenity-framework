package ucc.i.method.productprocess;

import java.util.HashMap;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

public class ProductProcessGET {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("api.base.url");
	static String serviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("productProcess.basePath");
	public static String ProductProcess_url = webserviceEndpoint+serviceEndpoint+"/api";
	String file_path = env_var.getProperty("json.body.path");
	
	
	
	public ValidatableResponse getwithParams(String endpoint, HashMap mapParams) {
		
		RestUtil.setBaseURI(ProductProcess_url);

		return	SerenityRest.rest()
				.given()
				.queryParams(mapParams)				
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.get(endpoint)
				.then()
				.log().all();
	}
	
	public ValidatableResponse get(String endpoint) {
		
		RestUtil.setBaseURI(ProductProcess_url);

		return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.get(endpoint)
				.then()
				.log().all();
	}

	public ValidatableResponse getProductPriceWithParams(String endpoint, HashMap mapParams, String authToken) {
		
		RestUtil.setBaseURI(ProductProcess_url);

		return	SerenityRest.rest()
				.given()
				.queryParams(mapParams)
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.header("akamai-auth-token", authToken)
				.log().all()
				.when()
				.get(endpoint)
				.then()
				.log().all();
	}
	
	public ValidatableResponse getProductPriceWithParams(String endpoint, HashMap mapParams) {
		
		RestUtil.setBaseURI(ProductProcess_url);

		return	SerenityRest.rest()
				.given()
				.queryParams(mapParams)
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.log().all()
				.when()
				.get(endpoint)
				.then()
				.log().all();
	}
	
	public String setEndpointToDetails() {
		  String endpoint = "/details";
		  return endpoint;
	}
	
	public String setEndpointToPrices() {
		  String endpoint = "/prices";
		  return endpoint;
	}
	
	public String setEndpointToProducts() {
		  String endpoint = "/products";
		  return endpoint;
	}
}
