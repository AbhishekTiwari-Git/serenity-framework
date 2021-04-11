package ucc.i.method.customerprocess;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

import java.io.File;

public class CustomerProcessPATCH {

    static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
    static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
    static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("customerProcess.basePath");
    public static String CustomerProcess_url = webserviceEndpoint + serviceEndpoint + "/api";
    TestUtils tUtil = new TestUtils();
    String file_path = env_var.getProperty("json.body.path");
    RestUtil CPA = new RestUtil(CustomerProcess_url);


    public ValidatableResponse patchUserByUcid(String endpoint, String token, String file_name) {

        String path = file_path+"/"+file_name;
        File file = new File(path);

        return	SerenityRest.rest()
                .given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .header("akamai-auth-token", token)
                .when()
                .log().all()
                .body(file)
                .log().all()
                .patch(CustomerProcess_url + endpoint)
                .then()
                .log().all();
    }
    
    public ValidatableResponse patchCustomer(String file_name, String token, String endPoint) {
    	
    	RestUtil.setBaseURI(CustomerProcess_url);
    	String path = file_path + "/" + file_name;
        File file = new File(path);
        return	SerenityRest.rest().given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .header("akamai-auth-token", token)
                .when()
                .log().all()
                .body(file)
                .log().all()
                .patch(endPoint)
                .then()
                .log().all();
	}

    public String setEndpointUserByUcid(String ucid) {
        String endpoint = "/users/" + ucid;
        return endpoint;
    }

    public String setEndpointCustomers(String ucid) {
        String endpoint = "/customers/" + ucid;
        return endpoint;
    }

    public String setEndpointAddress(String ucid) {
		String endpoint = "/customers/" + ucid + "/address";
        return endpoint;
	}
}
