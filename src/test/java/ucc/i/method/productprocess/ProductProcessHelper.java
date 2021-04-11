package ucc.i.method.productprocess;

import static org.junit.Assert.assertTrue;
import io.restassured.response.Response;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;


public class ProductProcessHelper {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String file_path = env_var.getProperty("json.body.path");
	private JsonUtils jsonUtils = new JsonUtils();
	
	
	
	public void updateJsonValues(String fileName, String term, Double price, String domestic, String promo) 
			throws Exception {
		
		jsonUtils.update_JSONValue(fileName, "price", price);
		jsonUtils.update_JSONValue(fileName, "term", term);
		jsonUtils.update_JSONValue(fileName, "domestic", domestic);
		jsonUtils.update_JSONValue(fileName, "promo", promo);
	}
	
	public void verifyJson(Response resp, String productCode) throws JSONException, JsonParseException,
	JsonMappingException, IOException {

		String path;
		path = file_path + "/" + "ProductProcess" + "/" + productCode + ".json";
		
		jsonUtils.verifyJSONObject(path, resp);
	}
}
