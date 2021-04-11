package ucc.i.method.cjexp;

import java.io.File;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class CJExpPOST {
  static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
  static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.exp.url");
  static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("cjExpAPI");
  public static String CJExp_url = webserviceEndpoint+serviceEndpoint+"/api";        
  TestUtils tUtil = new TestUtils();
  String file_path = env_var.getProperty("json.body.path");
  RestUtil CJEA = new RestUtil(CJExp_url);
  
  public ValidatableResponse validateToken(String endpoint, String filename) {
    
    String path = file_path + "/" + filename;
    File file = new File(path);
    
    RestUtil.setBaseURI(CJExp_url);

    return SerenityRest.rest()
        .given()
        .spec(ReuseableSpecifications.getGenericExpRequestSpec())
        .when()
        .body(file)
        .log().all()
        .post(endpoint)
        .then()
        .log().all();
  }
  
  public String setEndpointAccessToken() {
    String endpoint = "/access-token/validate";
    return endpoint;
  }
}
