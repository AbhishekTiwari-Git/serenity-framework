package ucc.i.method.aic;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.json.simple.JSONObject;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AICPOST {
	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("aic.basePath");
	public static String AIC_url = webserviceEndpoint+serviceEndpoint+"/api";    
	String Akamai_tokenUrl = EnvironmentSpecificConfiguration.from(env_var)
	          .getProperty("aic.token.url") + "access/getAccessToken";

	TestUtils tUtil = new TestUtils();
	String file_path = env_var.getProperty("json.body.path");
	RestUtil AIC = new RestUtil(AIC_url);
	
	
	
	public ValidatableResponse createCustomerInAkamai(String file_name, String endpoint) {
		
    	String path = file_path+"/"+file_name;
        File file = new File(path);
    					
    	return	SerenityRest.rest()
    		.given()
    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
    		.when()
    		.log().all()
    		.body(file)
    		.post(AIC_url + endpoint)
    		.then()
    		.log().all();
    	
	}
	
	
	public void verifyCustomerData(Map<String,String> mapV, Response resp) {
		
    	JsonPath jsonPathEvaluator = resp.jsonPath();
    	String e = jsonPathEvaluator.getString("email");
    	String p = jsonPathEvaluator.getString("password");
    	String f = jsonPathEvaluator.getString("firstName");
    	String l = jsonPathEvaluator.getString("lastName");
    	String s = jsonPathEvaluator.getString("suffix");
    	String r = jsonPathEvaluator.getString("roleCode");
		
		
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
    	
    	assertEquals("The email should be "+ mapV.get("email") + "but found " + e, mapV.get("email"), e);
    	assertEquals("The email should be "+ mapV.get("password") + "but found " + p, mapV.get("password"), p);
    	assertEquals("The email should be "+ mapV.get("firstName") + "but found " + f, mapV.get("firstName"), f);
    	assertEquals("The email should be "+ mapV.get("lastName") + "but found " + l, mapV.get("lastName"), l);
    	assertEquals("The email should be "+ mapV.get("suffix") + "but found " + s, mapV.get("suffix"), s);
    	assertEquals("The email should be "+ mapV.get("roleCode") + "but found " + r, mapV.get("roleCode"), r);
    	
    	
        System.out.println("Successfully matched created ICV user with email, name and Type...!!");
	}

	public void verify_AICmsg(Response resp, String msg, int code) {
    	
   	  @SuppressWarnings("rawtypes")
		 ResponseBody body = resp.getBody();
   	 
   	 // Get Response Body as String 
   	  String bodyStringValue = body.asString();
   	
   	  Assert.assertTrue("The response should contain message: " + msg +" but found " + bodyStringValue,bodyStringValue.contains(msg));
   	  Assert.assertEquals(resp.getStatusCode(),code);
   	    	   
	}
	
	public ValidatableResponse createToken(String uuid) {
		 
    	RestUtil.setBaseURI(Akamai_tokenUrl);
    	
    	RequestSpecification rspec = null;
    	
    	switch (System.getProperty("environment")) {
    	  case "devFeature":
    	    rspec = ReuseableSpecifications.getAkamaiDevRequestSpec();
    	    break;
    	  case "dev":
    	    rspec = ReuseableSpecifications.getAkamaiDevRequestSpec();
    	    break;
    	  case "qa":
    	    rspec = ReuseableSpecifications.getAkamaiQaRequestSpec();
    	    break;
    	  case "prod":
    	    rspec = ReuseableSpecifications.getAkamaiProdRequestSpec();
    	    break;
    	  default:
    	    
    	}
    					
    	return	SerenityRest.rest()
    		.given()
    		.spec(rspec)
    		.when()
    		.queryParam("uuid", uuid)
    		.queryParam("type_name", "user")
    		.log().all()
    		.post()
    		.then()
    		.log().all();
    	
	}
	
	public ValidatableResponse authenticate(String uccId, String token, String endpoint) {

		JSONObject requestParams = new JSONObject();
		requestParams.put("uuid", uccId);
		requestParams.put("token", token);
		
		RestUtil.setBaseURI(AIC_url);
 					
    	return	SerenityRest.rest()
    		.given()
    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
    		.when()
    		.body(requestParams)
    		.log().all()
    		.post(endpoint)
    		.then()
    		.log().all();
    	
	}
	
	public String getUUID(Response resp) {
    	JsonPath jsonPathEvaluator = resp.jsonPath();
		String id = jsonPathEvaluator.getString("uuid");
		return id;
	}
	
	public String getToken(Response resp) {
		JsonPath jsonPathEvaluator = resp.jsonPath();
		String token = jsonPathEvaluator.getString("accessToken");
		return token;
	}

	public String getDateUpdated(Response resp) {
		JsonPath jsonPathEvaluator = resp.jsonPath();
		String token = jsonPathEvaluator.getString("dateUpdated");
		return token;
	}

	public String getDateCreated(Response resp) {
		JsonPath jsonPathEvaluator = resp.jsonPath();
		String token = jsonPathEvaluator.getString("dateCreated");
		return token;
	}
	
	public String setEndPoint() {
	
		String endpoint = "/users";
		return endpoint;
	}

	public String setTokenEndPoint() {

		String endpoint = "/authentication/validate-token";
		return endpoint;
	}

}
