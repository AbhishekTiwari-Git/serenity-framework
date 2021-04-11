package ucc.i.method.catalystLiteratum;

import java.io.File;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

public class CatalystLiteratumSystemPOST {

	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    private static String webserviceEndpoint = EnvironmentSpecificConfiguration
            .from(envVar).getProperty("api.base.url");
    private static String serviceEndpoint = EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("literatum.basePath");
    private static String catalystLiteratumSystemUrl = webserviceEndpoint + serviceEndpoint + "/api";
    private static String filePath = envVar.getProperty("json.body.path");
    
    public ValidatableResponse post(final String filename, final String endpoint) {
        String path = filePath + "/" + filename;
        File file = new File(path);
        RestUtil.setBaseURI(catalystLiteratumSystemUrl);
        return SerenityRest
                .rest()
                .given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .when()
                .body(file)
                .log().all()
                .post(endpoint)
                .then().log().all();
    }
    
    public String setEndpontIdentitiesInstitution() {
		String endpoint = "/identities/institution";
		return endpoint;
	}
}
