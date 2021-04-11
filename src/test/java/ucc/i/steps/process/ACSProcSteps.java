package ucc.i.steps.process;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ucc.i.method.acs.ACSGET;
import ucc.i.method.acs.ACSHelper;
import ucc.i.method.acs.ACSPOST;
import ucc.i.method.acsprocess.ACSProcessGET;
import ucc.i.method.acsprocess.ACSProcessHelper;
import ucc.i.method.acsprocess.ACSProcessPOST;
import ucc.i.method.aic.AICGET;
import ucc.utils.ConnectionFactory;
import ucc.utils.DbUtils;
import ucc.utils.JsonUtils;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

public class ACSProcSteps {
    private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    private static String autoEmail =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("autoEmail");
    private static final Logger LOGGER = LoggerFactory.getLogger(ACSProcSteps.class);
    public static Response procResp = null;
    public static Response sysResp = null;
    public static Map<String,String> kmap = new HashMap<String,String>();
    TestUtils tUtil = new TestUtils();
    JsonUtils jsonUtils = new JsonUtils();
    static String endPt = null;
    private static String customerNumber;
    private static String ucid;
    private static String email;
    private static String lastName;
    private static String firstName;
    private static String postalCode;
    private static String countryCode;
    private final DbUtils dbUtils = new DbUtils();
    private List<String> customerNumbers = new ArrayList<>();
    private List<String> fNameList = new ArrayList<>();
    private List<String> lNameList = new ArrayList<>();
    private List<String> emailList = new ArrayList<>();
    SoftAssertions softAssert = new SoftAssertions();
    
    @Steps
    ACSGET acsGETSteps;

    @Steps
    ACSPOST ACSPOSTSteps;

    @Steps
    ACSProcessPOST ACSProcPOSTStpes;

    @Steps
    AICGET aicGETSteps;
    
    @Steps
    CustProcSteps custProcSteps;
    
    @Steps
    ACSProcessGET acsProcessGET;
    
    @Steps
    ACSHelper acsHelper;
    
    @Steps
    ACSProcessHelper acsProcHelper;
    
    
    
    String file_name = "ACS/ACS_Post.json";
    String file_order = "ACSProc_Post_Order.json";
    String filePostControlGroup = "ACS/ACS_Post_Control_Group.json";
    String customersCustomerNumberActivate = "ACS_PROC/customersCustomerNumberActivate.json";
    
    
    
    @Then("I see the user has the ucid linked to it with correct email fname lname after CustomerProc activate call")
    public void i_see_the_user_has_the_ucid_linked_to_it_with_correct_email_fname_lname_after_customer_proc_activate_call() {

    	Assert.assertEquals((String) tUtil.getFromSession("ucid"), procResp.jsonPath().getString("results[0].ucid").toLowerCase());
    	Assert.assertEquals((String) tUtil.getFromSession("email"), procResp.jsonPath().getString("results[0].email"));
    	Assert.assertEquals((String) tUtil.getFromSession("firstName"), procResp.jsonPath().getString("results[0].name.first"));
    	Assert.assertEquals((String) tUtil.getFromSession("lastName"), procResp.jsonPath().getString("results[0].name.last"));
    }
    
    @When("I do POST activate for the user with wrong postalCode")
    public void i_do_post_activate_for_the_user_with_wrong_postal_code() 
    		throws Exception {

    	ucid = tUtil.generateRandomUcid(36);
    	
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.ucid", ucid);
    	
    	LOGGER.info(ucid + " <<-- ucid ");
    	
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.email", email);
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.lastName", lastName);
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.postalCode", tUtil.AppendTimestamp("postalCode"));
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.country", countryCode);

    	endPt = ACSProcPOSTStpes.setEndpointcustomerNumberActivate(kmap.get("customerNumber"));
    	procResp = ACSProcPOSTStpes.post(customersCustomerNumberActivate, endPt)
    			.extract().response();
    }

    @Then("^I see error message (.*) for postalCode not matching$")
    public void i_see_error_message_subscription_customer_not_found_for_postal_code_not_matching(String message) {

    	tUtil.verifyStatus(procResp, ResponseCode.OK);
    	Assert.assertEquals(message, procResp.jsonPath().getString("errors[0].errorDescription"));
    }
    
    @When("I do POST activate for the user with invalid subcription")
    public void i_do_post_activate_for_the_user_with_invalid_subcription() 
    		throws Exception {

    	ucid = tUtil.generateRandomUcid(36);
    	
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.ucid", ucid);
    	
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.email", email);
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.lastName", lastName);
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.postalCode", postalCode);
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.country", countryCode);

    	endPt = ACSProcPOSTStpes.setEndpointcustomerNumberActivate(tUtil.AppendTimestamp("customerNumber"));
    	procResp = ACSProcPOSTStpes.post(customersCustomerNumberActivate, endPt)
    			.extract().response();
    }
    
    @Then("^I see error message (.*) for subscription not found$")
    public void i_see_error_message_subscription_customer_not_found_for_subscription_not_found(String message) {

    	tUtil.verifyStatus(procResp, ResponseCode.NOT_FOUND);
    	Assert.assertEquals(message, procResp.jsonPath().getString("errors[0].errorDescription"));
    }
    
