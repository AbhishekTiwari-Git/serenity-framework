package ucc.i.steps.system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import ucc.i.method.magento.MagentoGET;
import ucc.i.method.magento.MagentoPOST;
import ucc.i.method.magento.MagentoPUT;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

public class MagentoSteps {
	public static Response resp = null;
	public static Map<String, String> kmap = new HashMap<String, String>();
	static String end_pt = null;
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();

	String filePostCustomer = "Magento_POST_Customer.json";
	String filePostCustomerInvalid = "Magento_POST_Customer_Invalid.json";
	String filePutCustomer = "Magento_PUT_Customer.json";
	String filePutCustomerInvalid = "Magento_PUT_Customer_Invalid.json"; 
	
	@Steps
	MagentoPOST magentoPOSTSteps;
	@Steps
	MagentoPUT magentoPUTSteps;
	@Steps
	MagentoGET magentoGETSteps;

	// =================================================================================================================
	// Step Definitions:

	@When("^user sends a POST request to create a customer in Magento with email (.*) firstname (.*) lastname (.*)$")
	public void user_sends_a_post_request_to_create_a_customer_in_magento_with_email_firstname_lastname(
			String email, String fname, String lname) throws Throwable {
		String generatedUcid = tUtil.generateRandomUcid(36);
		String tsEmail = (email.isEmpty() || !email.matches("(.*)@(.*).(.*)")) ? email : tUtil.AppendTimestamp(email);
		jsonUtils.update_JSONValue(filePostCustomer, "email", tsEmail);
		jsonUtils.update_JSONValue(filePostCustomer, "firstName", fname);
		jsonUtils.update_JSONValue(filePostCustomer, "lastName", lname);
		jsonUtils.update_JSONValue(filePostCustomer, "ucid", generatedUcid);
		
		kmap.put("email", tsEmail);
		kmap.put("firstName", fname);
		kmap.put("lastName", lname);
		kmap.put("ucid", generatedUcid);

		end_pt = magentoPOSTSteps.setEndpointCreateCustomer();
		resp = magentoPOSTSteps.post(filePostCustomer, end_pt).extract().response();
		
		tUtil.putToSession("response", resp);
	}
	
	@When("^user sends a POST request to create a customer with existing email$")
    public void user_sends_a_post_request_to_create_a_customer_with_existing_email() throws Throwable {
        jsonUtils.update_JSONValue(filePostCustomer, "email", kmap.get("email"));
        
        end_pt = magentoPOSTSteps.setEndpointCreateCustomer();
		resp = magentoPOSTSteps.post(filePostCustomer, end_pt).extract().response();
    }
	
	@When("^user sends a POST request to create customer with invalid value (.*) for field (.*)$")
    public void user_sends_a_post_request_to_create_customer_with_invalid_value_for_field(String value, String field) throws Throwable {
        // restoring defaults
		jsonUtils.update_JSONValue(filePostCustomerInvalid, "primarySpecialty", "A");
		jsonUtils.update_JSONValue(filePostCustomerInvalid, "customerSuffix", "MBBS");
		jsonUtils.update_JSONValue(filePostCustomerInvalid, "role", "CEO");
		
		// Using invalid value for the given field 
		jsonUtils.update_JSONValue(filePostCustomerInvalid, field, value);
		
		end_pt = magentoPOSTSteps.setEndpointCreateCustomer();
		resp = magentoPOSTSteps.post(filePostCustomerInvalid, end_pt).extract().response();
    }
	
	@When("^user sends a PUT request to update the created customer in Magento with new email (.*) firstname (.*) lastname (.*)$")
    public void user_sends_a_put_request_to_update_the_created_customer_in_magento_with_new_email_firstname_lastname(String newEmail, String newFname, String newLname) throws Throwable {
		String tsEmail = (newEmail.isEmpty() || !newEmail.matches("(.*)@(.*).(.*)")) ? newEmail : tUtil.AppendTimestamp(newEmail);
		
		jsonUtils.update_JSONValue(filePutCustomer, "id", Integer.parseInt(kmap.get("id")));
		jsonUtils.update_JSONValue(filePutCustomer, "email", tsEmail);
		jsonUtils.update_JSONValue(filePutCustomer, "firstName", newFname);
		jsonUtils.update_JSONValue(filePutCustomer, "lastName", newLname);
		
		kmap.put("email", tsEmail);
		kmap.put("firstName", newFname);
		kmap.put("lastName", newLname);
		
		end_pt = magentoPUTSteps.setEndpointUpdateCustomer(kmap.get("id"));
		resp = magentoPUTSteps.put(filePutCustomer, end_pt).extract().response();
    }
	
	@When("^user sends a PUT request to update an invalid customer in Magento with customerId (.*) email (.*) firstname (.*) lastname (.*)$")
    public void user_sends_a_put_request_to_update_an_invalid_customer_in_magento_with_customerid_email_firstname_lastname(int custId, String newEmail, String newFname, String newLname) throws Throwable {
		String tsEmail = (newEmail.isEmpty() || !newEmail.matches("(.*)@(.*).(.*)")) ? newEmail : tUtil.AppendTimestamp(newEmail);
		
		jsonUtils.update_JSONValue(filePutCustomerInvalid, "id", custId);
		jsonUtils.update_JSONValue(filePutCustomerInvalid, "email", tsEmail);
		jsonUtils.update_JSONValue(filePutCustomerInvalid, "firstName", newFname);
		jsonUtils.update_JSONValue(filePutCustomerInvalid, "lastName", newLname);
		
		end_pt = magentoPUTSteps.setEndpointUpdateCustomer(String.valueOf(custId));
		System.out.println(String.valueOf(custId));
		resp = magentoPUTSteps.put(filePutCustomerInvalid, end_pt).extract().response();
    }
	
