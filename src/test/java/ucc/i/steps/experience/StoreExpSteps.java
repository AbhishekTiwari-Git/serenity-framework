package ucc.i.steps.experience;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ucc.i.method.accountexp.AccountExpGET;
import ucc.i.method.accountexp.AccountExpPOST;
import ucc.i.method.acs.ACSGET;
import ucc.i.method.acs.ACSPOST;
import ucc.i.method.acsprocess.ACSProcessPOST;
import ucc.i.method.aic.AICGET;
import ucc.i.method.aic.AICPOST;
import ucc.i.method.customerprocess.CustomerProcessPOST;
import ucc.i.method.icv.ICVGET;
import ucc.i.method.literatum.LiteratumGET;
import ucc.i.method.orderproc.OrderProcPOST;
import ucc.i.method.store.StoreGET;
import ucc.i.method.store.StoreHelper;
import ucc.i.method.store.StorePost;
import ucc.i.method.store.UpdateStoreFiles;
import ucc.i.steps.process.CustProcSteps;
import ucc.utils.*;
import ucc.utils.CucumberUtils.CucumberUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static ucc.i.method.literatum.utils.LiteratumHelper.checkBillingPaidStatus;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ucc.i.method.literatum.utils.LiteratumHelper.checkBillingUnpaidStatus;

