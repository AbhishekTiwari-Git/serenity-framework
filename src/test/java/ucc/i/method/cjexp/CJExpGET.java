package ucc.i.method.cjexp;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class CJExpGET {
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.exp.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("cjExpAPI");
	public static String CJExp_url = webserviceEndpoint+serviceEndpoint+"/api";        
	TestUtils tUtil = new TestUtils();
	RestUtil CJEA = new RestUtil(CJExp_url);
	
	public ValidatableResponse getEvent(String endpoint) {
		
		RestUtil.setBaseURI(CJExp_url);

		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.get(endpoint)
				.then()
				.log().all();
	}
	
public ValidatableResponse getEvent(String endpoint, String param) {
		
		RestUtil.setBaseURI(CJExp_url);

		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.queryParam("view", param)
				.when()
				.log().all()
				.get(endpoint)
				.then()
				.log().all();
	}
	
	
	public String setEndpointEvents() {
		String endpoint = "/events";
		return endpoint;
	}
}