	@When("^user sends a GET request with parameters fromDate (.*) toDate (.*) currentPage (.*) pageSize (.*)$")
    public void user_sends_a_get_request_with_parameters_fromdate_todate_currentpage_pagesize(String fromDate, String toDate, String curPage, String pgSize) throws Throwable {
        end_pt = magentoGETSteps.setEndpointOrderHistory(fromDate, toDate, curPage, pgSize);
        
        resp = magentoGETSteps.get(end_pt).extract().response();
        
        kmap.put("fromDate", fromDate);
        kmap.put("toDate", toDate);
        kmap.put("currentPage", curPage);
        kmap.put("pageSize", pgSize);
        
        tUtil.putToSession("response", resp);
    }
	
	@Then("^the request should return a message (.*) with status code (.*)$")
    public void the_request_should_return_a_message_with_status_code(String msg, int badstcode) throws Throwable {
        tUtil.verify_msgCode(resp, msg, badstcode);
    }
	
	@Then("^new Magento customer should be created with status code (.*)$")
    public void new_magento_customer_should_be_created_with_status_code(int stcode) throws Throwable {
		tUtil.verifyStatus(resp, stcode);
		
		// Updating the kmap
		JsonPath jsonpathEvaluator = resp.jsonPath();
		kmap.put("id", jsonpathEvaluator.get("id").toString());
    }
	
	@Then("^information in response body should match the information used for customer creation$")
    public void information_in_response_body_should_match_the_information_used_for_customer_creation() throws Throwable {
		JsonPath jsonpathEvaluator = resp.jsonPath();
        Assert.assertEquals("The email should be " + kmap.get("email") + " but got " + jsonpathEvaluator.getString("email"),
        		kmap.get("email"), jsonpathEvaluator.getString("email"));
        Assert.assertEquals("The first name should be " + kmap.get("firstName") + " but got " + jsonpathEvaluator.getString("firstName"),
        		kmap.get("firstName"), jsonpathEvaluator.getString("firstName"));
        Assert.assertEquals("The last name should be " + kmap.get("lastName") + " but got " + jsonpathEvaluator.getString("lastName"),
        		kmap.get("lastName"), jsonpathEvaluator.getString("lastName"));
        Assert.assertEquals("The ucid should be " + kmap.get("ucid") + " but got " + jsonpathEvaluator.getString("ucid"),
        		kmap.get("ucid"), jsonpathEvaluator.getString("ucid"));
        
    }
	
	@Then("^customer information should be updated with status code (.*)$")
    public void customer_information_should_be_updated_with_status_code(int updateStatus) throws Throwable {
        tUtil.verifyStatus(resp, updateStatus);
        
        JsonPath jsonpathEvaluator = resp.jsonPath();
        Assert.assertEquals("The email should be " + kmap.get("email") + " but got " + jsonpathEvaluator.getString("email"),
        		kmap.get("email"), jsonpathEvaluator.getString("email"));
        Assert.assertEquals("The first name should be " + kmap.get("firstName") + " but got " + jsonpathEvaluator.getString("firstName"),
        		kmap.get("firstName"), jsonpathEvaluator.getString("firstName"));
        Assert.assertEquals("The last name should be " + kmap.get("lastName") + " but got " + jsonpathEvaluator.getString("lastName"),
        		kmap.get("lastName"), jsonpathEvaluator.getString("lastName"));
        
    }
	
	@Then("^correct number of orders is displayed with 200 status code$")
    public void correct_number_of_orders_is_displayed_with_200_status_code() throws Throwable {
        JsonPath jsonpathEvaluator = resp.jsonPath();
        
		int totalCount = Integer.valueOf(jsonpathEvaluator.getString("total_count"));
		List<String> loCreatedDateString = jsonpathEvaluator.getList("orderItems.created_at");

		System.out.println(totalCount);
		// totalNumberOfPages - number of pages with orders 
		// numberShouldBeShown - number of orders that should be shown given the currentPage and pageSize
		// Logic given total count is not 0: If the totalCount can be divided by pageSize => each page has the same pageSize number of orders
		// If the currentPage is not the last => page should have pageSize number of orders
		// If the totalCount cannot be divided and currentPage is the last => remainder from division is the number of orders
		
		int pageSize = Integer.valueOf(kmap.get("pageSize"));
		int currentPage = Integer.valueOf(kmap.get("currentPage"));
		int totalNumOfPages = (totalCount % pageSize == 0) ? totalCount / pageSize : (totalCount / pageSize) + 1;
		int numShouldBeShown = (currentPage < totalNumOfPages || totalCount % pageSize == 0) ? pageSize : totalCount % pageSize;
		
		// if total count is 0, then 0 should be shown
		if (totalCount == 0) {
		  numShouldBeShown = 0;
		}
		
		Assert.assertEquals("The number of shown order was expected to be " + numShouldBeShown + " but got " + loCreatedDateString.size(),
				numShouldBeShown , loCreatedDateString.size());
		
		List<Date> loCreatedDate = loCreatedDateString.stream().map(x -> {
			try {
				return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(x);
			} catch (ParseException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}).collect(Collectors.toList());
		
		Date fromDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(kmap.get("fromDate").replaceAll("T", " "));
		Date toDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(kmap.get("toDate").replaceAll("T", " "));
		
		loCreatedDate.stream().forEach(date -> Assert.assertTrue(date.after(fromDate) && date.before(toDate)));
		
	}

}
