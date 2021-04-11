package ucc.i.method.store;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

import java.util.HashMap;

import static org.hamcrest.Matchers.empty;

public class StoreGET {
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("api.exp.url");
	static String serviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("store.basePath");
	public static String StoreExp_url = webserviceEndpoint + serviceEndpoint + "/api";

	
	
	public ValidatableResponse getProductPriceWithParams(
			String endpoint, HashMap mapParams) {
		
		RestUtil.setBaseURI(StoreExp_url);

		return	SerenityRest.rest()
				.given()
				.queryParams(mapParams)
				.spec(ReuseableSpecifications.getGenericExpRequestSpec()).log().all()
				.when()
				.get(endpoint)
				.then()
				.log().all();
	}
	
	public ValidatableResponse getStore(String endpoint, String authToken) {

		RestUtil.setBaseURI(StoreExp_url);

		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.header("akamai-auth-token", authToken)
				.when()
				.log().all()
				.get(endpoint)
				.then()
				.log().all();
	}
	
	public ValidatableResponse getwithProductFamily(String endpoint, String authToken, String productFamily) {

		RestUtil.setBaseURI(StoreExp_url);

		return SerenityRest.rest()
				.given().spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.header("akamai-auth-token", authToken)
				.when()
				.queryParam("productFamily", productFamily)
				.log().all()
				.get(endpoint)
				.then()
				.log().all();
	}
	
	
	public ValidatableResponse VerifyEmptyInvoicewithProductFamily(String endpoint, String authToken, String productFamily) {

        RestUtil.setBaseURI(StoreExp_url);

        return SerenityRest.rest()
                .given().spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .header("akamai-auth-token", authToken)
                .when()
                .queryParam("productFamily", productFamily)
                .log().all()
                .get(endpoint)
                .then()
                .assertThat()
                .body("invoices", empty())
                .log().all();
    }
	
	
	
	public String getField(Response resp, String field) {

		JsonPath jsonPathEvaluator = resp.jsonPath();
		return jsonPathEvaluator.getString(field);
	}

	public String setEndpointCustomerCJ(String ucid) {
		String endpoint = "/customers/" + ucid + "/purchase-eligibility?productFamily=catalyst&#39;";
		return endpoint;
	}

	public String setEndpointCustomer(String ucid) {
		String endpoint = "/customers/" + ucid;
		return endpoint;
	}

	public String setEndpointPrice() {
		String endpoint = "/products/prices";
		return endpoint;
	}

	public String setEndpointCustUCIDinvoices(String ucid) {
		String endpoint = "/customers/" + ucid + "/invoices";
		return endpoint;
	}
	
	public String setEndpointProductsPrices() {
		String endpoint = "/products/prices";
		return endpoint;
	}
}
