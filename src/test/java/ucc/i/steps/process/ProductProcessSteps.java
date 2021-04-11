package ucc.i.steps.process;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.i.method.productprocess.ProductProcessGET;
import ucc.i.method.productprocess.ProductProcessHelper;
import ucc.i.method.productprocess.ProductProcessPOST;
import ucc.i.method.productsystem.ProductSystemGET;
import ucc.i.steps.process.CustProcSteps;
import ucc.utils.JsonUtils;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;

public class ProductProcessSteps {

	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();

	private TestUtils tUtil = new TestUtils();
	private JsonUtils jsonUtils = new JsonUtils();
	CustProcSteps custProcSteps = new CustProcSteps();
	private static Response procResp = null;
	private static Response sysResp = null;
	private String endPoint = null;
	
	
	
	@Steps
	ProductProcessGET productProcessGET;
	
	@Steps
	ProductSystemGET productSystemGET;
	
	@Steps
	ProductProcessPOST productProcessPOST;
	
	@Steps
	ProductProcessHelper productProcessHelper;
	
	
	
	private final String getDetailsResponseFile = "ProductProcess/getDetailsResponseFile.json";
	private final String getPricesResponseFile = "ProductProcess/getPricesResponseFile.json";
	private final String getAllProducts = "ProductProcess/getAllProducts.json";
	private final String postPricesCATJ = "ProductProcess/postPricesCATJ.json";
	private final String postPricesCATJinvalidTerm = "ProductProcess/postPricesCATJinvalidTerm.json";

	

	@When("^user sends a GET request to ProductProc to retrieve details using productCode (.*) and term (.*)$")
	public void user_sends_a_get_request_to_product_proc_to_retrieve_details_using_productCode_and_term
	(String productCode, String term) {

		HashMap<String, String> params = new HashMap<String, String>(); 
		params.put("productCode", productCode);
		params.put("term", term);

		endPoint = productProcessGET.setEndpointToDetails();
		procResp = productProcessGET.getwithParams(endPoint, params)
			.extract().response();
	
		tUtil.putToSession("response", procResp);
	}
	
	@Then("^I see all details in the process api response (.*)$")
    public void isee200statuscodeandalldetailsfromProcAPi(String response) throws IOException {

		tUtil.verifyStatus(procResp, ResponseCode.OK);
		productProcessHelper.verifyJson(procResp, response);
    }
	
	@When("I do POST call to ProductProc prices for CATJ with invalid term")
	public void i_do_post_call_to_product_proc_prices_for_catj_with_invalid_term() {
		
		endPoint = productProcessPOST.setEndpointToPrices();
		procResp = productProcessPOST.post(postPricesCATJinvalidTerm, endPoint)
    			.extract().response();
    	
    	tUtil.putToSession("response", procResp);
	}

	@When("I do GET call to products endpoint ProductProc for invalid response")
	public void i_do_get_call_to_products_endpoint_product_proc_for_invalid_response() {
	    
		endPoint = productProcessGET.setEndpointToProducts();
    	endPoint = tUtil.AppendTimestamp(endPoint);
    	procResp = productProcessGET.get(endPoint)
    			.extract().response();
    	
    	tUtil.putToSession("response", procResp);
	}
	@When("^I do POST call to ProductProc prices for CATJ term (.*) with price (.*) and domestic (.*) and promo (.*)$")
	public void i_do_POST_call_to_ProductProc_prices_for_CATJ_term_price_domestic_promo
	(String term, Double price, String domestic, String promo) 
    		throws Exception {
		
		productProcessHelper.updateJsonValues(postPricesCATJ, term, price, domestic, promo);
    	
    	endPoint = productProcessPOST.setEndpointToPrices();
    	procResp = productProcessPOST.post(postPricesCATJ, endPoint)
    			.extract().response();
    	
    	tUtil.putToSession("response", procResp);
	}
	
	@When("I do GET call to products endpoint for ProductProc")
	public void i_do_get_call_to_products_endpoint_for_product_proc() {
		
		endPoint = productProcessGET.setEndpointToProducts();
    	procResp = productProcessGET.get(endPoint)
    			.extract().response();
	}

	@Then("I get all products in the response from ProductProc")
	public void i_get_all_products_in_the_response_from_product_proc() throws IOException {

		tUtil.verifyStatus(procResp, ResponseCode.OK);
    	tUtil.verify_json_to_response(procResp, getAllProducts);
	}
	
