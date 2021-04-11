package ucc.i.steps.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import org.assertj.core.api.SoftAssertions;
import org.json.JSONException;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ucc.i.method.accountexp.AccountExpGET;
import ucc.i.method.accountexp.AccountHelper;
import ucc.i.method.acs.ACSGET;
import ucc.i.method.aic.AICGET;
import ucc.i.method.aic.AICPOST;
import ucc.i.method.catalystLiteratum.CatalystLiteratumSystemPOST;
import ucc.i.method.customerprocess.CustomerProcessGET;
import ucc.i.method.customerprocess.CustomerProcessHelper;
import ucc.i.method.customerprocess.CustomerProcessPATCH;
import ucc.i.method.customerprocess.CustomerProcessPOST;
import ucc.i.method.literatum.LiteratumGET;
import ucc.i.method.nejmliteratumsystem.NEJMLiteratumSystemGET;
import ucc.i.method.nejmliteratumsystem.NEJMLiteratumSystemPOST;
import ucc.i.steps.experience.AccountExpSteps;
import ucc.i.steps.experience.ExpJsonBodyFile;
import ucc.i.steps.system.CatalystLiteratumSystemJsonBodyFile;
import ucc.i.steps.system.NEJMLiteratumSystemJsonBodyFile;
import ucc.utils.*;
import ucc.utils.CucumberUtils.CucumberUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;


public class CustProcSteps {

    private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    private static String autoEmail =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("autoEmail");
    private static Response sysResp = null;
    public static Response procResp = null;
    public static Map<String, String> kmap = new HashMap<String, String>();
    private static String endPt = null;
    public static String emailValue;
    private static Integer ticketId;
    private static String ucid;
    private final TestUtils tUtil = new TestUtils();
    private final JsonUtils jsonUtils = new JsonUtils();
    private final DbUtils dbUtils = new DbUtils();
    private List<String> emailList = new ArrayList<>();
    private List<String> subsIdList = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(CustProcSteps.class);
    private static AccountExpSteps accountExpSteps = new AccountExpSteps();
    private String subscrptnID;
    SoftAssertions softAssert = new SoftAssertions();
    private boolean procRespTagLabel = false;
	private boolean procRespTagCode = false;
	private boolean procRespTagSetCode = false;

    
    
    @Steps
    AccountExpGET accExpSteps;

    @Steps
    CustomerProcessGET custProcStepsGET;

    @Steps
    CustomerProcessPOST custProcStepsPOST;

    @Steps
    CustomerProcessPATCH custProcStepsPATCH;

    @Steps
    AICGET aicGETSteps;

    @Steps
    AICPOST aicPOSTSteps;

    @Steps
    LiteratumGET literatumGETSteps;

    @Steps
    ACSGET acsGETSteps;

    @Steps
    CustomerProcessHelper customerProcessHelper;
    
    @Steps
	NEJMLiteratumSystemGET nejmLitrtmSystemGET;
    @Steps
    CatalystLiteratumSystemPOST catJLSystmPOST;
    @Steps
	NEJMLiteratumSystemPOST nejmLiteratumSystemPOST;
    
    
    private static final String FILE_CREATE_TICKET = "Zendesk_Post_Create_Ticket.json";
    private static final String FILE_PROC_API_CREATE_USER = "ProcCustomer_POST_reg_user.json";
    private static final String FILE_PROC_API_USER = "CustomerProc_PATCH_User.json";
    private static final String FILE_POST_INVALID_TICKET = "CustomerProc_POST_Invalid_Ticket.json";
    private static final String customerUCIDpayments = "CustomerProcess/customerUCIDpayments.json";
    private static final String activateUser = "CustomerProcess/activateUser.json";

    
 
    @When("I do POST activate via CustProc with wrong lastName")
    public void i_do_post_activate_with_wrong_lastName() 
    		throws Exception {
    	
    	tUtil.putToSession("lastName", tUtil.AppendTimestamp("lastName"));
    	
    	customerProcessHelper.update_jsonFile_for_activate(activateUser);
    	
    	endPt = custProcStepsPOST.setEndpoint_activate((String) tUtil.getFromSession("ucid"));
    	procResp = custProcStepsPOST.postToken(activateUser, endPt, tUtil.getToken((String) tUtil.getFromSession("ucid")))
    			.extract().response();
    	
    	tUtil.putToSession("response", procResp);
    }
    
    @Then("^I see error message (.*) for the lastName not matches for custProc call$")
    public void i_see_error_for_the_lastName_not_matching(String message) {

    	tUtil.verifyStatus(procResp, ResponseCode.CREATED);
    	Assert.assertEquals(message, procResp.jsonPath().getString("[0].errors[0].errorDescription").trim());
    }
    
    @When("I do POST activate to the same user via CustProc")
    public void i_do_post_activate_to_the_same_user_via_cust_proc() {
    	
    	endPt = custProcStepsPOST.setEndpoint_activate((String) tUtil.getFromSession("ucid"));
    	procResp = custProcStepsPOST.postToken(activateUser, endPt, tUtil.getToken((String) tUtil.getFromSession("ucid")))
    			.extract().response();
    	
    	tUtil.putToSession("response", procResp);
    }
    
    @Then("^I see error message (.*) for the already activated user for custProc call$")
    public void i_see_error_for_the_already_activated_user(String message) {

    	tUtil.verifyStatus(procResp, ResponseCode.CREATED);
    	Assert.assertEquals(message, procResp.jsonPath().getString("[0].errors[0].errorDescription").trim());
    }
    
    @When("I do POST activate via CustProc")
    public void i_do_post_activate_via_cust_proc() 
    		throws Exception {

    	customerProcessHelper.update_jsonFile_for_activate(activateUser);
    	
    	endPt = custProcStepsPOST.setEndpoint_activate((String) tUtil.getFromSession("ucid"));
    	procResp = custProcStepsPOST.postToken(activateUser, endPt, tUtil.getToken((String) tUtil.getFromSession("ucid")))
    			.extract().response();
    	
    	tUtil.putToSession("response", procResp);
    }
    
    @When("I do POST activate via CustProc with wrong akamaiToken")
    public void i_do_post_activate_via_my_acc_exp_with_wrong_subscription_id() 
    		throws Exception {
    	
    	customerProcessHelper.update_jsonFile_for_activate(activateUser);

    	endPt = custProcStepsPOST.setEndpoint_activate((String) tUtil.getFromSession("ucid"));
    	procResp = custProcStepsPOST.postToken(activateUser, endPt, tUtil.generateRandomUcid(10))
    			.extract().response();

    	tUtil.putToSession("response", procResp);
    }
    
