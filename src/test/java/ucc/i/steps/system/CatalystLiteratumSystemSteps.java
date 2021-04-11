package ucc.i.steps.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.testng.asserts.SoftAssert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import ucc.i.method.catalystLiteratum.CatalystLiteratumSystemHelper;
import ucc.i.method.catalystLiteratum.CatalystLiteratumSystemPOST;
import ucc.i.method.literatum.LiteratumGET;
import ucc.i.method.literatum.LiteratumPATCH;
import ucc.i.method.literatum.LiteratumPOST;
import ucc.i.method.literatumprocess.CatalystliteratumprocessPOST;
import ucc.utils.JsonUtils;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;

public class CatalystLiteratumSystemSteps {

	public static Response resp = null;
	public static Map<String, String> kmap = new HashMap<String, String>();
	public static JSONObject requestParams = new JSONObject();
	Boolean flag = false;
	static String email_value;
	static String end_pt = null;
	String file_name = "Literatum_identity.json";
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	List<String> get_Vals = new ArrayList<String>();
	List<String> get_ConfVals = new ArrayList<String>();
	List<String> get_ConfKeys = new ArrayList<String>();
	static String ucid, inid;
	static int numOfAssignedLicenses;
	static int noTags;
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
	
	@When("^user sends a POST request with (.*) and (.*) to create an inid for catalyst literatum$")
	public void create_new_inid(String key, String value) throws Exception {
		String file = CatalystLiteratumSystemJsonBodyFile.Catalyst_Create_New_inid;
		CatalystLiteratumSystemHelper.POST_updateFile_create_institution_identity(file, key, value);
		end_pt = catJLSysPOSTSteps.setEndpontIdentitiesInstitution();
		resp = catJLSysPOSTSteps.post(file, end_pt).extract().response();
		tUtil.putToSession("inid", resp.jsonPath().get("inid"));
	}

	@Then("inid should be created with status code 201")
	public void verify_created_institution() {
		tUtil.verifyStatus(resp, ResponseCode.CREATED);
		Assert.assertEquals("INID missing from the response!", tUtil.getFromSession("inid"),
				resp.jsonPath().get("inid").toString());
	}

	@When("I send a GET call to fetch the inid info")
	public void GET_institution_identity() throws InterruptedException {
		String file = CatalystLiteratumSystemJsonBodyFile.Catalyst_Create_New_inid;
		int timeOutMin = 12;
		end_pt = LiteratumGETSteps.setEndpointInstitutionIdentities(tUtil.getFromSession("inid").toString());
		do {
			System.out.println("Waiting for request Catalyst Literatum System to get finished");
			TimeUnit.SECONDS.sleep(5);
			resp = LiteratumGETSteps.get(end_pt).extract().response();
			tUtil.verifyStatus(resp, ResponseCode.OK);
			if ((resp.jsonPath().get("address.address1").toString().equals(
					jsonUtils.getFromJSON(file, "$.address..address1").replaceAll("\\[", "").replaceAll("\\]", ""))
					&& (resp.jsonPath().get("address.address2").toString().equals(jsonUtils
							.getFromJSON(file, "$.address..address2").replaceAll("\\[", "").replaceAll("\\]", ""))))) {
				break;
			}
			timeOutMin--;

		} while (timeOutMin != 0);
	}

	@Then("I should verify fetched institution identity with status code 200")
	public void verify_fetched_institution_identity() {
		String file = CatalystLiteratumSystemJsonBodyFile.Catalyst_Create_New_inid;
		String actAdd1 = jsonUtils.getFromJSON(file, "$.address..address1").replaceAll("\\[", "").replaceAll("\\]", "");
		String actAdd2 = jsonUtils.getFromJSON(file, "$.address..address2").replaceAll("\\[", "").replaceAll("\\]", "");
		softAssert.assertEquals(resp.statusCode(), ResponseCode.OK, "status code is not 200 OK");
		softAssert.assertEquals(resp.jsonPath().get("inid"), tUtil.getFromSession("inid"),
				"INID is not as expected in the JSON response!");
		softAssert.assertEquals(resp.jsonPath().getString("address.address1"), actAdd1,
				"Address1 is not as expected in the JSON response!");
		softAssert.assertEquals(resp.jsonPath().getString("address.address2"), actAdd2,
				"Address2 is not as expected in the JSON response!");
		softAssert.assertAll();

	}

	@When("^I do a PATCH call to update (.*) with a value (.*) in an institution identity$")
	public void PATCH_to_update_institution_identity(String key, String value) throws Exception {
		String file = CatalystLiteratumSystemJsonBodyFile.Catalyst_Create_New_inid;
		CatalystLiteratumSystemHelper.PATCH_updateFile_create_institution_identity(file, key, value);
		end_pt = LiteratumPATCHSteps.setEndpointUpdateInid(tUtil.getFromSession("inid").toString());
		resp = LiteratumPATCHSteps.patch(file, end_pt).extract().response();
	}

