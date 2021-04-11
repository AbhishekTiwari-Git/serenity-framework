package ucc.i.method.acs;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Assert;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;


public class ACSGET {
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceSystemEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("acs.basePath");
	static String serviceProcessEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("acs.procPath");
	public static String ACS_system_url = webserviceEndpoint+serviceSystemEndpoint+"/api";
	public static String ACS_process_url = webserviceEndpoint+serviceProcessEndpoint+"/api";
	TestUtils tUtil = new TestUtils();
	RestUtil ACS = new RestUtil(ACS_system_url);
	public static Response acsResponse = null;
	static String end_pt = null;

	public ValidatableResponse getCustomers(String endpoint, String queryParams, String value) throws URISyntaxException{

	RestUtil.setBaseURI(ACS_system_url);

	return	SerenityRest.rest()
		.given()
		.spec(ReuseableSpecifications.getGenericExpRequestSpec()).log().all()
		.when()
		.queryParam(queryParams, value)
		.get(endpoint)
		.then()
		.log().all();
				
	}



	 public void verify_msg(String endpoint, String query, String value, String msg, int code) {
	        
	    	
		    if(msg.contains("message")) {
	    		JsonPath expectedJson = new JsonPath(new String(msg));
	    		SerenityRest.rest()
	    		.given()
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec()).log().all()
	    		.when()
	    		.queryParam(query, value)
	    		.get(endpoint)
	    		.then()
	    		.body("message", equalTo(expectedJson.get("message")))
	    		.assertThat()
	    		.statusCode(code)
	    		.log().all();
	    	   
	    	}
		    
