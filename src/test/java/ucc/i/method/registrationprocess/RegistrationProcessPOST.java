package ucc.i.method.registrationprocess;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

import java.io.File;

public class RegistrationProcessPOST {
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
      .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
      .getProperty("registrationProcess.basePath");
	public static String RegistrationProcess_url = webserviceEndpoint + serviceEndpoint + "/api";
	TestUtils tUtil = new TestUtils();
	RestUtil RPA = new RestUtil(RegistrationProcess_url);
	String file_path = env_var.getProperty("json.body.path");
	
	
	public ValidatableResponse registerUser(String file_name, String endpoint) {
		String path = file_path + "/" + file_name;
        File file = new File(path);
    					
    	return	SerenityRest.rest()
    		.given()
    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
    		.when()
    		.body(file)
    		.log().all()
    		.post(RegistrationProcess_url + endpoint)
    		.then()
    		.log().all();
	}

	public ValidatableResponse registerUserToken(String file_name, String endpoint, String token) {
		String path = file_path + "/" + file_name;
        File file = new File(path);

    	return	SerenityRest.rest()
    		.given()
    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
			.header("akamai-auth-token", token)
    		.when()
    		.body(file)
    		.log().all()
    		.post(RegistrationProcess_url + endpoint)
    		.then()
    		.log().all();
	}
	
	public String setEndpointRegistration() {
		String endpoint = "/registration";
		return endpoint;
	}
	public String setEndpointRegistrations() {
		String endpoint = "/registrations";
		return endpoint;
	}
	
	
	
}
