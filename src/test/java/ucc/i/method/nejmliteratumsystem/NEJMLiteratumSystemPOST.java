package ucc.i.method.nejmliteratumsystem;

import java.io.File;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

public class NEJMLiteratumSystemPOST {
	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
	private String filePath = envVar.getProperty("json.body.path");
	private static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("api.base.url");
	private static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("nejmLiteratumSystem.basePath");
	private static String nejmLiteratumSystemURL = webserviceEndpoint + serviceEndpoint + "/api";

	
	
	public ValidatableResponse post(String file_name, String endpoint) {
		String path = filePath + "/" + file_name;
		File file = new File(path);

		RestUtil.setBaseURI(nejmLiteratumSystemURL);

		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.body(file)
				.post(endpoint)
				.then()
				.log().all();
	}
	
	public ValidatableResponse postInstitutionLicense(String file, String endpoint) throws Exception {
		 String path = filePath + "/" + file;
		 File filename = new File(path);
		 RestUtil.setBaseURI(nejmLiteratumSystemURL);		 
		 return SerenityRest.rest()
				.given()
		    	.spec(ReuseableSpecifications.getGenericExpRequestSpec())
		    	.when()
		    	.body(filename)
		    	.log().all()
		    	.post(endpoint)
		    	.then().log().all();
	 }
	
	public ValidatableResponse postPersonLicense(String file, String endpoint) throws Exception {
		 String path = filePath + "/" + file;
		 File filename = new File(path);
		 RestUtil.setBaseURI(nejmLiteratumSystemURL);		 
		 return SerenityRest.rest()
				.given()
		    	.spec(ReuseableSpecifications.getGenericExpRequestSpec())
		    	.when()
		    	.body(filename)
		    	.log().all()
		    	.post(endpoint)
		    	.then().log().all();
	 }
	
	public String setEndpontIdentitiesPerson() {
		String endpoint = "/identities/person";
		return endpoint;
	}
	
	public String setEndpontIdentitiesInstitution() {
		String endpoint = "/identities/institution";
		return endpoint;
	}
	
	public String setEndpointLicenses() {
		 String endpoint = "/licenses";
		 return endpoint;
	 }
}
