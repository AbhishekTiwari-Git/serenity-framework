package ucc.i.steps.experience;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import net.thucydides.core.annotations.Steps;
import ucc.i.method.storeadminexp.StoreAdminExpHelper;
import ucc.i.method.storeadminexp.StoreAdminExpPOST;
import ucc.utils.TestUtils;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class StoreAdminExpSteps {
	public static Response expResp = null;
	public static Response procResp = null;
	public static Response sysResp = null;
	static String end_pt = null;
	TestUtils tUtil = new TestUtils();
	public static Map<String, String> kmap = new HashMap<String, String>();
	
	
	
	@Steps
	StoreAdminExpPOST storeAdminExpPOST;
	
	@Steps
	StoreAdminExpHelper storeAdminExpHelper;
	
	
	
	String post = "StoreAdminExp/post.json";
	String postInvalid = "StoreAdminExp/postInvalid.json";
	
	
	
	@When("I do POST call to StoreAdminExp prices for CATJ with invalid term")
	public void i_do_post_call_to_store_admin_exp_prices_for_catj_with_invalid_term() 
			throws URISyntaxException {
		
		end_pt = storeAdminExpPOST.setEndpointToPrices();
    	sysResp = storeAdminExpPOST.postStore(end_pt, postInvalid)
    			.extract().response();
    	
    	tUtil.putToSession("response", sysResp);
	}
	
	@When("^I do POST call to StoreAdminExp prices for term (.*) with price (.*) and domestic (.*) and promo (.*)$")
	public void i_do_post_call_to_store_admin_exp_prices
	(String term, Double price, String domestic, String promo) throws Exception{
	    
	    storeAdminExpHelper.updateJsonValues(post, term, price, domestic, promo);
    	
	    end_pt = storeAdminExpPOST.setEndpointToPrices();
	    expResp = storeAdminExpPOST.postStore(end_pt, post)
	    		.extract().response();
    	
    	tUtil.putToSession("response", expResp);
	}
}
