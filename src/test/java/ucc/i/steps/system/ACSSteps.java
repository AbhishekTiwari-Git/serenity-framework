package ucc.i.steps.system;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import org.junit.Assert;
import ucc.i.method.acs.ACSGET;
import ucc.i.method.acs.ACSPOST;
import ucc.i.method.acs.ACSPUT;
import ucc.utils.ConnectionFactory;
import ucc.utils.DbUtils;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class ACSSteps {
	public static Response resp = null;
	static String end_pt = null;
	public static Map<String,String> kmap = new HashMap<String,String>();
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	DbUtils dbUtils = new DbUtils();
	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    private static String acsDBUrl =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("ACSDbUrl");

	List<String> get_Keys=new ArrayList<String>();
	List<String> get_Vals=new ArrayList<String>();
	List<String> customerList=new ArrayList<String>();

	// =================================================================================================================
	
	// Json Files
	String file_name = "ACS/ACS_Post.json";
	String filePutCustomer = "ACS/ACS_Update_POST.json";
	String filePostPayment = "ACS/ACS_Post_Payment.json";
	String filePostOrder = "ACS/ACS_Post_Place_Order.json";
	String filePostOrderInvalid = "ACS/ACS_Post_Place_Order_Invalid.json";
	String filePostControlGroup = "ACS/ACS_Post_Control_Group.json";
	String filePostUcid = "ACS/ACS_Post_ucid.json";
	String filePostCustomerData = "ACS/ACS_Post_Customer_Data.json";
	String filePostInvalidControlGroup = "ACS/ACS_Post_InvalidControlGroup.json";
	
	float orderBalanceDue;						// Order's balance due
	float paymentAmount;						// Payment amount
	static final float DIFF = 50;				// Difference between the order amount and payment amount
	boolean dPosted;							// for storing isPosted value
	int dOffset = 0;							// default offset
	int dCount = Integer.MAX_VALUE;				// default value
	int countOrdersTrue;						// number of orders isPosted = true
	int countOrdersFalse;						// number of orders isPosted = false
	
	@Steps
	ACSGET ACSGETSteps;
	@Steps
	ACSPOST ACSPOSTSteps;
	@Steps
	ACSPUT ACSPUTSteps;
	
	// =================================================================================================================
	// Status Code Verification
	// =================================================================================================================
	
	@Title("Verify Status Code") 
	@Then("^the request should return status '200'$")
	public void verifySts() {
		tUtil.verifyStatus(resp);
	}
	
    @Then("^The request should return '201' status$")
    public void the_request_should_return_201_status() throws Throwable {
    	assertEquals(resp.getStatusCode(), 201);
    }

    @Then("^The request should return '204' status$")
    public void the_request_should_return_204_status() throws Throwable {
    	assertEquals(resp.getStatusCode(), 204);
    }
    
    @Then("^The request should return '400' status$")
    public void the_request_should_return_400_status() throws Throwable {
    	assertEquals(resp.getStatusCode(), 400);
    }
    
    @Then("^The request should return '404' status$")
    public void the_request_should_return_404_status() throws Throwable {
    	assertEquals(resp.getStatusCode(), 404);
    }
    
    @Then("^response should return (.*) status$")
    public void checkResponseStatus(int sts) {
    		tUtil.verifyStatus(resp, sts);
    }
   
	@Title("Verify Status Code and message for invalid Data") 
	@Then("^user should get appropriate status (.*) and message (.*)$")
	public void verifyUserInvalidTypeSts(int sts, String msg) {
		tUtil.verify_msgCode(resp, msg, sts);
	}
	
    @Then("^the request should return status code (.*) with message (.*)$")
    public void the_request_should_return_status_code_with_message(int stcode, String msg) throws Throwable {
        tUtil.verify_msgCode(resp, msg, stcode);
    }
    
	// =================================================================================================================
	// ACS Customer creation and verification
	// =================================================================================================================
    
	@When("^I send a POST request to create a customer$")
	public void post_Customer() throws Exception {
		String email_value = tUtil.AppendTimestamp("automation@example.com");
    	String ucid = tUtil.generateRandomUcid(36);
    	String firstName = tUtil.AppendTimestamp("firstName");
    	String lastName = tUtil.AppendTimestamp("lastName");
    	jsonUtils.update_JSONValue(file_name, "$.ucid", ucid);
		jsonUtils.update_JSONValue(file_name,
			"$.address.email", email_value);
		jsonUtils.update_JSONValue(file_name, "$.address.name.firstName", firstName);
		jsonUtils.update_JSONValue(file_name, "$.address.name.lastName", lastName);
		
		// Storing fields name in kmap
		kmap.put("ucid", ucid);
		kmap.put("email", email_value);
		kmap.put("firstName", firstName);
		kmap.put("lastName", lastName);
		
		end_pt = ACSPOSTSteps.setEndpoint();
		resp = ACSPOSTSteps.post(file_name, end_pt).extract().response();
		
		String custNumber = ACSPOSTSteps.getCustomerNumber(resp);
		String addressCode = resp.jsonPath().getString("addressCode");
		kmap.put("addressCode", addressCode);
		kmap.put("customerNumber", custNumber);
		
		end_pt = ACSPOSTSteps.setEndpoint(custNumber);
	}
	
	@When("^I send a POST request to create a customer with empty postalAddress$")
	public void i_send_POST_request_create_customer_empty_postalAddress() throws Throwable {
		String file_name = "ACS/ACS_Post_Empty_PostalAddress.json";
		String email_value = tUtil.AppendTimestamp("automation@example.com");
		String ucid = tUtil.generateRandomUcid(36);
		jsonUtils.update_JSONValue(file_name, "$.ucid", ucid);
		jsonUtils.update_JSONValue(file_name, "$.address.email", email_value);

		createControlGroup();
		jsonUtils.update_JSONValue(file_name, "$.controlGroupDate", kmap.get("timeStamp"));
		end_pt = ACSPOSTSteps.setEndpoint();
		resp = ACSPOSTSteps.post(file_name, end_pt)
				.extract().response();
	}
	
	@When("^User sends a POST request to insert customer with invalid data for any of the email (.*) firstname (.*) lastName (.*)$")
	public void POST_request_to_insert_customer_with_invalid_data(String email, String fname, String lname) throws Exception {

		String email_value = tUtil.AppendTimestamp(email);
		jsonUtils.update_JSONValue(file_name, "$.address.email", email_value);
		jsonUtils.update_JSONValue(file_name, "$.address.name.firstName", fname);
		jsonUtils.update_JSONValue(file_name, "$.address.name.lastName", lname);

		end_pt = ACSPOSTSteps.setEndpoint();
		resp = ACSPOSTSteps.post(file_name, end_pt).extract().response();

	}
	
	@And("^I send a POST request to create a customer again with same data$")
	public void i_send_a_POST_request_to_create_a_customer_again_with_same_data() throws URISyntaxException {
		end_pt = ACSPOSTSteps.setEndpoint();
		resp = ACSPOSTSteps.post(file_name, end_pt).extract().response();
	}
	
	@When("^I send a POST request with exist ucid$")
	public void i_send_POST_request_with_exist_ucid() throws Exception {
		post_Customer();
		String userUcid = kmap.get("ucid");
		post_Customer();
		jsonUtils.update_JSONValue(filePostUcid, "$.ucid", userUcid);
		end_pt = ACSGETSteps.setUcidEndpoint(kmap.get("customerNumber"));
		resp = ACSPOSTSteps.post(filePostUcid,end_pt).extract().response();
	}
	
	@When("^I send a POST request to create a customer with empty ucid$")
	public void i_send_POST_request_create_customer_empty_ucid() throws Throwable {
		jsonUtils.update_JSONValue(file_name, "$.ucid", "");
		createControlGroup();
		jsonUtils.update_JSONValue(file_name, "$.controlGroupDate", kmap.get("timeStamp"));
		end_pt = ACSPOSTSteps.setEndpoint();
		resp = ACSPOSTSteps.post(file_name, end_pt)
				.extract().response();

		String ucid = tUtil.generateRandomUcid(36);
		jsonUtils.update_JSONValue(file_name, "$.ucid", ucid);
	}

	@When("^I send a POST request to create a customer with empty address email$")
	public void i_send_POST_request_create_customer_empty_address_email() throws Throwable {
		jsonUtils.update_JSONValue(file_name, "$.address.email", "");
		createControlGroup();
		jsonUtils.update_JSONValue(file_name, "$.controlGroupDate", kmap.get("timeStamp"));
		end_pt = ACSPOSTSteps.setEndpoint();
		resp = ACSPOSTSteps.post(file_name, end_pt)
				.extract().response();

		String email_value = tUtil.AppendTimestamp("automation@example.com");
		jsonUtils.update_JSONValue(file_name, "$.address.email", email_value);
	}

	@When("^I send a POST request to create a customer with empty email$")
	public void i_send_POST_request_create_customer_empty_email() throws Throwable {
		jsonUtils.update_JSONValue(file_name, "$.address.email", "");
		createControlGroup();
		jsonUtils.update_JSONValue(file_name, "$.controlGroupDate", kmap.get("timeStamp"));
		end_pt = ACSPOSTSteps.setEndpoint();
		resp = ACSPOSTSteps.post(file_name, end_pt)
				.extract().response();

		String email_value = tUtil.AppendTimestamp("automation@example.com");
		jsonUtils.update_JSONValue(file_name, "$.email", email_value);
	}

	@When("^I send a POST request to create a customer with empty phone and fax$")
	public void i_send_POST_request_create_customer_empty_phone_fax() throws Throwable {
		String phone = jsonUtils.getFromJSON(file_name, "$.address.phone");
		String fax = jsonUtils.getFromJSON(file_name, "$.address.fax");

		jsonUtils.update_JSONValue(file_name, "$.address.phone", "");
		jsonUtils.update_JSONValue(file_name, "$.address.fax", "");
		createControlGroup();
		jsonUtils.update_JSONValue(file_name, "$.controlGroupDate", kmap.get("timeStamp"));
		end_pt = ACSPOSTSteps.setEndpoint();
		resp = ACSPOSTSteps.post(file_name, end_pt)
				.extract().response();

		jsonUtils.update_JSONValue(file_name, "$.address.phone", phone);
		jsonUtils.update_JSONValue(file_name, "$.address.fax", fax);
	}

	@When("^I send a POST request to create a customer without company filed$")
	public void i_send_POST_request_create_customer_without_company_filed() throws Throwable {
		String file_name = "ACS/ACS_Post_No_Company.json";
		String email_value = tUtil.AppendTimestamp("automation@example.com");
		String ucid = tUtil.generateRandomUcid(36);
		jsonUtils.update_JSONValue(file_name, "$.ucid", ucid);
		jsonUtils.update_JSONValue(file_name, "$.address.email", email_value);
		createControlGroup();
		jsonUtils.update_JSONValue(file_name, "$.controlGroupDate", kmap.get("timeStamp"));
		end_pt = ACSPOSTSteps.setEndpoint();
		resp = ACSPOSTSteps.post(file_name, end_pt)
				.extract().response();
	}

	@When("^I send a POST request to create a customer with duplicate ucid$")
	public void i_send_POST_request_create_customer_duplicate_ucid() throws Throwable {
		String email_value = tUtil.AppendTimestamp("automation@example.com");
		jsonUtils.update_JSONValue(file_name, "$.email", email_value);

		createControlGroup();
		jsonUtils.update_JSONValue(file_name, "$.controlGroupDate", kmap.get("timeStamp"));
		end_pt = ACSPOSTSteps.setEndpoint();
		resp = ACSPOSTSteps.post(file_name, end_pt)
				.extract().response();
	}
	
	@Then("^customer should be created$")
	public void customernumber_should_be_created() throws Throwable {
		// Response status verification
		the_request_should_return_201_status();
		
		// Verification of customer creation through the get call and field assertion (customerNumber, email, last name, first name)
		end_pt = ACSGETSteps.setEndpoint(kmap.get("customerNumber"));
		resp = ACSGETSteps.get(end_pt).extract().response();
		JsonPath jsonpathEvaluator = resp.jsonPath();
		
		// Actual -- received info
		// Expected -- sent info
		String actualCustomerNum = jsonpathEvaluator.getString("customerNumber");
		String actualEmail = jsonpathEvaluator.getString("addresses[0].email");
		String actualFirstName = jsonpathEvaluator.getString("addresses[0].name.firstName");
		String actualLastName = jsonpathEvaluator.getString("addresses[0].name.lastName");
		
		Assert.assertEquals(String.format("The customer number should be %s but got %s", kmap.get("customerNumber"), actualCustomerNum),
				kmap.get("customerNumber"), actualCustomerNum);
		Assert.assertEquals(String.format("The email should be %s but got %s", kmap.get("email"), actualEmail),
				kmap.get("email"), actualEmail);
		Assert.assertTrue(String.format("The first name should be %s but got %s", kmap.get("firstName"), actualFirstName),
				kmap.get("firstName").equalsIgnoreCase(actualFirstName));
		Assert.assertTrue(String.format("The last name should be %s but got %s", kmap.get("lastName"), actualLastName),
				kmap.get("lastName").equalsIgnoreCase(actualLastName));
	}
	
	@Then("^order should be created with status code 201$")
    public void order_should_be_created_with_status_code_201() throws Throwable {
        tUtil.verifyStatus(resp, 201);
        kmap.put("orderNumber", resp.jsonPath().getString("orderNumber"));
    }
	
	@Then("^I get the customerNum in response$")
	public void i_get_the_customerNum_in_response() {
		String custNumber = ACSPOSTSteps.getCustomerNumber(resp);
		get_Keys.add("customerNumber");
		get_Vals.add(custNumber);
		kmap.put("customerNumber", custNumber);
	}
	
	// =================================================================================================================
	// ACS Customer retrieval and verification
	// =================================================================================================================	
    
	@Title("Get Request with Customer number : {0}")
	@When("^I send a GET request to ACS with query as customerNumber (.*)$")
	public void i_send_a_GET_request_with_the_custNumber(String no) throws URISyntaxException {
		end_pt = ACSGETSteps.setEndpoint();
		resp = ACSGETSteps.getCustomers(end_pt, "custNumber", no).extract().response();
	}
	
	@Title("Get Request with Customer number : {0}")
	@When("^I send a GET request to ACS with customerNumber (.*)$")
	public void i_send_a_GET_request_custNumber(String no) throws URISyntaxException {
		end_pt = ACSGETSteps.setEndpoint(no);
		resp = ACSGETSteps.get(end_pt).extract().response();
	}

	@Title("Get Request with Customer email : {0}")
	@When("^I send a GET request to ACS with email (.*) as query$")
	public void i_send_a_GET_request_with_the_email(String mail) throws URISyntaxException {
		end_pt = ACSGETSteps.setEndpoint();
		resp = ACSGETSteps.getCustomers(end_pt, "email", mail).extract().response();
	}
	
	@When("^I send a GET request with the query for invalid data (.*) (.*)")
	public void i_send_a_GET_request_with_the_invalid_email_or_no(String query, String ID) throws URISyntaxException {
		end_pt = ACSGETSteps.setEndpoint();
		resp = ACSGETSteps.getCustomers(end_pt, query, ID).extract().response();
	}
	
	@When("^I send a GET request with the invalid data (.*)")
	public void i_send_a_GET_request_with_the_invalid_no(String custNumber) throws URISyntaxException {
		end_pt = ACSGETSteps.setEndpoint(custNumber);
		resp = ACSGETSteps.get(end_pt)
				 .extract().response();
	}
	
	@Then("^I make a Get request to get details of user with customer number of newly added customer$")
	public void get_details_of_newly_added_customer() throws URISyntaxException {
		resp = ACSGETSteps.getCustomers(end_pt,"custNumber", kmap.get("customerNumber")).extract().response();
	}
	
	@Then("^customerno and email returned from GET call should match with created customer$")
	public void customerno_and_email_returned_from_GET_call_should_match_with_created_customer() {
		ACSPOSTSteps.verify_Customer(kmap, end_pt);
	}
	
	@Then("^customerno and email returned from GET call should match with the details of created customer$")
	public void customerno_and_email_returned_from_GET_call_should_match_with_the_details_of_created_customer() {
		kmap = tUtil.store_jsonValues(get_Keys, get_Vals);
	    ACSPOSTSteps.verifyCustomerInfo(kmap, resp);
	}
	
	@Then("^response should match for specified query (.*) with the specified ID (.*) msg (.*) and status (.*)$")
	public void verify_invalidData(String query, String ID, String msg, int status) {
		ACSGETSteps.verify_msg(end_pt, query, ID, msg, status);	
	}
	
	@Then("^response should match with the specified ID (.*) msg (.*) and status (.*)$")
	public void verify_invalidDta(String ID, String msg, int status) {
		ACSGETSteps.verify_msg(end_pt, ID, msg, status);
	}
	
	@When("^I do a POST request to ACS to get the list of matching customers with valid data$")
	public void i_do_POST_request_with_valid_cusotmer_data() throws Exception {
		generate_customer_data();

		end_pt = ACSPOSTSteps.setEndpointCustomerMatches();
		resp = ACSPOSTSteps.post(filePostCustomerData, end_pt)
				.extract().response();
	}

	@When("^I do a POST request to ACS to get the list of matching customers with invalid customer$")
	public void i_do_POST_request_with_invalid_cusotmer() throws Exception {
		generate_customer_data();
		jsonUtils.update_JSONValue(filePostCustomerData, "$.firstName",
				tUtil.generateRandomUcid(36));

		end_pt = ACSPOSTSteps.setEndpointCustomerMatches();
		resp = ACSPOSTSteps.post(filePostCustomerData, end_pt)
				.extract().response();
	}

	@When("^I do a POST request to ACS to get the list of matching customers with invalid data$")
	public void i_do_POST_request_with_invalid_cusotmer_data() throws Exception {
		generate_customer_data();
		jsonUtils.update_JSONValue(filePostCustomerData, "$.firstName", "");

		end_pt = ACSPOSTSteps.setEndpointCustomerMatches();
		resp = ACSPOSTSteps.post(filePostCustomerData, end_pt)
				.extract().response();
	}

	@When("^The response should contain list of matching customer$")
	public void response_should_contain_list_matching_customer() {
		JsonPath jsonPathEvaluator = resp.jsonPath();
		int count = jsonPathEvaluator.getInt("count");

		Assert.assertTrue(count > 0);
	}
	
	// =================================================================================================================
	// ACS Customer update and verification
	// =================================================================================================================
	
	@When("^I send a PUT request to update a customer$")
	public void put_Customer() throws Exception {
		String newEmail = tUtil.AppendTimestamp("automation@example.com");
		jsonUtils.update_JSONValue(filePutCustomer, "$.ucid", kmap.get("ucid"));
		jsonUtils.update_JSONValue(filePutCustomer, "$.addresses[0].email", newEmail);
		jsonUtils.update_JSONValue(filePutCustomer, "$.email", newEmail);
		
		kmap.put("email", newEmail);
		
		end_pt = ACSPOSTSteps.setEndpoint(kmap.get("customerNumber"));
		resp = ACSPUTSteps.updateCustomer(filePutCustomer, end_pt).extract().response();

	}
	
	@When("^I send a POST request to update ucid$")
	public void i_send_POST_Request_update_ucid() throws Exception {
		String ucid = tUtil.generateRandomUcid(36);
		jsonUtils.update_JSONValue(filePostUcid, "$.ucid", ucid);
		end_pt = ACSGETSteps.setUcidEndpoint(kmap.get("customerNumber"));
		resp = ACSPOSTSteps.post(filePostUcid,end_pt).extract().response();
	}
	
	@Then("^customer should be updated$")
	public void customernumber_should_be_updated() throws Throwable {
		// Status code verification
		the_request_should_return_204_status();
		
		// Verifying the fields are updated (customerNumber, email, last name, first name)
		end_pt = ACSGETSteps.setEndpoint(kmap.get("customerNumber"));
		resp = ACSGETSteps.get(end_pt).extract().response();
		JsonPath jsonpathEvaluator = resp.jsonPath();

		// Actual -- received info
		// Expected -- sent info
		String actualCustomerNum = jsonpathEvaluator.getString("customerNumber");
		List<String> loActualEmail = jsonpathEvaluator.getList("addresses.email");
		List<String> loActualFirstName = jsonpathEvaluator.getList("addresses.name.firstName");
		List<String> loActualLastName = jsonpathEvaluator.getList("addresses.name.lastName");

		Assert.assertEquals(String.format("The customer number should be %s but got %s", kmap.get("customerNumber"),
				actualCustomerNum), kmap.get("customerNumber"), actualCustomerNum);
		Assert.assertTrue(String.format("The email %s should be listed but it is not", kmap.get("email")), 
				loActualEmail.contains(kmap.get("email")));
		Assert.assertTrue(String.format("The first name %s should be listed but it is not", kmap.get("firstName")),
				loActualFirstName.stream().anyMatch(x -> x.equalsIgnoreCase(kmap.get("firstName"))));
		Assert.assertTrue(String.format("The last name %s should be listed but it is not", kmap.get("lastName")),
				loActualLastName.stream().anyMatch(x -> x.equalsIgnoreCase(kmap.get("lastName"))));
		
	}

//	@And("^email updated for given customerNo should match with the returned email from GET call for same customer no$")
//	public void GET_call_should_match_with_created_customer() {
//		ACSPUTSteps.verify_UpdatedEmailCustomer(kmap, end_pt, kmap.get("customerNumber"));
//	}
	
	
	// =================================================================================================================
	// ACS Order creation and verification
	// =================================================================================================================
	
	public void placeOrder() throws URISyntaxException {
		end_pt = ACSPOSTSteps.setEndpointOrders();
    	resp = ACSPOSTSteps.post(filePostOrder, end_pt)
    			.extract().response();
    	
    	JsonPath jsonpathEvaluator = resp.jsonPath();
    	orderBalanceDue = jsonpathEvaluator.getFloat("balanceDue");
    	kmap.put("billingOrg", jsonpathEvaluator.getString("billingOrg"));
    	kmap.put("currencyCode", jsonpathEvaluator.getString("currencyCode"));
    	System.out.println(String.format("BillingOrg = %s, CurrencyCode = %s", kmap.get("billingOrg"), kmap.get("currencyCode")));
    	
	}
	
	@Then("^I send the post request to create order for newly created customer$")
    public void i_send_the_post_request_to_create_order_for_newly_created_customer() throws Throwable {
    	jsonUtils.update_JSONValue(filePostOrder, "$.billTo.customerNumber", kmap.get("customerNumber"));
    	jsonUtils.update_JSONValue(filePostOrder, "$.orderLines[0].access.participants[0].customerNumber", kmap.get("customerNumber"));
    	createControlGroup();
    	end_pt = ACSPOSTSteps.setEndpointOrders();
    	resp = ACSPOSTSteps.post(filePostOrder, end_pt).extract().response();
    }
	
	@When("^I get the order number in response$")
    public void i_get_the_order_number_in_response() {
		kmap.put("orderNumber", ACSPOSTSteps.getOrderNumber(resp));
    }
	
	@When("^I do a POST request to create an order$")
	public void i_do_POST_request_create_order() throws Throwable {
		jsonUtils.update_JSONValue(filePostOrder, "$.billTo.customerNumber", kmap.get("customerNumber"));
		jsonUtils.update_JSONValue(filePostOrder, "$.orderLines[0].access.participants[0].customerNumber", kmap.get("customerNumber"));
		createControlGroup();
		end_pt = ACSPOSTSteps.setEndpointOrders();
		resp = ACSPOSTSteps.post(filePostOrder, end_pt)
				.extract().response();
		kmap.put("orderNumber", resp.jsonPath().getString("orderNumber"));
	}
	
	@When("^user sends a POST request to create a valid order with productId (.*) and priceCode (.*)$")
    public void user_sends_a_post_request_to_create_a_valid_order_with_productid_and_pricecode(String productId, String priceCode) throws Throwable {
        jsonUtils.update_JSONValue(filePostOrder, "orderLines[0].itemNumber", productId);
        jsonUtils.update_JSONValue(filePostOrder, "orderLines[0].priceCode", priceCode);
        
        restoreDefault();
        placeOrder();
        
        kmap.put("productId", productId);
    }
	
	@When("^I do a POST request to create an order with invalid email$")
	public void i_do_POST_request_create_order_with_invalid_email() throws Throwable {
		String email_value = tUtil.AppendTimestamp("qa@qa.qa");
		jsonUtils.update_JSONValue(filePostOrderInvalid, "$.billTo.customerNumber", kmap.get("customerNumber"));
		jsonUtils.update_JSONValue(filePostOrderInvalid, "$.orderLines[0].access.participants[0].customerNumber", kmap.get("customerNumber"));
		jsonUtils.update_JSONValue(filePostOrderInvalid, "$.orderLines[0].access.participants[0].email", "qaqa.qa");

		createControlGroup();
		jsonUtils.update_JSONValue(filePostOrderInvalid, "$.controlGroupDate", kmap.get("timeStamp"));
		end_pt = ACSPOSTSteps.setEndpointOrders();
		resp = ACSPOSTSteps.post(filePostOrderInvalid, end_pt).extract().response();

		jsonUtils.update_JSONValue(filePostOrderInvalid, "$.orderLines[0].access.participants[0].email", email_value);
	}

	@When("^I do a POST request to create an order with invalid itemNumber$")
	public void i_do_POST_request_with_invalid_itemNumber() throws Throwable {
		String itemNumber = jsonUtils.getFromJSON(filePostOrderInvalid, "$.orderLines[0].itemNumber");
		jsonUtils.update_JSONValue(filePostOrderInvalid, "$.orderLines[0].itemNumber", "");

		createControlGroup();
		jsonUtils.update_JSONValue(filePostOrderInvalid, "$.controlGroupDate", kmap.get("timeStamp"));
		end_pt = ACSPOSTSteps.setEndpointOrders();
		resp = ACSPOSTSteps.post(filePostOrderInvalid, end_pt).extract().response();

		jsonUtils.update_JSONValue(filePostOrderInvalid, "$.orderLines[0].itemNumber", itemNumber);
	}

	@When("^I do a POST request to create an order with invalid priceCode$")
	public void i_do_POST_request_with_invalid_priceCode() throws Throwable {
		String actual_priceCode = jsonUtils.getFromJSON(filePostOrderInvalid, "$.orderLines[0].priceCode");
		String priceCode_value = tUtil.AppendTimestamp(actual_priceCode);
		jsonUtils.update_JSONValue(filePostOrderInvalid, "$.orderLines[0].priceCode", priceCode_value);

		createControlGroup();
		jsonUtils.update_JSONValue(filePostOrderInvalid, "$.controlGroupDate", kmap.get("timeStamp"));
		end_pt = ACSPOSTSteps.setEndpointOrders();
		resp = ACSPOSTSteps.post(filePostOrderInvalid, end_pt).extract().response();

		jsonUtils.update_JSONValue(filePostOrderInvalid, "$.orderLines[0].priceCode", actual_priceCode);
	}

	@When("^I do a POST request to create an order with invalid accessPeriod$")
	public void i_do_POST_request_with_invalid_accessPeriod() throws Throwable {
		String actual_accessPeriod = jsonUtils.getFromJSON(filePostOrderInvalid, "$.orderLines[0].access.accessPeriod");
		String accessPeriod_value = tUtil.AppendTimestamp(actual_accessPeriod);
		jsonUtils.update_JSONValue(filePostOrderInvalid, "$.billTo.customerNumber", kmap.get("customerNumber"));
		jsonUtils.update_JSONValue(filePostOrderInvalid, "$.orderLines[0].access.participants[0].customerNumber", kmap.get("customerNumber"));
		jsonUtils.update_JSONValue(filePostOrderInvalid, "$.orderLines[0].access.accessPeriod", accessPeriod_value);

		createControlGroup();
		jsonUtils.update_JSONValue(filePostOrderInvalid, "$.controlGroupDate", kmap.get("timeStamp"));
		end_pt = ACSPOSTSteps.setEndpointOrders();
		resp = ACSPOSTSteps.post(filePostOrderInvalid, end_pt).extract().response();

		jsonUtils.update_JSONValue(filePostOrderInvalid, "$.orderLines[0].access.accessPeriod", actual_accessPeriod);
	}
	
	// =================================================================================================================
	// ACS Order retrieval and verification
	// =================================================================================================================
	
	@When("^user send a GET call to get the details of orders done via customer having customer number$")
    public void user_send_a_GET_call_to_get_the_details_of_orders_done_via_customer_having_customer_number() throws Throwable {
    	String custNumber = jsonUtils.getFromJSON(filePostOrder, "$.billTo.customerNumber"); //vulnerable
    	kmap.put("customerNumber", custNumber);
    	end_pt = ACSGETSteps.setCustomerOrdersEndpoint(custNumber);
    	resp = ACSGETSteps.get(end_pt).extract().response();
    }
	
    @When("^user sends a GET request with invalid params customer number (.*), isPosted (.*), offset (.*), count (.*)$")
    public void user_sends_a_get_request_with_invalid_params_customer_number_isposted_offset_count(String custNo, String isPosted, String offset, String count) throws Throwable {
    	end_pt = ACSGETSteps.setOrdersEndpoint(custNo);
    	resp = ACSGETSteps.getOrders(end_pt, isPosted, offset, count).extract().response();
    	
    }
    
    @When("^I do a GET request to customer orders$")
	public void i_do_GET_request_customer_orders() throws URISyntaxException {
		end_pt = ACSGETSteps.setCustomerOrdersEndpoint(kmap.get("customerNumber"));
		resp = ACSGETSteps.get(end_pt).extract().response();
	}
    
    @When("^user sends a GET request with valid params customer number (.*), isPosted (.*), offset (.*), count (.*)$")
    public void user_sends_a_get_request_with_valid_params_customer_number_isposted_offset_count(String custNo, String isPosted, String offset, String count) throws Throwable {
    	
    	end_pt = ACSGETSteps.setOrdersEndpoint(custNo);
    	
    	if (!isPosted.isEmpty()) {
    		dPosted = Boolean.valueOf(isPosted);
    	}
    	
    	if (!offset.isEmpty()) {
    		dOffset = Integer.valueOf(offset);
    	}
    	
    	if (!count.isEmpty()) {
    		dCount = Integer.valueOf(count);
    	}
    	
    	resp = ACSGETSteps.getOrders(end_pt, isPosted, offset, count).extract().response();
    	
    }
    
    @When("^user sends a GET request with valid params customer number (.*), True isPosted, offset (.*), count (.*)$")
    public void user_sends_a_get_request_with_valid_params_customer_number_isposted_true_offset_count(String custno, String offset, String count) throws Throwable {
    	user_sends_a_get_request_with_valid_params_customer_number_isposted_offset_count(custno, "true", offset, count);
    }
    
    @When("^user sends a GET request with valid params customer number (.*), False isPosted, offset (.*), count (.*)$")
    public void user_sends_a_get_request_with_valid_params_customer_number_isposted_false_offset_count(String custno, String offset, String count) throws Throwable {
    	user_sends_a_get_request_with_valid_params_customer_number_isposted_offset_count(custno, "false", offset, count);
    }
    
    @When("^user sends a GET request with valid params customer number (.*), unspecified isPosted, offset (.*), count (.*)$")
    public void user_sends_a_get_request_with_valid_params_customer_number_unspecified_isposted_offset_count(String custno, String offset, String count) throws Throwable {
    	user_sends_a_get_request_with_valid_params_customer_number_isposted_offset_count(custno, "", offset, count);
    }
    
    @Then("^the request should return status code (.*) with proper number of orders$")
    public void the_request_should_return_status_code_with_proper_number_of_orders(int stcode) throws Throwable {
        tUtil.verifyStatus(resp, stcode);
        
        JsonPath jsonpathEvaluator = resp.jsonPath();
        int actualCount = jsonpathEvaluator.getInt("count");
        List<String> actualLoOrders = jsonpathEvaluator.getList("orders");
        
        int diffCountOffset = (actualCount < dOffset) ? 0 : actualCount - dOffset; // number of orders to show w/o count
        int expectedToShow = (diffCountOffset < dCount) ? diffCountOffset : dCount;
        System.out.println(actualLoOrders.size());
        Assert.assertEquals(expectedToShow, actualLoOrders.size());   
    }
    
    @Then("^the request should return status code (.*) with proper number of orders for isPosted True$")
    public void the_request_should_return_status_code_with_proper_number_of_orders_for_isposted_true(int stcode) throws Throwable {
    	the_request_should_return_status_code_with_proper_number_of_orders(stcode);
    	
    	countOrdersTrue = resp.jsonPath().getInt("count");
    }
    
    @Then("^the request should return status code (.*) with proper number of orders for isPosted False$")
    public void the_request_should_return_status_code_with_proper_number_of_orders_for_isposted_false(int stcode) throws Throwable {
    	the_request_should_return_status_code_with_proper_number_of_orders(stcode);
    	
    	countOrdersFalse = resp.jsonPath().getInt("count");
    }
    
    @Then("^the complete count of orders should be equal to isPosted True plus isPosted False$")
    public void the_complete_count_of_orders_should_be_equal_to_isposted_true_isposted_false() throws Throwable {
        JsonPath jsonpathEvaluator = resp.jsonPath();
        
        int totalCount = jsonpathEvaluator.getInt("count");
        
        Assert.assertEquals(totalCount, countOrdersFalse + countOrdersTrue);
    }
    
    @Then("^Order returned from GET request$")
	public void order_returned_from_GET_request() {
		String orderNumber = (String) resp.jsonPath().getList("orders.orderNumber").get(0);
		Assert.assertEquals(kmap.get("orderNumber"), orderNumber);
	}
    
    @Then("^Orders returned from GET request$")
	public void orders_returned_from_GET_request() {
		int orderNumber = resp.jsonPath().getInt("count");
		Assert.assertEquals(2, orderNumber);
	}
	
	// =================================================================================================================
	// ACS Agreements retrieval and verification
	// =================================================================================================================
	
	@When("^user send a GET call to get the details of agreements done via customer having customer number$")
    public void user_send_a_GET_call_to_get_the_details_of_agreements_done_via_customer_having_customer_number() {
    	String custNumber = jsonUtils.getFromJSON(filePostOrder, "$.billTo.customerNumber");
    	kmap.put("customerNumber", custNumber);
        end_pt = ACSGETSteps.setCustomerAgreementsEndpoint(kmap.get("customerNumber"));
        resp = ACSGETSteps.get(end_pt).extract().response();
    }
	
	@When("^user sends a GET request to get the agreementId of the created customer for ordered product$")
    public void user_sends_a_get_request_to_get_the_agreementid_of_the_created_customer_for_ordered_product() throws Throwable {
        end_pt = ACSGETSteps.setEnpointAgreementProduct(kmap.get("customerNumber"), kmap.get("productId"));
        resp = ACSGETSteps.get(end_pt).extract().response();
    }
	
	@When("^User sends a request to get subscription information of newly created customer$")
    public void get_subscription_information_for_customer_with_zero_order() {
    	end_pt = ACSGETSteps.setCustomerAgreementsEndpoint(kmap.get("customerNumber"));
    	resp = ACSGETSteps.get(end_pt).extract().response();
    }
	
	@When("^User sends a request to get subscription information with invalid customer number (.*)$")
    public void get_subscription_information_with_invalid_customer_number(String custNum) {	
    	end_pt = ACSGETSteps.setCustomerAgreementsEndpoint(custNum);
    	resp = ACSGETSteps.get(end_pt).extract().response();
    }
	
    @When("^User sends a request to get subscription information with empty customer number$")
    public void get_subscription_information_without_customer_number() {
    	end_pt = ACSGETSteps.setEmptyCustomerAgreementsEndpoint();
    	resp = ACSGETSteps.get(end_pt).extract().response();
    }
    
    @When("^user sends a GET request to get the agreementId using invalid data for customer (.*)$")
    public void user_sends_a_get_request_to_get_the_agreementid_using_invalid_data_for_customer(String custNo) throws Throwable {
        end_pt = ACSGETSteps.setEnpointAgreementProduct(custNo, kmap.get("productId"));
        resp = ACSGETSteps.get(end_pt).extract().response();
    }
	
	@When("^user sends a GET request to get the agreementId using invalid data for product (.*)$")
    public void user_sends_a_get_request_to_get_the_agreementid_using_invalid_data_for_product(String invProductId) throws Throwable {
		end_pt = ACSGETSteps.setEnpointAgreementProduct(kmap.get("customerNumber"), invProductId);
        resp = ACSGETSteps.get(end_pt).extract().response();
    }
    
    @Then("^Response should contain empty list of agreements and (.*) count$")
    public void response_should_contain_empty_list_of_agreements(int count) {
        ACSGETSteps.verifyAgreements(resp, count);
    }
    
    @Then("^response should contain (.*) count$")
    public void response_should_contain_count(int count) {
    	tUtil.verifyCountInResponse(resp, count);
    }

    @Then("^the count and number of agreements returned in response should match$")
    public void the_count_and_number_of_agreements_returned_in_response_should_match() {
    	ACSGETSteps.validateAgreementsCount(resp);
    }
    
    @Then("^order number returned in response should be present in List$")
    public void order_number_returned_in_response_should_be_present_in_List() {
    	ACSGETSteps.verifyOrderNumber(resp, kmap.get("orderNumber"));
    }
    
    @Then("'electronicPaymentId' should be present for every agreements and 'billingOrganization' should be present if debits field is not an empty array$")
    public void should_be_present_for_every_agreements_and_should_be_present_if_debits_field_is_not_an_empty_array() {
    	ACSGETSteps.verifyPaymentDetails(resp);
    }
    
    @Then("^the proper agreement is returned with 200 code$")
    public void the_proper_agreement_is_returned_with_200_code() throws Throwable {
        tUtil.verifyStatus(resp, 200);
        String outAgreemntId = resp.jsonPath().getString("agreementId");
        
        // verify that the order is in the list of agreements 
        // verify that if there are several product like this this is isposted true or last valid
        end_pt = ACSGETSteps.setCustomerAgreementsEndpoint(kmap.get("customerNumber"));
        resp = ACSGETSteps.get(end_pt).extract().response();
        List<String> loAgreements = resp.jsonPath().getList("agreements.agreementId");
        List<List<String>> loloProductCode = resp.jsonPath().getList("agreements.accessPoints.productCode");
        // Retrieving from list of list of string to list of strings
        List<String> loProductCode = loloProductCode.stream().map(x -> x.get(0)).collect(Collectors.toList());
        
        Assert.assertTrue(loAgreements.contains(outAgreemntId));
        String expectedProductCode = jsonUtils.getFromJSON(filePostOrder, "orderLines[0].itemNumber");
        System.out.println(loProductCode);
        Assert.assertTrue(loProductCode.contains(expectedProductCode));
        
        // Get the index of the agreements that match the productCode
        List<Integer> loIndexProduct = new ArrayList<Integer>();
        for (int i = 0; i < loProductCode.size(); i++) {
        	if (loProductCode.get(i).equals(expectedProductCode)) {
        		loIndexProduct.add(i);
        	}
        }
        Assert.assertTrue(loIndexProduct.size() != 0);
        
        List<Integer> loPostedTrue = new ArrayList<Integer>(); // list of indexes for posted orders
        int indOut = loIndexProduct.get(0); // index of the agreement to return
        Date latestDate = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss")
        		.parse(resp.jsonPath().getString(String.format("agreements[%d].agreementExpires", indOut))
        				.replaceAll("T", " ").replaceAll("Z", ""));
        for (int ind : loIndexProduct) {
        	// Adding to list of indexes for posted orders
        	if (resp.jsonPath().getBoolean(String.format("agreements[%d].isPosted", ind))) {
        		loPostedTrue.add(ind);
        	}
        	Date currDate = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss")
            		.parse(resp.jsonPath().getString(String.format("agreements[%d].agreementExpires", ind))
            				.replaceAll("T", " ").replaceAll("Z", ""));
        	if (currDate.after(latestDate)) {
        		latestDate = currDate;
        		indOut = ind;
        	}
        }

        // If there are posted orders => return lates AgreementExpires date
		if (loPostedTrue.size() != 0) {
			indOut = loPostedTrue.get(0);
			latestDate = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss")
					.parse(resp.jsonPath().getString(String.format("agreements[%d].agreementExpires", indOut))
							.replaceAll("T", " ").replaceAll("Z", ""));
			for (int ind : loPostedTrue) {
				Date currDate = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss")
						.parse(resp.jsonPath().getString(String.format("agreements[%d].agreementExpires", ind))
								.replaceAll("T", " ").replaceAll("Z", ""));
				if (currDate.after(latestDate)) {
					latestDate = currDate;
					indOut = ind;
				}
			}
		}
		Assert.assertTrue(loAgreements.get(indOut).equals(outAgreemntId)); 
    }
	
	// =================================================================================================================
	// ACS Credit card retrieval and verification
	// =================================================================================================================
	
	@When("^user sends a valid GET request to get the credit card info$")
    public void user_sends_a_valid_get_request_to_get_the_credit_card_info() throws Throwable {
        end_pt = ACSGETSteps.setCreditCardEndpoint(kmap.get("customerNumber"), kmap.get("billingOrg"), kmap.get("currencyCode"), kmap.get("paymentId"));
        resp = ACSGETSteps.get(end_pt).extract().response();
    }
	
    @When("^user sends a GET request with invalid data for customerNumber (.*) billiongOrg (.*) currencyCode (.*) paymentID (.*)$")
    public void user_sends_a_get_request_with_invalid_data_for_customernumber_billiongorg_currencycode_paymentid(String custNumber, String billorg, String currcode, String paymentid) throws Throwable {
    	end_pt = ACSGETSteps.setCreditCardEndpoint(custNumber, billorg, currcode, paymentid);
        resp = ACSGETSteps.get(end_pt).extract().response();
    }
    
    @Then("^the request should return credit card info with the status code 200$")
    public void the_request_should_return_credit_card_info_with_the_status_code_200() throws Throwable {
    	verifySts();
        
        // verify the following info: cardMask, code, expiremonth and year, electronicpaymentid
        JsonPath jsonpathEvaluator = resp.jsonPath();
        String actualCardNumber = jsonpathEvaluator.getString("cardMask");
        String actualPaymentMethodCode = jsonpathEvaluator.getString("paymentMethod.code");
        String actualExpireMonth = jsonpathEvaluator.get("expireMonth").toString();
        String actualExpireYear = jsonpathEvaluator.get("expireYear").toString();
        String actualElectronicPaymentId = jsonpathEvaluator.get("electronicPaymentId");
        
        
        Assert.assertEquals("Card number should be " + kmap.get("cardNumber") + " but got " + actualCardNumber,
        		kmap.get("cardNumber"), actualCardNumber);
        Assert.assertEquals("Payment method code should be " + kmap.get("paymentMethodCode") + " but got " + actualPaymentMethodCode,
        		kmap.get("paymentMethodCode"), actualPaymentMethodCode);
        Assert.assertEquals("Expire month should be " + kmap.get("expireMonth") + " but got " + actualExpireMonth,
        		kmap.get("expireMonth"), actualExpireMonth);
        Assert.assertEquals("Expire year should be " + kmap.get("expireYear") + " but got " + actualExpireYear,
        		kmap.get("expireYear"), actualExpireYear);
        Assert.assertEquals("Electronic payment id should be " + kmap.get("paymentId") + " but got " + actualElectronicPaymentId,
        		kmap.get("paymentId"), actualElectronicPaymentId);
        
    }
	
	// =================================================================================================================
	// ACS Control group creation and verification
	// =================================================================================================================
	
	public void createControlGroup() throws Throwable {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		jsonUtils.update_JSONValue(filePostControlGroup, "$.CTGDate", timeStamp);
		jsonUtils.update_JSONValue(filePostOrder, "$.controlGroupDate", timeStamp);
		
		end_pt = ACSPOSTSteps.setEndpointControlGroup();
		resp = ACSPOSTSteps.post(filePostControlGroup, end_pt)
				.extract().response();
		kmap.put("timeStamp", timeStamp);
		the_request_should_return_201_status();
	}
	
	@When("^User sends a POST request to ACS with invalid control group$")
	public void user_sends_a_POST_request_to_ACS_with_invalid_control_group() throws Throwable {
		end_pt = ACSPOSTSteps.setEndpointOrders();
		resp = ACSPOSTSteps.post(filePostInvalidControlGroup, end_pt).extract().response();
	}
	
	// =================================================================================================================
	// ACS Payment/Commit creation and verification
	// =================================================================================================================
	
	@Given("^the payment is posted for existing customer in ACS$")
    public void the_payment_is_posted_for_existing_customer_in_acs() throws Throwable {
    	user_sends_a_post_request_to_insert_new_payment_for_order_with_valid_data();
    	
    	// Getting an electronicPaymentId from agreements
    	user_send_a_GET_call_to_get_the_details_of_agreements_done_via_customer_having_customer_number();
    	
    	kmap.put("paymentId", resp.jsonPath().getString("agreements[0].electronicPaymentId"));
    	System.out.println("Electronic paymentId = " + kmap.get("paymentId"));
    }
	
	@When("^user sends a POST request to commit the order with valid data$")
    public void user_sends_a_post_request_to_commit_the_order_with_valid_data() throws Throwable {
        String validOrderNumber = kmap.get("orderNumber");
		
        end_pt = ACSPOSTSteps.setEndpointCommitOrder(validOrderNumber);
		resp  = ACSPOSTSteps.post(filePostPayment, end_pt).extract().response();
		
    }
	
	@When("^User sends a POST request to insert new payment for order with valid data$")
    public void user_sends_a_post_request_to_insert_new_payment_for_order_with_valid_data() throws Throwable {
    	// Placing an order
    	setUpPayment();
    	String orderNo = resp.jsonPath().getString("orderNumber");
    	kmap.put("orderNumber", orderNo);
    	end_pt = ACSPOSTSteps.setEndpointOrdersPayment(orderNo);
        resp = ACSPOSTSteps.post(filePostPayment, end_pt)
        		.extract().response();
        
        
        // TODO Update getFromJSON method to return not only string?
        paymentAmount = Float.parseFloat(jsonUtils.getFromJSON(filePostPayment, "$.creditCards..amount").replace("[", "").replace("]", ""));
        
        // Putting card info into map
        String cardNumber = jsonUtils.getFromJSON(filePostPayment, "$.creditCards..card.number").replace("[", "").replace("]", "");
        String paymentMethodCode = jsonUtils.getFromJSON(filePostPayment, "$.creditCards..card.paymentMethodCode").replace("[", "").replace("]", "");
        String expireMonth = jsonUtils.getFromJSON(filePostPayment, "$.creditCards..card.expireMonth").replace("[", "").replace("]", "");
        String expireYear = jsonUtils.getFromJSON(filePostPayment, "$.creditCards..card.expireYear").replace("[", "").replace("]", "");
        kmap.put("cardNumber", cardNumber);
        kmap.put("paymentMethodCode", paymentMethodCode);
        kmap.put("expireMonth", expireMonth);
        kmap.put("expireYear", expireYear);
        
    }
	
	@When("^User sends a POST request to insert new payment for order with valid data and amount which is less than the amountDue$")
    public void user_sends_a_post_request_to_insert_new_payment_for_order_with_valid_data_and_amount_which_is_less_than_the_amountdue() throws Throwable {
    	setUpPayment();
    	
    	String orderNo = resp.jsonPath().getString("orderNumber");
    	kmap.put("orderNumber", orderNo);
    	float lessNum = orderBalanceDue - DIFF;
    	float less = lessNum > 0 ? lessNum : 1;   	
    			
    	// POTENTIAL USE OF FILENAME
    	end_pt = ACSPOSTSteps.setEndpointOrdersPayment(orderNo);
    	jsonUtils.update_JSONValue(filePostPayment, "$.creditCards[0].amount", less);
    	resp = ACSPOSTSteps.post(filePostPayment, end_pt)
        		.extract().response();
    	
    	paymentAmount = less;
    }
	
	@When("^User sends a POST request to insert new payment for order with valid data and amount which is more than the amountDue$")
    public void user_sends_a_post_request_to_insert_new_payment_for_order_with_valid_data_and_amount_which_is_more_than_the_amountdue() throws Throwable {
    	setUpPayment();
    	String orderNo = resp.jsonPath().getString("orderNumber");
    	kmap.put("orderNumber", orderNo);
    	
    	float more = orderBalanceDue + DIFF;
    	
    	end_pt = ACSPOSTSteps.setEndpointOrdersPayment(orderNo);
    	jsonUtils.update_JSONValue(filePostPayment, "$.creditCards[0].amount", more);
    	
    	resp = ACSPOSTSteps.post(filePostPayment, end_pt)
        		.extract().response();
    	
    	paymentAmount = more;
    }
	
	@When("^User sends a POST request to insert new payment for order with invalid data for token number securityCode and paymentMethodCode$")
    public void user_sends_a_POST_request_to_insert_new_payment_for_order_with_invalid_data_for_token_number_securityCode_and_paymentMethodCode() throws Throwable {
    	setUpPayment();
    	String orderNo = resp.jsonPath().getString("orderNumber");
    	kmap.put("orderNumber", orderNo);
    	
    	end_pt = ACSPOSTSteps.setEndpointOrdersPayment(orderNo);
    	
    	String invalidToken = "";
    	String invalidSecCode = "";
    	String invalidPayMethod = "VSA";
    	
    	jsonUtils.update_JSONValue(filePostPayment, "$.creditCards..card.token", invalidToken);
    	jsonUtils.update_JSONValue(filePostPayment, "$.creditCards..securityCode", invalidSecCode);
    	jsonUtils.update_JSONValue(filePostPayment, "$.creditCards..card.paymentMethodCode", invalidPayMethod);
    	
    	resp = ACSPOSTSteps.post(filePostPayment, end_pt)
        		.extract().response();
    	
    	paymentAmount = 0;
    	
    }
	
    @When("^User sends a POST request to insert new payment for order with invalid data$")
    public void user_sends_a_POST_request_to_insert_new_payment_for_order_with_invalid_data() throws URISyntaxException {
    	
    	String invalidOrderNo = "11111110";
    	
    	end_pt = ACSPOSTSteps.setEndpointOrdersPayment(invalidOrderNo);
    	
    	resp = ACSPOSTSteps.post(filePostPayment, end_pt)
        		.extract().response();
    	
    	paymentAmount = 0;
    }
    
    @Then("^the amountDue value should be updated accordingly for order with orderNum$")
    public void the_amountdue_value_should_be_updated_accordingly_for_order_with_ordernum() throws Throwable {
    	/*
    	Get the Value from the original order 
    	Get the value from payment
    	Compare
    	*/
    	JsonPath jsonpathEvaluator = resp.jsonPath();
    	List<String> loOrderNumbers = jsonpathEvaluator.getList("orders.orderNumber");
    	List<Float> loAmountDues = jsonpathEvaluator.getList("orders.amountDue");
    	
    	int i = 0;
    	boolean found = false;
    	float left = 0;
    	
    	while (!found && i < loOrderNumbers.size()) {
    		// JSONObject order = (JSONObject) loOrders.get(i);
    		if (loOrderNumbers.get(i).equals(kmap.get("orderNumber"))) {
    			left = loAmountDues.get(i);
    			found = true;
    		}
    		i++;
    	}
    	
    	if (found) {
    	//	float expVal = tUtil.round((orderBalanceDue - paymentAmount));
    		System.out.println(tUtil.round((orderBalanceDue - paymentAmount),1) + ":" + tUtil.round(left,1));
    		Assert.assertEquals(tUtil.round((orderBalanceDue - paymentAmount),1), tUtil.round(left,1), 0.001);
    	} else {
    		throw new Exception("Could not find the orderNumber");
    	}
    }
    
    // Checks the agremeents for credits and electronicPaymentId and checks orders for updated amountDue
    @Then("^the payment should be inserted for order$")
    public void the_payment_should_be_inserted_for_order() throws Throwable {
        // Check the agreemets for orderNumber, Payment amount and ?
    	
    	JsonPath jsonpathevaluator = resp.jsonPath();
    	String dummyElectronicPaymentId = "000000000000"; // dummy value in agreements
    	List<String> loElectronicPaymentId = jsonpathevaluator.getList("agreements.electronicPaymentId");
    	List<String> loCustomerNumbers = jsonpathevaluator.getList("agreements.billTo.customerNumber");
    	List<String> loOrderNumbers = jsonpathevaluator.getList("agreements.orderNumber");    	 
    	List<List<Object>> loCredits = jsonpathevaluator.getList("agreements.credits");
    	System.out.println(loCustomerNumbers);
    	
    	Assert.assertTrue(loCustomerNumbers.size() == loOrderNumbers.size());
    	Assert.assertTrue(loCustomerNumbers.size() > 0);
    	
    	boolean found = false;
    	int i = 0;
    	while (!found && i < loCustomerNumbers.size()) {
    		
    		String actualCustNo = loCustomerNumbers.get(i);
    		// System.out.println(actualCustNo);
    		String actualOrderNo = loOrderNumbers.get(i);
    		// System.out.println(actualOrderNo);
    		
    		
    		if (actualCustNo.equals(kmap.get("customerNumber")) && actualOrderNo.equals(kmap.get("orderNumber"))) {
    	    	// Ensure that the paymentId is different from the dummy
    	    	Assert.assertTrue(!loElectronicPaymentId.get(i).equals(dummyElectronicPaymentId));
    			Assert.assertTrue(loCredits.get(i).size() > 0);
    			found = true;
    		}
    		i++;
    	}
    	
    	if (!found) {
    		throw new Exception("Could not find the agreement");
    	}
    	
    	user_send_a_GET_call_to_get_the_details_of_orders_done_via_customer_having_customer_number();
    	the_amountdue_value_should_be_updated_accordingly_for_order_with_ordernum();
    	
    }
    
    @Then("^No payment is inserted for order with number$")
    public void no_payment_is_inserted_for_order_with_number() throws Throwable {
        // Check the agreements for orderNumber, Payment amount and ?Additional check needed
    	
    	JsonPath jsonpathevaluator = resp.jsonPath();
    	String dummyElectronicPaymentId = "000000000000"; // dummy value in agreements
    	List<String> loElectronicPaymentId = jsonpathevaluator.getList("agreements.electronicPaymentId");
    	List<String> loCustomerNumbers = jsonpathevaluator.getList("agreements.billTo.customerNumber");
    	List<String> loOrderNumbers = jsonpathevaluator.getList("agreements.orderNumber");    	 
    	List<List<Object>> loCredits = jsonpathevaluator.getList("agreements.credits");
    	
    	
    	Assert.assertTrue(loCustomerNumbers.size() == loOrderNumbers.size());
    	Assert.assertTrue(loCustomerNumbers.size() > 0);
    	
    	boolean found = false;
    	int i = 0;
    	while (!found && i < loCustomerNumbers.size()) {
    		
    		String actualCustNo = loCustomerNumbers.get(i);
    		// System.out.println(actualCustNo);
    		String actualOrderNo = loOrderNumbers.get(i);
    		// System.out.println(actualOrderNo);
    		
    		
    		if (actualCustNo.equals(kmap.get("customerNumber")) && actualOrderNo.equals(kmap.get("orderNumber"))) {
    	    	// Ensure that the paymentId is the same as the dummy
    	    	Assert.assertTrue(loElectronicPaymentId.get(i).equals(dummyElectronicPaymentId));
    			Assert.assertTrue(loCredits.get(i).size() == 0);
    			found = true;
    		}
    		i++;
    	}
    	
    	if (!found) {
    		System.out.println("Could not find the agreement");
    		throw new RuntimeException();
    	}
    	
    	user_send_a_GET_call_to_get_the_details_of_orders_done_via_customer_having_customer_number();
    	the_amountdue_value_should_be_updated_accordingly_for_order_with_ordernum();
    }
    
	// =================================================================================================================
	// Helper methods
	// =================================================================================================================
	
	// Restores the default values for JSON files
	// Default valid values for credit card info
	// Values for posting order --> valid custNumber, adresscode, email and controlGroupId
	// Note: this should be used only after valid customer creation 
	private void restoreDefault() throws Exception {
		// Defaults for POST Payment
		jsonUtils.update_JSONValue(filePostPayment, "$.creditCards..card.token", "4111111111111111");
		jsonUtils.update_JSONValue(filePostPayment, "$.creditCards..card.number", "XXXXXXXXXXXX1111");
		jsonUtils.update_JSONValue(filePostPayment, "$.creditCards..card.paymentMethodCode", "VISA");
		jsonUtils.update_JSONValue(filePostPayment, "$.creditCards..card.expireMonth", 12);
		jsonUtils.update_JSONValue(filePostPayment, "$.creditCards..card.expireYear", 2026);
		jsonUtils.update_JSONValue(filePostPayment, "$.creditCards..securityCode", "123");
		jsonUtils.update_JSONValue(filePostPayment, "$.creditCards..amount", 327.87);
		
		// Defaults for POST order
		jsonUtils.update_JSONValue(filePostOrder, "$.billTo.customerNumber", kmap.get("customerNumber"));
		jsonUtils.update_JSONValue(filePostOrder, "$.billTo.addressCode", kmap.get("addressCode"));
		jsonUtils.update_JSONValue(filePostOrder, "$.orderLines..access.participants..customerNumber", kmap.get("customerNumber"));
		jsonUtils.update_JSONValue(filePostOrder, "$.orderLines..access.participants..email", kmap.get("email"));
		jsonUtils.update_JSONValue(filePostOrder, "$.controlGroupId", "UC233G");
	}
	
	/*
	Set up for the payment:
		Create a customer
		Create a control group
		Create an order
	*/ 
	public void setUpPayment() throws Throwable {
		post_Customer();
		createControlGroup();
		restoreDefault();	
		placeOrder();
	}
	
	public void generate_customer_data() throws Exception {
		jsonUtils.update_JSONValue(filePostCustomerData, "$.firstName",
				jsonUtils.getFromJSON(file_name, "$.address.name.firstName"));
		jsonUtils.update_JSONValue(filePostCustomerData, "$.lastName",
				jsonUtils.getFromJSON(file_name, "$.address.name.lastName"));
		jsonUtils.update_JSONValue(filePostCustomerData, "$.middleInitial",
				jsonUtils.getFromJSON(file_name, "$.address.name.middleInitial"));

		jsonUtils.update_JSONValue(filePostCustomerData, "$.street",
				jsonUtils.getFromJSON(file_name, "$.address.postalAddress.address1"));
		jsonUtils.update_JSONValue(filePostCustomerData, "$.city",
				jsonUtils.getFromJSON(file_name, "$.address.postalAddress.city"));
		jsonUtils.update_JSONValue(filePostCustomerData, "$.state",
				jsonUtils.getFromJSON(file_name, "$.address.postalAddress.stateCode"));
		jsonUtils.update_JSONValue(filePostCustomerData, "$.zipcode",
				jsonUtils.getFromJSON(file_name, "$.address.postalAddress.postalCode"));
		jsonUtils.update_JSONValue(filePostCustomerData, "$.country",
				jsonUtils.getFromJSON(file_name, "$.address.postalAddress.countryCode"));
	}

	@When("^User sends a POST request to insert new commit for order with valid data$")
	public void user_sends_a_post_request_to_insert_new_commit_for_order_with_valid_data() throws Throwable {
		setUpPayment();
		String orderNo = resp.jsonPath().getString("orderNumber");
		kmap.put("orderNumber", orderNo);
		end_pt = ACSPOSTSteps.setEndpointCommitOrder(orderNo);
		resp = ACSPOSTSteps.post(filePostPayment, end_pt)
				.extract().response();


		paymentAmount = Float.parseFloat(jsonUtils.getFromJSON(filePostPayment, "$.creditCards..amount").replace("[", "").replace("]", ""));

		String cardNumber = jsonUtils.getFromJSON(filePostPayment, "$.creditCards..card.number").replace("[", "").replace("]", "");
		String paymentMethodCode = jsonUtils.getFromJSON(filePostPayment, "$.creditCards..card.paymentMethodCode").replace("[", "").replace("]", "");
		String expireMonth = jsonUtils.getFromJSON(filePostPayment, "$.creditCards..card.expireMonth").replace("[", "").replace("]", "");
		String expireYear = jsonUtils.getFromJSON(filePostPayment, "$.creditCards..card.expireYear").replace("[", "").replace("]", "");
		kmap.put("cardNumber", cardNumber);
		kmap.put("paymentMethodCode", paymentMethodCode);
		kmap.put("expireMonth", expireMonth);
		kmap.put("expireYear", expireYear);

	}


	@When("^User sends a POST request to insert new commit for order with valid data and amount which is more than the amountDue$")
	public void user_sends_a_post_request_to_insert_new_commit_for_order_with_valid_data_and_amount_which_is_more_than_the_amountdue() throws Throwable {
		setUpPayment();
		String orderNo = resp.jsonPath().getString("orderNumber");
		kmap.put("orderNumber", orderNo);

		float more = orderBalanceDue + DIFF;

		end_pt = ACSPOSTSteps.setEndpointCommitOrder(orderNo);
		jsonUtils.update_JSONValue(filePostPayment, "$.creditCards[0].amount", more);

		resp = ACSPOSTSteps.post(filePostPayment, end_pt)
				.extract().response();

		paymentAmount = more;
	}


	@When("^User sends a POST request to insert new commit for order with valid data and amount which is less than the amountDue$")
	public void user_sends_a_post_request_to_insert_new_commit_for_order_with_valid_data_and_amount_which_is_less_than_the_amountdue() throws Throwable {
		setUpPayment();

		String orderNo = resp.jsonPath().getString("orderNumber");
		kmap.put("orderNumber", orderNo);
		float lessNum = orderBalanceDue - DIFF;
		float less = lessNum > 0 ? lessNum : 1;

		end_pt = ACSPOSTSteps.setEndpointCommitOrder(orderNo);
		jsonUtils.update_JSONValue(filePostPayment, "$.creditCards[0].amount", less);
		resp = ACSPOSTSteps.post(filePostPayment, end_pt)
				.extract().response();

		paymentAmount = less;
	}

	@When("^User sends a POST request to insert new commit for order with invalid data for token number securityCode and paymentMethodCode$")
	public void user_sends_a_POST_request_to_insert_new_commit_for_order_with_invalid_data_for_token_number_securityCode_and_paymentMethodCode() throws Throwable {
		setUpPayment();
		String orderNo = resp.jsonPath().getString("orderNumber");
		kmap.put("orderNumber", orderNo);

		end_pt = ACSPOSTSteps.setEndpointCommitOrder(orderNo);

		String invalidToken = "";
		String invalidSecCode = "";
		String invalidPayMethod = "VSA";

		jsonUtils.update_JSONValue(filePostPayment, "$.creditCards..card.token", invalidToken);
		jsonUtils.update_JSONValue(filePostPayment, "$.creditCards..securityCode", invalidSecCode);
		jsonUtils.update_JSONValue(filePostPayment, "$.creditCards..card.paymentMethodCode", invalidPayMethod);

		resp = ACSPOSTSteps.post(filePostPayment, end_pt)
				.extract().response();

		paymentAmount = 0;
	}

	@When("^User sends a POST request to insert new commit for order with invalid data$")
	public void user_sends_a_POST_request_to_insert_new_commit_for_order_with_invalid_data() throws URISyntaxException {

		String invalidOrderNo = "11111110";

		end_pt = ACSPOSTSteps.setEndpointCommitOrder(invalidOrderNo);

		resp = ACSPOSTSteps.post(filePostPayment, end_pt)
				.extract().response();

		paymentAmount = 0;
	}

	@When("^I send a POST request to create a control group with valid data$")
	public void iSendAPOSTRequestToCreateAControlGroupWithValidData() throws Throwable {
		createControlGroup();
	}

	@When("^I send a POST request to open a control group with exists data$")
	public void iSendAPOSTRequestToOpenAControlGroupWithExistsData() throws Throwable {
		createControlGroup();

		end_pt = ACSPOSTSteps.setEndpointControlGroup();
		resp = ACSPOSTSteps.post(filePostControlGroup, end_pt)
				.extract().response();
	}

	@When("^I send a POST request to open a control group with invalid date$")
	public void iSendAPOSTRequestToOpenAControlGroupWithInvalidDate() throws Exception {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		jsonUtils.update_JSONValue(filePostControlGroup, "$.CTGDate", timeStamp.replaceAll("-", ""));
		jsonUtils.update_JSONValue(filePostOrder, "$.controlGroupDate", timeStamp);

		end_pt = ACSPOSTSteps.setEndpointControlGroup();
		resp = ACSPOSTSteps.post(filePostControlGroup, end_pt)
				.extract().response();

		jsonUtils.update_JSONValue(filePostControlGroup, "$.CTGDate", timeStamp);
	}

	@When("^I send a POST request to open a control group with empty date$")
	public void iSendAPOSTRequestToOpenAControlGroupWithEmptyDate() throws Exception {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		jsonUtils.update_JSONValue(filePostControlGroup, "$.CTGDate", "");
		jsonUtils.update_JSONValue(filePostOrder, "$.controlGroupDate", timeStamp);

		end_pt = ACSPOSTSteps.setEndpointControlGroup();
		resp = ACSPOSTSteps.post(filePostControlGroup, end_pt)
				.extract().response();

		jsonUtils.update_JSONValue(filePostControlGroup, "$.CTGDate", timeStamp);
	}

    @When("^User sends a POST request to insert new payment for order with card data$")
    public void userSendsAPOSTRequestToInsertNewPaymentForOrderWithCardData() throws Throwable {
		setUpPayment();
		String orderNo = resp.jsonPath().getString("orderNumber");
		kmap.put("orderNumber", orderNo);
		end_pt = ACSPOSTSteps.setEndpointOrdersPayment(orderNo);
		resp = ACSPOSTSteps.post(filePostPayment, end_pt)
				.extract().response();
    }

	@When("^User sends a POST request to insert new payment for order with card token (.*) and code (.*)$")
	public void userSendsAPOSTRequestToInsertNewPaymentForOrderWithCardTokenTokenAndCodeCode(String token, String code) throws Throwable {
		setUpPayment();
		String orderNo = resp.jsonPath().getString("orderNumber");
		String clean_token = token.replace("\"", "");
		String number = "XXXXXXXXXXXX" + clean_token.substring(clean_token.length()-4);

		jsonUtils.update_JSONValue(filePostPayment, "$.creditCards..card.token", clean_token);
		jsonUtils.update_JSONValue(filePostPayment, "$.creditCards..number", number);
		jsonUtils.update_JSONValue(filePostPayment, "$.creditCards..card.paymentMethodCode", code);

		end_pt = ACSPOSTSteps.setEndpointOrdersPayment(orderNo);
		resp = ACSPOSTSteps.post(filePostPayment, end_pt)
				.extract().response();
	}

	@When("^I send a POST request to create a matching customer$")
	public void iSendAPOSTRequestToCreateAMatchingCustomer() throws Exception {
		generate_customer_data();

		end_pt = ACSPOSTSteps.setEndpointCustomerMatches();
		resp = ACSPOSTSteps.post(filePostCustomerData, end_pt)
			.extract().response();
	}

	@Given("I do sql request to get users with subscriptions")
	public void iDoSqlRequestToGetUsersWithSubscriptions() throws IOException, SQLException {

		String sqlQuery = dbUtils.buildQuery("customerWithSubscriptions.sql");
		String dbURL = dbUtils.buildDbUrl(acsDBUrl);
		Connection conn = ConnectionFactory.getConnectionACS(dbURL, dbUtils.acsDBUser(), dbUtils.acsDBPass());
		PreparedStatement ps = conn.prepareStatement(sqlQuery) ;

		ResultSet resultSet = ps.executeQuery() ;

		while (resultSet.next()) {
			customerList.add(resultSet.getString("CTM_NBR"));
		}
	}

	@Then("I send a get request to get details of subscriptions")
	public void iSendAGetRequestToGetDetailsOfSubscriptions() throws URISyntaxException {
		for (String customer: customerList) {
			String end_pt = ACSGETSteps.setSubscriptionsEndpoint(customer);
			resp = ACSGETSteps.get(end_pt).extract().response();
			System.out.println("print");
			if (!resp.jsonPath().getList("subscriptions").isEmpty()) break;
		}
	}

	@Then("I check deliveryCode fields is not null")
	public void iCheckDeliveryCodeFields() {
		List subscriptions = resp.jsonPath().getList("subscriptions");
		Map subscription = (Map) subscriptions.get(0);
		Assert.assertNotNull(subscription.get("deliveryCode"));
	}
}
