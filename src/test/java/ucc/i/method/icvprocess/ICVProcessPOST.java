package ucc.i.method.icvprocess;

import java.io.File;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class ICVProcessPOST {
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
      .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
      .getProperty("icvProcess.basePath");
	public static String IcvProcess_url = webserviceEndpoint + serviceEndpoint + "/api";
	TestUtils tUtil = new TestUtils();
	String file_path = env_var.getProperty("json.body.path");
	RestUtil ICVProcAPI = new RestUtil(IcvProcess_url);
	
	public ValidatableResponse registerUser(String endpoint, String filename) {
		
		String path = file_path + "/" + filename;
        File file = new File(path);
    					
    	return	SerenityRest.rest()
    		.given()
    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
    		.when()
    		.log().all()
    		.body(file)
    		.post(IcvProcess_url + endpoint)
    		.then()
    		.log().all();
	}
	
	public String setEndpointRegistration(int eventId) {
		String endpoint = String.format("/events/%d/users", eventId);
		return endpoint;
	}
}
