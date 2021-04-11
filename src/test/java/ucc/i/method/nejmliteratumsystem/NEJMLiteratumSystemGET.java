package ucc.i.method.nejmliteratumsystem;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

public class NEJMLiteratumSystemGET {
	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
	private static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("api.base.url");
	private static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("nejmLiteratumSystem.basePath");
	private static String nejmLiteratumSystemURL = webserviceEndpoint + serviceEndpoint + "/api";

	
	public ValidatableResponse get(String endpoint) {

		RestUtil.setBaseURI(nejmLiteratumSystemURL);

		return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.get(endpoint)
				.then()
				.log().all();
	}
	
	
	public String setEndpontIdentitiesUCID(String ucid) {
        String endpoint = "/identities/"+ ucid;
        return endpoint;
    }
	
	public String setEndpontIdentitiesUCIDLicences(String ucid) {
        String endpoint = "/identities/" + ucid + "/licenses";
        return endpoint;
    }
	
	public String setEndpointPerson(String ucid) {
        String endpoint = "/identities/person/" + ucid;
        return endpoint;
    }
	
	public String setEndpointIdentity(String ucid) {
        String endpoint = "/identities/" + ucid;
        return endpoint;
    }
	
	public String setEndpointInstitutionIdentities(String inid) {
        String endpoint = "/identities/institution/" + inid;
        return endpoint;
    }
	
	public String setEndpointInstitutionLicense(String inid) {
        String endpoint = "/identities/institution/" + inid+"/licenses";
        return endpoint;
    }
	
	public String setEndpointPersonLicences(String ucid) {
        String endpoint = "/identities/person/" + ucid + "/licenses";
        return endpoint;
    }
	
}
