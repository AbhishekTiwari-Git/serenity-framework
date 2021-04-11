package ucc.i.method.nejmliteratumsystem;

import java.io.File;
import java.net.URISyntaxException;

import org.json.simple.JSONObject;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

public class NEJMLiteratumSystemPATCH {

	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
	private static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("api.base.url");
	private static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("nejmLiteratumSystem.basePath");
	private String nejmLiteratumUrl = webserviceEndpoint+serviceEndpoint+"/api";
	private String file_path = envVar.getProperty("json.body.path");

	
	public ValidatableResponse patch(String file_name, String endpoint) throws URISyntaxException {
		
		String path = file_path + "/" + file_name;
		File file = new File(path);
		RestUtil.setBaseURI(nejmLiteratumUrl);
		
		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.body(file)
				.log().all()
				.patch(endpoint)
				.then()
				.log().all();
	}
	
	public ValidatableResponse patch_fields(String endpoint, String upKey, String upVal) throws URISyntaxException {
		
		JSONObject requestParams = new JSONObject();
		requestParams.put(upKey , upVal);

		
		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.body(requestParams)
				.log().all()
				.patch(endpoint)
				.then()
				.log().all();
	}
	
	public ValidatableResponse patchInstitutionLicense(String file, String endpoint) throws Exception {
		 String path = file_path + "/" + file;
         File filename = new File(path);
		 RestUtil.setBaseURI(nejmLiteratumUrl);		 
		 return SerenityRest.rest()
				.given()
		    	.spec(ReuseableSpecifications.getGenericExpRequestSpec())
		    	.when()
		    	.body(filename)
		    	.log().all()
		    	.patch(endpoint)
		    	.then().log().all();
	 }
	
	public ValidatableResponse patchPersonLicense(String file, String endpoint) throws Exception {
		 String path = file_path + "/" + file;
         File filename = new File(path);
		 RestUtil.setBaseURI(nejmLiteratumUrl);		 
		 return SerenityRest.rest()
				.given()
		    	.spec(ReuseableSpecifications.getGenericExpRequestSpec())
		    	.when()
		    	.body(filename)
		    	.log().all()
		    	.patch(endpoint)
		    	.then().log().all();
	 }

	public String setEndpontIdentitiesPersonUCID(String ucid) {
		
		String endpoint = "/identities/person/" + ucid;
		return endpoint;
	}
	
	public String setEndpointUpdateInid(String inid) {
		String endpoint = "/identities/institution/" + inid;
		return endpoint;
	}
	
	public String setEndpointLicenses() {
		String endpoint = "/licenses";
		return endpoint;
	}
}
