package ucc.i.method.icv;

import java.net.URISyntaxException;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class ICVDELETE {
	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("icv.basePath");
	public static String icvUrl = webserviceEndpoint+serviceEndpoint+"/api";     
	TestUtils tUtil = new TestUtils();
	RestUtil ICV = new RestUtil(icvUrl);

	
	public ValidatableResponse deleteUser(String endpoint) throws URISyntaxException{
	

					
	return	SerenityRest.rest()
		.given()
		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
		.when()
		.log().all()
		.delete(endpoint)
		.then()
		.log().all();
				
	}
	
	
	 
	 
}
