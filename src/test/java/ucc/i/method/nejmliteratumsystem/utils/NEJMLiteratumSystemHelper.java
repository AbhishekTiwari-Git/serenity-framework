package ucc.i.method.nejmliteratumsystem.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.i.method.catalystLiteratum.CatalystLiteratumSystemHelper;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

public class NEJMLiteratumSystemHelper {

	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
	private static String autoEmail = EnvironmentSpecificConfiguration.from(envVar).getProperty("autoEmail");
	private static TestUtils tUtil = new TestUtils();
	private static JsonUtils jsonUtils = new JsonUtils();
	private static String email = null;
	private static String ucid = null;
	private static String firstName = null;
	private static String lastName = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(NEJMLiteratumSystemHelper.class);

	public static Map<String, String> updateFile_create_PersonID_random_ucid(String file) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		email = tUtil.AppendTimestamp(autoEmail);
		ucid = tUtil.generateRandomUcid(36);
		firstName = tUtil.AppendTimestamp("firstName");
		lastName = tUtil.AppendTimestamp("lastName");

		jsonUtils.update_JSONValue(file, "$.email", email);
		jsonUtils.update_JSONValue(file, "$.ucid", ucid);
		jsonUtils.update_JSONValue(file, "$.firstName", firstName);
		jsonUtils.update_JSONValue(file, "$.lastName", lastName);
		
		map.put("email", email);
		map.put("ucid", ucid);
		map.put("firstName", firstName);
		map.put("lastName", lastName);
		
		return map;
	}
	
	public static void updateFile_create_prsnIdentity(String file) throws Exception {
		email = tUtil.AppendTimestamp(autoEmail);
		ucid = tUtil.generateRandomUcid(36);
		firstName = tUtil.AppendTimestamp("firstName");
		lastName = tUtil.AppendTimestamp("lastName");

		jsonUtils.update_JSONValue(file, "$.email", email);
		jsonUtils.update_JSONValue(file, "$.ucid", ucid);
		jsonUtils.update_JSONValue(file, "$.firstName", firstName);
		jsonUtils.update_JSONValue(file, "$.lastName", lastName);
		
		tUtil.putToSession("ucid", ucid);
		
	}
	
	public static void POST_updateFile_create_inid_nejm(String file, String key, String value) throws Exception {
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
	
	public static void PATCH_updateFile_create_inid_nejm(String file, String key, String value) throws Exception {
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
	
	public static void POST_updateFile_create_inst_license_nejm(String file) throws Exception {
		String uniqCode = tUtil.AppendTimestamp("-onn-nejm-institution");
		jsonUtils.update_JSONValue(file, "$.code", uniqCode);
		jsonUtils.update_JSONValue(file, "$.inid", tUtil.getFromSession("inid").toString());
	}
	
	public static void PATCH_updateFile_create_inst_license_nejm(String file) throws Exception {
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
	
	public static void POST_updateFile_create_person_license_nejm(String file) throws Exception {
		String uniqCode = tUtil.generateRandomInid();
		jsonUtils.update_JSONValue(file, "$.code", uniqCode);
		jsonUtils.update_JSONValue(file, "$.orderNumber", uniqCode);
		jsonUtils.update_JSONValue(file, "$.ucid", tUtil.getFromSession("ucid").toString());
		jsonUtils.update_JSONValue(file, "$.orderNumber", uniqCode);
	}
	
	public static void PATCH_updateFile_create_person_license_nejm(String file) throws Exception {
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
	
	public static void POST_updateFile_create_person_identity_nejm(String file) throws Exception {
		jsonUtils.update_JSONValue(file, "$.ucid", tUtil.generateRandomUcid(36));
		jsonUtils.update_JSONValue(file, "$.email", tUtil.AppendTimestamp(autoEmail));
		jsonUtils.update_JSONValue(file, "$.firstName", tUtil.AppendTimestamp("firstName"));
		jsonUtils.update_JSONValue(file, "$.lastName", tUtil.AppendTimestamp("lastName"));
		jsonUtils.update_JSONValue(file, "$.related-identity..id-value", tUtil.getFromSession("inid").toString());
	}
	
	public static void PATCH_updateFile_create_person_identity_nejm(String file) throws Exception {
		jsonUtils.update_JSONValue(file, "$.firstName", "Appending"+tUtil.AppendTimestamp("firstName"));
		jsonUtils.update_JSONValue(file, "$.lastName", "Appending"+tUtil.AppendTimestamp("lastName"));
	}

}
