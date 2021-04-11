package ucc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.testbase.TestBase;

public class JiraConnect {

	EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
	String fName;
	String smeStr;
	RestUtil sRest = new RestUtil();
	public int test_cycle_id;
	public static Boolean apiFlag = false;
	String APILayer;
	int recordCount;
	List<Integer> issue_id;
	Map<String, Integer> mapC = new HashMap<String, Integer>();
	Map<Integer, List<Integer>> mapStep = new HashMap<Integer, List<Integer>>();
	Map<String, Integer> mapA = new HashMap<String, Integer>();
	Map<Integer, List<Integer>> mapStepInfo = new HashMap<Integer, List<Integer>>();
	static List<Integer> rStep_id;
	List<String> rStep_result;
	static List<String> rStep_summary;
	List<String> label;
	List<Integer> sId;
	Boolean Examples = false;
	List<String> issue_key;
	List<String> step_Details;
	List<String> step_Expected;
	List<String> step_Data;
	String test_cycle_name = null;
	static List<String> summary = null;
	List<String> description = null;
	static Response response = null;
	String startPath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "features"
			+ File.separator;
	public static final String FILE_PATH = "src" + File.separator + "test" + File.separator + "resources"
			+ File.separator + "testdata" + File.separator;
	public static String PATH;
	DataFormatter formatter = new DataFormatter(Locale.US);
	String jiraUrl = variables.getProperty("jira1.url");
	XSSFWorkbook myWorkBook;
	public static int cycleId;
	String fileName;
	String cycleName = variables.getProperty("testCycle");
	List<Integer> rId;
	List<String> rSummary;
	public static Map<String, Integer> mapInfos = new HashMap<String, Integer>();
	public static Map<Integer, List<Integer>> mapSteps = new HashMap<Integer, List<Integer>>();
	String directoryName;

	public void getIDetails() {

		System.out.println(TestBase.test_cycle_id);

		RestUtil.setBaseURI(jiraUrl + "zapi/latest/execution");
		Response res = RestAssured.given().spec(ReuseableSpecifications.getGenericJiraRequestSpec())
				.queryParams("cycleId", TestBase.test_cycle_id).get().then().extract().response();

		JsonPath jsonPathEvaluator = res.jsonPath();

		rId = jsonPathEvaluator.getList("executions.id");
		rSummary = jsonPathEvaluator.getList("executions.summary");

		for (int i = 0; i < rId.size(); i++) {
			mapInfos.put(rSummary.get(i).trim(), rId.get(i));

			Response resp = getStepId(rId.get(i)).statusCode(200).log().all().extract().response();

			jsonPathEvaluator = resp.jsonPath();
			sId = jsonPathEvaluator.getList("id");
			mapSteps.put(rId.get(i), sId);
		}
		System.out.println(sId);
		System.out.println(mapSteps.values().toString());

	}

	public void readIssueDetails() {

		System.out.println(TestBase.test_cycle_id);
		test_cycle_id = TestBase.test_cycle_id;
		test_cycle_name = TestBase.test_cycle_name;

		RestUtil.setBaseURI(jiraUrl + "zapi/latest/execution");
		response = RestAssured.given().spec(ReuseableSpecifications.getGenericJiraRequestSpec())
				.queryParams("cycleId", test_cycle_id).get().then().extract().response();

		JsonPath jsonPathEvaluator = response.jsonPath();
		issue_id = response.jsonPath().getList("executions.issueId");
		rStep_id = jsonPathEvaluator.getList("executions.id");
		issue_key = jsonPathEvaluator.getList("executions.issueKey");
		summary = jsonPathEvaluator.getList("executions.summary");
		description = jsonPathEvaluator.getList("executions.issueDescription");
		recordCount = jsonPathEvaluator.getInt("recordsCount");
		label = jsonPathEvaluator.getList("executions.label");
	}

	public ValidatableResponse jiraExecution() {

		RestUtil.setBaseURI(jiraUrl + "zapi/latest/execution");

		return SerenityRest.rest().given().spec(ReuseableSpecifications.getGenericJiraRequestSpec())
				.queryParams("cycleId", test_cycle_id).get().then().log().all();

	}

