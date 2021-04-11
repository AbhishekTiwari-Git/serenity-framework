package ucc.i.method.kinesys;

import static org.junit.Assert.assertEquals;


import java.io.File;

import java.net.URISyntaxException;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;



import java.util.Map.Entry;

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




public class KinesysPOST {
	TestUtils tUtil = new TestUtils();
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("kinesys.basePath");
	public static String kinesys_url = webserviceEndpoint+serviceEndpoint+"/api";     
	String file_path = env_var.getProperty("json.body.path");
	RestUtil kinesys = new RestUtil(kinesys_url);
	
	 public ValidatableResponse createPanelist(String file_name, String endpoint) throws URISyntaxException{
	    	
	    //	String fName = file_name;
	    	
	    	String path = file_path+"/"+file_name;
	        File file = new File(path);
	        
	    
	    					
	    	return	SerenityRest.rest()
	    		.given()
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
	    		.when()
	    		.log().all()
	    		.body(file)
	    		.log().all()
	    		.post(endpoint)
	    		.then()
	    		.log().all();
	    				
	    	}
	    
	    
	    public String getID(Response resp) {
	    	JsonPath jsonPathEvaluator = resp.jsonPath();
			String Id = jsonPathEvaluator.getString("alternateId");
			return Id;
	    }
	    
	    public void verify_Panelist(Map<String,String> mapV, String endpoint) {
	        
	    	Response res = SerenityRest.rest()
	    		.given()
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
	    		.when()
	    		.log().all()
	    		.get(endpoint)
	    		.then()
	    		.extract().response();
	    	
	    	JsonPath jsonPathEvaluator = res.jsonPath();
			String alternateId = jsonPathEvaluator.getString("alternateId");
			String email = jsonPathEvaluator.getString("email");
		//	String id = jsonPathEvaluator.getString("id");
			
			
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
			
			System.out.println(list.get(0) +": "+  alternateId);
	    	System.out.println(list.get(2) +": "+  email);
	    	
	    	assertEquals(list.get(0), alternateId);
	    	assertEquals(list.get(2), email);
	    	
	    	
	        System.out.println("Successfully matched created panelist with alternateID and email...!!");
	    }

	public ValidatableResponse postOptIn(String file, String endpoint) throws URISyntaxException{

		return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.body(file)
				.log().all()
				.post(endpoint)
				.then()
				.log().all();

	}


	public String setEndpoint() {
	    	String endpoint = "/panelists";
			return endpoint;
	    }
	    
	    public String setEndpoint(String ID) {
	    	String endpoint = "/panelists/"+ID;
			return endpoint;
	    }
	    
	    public String setEndpoint(String ID, String survey) {
	    	String endpoint = "/panelists/"+ID+survey;
			return endpoint;
	    }

	    public String setEndpointOptIn() {
	    	String endpoint = "/optIn/confirmation";
			return endpoint;
	    }
    
}
