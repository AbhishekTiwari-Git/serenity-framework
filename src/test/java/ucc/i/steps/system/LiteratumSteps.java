package ucc.i.steps.system;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import ucc.cr.pages.catalyst.ui.RegInsighCouncilPage;
import ucc.i.method.catalystLiteratum.CatalystLiteratumSystemHelper;
import ucc.i.method.catalystLiteratum.CatalystLiteratumSystemPOST;
import ucc.i.method.literatum.LiteratumGET;
import ucc.i.method.literatum.LiteratumPATCH;
import ucc.i.method.literatum.LiteratumPOST;
import ucc.i.method.literatumprocess.CatalystliteratumprocessPOST;
import ucc.i.steps.utils.CheckResponseCode;
import ucc.utils.ConnectionFactory;
import ucc.utils.DbUtils;
import ucc.utils.JsonUtils;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;

public class LiteratumSteps {
	
	
	public static Response resp=null;
	public static Map<String,String> kmap = new HashMap<String,String>();
	public static JSONObject requestParams = new JSONObject();
	Boolean flag = false;
	static String email_value;
	static String end_pt = null;
	String file_name = "Literatum_identity.json";
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	List<String> get_Vals=new ArrayList<String>();
	List<String> get_ConfVals=new ArrayList<String>();
	List<String> get_ConfKeys=new ArrayList<String>();
	private List<String> subsIdList = new ArrayList<>();
	private final DbUtils dbUtils = new DbUtils();
	static String ucid, inid;
	static int numOfAssignedLicenses;
	static int noTags;
	private String subscrptnID;
	private SoftAssert softAssert = new SoftAssert();
	
	@Steps
	LiteratumPOST LiteratumPOSTSteps;
	
	@Steps
	LiteratumGET LiteratumGETSteps;
	
	@Steps 
	LiteratumPATCH LiteratumPATCHSteps;
	
	@Steps
	CatalystliteratumprocessPOST catJLPOSTSteps;
	
	@Steps
	CatalystLiteratumSystemPOST catJLSysPOSTSteps;
	
	String filePostPersonIdentity = "Literatum_Post_Identity.json";
	String filePatchIdentity = "Literatum_Patch_Identity.json";
	String filePostLicense = "Literatum_Post_License.json";
	String filePostWrongTag = "Literatum_Post_Wrong_Tag.json";
	String filePostValidTag = "Literatum_Post_Valid_Tag.json";
	String filePatchLicense = "Literatum_Patch_License.json";

	
	@Title("POST Request for identity ")
	@When("^I send a POST request to create an identity$")
	public void POST_request_with_the_identity() throws Exception {
		end_pt = LiteratumPOSTSteps.setIdentityEndpoint();
		jsonUtils.JSONArrayAppendTimestampfunction(file_name, "$..email[0].value", "automation@example.com");
		
		resp = LiteratumPOSTSteps.CreateIdentity(file_name, end_pt, "IND")
			   .extract().response();
		
	}
	
	@When("I do a POST call to create a person identity")
	public void create_person_identity() throws Exception {
		String file = CatalystLiteratumSystemJsonBodyFile.Catalyst_Person_Identity;
		CatalystLiteratumSystemHelper.POST_updateFile_create_person_identity(file);
    	
    	end_pt = LiteratumPOSTSteps.setEndpointPersonIdentity();		
		resp = LiteratumPOSTSteps.post(file, end_pt)
				.extract().response();
	}
	
	@Then("person identity should be created with 201 status code")
	public void verify_created_identity() {
		tUtil.verifyStatus(resp, ResponseCode.CREATED);
		Assert.assertEquals("UCID missing from the response!", tUtil.getFromSession("ucid"), resp.jsonPath().get("ucid").toString());
	}
	
	 @When("I send a GET call to fetch the person identity")
	 public void GET_person_identity() throws InterruptedException {
			int timeOutMin = 12;
			end_pt = LiteratumGETSteps.setEndpointPersonIdentity(tUtil.getFromSession("ucid").toString());

			do {
				System.out.println("Waiting for request Catalyst Literatum System to get finished");
				TimeUnit.SECONDS.sleep(5);
				resp = LiteratumGETSteps.get(end_pt).extract().response();
				tUtil.putToSession("response", resp);
				CheckResponseCode.verifyResponseCode200();
				if ((resp.jsonPath().getString("email").equals(tUtil.getFromSession("email")))) {
					break;
				}
				timeOutMin--;

			} while (timeOutMin != 0);
	 }
	 