    @Then("^I see error message (.*) for the invalid akamaiToken and 401 statusCode from custProc$")
    public void i_see_error_message_message_for_the_invalid_akamai_token_and_status_code(String message) {

    	tUtil.verifyStatus(procResp, ResponseCode.UNAUTHORIZED);
    	Assert.assertEquals(message, procResp.jsonPath().getString("message").trim());
    }
    
    @When("I do GET call to customers endpoint with ucid")
    public void i_do_get_call_to_customers_endpoint_with_ucid() {
    	endPt = custProcStepsGET.setEndpointCustomersUCID(kmap.get("ucid"));
    	procResp = custProcStepsGET.getUserByUcid(endPt, kmap.get("accessToken"))
    			.extract().response();
    	
    	tUtil.putToSession("response", procResp);
    }
    
    @When("I do GET call to customers endpoint with invalid ucid")
    public void i_do_get_call_to_customers_endpoint_with_invalid_ucid() {
    	endPt = custProcStepsGET.setEndpointCustomersUCID(tUtil.AppendTimestamp(kmap.get("ucid")));
    	procResp = custProcStepsGET.getUserByUcid(endPt, kmap.get("accessToken"))
    			.extract().response();
    	
    	tUtil.putToSession("response", procResp);
    }
    
    @When("I do GET call to customers endpoint without ucid")
    public void i_do_get_call_to_customers_endpoint_without_ucid() {
    	endPt = custProcStepsGET.setEndpointCustomersUCID("");
    	procResp = custProcStepsGET.getUserByUcid(endPt, kmap.get("accessToken"))
    			.extract().response();
    	
    	tUtil.putToSession("response", procResp);
    }
    
    @When("I call ACS system api with email to get the customerNumber for RegisteredUser")
    public void i_call_acs_system_api_with_email_to_get_the_customer_number_for_RegisteredUser() throws URISyntaxException {
        endPt = acsGETSteps.setEndpoint();
        sysResp = acsGETSteps.getCustomers(endPt, "email", accountExpSteps.kmap.get("email"))
        		.extract().response();
    }
    
    @Then("I got 200 with empty body")
    public void i_got_with_empty_body() {
    	tUtil.verifyStatus(sysResp, ResponseCode.OK);
    	tUtil.verify_emptyArray(sysResp);
    }
    
    @When("I call ACS system api with email to get the customerNumber")
    public void i_call_acs_system_api_with_email_to_get_the_customer_number() throws URISyntaxException {
        endPt = acsGETSteps.setEndpoint();
        sysResp = acsGETSteps.getCustomers(endPt, "email", emailValue)
        		.extract().response();
    }

    @Then("I see 200 with customerNumber in response")
    public void i_see_with_customer_number_in_response() throws JsonMappingException, JsonProcessingException {
    	tUtil.verifyStatus(sysResp, ResponseCode.OK);
        kmap.put("customerNumber", sysResp.jsonPath().getString("[0].customerNumber"));
    }

    @When("I call ACS system api with customerNumber to get the orderId")
    public void i_call_acs_system_api_with_customer_number_to_get_the_order_id() {
        endPt = acsGETSteps.setCustomerAgreementsEndpoint(kmap.get("customerNumber"));
        sysResp = acsGETSteps.get(endPt)
        		.extract().response();
    }

    @Then("I see 200 with orderId in response")
    public void i_see_with_order_id_in_response() {
        tUtil.verifyStatus(sysResp, ResponseCode.OK);
        kmap.put("orderId", sysResp.jsonPath().getString("agreements[0].orderNumber"));
    }

    @When("I do a GET using ucid to CustProc to see invoices for the user")
    public void i_do_a_get_to_cust_proc_to_see_invoices_for_the_user() {
    	endPt = custProcStepsGET.setEndpointCustomersUCIDinvoices(ucid);
    	procResp = custProcStepsGET.getUserByUcid(endPt, kmap.get("accessToken"))
    			.extract().response();
    }

    @Then("I see 200 in response with no invoice")
    public void i_see_in_response_with_no_invoice() {
    	tUtil.verifyStatus(procResp, ResponseCode.OK);
    	
    	kmap.put("invoices", procResp.jsonPath().get("invoices"));
    	Assert.assertEquals(0, procResp.jsonPath().getList("invoices").size());
    }
    
    @Then("I see orderId and productCode fields in response and agreementId has been removed from response")
    public void i_see_order_id_and_product_code_fields_in_response_and_agreement_id_has_been_removed_from_response() {
        Assert.assertFalse(procResp.jsonPath().getList("invoices.products.productCode").isEmpty());
        Assert.assertFalse(procResp.jsonPath().getList("invoices.orderId").isEmpty());
    	Assert.assertFalse(procResp.jsonPath().get("invoices.products").toString().contains("agreementId"));
    }
    
    @When("^I get users email list from database using customerWithInvoice query$")
    public void getTopTenUsers() throws SQLException, IOException {

    	LOGGER.info("I get users email list using customerWithInvoice query");
    	LOGGER.info("Now we're waiting for the sql query being executed");
    	
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateTo = localDate.format(formatter);

        String sqlQuery = dbUtils.buildQuery("paymybill.sql");
        String dbURL = dbUtils.buildDbUrl("ACSDbUrl");
        Connection conn = ConnectionFactory.getConnectionACS(
            dbURL, dbUtils.acsDBUser(), dbUtils.acsDBPass());
        PreparedStatement ps = conn.prepareStatement(sqlQuery) ;

        ps.setString( 1, dateTo );
        ResultSet resultSet = ps.executeQuery() ;
  
        while (resultSet.next()) {
            emailList.add(resultSet.getString("ADR_EMAL"));
        }
        
//        Serenity.recordReportData().withTitle("Database evidence").andContents(emailList.toString());

        tUtil.putToSession("emailList", emailList);        
        LOGGER.info("emailList  --> " + emailList.toString());
     }
    
    @And("^User retreives Email list")
    public void Getuseremaillist() {
    	emailList = (ArrayList<String>) tUtil.getFromSession("emailList");
    }
    