		    else {
	    	
	    					
	    	SerenityRest.rest()
	    		.given()
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec()).log().all()
	    		.when()
	    		.queryParam(query, value)
	    		.get(endpoint)
	    		.then()
	    		.body("", Matchers.empty())
	    		.assertThat()
	    		.statusCode(code)
	    		.log().all();
		    }
	    } 
	
	 public void verify_msg(String endpoint, String value, String msg, int code) {
	        
	    	
		    if(msg.contains("message")) {
	    		JsonPath expectedJson = new JsonPath(new String(msg));
	    		SerenityRest.rest()
	    		.given()
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec()).log().all()
	    		.when()
	    		.get(endpoint)
	    		.then()
	    		.body("message", equalTo(expectedJson.get("message")))
	    		.assertThat()
	    		.statusCode(code)
	    		.log().all();
	    	   
	    	}
		    
		    else {
	    	
	    					
	    	SerenityRest.rest()
	    		.given()
	    		.spec(ReuseableSpecifications.getGenericExpRequestSpec()).log().all()
	    		.when()
	    		.get(endpoint)
	    		.then()
	    	//	.body("", Matchers.empty())
	    		.body("isEmpty()", Matchers.is(true))
	    		.assertThat()
	    		.statusCode(code)
	    		.log().all();
		    }
	    } 
	 
	 public void verifyOrderNumber(Response resp, String orderNum) {
	    	JsonPath jsonPathEvaluator = resp.jsonPath();
			String orderNumInResp = jsonPathEvaluator.getString("agreements[0].orderNumber");
			Assert.assertEquals("The response should contain order number: " + orderNum  + " but found " + orderNumInResp,
					orderNum,orderNumInResp );
	 }
	 
	 public void verifyPaymentDetails(Response resp) {
		 
	    	JSONObject JSONResponseBody = new   JSONObject(resp.body().asString());
	    	org.json.JSONArray arrJson = JSONResponseBody.getJSONArray("agreements");
	    	JSONObject agreement = arrJson.getJSONObject(0);
			Assert.assertTrue("The response should contain electronicPaymentId key", agreement.has("electronicPaymentId") );
			
			
			org.json.JSONArray debitArr = agreement.getJSONArray("debits");
			
			if(!debitArr.isEmpty()) {
		    	JSONObject debit = debitArr.getJSONObject(0);
				Assert.assertTrue("The response should contain electronicPaymentId key", debit.has("billingOrganization") );
			}
	 }
	 
	 
	  public String setEndpoint() {
	    	String endpoint = "/customers";
			return endpoint;
	    }
	    
	  public String setEndpoint(String custNo) {
	    	String endpoint = "/customers/"+custNo;
			return endpoint;
	    }
	  
	  // ===============================================================================================================
	  
	  public String setCustomerOrdersEndpoint(String custNo) {
		  String endpoint = "/customers/" + custNo + "/orders?isPosted=false";
		  return endpoint;
	  }
	  
	  public String setCustomerAgreementsEndpoint(String custNo) {
		  String endpoint = "/customers/" + custNo + "/agreements";
		  return endpoint;
	  }
	  
	  
	  public String setEmptyCustomerAgreementsEndpoint() {
		  String endpoint = "/customers/agreements";
		  return endpoint;
	  }
	  
	  public String setEnpointAgreementProduct(String custNo, String productId) {
		  String endpoint = "/customers/" + custNo + "/agreements/agreementIdByProduct?prdCode=" + productId;
		  return endpoint;
	  }
		  
	  public String setOrdersEndpoint(String custNo) {
		  String endpoint = "/customers/" + custNo + "/orders";
		  return endpoint;
	  }

	  public String setUcidEndpoint(String custNo) {
		  String endpoint = "/customers/" + custNo + "/ucid";
		  return endpoint;
	  }
	  
	  public String setCreditCardEndpoint(String custNumber, String billingOrg, String currCode, String paymentId) {
		  String endpoint = String.format("/customers/%s/credit-cards/%s/%s/%s", custNumber, billingOrg, currCode, paymentId);
		  return endpoint;
	  }

	  public String setVerifyInstAdminEndpoint(String custNo) {
		  String endpoint = "/customers/" + custNo + "/verify-inst-admin";
		  return endpoint;
	  }

	  public String setAgreementEnpoint(String agreementId) {
		  String endpoint = "/agreements/" + agreementId;
		  return endpoint;
	  }

	  public ValidatableResponse get(String endpoint) {

		RestUtil.setBaseURI(ACS_system_url);

		  return SerenityRest.rest()
				  .given()
				  .spec(ReuseableSpecifications.getGenericExpRequestSpec())
				  .log().all()
				  .when()
				  .get(endpoint)
				  .then().log().all();
	  } 
	   
	  public void verifyAgreements(Response resp, int count) {
		  JsonPath jsonPathEvaluator = resp.jsonPath();
		  int agreementCount = jsonPathEvaluator.getInt("count");
		  List<Object> agreementsList = jsonPathEvaluator.getList("agreements");
		  
		  assertEquals("Expected count " + count + " but got " + agreementCount,count, agreementCount);
	      assertEquals("Expected size should be 0" + " but got " + agreementsList.size(), 0 , agreementsList.size());
	  }

	public ValidatableResponse getOrders(String endpoint, String isPosted, String offset, String count) {

		RequestSpecification rs = SerenityRest.rest().given().spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.log().all().when();

		if (!isPosted.isEmpty()) {
			rs.queryParam("isPosted", isPosted);
		}

		if (!offset.isEmpty()) {
			rs.queryParam("offset", offset);
		}

		if (!count.isEmpty()) {
			rs.queryParam("count", count);
		}

		return rs.get(endpoint).then().log().all();

	}
	
	
	  public void validateAgreementsCount(Response resp) {
		  JsonPath jsonPathEvaluator = resp.jsonPath();
		  int agreementCount = jsonPathEvaluator.getInt("count");
		  List<Object> agreementsList = jsonPathEvaluator.getList("agreements");
		 
	      assertEquals("Count and number of Agreements are not equal", agreementCount, agreementsList.size());
	  }

	public ValidatableResponse processGET(String endpoint) throws URISyntaxException{

		RestUtil.setBaseURI(ACS_process_url);

		return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.get(endpoint)
				.then()
				.log().all();

	}

	public String setSubscriptionsEndpoint(String custNo) {
		String endpoint = "/customers/" + custNo + "/subscriptions";
		return endpoint;
	}
	
	public Response UserCall_ACS(String emailId) throws URISyntaxException
	{
		 end_pt = setEndpoint();
	        acsResponse = getCustomers(end_pt, "email", emailId)
	                .extract().response();
	        Object custNum = acsResponse.jsonPath().getList("customerNumber").get(0);
	        end_pt = setCustomerAgreementsEndpoint((String) custNum);
	        acsResponse = get(end_pt).extract().response();
	        return acsResponse;
	}
	
	public void Order_check_ACS(Map<String,String> AcsKmap, Response ACSResp) throws URISyntaxException
	{
    	Assert.assertEquals(ACSResp.jsonPath().getInt("count"), 1);
        Assert.assertNotEquals(ACSResp.jsonPath().getList("agreements").size(), 0);
        tUtil.writeOrdersemailUcid(AcsKmap);
    
	}
	
	public String setEndpointCustCustNoUsersUCC(String custNo) {
		String endpoint = "/customers/" + custNo + "/users/ucc";
		return endpoint;
	}
	
	public ValidatableResponse getInstAdminDetails(String endpoint, String fName, String lName, String email) throws URISyntaxException{

		RestUtil.setBaseURI(ACS_process_url);

		return	SerenityRest.rest()
			.given()
			.spec(ReuseableSpecifications.getGenericExpRequestSpec())
			.log().all()
			.when()
			.queryParam("firstName", fName)
			.queryParam("lastName", lName)
			.queryParam("email", email)
			.get(endpoint)
			.then()
			.log().all();
					
		}

	public String extractAndValidateUcid(Response response) {
		try {
			String ucId = response.jsonPath().getString("results[0].userName");
			String token = tUtil.getToken(ucId); // validate token
			return StringUtils.isBlank(token) ? null : ucId.toLowerCase();
		} catch (Exception ignored) {
			return null;
		}
	}
}