	 @Then("I should verify fetched person identity with status code 200")
	 public void verify_fetched_person_identity() {
		 softAssert.assertEquals(resp.statusCode(), ResponseCode.OK, "status code is not 200 OK");
		 softAssert.assertEquals(resp.jsonPath().get("ucid"), tUtil.getFromSession("ucid"), "UCID is not as expected in the JSON response!");
		 softAssert.assertEquals(resp.jsonPath().get("email"), tUtil.getFromSession("email"), "EMAIL is not as expected in the JSON response!");
		 softAssert.assertEquals(resp.jsonPath().get("firstName"), tUtil.getFromSession("firstName"), "First Name is not as expected in the JSON response!");
		 softAssert.assertEquals(resp.jsonPath().get("lastName"), tUtil.getFromSession("lastName"), "Last Name is not as expected in the JSON response!");
		 
		 softAssert.assertAll();
	 }
	 
	 @When("I do a PATCH call to update a person identity")
	 public void PATCH_to_update_person_identity() throws Exception {
		String file = CatalystLiteratumSystemJsonBodyFile.Catalyst_Person_Identity;
		CatalystLiteratumSystemHelper.PATCH_updateFile_create_person_identity(file);

		end_pt = LiteratumPATCHSteps.setEndpointUpdateIdentity(tUtil.getFromSession("ucid").toString());
		resp = LiteratumPATCHSteps.patch(file, end_pt).extract().response();
	 }
	 
	 @Then("person identity should be updated with 204 status code")
	 public void verify_updated_person_identity() {
		 tUtil.verifyStatus(resp, ResponseCode.UPDATED);
	 }
	 
	@Title("Verify Status Code")
	@Then("^identity should be created with 201 status code$")
	public void verifyUsersts() {
		tUtil.verifyStatus(resp, 201);
	}
	
	@Title("POST Request for identity with minimum post body")
	@When("^I send a POST request to create an identity with minimum body$")
	public void POST_request_minimum_identity() throws Exception {
		end_pt = LiteratumPOSTSteps.setIdentityEndpoint();
		
		resp = LiteratumPOSTSteps.CreateIdentity("Literatum_identity_minimum.json", end_pt, "IND")
			   .extract().response();
		
	}
	
	
	@Title("POST Request for institute")
	@When("^I send a POST request to create an institute with minimum body$")
	public void POST_request_with_the_institute() throws Exception {
		end_pt = LiteratumPOSTSteps.setIdentityEndpoint();
		jsonUtils.JSONArrayAppendTimestampfunction("Literatum_institute.json", "$..isni", "11");
		
		resp = LiteratumPOSTSteps.CreateIdentity("Literatum_institute.json", end_pt, "INST")
			   .extract().response();
		
	}
	
	
	@Title("POST Request for institute with invalid data")
	@When("^I send a POST request to create an institute with invalid body field (.*)$")
	public void invalid_institute(String field) throws Exception {
		end_pt = LiteratumPOSTSteps.setIdentityEndpoint();
		jsonUtils.update_JSONArrayfunction("Literatum_institute_invalid.json", "$..identity.organization-type", "institute");
		
		resp = LiteratumPOSTSteps.CreateIdentity("Literatum_institute_invalid.json", end_pt, "INST")
			   .extract().response();
		
	}
	
	@Title("Verify error code and msg")
	@Then("^application should throw valid error code (.*) and message (.*)$")
	public void verifyMsgSts(int sts, String msg) {
		LiteratumPOSTSteps.verify_msg(resp, msg, sts);
	}
	
	
	@Title("POST Request for same institute")
	@When("^I send a POST request to create an institute with same body$")
	public void institute() throws Exception {
		end_pt = LiteratumPOSTSteps.setIdentityEndpoint();
		resp = LiteratumPOSTSteps.CreateIdentity("Literatum_institute.json", end_pt, "INST")
			   .extract().response();
		
	}
	
	// =================================================================================================================
	
	@Title("POST request to create a person identity")
	public void createPersonIdentity(String uc, String eml) throws Exception {
		jsonUtils.modify_JSONAppendTimestampfunction(filePostPersonIdentity, "email", eml);
		jsonUtils.modify_JSONAppendTimestampfunction(filePostPersonIdentity, "ucid", uc);
		jsonUtils.update_JSONValue(filePostPersonIdentity, "$.tag..tag-set-code", "catalyst-audience-seg");
    	jsonUtils.update_JSONValue(filePostPersonIdentity, "$.tag..tag-code", "other");
		 
		end_pt = LiteratumPOSTSteps.setEndpointPersonIdentity();
		
		resp = LiteratumPOSTSteps.post(filePostPersonIdentity, end_pt)
				.extract().response();
		
		LiteratumSteps.ucid = resp.body().jsonPath().getString("ucid");
		System.out.println("Newly created ucid: " + ucid);
	}
	
