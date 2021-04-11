package ucc.i.steps.system;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;

import ucc.i.method.accountexp.AccountExpGET;
import ucc.i.method.productsystem.ProductSystemGET;
import ucc.i.method.productsystem.ProductSystemHelper;
import ucc.i.method.productsystem.ProductSystemPOST;
import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;
import ucc.utils.CucumberUtils.CucumberUtils;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class ProductSystemSteps {
	
	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    private static Response sysResp = null;
    CommonFunc commonFunc = new CommonFunc();
    private TestUtils tUtil = new TestUtils();
    private JsonUtils jsonUtils = new JsonUtils();
    public static Map<String, String> kmap = new HashMap<String, String>();
    private String email = null;
    private static String ucid = null;
    private static String firstName = null; 
    private static String endPoint = null;
    
    
    
    @Steps
    ProductSystemGET productSystemGET;
    
    @Steps
    ProductSystemPOST productSystemPOST;
    
    @Steps
    ProductSystemHelper productSystemHelper;
    
    
    
    private final String getDetailsResponseFile = "ProductSystem/getDetailsResponseFile.json";
    private final String getPricesResponseFile = "ProductSystem/getPricesResponseFile.json";
    private final String getDetailsNEJMResponseFile = "ProductSystem/getDetailsNEJMResponseFile.json";
    private final String getDetailsCATJmonthlyResponseFile = "ProductSystem/getDetailsCATJmonthlyResponseFile.json";
    private final String getDetailsCATJyearlyResponseFile = "ProductSystem/getDetailsCATJyearlyResponseFile.json";
    private final String getAllProducts = "ProductSystem/getAllProducts.json";
    private final String postPricesCATJ = "ProductSystem/postPricesCATJyearly.json";
    private final String postPricesCATJinvalidTerm = "ProductSystem/postPricesCATJinvalidTerm.json";

    
    
    @When("^I do GET call to products endpoint for invalid response$")
    public void i_do_get_call_to_products_endpoint_for_invalid_response() {
        
    	endPoint = productSystemGET.setEndpointToProducts();
    	endPoint = tUtil.AppendTimestamp(endPoint);
    	sysResp = productSystemGET.get(endPoint)
    			.extract().response();
    	
    	tUtil.putToSession("response", sysResp);
    }

    @When("I do GET call to products endpoint")
    public void i_do_get_call_to_products_endpoint() {
        
    	endPoint = productSystemGET.setEndpointToProducts();
    	sysResp = productSystemGET.get(endPoint)
    			.extract().response();
    }

    @Then("I get all products in the response")
    public void i_get_all_products_in_the_response() throws IOException {

    	tUtil.verifyStatus(sysResp, ResponseCode.OK);
    	tUtil.verify_json_to_response(sysResp, getAllProducts);
    }
    
    @Then("^I see price (.*) in the response$")
    public void i_see_price_in_the_response(String price) {
        
    	tUtil.verifyStatus(sysResp, ResponseCode.OK);
    	Assert.assertEquals(price, sysResp.jsonPath().get("products[0].terms[0].price").toString());
    }
    
    @When("^I do GET call to ProductSys prices for CATJ with promo (.*) products (.*) professionalCategory (.*) "
    		+ "country (.*) and customerNumber (.*)$")
    public void i_do_get_call_to_product_sys_prices_for_catj_term_y_with_price
    (String promo, String products, String professionalCategory, String country, String customerNumber) {
    	
    	HashMap<String, String> params = new HashMap<String, String>(); 
    		params.put("promo", promo);
    		params.put("products", tUtil.encodeValue(products));
    		params.put("professionalCategory", professionalCategory);
    		params.put("country", country);
    		params.put("customerNumber", country);
        
    	endPoint = productSystemGET.setEndpointToPrices();
    	sysResp = productSystemGET.getwithParams(endPoint, params)
    			.extract().response();
    		
    	tUtil.putToSession("response", sysResp);
    }
    
    @When("^I do POST call to ProductSys prices for CATJ term (.*) with price (.*) and domestic (.*) and promo (.*)$")
    public void i_do_post_call_to_product_prices_api_prices_for_catj_yearlyTerm
    (String term, Double price, String domestic, String promo) 
    		throws Exception {
    	
    	jsonUtils.update_JSONValue(postPricesCATJ, "price", price);
    	jsonUtils.update_JSONValue(postPricesCATJ, "term", term);
    	jsonUtils.update_JSONValue(postPricesCATJ, "domestic", domestic);
    	jsonUtils.update_JSONValue(postPricesCATJ, "promo", promo);
    	
    	endPoint = productSystemPOST.setEndpointToPrices();
    	sysResp = productSystemPOST.post(postPricesCATJ, endPoint)
    			.extract().response();
    	
    	tUtil.putToSession("response", sysResp);
    }
    
    @When("I do POST call to ProductSys prices for CATJ with invalid term")
    public void i_do_post_call_to_product_prices_api_prices_for_catj_with_invalid_term() {

    	endPoint = productSystemPOST.setEndpointToPrices();
    	sysResp = productSystemPOST.post(postPricesCATJinvalidTerm, endPoint)
    			.extract().response();
    	
    	tUtil.putToSession("response", sysResp);
    }
    
    @When("^user sends a GET request to ProductSys to retrieve details using term (.*) and invalid productCode (.*)$")
    public void user_sends_a_get_request_to_product_sys_to_retrieve_details_using_term_and_invalid_product_code(String term, String productCode) {
    	
    	HashMap<String, String> params = new HashMap<String, String>(); 
			params.put("productCode", productCode);
			params.put("term", term);
	
		endPoint = productSystemGET.setEndpointToDetails();
		sysResp = productSystemGET.getwithParams(endPoint, params)
				.extract().response();
		
		tUtil.putToSession("response", sysResp);
    }

    @When("^user sends a GET request to ProductSys to retrieve details using productCode (.*) and invalid term (.*)$")
    public void user_sends_a_get_request_to_product_sys_to_retrieve_details_using_product_code_and_invalid_term
    (String productCode, String term) {
    	
    	HashMap<String, String> params = new HashMap<String, String>(); 
			params.put("productCode", productCode);
			params.put("term", term);

		endPoint = productSystemGET.setEndpointToDetails();
		sysResp = productSystemGET.getwithParams(endPoint, params)
				.extract().response();
		
		tUtil.putToSession("response", sysResp);
    }
    
    @When("^user sends a GET request to ProductSys to retrieve details using productCode (.*) and term (.*)$")
    public void user_sends_a_get_request_to_product_sys_to_retrieve_details_using_product_code_product_code_and_term(String productCode, String term) {
    	
    	HashMap<String, String> params = new HashMap<String, String>(); 
			params.put("productCode", productCode);
			params.put("term", term);
    	
    	endPoint = productSystemGET.setEndpointToDetails();
        sysResp = productSystemGET.getwithParams(endPoint, params)
        		.extract().response();
        
        tUtil.putToSession("response", sysResp);
	}
    
    @When("^user sends a GET request to ProductSys to retrieve details without sku and term$")
    public void userSendsGETrequestToProductSysToRetrieveDetailsWithoutSkuAndTerm() {
    	
        endPoint = productSystemGET.setEndpointToDetails();
        sysResp = productSystemGET.get(endPoint)
        		.extract().response();
        
        tUtil.putToSession("response", sysResp);
    }
    
	@When("^user sends a GET request to ProductSys retrieve to prices using promoNumber (.*), productsName (.*), professionalCategory (.*), country (.*), customerNumber (.*)$")
    public void userSendsGETrequestToPricesUsingPromoNumberProductsNameProfessionalCategoryCountryCustomerNumber(String promo, String products, 
    		String professionalCategory, String country, String customerNumber) {

		HashMap<String, String> params = new HashMap<String, String>(); 
			params.put("promo", promo);
			params.put("products", products);
			params.put("professionalCategory", professionalCategory);
			params.put("country", country);
			params.put("customerNumber", customerNumber);
		
        endPoint = productSystemGET.setEndpointToPrices();
        sysResp = productSystemGET.getwithParams(endPoint, params)
        		.extract().response();
        
        tUtil.putToSession("response", sysResp);
    }
	
	@When("^user sends a GET request to ProductSys to retrieve details using sku (.*) and term (.*)$")
    public void userSendsGETrequestToTetrieveDetailsUsingSkuAndTerm(String sku, String term) {

		HashMap<String, String> params = new HashMap<String, String>(); 
			params.put("sku", sku);
			params.put("term", term);
		
        endPoint = productSystemGET.setEndpointToDetails();
        sysResp = productSystemGET.getwithParams(endPoint, params)
        		.extract().response();
        
        tUtil.putToSession("response", sysResp);
    }
	
	@Then("I call system product prices")
    public void iCallSystemProductPrices(DataTable dataTable) {
        Map<String, String> row = CucumberUtils.convert(dataTable);
        String customerNumber = (String) tUtil.getFromSession("customerNumber");

        String cutCustNum = customerNumber.substring(customerNumber.length() - 9);
        endPoint = productSystemGET.setEndpointToPrices();
        sysResp = productSystemGET.getPrice(
        		endPoint, row.get("promo"), tUtil.encodeValue(row.get("products")),
                row.get("professionalCategory"), row.get("country"), cutCustNum
        ).extract().response();
    }

    @Then("I check product sys and procedure responses are equals")
    public void iCheckProductSysAndProcedureResponsesAreEquals() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode prodecureJson = mapper.readTree((String) tUtil.getFromSession("productPrices"));
        JsonNode httResponseJson = mapper.readTree(sysResp.getBody().asString());

        TestUtils.NumericNodeComparator cmp = new TestUtils.NumericNodeComparator();
        Assert.assertTrue(prodecureJson.equals(cmp, httResponseJson));
    }
	
	@Then("^I see status code as 200 and all prices in the response$")
    public void isee200statuscodeandallprices() throws IOException {

		tUtil.verifyStatus(sysResp, ResponseCode.OK);
		tUtil.verify_json_to_response(sysResp, getPricesResponseFile);
    }
	
	@Then("^I see status code as 200 and all details in the response$")
    public void isee200statuscodeandalldetails() throws IOException {

		tUtil.verifyStatus(sysResp, ResponseCode.OK);
		tUtil.verify_json_to_response(sysResp, getDetailsResponseFile);
    }
	
	@Then("I see status code as 200 and details in the response for NEJ yearly")
	public void i_see_status_code_as_and_details_in_the_response() throws IOException {
		tUtil.verifyStatus(sysResp, ResponseCode.OK);
		tUtil.verify_json_to_response(sysResp, getDetailsNEJMResponseFile);
	}
	
	@Then("I see status code as 200 and details in the response for CATJ monthly")
	public void i_see_status_code_as_and_details_in_the_response_for_catj_monthly() throws IOException {
		tUtil.verifyStatus(sysResp, ResponseCode.OK);
		tUtil.verify_json_to_response(sysResp, getDetailsCATJmonthlyResponseFile);
	}

	@Then("I see status code as 200 and details in the response for CATJ yearly")
	public void i_see_status_code_as_and_details_in_the_response_for_catj_yearly() throws IOException {
		tUtil.verifyStatus(sysResp, ResponseCode.OK);
		tUtil.verify_json_to_response(sysResp, getDetailsCATJyearlyResponseFile);
	}

	@Then("I check product sys and procedure responses")
	public void iCheckProductSysAndProcedureResponses() throws JsonProcessingException {
		
		productSystemHelper.checkProductPrices(sysResp);
	}
}
