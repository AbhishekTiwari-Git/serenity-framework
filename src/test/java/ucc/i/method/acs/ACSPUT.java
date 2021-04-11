package ucc.i.method.acs;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

public class ACSPUT {
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("acs.basePath");
	public static String ACS_url = webserviceEndpoint+serviceEndpoint+"/api";        
	TestUtils tUtil = new TestUtils();
	String file_path = env_var.getProperty("json.body.path");

	
	 public ValidatableResponse updateCustomer(String file_name, String endpoint) throws URISyntaxException{
	    	
	    //	String fName = file_name;
	    	
	    	String path = file_path+"/"+file_name;
	        File file = new File(path);
	        
	    	RestUtil.setBaseURI(ACS_url);
	    					
	    	return	SerenityRest.rest()
	    		.given()
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec()).log().all()
	    		.when()
	    		.body(file)
	    		.log().all()
	    		.put(endpoint)
	    		.then()
	    		.log().all();
	    				
	    	}
	    
	    
	 public void verify_UpdatedEmailCustomer(Map<String,String> mapV, String endpoint, String ID) {
	        
	    	RestUtil.setBaseURI(ACS_url);
	    	
	    	Response res = SerenityRest.rest()
	    		.given()
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec()).log().all()
	    		.when()
	    		.get(endpoint)
	    		.then()
	    		.extract().response();
	    	
	    	JsonPath jsonPathEvaluator = res.jsonPath();
	    	String customerNumber = jsonPathEvaluator.getString("customerNumber");
	    	List<String> email = jsonPathEvaluator.getList("addresses.email");
			
			List<String> list = new ArrayList<String>();

			 for (Entry<String,String> ent : mapV.entrySet()) {
		          	if(ent.getKey().isEmpty()) {
		          		System.out.println("Empty map");
		          	}
		          	else {
		          		
		          		System.out.println(ent.getKey() +": "+ ent.getValue());
		          		list.add(ent.getValue());
		          		
		          	}
		          }
			
			
	    	String cNo = customerNumber.replaceAll("\\[", "").replaceAll("\\]","");
	    	
	    	System.out.println(ID +": "+  cNo);
	    	System.out.println(list.get(0) +": "+  email.get(0));
	    	
	    	assertEquals(ID, cNo);
	    	assertTrue(email.contains(list.get(0)));
	    	
	    	
	        System.out.println("Successfully matched created panelist with alternateID and email...!!");
	    }
}
