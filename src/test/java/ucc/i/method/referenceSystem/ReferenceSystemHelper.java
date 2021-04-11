package ucc.i.method.referenceSystem;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.TestUtils;

public class ReferenceSystemHelper {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String expJson = null;
	static String file_path = env_var.getProperty("json.body.path");
	private TestUtils tUtil = new TestUtils();
	public static Map<String, String> kmap = new HashMap<String, String>();
	
	
	public void verifyJson(Response resp, String fields_fieldName) throws JSONException, JsonParseException,
			JsonMappingException, IOException {

		String path;

		if (fields_fieldName.equals("fields")) {
			path = file_path + "/AICFieldsValidation/fields.json";
		} 
		else {
			path = file_path + "/" + "AICFieldsValidation" + "/" + fields_fieldName + ".json";
		}

		try {
			verifyJSONObject(path, resp);
		} catch (Exception e) {
			verifyJSONArray(path, resp);
		}
	}

	public static void verifyJSONObject(String path, Response resp) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		File file = new File(path);

		JSONObject root = mapper.readValue(file, JSONObject.class);
		expJson = root.toString();
		System.out.println(root.toString());

		System.out.println(resp.getBody().asString());

		Map<String, Object> m1 = (Map<String, Object>) (mapper.readValue(expJson, Map.class));
		Map<String, Object> m2 = (Map<String, Object>) (mapper.readValue(resp.getBody().asString(), Map.class));
		System.out.println(m1);
		System.out.println(m2);
		System.out.println(m1.equals(m2));

		assertTrue(m1.equals(m2));
	}

	public static void verifyJSONArray(String path, Response resp) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		File file = new File(path);

		JSONArray m1 = mapper.readValue(file, JSONArray.class);
		JSONArray m2 = mapper.readValue(resp.getBody().asString(), JSONArray.class);
		assertTrue(m1.equals(m2));
	}
	
}
