package ucc.i.method.literatum;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.net.URISyntaxException;

import org.json.simple.JSONObject;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class LiteratumPOST { 
	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("literatum.basePath");
	public static String literatum_url = webserviceEndpoint+serviceEndpoint+"/api";     
	TestUtils tUtil = new TestUtils();
	String file_path = env_var.getProperty("json.body.path");
	RestUtil Literatum = new RestUtil(literatum_url);
	
	 
	 @Step("Creating an identity Json_File:{0}, Endpoint:{1}, QueryParam_Value:{2},")
	 public ValidatableResponse CreateIdentity(String file_name, String endpoint, String queryParms) throws URISyntaxException{
	    	
	    //	String fName = file_name;
	    	
	    	String path = file_path+"/"+file_name;
	        File file = new File(path);
	        
	    	return	SerenityRest.rest()
	    		.given()
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
	    		.queryParams("type",queryParms)
	    		.when()
	    		.body(file)
	    		.log().all()
	    		.post(endpoint)
	    		.then()
	    		.log().all();
	    				
	    	}
	    
	 public void verifyResponse(Response resp, String valuePath, String expectedValue) {
		 
		 JsonPath jsonPathEvaluator = resp.jsonPath();
		// String value = resp.path(valuePath);
		 
		 String jsonVal = resp.getBody().asString();
		 
		 JsonPath jsonPath = new JsonPath(jsonVal);
		 
		 net.minidev.json.JSONArray jArray = com.jayway.jsonpath.JsonPath.read(jsonVal,valuePath);
			
	 	String oldValue = (String) jArray.get(0);
	 	System.out.println("Retrived value from response: "+oldValue);
	 	 
		 String all = jsonPath.get("identity..email[0].value"); 
	
		 
	 }
	 
	 
	 
	 public void verify_msg(Response resp, String msg, int code) {
	    	
	    	boolean f = false;
	    	String body  = resp.getBody().asString();
	    	
	    	if(body.contains(msg)){
	    		f =true;
	    	}
	    	
	    	assertTrue(("Body: "+body+" Contains: "+msg),f);
	    	assertEquals(("Expected Code: "+code+" Actual Code: "+resp.getStatusCode()),code, resp.getStatusCode()); 	   
	    	
	    } 
	 
	 
	 
	 public String setIdentityEndpoint() {
	    	String endpoint = "/identities";
			return endpoint;
	    }  
	
	 // ================================================================================================================
	    
	 public String setEndpointPersonIdentity() {
		 String endpoint = "/identities/person";
		 return endpoint;
	 }
	 
	 public String setEndpointLicenses() {
		 String endpoint = "/licenses";
		 return endpoint;
	 }
	 
	 public ValidatableResponse post(String filename, String endpoint) throws Exception {
		 String path = file_path + "/" + filename;
		 File file = new File(path);

		 RestUtil.setBaseURI(literatum_url);
		 
		 return SerenityRest.rest()
				.given()
		    	.spec(ReuseableSpecifications.getGenericExpRequestSpec())
		    	.when()
		    	.body(file)
		    	.log().all()
		    	.post(endpoint)
		    	.then().log().all();
	 }

	public ValidatableResponse checkURL(String endpoint) {

		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.post(endpoint)
				.then().log().all();
	}
	
	public ValidatableResponse postInstitutionLicense(String file, String endpoint) throws Exception {
		 String path = file_path + "/" + file;
		 File filename = new File(path);
		 RestUtil.setBaseURI(literatum_url);		 
		 return SerenityRest.rest()
				.given()
		    	.spec(ReuseableSpecifications.getGenericExpRequestSpec())
		    	.when()
		    	.body(filename)
		    	.log().all()
		    	.post(endpoint)
		    	.then().log().all();
	 }
	 
	 
}
