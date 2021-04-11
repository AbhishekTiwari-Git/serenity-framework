package ucc.i.steps.system;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;

import org.junit.Assert;

import ucc.i.method.zendesk.ZendeskGET;
import ucc.i.method.zendesk.ZendeskPOST;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

public class ZendeskSteps {

    public static Response resp=null;
    TestUtils tUtil = new TestUtils();
    JsonUtils jUtil = new JsonUtils();

    
    
    @Steps
    ZendeskGET ZendeskGETSteps;
    @Steps
    ZendeskPOST ZendeskPOSTSteps;
    
    
    
    private static final String FILE_CREATE_TICKET = "Zendesk_Post_Create_Ticket.json";
    private static final String FILE_EMPTY_TICKET = "Zendesk_Post_Empty_Ticket.json";
    private static final String FILE_AllEMPTYFields_TICKET = "Zendesk_Post_AllEmptyFields_Ticket.json";
    private static final String createTicket = "ZendeskSystem/createTicket.json";
    
    


    @When("^I send a POST to ZendeskSys to create a ticket for product (.*)$")
    public void i_send_a_post_to_zendesk_sys_to_create_a_ticket(String product) 
    		throws Exception {
    	
    	jUtil.update_JSONValue(createTicket, "$.product", product);
    	
    	resp = ZendeskPOSTSteps.createTicket(createTicket)
                .extract().response();
    	
    	tUtil.putToSession("response", resp);
    }
    
    @Then("^I see the product (.*) in the response$")
    public void i_see_the_product_in_the_response(String product) {
        
    	Assert.assertEquals(product, resp.jsonPath().getString("ticket.tags[0]"));
    }
    
    
    @When("I send a GET request to ZendeskSys with tickerID")
    public void i_send_a_get_request_to_zendesk_sys_with_ticker_id() {
    	String id = ZendeskGETSteps.getTicketID((Response)tUtil.getFromSession("response"));
        resp = ZendeskGETSteps.getTicket(id)
                .extract().response();
        
        tUtil.putToSession("response", resp);
    }
    
    @Title("Get Request with ticket ID : {0}")
    @When("^I send a GET request to Zendesk with tickerID$")
    public void i_send_a_GET_request_ticketID() {
        String id = ZendeskGETSteps.getTicketID(resp);
        resp = ZendeskGETSteps.getTicket(id)
                .extract().response();

        tUtil.putToSession("response", resp);
    }

    @When("^I send a GET request to Zendesk with invalid (.*)$")
    public void i_send_a_GET_request_invalid_ticketID(String id) {
        resp = ZendeskGETSteps.getTicket(id)
                .extract().response();

        tUtil.putToSession("response", resp);
    }

    @Title("Post Request Create Ticket")
    @When("^I send a POST request to create a ticket$")
    public void i_send_a_post_request_to_create_a_ticket() {
        resp = ZendeskPOSTSteps.createTicket(FILE_CREATE_TICKET)
                .extract().response();
    }

    @When("^I send a GET request to Zendesk with ID from the response")
    public void i_send_a_GET_request_with_id_from_response() {
        JsonPath jsonPathEvaluator = resp.jsonPath();
        String ticketID = jsonPathEvaluator.getString("ticket.id");
        resp = ZendeskGETSteps.getTicket(ticketID)
                .extract().response();

    }

    @Title("Get Request with Ticket blank number")
    @When("^I send GET request with invalid data (.*) to Zendesk$")
    public void i_send_a_GET_request_to_Zendesk_with_invalid_endpoint(String no) {
        resp = ZendeskGETSteps.getTicket(no + "/")
                .extract().response();
        
        tUtil.putToSession("response", resp);
    }

    @Title("Verify Status Code")
    @Then("^response should be with (\\d+) status code$")
    public void response_should_be_with_status_code(int arg1) {
        tUtil.verifyStatus(resp, arg1);
    }

    @Then("^response should match with msg (.*) and status (.*)$")
    public void verify_invalidDta(String msg, int sts) {
        tUtil.verify_msgCode(resp, msg, sts);
    }

    @Then("^response should match with (.*) status and (.*) type$")
    public void response_should_math_with_status_and_type(String sts, String type) {
        JsonPath jsonPathEvaluator = resp.jsonPath();
        String stasus = jsonPathEvaluator.getString("ticket.status");
        String ticketType = jsonPathEvaluator.getString("ticket.type");

        Assert.assertEquals(stasus, sts);
        Assert.assertEquals(ticketType, type);
    }

    @When("^I send a POST request to create a ticket without data$")
    public void i_send_a_POST_request_to_create_a_ticket_without_data() {
        resp = ZendeskPOSTSteps.createTicket(FILE_EMPTY_TICKET)
                .extract().response();
    }

    @Given("^Created new ticket$")
    public void created_new_ticket() {
        resp = ZendeskPOSTSteps.createTicket(FILE_CREATE_TICKET)
                .extract().response();
    }

    @Then("^response should match with status (.*)$")
    public void responseShouldMatchWithStatusSts(int sts) {
        tUtil.verifyStatus(resp, sts);
    }

    @Then("^response should match valid status code (.*)$")
    public void responseShouldMatchValidStatusCodeScode(int scode) {
        tUtil.verifyStatus(resp, scode);
    }

    @Then("^the ticket should be created$")
    public void theTicketShouldBeCreated() {
        tUtil.verifyStatus(resp, 201);
    }
    
    @When("^I send a POST request to create a ticket all empty fields$")
    public void i_send_a_POST_request_to_create_a_ticket_allEmptyFields() {
        resp = ZendeskPOSTSteps.createTicket(FILE_AllEMPTYFields_TICKET)
                .extract().response();
    }
}