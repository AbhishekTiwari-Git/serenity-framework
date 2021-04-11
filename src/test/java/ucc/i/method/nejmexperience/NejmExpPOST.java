package ucc.i.method.nejmexperience;

import io.restassured.response.ValidatableResponse;

import java.io.File;

import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class NejmExpPOST {
	
	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    private static String webserviceEndpoint = EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("api.exp.url");
    private static String serviceEndpoint = EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("nejmExp.basePath");
    private static String nejmExpUrl = webserviceEndpoint + serviceEndpoint + "/api";
    private String filePath = envVar.getProperty("json.body.path");

    
    
    public ValidatableResponse postToken(String file_name, String endpoint, String token) {
		String path = filePath + "/" + file_name;
		File file = new File(path);

		RestUtil.setBaseURI(nejmExpUrl);

		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.header("akamai-auth-token", token)
				.when()
				.body(file)
				.post(endpoint)
				.then()
				.log().all();
	}

    public String setEndpointSavedItemsSearches(String ucid) {
		String endpoint = "customers/" + ucid + "/saved-items/searches";
		return endpoint;
	}
}