	public void setDataPath(String dataFileName) {

		String name = StringUtils.substringBetween(dataFileName, "=", "_");

		switch (name) {

		case "ICV":

			PATH = FILE_PATH + name + ".xlsx";
			break;

		case "Kinesys":

			PATH = FILE_PATH + name + ".xlsx";
			break;

		case "ACS":

			PATH = FILE_PATH + name + ".xlsx";
			break;

		case "Literatum":

			PATH = FILE_PATH + name + ".xlsx";
			break;

		case "LiteratumProc":

			PATH = FILE_PATH + name + ".xlsx";
			break;

		case "AIC":

			PATH = FILE_PATH + name + ".xlsx";
			break;

		case "Zendesk":

			PATH = FILE_PATH + name + ".xlsx";
			break;

		case "Magento":

			PATH = FILE_PATH + name + ".xlsx";
			break;

		case "AMC":

			PATH = FILE_PATH + name + ".xlsx";
			break;

		case "AutomationDevelopmentRunCycle":

			PATH = FILE_PATH + name + ".xlsx";
			break;

		case "MyAccountExp":

			PATH = FILE_PATH + name + ".xlsx";
			break;

		case "CustProcAPI":

			PATH = FILE_PATH + name + ".xlsx";
			break;

		case "EventProc":

			PATH = FILE_PATH + name + ".xlsx";
			break;

		case "RegistrationProc":

			PATH = FILE_PATH + name + ".xlsx";
			break;

		case "ACSProc":

			PATH = FILE_PATH + name + ".xlsx";
			break;

		case "CJExp":

			PATH = FILE_PATH + name + ".xlsx";
			break;

		case "Lead":

			PATH = FILE_PATH + name + ".xlsx";
			break;

		case "Footer":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "CJSearch":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "CJSearchFilter":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "EventsCustReg":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "PDFCustReg":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "InsightsCustReg":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "HomePage":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "testExperienceAPI":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "ToolTip":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "CreateAccount":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "SignIn":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "MyAccountPage":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "Connect":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "Session":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "Widget":

			PATH = FILE_PATH + name + ".xlsx";

			break;
			
		case "Saved":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "JoinIC":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "StoreExp":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "CommerceUI":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "GuestOrderUI":

			PATH = FILE_PATH + name + ".xlsx";

			break;
		case "ComPaybill":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "MagentoUI":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "ComPaybillRenew":

			PATH = FILE_PATH + name + ".xlsx";

			break;
		
		case "ComCJRenew":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "ComUIPricing":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "Tax":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "CJArticlePage":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "PanelistProc":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "CJSmoke":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "CJUserAccess":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "CJHeader":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "ReferenceSys":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "NEJMLiteratumSys":

			PATH = FILE_PATH + name + ".xlsx";

			break;
			
		case "NejmLiteratumProc":
			
			PATH = FILE_PATH + name + ".xlsx";

			break;

			
		case "NejmReg":

			PATH = FILE_PATH + name + ".xlsx";

			break;
			
			
		case "NejmGrReg":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "ProductSys":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "ProductProc":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "LookupProc":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "LookupExp":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "StoreAdminExp":

			PATH = FILE_PATH + name + ".xlsx";

			break;

		case "mediacenter":
		    
		    PATH = FILE_PATH + name + ".xlsx";

        break;

		case "CJCookies":

			PATH = FILE_PATH + name + ".xlsx";

			break;
			
		case "CustomerServiceExp":

			PATH = FILE_PATH + name + ".xlsx";
			break;
			
		case "CustomerServiceProc":

			PATH = FILE_PATH + name + ".xlsx";
			break;


		default:

			System.out.println("Please Check DataFile in Jira ticket or respective test data file");

			System.out.println("File Name is  " + name);
			break;
		}

	}

	@SuppressWarnings("unchecked")
	public ValidatableResponse updateExecution(int Id, String status) {

		RestUtil.setBaseURI(jiraUrl + "zapi/latest/execution/" + Id + "/execute");
		JSONObject requestParams = new JSONObject();
		requestParams.put("status", status);

		return SerenityRest.rest().given().spec(ReuseableSpecifications.getGenericJiraRequestSpec()).log().all().when()
				.body(requestParams.toJSONString()).put().then().log().all();

	}