    @When("^I get users info from AIC system api using email adress$")
    public void iGetUsersInfoUsingEmail() {
    	
    	boolean flag = false;
    	
    	for (int i = 0; i < 10; i++) {
    		//we're getting email from the emailList 
    		//so we can call akamai and get the user's uuid
    		emailValue = emailList.get(i);
    		endPt = aicGETSteps.setEndPointEmail(emailList.get(i));
            sysResp = aicGETSteps.getUserFromAkamai(endPt)
            		.extract().response();
            
            //from sysResp we are getting uuid and 
            //we're gonna get the accessToken with it
            kmap.put("ucid", sysResp.jsonPath().getString("uuid"));
            ucid = sysResp.jsonPath().getString("uuid");
            userSendsAPostRequestWithUcidToGetAkamaiAccessToken();
            String token = sysResp.jsonPath().get("accessToken");
            
            //we're calling custProc GET invoice to see
            //if the user has any invoice or not
            //if user has invoice in the response
            //we should break the loop and use that user for
            //testing
            endPt = custProcStepsGET.setEndpointCustomersUCIDinvoices(ucid);
            procResp = custProcStepsGET.getInvoice(endPt, token)
            		.extract().response();
            
            if (procResp.getStatusCode()==ResponseCode.OK) {
                flag = customerProcessHelper.verifyUserHasInvoice(sysResp); // here we have a problem?!
                break;
            }
    	}
    	
        LOGGER.info("ucid that has ACS altrernateID  --> " + ucid);
        
        kmap.put("ucid", ucid);
        tUtil.putToSession("ucid", ucid);
     }
    
    @Then("^I see 200 in response with the info about the user in response$")
    public void iGotUserWhichHasInvoice() {
    	tUtil.verifyStatus(sysResp, ResponseCode.OK);
    }
    
    @When("^I do a GET call to CustProc api to see user invoice for catalyst (.*)$")
    public void idoGETcallToCustProcApiToSeeUserInvoiceForCATALYST(String catalyst) {
    	endPt = custProcStepsGET.setEndpointCustomersUCIDinvoices(ucid);
    	procResp = custProcStepsGET.getInvoiceWithProductFamily(endPt, generateToken(ucid), catalyst)
    			.extract().response();
    }
    
    @When("^I do a GET call to CustProc api to see user invoice for nejm (.*)$")
    public void idoGETcallToCustProcApiToSeeUserInvoiceForNEJM(String nejm) {
    	endPt = custProcStepsGET.setEndpointCustomersUCIDinvoices(ucid);
    	procResp = custProcStepsGET.getInvoiceWithProductFamily(endPt, generateToken(ucid), nejm)
    			.extract().response();
    }
    
    @When("^I do a GET call to CustProc api to see user invoice with invalid (.*) productFamily in the url$")
    public void idoGETcallToCustProcApiToSeeUserInvoiceForInvalid(String invalid) {
    	endPt = custProcStepsGET.setEndpointCustomersUCIDinvoices(ucid);
    	procResp = custProcStepsGET.getInvoiceWithProductFamily(endPt, generateToken(ucid), invalid)
    			.extract().response();
    	tUtil.putToSession("response", procResp);
    }
    
    @When("I do a GET call to CustProc api to see user invoice with empty productFamily in the url")
    public void i_do_a_get_call_to_cust_proc_api_to_see_user_invoice_with_empty_product_family_in_the_url() {
    	endPt = custProcStepsGET.setEndpointCustomersUCIDinvoices(ucid);
    	procResp = custProcStepsGET.getInvoiceWithProductFamily(endPt, generateToken(ucid), "")
    			.extract().response();
    }
    
    
    
    // ===================================================================================================
    // Fields Retrieval and Verification
    // ===================================================================================================
    
    
    
    @Given("^user calls Process API to retrieve all fields$")
    public void userSendsAGETCallAtExpLayerToGetAllReferenceFields() throws URISyntaxException {
        procResp = custProcStepsGET.getFields(null)
                .extract().response();
    }

    @Then("^field matches to the business requirements$")
    public void userReceivesCode() throws JSONException, IOException {
        tUtil.verifyStatus(procResp, ResponseCode.OK);
        accExpSteps.verifyJson(procResp, "AIC_Fields");
    }

    @When("^user calls System API$")
    public void userSendsAGETCallToSystemAPI() {
        sysResp = aicGETSteps.getFields(null)
                .extract().response();
    }

    @Then("^Process API should match with system API$")
    public void fieldsFromProcessLayerShouldMatchWithSystemLayer() throws JSONException, IOException {
        custProcStepsGET.verifyJson(sysResp, procResp);
    }

    @Given("^user calls invalid field (.*) values$")
    public void userSendsAGETCallWithInvalidValuesCouAtExpLayer(final String field) throws URISyntaxException {
        sysResp = custProcStepsGET.getFields(field)
                .extract().response();
    }

    @Then("^appropriate code (.*) should be displayed$")
    public void validStatusCodeAndErrorMessageShouldBeDisplayed(final int arg1) {
        tUtil.verifyStatus(sysResp, arg1);
    }

    @Given("^user calls process API for the given reference field (.*)$")
    public void userSendsAGETCallAtExpLayerToGetAllValuesFromTheGivenReferenceField(
            final String field) throws URISyntaxException {
        procResp = custProcStepsGET.getFields(field)
                .extract().response();
    }

    @Then("^user receives status code as 200 success$")
    public void validStatusDisplayed() {
        assertEquals(procResp.getStatusCode(), ResponseCode.OK);
    }

    @Then("^User receives status code as 201 success$")
    public void valid201StatusDisplayed() {
        assertEquals(procResp.getStatusCode(), ResponseCode.CREATED);
    }

    @Then("^User receives status code as 400 status code$")
    public void valid400StatusDisplayed() {
        assertEquals(procResp.getStatusCode(), ResponseCode.BAD_REQUEST);
    }
    
    @Then("^User receives status code as 500 status code$")
    public void valid500StatusDisplayed() {
        assertEquals(procResp.getStatusCode(), ResponseCode.INTERNAL_ERROR);
    }

    @Then("User should get msg (.*) and status (.*)$")
    public void shouldGetMsgMsgAndStatusSts(
            final String msg, final int sts) {
        tUtil.verify_msgCode(procResp, msg, sts);
    }

    @When("^user calls System API for the given reference field (.*)$")
    public void userReferenceField(final String field) {
        sysResp = aicGETSteps.getFields(field)
                .extract().response();
    }

    // ==============================================================================================
    // User Information Retrieval and Verification
    // ==============================================================================================

