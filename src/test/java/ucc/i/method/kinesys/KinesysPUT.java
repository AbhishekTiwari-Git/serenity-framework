package ucc.i.method.kinesys;

import static org.junit.Assert.assertEquals;


import java.io.File;

import java.net.URISyntaxException;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;



import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;




public class KinesysPUT {
	TestUtils tUtil = new TestUtils();
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("kinesys.basePath");
	public static String kinesys_url = webserviceEndpoint+serviceEndpoint+"/api";     
	String file_path = env_var.getProperty("json.body.path");
	static String tuV;
	static String email_value;
	
	RestUtil kinesys = new RestUtil(kinesys_url);
	
	 public ValidatableResponse updatePanelist(Map<String,String> mapV, String excludeKey, String uK, String uV, String endpoint) throws URISyntaxException{
	    	
	  
			JSONObject requestParams = new JSONObject();
			
			ConcurrentHashMap<String, String> mapK = new ConcurrentHashMap<>(mapV);
			
			
			for(Entry<String, String> ent : mapK.entrySet()) {
				if(ent.getKey().isEmpty()) {
	          		System.out.println("Empty map");
	          	}
	          	else {
	          		if(ent.getKey().equalsIgnoreCase(excludeKey)) {
	          			mapK.remove(ent.getKey(), ent.getValue());
	          			System.out.println("Key removed");
	          			if(uK.equalsIgnoreCase("subscribed")) 
	          			{
	          				mapK.put(uK, uV);
	          			}
	          		}
	          		else if(ent.getKey().equalsIgnoreCase(uK)) {
	          				tuV = tUtil.AppendTimestamp(uV);
	          				mapK.put(ent.getKey(), tuV);
	          		    
	          		}
	          		
	          	}
			}
			
			for(Entry<String, String> ent : mapK.entrySet()) {
	          		requestParams.put(ent.getKey(), ent.getValue());
	          		System.out.println(ent.getKey() +": "+ ent.getValue());
	           }	
				
			
			System.out.println("Request parameters :" + requestParams);
			email_value = mapK.get("email");
			System.out.println("Email :" + email_value);
			
			return	SerenityRest.rest()
				    		.given()
				    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				    		.when()
				    		.log().all()
				    		.body(requestParams)
				    		.log().all()
				    		.put(endpoint)
				    		.then()
				    		.log().all();
				    				
		}
	          

	 public ValidatableResponse updatePanelist(String id, String email, String subscriber, String endpoint) throws URISyntaxException{
	    	
		 
		 
		  //  email = tUtil.AppendTimestamp(email);
		  
			JSONObject requestParams = new JSONObject();
			requestParams.put("alternateId", id);
			requestParams.put("email", email);
			requestParams.put("subscribed", subscriber);
			
			return	SerenityRest.rest()
				    		.given()
				    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				    		.when()
				    		.log().all()
				    		.body(requestParams)
				    		.log().all()
				    		.put(endpoint)
				    		.then()
				    		.log().all();
				    				
		}	
	 
	 
	 public void verify_UpdatedPanelist(String endpoint) {
	        
	    	Response res = SerenityRest.rest()
	    		.given()
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
	    		.when()
	    		.log().all()
	    		.get(endpoint)
	    		.then()
	    		.extract().response();
	    	
	    	JsonPath jsonPathEvaluator = res.jsonPath();
			String email = jsonPathEvaluator.getString("email");
		//	String id = jsonPathEvaluator.getString("id");
			
			assertEquals(email_value, email);
	    	
	    	
	        System.out.println("Successfully matched updated panelist with email...!!");
	    }	
			
			
		
    
	    public String setEndpoint() {
	    	String endpoint = "/panelists";
			return endpoint;
	    }
	    
	    public String setEndpoint(String tpe) {
	    	String endpoint = "/panelists/"+tpe;
			return endpoint;
	    }
    
}