	public void getVersionId() {

		int vId = Integer.parseInt(variables.getProperty("versionId"));

		switch (vId) {

		case 19241:

			String[] DirName = "INTEG".split(",");
			String tc = test_cycle_name;

			directoryName = startPath + DirName[0] + File.separator + tc;
			apiFlag = true;

			break;

		case 19242:

			String[] DirName4 = "COM".split(",");
			String tc4 = test_cycle_name;

			directoryName = startPath + DirName4[0] + File.separator + tc4;
			apiFlag = false;

			break;

		case 19243:

			String[] DirName1 = "CustReg".split(",");
			String tc1 = test_cycle_name;

			directoryName = startPath + DirName1[0] + File.separator + tc1;
			apiFlag = false;

			break;

		case 19670:

			String[] DirName3 = "CJ".split(",");
			String tc3 = test_cycle_name;

			directoryName = startPath + DirName3[0] + File.separator + tc3;
			apiFlag = false;

			break;

		case 20828:

			String[] DirName5 = "NejmUI".split(",");
			String tc5 = test_cycle_name;

			directoryName = startPath + DirName5[0] + File.separator + tc5;
			apiFlag = false;
			break;

		default:

			String[] DirName2 = "INTEG".split(",");
			String tc2 = test_cycle_name;

			directoryName = startPath + DirName2[0] + File.separator + tc2;
			System.out.println("Default INTEG Directory and version ID used");
			apiFlag = true;
			break;

		}

		System.setProperty("versionFlag", apiFlag.toString());

	}

	public ValidatableResponse getStepId(int executionId) {

		RestUtil.setBaseURI(jiraUrl + "zapi/latest/stepResult");

		return RestAssured.given().spec(ReuseableSpecifications.getGenericJiraRequestSpec()).log().all()
				.queryParam("executionId", executionId).get().then().log().all();

	}

	@SuppressWarnings("unchecked")
	public ValidatableResponse updateStepExecution(int Id, String status) {

		RestUtil.setBaseURI(jiraUrl + "zapi/latest/stepResult/" + Id);
		JSONObject requestParams = new JSONObject();
		requestParams.put("status", status);

		return RestAssured.given().spec(ReuseableSpecifications.getGenericJiraRequestSpec()).log().all().when()
				.body(requestParams.toJSONString()).put().then().log().all();

	}

