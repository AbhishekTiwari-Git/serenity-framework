package ucc.i.method.acsprocess;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

import java.io.File;

public class ACSProcessPOST {
    static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
    static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
    static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("acsProcess.basePath");
    public static String AcsProcess_url = webserviceEndpoint + serviceEndpoint + "/api";
    TestUtils tUtil = new TestUtils();
    String file_path = env_var.getProperty("json.body.path");
    RestUtil ICVProcAPI = new RestUtil(AcsProcess_url);

    public ValidatableResponse customers(String filename) {

        String endpoint = "/customers";
        String path = file_path + "/" + filename;
        File file = new File(path);

        return	SerenityRest.rest()
                .given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .when()
                .log().all()
                .body(file)
                .post(AcsProcess_url + endpoint)
                .then()
                .log().all();
    }
    
    public ValidatableResponse post(String filename, String endpoint) {

        String path = file_path + "/" + filename;
        File file = new File(path);

        return	SerenityRest.rest()
                .given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .when()
                .log().all()
                .body(file)
                .post(AcsProcess_url + endpoint)
                .then()
                .log().all();
    }
    
    public String setEndpoint() {
    	String endpoint = "/customers";
		return endpoint;
    }

    public String setEndpointCustomerPayments(String ucid) {
        String endpoint = "/customers/" + ucid + "/payments";
        return endpoint;
    }
    
    public String setEndpointcustomerNumberActivate(String customerNumber) {
        String endpoint = "/customers/" + customerNumber + "/activate";
        return endpoint;
    }
}
