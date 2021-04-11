package ucc.testbase;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.cucumber.suiteslicing.SerenityTags;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import ucc.i.steps.JiraSteps;
import ucc.pages.ui.CommonFunc;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class TestBase {

	public static boolean tagName = false;
	static EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
	static Response response = null;
	static String jiraUrl = variables.getProperty("jira1.url");
	public static int test_cycle_id = 0;
	public static String test_cycle_name = null;
	public static String version_name = null;
	static TestUtils tUtils = new TestUtils();
	public CommonFunc comFun = new CommonFunc();
	public static HashMap<String, String> cycleMap = new HashMap<String, String>();

	

	@BeforeClass
	public static void setUp() {
	    
	}

	@Before
	public void before(Scenario scenario) throws FileNotFoundException {

		// for IntellJ IDEA
		// OnStage.setTheStage(new OnlineCast());

		SerenityTags.create().tagScenarioWithBatchingInfo();
		String sName = scenario.getName();

		System.out.println("----------------------------------");
		System.out.println("**********************************");
		System.out.println("Current Scenario for execution: " + sName);
		System.out.println("**********************************");
		System.out.println("----------------------------------");
	}

	public static void jiraInit() {

		if (System.getProperty("cucumber.filter.tags") != null) {
			String fName = System.getProperty("cucumber.filter.tags");
			String[] nPath;
			String cycleName;

			boolean cycleFlag = false;
			tagName = false;

			if (fName.contains("=")) {
				nPath = fName.split("=");
				cycleName = nPath[1].trim();
				cycleFlag = true;
				tagName = true;
			} else if (fName.contains(":")) {
				nPath = fName.split(":");
				cycleName = nPath[1].trim();
				cycleFlag = false;
				tagName = false;
			} else {
				cycleName = fName;
				cycleFlag = false;
				tagName = false;
			}

			if (cycleFlag) {

				RestUtil.setBaseURI(jiraUrl + "zapi/latest/cycle");
				response = RestAssured.given().spec(ReuseableSpecifications.getGenericJiraRequestSpec())
						.queryParams("projectId", variables.getProperty("projectId"))
						.queryParam("versionId", variables.getProperty("versionId")).get().then().log().all().extract()
						.response();

				JsonPath jsonPathEvaluator = response.jsonPath();
				

				/*
				 * Fetching the valid cycleIDs from the JSON response and storing them in a Set
				 */

				String responseBody = response.getBody().asString();
				org.json.JSONObject jObj = new org.json.JSONObject(responseBody);

				Set<String> keys = jObj.keySet();
				keys.remove("-1");
				keys.remove("recordsCount");

				/* Storing the required cycleID through the selected test cycle */

				for (String key : keys) {
					if (cycleName.equalsIgnoreCase(jsonPathEvaluator.getString(key + ".name"))) {
						test_cycle_name = jsonPathEvaluator.getString(key + ".name");
						version_name = jsonPathEvaluator.getString(key + ".versionName");
						test_cycle_id = Integer.parseInt(key);
					}
				}
				System.out.println("----------------------------------");
				System.out.println("TEST CYCLE SELECTED FOR EXECUTION: " + test_cycle_name);
				System.out.println("TEST CYCLE ID: " + test_cycle_id);
				System.out.println("----------------------------------");

				System.setProperty("cycleId", String.valueOf(test_cycle_id));
				System.setProperty("report.customfields.Release", String.valueOf(version_name));
				

			} else {

				System.setProperty("execution.mode", "local");

				System.out.println("************Local execution**************");
				tagName = false;
				System.setProperty("serenity.skip.jira.updates", "true");
				System.setProperty("serenity.jira.zephyr.active", "false");

			}

		} 
		
	}

	@After
	public void afterScenario(Scenario scenario) throws IOException {

	  
	    System.out.println("----------------------------------");
        System.out.println("**********************************");
        System.out.println("After Scenario for execution status: " + scenario.getStatus());
        System.out.println("**********************************");
        System.out.println("----------------------------------");
        
        comFun.driverQuit();
        
	    	
	}
	

	@AfterClass
	public static void tearDown(){

	}

	
	public static void main(String[] args) throws Throwable {

	
		TestUtils.createExcelFile("OrderDetails.xlsx");
		JiraSteps jStep = new JiraSteps();
		jiraInit();
		if (tagName) {

			jStep.verify_site();

		} else {
			System.out.println("----------------------------------");
			System.out.println("**********************************");
			System.out.println("Local Execution");
			System.out.println("**********************************");
			System.out.println("----------------------------------");

		}
	}
}