    @When("^user sends a GET request to retrieve user info using email through Customer Process API$")
    public void userSendsAGetRequestToRetrieveUserInfoUsingEmailThroughCustomerProcessApi()
            throws Throwable {
        if (!kmap.containsKey("email")) {
            kmap.put("email", RegistrationProcSteps.kmap.get("email"));
        }

        endPt = custProcStepsGET.setEndpointUserByEmail(kmap.get("email"));
        procResp = custProcStepsGET.getUserInfo(endPt).extract().response();
    }

    @When("^user sends a GET request to retrieve user details using valid akamai auth token$")
    public void userSendsAGetRequestToRetrieveUserDetailsUsingValidAkamaiauthtoken() {
        endPt = custProcStepsGET.setEndpointUserByUcid(kmap.get("ucid"));
        procResp = custProcStepsGET.getUserByUcid(endPt, kmap.get("accessToken")).extract().response();
    }

    @When("^user sends a GET request to retrieve user details without akamai auth token$")
    public void userSendsAGetRequestToRetrieveUserDetailsWithoutAkamaiAuthToken() throws Throwable {
        endPt = custProcStepsGET.setEndpointUserByUcid(kmap.get("ucid"));
        procResp = custProcStepsGET.getUserInfo(endPt).extract().response();
    }

    @When("^user sends a GET request to retrieve user details using invalid ucid (.*)$")
    public void userSendsAGetRequestToRetrieveUserDetailsUsingInvalidUcid(final String ucid) {
        endPt = custProcStepsGET.setEndpointUserByUcid(ucid);
        procResp = custProcStepsGET.getUserByUcid(endPt, kmap.get("accessToken")).extract().response();
    }

    @Then("^user info should be displayed with uuid$")
    public void userInfoShouldBeDisplayedWithUuid() {
        tUtil.verifyStatus(procResp, ResponseCode.OK);
        String[] keys = {"uuid", "email", "firstName", "lastName", "audienceType"};
        tUtil.verifyKeysInObject(procResp, keys);

        kmap.put("ucid", procResp.jsonPath().getString("uuid"));
    }

    // =====================================================================================================
    // Access Token and Verification
    // =====================================================================================================

    @When("^user sends a POST request with ucid to get Akamai access token$")
    public void userSendsAPostRequestWithUcidToGetAkamaiAccessToken() {
    	
        sysResp = aicPOSTSteps.createToken(kmap.get("ucid"))
        		.extract().response();
    }

    public void userSendsAPostRequestWithUcidToGetAkamaiAccessToken(String token) {

        sysResp = aicPOSTSteps.createToken(token)
        		.extract().response();
    }

    @Then("^access token should be returned with status 200$")
    public void accessTokenShouldBeReturnedWithStatus200() {
    	
        tUtil.verifyStatus(sysResp, ResponseCode.OK);

        String[] keys = {"stat", "accessToken"};
        tUtil.verifyKeysInObject(sysResp, keys);

        kmap.put("accessToken", sysResp.jsonPath().getString("accessToken"));
        tUtil.putToSession("accessToken", sysResp.jsonPath().getString("accessToken"));
    }

    // ======================================================================================================
    // Ticket creation and verification
    // ======================================================================================================


    @When("^User send a POST request to open ticket with valid data$")
    public void userSendAPOSTRequestToOpenTicketWithValidData() throws Exception {
        emailValue = tUtil.AppendTimestamp("automation@example.com");
        jsonUtils.update_JSONValue(FILE_CREATE_TICKET, "$.email", emailValue);
        procResp = custProcStepsPOST.postTickets(FILE_CREATE_TICKET)
                .extract().response();

        ticketId = procResp.jsonPath().get("ticket.id");
    }

    @When("^User send a POST request to open ticket with invalid data (.*) for field (.*)$")
    public void userSendAPostRequestToOpenTicketWithInvalidDataForField(
            final String value, final String field) throws Throwable {
        jsonUtils.update_JSONValue(FILE_POST_INVALID_TICKET, field, value);
        procResp = custProcStepsPOST.postTickets(FILE_POST_INVALID_TICKET)
                .extract().response();
    }

    @When("^User send a POST request to open ticket with same data$")
    public void userSendAPOSTRequestToOpenTicketWithSameData() {
        procResp = custProcStepsPOST.postTickets(FILE_CREATE_TICKET)
                .extract().response();
    }

    @When("^id created is always unique$")
    public void idCreatedIsAlwaysUnique() {
        Assert.assertNotEquals(ticketId,
                procResp.jsonPath().get("ticket.id"));
    }

    @When("^User send a Get request to get the details of ticket with valid id$")
    public void userSendAGetRequestToGetTheDetailsOfTicketWithValidId() throws URISyntaxException {
        procResp = custProcStepsGET.getTickets(ticketId)
                .extract().response();
    }

    @When("^User send a POST request to open ticket with blank body$")
    public void userSendAPOSTRequestToOpenTicketWithBlankBody() {
        String file = "Blank_Body_File.json";
        procResp = custProcStepsPOST.postTickets(file)
                .extract().response();
    }

    @When("^User send a Get request to get the details of ticket with invalid (.*)$")
    public void userSendAGetRequestToGetTheDetailsOfTicketWithInvalidId(
            final Integer id) throws URISyntaxException {
        procResp = custProcStepsGET.getTickets(id)
                .extract().response();
    }

    @When("^I do a POST request to Process API to register user$")
    public void iDoAPOSTRequestToProcessAPIToRegisterUser() throws Exception {
        emailValue = tUtil.AppendTimestamp("automation@example.com");
        String firstName = tUtil.AppendTimestamp("firstName");
        String lastName = tUtil.AppendTimestamp("lastName");
        jsonUtils.update_JSONValue(FILE_PROC_API_CREATE_USER, "$.firstName", firstName);
        jsonUtils.update_JSONValue(FILE_PROC_API_CREATE_USER, "$.lastName", lastName);
        jsonUtils.update_JSONValue(FILE_PROC_API_CREATE_USER, "$.email", emailValue);

        procResp = custProcStepsPOST.userRegister(FILE_PROC_API_CREATE_USER)
                .extract().response();

        String ucid = procResp.jsonPath().getString("ucId");
        kmap.put("ucid", ucid);
    }

    @When("^I do a GET request to System API AIC with email to get user info$")
    public void iDoAGETRequestToSystemAPIAICWithEmail() {
        endPt = aicGETSteps.setEndPointEmail(emailValue);
        sysResp = aicGETSteps.getUserFromAkamai(endPt).extract().response();

        String ucid = sysResp.jsonPath().getString("uuid");
        kmap.put("ucid", ucid);
    }

