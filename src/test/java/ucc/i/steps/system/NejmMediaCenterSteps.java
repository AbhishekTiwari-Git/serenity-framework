package ucc.i.steps.system;

import java.io.IOException;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.i.method.nejmmediacenter.NejmMediaCenterGET;
import ucc.i.method.nejmmediacenter.NejmMediaCenterPOST;
import ucc.utils.JsonUtils;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;

public class NejmMediaCenterSteps {
    
    String endpoint = null;
    Response resp = null;
    TestUtils tUtil = new TestUtils();
    JsonUtils jUtil = new JsonUtils();
   
    private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    private static String autoEmail =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("autoEmail");
    public static String emailUser = null;
    String filePath = "NEJMMediaCenter/POST_Subscription.json";
    String applicationPath = "NEJMMediaCenter/POST_mediaapplication.json";

    
    @Steps
    NejmMediaCenterGET mediaGET;
    
    @Steps
    NejmMediaCenterPOST mediaPOST;
    
    
    @When("^User calls nejm media center sys api for (.*) and (.*)$")
    public void getMediacalls(String codeType, String codeDesp) {
        
        endpoint = mediaGET.setCodeValue();
        resp = mediaGET.getmedia(endpoint, codeType, codeDesp)
               .extract().response();
        
    }

   
    @When("^user passes endpoint (.*) and (.*) (.*) to Media Center System API$")
    public void getcalls(String ep, String queryparam, String val ) {
        
        endpoint = mediaGET.setDynamicEndpoint(ep);
        resp = mediaGET.getall(endpoint, queryparam, val)
               .extract().response();
        
    }
    

    @Then("^user should receive valid codes (.*) as defined by the system$")
    public void validateResp(String verf) throws IOException {

        tUtil.verifyStatus(resp, ResponseCode.OK);
       
    }
    
    @When("^user calls post subsriptions on mediacenter sys api$")
    public void getMediaSub() throws Exception {
        
        emailUser = tUtil.AppendTimestamp(autoEmail);
        String firstName = tUtil.AppendTimestamp("testAutofn");
        String lastName = tUtil.AppendTimestamp("testAutoln");
        
        jUtil.update_JSONValue(filePath, "$.firstName", firstName);
        jUtil.update_JSONValue(filePath, "$.lastName", lastName);
        jUtil.update_JSONValue(filePath, "$.email", emailUser);
        
        
        endpoint = mediaPOST.setNejmMediaSubscriptions();
        resp = mediaPOST.postMediaSubscription(endpoint, filePath)
               .extract().response();
        
    }
    
    @When("^user calls post applications on mediacenter sys api$")
    public void getMediaappSub() throws Exception {
        
        emailUser = tUtil.AppendTimestamp(autoEmail);
        String firstName = tUtil.AppendTimestamp("testAutofn");
        String lastName = tUtil.AppendTimestamp("testAutoln");
        
        jUtil.update_JSONValue(applicationPath, "$.firstName", firstName);
        jUtil.update_JSONValue(applicationPath, "$.lastName", lastName);
        jUtil.update_JSONValue(applicationPath, "$.email", emailUser);
        
        
        endpoint = mediaPOST.setNejmMediaapplications();
        resp = mediaPOST.postMediaSubscription(endpoint, applicationPath)
               .extract().response();
        
    }
    
    
    
    @Then("^user should receive valid response on mediacenter sys api$")
    public void validatemediaResp() throws IOException {

        tUtil.verifyStatus(resp, ResponseCode.CREATED);
       
    }
    
    
    
    
    
    
    /*
     *   jsonUtils.update_JSONValue(fileName, "$.address.email", emailValue);
        jsonUtils.update_JSONValue(fileName, "$.address.name.firstName", firstName);
        jsonUtils.update_JSONValue(fileName, "$.address.name.lastName", lastName);
     * 
     */
    
}
