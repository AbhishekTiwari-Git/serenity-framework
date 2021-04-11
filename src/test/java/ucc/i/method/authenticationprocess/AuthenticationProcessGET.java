package ucc.i.method.authenticationprocess;

import io.restassured.response.ValidatableResponse;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class AuthenticationProcessGET {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("api.base.url");
	static String serviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("authenticationProcess.basePath");
	public static String authenticationProc_url = webserviceEndpoint + serviceEndpoint + "/api";
    private static String filePath = env_var.getProperty("json.body.path");
	
	
	
	public ValidatableResponse get(String endpoint) {

		RestUtil.setBaseURI(authenticationProc_url);

		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.get(endpoint)
				.then()
				.log().all();
	}
	
	public String setEndpointToAccessTokens(String ucid) {
		String endpoint = "/access-tokens/" + ucid;
		return endpoint;
	}
}
