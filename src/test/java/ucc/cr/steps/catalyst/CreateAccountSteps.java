package ucc.cr.steps.catalyst;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import ucc.cr.pages.catalyst.ui.CreateAccount;
import ucc.cr.pages.catalyst.ui.CreateLead;
import ucc.i.method.aic.AICGET;
import ucc.pages.ui.HomePage;
import ucc.utils.TestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CreateAccountSteps {

    TestUtils tUtil=new TestUtils();
    public static Response sysResp = null;
    static String end_pt = null;
    private String user_email;

    @Steps
    CreateAccount createAccount;

    @Steps
    HomePage homepage;

    @Steps
    CreateLead LEAD;

    @Steps
    AICGET aicGETSteps;

    @Managed
    WebDriver driver;

    @Then("^User close browser$")
    public void userCloseBrowser() {
        homepage.closeHomePage();
    }

    @Then("^User clicks to link Create Account$")
    public void userClicksLinkCreateAccount() {
        createAccount.click_create_account();
    }

    @Then("^User enters (.*), (.*), firstName, lastName, (.*), (.*), (.*), (.*), (.*)$")
    public void userEntersEmailPasswordFirstNameLastNameSuffixRolePlaceOrgNameCountry(
            String email, String password, String suffix, String role, String place, String orgName, String country
    ) {
        user_email = tUtil.AppendTimestamp(email);
        createAccount.create_account(user_email, password, suffix, role, place, orgName, country);
    }

    @And("^User sees message (.*)$")
    public void userSeesMessageMessage(String message) {
        String text = createAccount.getSuccessText();
        Assert.assertEquals(message, text);
    }

    @When("^User enters data (.*), (.*), (.*), (.*), (.*), (.*) to join$")
    public void userEntersTypeOfOrgHealthNetPatientAmountYearOfBithState(
            String typeOfOrg, String health, String netPatient, String amount, String yearOfBith, String state
    ) {
        createAccount.fillDataToJoin(typeOfOrg, health, netPatient, amount, yearOfBith, state);
    }

    @Then("^User clicks button Join$")
    public void userClicksButtonJoin() {
        createAccount.clickBtn();
    }

    @Then("^User AudienceType is REGISTERED USER$")
    public void iCheckUserAlternateIDAndAudienceType() {
        end_pt = aicGETSteps.setEndPointEmail(user_email);
        sysResp = aicGETSteps.getUserFromAkamai(end_pt).extract().response();

        List alternateID = sysResp.jsonPath().get("alternateID.IDType");
        String type = sysResp.jsonPath().getString("audienceType");
        Assert.assertEquals(alternateID.get(0), "MAGENTO");
        Assert.assertEquals(type, "REGISTERED USER");
    }

    @Then("^Check is user Insights Council member$")
    public void checkIsUserInsightsCouncilMember() {
        end_pt = aicGETSteps.setEndPointEmail(user_email);
        sysResp = aicGETSteps.getUserFromAkamai(end_pt).extract().response();

        Boolean alternateID = sysResp.jsonPath().get("Catalyst.insightsCouncilMember");
        Assert.assertTrue(alternateID);
    }

    @When("^User opens new Catalyst home page$")
    public void userOpensNewCatalystHomePage() {
        homepage.launchHomePagewithCookie();
    }

    @When("^User enters (.*), (.*), (.*), (.*) to create account$")
    public void userEntersPasswordSuffixRolePlaceToCreateAccount(String password, String suffix, String role, String place) {
        user_email = createAccount.get_account_email();
        createAccount.create_lead_account(password, suffix, role, place);
    }

    @Then("User properties values are correct")
    public void userPropertiesValuesAreCorrect() {
        end_pt = aicGETSteps.setEndPointEmail(user_email);
        sysResp = aicGETSteps.getUserFromAkamai(end_pt).extract().response();

        boolean qualifiedForCouncil = sysResp.jsonPath().getBoolean("Catalyst.qualifiedForCouncil");
        String audienceSegment = sysResp.jsonPath().getString("Catalyst.audienceSegment");
        String professionalCategoryCode = sysResp.jsonPath().getString("professionalCategoryCode");
        Assert.assertNotNull(audienceSegment);
        Assert.assertNotNull(professionalCategoryCode);
        Assert.assertTrue(qualifiedForCouncil);
    }

    @Then("Check value qualifiedForCouncil for Account")
    public void checkValueQualifiedForCouncilForAccount() {
        end_pt = aicGETSteps.setEndPointEmail(user_email);
        sysResp = aicGETSteps.getUserFromAkamai(end_pt).extract().response();

        assertEquals(sysResp.jsonPath().getString("Catalyst.qualifiedForCouncil"),
                "true");
    }

    @Then("Check value Professional Code for Account")
    public void checkValueProfessionalCodeForAccount() {
        end_pt = aicGETSteps.setEndPointEmail(user_email);
        sysResp = aicGETSteps.getUserFromAkamai(end_pt).extract().response();

        String audienceSegment = sysResp.jsonPath().getString("professionalCategoryCode");
        Assert.assertTrue(
                Arrays.asList("STU", "RES", "OTH", "PHY", "PA", "NP", "NUR")
                        .contains(audienceSegment)
        );
    }

    @Then("Check values for non qual for Account")
    public void checkValuesForNonQualForAccount() {
        end_pt = aicGETSteps.setEndPointEmail(user_email);
        sysResp = aicGETSteps.getUserFromAkamai(end_pt).extract().response();

        String audienceSegment = sysResp.jsonPath().getString("professionalCategoryCode");
        Assert.assertTrue(
                Arrays.asList("STU", "RES", "OTH", "PHY", "PA", "NP", "NUR")
                        .contains(audienceSegment)
        );
        assertEquals(sysResp.jsonPath().getString("Catalyst.qualifiedForCouncil"),
                "false");
        assertEquals(sysResp.jsonPath().getString("Catalyst.insightsCouncilMember"),
                null);
    }

    @When("^User get message (.*) for non qaul$")
    public void userGetMessageMessageForNonQaul(String message) {
        String text = createAccount.getThanYouText();
        Assert.assertEquals(message, text);
    }
}
