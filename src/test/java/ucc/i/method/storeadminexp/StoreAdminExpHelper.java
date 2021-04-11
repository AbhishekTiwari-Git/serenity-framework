package ucc.i.method.storeadminexp;

import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

public class StoreAdminExpHelper {

	private TestUtils tUtil = new TestUtils();
	private JsonUtils jsonUtils = new JsonUtils();
	
	
	
	public void updateJsonValues(String fileName, String term, Double price, String domestic, String promo) 
			throws Exception {
		
		jsonUtils.update_JSONValue(fileName, "price", price);
		jsonUtils.update_JSONValue(fileName, "term", term);
		jsonUtils.update_JSONValue(fileName, "domestic", domestic);
		jsonUtils.update_JSONValue(fileName, "promo", promo);	
	}
}
