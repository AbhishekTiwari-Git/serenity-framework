package ucc.i.method.customerprocess;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import org.json.JSONException;
import org.junit.Assert;

import static org.junit.Assert.assertTrue;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerProcessGET {
	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
      .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
      .getProperty("customerProcess.basePath");
	public static String CustomerProcess_url = webserviceEndpoint + serviceEndpoint + "/api"; 
	TestUtils tUtil = new TestUtils();
	RestUtil CPA = new RestUtil(CustomerProcess_url);
	
	
	
	public ValidatableResponse getInvoice(String endpoint, String authToken) {
		RestUtil.setBaseURI(CustomerProcess_url);

		return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.header("akamai-auth-token", authToken)
				.log().all()
				.when()
				.get(endpoint)
				.then()
				.log().all();
	}
	
	public ValidatableResponse getInvoiceWithProductFamily(String endpoint, String authToken, String productFamily) {
		RestUtil.setBaseURI(CustomerProcess_url);

		return	SerenityRest.rest()
				.given()
				.queryParam("productFamily", productFamily)
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.header("akamai-auth-token", authToken)
				.log().all()
				.when()
				.get(endpoint)
				.then()
				.log().all();
	}

	
	public ValidatableResponse getFields(String field) throws URISyntaxException{
		
		String endpt;
		
		if (field != null) {
			endpt = CustomerProcess_url + "/fields/" + field;
		} else {
			endpt = CustomerProcess_url + "/fields";
		}
		
		
	return	SerenityRest.rest()
		.given()
		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
		.when()
		.get(endpt)
		.then()
		.log().all();
				
	}
	
	public ValidatableResponse get(String endpoint) {

		RestUtil.setBaseURI(CustomerProcess_url);

		return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.get(endpoint)
				.then()
				.log().all();
	}
	
	public ValidatableResponse getOrderReciept(String endPoint, String token) {
		 
		 RestUtil.setBaseURI(CustomerProcess_url);
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
	
	public ValidatableResponse getUserInfo(String endpoint) throws URISyntaxException{

		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.get(CustomerProcess_url + endpoint)
				.then()
				.log().all();
				
	}
	

    public void verifyJson(Response resp1, Response resp2) throws JSONException, JsonParseException, JsonMappingException, IOException {
		
    	ObjectMapper mapper = new ObjectMapper();
	
		Map<String, Object> m1 = (Map<String, Object>)(mapper.readValue(resp1.getBody().asString(), Map.class));
		Map<String, Object> m2 = (Map<String, Object>)(mapper.readValue(resp2.getBody().asString(), Map.class));
	            
	    System.out.println(m1.equals(m2));

	    ArrayList m1_al = (ArrayList) m1.get("items");
	    ArrayList m2_al = (ArrayList) m2.get("items");

	    for (int i=0; i < m1_al.size() - 1; i++) {
			if (m1_al.get(i).equals(m2_al.get(i))) {
				assertTrue(m1_al.get(i).equals(m2_al.get(i)));
			}
		}
			
//        assertTrue(m1.equals(m2));
		
	//	JSONAssert.assertEquals(expJson, resp.getBody().asString(), false);
			
	}
    
    // Compares user info from two responses
    // Note: boolean flag indicates whether response from experience api is included as 1st argument
    public void verifyUserInfo(Response r1, Response r2, boolean exp) {
    	JsonPath jsonpathEvaluator = r1.jsonPath();
    	
    	String r1Ucid = exp ? jsonpathEvaluator.getString("ucId") : jsonpathEvaluator.get("uuid");
    	String r1Email = jsonpathEvaluator.getString("email");
    	String r1LastName = jsonpathEvaluator.getString("lastName");
    	String r1FirstName = jsonpathEvaluator.getString("firstName");
    	
    	jsonpathEvaluator = r2.jsonPath();
    	
    	String r2Ucid = jsonpathEvaluator.getString("uuid");
    	String r2Email = jsonpathEvaluator.getString("email");
    	String r2LastName = jsonpathEvaluator.getString("lastName");
    	String r2FirstName = jsonpathEvaluator.getString("firstName");
    	
    	Assert.assertEquals(String.format("The ucid should be %s but got %s", r1Ucid, r2Ucid), 
    			r1Ucid, r2Ucid);
		Assert.assertEquals(String.format("The email should be %s but got %s", r1Email, r2Email), 
				r1Email, r2Email);
		Assert.assertEquals(String.format("The first name should be %s but got %s", r1FirstName, r2FirstName), 
				r1FirstName, r2FirstName);
		Assert.assertEquals(String.format("The last name should be %s but got %s", r1LastName, r2LastName), 
				r1LastName, r2LastName);
    }
    
    public void verifyAlternateId(Response r1, Response r2) {
    	JsonPath jsonpathEvaluator = r2.jsonPath();
    	
    	String idValue = jsonpathEvaluator.getString("id");
    	String r2Name = jsonpathEvaluator.getString("name");
    	String r2Email = jsonpathEvaluator.getString("email");
    	String r2Type = jsonpathEvaluator.getString("type");
    	
    	jsonpathEvaluator = r1.jsonPath();
    	
    	String r1LastName = jsonpathEvaluator.getString("lastName");
    	String r1FirstName = jsonpathEvaluator.getString("firstName");
    	String r1Email = jsonpathEvaluator.getString("email");
    	List<String> loIdValue = jsonpathEvaluator.getList("alternateID.IDValue");
    	List<String> loIdType = jsonpathEvaluator.getList("alternateID.IDType");
    	
    	Assert.assertEquals(String.format("The email should be %s but got %s", r1Email, r2Email), 
				r1Email, r2Email);
		Assert.assertTrue(r2Name.contains(r1FirstName) && r2Name.contains(r1LastName));
		Assert.assertTrue(loIdValue.stream().anyMatch(val -> val.equals(idValue) && loIdType.get(loIdValue.indexOf(val)).equals("ICV")));
    	
    }
     
    public String setFieldsEndpoint() {
    	String endpoint = "/fields";
		return endpoint;
	}
    
    public String setEndpointUserByEmail(String email) {
    	String endpoint = "/users/filter/email/" + email;
    	return endpoint;
    }
    
    public String setEndpointUserByUcid(String ucid) {
      String endpoint = "/users/" + ucid;
      return endpoint;
    }

    public String setEndpointUserSurveysByUcid(String ucid) {
      String endpoint = "/users/" + ucid + "/surveys";
      return endpoint;
    }
    
    public ValidatableResponse getUserByUcid(String endpoint, String token) {
      
      return  SerenityRest.rest()
          .given()
          .spec(ReuseableSpecifications.getGenericExpRequestSpec())
          .header("akamai-auth-token", token)
          .when()
          .get(CustomerProcess_url + endpoint)
          .then()
          .log().all();
    }

	public ValidatableResponse getTickets(Integer ticketId) throws URISyntaxException{

		String endpt;

		if (ticketId != null) {
			endpt = CustomerProcess_url + "/tickets/" + ticketId;
		} else {
			endpt = CustomerProcess_url + "/tickets";
		}


		return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.get(endpt)
				.then()
				.log().all();

	}
	
	public String setEndpointCustomersUCIDinvoices(String ucid) {
      String endpoint = "/customers/" + ucid + "/invoices";
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