    @Then("I see the user has the ucid linked to it with correct email fname lname after MyAccExp activate call")
    public void i_see_the_user_has_the_ucid_linked_to_it_with_correct_email_fname_lname_after_my_acc_exp_activate_call() {
    	//we verify that activated user has linked to its ucid and details are matching
    	tUtil.verifyStatus(procResp, ResponseCode.OK);
    	
    	Assert.assertEquals((String) tUtil.getFromSession("ucid"), procResp.jsonPath().getString("results[0].ucid").toLowerCase());
    	Assert.assertEquals((String) tUtil.getFromSession("email"), procResp.jsonPath().getString("results[0].email"));
    	Assert.assertEquals((String) tUtil.getFromSession("firstName"), procResp.jsonPath().getString("results[0].name.first"));
    	Assert.assertEquals((String) tUtil.getFromSession("lastName"), procResp.jsonPath().getString("results[0].name.last"));
    }
    
    @When("^I get users email list from database using for nejm user$")
    public void getTopTenUsers() throws SQLException, IOException {

    	LOGGER.info("I get users email list from database using for nejm user");
    	LOGGER.info("Now we're waiting for the sql query being executed");

    	String envVal = acsProcHelper.getEnv();
    	String queryFileName = acsProcHelper.setPayBillDbfile(envVal);
    	System.out.println("Query file name: " + queryFileName);
    	
    	String sqlQuery = dbUtils.buildQuery(queryFileName);
        System.out.println("Database Query: " + sqlQuery);
        
        String dbURL = dbUtils.buildDbUrl("ACSDbUrl");
        System.out.println("Database Url: " + dbURL);
        
        Connection conn = ConnectionFactory.getConnectionACS(
                dbURL, dbUtils.acsDBUser(), dbUtils.acsDBPass());
        PreparedStatement ps = conn.prepareStatement(sqlQuery);

        ResultSet resultSet = ps.executeQuery();
  
        while (resultSet.next()) {
            customerNumbers.add(resultSet.getString("ctm_nbr"));
        }

        tUtil.putToSession("customerNumbers", customerNumbers);        
        LOGGER.info("customerNumbers  --> " + customerNumbers.toString());
    }
    
    @When("I call AcsSys usersUCC endpoint with the customerNumber")
    public void i_call_acs_sys_users_ucc_endpoint_with_the_customer_number() {

    	boolean flag = false;
    	
    	for (int i = 0; i < customerNumbers.size(); i++) {
    		customerNumber = customerNumbers.get(i);
    		
    		endPt = acsProcessGET.setEndPointUsersUCC(customerNumbers.get(i));
    		procResp = acsProcessGET.get(endPt)
    				.extract().response();
    		
    		kmap.put("customerNumber", customerNumbers.get(i));
    		
    		List results = procResp.jsonPath().getList("results");
            LOGGER.info("results.size() from /customers/{customerNumber}/users/ucc call, should be 0 --> " + results.size());
            
            if(results.isEmpty()){
            	iSnedAGETCallSystemAPIToGetDetailCustomer();
            	if(acsHelper.isUserHasUniqueLastName_andPostalCode(sysResp)){
            		break;
            	}
            }
            else flag=false;
    	}
    }
    
    @Then("I see customers details in the response")
    public void i_see_customers_details_in_the_response() {
        
    	tUtil.verifyStatus(sysResp, ResponseCode.OK);
    	
    	acsHelper.getTheDetails(sysResp);
    	
    	email = (String) tUtil.getFromSession("email");
    	firstName = (String) tUtil.getFromSession("firstName");
    	lastName = (String) tUtil.getFromSession("lastName");
    	postalCode = (String) tUtil.getFromSession("postalCode");
    	countryCode = (String) tUtil.getFromSession("countryCode");
    	
    	LOGGER.info(email + " " + firstName + " " + lastName + " " + postalCode + " " + countryCode);
    }
    
    @When("I do POST activate for the user")
    public void i_do_post_activate_for_the_user() 
    		throws Exception {
    	
    	ucid = tUtil.generateRandomUcid(36);
    	
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.ucid", ucid);
    	
    	LOGGER.info(ucid + " <<-- ucid ");
    	
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.email", email);
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.lastName", lastName);
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.postalCode", postalCode);
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.country", countryCode);

    	endPt = ACSProcPOSTStpes.setEndpointcustomerNumberActivate(kmap.get("customerNumber"));
    	procResp = ACSProcPOSTStpes.post(customersCustomerNumberActivate, endPt)
    			.extract().response();
    }
    
    @When("I do POST activate for the same user")
    public void i_do_post_activate_for_the_same_user() 
    		throws Exception {

    	ucid = tUtil.generateRandomUcid(36);
    	
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.ucid", ucid);
    	
    	LOGGER.info(ucid + " <<-- ucid ");
    	
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.email", email);
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.lastName", lastName);
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.postalCode", postalCode);
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.country", countryCode);

    	endPt = ACSProcPOSTStpes.setEndpointcustomerNumberActivate(kmap.get("customerNumber"));
    	procResp = ACSProcPOSTStpes.post(customersCustomerNumberActivate, endPt)
    			.extract().response();
    }
    
    @When("I do POST activate for the user with wrong lastName")
    public void i_do_post_activate_for_the_user_with_wrong_last_name() 
    		throws Exception {
    	
    	lastName = tUtil.AppendTimestamp(lastName); // we're updating the lastName to get an error

    	ucid = tUtil.generateRandomUcid(36);
    	
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.ucid", ucid);
    	
    	LOGGER.info(ucid + " <<-- ucid ");
    	
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.email", email);
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.lastName", lastName);
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.postalCode", postalCode);
    	jsonUtils.update_JSONValue(customersCustomerNumberActivate, "$.country", countryCode);

    	endPt = ACSProcPOSTStpes.setEndpointcustomerNumberActivate(kmap.get("customerNumber"));
    	procResp = ACSProcPOSTStpes.post(customersCustomerNumberActivate, endPt)
    			.extract().response();
    }
    
