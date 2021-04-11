package ucc.i.steps.experience;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import ucc.i.method.acs.ACSGET;
import ucc.i.method.aic.AICGET;
import ucc.i.method.icv.ICVGET;
import ucc.i.method.literatum.LiteratumGET;
import ucc.i.method.nejmliteratumsystem.NEJMLiteratumSystemGET;
import ucc.i.method.store.StoreHelper;
import ucc.i.method.store.StorePost;
import ucc.i.method.store.UpdateStoreFiles;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;
import ucc.utils.CucumberUtils.CucumberUtils;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;

import static org.junit.Assert.*;
import static ucc.i.method.literatum.utils.LiteratumHelper.verifyLicenseAndTag;


public class StoreExpStepsV1 {

    public static Map<String, String> kmap = new HashMap<String, String>();
    private static final TestUtils tUtil = new TestUtils();
   
    private static Response expResp, literatumResp, icvResp, aicResp, acsResp = null;
    public static String ucid = null;
    private static UpdateStoreFiles updateStoreFiles = new UpdateStoreFiles();
    private static String endPt = null;
    SoftAssertions softAssert = new SoftAssertions();

    @Steps
    StorePost storePost;

    @Steps
    LiteratumGET literatumGETSteps;

    @Steps
    ICVGET icvGETSteps;

    @Steps
    AICGET aicGETSteps;

    @Steps
    ACSGET acsGETSteps;

    @Steps
    NEJMLiteratumSystemGET nejmLiteratumSystemGET;

    @Then("I do a POST Store request to create Catalyst single order")
    public void iDoAPOSTStoreRequestToCreateCatalystSingleOrder() throws Exception {
        String fileName = StoreJsonBodyFile.STORE_CATALYST_SINGLE_ORDER;
      
        
        String ucid = (String) tUtil.getFromSession("ucid");

        StoreHelper.updateCustomerFNameLNameEmail(fileName);
        StoreHelper.updateAuthDateCreatedAt(fileName);
        StoreHelper.updateOrderEntityId(fileName);
        StoreHelper.updatePromoCode(fileName);

        String endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
            endPt,
            fileName,
            tUtil.getToken(ucid)
        ).extract().response();

