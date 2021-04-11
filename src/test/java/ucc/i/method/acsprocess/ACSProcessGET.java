package ucc.i.method.acsprocess;

import io.restassured.response.ValidatableResponse;

import java.net.URISyntaxException;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class ACSProcessGET {
	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
    static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
    static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("acsProcess.basePath");
    public static String AcsProcess_url = webserviceEndpoint + serviceEndpoint + "/api";
    TestUtils tUtil = new TestUtils();
    String file_path = env_var.getProperty("json.body.path");
    RestUtil ICVProcAPI = new RestUtil(AcsProcess_url);
    
    
    public ValidatableResponse getCustomers(String endpoint, String productFamily) throws URISyntaxException{

    	RestUtil.setBaseURI(AcsProcess_url);

    	return	SerenityRest.rest()
    		.given()
    		.spec(ReuseableSpecifications.getGenericExpRequestSpec()).log().all()
    		.when()
    		.queryParam("productFamily", productFamily)
    		.get(endpoint)
    		.then()
    		.log().all();
    }
    
    public ValidatableResponse get(String endpoint){

    	RestUtil.setBaseURI(AcsProcess_url);

    	return	SerenityRest.rest()
    		.given()
    		.spec(ReuseableSpecifications.getGenericExpRequestSpec()).log().all()
    		.when()
    		.get(endpoint)
    		.then()
    		.log().all();
    }
    
    public String setEndpointToCustCustNumbInvoices(String custNumb) {
    	String endpoint = "/customers/" + custNumb + "/invoices";
		return endpoint;
    }
	
    public String setEndPointUsersUCC(String custNumb) {
    	String endpoint = "/customers/" + custNumb + "/users/ucc";
		return endpoint;
    }
    
    public String setEndPointCustomerMappings(String custNumb) {
    	String endpoint = "customers/"+ custNumb +"/mappings";
		return endpoint;
    }
}
