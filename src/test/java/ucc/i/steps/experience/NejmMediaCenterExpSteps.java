package ucc.i.steps.experience;

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
import ucc.i.method.nejmmediacenterexp.NejmMediaCenterExpGET;
import ucc.i.method.nejmmediacenterexp.NejmMediaCenterExpPATCH;
import ucc.i.method.nejmmediacenterexp.NejmMediaCenterExpPOST;
import ucc.i.method.nejmmediacenterprocess.NejmMediaCenterProcessGET;
import ucc.i.method.nejmmediacenterprocess.NejmMediaCenterProcessPOST;
import ucc.utils.JsonUtils;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;

public class NejmMediaCenterExpSteps {
	
	
	    String endpoint = null;
	    Response expresp = null;
	    Response resp = null;
	    TestUtils tUtil = new TestUtils();
	    JsonUtils jUtil = new JsonUtils();
	    
	    private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
	    private static String autoEmail =  EnvironmentSpecificConfiguration.from(envVar)
	            .getProperty("autoEmail");
	    public static String emailUser = null;
	       
	    String filePath = "NEJMMediaCenter/POST_Subscription.json";
	    String applicationPath = "NEJMMediaCenter/POST_mediaapplication.json";
	    String firstclasssubPath = "NEJMMediaCenter/PATCH_firstclasssub.json";
	    String pocdataPath = "NEJMMediaCenter/PATCH_pocdata.json";
	    
	    
	    @Steps
	    NejmMediaCenterExpGET mediaexpGET;
	    
	        
	    @Steps
	    NejmMediaCenterExpPOST mediaexpPOST;   
	    
	    
	    @Steps
	    NejmMediaCenterExpPATCH mediaexpPATCH;
	    
	    
	    
	    @When("^User calls nejm media center exp api for (.*) and (.*)$")
	    public void getMediaexpcalls(String codeType, String codeDesp) {
	        
	        endpoint = mediaexpGET.setExpCodeValue();
	        expresp = mediaexpGET.expmedia(endpoint, codeType, codeDesp)
	               .extract().response();
	        
		}
	    
	    @When("^user passes endpoint (.*) and (.*) (.*) to Media Center Exp API$")
	    public void getexpcalls(String ep, String queryparam, String val ) {
	        
	        endpoint = mediaexpGET.setExpDynamicEndpoint(ep);
	        expresp = mediaexpGET.getexpall(endpoint, queryparam, val)
	               .extract().response();
	        
	    }
	    
	    @Then("^user should receive valid exp media status$")
	    public void validateexpResp() throws IOException {

	        tUtil.verifyStatus(expresp, ResponseCode.OK);
	       
	    }
   
	    
	    @When("^user calls post subsriptions on mediacenter exp api$")
	    public void getexpMediaSub() throws Exception {
	        
	        endpoint = mediaexpPOST.setNejmMediaSubscriptions();
	        resp = mediaexpPOST.postexpMediaSubscription(endpoint, filePath)
	               .extract().response();
	        
	    }
	    
	    @When("^user calls post applications on mediacenter exp api$")
	    public void getMediaappSub() throws Exception {
	        
	    	emailUser = tUtil.AppendTimestamp(autoEmail);
	        String firstName = tUtil.AppendTimestamp("testAutofn");
	        String lastName = tUtil.AppendTimestamp("testAutoln");
	        
	        jUtil.update_JSONValue(applicationPath, "$.firstName", firstName);
	        jUtil.update_JSONValue(applicationPath, "$.lastName", lastName);
	        jUtil.update_JSONValue(applicationPath, "$.email", emailUser);
	    	
	    	endpoint = mediaexpPOST.setNejmMediaapplications();
	        resp = mediaexpPOST.postexpMediaSubscription(endpoint, applicationPath)
	               .extract().response();
	        
	    }
	    
	    
	    @Then("^user should receive valid response on mediacenter exp api$")
	    public void validatemediaResp() throws IOException {

	        tUtil.verifyStatus(resp, ResponseCode.CREATED);
	       
	    }
	    
	    
	    @When("^user calls patch firstclasssubscription (.*) on mediacenter exp api$")
	    public void getexpMediafirstclassSub(String custnum) throws Exception {
	        
	        endpoint = mediaexpPATCH.setNejmMediafirstclasssubscriptions(custnum);
	        expresp = mediaexpPATCH.patchexpMediaFirstClassSubscription(endpoint, firstclasssubPath)
	               .extract().response();
	        
	    }
	    
	    
	    @When("^user calls patch pocdata (.*) on mediacenter exp api$")
	    public void getexpMediapocdataSub(String pocid) throws Exception {
	        
	        endpoint = mediaexpPATCH.setNejmMediapocdata(pocid);
	        expresp = mediaexpPATCH.patchexpMediaFirstClassSubscription(endpoint, pocdataPath)
	               .extract().response();
	        
	    }
	    
	    
}
