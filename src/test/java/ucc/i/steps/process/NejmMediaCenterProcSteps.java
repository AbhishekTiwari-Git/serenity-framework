package ucc.i.steps.process;

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
import ucc.i.method.nejmmediacenterprocess.NejmMediaCenterProcessGET;
import ucc.i.method.nejmmediacenterprocess.NejmMediaCenterProcessPOST;
import ucc.utils.JsonUtils;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;

public class NejmMediaCenterProcSteps {
	
	
	    String endpoint = null;
	    Response procresp = null;
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
	    NejmMediaCenterProcessGET mediaprocGET;
	    
	    @Steps
	    NejmMediaCenterGET mediaGET;
	    
	    @Steps
	    NejmMediaCenterProcessPOST mediaProcPOST;
	    
	    
	    @When("^User calls nejm media center proc api for (.*) and (.*)$")
	    public void getMediaproccalls(String codeType, String codeDesp) {
	        
	        endpoint = mediaGET.setCodeValue();
	        procresp = mediaprocGET.getprocmedia(endpoint, codeType, codeDesp)
	               .extract().response();
	        
		}
	    
	    @When("^user passes endpoint (.*) and (.*) (.*) to Media Center Process API$")
	    public void getcalls(String ep, String queryparam, String val ) {
	        
	        endpoint = mediaprocGET.setDynamicprocEndpoint(ep);
	        procresp = mediaprocGET.getprocall(endpoint, queryparam, val)
	               .extract().response();
	        
	    }
	    
	    @Then("^user should receive valid process media status$")
	    public void validateprocResp() throws IOException {

	        tUtil.verifyStatus(procresp, ResponseCode.OK);
	       
	    }
   
	    @When("^user calls post subsriptions on mediacenter proc api$")
	    public void getMediaSub() throws Exception {
	        
	        endpoint = mediaProcPOST.setNejmMediaSubscriptions();
	        resp = mediaProcPOST.postMediaSubscription(endpoint, filePath)
	               .extract().response();
	        
	    }
	    
	    @When("^user calls post applications on mediacenter proc api$")
	    public void getMediaappSub() throws Exception {
	        
	    	emailUser = tUtil.AppendTimestamp(autoEmail);
	        String firstName = tUtil.AppendTimestamp("testAutofn");
	        String lastName = tUtil.AppendTimestamp("testAutoln");
	        
	        jUtil.update_JSONValue(applicationPath, "$.firstName", firstName);
	        jUtil.update_JSONValue(applicationPath, "$.lastName", lastName);
	        jUtil.update_JSONValue(applicationPath, "$.email", emailUser);
	    	
	    	endpoint = mediaProcPOST.setNejmMediaapplications();
	        resp = mediaProcPOST.postMediaSubscription(endpoint, applicationPath)
	               .extract().response();
	        
	    }
	    
	    
	    @Then("^user should receive valid response on mediacenter proc api$")
	    public void validatemediaResp() throws IOException {

	        tUtil.verifyStatus(resp, ResponseCode.CREATED);
	       
	    }
	    
}
