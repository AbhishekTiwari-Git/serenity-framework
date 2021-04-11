package ucc.i.steps.experience;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.i.method.paymanttoken.PaymentTokenPOST;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

import java.util.HashMap;
import java.util.Map;

public class PaymentTokenSteps {

    static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
    static String autoEmail =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("autoEmail");

    public static Response resp = null;
    static String end_pt = null;
    TestUtils tUtil = new TestUtils();
    JsonUtils jsonUtils = new JsonUtils();
    public static Map<String,String> kmap = new HashMap<String,String>();

    @Steps
    PaymentTokenPOST paymentTokenPOST;

    String filePostPaymentToken = "PaymentToken/PaymentToken_Post.json";

    @Then("^I do a POST call to paymentToken$")
    public void iDoPOSTCallPaymentToken() throws Exception {
        String name_value = tUtil.AppendTimestamp("Name");
        jsonUtils.update_JSONValue(filePostPaymentToken, "$.card.name", name_value);
        end_pt = paymentTokenPOST.setEndpointCardToken();
        resp = paymentTokenPOST.paymentTokenPost(filePostPaymentToken, end_pt)
                .extract().response();
    }

    @Then("^I see status '201' from paymentToken$")
    public void iSeeStatusFromPaymentToken() {
        tUtil.verifyStatus(resp, 201);
    }

    @When("^I do a POST call to paymentToken with incorrect data$")
    public void iDoAPOSTCallToPaymentTokenWithIncorrectData() throws Exception {
        String name_value = tUtil.AppendTimestamp("Name");
        String masked = jsonUtils.getFromJSON(filePostPaymentToken, "masked");

        jsonUtils.update_JSONValue(filePostPaymentToken, "$.card.name", name_value);
        jsonUtils.remove_JSONPath(filePostPaymentToken, "$.card.bin");

        end_pt = paymentTokenPOST.setEndpointCardToken();
        resp = paymentTokenPOST.paymentTokenPost(filePostPaymentToken, end_pt)
                .extract().response();

        jsonUtils.add_JSONPathJsonValue(filePostPaymentToken, "$.card.bin", "424242");
    }

    @Then("^I see status '500' from paymentToken$")
    public void iSeeStatus500FromPaymentToken() {
        tUtil.verifyStatus(resp, 500);
    }
}
