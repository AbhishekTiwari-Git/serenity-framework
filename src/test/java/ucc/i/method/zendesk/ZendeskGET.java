package ucc.i.method.zendesk;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

import java.util.Map;

public class ZendeskGET {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("zendesk.basePath");
	public static String Zendesk_url = webserviceEndpoint+serviceEndpoint+"/api";    
    RestUtil zendesk = new RestUtil(Zendesk_url);
    private static final String PATH = "/tickets";

    public ValidatableResponse getTicket(String id) {
        String url = PATH + "/" + id;
        return	SerenityRest.rest()
                .given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .when()
                .log().all()
                .get(url)
                .then()
                .log().all();
    }

    public String getTicketID(Response resp) {
        JsonPath jsonPathEvaluator = resp.jsonPath();
        String ticketID = jsonPathEvaluator.getString("ticket.id");
        return ticketID;
    }
}
