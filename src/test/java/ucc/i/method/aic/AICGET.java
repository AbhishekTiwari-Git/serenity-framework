package ucc.i.method.aic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class AICGET {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("api.base.url");
	static String serviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("aic.basePath");

	public static String AIC_url = webserviceEndpoint + serviceEndpoint + "/api";
	public static Response aicResponse = null;
	static String end_pt = null;

	String Ref_url;
	TestUtils tUtil = new TestUtils();
	String file_path = env_var.getProperty("json.body.path");
	RestUtil AIC = new RestUtil(AIC_url);
	Boolean flag = false;
	private static final Logger LOGGER = LoggerFactory.getLogger(AICGET.class);

	public ValidatableResponse getUserFromAkamai(String endpoint) {

		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.get(AIC_url + endpoint)
				.then()
				.log().all();

	}

	public String getField(Response resp, String field) {

		return resp.jsonPath().getString(field);
	}

	public ValidatableResponse getChanges(String from, String to) {

		String endpoint;

		if (to != null) {
			endpoint = "/users/filter/change-identities?from=" + from + "&to=" + to;
		} else {
			endpoint = "/users/filter/change-identities?from=" + from;
		}

		return SerenityRest.rest().given().spec(ReuseableSpecifications.getGenericExpRequestSpec()).when().log().all()
				.get(endpoint).then().log().all();

	}

	public ValidatableResponse getFields(String field) {

		String endpoint;

		String env = System.getProperty("environment").toString();

		switch (env) {
		case "dev":

			Ref_url = "https://api.internal.nejm-dev.org/sys-api-reference-v1/api";

			break;

		case "qa":

			Ref_url = "https://api.internal.nejm-qa.org/sys-api-reference-v1/api";

			break;

		default:

			System.out.println("Please pass valid env");
			Ref_url = "https://api.internal.nejm-dev.org/sys-api-reference-v1/api";
			break;
		}

		if (field != null) {
			endpoint = Ref_url + "/fields/" + field;
		} else {
			endpoint = Ref_url + "/fields";
		}

		return SerenityRest.rest().given().spec(ReuseableSpecifications.getGenericExpRequestSpec()).when().log().all()
				.get(endpoint).then().log().all();

	}

	public void verifyUcidAndAudienceType(Map<String, String> mapV, Response resp) {

		JsonPath jsonPathEvaluator = resp.jsonPath();
		String u = jsonPathEvaluator.getString("uuid");
		String a = jsonPathEvaluator.getString("audienceType");

		List<String> list = new ArrayList<String>();

		for (Entry<String, String> ent : mapV.entrySet()) {
			if (ent.getKey().isEmpty()) {
				System.out.println("Empty map");
			} else {

				System.out.println(ent.getKey() + ": " + ent.getValue());
				list.add(ent.getValue());

			}
		}

		assertEquals("The uuid should be " + mapV.get("uuid") + " but found " + u, mapV.get("uuid"), u);
		assertEquals("The audienceType should be " + mapV.get("audienceType") + " but found " + a,
				mapV.get("audienceType"), a);

		System.out.println("Successfully matched created uuid and AudienceType...!!");
	}

	public void verifyAudienceType(Response resp) {

		JsonPath jsonPathEvaluator = resp.jsonPath();
		checkAudenceType(jsonPathEvaluator);
		checkAlternateId(jsonPathEvaluator);
		System.out.println("Successfully validated ICV, ACS in alternateId of Akamai...!!");

	}
	
	public boolean verifyCJAudienceType(Response resp) {
        boolean flag = false;
        JsonPath jsonPathEvaluator = resp.jsonPath();
        List<String> a = jsonPathEvaluator.getList("alternateID.IDType");
        System.out.println(a);
        boolean atype = jsonPathEvaluator.getString("audienceType").equals("REGISTERED USER");
        boolean catsubsrp = jsonPathEvaluator.getString("Catalyst.subscriptionStatus").equals("SUBSCRIBER");
        List<String> expected = new ArrayList<String>();
        expected.add("ICV");
        expected.add("ACS");
        if (a.containsAll(expected)) {
            flag = true;
			System.out.println("Successfully validated ICV, ACS in alternateId of Akamai...!!");
        }

        return atype && catsubsrp && flag ;
    }

	public void verifyAudienceTypeRegisteredUser(Response resp) {
        //This function only validates if the user who registered before placing order is present in Akamai or not. 
		JsonPath jsonPathEvaluator = resp.jsonPath();
		checkAudenceType_registered_user(jsonPathEvaluator);		

	}

	private void checkAlternateId(JsonPath jsonPathEvaluator) {
		//		assertThat(a, containsInAnyOrder("ICV", "ACS","ACS"));
		List<String> a = jsonPathEvaluator.getList("alternateID.IDType");
		assertTrue(a.contains("ICV"));
		assertTrue(a.contains("ACS"));
	}

	private void checkAudenceType(JsonPath jsonPathEvaluator) {
		assertEquals(jsonPathEvaluator.getString("audienceType"), "REGISTERED USER");
		assertEquals(jsonPathEvaluator.getString("Catalyst.subscriptionStatus"), "SUBSCRIBER");
	}
	
	private void checkAudenceType_registered_user(JsonPath jsonPathEvaluator) {
		assertEquals(jsonPathEvaluator.getString("audienceType"), "REGISTERED USER");
		
	}

	public void Verify_AlternateID_Sys_name_Anonymous(Response resp) {

		JsonPath jsonPathEvaluator = resp.jsonPath();
		List<String> a = jsonPathEvaluator.getList("alternateID.IDType");		
		try {
		     a.remove(a.indexOf("MAGENTO"));
		    }
		catch (Exception e)
		   {
			LOGGER.info("Magento not found in the alternate id of Akamai");
		   }

		assertThat(a, containsInAnyOrder("ICV", "ACS"));

		LOGGER.info("Successfully validated ICV, ACS in alternateId of Akamai...!!");
	}
	
	public void verifyAudienceType_for_ICV_ACS(Response resp) {

		JsonPath jsonPathEvaluator = resp.jsonPath();
		List<String> a = jsonPathEvaluator.getList("alternateID.IDType");	

		assertThat(a, containsInAnyOrder("ICV","ACS"));

		System.out.println("Successfully validated ICV and ACS in alternateId of Akamai...!!");
	}

	public void verifyRegisterCJAudienceType(Response resp) {

		JsonPath jsonPathEvaluator = resp.jsonPath();
		List<String> a = jsonPathEvaluator.getList("alternateID.IDType");

		System.out.println(a);

		assertThat(a, containsInAnyOrder("MAGENTO"));

		System.out.println("Successfully validated Magento in alternateId of Akamai...!!");
	}

	public String getUUID(Response resp) {
		JsonPath jsonPathEvaluator = resp.jsonPath();
		String id = jsonPathEvaluator.getString("uuid");
		return id;
	}

	public String setEndpointUserID(String uuid) {
		String endpoint = "/users/" + uuid;
		return endpoint;
	}

	public String setEndPointEmail(String email) {
		String endpoint = "/users/filter/email/" + email;
		return endpoint;
	}

	public String setEndPoint() {

		String endpoint = "/users";
		return endpoint;
	}

	public String setEndpointAccessToken(String ucid) {
		String endpoint = "/authentication/token/" + ucid;
		return endpoint;
	}

	public String setEndpointFromTo(String from, String to) {
		String endpoint = "/users/filter/change-identities?from=" + from + "&to=" + to;
		return endpoint;
	}
	
	public String setEndpointFrom(String from) {
		String endpoint = "/users/filter/change-identities?from=" + from;
		return endpoint;
	}

	public ValidatableResponse get(String endpoint) {

		return SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.get(AIC_url + endpoint)
				.then()
				.log().all();

	}

	public ValidatableResponse getChanges663(String from, String to) {

		String endpoint;

		String URL = "http://api.internal.nejm-dev.org/sys-api-aic-v1-663/api";
		endpoint = "/users/filter/change-identities?from=" + from + "&to=" + to;

		return SerenityRest.rest().given().spec(ReuseableSpecifications.getGenericExpRequestSpec()).when().log().all()
				.get(URL + endpoint).then().log().all();

	}

	public ValidatableResponse getChangeIdentities(String from, String to, String endpoint) {

		return SerenityRest.rest().given().spec(ReuseableSpecifications.getGenericExpRequestSpec()).when().log().all()
				.get(AIC_url + endpoint).then().log().all();

	}

	public List<String> verifyComAudienceType(Response Respcom) {

		JsonPath jsonPathEvaluator = Respcom.jsonPath();
		List<String> a = jsonPathEvaluator.getList("alternateID.IDType");
		return a;

	}

	public void verifyIdentities(Set<Map<String, String>> oset, Response resp) {

		List<String> uuid = new ArrayList<String>();
		List<String> email = new ArrayList<String>();

		JsonPath jsonpathEvaluator = resp.jsonPath();

		uuid = jsonpathEvaluator.getList("uuid");

		System.out.println(uuid);

		try {

			FileInputStream excelFile = new FileInputStream(new File("ExcelFile.xlsx"));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();

			while (iterator.hasNext()) {

				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				while (cellIterator.hasNext()) {

					Cell currentCell = cellIterator.next();
					// getCellTypeEnum shown as deprecated for version 3.15
					// getCellTypeEnum ill be renamed to getCellType starting from version 4.0
					if (currentCell.getCellType() == CellType.STRING) {
						System.out.print(currentCell.getStringCellValue() + "--");
						if (uuid.contains(currentCell.getStringCellValue())) {
							flag = true;
							assertTrue(flag);
						}
					} else if (currentCell.getCellType() == CellType.NUMERIC) {
						System.out.print(currentCell.getNumericCellValue() + "--");
					}

				}
				System.out.println();

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(flag);
		assertTrue(flag);
	}

	public String setEndPointToSavedSearches(String ucid) {

		String endpoint = "/users/" + ucid + "/saved-items/searches";
		return endpoint;
	}
}
