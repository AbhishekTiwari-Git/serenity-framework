package ucc.i.method.icv;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

public class ICVPUT {
	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("icv.basePath");
	public static String icvUrl = webserviceEndpoint+serviceEndpoint+"/api";     
	TestUtils tUtil = new TestUtils();
	//String file_path = env_var.getProperty("json.body.path");
	RestUtil ICV = new RestUtil(icvUrl);

    
	 public ValidatableResponse updateConference(String desp, String endDate, String name, String startDate, String state, String timezone, String endpoint, String updateType, String UpdateVal) throws URISyntaxException{
	    	
		 	JSONObject requestParams = new JSONObject();
			requestParams.put("description", desp);
			requestParams.put("end_date", endDate);
			requestParams.put("name", name);
			requestParams.put("start_date", startDate);
			requestParams.put("state", state);
			requestParams.put("timezone", timezone);
		 
			for (Object key : requestParams.keySet()) {
		        //based on you key types
		        String keyStr = (String)key;
		        Object keyvalue = requestParams.get(keyStr);

		        //Print key and value
		        System.out.println("key: "+ keyStr + " value: " + keyvalue);
                if (keyStr.contains(updateType)) {
                	requestParams.put(keyStr, UpdateVal);
                }
                else {
                	System.out.println("No matching key found");
                }
		       
		    }
		 
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
	 
	 
	 public ValidatableResponse updateConference(String desp, String endDate, String name, String startDate, String state, String timezone, String endpoint) throws URISyntaxException{
	    	
		 	JSONObject requestParams = new JSONObject();
			requestParams.put("description", desp);
			requestParams.put("end_date", endDate);
			requestParams.put("name", name);
			requestParams.put("start_date", startDate);
			requestParams.put("state", state);
			requestParams.put("timezone", timezone);
		 
			
		 
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
	 
	  public void verify_ICVConference(Map<String,String> mapV, Response resp, String uK, String uV) {
	        
	    	JsonPath jsonPathEvaluator = resp.jsonPath();
	    	String desp = jsonPathEvaluator.getString("description");
	    	String n = jsonPathEvaluator.getString("name");
	    	String end_d = jsonPathEvaluator.getString("end_date");
	    	String start_d = jsonPathEvaluator.getString("start_date");
	    	String state = jsonPathEvaluator.getString("state");
	    	String tz = jsonPathEvaluator.getString("timezone");
			
			List<String> list = new ArrayList<String>();

		//	Entry<String,String> ent;
			 for (Entry<String,String> ent : mapV.entrySet()) {
		          	if(ent.getKey().isEmpty()) {
		          		System.out.println("Empty map");
		          	}
		          	else {
		          		
		          		System.out.println(ent.getKey() +": "+ ent.getValue());
		          		if(ent.getKey().equalsIgnoreCase(uK)) {
		          		mapV.put(ent.getKey(), uV);
		          		}
		          		else {
		          			System.out.println("Non updated value");
		          		}
		          	}
		          }
			 
			 System.out.println(mapV.get("description") +": "+  desp);
		     System.out.println(mapV.get("name") +": "+  n);
		     System.out.println(mapV.get("end_date") +": "+  end_d);
		     System.out.println(mapV.get("start_date") +": "+  start_d);
		     System.out.println(mapV.get("state") +": "+  state);
		     System.out.println(mapV.get("timezone") +": "+  tz);
			 
	    //	String cNo = customerNumber.replaceAll("\\[", "").replaceAll("\\]","");
	   // 	String eml = email.replaceAll("\\[", "").replaceAll("\\]","");
	    	
	   // 	System.out.println(list.get(0) +": "+  cNo);
	   // 	System.out.println(list.get(1) +": "+  eml);
	    	
	    	assertEquals(mapV.get("description"), desp);
	    	assertEquals(mapV.get("name"), n);
	    	assertEquals(mapV.get("end_date"), end_d);
	    	assertEquals(mapV.get("start_date"), start_d);
	    	assertEquals(mapV.get("state"), state);
	    	assertEquals(mapV.get("timezone"), tz);
	    	
	    	
	        System.out.println("Successfully matched created ICV conference with description, name, end date, start date, state and timezone...!!");
	        
	    }
	  
	  
	  public void verify_ICVUser(Map<String,String> mapV, Response resp, String uK, String uV, String emailVal) {
	        
	      
		  
		  JsonPath jsonPathEvaluator = resp.jsonPath();
	    	String e = jsonPathEvaluator.getString("email");
	    	String n = jsonPathEvaluator.getString("name");
	    	String t = jsonPathEvaluator.getString("type");
	    	
			
			List<String> list = new ArrayList<String>();

		//	Entry<String,String> ent;
			 for (Entry<String,String> ent : mapV.entrySet()) {
		          	if(ent.getKey().isEmpty()) {
		          		System.out.println("Empty map");
		          	}
		          	else {
		          		
		          		System.out.println(ent.getKey() +": "+ ent.getValue());
		          		if(ent.getKey().equalsIgnoreCase(uK)) {
		          			if(ent.getKey().equalsIgnoreCase("email")) {
		          				uV = emailVal;
		          			}
		          		mapV.put(ent.getKey(), uV);
		          		}
		          		else {
		          			System.out.println("Non updated value");
		          		}
		          	}
		          }
			 
			 System.out.println(mapV.get("email") +": "+  e);
		     System.out.println(mapV.get("name") +": "+  n);
		     System.out.println(mapV.get("type") +": "+  t);
		     
			 
	    	assertEquals(mapV.get("email"), e);
	    	assertEquals(mapV.get("name"), n);
	    	assertEquals(mapV.get("type"), t);
	    	
	    	
	        System.out.println("Successfully matched created ICV user with updated name, email and type...!!");
	        
	    }
	    
	  public ValidatableResponse updateUser(JSONObject reqParam, String uK, String uV, String endpoint) throws URISyntaxException{
	    	
		  for (Object key : reqParam.keySet()) {
		        //based on you key types
		        String keyStr = (String)key;
		        Object keyvalue = reqParam.get(keyStr);

		        //Print key and value
		        System.out.println("key: "+ keyStr + " value: " + keyvalue);
              if (keyStr.contains(uK)) {
            	  
            	  reqParam.put(keyStr, uV);
              }
              else {
              	System.out.println("No matching key found");
              }
		       
		    }
			
		 
		 
	    	return	SerenityRest.rest()
	    		.given()
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
	    		.when()
	    		.log().all()
	    		.body(reqParam)
	    		.log().all()
	    		.put(endpoint)
	    		.then()
	    		.log().all();
	    				
	    	}
	 
	  
	  public ValidatableResponse updateUser(JSONObject reqParam, String endpoint) throws URISyntaxException{
	    	
		  
	    	return	SerenityRest.rest()
	    		.given()
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
	    		.when()
	    		.log().all()
	    		.body(reqParam)
	    		.log().all()
	    		.put(endpoint)
	    		.then()
	    		.log().all();
	    				
	    	}
	   
}
