package ucc.i.method.paymanttoken;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

import java.io.File;

public class PaymentTokenPOST {

    static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
    static String apiservice = EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.exp.url");
    static String serviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("paymentToken.basePath");
    public static String paymenttoken_url = apiservice + serviceEndpoint + "/api";
    String file_path = env_var.getProperty("json.body.path");

    public ValidatableResponse paymentTokenPost(String file_name, String endpoint) {

        String path = file_path + "/" + file_name;
        File file = new File(path);

        RestUtil.setBaseURI(paymenttoken_url);

        return SerenityRest.rest()
                .given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .header("client-token", "Exp07102020Ashish4")
                .when()
                .log().all()
                .body(file)
                .log().all()
                .post(endpoint)
                .then()
                .log().all();

    }

    public String setEndpointCardToken() {
        String endpoint = "/card-token";
        return endpoint;
    }
}
