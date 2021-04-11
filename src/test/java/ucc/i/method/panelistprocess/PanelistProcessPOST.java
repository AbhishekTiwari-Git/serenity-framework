package ucc.i.method.panelistprocess;

import java.io.File;
import java.net.URISyntaxException;

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

public class PanelistProcessPOST {
	
	TestUtils tUtil = new TestUtils();
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String apiservice =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("procpanelist.basePath");
	public static String panelist_url = apiservice+serviceEndpoint+"/api";     
	String file_path = env_var.getProperty("json.body.path");
	RestUtil Panelist = new RestUtil(panelist_url);
	
	
	 public ValidatableResponse postPanelists(String file_name, String endpoint) throws URISyntaxException{
	    	   	
		    	String path = file_path + "/" + file_name;
		        File file = new File(path);
		        
		        RestUtil.setBaseURI(panelist_url);
		        
		    	return	SerenityRest.rest()
		    		.given()
		    		.spec(ReuseableSpecifications.getGenericExpRequestSpec())
		    		.when()
		    		.log().all()
		    		.body(file)
		    		.log().all()
		    		.post(endpoint)
		    		.then()
		    		.log().all();
		    				
		    	}
	
	 
	 public String getAlternateID(Response resp) {
	    	JsonPath jsonPathEvaluator = resp.jsonPath();
			String Id = jsonPathEvaluator.getString("alternateId");
			return Id;
	    }
	 
	 
	 public String setPanelistEndpoint() {
	    	String endpoint = "/panelists";
			return endpoint;
	    }

}
