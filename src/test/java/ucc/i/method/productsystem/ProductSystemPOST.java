package ucc.i.method.productsystem;

import java.io.File;

import io.restassured.response.ValidatableResponse;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class ProductSystemPOST {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("api.base.url");
	static String serviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("productSystem.basePath");
	public static String ProductSystem_url = webserviceEndpoint+serviceEndpoint+"/api";
	String file_path = env_var.getProperty("json.body.path");
	
	
	
	public ValidatableResponse post(String file_name, String endpoint) {
		String path = file_path + "/" + file_name;
		File file = new File(path);

		RestUtil.setBaseURI(ProductSystem_url);

		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.body(file)
				.post(endpoint)
				.then()
				.log().all();
	}
	
	
	public String setEndpointToPrices() {
		  String endpoint = "/prices";
		  return endpoint;
	}
}
