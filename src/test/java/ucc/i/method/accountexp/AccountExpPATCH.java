package ucc.i.method.accountexp;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.response.ValidatableResponseOptions;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import org.json.simple.JSONObject;

import ucc.utils.ResponseCode;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

import java.io.File;
import java.net.URISyntaxException;

public class AccountExpPATCH {
    static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
    static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.exp.url");
    static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("accountEXpAPI");
    public static String AccountExp_url = webserviceEndpoint+serviceEndpoint+"/api";
    RestUtil AEA = new RestUtil(AccountExp_url);
    String file_path = env_var.getProperty("json.body.path");

    
    
    public ValidatableResponse patchUserWithJsonFile(String file_name, String endpoint, String token) 
    		throws URISyntaxException{
    	
    	RestUtil.setBaseURI(AccountExp_url);
    	
    	String path = file_path + "/" + file_name;
        File file = new File(path);

        return	SerenityRest.rest()
        		.given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .header("akamai-auth-token", token)
                .when()
                .log().all()
                .body(file)
                .log().all()
                .patch(endpoint)
                .then()
                .log().all();	
    }
    
    public ValidatableResponse updateUser(String endpoint, String field, String fieldValue, String authToken) throws URISyntaxException {

    	RestUtil.setBaseURI(AccountExp_url);
    	
        JSONObject requestParams = new JSONObject();
        requestParams.put(field, fieldValue);

        return	SerenityRest.rest().given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .header("akamai-auth-token", authToken)
                .when()
                .log().all()
                .body(requestParams)
                .log().all()
                .patch(endpoint)
                .then()
                .log().all();

    }
    
    public ValidatableResponse patchCustomer(String file_name,String token, String endPoint) {
    	
    	RestUtil.setBaseURI(AccountExp_url);
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

    public String setEndpointUpdateUserByUcid(String ucid) {
        String endpoint = "/users/" + ucid;
        return endpoint;
    }
    
    public String setEndpointUpdatecustomersByUcid(String ucid) {
        String endpoint = "/customers/" + ucid;
        return endpoint;
    }

	public String setEndpointAddress(String ucid) {
		String endpoint = "/customers/" + ucid + "/address";
        return endpoint;
	}

    public String setEndpointUpdatePaymentDetails(String ucid, String subscriptionId) {
        String endpoint = "/customers/" + ucid + "/subscriptions/" + subscriptionId + "/payment-details";
        return endpoint;
    }

}