	@Title("POST request to assign license to created person identity")
	public void createLicense() throws Exception {
		String uniqCode = tUtil.AppendTimestamp("code");
		jsonUtils.update_JSONValue(filePostLicense, "code", uniqCode);
		jsonUtils.update_JSONValue(filePostLicense, "ucid", LiteratumSteps.ucid);
		
		end_pt = LiteratumPOSTSteps.setEndpointLicenses();
		
		resp = LiteratumPOSTSteps.post(filePostLicense, end_pt)
				.extract().response();
		
		kmap.put("code", uniqCode);
	}
	
    @Given("^user sends a POST request with valid ucid (.*) and email (.*)$")
    public void user_sends_a_post_request_with_valid_ucid_and_email(String ucid, String email) throws Throwable {
    	createPersonIdentity(ucid, email);
    }
    
    @Given("^Literatum identity with valid email (.*) and ucid (.*) and (.*) assigned licenses exists$")
    public void literatum_identity_with_valid_email_and_ucid_and_assigned_licenses_exists(String email, String ucid, int numOfLicenses) throws Throwable {
        createPersonIdentity(ucid,  email);
        
        for (int i = 0; i < numOfLicenses; i++) {
        	try {
        		Thread.sleep(700);
        		createLicense();
            	System.out.println("adding license...");
        	} catch(InterruptedException ex) {
        		Thread.currentThread().interrupt();
        	}
        }
        
        LiteratumSteps.numOfAssignedLicenses = numOfLicenses;
        
    }
    
    @When("^user sends a POST request to create an identity with valid ucid email (.*) tag-set-code (.*) tag-code (.*)$")
    public void user_sends_a_post_request_to_create_an_identity_with_valid_ucid_email_tagsetcode_tagcode(String email, String tagSetCode, String tagCode) throws Throwable {
        LiteratumSteps.ucid = tUtil.generateRandomUcid(36);
        
        jsonUtils.update_JSONValue(filePostPersonIdentity, "ucid", LiteratumSteps.ucid);
        jsonUtils.modify_JSONAppendTimestampfunction(filePostPersonIdentity, "email", email);
        jsonUtils.update_JSONValue(filePostPersonIdentity, "$.tag..tag-set-code", tagSetCode);
    	jsonUtils.update_JSONValue(filePostPersonIdentity, "$.tag..tag-code", tagCode);
    	
    	end_pt = LiteratumPOSTSteps.setEndpointPersonIdentity();
		
		resp = LiteratumPOSTSteps.post(filePostPersonIdentity, end_pt)
				.extract().response();
		System.out.println("Newly created ucid: " + LiteratumSteps.ucid);
    }
    
    @When("^user sends a POST request to create an identity with invalid ucid (.*) email (.*) tag-set-code (.*) tag-code (.*)$")
    public void user_sends_a_post_request_to_create_an_identity_with_invalid_ucid_email_tagsetcode_tagcode(String ucid, String email, String tagSetCode, String tagCode) throws Throwable {
    	String tsEmail = (email.isEmpty() || !email.matches("(.*)@(.*).(.*)")) ? email : tUtil.AppendTimestamp(email);
        jsonUtils.update_JSONValue(filePostPersonIdentity, "ucid", ucid);
        jsonUtils.update_JSONValue(filePostPersonIdentity, "email", tsEmail);
        jsonUtils.update_JSONValue(filePostPersonIdentity, "$.tag..tag-set-code", tagSetCode);
    	jsonUtils.update_JSONValue(filePostPersonIdentity, "$.tag..tag-code", tagCode);
    	
    	end_pt = LiteratumPOSTSteps.setEndpointPersonIdentity();
		
		resp = LiteratumPOSTSteps.post(filePostPersonIdentity, end_pt)
				.extract().response();
    }
	
    @When("^user sends a GET request to get Literatum identity by valid ucid$")
    public void user_sends_a_get_request_to_get_literatum_identity_by_valid_ucid() throws Throwable {
    	end_pt = LiteratumGETSteps.setEndpointIdentity(ucid);
        
        resp = LiteratumGETSteps.get(end_pt)
        		.extract().response();
    }
    