	public void getFeatureDetails() throws IOException {

//	String[] DirName = test_cycle_name.split(",");
//	String directoryName = startPath + DirName[0];

		getVersionId();

		File directory = new File(directoryName);
		if (!directory.exists()) {
			directory.mkdir();
			// If you require it to make the entire directory path including parents,
			// use directory.mkdirs(); here instead.
		}

		File file = new File(directoryName + File.separator + test_cycle_name + ".feature");

		// boolean exists = file.exists();
		if (file.exists() && file.isFile()) {
			file.delete();
			System.out.println("file exists, and its recreated");
		}

		file.getParentFile().mkdirs();
		FileWriter fw = new FileWriter(file, false);
		// create the print writer
		PrintWriter pw = new PrintWriter(fw, true);
		pw.write("Feature: " + test_cycle_name);

		for (int id = 0; id < issue_id.size(); id++) {

			RestUtil.setBaseURI(jiraUrl + "zapi/latest/teststep/" + issue_id.get(id).toString());
			response = RestAssured.given().spec(ReuseableSpecifications.getGenericJiraRequestSpec()).log().all().get()
					.then().log().all().extract().response();

			JsonPath jsonPathEvaluator = response.jsonPath();
			step_Details = jsonPathEvaluator.getList("stepBeanCollection.step");
			step_Expected = jsonPathEvaluator.getList("stepBeanCollection.result");
			step_Data = jsonPathEvaluator.getList("stepBeanCollection.data");

			response = RestAssured.given().spec(ReuseableSpecifications.getGenericJiraRequestSpec()).log().all()
					.get(jiraUrl + "api/latest/issue/" + issue_key.get(id).toString().trim()).then().log().all()
					.extract().response();

			String typeval;

			jsonPathEvaluator = response.jsonPath();
			typeval = jsonPathEvaluator.get("fields.customfield_13040.value");

			int vId = Integer.parseInt(variables.getProperty("versionId"));

			if (apiFlag) {
				APILayer = jsonPathEvaluator.get("fields.customfield_13141.value");

				APILayer = APILayer.replace(" ", "_");

			}

			System.out.println(step_Data.get(0));
			String featureName = summary.get(id).toLowerCase();
			featureName = featureName.replace(" ", "-");

			String[] tags = "Jira".split(",");
			int featureCounter = id;
			featureName = featureName.replaceAll("[\\[\\]]", "");

			if (description.get(id).contains("DataFile")) {
				smeStr = description.get(id);
				String[] parts = smeStr.split("=");
				smeStr = parts[1].replaceAll("\\<[^>]*>", "");
				smeStr = smeStr.trim();
				// create the new file
			} else {
				smeStr = featureName;
			}

			String tag = issue_key.get(id).toString().trim();
			// String[] ik = tag.split("-");

			pw.println();
			pw.println();
			pw.write("@issue:" + tag);
			pw.println();

			pw.write("@Cycle=" + test_cycle_name);

			pw.println();
			if (apiFlag == true) {
				pw.write("@" + APILayer);
				pw.println();
			}

			// pw.write((description.get(id).replaceAll("\\<[^>]*>","")));

			System.out.println(label);

			if (typeval.contains("Scenario Outline")) {
				pw.print("Scenario Outline: " + summary.get(id));

				setDataPath(description.get(id));

			} else {
				pw.print("Scenario: " + summary.get(id));
			}
			pw.println();

			int size = step_Details.size();

			for (int i = 0; i < size; i++) {
				pw.write(step_Details.get(i).trim());
				if (step_Data.get(i) != "") {
					pw.println();
					pw.write(step_Data.get(i).trim());
				}
				if (step_Expected.get(i) != "") {
					pw.println();
					pw.write(step_Expected.get(i).trim());
				}
				pw.println();
			}

			if (typeval.contains("Scenario Outline")) {
				Examples = true;
				pw.println();
				pw.write("Examples:");

				File myFile = new File(PATH);
				FileInputStream fis = new FileInputStream(myFile);
				XSSFWorkbook wrkBook = new XSSFWorkbook(fis);
				XSSFSheet mySheet = wrkBook.getSheet(smeStr);

				// Iterator<Row> rowIterator = mySheet.iterator();
				for (int i = 0; i <= mySheet.getLastRowNum(); i++) {
					Row r = mySheet.getRow(i);
					pw.println();
					pw.write("    |");
					if (r == null) {
						// empty row, skip
						pw.write("|");
					} else {
						for (int j = 0; j < r.getLastCellNum(); j++) {
							Cell c = r.getCell(j);
							if (c == null) {
								pw.write("|");
							} else {
								formatter.formatCellValue(r.getCell(j));
								System.out.println("Excel values are " + formatter.formatCellValue(r.getCell(j)));

								pw.write(formatter.formatCellValue(r.getCell(j)) + "|");
							}
						}
					}
				}
				wrkBook.close();
			} else {
				Examples = false;
			}

			// Flush the output to the file
			pw.flush();
			// Close the Print Writer
			// pw.close();
			// Close the File Writer
			// fw.close();

		}

	}

	public Response getCyclesStatuses(Map cycleMap) {

		RestUtil.setBaseURI(jiraUrl + "zapi/latest/execution");
		return RestAssured.given().spec(ReuseableSpecifications.getGenericJiraRequestSpec())
				.queryParams("projectId", variables.getProperty("projectId"))
				.queryParams("versionId", variables.getProperty("versionId"))
				.queryParam("cycleId", cycleMap.get("test_cycle_id")).get().then().extract().response();
	}

	public HashMap<String, String> getCycleInfo() {

		String fName = System.getProperty("cucumber.filter.tags");
		String[] nPath;
		String cycleName;

		if (fName.contains("=")) {
			nPath = fName.split("=");
			cycleName = nPath[1].trim();

		} else if (fName.contains(":")) {
			nPath = fName.split(":");
			cycleName = nPath[1].trim();
		} else {
			cycleName = fName;
		}

		RestUtil.setBaseURI(jiraUrl + "zapi/latest/cycle");
		response = RestAssured.given().spec(ReuseableSpecifications.getGenericJiraRequestSpec())
				.queryParams("projectId", variables.getProperty("projectId"))
				.queryParam("versionId", variables.getProperty("versionId")).get().then().extract().response();

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
				test_cycle_id = Integer.parseInt(key);
			}
		}

		HashMap<String, String> cycleMap = new HashMap<String, String>() {
			{
				put("test_cycle_name", test_cycle_name);
				put("test_cycle_id", String.valueOf(test_cycle_id));
			}
		};
		return cycleMap;
	}
}