    @Then("^user receives status code as 200 success AIC$")
    public void userReceivesStatusCodeAsSuccessAIC() {
        assertEquals(sysResp.getStatusCode(), ResponseCode.OK);

        String email = sysResp.jsonPath().getString("email");
        String firstName = sysResp.jsonPath().getString("firstName");
        String lastName = sysResp.jsonPath().getString("lastName");

        assertEquals(emailValue, email);
        assertEquals(jsonUtils.getFromJSON(FILE_PROC_API_CREATE_USER, "$.firstName"), firstName);
        assertEquals(jsonUtils.getFromJSON(FILE_PROC_API_CREATE_USER, "$.lastName"), lastName);
    }

    @When("^I do a GET request to System API Literatum with ucid to get user info$")
    public void iDoAGETRequestToSystemAPILiteratumWithUcid() {
        endPt = literatumGETSteps.setEndpointIdentity(kmap.get("ucid"));
        sysResp = literatumGETSteps.get(endPt).extract().response();
    }

    @Then("^user receives status code as 200 success Literatum$")
    public void userReceivesStatusCodeAsSuccessLiteratum() {
        assertEquals(sysResp.getStatusCode(), ResponseCode.OK);

        String ucid = sysResp.jsonPath().getString("ucid");
        String firstName = sysResp.jsonPath().getString("firstName");
        String lastName = sysResp.jsonPath().getString("lastName");

        assertEquals(kmap.get("ucid"), ucid);
        assertEquals(jsonUtils.getFromJSON(FILE_PROC_API_CREATE_USER, "$.firstName"), firstName);
        assertEquals(jsonUtils.getFromJSON(FILE_PROC_API_CREATE_USER, "$.lastName"), lastName);
    }

    @When("^I do a PATCH call to Proc API to update user$")
    public void iDoAPATCHCallToProcAPIToUpdateUser() throws Throwable {
        endPt = custProcStepsPATCH.setEndpointUserByUcid(kmap.get("ucid"));
        String firstName = tUtil.AppendTimestamp("firstName");
        String lastName = tUtil.AppendTimestamp("lastName");
        jsonUtils.update_JSONValue(FILE_PROC_API_USER, "$.email", emailValue);
        jsonUtils.update_JSONValue(FILE_PROC_API_USER, "$.firstName", firstName);
        jsonUtils.update_JSONValue(FILE_PROC_API_USER, "$.lastName", lastName);

        userSendsAPostRequestWithUcidToGetAkamaiAccessToken();
        String token = sysResp.jsonPath().getString("accessToken");

        procResp = custProcStepsPATCH.patchUserByUcid(endPt, token, FILE_PROC_API_USER)
                .extract().response();
    }

    @When("^User sends a GET request to get details of surveys for user with ucid (.*)$")
    public void userSendsAGETRequestToGetDetailsOfSurveysForUserWithInvalidUcidUcid(
            final String ucid) throws URISyntaxException {
        endPt = custProcStepsGET.setEndpointUserSurveysByUcid(ucid);
        procResp = custProcStepsGET.getUserInfo(endPt).extract().response();
    }

    @Then("^user receives status code as 200 success and list size (.*)$")
    public void validStatusdisplayedAndEmptyList(final int size) {
        assertEquals(procResp.getStatusCode(), ResponseCode.OK);
        List resp = procResp.jsonPath().get();
        assertEquals(resp.size(), size);
    }

    @When("^I do request to Process API to register user$")
    public void iDoRequestToProcessAPIToRegisterUser() throws Exception {
        String fileName = "ProcCustomer_POST_content_premium.json";
        createdNewUserWithUniqueUuid();

        emailValue = tUtil.AppendTimestamp("automation@example.com");
        String firstName = tUtil.AppendTimestamp("firstName");
        String lastName = tUtil.AppendTimestamp("lastName");
        jsonUtils.update_JSONValue(fileName, "$.firstName", firstName);
        jsonUtils.update_JSONValue(fileName, "$.lastName", lastName);
        jsonUtils.update_JSONValue(fileName, "$.email", emailValue);
        jsonUtils.update_JSONValue(fileName, "$.ucid", kmap.get("ucid"));

        procResp = custProcStepsPOST.userRegister(fileName)
                .extract().response();

        String ucid = procResp.jsonPath().getString("ucId");
        kmap.put("ucid", ucid);
    }

    public void createdNewUserWithUniqueUuid() throws Exception {
        endPt = aicPOSTSteps.setEndPoint();
        String email = tUtil.AppendTimestamp(autoEmail);
        jsonUtils.update_JSONValue("AIC_Customer.json", "$.email", email);
        kmap.put("email", email);

        sysResp = aicPOSTSteps.createCustomerInAkamai("AIC_Customer.json", endPt)
                .extract().response();

        kmap.put("ucid", sysResp.jsonPath().getString("uuid"));
    }

    @Then("^I see status as 201 created with ucid and token in response$")
    public void iSeeStatusAsCreatedWithUcidAndTokenInResponse() {
        assertEquals(procResp.getStatusCode(), ResponseCode.OK);
        Assert.assertNotNull(procResp.jsonPath().getString("ucId"));
        Assert.assertNotNull(procResp.jsonPath().getString("authToken"));
    }

    @When("User send a POST request to create a customer in Literatum")
    public void userSendAPOSTRequestToCreateACustomerInLiteratum() throws Exception {
        String fileName = "CustomerProcess/CustProc_POST_create_customer.json";
        emailValue = tUtil.AppendTimestamp(autoEmail);
        String firstName = tUtil.AppendTimestamp("FirstName");
        String lastName = tUtil.AppendTimestamp("Lastname");
        jsonUtils.update_JSONValue(fileName, "$.customer.email", emailValue);
        jsonUtils.update_JSONValue(fileName, "$.customer.firstName", firstName);
        jsonUtils.update_JSONValue(fileName, "$.customer.lastName", lastName);

        procResp = custProcStepsPOST.customers(fileName).extract().response();
        String ucid = procResp.jsonPath().get("ucid");
        kmap.put("ucid", ucid);

        tUtil.putToSession("ucid", ucid);
        tUtil.putToSession("email", emailValue);
        tUtil.putToSession("firstName", firstName);
        tUtil.putToSession("lastName", lastName);
    }

