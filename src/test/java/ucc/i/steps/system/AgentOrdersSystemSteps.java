package ucc.i.steps.system;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.i.method.agentorderssystem.AgentOrdersSystemPOST;
import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;

public class AgentOrdersSystemSteps {

	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    private static Response sysResp = null;
    private TestUtils tUtil = new TestUtils();
    private JsonUtils jsonUtils = new JsonUtils();
    public static Map<String, String> kmap = new HashMap<String, String>();
    private static String endPoint = null;
	
    
    
    @Steps
    AgentOrdersSystemPOST agOrderSysPost;
    
    
    
    private final String customerActivate = "AgentOrdersSystem/customerActivate.json";
    private final String customerGotActivated = "AgentOrdersSystem/customerGotActivated.json";
    private final String customerActivateWrPostCode = "AgentOrdersSystem/customerActivateWrPostCode.json";
    private final String errorPostalCode = "AgentOrdersSystem/errorPostalCode.json";
    private final String customerActivateWrCustId = "AgentOrdersSystem/customerActivateWrCustId.json";
    private final String errorCustId = "AgentOrdersSystem/errorCustId.json";
    private final String customerActivateInvSubscId = "AgentOrdersSystem/customerActivateInvSubscId.json";
    private final String errorSubscId = "AgentOrdersSystem/errorSubscId.json";

    
    
    @When("I do POST activate via AgentOrderSys")
	public void i_do_post_activate_via_agent_order_sys() {

    	endPoint = agOrderSysPost.setEndpointToCustomersActivate((String) tUtil.getFromSession("ucid"));
    	sysResp = agOrderSysPost.post(customerActivate, endPoint)
    			.extract().response();
    	
    	tUtil.putToSession("response", sysResp);
    }
    
    @Then("^I see the user is activated with success response$")
    public void i_see_the_user_is_activated_with_success_response() 
    		throws IOException {
        
    	tUtil.verifyStatus(sysResp, ResponseCode.OK);
    	tUtil.verify_json_to_response(sysResp, customerGotActivated);
    }
    
    @When("I do POST activate via AgentOrderSys with wrong postalCode")
    public void i_do_post_activate_via_agent_order_sys_with_wrong_postal_code() {

    	endPoint = agOrderSysPost.setEndpointToCustomersActivate((String) tUtil.getFromSession("ucid"));
    	sysResp = agOrderSysPost.post(customerActivateWrPostCode, endPoint)
    			.extract().response();
    	
    	tUtil.putToSession("response", sysResp);
    }
    
    @Then("I see the postalCode is not matching error")
    public void i_see_the_postal_code_is_not_matching_error() 
    		throws IOException {

    	tUtil.verifyStatus(sysResp, ResponseCode.OK);
    	tUtil.verify_json_to_response(sysResp, errorPostalCode);
    }
    
    @When("I do POST activate via AgentOrderSys with the wrong customerId")
    public void i_do_post_activate_via_agent_order_sys_with_the_wrong_customer_id() {

    	endPoint = agOrderSysPost.setEndpointToCustomersActivate((String) tUtil.getFromSession("ucid"));
    	sysResp = agOrderSysPost.post(customerActivateWrCustId, endPoint)
    			.extract().response();
    	
    	tUtil.putToSession("response", sysResp);
    }

    @Then("I see error for the wrong customerId")
    public void i_see_error_for_the_subscription_not_found() 
    		throws IOException {

    	tUtil.verifyStatus(sysResp, ResponseCode.OK);
    	tUtil.verify_json_to_response(sysResp, errorCustId);
    }
    
    @When("I do POST activate via AgentOrderSys with invalid subscriptionId")
    public void i_do_post_activate_via_agent_order_sys_with_invalid_subscription_id() {

    	endPoint = agOrderSysPost.setEndpointToCustomersActivate((String) tUtil.getFromSession("ucid"));
    	sysResp = agOrderSysPost.post(customerActivateInvSubscId, endPoint)
    			.extract().response();
    	
    	tUtil.putToSession("response", sysResp);
    }

    @Then("I see error for the invalid subscriptionId")
    public void i_see_error_for_the_invalid_subscription_id() 
    		throws IOException {

    	tUtil.verifyStatus(sysResp, ResponseCode.OK);
    	tUtil.verify_json_to_response(sysResp, errorSubscId);
    }
}
