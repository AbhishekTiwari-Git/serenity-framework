package ucc.i.steps.experience;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import ucc.i.method.customerserviceexp.CustomerServiceExpPOST;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class CustomerServiceExpSteps {

	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    private static String autoEmail =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("autoEmail");
    TestUtils tUtil = new TestUtils();
    JsonUtils jUtil = new JsonUtils();
    
    
    private static Response expResp = null;
    private static String endpoint = null;
    
    
    
    @Steps
    CustomerServiceExpPOST custserExpPost;
    
    
    
    private final String createTicket = "CustomerserviceExp/createTicket.json";
    private final String createTicketWoutSubCom = "CustomerserviceExp/createTicketWithoutSubCom.json";
    
    
    
    @When("^I send a POST to CustomerServiceExp to create a ticket for product (.*)$")
    public void i_send_a_post_to_customer_service_exp_to_create_a_ticket_for_product_product(String product) 
    		throws Exception {
    
    	jUtil.update_JSONValue(createTicket, "$.product", product);
    	
    	endpoint = custserExpPost.setEndpointTickets();
    	expResp = custserExpPost.post(createTicket, endpoint)
    			.extract().response();
    	
    	tUtil.putToSession("response", expResp);
    }
    
    @When("I send a POST to CustomerServiceExp to create a ticket without subject and comment")
    public void i_send_a_post_to_customer_service_exp_to_create_a_ticket_without_subject_and_comment() {

    	endpoint = custserExpPost.setEndpointTickets();
    	expResp = custserExpPost.post(createTicketWoutSubCom, endpoint)
    			.extract().response();
    	
    	tUtil.putToSession("response", expResp);
    }
    
    @When("I send a POST to CustomerServiceExp to create a ticket with invalid endpoint")
    public void i_send_a_post_to_customer_service_exp_to_create_a_ticket_with_invalid_endpoint() {

    	endpoint = custserExpPost.setEndpointTickets() + "invalid";
    	expResp = custserExpPost.post(createTicketWoutSubCom, endpoint)
    			.extract().response();
    	
    	tUtil.putToSession("response", expResp);
    }
}
