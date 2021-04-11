package ucc.i.steps.process;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucc.i.method.authenticationprocess.AuthenticationProcessGET;
import ucc.i.steps.experience.AccountExpSteps;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;

public class AuthenticationProcSteps {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationProcSteps.class);
	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
	private static String autoEmail =  EnvironmentSpecificConfiguration.from(envVar)
			.getProperty("autoEmail");
	AccountExpSteps accountExpSteps = new AccountExpSteps();
    private TestUtils tUtil = new TestUtils();
	private static String endPoint;
	private static Response procResp;
	
	
	
	@Steps
	AuthenticationProcessGET authenticationProcessGET;
	
	
	
	@When("I do GET call to Authentication Process API for accessTokens")
	public void i_do_get_call_to_authentication_process_api_for_access_tokens() {
		endPoint = authenticationProcessGET.setEndpointToAccessTokens(accountExpSteps.kmap.get("ucid"));
		procResp = authenticationProcessGET.get(endPoint)
				.extract().response();
	}
	
	@When("I do GET call to Authentication Process API for accessTokens without ucid")
	public void i_do_get_call_to_authentication_process_api_for_access_tokens_without_ucid() {
		endPoint = authenticationProcessGET.setEndpointToAccessTokens("");
		procResp = authenticationProcessGET.get(endPoint)
				.extract().response();
		
		tUtil.putToSession("response", procResp);
	}
	
	@When("I do GET call to Authentication Process API for accessTokens with invalid ucid")
	public void i_do_get_call_to_authentication_process_api_for_access_tokens_with_invalid_ucid() {
		endPoint = authenticationProcessGET.setEndpointToAccessTokens(tUtil.AppendTimestamp(accountExpSteps.kmap.get("ucid")));
		procResp = authenticationProcessGET.get(endPoint)
				.extract().response();
		
		tUtil.putToSession("response", procResp);
	}

	@Then("I see accessToken in the response")
	public void i_see_token_in_the_response() {
		tUtil.verifyStatus(procResp, ResponseCode.OK);
	    Assert.assertNotNull(procResp.jsonPath().get("accessToken"));
	}
}