    @Then("^I see error message (.*) for lastName not matching$")
    public void i_see_error_for_lastName_not_matching(String msg) {

    	tUtil.verifyStatus(procResp, ResponseCode.OK);
    	
    	LOGGER.info(procResp.jsonPath().getString("errors[0].errorDescription") + "   errorDescription");
    	Assert.assertEquals(msg, procResp.jsonPath().getString("errors[0].errorDescription").trim());
    }
    
    @Then("^I see error message (.*) for the user is being already active$")
    public void i_see_error_for_the_user_is_being_already_active(String msg) {

    	tUtil.verifyStatus(procResp, ResponseCode.OK);
    	
    	LOGGER.info(procResp.jsonPath().getString("errors[0].errorDescription") + "   errorDescription");
    	Assert.assertEquals(msg, procResp.jsonPath().getString("errors[0].errorDescription").trim());
    }
    
    @When("I see no error in the response and subscription info")
    public void  i_see_no_error_in_the_response_and_subscription_info() 
    		throws Exception {
    	
    	String errors;
    	List subscriptions = new ArrayList<>();
    	
    	errors = procResp.jsonPath().getString("errors");
    	subscriptions = procResp.jsonPath().getList("subscriptions");
    	
    	Assert.assertEquals("[]", errors);
    	Assert.assertNotNull(subscriptions);
    }
    
    @When("I call ACSProc mappings endpoint")
    public void i_call_acs_proc_mappings_endpoint() {

    	endPt = acsProcessGET.setEndPointCustomerMappings(customerNumber);
    	procResp = acsProcessGET.get(endPt)
    			.extract().response();
    	
    	tUtil.putToSession("response", procResp);
    }
    
    @Then("I see the user has the ucid linked to it with correct email fname lname")
    public void i_see_the_user_has_the_ucid_linked_to_it_with_correct_email_fname_lname() {
    	//we verify that activated user has linked to its ucid and details are matching
    	tUtil.verifyStatus(procResp, ResponseCode.OK);
    	
    	Assert.assertEquals(ucid.toLowerCase(), procResp.jsonPath().getString("results[0].ucid").toLowerCase());
    	Assert.assertEquals(email, procResp.jsonPath().getString("results[0].email"));
    	Assert.assertEquals(firstName, procResp.jsonPath().getString("results[0].name.first"));
    	Assert.assertEquals(lastName, procResp.jsonPath().getString("results[0].name.last"));
    }
    
    @When("I call AcsSys usersUCC endpoint with the same customerNumber")
    public void i_call_acs_sys_users_ucc_endpoint_with_the_same_customer_number() {
    	
    	endPt = acsProcessGET.setEndPointUsersUCC(customerNumber);
		procResp = acsProcessGET.get(endPt)
				.extract().response();
		
		tUtil.putToSession("response", procResp);    
    }
    
    @Then("I see the user is activated")
    public void i_see_the_user_is_activated() {

    	tUtil.verifyStatus(procResp, ResponseCode.OK);
    	
    	List results = procResp.jsonPath().getList("results");
    	Assert.assertNotNull(results);
    }
    
    @When("I GET the NEJM subscriber from database for the Grace period user")
    public void i_get_the_nejm_subscriber_from_database_for_the_grace_period_user() throws Exception{

    	LOGGER.info("I GET the NEJM subscriber from database for the Grace period user");
    	LOGGER.info("Now we're waiting for the sql query being executed");

        String sqlQuery = dbUtils.buildQuery("ACSProc/acsNEJMgracePeriodUser.sql");
        String dbURL = dbUtils.buildDbUrl("ACSDbUrl");
        Connection conn = ConnectionFactory.getConnectionACS(
            dbURL, dbUtils.acsDBUser(), dbUtils.acsDBPass());
        PreparedStatement ps = conn.prepareStatement(sqlQuery) ;

        ResultSet resultSet = ps.executeQuery();
  
        while (resultSet.next()) {
            customerNumbers.add(resultSet.getString("ctm_nbr"));
        }

        tUtil.putToSession("customerNumbers", customerNumbers);        
        LOGGER.info("customerNumbers from Grace Period query --> " + customerNumbers.toString());
    }
    
    @Then("I get 200 ok with customerNumber in the response")
    public void i_get_ok_with_customer_number_in_the_response() {
    	tUtil.verifyStatus(custProcSteps.procResp, ResponseCode.OK);
        customerNumber = custProcSteps.procResp.jsonPath().get("customerNumber");
    }
    
    @When("^I get user invoice with customerNumber via ACS Proc for catalyst (.*)$")
    public void i_get_user_invoice_with_customer_number_via_acs_proc_for_catalyst_catalyst(String catalyst) throws URISyntaxException {
    	endPt = acsProcessGET.setEndpointToCustCustNumbInvoices(customerNumber);
    	procResp = acsProcessGET.getCustomers(endPt, catalyst)
    			.extract().response();
    }
    
    @Then("I get 200 with invoice in the response")
    public void i_get_with_invoice_in_the_response() {
    	tUtil.verifyStatus(procResp, ResponseCode.OK);
    }
    
    @Then("I get 400 with invoice in the response")
    public void i_get_with_invoice_in_the_response400() {
    	tUtil.verifyStatus(procResp, ResponseCode.BAD_REQUEST);
    }
    
