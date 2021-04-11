package ucc.i.method.orderproc;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

import java.io.File;

public class OrderProcPOST {
    static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("api.base.url");
    static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("orderProc.basePath");
    public static String orderProcApiURL = webserviceEndpoint+serviceEndpoint+"/api";
    String filePath = envVar.getProperty("json.body.path");

    public ValidatableResponse postOrderProc(String endpoint, String filename) {

        String path = filePath + "/" + filename;
        File file = new File(path);

        RestUtil.setBaseURI(orderProcApiURL);

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

    public String setEndpointOrder() {
        String endpoint = "/order";
        return endpoint;
    }

    public String setEndpointUcidOrder(String ucid) {
        String endpoint = "/customers/" + ucid + "/orders";
        return endpoint;
    }

    public ValidatableResponse postOrderProcWithToken(String endpoint, String filename, String token) {

        String path = filePath + "/" + filename;
        File file = new File(path);

        RestUtil.setBaseURI(orderProcApiURL);

        return SerenityRest.rest().given()
            .spec(ReuseableSpecifications.getGenericExpRequestSpec()).log().all()
            .header("akamai-auth-token", token)
            .when()
            .body(file)
            .post(endpoint)
            .then()
            .log().all();
    }
}
