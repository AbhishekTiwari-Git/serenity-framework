package ucc.i.method.kinesys;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

import static org.hamcrest.Matchers.equalTo;


public class KinesysGET {
	TestUtils tUtil = new TestUtils();
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("kinesys.basePath");
	public static String kinesys_url = webserviceEndpoint+serviceEndpoint+"/api";     
	
	RestUtil kinesys = new RestUtil(kinesys_url);
	
	public ValidatableResponse getPanelist(String endpoint) throws URISyntaxException{
	
	//String url = "https://privateapps.nejmgroup-dev.org/dev-nejmg-sys-api-kinesis-v1/api/panelists/"+ID;
	
	
					
	return	SerenityRest.rest()
		.given()
		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
		.when()
		.log().all()
		.get(endpoint)
		.then()
		.log().all();
				
	}
	
	public ValidatableResponse getSurveys(String endpoint) throws URISyntaxException{
		
		//String url = "https://privateapps.nejmgroup-dev.org/dev-nejmg-sys-api-kinesis-v1/api/panelists/"+ID;
		
	
						
		return	SerenityRest.rest()
			.given()
			.spec(ReuseableSpecifications.getGenericExpRequestSpec())
			.when()
			.log().all()
			.get(endpoint)
			.then()
			.log().all();
					
		}
		
		
		public Boolean verify_Survey(Response res) {
			JsonPath jsonPathEvaluator = res.jsonPath();
			List<String> survey_Val = jsonPathEvaluator.getList("subject");
			Boolean flag = false;
			if(survey_Val.size()>0) {
				flag = true;
			}
			else {
				flag= false;
			}
			return flag;
		}
	
	 public Map<String,String> store_KinesysValues(Response res) {
			
			JsonPath jsonPathEvaluator = res.jsonPath();
			String alternateId = jsonPathEvaluator.getString("alternateId");
			String email = jsonPathEvaluator.getString("email");
			String id = jsonPathEvaluator.getString("id");
			
			Map kinesysValues = new HashMap<String,String>();
			kinesysValues.put("alternateId", alternateId);
			kinesysValues.put("email", email);
			kinesysValues.put("id", id);
			
			System.out.println("Stored Values "+ kinesysValues);
			
			return kinesysValues;
			
			}
	


    public boolean verify_KinsysNotNullValues(Map<String,String> mapV ) {
    	
    	    Boolean flag = false;
    	    
    	    for (Entry<String,String> ent : mapV.entrySet()) {
	          	if(ent.getKey().isEmpty()) {
	          	flag = false;
	          	}
	          	else {
	          	flag = true;	
	          	}
	          }
    	    
    	    return flag;
    }
    
    public void verify_msg(String endpoint, String msg, int code) {
    	
    //	String url = "https://privateapps.nejmgroup-dev.org/dev-nejmg-sys-api-kinesis-v1/api";
    	
    	JsonPath expectedJson = new JsonPath(new String(msg));
    
    					
    	SerenityRest.rest()
    		.given()
    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
    		.when()
    		.log().all()
    		.get(endpoint)
    		.then()
    		.body("message", equalTo(expectedJson.get("message")))
    		.assertThat()
    		.statusCode(code);
    	
    } 
    
    public String setEndpoint(String ID) {
    	String endpoint = "/panelists/"+ID;
		return endpoint;
    }
    
    public String setEndpoint(String ID, String survey) {
    	String endpoint = "/panelists/"+ID+survey;
		return endpoint;
    }
        
}