        tUtil.putToSession("response", expResp);
    }

    @Then("I do a POST Store request to create NEJM single order")
    public void iDoAPOSTStoreRequestToCreateNEJMSingleOrder() throws Exception {
        String fileName = StoreJsonBodyFile.STORE_NEJM_SINGLE_ORDER;
        String ucid = (String) tUtil.getFromSession("ucid");

        StoreHelper.updateCustomerFNameLNameEmail(fileName);
        StoreHelper.updateAuthDateCreatedAt(fileName);
        StoreHelper.updateOrderEntityId(fileName);
        StoreHelper.updatePromoCode(fileName);

        String endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
            endPt,
            fileName,
            tUtil.getToken(ucid)
        ).extract().response();

        tUtil.putToSession("response", expResp);
    }

    @Then("User check Literatum Identity")
    public void userCheckLiteratumIdentity() throws InterruptedException {
        String ucid = (String) tUtil.getFromSession("ucid");
        String endPt = literatumGETSteps.setEndpointIdentity(ucid);

        int timeOutMin = 5;
        do {
            System.out.println("Waiting for request Literatum Identity....");
            TimeUnit.SECONDS.sleep(10);

            literatumResp = literatumGETSteps.get(endPt)
                .statusCode(ResponseCode.OK)
                .extract()
                .response();

            if (verifyLicenseAndTag(literatumResp)) break;

            timeOutMin--;
        } while (timeOutMin != 0);
       
        softAssert.assertThat(verifyLicenseAndTag(literatumResp)).isTrue();
    }

    @Then("User check NEJM Literatum Identity")
    public void userCheckNEJMLiteratumIdentity() throws InterruptedException {
        String ucid = (String) tUtil.getFromSession("ucid");
        String endPt = nejmLiteratumSystemGET.setEndpontIdentitiesUCID(ucid);

        int timeOutMin = 1;
        do {
            System.out.println("Waiting for request Literatum Identity....");
            TimeUnit.SECONDS.sleep(5);

            literatumResp = nejmLiteratumSystemGET.get(endPt)
                .statusCode(ResponseCode.OK)
                .extract()
                .response();

            if (verifyLicenseAndTag(literatumResp)) break;

            timeOutMin--;
        } while (timeOutMin != 0);
        assertTrue(verifyLicenseAndTag(literatumResp));
    }

    @Then("User check Literatum License")
    public void userCheckLiteratumLicense() throws InterruptedException {
        String ucid = (String) tUtil.getFromSession("ucid");
        String endPt = literatumGETSteps.setEndpointLicenses(ucid);

        int timeOutMin = 5;
        do {
            System.out.println("Waiting for request Literatum License....");
            TimeUnit.SECONDS.sleep(5);

            literatumResp = literatumGETSteps.get(endPt)
                .statusCode(ResponseCode.OK)
                .extract()
                .response();
            if (literatumResp.jsonPath().getString("code").length() != 2) break;

            timeOutMin--;
        } while (timeOutMin != 0);
        softAssert.assertThat(literatumResp.jsonPath().getString("code").length()).isNotEqualTo(2);
    }

    @Then("User check NEJM Literatum License")
    public void userCheckNEJMLiteratumLicense() throws InterruptedException {
        String ucid = (String) tUtil.getFromSession("ucid");
        String endPt = nejmLiteratumSystemGET.setEndpontIdentitiesUCIDLicences(ucid);

        int timeOutMin = 18;
        do {
            System.out.println("Waiting for request Literatum License....");
            TimeUnit.SECONDS.sleep(5);

            literatumResp = nejmLiteratumSystemGET.get(endPt)
                .statusCode(ResponseCode.OK)
                .extract()
                .response();
            if (literatumResp.jsonPath().getString("code").length() != 2) break;

            timeOutMin--;
        } while (timeOutMin != 0);
        assertNotEquals(literatumResp.jsonPath().getString("code").length(), 2);
    }

    @Then("User check ICV audienceType is subscriber")
    public void userCheckICVAudienceTypeIsSubscriber() throws URISyntaxException, InterruptedException {
        String email = (String) tUtil.getFromSession("email");
        String endPt = icvGETSteps.setEndpoint(email);
        int timeOutMin = 5;

        do {
            System.out.println("Waiting for request ICV...");
            TimeUnit.SECONDS.sleep(5);

            icvResp = icvGETSteps.getUsers(endPt)
                .statusCode(ResponseCode.OK)
                .extract()
                .response();
            String typeICV = icvResp.jsonPath().getString("type");

            if (typeICV.equalsIgnoreCase("subscriber")) break;

            timeOutMin--;
        } while (timeOutMin != 0);
    }


    @When("^User calls Akamai to check alternateID$")
    public void userCallsAkamai() throws Throwable {
        String ucid = (String) tUtil.getFromSession("ucid");
        String endPt = aicGETSteps.setEndpointUserID(ucid);

        int timeOutMin = 5;
        do {
            System.out.println("Waiting for request Akamai...");
            TimeUnit.SECONDS.sleep(10);
            aicResp = aicGETSteps.getUserFromAkamai(endPt)
                .statusCode(ResponseCode.OK)
                .extract()
                .response();
            
            if (aicGETSteps.verifyCJAudienceType(aicResp)) break;
            
            timeOutMin--;
        } while (timeOutMin != 0);
        softAssert.assertThat(aicGETSteps.verifyCJAudienceType(aicResp)).isTrue();
    }


    @Then("User calls ACS to check order")
    public void userCallsACSToCheckOrder() throws URISyntaxException, InterruptedException {
        String email = (String) tUtil.getFromSession("email");
        String endPt = acsGETSteps.setEndpoint();

        int timeOutMin = 5;
        do {
            System.out.println("Waiting for request ACS to get finished");
            TimeUnit.SECONDS.sleep(5);

            acsResp = acsGETSteps.getCustomers(endPt, "email", email)
                .statusCode(ResponseCode.OK)
                .extract().response();

            Object custNum = acsResp.jsonPath().getList("customerNumber").get(0);

            endPt = acsGETSteps.setCustomerAgreementsEndpoint((String) custNum);
            acsResp = acsGETSteps.get(endPt).extract().response();
            int agreements = acsResp.jsonPath().getList("agreements").size();
            if (agreements == 1 && custNum != null) break;

            timeOutMin--;
        } while (timeOutMin != 0);
   
        softAssert.assertAll();
        resetSoftAssert();
       
    }

    @Then("^I do a POST Store request to create Catalyst multi order$")
    public void iDoAPOSTStoreRequestToCreateCatalystMultiOrder() throws Exception {
        String fileName = StoreJsonBodyFile.STORE_CATALYST_MULTI_ORDER;
        String ucid = (String) tUtil.getFromSession("ucid");

        StoreHelper.updateCustomerFNameLNameEmail(fileName);
        StoreHelper.updateAuthDateCreatedAt(fileName);
        StoreHelper.updateOrderEntityId(fileName);
        StoreHelper.updatePromoCode(fileName);

        String endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
            endPt,
            fileName,
            tUtil.getToken(ucid)
        ).extract().response();

        tUtil.putToSession("response", expResp);
    }

    @Then("^I do a POST Store request to create NEJM multi order$")
    public void iDoAPOSTStoreRequestToCreateNEJMMultiOrder() throws Exception {
        String fileName = StoreJsonBodyFile.STORE_NEJM_MULTI_ORDER;
        String ucid = (String) tUtil.getFromSession("ucid");

        StoreHelper.updateCustomerFNameLNameEmail(fileName);
        StoreHelper.updateAuthDateCreatedAt(fileName);
        StoreHelper.updateOrderEntityId(fileName);
        StoreHelper.updatePromoCode(fileName);

        String endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
            endPt,
            fileName,
            tUtil.getToken(ucid)
        ).extract().response();

        tUtil.putToSession("response", expResp);
    }

    @Then("I do a POST Store request to create Catalyst comp")
    public void iDoAPOSTStoreRequestToCreateCatalystComp() throws Exception {
        String fileName = StoreJsonBodyFile.STORE_CATALYST_COMP;
        String ucid = (String) tUtil.getFromSession("ucid");

        StoreHelper.updateCustomerFNameLNameEmail(fileName);
        StoreHelper.updateAuthDateCreatedAt(fileName);
        StoreHelper.updateOrderEntityId(fileName);
        StoreHelper.updatePromoCode(fileName);

        String endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
            endPt,
            fileName,
            tUtil.getToken(ucid)
        ).extract().response();

        tUtil.putToSession("response", expResp);
    }

    @Then("I do a POST Store request to create Catalyst and NEJM multi line order")
    public void iDoAPOSTStoreRequestToCreateCatalystAndNEJMMultiLineOrder() throws Exception {
        String fileName = StoreJsonBodyFile.STORE_CATALYST_NEJM_MULTI_ORDER;
        String ucid = (String) tUtil.getFromSession("ucid");

        StoreHelper.updateCustomerFNameLNameEmail(fileName);
        StoreHelper.updateAuthDateCreatedAt(fileName);
        StoreHelper.updateOrderEntityId(fileName);
        StoreHelper.updatePromoCode(fileName);

        String endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
            endPt,
            fileName,
            tUtil.getToken(ucid)
        ).extract().response();

        tUtil.putToSession("response", expResp);
    }

    @Then("I do a POST Store request to create Catalyst with PayPal wallet")
    public void iDoAPOSTStoreRequestToCreateCatalystWithPayPalWallet() throws Exception {
        String fileName = StoreJsonBodyFile.STORE_CATALYST_PAYPAL_WALLET_ORDER;
        String ucid = (String) tUtil.getFromSession("ucid");

        StoreHelper.updateCustomerFNameLNameEmail(fileName);
        StoreHelper.updateAuthDatePaymentDate(fileName);
        StoreHelper.updateOrderEntityId(fileName);
        StoreHelper.updatePromoCode(fileName);

        String endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
            endPt,
            fileName,
            tUtil.getToken(ucid)
        ).extract().response();

        tUtil.putToSession("response", expResp);
    }

    @Then("I do a POST Store request to create Catalyst with Credit Card")
    public void iDoAPOSTStoreRequestToCreateCatalystWithCreditCard() throws Exception {
        String fileName = StoreJsonBodyFile.STORE_CATALYST_CREDIT_CARD_ORDER;
        String ucid = (String) tUtil.getFromSession("ucid");

        StoreHelper.updateCustomerFNameLNameEmail(fileName);
        StoreHelper.updateAuthDateCreatedAt(fileName);
        StoreHelper.updateOrderEntityId(fileName);
        StoreHelper.updatePromoCode(fileName);

        String endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
            endPt,
            fileName,
            tUtil.getToken(ucid)
        ).extract().response();

        tUtil.putToSession("response", expResp);
    }

    @Then("I do a POST Store request to create Catalyst with Send Invoice")
    public void iDoAPOSTStoreRequestToCreateCatalystWithSendInvoice() throws Exception {
        String fileName = StoreJsonBodyFile.STORE_CATALYST_SEND_INVOICE_ORDER;
        String ucid = (String) tUtil.getFromSession("ucid");

        StoreHelper.updateCustomerFNameLNameEmail(fileName);
        StoreHelper.updateCreatedAt(fileName);
        StoreHelper.updateOrderEntityId(fileName);
        StoreHelper.updatePromoCode(fileName);

        String endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
            endPt,
            fileName,
            tUtil.getToken(ucid)
        ).extract().response();

        tUtil.putToSession("response", expResp);
    }

    @Then("I do a POST Store request to create Catalyst multi order with FREE")
    public void iDoAPOSTStoreRequestToCreateCatalystMultiOrderWithFREE() throws Exception {
        String fileName = StoreJsonBodyFile.STORE_CATALYST_MULTI_ORDER_WITH_FREE;
        String ucid = (String) tUtil.getFromSession("ucid");

        StoreHelper.updateCustomerFNameLNameEmail(fileName);
        StoreHelper.updateAuthDateCreatedAt(fileName);
        StoreHelper.updateOrderEntityId(fileName);
        StoreHelper.updatePromoCode(fileName);

        String endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
            endPt,
            fileName,
            tUtil.getToken(ucid)
        ).extract().response();

        tUtil.putToSession("response", expResp);
    }

    @Then("I do a POST Store request to create NEJM with PayPal wallet")
    public void iDoAPOSTStoreRequestToCreateNEJMWithPayPalWallet() throws Exception {
        String fileName = StoreJsonBodyFile.STORE_NEJM_PAYPAL_WALLET_ORDER;
        String ucid = (String) tUtil.getFromSession("ucid");

        StoreHelper.updateCustomerFNameLNameEmail(fileName);
        StoreHelper.updateAuthDatePaymentDate(fileName);
        StoreHelper.updateOrderEntityId(fileName);
        StoreHelper.updatePromoCode(fileName);

        String endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
            endPt,
            fileName,
            tUtil.getToken(ucid)
        ).extract().response();

        tUtil.putToSession("response", expResp);
    }

    @Then("I do a POST Store request to create NEJM with Credit Card")
    public void iDoAPOSTStoreRequestToCreateNEJMWithCreditCard() throws Exception {
        String fileName = StoreJsonBodyFile.STORE_CATALYST_CREDIT_CARD_ORDER;
        String ucid = (String) tUtil.getFromSession("ucid");

        StoreHelper.updateCustomerFNameLNameEmail(fileName);
        StoreHelper.updateAuthDateCreatedAt(fileName);
        StoreHelper.updateOrderEntityId(fileName);
        StoreHelper.updatePromoCode(fileName);

        String endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
            endPt,
            fileName,
            tUtil.getToken(ucid)
        ).extract().response();

        tUtil.putToSession("response", expResp);
    }
    
    

    @Then("I do a POST Store request to create NEJM with Send Invoice")
    public void iDoAPOSTStoreRequestToCreateNEJMWithSendInvoice() throws Exception {
        String fileName = StoreJsonBodyFile.STORE_NEJM_SEND_INVOICE_ORDER;
        String ucid = (String) tUtil.getFromSession("ucid");

        StoreHelper.updateCustomerFNameLNameEmail(fileName);
        StoreHelper.updateCreatedAt(fileName);
        StoreHelper.updateOrderEntityId(fileName);
        StoreHelper.updatePromoCode(fileName);

        String endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
            endPt,
            fileName,
            tUtil.getToken(ucid)
        ).extract().response();

        tUtil.putToSession("response", expResp);
    }

    @Then("I do a POST Store request to create Catalyst Admin single order")
    public void iDoAPOSTStoreRequestToCreateCatalystAdminSingleOrder() throws Exception {
        String fileName = StoreJsonBodyFile.STORE_CATALYST_ADMIN_SINGLE_ORDER;
        String ucid = (String) tUtil.getFromSession("ucid");

        StoreHelper.updateCustomerFNameLNameEmail(fileName);
        StoreHelper.updateCreatedAt(fileName);
        StoreHelper.updateOrderEntityId(fileName);
        StoreHelper.updatePromoCode(fileName);

        String endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
            endPt,
            fileName,
            tUtil.getToken(ucid)
        ).extract().response();

        tUtil.putToSession("response", expResp);
    }
    
    
    @When("^I do a POST Store request to create order using CC with user$")
    public void iDoAPOSTStoreRequestToCreateOrderUsingCCWithUser(DataTable dataTable) throws Exception {
        
        Map<String, String> row = CucumberUtils.convert(dataTable);
        ucid = (String) tUtil.getFromSession("ucid");
        kmap = StoreHelper.updateKmapFromSession();

        updateStoreFiles.updateCustomerData(
                StoreJsonBodyFile.STORE_CATALYST_CREDIT_CARD_ORDER, kmap.get("firstName"), kmap.get("lastName"),
                (String) tUtil.getFromSession("email"), row
        );
        Map<String, String> map = updateStoreFiles.update_cc_annual_file(
                StoreJsonBodyFile.STORE_CATALYST_CREDIT_CARD_ORDER, row);
        
        kmap.putAll(map);

        endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
                endPt, StoreJsonBodyFile.STORE_CATALYST_CREDIT_CARD_ORDER, (String) tUtil.getFromSession("token")).extract().response();
    }
    
    @When("^I do a POST Store request to create order using send invoice$")
    public void sendInvoice(DataTable dataTable) throws Exception {
        
        Map<String, String> row = CucumberUtils.convert(dataTable);
        ucid = (String) tUtil.getFromSession("ucid");
        kmap = StoreHelper.updateKmapFromSession();

        updateStoreFiles.updateCustomerData(
                StoreJsonBodyFile.STORE_CATALYST_SEND_INVOICE_ORDER, kmap.get("firstName"), kmap.get("lastName"),
                (String) tUtil.getFromSession("email"), row
        );
        
        Map<String, String> map = updateStoreFiles.update_invoice_file(
                StoreJsonBodyFile.STORE_CATALYST_SEND_INVOICE_ORDER, row);
        
        kmap.putAll(map);

        endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
                endPt, StoreJsonBodyFile.STORE_CATALYST_SEND_INVOICE_ORDER, (String) tUtil.getFromSession("token")).extract().response();
    }
    
    @When("^I do a POST Store request to create order using Paypal$")
    public void paypal(DataTable dataTable) throws Exception {
        
        Map<String, String> row = CucumberUtils.convert(dataTable);
        ucid = (String) tUtil.getFromSession("ucid");
        kmap = StoreHelper.updateKmapFromSession();

        updateStoreFiles.updateCustomerData(
                StoreJsonBodyFile.STORE_CATALYST_PAYPAL_WALLET_ORDER, kmap.get("firstName"), kmap.get("lastName"),
                (String) tUtil.getFromSession("email"), row
        );
        
        Map<String, String> map = updateStoreFiles.update_Paypal(
                StoreJsonBodyFile.STORE_CATALYST_PAYPAL_WALLET_ORDER, row);
        
        kmap.putAll(map);

        endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
                endPt, StoreJsonBodyFile.STORE_CATALYST_PAYPAL_WALLET_ORDER, (String) tUtil.getFromSession("token")).extract().response();
    }
    
    @When("^I do a POST Store data request to create order using Paypal with following$")
    public void paypaldataorder(DataTable dataTable) throws Exception {
        
        Map<String, String> row = CucumberUtils.convert(dataTable);
        ucid = (String) tUtil.getFromSession("ucid");
        kmap = StoreHelper.updateKmapFromSession();

        updateStoreFiles.updateCustomerData(
                StoreJsonBodyFile.STORE_CATALYST_PAYPAL_WALLET_ORDER, kmap.get("firstName"), kmap.get("lastName"),
                (String) tUtil.getFromSession("email"), row);
        
        Map<String, String> map = updateStoreFiles.update_Paypal(
                StoreJsonBodyFile.STORE_CATALYST_PAYPAL_WALLET_ORDER, row);
        
        kmap.putAll(map);

        endPt = storePost.setEndpointOrder(ucid);
        expResp = storePost.postStore_withToken(
                endPt, StoreJsonBodyFile.STORE_CATALYST_PAYPAL_WALLET_ORDER, (String) tUtil.getFromSession("token")).extract().response();
    }
    
    @And("^created order exists$")
    public void orderShouldBeCreated() {
        Assert.assertEquals(expResp.statusCode(), ResponseCode.CREATED);
    }
    
    
    @Then("^order should exist in ACS$")
    public void orderShouldExistInAcs() throws Throwable {

        tUtil.writeOrdersemailUcid(kmap);
        tUtil.writeExcelFile(kmap, "ExcelFile.xlsx");
    }
    
    public void resetSoftAssert () {
        softAssert = new SoftAssertions();  
        
    }
}