public class StoreExpSteps {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StoreExpSteps.class);
	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
	private static String autoEmail =  EnvironmentSpecificConfiguration.from(envVar)
			.getProperty("autoEmail");
    private static Response procResp = null;
    private static Response expResp = null;
    private static Response icvResp = null;
    private static Response aicResp = null;
    private static Response acsResp = null;
    private static Response literRespIdentity = null;
    private static Response literRespLicense = null;
    private static String endPt = null;
    public static String ucid = null;
    public static String emailUser = null;
    public static Map<String, String> kmap = new HashMap<String, String>();
    private static final TestUtils tUtil = new TestUtils();
    private static final DbUtils dbUtils = new DbUtils();
    private static final JsonUtils jsonUtils = new JsonUtils();
    private static UpdateStoreFiles updateStoreFiles = new UpdateStoreFiles();
    private List<String> emailList = new ArrayList<>();
    private static String productPrices;
    private List<HashMap> usersList = new ArrayList<>();
    private static Response sysResp = null;
   
    
    
    
    @Steps
    StorePost storePost;

    @Steps
    StoreGET storeGET;

    @Steps
    ACSPOST acsPOSTSteps;

    @Steps
    LiteratumGET literatumGETSteps;

    @Steps
    ICVGET icvGETSteps;

    @Steps
    AICGET aicGETSteps;

    @Steps
    AICPOST aicPOSTSteps;

    @Steps
    ACSGET acsGETSteps;

    @Steps
    AccountExpPOST accExpPOSTSteps;

    @Steps
    AccountExpGET accExpGETSteps;

    @Steps
    OrderProcPOST orderProcPOST;

    @Steps
    ACSProcessPOST acsProcessPOST;

    @Steps
    CustomerProcessPOST custProcStepsPOST;
    
    @Steps
    CustProcSteps custProcSteps;

    @Steps
    StoreHelper storeHelper;
    
    private String fileName = "ACS/ACS_Post.json";
    private String Store_cc_annual = "Store/store_cc_annual.json";
    private String Store_cc_monthly = "Store/store_cc_monthly.json";
    private String Store_invoice_annual = "Store/store_invoice_annual.json";
    private String Store_user_invoice_annual = "Store/store_user_invoice_annual.json";
    private String file_order = "Store/Store_Post_Order.json";
    private String Store_681 = "Store/store_681.json";
    private String Store_682 = "Store/store_682.json";
    
    @When("^I do GET call to product_prices without ucid  and with products (.*) country (.*) promo (.*) professionalCategory (.*)$")
    public void i_do_get_call_to_product_prices_without_ucid_and_with_products_country_promo_professionalCategory(
    		String products, String country, String promo, String professionalCategory) {
    	
    	HashMap<String, String> params = new HashMap<String, String>(); 
			params.put("products", products);
			params.put("country", country);
			params.put("promo", promo);
    	
    	endPt = storeGET.setEndpointProductsPrices();
        expResp = storeGET.getProductPriceWithParams(endPt, params)
        		.extract().response();
    }
    
    @When("^I do GET call to product_prices with empty ucid  products (.*) country (.*) and promo (.*) to see details about the product$")
    public void i_get_call_to_product_prices_with_empty_ucid_products(
    		String products, String country, String promo) {

    	HashMap<String, String> params = new HashMap<String, String>(); 
			params.put("products", products);
			params.put("country", country);
			params.put("promo", promo);
			params.put("ucid", "");
    	
    	endPt = storeGET.setEndpointProductsPrices();
        expResp = storeGET.getProductPriceWithParams(endPt, params)
        		.extract().response();
        
        tUtil.putToSession("response", expResp);
    }
    
    @When("^I do GET call to product_prices with invalid ucid  products (.*) country (.*) and promo (.*) to see details about the product$")
    public void i_get_call_to_product_prices_with_invalid_ucid_products(
    		String products, String country, String promo) {
    	
    	HashMap<String, String> params = new HashMap<String, String>(); 
			params.put("products", products);
			params.put("country", country);
			params.put("promo", promo);
			params.put("ucid", tUtil.AppendTimestamp(custProcSteps.kmap.get("ucid")));
    	
    	ucid = tUtil.AppendTimestamp(custProcSteps.kmap.get("ucid"));
    	
    	endPt = storeGET.setEndpointProductsPrices();
        expResp = storeGET.getProductPriceWithParams(endPt, params)
        		.extract().response();
        
        tUtil.putToSession("response", expResp);
    }
    
    @When("^I do GET call to product_prices with the ucid  products (.*) country (.*) and promo (.*) to see details about the product$")
    public void i_get_call_to_product_prices_with_the_ucid_products(String products, String country, String promo) {
    	
    	HashMap<String, String> params = new HashMap<String, String>(); 
			params.put("products", products);
			params.put("country", country);
			params.put("promo", promo);
			params.put("ucid", custProcSteps.kmap.get("ucid"));
    	
        ucid = custProcSteps.kmap.get("ucid");
    	
    	endPt = storeGET.setEndpointProductsPrices();
        expResp = storeGET.getProductPriceWithParams(endPt, params)
        		.extract().response();
    }
    
    @Then("I see the details about the product")
    public void i_see_the_details_about_the_product() {
    	tUtil.verifyStatus(expResp, ResponseCode.OK);
    	
    	Assert.assertNotNull(expResp.jsonPath().get("products"));
    	Assert.assertNotNull(expResp.jsonPath().get("products[0].terms[0].price"));
    }
    
    @Then("^I see the new user got created with new field professionalCategory (.*)$")
	public void iSeeTheNewUserGotCreatedWithNewField(String professionalCategory) {

		LOGGER.info(storeGET.getField(expResp, professionalCategory) + "   <<-- this is the field I got from storeExp");
		Assert.assertFalse(storeGET.getField(expResp, professionalCategory).isEmpty());
	}
    
    @When("^user calls GET store customer with ucid$")
	public void user_calls_GET_store_customer() throws Throwable {
    	
    	aicResp = aicPOSTSteps.createToken(AccountExpSteps.kmap.get("ucid"))
                .extract().response();
    	
        String token = aicPOSTSteps.getToken(aicResp);
        
		endPt = storeGET.setEndpointCustomer(AccountExpSteps.kmap.get("ucid"));
		expResp = storeGET.getStore(endPt, token)
				.extract().response();
	}
    
    @Then("^I see the new user got created with new field akamai_professionalCategory (.*) in akamai$")
	public void iSeeTheNewUserGotCreatedWithNewFieldinAkamai(String akamai_professionalCategory) {
    	LOGGER.info((aicResp.jsonPath().getString(akamai_professionalCategory)
				+ "<<-- this is the field I got from AICget"));
		Assert.assertFalse((aicResp.jsonPath().getString(akamai_professionalCategory)).isEmpty());
	}
    
    @When("^user calls Akamai to GET the user$")
    public void userCallsAkamaiToGetTheUser() throws Throwable {
        String ucidAic = kmap.get("ucid") != null ? kmap.get("ucid") : AccountExpSteps.kmap.get("ucid");
        endPt = aicGETSteps.setEndpointUserID(ucidAic);
        aicResp = aicGETSteps.getUserFromAkamai(endPt)
                .extract().response();
    }
    
    @When("^I send a POST request to create a new customer$")
    public void postCustomer() throws Exception {
        String emailValue = tUtil.AppendTimestamp(autoEmail);
        String ucidValue = tUtil.generateRandomUcid(36);
        String firstName = tUtil.AppendTimestamp("test_fname");
        String lastName = tUtil.AppendTimestamp("test_lname");
        jsonUtils.update_JSONValue(fileName, "$.ucid", ucidValue);
        jsonUtils.update_JSONValue(fileName, "$.address.email", emailValue);
        jsonUtils.update_JSONValue(fileName, "$.address.name.firstName", firstName);
        jsonUtils.update_JSONValue(fileName, "$.address.name.lastName", lastName);

        // Storing fields name in kmap
        kmap.put("ucid", ucidValue);
        kmap.put("email", emailValue);
        kmap.put("firstName", firstName);
        kmap.put("lastName", lastName);

        endPt = acsPOSTSteps.setEndpoint();
        procResp = acsPOSTSteps.post(fileName, endPt).extract().response();

        String customerNumber = procResp.jsonPath().getString("customerNumber");
        String addressCode = procResp.jsonPath().getString("addressCode");
        kmap.put("addressCode", addressCode);
        kmap.put("customerNumber", customerNumber);
        tUtil.putToSession("customerNumber", customerNumber);
    }

    @Given("^I do a POST Store request to create order$")
    public void iDoAPOSTRequestToCreateSingleAgreement() throws Exception {
        String localUcid = (ucid != null) ? ucid : (String) tUtil.getFromSession("ucid");
        endPt = storePost.setEndpointOrder(localUcid);
        expResp = storePost.postStore_withToken(endPt, file_order, getToken(localUcid))
                .extract().response();
    }

    @Then("^order should get created$")
    public void orderShouldGetCreated() {
        tUtil.verifyStatus(expResp, ResponseCode.CREATED);
    }

    @Then("^order should get created with status 200$")
    public void orderShouldGetCreatedWithStatus200() {
        tUtil.verifyStatus(expResp, ResponseCode.OK);
    }

    @When("^user calls ACS$")
    public void userCallsAcs() throws Throwable {
        if(emailUser == null) emailUser = (String) tUtil.getFromSession("email");

        int timeOutMin = 12;
        do {
            System.out.println("Waiting for request ACS to get finished");
            TimeUnit.SECONDS.sleep(5);
            if (checkACSResponce()) {
                break;
            }
            timeOutMin--;
        } while (timeOutMin != 0);
    }

    private Boolean checkACSResponce() throws URISyntaxException {
        endPt = acsGETSteps.setEndpoint();
        acsResp = acsGETSteps.getCustomers(endPt, "email", emailUser)
                .extract().response();
        tUtil.verifyStatus(acsResp, ResponseCode.OK);

        Object custNum = acsResp.jsonPath().getList("customerNumber").get(0);
        kmap.put("customerNumber", (String) custNum);
        endPt = acsGETSteps.setCustomerAgreementsEndpoint((String) custNum);
        acsResp = acsGETSteps.get(endPt).extract().response();

        return acsResp.jsonPath().getList("agreements").size() == 1 &&
                custNum != null;
    }

    @When("^user calls Akamai$")
    public void userCallsAkamai() throws Throwable {
        int timeOutMin = 12;
        do {
            System.out.println("Waiting for request Akamai to get finished");
            TimeUnit.SECONDS.sleep(10);
            if (checkAICResponse()) {
                break;
            }
            timeOutMin--;
        } while (timeOutMin != 0);
    }

    private Boolean checkAICResponse() {
        endPt = aicGETSteps.setEndpointUserID(ucid);
        aicResp = aicGETSteps.getUserFromAkamai(endPt)
                .extract().response();       
        return aicResp.statusCode() == ResponseCode.OK &&  aicGETSteps.verifyCJAudienceType(aicResp);
    }

    @Then("^ICV and ACS should exist in alternateID$")
    public void aicGETSteps() {
    	assertTrue(aicGETSteps.verifyCJAudienceType(aicResp));
    	
    }
    
    @Then("^audienceType should exist as Registered user in Akamai$")
	public void verify_audience_type_Akamai() throws Throwable {
					
    	aicGETSteps.verifyAudienceTypeRegisteredUser(aicResp);
		LOGGER.info("verified audience type as registered user");
	}

    @When("^user calls ICV$")
    public void userCallsIcv() throws Throwable {
        if(emailUser == null) emailUser = (String) tUtil.getFromSession("email");

        int timeOutMin = 12;
        do {
            System.out.println("Waiting for request ICV to get finished");
            TimeUnit.SECONDS.sleep(10);
            if (checkICVResponse()) {
                break;
            }
            timeOutMin--;
        } while (timeOutMin != 0);
    }

    private Boolean checkICVResponse() throws URISyntaxException {
        endPt = icvGETSteps.setEndpoint(emailUser);
        icvResp = icvGETSteps.getUsers(endPt)
                .extract().response();
        String typeICV = icvResp.jsonPath().getString("type");
        return icvResp.statusCode() == ResponseCode.OK &&
                typeICV.equalsIgnoreCase("subscriber");
    }
    
    @Then("^user should be able to retrieve values from UI and passes to API$")
    public void userpassesAPIvalue() throws InterruptedException {
        kmap.put("ucid",  tUtil.getFromSession("ucid").toString().trim());
        ucid = tUtil.getFromSession("ucid").toString().trim();
        emailUser = tUtil.getFromSession("email").toString().trim();
        kmap.put("email",  tUtil.getFromSession("email").toString().trim());
        kmap.put("fname",  tUtil.getFromSession("fname").toString().trim());
        kmap.put("lname",  tUtil.getFromSession("lname").toString().trim());       
    }
    
    @Then("^Paybill user should be able to retrieve values from UI and passes to API$")
    public void PaybilluserpassesAPIvalue() throws InterruptedException {
        kmap.put("ucid",  tUtil.getFromSession("ucid").toString().trim());
        ucid = tUtil.getFromSession("ucid").toString().trim();
        kmap.put("email",  tUtil.getFromSession("email").toString().trim());

    }

    @When("^user calls Literatum$")
    public void userCallsLiteratum() throws InterruptedException {
        int timeOutMin = 20;
        if(emailUser == null) emailUser = (String) tUtil.getFromSession("email");

        do {
            System.out.println("Waiting for async request Identity and License to get finished");
            TimeUnit.SECONDS.sleep(5);
            if (callLiteratumIdentity() && callLiteratumLicense()) {
                break;
            }
            timeOutMin--;
        } while (timeOutMin != 0);
    }

    @When("^user calls Literatum list of emails$")
    public void loopEmailList() throws InterruptedException {

        emailList = tUtil.checkSession("emailList", emailList);

        for (String email: emailList) {
            if(!checkAkamai(email)) continue;
            if (userCallsLiteratumBillingStatus(email)) {
                tUtil.putToSession("email", email);
                break;
            }
        }
    }
    
    @When("^I validate the list of fetched users in Literatum$")
    public void loopEmailListBalanceDue() throws InterruptedException {

		emailList = tUtil.checkSession("emailList", emailList);
    }
    
    public void getAkamaiaccesstoken()
    {
    	sysResp = aicPOSTSteps.createToken(ucid)
        		.extract().response();
    	tUtil.verifyStatus(sysResp, ResponseCode.OK);

        String[] keys = {"stat", "accessToken"};
        tUtil.verifyKeysInObject(sysResp, keys);

        kmap.put("accessToken", sysResp.jsonPath().getString("accessToken"));
        tUtil.putToSession("accessToken", sysResp.jsonPath().getString("accessToken"));
    }
    
    public void getStoreaccesstoken()
    {
        sysResp = aicPOSTSteps.createToken(ucid)
                .extract().response();
        tUtil.verifyStatus(sysResp, ResponseCode.OK);

        String[] keys = {"stat", "accessToken"};
        tUtil.verifyKeysInObject(sysResp, keys);

        kmap.put("accessToken", sysResp.jsonPath().getString("accessToken"));
        tUtil.putToSession("accessToken", sysResp.jsonPath().getString("accessToken"));
    }

    public boolean zeroPendinginvoice()
    {
    	boolean invoicesize;
    	endPt = storeGET.setEndpointCustUCIDinvoices(ucid);
		expResp = storeGET.getwithProductFamily(endPt, getToken(ucid), "catalyst").extract().response();
		if (expResp.jsonPath().getList("invoices") != null && !(expResp.jsonPath().getList("invoices").size() == 0)) {
			invoicesize = expResp.jsonPath().getString("invoices[0].amounts.balanceDue").equals("0");
			} else {				

				invoicesize = true;
			}
		return (invoicesize);

    }

    public Boolean checkAkamai(String email) {
        endPt = accExpGETSteps.setEndpointCustomers();
        expResp = accExpGETSteps.getUserByEmail(endPt, email).extract().response();
        ucid = expResp.jsonPath().getString("ucId");
        return ucid != null;
    }

    public Boolean userCallsLiteratumBillingStatus(String email) throws InterruptedException {
        int timeOutMin = 1;
//        if(emailUser == null) emailUser = (String) tUtil.getFromSession("email");
        emailUser = email;
        getUcidUser(emailUser);


        do {
            System.out.println("Waiting for async request Identity and License to get finished");
            TimeUnit.SECONDS.sleep(5);
            if (checkLiteraturmBillingStatus()) {
                return true;
            }
            timeOutMin--;
        } while (timeOutMin != 0);
        return false;
    }

    public Boolean checkLiteraturmBillingStatus() {

        endPt = literatumGETSteps.setEndpointIdentity(ucid);
        literRespIdentity = literatumGETSteps.get(endPt)
                .extract().response();
        if(literRespIdentity.statusCode() == ResponseCode.NOT_FOUND) return false;
        kmap.put("firstName", literRespIdentity.jsonPath().getString("firstName"));
        kmap.put("lastName", literRespIdentity.jsonPath().getString("lastName"));

        return checkBillingUnpaidStatus(literRespIdentity);
    }

    private Boolean callLiteratumIdentity() {
        endPt = literatumGETSteps.setEndpointIdentity(ucid);
        literRespIdentity = literatumGETSteps.get(endPt)
                .extract().response();
        kmap.put("email", emailUser);
//        kmap.computeIfAbsent("email", k -> emailUser);

        return literRespIdentity.statusCode() == ResponseCode.OK &&
                literatumGETSteps.Verify_license_and_tag_information(kmap, literRespIdentity);

    }

    private Boolean callLiteratumLicense() {
    	
        endPt = literatumGETSteps.setEndpointLicenses(ucid);        
        literRespLicense = literatumGETSteps.get(endPt)
                .extract().response();

        return literRespLicense.statusCode() == ResponseCode.OK &&
                literRespLicense.jsonPath().getString("code")!= null;
    }

    @Then("^audienceType should exist as subscriber in ICV$")
    public void audiencetypeShouldExistAsSubscriberInIcv() {
    	Assert.assertEquals(icvResp.jsonPath().getString("type"), "subscriber");
    }

    @Then("^license and tag information should exist in Literatum$")
    public void licenseAndTagInformationShouldExistInLiteratum() {
        tUtil.verifyStatus(literRespIdentity, ResponseCode.OK);
        tUtil.verifyStatus(literRespLicense, ResponseCode.OK);
        Assert.assertTrue(literatumGETSteps.Verify_license_and_tag_information(kmap, literRespIdentity));
        Assert.assertNotNull(literRespLicense.jsonPath().getString("code"));
    }    
    
    
    @Then("^tag information should contain audience type as Registered user$")
    public void TagInformationShouldContainRegisteredUser() {
        tUtil.verifyStatus(literRespIdentity, ResponseCode.OK);
        tUtil.verifyStatus(literRespLicense, ResponseCode.OK);
        Assert.assertTrue(literatumGETSteps.Verify_tag_information_registered_user(kmap, literRespIdentity));
        Assert.assertNotNull(literRespLicense.jsonPath().getString("code"));
    }

    @Then("^license and tag information should exist and contain Paid tag in Literatum for paybill user$")
    public void licenseAndTagInformationShouldExistInLiteratum_paidtag() {
        tUtil.verifyStatus(literRespIdentity, ResponseCode.OK);
        tUtil.verifyStatus(literRespLicense, ResponseCode.OK);
        Assert.assertTrue(literatumGETSteps.Verify_license_and_tag_information(kmap, literRespIdentity));
        Assert.assertTrue(literatumGETSteps.Verify_license_and_tag_information_SI_paybill_user(kmap, literRespIdentity));
        Assert.assertNotNull(literRespLicense.jsonPath().getString("code"));
    }

    @When("^I send a POST request to create customer$")
    public void iSendAPOSTRequestMyAccountToCreateACustomerWithContextCATALYST() throws Exception {
        String file = "Store/store_create_user.json";
        String fnameValue = tUtil.AppendTimestamp("AutoFname");
        String lnameValue = tUtil.AppendTimestamp("AutoLname");
        emailUser = tUtil.AppendTimestamp(autoEmail);

        jsonUtils.update_JSONValue(file, "$.email", emailUser);
        jsonUtils.update_JSONValue(file, "$.firstName", fnameValue);
        jsonUtils.update_JSONValue(file, "$.lastName", lnameValue);

        kmap.put("firstName", fnameValue);
        kmap.put("lastName", lnameValue);

        endPt = accExpPOSTSteps.setEndpontCustRegCatalyst();
        expResp = accExpPOSTSteps.post(file, endPt)
                .extract().response();

        getUcidUser(emailUser);
        tUtil.putToSession("ucid", ucid);
        tUtil.putToSession("email", emailUser);
        tUtil.putToSession("firstName", fnameValue);
        tUtil.putToSession("lastName", lnameValue);
    }

    private void getUcidUser(final String email) {
        endPt = accExpGETSteps.setEndpointCustomers();
        expResp = accExpGETSteps.getUserByEmail(endPt, email).extract().response();

        ucid = expResp.jsonPath().getString("ucId");
        kmap.put("ucid", ucid);
    }

    private String getToken() {
        aicResp = aicPOSTSteps.createToken(ucid)
                .extract().response();
        return aicPOSTSteps.getToken(aicResp);
    }

    private String getToken(final String Ucid) {
        aicResp = aicPOSTSteps.createToken(Ucid)
                .extract().response();
        return aicPOSTSteps.getToken(aicResp);
    }

    @When("^I check user IDType$")
    public void iSendAGETRequestToAICGetUserInfo() throws InterruptedException {
    	endPt = aicGETSteps.setEndpointUserID(ucid);
    	TimeUnit.SECONDS.sleep(17);
    	aicResp = aicGETSteps.getUserFromAkamai(endPt).extract().response();
        String idType = aicResp.jsonPath().getString("alternateID[0].IDType");
        Assert.assertEquals("MAGENTO", idType);
    }

    @When("^ICV calls$")
    public void icvCalls() throws Throwable {
        endPt = icvGETSteps.setEndpoint(emailUser);
        icvResp = icvGETSteps.getUsers(endPt)
                .extract().response();
    }

    @When("^I send a POST request to create customer with EMAIL$")
    public void createUserWithEMAIL() throws Exception {
        String file = "Store/store_create_user_email.json";
        String fnameValue = tUtil.AppendTimestamp("AutoFname");
        String lnameValue = tUtil.AppendTimestamp("AutoLname");
        emailUser = tUtil.AppendTimestamp(autoEmail);

        jsonUtils.update_JSONValue(file, "$.email", emailUser);
        jsonUtils.update_JSONValue(file, "$.firstName", fnameValue);
        jsonUtils.update_JSONValue(file, "$.lastName", lnameValue);

        kmap.put("firstName", fnameValue);
        kmap.put("lastName", lnameValue);

        endPt = accExpPOSTSteps.setEndpontCustRegEmail();
        expResp = accExpPOSTSteps.post(file, endPt)
                .extract().response();

        getUcidUser(emailUser);
    }

    @When("I send a POST request to create customer with ICV")
    public void iSendAPOSTRequestToCreateCustomerWithICV() throws Exception {
        String file = "Store/store_create_user_icv.json";
        String fnameValue = tUtil.AppendTimestamp("AutoFname");
        String lnameValue = tUtil.AppendTimestamp("AutoLname");
        emailUser = tUtil.AppendTimestamp(autoEmail);

        jsonUtils.update_JSONValue(file, "$.email", emailUser);
        jsonUtils.update_JSONValue(file, "$.firstName", fnameValue);
        jsonUtils.update_JSONValue(file, "$.lastName", lnameValue);

        kmap.put("firstName", fnameValue);
        kmap.put("lastName", lnameValue);

        endPt = accExpPOSTSteps.setEndpontCustRegCatalystEvent("1");
        expResp = accExpPOSTSteps.post(file, endPt)
                .extract().response();

        endPt = icvGETSteps.setEndpoint(emailUser);
        aicResp = icvGETSteps.getUsers(endPt)
                .extract().response();
        getUcidUser(emailUser);
    }

    @When("^I do a GET call to get customers info$")
    public void iDoAGETCallToGetCustomersInfo() {
        aicResp = aicPOSTSteps.createToken(ucid)
                .extract().response();
        String token = aicPOSTSteps.getToken(aicResp);
        endPt = storeGET.setEndpointCustomerCJ(ucid);
        expResp = storeGET.getStore(endPt, token).extract().response();
    }

    @When("^I do a GET call with does not exist ucid$")
    public void iDoAGETCallWithDoesNotExistUcid() {
        ucid = "00000000-0000-0000-0000-000000000000";
        endPt = storeGET.setEndpointCustomerCJ(ucid);
        expResp = storeGET.getStore(endPt, "token").extract().response();
    }

    @Then("^I see status as 500 error$")
    public void iSeeStausAsError() {
        tUtil.verifyStatus(expResp, ResponseCode.INTERNAL_ERROR);
    }

    @When("^I do a GET call if ucid is invalid$")
    public void iDoAGETCallIfUcidIsMissing() {
        endPt = storeGET.setEndpointCustomerCJ("183c57f2-c43f-46d6-3d9dcb6d760a");
        expResp = storeGET.getStore(endPt, "token").extract().response();
    }

    @Then("^I see status 200 ucid in body$")
    public void iSeeStatusUcidInBody() {
        tUtil.verifyStatus(expResp, ResponseCode.OK);

        String ucidResp = expResp.jsonPath().getString("ucid");
        Assert.assertEquals(ucid, ucidResp);

        int productsList = expResp.jsonPath().getList("products").size();
        Assert.assertEquals(productsList, 0);
    }

    @When("^User post order invoice with data:$")
    public void userPostOrderInvoiceWithData(final DataTable dataTable) throws Exception {

        Map<String, String> row = CucumberUtils.convert(dataTable);

        updateStoreFiles.updateCustomerData(
                Store_invoice_annual, kmap.get("firstName"),
                kmap.get("lastName"), emailUser, row
        );
        Map<String, String> map = updateStoreFiles.update_invoice_file(
                Store_invoice_annual, row);

        kmap.putAll(map);

        endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
                endPt, Store_invoice_annual, getToken())
                .extract().response();
    }

    @When("^User post order cc_annual with data:$")
    public void userPostOrderCcAnnualWithData(final DataTable dataTable) throws Exception {

        Map<String, String> row = CucumberUtils.convert(dataTable);

        updateStoreFiles.updateCustomerData(
                Store_cc_annual, kmap.get("firstName"),
                kmap.get("lastName"), emailUser, row
        );
        Map<String, String> map = updateStoreFiles.update_cc_annual_file(
                Store_cc_annual, row);
        kmap.putAll(map);

        endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
                endPt, Store_cc_annual, getToken()).extract().response();
    }

    @When("User post order annual invoice with data:")
    public void userPostOrderAnnualInvoiceWithData(final DataTable dataTable) throws Exception {
        Map<String, String> row = CucumberUtils.convert(dataTable);

        updateStoreFiles.updateCustomerData(
                Store_user_invoice_annual, kmap.get("firstName"),
                kmap.get("lastName"), emailUser, row
        );
        Map<String, String> map = updateStoreFiles.update_invoice_file(
                Store_user_invoice_annual, row);

        kmap.putAll(map);

        endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
                endPt, Store_user_invoice_annual, getToken()).extract().response();
    }

    @When("^User post monthly with data:$")
    public void userPostMonthlyWithData(final DataTable dataTable) throws Exception {
        Map<String, String> row = CucumberUtils.convert(dataTable);

        updateStoreFiles.updateCustomerData(
                Store_681, kmap.get("firstName"),
                kmap.get("lastName"), emailUser, row
        );
        Map<String, String> map = updateStoreFiles.update_store_file(Store_681, row);
        kmap.putAll(map);

        endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(endPt, Store_681, getToken()).extract().response();

    }

    @When("^User post order for an existing user with data:$")
    public void userPostOrderForAnExistingUserWithData(final DataTable dataTable) throws Exception {

        Map<String, String> row = CucumberUtils.convert(dataTable);

        updateStoreFiles.updateCustomerData(
                Store_682, kmap.get("firstName"),
                kmap.get("lastName"), emailUser, row
        );
        Map<String, String> map = updateStoreFiles.update_store_file(Store_682, row);
        kmap.putAll(map);

        endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(endPt, Store_682, getToken()).extract().response();

    }

    @When("^User post order monthly with data:$")
    public void userPostOrderMonthlyWithData(final DataTable dataTable) throws Exception {
        Map<String, String> row = CucumberUtils.convert(dataTable);

        updateStoreFiles.updateCustomerData(
                Store_cc_monthly, kmap.get("firstName"),
                kmap.get("lastName"), emailUser, row
        );
        Map<String, String> map = updateStoreFiles.update_invoice_file(
                Store_cc_monthly, row);

        Double paymentAmount = Double.parseDouble(row.get("tdue")) - Double.parseDouble(row.get("tax"));
        jsonUtils.update_JSONValue(Store_cc_monthly, "$.payment.paymentAmount", paymentAmount);

        kmap.putAll(map);

        endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
                endPt, Store_cc_monthly, getToken()).extract().response();

    }

    @When("User send a POST request to create a new order")
    public void userSendAPOSTRequestToCreateANewOrder() throws Exception {
        String file = "OrderProcess/OrderProc_POST_order.json";
        emailUser = tUtil.AppendTimestamp(autoEmail);

        jsonUtils.update_JSONValue(file, "$.customer.firstname", tUtil.AppendTimestamp("FirstName"));
        jsonUtils.update_JSONValue(file, "$.customer.lastname", tUtil.AppendTimestamp("LastName"));
        jsonUtils.update_JSONValue(file, "$.customer.email", emailUser);
        jsonUtils.update_JSONValue(file, "$.customer.customerId", tUtil.generateRandomUcid(12));

        endPt = orderProcPOST.setEndpointOrder();
        expResp = orderProcPOST.postOrderProc(endPt, file).extract().response();
        ucid = expResp.jsonPath().getString("ucid");
    }

    @When("User send a POST request order process to create a new order")
    public void userSendAPOSTRequestOrderProcessToCreateANewOrder() throws Exception {
        String file = "OrderProcess/OrderProc_POST_order.json";
        emailUser = (String) tUtil.getFromSession("email");
        ucid = (String) tUtil.getFromSession("ucid");
        String firstName = (String) tUtil.getFromSession("firstName");
        String lastName = (String) tUtil.getFromSession("lastName");

        jsonUtils.update_JSONValue(file, "$.customer.firstname", firstName);
        jsonUtils.update_JSONValue(file, "$.customer.lastname", lastName);
        jsonUtils.update_JSONValue(file, "$.customer.email", emailUser);
        jsonUtils.update_JSONValue(file, "$.customer.customerId", ucid);

        endPt = orderProcPOST.setEndpointUcidOrder(ucid);
        expResp = orderProcPOST.postOrderProcWithToken(endPt, file, getToken(ucid)).extract().response();
//        ucid = expResp.jsonPath().getString("ucid");
        kmap.put("firstName", firstName);
        kmap.put("lastName", lastName);
    }


    @And("user check ACS agreement")
    public void userCheckACSAgreement() {
        endPt = accExpGETSteps.setEndpointCustomers();
        expResp = accExpGETSteps.getUserByEmail(endPt, emailUser).extract().response();
        ucid = expResp.jsonPath().getString("ucId");
    }

    @When("^LEAD User post order cc_annual with data:$")
    public void leadUserPostOrderCc_annualWithData(DataTable dataTable) throws Exception {
        String fileName = "Store/store_lead_cc_annual.json";
        Map<String, String> row = CucumberUtils.convert(dataTable);

        updateStoreFiles.updateCustomerData(
                fileName, kmap.get("firstName"),
                kmap.get("lastName"), emailUser, row
        );
        Map<String, String> map = updateStoreFiles.update_cc_annual_file(
                fileName, row);
        kmap.putAll(map);

        endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
                endPt, fileName, getToken()).extract().response();
    }

    @Then("User get order number")
    public void userGetOrderNumber() {
        endPt = acsGETSteps.setOrdersEndpoint(kmap.get("customerNumber"));
        acsResp = acsGETSteps.get(endPt).extract().response();
        String orderNum = acsResp.jsonPath().getString("orders[0].orderNumber");
        kmap.put("orderNumber", orderNum);
    }

    @When("User send a CC request to customer payments")
    public void userSendARequestToCustomerPayments() throws Exception {
        String fileName = "CustomerProcess/CustProc_POST_payments_CC.json";
        jsonUtils.update_JSONValue(fileName, "$.paymentAmount", Double.valueOf(kmap.get("paymentAmount")));
        jsonUtils.update_JSONValue(fileName, "$.orderId", kmap.get("orderNumber"));
        jsonUtils.update_JSONValue(fileName, "$.customerNumber", kmap.get("customerNumber"));
        endPt =  acsProcessPOST.setEndpointCustomerPayments(kmap.get("customerNumber"));
        procResp = acsProcessPOST.post(fileName, endPt).extract().response();
    }


    @And("created not checked order exists")
    public void createdNotCheckedOrderExists() {
        tUtil.writeExcelFile(kmap, "ExcelFile.xlsx");
        Assert.assertEquals(expResp.statusCode(), ResponseCode.CREATED);
    }

    @Then("User check response and status code")
    public void userCheckResponseAndStatusCode() {
        Assert.assertEquals(procResp.statusCode(), ResponseCode.CREATED);
        Assert.assertNotNull(procResp.jsonPath().get("amountPaid"));
    }

    @When("User send a DW request to customer payments")
    public void userSendADWRequestToCustomerPayments() throws Exception {
        String fileName = "CustomerProcess/CustProc_POST_payments_DW.json";
        jsonUtils.update_JSONValue(fileName, "$.paymentAmount", Double.valueOf(kmap.get("paymentAmount")));
        jsonUtils.update_JSONValue(fileName, "$.orderId", kmap.get("orderNumber"));
        jsonUtils.update_JSONValue(fileName, "$.customerNumber", kmap.get("customerNumber"));
        endPt =  acsProcessPOST.setEndpointCustomerPayments(kmap.get("customerNumber"));
        procResp = acsProcessPOST.post(fileName, endPt).extract().response();
    }

    @When("^User send a request customer payments to store$")
    public void userSendARequestCustomerPaymentsToStore() throws Exception {
        String fileName = "Store/store_customer_payments.json";
        kmap = updateStoreFiles.update_payment_file(fileName, kmap);
        
        endPt = storePost.setEndpointPayments(ucid);
        expResp = storePost.postStore_withToken(
                endPt, fileName, getToken(ucid)).extract().response();

        tUtil.putToSession("response", expResp);
    }
    
    @Then("^User send a paypal payment request to store$")
    public void payPalRequestCustomerPaymentsToStore() throws Exception {
        String fileName = "Store/store_payBill_Paypal_payments.json";
        kmap = updateStoreFiles.update_paypal_payment_file(fileName, kmap);
        
        endPt = storePost.setEndpointPayments(ucid);
        expResp = storePost.postStore_withToken(
                endPt, fileName, getToken(ucid)).extract().response();

        tUtil.putToSession("response", expResp);
    }
    
    @When("^I do GET call to StoreExp invoices using ucid for catalyst (.*)$")
    public void i_do_get_call_to_store_exp_invoices_using_ucid(String catalyst) {
         endPt = storeGET.setEndpointCustUCIDinvoices(custProcSteps.kmap.get("ucid"));
         expResp = storeGET.getwithProductFamily(endPt, getToken(custProcSteps.kmap.get("ucid")), catalyst)
        		 .extract().response();
    }
    
    @When("^I do GET call to StoreExp invoices using ucid for nejm (.*)$")
    public void i_do_get_call_to_store_exp_invoices_using_ucid_for_nejm_nejm(String nejm) {
    	endPt = storeGET.setEndpointCustUCIDinvoices(custProcSteps.kmap.get("ucid"));
        expResp = storeGET.getwithProductFamily(endPt, getToken(custProcSteps.kmap.get("ucid")), nejm)
       		 .extract().response();
    }
    
    @When("I do GET call to StoreExp invoices using ucid for empty productFamily")
    public void i_do_get_call_to_store_exp_invoices_using_ucid_for_empty_product_family() {
    	endPt = storeGET.setEndpointCustUCIDinvoices(custProcSteps.kmap.get("ucid"));
        expResp = storeGET.getwithProductFamily(endPt, getToken(custProcSteps.kmap.get("ucid")), "")
       		 .extract().response();
    }

    @When("^I do GET call to StoreExp invoices using ucid for invalid (.*) productFamily$")
    public void i_do_get_call_to_store_exp_invoices_using_ucid_for_invalid_product_family(String invalid) {
    	endPt = storeGET.setEndpointCustUCIDinvoices(custProcSteps.kmap.get("ucid"));
        expResp = storeGET.getwithProductFamily(endPt, getToken(custProcSteps.kmap.get("ucid")), invalid)
       		 .extract().response();
    }

    @Then("I get 200 and invoices for the user in the response")
    public void i_get_and_invoices_for_the_user_in_the_response() {
        tUtil.verifyStatus(expResp, ResponseCode.OK);
//        expResp.jsonPath().get("")
    }
    
    @Then("I get 400 with error in the response")
    public void i_get_with_invoice_in_the_response400() {
    	tUtil.verifyStatus(expResp, ResponseCode.BAD_REQUEST);
    }

    @When("^User get users email list$")
    public void getTopTenUsers() throws SQLException, IOException {

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateTo = localDate.format(formatter);

        String sqlQuery = dbUtils.buildQuery("paymybill.sql");
        String dbURL = dbUtils.buildDbUrl("ACSDbUrl");
        Connection conn = ConnectionFactory.getConnectionACS(
            dbURL, dbUtils.acsDBUser(), dbUtils.acsDBPass());
        PreparedStatement ps = conn.prepareStatement(sqlQuery) ;

        ps.setString( 1, dateTo ) ;

        ResultSet resultSet = ps.executeQuery() ;
        while (resultSet.next()) {
            emailList.add(resultSet.getString("ADR_EMAL"));
        }
     }

    @When("User calls Literatum for check billing status")
    public void userCallsLiteratumForCheckBillingStatus() {
        endPt = literatumGETSteps.setEndpointIdentity(ucid);
        literRespIdentity = literatumGETSteps.get(endPt)
                .extract().response();
        Assert.assertTrue(checkBillingUnpaidStatus(literRespIdentity));
    }

    @When("User calls Literatum for check Paid billing status")
    public void userCallsLiteratumForCheckPaidBillingStatus() {
        endPt = literatumGETSteps.setEndpointIdentity(ucid);
        literRespIdentity = literatumGETSteps.get(endPt)
                .extract().response();
        Assert.assertTrue(checkBillingPaidStatus(literRespIdentity));
    }

    @Then("I call store product prices")
    public void iCallStoreProductPrices(DataTable dataTable) {

        Map<String, String> row = CucumberUtils.convert(dataTable);
        String customerNumber = kmap.get("customerNumber");

        String cutCustNum = customerNumber.substring(customerNumber.length() - 9);
        endPt = storeGET.setEndpointPrice();
        String query = "?promo=" + row.get("promo") +
                "&products=" + tUtil.encodeValue(row.get("products")) +
                "&professionalCategory=" + row.get("professionalCategory") +
                "&country=" + row.get("country") +
                "&customerNumber=" + cutCustNum;
        
        HashMap<String, String> params = new HashMap<String, String>(); 
        params.put("promo", row.get("promo"));
        params.put("products",tUtil.encodeValue(row.get("products")));
        params.put("professionalCategory", row.get("professionalCategory"));
        params.put("country", row.get("country"));
        params.put("customerNumber", cutCustNum);
        
		expResp = storeGET.getProductPriceWithParams(endPt, params)
				.extract().response();
    }

    @Then("I call database procedure ProductPrices")
    public void iCallDatabaseProcedureProductPrices(DataTable dataTable) {
        Map<String, String> row = CucumberUtils.convert(dataTable);
        String dbURL = dbUtils.buildDbUrl("UCCDbUrl");

        Connection conn = ConnectionFactory.getConnectionRDS(
            dbURL, envVar.getProperty("rdsDBUser"), envVar.getProperty("rdsDBPass"));

        productPrices = DbUtils.callProceduregetProductPrices(
                conn, row.get("promo"), row.get("professionalCategory"), row.get("country"),
                kmap.getOrDefault("customerNumber", (String) tUtil.getFromSession("customerNumber")),
                row.get("products")
        );
        Assert.assertNotNull(productPrices);
        tUtil.putToSession("productPrices", productPrices);
    }

    @Then("I check response http and procedure response")
    public void iCheckResponseHttpAndProcedureResponse() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode prodecureJson = mapper.readTree(productPrices);
        JsonNode httResponseJson = mapper.readTree(expResp.getBody().asString());

        TestUtils.NumericNodeComparator cmp = new TestUtils.NumericNodeComparator();
        Assert.assertTrue(prodecureJson.equals(cmp, httResponseJson));
    }


    @Given("Get users with email list")
    public void getUsersWithEmailList() throws IOException, SQLException {
       
        String envVal = storeHelper.getEnv();
        String queryFileName = storeHelper.setPayBillDbfile(envVal);
        System.out.println("Query file name: " + queryFileName);
        String sqlQuery = dbUtils.buildQuery(queryFileName);
        System.out.println("Database Query: " + sqlQuery);
        String dbURL = dbUtils.buildDbUrl("ACSDbUrl");
        System.out.println("Database Url: " + dbURL);
        Connection conn = ConnectionFactory.getConnectionACS(
            dbURL, dbUtils.acsDBUser(), dbUtils.acsDBPass());
        PreparedStatement ps = conn.prepareStatement(sqlQuery) ;
  

        ResultSet resultSet = ps.executeQuery() ;
        while (resultSet.next()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("orderNumber",resultSet.getString("dbt_nbr"));
            user.put("customerNumber",resultSet.getString("src_ctm"));
            user.put("email",resultSet.getString("adr_eml"));
            user.put("paymentAmount",resultSet.getString("tot_dbt"));
            usersList.add(user);
        }
    
       System.out.println("SQL Query Result: " + usersList); 
       Serenity.recordReportData().withTitle("Database evidence").andContents(usersList.toString());
        
    }

    @When("Get user details")
    public void getUserDetails() {
        for (HashMap<String, String> user: usersList
        ) {

            endPt = acsGETSteps.setEndpoint(user.get("customerNumber"));
            acsResp = acsGETSteps.get(endPt).extract().response();
            if (acsResp.statusCode() == ResponseCode.OK) {
                kmap.put("firstName", acsResp.jsonPath().get("addresses[0].name.firstName"));
                kmap.put("lastName", acsResp.jsonPath().get("addresses[0].name.lastName"));
                kmap.put("orderNumber", user.get("orderNumber"));
                kmap.put("customerNumber", user.get("customerNumber"));
                kmap.put("paymentAmount", user.get("paymentAmount"));
            }

            getUcidUser(user.get("email"));
          
            if (expResp.statusCode() == ResponseCode.OK) break;

            LOGGER.info("Request user with customer number {} to ACS failed", user.get("CustomerNumber"));
        }
    }
    

    @Then("User send a request customer payments to Proc API")
    public void userSendARequestToPayments() throws Exception {
        String fileName = "CustomerProcess/CustProc_POST_payments.json";
        jsonUtils.update_JSONValue(fileName, "$.paymentAmount", Double.valueOf(kmap.get("paymentAmount")));
        jsonUtils.update_JSONValue(fileName, "$.orderId", kmap.get("orderNumber"));
        jsonUtils.update_JSONValue(fileName, "$.customerNumber", kmap.get("customerNumber"));
        jsonUtils.update_JSONValue(fileName,
                "$.creditCardInfo.customerName",
                kmap.get("firstName") + " " + kmap.get("lastName")
        );

        procResp = custProcStepsPOST.customerPayments(
                fileName, ucid, getToken(ucid)).extract().response();
        procResp.getStatusCode();
        tUtil.putToSession("response", procResp);
    }

    private void getEmailAddressAndUcid() {
        endPt = acsGETSteps.setEndpoint(kmap.get("customerNumber"));
        acsResp = acsGETSteps.get(endPt).extract().response();
        emailUser = acsResp.jsonPath().get("addresses.email[0]");
        getUcidUser(emailUser);
    }

    @Then("user check Akamai SUBSCRIBER status")
    public void userCheckAkamaiStatus() {
        assertEquals(
                aicResp.jsonPath().getString("audienceType"),
                "REGISTERED USER");
        assertEquals(
                aicResp.jsonPath().getString("Catalyst.subscriptionStatus"),
                "SUBSCRIBER");
    }

    @When("Unpaid user send a CC request to customer payments")
    public void unpaidUserSendCCRequestCustomerPayments() throws Exception {

        for (HashMap<String, String> user : usersList
        ) {
            getUserDetails(user);
            if(ucid==null) continue;
            userSendARequestToCustomerPayments();

            if (procResp.statusCode() == ResponseCode.CREATED) {
                tUtil.putToSession("response", procResp);
                break;
            }
        }

    }

    @When("Unpaid user send a DW request to customer payments")
    public void unpaidUserSendDWRequestCustomerPayments() throws Exception {

        for (HashMap<String, String> user : usersList
        ) {
            getUserDetails(user);
            if(ucid==null) continue;
            userSendADWRequestToCustomerPayments();

            if (procResp.statusCode() == ResponseCode.CREATED) {
                tUtil.putToSession("response", procResp);
                break;
            }
        }

    }

    private void getUserDetails(HashMap<String, String> user) {
        endPt = acsGETSteps.setEndpoint(user.get("customerNumber"));
        acsResp = acsGETSteps.get(endPt).extract().response();
        try {
            kmap.put("firstName", acsResp.jsonPath().get("addresses[0].name.firstName"));
            kmap.put("lastName", acsResp.jsonPath().get("addresses[0].name.lastName"));
            kmap.put("orderNumber", user.get("orderNumber"));
            kmap.put("customerNumber", user.get("customerNumber"));
            kmap.put("paymentAmount", user.get("paymentAmount"));
            getUcidUser(user.get("email"));
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    @When("^User send a request customer payments to store with error$")
    public void userSendARequestCustomerPaymentsToStoreWithError() throws Exception {
        String fileName = "Store/store_customer_payments.json";
        jsonUtils.update_JSONValue(fileName, "$.paymentAmount", Double.valueOf(kmap.get("paymentAmount")));
        jsonUtils.update_JSONValue(fileName, "$.orderId", kmap.get("orderNumber"));
        jsonUtils.update_JSONValue(fileName, "$.customerNumber", kmap.get("customerNumber"));
        jsonUtils.update_JSONValue(fileName,
                "$.creditCardInfo.customerName",
                kmap.get("firstName") + " " + kmap.get("lastName")
        );

        endPt = storePost.setEndpointPayments(ucid);
        expResp = storePost.postStore_withToken(
                endPt + "1", fileName, getToken(ucid)).extract().response();

        tUtil.putToSession("response", expResp);
    }

    @And("User check response error message")
    public void userCheckResponseErrorMessage() {
        assertEquals(expResp.jsonPath().get("message"), storePost.setEndpointPayments(ucid)+"1");
    }

    @Then("User check payment is applied")
    public void userCheckPaymentIsApplied() throws IOException, SQLException {
        String amount = null;
        int dayOfYear = LocalDate.now().getDayOfYear();

        // For the UC control groups, the numbers in the control group are the day of the year.
        // So 11/13/2020 is the 318th day of the year, thus UC318G (G is for Good.)
        String ord_ctg = "UC" + dayOfYear + "G";

        String sqlQuery = dbUtils.buildQuery("payments_applied.sql");
        String dbURL = dbUtils.buildDbUrl("ACSDbUrl");
        Connection conn = ConnectionFactory.getConnectionACS(
            dbURL, dbUtils.acsDBUser(), dbUtils.acsDBPass());
        PreparedStatement ps = conn.prepareStatement(sqlQuery);
        ps.setString( 1, ord_ctg ) ;
        ps.setString( 2, kmap.get("customerNumber"));

        ResultSet resultSet = ps.executeQuery() ;
        while (resultSet.next()) {
            amount = resultSet.getString("TOT_CST");
        }

        assertEquals(kmap.get("paymentAmount"), amount.substring(1));

    }

    @Then("^User send a request to Catalyst to check payment is applied$")
    public void userSendARequestToCatalystToCheckPaymentIsApplied() {
        endPt = storeGET.setEndpointCustUCIDinvoices(ucid);
        expResp = storeGET.VerifyEmptyInvoicewithProductFamily(endPt, getToken(ucid), "catalyst")
            .extract().response();
       
    }
    
    @Then("^tag and label should contain complimentary in Literatum for Magento user$")
	public void license_and_tag_information_should_exist_in_literatum_system() throws InterruptedException {
    	 Assert.assertTrue(literatumGETSteps.Verify_license_and_tag_information_Magento_Comp_Order(literRespIdentity));
	}

    @When("^I do GET request to store customer with ucid$")
    public void iDoGETRequestStoreCustomer() {
        endPt = storeGET.setEndpointCustomer(ucid);
        expResp = storeGET.getStore(endPt, getToken(ucid))
            .extract().response();
    }

    @And("Fields nejmMediaAccess and nejmInstitutionalAdmin changed")
    public void fieldsNejmMediaAccessAndNejmInstitutionalAdminChanged() {
        Assert.assertNotEquals(
            aicResp.jsonPath().getString("Nejm.mediaAccess"),
            expResp.jsonPath().getMap("Customer").get("nejm.mediaAccess")
        );
        Assert.assertNotEquals(
            aicResp.jsonPath().getString("Nejm.institutionalAdmin"),
            expResp.jsonPath().getMap("Customer").get("nejm.institutionalAdmin")
        );

    }

    @Then("^user validates data in backend for Literatum, ICV, Akamai and ACS$")
	public void validate_backend_tools() throws Throwable {
    	userCallsLiteratumForCheckPaidBillingStatus();
    	userCallsIcv();
    	audiencetypeShouldExistAsSubscriberInIcv();
    	userCallsAkamaiToGetTheUser();
    	aicGETSteps();
    	userCallsAcs();
   // 	orderShouldExistInAcs();
    }
    
    @Then("^I call invoices api with fetched users to validate balance due$")
    public void PayBilluserCallsAkamaiToGetTheUser() throws Throwable {
        
        emailList = tUtil.checkSession("emailList", emailList);
        
        for (String email : emailList) {
           
            if(!checkAkamai(email)) continue;
           
                if (!(zeroPendinginvoice())) {
                    tUtil.putToSession("email", email);
                    tUtil.putToSession("ucid", ucid);
                    break;
                }
            }

        }
    @Then("^I call Akamai for fetched renew users to get ucid$")
    public void renewuserCallsAkamaiToGetTheUser() throws Throwable {
        
        emailList = tUtil.checkSession("emailList", emailList);
        
        for (String email : emailList) {
            if(!checkAkamai(email)) 
            	{ 
            	  continue;
            	}
            else {
                    tUtil.putToSession("email", email);
                    tUtil.putToSession("ucid", ucid);
                    break;
                 }
                
            }
        

        }
    	
}

