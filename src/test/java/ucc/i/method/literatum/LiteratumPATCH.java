package ucc.i.method.literatum;

import java.io.File;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import org.json.simple.JSONObject;
import org.junit.Assert;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class LiteratumPATCH {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("literatum.basePath");
	public static String literatum_url = webserviceEndpoint+serviceEndpoint+"/api";    
	TestUtils tUtil = new TestUtils();
	String file_path = env_var.getProperty("json.body.path");
	RestUtil Literatum = new RestUtil(literatum_url);
	
	public String setEndpointUpdateIdentity(String ucid) {
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
	
	public ValidatableResponse patch(String filename, String endpoint) {
		
		String path = file_path + "/" + filename;
        File file = new File(path);
        
        return SerenityRest.rest()
        		.given()
        		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
        		.when()
        		.body(file)
        		.log().all()
        		.patch(endpoint)
        		.then()
        		.log().all();
	}

	public void verify_msg(Response resp, String msg, int code) {

		@SuppressWarnings("rawtypes")
		ResponseBody body = resp.getBody();

		String bodyStringValue = body.asString();

		Assert.assertTrue("The response should contain message: " + msg +
				" but found " + bodyStringValue,bodyStringValue.contains(msg));
		Assert.assertEquals(resp.getStatusCode(),code);
	}
	
	public ValidatableResponse patchInstitutionLicense(String file, String endpoint) throws Exception {
		 String path = file_path + "/" + file;
         File filename = new File(path);
		 RestUtil.setBaseURI(literatum_url);		 
		 return SerenityRest.rest()
				.given()
		    	.spec(ReuseableSpecifications.getGenericExpRequestSpec())
		    	.when()
		    	.body(filename)
		    	.log().all()
		    	.patch(endpoint)
		    	.then().log().all();
	 }
}
