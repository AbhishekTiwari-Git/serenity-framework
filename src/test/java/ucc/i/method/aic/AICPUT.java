package ucc.i.method.aic;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URISyntaxException;
import java.text.ParseException;
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


public class AICPUT {
	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("aic.basePath");
	public static String AIC_url = webserviceEndpoint+serviceEndpoint+"/api";     
	TestUtils tUtil = new TestUtils();
	String file_path = env_var.getProperty("json.body.path");
	RestUtil AIC = new RestUtil(AIC_url);
	
	
	public ValidatableResponse registrUserEvent (String endpoint,String eventID, String name, String regDate) {
		
		JSONObject requestParams = new JSONObject();
		 requestParams.put("eventID", eventID);
		 requestParams.put("name", name);
		 requestParams.put("registrationDate", regDate);
		 
    	return	SerenityRest.rest()
        		.given()
        		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
        		.when()
        		.log().all()
        		.body(requestParams)
        		.put(endpoint)
        		.then()
        		.log().all();
	}
	
	
	public ValidatableResponse registerUserEventWithoutRequiredFields (String endpoint,String fieldname1, String fieldvalue1, String fieldname2, String fieldvalue2) {
		
		JSONObject requestParams = new JSONObject();
		 requestParams.put(fieldname1, fieldvalue1);
		 requestParams.put(fieldname2, fieldvalue2);
		 
    	return	SerenityRest.rest()
        		.given()
        		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
        		.when()
        		.log().all()
        		.body(requestParams)
        		.put(endpoint)
        		.then()
        		.log().all();
	}
	
	
	public void veriyRegisterEventInfo(Map<String,String> mapV, Response resp) throws ParseException {
		
		
    	JsonPath jsonPathEvaluator = resp.jsonPath();
    	String e = jsonPathEvaluator.getString("Catalyst.events[0].eventID");
    	String n = jsonPathEvaluator.getString("Catalyst.events[0].name");
    	String r = jsonPathEvaluator.getString("Catalyst.events[0].registrationDate");
    	
    	String myDateString  = r.split(" ")[0];
		
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
    	
    	assertEquals("The eventID should be "+ mapV.get("eventID") + "but found " + e, mapV.get("eventID"), e);
    	assertEquals("The name should be "+ mapV.get("name") + "but found " + n, mapV.get("name"), n);
    	assertEquals("The registrationDate should be "+ mapV.get("registrationDate") + "but found " + myDateString, mapV.get("registrationDate"), myDateString);

    	
    	
        System.out.println("Successfully matched eventID, name and registrationDate...!!");
 	
	}
	
	
	public String setEndPointEvents(String uuid) {
	
		String endpoint = "/users/"+uuid+"/events";
		return endpoint;
	}
	
	// =================================================================================================================
	
	public String setEndpointAlternateID(String uuid) {
		String endpoint = "/users/" + uuid + "/alternateID";
		return endpoint;
	}
	
	public ValidatableResponse putAlternateID(String filename, String endpoint) {
		String path = file_path + "/" + filename;
		File file = new File(path);
		
		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.body(file)
				.put(AIC_url + endpoint)
				.then()
				.log().all();
	}

	public ValidatableResponse put(String file_name, String endpoint) {

		String path = file_path+"/"+file_name;
		File file = new File(path);

		RestUtil.setBaseURI(AIC_url);

		return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.body(file)
				.log().all()
				.put(endpoint)
				.then()
				.log().all();
	}
	
	public ValidatableResponse put_withJSON(String file_name, String endpoint) throws URISyntaxException{
    	
	    	
	    String path = file_path+"/"+file_name;
	    File file = new File(path);
	           
	    					
	    return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.body(file)
				.log().all()
				.put(endpoint)
				.then()
				.log().all();
	    				
	}

	public String setEndPointActivatePassword() {

		String endpoint = "/users/activatePassword";
		return endpoint;
	}
}
