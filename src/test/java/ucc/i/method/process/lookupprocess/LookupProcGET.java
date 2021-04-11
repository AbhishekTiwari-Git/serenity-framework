package ucc.i.method.process.lookupprocess;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

import java.net.URISyntaxException;

public class LookupProcGET {

    static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
    static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
        .getProperty("api.base.url");
    static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
        .getProperty("lookupProcess.basePath");
    public static String LookupProcess_url = webserviceEndpoint + serviceEndpoint + "/api";

    public ValidatableResponse getSingleField(String field) {

        String endpt = LookupProcess_url + "/" + field;

        return	SerenityRest.rest()
            .given()
            .spec(ReuseableSpecifications.getGenericExpRequestSpec()).log().all()
            .when()
            .get(endpt)
            .then()
            .log().all();
    }

    public ValidatableResponse getFields(String fields_fieldName) throws URISyntaxException {

        String endpt;

        if (fields_fieldName.equals("fields")) {
            endpt = LookupProcess_url + "/fields";
        } else {
            endpt = LookupProcess_url + "/fields/" + fields_fieldName;
        }

        return	SerenityRest.rest()
            .given()
            .spec(ReuseableSpecifications.getGenericExpRequestSpec())
            .when()
            .get(endpt)
            .then()
            .log().all();

    }

}
