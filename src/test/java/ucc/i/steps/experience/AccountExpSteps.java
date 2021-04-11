package ucc.i.steps.experience;

import com.google.common.collect.Comparators;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import ucc.i.method.accountexp.AccountExpGET;
import ucc.i.method.accountexp.AccountExpPATCH;
import ucc.i.method.accountexp.AccountExpPOST;
import ucc.i.method.accountexp.AccountHelper;
import ucc.i.method.accountexp.UpdateAccountFiles;
import ucc.i.method.acs.ACSGET;
import ucc.i.method.aic.AICGET;
import ucc.i.method.aic.AICPOST;
import ucc.i.method.aic.AICPUT;
import ucc.i.method.catalystLiteratum.CatalystLiteratumSystemGET;
import ucc.i.method.catalystLiteratum.CatalystLiteratumSystemPOST;
import ucc.i.method.cjexp.CJExpGET;
import ucc.i.method.customerprocess.CustomerProcessGET;
import ucc.i.method.customerprocess.CustomerProcessPOST;
import ucc.i.method.eventprocess.EventProcessGET;
import ucc.i.method.icv.ICVGET;
import ucc.i.method.icv.ICVPOST;
import ucc.i.method.nejmliteratumsystem.NEJMLiteratumSystemGET;
import ucc.i.method.nejmliteratumsystem.NEJMLiteratumSystemPOST;
import ucc.i.method.store.StoreGET;
import ucc.i.method.store.StorePost;
import ucc.i.steps.system.CatalystLiteratumSystemJsonBodyFile;
import ucc.i.steps.system.NEJMLiteratumSystemJsonBodyFile;
import ucc.pages.ui.CommonFunc;
import ucc.utils.CucumberUtils.CucumberUtils;
import ucc.utils.ConnectionFactory;
import ucc.utils.DbUtils;
import ucc.utils.JsonUtils;
import ucc.utils.ResponseCode;
import ucc.utils.SubscriptionStatus;
import ucc.utils.TestUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class AccountExpSteps {
    private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    private static String autoEmail =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("autoEmail");
    public static String token;
    private static Response response = null;
    private static Response expResp = null;
    private static Response procResp = null;
    private static Response sysResp = null;
    private Response literRespIdentity;
    private Response literRespLicense;
    private static String endPoint = null;
    CommonFunc commonFunc = new CommonFunc();
    private static TestUtils tUtil = new TestUtils();
    private static JsonUtils jsonUtils = new JsonUtils();
    private final DbUtils dbUtils = new DbUtils();
    private List<String> subsIdList = new ArrayList<>();
    // CHECKSTYLE:OFF: VisibilityModifier
    public static Map<String, String> kmap = new HashMap<String, String>();
    public static Set<Map<String, String>> orderSet = new LinkedHashSet<>();
    public Map<String, String> customerMap = new LinkedHashMap<>();
    // CHECKSTYLE:ON: VisibilityModifier
    private String email = null;
    private static String ucid = null;
    private String subscrptnID;
    private static String firstName = null; 
    private static String lastName = null; 
    private static final int TIMEOUT = 7;
    SoftAssertions softAssert = new SoftAssertions();
    UpdateAccountFiles updateAcc = new UpdateAccountFiles();
    AccountHelper accHelper = new AccountHelper();
    
    /*
     * Values in kmap:
     * ucid -- user's ucid
     * icvId -- id in icv
     * email -- user's email
     * lastName -- user's last name
     * firstName -- user's first name
     * audienceType -- audience type
     * eventId -- id of event registered for
     */
    
    
    
    @Steps
    AccountExpGET accExpGETSteps;
    @Steps
    AccountExpPOST accExpPOSTSteps;
    @Steps
    AccountExpPATCH accExpPATCHSteps;
    @Steps
    CustomerProcessGET custProcGETSteps;
    @Steps
    CustomerProcessPOST custProcPOSTSteps;
    @Steps
    AICGET aicGETSteps;
    @Steps
    AICPOST aicPOSTSteps;
    @Steps
    AICPUT aicPUTSteps;
    @Steps
    EventProcessGET eventProcGETSteps;
    @Steps
    ICVGET icvGETSteps;
    @Steps
    ICVPOST icvPOSTSteps;
    @Steps
	StorePost storePost;
    @Steps
    StoreGET storeGET;
    @Steps
    CJExpGET cjExpGETSteps;
    @Steps
	NEJMLiteratumSystemGET nejmLitrtmSystemGET;
    @Steps
    CatalystLiteratumSystemPOST catJLSystmPOST;
    @Steps
    CatalystLiteratumSystemGET catJLSystmGET;
    @Steps
	NEJMLiteratumSystemPOST nejmLiteratumSystemPOST;
    @Steps
    ACSGET acsGETSteps;
    
    private boolean expRespTagLabel = false;
	private boolean expRespTagCode = false;
	private boolean expRespTagSetCode = false;
    
    
    
    // Files used
    private final String filePostFullRegisterForEvent = "AccExp_POST_Register_For_Event.json";
    private final String filePostRegisterForEventUcid = "AccExp_POST_Register_For_Event_Ucid.json";
    private final String filePostRegistrationExtra = "AccExp_POST_Registration_Extra.json";
    private final String fileTicket = "Account_POST_tickets.json";
    private final String filePostRegisterContext = "AccExp_POST_User_Context.json";
    private final String filePostRegisterUser = "MyAccExp_POST_Reg_User.json";
    private final String filePostRegCatEvent = "AccExp_POST_custRegCatalystEvent.json";
    private final String registeredUserNEJM = "AccExp/registeredUserNEJM.json";
    private final String PATCHregisteredUser = "AccExp/PATCHregisteredUser.json";
    private final String activateUserviaMyAccExp = "AccExp/activateUserviaMyAccExp.json";
    private final String paymentDetails = "AccExp/AccExp_PATCH_payment_details.json";

    
   
    @When("I do POST activate via MyAccExp with wrong lastName")
    public void i_do_post_activate_via_my_acc_exp_with_wrong_last_name() 
    		throws Exception {
    	
    	tUtil.putToSession("lastName", tUtil.AppendTimestamp("lastName"));
    	
    	updateAcc.update_jsonFile_for_activate(activateUserviaMyAccExp);

    	token = tUtil.getToken((String) tUtil.getFromSession("ucid"));

    	endPoint = accExpPOSTSteps.setEndpoint_activate((String) tUtil.getFromSession("ucid"));
    	expResp = accExpPOSTSteps.postToken(activateUserviaMyAccExp, endPoint, token)
    			.extract().response();

    	tUtil.putToSession("response", expResp);
    }
    
    @When("I do POST activate to the same user via MyAccExp")
    public void i_do_post_activate_to_the_same_user_via_my_acc_exp() 
    		throws Exception {
    	
    	updateAcc.update_jsonFile_for_activate(activateUserviaMyAccExp);

    	token = tUtil.getToken((String) tUtil.getFromSession("ucid"));

    	endPoint = accExpPOSTSteps.setEndpoint_activate((String) tUtil.getFromSession("ucid"));
    	expResp = accExpPOSTSteps.postToken(activateUserviaMyAccExp, endPoint, token)
    			.extract().response();

    	tUtil.putToSession("response", expResp);
    }

    @When("I do POST activate via MyAccExp with wrong akamaiToken")
    public void i_do_post_activate_via_my_acc_exp_with_wrong_subscription_id() 
    		throws Exception {
    	
    	updateAcc.update_jsonFile_for_activate(activateUserviaMyAccExp);

    	token = tUtil.generateRandomUcid(10);

    	endPoint = accExpPOSTSteps.setEndpoint_activate((String) tUtil.getFromSession("ucid"));
    	expResp = accExpPOSTSteps.postToken(activateUserviaMyAccExp, endPoint, token)
    			.extract().response();

    	tUtil.putToSession("response", expResp);
    }

    @Then("^I see error message (.*) for the invalid akamaiToken and 401 statusCode$")
    public void i_see_error_message_message_for_the_invalid_akamai_token_and_status_code(String message) {

    	tUtil.verifyStatus(expResp, ResponseCode.UNAUTHORIZED);
    	Assert.assertEquals(message, expResp.jsonPath().getString("message").trim());
    }

    @Then("^I see error message (.*) for the already activated user$")
    public void i_see_error_for_the_already_activated_user(String message) {

    	tUtil.verifyStatus(expResp, ResponseCode.CREATED);
    	Assert.assertEquals(message, expResp.jsonPath().getString("[0].errors[0].errorDescription").trim());
    }

    @Then("^I see error message (.*) for the lastName not matches$")
    public void i_see_error_for_the_lastName_not_matches(String message) {

    	tUtil.verifyStatus(expResp, ResponseCode.CREATED);
    	Assert.assertEquals(message, expResp.jsonPath().getString("[0].errors[0].errorDescription"));
    }

    @When("I do POST activate via MyAccExp")
    public void i_do_post_activate_via_my_acc_exp() 
    		throws Exception {
    	
    	updateAcc.update_jsonFile_for_activate(activateUserviaMyAccExp);

    	token = tUtil.getToken((String) tUtil.getFromSession("ucid"));
    	
    	endPoint = accExpPOSTSteps.setEndpoint_activate((String) tUtil.getFromSession("ucid"));
    	expResp = accExpPOSTSteps.postToken(activateUserviaMyAccExp, endPoint, token)
    			.extract().response();
    	
    	tUtil.putToSession("response", expResp);
    }

    @When("^I do PATCH call to invalid customers_ucid via MyAccExp and update email fname lname specialtyGroup (.*)$")
    public void do_patch_call_invalid_customersUCID_via_myAccExp_and_update_emailFnameLnameSpecialtyGroup
    (String specialtyGroup) throws Exception {

    	token = (String) tUtil.getFromSession("token");
        
        email = tUtil.AppendTimestamp(autoEmail);
        firstName = tUtil.AppendTimestamp("firstName");
        lastName = tUtil.AppendTimestamp("lastName");

        jsonUtils.update_JSONValue(PATCHregisteredUser, "uuid", kmap.get("ucid"));
        jsonUtils.update_JSONValue(PATCHregisteredUser, "email", email);
        jsonUtils.update_JSONValue(PATCHregisteredUser, "firstName", firstName);
        jsonUtils.update_JSONValue(PATCHregisteredUser, "lastName", lastName);
        jsonUtils.update_JSONValue(PATCHregisteredUser, "primarySpecialty", specialtyGroup);
        
        endPoint = accExpPATCHSteps.setEndpointUpdatecustomersByUcid(kmap.get("ucid"));
        endPoint = tUtil.AppendTimestamp(endPoint);
        expResp = accExpPATCHSteps.patchUserWithJsonFile(PATCHregisteredUser, endPoint, token)
                .extract().response();
        
        tUtil.putToSession("response", expResp);
    }
    
    @Then("^I see email fname lname and specialtyGroup (.*) got updated$")
    public void i_see_specialty_group_got_updated(String specialtyGroup) {

    	response = (Response) tUtil.getFromSession("response");
    	
    	tUtil.verifyStatus(response, ResponseCode.OK);
    	
    	Assert.assertNotEquals(kmap.get("email"), response.jsonPath().getString("email"));
    	Assert.assertNotEquals(kmap.get("firstName"), response.jsonPath().getString("firstName"));
    	Assert.assertNotEquals(kmap.get("lastName"), response.jsonPath().getString("lastName"));
    	Assert.assertEquals(specialtyGroup, 
    			response.jsonPath().getString("primarySpecialty"));
    }
    
    @Then("^I see email fname lname and specialtyGroup (.*) got updated in AIC$")
    public void i_see_specialty_group_got_updated_in_AIC(String specialtyGroup) {

    	response = (Response) tUtil.getFromSession("response");
    	
    	tUtil.verifyStatus(response, ResponseCode.OK);
    	
    	Assert.assertNotEquals(kmap.get("email"), response.jsonPath().getString("email"));
    	Assert.assertNotEquals(kmap.get("firstName"), response.jsonPath().getString("firstName"));
    	Assert.assertNotEquals(kmap.get("lastName"), response.jsonPath().getString("lastName"));
    	Assert.assertEquals(specialtyGroup, 
    			response.jsonPath().getString("primarySpecialtyCode"));
    }
    
    @When("^I do PATCH call to customers_ucid via MyAccExp and update email fname lname specialtyGroup (.*)$")
    public void i_do_patch_call_to_customers_ucid_via_my_acc_exp(String specialtyGroup) 
    		throws Exception {

        token = (String) tUtil.getFromSession("token");
        
        email = tUtil.AppendTimestamp(autoEmail);
        firstName = tUtil.AppendTimestamp("firstName");
        lastName = tUtil.AppendTimestamp("lastName");

        jsonUtils.update_JSONValue(PATCHregisteredUser, "uuid", kmap.get("ucid"));
        jsonUtils.update_JSONValue(PATCHregisteredUser, "email", email);
        jsonUtils.update_JSONValue(PATCHregisteredUser, "firstName", firstName);
        jsonUtils.update_JSONValue(PATCHregisteredUser, "lastName", lastName);
        jsonUtils.update_JSONValue(PATCHregisteredUser, "primarySpecialty", specialtyGroup);
        
        endPoint = accExpPATCHSteps.setEndpointUpdatecustomersByUcid(kmap.get("ucid"));
        expResp = accExpPATCHSteps.patchUserWithJsonFile(PATCHregisteredUser, endPoint, token)
                .extract().response();
        
        tUtil.putToSession("response", expResp);
    }
    
    @When("I create RegisteredUser user via MyAccExp for NEJM")
    public void i_do_get_call_via_my_acc_exp_for_NEJM() 
    		throws Exception {

    	endPoint = accExpPOSTSteps.setEndpointCustRegNEJM();
        
        email = tUtil.AppendTimestamp(autoEmail);
        String fname = tUtil.AppendTimestamp("firstName");
        String lname = tUtil.AppendTimestamp("lastName");
        
        jsonUtils.update_JSONValue(registeredUserNEJM, "$.email", email);
        jsonUtils.update_JSONValue(registeredUserNEJM, "$.firstName", fname);
        jsonUtils.update_JSONValue(registeredUserNEJM, "$.lastName", lname);

        expResp = accExpPOSTSteps.post(registeredUserNEJM, endPoint)
        		.extract().response();  
        
        kmap.put("email", email);
        kmap.put("firstName", fname);
        kmap.put("lastName", lname);
        kmap.put("ucid", expResp.jsonPath().getString("ucid"));
        
        tUtil.putToSession("ucid", expResp.jsonPath().getString("ucid"));
        tUtil.putToSession("token", expResp.jsonPath().getString("authenticationToken"));

        tUtil.putToSession("response", expResp);
    }
    
    @When("user sends a GET request to MyAccExp using ucid")
    public void user_sends_a_get_request_to_MyAccExp_using_ucid() {

    	token = (String)tUtil.getFromSession("token");
    	
    	endPoint = accExpGETSteps.setEndpointCustomersUCID((String)tUtil.getFromSession("ucid"));
    	expResp = accExpGETSteps.getUserByUcid(endPoint, token)
    			.extract().response();
    	
    	tUtil.putToSession("primarySpecialty", expResp.jsonPath().get("primarySpecialty"));
    	
    	tUtil.putToSession("response", expResp);
    }
    
    @When("user sends a GET request to MyAccExp using invalid ucid")
    public void user_sends_a_get_request_to_my_acc_exp_using_invalid_ucid() {

    	token = (String)tUtil.getFromSession("token");
    	
    	endPoint = accExpGETSteps.setEndpointCustomersUCID((String)tUtil.getFromSession("ucid"));
    	endPoint = tUtil.AppendTimestamp(endPoint);
    	expResp = accExpGETSteps.getUserByUcid(endPoint, token)
    			.extract().response();
    	
    	tUtil.putToSession("response", expResp);
    }
    
    @Then("I see nejmInstitutionalAdmin and nejmMediaAccess in the response")
    public void i_see_nejm_institutional_admin_and_nejm_media_access_in_the_response() {
 
    	response = (Response) tUtil.getFromSession("response");
    	Assert.assertNotNull(response.jsonPath().getString("Nejm.mediaAccess"));
    	Assert.assertNull(response.jsonPath().getString("Nejm.institutionalAdmin"));
    }
    
    @When("user sends a GET request to AIC using ucid")
    public void user_sends_a_get_request_to_aic_using_ucid() {

    	endPoint = aicGETSteps.setEndpointUserID(kmap.get("ucid"));
        sysResp = aicGETSteps.get(endPoint)
        		.extract().response();
        
        tUtil.putToSession("response", sysResp);
    }
    
    @Then("^audienceType of user should be LEAD$")
    public void iCheckLEADDataForExpAPI() {
    	
        assertEquals(expResp.jsonPath().getString("audienceType"), "LEAD");
    }
    
    @Then("^audienceType of user should be REGISTERED USER$")
    public void iCheckREGISTERUserDataForExpAPI() {
    	
        assertEquals(expResp.jsonPath().getString("audienceType"), "REGISTERED USER");
    }

    @When("^I do a POST call to /registration to register a user as a REGISTERED USER$") //this user will become REGISTERED USER and this user is qual to IC
    public void iDoAPOSTCallTo_reg_RegisteredUser_qualToIC() throws Exception {

        String registration_qualtoIC_RegisteredUser = "registration_qualtoIC_RegisteredUser.json";
    	endPoint = accExpPOSTSteps.setEndpontCustRegCatalyst();
        
        email = tUtil.AppendTimestamp(autoEmail);
        String fname = tUtil.AppendTimestamp("firstName");
        String lname = tUtil.AppendTimestamp("lastName");
        
        jsonUtils.update_JSONValue(registration_qualtoIC_RegisteredUser, "$.email", email);
        jsonUtils.update_JSONValue(registration_qualtoIC_RegisteredUser, "$.firstName", fname);
        jsonUtils.update_JSONValue(registration_qualtoIC_RegisteredUser, "$.lastName", lname);

        expResp = accExpPOSTSteps.post(registration_qualtoIC_RegisteredUser, endPoint)
        		.extract().response();        
        kmap.put("email", email);
        kmap.put("firstName", fname);
        kmap.put("lastName", lname);
        kmap.put("ucid", expResp.jsonPath().getString("ucid"));
        
        tUtil.putToSession("ucid", expResp.jsonPath().getString("ucid"));
        tUtil.putToSession("email", email);
        tUtil.putToSession("firstName", fname);
        tUtil.putToSession("lastName", lname);
        tUtil.putToSession("token", expResp.jsonPath().getString("authenticationToken"));
        tUtil.putToSession("response", expResp);
    }
    
   @When("^I do a POST call to customers_registrations_catalyst_email to register a user as a LEAD$") //this user will become LEAD
    public void iDoAPOSTCallTo_regCatEmail() throws Exception {
        String customers_registrations_catalyst_email_mandatoryFieldsOnly = "AccExp_POST_custRegCatEmailmandatoryFieldsOnly.json";
        endPoint = accExpPOSTSteps.setEndpontCustRegEmail();
        email = tUtil.AppendTimestamp(autoEmail);
        jsonUtils.update_JSONValue(customers_registrations_catalyst_email_mandatoryFieldsOnly, "$.email", email);

        expResp = accExpPOSTSteps.post(customers_registrations_catalyst_email_mandatoryFieldsOnly, endPoint)
        		.extract().response();
        kmap.put("email", email);
        kmap.put("ucid", expResp.jsonPath().getString("ucid"));
    }
  
    @Then("^I see user is registered to CATALYST$")
	  public void iSeeUserIsRegisteredToCATALYST() throws URISyntaxException, IOException {
    	Assert.assertEquals("CATALYST", sysResp.jsonPath().getString("originalBrand"));
    }
    
    @When("^I do a POST call to customers_registrations_catalyst to register user as a LEAD$")
    public void iDoAPOSTCallTocustomers_registrations_catalyst() throws Exception {
        String customers_registrations_catalyst = "AccExp_POST_customers_registrations_catalyst.json";
    	email = tUtil.AppendTimestamp(autoEmail);
    	jsonUtils.update_JSONValue(customers_registrations_catalyst, "email", email);
    	endPoint = accExpPOSTSteps.setEndpontCustRegCatalyst();
    	
    	expResp = accExpPOSTSteps.post(customers_registrations_catalyst, endPoint)
    			.extract().response();
    	
    	kmap.put("email", email);
    	kmap.put("ucid", expResp.jsonPath().get("ucid"));
    	kmap.put("token", expResp.jsonPath().get("authenticationToken"));
    }

    @Then("^I see user is registered to an EVENT$")
	public void iSeeUserIsRegisteredToEVENT() throws URISyntaxException, IOException {
    	Assert.assertEquals("1", sysResp.jsonPath().getString("Catalyst.events[0].eventID"));
    }

    @When("^I do a POST call to cust_regist_catalyst_pdf to register a user as a LEAD$") //this user will become LEAD
    public void iDoAPOSTCallTo_cust_regist_catalyst_pdf() throws Exception {
        String cust_regist_catalyst_pdf_onlyMANDAtoryfields = "AccExp_POST_cust_regist_catalyst_pdf_onlyMANDAtoryfields.json";
        endPoint = accExpPOSTSteps.setEndpontCustRegPDF();
        email = tUtil.AppendTimestamp(autoEmail);
        jsonUtils.update_JSONValue(cust_regist_catalyst_pdf_onlyMANDAtoryfields, "$.email", email);

        expResp = accExpPOSTSteps.post(cust_regist_catalyst_pdf_onlyMANDAtoryfields, endPoint)
        		.extract().response();

        kmap.put("email",email);
        kmap.put("ucid", expResp.jsonPath().get("ucid"));
        kmap.put("accessToken", expResp.jsonPath().get("authenticationToken"));
    }
   
    @When("^I do a POST call to customers_event_eventId to register a user to an EVENT with only mandatory fields$") //this user will become LEAD
    public void iDoAPOSTCallTo_customers_event_eventId() throws Exception {
        String customers_event_eventId_mandatoryFieldsOnly = "AccExp_POST_customers_event_eventId_mandatoryFieldsOnly.json";
        endPoint = accExpPOSTSteps.setEndpontCustRegCatalystEvent() + "1";
        email = tUtil.AppendTimestamp(autoEmail);
        jsonUtils.update_JSONValue(customers_event_eventId_mandatoryFieldsOnly, "$.email", email);

        expResp = accExpPOSTSteps.post(customers_event_eventId_mandatoryFieldsOnly, endPoint)
        		.extract().response();
        
        kmap.put("email", email);
        kmap.put("ucid", expResp.jsonPath().getString("uuid"));
        kmap.put("token", expResp.jsonPath().getString("authenticationToken"));
    }

    @When("^I do a POST call to customers_insightsCouncil to register a user as a LEAD with only mandatory fields$")
    public void iDoAPOSTCallTo_customers_insightsCouncil() throws Exception {
        String customers_insightsCouncil_onlyMANDATORYfields = "AccExp_POST_customers_insightsCouncil_onlyMANDATORYfields.json";
        endPoint = accExpPOSTSteps.setEndpontCustRegCatalyst_IC();
        jsonUtils.update_JSONValue(customers_insightsCouncil_onlyMANDATORYfields, "$.email", kmap.get("email"));
        jsonUtils.update_JSONValue(customers_insightsCouncil_onlyMANDATORYfields, "$.ucid", kmap.get("ucid"));

        expResp = accExpPOSTSteps.postToken(customers_insightsCouncil_onlyMANDATORYfields ,endPoint ,token)
        		.extract().response();
    }

    @When("User send a POST request to Catalyst to create a qual IC LEAD user")
    public void userSendAPOSTRequestToCatalyst() throws Exception {
        String customersRegistrationsCatalystqualtoICLEADuser = "AccExp_POST_custRegCat_qual_IC_LEAD.json";
    	email = tUtil.AppendTimestamp(autoEmail);
    	jsonUtils.update_JSONValue(customersRegistrationsCatalystqualtoICLEADuser, "email", email);
    	jsonUtils.modify_JSONAppendTimestampfunction(customersRegistrationsCatalystqualtoICLEADuser, "firstName", "firstName");
    	jsonUtils.modify_JSONAppendTimestampfunction(customersRegistrationsCatalystqualtoICLEADuser, "lastName", "lastName");
    	
        endPoint = accExpPOSTSteps.setEndpontCustRegCatalyst();
        expResp = accExpPOSTSteps.post(customersRegistrationsCatalystqualtoICLEADuser, endPoint)
                .extract().response();

        kmap.put("email", email);
        kmap.put("ucid", expResp.jsonPath().get("ucid"));
        token = expResp.jsonPath().get("authenticationToken");
    }
    
    @When("^I do a POST call to StoreExp to create an order$")
    public void iDoAPOSTCallToCreateAnOrder() throws Exception {
        String StoreOrderwithUCID = "Store/store_orderCall_withUCID.json";
        endPoint = storePost.setEndpointOrder(kmap.get("ucid"));
        jsonUtils.update_JSONValue(StoreOrderwithUCID, "$.customer.email", kmap.get("email"));
        jsonUtils.update_JSONValue(StoreOrderwithUCID, "$.customer.firstName", kmap.get("firstName"));
        jsonUtils.update_JSONValue(StoreOrderwithUCID, "$.customer.lastName", kmap.get("lastName"));
        jsonUtils.update_JSONValue(StoreOrderwithUCID, "$.customer.ucId", kmap.get("ucid"));

        expResp = storePost.postStore_withToken(endPoint, StoreOrderwithUCID, token)
                .extract().response();
    }

    @Then("^I see insightsCouncilJoinDate (.*) field in response body$")
    public void IseeInsightsCouncilJoinDateInResponse(String insightsCouncilJoinDate) {
    	Assert.assertFalse(expResp.jsonPath().get(insightsCouncilJoinDate).toString().isEmpty());
    }
    
    @When("^I send a POST request to customersRegistrationsCatalystInsightsCouncil and join insightCouncil$")
    public void userJoinsToInsighCouncilWith_customersRegistrationsCatalystInsightsCouncil() throws Exception {
        String customersRegistrationsCatalystInsightCouncil = "AccExp_POST_custRegCatInsightCouncil.json";
    	jsonUtils.update_JSONValue(customersRegistrationsCatalystInsightCouncil, "ucid", kmap.get("ucid"));
    	jsonUtils.update_JSONValue(customersRegistrationsCatalystInsightCouncil, "email", email);
    	endPoint = accExpPOSTSteps.setEndpoint_customersRegistrationsCatalystInsightCouncil();
    	
    	expResp = accExpPOSTSteps.postToken(customersRegistrationsCatalystInsightCouncil, endPoint, token)
    			.extract().response();

    }
    
    @When("^user sends a GET request with email to retrieve customer info through AIC System API$")
    public void userSendsAGETRequestWithEmailToRetrieveUserInfoThroughAICSystemAPI() {
        endPoint = aicGETSteps.setEndPointEmail(kmap.get("email"));
        sysResp = aicGETSteps.getUserFromAkamai(endPoint)
                .extract().response();

        kmap.put("ucid", sysResp.jsonPath().getString("uuid"));
        kmap.put("lastName", sysResp.jsonPath().getString("lastName"));
        kmap.put("firstName", sysResp.jsonPath().getString("firstName"));
        tUtil.putToSession("response", sysResp);
    }
    
	@When("I send a POST request to join IC")
	public void i_send_a_POST_request_to_join_IC() throws Exception {
        String filePostCustRegCat_join_IC = "AccExp_POST_custRegCat_join_IC.json";
		jsonUtils.update_JSONValue(filePostCustRegCat_join_IC, "$.ucid", kmap.get("ucid"));
		System.out.println(kmap.get("ucid") + "kmapgetucid");
		endPoint = accExpPOSTSteps.setEndpontCustRegCatalyst_IC();
		
		expResp = accExpPOSTSteps.post(filePostCustRegCat_join_IC, endPoint)
				.extract().response();
	}
	  
	@When("^user sends a POST request to customers/registrations/catalyst/event/ for registration of an event for Subscriber$")
	public void userSendsAPostRequestTocustomerRegistrationCatalystEventforSubscriber() throws Exception {
		jsonUtils.update_JSONValue(filePostRegCatEvent, "$.ucid", kmap.get("ucid"));
		jsonUtils.update_JSONValue(filePostRegCatEvent, "$.email", kmap.get("email"));
		jsonUtils.update_JSONValue(filePostRegCatEvent, "$.firstName", kmap.get("firstName"));
		jsonUtils.update_JSONValue(filePostRegCatEvent, "$.lastName", kmap.get("lastName"));

		endPoint = accExpPOSTSteps.setEndpontCustRegCatalystEvent() + "1";
		expResp = accExpPOSTSteps.post(filePostRegCatEvent, endPoint).extract().response();
	}
    
    @When("^user sends a POST request to customers/registrations/catalyst/event/ for registration of an event for RegisteredUser$")
	public void userSendsAPostRequestTocustomerRegistrationCatalystEventforRegisteredUser() throws Exception {

		jsonUtils.update_JSONValue(filePostRegCatEvent, "$.ucid", kmap.get("ucid"));
		jsonUtils.update_JSONValue(filePostRegCatEvent, "$.email", kmap.get("email"));
		jsonUtils.update_JSONValue(filePostRegCatEvent, "$.firstName", kmap.get("firstName"));
		jsonUtils.update_JSONValue(filePostRegCatEvent, "$.lastName", kmap.get("lastName"));

		endPoint = accExpPOSTSteps.setEndpontCustRegCatalystEvent() + "1";
		expResp = accExpPOSTSteps.post(filePostRegCatEvent, endPoint).extract().response();
	}
    
	@When("^user sends a POST request to customers/registrations/catalyst/event/ again for another event for RegisteredUser$")
	public void userSendsAPostRequestTocustomerRegistrationCatalystEventforRegisteredUserAnotherEvent()
			throws Exception {

		jsonUtils.update_JSONValue(filePostRegCatEvent, "$.ucid", kmap.get("ucid"));
		jsonUtils.update_JSONValue(filePostRegCatEvent, "$.email", kmap.get("email"));
		jsonUtils.update_JSONValue(filePostRegCatEvent, "$.firstName", kmap.get("firstName"));
		jsonUtils.update_JSONValue(filePostRegCatEvent, "$.lastName", kmap.get("lastName"));

		endPoint = accExpPOSTSteps.setEndpontCustRegCatalystEvent() + "2";
		expResp = accExpPOSTSteps.post(filePostRegCatEvent, endPoint).extract().response();
	}
    
	@When("user sends a POST request to customersRegistrationsCatalystEvent for registration of an event for LEAD")
	public void userSendsAPostRequestTocustomerRegistrationCatalystEventforRegistrationOfAnEventForLead()
			throws Exception {

		jsonUtils.update_JSONValue(filePostRegCatEvent, "$.ucid", kmap.get("ucid"));
		jsonUtils.update_JSONValue(filePostRegCatEvent, "$.email", kmap.get("email"));
		jsonUtils.update_JSONValue(filePostRegCatEvent, "$.firstName", kmap.get("firstName"));
		jsonUtils.update_JSONValue(filePostRegCatEvent, "$.lastName", kmap.get("lastName"));

		endPoint = accExpPOSTSteps.setEndpontCustRegCatalystEvent() + "1";
		expResp = accExpPOSTSteps.post(filePostRegCatEvent, endPoint).extract().response();
	}
	
    @And("^I get the ucid from response$")
	public void ciGetUcidFromExpResp() throws Exception {
		System.out.println(kmap.toString() + "kmap get ucid here");
		kmap.put("ucid", expResp.jsonPath().get("ucId"));
		System.out.println(kmap.toString() + "kmap get ucid here");
	}

	@Given("^Created new Registered user for non-qualified to IC$") // Registered user non-qual to IC
	public void createdNewRegisteredUYserforNonqualToIC() throws Exception {
        String filePostRegUserNonqualToIC = "AccExp_POST_registeredUser_nonqualToIC.json";
		endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
		String emailValue = tUtil.AppendTimestamp(autoEmail);
		String fNameValue = tUtil.AppendTimestamp("firstName");
		String lNameValue = tUtil.AppendTimestamp("lastName");
		jsonUtils.update_JSONValue(filePostRegUserNonqualToIC, "$.email", emailValue);
		jsonUtils.update_JSONValue(filePostRegUserNonqualToIC, "$.firstName", fNameValue);
		jsonUtils.update_JSONValue(filePostRegUserNonqualToIC, "$.lastName", lNameValue);

		kmap.put("email", emailValue);
		kmap.put("lastName", lNameValue);
		kmap.put("firstName", fNameValue);

		expResp = accExpPOSTSteps.post(filePostRegUserNonqualToIC, endPoint).extract().response();
	}
    
    // ==========================================================================================
    // Status code and message verification
    // ==========================================================================================

    @Then("^valid status code (.*) and error message (.*) should be displayed$")
    public void validStatusCodeAndErrorMessageShouldBeDisplayed(int sts, String msg) {
        tUtil.verifyStatus(expResp, sts);
    }

    @Then("^the request to System API should return 200 status code$")
    public void theSysRequestShouldReturnStatusCode() {
        tUtil.verifyStatus(sysResp, ResponseCode.OK);
    }

    @Then("^the request to Process API should return 200 status code$")
    public void theProcRequestShouldReturnStatusCode() {
        tUtil.verifyStatus(procResp, ResponseCode.OK);
    }

    @Then("^the request to Experience API should return 200 status code$")
    public void theExpRequestShouldReturnStatusCode() {
        tUtil.verifyStatus(expResp, ResponseCode.OK);
    }

    @Then("^the request should return 201 status code$")
    public void theRequestShouldReturn201StatusCode() {
        tUtil.verifyStatus(expResp, ResponseCode.CREATED);
    }

    // ==========================================================================================
    // Fields Retrieval and Verification
    // ==========================================================================================

    @Given("^user sends a GET call at Exp layer to get all reference fields$")
    public void userSendsAGETCallAtExpLayerToGetAllReferenceFields() throws URISyntaxException {
        expResp = accExpGETSteps.getFields(null)
                .extract().response();

    }

    @Then("^user receives 200 status code and field matches to the business requirements$")
    public void userReceivesStatusCode() throws JSONException, IOException {
        tUtil.verifyStatus(expResp, ResponseCode.OK);
        accExpGETSteps.verifyJson(expResp, "AIC_Fields");
    }

    @When("^user sends a GET call to process API to retrieve all fields$")
    public void userSendsAGETCallToProcessAPIToRetrieveAllFields() throws URISyntaxException {
        procResp = custProcGETSteps.getFields(null)
                .extract().response();
    }

    @Then("^fields from exp layer should match with process layer$")
    public void fieldsFromExpLayerShouldMatchWithProcessLayer() throws JSONException, IOException {
        custProcGETSteps.verifyJson(expResp, procResp);
    }

    @When("^user sends a GET call to system API$")
    public void userSendsAGETCallToSystemAPI() {
        expResp = aicGETSteps.getFields(null)
                .extract().response();
    }

    @Then("^fields from Process layer should match with system layer$")
    public void fieldsFromProcessLayerShouldMatchWithSystemLayer() throws JSONException, IOException {
        custProcGETSteps.verifyJson(expResp, procResp);
    }


    @Given("^user sends a GET call at Exp layer to get all values from the given reference field (.*)$")
    public void userSendsGETCallAtExpLayerToGetAllValuesFromTheGivenReferenceField(
            String field) throws URISyntaxException {
        expResp = accExpGETSteps.getFields(field)
                .extract().response();
    }


    @Then("^all values from the given reference field (.*) should match with the business requirements$")
    public void allValuesFromTheGivenReferenceFieldShouldMatchWithTheBusinessRequirements(
            String field) throws JSONException, IOException {
        tUtil.verifyStatus(expResp, ResponseCode.OK);
        accExpGETSteps.verifyJson(expResp, field);
    }

    @When("^user sends a GET call to process API to get all values from the given reference field (.*)$")
    public void userSendsGETCallToProcessAPIToGetAllValuesFromTheGivenReferenceField(
            String field) throws URISyntaxException {
        procResp = custProcGETSteps.getFields(field)
                .extract().response();
    }

    @Then("^all values from the given reference field (.*) from exp layer should match with process layer$")
    public void allValuesFromTheGivenReferenceFieldFromExpLayerShouldMatchWithProcessLayer(
            String field) throws JSONException, IOException {
        custProcGETSteps.verifyJson(expResp, procResp);
    }

    @When("^user sends a GET call to system API to get all values from the given reference field (.*)$")
    public void userSendsGETCallToSystemAPIGetAllValuesFromTheGivenReferenceField(String field) {
        sysResp = aicGETSteps.getFields(field)
                .extract().response();
    }

    @Then("^all values from the given reference field (.*) from Process layer should match with system layer$")
    public void allValuesFromTheGivenReferenceFieldFromProcessLayerShouldMatchWithSystemLayer(
            String field) throws JSONException, IOException {
        custProcGETSteps.verifyJson(sysResp, procResp);
    }

    @Given("^user sends a GET call with invalid values (.*) at Exp layer$")
    public void userSendsGETCallWithInvalidValuesCouAtExplayer(String field) throws URISyntaxException {
        expResp = accExpGETSteps.getFields(field)
                .extract().response();
    }

    // =====================================================================================================
    // User Creation and Verification
    // =====================================================================================================

    @Given("^Created new user with unique UUID to System API$")
    public void createdNewUserWithUniqueUuid() throws Exception {
        endPoint = aicPOSTSteps.setEndPoint();
        String emailValue = tUtil.AppendTimestamp(autoEmail);
        String fNameValue = tUtil.AppendTimestamp("firstName");
        String lNameValue = tUtil.AppendTimestamp("lastName");
        jsonUtils.update_JSONValue("AIC_Customer.json", "$.email", emailValue);
        jsonUtils.update_JSONValue("AIC_Customer.json", "$.firstName", fNameValue);
        jsonUtils.update_JSONValue("AIC_Customer.json", "$.lastName", lNameValue);

        kmap.put("email", emailValue);

        sysResp = aicPOSTSteps.createCustomerInAkamai("AIC_Customer.json", endPoint)
                .extract().response();

        kmap.put("ucid", sysResp.jsonPath().getString("uuid"));
        kmap.put("audienceType", sysResp.jsonPath().getString("audienceType"));

        kmap.put("lastName", lNameValue);
        kmap.put("firstName", fNameValue);
    }

    @And("^a new user is created in ICV with the same data through ICV System API$")
    public void aNewUserIsCreatedInIcvWithTheSameDataThroughIcvSystemApi() throws Throwable {
        endPoint = icvPOSTSteps.setEndpoint();
        sysResp = icvPOSTSteps.createUser(
                kmap.get("email"),
                kmap.get("lastName") + kmap.get("firstName"),
                "registered", endPoint
        ).extract().response();

        kmap.put("icvId", String.valueOf(sysResp.jsonPath().getInt("id")));
    }

    @And("^created users are linked through alternateId in AIC$")
    public void createdUsersAreLinkedThroughAlternateidInAIC() throws Throwable {
        String filePutAlternateID = "AIC_Put_AlternateID.json";
        endPoint = aicPUTSteps.setEndpointAlternateID(kmap.get("ucid"));

        jsonUtils.update_JSONValue(filePutAlternateID, "$.IDType", "ICV");
        jsonUtils.update_JSONValue(filePutAlternateID, "$.IDValue", kmap.get("icvId"));

        sysResp = aicPUTSteps.putAlternateID(filePutAlternateID, endPoint).extract().response();
    }

    //==========================================================================================================
    // User Information Retrieval and Verification
    // =========================================================================================================

    @When("^user sends a GET request to retrieve user info using email$")
    public void userSendsGETRequestToRetrieveUserInfoUsingEmail() {
        String kmapEmail = kmap.get("email");
        endPoint = accExpGETSteps.setEndpointCustomers();
        expResp = accExpGETSteps.getUserByEmail(endPoint, kmapEmail).extract().response();
    }

    @When("^user sends a GET request to retrieve user info using generated access token$")
    public void userSendsGETRequestToRetrieveUserInfoUsingGeneratedAccessToken() {
        endPoint = accExpGETSteps.setEndpointGetUserByUcid(kmap.get("ucid"));
        expResp = accExpGETSteps.getUserByUcid(endPoint, kmap.get("accessToken")).extract().response();
        ucid = kmap.get("ucid");
    }

    @When("^user sends a GET request to retrieve user info without using generated access token$")
    public void userSendsAGetRequestToRetrieveUserInfoWithoutUsingGeneratedAccessToken() {
        endPoint = accExpGETSteps.setEndpointGetUserByUcid(kmap.get("ucid"));
        expResp = accExpGETSteps.getUserByUcid(endPoint, "").extract().response();
    }

    @When("^user sends a GET request to retrieve user info using generated access token and ucid (.*)$")
    public void userSendsAGetRequestToRetrieveUserInfoUsingGeneratedAccessTokenAndUcid(String id) {
        endPoint = accExpGETSteps.setEndpointGetUserByUcid(id);
        expResp = accExpGETSteps.getUserByUcid(endPoint, kmap.get("accessToken")).extract().response();
    }

    @When("^user sends a GET request to retrieve user info through Account Experience API$")
    public void userSendsGETRequestRetrieveUserInfoThroughAccountExperienceAPI() {
        iDoPOSTCallToGgetaccesstokenWithUuid();
        
        endPoint = accExpGETSteps.setEndpointGetUserByUcid(kmap.get("ucid"));        
        expResp = accExpGETSteps.getUserByUcid(endPoint, token)
        		.extract().response();
        
        
    }

    @When("^user sends a GET request to retrieve user info through Customer Process API$")
    public void userSendsGETRequestRetrieveUserInfoThroughCustomerProcessAPI() throws Throwable {
        endPoint = custProcGETSteps.setEndpointUserByEmail(kmap.get("email"));
        procResp = custProcGETSteps.getUserInfo(endPoint).extract().response();
    }

    @When("^user sends a GET request to retrieve user info through AIC System API$")
    public void userSendsAGETRequestToRetrieveUserInfoThroughAICSystemAPI() {
        endPoint = aicGETSteps.setEndPointEmail(kmap.get("email"));
        sysResp = aicGETSteps.getUserFromAkamai(endPoint)
                .extract().response();
        
        tUtil.putToSession("response", sysResp);
    }

    @When("^user sends a GET request to retrieve user info through ICV System API$")
    public void userSendsAGetRequestToRetrieveUserInfoThroughIcvSystemApi() throws Throwable {
        sysResp = icvGETSteps.getUsers(icvGETSteps.setEndpoint(kmap.get("email")))
        		.extract().response();
        
        tUtil.putToSession("response", sysResp);
    }

    @Then("^user information should be displayed with status code 200$")
    public void userInformationShouldBeDisplayedWithStatusCode200() throws Throwable {
        tUtil.verifyStatus(expResp, ResponseCode.OK);

        JsonPath jsonpathEvaluator = expResp.jsonPath();
        String[] keys = {"email", "ucId", "audienceType"};
        tUtil.verifyKeysInObject(expResp, keys);

        assertEquals(
                String.format(
                        "The email should be %s but got %s", kmap.get("email"),
                        jsonpathEvaluator.getString("email")
                ),
                kmap.get("email"), jsonpathEvaluator.getString("email"));
        kmap.put("ucid", jsonpathEvaluator.getString("ucId"));
        kmap.put("audienceType", jsonpathEvaluator.getString("audienceType"));
    }

    @Then("^user info should be displayed with 200 status$")
    public void userInfoShouldBeDisplayedWith200Status() throws Throwable {
        tUtil.verifyStatus(expResp, ResponseCode.OK);

        JsonPath jsonpathEvaluator = expResp.jsonPath();
        String[] keys = {"email", "ucId", "audienceType", "phone"};
        tUtil.verifyKeysInObject(expResp, keys);


        assertEquals(
                String.format(
                        "The ucid should be %s but got %s",
                        kmap.get("ucid"), jsonpathEvaluator.get("ucId")
                ),
                kmap.get("ucid"), jsonpathEvaluator.get("ucId"));
        assertEquals(
                String.format(
                        "The email should be %s but got %s", kmap.get("email"),
                        jsonpathEvaluator.getString("email")
                ),
                kmap.get("email"), jsonpathEvaluator.getString("email"));
        assertEquals(
                String.format(
                        "The first name should be %s but got %s",
                        kmap.get("firstName"), jsonpathEvaluator.getString("firstName")
                ),
                kmap.get("firstName"), jsonpathEvaluator.getString("firstName"));
        assertEquals(
                String.format(
                        "The last name should be %s but got %s", kmap.get("lastName"),
                        jsonpathEvaluator.getString("lastName")
                ),
                kmap.get("lastName"), jsonpathEvaluator.getString("lastName"));
        assertEquals(
                String.format(
                        "The audience type should be %s but got %s",
                        kmap.get("audienceType"), jsonpathEvaluator.getString("audienceType")
                ),
                kmap.get("audienceType"), jsonpathEvaluator.getString("audienceType"));

    }

    @Then("^user info should match the info used in registration through Experience API with 200 status$")
    public void userInfoShouldMatchTheInfoUsedInRegistrationThroughExperienceApiWith200Status() {
        tUtil.verifyStatus(procResp, ResponseCode.OK);

        custProcGETSteps.verifyUserInfo(expResp, procResp, true);
    }

    @Then("^user info should match the info received through Process API$")
    public void userInfoShouldMatchTheInfoReceivedThroughProcessApi() {
        tUtil.verifyStatus(sysResp, ResponseCode.OK);

        custProcGETSteps.verifyUserInfo(procResp, sysResp, false);
    }

    @Then("^user info should match the alternateId info received through Process API$")
    public void userInfoShouldMatchTheAlternateidInfoReceivedThroughProcessApi() {
        tUtil.verifyStatus(sysResp, ResponseCode.OK);
        custProcGETSteps.verifyAlternateId(procResp, sysResp);
    }

    // =====================================================================================================
    // User Registration for Event and Verification
    // =====================================================================================================

    @When("^user sends a POST request to register a user for event (.*) through Account Experience API$")
    public void userSendsAPostRequestToRegisterAUserForEventThroughAccountExperience(int eventid) throws Throwable {
        String emailValue = tUtil.AppendTimestamp(autoEmail);
        String firstName = tUtil.AppendTimestamp("firstName");
        String lastName = tUtil.AppendTimestamp("lastName");
        jsonUtils.update_JSONValue(filePostFullRegisterForEvent, "email", emailValue);
        jsonUtils.update_JSONValue(filePostFullRegisterForEvent, "icvEventId", eventid);
        jsonUtils.update_JSONValue(filePostFullRegisterForEvent, "firstName", firstName);
        jsonUtils.update_JSONValue(filePostFullRegisterForEvent, "lastName", lastName);

        kmap.put("eventId", String.valueOf(eventid));
        kmap.put("email", emailValue);
        kmap.put("firstName", firstName);
        kmap.put("lastName", lastName);

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(filePostFullRegisterForEvent, endPoint).extract().response();
    }

    @When("^user sends a POST request to register a user for valid event through Account Experience API$")
    public void userSendsAPostRequestToRegisterAUserForValidEventThroughAccountExperienceApi() throws Throwable {
        // if there is no event id present in kmap, get it from CJ Experience API Steps
        if (!kmap.containsKey("eventId")) {
            kmap = CJExpSteps.kmap;
        }

        String emailValue = tUtil.AppendTimestamp("automation@example.com");
        String firstName = tUtil.AppendTimestamp("firstName");
        String lastName = tUtil.AppendTimestamp("lastName");
        jsonUtils.update_JSONValue(filePostFullRegisterForEvent, "email", emailValue);
        jsonUtils.update_JSONValue(filePostFullRegisterForEvent, "firstName", firstName);
        jsonUtils.update_JSONValue(filePostFullRegisterForEvent, "lastName", lastName);
        jsonUtils.update_JSONValue(filePostFullRegisterForEvent,
                "icvEventId", Integer.parseInt(kmap.get("eventId")));

        kmap.put("email", emailValue);
        kmap.put("firstName", firstName);
        kmap.put("lastName", lastName);

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(filePostFullRegisterForEvent, endPoint).extract().response();
    }

    @When("^user sends a POST request to register existing user for event (.*) through Account Experience API$")
    public void userSendsAPostRequestToRegisterExistingUserForEventThroughAccountExperienceApi(
            int anotherEventid) throws Throwable {
        userSendsGETRequestToRetrieveUserInfoUsingEmail();
        userInformationShouldBeDisplayedWithStatusCode200();

        jsonUtils.update_JSONValue(filePostRegisterForEventUcid, "icvEventId", anotherEventid);
        jsonUtils.update_JSONValue(filePostRegisterForEventUcid, "ucId", kmap.get("ucid"));
        jsonUtils.update_JSONValue(filePostRegisterForEventUcid, "email", kmap.get("email"));

        kmap.put("eventId", String.valueOf(anotherEventid));
        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(filePostRegisterForEventUcid, endPoint).extract().response();
    }

    @When("^user sends a valid POST request to register existing user"
            + " for another event through Account Experience API$")
    public void userSendsAValidPostRequestToRegisterExistingUserForAnotherEventThroughAccountExperienceApi()
            throws Throwable {
        jsonUtils.update_JSONValue(filePostRegisterForEventUcid,
                "icvEventId", Integer.parseInt(CJExpSteps.kmap.get("eventId")));
        jsonUtils.update_JSONValue(filePostRegisterForEventUcid, "ucId", kmap.get("ucid"));
        jsonUtils.update_JSONValue(filePostRegisterForEventUcid, "email", kmap.get("email"));
        jsonUtils.update_JSONValue(filePostRegisterForEventUcid, "firstName", kmap.get("firstName"));
        jsonUtils.update_JSONValue(filePostRegisterForEventUcid, "lastName", kmap.get("lastName"));

        kmap.put("eventId", CJExpSteps.kmap.get("eventId"));

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(filePostRegisterForEventUcid, endPoint).extract().response();
    }

    @When("^user sends a POST request with both ucid and email (.*) "
            + "to register existing user for event (.*) through Account Experience API$")
    public void userSendsAPostRequestWithBothUcidAndEmailToRegisterExistingUserForEventThroughAccountExperienceApi(
            String emailValue, int anothereventid) throws Throwable {
        userSendsGETRequestToRetrieveUserInfoUsingEmail();
        userInformationShouldBeDisplayedWithStatusCode200();

        jsonUtils.update_JSONValue(filePostRegistrationExtra, "icvEventId", anothereventid);
        jsonUtils.update_JSONValue(filePostRegistrationExtra, "ucId", kmap.get("ucid"));
        jsonUtils.update_JSONValue(filePostRegistrationExtra, "email", emailValue);

        kmap.put("eventId", String.valueOf(anothereventid));

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(filePostRegistrationExtra, endPoint).extract().response();
    }

    @When("^user sends a valid POST request with both ucid and email (.*) "
            + "to register existing user for event through Account Experience API$")
    public void userSendsAValidPostRequestWithBothUcidAndEmailToRegisterExistingUserForEventThroughAccountExperienceApi(
            String emailValue) throws Throwable {
        userSendsGETRequestToRetrieveUserInfoUsingEmail();
        userInformationShouldBeDisplayedWithStatusCode200();

        jsonUtils.update_JSONValue(filePostRegistrationExtra,
                "icvEventId", Integer.parseInt(CJExpSteps.kmap.get("eventId")));
        jsonUtils.update_JSONValue(filePostRegistrationExtra, "ucId", kmap.get("ucid"));
        jsonUtils.update_JSONValue(filePostRegistrationExtra, "email", emailValue);

        kmap.put("eventId", CJExpSteps.kmap.get("eventId"));

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(filePostRegistrationExtra, endPoint).extract().response();
    }

    @When("^user sends a POST invalid request to register existing user"
            + " email for event (.*) through Account Experience API$")
    public void userSendsAPostInvalidRequestToRegisterExistingUserEmailForEventThroughAccountExperienceApi(
            int eventid) throws Throwable {
        jsonUtils.update_JSONValue(filePostFullRegisterForEvent, "email", kmap.get("email"));
        jsonUtils.update_JSONValue(filePostFullRegisterForEvent, "icvEventId", eventid);

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(filePostFullRegisterForEvent, endPoint).extract().response();
    }

    @When("^user sends a POST invalid request to register existing"
            + " user email for valid event through Account Experience API$")
    public void userSendsAPostInvalidRequestToRegisterExistingUserEmailForValidEventThroughAccountExperienceApi()
            throws Throwable {
        jsonUtils.update_JSONValue(filePostFullRegisterForEvent, "email", kmap.get("email"));
        jsonUtils.update_JSONValue(filePostFullRegisterForEvent,
                "icvEventId", Integer.parseInt(kmap.get("eventId")));

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(filePostFullRegisterForEvent, endPoint).extract().response();
    }

    @When("^user sends a POST request to register an invalid user with"
            + " email (.*) for event (.*) through Account Experience API$")
    public void userSendsAPostRequestToRegisterAnInvalidUserWithEmailForEventThroughAccountExperienceApi(
            String emailValue, int eventid) throws Throwable {
        String filePostRegisterInvalid = "AccExp_POST_Register_Invalid.json";
        String tsEmail =
                (emailValue.isEmpty() || !emailValue.matches("(.*)@(.*).(.*)"))
                        ? email : tUtil.AppendTimestamp(email);
        jsonUtils.update_JSONValue(filePostRegisterInvalid, "email", tsEmail);
        jsonUtils.update_JSONValue(filePostRegisterInvalid, "icvEventId", eventid);

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(filePostRegisterInvalid, endPoint).extract().response();
    }

    @Then("^user should be registered for the event with status code 201$")
    public void userShouldBeRegisteredForTheEventWithStatusCode201() throws Throwable {
        tUtil.verify_msgCode(expResp, "Registration completed successfully.", ResponseCode.CREATED);
        userSendsGETRequestToRetrieveUserInfoUsingEmail();
        userInformationShouldBeDisplayedWithStatusCode200();
        // Verification through GET call
        endPoint = accExpGETSteps.setEndpointGetUserEvents(kmap.get("ucid"));
        expResp = accExpGETSteps.get(endPoint).extract().response();

        JsonPath jsonpathEvaluator = expResp.jsonPath();
        List<Integer> actualLoEventId = jsonpathEvaluator.get("id");
        System.out.println(actualLoEventId);
        Assert.assertTrue("The list should not be empty", actualLoEventId.size() > 0);
        Assert.assertTrue(String.format("EventId %s should be in the list", kmap.get("eventId")),
                actualLoEventId.contains(Integer.valueOf(kmap.get("eventId"))));

        // Keys that should be present in event info
        String[] arrayKeys = {"title", "startDate", "hosts", "timezone", "fullDetailsLink"};
        List<String> loKeys = Arrays.asList(arrayKeys);

        // Verification of fields existence
        for (String k : loKeys) {
            Assert.assertNotNull(jsonpathEvaluator.getList(k));
        }

    }

    @And("^email (.*) should be ignored$")
    public void emailShouldBeIgnored(String emailValue) {
        endPoint = accExpGETSteps.setEndpointCustomers();
        expResp = accExpGETSteps.getUserByEmail(endPoint, emailValue).extract().response();
        tUtil.verify_msgCode(expResp, "Resource not found", ResponseCode.INTERNAL_ERROR);
    }

    // ===============================================================================================
    // Event retrieval and Verification
    // ===============================================================================================

    @When("^user sends a GET request to retrieve the events user is registered for$")
    public void userSendsAGetRequestToRetrieveTheEventsUserIsRegisteredFor() {
        endPoint = accExpGETSteps.setEndpointGetUserEvents(kmap.get("ucid"));
        expResp = accExpGETSteps.get(endPoint).extract().response();
    }

    @When("^user sends a GET request to retrieve the events user (.*) is registered for$")
    public void userSendsAGetRequestToRetrieveTheEventsUserIsRegisteredFor(String ucidValue) {
        endPoint = accExpGETSteps.setEndpointGetUserEvents(ucidValue);
        expResp = accExpGETSteps.get(endPoint).extract().response();
        tUtil.putToSession("response", expResp);
    }

    @When("^user sends a GET request to retrieve all events for the user through Event Process API$")
    public void userSendsAGetRequestToRetrieveAllEventsForTheUserThroughEventProcessApi() {
        procResp = eventProcGETSteps.get(eventProcGETSteps.setEndpointUserEvents(kmap.get("ucid")))
                .extract().response();
    }

    @When("^user sends a GET request to retrieve all events for the user through ICV System API$")
    public void userSendsAGetRequestToRetrieveAllEventsForTheUserThroughIcvSystemApi() throws Throwable {
        sysResp = icvGETSteps.get(icvGETSteps.setuserEventsEndpoint(kmap.get("email")))
        		.extract().response();
        
        tUtil.putToSession("response", sysResp);
    }

    @When("^user sends a GET request to retrieve info of event (.*) through My Account Experience API$")
    public void userSendsAGetRequestToRetrieveInfoOfEventThroughMyAccountExperienceApi(String eventid) {
        kmap.put("eventId", eventid);
        endPoint = accExpGETSteps.setEndpointEventById(eventid);
        expResp = accExpGETSteps.getEventById(endPoint).extract().response();
    }

    @When("^user sends a GET request to retrieve info of valid event through My Account Experience API$")
    public void userSendsAGetRequestToRetrieveInfoOfValidEventThroughMyAccountExperienceApi() {
        // Getting the kmap (value of eventId) from CJ Experience API Steps
        // (since CJ experience api has /events endpoint)
        kmap = CJExpSteps.kmap;
        endPoint = accExpGETSteps.setEndpointEventById(kmap.get("eventId"));
        expResp = accExpGETSteps.getEventById(endPoint).extract().response();
    }

    @When("^user sends a GET request to retrieve info of event (.*) through Event Process API$")
    public void userSendsAGetRequestToRetrieveInfoOfEventThroughEventProcessApi(String eventid) {
        procResp = eventProcGETSteps.get(eventProcGETSteps.setEndpointEventById(eventid)).extract().response();
    }

    @When("^user sends a GET request to retrieve info of valid event through Event Process API$")
    public void userSendsAGetRequestToRetrieveInfoOfValidEventThroughEventProcessApi() {
        procResp = eventProcGETSteps.get(
                eventProcGETSteps.setEndpointEventById(kmap.get("eventId"))).extract().response();
    }

    @When("^user sends a GET request to retrieve info of event (.*) through ICV System API$")
    public void userSendsAGetRequestToRetrieveInfoOfEventThroughIcvSystemApi(String eventid) throws Throwable {
        sysResp = icvGETSteps.getEventbyId(
                icvGETSteps.setEndpointEventById(eventid)).extract().response();
    }

    @When("^user sends a GET request to retrieve info of valid event through ICV System API$")
    public void userSendsAGetRequestToRetrieveInfoOfValidEventThroughIcvSystemApi() throws Throwable {
        sysResp = icvGETSteps.getEventbyId(
                icvGETSteps.setEndpointEventById(kmap.get("eventId"))).extract().response();
    }

    @Then("^the response should return all the events for that user in right order with status code 200$")
    public void theResponseShouldReturnAllTheEventsForThatUserInRightOrderWithStatusCode200() {
        tUtil.verifyStatus(expResp, ResponseCode.OK);

        JsonPath jsonpathEvaluator = expResp.jsonPath();
        List<Date> actualLoStartDate = jsonpathEvaluator.getList("startDate").stream().map(x -> {
            try {
                return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(x.toString());
            } catch (ParseException e) {
                System.out.println("Date: " + x.toString());
                return null;
            }
        }).collect(Collectors.toList());
        assertFalse(actualLoStartDate.contains(null));

        // Assert reverse chronological order
        Assert.assertTrue("Dates should be in a reverse chronological order",
                Comparators.isInOrder(actualLoStartDate, new Comparator<Date>() {
                    @Override
                    public int compare(Date d1, Date d2) {
                        return d2.compareTo(d1);
                    }
                }));

    }

    @Then("^the response should return empty list of events for that user with status code 200$")
    public void theResponseShouldReturnEmptyListOfEventsForThatUserWithStatusCode200() {
        tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_emptyArray(expResp);
    }

    @Then("^the recieved list of events should match the list from Experince API$")
    public void theRecievedListOfEventsShouldMatchTheListFromExperinceApi() {
        eventProcGETSteps.verifyUserEvents(expResp, procResp, false);
    }

    @Then("^the received list of events should match the list from Process API$")
    public void theReceivedListOfEventsShouldMatchTheListFromProcessApi() {
        eventProcGETSteps.verifyUserEvents(procResp, sysResp, true);
    }

    @Then("^event information should be displayed with 200 status$")
    public void eventInformationShouldBeDisplayedWith200Status() throws Throwable {
        tUtil.verifyStatus(expResp, ResponseCode.OK);

        JsonPath jsonpathEvaluator = expResp.jsonPath();
        Assert.assertTrue(
                String.format(
                        "The event id is supposed to be %s but got %s",
                        kmap.get("eventId"),
                        jsonpathEvaluator.getInt("id")
                ),
                kmap.get("eventId").equals(String.valueOf(jsonpathEvaluator.getInt("id"))));

        // Keys that should be present in event info
        String[] arrayKeys = {"title", "startDate", "hosts", "timezone", "fullDetailsLink"};
        List<String> loKeys = Arrays.asList(arrayKeys);

        // Verification of fields existence
        for (String k : loKeys) {
            Assert.assertNotNull(jsonpathEvaluator.getString(k));
        }

    }

    @Then("^the event information from Experience and Process layers should match$")
    public void theEventInformationFromExperienceAndProcessLayersShouldMatch() {
        tUtil.verifyStatus(procResp, ResponseCode.OK);
        eventProcGETSteps.verifyEvents(expResp, procResp, false);
    }

    @Then("^the even information from Process and System layers should match$")
    public void theEvenInformationFromProcessAndSystemLayersShouldMatch() {
        tUtil.verifyStatus(sysResp, ResponseCode.OK);
        eventProcGETSteps.verifyEvents(procResp, sysResp, true);
    }

    // ==================================================================================================
    // Access Token Verification
    // ==================================================================================================

    @When("^I do POST call to Exp API to validate url with valid data$")
    public void iDoPOSTCallToValidateUrlValidData() {
        token = aicPOSTSteps.getToken(sysResp);
        expResp = accExpPOSTSteps.validateToken(kmap.get("ucid"), token)
                .extract().response();
    }

    @When("^I do a POST call to getAccessToken with UUID to System API$")
    public void iDoPOSTCallToGgetaccesstokenWithUuid() {
        sysResp = aicPOSTSteps.createToken(kmap.get("ucid"))
                .extract().response();
        token = aicPOSTSteps.getToken(sysResp);
        tUtil.putToSession("response", sysResp);
    }

    @When("^I do POST call to Process API to validate url with valid data$")
    public void iDoPOSTCallToProcessAPIToValidateUrlWithValidData() {
        procResp = custProcPOSTSteps.validateToken(kmap.get("ucid"), token)
                .extract().response();
    }

    @When("^I do POST call to Exp API to validate url with invalid token (.*)$")
    public void iDoPOSTCallExpValidateUrlInvalidToken(String tokenValue) {
        expResp = accExpPOSTSteps.validateToken(kmap.get("ucid"), tokenValue)
                .extract().response();
    }

    @When("^I do POST call to Process API to validate url with invalid token (.*)$")
    public void iDoPOSTCallProcessValidateUrlInvalidToken(String tokenValue) {
        procResp = custProcPOSTSteps.validateToken(kmap.get("ucid"), tokenValue)
                .extract().response();
    }

    @When("^I do POST call to Exp API validate url with invalid UUID (.*)$")
    public void iDoPOSTCallToValidateUrlInvalidUuid(String userUUID) {
        expResp = accExpPOSTSteps.validateToken(userUUID, token)
                .extract().response();
    }

    @When("^I do POST call to Process API validate url with invalid UUID (.*)$")
    public void iDoPOSTCallToProcessAPIValidateUrlWithInvalidUUIDUuid(String uuid) {
        procResp = custProcPOSTSteps.validateToken(uuid, token)
                .extract().response();
    }

    @When("^User send a POST request to send notification with valid data$")
    public void userSendAPOSTRequestToSendNotificationWithValidData() throws Exception {
        String emailValue = tUtil.AppendTimestamp(autoEmail);
        jsonUtils.update_JSONValue(fileTicket, "$.email", emailValue);
        expResp = accExpPOSTSteps.postTickets(fileTicket)
                .extract().response();
    }

    @When("^User send a POST request to send notification with invalid data (.*)$")
    public void userSendAPOSTRequestToSendNotificationWithInvalidDataField(String field) throws Exception {
        String valueField = "$.".concat(field);
        String dataField = jsonUtils.getFromJSON(fileTicket, valueField);
        jsonUtils.update_JSONValue(fileTicket, valueField, "");
        expResp = accExpPOSTSteps.postTickets(fileTicket)
                .extract().response();

        jsonUtils.update_JSONValue(fileTicket, valueField, dataField);
    }

    @When("^User send a POST request to send notification with valid data and without the optional field (.*)$")
    public void userSendAPOSTRequestToSendNotificationWithValidDataAndWithoutTheOptionalFieldField(
            String field) throws Exception {
        String valueField = "$.".concat(field);
        String dataField = jsonUtils.getFromJSON(fileTicket, valueField);
        jsonUtils.update_JSONValue(fileTicket, valueField, "");

        String emailValue = tUtil.AppendTimestamp(autoEmail);
        jsonUtils.update_JSONValue(fileTicket, "$.email", emailValue);
        expResp = accExpPOSTSteps.postTickets(fileTicket)
                .extract().response();

        jsonUtils.update_JSONValue(fileTicket, valueField, dataField);
    }

    @Then("^valid status (.*) and message (.*) should be displayed$")
    public void validStatusMessageShouldBeDisplayed(int sts, String msg) {
        tUtil.verify_StsAndMsg(procResp, msg, sts);
    }

    @When("^user sends a POST request to Akamai to get access token$")
    public void userSendsPpostRequestToAkamaiToGetAccessToken() {
        sysResp = aicPOSTSteps.createToken(kmap.get("ucid")).extract().response();
    }

    @Then("^request should return access token with status 200$")
    public void requestShouldReturnAccessTokenWithStatus200() {
        tUtil.verifyStatus(sysResp, ResponseCode.OK);
        kmap.put("accessToken", sysResp.jsonPath().getString("accessToken"));
    }

    @When("^I do a POST request to Experience API registration with context$")
    public void createdNewUserMyAccountExpAPI() throws Exception {
    	
        String emailValue = tUtil.AppendTimestamp(autoEmail);
        String fNameValue = tUtil.AppendTimestamp("firstName");
        String lNameValue = tUtil.AppendTimestamp("lastName");
        
        jsonUtils.update_JSONValue(filePostRegisterContext, "$.email", emailValue);
        jsonUtils.update_JSONValue(filePostRegisterContext, "$.firstName", fNameValue);
        jsonUtils.update_JSONValue(filePostRegisterContext, "$.lastName", lNameValue);

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(filePostRegisterContext, endPoint)
                .extract().response();
    }

    @When("^I do a POST call to Exp API to register user with context EVENTS$")
    public void iDoAPOSTCallToExpAPIToRegisterUserWithContextEVENTS() throws Exception {
        String filePostRegisterContextEvents = "AccExp_POST_User_Ctx_Events.json";
        String fNameValue = tUtil.AppendTimestamp("firstName");
        String lNameValue = tUtil.AppendTimestamp("lastName");
        jsonUtils.update_JSONValue(filePostRegisterContextEvents, "$.ucId", kmap.get("ucid"));
        jsonUtils.update_JSONValue(filePostRegisterContextEvents, "$.firstName", fNameValue);
        jsonUtils.update_JSONValue(filePostRegisterContextEvents, "$.lastName", lNameValue);

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(filePostRegisterContextEvents, endPoint)
                .extract().response();
    }


    @When("^I do a POST call to Exp API for Catalyst OAD Connect Email$")
    public void iDoAPOSTCallToExpAPIForCatalystOADConnectEmail() throws Exception {
        String fileName = "AccExp_POST_Catalyst_OAD.json";
        String emailValue = tUtil.AppendTimestamp("automation@example.com");
        String fNameValue = tUtil.AppendTimestamp("firstName");
        String lNameValue = tUtil.AppendTimestamp("lastName");
        jsonUtils.update_JSONValue(fileName, "$.email", emailValue);
        jsonUtils.update_JSONValue(fileName, "$.firstName", fNameValue);
        jsonUtils.update_JSONValue(fileName, "$.lastName", lNameValue);

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(fileName, endPoint)
                .extract().response();
    }

    @When("^User sends a GET request to Exp API to get details of surveys for user with ucid (.*)$")
    public void userSendsAGETRequestToGetDetailsOfSurveysForUserWithInvalidUcidUcid(String ucidValue) {
        endPoint = accExpGETSteps.setEndpointUserSurveysByUcid(ucidValue);
        expResp = accExpGETSteps.get(endPoint).extract().response();
    }

    @Then("^user receives status code 200 and list size (.*)$")
    public void validStatusdisplayedAndEmptyList(int size) {
        assertEquals(expResp.getStatusCode(), ResponseCode.OK);
        List resp = expResp.jsonPath().get();
        assertEquals(resp.size(), size);
    }

    @When("^I Do a POST call to CJ Registration Flow$")
    public void iDoAPOSTCallToCJRegistrationFlow() throws Exception {
        String fileName = "AccExp_Post_CJ_Reg.json";
        String fNameValue = tUtil.AppendTimestamp("firstName");
        String lNameValue = tUtil.AppendTimestamp("lastName");
        String emailValue = tUtil.AppendTimestamp("automation@exmaple.com");

        jsonUtils.update_JSONValue(fileName, "$.email", emailValue);
        jsonUtils.update_JSONValue(fileName, "$.firstName", fNameValue);
        jsonUtils.update_JSONValue(fileName, "$.lastName", lNameValue);

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(fileName, endPoint)
                .extract().response();
    }

    @Then("^user get status code (.*) and message (.*)$")
    public void userGetValidStatusCodeStsAndMessageMsg(int sts, String msg) {
        tUtil.verify_StsAndMsg(expResp, msg, sts);
    }

    @When("^User sends a POST request to register existing user for event with invalid Data (.*) for field (.*)$")
    public void registerExistingUserForEventWithInvalidValue(String fieldValue, String fieldName) throws Exception {

        String emailValue = tUtil.AppendTimestamp("automation@example.com");
        String fNameValue = tUtil.AppendTimestamp("firstName");
        String lNameValue = tUtil.AppendTimestamp("lastName");
        if (fieldName.equals("firstName")) {
            jsonUtils.update_JSONValue(filePostRegisterForEventUcid, "$.lastName", fNameValue);
            jsonUtils.update_JSONValue(filePostRegisterForEventUcid, "$.email", emailValue);
            jsonUtils.update_JSONValue(filePostRegisterForEventUcid, "$.firstName", fieldValue);
        } else if (fieldName.equals("lastName")) {
            jsonUtils.update_JSONValue(filePostRegisterForEventUcid, "$.email", emailValue);
            jsonUtils.update_JSONValue(filePostRegisterForEventUcid, "$.firstName", fNameValue);
            jsonUtils.update_JSONValue(filePostRegisterForEventUcid, "$.lastName", fieldValue);
        } else {
            jsonUtils.update_JSONValue(filePostRegisterForEventUcid, "$.firstName", fNameValue);
            jsonUtils.update_JSONValue(filePostRegisterForEventUcid, "$.lastName", lNameValue);
            jsonUtils.update_JSONValue(filePostRegisterForEventUcid, "$.email", fieldValue);
        }

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(filePostRegisterForEventUcid, endPoint).extract().response();
    }

    @When("^I do a POST call to ExpAPI registration user$")
    public void iDoAPOSTCallToExpAPIRegistrationUser() throws Exception {
        email = tUtil.AppendTimestamp(autoEmail);
        jsonUtils.update_JSONValue(filePostRegisterContext, "$.email", email);

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(filePostRegisterContext, endPoint)
                .extract().response();

        kmap.put("email", email);
    }

    @When("^User sends a POST request to register user with valid data$")
    public void userSendsAPOSTRequestToRegisterUserWithValidData() throws Exception {
        String fNameValue = tUtil.AppendTimestamp("firstName");
        String lNameValue = tUtil.AppendTimestamp("lastName");
        String emailValue = tUtil.AppendTimestamp("automation@exmaple.com");

        jsonUtils.update_JSONValue(filePostRegisterUser, "$.email", emailValue);
        jsonUtils.update_JSONValue(filePostRegisterUser, "$.firstName", fNameValue);
        jsonUtils.update_JSONValue(filePostRegisterUser, "$.lastName", lNameValue);
        jsonUtils.add_JSONPathJsonValue(filePostRegisterUser, "$.ucid", kmap.get("ucid"));

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(filePostRegisterUser, endPoint)
                .extract().response();

        jsonUtils.remove_JSONPath(filePostRegisterUser, "$.ucid");
    }

    @When("^User sends a POST request to register user with invalid data$")
    public void userSendsAPOSTRequestToRegisterUserWithInvalidData() throws Exception {
        String fNameValue = tUtil.AppendTimestamp("firstName");
        String lNameValue = tUtil.AppendTimestamp("lastName");
        String emailValue = tUtil.AppendTimestamp("automation@exmaple.com");

        jsonUtils.update_JSONValue(filePostRegisterUser, "$.email", "automationexample.com");
        jsonUtils.update_JSONValue(filePostRegisterUser, "$.firstName", fNameValue);
        jsonUtils.update_JSONValue(filePostRegisterUser, "$.lastName", lNameValue);

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(filePostRegisterUser, endPoint)
                .extract().response();

        jsonUtils.update_JSONValue(filePostRegisterUser, "$.email", emailValue);
    }

    @And("^the audienceType of the user should not be updated$")
    public void theAudienceTypeOfTheUserShouldNotBeUpdated() {
        sysResp = aicGETSteps.getUserFromAkamai(
                aicGETSteps.setEndpointAccessToken(kmap.get("ucid")))
                .extract().response();
        String authenToken = sysResp.jsonPath().getString("accessToken");
        endPoint = accExpGETSteps.setEndpointGetUserByUcid(kmap.get("ucid"));
        expResp = accExpGETSteps.getUserByUcid(endPoint, authenToken).extract().response();

        JsonPath jsonpathEvaluator = expResp.jsonPath();
        assertEquals(kmap.get("audienceType"), jsonpathEvaluator.getString("$.catalyst.audienceType"));
    }

    @When("^User sends a POST request to register new user with valid data$")
    public void userSendsAPOSTRequestToRegisterNewUserWithValidData() throws Exception {
        
    	String fileName = "MyAccExp_POST_Reg_LEAD_User.json";
        
    	jsonUtils.update_JSONValue(fileName, "$.ucId", kmap.get("ucid"));
    	jsonUtils.update_JSONValue(fileName, "$.email", kmap.get("email"));
    	jsonUtils.update_JSONValue(fileName, "$.firstName", kmap.get("firstName"));
    	jsonUtils.update_JSONValue(fileName, "$.lastName", kmap.get("lastName"));
    	
    	endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(fileName, endPoint)
                .extract().response();
    }

    @When("^User sends a POST request to register new user with invalid data$")
    public void userSendsAPOSTRequestToRegisterNewUserWithInvalidData() throws Exception {
    	
        String fileName = "MyAccExp_POST_Reg_LEAD_User_invalid.json";
        
        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(fileName, endPoint)
                .extract().response();
    }

    @Given("^Created new LEAD user with unique UUID to System API$")
    public void createdNewLEADUserWithUniqueUUIDToSystemAPI() throws Exception {
        endPoint = aicPOSTSteps.setEndPoint();
        String emailValue = tUtil.AppendTimestamp(autoEmail);
        jsonUtils.update_JSONValue("AIC_Customer.json", "$.email", emailValue);
        jsonUtils.add_JSONPathJsonValue("AIC_Customer.json", "$.audienceType", "LEAD");
        kmap.put("email", emailValue);
        sysResp = aicPOSTSteps.createCustomerInAkamai("AIC_Customer.json", endPoint)
                .extract().response();

        kmap.put("ucid", sysResp.jsonPath().getString("uuid"));
        kmap.put("audienceType", sysResp.jsonPath().getString("audienceType"));
        kmap.put("lastName", jsonUtils.getFromJSON("AIC_Customer.json", "lastName"));
        kmap.put("firstName", jsonUtils.getFromJSON("AIC_Customer.json", "firstName"));
        jsonUtils.remove_JSONPath("AIC_Customer.json", "$.audienceType");

        tUtil.putToSession("ucid", kmap.get("ucid"));
    }

    @And("^the audienceType of the user should be LEAD$")
    public void theAudienceTypeOfTheUserShouldBeLEAD() {
        sysResp = aicGETSteps.getUserFromAkamai(
                aicGETSteps.setEndpointAccessToken(kmap.get("ucid"))).extract().response();
        String authenToken = sysResp.jsonPath().getString("accessToken");
        endPoint = accExpGETSteps.setEndpointGetUserByUcid(kmap.get("ucid"));
        expResp = accExpGETSteps.getUserByUcid(endPoint, authenToken).extract().response();

        JsonPath jsonpathEvaluator = expResp.jsonPath();
        assertEquals(kmap.get("audienceType"), jsonpathEvaluator.getString("$.audienceType"));
    }

    @When("^User sends a GET request to My Account Experience API using (.*)$")
    public void userSendsAGETRequestToMyAccountExperienceAPIUsingEmail(String emailValue) {
        endPoint = accExpGETSteps.setEndpointCustomers();
        expResp = accExpGETSteps.getUserByEmail(endPoint, emailValue).extract().response();
    }

    @Then("^User receives the appropriate error message (.*) and status code (.*)$")
    public void userReceivesTheAppropriateErrorMessageMsgAndStatusCodeSts(String msg, int sts) {
        tUtil.verify_StsAndMsg(expResp, msg, sts);
    }

    @When("^User sends a GET request to My Account ExpAPI using email exist in Akamai$")
    public void userSendsAGETRequestToMyAccountExpAPIUsingEmailExistInAkamai() {
        endPoint = aicGETSteps.setEndpointUserID(kmap.get("ucid"));
        sysResp = aicGETSteps.get(endPoint).extract().response();
        String emailValue = sysResp.jsonPath().getString("email");

        endPoint = accExpGETSteps.setEndpointCustomers();
        expResp = accExpGETSteps.getUserByEmail(endPoint, emailValue).extract().response();
    }

    @Then("^user receives the information having Ucc Id, email and audience type$")
    public void userReceivesTheInformationHavingUccIdEmailAndAudienceType() {
        tUtil.verifyStatus(expResp, ResponseCode.OK);
        assertNotNull(expResp.jsonPath().getString("ucId"));
        assertNotNull(expResp.jsonPath().getString("email"));
        assertNotNull(expResp.jsonPath().getString("audienceType"));
    }

    @Then("^I do a GET request to Exp API to get subscriptions$")
    public void iDoAGETRequestToExpAPIToGetSubscriptions() {
        sysResp = aicGETSteps.get(
                aicGETSteps.setEndpointAccessToken(kmap.get("ucid")))
                .extract().response();
        String authenToken = sysResp.jsonPath().getString("accessToken");

        endPoint = accExpGETSteps.setEndpointUserSubscriptions(kmap.get("ucid"));
        expResp = accExpGETSteps.getUserSubscriptions(
                endPoint, authenToken).extract().response();
    }

    @When("^I do a PATCH request to Exp API to update firstName$")
    public void iDoAPATCHRequestToExpAPIToUpdateFirstName() throws URISyntaxException {
    	
        sysResp = aicGETSteps.get(aicGETSteps.setEndpointAccessToken(kmap.get("ucid")))
                .extract().response();
        
        String authenToken = sysResp.jsonPath().getString("accessToken");
        firstName = tUtil.AppendTimestamp("firstName");
        
        endPoint = accExpPATCHSteps.setEndpointUpdateUserByUcid(kmap.get("ucid"));
        expResp = accExpPATCHSteps.updateUser(
                endPoint, "firstName", firstName, authenToken)
                .extract().response();
    }

    @Then("^I see updated firstName field$")
    public void iSeeUpdatedFirstNameField() {
        String authToken = generateToken();
        endPoint = accExpGETSteps.setEndpointGetUserByUcid(kmap.get("ucid"));
        expResp = accExpGETSteps.getUserByUcid(endPoint, authToken)
                .extract().response();
        assertEquals(
                expResp.jsonPath().getString("firstName"),
                "New First Name"
        );
    }

    public String generateToken() {
        sysResp = aicGETSteps.get(aicGETSteps.setEndpointAccessToken(kmap.get("ucid")))
                .extract().response();
        return sysResp.jsonPath().getString("accessToken");
    }

    @Then("^firstName field updated$")
    public void firstnameFieldUpdated() throws Exception {
        
    	String authToken = generateToken();
        
    	endPoint = accExpGETSteps.setEndpointGetUserByUcid(kmap.get("ucid"));
        expResp = accExpGETSteps.getUserByUcid(endPoint, authToken)
                .extract().response();
        
        assertEquals(firstName, expResp.jsonPath().getString("firstName"));
        jsonUtils.update_JSONValue("AIC_Customer.json", "$.firstName", expResp.jsonPath().getString("firstName"));
    }

    @Then("^I do a PATCH request to Exp API to update lastName$")
    public void iDoAPATCHRequestToExpAPIToUpdateLastName() throws URISyntaxException {
        sysResp = aicPOSTSteps.createToken(kmap.get("ucid")).extract().response();
        String authenToken = sysResp.jsonPath().getString("accessToken");
        endPoint = accExpPATCHSteps.setEndpointUpdateUserByUcid(kmap.get("ucid"));
        expResp = accExpPATCHSteps.updateUser(
                endPoint, "lastName", "New Last Name", authenToken)
                .extract().response();
    }

    @When("^I do a POST request to webhook$")
    public void iDoAPOSTRequestToWebhook() throws Exception {
        String fileWebhook = "WebHook.json";

        JSONObject jsonData = jsonUtils.getJsonFromArray(fileWebhook);
        jsonData.put("uuid", kmap.get("ucid"));
        jsonData.put("datetime", Instant.now().toString());
        String query = "[" + jsonData.toString() + "]";

        endPoint = accExpPOSTSteps.setEndpointWebHook();
        expResp = accExpPOSTSteps.postWebHook(endPoint, query)
                .extract().response();
    }

    @Then("^I see response with status code 200$")
    public void iSeeResponseWithStatusCode() {
        Assert.assertEquals(expResp.statusCode(), ResponseCode.OK);
    }

    @When("^I send a POST request my-account to create a customer with context EMAIL$")
    public void iSendAPOSTRequestMyAccountToCreateACustomerWithContextEMAIL() throws Exception {
        String file = "flow_reg_EMAIL.json";
        String fNameValue = tUtil.AppendTimestamp("firstName");
        String lNameValue = tUtil.AppendTimestamp("lastName");
        email = tUtil.AppendTimestamp(autoEmail);

        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.firstName", fNameValue);
        jsonUtils.update_JSONValue(file, "$.lastName", lNameValue);

        endPoint = accExpPOSTSteps.setEndpontCustRegEmail();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();
        
        kmap.put("email", email);
        String ucidValue = expResp.jsonPath().getString("ucid");
        kmap.put("ucid", ucidValue);
        tUtil.putToSession("response", expResp);
    }

    @Then("^I do a GET request to get ucId$")
    public void iDoAGETRequestToGetUcId() {
        endPoint = accExpGETSteps.setEndpointCustomers();
        expResp = accExpGETSteps.getUserByEmail(endPoint, email).extract().response();
        String ucidValue = expResp.jsonPath().getString("ucId");
        kmap.put("ucid", ucidValue);
    }

    @When("^I send a GET request to AIC get user info$")
    public void iSendAGETRequestToAICGetUserInfo() throws InterruptedException {
    	
    	TimeUnit.SECONDS.sleep(1);
    	/*
    	 * This code being used by qauto640 and 641. And while verifying, API needs to wait because we want to
    	 * verify alternteId for ICV. If we don't wait alternateId is not getting updated on AIC so because of that
    	 * test is failing.
    	*/
        endPoint = aicGETSteps.setEndpointUserID(kmap.get("ucid"));
        sysResp = aicGETSteps.getUserFromAkamai(endPoint).extract().response();

        kmap.put("firstName", sysResp.jsonPath().getString("firstName"));
        kmap.put("lastName", sysResp.jsonPath().getString("lastName"));
        kmap.put("source", sysResp.jsonPath().getString("source[0].source"));
    }

    @When("^I send an async GET request to AIC get user info$")
    public void iSendAGETRequestToAICGet() throws InterruptedException {
        endPoint = aicGETSteps.setEndpointUserID(kmap.get("ucid"));
        TimeUnit.SECONDS.sleep(TIMEOUT);
        sysResp = aicGETSteps.getUserFromAkamai(endPoint).extract().response();

        kmap.put("firstName", sysResp.jsonPath().getString("firstName"));
        kmap.put("lastName", sysResp.jsonPath().getString("lastName"));
        kmap.put("source", sysResp.jsonPath().getString("source[0].source"));
    }

    @Then("^I check LEAD user data for AIC$")
    public void iCheckUserTypeIsLEAD() {
        assertEquals(sysResp.jsonPath().getString("audienceType"),
                "LEAD");
//        assertTrue(sysResp.jsonPath().getString("source.source").contains("connect"));
        assertEquals(sysResp.jsonPath().getString("emailPreferences.catalystSOI.optIn"),
                "true");
        assertNotNull(sysResp.jsonPath().getString("emailPreferences.catalystSOI.optInDate"));
        assertEquals(sysResp.jsonPath().getString("emailPreferences.catalystConnect.optIn"),
                "true");
        assertNotNull(sysResp.jsonPath().getString("emailPreferences.catalystConnect.optInDate"));
    }

    @When("^I send a POST request to complite registration$")
    public void iSendAPOSTRequestToCompliteRegistration() throws Exception {
        String file = "flow_reg_ucid_EMAIL.json";

        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.ucId", kmap.get("ucid"));

        endPoint = accExpPOSTSteps.setEndpontCustRegCatalystEvent();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();
    }

    @When("^I send a POST request to complite registration with pre-populated email$")
    public void iSendAPOSTRequestToCompliteRegistrationWithPrePopulatedEmail() throws Exception {
        String file = "flow_reg_ucid_pre_EMAIL.json";

        jsonUtils.update_JSONValue(file, "$.firstName", tUtil.AppendTimestamp("firstName"));
        jsonUtils.update_JSONValue(file, "$.lastName", tUtil.AppendTimestamp("lastName"));
        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.ucid", kmap.get("ucid"));

        endPoint = accExpPOSTSteps.setEndpontCustRegCatalystEvent(this.getRandomCJExpEvent());
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();
    }

    @When("^I send a POST request my-account to create a register customer with context EMAIL$")
    public void iSendAPOSTRequestMyAccountToCreateARegisterCustomerWithContextEMAIL() throws Exception {
        String file = "flow_reg_cust_EMAIL.json";
        String fNameValue = tUtil.AppendTimestamp("firstName");
        String lNameValue = tUtil.AppendTimestamp("lastName");
        email = tUtil.AppendTimestamp(autoEmail);

        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.firstName", fNameValue);
        jsonUtils.update_JSONValue(file, "$.lastName", lNameValue);

        kmap.put("firstName", fNameValue);
        kmap.put("lastName", lNameValue);

        endPoint = accExpPOSTSteps.setEndpontCustRegEmail();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();
    }

    @Then("^I check REGISTER user data for AIC$")
    public void iCheckREGISTERUserDataForAIC() {
        assertEquals(sysResp.jsonPath().getString("audienceType"),
                "REGISTERED USER");
//        aicGETSteps.verifyRegisterCJAudienceType(sysResp);
    }

    @When("^I send a POST request to sign up for connect$")
    public void iSendAPOSTRequestToSignUpForConnect() throws Exception {
        String file = "flow_reg_cust_sign_EMAIL.json";

        jsonUtils.update_JSONValue(filePostRegCatEvent, "$.email", email);
        jsonUtils.update_JSONValue(filePostRegCatEvent, "$.ucid", kmap.get("ucid"));
        jsonUtils.update_JSONValue(filePostRegCatEvent, "$.firstName", kmap.get("firstName"));
        jsonUtils.update_JSONValue(filePostRegCatEvent, "$.lastName", kmap.get("lastName"));

        endPoint = accExpPOSTSteps.setEndpontCustRegCatalystEvent(this.getRandomCJExpEvent());
        expResp = accExpPOSTSteps.post(filePostRegCatEvent, endPoint)
                .extract().response();
    }

    @When("^I send a POST request my-account to create a customer with context CATALYST$")
    public void iSendAPOSTRequestMyAccountToCreateACustomerWithContextCATALYST() throws Exception {
        String file = "flow_reg_cust_CATALYST.json";
        String fNameValue = tUtil.AppendTimestamp("firstName");
        String lNameValue = tUtil.AppendTimestamp("lastName");
        email = tUtil.AppendTimestamp(autoEmail);

        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.firstName", fNameValue);
        jsonUtils.update_JSONValue(file, "$.lastName", lNameValue);

        kmap.put("firstName", fNameValue);
        kmap.put("lastName", lNameValue);
        String eventId = getRandomEvent();
        endPoint = accExpPOSTSteps.setEndpontCustRegCatalystEvent(eventId);
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();
    }

    @Given("^I send a POST request with suffix (.*) role (.*) and (.*) "
            + "to my-account to create a customer with context CATALYST$")
    public void iSendAPOSTRequestMyCATALYST(String suffix, String role, String country) throws Exception {
        String file = "MyAccount_Reg_Catalyst.json";
        String fNameValue = tUtil.AppendTimestamp("firstName");
        String lNameValue = tUtil.AppendTimestamp("lastName");
        email = tUtil.AppendTimestamp(autoEmail);

        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.firstName", fNameValue);
        jsonUtils.update_JSONValue(file, "$.lastName", lNameValue);
        jsonUtils.update_JSONValue(file, "$.suffix", suffix);
        jsonUtils.update_JSONValue(file, "$.role", role);
        jsonUtils.update_JSONValue(file, "$.country", country);

        kmap.put("firstName", fNameValue);
        kmap.put("lastName", lNameValue);
        kmap.put("email", email);
        kmap.put("suffix", suffix);
        kmap.put("role", role);
        kmap.put("country", country);

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();
    }

    @When("^I send request to create REGISTER USER from LEAD$")
    public void iSendRequestToCreateREGISTERUSERFromLEAD() throws Exception {
        String file = "flow_reg_lead_CATALYST.json";

        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.ucId", kmap.get("ucid"));
        jsonUtils.update_JSONValue(file, "$.firstName", kmap.get("firstName"));
        jsonUtils.update_JSONValue(file, "$.lastName", kmap.get("lastName"));

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();
    }

    @When("^I send a POST request my-account to create a customer with context EVENT$")
    public void iSendAPOSTRequestMyAccountToCreateACustomerWithContextEVENT() throws Exception {
        String file = "flow_reg_EVENT.json";

        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.ucid", kmap.get("ucid"));
        jsonUtils.update_JSONValue(file, "$.firstName", kmap.get("firstName"));
        jsonUtils.update_JSONValue(file, "$.lastName", kmap.get("lastName"));
        
        String eventId = getRandomEvent();
        
        endPoint = accExpPOSTSteps.setEndpontCustRegCatalystEvent(eventId);
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();
    }
    
    @Then("^I send a GET request to ICV get user info$")
    public void iSendAGETRequestToICVGetUserInfo() throws URISyntaxException {
        sysResp = icvGETSteps.getUsers(icvGETSteps.setEndpoint(kmap.get("email")))
        		.extract().response();
        
        tUtil.putToSession("response", sysResp);
    }

    @Then("^I check LEAD user data for ICV$")
    public void iCheckLEADUserDataForICV() {
        assertEquals(sysResp.jsonPath().getString("audienceType"),
                "LEAD");
        assertEquals(sysResp.jsonPath().getString("emailPreferences.catalystSOI.optIn"),
                "true");
        assertNotNull(sysResp.jsonPath().getString("emailPreferences.catalystSOI.optInDate"));
        assertEquals(sysResp.jsonPath().getString("emailPreferences.catalystConnect.optIn"),
                "true");
        assertNotNull(sysResp.jsonPath().getString("emailPreferences.catalystConnect.optInDate"));
    }

    @When("^I send a POST request my-account to create LEAD with context EVENT$")
    public void iSendAPOSTRequestMyAccountToCreateLEADWithContextEVENT() throws Exception {
        String file = "flow_reg_lead_EVENT.json";
        String fNameValue = tUtil.AppendTimestamp("firstName");
        String lNameValue = tUtil.AppendTimestamp("lastName");
        email = tUtil.AppendTimestamp(autoEmail);

        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.firstName", fNameValue);
        jsonUtils.update_JSONValue(file, "$.lastName", lNameValue);
        String eventId = getRandomEvent();
        endPoint = accExpPOSTSteps.setEndpontCustRegCatalystEvent(eventId);
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();
        kmap.put("email", email);

    }

    @When("^I send a POST request to register for event$")
    public void iSendAPOSTRequestToRegisterForEvent() throws Exception {
        String file = "flow_reg_event_EVENT.json";
        email = tUtil.AppendTimestamp("automation@exmaple.com");
        String fNameValue = tUtil.AppendTimestamp("firstName");
        String lNameValue = tUtil.AppendTimestamp("lastName");

        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.firstName", fNameValue);
        jsonUtils.update_JSONValue(file, "$.lastName", lNameValue);

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();

        kmap.put("firstName", fNameValue);
        kmap.put("lastName", lNameValue);
        kmap.put("email", email);
    }

    @When("^I send a POST request to create LEAD with context EVENT$")
    public void iSendAPOSTRequestToCreateLEADWithContextEVENT() throws Exception {
        String file = "flow_reg_lead_EVENT.json";
        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.firstName", kmap.get("firstName"));
        jsonUtils.update_JSONValue(file, "$.lastName", kmap.get("firstName"));

        String eventId = getRandomEvent();

        endPoint = accExpPOSTSteps.setEndpontCustRegCatalystEvent(eventId);
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();
    }


    @Then("^I check user register for event$")
    public void iCheckUserRegisterForEvent() {
        List events = sysResp.jsonPath().getList("Catalyst.events");
        assertEquals(sysResp.jsonPath().getString("audienceType"),
                "LEAD");
        assertNotEquals(events.size(), 0);
        assertEquals(sysResp.jsonPath().getString("emailPreferences.catalystSOI.optIn"),
                "true");
        assertNotNull(sysResp.jsonPath().getString("emailPreferences.catalystSOI.optInDate"));
        assertEquals(sysResp.jsonPath().getString("emailPreferences.catalystConnect.optIn"),
                "true");
        assertNotNull(sysResp.jsonPath().getString("emailPreferences.catalystConnect.optInDate"));
    }

    @When("^I send a POST request my-account to create a register customer with context EVENT$")
    public void iSendAPOSTRequestMyAccountToCreateARegisterCustomerWithContextEVENT() throws Exception {
        String file = "flow_reg_cust_EVENT.json";

        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.ucid", kmap.get("ucid"));
        jsonUtils.update_JSONValue(file, "$.firstName", kmap.get("firstName"));
        jsonUtils.update_JSONValue(file, "$.lastName", kmap.get("lastName"));

        String eventId = getRandomEvent();
        
        endPoint = accExpPOSTSteps.setEndpontCustRegCatalystEvent(eventId);
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();
    }

    @Then("^I check REGISTER user data for ICV$")
    public void iCheckREGISTERUserDataForICV() {
        assertEquals(sysResp.jsonPath().getString("audienceType"),
                "REGISTERED USER");
        String idType = sysResp.jsonPath().getString("alternateID[0].IDType");
        List<String> listIDTypes = Arrays.asList("MAGENTO", "ICV");
        assertTrue(listIDTypes.stream().anyMatch(idType::contains));
    }

    @Then("^I send a POST request REGISTER USER for event$")
    public void iSendAPOSTRequestREGISTERUSERForEvent() throws Exception {
        String file = "flow_reg_event_user_EVENT.json";

        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.ucId", kmap.get("ucid"));
        jsonUtils.update_JSONValue(file, "$.registrationSource", kmap.get("source"));

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();
    }

    @When("^I send a POST request to Apply Now with context EMAIL$")
    public void iSendAPOSTRequestToApplyNowWithContextEMAIL() throws Exception {
        String file = "flow_apply_now_IC.json";
        String fNameValue = tUtil.AppendTimestamp("firstName");
        String lNameValue = tUtil.AppendTimestamp("lastName");
        email = tUtil.AppendTimestamp(autoEmail);

        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.firstName", fNameValue);
        jsonUtils.update_JSONValue(file, "$.lastName", lNameValue);


        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();
    }

    @When("^I send request to join Insights Council$")
    public void iSendRequestToJoinInsightsCouncil() throws Exception {
        String file = "flow_join_IC.json";

        jsonUtils.update_JSONValue(file, "$.ucid", kmap.get("ucid"));
        Response resp = aicPOSTSteps.createToken(kmap.get("ucid"))
                .extract().response();
        token = aicPOSTSteps.getToken(resp);

        endPoint = accExpPOSTSteps.setEndpointRegistrations();
        expResp = accExpPOSTSteps.postToken(file, endPoint, token)
                .extract().response();
    }

    @When("^I send a POST request to Apply Now with user ucId$")
    public void iSendAPOSTRequestToApplyNowWithUserUcId() throws Exception {
        String file = "flow_apply_now_ucid_IC.json";
        String fNameValue = tUtil.AppendTimestamp("firstName");
        String lNameValue = tUtil.AppendTimestamp("lastName");
        email = tUtil.AppendTimestamp(autoEmail);

        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.ucId", kmap.get("ucid"));
        jsonUtils.update_JSONValue(file, "$.firstName", fNameValue);
        jsonUtils.update_JSONValue(file, "$.lastName", lNameValue);

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();

        kmap.put("firstName", fNameValue);
        kmap.put("lastName", lNameValue);
    }

    @When("^I send a POST request my-account to create a register non qual customer with context EMAIL$")
    public void iSendAPOSTRequestMyAccountToCreateARegisterNonQualCustomerWithContextEMAIL() throws Exception {
        String file = "flow_reg_non_qual_cust_EMAIL.json";
        String fNameValue = tUtil.AppendTimestamp("firstName");
        String lNameValue = tUtil.AppendTimestamp("lastName");
        email = tUtil.AppendTimestamp(autoEmail);

        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.firstName", fNameValue);
        jsonUtils.update_JSONValue(file, "$.lastName", lNameValue);


        endPoint = accExpPOSTSteps.setEndpontCustRegEmail();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();
    }

    @When("^I send a POST request to Apply Now non qual customer$")
    public void iSendAPOSTRequestToApplyNowNonQualCustomer() throws Exception {
        String file = "flow_apply_now_non_qual_IC.json";
        String fNameValue = tUtil.AppendTimestamp("firstName");
        String lNameValue = tUtil.AppendTimestamp("lastName");
        email = tUtil.AppendTimestamp(autoEmail);

        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.ucId", kmap.get("ucid"));
        jsonUtils.update_JSONValue(file, "$.firstName", fNameValue);
        jsonUtils.update_JSONValue(file, "$.lastName", lNameValue);

        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();

        kmap.put("firstName", fNameValue);
        kmap.put("lastName", lNameValue);
    }

    @When("^I send request non qual to join Insights Council$")
    public void iSendRequestNonQualToJoinInsightsCouncil() throws Exception {
        String file = "flow_join_non_qual_IC.json";
        jsonUtils.update_JSONValue(file, "$.ucid", kmap.get("ucid"));

        Response resp = aicPOSTSteps.createToken(kmap.get("ucid"))
                .extract().response();
        token = aicPOSTSteps.getToken(resp);

        endPoint = accExpPOSTSteps.setEndpointRegistrations();
        expResp = accExpPOSTSteps.postToken(file, endPoint, token)
                .extract().response();
    }

    @And("^I check user is REGISTER for IC$")
    public void iCheckUserDataForIC() {
        assertEquals(sysResp.jsonPath().getString("audienceType"),
                "REGISTERED USER");
        assertEquals(sysResp.jsonPath().getString("Catalyst.qualifiedForCouncil"),
                "true");
        assertEquals(sysResp.jsonPath().getString("Catalyst.insightsCouncilMember"),
                "true");
    }

    @And("^I check user REGISTERED USER qual data for IC$")
    public void iCheckUserREGISTEREDUSERQualDataForIC() {
        assertEquals(sysResp.jsonPath().getString("audienceType"),
                "REGISTERED USER");
        assertEquals(sysResp.jsonPath().getString("Catalyst.qualifiedForCouncil"),
                "true");
        assertEquals(sysResp.jsonPath().getString("Catalyst.insightsCouncilMember"),
                "true");
    }

    @And("^I check non qual user REGISTERED USER data for IC$")
    public void iCheckNonQualUserREGISTEREDUSERDataForIC() {
        assertEquals(sysResp.jsonPath().getString("audienceType"),
                "REGISTERED USER");
        assertEquals(sysResp.jsonPath().getString("Catalyst.qualifiedForCouncil"),
                "true");
        assertEquals(sysResp.jsonPath().getString("Catalyst.insightsCouncilMember"),
                "true");
    }

    @When("^I send request to create non qual REGISTER USER from LEAD$")
    public void iSendRequestToCreateNonQualREGISTERUSERFromLEAD() throws Exception {
        String file = "flow_reg_non_qual_lead_CATALYST.json";

        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.ucId", kmap.get("ucid"));
        jsonUtils.update_JSONValue(file, "$.firstName", kmap.get("firstName"));
        jsonUtils.update_JSONValue(file, "$.lastName", kmap.get("lastName"));


        endPoint = accExpPOSTSteps.setEndpointRegisterForEvent();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();
    }

    @And("^I check non qual REGISTERED USER for IC$")
    public void iCheckNonQualREGISTEREDUSERForIC() {
        assertEquals(sysResp.jsonPath().getString("audienceType"),
                "REGISTERED USER");
        assertEquals(sysResp.jsonPath().getString("Catalyst.qualifiedForCouncil"),
                "false");
        assertEquals(sysResp.jsonPath().getString("Catalyst.insightsCouncilMember"),
                null);
    }

    @When("^I create no (.*) of users of type (.*)$")
    public void createUsersOfGivenType(int no, String type) throws Exception {
        for (int i = 0; i < no; i++) {
            Map<String, String> map = new HashMap<>();
            iSendAPOSTRequestMyAccountToCreateARegisterCustomerWithContextEMAIL();
            iDoAGETRequestToGetUcId();
            iSendAGETRequestToAICGetUserInfo();

            String uuid = sysResp.jsonPath().getString("uuid");
            map.put("uuid", uuid);

            email = sysResp.jsonPath().getString("email");
            map.put("email", email);

            String dateCreated = sysResp.jsonPath().getString("dateCreated");
            //fast and ugly
            // CHECKSTYLE:OFF: MagicNumber
            map.put("date", dateCreated.split(" ")[0] + "T" + dateCreated.split(" ")[1].substring(0, 8));
            //CHECKSTYLE:ON: MagicNumber

            orderSet.add(map);
        }
    }

    @Then("^excel should be updated with created user details$")
    public void updateuserExcel() throws Exception {
        // TODO need refactor, move to other
        System.out.println("Creating file");
        try {
            String filename = "ExcelFile.xlsx";
            XSSFWorkbook wrkBook = new XSSFWorkbook();
            XSSFSheet mySheet = wrkBook.createSheet("sheet1");
            int i = 0;
            for (Map<String, String> entry
                    : orderSet) {
                XSSFRow row = mySheet.createRow(i);
                row.createCell(0).setCellValue(entry.get("uuid"));
                row.createCell(1).setCellValue(entry.get("email"));
                row.createCell(2).setCellValue(entry.get("date"));
                i++;
            }

            FileOutputStream fileOut = new FileOutputStream(filename);
            wrkBook.write(fileOut);
            fileOut.close();
            wrkBook.close();
            System.out.println("Your excel file has been generated!");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @Then("^the request should return 201 code and check user information$")
    public void theRequestShouldReturnStatusCodeAndCheckUserInformation() {
        tUtil.verifyStatus(expResp, ResponseCode.CREATED);
        assertNotNull(expResp.jsonPath().getString("ucid"));
        assertNotNull(expResp.jsonPath().getString("authenticationToken"));
        assertNotNull(expResp.jsonPath().getString("audienceType"));
    }

    @When("User send a POST request to Catalyst with data:")
    public void userSendAPOSTRequestToCatalystWithData(DataTable dataTable) throws Exception {

        String file = "AccExp_POST_CustRegCat.json";
        Map<String, String> row = CucumberUtils.convert(dataTable);
        accHelper.update_File_Reg_Catalyst(file, row);
        //   updateFileRegCatalyst(file, row);

        endPoint = accExpPOSTSteps.setEndpontCustRegCatalyst();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();

        ucid = expResp.jsonPath().get("ucid");
    }

    @When("^I do a request to Literatum Indentity$")
    public void userCallsLiteratumIndentity() throws InterruptedException {
        endPoint = catJLSystmGET.setEndpointIdentity(ucid);
        System.out.println("Waiting for async request to get finished");
        TimeUnit.SECONDS.sleep(TIMEOUT * 2);
        literRespIdentity = catJLSystmGET.get(endPoint)
                .extract().response();
    }

    @When("^I do a request to Literatum License$")
    public void userCallsLiteratumLicense() throws InterruptedException {
        endPoint = catJLSystmGET.setEndpointLicenses(ucid);
        TimeUnit.SECONDS.sleep(TIMEOUT);
        literRespLicense = catJLSystmGET.get(endPoint)
                .extract().response();
    }

    @Then("^I check Literatum Indentity information$")
    public void checkLiteratumIndentityInformation() {
        tUtil.verifyStatus(literRespIdentity, ResponseCode.OK);
        Assert.assertNotEquals(
                literRespIdentity.jsonPath().getList("tag").size(), 0);
    }

    @Then("^I check Literatum License information$")
    public void checkLiteratumLicenseInformation() {
        tUtil.verifyStatus(literRespLicense, ResponseCode.OK);
        Assert.assertNotNull(literRespLicense.jsonPath().getString("code"));
    }

    @When("^I do a request to Akamai$")
    public void userCallsAkamai() throws Throwable {
        int timeOutMin = 12;

        endPoint = aicGETSteps.setEndpointUserID(ucid);

        do {
            System.out.println("Waiting for request ACS to get finished");
            TimeUnit.SECONDS.sleep(5);

            sysResp = aicGETSteps.getUserFromAkamai(endPoint)
                    .extract().response();
            if (sysResp.statusCode() == ResponseCode.OK) {
                break;
            }
            timeOutMin--;
        } while (timeOutMin != 0);
    }

    @Then("^I check Akamai response$")
    public void checkAkamaiResponse() {
        Assert.assertEquals(sysResp.jsonPath().get("uuid"), ucid);
    }

    @When("User send a POST request to Catalyst Email Registration with data:")
    public void userSendAPOSTRequestToCatalystEmailRegistrationWithData(DataTable dataTable) throws Exception {

        String file = "AccExp_POST_CustRegEmail.json";
        Map<String, String> row = CucumberUtils.convert(dataTable);
        accHelper.update_File_Reg_Email(file, row);
        //    updateFileRegEmail(file, row);

        endPoint = accExpPOSTSteps.setEndpontCustRegEmail();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();

        ucid = expResp.jsonPath().get("ucid");
    }

    @When("User send a POST request to Catalyst PDF Registration with data:")
    public void userSendAPOSTRequestToCatalystPDFRegistrationWithData(DataTable dataTable) throws Exception {

        String file = "AccExp_POST_CustRegPDF.json";
        Map<String, String> row = CucumberUtils.convert(dataTable);
        accHelper.update_File_Reg_PDF(file, row);
        //     updateFileRegPDF(file, row);

        endPoint = accExpPOSTSteps.setEndpontCustRegPDF();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();

        ucid = expResp.jsonPath().get("ucid");
    }

    @When("User send a POST request to Catalyst Email Registration with same data")
    public void userSendAPOSTRequestToCatalystEmailRegistrationWithSameData() {

        String file = "AccExp_POST_CustRegEmail.json";

        endPoint = accExpPOSTSteps.setEndpontCustRegEmail();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();

        ucid = expResp.jsonPath().get("ucid");
    }


    @Then("^I see response with status code 500$")
    public void iSeeResponseWithStatusCode500() {
        Assert.assertEquals(expResp.statusCode(), ResponseCode.INTERNAL_ERROR);
    }

    @When("User send a POST request to Catalyst Email Registration with mandatory fields:")
    public void userSendAPOSTRequestToCatalystEmailRegistrationWithMandatoryFields(
            DataTable dataTable) throws Exception {

        String file = "AccExp_POST_CustRegEmail_Mandatory.json";
        Map<String, String> row = CucumberUtils.convert(dataTable);
        accHelper.update_File_Reg_Mandatory_Fields_Email(file, row);
        //    updateFileRegMandatoryFieldsEmail(file, row);

        endPoint = accExpPOSTSteps.setEndpontCustRegEmail();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();

        ucid = expResp.jsonPath().get("ucid");
    }
    
    @When("^User send a POST request to Catalyst with mandatory fields:$")
	public void userSendAPOSTRequestToCatalystWithMandatoryData(DataTable dataTable) throws Exception {

		String file = "AccExp_POST_CustRegCat_Mandate_fields.json";
		Map<String, String> row = CucumberUtils.convert(dataTable);
		accHelper.update_File_Reg_Catalyst_Mandatory_fields(file, row);
	//	update_File_Reg_Catalyst_Mandatory_fields(file, row);

		endPoint = accExpPOSTSteps.setEndpontCustRegCatalyst();
		expResp = accExpPOSTSteps.post(file, endPoint)
				.extract().response();

		ucid = expResp.jsonPath().get("ucid");
	}
    
    @When("^User send a POST request to Catalyst with mandatory fields with password:$")
	public void userSendAPOSTRequestToCatalystWithMandatoryDatawithPassword(DataTable dataTable) throws Exception {

		String file = AccountJsonBodyFile.Account_Catalyst_REG;
       
		Map<String, String> row = CucumberUtils.convert(dataTable);

		UpdateAccountFiles.update_File_Reg_Catalyst(file, row);

		endPoint = accExpPOSTSteps.setEndpontCustRegCatalyst();
		expResp = accExpPOSTSteps.post(file, endPoint)
				.extract().response();

		ucid = expResp.jsonPath().get("ucid");
		
        tUtil.putToSession("ucid", ucid);
        tUtil.putToSession("email", jsonUtils.getFromJSON(file, "$.email"));
        tUtil.putToSession("firstName", jsonUtils.getFromJSON(file, "$.firstName"));
        tUtil.putToSession("lastName", jsonUtils.getFromJSON(file, "$.lastName"));
        tUtil.putToSession("token", expResp.jsonPath().get("authenticationToken"));
	    
    }
    
    @When("^User send a POST request to Catalyst for registered user with following$")
    public void userSendAPOSTRequ(DataTable dataTable) throws Exception {

        String file = AccountJsonBodyFile.Account_Catalyst_REG;
       
        Map<String, String> row = CucumberUtils.convert(dataTable);

        UpdateAccountFiles.update_File_Reg_Catalyst_excel(file, row);

        endPoint = accExpPOSTSteps.setEndpontCustRegCatalyst();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();

        ucid = expResp.jsonPath().get("ucid");
        
        tUtil.putToSession("ucid", ucid);
        tUtil.putToSession("email", jsonUtils.getFromJSON(file, "$.email"));
        tUtil.putToSession("firstName", jsonUtils.getFromJSON(file, "$.firstName"));
        tUtil.putToSession("lastName", jsonUtils.getFromJSON(file, "$.lastName"));
        tUtil.putToSession("token", expResp.jsonPath().get("authenticationToken"));
        
    }
	
	@When("User send a POST request to Catalyst with all the fields except password:")
	public void userSendAPOSTRequestToCatalystWithallfieldsexceptPassword(DataTable dataTable) throws Exception {
        String AccExp_POST_CustRegCat_AllFields_Except_Password = "AccExp_POST_CustRegCat_AllFields_Except_Password.json";
		Map<String, String> row = CucumberUtils.convert(dataTable);

		accHelper.update_File_Reg_Catalyst_All_fields(AccExp_POST_CustRegCat_AllFields_Except_Password, row);

		endPoint = accExpPOSTSteps.setEndpontCustRegCatalyst();
		expResp = accExpPOSTSteps.post(AccExp_POST_CustRegCat_AllFields_Except_Password, endPoint)
				.extract().response();

		ucid = expResp.jsonPath().get("ucid");
	}
	
	@When("^User sends a password and Ucid with mandatory fields for existing lead user along with same data:$")
	public void userSendAPOSTRequestToChangeLeadtoRegisterUser(DataTable dataTable) throws Exception {

		String file = "AccExp_POST_CustRegCat_Mandate_fields_Lead_to_Register.json";
		Map<String, String> row = CucumberUtils.convert(dataTable);

		accHelper.update_File_Reg_Catalyst_Mandatory_Lead_to_Register(file, row, ucid);

		endPoint = accExpPOSTSteps.setEndpontCustRegCatalyst();
		expResp = accExpPOSTSteps.post(file, endPoint)
				.extract().response();

		ucid = expResp.jsonPath().get("ucid");
	}
	
	
	@When("^User sends a password and Ucid with all fields for existing lead user along with same data:$")
	public void userSendAPOSTRequestToChangeLeadtoRegisterUserallFields(DataTable dataTable) throws Exception {
        String AccExp_POST_CustRegCat_AllFields_Lead_to_Register = "AccExp_POST_CustRegCat_AllFields_Lead_to_Register.json";
		Map<String, String> row = CucumberUtils.convert(dataTable);

		accHelper.update_File_Reg_Catalyst_AllFields_Lead_to_Register(AccExp_POST_CustRegCat_AllFields_Lead_to_Register, row, ucid);

		endPoint = accExpPOSTSteps.setEndpontCustRegCatalyst();
		expResp = accExpPOSTSteps.post(AccExp_POST_CustRegCat_AllFields_Lead_to_Register, endPoint)
				.extract().response();

		ucid = expResp.jsonPath().get("ucid");
	}

    @When("^User send a POST request to Catalyst Email Registration with data for qualified:$")
    public void userSendAPOSTRequestToCatalystEmailRegistrationWithDataForQualified(
            DataTable dataTable) throws Exception {

        String file = "AccExp_POST_CustRegEmail.json";
        Map<String, String> row = CucumberUtils.convert(dataTable);
        accHelper.update_File_Reg_Email(file, row);
        //      updateFileRegEmail(file, row);

        endPoint = accExpPOSTSteps.setEndpontCustRegEmail();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();

        ucid = expResp.jsonPath().get("ucid");
    }

    @When("User send a POST request to Catalyst PDF Registration REGISTER USER with same data")
    public void userSendAPOSTRequestToCatalystPDFRegistrationREGISTERUSERWithSameData() {
        String file = "AccExp_POST_CustRegPDF.json";
        endPoint = accExpPOSTSteps.setEndpontCustRegPDF();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();

        ucid = expResp.jsonPath().get("ucid");
    }

    @When("User send a POST request to Catalyst PDF Registration LEAD user with data:")
    public void userSendAPOSTRequestToCatalystPDFRegistrationLEADUserWithData(
            DataTable dataTable) throws Exception {

        String file = "AccExp_POST_CustRegPDF_LEAD.json";
        Map<String, String> row = CucumberUtils.convert(dataTable);
        accHelper.update_File_Reg_PDF(file, row);
        //      updateFileRegPDF(file, row);

        endPoint = accExpPOSTSteps.setEndpontCustRegPDF();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();

        ucid = expResp.jsonPath().get("ucid");
    }

    @When("User send a POST request to Catalyst PDF Registration LEAD user with same data")
    public void userSendAPOSTRequestToCatalystPDFRegistrationLEADUserWithSameData() {
        String file = "AccExp_POST_CustRegPDF_LEAD.json";

        endPoint = accExpPOSTSteps.setEndpontCustRegPDF();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();

        ucid = expResp.jsonPath().get("ucid");
    }

    @When("User send a POST request to Catalyst PDF Registration REGISTER USER with mandatory:")
    public void userSendAPOSTRequestToCatalystPDFRegistrationREGISTERUSERWithMandatory(
            DataTable dataTable) throws Exception {

        String file = "AccExp_POST_CustRegPDF_Mandatory.json";
        
        jsonUtils.update_JSONValue(file, "firstName", kmap.get("firstName"));
        jsonUtils.update_JSONValue(file, "lastName", kmap.get("lastName"));
        jsonUtils.update_JSONValue(file, "email", kmap.get("email"));
        jsonUtils.update_JSONValue(file, "ucid", kmap.get("ucid"));
        
        Map<String, String> row = CucumberUtils.convert(dataTable);
        accHelper.update_File_Reg_Mandatory_Fields_Email(file, row);
        //       updateFileRegMandatoryFields(file, row);

        endPoint = accExpPOSTSteps.setEndpontCustRegEmail();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();

        ucid = expResp.jsonPath().get("ucid");
    }

    @When("User send a POST request to Catalyst PDF Registration LEAD with mandatory:")
    public void userSendAPOSTRequestToCatalystPDFRegistrationLEADUSERWithMandatory(
            DataTable dataTable) throws Exception {
        String file = "AccExp_POST_CustRegPDF_LEAD_Mandatory.json";
        Map<String, String> row = CucumberUtils.convert(dataTable);
        accHelper.update_File_Reg_Mandatory_Fields_Email(file, row);
        //      updateFileRegMandatoryFields(file, row);

        endPoint = accExpPOSTSteps.setEndpontCustRegEmail();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();

        ucid = expResp.jsonPath().get("ucid");
    }

    @When("^User send a POST request to Catalyst with mandatory fields for REG USER:$")
    public void userSendAPOSTRequestToCatalystWithMandatoryFieldsForREGUSER(DataTable dataTable) throws Exception {
    	
        String fileName = "AccExp/AccExp_POST_Catalyst_REG_possible_mandatory_values.json";
        
        jsonUtils.update_JSONValue(fileName, "firstName", kmap.get("firstName"));
        jsonUtils.update_JSONValue(fileName, "lastName", kmap.get("lastName"));
        jsonUtils.update_JSONValue(fileName, "email", kmap.get("email"));
        jsonUtils.update_JSONValue(fileName, "ucid", kmap.get("ucid"));
        
        Map<String, String> row = CucumberUtils.convert(dataTable);
        accHelper.update_File_Reg_Mandatory_Fields_Email(fileName, row);
  //      updateFileRegMandatoryFields(fileName, row);

        endPoint = accExpPOSTSteps.setEndpontCustRegCatalyst();
        expResp = accExpPOSTSteps.post(fileName, endPoint)
                .extract().response();
    }

    @Then("^Verify request status code (.*)$")
    public void catalystRequestShouldReturnStsCode(int sts) {
        tUtil.verifyStatus(expResp, sts);
    }


    public String getRandomEvent() throws URISyntaxException {

        endPoint = icvPOSTSteps.setConferenceEndpoint();
        sysResp = icvGETSteps.getUsers(endPoint)
                .extract().response();
        List lst = sysResp.jsonPath().get();
        HashMap map = (HashMap) lst.get(0);
        return String.valueOf(map.get("id"));
    }

    public String getRandomCJExpEvent() throws URISyntaxException {

        endPoint = icvPOSTSteps.setConferenceEndpoint();
        sysResp = icvGETSteps.getUsers(endPoint)
                .extract().response();

        endPoint = cjExpGETSteps.setEndpointEvents();
        expResp = cjExpGETSteps.getEvent(endPoint).extract().response();
        JsonPath jsonpathEvaluator = expResp.jsonPath();
        List<Integer> loEventId = jsonpathEvaluator.get("id");

        Random rand = new Random();
        return String.valueOf(loEventId.get(rand.nextInt(loEventId.size())));
    }
    
    @When("^user send a post request to generate sso url token$")
    public void postRequest_To_GenerateSsoUrlToken() throws Exception
    {
         String filePostGenerateSSOAuthToken = "AccExp_POST_Generate_SSO_Auth_Token.json";
    	 String file = "registration_qualtoIC_RegisteredUser.json";
    	 String filePostSSOAuthTokenUrl = "AccExp_POST_SSO_Auth_Token_Url.json";
    	 String emailValue = jsonUtils.getFromJSON(file, "email").toString();
    	 String exp=commonFunc.getCustomDateTime("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "year");
    	 jsonUtils.update_JSONValue(filePostGenerateSSOAuthToken, "$.username", emailValue);
         jsonUtils.update_JSONValue(filePostGenerateSSOAuthToken, "$.exp", exp);
    	 endPoint = accExpPOSTSteps.setEndpontSsoUrlToken();
    	 expResp = accExpPOSTSteps.postSsoToken(filePostGenerateSSOAuthToken, endPoint)
                 .extract().response();
    	 jsonUtils.update_JSONValue(filePostSSOAuthTokenUrl, "$.widgetTestUrl", expResp.jsonPath().get("widgetTestUrl").toString());
    	 jsonUtils.update_JSONValue(filePostSSOAuthTokenUrl, "$.myacctTestUrl", expResp.jsonPath().get("myacctTestUrl").toString());
    }

    @Then("^I check user Catalyst subscription status$")
    public void iCheckUserCatalystSubscriptionStatus() {

        String status = sysResp.jsonPath().getMap("Catalyst").get("subscriptionStatus").toString();
        List<String> statusList = Arrays.asList(
                SubscriptionStatus.SUBSCRIBER,
                SubscriptionStatus.EXP_SUBSCRIBER,
                SubscriptionStatus.NONE
        );

        if (sysResp.jsonPath().getString("audienceType").equals("REGISTERED USER")) {
            assertTrue(statusList.contains(status));
        } else {
            assertEquals(status, SubscriptionStatus.NONE);
        }
    }


    @When("User send a POST request to create user with NotChecked email:")
    public void userSendAPOSTRequestToCreateUserWithNotCheckedEmail(DataTable dataTable) throws Exception {

        String file = "AccExp_POST_CustRegCat_Mandate_fields with password.json";
        Map<String, String> row = CucumberUtils.convert(dataTable);

  //      update_File_Reg_Catalyst_NotChecked_Email(file, row);

        endPoint = accExpPOSTSteps.setEndpontCustRegCatalyst();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();

        ucid = expResp.jsonPath().get("ucid");
        tUtil.putToSession("ucid", ucid);
        tUtil.putToSession("email", jsonUtils.getFromJSON(file, "$.email"));
        tUtil.putToSession("firstName", jsonUtils.getFromJSON(file, "$.firstName"));
        tUtil.putToSession("lastName", jsonUtils.getFromJSON(file, "$.lastName"));

    }

    // NEJM accounts
    @When("^I do a POST call to customers_registrations_nejm to register a user$")
    public void iDoAPOSTCallTo_regNEJM() throws Exception {
        String customers_registrations_nejm = "AccExp_POST_NEJM_cust_reg.json";
        endPoint = accExpPOSTSteps.setEndpointCustRegNEJM();
        email = tUtil.AppendTimestamp(autoEmail);
        String fname = tUtil.AppendTimestamp("firstName");
        String lname = tUtil.AppendTimestamp("lastName");
        jsonUtils.update_JSONValue(customers_registrations_nejm, "$.email", email);
        jsonUtils.update_JSONValue(customers_registrations_nejm, "$.firstName", fname);
        jsonUtils.update_JSONValue(customers_registrations_nejm, "$.lastName", lname);

        expResp = accExpPOSTSteps.post(customers_registrations_nejm, endPoint)
            .extract().response();

        kmap.put("email", email);
        ucid = expResp.jsonPath().getString("ucid");
        token = expResp.jsonPath().getString("authenticationToken");
        tUtil.putToSession("response", expResp);

        tUtil.putToSession("ucid", expResp.jsonPath().getString("ucid"));
        tUtil.putToSession("email", email);
        tUtil.putToSession("firstName", fname);
        tUtil.putToSession("lastName", lname);
        tUtil.putToSession("token", expResp.jsonPath().getString("authenticationToken"));

    }

    @When("^User calls GET store NEJM customer with ucid$")
    public void user_calls_GET_store_customer() {

        endPoint = storeGET.setEndpointCustomer(ucid);
        expResp = storeGET.getStore(endPoint, token)
            .extract().response();
    }

    @And("User check a new customer")
    public void userCheckANewCustomer() {
        Map<String, String> customerMap = expResp.jsonPath().getMap("Customer");
        assertEquals(customerMap.get("ucid"), ucid);
        assertEquals(customerMap.get("email"), email);
    }

    @Then("I see pdf premium in the exp response")
    public void iSeePdfPremiumInTheExpResponse() {
        List<Object> catalystMap = expResp.jsonPath().get("catalyst.pdfPremiums");
        assertNotEquals(catalystMap.size(), 0);
    }

    @Then("I see pdf premium in the sys response")
    public void iSeePdfPremiumInTheSysResponse() {
        List<Object> catalystMap = sysResp.jsonPath().get("Catalyst.pdfPremiums");
        assertNotEquals(catalystMap.size(), 0);
    }
    
    @And("^user send a post request to enable or disable Captcha:$")
    public void postRequest_To_GenerateSsoUrlToken(DataTable dt) throws Exception
    {
    	 Map<String, String> map = CucumberUtils.convert(dt);
    	 String fileCaptchaSettings = "AccExp/enableDisableCaptcha.json";
     	 jsonUtils.update_JSONValue(fileCaptchaSettings, "$.value", Boolean.parseBoolean(map.get("Captcha Settings")));
    	 endPoint = accExpPOSTSteps.setEndpointCaptchaSettings();
    	 expResp = accExpPOSTSteps.postCaptchaSettings(fileCaptchaSettings, endPoint)
                 .extract().response();
    	 Assert.assertEquals("true", expResp.jsonPath().getString("success"));
    }
    
    @When("^user send a post request to generate sso url token for NEJM Reg user$")
    public void postRequest_To_GenerateSsoUrlToken_NejmReg() throws Exception
    {
         String filePostGenerateSSOAuthToken = "AccExp_POST_Generate_SSO_Auth_Token.json";
    	 String file = "AccExp_POST_NEJM_cust_reg.json";
    	 String filePostSSOAuthTokenUrl = "AccExp_POST_SSO_Auth_Token_Url.json";
    	 String emailValue = jsonUtils.getFromJSON(file, "email").toString();
    	 String exp=commonFunc.getCustomDateTime("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "year");
    	 jsonUtils.update_JSONValue(filePostGenerateSSOAuthToken, "$.username", emailValue);
         jsonUtils.update_JSONValue(filePostGenerateSSOAuthToken, "$.exp", exp);
    	 endPoint = accExpPOSTSteps.setEndpontSsoUrlToken();
    	 expResp = accExpPOSTSteps.postSsoToken(filePostGenerateSSOAuthToken, endPoint)
                 .extract().response();
    	 jsonUtils.update_JSONValue(filePostSSOAuthTokenUrl, "$.widgetTestUrl", expResp.jsonPath().get("widgetTestUrl").toString());
    	 jsonUtils.update_JSONValue(filePostSSOAuthTokenUrl, "$.myacctTestUrl", expResp.jsonPath().get("myacctTestUrl").toString());
    }
    
    @When("I fetch the top ten subscription ids for {string} acc exp api from DB")
	public void fetch_subscrptnIds_frmDB(String litrtmTyp) throws IOException, SQLException {
    	String sqlQuery = null;
		if (litrtmTyp.equals("Nejm")) {
			sqlQuery = dbUtils.buildQuery("NEJMCustomers_with_subscription_Ids.sql");
		} else if (litrtmTyp.equals("Catalyst")) {
			sqlQuery = dbUtils.buildQuery("CatalystCustomers_with_subscription_Ids.sql");
		}
		String dbURL = dbUtils.buildDbUrl("ACSDbUrl");
		Connection conn = ConnectionFactory.getConnectionACS(dbURL, dbUtils.acsDBUser(), dbUtils.acsDBPass());
		PreparedStatement ps = conn.prepareStatement(sqlQuery);

		ResultSet resultSet = ps.executeQuery();

		while (resultSet.next()) {
			subsIdList.add(resultSet.getString("bil_ctm"));
		}

	}
    
    public void user_creates_inid_catalyst() throws Exception {
		String file = CatalystLiteratumSystemJsonBodyFile.Catalyst_Create_New_inid;
		jsonUtils.update_JSONValue(file, "inid", subscrptnID);
		endPoint = catJLSystmPOST.setEndpontIdentitiesInstitution();
		sysResp = catJLSystmPOST.post(file, endPoint).extract().response();
	}
    
    public void user_creates_inid_nejm() throws Exception {
		String file = NEJMLiteratumSystemJsonBodyFile.NEJM_Create_New_inid;
		jsonUtils.update_JSONValue(file, "inid", subscrptnID);
		endPoint = nejmLiteratumSystemPOST.setEndpontIdentitiesInstitution();
		sysResp = nejmLiteratumSystemPOST.post(file, endPoint).extract().response();
	}
    
    @When("user sends a POST call to provide institutional activation for {int} subscribed institution to an admin for nejm my acc exp api")
    public void POST_request_to_provide_instnal_activation(Integer inidCount) throws Exception {
    	do {
		for (int i = 0; i < subsIdList.size(); i++) {
			subscrptnID = subsIdList.get(i);
			GET_call_to_fetch_linked_related_identities_nejm();
			if((expResp.getStatusCode() != (ResponseCode.OK))) {
				user_creates_inid_nejm();
			}
			String ucid = tUtil.getFromSession("ucid").toString();
			endPoint = accExpPOSTSteps.setEndpointInstitutionalActivate(ucid);
			expResp = accExpPOSTSteps.institutionalActivate(endPoint, tUtil.getToken(ucid), subscrptnID).extract()
					.response();

			if (expResp.getStatusCode() == ResponseCode.CREATED) {
				inidCount--;
				if(inidCount == 0) {
					break;
				}
				
			}
		}
    	}
    	while (inidCount!=0);
    	tUtil.putToSession("subId", subscrptnID);
	}
    

    @When("user sends a POST call to provide institutional activation for {int} subscribed institution to an admin for catalyst my acc exp api")
    public void POST_request_to_provide_instnal_activation_catMyExp(Integer inidCount) throws Exception{
    	do {
		for (int i = 0; i < subsIdList.size(); i++) {
			subscrptnID = subsIdList.get(i);
			GET_call_to_fetch_linked_related_identities_catalyst();
			String ucid = tUtil.getFromSession("ucid").toString();
			if((expResp.getStatusCode() != (ResponseCode.OK))) {
				user_creates_inid_catalyst();
			}
			endPoint = accExpPOSTSteps.setEndpointInstitutionalActivate(ucid);
			expResp = accExpPOSTSteps.institutionalActivate(endPoint, tUtil.getToken(ucid), subscrptnID).extract()
					.response();

			if (expResp.getStatusCode() == ResponseCode.CREATED) {
				inidCount--;
				if(inidCount == 0) {
					break;
				}
				
			}
		}
    	}
    	while (inidCount!=0);
    	tUtil.putToSession("subId", subscrptnID);
	}
    
    @When("user sends a POST call to provide institutional activation for same subscribed institution to another user in nejm")
	public void POST_request_to_provide_instnal_activation_forSame_inst_toDiffrnt_user_inNejm()
			throws IOException, SQLException {
		String ucid = tUtil.getFromSession("ucid").toString();
		endPoint = accExpPOSTSteps.setEndpointInstitutionalActivate(ucid);
		expResp = accExpPOSTSteps
				.institutionalActivate(endPoint, tUtil.getToken(ucid), tUtil.getFromSession("subId").toString())
				.extract().response();
	}
    
    @When("user sends a POST call to provide institutional activation for same subscribed institution to another user in catalyst")
	public void POST_request_to_provide_instnal_activation_forSame_inst_toDiffrnt_user_inCatJL()
			throws IOException, SQLException {
		String ucid = tUtil.getFromSession("ucid").toString();
		endPoint = accExpPOSTSteps.setEndpointInstitutionalActivate(ucid);
		expResp = accExpPOSTSteps
				.institutionalActivate(endPoint, tUtil.getToken(ucid), tUtil.getFromSession("subId").toString())
				.extract().response();
	}
    
    @Then("Admin should get activated on a subscribed institution for nejm my acc exp api with status code 201")
    public void verify_admin_should_get_activated() {
    	softAssert.assertThat(expResp.statusCode()).isEqualTo(ResponseCode.CREATED);
    	softAssert.assertThat(expResp.jsonPath().getString("activatedProducts")).isNotNull();
    	softAssert.assertThat(expResp.jsonPath().getString("errors")).isEqualTo("[]");
    
		softAssert.assertAll();
    }
    
    @When("user sends a GET call to fetch the activated person identity linked to {int} subscribed institution for nejm my acc exp api")
    public void GET_to_fetch_person_identity(Integer inidCount) throws InterruptedException {
    	int timeOutMin = 12;
    	endPoint = nejmLitrtmSystemGET.setEndpointIdentity(tUtil.getFromSession("ucid").toString());
		do {
			System.out.println("Waiting for request Nejm Literatum System to get finished");
			TimeUnit.SECONDS.sleep(5);
			expResp = nejmLitrtmSystemGET.get(endPoint).extract().response();
			tUtil.verifyStatus(expResp, ResponseCode.OK);
			List<Map<String, String>> respMaps = expResp.jsonPath().getList("tag");
			for (Map<String, String> map : respMaps) {
				expRespTagLabel = (map.containsValue("Yes"));
				expRespTagCode = (map.containsValue("y"));
				expRespTagSetCode = map.containsValue("institutionadminindicator");
				if ((expRespTagLabel && expRespTagCode) && expRespTagSetCode) {
					break;
				}
			}
			if ((expResp.jsonPath().getString("related-identity") != null) && expRespTagLabel
					&& expResp.jsonPath().getList("related-identity").size() == inidCount) {
				break;
			}
			timeOutMin--;

		} while (timeOutMin != 0);
    }
    
    @Then("I verify tags for institutional admin indicator and related-identity for activated admins for nejm my acc exp api with status code 200")
    public void verify_institnal_adminIndicator_and_relatedIdentity_tags() {
		softAssert.assertThat(expRespTagLabel).isTrue();
		softAssert.assertThat(expRespTagCode).isTrue();
		softAssert.assertThat(expRespTagSetCode).isTrue();
		softAssert.assertThat(expResp.jsonPath().get("related-identity.related-identity-type").toString()).isEqualTo(
				"[admined-org]");
		softAssert.assertThat(expResp.jsonPath().get("related-identity.id-value").toString()).isNotNull();

		softAssert.assertAll();
	}
    
    @When("user sends a GET call to fetch the activated person identity linked to {int} subscribed institution for catalyst my acc exp api")
	public void GET_fetch_personId_catalyst(Integer inidCount) throws Throwable {
		int timeOutMin = 12;
		endPoint = catJLSystmGET.setEndpointPersonIdentity(tUtil.getFromSession("ucid").toString());
		do {
			System.out.println("Waiting for request Catalyst Literatum System to get finished");
			TimeUnit.SECONDS.sleep(5);
			expResp = catJLSystmGET.get(endPoint).extract().response();
			tUtil.verifyStatus(expResp, ResponseCode.OK);
			if ((expResp.jsonPath().getString("related-identity") != null)
					&& expResp.jsonPath().getList("related-identity").size() == inidCount) {
				break;
			}
			timeOutMin--;

		} while (timeOutMin != 0);
	}
    
    @Then("I verify tags for institutional admin indicator and related-identity for activated admins for catalyst my acc exp api with status code 200")
    public void verify_cat_institnal_adminIndicator_and_relatedIdentity_tags() {
		softAssert.assertThat(expResp.jsonPath().get("related-identity.related-identity-type").toString()).isEqualTo(
				"[admined-org]");
		softAssert.assertThat(expResp.jsonPath().get("related-identity.id-value").toString()).isNotNull();

		softAssert.assertAll();
	}
    
    @Then("I verify respective related-identity should get linked to a multiple inid in nejm literatum my acc exp api with status code 200")
    public void verify_related_identity_linked_to_multiple_inid() {
    	softAssert.assertThat(expRespTagLabel).isTrue();
		softAssert.assertThat(expRespTagCode).isTrue();
		softAssert.assertThat(expRespTagSetCode).isTrue();
		softAssert.assertThat(expResp.jsonPath().getList("related-identity").size()).isEqualTo(2);
		softAssert.assertAll();
	}
    
    @When("user sends a GET call to fetch the subscribed institution details linked to multiple person identities for nejm my acc exp api")
    public void GET_call_to_fetch_linked_related_identities_nejm() throws InterruptedException {
		endPoint = nejmLitrtmSystemGET.setEndpointInstitutionIdentities(subscrptnID);
		expResp = nejmLitrtmSystemGET.get(endPoint).extract().response();
	}
    
    @When("user sends a GET call to fetch the subscribed institution details linked to multiple person identities for catalyst my acc exp api")
    public void GET_call_to_fetch_linked_related_identities_catalyst() throws InterruptedException {
		endPoint = catJLSystmGET.setEndpointInstitutionIdentities(subscrptnID);
		expResp = catJLSystmGET.get(endPoint).extract().response();
	}
    
    @Then("I verify respective related-identity should get linked to a single inid in nejm literatum my acc exp api with status code 200")
	public void verify_related_identity_linked_to_multiple_ucid_nejm() {
		Assert.assertTrue("related-identity size is not as expected",
				expResp.jsonPath().getList("related-identity").size() > 1);
	}
    
    @Then("I verify respective related-identity should get linked to a multiple inid in catalyst literatum my acc exp api with status code 200")
    public void verify_related_identity_linked_to_multiple_inid_inCatJL() {
    	softAssert.assertThat(expResp.getStatusCode()).isEqualTo(ResponseCode.OK);
		softAssert.assertThat(expResp.jsonPath().getList("related-identity").size()).isEqualTo(2);
		softAssert.assertAll();
	}

    @Then("Admin should get activated on a subscribed institution for catalyst my acc exp api with status code 201")
    public void verify_catlst_admin_should_get_activated() {
    	softAssert.assertThat(expResp.statusCode()).isEqualTo(ResponseCode.CREATED);
    	softAssert.assertThat(expResp.jsonPath().getString("activatedProducts")).isNotNull();
    	softAssert.assertThat(expResp.jsonPath().getString("errors")).isEqualTo("[]");
    	for(int i=0;i<expResp.jsonPath().getList("activatedProducts").size();i++ ) {
    		softAssert.assertThat(expResp.jsonPath().getString("activatedProducts.product"+"["+i+"]")).contains("Catalyst");
    	}
		softAssert.assertAll();
    }

    @Then("I verify respective related-identity should get linked to a single inid in catalyst literatum my acc exp api with status code 200")
    public void verify_related_identity_linked_to_multiple_ucid_catJL() {
		Assert.assertTrue("related-identity size is not as expected",
				expResp.jsonPath().getList("related-identity").size() > 1);
	}
    
    @When("^User send a POST request for AMB using send invoice with following$")
    public void userSendAMBPOSTRequ(DataTable dataTable) throws Exception {

        String file = AccountJsonBodyFile.Account_Catalyst_REG;
       
        Map<String, String> row = CucumberUtils.convert(dataTable);

        UpdateAccountFiles.update_File_AMB_Reg_Catalyst_excel(file, row);

        endPoint = accExpPOSTSteps.setEndpontCustRegCatalyst();
        expResp = accExpPOSTSteps.post(file, endPoint)
                .extract().response();

        ucid = expResp.jsonPath().get("ucid");
        
        tUtil.putToSession("ucid", ucid);
        tUtil.putToSession("email", jsonUtils.getFromJSON(file, "$.email"));
        tUtil.putToSession("firstName", jsonUtils.getFromJSON(file, "$.firstName"));
        tUtil.putToSession("lastName", jsonUtils.getFromJSON(file, "$.lastName"));
        tUtil.putToSession("token", expResp.jsonPath().get("authenticationToken"));
        
    }
    
    @When("I do a PATCH call to update the address for catalyst customers with following")
	public void PATCH_to_update_cstmrAdd(DataTable dataTable) throws Exception {
		String file = ExpJsonBodyFile.Catalyst_Update_Address;
		int timeOutMin = 12;
		Map<String, String> map = CucumberUtils.convert(dataTable);
		String ucid = tUtil.getFromSession("ucid").toString();
		AccountHelper.PATCH_updateAddressFile(file, map.get("address1"), map.get("address2"));
		endPoint = accExpPATCHSteps.setEndpointAddress(ucid);
		do {
			TimeUnit.SECONDS.sleep(5);
			expResp = accExpPATCHSteps.patchCustomer(file, tUtil.getToken(ucid), endPoint).extract().response();
			if (expResp.getStatusCode() == (ResponseCode.OK)) {
				break;
			}
			timeOutMin--;
		} while (timeOutMin != 0);
	}
    
    @Then("I should verify that address should be updated with status code 200")
    public void verify_Address_PATCH_call() {
    	Assert.assertEquals("Address Patch call did not return 200 OK", ResponseCode.OK, expResp.getStatusCode());
    }
    
    @When("I do a GET call to fetch customer details")
    public void GET_fetch_customer_details() throws URISyntaxException, InterruptedException {
    	String custNum;
		int timeOutMin = 12;
		endPoint = acsGETSteps.setEndpoint();
		do {
			TimeUnit.SECONDS.sleep(5);
			expResp = acsGETSteps.getCustomers(endPoint, "email", tUtil.getFromSession("email").toString()).extract()
					.response();
			if (expResp.jsonPath().getString("customerNumber[0]") != null) {
				break;
			}
			timeOutMin--;
		} while (timeOutMin != 0);
		custNum = expResp.jsonPath().getString("customerNumber[0]");
		tUtil.putToSession("customerNum", custNum);
	}
    
    @And("I do a GET call fetch customer address details")
    public void GET_fetch_customer_Address() throws URISyntaxException {
    	endPoint = acsGETSteps.setEndpoint(tUtil.getFromSession("customerNum").toString());
    	expResp = acsGETSteps.getCustomers(endPoint, "email", tUtil.getFromSession("email").toString()).extract().response();
    }
    
    @Then("I should verify the updated address")
	public void verify_updated_Address() {
		String actAdd1 = null, actAdd2 = null;
		String file = ExpJsonBodyFile.Catalyst_Update_Address;
		List<HashMap> addresses = expResp.jsonPath().getList("addresses");
		for (HashMap address : addresses) {
			HashMap postalAddress = (HashMap) address.get("postalAddress");
			actAdd1 = postalAddress.get("address1").toString();
			actAdd2 = postalAddress.get("address2").toString();
		}
		softAssert.assertThat(actAdd1).isEqualToIgnoringCase(
				jsonUtils.getFromJSON(file, "$.address..address1").replaceAll("\\[", "").replaceAll("\\]", ""));
		softAssert.assertThat(actAdd2).isEqualToIgnoringCase(
				jsonUtils.getFromJSON(file, "$.address..address2").replaceAll("\\[", "").replaceAll("\\]", ""));
		softAssert.assertAll();
	}
    
    @And("I do a GET call to fetch the order details")
    public void GET_fetch_orderNum() {
    	String orderNumber = null;
    	endPoint = acsGETSteps.setOrdersEndpoint(tUtil.getFromSession("customerNum").toString());
    	expResp = acsGETSteps.get(endPoint).extract().response();
    	List<HashMap> orderDetails = expResp.jsonPath().getList("orders");
		for (HashMap orders : orderDetails) {
			orderNumber = orders.get("orderNumber").toString();
		}
    	tUtil.putToSession("orderNum", orderNumber);
    }
    
    @When("I do a GET call to fetch the order receipt for my acc exp api")
	public void GET_fetch_orderRecpt_details() throws InterruptedException {
		String ucid = tUtil.getFromSession("ucid").toString();
		int timeOutMin = 12;
		endPoint = accExpGETSteps.setEndpointCustomersOrderRecpt(ucid, tUtil.getFromSession("orderNum").toString());
		do {
			TimeUnit.SECONDS.sleep(5);
			expResp = accExpGETSteps.getOrderReciept(endPoint, tUtil.getToken(ucid)).extract().response();
			if (expResp.getStatusCode() == (ResponseCode.OK)) {
				break;
			}
			timeOutMin--;
		} while (timeOutMin != 0);
	}
    
    @Then("I verify that customer number should be returned as a part of order receipt payload for my acc exp api")
    public void verify_recptDetails_should_contain_custNum() {
    	softAssert.assertThat(expResp.getStatusCode()).isEqualTo(ResponseCode.OK);
    	softAssert.assertThat(expResp.jsonPath().getString("customerNumber")).isEqualTo(tUtil.getFromSession("customerNum"));
    	softAssert.assertAll();
    }

    @Given("I get nejm customers with active subscription and ucid")
    public void iGetNejmCustomersWithActiveSubscriptionAndUcid() throws IOException, SQLException {
        String sqlQuery = dbUtils.buildQuery("NEJMcustomersWithUcId.sql");
        String dbURL = dbUtils.buildDbUrl("ACSDbUrl");
        Connection conn = ConnectionFactory.getConnectionACS(dbURL, dbUtils.acsDBUser(), dbUtils.acsDBPass());
        PreparedStatement ps = conn.prepareStatement(sqlQuery);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            customerMap.put(resultSet.getString("CTM_NBR"), resultSet.getString("BIL_CUR"));
        }
    }

    @Then("I do GET request to get ucId subscriptionId paymentId")
    public void iDoAGETRequestToGetSubscriptionIdAndUcId() throws URISyntaxException {
        boolean isFound = false;
        for (Map.Entry<String, String> entry : customerMap.entrySet()) {
            String customerId = entry.getKey();
            String end_pt = acsGETSteps.setEndpointCustCustNoUsersUCC(customerId);
            response = acsGETSteps.get(end_pt).extract().response();

            String ucId = acsGETSteps.extractAndValidateUcid(response);
            if (StringUtils.isBlank(ucId)) {
                continue; // not activated
            }

            end_pt = acsGETSteps.setSubscriptionsEndpoint(customerId);
            response = acsGETSteps.get(end_pt).extract().response();
            List<HashMap> subscriptions = response.jsonPath().getList("subscriptions");

            HashMap subscription = tUtil.getActiveSubscriptionWithPaymentId(subscriptions);
            if (subscription == null) {
                continue;
            }

            tUtil.putActiveSubscriptionWithPaymentIdToSession(subscription);
            tUtil.putToSession("ucId", ucId);
            tUtil.putToSession("customerId", customerId);
            isFound = true;
            break;
        }
        softAssert.assertThat(isFound).isTrue();
        softAssert.assertAll();
    }

    @When("I do GET request to get agreementId and ucId")
    public void iDoGETRequestToGetAgreementIdAndUcId() {
        boolean isFound = false;
        for (Map.Entry<String, String> entry : customerMap.entrySet()) {
            String customerId = entry.getKey();
            String end_pt = acsGETSteps.setEndpointCustCustNoUsersUCC(customerId);
            response = acsGETSteps.get(end_pt).extract().response();

            String ucId = acsGETSteps.extractAndValidateUcid(response);
            if (StringUtils.isBlank(ucId)) {
                continue; // not activated
            }

            end_pt = acsGETSteps.setCustomerAgreementsEndpoint(customerId);
            response = acsGETSteps.get(end_pt).extract().response();
            List<HashMap> agreements = response.jsonPath().getList("agreements");

            HashMap agreement = tUtil.getActiveAgreement(agreements);
            if (agreement == null) {
                continue;
            }

            tUtil.putToSession("subscriptionId", agreement.get("agreementId"));
            tUtil.putToSession("currencyCode", entry.getValue());
            tUtil.putToSession("billOrg", "MMS");
            tUtil.putToSession("ucId", ucId);
            tUtil.putToSession("customerId", customerId);
            isFound = true;
            break;
        }
        softAssert.assertThat(isFound).isTrue();
        softAssert.assertAll();
    }

    @Then("I do PATCH request to update payment details")
    public void iDoPATCHRequestToUpdatePaymentDetails() throws Exception {
        String ucId = (String) tUtil.getFromSession("ucId");
        String subscriptionId = (String) tUtil.getFromSession("subscriptionId");
        String paymentId = (String) tUtil.getFromSession("paymentId");
        String currencyCode = (String) tUtil.getFromSession("currencyCode");

        HashMap cardDetails = tUtil.generateCardDetails();
        jsonUtils.update_JSONPaymentDetails(paymentDetails, paymentId, currencyCode, cardDetails);
        tUtil.putToSession("cardDetails", cardDetails);

        endPoint = accExpPATCHSteps.setEndpointUpdatePaymentDetails(ucId, subscriptionId);
        expResp = accExpPATCHSteps.patchUserWithJsonFile(paymentDetails, endPoint, tUtil.getToken(ucId))
                .statusCode(ResponseCode.UPDATED).extract().response();
    }

    @Then("I do PATCH request to replace payment details")
    public void iDoPATCHRequestToReplacePaymentDetails() throws Exception {
        String ucId = (String) tUtil.getFromSession("ucId");
        String subscriptionId = (String) tUtil.getFromSession("subscriptionId");
        String currencyCode = (String) tUtil.getFromSession("currencyCode");
        String paymentId = jsonUtils.getFromJSON(paymentDetails, "paymentId");

        HashMap cardDetails = tUtil.generateCardDetails();
        tUtil.putToSession("cardDetails", cardDetails);
        tUtil.putToSession("cardType", jsonUtils.getFromJSON(paymentDetails, "creditCardInfo.cardType"));
        jsonUtils.update_JSONPaymentDetails(paymentDetails, null, currencyCode, cardDetails);

        endPoint = accExpPATCHSteps.setEndpointUpdatePaymentDetails(ucId, subscriptionId);
        expResp = accExpPATCHSteps.patchUserWithJsonFile(paymentDetails, endPoint, tUtil.getToken(ucId))
                .statusCode(ResponseCode.UPDATED).extract().response();
        jsonUtils.add_JSONPathJsonValue(paymentDetails, "paymentId", paymentId);
    }

    @Then("I validate credit card details updated")
    public void iValidateCreditCardDetailsUpdated() {
        String customerId = (String) tUtil.getFromSession("customerId");
        String paymentId = (String) tUtil.getFromSession("paymentId");
        String curr = (String) tUtil.getFromSession("currencyCode");
        String billOrg = (String) tUtil.getFromSession("billOrg");
        String end_pt = acsGETSteps.setCreditCardEndpoint(customerId, billOrg, curr, paymentId);
        response = acsGETSteps.get(end_pt).statusCode(ResponseCode.OK).extract().response();

        HashMap cardDetail = (HashMap) tUtil.getFromSession("cardDetails");
        softAssert.assertThat(cardDetail.get("month")).isEqualTo(response.jsonPath().get("expireMonth"));
        softAssert.assertThat(cardDetail.get("year")).isEqualTo(response.jsonPath().get("expireYear"));
        softAssert.assertAll();
    }

    @Then("I validate credit card details replaced for agreement")
    public void iValidateCreditCardDetailsReplacedForAgreement() {
        String customerId = (String) tUtil.getFromSession("customerId");
        String agreementId = (String) tUtil.getFromSession("subscriptionId");
        String end_pt = acsGETSteps.setAgreementEnpoint(agreementId);
        response = acsGETSteps.get(end_pt).statusCode(ResponseCode.OK).extract().response();

        String paymentId = response.jsonPath().get("electronicPaymentId");
        String curr = (String) tUtil.getFromSession("currencyCode");
        String billOrg = (String) tUtil.getFromSession("billOrg");
        end_pt = acsGETSteps.setCreditCardEndpoint(customerId, billOrg, curr, paymentId);
        response = acsGETSteps.get(end_pt).statusCode(ResponseCode.OK).extract().response();
        tUtil.putToSession("paymentId", paymentId);

        HashMap cardDetail = (HashMap) tUtil.getFromSession("cardDetails");
        String cardType = (String) tUtil.getFromSession("cardType");
        String cardMask =  response.jsonPath().get("cardMask");

        softAssert.assertThat(cardDetail.get("month")).isEqualTo(response.jsonPath().get("expireMonth"));
        softAssert.assertThat(cardDetail.get("year")).isEqualTo(response.jsonPath().get("expireYear"));
        softAssert.assertThat(cardType).isEqualTo(response.jsonPath().get("paymentMethod.code"));
        softAssert.assertThat(cardMask).contains(String.valueOf(cardDetail.get("lastFour")));
        softAssert.assertAll();
    }

}