	@When("^I do GET call to Product Process API prices with ucid and products (.*) country (.*) promo (.*)$")
	public void i_do_get_call_to_productProcessAPI_prices_with_ucid_and_products_country_promo(String products, String country, String promo) {

		HashMap<String, String> params = new HashMap<String, String>(); 
		params.put("products", products);
		params.put("country", country);
		params.put("promo", promo);
		params.put("ucid", (String)tUtil.getFromSession("ucid"));
		
		endPoint = productProcessGET.setEndpointToPrices();
		procResp = productProcessGET.getProductPriceWithParams(endPoint, params, (String)tUtil.getFromSession("accessToken"))
				.extract().response();
		
		tUtil.putToSession("response", procResp);
	}
	
	@When("^I do GET call to Product Process prices with promo (.*) products (.*) professionalCategory (.*) country (.*)$")
	public void i_do_get_call_to_product_process_prices_with_promo_products_professionalCategory_country
	(String promo, String products, String professionalCategory, String country) {

		HashMap<String, String> params = new HashMap<String, String>(); 
		params.put("promo", promo);
		params.put("products", products);
		params.put("professionalCategory", professionalCategory);
		params.put("country", country);
		
		endPoint = productProcessGET.setEndpointToPrices();
		procResp = productProcessGET.getProductPriceWithParams(endPoint, params)
				.extract().response();
		
		tUtil.putToSession("response", procResp);
	}
	
	@When("^I do GET call to Product Process API prices with invalid ucid and products (.*) country (.*) promo (.*)$")
	public void i_do_get_call_to_product_process_prices_with_invalidUCID_promo_products_professionalCategory_country
	(String products, String country, String promo) {

		HashMap<String, String> params = new HashMap<String, String>(); 
		params.put("products", products);
		params.put("country", country);
		params.put("promo", promo);
		params.put("ucid", tUtil.AppendTimestamp((String)tUtil.getFromSession("ucid")));
		
		endPoint = productProcessGET.setEndpointToPrices();
		procResp = productProcessGET.getProductPriceWithParams(endPoint, params, (String)tUtil.getFromSession("accessToken"))
				.extract().response();
		
		tUtil.putToSession("response", procResp);
	}
	
	@When("^user sends a GET request to retrieve prices using promo (.*), products (.*), professionalCategory (.*), country (.*), customerNumber (.*)$")
    public void userSendsGETrequestToRetrievePricesUsingPromoNumberProductsNameProfessionalCategoryCountryCustomerNumber$ 
    (String promo, String products, String professionalCategory, String country, String customerNumber) {

		HashMap<String, String> params = new HashMap<String, String>(); 
		params.put("promo", promo);
		params.put("products", products);
		params.put("professionalCategory", professionalCategory);
		params.put("country", country);
		params.put("customerNumber", customerNumber);
		
        endPoint = productProcessGET.setEndpointToPrices();
        procResp = productProcessGET.getProductPriceWithParams(endPoint, params)
        		.extract().response();
        
        tUtil.putToSession("response", procResp);
    }
	
	@When("^user sends a GET request to retrieve details using sku (.*) and term (.*)$")
    public void userSendsGETrequestToRetrieveDetailsUsingSkuAndTerm (String sku, String term) {

		HashMap<String, String> params = new HashMap<String, String>(); 
		params.put("sku", sku);
		params.put("term", term);
		
        endPoint = productProcessGET.setEndpointToDetails();
        procResp = productProcessGET.getProductPriceWithParams(endPoint, params)
        		.extract().response();
        
        tUtil.putToSession("response", procResp);
    }
	
	@Then("^I see 200 status code and all details in the response body$")
    public void isee200statuscodeandalldetails() throws IOException {

		tUtil.verifyStatus(procResp, ResponseCode.OK);
		tUtil.verify_json_to_response(procResp, getDetailsResponseFile);
    }
	
	@Then("^I see 200 status code and prices in the response body$")
    public void isee200statuscodeandprices() throws IOException {

		tUtil.verifyStatus(procResp, ResponseCode.OK);
		tUtil.verify_json_to_response(procResp, getPricesResponseFile);
    }

	@Then("I see the details about the product prices")
	public void i_see_the_details_about_the_product_prices_jakshdkljb() {
	    
		Assert.assertNotNull(procResp.jsonPath().get("products"));
    	Assert.assertNotNull(procResp.jsonPath().get("products[0].terms[0].price"));
	}
}