    @When("^user sends a GET request to get Literatum identity by invalid ucid (.*)$")
    public void user_sends_a_get_request_to_get_literatum_identity_by_invalid_ucid(String i_ucid) throws Throwable {
    	end_pt = LiteratumGETSteps.setEndpointIdentity(i_ucid);
        
        resp = LiteratumGETSteps.get(end_pt)
        		.extract().response();
    }
    
    @When("^user sends an invalid PATCH request to update a person (.*) (.*) (.*)$")
    public void user_sends_an_invalid_PATCH_request_to_update_a_person(String n_email, String tagSetCode, String tagCode) throws Exception {
        // Update the json file: There are only enforced restrictions on email, tag-set-code and tag-code
    	// So, we only need to update those values for invalid PATCH request
    	jsonUtils.update_JSONValue(filePatchIdentity, "$.email", n_email);
    	jsonUtils.update_JSONValue(filePatchIdentity, "$.tag..tag-set-code", tagSetCode);
    	jsonUtils.update_JSONValue(filePatchIdentity, "$.tag..tag-code", tagCode);
    	
    	end_pt = LiteratumPATCHSteps.setEndpointUpdateIdentity(ucid);
    	
    	resp = LiteratumPATCHSteps.patch(filePatchIdentity, end_pt)
    			.extract().response();
    	tUtil.putToSession("response", resp);
    }

    @When("^user sends a valid PATCH request to update a person (.*) (.*) (.*) (.*) (.*)$")
    public void user_sends_a_valid_PATCH_request_to_update_a_person(String n_email, String fname, String lname, String tagSetCode, String tagCode) throws Exception {
        // Update the json file 
     	jsonUtils.update_JSONValue(filePatchIdentity, "$.email", n_email);
    	jsonUtils.update_JSONValue(filePatchIdentity, "$.firstName", fname);
    	jsonUtils.update_JSONValue(filePatchIdentity, "$.lastName", lname);
    	jsonUtils.update_JSONValue(filePatchIdentity, "$.tag..tag-set-code", tagSetCode);
    	jsonUtils.update_JSONValue(filePatchIdentity, "$.tag..tag-code", tagCode);
    	
    	end_pt = LiteratumPATCHSteps.setEndpointUpdateIdentity(ucid);
    	
    	resp = LiteratumPATCHSteps.patch(filePatchIdentity, end_pt)
    			.extract().response();
    	tUtil.putToSession("response", resp);
    }
    
    @When("^user sends a Literatum GET request to retrieve licenses assigned to identity with valid ucid$")
    public void user_sends_a_literatum_get_request_to_retrieve_licenses_assigned_to_identity_with_valid_ucid() throws Throwable {
    	end_pt = LiteratumGETSteps.setEndpointLicenses(ucid);
    	
    	resp = LiteratumGETSteps.get(end_pt)
    			.extract().response();
    }
    
	@When("^user sends a Literatum GET request to retrieve licenses assigned to identity with invalid ucid (.*)$")
	public void user_sends_a_literatum_get_request_to_retrieve_licenses_assigned_to_identity_with_invalid_ucid(String ucid) throws Throwable {
		end_pt = LiteratumGETSteps.setEndpointLicenses(ucid);

		resp = LiteratumGETSteps.get(end_pt)
				.extract().response();
	}
	
    @When("^user sends a POST request to create and assign valid license to identity$")
    public void user_sends_a_post_request_to_create_and_assign_valid_license_to_identity() throws Throwable {
        createLicense();
    }
    
    @Then("^the license should be created and assigned with status code (.*)$")
    public void the_license_should_be_created_and_assigned_with_status_code(int stcode) throws Throwable {
        tUtil.verifyStatus(resp, stcode);
    }
    
    @Then("^created license should be in the response$")
    public void created_license_should_be_in_the_response() throws Throwable {
        JsonPath jsonpathEvaluator = resp.jsonPath();
        
        List<String> loCode = jsonpathEvaluator.getList("code");
        Assert.assertTrue(loCode.contains(kmap.get("code")));
        
        List<String> loLicensesUcid = jsonpathEvaluator.getList("ucid");
        loLicensesUcid.forEach(el -> {Assert.assertEquals(el, LiteratumSteps.ucid);});
    }
	
