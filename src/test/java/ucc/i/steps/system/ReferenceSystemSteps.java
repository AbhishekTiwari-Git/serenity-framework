package ucc.i.steps.system;

import java.io.IOException;
import java.net.URISyntaxException;

import org.json.JSONException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.i.method.referenceSystem.ReferenceSystemGET;
import ucc.i.method.referenceSystem.ReferenceSystemHelper;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;

public class ReferenceSystemSteps {

	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	private TestUtils tUtil = new TestUtils();
	private static Response sysResp = null;
	private String endPoint = null;
	

	
	@Steps
	ReferenceSystemGET referenceSystemGET;
	
	@Steps
	ReferenceSystemHelper referenceSystemHelper;

	
	
	String file_path = env_var.getProperty("json.body.path");
	private final String primary_specialties = file_path + "/AICFieldsValidation/primary-specialities.json";
	private final String canada_provinces = file_path + "/AICFieldsValidation/canada/provinces.json";
	private final String institutionalType = file_path + "/AICFieldsValidation/institutionalType.json";
									

	@When("user sends a GET request to refSysAPI for invalid field")
	public void user_sends_a_get_request_to_invalid_field() 
			throws URISyntaxException {

		endPoint = referenceSystemGET.setEndpointTo_invalidField();
		sysResp = referenceSystemGET.get(endPoint)
				.extract().response();

		tUtil.putToSession("response", sysResp);
	}

	@When("user sends a GET request to fields_institutionalType for Reference Sys API")
	public void user_sends_a_get_request_to_fields_institutionalType() 
			throws URISyntaxException {

		endPoint = referenceSystemGET.setEndpointTo_fields_institutionalType();
		sysResp = referenceSystemGET.get(endPoint)
				.extract().response();

		tUtil.putToSession("response", sysResp);
	}

	@And("return response should match with business provided json file for fields_institutionalType")
	public void return_response_should_match_with_business_provided_json_file_for_fields_institutionalType() 
			throws IOException {

		tUtil.verifyStatus(sysResp, ResponseCode.OK);
		ReferenceSystemHelper.verifyJSONObject(institutionalType, sysResp);
	}
	
	@When("^user sends a GET request to refSysAPI fields (.*)$")
	public void userSendsGETrequestTo_refSysAPI_toEndpoint_fields(String fields) throws URISyntaxException{

		sysResp = referenceSystemGET.getFields(fields).extract().response();
	}
	
	@And("^fields (.*) should be present in API Response$")
	public void verifyJsonForFields (String fields) throws IOException{
		
		tUtil.verifyStatus(sysResp, ResponseCode.OK);
		referenceSystemHelper.verifyJson(sysResp, fields);
	}

	@When("^user sends a GET request to refSysAPI fields_fieldName (.*)$")
	public void userSendsGETrequestTo_refSysAPI_toEndpoint_fields_fieldName(String fields_fieldName)
			throws URISyntaxException {
		
		sysResp = referenceSystemGET.getFields(fields_fieldName).extract().response();
	}
	
	@Then("^the request should return 200 responseCode$")
    public void status200() {
		
        tUtil.verifyStatus(sysResp, ResponseCode.OK);
    }

	@And("^all fields_fieldName (.*) should be present in API Response$")
	public void verifyJsonFilematchesToResponse(String fields_fieldName)
			throws JSONException, IOException {
		
		referenceSystemHelper.verifyJson(sysResp, fields_fieldName);
	}
	
	@When("user sends a GET request to fields_primarySpecialty")
	public void user_sends_a_get_request_to_fields_primary_specialty() throws URISyntaxException {
	    
		endPoint = referenceSystemGET.setEndpointTo_fieldsPrimarySpecialties();
		sysResp = referenceSystemGET.get(endPoint).extract().response();
	}
	
	@Then("return response should match with business provided json file for primary_specialties")
	public void return_response_should_match_with_business_provided_json_file() throws IOException {
	    
		tUtil.verifyStatus(sysResp, ResponseCode.OK);
		referenceSystemHelper.verifyJSONObject(primary_specialties, sysResp);
	}

	@When("user sends a GET request to fields_canada_provinces")
	public void user_sends_a_get_request_to_fields_canada_provinces() throws URISyntaxException {
	    
		endPoint = referenceSystemGET.setEndpointTo_fieldsCanadaProvinces();
		sysResp = referenceSystemGET.get(endPoint).extract().response();
	}
	
	@Then("return response should match with business provided json file for canada_provinces")
	public void return_response_should_match_with_business_provided_json_file_for_canada_provinces() throws IOException {
	    
		tUtil.verifyStatus(sysResp, ResponseCode.OK);
		ReferenceSystemHelper.verifyJSONObject(canada_provinces, sysResp);
	}
}
