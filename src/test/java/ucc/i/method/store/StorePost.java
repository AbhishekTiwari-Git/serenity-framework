package ucc.i.method.store;

import java.io.File;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class StorePost {
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("api.exp.url");
	static String serviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("store.basePath");
	public static String StoreExp_url = webserviceEndpoint + serviceEndpoint + "/api";
	TestUtils tUtil = new TestUtils();
	String file_path = env_var.getProperty("json.body.path");

	public ValidatableResponse postStore(String endpoint, String filename) {

		String path = file_path + "/" + filename;
		File file = new File(path);

		RestUtil.setBaseURI(StoreExp_url);

		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.body(file)
				.log().all()
				.post(endpoint)
				.then()
				.log().all();
	}
	
	public ValidatableResponse postStore_withToken(String endpoint, String filename, String token) {

		String path = file_path + "/" + filename;
		File file = new File(path);

		RestUtil.setBaseURI(StoreExp_url);

		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec()).log().all()
				.header("akamai-auth-token", token)
				.when()
				.body(file)
				.post(endpoint)
				.then()
				.log().all();
	}

	public String setEndpointOrder(String ucid) {
		String endpoint = "/customers/" + ucid + "/orders";
		return endpoint;
	}

	public String setEndpointPayments(String ucid) {
		String endpoint = "/customers/" + ucid + "/payments";
		return endpoint;
	}

	public String setEndpointCustomer() {
		String endpoint = "/customers";
		return endpoint;
	}
}
