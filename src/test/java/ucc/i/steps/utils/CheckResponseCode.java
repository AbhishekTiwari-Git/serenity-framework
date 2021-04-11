package ucc.i.steps.utils;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;

public class CheckResponseCode {

    private static TestUtils tUtil = new TestUtils();

    @Then("^User should receive 500 Response code$")
    public static void verifyResponseCode500() {
        Response resp = (Response) tUtil.getFromSession("response");
        tUtil.verifyStatus(resp, ResponseCode.INTERNAL_ERROR);
    }

    @Then("^User should receive 201 response code$")
    public static void verifyResponseCode201() {
        Response resp = (Response) tUtil.getFromSession("response");
        tUtil.verifyStatus(resp, ResponseCode.CREATED);
    }

    @Then("^User should receive 200 response code$")
    public static void verifyResponseCode200() {
        Response resp = (Response) tUtil.getFromSession("response");
        tUtil.verifyStatus(resp, ResponseCode.OK);
    }
    
    @Then("^User should receive 204 response code$")
    public static void verifyResponseCode204() {
        Response resp = (Response) tUtil.getFromSession("response");
        tUtil.verifyStatus(resp, ResponseCode.UPDATED);
    }
    
    @Then("^User should receive 400 response code$")
    public static void verifyResponseCode400() {
        Response resp = (Response) tUtil.getFromSession("response");
        tUtil.verifyStatus(resp, ResponseCode.BAD_REQUEST);
    }
    
    @Then("^User should receive 404 response code$")
    public static void verifyResponseCode404() {
        Response resp = (Response) tUtil.getFromSession("response");
        tUtil.verifyStatus(resp, ResponseCode.NOT_FOUND);
    }
    
    @Then("^User should receive 401 response code$")
    public static void verifyResponseCode401() {
        Response resp = (Response) tUtil.getFromSession("response");
        tUtil.verifyStatus(resp, ResponseCode.UNAUTHORIZED);
    }
    
    @Then("^User should receive st_code (.*) response code$")
    public static void verifyResponse_stCode(int st_code) {
        Response resp = (Response) tUtil.getFromSession("response");
        tUtil.verifyStatus(resp, st_code);
    }
    
}
