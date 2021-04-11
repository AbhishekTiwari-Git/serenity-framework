package ucc.i.method.literatumprocess;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.json.simple.JSONObject;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

import java.io.File;

public class CatalystliteratumprocessPOST {

    private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    private static String webserviceEndpoint = EnvironmentSpecificConfiguration
            .from(envVar).getProperty("api.base.url");
    private static String serviceEndpoint = EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("literatumCatalystProcess.basePath");
    private static String catalystLiteratumProcessUrl = webserviceEndpoint + serviceEndpoint + "/api";
    private static String filePath = envVar.getProperty("json.body.path");


    public ValidatableResponse post(final String filename, final String endpoint) {
        String path = filePath + "/" + filename;
        File file = new File(path);
        RestUtil.setBaseURI(catalystLiteratumProcessUrl);
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

    public ValidatableResponse postPerson(final String endpoint, final String ucid) {

        JSONObject file = new JSONObject();
        file.put("ucid", ucid);

        RestUtil.setBaseURI(catalystLiteratumProcessUrl);
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
    
    public ValidatableResponse provideInstitutionalLicense(String endPoint, String inid) {
		JSONObject requestParams = new JSONObject();
		requestParams.put("inid",inid );		
		RestUtil.setBaseURI(catalystLiteratumProcessUrl);

		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.body(requestParams)
				.post(endPoint)
				.then()
				.log().all();
	}
    
    public ValidatableResponse createPersonID(String endPoint, String ucid) {
		JSONObject requestParams = new JSONObject();
		requestParams.put("ucid",ucid );
		
		RestUtil.setBaseURI(catalystLiteratumProcessUrl);

		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.body(requestParams)
				.post(endPoint)
				.then()
				.log().all();
	}

    public String setEndpointidentityAcsSubscription() {
        String endpoint = "identity/acs-subscription";
        return endpoint;
    }

    public String setEndpointIndentityPerson() {
        String endpoint = "identity/person";
        return endpoint;
    }

    public String setEndpointIndentityPayMyBill(final String ucid) {
        String endpoint = "identity/" + ucid + "/paymybill";
        return endpoint;
    }

    public String setEndpointLicenses() {
        String endpoint = "licenses";
        return endpoint;
    }
	
	public String setEndpointInstitutionalAdminLicense(String ucid) {
		String endpoint = "identity/person/" + ucid + "/institutional-admin-activate";
		return endpoint;
	}
}
