package ucc.i.method.customerprocess;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

public class CustomerProcessHelper {
	
	static JsonUtils jsonUtils = new JsonUtils();
	static TestUtils tUtil = new TestUtils();

	
	
	public boolean verifyUserHasInvoice(Response resp) {

		boolean flag = false;

		JsonPath jsonPathEvaluator = resp.jsonPath();
		String a = jsonPathEvaluator.get("invoices");

		System.out.println(a);

		if (a.contains("orderStatus")) {
			flag = true;
			System.out.println("invoices has orderStatus and extOrderNumber in the response");
		} else {
			flag = false;
		}
		return flag;
	}
	
	public void update_jsonFile_for_activate(String file) 
    		throws Exception{
    	
    	jsonUtils.update_JSONValue(file, "email", (String) tUtil.getFromSession("email"));
    	jsonUtils.update_JSONValue(file, "lastName", (String) tUtil.getFromSession("lastName"));
    	jsonUtils.update_JSONValue(file, "country", (String) tUtil.getFromSession("countryCode"));
    	jsonUtils.update_JSONValue(file, "postalCode", (String) tUtil.getFromSession("postalCode"));
    	jsonUtils.update_JSONValue(file, "subscriptionId", (String) tUtil.getFromSession("customerNumber"));
    }
}