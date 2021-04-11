package ucc.i.method.zendesk;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

import java.io.File;

public class ZendeskPOST {
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("zendesk.basePath");
	public static String Zendesk_url = webserviceEndpoint+serviceEndpoint+"/api";    
    String file_path = env_var.getProperty("json.body.path");
    RestUtil zendesk = new RestUtil(Zendesk_url);
    private static final String PATH = "/tickets";

    public ValidatableResponse createTicket(String file_name) {

        String path = file_path+"/"+file_name;
        File file = new File(path);

        return	SerenityRest.rest()
                .given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .when()
                .body(file)
                .log().all()
                .post(PATH)
                .then()
                .log().all();

    }
}
