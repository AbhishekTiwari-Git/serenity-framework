package ucc.cr.steps.catalyst;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import ucc.cr.pages.catalyst.ui.EventsPage;
import ucc.cr.pages.catalyst.ui.PDFPremiumRegPage;
import ucc.cr.pages.catalyst.ui.utils.EventPageHelper;
import ucc.i.method.aic.AICGET;
import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

public class EventsSteps {

	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	private String user_email;
	static String end_ptAk, end_ptLt = null;
	public static Response sysRespAk, sysRespLt = null;
	

	@Steps
	CommonFunc commonFunc;

	@Steps
	EventsPage eventsPage;
	
	@Steps
	EventPageHelper eventsPageHelper;
	
	@Steps
	PDFPremiumRegPage pdfPremiumRegPage;
	
	@Steps
	AICGET AICGETSteps;

	@Managed
	WebDriver driver;

	@Given("^I launch the application with (.*)$")
	public void i_launch_the_application_with_url(String url) throws Throwable {
		commonFunc.Launch_URL(url);
		commonFunc.dismissVideoWindow();
	}

	@When("^I click Register button$")
	public void iClickRegisterButton() {
		eventsPage.click_register_button();
	}

	@Then("^I input email (.*) and click Register$")
	public void iInputEmailAndClickLogin(String email) throws Exception {
		user_email = tUtil.AppendTimestamp(email);
		eventsPage.input_email(user_email);
		eventsPage.click_register_button();
	}

	@Then("^I enter user firstName, lastName, (.*), (.*), (.*), (.*), (.*) for event$")
	public void iEnterUserDataForEvent(String suffix, String role, String place, String orgName, String country) {
		eventsPage.input_user_data(suffix, role, place, orgName, country);
	}

	@And("^I get text (.*) modal window$")
	public void iGetTextMessageModalWindow(String message) {
		String text = eventsPage.get_modal_text();
		assertEquals(message, text);
	}

	@When("^I click events link$")
	public void iClickEventsLink() {
		eventsPage.click_events();
	}
	
	@And("^user validate registration success message (.*)$")
	public void validateRegSuccessMessage(String expMessage) {
		Assert.assertEquals(expMessage, eventsPage.validateEventsMsg());
	}
	
	@When("^user validate event date and time$")
	public void validateEventDateTime() {
		Assert.assertEquals(true, eventsPage.validateEventDateTime());
	}
	
	@Then("^user validate date calandar is displayed with upcoming health care events link$")
	public void validateEventDateCalandar() {
		Assert.assertEquals(true, eventsPage.validateCalandarDate_UpcomingLink());
	}

	@And("^not cookied lead user validate soi (.*), (.*), (.*) as per JSON file (.*)$")
	public void existingLeadEmail_Soi(boolean soiStatus, boolean soiVisib, boolean connStatus, String file) throws Exception {
		eventsPage.input_email(jsonUtils.getFromJSON(file, "email"));
		eventsPage.click_register_button();
		Assert.assertEquals(true, eventsPage.disabledRegisterBtn());
		Assert.assertEquals(true, pdfPremiumRegPage.checkSOIVisibility(soiStatus, soiVisib));
		tUtil.putToSession("connectValue", connStatus);
	}
	
	@And("^not cookied lead user validate connect (.*), (.*), (.*) as per JSON file (.*)$")
	public void existingLeadEmail_Connect(boolean connStatus, boolean connVisib, boolean soiStatus, String file) throws Exception {
		eventsPage.input_email(jsonUtils.getFromJSON(file, "email"));
		eventsPage.click_register_button();
		Assert.assertEquals(true, eventsPage.disabledRegisterBtn());
		Assert.assertEquals(true, pdfPremiumRegPage.checkConnectVisibility(connStatus, connVisib));
		tUtil.putToSession("soiValue", soiStatus);
	}
	
	@And("^user submit email on event sign up form as per JSON (.*)$")
	public void submitExistingEmail(String file) throws Exception {
		eventsPage.input_email(jsonUtils.getFromJSON(file, "email"));
	}
	
	@And("^user fetches uuid for backend validation using json(.*)$")
	public void fetchUUID(String file) {
		user_email = jsonUtils.getFromJSON(file, "email");
		end_ptAk = AICGETSteps.setEndPointEmail(user_email);
		sysRespAk = AICGETSteps.getUserFromAkamai(end_ptAk).extract().response();
		tUtil.putToSession("uuid", sysRespAk.jsonPath().getString("uuid"));
	}
	
	@And("^user validate events in Akamai using data:$")
	public void eventAkamaiValidations(DataTable dt) {
		end_ptAk = AICGETSteps.setEndpointUserID((String) tUtil.getFromSession("uuid"));
		sysRespAk = AICGETSteps.getUserFromAkamai(end_ptAk)
				.extract().response();
		Assert.assertEquals(true, eventsPageHelper.akamaiValidations(sysRespAk, dt));
	}
	
	@And("^user set boolean jsonAttributeValue (.*) for jsonAttributekey (.*) in jsonfile (.*)$")
	public void setPlace_Anc(boolean attributeValue, String attributeKey, String file) throws Exception {
		jsonUtils.update_JSONValue(file, attributeKey, attributeValue);
	}
	
	@When("^user click on event link in my account$")
	public void clickMyAccEventLink() {
		eventsPage.clickMyAccEventLink();
	}
	
	@Then("^event which user has registered should be displayed$")
	public void validateEventMyAcc() {
		Assert.assertEquals(true, eventsPage.validateEventMyAcc());
	}
	
	@When("^user click on upcoming health care events$")
	public void selectUpcomingHealthCareEvents() {
		eventsPage.selectUpcomingHealthCareEvents();
	}
	
	@Then("^user should be redirected to catalyst site on multiple events page$")
	public void validateUpcomingEventsPage() {
		eventsPage.printUpcomingEventUrl(commonFunc.getCurrentBrowserUrl());
		CommonFunc.verifyLinkActive(commonFunc.getCurrentBrowserUrl());
	}
}
