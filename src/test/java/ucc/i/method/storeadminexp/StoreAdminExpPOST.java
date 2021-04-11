package ucc.i.method.storeadminexp;

import io.restassured.response.ValidatableResponse;

import java.io.File;
import java.net.URISyntaxException;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class StoreAdminExpPOST {
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("api.exp.url");
	static String serviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("sAdmin.basePath");
	public static String storeAdminExp_url = webserviceEndpoint + serviceEndpoint + "/api";
	TestUtils tUtil = new TestUtils();
	String file_path = env_var.getProperty("json.body.path");
	
	
	
	public ValidatableResponse postStore(String endpoint, String filename) throws URISyntaxException{

		String path = file_path + "/" + filename;
		File file = new File(path);

		RestUtil.setBaseURI(storeAdminExp_url);

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
	
	
	
	public String setEndpointToPrices() {
		String endpoint = "/prices";
		return endpoint;
	}
}