    @When("^I get user invoice with customerNumber via ACS Proc for nejm (.*)$")
    public void i_get_user_invoice_with_customer_number_via_acs_proc_for_nejm(String nejm) throws URISyntaxException {
    	endPt = acsProcessGET.setEndpointToCustCustNumbInvoices(customerNumber);
    	procResp = acsProcessGET.getCustomers(endPt, nejm)
    			.extract().response();
    }
    
    @When("^I get user invoice with customerNumber via ACS Proc with invalid (.*) productFamily in the url$")
    public void i_get_user_invoice_with_customer_number_via_acs_proc_with_invalid_invalid_product_family_in_the_url(String invalid) throws URISyntaxException {
    	endPt = acsProcessGET.setEndpointToCustCustNumbInvoices(customerNumber);
    	procResp = acsProcessGET.getCustomers(endPt, invalid)
    			.extract().response();
    }

    @When("I get user invoice with customerNumber via ACS Proc with empty productFamily in the url")
    public void i_get_user_invoice_with_customer_number_via_acs_proc_with_empty_product_family_in_the_url() throws URISyntaxException {
    	endPt = acsProcessGET.setEndpointToCustCustNumbInvoices(customerNumber);
    	procResp = acsProcessGET.getCustomers(endPt, "")
    			.extract().response();
    }

//    @When("^I send a POST request to add a customer$")
    @When("^I send a POST request to process API create a customer$")
    public void post_Customer() throws Exception {
        String email_value = tUtil.AppendTimestamp("automation@example.com");
        String ucid = tUtil.generateRandomUcid(36);
        String firstName = tUtil.AppendTimestamp("test_fname");
        String lastName = tUtil.AppendTimestamp("test_lname");
        jsonUtils.update_JSONValue(file_name, "$.ucid", ucid);
        jsonUtils.update_JSONValue(file_name, "$.address.email", email_value);
        jsonUtils.update_JSONValue(file_name, "$.address.name.firstName", firstName);
        jsonUtils.update_JSONValue(file_name, "$.address.name.lastName", lastName);

        // Storing fields name in kmap
        kmap.put("ucid", ucid);
        kmap.put("email", email_value);
        kmap.put("firstName", firstName);
        kmap.put("lastName", lastName);

        endPt = ACSProcPOSTStpes.setEndpoint();
        procResp = ACSProcPOSTStpes.post(file_name, endPt).extract().response();

        String customerNumber = procResp.jsonPath().getString("customerNumber");
        String addressCode = procResp.jsonPath().getString("addressCode");
        kmap.put("addressCode", addressCode);
        kmap.put("customerNumber", customerNumber);
        kmap.put("email", email_value);
        kmap.put("lastName", lastName);
    }

    @Then("^user sends a get request to get details of subscription status for customer$")
    public void userSendsAGetRequestToGetDetailsOfSubscriptionStatusForCustomer() throws URISyntaxException {
        String end_pt = acsGETSteps.setSubscriptionsEndpoint(kmap.get("customerNumber"));
        procResp = acsGETSteps.processGET(end_pt).extract().response();
    }

    @Then("^agreement status should be returned in response$")
    public void agreementStatusShouldBeReturnedInResponse() {
        JsonPath jsonPathEvaluator = procResp.jsonPath();
        String status = jsonPathEvaluator.getString("[0].status.code");
        Assert.assertNotNull(status);
    }

    @Then("^response should contain (.*) with (.*)$")
    public void responseShouldContainMsgWithSts(String msg, Integer sts) {
        tUtil.verify_msgCode(procResp, msg, sts);
    }

    @When("^user sends a get request to get details of subscription status with invalid customer number (.*)$")
    public void userSendsAGetRequestToGetDetailsOfSubscriptionStatusWithInvalidCustomerNumberCustNum(String custnum) throws URISyntaxException {
        String end_pt = acsGETSteps.setSubscriptionsEndpoint(custnum);
        procResp = acsGETSteps.processGET(end_pt).extract().response();
    }

    @Given("^I do a POST request to create single agreement$")
    public void iDoAPOSTRequestToCreateSingleAgreement() throws Exception {
        post_Customer();
        jsonUtils.update_JSONValue(file_order, "$.billTo.customerNumber", kmap.get("customerNumber"));
        jsonUtils.update_JSONValue(file_order, "$.billTo.addressCode", kmap.get("addressCode"));
        ACSPOSTSteps.processOrder(file_order);
    }

    @When("^user sends a get request to get details of subscription status for customer with active agreement$")
    public void userSendsAGetRequestToGetDetailsOfSubscriptionStatusForCustomerWithActiveAgreement() throws URISyntaxException {
        String end_pt = acsGETSteps.setSubscriptionsEndpoint(kmap.get("customerNumber"));
        procResp = acsGETSteps.processGET(end_pt).extract().response();
    }

    @Then("^active agreement should be returned in response$")
    public void activeAgreementShouldBeReturnedInResponse() {
        JsonPath jsonPathEvaluator = procResp.jsonPath();
        List<Object> agreementsList = jsonPathEvaluator.getList("agreements");
        Assert.assertEquals(agreementsList.size(), 1);
    }

