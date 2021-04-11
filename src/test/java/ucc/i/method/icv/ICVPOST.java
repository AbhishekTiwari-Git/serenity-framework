package ucc.i.method.icv;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import org.junit.Assert;

import java.util.Map.Entry;

import io.restassured.path.json.JsonPath;
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

public class ICVPOST {
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("icv.basePath");
	
	public static String IcvUrl = webserviceEndpoint+serviceEndpoint+"/api";     
	TestUtils tUtil = new TestUtils();
	//String file_path = env_var.getProperty("json.body.path");
	RestUtil ICV = new RestUtil(IcvUrl);
	
	
	
	 public ValidatableResponse createUser(String email, String name, String type, String endpoint) throws URISyntaxException{
	    	
		 
		   
		    System.out.println(IcvUrl);
		   
		    
		    
		 	JSONObject requestParams = new JSONObject();
			requestParams.put("email", email);
			requestParams.put("name", name);
			requestParams.put("type", type);
		 
		 
	    	return	SerenityRest.rest()
	    		.given()
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
	    		.when()
	    		.log().all()
	    		.body(requestParams)
	    		.log().all()
	    		.post(IcvUrl + endpoint)
	    		.then()
	    		.log().all();
	    				
	    	}
	 
	 public ValidatableResponse createConference(String desp, String endDate, String name, String startDate, String state, String timezone, String endpoint) throws URISyntaxException{
	    	
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
	    		.post(endpoint)
	    		.then()
	    		.log().all();
	    				
	    	}
	    
	 public ValidatableResponse createUserInvalidData(String email, String name, String type, String endpoint) throws URISyntaxException{
	    	
		 	JSONObject reqParam = new JSONObject();
		 	reqParam.put("email", email);
		 	reqParam.put("name", name);
		 	reqParam.put("type", type);
		 
		 
	    	return	SerenityRest.rest()
	    		.given()
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
	    		.when()
	    		.log().all()
	    		.body(reqParam)
	    		.log().all()
	    		.post(endpoint)
	    		.then()
	    		.log().all();
	    				
	    	}
			

	 public ValidatableResponse registerUser(String email, String endpoint) throws URISyntaxException{
	    	
		 	JSONObject requestParams = new JSONObject();
		 	if(!email.equals("")) {
		 		requestParams.put("email", email);
		 	}
		
	    	return	SerenityRest.rest()
	    		.given()
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
	    		.when()
	    		.log().all()
	    		.body(requestParams)
	    		.post(endpoint)
	    		.then()
	    		.log().all();
	    				
	    	}
	    
