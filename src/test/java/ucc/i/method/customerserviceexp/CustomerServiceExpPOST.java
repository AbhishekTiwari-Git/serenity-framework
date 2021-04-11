package ucc.i.method.customerserviceexp;

import io.restassured.response.ValidatableResponse;

import java.io.File;

import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class CustomerServiceExpPOST {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("api.exp.url");
	static String serviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("customerserviceExpApi");
	public static String customerserviceExpApi_url = webserviceEndpoint + serviceEndpoint + "/api";
	String file_path = env_var.getProperty("json.body.path");

	
	
	public ValidatableResponse post(String file_name, String endpoint) {
		String path = file_path + "/" + file_name;
		File file = new File(path);

		RestUtil.setBaseURI(customerserviceExpApi_url);

		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.body(file)
				.post(endpoint)
				.then()
				.log().all();
	}
	
	public String setEndpointTickets() {
		String endpoint = "tickets";
		return endpoint;
	}
}