    @Given("^I do a POST request to create multi agreement$")
    public void iDoAPOSTRequestToCreateMultiAgreement() throws Exception {
        post_Customer();
        jsonUtils.update_JSONValue(file_order, "$.billTo.customerNumber", kmap.get("customerNumber"));
        jsonUtils.update_JSONValue(file_order, "$.billTo.addressCode", kmap.get("addressCode"));
        ACSPOSTSteps.processOrder(file_order);
        ACSPOSTSteps.processOrder(file_order);
    }

    @Then("^active agreements should be returned in response$")
    public void activeAgreementsShouldBeReturnedInResponse() {
        JsonPath jsonPathEvaluator = procResp.jsonPath();
        List<Object> agreementsList = jsonPathEvaluator.getList("agreements");
        Assert.assertEquals(agreementsList.size(), 2);
    }

    @Then("^The request should return 201 status code$")
    public void theRequestShouldReturn201StatusCode() {
        assertEquals(procResp.getStatusCode(), 201);
    }

    @Then("^The request should return 400 status code$")
    public void theRequestShouldReturn400StatusCode() {
        assertEquals(procResp.getStatusCode(), 400);
    }

    @Then("^The request should return 200 status code$")
    public void theRequestShouldReturn200StatusCode() {
        assertEquals(procResp.getStatusCode(), 200);
    }

    @Then("^The request should return customer number and 200 status code$")
    public void theRequestShouldReturnCustomerNumberAndStatusCode() {
        assertEquals(procResp.getStatusCode(), 200);

        JsonPath jsonpathEvaluator = procResp.jsonPath();

        String[] arrayKeys = {"customerNumber", "addressCode"};
        List<String> loKeys = Arrays.asList(arrayKeys);

        // Verification of fields existence
        for (String k : loKeys) {
            Assert.assertNotNull(jsonpathEvaluator.getString(k));
        }
    }

    @When("^I send a POST request to add a customer with invalid number (.*)$")
    public void iSendAPOSTRequestToAddACustomerWithInvalidNumberCustomerNumber(String ucid) throws Exception {
        String email_value = tUtil.AppendTimestamp("automation@example.com");
        String firstName = tUtil.AppendTimestamp("test_fname");
        String lastName = tUtil.AppendTimestamp("test_lname");
        jsonUtils.update_JSONValue(file_name, "$.ucid", ucid);
        jsonUtils.update_JSONValue(file_name, "$.address.email", email_value);
        jsonUtils.update_JSONValue(file_name, "$.address.name.firstName", firstName);
        jsonUtils.update_JSONValue(file_name, "$.address.name.lastName", lastName);

        procResp = ACSProcPOSTStpes.customers(file_name).extract().response();

        String gen_ucid = tUtil.generateRandomUcid(36);
        jsonUtils.update_JSONValue(file_name, "$.ucid", gen_ucid);
    }

    @When("^I send a POST request to API with the same payload$")
    public void iSendAPOSTRequestToAPIWithTheSamePayload() {
        procResp = ACSProcPOSTStpes.customers(file_name).extract().response();
    }

    @Then("^The same customerNumber should be returned in response$")
    public void theSameCustomerNumberShouldBeReturnedInResponse() {
        String customerNumber = procResp.jsonPath().getString("customerNumber");
        assertEquals(customerNumber, kmap.get("customerNumber"));
    }

    @When("^I send a GET call SystemAPI to get detail customer$")
    public void iSnedAGETCallSystemAPIToGetDetailCustomer() {
        endPt = acsGETSteps.setEndpoint(kmap.get("customerNumber"));
        sysResp = acsGETSteps.get(endPt)
        		.extract().response();
        
        tUtil.putToSession("customerNumber", kmap.get("customerNumber"));
    }

    @Then("^lastname and email returned in response should match with provided data$")
    public void lastnameAndEmailReturnedInResponseShouldMatchWithProvidedData() {
        JsonPath jsonpathEvaluator = sysResp.jsonPath();

        String resp_email = jsonpathEvaluator.getString("addresses[0].email");
        String resp_lname = jsonpathEvaluator.getString("addresses[0].name.lastName");

        assertEquals(resp_email, kmap.get("email"));
        assertEquals(resp_lname.toLowerCase(), kmap.get("lastName"));
    }

    @Then("^The request SystemAPI should return 200 status code$")
    public void theRequestSystemAPIShouldReturn200StatusCode() {
        assertEquals(sysResp.getStatusCode(), 200);
    }

    @When("^I send a POST request to create customer with invalid data for (.*) and (.*)$")
    public void iSendAPOSTRequestToCreateCustomerWithInvalidDataForFieldNameAndFieldValue(String fieldName, String fieldValue) throws Exception {
        String email_value = tUtil.AppendTimestamp("automation@example.com");
        String ucid = tUtil.generateRandomUcid(36);
        String firstName = tUtil.AppendTimestamp("test_fname");
        String lastName = tUtil.AppendTimestamp("test_lname");
        jsonUtils.update_JSONValue(file_name, "$.ucid", ucid);
        jsonUtils.update_JSONValue(file_name, "$.address.email", email_value);
        jsonUtils.update_JSONValue(file_name, "$.email", email_value);
        jsonUtils.update_JSONValue(file_name, "$.address.name.firstName", firstName);
        jsonUtils.update_JSONValue(file_name, "$.address.name.lastName", lastName);

        if (fieldName.equals("lastName") || fieldName.equals("firstName")) {
            String value_field = jsonUtils.getFromJSON(file_name, "$.address.name.".concat(fieldName));
            jsonUtils.update_JSONValue(file_name, "$.address.name.".concat(fieldName), fieldValue);

            procResp = ACSProcPOSTStpes.customers(file_name).extract().response();

            jsonUtils.update_JSONValue(file_name, "$.address.name.".concat(fieldName), value_field);
        } else if (fieldName.equals("email")){
            String value_field = jsonUtils.getFromJSON(file_name, "$.address".concat(fieldName));
            jsonUtils.update_JSONValue(file_name, "$.address.".concat(fieldName), fieldValue);

            procResp = ACSProcPOSTStpes.customers(file_name).extract().response();

            jsonUtils.update_JSONValue(file_name, "$.address.".concat(fieldName), value_field);
        } else {
            String value_field = jsonUtils.getFromJSON(file_name, "$.".concat(fieldName));
            jsonUtils.update_JSONValue(file_name, "$.".concat(fieldName), fieldValue);

            procResp = ACSProcPOSTStpes.customers(file_name).extract().response();

            jsonUtils.update_JSONValue(file_name, "$.".concat(fieldName), value_field);
        }
    }

