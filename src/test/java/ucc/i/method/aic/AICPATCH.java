package ucc.i.method.aic;

import java.io.File;
import java.net.URISyntaxException;

import org.json.simple.JSONObject;
import org.junit.Assert;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class AICPATCH {
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("aic.basePath");
	public static String AIC_url = webserviceEndpoint+serviceEndpoint+"/api";     
	TestUtils tUtil = new TestUtils();
	String file_path = env_var.getProperty("json.body.path");
	RestUtil AIC = new RestUtil(AIC_url);
	
	
	
	 public ValidatableResponse updateUser(String file_name, String endpoint) throws URISyntaxException{
		    	
		    	String path = file_path+"/"+file_name;
		        File file = new File(path);
		    					
		    	return	SerenityRest.rest()
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
	 
	 public ValidatableResponse updateUser(String field, String fieldValue, String endpoint) throws URISyntaxException{
	        
		 JSONObject requestParams = new JSONObject();
		 requestParams.put(field, fieldValue);
	    					
	    	return	SerenityRest.rest()
	    		.given()
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
	    		.when()
	    		.log().all()
	    		.body(requestParams)
	    		.log().all()
	    		.patch(endpoint)
	    		.then();
	    				
	    	}
	 
	 public ValidatableResponse PatchRequestWithEmptyBody(String endpoint) throws URISyntaxException{
	    	
		 	JSONObject requestParams = new JSONObject();
	    					
	    	return	SerenityRest.rest()
	    		.given()
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
	    		.when()
	    		.log().all()
	    		.body(requestParams)
	    		.log().all()
	    		.patch(endpoint)
	    		.then();
	    		//.log().all();
	    				
	    	}

	 
	    public void verify_ICVmsg(Response resp, String msg, int code) {
	    	
	    	 @SuppressWarnings("rawtypes")
			 ResponseBody body = resp.getBody();
	    	 
	    	 // Get Response Body as String 
	    	 String bodyStringValue = body.asString();

	    	 Assert.assertTrue("The response should contain message: " + msg +" but found " + bodyStringValue,bodyStringValue.contains(msg));
	    	 Assert.assertEquals(resp.getStatusCode(),code);
	    	    	   
} 
	 
	 
	 public String setEndpointUserID(String uuid) {
	    	String endpoint = "/users/"+uuid;
			return endpoint;
	 }
	 
	 public ValidatableResponse updatePassword(String fieldValue, String endpoint) throws URISyntaxException{
	     
		 RestUtil.setBaseURI(AIC_url);
		 JSONObject requestParams = new JSONObject();
		 requestParams.put("password", fieldValue);
	    					
	    	return SerenityRest.rest()
	    		.given()	    		
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
	    		.when()
	    		.log().all()
	    		.body(requestParams)
	    		.log().all()
	    		.patch(endpoint)
	    		.then();
	    				
	    	}
	 
}
