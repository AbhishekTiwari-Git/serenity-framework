package ucc.cr.pages.catalyst.ui;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

public class EventsPage extends PageObject {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventsPage.class);

	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	CommonFunc comFun = new CommonFunc();

	@FindBy(xpath = "//button[contains(text(),'Register')]")
	WebElementFacade RegisterBtn;

	@FindBy(xpath = "//*[starts-with(@id,'uccEmail')]")
	WebElementFacade LoginEmail;

	@FindBy(xpath = "//button[contains(text(),'Register') and contains(@disabled, 'true')]")
	WebElementFacade disabledRegisterBtn;

	@FindBy(xpath = "//*[starts-with(@id,'uccFirstName')]")
	WebElementFacade firstName;

	@FindBy(xpath = "//*[starts-with(@id,'uccLastName')]")
	WebElementFacade lastName;

	@FindBy(xpath = "//*[starts-with(@id,'uccSuffix')]")
	WebElementFacade suffixDropDown;

	@FindBy(xpath = "//*[starts-with(@id,'uccRole')]")
	WebElementFacade roleDropDown;

	@FindBy(xpath = "//*[starts-with(@id,'uccPlaceOfWork')]")
	WebElementFacade placeOfWorkDropDown;

	@FindBy(xpath = "//*[starts-with(@id,'uccNameOfOrg')]")
	WebElementFacade nameOfOrg;

	@FindBy(xpath = "//*[starts-with(@id,'uccCountry')]")
	WebElementFacade countryDropDown;

	@FindBy(xpath = "//h1[contains(text(),'Thank You')] ")
	WebElementFacade modalText;

	@FindBy(xpath = "//a[contains(text(),'Event')]")
	WebElementFacade EventsLink;

	@FindBy(xpath = "//p[contains(text(),'You are now registered for the Web event.')]")
	WebElementFacade eventRegSuccessMsg;

	@FindBy(xpath = "//*[starts-with(@class,'issue-item_meta_date')]")
	WebElementFacade eventDateTime;

	@FindBy(xpath = "//*[starts-with(@class,'issue-item_date-box_day')]/span")
	WebElementFacade calandarDate;

	@FindBy(xpath = "//*[starts-with(text(),'Upcoming Health Care 2030 Events')]")
	WebElementFacade upcomingHealthCareEvents;

	@FindBy(xpath = "//*[starts-with(@class,'issue-item_title')]/a")
	WebElementFacade eventName;

	@FindBy(xpath = "//ul[@class='my-account_nav-list']//*[starts-with(@title,'My Events')]")
	WebElementFacade myEventsLink;

	@FindBy(xpath = "//*[starts-with(@class,'issue-item_title')]/a")
	WebElementFacade eventNameMyAccount;

	@Step("Input login email")
	public void input_email(String user_email) throws Exception {
		LoginEmail.waitUntilClickable().clear();
		LoginEmail.sendKeys(user_email);
		jsonUtils.update_JSONValue("flow_reg_cust_EMAIL.json", "email", user_email);
	}

	@Step("Click register button modal")
	public void click_register_button() {
		comFun.clickElement(RegisterBtn);
	}

	@Step("Input user data")
	public void input_user_data(String suffix, String role, String place, String orgName, String country) {
		String fName = tUtil.AppendTimestamp("firstName");
		String lName = tUtil.AppendTimestamp("lastName");
		createUserData(fName, lName, suffix, role, place, orgName, country);
	}

	private void createUserData(String fName, String lName, String suffix, String role, String place, String orgName,
			String country) {
		firstName.waitUntilClickable().clear();
		firstName.sendKeys(fName);
		lastName.waitUntilClickable().clear();
		lastName.sendKeys(lName);
		suffixDropDown.waitUntilClickable().selectByValue(suffix);
		roleDropDown.waitUntilClickable().selectByValue(role);
		placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
		nameOfOrg.waitUntilClickable().clear();
		nameOfOrg.sendKeys(orgName);
		countryDropDown.waitUntilClickable().selectByValue(country);
		comFun.clickElement(RegisterBtn);
	}

	@Step("Get modal text window")
	public String get_modal_text() {
		return modalText.waitUntilVisible().getText();
	}

	public void click_events() {
		comFun.clickElement(EventsLink);
	}

	public String validateEventsMsg() {
		return eventRegSuccessMsg.waitUntilVisible().getText();
	}

	public boolean validateEventDateTime() {
		LOGGER.info("==============EVENT NAME=======================");
		LOGGER.info(eventName.getText());
		LOGGER.info("============REGISTERED DATE & TIME ============");
		LOGGER.info(eventDateTime.getText());
		LOGGER.info("===============================================");
		tUtil.putToSession("Event Name", eventName.getText());
		return (eventDateTime.getText() != null && eventName.isDisplayed()) ? true : false;
	}

	public boolean validateCalandarDate_UpcomingLink() {
		return (calandarDate.isDisplayed() && upcomingHealthCareEvents.isDisplayed()) ? true : false;
	}

	public boolean disabledRegisterBtn() {
		return disabledRegisterBtn.waitUntilVisible().isCurrentlyVisible();
	}

	@Step("user click on my event link on my account page")
	public void clickMyAccEventLink() {
		myEventsLink.waitUntilClickable().click();
	}

	@Step("user validate event on my account page")
	public boolean validateEventMyAcc() {
		LOGGER.info("Event Name on My Account Page - " + eventNameMyAccount.getText());
		LOGGER.info("Expected Event Name - " + tUtil.getFromSession("Event Name"));
		return tUtil.getFromSession("Event Name").equals(eventNameMyAccount.getText()) ? true : false;

	}
	
	@Step("User select upcoming health care events")
	public void selectUpcomingHealthCareEvents() {
		upcomingHealthCareEvents.waitUntilClickable().click();
	}

	public void printUpcomingEventUrl(String url) {
		LOGGER.info("==================Upcoming Event Link Launched=======================");
		LOGGER.info("Upcoming Event URL ---->>>>>>***** MENTIONED BELOW *******");
		LOGGER.info(url);
		LOGGER.info("====================================================");
	}

}