    @Then("^appropriate status (.*) and message (.*) should be returned$")
    public void appropriateStatusStsAndMessageMsgShouldBeReturned(int sts, String msg) {
        tUtil.verify_msgCode(procResp, msg, sts);
    }

    public void createControlGroup() throws Throwable {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        jsonUtils.update_JSONValue(filePostControlGroup, "$.CTGDate", timeStamp);
        jsonUtils.update_JSONValue(file_order, "$.controlGroupDate", timeStamp);

        endPt = ACSPOSTSteps.setEndpointControlGroup();
         procResp = ACSPOSTSteps.post(filePostControlGroup, endPt)
                .extract().response();
        kmap.put("timeStamp", timeStamp);
        theRequestShouldReturn201StatusCode();
    }

    @When("^User sends a POST request to process API to create order with valid data for order and credit card$")
    public void userSendsAPOSTRequestToProcessAPIToCreateOrderWithValidDataForOrderAndCreditCard() throws Throwable {
        createControlGroup();
        jsonUtils.update_JSONValue(file_order, "$.billTo.customerNumber", kmap.get("customerNumber"));
        jsonUtils.update_JSONValue(file_order, "$.billTo.addressCode", kmap.get("addressCode"));
        jsonUtils.update_JSONValue(file_order, "$.billLater", false);
        procResp = ACSPOSTSteps.processOrder(file_order).extract().response();

        String orderNumber = procResp.jsonPath().getString("orderNumber");
        kmap.put("orderNumber", orderNumber);
        jsonUtils.update_JSONValue(file_order, "$.billLater", true);
    }

    @And("^response should contain status value true, hasWarning false, customerNumber and OrderNumber in response$")
    public void responseShouldContainStatusValueTrueHasWarningFalseCustomerNumberAndOrderNumberInResponse() {
        JsonPath jsonpathEvaluator = procResp.jsonPath();

        String success = jsonpathEvaluator.getString("success");
        String hasWarnings = jsonpathEvaluator.getString("hasWarnings");

        assertEquals(success, "true");
        assertEquals(hasWarnings, "false");
        assertNotNull(jsonpathEvaluator.getString("customerNumber"));
        assertNotNull(jsonpathEvaluator.getString("orderNumber"));
    }

    @When("^User sends a POST request to ACS to create order with valid data for order and invalid data for credit card$")
    public void userSendsAPOSTRequestToACSToCreateOrderWithValidDataForOrderAndInvalidDataForCreditCard() throws Throwable {
        createControlGroup();
        String  expYear = jsonUtils.getFromJSON(file_order, "$.creditCard.expireYear");
        jsonUtils.update_JSONValue(file_order, "$.billLater", false);
        jsonUtils.update_JSONValue(file_order, "$.billTo.customerNumber", kmap.get("customerNumber"));
        jsonUtils.update_JSONValue(file_order, "$.billTo.addressCode", kmap.get("addressCode"));
        jsonUtils.update_JSONValue(file_order, "$.creditCard.expireYear", 12026);
        procResp = ACSPOSTSteps.processOrder(file_order).extract().response();

        jsonUtils.update_JSONValue(file_order, "$.creditCard.expireYear", Integer.valueOf(expYear));
        jsonUtils.update_JSONValue(file_order, "$.billLater", true);

        String orderNumber = procResp.jsonPath().getString("orderNumber");
        kmap.put("orderNumber", orderNumber);
    }

    @And("^response should contain status value, hasWarning, customerNumber and error message in response$")
    public void responseShouldContainStatusValueHasWarningCustomerNumberAndErrorMessageInResponse() {
        JsonPath jsonpathEvaluator = procResp.jsonPath();

        String success = jsonpathEvaluator.getString("success");
        String hasWarnings = jsonpathEvaluator.getString("hasWarnings");
        String errorMsg = jsonpathEvaluator.getString("errorMessage");

        assertEquals(success, "true");
        assertEquals(hasWarnings, "true");
        assertEquals(errorMsg, "\n Error when applying payment to order");
    }

    @When("^User sends a POST request to process API to create order with invalid order data$")
    public void userSendsAPOSTRequestToProcessAPIToCreateOrderWithInvalidOrderData() throws Throwable {
        createControlGroup();
        String  priceCode = jsonUtils.getFromJSON(file_order, "$.orderLines[0].priceCode");
        jsonUtils.update_JSONValue(file_order, "$.orderLines[0].priceCode", "");
        jsonUtils.update_JSONValue(file_order, "$.billTo.customerNumber", kmap.get("customerNumber"));
        jsonUtils.update_JSONValue(file_order, "$.billTo.addressCode", kmap.get("addressCode"));
        procResp = ACSPOSTSteps.processOrder(file_order).extract().response();

        jsonUtils.update_JSONValue(file_order, "$.orderLines[0].priceCode", priceCode);
    }

