package ucc.cr.steps.catalyst;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import ucc.cr.pages.catalyst.ui.JoinIC;
import ucc.cr.pages.catalyst.ui.MyAccountPage;
import ucc.cr.pages.catalyst.ui.RegInsighCouncilPage;
import ucc.i.method.aic.AICGET;
import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;
import ucc.utils.TestUtils;

import java.util.HashMap;
import java.util.SplittableRandom;

public class JoinICSteps {

	TestUtils tUtil = new TestUtils();

	public static Response sysResp = null;
	static String end_pt = null;
	static String file, user_email, pass, ICMember;

	@Steps
	JoinIC joinIC;

	@Steps
	HomePage homepage;

	@Managed
	WebDriver driver;

	@When("^user select place of work (.*) with other information (.*), (.*), (.*), (.*), (.*), (.*) and click continue$")
	public void selectplaceOfWork(String place, String fname, String lname, String suffix, String role, String country,
			String message) {
		joinIC.selectPlaceofWork(place, fname, lname, suffix, role, country, message);
	}

	@Then("^user select question values (.*), (.*), (.*), (.*), (.*), (.*), (.*), (.*) on third page of join IC on the basis of role_place (.*)$")
	public void selectThirdPageQuestion(String orgVal, String affVal, String netVal, String dollarVal, String yobVal,
			String stateVal, String passVal, String vendorVal, String role_place) {
		joinIC.selectQuestions_Common(orgVal, affVal, netVal, dollarVal, yobVal, stateVal, passVal, vendorVal,
				role_place);
	}

	@When("^user checks questions (.*) for no of beds, sites and physicians as per place (.*)$")
	public void checkQuestions_beds_sites_phy(String quesPresent, String place) {
		joinIC.printActualValues(place);
		joinIC.printExpectedValues(quesPresent);
	}

	@Then("^questions should be displayed (.*) on the basis of place of work$")
	public void validateQuestions_placeOWork(String quesPresent) {
		Assert.assertEquals(joinIC.compareValues(), quesPresent);
	}

	@And("^user join the IC (.*) if required$")
	public void joinIC(String iCJoin) {
		joinIC.joinInsightsCouncil(iCJoin);
	}

	@When("^user select role (.*) with other information (.*), (.*), (.*), (.*), (.*), (.*) and click continue$")
	public void selectRole(String role, String fname, String lname, String suffix, String place, String country,
			String message) {
		joinIC.selectRole(role, fname, lname, suffix, place, country, message);
	}

	@When("^user checks questions (.*) for vendors and dollar as per role (.*)$")
	public void checkQuestions_vendor_dollar(String quesPresent, String role) {
		joinIC.printActualValues_dollarVendor(role);
		joinIC.printExpectedValues_dollarVendor(quesPresent);
	}

	@Then("^questions should be displayed (.*) on the basis of role selected$")
	public void validateQuestions_role(String quesPresent) {
		Assert.assertEquals(joinIC.compareValues_vendorDollar(), quesPresent);
	}

	@When("^user check different question fields like organization (.*), (.*), (.*), (.*), (.*)$")
	public void checkFields_ICForm(boolean quesOrg, boolean quesAffliation, boolean quesNetPaitent, boolean quesVendors,
			boolean quesDollars) {
		joinIC.checkQuestionsFieldsICForm();
	}

	@Then("^fields should appear$")
	public void validateFieldsICForm() {
		Assert.assertEquals(joinIC.validateQuestionFieldsICForm(), true);
	}

	@When("^user checks all common fields (.*), (.*), (.*), (.*), (.*) should appear on IC form$")
	public void validateFields_ICForm(boolean yOB, boolean state, boolean title, boolean phone, boolean password) {
		joinIC.checkCommonFieldsICForm();
	}

	@Then("^validate all common fields$")
	public void validateCommonFields() {
		joinIC.printQuestionsFieldsMaps();
		Assert.assertEquals(joinIC.validateCommonFieldsICForm(), true);
	}

	@When("^user validates error messages (.*), (.*), (.*), (.*), (.*), (.*), (.*), (.*), (.*), (.*), (.*) for blank fields values$")
	public void checkValidationMessages(String orgError, String sitesError, String bedsError, String phyError,
			String affliationError, String netError, String dollarError, String yobError, String stateError,
			String passError, String vendorError) {
		joinIC.clickJoinbutton();
		Assert.assertEquals(joinIC.checkErrorMessages(orgError, sitesError, bedsError, phyError, affliationError,
				netError, dollarError, yobError, stateError, passError, vendorError), "true");
	}

	@And("^user closes the home page$")
	public void closeHomePage() {
		homepage.closeHomePage();
	}

	@And("^user check join IC (.*) thankyou and almost there popup if user joined IC$")
	public void joinIC_thankyouPopUp(String iCJoin) {
		Assert.assertEquals(true, joinIC.joinInsightsCouncil_thankyouPopUp(iCJoin));

	}

}
