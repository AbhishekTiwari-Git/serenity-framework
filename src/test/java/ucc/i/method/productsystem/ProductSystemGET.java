package ucc.i.method.productsystem;

import java.util.HashMap;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.i.method.referenceSystem.ReferenceSystemHelper;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

public class ProductSystemGET {


	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("api.base.url");
	static String serviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("productSystem.basePath");
	public static String ProductSystem_url = webserviceEndpoint+serviceEndpoint+"/api";
	String file_path = env_var.getProperty("json.body.path");
	
	static String expJson = null;

	
	
	public ValidatableResponse getwithParams(String endpoint, HashMap mapParams) {
		
		RestUtil.setBaseURI(ProductSystem_url);

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
		
		RestUtil.setBaseURI(ProductSystem_url);

		return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.get(endpoint)
				.then()
				.log().all();
	}
	
	public ValidatableResponse getPrice(
            String endpoint, String promo, String products,
            String professionalCategory, String country, String customerNumber
    ) {
        RestUtil.setBaseURI(ProductSystem_url);

        return	SerenityRest.rest()
                .given()
                .queryParam("promo", promo)
                .queryParam("products", products)
                .queryParam("professionalCategory", professionalCategory)
                .queryParam("country", country)
                .queryParam("customerNumber", customerNumber)
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .when()
                .log().all()
                .get(endpoint)
                .then()
                .log().all();
    }
	
	public String setEndpointToPrices() {
		  String endpoint = "/prices";
		  return endpoint;
	}
	
	public String setEndpointToDetails() {
		  String endpoint = "/details";
		  return endpoint;
	}
	
	public String setEndpointToProducts() {
		  String endpoint = "/products";
		  return endpoint;
	}
}
