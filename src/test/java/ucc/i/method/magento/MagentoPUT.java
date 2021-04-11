package ucc.i.method.magento;

import java.io.File;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class MagentoPUT {
	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("megento.basePath");
	public static String magento_url = webserviceEndpoint+serviceEndpoint+"/api";    
	String file_path = env_var.getProperty("json.body.path");
	TestUtils tUtil = new TestUtils();
	RestUtil Magento = new RestUtil(magento_url);
	
	 public ValidatableResponse put(String filename, String endpoint) throws Exception {
		 String path = file_path + "/" + filename;
		 File file = new File(path);
		 
		 return SerenityRest.rest()
				.given()
		    	.spec(ReuseableSpecifications.getGenericExpRequestSpec())
		    	.when()
		    	.body(file)
		    	.log().all()
		    	.put(endpoint)
		    	.then()
		    	.log().all();
	 }
	 
	 public String setEndpointUpdateCustomer(String customerID) {
		 String endpoint = "/customers/" + customerID;
		 return endpoint;
	 }
}