    @Then("^the Literatum GET request should return a message (.*) with status code (.*)$")
    public void the_literatum_get_request_should_return_a_message_with_status_code(String msg, int stcode) throws Throwable {
        tUtil.verify_msgCode(resp, msg, stcode);
    }
    
    @Then("^the Literatum GET request should return a list of licenses assigned to valid ucid with status code (.*)$")
    public void the_literatum_get_request_should_return_a_list_of_licenses_assigned_to_valid_ucid_with_status_code(int stcode) throws Throwable {
        tUtil.verifyStatus(resp, stcode);
        List<String> loLicensesUcid = resp.jsonPath().getList("ucid");
        loLicensesUcid.forEach(el -> {Assert.assertEquals(el, LiteratumSteps.ucid);});
        Assert.assertEquals(loLicensesUcid.size(), LiteratumSteps.numOfAssignedLicenses);
    }
    
    @Then("^user should be created with 201 code$")
    public void user_should_be_created() {
        tUtil.verifyStatus(resp, 201);
    }

    @Then("^the request should return corresponding code status (.*) with message (.*)$")
    public void the_request_should_return_corresponding_code_status_with_message(int code, String msg) throws Throwable {
    	tUtil.verify_msgCode(resp, msg, code);
    }
    
    @Then("^the request should return '200' status with corresponding person identity information$")
    public void the_request_should_return_200_status_with_valid_identity() throws Throwable {
        tUtil.verifyStatus(resp, 200);
        // We can verify ucid, email, firstname, lastname
        // Only need to check for email and ucid because they should be unique
        
        JsonPath jsonpathEvaluator = resp.jsonPath();
        
        String actualUcid = jsonpathEvaluator.getString("ucid");
        String actualEmail = jsonpathEvaluator.getString("email");
        String actualFName = jsonpathEvaluator.getString("firstName");
        String actualLName = jsonpathEvaluator.getString("lastName");
        
        String expectedUcid =  jsonUtils.getFromJSON(filePostPersonIdentity, "ucid");
        String expectedEmail = jsonUtils.getFromJSON(filePostPersonIdentity, "email");
        String expectedFName = jsonUtils.getFromJSON(filePostPersonIdentity, "firstName");
        String expectedLName = jsonUtils.getFromJSON(filePostPersonIdentity, "lastName");
        
        Assert.assertEquals(actualUcid, expectedUcid);
        Assert.assertEquals(actualEmail, expectedEmail);
        Assert.assertEquals(actualFName, expectedFName);
        Assert.assertEquals(actualLName, expectedLName);
    }
    
    @Then("^tag-set-code (.*) and tag-code (.*) should be present$")
    public void tagsetcode_and_tagcode_should_be_present(String tagsetcode, String tagcode) throws Throwable {
    	JsonPath jsonpathEvaluator = resp.jsonPath();
    	
    	List<String> actualLoTagSetCode = jsonpathEvaluator.getList("tag.tag-set-code");
        List<String> actualLoTagCode = jsonpathEvaluator.getList("tag.tag-code");
        
        // Can try using Map or similar for a better lookup
        
        int foundPair = 0;
        for (int i = 0; i < actualLoTagSetCode.size(); i++) {
        	if (actualLoTagSetCode.get(i).equals(tagsetcode)) {
        		// Found a tag-set-code
        		if (actualLoTagCode.get(i).equals(tagcode)) {
        			// Found its tag-code 
        			foundPair++;
        		}
        	}
        }
        
        Assert.assertTrue("The tag-set-code tag-code pair was not updated/added", foundPair == 1);
    }

    @Then("^PATCH request response should return corresponding code status (.*) with message (.*)$")
    public void patch_request_response_should_return_corresponding_code_status_with_message(int st_code, String msg) {
    	tUtil.verify_msgCode(resp, msg, st_code);
    }

