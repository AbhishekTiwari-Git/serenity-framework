package ucc.i.method.accountexp;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.json.JSONException;
import org.json.simple.JSONObject;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class AccountExpGET {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.exp.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("accountEXpAPI");
	public static String AccountExp_url = webserviceEndpoint+serviceEndpoint+"/api";        
	TestUtils tUtil = new TestUtils();
	RestUtil AEA = new RestUtil(AccountExp_url);
	String file_path = env_var.getProperty("json.body.path");
	String file_name = "ExpResponse.json";
	static String path1;
	static String path2;
	static String expJson;
	static String Ucid = null;
	static String end_pt = null;
	public static Response expResp = null;

	
	public ValidatableResponse getFields(String field) throws URISyntaxException{
	
		String endpt;
		
		if (field != null) {
			endpt = AccountExp_url + "/fields/" + field;
		} else {
			endpt = AccountExp_url + "/fields";
		}
		
		
	return	SerenityRest.rest()
		.given()
		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
		.when()
		.get(endpt)
		.then()
		.log().all();
				
	}
	
	/* Place holder for writing a JSON Response to a File */
	
	public void writeJsonFile(Response resp) throws IOException {
		
		path1 = file_path + "/" + file_name;
		System.out.println(path1);
		File file = new File(path1);
		String ExpJson = resp.asString();
		
		if (file.exists() && file.isFile()) {
			file.delete();
			System.out.println("file exists, and its recreated");
		}

		file.getParentFile().mkdirs();
		FileWriter fw = new FileWriter(file, false);
		// create the print writer
		PrintWriter pw = new PrintWriter(fw, true);
		
		try {
			pw.write(ExpJson);
			System.out.println("Successfully updated json object to file...!!");
		} finally {
			pw.flush();
			pw.close();
			
		}
		
	}
	
	
	public void verifyJson(Response resp, String fileName) throws JSONException, JsonParseException, JsonMappingException, IOException {
		
		String path = file_path + "/" + "AICFieldsValidation" + "/"+ fileName+".json";
		
		File file = new File(path);
		
		ObjectMapper mapper = new ObjectMapper();

		
			JSONObject root = mapper.readValue(file, JSONObject.class);
			expJson = root.toString();
			System.out.println(root.toString());
		
			System.out.println(resp.getBody().asString());
		
		        Map<String, Object> m1 = (Map<String, Object>)(mapper.readValue(expJson, Map.class));
	            Map<String, Object> m2 = (Map<String, Object>)(mapper.readValue(resp.getBody().asString(), Map.class));
	            System.out.println(m1);
	            System.out.println(m2);
	            System.out.println(m1.equals(m2));
	       
			
	            assertTrue(m1.equals(m2));
		
	//	JSONAssert.assertEquals(expJson, resp.getBody().asString(), false);
			
	}
	
	public ValidatableResponse get(String endpoint) {
		
		RestUtil.setBaseURI(AccountExp_url);
		
		return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.get(endpoint)
				.then()
				.log().all();

	}
	
	public ValidatableResponse getUserByUcid(String endpoint, String authToken) {
		
		RestUtil.setBaseURI(AccountExp_url);
		
		return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.header("akamai-auth-token", authToken)
				.when()
				.log().all()
				.get(endpoint)
				.then()
				.log().all();

	}
	
	public ValidatableResponse getUserByEmail(String endpoint, String email) {
		
		RestUtil.setBaseURI(AccountExp_url);
		System.out.println(email);
		return	SerenityRest.rest()
				.given()
				.queryParam("email", email)
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.get(endpoint)
				.then()
				.log().all();

	}
	
	public ValidatableResponse getEventById(String endpoint) {
		
		RestUtil.setBaseURI(AccountExp_url);
		
		return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.get(endpoint)
				.then()
				.log().all();

	}

	public ValidatableResponse getUserSubscriptions(String endpoint, String authToken) {

		RestUtil.setBaseURI(AccountExp_url);

		return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.header("akamai-auth-token", authToken)
				.when()
				.log().all()
				.get(endpoint)
				.then()
				.log().all();

	}
	
	public ValidatableResponse getOrderReciept(String endPoint, String token) {
		 
		 RestUtil.setBaseURI(AccountExp_url);
		 return	SerenityRest.rest()
				 	.given()
				 	.spec(ReuseableSpecifications.getGenericExpRequestSpec())
	                .header("akamai-auth-token", token)
	                .when()
	                .log().all()
	                .get(endPoint)
	                .then()
	                .log().all();
	 }
	
	public String setFieldsEndpoint() {
		  String endpoint = "/fields";
		  return endpoint;
	}
	
	public String setEndpointGetUserByUcid(String ucid) {
		String endpoint = "/users/" + ucid;
		return endpoint;
	}
	
	public String setEndpointGetUserEvents(String ucid) {
		String endpoint = String.format("/users/%s/events", ucid);
		return endpoint;
	}
	
	public String setEndpointUserByEmail() {
		String endpoint = "/usersByEmail";
		return endpoint;
	}
	
	public String setEndpointCustomers() {
		String endpoint = "/customers";
		return endpoint;
	}
	
	public String setEndpointEventById(String eventId) {
		String endpoint = "/events/" + eventId;
		return endpoint;
	}

	public String setEndpointUserSurveysByUcid(String ucid) {
		String endpoint = "/users/" + ucid + "/surveys";
		return endpoint;
	}

	public String setEndpointUserSubscriptions(String ucid) {
		String endpoint = "/users/" + ucid + "/subscriptions";
		return endpoint;
	}

	public String setEndpointCustomerByEmail() {
		String endpoint = "/customers";
		return endpoint;
	}
	
	public String setEndpointCustomersUCID(String ucid) {
		String endpoint = "/customers/" + ucid;
		return endpoint;
	}
	
	public String setEndpointCustomersOrderRecpt(String ucid, String orderNumber) {
		String endpoint = "/customers/" + ucid + "/orders/" + orderNumber + "/receipt";
		return endpoint;
	}
}
