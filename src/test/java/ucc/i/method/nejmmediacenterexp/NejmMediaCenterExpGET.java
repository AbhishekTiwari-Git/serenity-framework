package ucc.i.method.nejmmediacenterexp;

import java.net.URISyntaxException;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class NejmMediaCenterExpGET {
	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
    static String apiUrl =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.exp.url");
    static String servicePath =  EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("accountEXpAPI");
	
	public static String mediacenterExpUrl = apiUrl+servicePath+"/api";
	
	TestUtils tUtil = new TestUtils();
	
public ValidatableResponse expmedia(String endpoint, String queryParam, String queryParam1) {
        
        RestUtil.setBaseURI(mediacenterExpUrl);
        
        return SerenityRest.rest()
               .given() 
               .spec(ReuseableSpecifications.getGenericExpRequestSpec())
               .when()
               .queryParam("codeType", queryParam)
               .queryParam("codeDesc", queryParam1)
               .log().all()
               .get(endpoint)
               .then()
               .log().all();
        
    }
        
    public ValidatableResponse getexpall(String endpoint, String queryParam, String queryParam1) {
        
        RestUtil.setBaseURI(mediacenterExpUrl);
        
        return SerenityRest.rest()
               .given() 
               .spec(ReuseableSpecifications.getGenericExpRequestSpec())
               .when()
               .queryParam(queryParam, queryParam1)
               .log().all()
               .get(endpoint)
               .then()
               .log().all();
        
    }
        public String setExpCodeValue(){
            
            String endpoint = "/media-center/code-value";
            return endpoint;
            
        }
        
        public String setExpDynamicEndpoint(String de)
        {
        	String endpoint = "/media-center/"+de;
        	return endpoint;
        	
        }
	
	

}
