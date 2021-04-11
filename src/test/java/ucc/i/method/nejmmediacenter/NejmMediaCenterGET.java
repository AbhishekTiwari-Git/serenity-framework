package ucc.i.method.nejmmediacenter;

import java.net.URISyntaxException;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class NejmMediaCenterGET {
    
    static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
    static String apiUrl =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
    static String servicePath =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("nejmmediacenter.basePath");
    
    public static String mediacenterSysUrl = apiUrl+servicePath+"/api";
   
    TestUtils tUtil = new TestUtils();
    
    
    public ValidatableResponse getmedia(String endpoint, String queryParam, String queryParam1) {
        
        RestUtil.setBaseURI(mediacenterSysUrl);
        
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
        
    public ValidatableResponse getall(String endpoint, String queryParam, String queryParam1) {
        
        RestUtil.setBaseURI(mediacenterSysUrl);
        
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
        public String setCodeValue(){
            
            String endpoint = "/code-value";
            return endpoint;
            
        }
        
        public String setDynamicEndpoint(String de)
        {
        	String endpoint = "/"+de;
        	return endpoint;
        	
        }

}