    @Then("^person identity information should be updated with status code (.*)$")
    public void person_identity_information_should_be_updated_with_status_code(int st_code) throws Throwable {
        tUtil.verifyStatus(resp, st_code);
        
        String expectedEmail = jsonUtils.getFromJSON(filePatchIdentity, "email");
        String expectedFName = jsonUtils.getFromJSON(filePatchIdentity, "firstName");
        String expectedLName = jsonUtils.getFromJSON(filePatchIdentity, "lastName");
        String expectedTagSetCode = jsonUtils.getFromJSON(filePatchIdentity, "tag..tag-set-code").replace("[", "").replace("]", "");
        String expectedTagCode = jsonUtils.getFromJSON(filePatchIdentity, "tag..tag-code").replace("[", "").replace("]", "");
        
        // Get the identity info from GET call
        user_sends_a_get_request_to_get_literatum_identity_by_valid_ucid();
        
        JsonPath jsonpathEvaluator = resp.jsonPath();
        
        String actualEmail = jsonpathEvaluator.getString("email");
        String actualFName = jsonpathEvaluator.getString("firstName");
        String actualLName = jsonpathEvaluator.getString("lastName");
        
        List<String> actualLoTagSetCode = jsonpathEvaluator.getList("tag.tag-set-code");
        List<String> actualLoTagCode = jsonpathEvaluator.getList("tag.tag-code");
        Assert.assertEquals(actualLoTagSetCode.size(), actualLoTagCode.size());
        
        Assert.assertEquals(actualEmail, expectedEmail);
        Assert.assertEquals(actualFName, expectedFName);
        Assert.assertEquals(actualLName, expectedLName);
        Assert.assertTrue(actualLoTagSetCode.contains(expectedTagSetCode));
        Assert.assertTrue(actualLoTagCode.contains(expectedTagCode));
        
        int foundPair = 0;
        for (int i = 0; i < actualLoTagSetCode.size(); i++) {
        	if (actualLoTagSetCode.get(i).equals(expectedTagSetCode)) {
        		// Found a tag-set-code
        		if (actualLoTagCode.get(i).equals(expectedTagCode)) {
        			// Found its tag-code 
        			foundPair++;
        		}
        	}
        }
        
        Assert.assertTrue("The tag-set-code tag-code pair was not updated/added", foundPair == 1);
     
    }

	@When("^I send a POST request to create user without ucid$")
	public void i_send_POST_request_create_user_without_ucid() throws Exception {
		jsonUtils.update_JSONValue(filePostPersonIdentity, "ucid", "");
		jsonUtils.update_JSONValue(filePostPersonIdentity, "email", "automation@example.com");

		end_pt = LiteratumPOSTSteps.setEndpointPersonIdentity();

		resp = LiteratumPOSTSteps.post(filePostPersonIdentity, end_pt)
				.extract().response();
	}

	@When("^I send a POST request to create user without email$")
	public void i_send_POST_request_create_user_without_email() throws Exception {
		jsonUtils.update_JSONValue(filePostPersonIdentity, "ucid", "QA_test");
		jsonUtils.update_JSONValue(filePostPersonIdentity, "email", "");

		end_pt = LiteratumPOSTSteps.setEndpointPersonIdentity();

		resp = LiteratumPOSTSteps.post(filePostPersonIdentity, end_pt)
				.extract().response();
	}

	@When("^I send a POST request to create user with invalid tag-code$")
	public void i_send_POST_request_create_user_with_invalid_tagcode() throws Exception {
		String ucid = jsonUtils.getFromJSON(filePostWrongTag, "ucid");
		String email = jsonUtils.getFromJSON(filePostWrongTag, "email");
		jsonUtils.modify_JSONAppendTimestampfunction(filePostWrongTag, "ucid", ucid);
		jsonUtils.modify_JSONAppendTimestampfunction(filePostWrongTag, "email", email);
		end_pt = LiteratumPOSTSteps.setEndpointPersonIdentity();

		resp = LiteratumPOSTSteps.post(filePostWrongTag, end_pt)
				.extract().response();
	}

	@When("^I send a POST request to create user with valid tag-code$")
	public void i_send_POST_request_create_user_with_valid_tagcode() throws Exception {
		ucid = tUtil.generateRandomUcid(36);

		jsonUtils.modify_JSONAppendTimestampfunction(filePostValidTag, "ucid", ucid);
		jsonUtils.modify_JSONAppendTimestampfunction(filePostValidTag, "email", "automation@example.com");
		end_pt = LiteratumPOSTSteps.setEndpointPersonIdentity();

		resp = LiteratumPOSTSteps.post(filePostValidTag, end_pt)
				.extract().response();

		ucid = resp.body().jsonPath().getString("ucid");
	}

	@When("^I send a GET request to Literatum with ucid$")
	public void i_send_GET_request_with_ucid() {
		end_pt = LiteratumGETSteps.setEndpointIdentity(ucid);

		resp = LiteratumGETSteps.get(end_pt)
				.extract().response();
	}