    @And("^response should contain status value true, hasWarning false, customerNumber and error in response$")
    public void responseShouldContainStatusValueTrueHasWarningFalseCustomerNumberAndErrorInResponse() {
        JsonPath jsonpathEvaluator = procResp.jsonPath();

        String success = jsonpathEvaluator.getString("success");
        String hasWarnings = jsonpathEvaluator.getString("hasWarnings");
        String errorMsg = jsonpathEvaluator.getString("errorMessage");

        assertEquals(success, "false");
        assertEquals(hasWarnings, "false");
        assertEquals(errorMsg, "\n Error when creating order");
    }

    // TODO need check
    @When("^User sends a POST request to process API to create order with invalid data$")
    public void userSendsAPOSTRequestToProcessAPIToCreateOrderWithInvalidData() throws Throwable {
        createControlGroup();
        jsonUtils.update_JSONValue(file_order, "$.billLater", "false");
        jsonUtils.update_JSONValue(file_order, "$.billTo.customerNumber", kmap.get("customerNumber"));
        jsonUtils.update_JSONValue(file_order, "$.billTo.addressCode", kmap.get("addressCode"));
        procResp = ACSPOSTSteps.processOrder(file_order).extract().response();

        jsonUtils.update_JSONValue(file_order, "$.billLater", true);
    }

    @When("^User sends a get request to get details of order for customer with customerNumber$")
    public void userSendsAGetRequestToGetDetailsOfOrderForCustomerWithCustomerNumber() {
        endPt = acsGETSteps.setOrdersEndpoint(kmap.get("customerNumber"));
        sysResp = acsGETSteps.get(endPt).extract().response();
    }

    @Then("^the order should appear in the list of orders$")
    public void theOrderShouldAppearInTheListOfOrders() {
        JsonPath jsonpathEvaluator = sysResp.jsonPath();

        String orderNum = jsonpathEvaluator.getString("orders[0].orderNumber");
        assertEquals(orderNum, kmap.get("orderNumber"));
    }
//
//    @Then("^the payment with amount should be inserted for order$")
//    public void thePaymentWithAmountShouldBeInsertedForOrder() {
//        JsonPath jsonpathEvaluator = sysResp.jsonPath();
//
//        String amount = tUtil.getFromJSON(file_order, "$.amount");
//
//
//        String orderNum = jsonpathEvaluator.getString("orders[0].orderNumber");
//
//        List<Float> loAmountDues = jsonpathEvaluator.getList("orders.amountDue");
//        List<Float> loCost = jsonpathEvaluator.getList("orders.cost");
//
//        assertEquals(orderNum, kmap.get("orderNumber"));
//        assertEquals(
//               tUtil.round(Float.valueOf(amount), 1),
//               tUtil.round((loCost.get(0) - loAmountDues.get(0)),1),
//               0.001
//        );
//    }

    @Then("^the payment with amount than the amountDue should be inserted for order$")
    public void thePaymentWithAmountThanTheAmountDueShouldBeInsertedForOrder() {
        JsonPath jsonpathEvaluator = sysResp.jsonPath();

        String amount = jsonUtils.getFromJSON(file_order, "$.amount");
        String orderNum = jsonpathEvaluator.getString("orders[0].orderNumber");
        List<Float> loAmountDues = jsonpathEvaluator.getList("orders.amountDue");

        assertEquals(orderNum, kmap.get("orderNumber"));

        assertTrue(
                tUtil.round(loAmountDues.get(0),1) >
                tUtil.round(Float.valueOf(amount), 1)
        );
    }

    @When("^User sends a get request to get details of order for agreements with customerNumber$")
    public void userSendsAGetRequestToGetDetailsOfOrderForAgreementsWithCustomerNumber() {
        endPt = acsGETSteps.setCustomerAgreementsEndpoint(kmap.get("customerNumber"));
        sysResp = acsGETSteps.get(endPt).extract().response();
    }

    @Then("^the total amountDue for order should be update accordingly$")
    public void theTotalAmountDueForOrderShouldBeUpdateAccordingly() {
        JsonPath jsonpathEvaluator = sysResp.jsonPath();

        List<Float> loCost = jsonpathEvaluator.getList("orders.cost");
        List<Float> loAmountDues = jsonpathEvaluator.getList("orders.amountDue");


        assertNotEquals(
                tUtil.round(loAmountDues.get(0),1),
                tUtil.round(loCost.get(0), 1),
                0.0001
        );
    }

    @Then("^the payment with amount should be inserted for order agreements$")
    public void thePaymentWithAmountShouldBeInsertedForOrderAgreements() {
        JsonPath jsonpathEvaluator = sysResp.jsonPath();

        String amount = jsonUtils.getFromJSON(file_order, "$.amount");

        String orderNum = jsonpathEvaluator.getString("agreements[0].orderNumber");

        List<Float> loAmount = jsonpathEvaluator.getList("agreements[0].credits.amount");

        assertEquals(orderNum, kmap.get("orderNumber"));
        assertEquals(
                tUtil.round(Float.valueOf(amount), 1),
                tUtil.round((loAmount.get(0)),1),
                0.001
        );
    }

