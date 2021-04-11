package ucc.i.method.customerprocess;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import org.json.simple.JSONObject;

import ucc.utils.ConnectionFactory;
import ucc.utils.DbUtils;
import ucc.utils.ResponseCode;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

import java.io.File;


public class CustomerProcessPOST {
    private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    private static String webserviceEndpoint = EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("api.base.url");
    private static String serviceEndpoint = EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("customerProcess.basePath");
    private static String customerProcessUrl = webserviceEndpoint + serviceEndpoint + "/api";
    private String filePath = envVar.getProperty("json.body.path");
    private static JSONObject requestParams = new JSONObject();
    private final DbUtils dbUtils = new DbUtils();

    public ValidatableResponse validateToken(final String uuid, final String token) {

        String endpoint = "/access-token/validate";

        JSONObject requestParams = new JSONObject();
        requestParams.put("uuid", uuid);
        requestParams.put("token", token);

        RestUtil.setBaseURI(customerProcessUrl);

        return SerenityRest.rest()
                .given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .when()
                .body(requestParams)
                .log().all()
                .post(endpoint)
                .then()
                .log().all();
    }

    public ValidatableResponse postTickets(final String fileName) {

        RestUtil.setBaseURI(customerProcessUrl);

        String endpoint = "/tickets";

        String path = filePath + "/" + fileName;
        File file = new File(path);

        return SerenityRest.rest()
                .given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .when()
                .log().all()
                .body(file)
                .log().all()
                .post(endpoint)
                .then()
                .log().all();
    }

    public ValidatableResponse userRegister(final String fileName) {

        RestUtil.setBaseURI(customerProcessUrl);

        String endpoint = "/registration/register";

        String path = filePath + "/" + fileName;
        File file = new File(path);

        return SerenityRest.rest()
                .given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .when()
                .log().all()
                .body(file)
                .log().all()
                .post(endpoint)
                .then()
                .log().all();
    }


    public ValidatableResponse customers(final String filename) {
        RestUtil.setBaseURI(customerProcessUrl);
        String endpoint = "/customers";
        String path = filePath + "/" + filename;
        File file = new File(path);

        return SerenityRest.rest()
                .given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .when()
                .log().all()
                .body(file)
                .post(endpoint)
                .then()
                .log().all();
    }

    public ValidatableResponse customerPayments(
            final String filename, final String ucid, final String token) {
        RestUtil.setBaseURI(customerProcessUrl);
        String endpoint = "/customers/" + ucid + "/payments";
        String path = filePath + "/" + filename;
        File file = new File(path);

        return SerenityRest.rest()
                .given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .header("akamai-auth-token", token)
                .when()
                .log().all()
                .body(file)
                .post(endpoint)
                .then()
                .log().all();
    }
    
    public ValidatableResponse institutionalActivate(String endpoint,String token, String subscrptnID) {
    	
    	
        RestUtil.setBaseURI(customerProcessUrl);
		requestParams.put("subscriptionId",subscrptnID );
		requestParams.put("institutionType","INS");

        return SerenityRest.rest()
                .given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .header("akamai-auth-token", token)
                .when()
                .log().all()
                .body(requestParams)
                .post(endpoint)
                .then()
                .assertThat().statusCode(ResponseCode.CREATED)
                .log().all();
    }
    
    
    public String setEndpointInstitutionalActivate(String ucid) {
		String endpoint = "/customers/" + ucid + "/institutional-activate";
		return endpoint;
    }
    
    public ValidatableResponse postToken(String file_name, String endpoint, String token) {
		RestUtil.setBaseURI(customerProcessUrl);
    	
    	String path = filePath + "/" + file_name;
		File file = new File(path);

		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.header("akamai-auth-token", token)
				.when()
				.body(file)
				.post(endpoint)
				.then()
				.log().all();
	}
    
    
    
    public String setEndpoint_activate(String ucid) {
		String endpoint = "customers/" + ucid +"/activate";
		return endpoint;
	}
}