    @Then("^User send a request to check a new customer in Akamai$")
    public void iCheckUserAlternateIDAndAudienceType() {
        emailValue = tUtil.checkSession("email", emailValue);

        endPt = aicGETSteps.setEndPointEmail(emailValue);
        sysResp = aicGETSteps.getUserFromAkamai(endPt).extract().response();

        String audienceType = sysResp.jsonPath().get("audienceType");
        String sysUuid = sysResp.jsonPath().get("uuid");
//        String procUcid = procResp.jsonPath().get("ucid");
        String email = sysResp.jsonPath().getString("email");

//        Assert.assertEquals(sysUuid, procUcid);
        Assert.assertEquals(audienceType, "REGISTERED USER");
        Assert.assertEquals(emailValue, email);

        ucid = sysUuid;
        tUtil.putToSession("ucid", sysUuid);
        kmap.put("ucid", sysUuid);
        tUtil.putToSession("response", sysResp);
    }

    public String generateToken() {
        sysResp = aicGETSteps.get(aicGETSteps.setEndpointAccessToken(kmap.get("ucid")))
                .extract().response();
        return sysResp.jsonPath().getString("accessToken");
    }
    
    public String generateToken(String ucid) {
        sysResp = aicGETSteps.get(aicGETSteps.setEndpointAccessToken(ucid))
                .extract().response();
        return sysResp.jsonPath().getString("accessToken");
    }

    @Deprecated
    @Then("User send a request to payments")
    public void userSendARequestToPayments() {
        String fileName = "CustomerProcess/CustProc_POST_payments_CC.json";
        String authToken = generateToken();
        procResp = custProcStepsPOST.customerPayments(
                fileName, kmap.get("ucid"), authToken).extract().response();
        procResp.getStatusCode();
    }
    
    @When("I do POST call to CustProc payments")
    public void i_do_post_call_to_cust_proc_payments() throws Exception {
    	
    	jsonUtils.update_JSONValue(customerUCIDpayments, "customerNumber", kmap.get("customerNumber"));
    	jsonUtils.update_JSONValue(customerUCIDpayments, "orderId", kmap.get("orderId"));

        procResp = custProcStepsPOST.customerPayments(customerUCIDpayments, kmap.get("ucid"), kmap.get("accessToken"))
        		.extract().response();
    }

    @Then("I see payment 201 status")
    public void i_see_payment_status() {
        tUtil.verifyStatus(procResp,ResponseCode.CREATED);
    }
    
    @When("I do POST call to CustProc payments for RegisteredUser")
    public void i_do_post_call_to_cust_proc_payments_RegisteredUser() throws Exception {

    	jsonUtils.update_JSONValue(customerUCIDpayments, "customerNumber", ""); // we're updating fields with empty string
    	jsonUtils.update_JSONValue(customerUCIDpayments, "orderId", "");		// because we don't have those fields for RegisteredUser
    	
        procResp = custProcStepsPOST.customerPayments(customerUCIDpayments, accountExpSteps.kmap.get("ucid"),
        		generateToken(accountExpSteps.kmap.get("ucid")))
        		.extract().response();
    }

    @And("I check nejm fields institutionalAdmin and mediaAccess")
    public void iCheckNejmFieldsInstitutionalAdminAndMediaAccess() {
        String institutionalAdmin = procResp.jsonPath().getString("Nejm.institutionalAdmin");
        String mediaAccess = procResp.jsonPath().getString("Nejm.mediaAccess");
        assertTrue(tUtil.isBooleanOrNull(institutionalAdmin));
        assertTrue(
            Arrays.asList("none","regular","advanced").contains(mediaAccess.toLowerCase())
        );
    }

    @And("I check nejm fields nejmEmbargoTOC and nejmEmbargoRecentlyPublished")
    public void iCheckNejmFieldsNejmEmbargoTOCAndNejmEmbargoRecentlyPublished() {
        Map nejmEmbargoTOC = procResp.jsonPath()
            .getMap("emailPreferences.nejmEmbargoTOC");
        Map nejmEmbargoRecentlyPublished = procResp.jsonPath()
            .getMap("emailPreferences.nejmEmbargoRecentlyPublished");

        assertNotNull(nejmEmbargoRecentlyPublished);
        assertNotNull(nejmEmbargoTOC);
    }

    @When("^I do a PATCH call customer to Proc API to update user$")
    public void iDoAPATCHCallCustomerToProcAPIToUpdateUser() throws Throwable {
        String patchCustomer = "CustomerProcess/CustProc_PATCH_customer.json";
        emailValue = emailValue != null ? emailValue : (String) tUtil.getFromSession("email");
        ucid = tUtil.checkSession("ucid", kmap.get("ucid"));
        endPt = custProcStepsPATCH.setEndpointCustomers(ucid);
        String firstName = tUtil.AppendTimestamp("firstName");
        String lastName = tUtil.AppendTimestamp("lastName");
        jsonUtils.update_JSONValue(patchCustomer, "$.customer.uuid", ucid);
        jsonUtils.update_JSONValue(patchCustomer, "$.customer.email", emailValue);
        jsonUtils.update_JSONValue(patchCustomer, "$.customer.firstName", firstName);
        jsonUtils.update_JSONValue(patchCustomer, "$.customer.lastName", lastName);

        userSendsAPostRequestWithUcidToGetAkamaiAccessToken(ucid);
        String token = sysResp.jsonPath().getString("accessToken");

        procResp = custProcStepsPATCH.patchUserByUcid(endPt, token, patchCustomer)
            .extract().response();
    }
    
    @When("I fetch the top ten subscription ids for {string} for customer proc api from DB")
	public void fetch_subscrptnIds_frmDB(String litrtmTyp) throws IOException, SQLException {
    	String sqlQuery = null;
		if (litrtmTyp.equals("Nejm")) {
			sqlQuery = dbUtils.buildQuery("NEJMCustomers_with_subscription_Ids.sql");
		} else if (litrtmTyp.equals("Catalyst")) {
			sqlQuery = dbUtils.buildQuery("CatalystCustomers_with_subscription_Ids.sql");
		}
		String dbURL = dbUtils.buildDbUrl("ACSDbUrl");
		Connection conn = ConnectionFactory.getConnectionACS(dbURL, dbUtils.acsDBUser(), dbUtils.acsDBPass());
		PreparedStatement ps = conn.prepareStatement(sqlQuery);

		ResultSet resultSet = ps.executeQuery();

		while (resultSet.next()) {
			subsIdList.add(resultSet.getString("bil_ctm"));
		}
	}
    