	@Then("^Ucid should be in response$")
	public void ucid_should_in_response() {
		String expectedUcid = jsonUtils.getFromJSON(filePostValidTag, "ucid");
		JsonPath jsonpathEvaluator = resp.jsonPath();

		String actualUcid = jsonpathEvaluator.getString("ucid");
		Assert.assertEquals(actualUcid, expectedUcid);
	}

	@Given("^Endpont license is up and running$")
	public void event_is_up_and_running() {
		String endpoint = LiteratumPOSTSteps.setEndpointLicenses();
		resp = LiteratumPOSTSteps.checkURL(endpoint)
				.extract().response();
		int statusCode = resp.getStatusCode();
		Assert.assertFalse(500 <= statusCode && statusCode <= 599);
	}

	@When("^I do a PATCH request to Literatum license$")
	public void i_do_path_call_license_url() throws Exception {

		end_pt = LiteratumPATCHSteps.setEndpointLicenses();

		resp = LiteratumPATCHSteps.patch(filePatchLicense, end_pt)
				.extract().response();
	}

	@Then("^the request should return 200$")
	public void verify_response_status() {
		tUtil.verifyStatus(resp);
	}

	@Then("^the request should return 204$")
	public void verify_response_204() {
		tUtil.verifyStatus(resp, 204);
	}

	@Then("^the request should return status code (.*) and message (.*)$")
	public void the_request_should_return_status_code_message(int stcode, String msg) throws Throwable {
		tUtil.verify_msgCode(resp, msg, stcode);
	}

	@When("^I send a GET request to Literatum license$")
	public void send_GET_request_Literatum_license() {
		String expectedUcid = jsonUtils.getFromJSON(filePatchLicense, "ucid");
		String endpoint = LiteratumGETSteps.setEndpointLicenses(expectedUcid);
		resp = LiteratumGETSteps.get(endpoint)
				.extract().response();
	}

	@Then("^the license should be updated$")
	public void theLicenseShouldBeUpdated() {
		JsonPath jsonpathEvaluator = resp.jsonPath();
		String actualCode = (String) jsonpathEvaluator.getList("code").get(0);
		String code = jsonUtils.getFromJSON(filePatchLicense, "code");

		Assert.assertEquals(actualCode, code);
	}

	@Given("^request to assign license to created person identity$")
	public void create_new_License() throws Exception {
		String ucid = tUtil.generateRandomUcid(36);
		String offerCode = jsonUtils.getFromJSON(filePatchLicense, "offerCode");

		createPersonIdentity(ucid, "qauto@qauto.qa");

		jsonUtils.modify_JSONAppendTimestampfunction(filePatchLicense, "code", "code");
		jsonUtils.update_JSONValue(filePatchLicense, "ucid", LiteratumSteps.ucid);

		jsonUtils.modify_JSONAppendTimestampfunction(filePostLicense, "code", "code");
		jsonUtils.update_JSONValue(filePostLicense, "ucid", LiteratumSteps.ucid);
		jsonUtils.update_JSONValue(filePostLicense, "offerCode", offerCode);

		end_pt = LiteratumPOSTSteps.setEndpointLicenses();

		resp = LiteratumPOSTSteps.post(filePostLicense, end_pt)
				.extract().response();
	}
	
	@When("user sends a POST request to create an inid for catalyst literatum")
	public void create_institution_id() throws Exception {
		String file = CatalystLiteratumSystemJsonBodyFile.Catalyst_Create_New_inid;
		jsonUtils.update_JSONValue(file, "inid", tUtil.generateRandomInid());
		end_pt = catJLSysPOSTSteps.setEndpontIdentitiesInstitution();
		resp = catJLSysPOSTSteps.post(file, end_pt).extract().response();
		tUtil.putToSession("inid", resp.jsonPath().get("inid"));
	}
	
	@When("user sends a POST call to link person to an institution in catalyst literatum")
	public void POST_request_toProvide_Person_institutional_license() throws Exception {
		end_pt = catJLPOSTSteps
				.setEndpointInstitutionalAdminLicense(tUtil.getFromSession("ucid").toString());
		resp = catJLPOSTSteps
				.provideInstitutionalLicense(end_pt, tUtil.getFromSession("inid").toString()).extract().response();
		tUtil.putToSession("response", resp);
	}

	@Then("The Person should get linked to an institution with status code 201 in catalyst literatum")
	public void verify_person_got_linked_to_institutionalLicense() {
		CheckResponseCode.verifyResponseCode201();
	}

