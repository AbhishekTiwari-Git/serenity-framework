package ucc.i.method.catalystLiteratum;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

public class CatalystLiteratumSystemHelper {

	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
	private static String autoEmail = EnvironmentSpecificConfiguration.from(envVar).getProperty("autoEmail");
	private static TestUtils tUtil = new TestUtils();
	private static JsonUtils jsonUtils = new JsonUtils();
	private static String email = null;
	private static String ucid = null;
	private static String firstName = null;
	private static String lastName = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(CatalystLiteratumSystemHelper.class);

	public static void POST_updateFile_create_person_identity(String file) throws Exception {
		ucid = tUtil.generateRandomUcid(36);
		email = tUtil.AppendTimestamp(autoEmail);
		firstName = tUtil.AppendTimestamp("fname");
		lastName = tUtil.AppendTimestamp("lname");
		tUtil.putToSession("ucid", ucid);
		tUtil.putToSession("email", email);
		tUtil.putToSession("firstName", firstName);
		tUtil.putToSession("lastName", lastName);
        jsonUtils.update_JSONValue(file, "ucid", ucid);
        jsonUtils.update_JSONValue(file, "email", email);
        jsonUtils.update_JSONValue(file, "firstName", firstName);
		jsonUtils.update_JSONValue(file, "lastName", lastName);
	}
	
	public static void PATCH_updateFile_create_person_identity(String file) throws Exception {
		email = tUtil.AppendTimestamp(autoEmail);
		firstName = tUtil.AppendTimestamp("fname");
		lastName = tUtil.AppendTimestamp("lname");
		tUtil.putToSession("email", email);
		tUtil.putToSession("firstName", firstName);
		tUtil.putToSession("lastName", lastName);
		jsonUtils.update_JSONValue(file, "email", email);
		jsonUtils.update_JSONValue(file, "firstName", firstName);
		jsonUtils.update_JSONValue(file, "lastName", lastName);
	}
	
	public static void POST_updateFile_create_institution_identity(String file, String key, String value) throws Exception {
		String inid = tUtil.generateRandomInid();
		
		switch(key) {
		case "address1":
			String address1 = tUtil.AppendTimestamp(value);
			jsonUtils.update_JSONValue(file, "$.address..address1", address1);
			
			break;
			
		case "address2":
			String address2 = tUtil.AppendTimestamp(value);
			jsonUtils.update_JSONValue(file, "$.address..address2", address2);
			
			break;
			
		default :
			LOGGER.info("Invalid key passed"+key);
		}
		jsonUtils.update_JSONValue(file, "inid", inid);
	}
	
	public static void PATCH_updateFile_create_institution_identity(String file, String key, String value) throws Exception {
		switch(key) {
		case "address1":
			String address1 = tUtil.AppendTimestamp(value);
			jsonUtils.update_JSONValue(file, "$.address..address1", address1);
			
			break;
			
		case "address2":
			String address2 = tUtil.AppendTimestamp(value);
			jsonUtils.update_JSONValue(file, "$.address..address2", address2);
			
			break;
			
		default :
			LOGGER.info("Invalid key passed"+key);
		}
		
	}
	
	public static void POST_updateFile_create_institution_license(String file) throws Exception {
		String uniqCode = tUtil.AppendTimestamp("-catalyst-individual");
		jsonUtils.update_JSONValue(file, "$.code", uniqCode);
		jsonUtils.update_JSONValue(file, "$.inid", tUtil.getFromSession("inid").toString());
		tUtil.putToSession("uniqCode", uniqCode);
	}
	
	public static void PATCH_updateFile_create_institution_license(String file) throws Exception {
		LocalDateTime startDt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String startDate = startDt.format(formatter);
        LocalDateTime endDate = startDt.plusMonths(2);
        String endDt = endDate.format(formatter);
		String orderNumber = tUtil.AppendTimestamp("-orderNumber");
		jsonUtils.update_JSONValue(file, "orderNumber", orderNumber);
		jsonUtils.update_JSONValue(file, "startDate", startDate);
		jsonUtils.update_JSONValue(file, "endDate", endDt);
		jsonUtils.update_JSONValue(file, "expDate", endDt);
	}
}
