package ucc.i.method.tax;

import java.io.File;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.i.method.taxexp.TaxExpPost;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class TaxPOST {
	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
    static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
    static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("sysThomson.basePath");
    public static String ThomsonSysApi_url = webserviceEndpoint+serviceEndpoint+"/api";
	TestUtils tUtil = new TestUtils();
	TaxExpPost postTax = new TaxExpPost();
    String file_path = env_var.getProperty("json.body.path");
    String tax_ship = "SysTaxThomson.json";

    public ValidatableResponse post(String endpoint, String filename) {

        String path = file_path + "/" + filename;
        File file = new File(path);

        RestUtil.setBaseURI(ThomsonSysApi_url);

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

    public String setEndpoint() {
        String endpoint = "/tax/calculate";
        return endpoint;
    }

    public void updateSysTaxJson(String product, String fname, String lname,
                              String Sstreet, String currency, String Scountry, String Sstate, String Scity, String Szip,
                              String Bstreet, String Bcountry, String Bstate, String Bcity, String Bzip,
                              String vatId, Double amount) throws Exception {

       postTax.updateTaxJson(product, fname, lname, Sstreet, currency, Scountry, 
    		   Sstate, Scity, Szip, Bstreet, Bcountry, Bstate, Bcity, Bzip, vatId, amount);


    }
}