	    public String getUserEmail(Response resp) {
	    	JsonPath jsonPathEvaluator = resp.jsonPath();
			String eml = jsonPathEvaluator.getString("email");
			return eml;
	    }
	    
	    
	    public void verify_ICVUser(Map<String,String> mapV, Response resp) {
	        
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
		          		list.add(ent.getValue());
		          		
		          	}
		          }
			 
			 System.out.println(mapV.get("email") +": "+  e);
		     System.out.println(mapV.get("name") +": "+  n);
		     System.out.println(mapV.get("type") +": "+  t);
			 
	    //	String cNo = customerNumber.replaceAll("\\[", "").replaceAll("\\]","");
	   // 	String eml = email.replaceAll("\\[", "").replaceAll("\\]","");
	    	
	   // 	System.out.println(list.get(0) +": "+  cNo);
	   // 	System.out.println(list.get(1) +": "+  eml);
	    	
	    	assertEquals(mapV.get("email"), e);
	    	assertEquals(mapV.get("name"), n);
	    	assertEquals(mapV.get("type"), t);
	    	
	    	
	        System.out.println("Successfully matched created ICV user with email, name and Type...!!");
	    }
	    
	    
	    public void verify_ICVConference(Map<String,String> mapV, Response resp) {
	        
	    	JsonPath jsonPathEvaluator = resp.jsonPath();
	    	String desp = jsonPathEvaluator.getString("description");
	    	String n = jsonPathEvaluator.getString("name");
	   // 	String end_d = jsonPathEvaluator.getString("end_date");
	   // 	String start_d = jsonPathEvaluator.getString("start_date");
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
		          		list.add(ent.getValue());
		          		
		          	}
		          }
			 
			 desp =  desp.replaceAll("[\\[\\]]", "");
		     n = n.replaceAll("[\\[\\]]", "");
		 //    end_d =  end_d.replaceAll("[\\[\\]]", "");
		//     start_d =  start_d.replaceAll("[\\[\\]]", "");
		     state =  state.replaceAll("[\\[\\]]", "");
		     tz =  tz.replaceAll("[\\[\\]]", "");
			 
	    //	String cNo = customerNumber.replaceAll("\\[", "").replaceAll("\\]","");
	   // 	String eml = email.replaceAll("\\[", "").replaceAll("\\]","");
	    	
	   // 	System.out.println(list.get(0) +": "+  cNo);
	   // 	System.out.println(list.get(1) +": "+  eml);
	    	
	    	assertEquals(("Actual description :"+mapV.get("description")+" Expected :" + desp +"does not match"),mapV.get("description"), desp);
	    	assertEquals(("Actual name :"+mapV.get("name")+" Expected :" + n +"does not match"),mapV.get("name"), n);
	   // 	assertEquals(("Actual end date :"+mapV.get("end_date")+" Expected :" + end_d +"does not match"),mapV.get("end_date"), end_d);
	   // 	assertEquals(("Actual start date :"+mapV.get("start_date")+" Expected :" + start_d +"does not match"),mapV.get("start_date"), start_d);
	    	assertEquals(("Actual state :"+mapV.get("state")+" Expected :" + state +"does not match"),mapV.get("state"), state);
	    	assertEquals(("Actual timezone :"+mapV.get("tz")+" Expected :" + tz +"does not match"),mapV.get("timezone"), tz);
	    	
	    	
	        System.out.println("Successfully matched created ICV conference with description, name, end date, start date, state and timezone...!!");
	    }
	    
	    
	    public void verify_ICVmsg(Response resp, String msg, int code) {
	    	
	    	 @SuppressWarnings("rawtypes")
			 ResponseBody body = resp.getBody();
	    	 
	    	 // Get Response Body as String 
	    	 String bodyStringValue = body.asString();
	    	
	    	 Assert.assertTrue("The response should contain message: " + msg +" but found " + bodyStringValue,bodyStringValue.contains(msg));
	    	 Assert.assertEquals(resp.getStatusCode(),code);
	    	    	   
 }  
	    
	    public ValidatableResponse createUserType(String email, String name, String type, String endpoint) throws URISyntaxException{
	    	
		 	JSONObject requestParams = new JSONObject();
			requestParams.put("email", email);
			requestParams.put("name", name);
			requestParams.put("type", type);
			
		 
	    	return	SerenityRest.rest()
	    		.given()
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
	    		.when()
	    		.log().all()
	    		.body(requestParams)
	    		.log().all()
	    		.post(endpoint)
	    		.then()
	    		.log().all();
	    				
	    	}
	    
	    
public Map<String, String> store_ICVConferenceId(Response resp) {
	        
	    	JsonPath jsonPathEvaluator = resp.jsonPath();
	    	String desp = jsonPathEvaluator.getString("description");
	    	String name = jsonPathEvaluator.getString("name");
	    	String endDate = jsonPathEvaluator.getString("end_date");
	    	String startDate = jsonPathEvaluator.getString("start_date");
	    	String state = jsonPathEvaluator.getString("state");
	    	String tz = jsonPathEvaluator.getString("timezone");
			
	    	String[] Keys = { "description", "end_date", "name","start_date", "state", "timezone" };
	 		String[] values = {desp, endDate, name, startDate, state, tz};
	 		
	 		List<String> get_ConfVals=new ArrayList<String>();
	 		List<String> get_ConfKeys=new ArrayList<String>();
	 		
	 		get_ConfKeys = Arrays.asList( Keys );
	 		get_ConfVals = Arrays.asList( values );
	 		
	 		Map<String,String> kmap = new HashMap<String,String>();
	 		kmap = tUtil.store_jsonValues(get_ConfKeys, get_ConfVals);
	 		
	 		return kmap;
	    	
}
	    
	    public String getID(Response resp) {
	    	JsonPath jsonPathEvaluator = resp.jsonPath();
			String Id = jsonPathEvaluator.getString("id");
			return Id;
	    }
	    
 
	    public String setEndpoint() {
	    	String endpoint = "/users";
			return endpoint;
	    }
	    
	    public String setEndpoint(String eml) {
	    	String endpoint = "/users/"+eml;
			return endpoint;
	    }
		
	    public String setEndpointType(String tpe) {
	    	String endpoint = "/"+tpe;
			return endpoint;
	    }
	  
	    public String setConferenceEndpoint() {
	    	String endpoint = "/conferences";
			return endpoint;
	    }
	    
	    public String setConferenceEndpoint(String eml) {
	    	String endpoint = "/conferences/"+eml;
			return endpoint;
	    }
	    
	    
	    public String registerConferenceEndpoint(String id) {
	    	String endpoint = "/conferences/"+id+"/users";
			return endpoint;
	    }
}