    public void user_creates_inid_catalyst() throws Exception {
		String file = CatalystLiteratumSystemJsonBodyFile.Catalyst_Create_New_inid;
		jsonUtils.update_JSONValue(file, "inid", subscrptnID);
		endPt = catJLSystmPOST.setEndpontIdentitiesInstitution();
		procResp = catJLSystmPOST.post(file, endPt).extract().response();
	}
    
    public void user_creates_inid_nejm() throws Exception {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Create_New_inid;
		jsonUtils.update_JSONValue(file, "inid", subscrptnID);
		endPt = nejmLiteratumSystemPOST.setEndpontIdentitiesInstitution();
		procResp = nejmLiteratumSystemPOST.post(file, endPt).extract().response();
	}
    
    @When("user sends a POST call to provide institutional activation for {int} subscribed institution to an admin for nejm customer proc api")
	public void POST_request_to_provide_instnal_activation_nejmCustProc(Integer inidCount) throws Exception {
		do {
			for (int i = 0; i < subsIdList.size(); i++) {
				subscrptnID = subsIdList.get(i);
				GET_call_to_fetch_linked_related_identities_nejm();
				if ((procResp.getStatusCode() != (ResponseCode.OK))) {
					user_creates_inid_nejm();
				}
				String ucid = tUtil.getFromSession("ucid").toString();
				endPt = custProcStepsPOST.setEndpointInstitutionalActivate(ucid);
				procResp = custProcStepsPOST.institutionalActivate(endPt, tUtil.getToken(ucid), subscrptnID)
						.extract().response();

				if (procResp.getStatusCode() == ResponseCode.CREATED) {
					inidCount--;
					if (inidCount == 0) {
						break;
					}
				}
			}
		} while (inidCount != 0);
		tUtil.putToSession("subId", subscrptnID);
	}
    
    @When("user sends a POST call to provide institutional activation for {int} subscribed institution to an admin for catalyst customer proc api")
	public void POST_request_to_provide_instnal_activation_catCustProc(Integer inidCount) throws Exception {
		do {
			for (int i = 0; i < subsIdList.size(); i++) {
				subscrptnID = subsIdList.get(i);
				GET_call_to_fetch_linked_related_identities_catalyst();
				if ((procResp.getStatusCode() != (ResponseCode.OK))) {
					user_creates_inid_nejm();
				}
				String ucid = tUtil.getFromSession("ucid").toString();
				endPt = custProcStepsPOST.setEndpointInstitutionalActivate(ucid);
				procResp = custProcStepsPOST.institutionalActivate(endPt, tUtil.getToken(ucid), subscrptnID)
						.extract().response();

				if (procResp.getStatusCode() == ResponseCode.CREATED) {
					inidCount--;
					if (inidCount == 0) {
						break;
					}
				}
			}
		} while (inidCount != 0);
		tUtil.putToSession("subId", subscrptnID);
	}
    
    @When("user sends a POST call to provide institutional activation for same subscribed institution to another user for nejm customer proc api")
	public void POST_request_to_provide_instnal_activation_forSame_inst_toDiffrnt_user_inNejm()
			throws IOException, SQLException {
		String ucid = tUtil.getFromSession("ucid").toString();
		endPt = custProcStepsPOST.setEndpointInstitutionalActivate(ucid);
		procResp = custProcStepsPOST
				.institutionalActivate(endPt, tUtil.getToken(ucid), tUtil.getFromSession("subId").toString())
				.extract().response();
	}
    
    @When("user sends a POST call to provide institutional activation for same subscribed institution to another user for catalyst customer proc api")
	public void POST_request_to_provide_instnal_activation_forSame_inst_toDiffrnt_user_inCatJL()
			throws IOException, SQLException {
		String ucid = tUtil.getFromSession("ucid").toString();
		endPt = custProcStepsPOST.setEndpointInstitutionalActivate(ucid);
		procResp = custProcStepsPOST
				.institutionalActivate(endPt, tUtil.getToken(ucid), tUtil.getFromSession("subId").toString())
				.extract().response();
	}
    
    @Then("Admin should get activated on a subscribed institution for nejm customer proc api with status code 201")
    public void verify_nejm_admin_should_get_activated() {
    	softAssert.assertThat(procResp.statusCode()).isEqualTo(ResponseCode.CREATED);
    	softAssert.assertThat(procResp.jsonPath().getString("activatedProducts")).isNotNull();
    	softAssert.assertThat(procResp.jsonPath().getString("errors")).isEqualTo("[]");
		softAssert.assertAll();
    }
    
    @Then("Admin should get activated on a subscribed institution for catalyst customer proc api with status code 201")
    public void verify_catlst_admin_should_get_activated() {
    	softAssert.assertThat(procResp.statusCode()).isEqualTo(ResponseCode.CREATED);
    	softAssert.assertThat(procResp.jsonPath().getString("activatedProducts")).isNotNull();
    	softAssert.assertThat(procResp.jsonPath().getString("errors")).isEqualTo("[]");
		softAssert.assertAll();
    }
    
    @When("user sends a GET call to fetch the activated person identity linked to {int} subscribed institution for nejm customer proc api")
	public void GET_to_fetch_person_identity_nejm(Integer inidCount) throws InterruptedException {
		int timeOutMin = 12;
		endPt = nejmLitrtmSystemGET.setEndpointIdentity(tUtil.getFromSession("ucid").toString());
		do {
			System.out.println("Waiting for request Nejm Literatum System to get finished");
			TimeUnit.SECONDS.sleep(5);
			procResp = nejmLitrtmSystemGET.get(endPt).extract().response();
			tUtil.verifyStatus(procResp, ResponseCode.OK);
			List<Map<String, String>> respMaps = procResp.jsonPath().getList("tag");
			for (Map<String, String> map : respMaps) {
				procRespTagLabel = (map.containsValue("Yes"));
				procRespTagCode = (map.containsValue("y"));
				procRespTagSetCode = map.containsValue("institutionadminindicator");
				if ((procRespTagLabel && procRespTagCode) && procRespTagSetCode) {
					break;
				}
			}
			if ((procResp.jsonPath().getString("related-identity") != null)) {
				if (procRespTagLabel && procResp.jsonPath().getList("related-identity").size() == inidCount) {
					break;
				}
			}
			timeOutMin--;

		} while (timeOutMin != 0);
	}
    
