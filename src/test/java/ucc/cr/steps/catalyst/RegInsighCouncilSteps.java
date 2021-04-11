package ucc.cr.steps.catalyst;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import ucc.cr.pages.catalyst.ui.RegInsighCouncilPage;
import ucc.i.method.aic.AICGET;
import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RegInsighCouncilSteps {

    TestUtils tUtil = new TestUtils();
    JsonUtils jsonUtils = new JsonUtils();
    public static Response sysResp = null;
    static String end_pt = null;
    private String user_email;

    @Steps
    RegInsighCouncilPage regInsighCouncilPage;

    @Steps
    HomePage homepage;

    @Steps
    CommonFunc commonFunc;

    @Steps
    AICGET aicGETSteps;

    @Managed
    WebDriver driver;

    @Given("^User opens Catalyst home page$")
    public void user_is_on_Catalyst_home_page() throws Throwable {
        homepage.launchHomePage();
        commonFunc.waitForLoadPage();
    }

    @When("^User clicks to link Insights Council$")
    public void userClicksToLinkInsightsCouncil() {
        regInsighCouncilPage.clickInsightsCouncil();
    }

    @Then("^User clicks to button Apply now$")
    public void userClicksToButtonApplyNow() {
        regInsighCouncilPage.clickApplyBtn();
        commonFunc.waitForLoadPage();
    }

    @Then("^User clicks to button Continue$")
    public void userClicksToButtonContinue() {
        user_email = regInsighCouncilPage.getUserEmail();
        regInsighCouncilPage.clickContinue();
    }

    @When("^User enter email (.*) and click button$")
    public void userEnterEmailAndClickButton(String email) throws Exception {
        user_email = tUtil.AppendTimestamp(email);
        regInsighCouncilPage.enter_email_click_button(user_email);
        jsonUtils.update_JSONValue("flow_reg_cust_EMAIL.json", "email", user_email);
    }

    @Then("^User enters firstName, lastName, (.*), (.*), (.*), (.*), (.*)$")
    public void userEntersFirstNameLastNameCountryRolePlaceOrgNameCountry(
            String suffix, String role, String place, String orgName, String country
    ) {
        regInsighCouncilPage.enter_user_data(suffix, role, place, orgName, country);
    }

    @Then("^User sees text (.*) on page$")
    public void userSeesTextMessageOnPage(String message) {
        Assert.assertEquals(regInsighCouncilPage.account_created(), message);
        homepage.closeHomePage();
    }

    @And("^I check alternateID and audienceType for user$")
    public void iCheckAlternateIDAndAudienceTypeForUser() {
        System.out.println(user_email);
        end_pt = aicGETSteps.setEndPointEmail(user_email);
        sysResp = aicGETSteps.getUserFromAkamai(end_pt).extract().response();

        List listID = sysResp.jsonPath().getList("alternateID");
        String type = sysResp.jsonPath().getString("audienceType");
        Assert.assertEquals(listID.size(), 0);
        Assert.assertEquals(type, "LEAD");
    }

    @Then("^User enters suffix (.*), role (.*), place (.*) for Insights Council$")
    public void userEntersSuffixSuffixRoleRolePlacePlaceForInsightsCouncil(
            String suffix, String role, String place
    ) {
        regInsighCouncilPage.enter_lead_user_data(suffix, role, place);
    }


    @Then("User properties values for IC are correct")
    public void userPropertiesValuesForICAreCorrect() {
        end_pt = aicGETSteps.setEndPointEmail(user_email);
        sysResp = aicGETSteps.getUserFromAkamai(end_pt).extract().response();
        boolean qualifiedForCouncil = sysResp.jsonPath().getBoolean("Catalyst.qualifiedForCouncil");
        String audienceSegment = sysResp.jsonPath().getString("Catalyst.audienceSegment");
        String professionalCategoryCode = sysResp.jsonPath().getString("professionalCategoryCode");
        Assert.assertNotNull(audienceSegment);
        Assert.assertNotNull(professionalCategoryCode);
        Assert.assertTrue(qualifiedForCouncil);
    }

    @Then("Check value audienceSegment for IC")
    public void checkValueAudienceSegmentForIC() {
        end_pt = aicGETSteps.setEndPointEmail(user_email);
        sysResp = aicGETSteps.getUserFromAkamai(end_pt).extract().response();

        String audienceSegment = sysResp.jsonPath().getString("Catalyst.audienceSegment");
        Assert.assertTrue(
                Arrays.asList("CLINICAL", "HC Executive", "CLINICAL LEADER")
                        .contains(audienceSegment)
        );
    }

    @Then("Check value qualifiedForCouncil for IC")
    public void checkValueQualifiedForCouncilForIC() {
        end_pt = aicGETSteps.setEndPointEmail(user_email);
        sysResp = aicGETSteps.getUserFromAkamai(end_pt).extract().response();

        Assert.assertTrue(sysResp.jsonPath().getBoolean("Catalyst.qualifiedForCouncil"));
    }

    @Then("Check value Professional Code for IC")
    public void checkValueProfessionalCodeForIC() {
        end_pt = aicGETSteps.setEndPointEmail(user_email);
        sysResp = aicGETSteps.getUserFromAkamai(end_pt).extract().response();

        String audienceSegment = sysResp.jsonPath().getString("professionalCategoryCode");
        Assert.assertTrue(
                Arrays.asList("STU", "RES", "OTH", "PHY", "PA", "NP", "NUR")
                        .contains(audienceSegment)
        );
    }

    @Then("Check values for non qual IC")
    public void checkValueQualifiedForCouncilForNonQualIC() {
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
    
    @When("^user hovers insights council link$")
	public void hoverIC() {
    	regInsighCouncilPage.hoverICLink();
	}
    
    @Then("^user select link apply to join$")
    public void selectApplyToJoin() {
    	regInsighCouncilPage.selectApplyToJoin();
    	commonFunc.switchToSecondWindow();
    	commonFunc.waitForLoadPage();
    }

    
}
