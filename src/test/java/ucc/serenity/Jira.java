package ucc.serenity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

public class Jira extends net.thucydides.core.pages.PageObject {
	
	EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
	public static final String FILE_PATH = "src"+File.separator+"test"+File.separator+"resources"+File.separator+"testdata"+File.separator;
	public static String PATH;
	String fileName, test_cycle_name;
	int test_cycle_id;
	public static String jira_url = "https://jira.mms.org/";  
	RestUtil jira = new RestUtil(jira_url);
	
	
	@Step("Authenticating Jira and retriving Cycle details")
	public ValidatableResponse initJira(int pId, int vId){
	  
	//	RestUtil.setBaseURI(jira_url);
		return SerenityRest.rest()
		.given()
		.spec(ReuseableSpecifications.getGenericJiraRequestSpec()).log().all()
		.queryParams("projectId",pId)
        .queryParam("versionId", vId)
       	.get("rest/zapi/latest/cycle").then().log().all();
		
	}

    
    @Step("Setting up the execution Cycle")
    public void setupCycle() {
    	String fName = System.getProperty("cucumber.options");
    	String [] nPath;
    
    	
    	if(fName.contains("=")) {
			nPath = fName.split("=");
 	        fileName = nPath[1].trim();
			}
			else if(fName.contains(":")) {
			nPath = fName.split(":");
	 	    fileName = nPath[1].trim();	
			}
			else {
			fileName="test";	
			}
		
    	switch (fileName) // nested switch 
        { 
         
        case "ICV": 
            System.out.println("Test file :" + fileName); 
            PATH = FILE_PATH + fileName+".xlsx";
            System.out.println("Test file Complete Path :" + PATH); 
            test_cycle_id=7882;
            test_cycle_name = "ICV";
            break; 

        case "Kinesys": 
        	System.out.println("Test file :" + fileName); 
            PATH = FILE_PATH + fileName+".xlsx";
            System.out.println("Test file Complete Path :" + PATH); 
            test_cycle_id=7860;
            test_cycle_name = "Kinesys";
            break; 
            
        case "ACS": 
            System.out.println("Test file :" + fileName); 
            PATH = FILE_PATH + fileName+".xlsx";
            System.out.println("Test file Complete Path :" + PATH); 
            test_cycle_id=7840;
            test_cycle_name = "ACS";
            break; 
            
        case "Literatum": 
            System.out.println("Test file :" + fileName); 
            PATH = FILE_PATH + fileName+".xlsx";
            System.out.println("Test file Complete Path :" + PATH); 
            test_cycle_id=7954;
            test_cycle_name = "Literatum";
            break;    
           
        default: 
            
        	System.out.println("Please specify valid tag to execute the cycle"); 
      
        }
    	
    }


    @Step("retriving tests in Cycle")
	public ValidatableResponse readCycle(){
	  
		//RestUtil.setBaseURI(jiraUrl+"zapi/latest/cycle");
		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericJiraRequestSpec())
				.queryParams("cycleId",test_cycle_id)
		      	.get("rest/zapi/latest/execution").then();
		
	}

    @Step("retriving tests in Cycle")
    public ValidatableResponse updateZypherExecution(int Id, String status){
		
	//	RestUtil.setBaseURI(jiraUrl+"zapi/latest/execution/"+Id+"/execute");
		JSONObject requestParams = new JSONObject();
		requestParams.put("status", status); 
	
		
	return	SerenityRest.rest().given()
		.spec(ReuseableSpecifications.getGenericJiraRequestSpec())
		.when()
		.body(requestParams.toJSONString())
		.put("rest/zapi/latest/execution/"+Id+"/execute")
		.then();
				
	}

    @Step("retriving steps in test")
    public ValidatableResponse getZapiStepId(int executionId){
    	
       	return	RestAssured.given()
    			.spec(ReuseableSpecifications.getGenericJiraRequestSpec()).log().all()
    			.queryParam("executionId", executionId)
    			.get("rest/zapi/latest/stepResult")
    			.then();
    			
    }

    
    @Step("Update steps execution in test")
    public ValidatableResponse updateZapiExecution(int Id, String status){
    	
    //	RestUtil.setBaseURI(jiraUrl+"zapi/latest/stepResult/"+Id);
    	JSONObject requestParams = new JSONObject();
    	requestParams.put("status", status); 

    	
    	return	RestAssured.given()
    			.spec(ReuseableSpecifications.getGenericJiraRequestSpec()).log().all()
    			.when()
    			.body(requestParams.toJSONString())
    			.put("rest/zapi/latest/stepResult/"+Id)
    			.then();
    			
    }
    
    @Step("retriving custom Field in Issue")
	public ValidatableResponse getCustomIssueField(String Issue){
	  
		//RestUtil.setBaseURI(jiraUrl+"zapi/latest/cycle");
		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericJiraRequestSpec())
				.get("rest/api/latest/issue/"+Issue).then()
				.log().all();
		        
	}
    
    
    @Step("retriving zapi steps by Issue Id")
    public ValidatableResponse getZapiStepByIssue(List<Integer> IssueId, int id){
    	
    	List <Integer> valList = new ArrayList();
    	valList = IssueId;
    	
    	return	 //RestUtil.setBaseURI(jiraUrl+"zapi/latest/teststep/"+issue_id.get(id).toString());
       			SerenityRest.rest().given()
		.spec(ReuseableSpecifications.getGenericJiraRequestSpec())
		.get("rest/zapi/latest/teststep/"+valList.get(id).toString())
		.then();
    			
    }
    
    

    public List <String> getItemList (Response resp, String path) {
	    
    	List <String> valList = new ArrayList();
    	JsonPath jsonPathEvaluator = resp.jsonPath();
    	valList = jsonPathEvaluator.getList(path);
    	
    	return valList;
		
    }
    
    public List <Integer> getIntItemList (Response resp, String path) {
	    
    	List <Integer> valList = new ArrayList();
    	JsonPath jsonPathEvaluator = resp.jsonPath();
    	valList = jsonPathEvaluator.getList(path);
    	
    	return valList;
		
    }
    
  
}
