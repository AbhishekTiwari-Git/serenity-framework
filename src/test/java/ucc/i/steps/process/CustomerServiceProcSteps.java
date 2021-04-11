package ucc.i.steps.process;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.i.method.customerserviceproc.CustomerServiceProcPOST;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

public class CustomerServiceProcSteps {

	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    private static String autoEmail =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("autoEmail");
    TestUtils tUtil = new TestUtils();
    JsonUtils jUtil = new JsonUtils();
    
    private static Response procResp = null;
    private static String endpoint = null;
    
    
    
    @Steps
    CustomerServiceProcPOST custserProcPost;
    
    
    
    private final String createTicket = "CustomerserviceProc/createTicket.json";
    private final String createTicketWoutSubCom = "CustomerserviceProc/createTicketWoutSubCom.json";
    
    
    
    @When("^I send a POST to CustomerServiceProc to create a ticket for product (.*)$")
    public void i_send_a_post_to_customer_service_proc_to_create_a_ticket_for_product(String product) 
    		throws Exception {
        
    	jUtil.update_JSONValue(createTicket, "$.product", product);
    	
    	endpoint = custserProcPost.setEndpointTickets();
    	procResp = custserProcPost.post(createTicket, endpoint)
    			.extract().response();
    	
    	tUtil.putToSession("response", procResp);
    }
    
    @When("I send a POST to CustomerServiceProc to create a ticket with invalid endpoint")
    public void i_send_a_post_to_customer_service_proc_to_create_a_ticket_with_invalid_endpoint() {

    	endpoint = custserProcPost.setEndpointTickets() + "invalid";
    	procResp = custserProcPost.post(createTicket, endpoint)
    			.extract().response();
    	
    	tUtil.putToSession("response", procResp);
    }
    
    
    @When("I send a POST to CustomerServiceProc to create a ticket without subject and comment")
    public void i_send_a_post_to_customer_service_proc_to_create_a_ticket_without_subject_and_comment() {

    	endpoint = custserProcPost.setEndpointTickets();
    	procResp = custserProcPost.post(createTicketWoutSubCom, endpoint)
    			.extract().response();
    	
    	tUtil.putToSession("response", procResp);
    }
}
