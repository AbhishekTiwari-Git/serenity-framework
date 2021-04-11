package ucc.i.steps.process;

import static org.junit.Assert.assertEquals;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.i.method.panelistprocess.PanelistProcessGET;
import ucc.i.method.panelistprocess.PanelistProcessPATCH;
import ucc.i.method.panelistprocess.PanelistProcessPOST;

import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

public class PanelistSteps {
	public static Response response = null;
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	public static Map<String, String> kmap = new HashMap<String, String>();
	Boolean flag = false;
	static String alternateId;
	String file_name = "Panelist_Post.json";
	static String end_pt = null;
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String email = EnvironmentSpecificConfiguration.from(env_var).getProperty("autoEmail");
	static String ukv = null;
	static String uvv = null;

	@Steps
	PanelistProcessPOST panelistPOSTSteps;
	
	@Steps
	PanelistProcessGET panelistGETSteps;
	
	@Steps
	PanelistProcessPATCH panelistPATCHSteps;


	@When("^POST request is being sent to create a panelist$")
	public void post_panelists() throws Exception {
		file_name = "Panelist/" + file_name;
		jsonUtils.modify_JSONAppendTimestampfunction(file_name, "alternateId", email);
		jsonUtils.modify_JSONAppendTimestampfunction(file_name, "email", email);
		end_pt = panelistPOSTSteps.setPanelistEndpoint();
		response = panelistPOSTSteps.postPanelists(file_name, end_pt).extract().response();
		kmap = panelistGETSteps.store_PanelistValues(response);
		alternateId = panelistPOSTSteps.getAlternateID(response);

	}

	@Then("^Panelist should be created in Panelist Process API$")
	public void panelist_reated() {
		assertEquals(response.getStatusCode(), 201);

		// verify following fields are present in response
		String[] keys = { "alternateId", "email", "id" };
		List<String> loKeys = Arrays.asList(keys);

		for (String k : loKeys) {
			Assert.assertNotNull(response.jsonPath().get(k));
		}
	}
	
	@When("^I call GET panelist in process api$")
    public void getpanelist_info_using_the_alternateid() throws Throwable {
		end_pt=panelistGETSteps.setEndpoint(alternateId);
		response = panelistGETSteps.getProcPanelist(end_pt).extract().response();
    }
	
	
	@Then("^newly created panelist should exist in the system$")
	public void veryProcPanelist() {
		panelistGETSteps.verifyPanelistValues(response, kmap);
		
	}
	
	@When("^user updates field (.*) with value (.*)$")
	public void user_updatstName(String uk, String uv) throws URISyntaxException {
	   
	    end_pt = panelistPATCHSteps.setalternateIdEndpoint(alternateId);
	    response = panelistPATCHSteps.patchProcPanelist(end_pt, uk, uv)
	    		   .extract().response();
	    ukv = uk;
	    uvv = uv;
	}

	@Then("^panelist should get updated$")
	public void panelist_should_get_updated() {
	   
	    tUtil.verifyStatus(response, 201);
	    Assert.assertNotNull(response.jsonPath().get("alternateId"));
	}

	@Then("^updated values should be returned$")
	public void updated_value_shurned() {
	   
		panelistPATCHSteps.verifyUpdatedValues(response,ukv,uvv);
	}
}