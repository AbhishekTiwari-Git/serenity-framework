package ucc.i.steps;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;

import ucc.i.method.amc.AMCPOST;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

public class AMCSteps {

	public static Response resp=null;
	public static Map<String,String> kmap = new HashMap<String,String>();
	public static JSONObject requestParams = new JSONObject();
	static String end_pt = null;
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	static String email_value;
	
	@Steps
	AMCPOST AMCPOSTSteps;

	String filePostEvent = "AdobeMC_Post_event.json";

	@Given("^User send a POST request to adobe message center with valid data (.*) (.*) (.*) (.*)$")
	public void post_eventAdobeMsgCenter(String eventTyp, String eml, String extId, String ctxCt) throws Exception {
		
		end_pt = AMCPOSTSteps.setEndPoint();
		
		resp = AMCPOSTSteps.pushEventInAdobe(eventTyp, eml, extId, ctxCt,end_pt)
			   .extract().response();
		
	}

	@Then("^request should return '200' status$")
	public void patch_update_User_invalid_data() throws Exception {
		
	//  AMCPOSTSteps.verify_emailReceived();	
		
	}

	@When("^I send a POST request to AdobeMC event with valid entries$")
	public void iSendAPOSTRequestToAdobeMCEventWithValidEntries() throws Exception {
		email_value = tUtil.AppendTimestamp("qa@example.com");
		jsonUtils.update_JSONValue(filePostEvent, "$.email", email_value);

		resp = AMCPOSTSteps.postProcessEvent(filePostEvent)
				.extract().response();
	}

	@Then("^I see status '200' in response$")
	public void iSeeStatusInResponse() {
		tUtil.verifyStatus(resp);
	}

	@When("^I send a POST request to AdobeMC event with blank eventType$")
	public void iSendAPOSTRequestToAdobeMCEventWithBlankEventType() throws Exception {
		email_value = tUtil.AppendTimestamp("qa@example.com");
		jsonUtils.update_JSONValue(filePostEvent, "$.email", email_value);

		String eventType = jsonUtils.getFromJSON(filePostEvent, "$.eventType");
		jsonUtils.update_JSONValue(filePostEvent, "$.eventType", "");
		resp = AMCPOSTSteps.postProcessEvent(filePostEvent)
				.extract().response();

		jsonUtils.update_JSONValue(filePostEvent, "$.eventType", eventType);
	}

	@When("^I send a POST request to AdobeMC event with blank email$")
	public void iSendAPOSTRequestToAdobeMCEventWithBlankEmail() throws Exception {
		jsonUtils.update_JSONValue(filePostEvent, "$.email", "");
		resp = AMCPOSTSteps.postProcessEvent(filePostEvent)
				.extract().response();

		email_value = tUtil.AppendTimestamp("qa@example.com");
		jsonUtils.update_JSONValue(filePostEvent, "$.email", email_value);
	}

	@When("^I send a POST request to AdobeMC event with invalid email$")
	public void iSendAPOSTRequestToAdobeMCEventWithInvalidEmail() throws Exception {
		email_value = tUtil.AppendTimestamp("qaexample.com");
		jsonUtils.update_JSONValue(filePostEvent, "$.email", email_value);

		jsonUtils.update_JSONValue(filePostEvent, "$.email", email_value);
		resp = AMCPOSTSteps.postProcessEvent(filePostEvent)
				.extract().response();

		email_value = tUtil.AppendTimestamp("qa@example.com");
		jsonUtils.update_JSONValue(filePostEvent, "$.email", email_value);
	}

	@Then("^I see status (.*) in response and message (.*)$")
	public void iSeeStatusStsInResponseAndMessageMsg(int sts, String msg) {
		tUtil.verify_msgCode(resp, msg, sts);
	}

	@When("^I send a POST request to AdobeMC event with blank ProductNames$")
	public void iSendAPOSTRequestToAdobeMCEventWithBlankProductNames() throws Exception {
		email_value = tUtil.AppendTimestamp("qa@example.com");
		jsonUtils.update_JSONValue(filePostEvent, "$.email", email_value);

		String ProductNames = jsonUtils.getFromJSON(filePostEvent, "$.ProductNames");
		jsonUtils.update_JSONValue(filePostEvent, "$.ProductNames", "");
		resp = AMCPOSTSteps.postProcessEvent(filePostEvent)
				.extract().response();

		jsonUtils.update_JSONValue(filePostEvent, "$.ProductNames", ProductNames);
	}

	@When("^I send a POST request to AdobeMC event with blank OrderDate$")
	public void iSendAPOSTRequestToAdobeMCEventWithBlankOrderDate() throws Exception {
		email_value = tUtil.AppendTimestamp("qa@example.com");
		jsonUtils.update_JSONValue(filePostEvent, "$.email", email_value);

		String OrderDate = jsonUtils.getFromJSON(filePostEvent, "$.OrderDate");
		jsonUtils.update_JSONValue(filePostEvent, "$.OrderDate", "");
		resp = AMCPOSTSteps.postProcessEvent(filePostEvent)
				.extract().response();

		jsonUtils.update_JSONValue(filePostEvent, "$.OrderDate", OrderDate);
	}

	@When("^I send a POST request to AdobeMC event with blank Username$")
	public void iSendAPOSTRequestToAdobeMCEventWithBlankUsername() throws Exception {
		email_value = tUtil.AppendTimestamp("qa@example.com");
		jsonUtils.update_JSONValue(filePostEvent, "$.email", email_value);

		String Username = jsonUtils.getFromJSON(filePostEvent, "$.Username");
		jsonUtils.update_JSONValue(filePostEvent, "$.Username", "");
		resp = AMCPOSTSteps.postProcessEvent(filePostEvent)
				.extract().response();

		jsonUtils.update_JSONValue(filePostEvent, "$.Username", Username);
	}

	@When("^I send a POST request to AdobeMC event verify optional field (.*)$")
	public void iSendAPOSTRequestToAdobeMCEventVerifyOptionalField(String field) throws Exception {
		email_value = tUtil.AppendTimestamp("qa@example.com");
		jsonUtils.update_JSONValue(filePostEvent, "$.email", email_value);

		String field_value = jsonUtils.getFromJSON(filePostEvent, "$.".concat(field));
		jsonUtils.update_JSONValue(filePostEvent, "$.".concat(field), "");

		resp = AMCPOSTSteps.postProcessEvent(filePostEvent)
				.extract().response();

		jsonUtils.update_JSONValue(filePostEvent, "$.".concat((field)), field_value);
	}

	@Then("^I see status '500' in response$")
	public void iSee500StatusInResponse() {
		tUtil.verifyStatus(resp, 500);
	}
}
