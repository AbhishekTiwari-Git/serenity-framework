package ucc.i.method.literatumprocess;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

import java.io.File;

public class LiteratumProcessPOST {
    private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    private static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("api.base.url");
    private static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("literatumProcess.basePath");
    private static String literatumProcessURL = webserviceEndpoint + serviceEndpoint + "/api";
    private String filePath = envVar.getProperty("json.body.path");
//    RestUtil Literatum = new RestUtil(literatumProcessURL);

    public ValidatableResponse post(final String filename, final String endpoint) {
        String path = filePath + "/" + filename;
        File file = new File(path);
        RestUtil.setBaseURI(literatumProcessURL);

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

    public ValidatableResponse postWithHeader(final String filename, final String endpoint, final String source) {
        String path = filePath + "/" + filename;
        File file = new File(path);
        RestUtil.setBaseURI(literatumProcessURL);

        return SerenityRest
                .rest()
                .given()
                .header("source", source)
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .when()
                .body(file)
                .log().all()
                .post(endpoint)
                .then().log().all();
    }

    public String setEndpointIndentityPayMyBill(final String ucid) {
        String endpoint = "identity/" + ucid + "/paymybill";
        return endpoint;
    }

    public String setEndpointLicenses() {
        String endpoint = "licenses/";
        return endpoint;
    }
    
    public String setEndpointToLicenses() {
        String endpoint = "licenses";
        return endpoint;
    }

    public String setEndpointIdentityPerson() {
        String endpoint = "/identity/person";
        return endpoint;
    }
}