    @Then("^no payment should be inserted for order$")
    public void noPaymentShouldBeInsertedForOrder() {
        JsonPath jsonpathEvaluator = sysResp.jsonPath();

        List payment = jsonpathEvaluator.getList("agreements[0].credits");
        assertEquals(payment.size(), 0);
    }
    
    @When("I fetch the top twenty customer numbers of customers with along with {string} their fistName lastName email")
	public void fetch_customerNum_toVerfy_inst_admin_frmDB(String custType) throws IOException, SQLException {
		String sqlQuery = null;
		switch (custType) {

		case "active institution subscriptions":
			sqlQuery = dbUtils.buildQuery("ACSProc/acsCustomers_with_act_Inst_subscrptn.sql");
			break;

		case "non-institution subscriptions":
			sqlQuery = dbUtils.buildQuery("ACSProc/acsCustomers_with_noInst_subscrptn.sql");
			break;

		case "inactive institution subscription":
			sqlQuery = dbUtils.buildQuery("ACSProc/acsCustomers_with_inActInst_subscrptn.sql");
			break;
			
		default:
			LOGGER.info("Invalid SQL Query file");
		}

		String dbURL = dbUtils.buildDbUrl("ACSDbUrl");
		Connection conn = ConnectionFactory.getConnectionACS(dbURL, dbUtils.acsDBUser(), dbUtils.acsDBPass());
		PreparedStatement ps = conn.prepareStatement(sqlQuery);

		ResultSet resultSet = ps.executeQuery();

		while (resultSet.next()) {
			if (custType.equals("non-institution subscriptions")) {
				customerNumbers.add(resultSet.getString("ctm_nbr"));
			} else {
				customerNumbers.add(resultSet.getString("bil_ctm"));
			}

			fNameList.add(resultSet.getString("atn_1st"));
			lNameList.add(resultSet.getString("atn_end"));
			emailList.add(resultSet.getString("adr_emal"));
		}

	}
    
    @And("I send a GET call to fetch the activated product details to verify inst admin details")
    public void GET_callTo_verify_inst_admin_details() throws URISyntaxException {
    	for (int i = 0; i < customerNumbers.size(); i++) {
    		customerNumber = customerNumbers.get(i);
    		firstName = fNameList.get(i);
    		lastName = lNameList.get(i);
    		email = emailList.get(i);
    		
    		endPt = acsGETSteps.setVerifyInstAdminEndpoint(customerNumber);
            procResp = acsGETSteps.getInstAdminDetails(endPt,firstName, lastName, email).extract().response();
            
            if (procResp.getStatusCode() == ResponseCode.OK) {
				break;
			}
    	}
    	
    }
    
    @Then("I should verify that expected product array details should be returned for Customers with active institution product with status code 200")
    public void verify_fetched_prdct_details() {
    	softAssert.assertThat(procResp.statusCode()).isEqualTo(ResponseCode.OK);
    	for(int i=0;i<procResp.jsonPath().getList("activatedProducts").size();i++ ) {
    		softAssert.assertThat(procResp.jsonPath().getString("activatedProducts.productDescription"+"["+i+"]")).contains("NEJM");
    		softAssert.assertThat(procResp.jsonPath().getString("activatedProducts.productDescription"+"["+i+"]")).contains("License");
    		softAssert.assertThat(procResp.jsonPath().getString("errors")).isEqualTo("[]");
    		softAssert.assertThat(procResp.jsonPath().getString("activatedProducts.productCode"+"["+i+"]")).isNotNull();
    	}
    	softAssert.assertAll();
    }
    
    @Then("I should verify that error should trigger for Customers with non-institution subscriptions with status code 200")
    public void verify_fetched_nonInst_subscrDtls() {
    	softAssert.assertThat(procResp.statusCode()).isEqualTo(ResponseCode.OK);
    	for(int i=0;i<procResp.jsonPath().getList("errors").size();i++ ) {
    		softAssert.assertThat(procResp.jsonPath().getString("errors.errorCode"+"["+i+"]")).isNotNull();
    		softAssert.assertThat(procResp.jsonPath().getString("errors.message"+"["+i+"]")).isNotNull();
    	}
    	softAssert.assertAll();
    }
    
    @Then("I should verify that error should trigger for Customers with inactive institution subscription with status code 200")
    public void verify_fetched_inActInst_subscrDtls() {
    	softAssert.assertThat(procResp.statusCode()).isEqualTo(ResponseCode.OK);
    	for(int i=0;i<procResp.jsonPath().getList("errors").size();i++ ) {
    		softAssert.assertThat(procResp.jsonPath().getString("errors.errorCode"+"["+i+"]")).isNotNull();
    		softAssert.assertThat(procResp.jsonPath().getString("errors.message"+"["+i+"]")).isNotNull();
    		softAssert.assertThat(procResp.jsonPath().getString("errors.productCode"+"["+i+"]")).isNotNull();
    	}
    	if(procResp.jsonPath().getString("activatedProducts")!="[]") {
    		for(int i=0;i<procResp.jsonPath().getList("activatedProducts").size();i++ ) {
    			softAssert.assertThat(procResp.jsonPath().getString("activatedProducts.productDescription"+"["+i+"]")).isNotNull();
    			softAssert.assertThat(procResp.jsonPath().getString("activatedProducts.productCode"+"["+i+"]")).isNotNull();
    		}
    	}else {
    		softAssert.assertThat(procResp.jsonPath().getString("activatedProducts")).isEqualTo("[]");
    	}    	
    	softAssert.assertAll();
    }
}