	@Then("inid info should be updated with status code 204")
	public void verify_updated_institution_identity() {
		tUtil.verifyStatus(resp, ResponseCode.UPDATED);
	}

	@When("I send a POST request to Create and assign license to a institution identity")
	public void create_assign_institution_license() throws Exception {
		String file = CatalystLiteratumSystemJsonBodyFile.Catalyst_Institution_License;
		CatalystLiteratumSystemHelper.POST_updateFile_create_institution_license(file);
		end_pt = LiteratumPOSTSteps.setEndpointLicenses();
		resp = LiteratumPOSTSteps.postInstitutionLicense(file, end_pt).extract().response();
	}

	@Then("License should be created and assigned to a institution identity with status code 201")
	public void verify_license_shouldBe_created() {
		tUtil.verifyStatus(resp, ResponseCode.CREATED);
	}

	@When("I send a GET request to fetch licenses assigned to an Institution identity")
	public void fetch_institution_license() {
		end_pt = LiteratumGETSteps.setEndpointInstitutionLicense(tUtil.getFromSession("inid").toString());
		resp = LiteratumGETSteps.get(end_pt).extract().response();
	}

	@Then("I should verify the assigned license to an Institution identity with status code 200")
	public void verify_fetched_institution_license() {
		String actUniqCode = resp.getBody().jsonPath().getString("code[0]");
		softAssert.assertEquals(resp.statusCode(), ResponseCode.OK, "Unexpected Status Code!");
		softAssert.assertEquals(actUniqCode, tUtil.getFromSession("uniqCode"), "Unexpected Unique Code returned!");
		softAssert.assertEquals(resp.jsonPath().getString("offerCode[0]"), "catalyst-individual",
				"Unexpected offer code returned");

		softAssert.assertAll();
	}

	@When("I send a PATCH request to update the assigned institution identity license")
	public void update_institution_license() throws Exception {
		String file = CatalystLiteratumSystemJsonBodyFile.Catalyst_Institution_License;
		CatalystLiteratumSystemHelper.PATCH_updateFile_create_institution_license(file);
		end_pt = LiteratumPATCHSteps.setEndpointLicenses();

		resp = LiteratumPATCHSteps.patchInstitutionLicense(file, end_pt).extract().response();
	}

	@Then("I should verify that institution license should be updated with status code 204")
	public void verify_institution_license_shouldBe_updated() {
		tUtil.verifyStatus(resp, ResponseCode.UPDATED);
	}

	@When("I send a GET request to fetch the updated licenses assigned to an Institution identity")
	public void fetch_updated_inst_license() throws InterruptedException {
		String file = CatalystLiteratumSystemJsonBodyFile.Catalyst_Institution_License;
		int timeOutMin = 12;
		end_pt = LiteratumGETSteps.setEndpointInstitutionLicense(tUtil.getFromSession("inid").toString());
		do {
			System.out.println("Waiting for request Catalyst Literatum System to get finished");
			TimeUnit.SECONDS.sleep(5);
			resp = LiteratumGETSteps.get(end_pt).extract().response();
			tUtil.verifyStatus(resp, ResponseCode.OK);
			if ((resp.jsonPath().getString("orderNumber[0]").equals(jsonUtils.getFromJSON(file, "orderNumber")))) {
				break;
			}
			timeOutMin--;

		} while (timeOutMin != 0);
	}

	@Then("I should verify the assigned license to an Institution identity should be updated with status code 200")
	public void verify_fetched_updated_inst_license_response() {
		String file = CatalystLiteratumSystemJsonBodyFile.Catalyst_Institution_License;
		softAssert.assertEquals(resp.statusCode(), ResponseCode.OK, "Invalid staus code returned!");
		softAssert.assertEquals(resp.jsonPath().getString("orderNumber[0]"), jsonUtils.getFromJSON(file, "orderNumber"),
				"Order Number is not Updated by PATCH call");
		softAssert.assertEquals(resp.jsonPath().getString("startDate[0]"),
				jsonUtils.getFromJSON(file, "startDate").substring(0, 10), "Start Date is not Updated by PATCH call");
		softAssert.assertEquals(resp.jsonPath().getString("endDate[0]"),
				jsonUtils.getFromJSON(file, "endDate").substring(0, 10), "End Date is not Updated by PATCH call");
		softAssert.assertEquals(resp.jsonPath().getString("expDate[0]"),
				jsonUtils.getFromJSON(file, "expDate").substring(0, 10), "Expiry Date is not Updated by PATCH call");

		softAssert.assertAll();
	}
}