    @When("user sends a GET call to fetch the activated person identity linked to {int} subscribed institution for catalyst customer proc api")
	public void GET_to_fetch_person_identity_catlst(Integer inidCount) throws InterruptedException {
		int timeOutMin = 12;
		endPt = literatumGETSteps.setEndpointPersonIdentity(tUtil.getFromSession("ucid").toString());
		do {
			System.out.println("Waiting for request Catalyst Literatum System to get finished");
			TimeUnit.SECONDS.sleep(5);
			procResp = literatumGETSteps.get(endPt).extract().response();
			tUtil.verifyStatus(procResp, ResponseCode.OK);
			if ((procResp.jsonPath().getString("related-identity") != null)) {
				if (procResp.jsonPath().getList("related-identity").size() == inidCount) {
					break;
				}
			}
			timeOutMin--;

		} while (timeOutMin != 0);
	}
    
    @Then("I verify tags for institutional admin indicator and related-identity for activated admins for nejm customer proc api with status code 200")
    public void verify_nejm_institnal_adminIndicator_and_relatedIdentity_tags() {
		softAssert.assertThat(procRespTagLabel).isTrue();
		softAssert.assertThat(procRespTagCode).isTrue();
		softAssert.assertThat(procRespTagSetCode).isTrue();
		softAssert.assertThat(procResp.jsonPath().get("related-identity.related-identity-type").toString()).isEqualTo(
				"[admined-org]");
		softAssert.assertThat(procResp.jsonPath().get("related-identity.id-value").toString()).isNotNull();

		softAssert.assertAll();
	}
    
    @Then("I verify tags for institutional admin indicator and related-identity for activated admins for catalyst customer proc api with status code 200")
    public void verify_catlst_institnal_adminIndicator_and_relatedIdentity_tags() {
		softAssert.assertThat(procResp.jsonPath().get("related-identity.related-identity-type").toString()).isEqualTo(
				"[admined-org]");
		softAssert.assertThat(procResp.jsonPath().get("related-identity.id-value").toString()).isNotNull();
		softAssert.assertAll();
	}
    
    @Then("I verify respective related-identity should get linked to a multiple inid in nejm literatum customer proc api with status code 200")
	public void verify_nejm_multiple_inid_gotLinked_toSnglUcid() {
		softAssert.assertThat(procRespTagLabel).isTrue();
		softAssert.assertThat(procRespTagCode).isTrue();
		softAssert.assertThat(procRespTagSetCode).isTrue();
		softAssert.assertThat(procResp.jsonPath().getList("related-identity").size()).isEqualTo(2);

		softAssert.assertAll();
	}
    
    @Then("I verify respective related-identity should get linked to a multiple inid in catalyst literatum customer proc api with status code 200")
    public void verify_cat_multiple_inid_gotLinked_toSnglUcid() {
    	softAssert.assertThat(procResp.getStatusCode()).isEqualTo(ResponseCode.OK);
		softAssert.assertThat(procResp.jsonPath().getList("related-identity").size()).isEqualTo(2);
		softAssert.assertAll();
	}
    
    @When("user sends a GET call to fetch the subscribed institution details linked to multiple person identities for nejm customer proc api")
    public void GET_call_to_fetch_linked_related_identities_nejm() throws InterruptedException {
		endPt = nejmLitrtmSystemGET.setEndpointInstitutionIdentities(subscrptnID);
		procResp = nejmLitrtmSystemGET.get(endPt).extract().response();
	}
    
    @When("user sends a GET call to fetch the subscribed institution details linked to multiple person identities for catalyst customer proc api")
    public void GET_call_to_fetch_linked_related_identities_catalyst() throws InterruptedException {
		endPt = literatumGETSteps.setEndpointInstitutionIdentities(subscrptnID);
		procResp = literatumGETSteps.get(endPt).extract().response();
	}
    
    @Then("I verify respective related-identity should get linked to a single inid in nejm literatum customer proc api with status code 200")
    public void verify_related_identity_linked_to_multiple_ucid_nejm() {
		Assert.assertTrue("related-identity size is not as expected",
				procResp.jsonPath().getList("related-identity").size() > 1);
	}
    
    @Then("I verify respective related-identity should get linked to a single inid in catalyst literatum customer proc api with status code 200")
    public void verify_related_identity_linked_to_multiple_ucid_cat() {
		Assert.assertTrue("related-identity size is not as expected",
				procResp.jsonPath().getList("related-identity").size() > 1);
	}
    
    @And("I do a GET call to fetch the order receipt for customer proc api")
    public void GET_fetch_orderRecpt_details() {
    	String ucid = tUtil.getFromSession("ucid").toString();
    	endPt = custProcStepsGET.setEndpointCustomersOrderRecpt(ucid, tUtil.getFromSession("orderNum").toString());
		procResp = custProcStepsGET.getOrderReciept(endPt, tUtil.getToken(ucid)).extract().response();
    }
    
    @Then("I verify that customer number should be returned as a part of order receipt payload for customer proc api")
    public void verify_recptDetails_should_contain_custNum() {
    	softAssert.assertThat(procResp.getStatusCode()).isEqualTo(ResponseCode.OK);
    	softAssert.assertThat(procResp.jsonPath().getString("customerNumber")).isEqualTo(tUtil.getFromSession("customerNum"));
    	softAssert.assertAll();
    }
    
    @When("I do a PATCH call to customer proc api to update the address for catalyst customers with following")
    public void PATCH_to_update_cstmrAdd(DataTable dataTable) throws Exception {
		String file = ExpJsonBodyFile.Catalyst_Update_Address;
		int timeOutMin = 12;
		Map<String, String> map = CucumberUtils.convert(dataTable);
		String ucid = tUtil.getFromSession("ucid").toString();
		AccountHelper.PATCH_updateAddressFile(file, map.get("address1"), map.get("address2"));
		endPt = custProcStepsPATCH.setEndpointAddress(ucid);
		do {
			TimeUnit.SECONDS.sleep(5);
			procResp = custProcStepsPATCH.patchCustomer(file, tUtil.getToken(ucid), endPt).extract().response();
			if (procResp.getStatusCode() == (ResponseCode.OK)) {
				break;
			}
			timeOutMin--;
		} while (timeOutMin != 0);
	}
    
    @Then("I should verify that address should be updated with status code 200 for customer proc api")
    public void verify_Address_PATCH_call() {
    	Assert.assertEquals("Address Patch call did not return 200 OK", ResponseCode.OK, procResp.getStatusCode());
    }
    
}