	@When("user sends a GET request to fetch catalyst literatum identity details")
	public void user_sends_a_get_request_to_fetch_literatum_identity_details() throws Throwable {
		int timeOutMin = 12;
		end_pt = LiteratumGETSteps.setEndpointPersonIdentity(tUtil.getFromSession("ucid").toString());
		do {
			System.out.println("Waiting for request Catalyst Literatum System to get finished");
			TimeUnit.SECONDS.sleep(5);
			resp = LiteratumGETSteps.get(end_pt).extract().response();
			tUtil.putToSession("response", resp);
			CheckResponseCode.verifyResponseCode200();
			if ((resp.jsonPath().get("related-identity") != null)) {
				break;
			}
			timeOutMin--;

		} while (timeOutMin != 0);
	}

	@Then("I verify related-identity info with status code 200 in catalyst literatum")
	public void verify_institnal_adminIndicator_and_relatedIdentity_tags() {
		softAssert.assertEquals(resp.statusCode(), ResponseCode.OK, "Invalid Response code returned!");
		softAssert.assertEquals(resp.jsonPath().get("related-identity.related-identity-type").toString(),
				"[admined-org]", "related-identity-type is not as expected");
		softAssert.assertNotNull(resp.jsonPath().get("related-identity.id-value").toString(), "id-value is null");

		softAssert.assertAll();
	}

	@When("user sends a GET request to fetch the linked multiple institution identities in catalyst literatum")
	public void user_sends_a_get_request_to_fetch_linked_multiple_inid() throws Throwable {
		int timeOutMin = 12;
		end_pt = LiteratumGETSteps.setEndpointPersonIdentity(tUtil.getFromSession("ucid").toString());
		do {
			System.out.println("Waiting for request Catalyst Literatum System to get finished");
			TimeUnit.SECONDS.sleep(5);
			resp = LiteratumGETSteps.get(end_pt).extract().response();
			tUtil.putToSession("response", resp);
			CheckResponseCode.verifyResponseCode200();
			if (resp.jsonPath().getList("related-identity") != null) {
				if ((resp.jsonPath().getList("related-identity").size() == 2)) {
					break;
				}
			}
			timeOutMin--;

		} while (timeOutMin != 0);
	}

	@Then("I verify respective related-identity should get linked to multiple institutions info with status code 200 in catalyst literatum")
	public void verify_related_identity_linked_to_multiple_inid() {
		Assert.assertEquals("related-identity size is not as expected", 2,
				resp.jsonPath().getList("related-identity").size());
	}

	@When("user sends a GET call to fetch the linked multiple ucid to an institution")
	public void GET_call_to_fetch_linked_related_identities() throws InterruptedException {
		end_pt = LiteratumGETSteps.setEndpointInstitutionIdentities(tUtil.getFromSession("inid").toString());
		resp = LiteratumGETSteps.get(end_pt).extract().response();
		tUtil.putToSession("response", resp);
		CheckResponseCode.verifyResponseCode200();
	}

	@Then("I verify respective related-identities in catalyst literatum should get linked to a single inid with status code 200")
	public void verify_related_identity_linked_to_multiple_ucid() {
		Assert.assertEquals("related-identity size is not as expected", 2,
				resp.jsonPath().getList("related-identity").size());
	}
	
	@And("User sends a POST request to create person identity to Catalyst Literatum")
	public void create_person_identity_inCatLitrtum() {
		end_pt = catJLPOSTSteps
				.setEndpointIndentityPerson();
		resp = catJLPOSTSteps
				.createPersonID(end_pt, tUtil.getFromSession("ucid").toString()).extract().response();
	}
	
	@Then("person identity should be created in Catalyst Literatum with status code 201")
	public void verify_prsnID_shd_be_crtd() {
		tUtil.verifyStatus(resp, ResponseCode.CREATED);
	}
	
	@When("I fetch the top ten subscription ids for Catalyst acc exp api from DB")
	public void fetch_subscrptnIds_frmDB() throws IOException, SQLException {
    	String sqlQuery = dbUtils.buildQuery("CatalystCustomers_with_subscription_Ids.sql");
        String dbURL = dbUtils.buildDbUrl("ACSDbUrl");
        Connection conn = ConnectionFactory.getConnectionACS(
            dbURL, dbUtils.acsDBUser(), dbUtils.acsDBPass());
        PreparedStatement ps = conn.prepareStatement(sqlQuery) ;

        ResultSet resultSet = ps.executeQuery() ;
  
        while (resultSet.next()) {
        	subsIdList.add(resultSet.getString("bil_ctm"));
        }
        
    }
}
