package ucc.i.method.acs;


import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;


import java.util.Map.Entry;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import static org.junit.Assert.*;

public class ACSPOST {
    static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
    static String webserviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
        .getProperty("api.base.url");
    static String serviceSystemEndpoint = EnvironmentSpecificConfiguration.from(env_var)
        .getProperty("acs.basePath");
    static String serviceProcessEndpoint = EnvironmentSpecificConfiguration.from(env_var)
        .getProperty("acs.procPath");
    public static String ACS_system_url = webserviceEndpoint + serviceSystemEndpoint + "/api";
    public static String ACS_process_url = webserviceEndpoint + serviceProcessEndpoint + "/api";
    String file_path = env_var.getProperty("json.body.path");

    public ValidatableResponse post(String file_name, String endpoint) throws URISyntaxException {

        RestUtil.setBaseURI(ACS_system_url);

        String path = file_path + "/" + file_name;
        File file = new File(path);

        return SerenityRest.rest()
            .given()
            .spec(ReuseableSpecifications.getGenericExpRequestSpec()).log().all()
            .when()
            .body(file)
            .log().all()
            .post(endpoint)
            .then()
            .log().all();
    }

    public String getCustomerNumber(Response resp) {
        JsonPath jsonPathEvaluator = resp.jsonPath();
        String Id = jsonPathEvaluator.getString("customerNumber");
        return Id;
    }

    public String getOrderNumber(Response resp) {
        JsonPath jsonPathEvaluator = resp.jsonPath();
        String Id = jsonPathEvaluator.getString("orderNumber");
        return Id;
    }

    public void verify_Customer(Map<String, String> mapV, String endpoint) {


        Response res = SerenityRest.rest()
            .given()
            .spec(ReuseableSpecifications.getGenericExpRequestSpec()).log().all()
            .when()
            .get(endpoint)
            .then()
            .extract().response();

        JsonPath jsonPathEvaluator = res.jsonPath();
        String customerNumber = jsonPathEvaluator.getString("customerNumber");
        String email = jsonPathEvaluator.getString("addresses.email");


        List<String> list = new ArrayList<String>();

        //	Entry<String,String> ent;
        for (Entry<String, String> ent : mapV.entrySet()) {
            if (ent.getKey().isEmpty()) {
                System.out.println("Empty map");
            } else {

                System.out.println(ent.getKey() + ": " + ent.getValue());
                list.add(ent.getValue());

            }
        }

        System.out.println(list.get(0) + ": " + customerNumber);
        System.out.println(list.get(1) + ": " + email);

        String cNo = customerNumber.replaceAll("\\[", "").replaceAll("\\]", "");
        String eml = email.replaceAll("\\[", "").replaceAll("\\]", "");

        System.out.println(list.get(0) + ": " + cNo);
        System.out.println(list.get(1) + ": " + eml);

        assertEquals(list.get(0), cNo);
        assertEquals(list.get(1), eml);


        System.out.println("Successfully matched created panelist with alternateID and email...!!");
    }

    public void verifyCustomerInfo(Map<String, String> mapV, Response res) {

        JsonPath jsonPathEvaluator = res.jsonPath();
        String cNo = jsonPathEvaluator.get("[0].customerNumber");
        String e = jsonPathEvaluator.get("[0].addresses[0].email");

        List<String> list = new ArrayList<String>();

        for (Entry<String, String> ent : mapV.entrySet()) {
            if (ent.getKey().isEmpty()) {
                System.out.println("Empty map");
            } else {

                System.out.println(ent.getKey() + ": " + ent.getValue());
                list.add(ent.getValue());

            }
        }

        System.out.println(list.get(0) + ":" + cNo);
        System.out.println(list.get(1) + ":" + e);

        assertEquals("The customerNumber should be" + mapV.get("customerNumber") + "but found" + cNo, mapV.get("customerNumber"), cNo);
        assertEquals("The email should be" + mapV.get("email") + "but found" + e, mapV.get("email"), e);

        System.out.println("Successfully matched created customerno and email...!!");
    }


    public void verifyCustomerNumber(String custNum1, String custNum2) {

        assertFalse("The CustomerNumber Returned is not unique", custNum1.equals(custNum2));
    }


    public String setEndpoint() {
        String endpoint = "/customers";
        return endpoint;
    }

    public String setEndpoint(String ID) {
        String endpoint = "/customers/" + ID;
        return endpoint;
    }

    // =============================================================================================================

    public String setEndpointOrders() {
        String endpoint = "/orders";
        return endpoint;
    }

    public String setEndpointOrdersPayment(String orderNum) {
        String endpoint = "/orders/" + orderNum + "/payments";
        return endpoint;
    }

    public String setEndpointControlGroup() {
        String endpoint = "control-group";
        return endpoint;
    }

    public String setEndpointCommitOrder(String orderNumber) {
        String endpoint = "/orders/" + orderNumber + "/commit";
        return endpoint;
    }

    public String setEndpointCustomerMatches() {
        String endpoint = "/customers/customer-matches";
        return endpoint;
    }

    public ValidatableResponse processOrder(String file_name) throws URISyntaxException {

        String path = file_path + "/" + file_name;
        File file = new File(path);

        String endpoint = "/orders";
        RestUtil.setBaseURI(ACS_process_url);

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
}
