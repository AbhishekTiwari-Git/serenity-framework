package ucc.i.steps.process;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucc.i.method.process.lookupprocess.LookupProcGET;
import ucc.i.method.referenceSystem.ReferenceSystemHelper;
import ucc.utils.TestUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LookupProcSteps {

    private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    private static String autoEmail =  EnvironmentSpecificConfiguration.from(envVar)
        .getProperty("autoEmail");

    public static Response lookupResp = null;
    private static String endPt = null;

    public static Map<String, String> kmap = new HashMap<String, String>();
    private TestUtils tUtil = new TestUtils();
    private static final Logger LOGGER = LoggerFactory.getLogger(CustProcSteps.class);

    @Steps
    LookupProcGET lookupProcGET;

    @Steps
    ReferenceSystemHelper referenceSystemHelper;


    @When("^User sends a lookup request to get field (.*)$")
    public void userSendsALookupRequestToGetFieldField(String field) {
        lookupResp = lookupProcGET.getSingleField(field)
            .extract().response();
        tUtil.putToSession("response", lookupResp);
    }

    @Then("^Field (.*) should be present in lookup response$")
    public void fieldFieldShouldBePresentInLookupResponse(String field) throws IOException {
        referenceSystemHelper.verifyJson(lookupResp, field);
    }

}
