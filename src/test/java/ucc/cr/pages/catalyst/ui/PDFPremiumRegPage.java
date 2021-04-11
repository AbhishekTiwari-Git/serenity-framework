package ucc.cr.pages.catalyst.ui;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;
import ucc.utils.CucumberUtils.CucumberUtils;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;

public class PDFPremiumRegPage extends PageObject {

	private static final Logger LOGGER = LoggerFactory.getLogger(PDFPremiumRegPage.class);
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String baseUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("pdf.base.url");
	public static Response sysResp = null;
	CommonFunc comFun = new CommonFunc();

	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	Map<String, String> kMap = new HashMap<String, String>();

	@FindBy(xpath = "//*[starts-with(@id,'uccEmail')]")
	WebElementFacade inputEmail;

	@FindBy(xpath = "//button[contains(text(),'CONTINUE')]")
	WebElementFacade Button;

	@FindBy(xpath = "//button[contains(text(),'DOWNLOAD NOW')]")
	WebElementFacade downloadNowBtn;

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

	@FindBy(xpath = "//h1[contains(text(),'Thank you')]")
	WebElementFacade textOnPage;

	@FindBy(xpath = "//div[@class='ucc-modal-container']")
	WebElementFacade modalContainer;

	@FindBy(xpath = "//tr[6]//td[2]//a[1]")
	WebElementFacade EmbeddedLink;

	@FindBy(xpath = "//tr[6]//td[2]//a[2]")
	WebElementFacade ModalLink;

	@FindBy(xpath = "//a[@id='event-signup']")
	WebElementFacade SignUp;

	@FindBy(xpath = "//a[contains(text(),'Not you? Click here to reset')]")
	WebElementFacade NotYouLink;

	private By soiActual = new By.ByCssSelector("input[name=uccSoi]:checked[type=checkbox]");

	private By connectActual = new By.ByCssSelector("input[name=uccCatalystConnect]:checked[type=checkbox]");

	@FindBy(xpath = "//span[contains(text(),'Email me information')]")
	WebElementFacade soiCheckbox;

	@FindBy(xpath = "//span[contains(text(),'Stay informed')]")
	WebElementFacade conCheckbox;

	@FindBy(xpath = "//a[contains(text(),'Download PDF')]")
	WebElementFacade downloadPdfBtn;

	@Step("User launches pdf premium store site url")
	public void LaunchUrl() throws Throwable {
		comFun.Launch_URL(baseUrl);
	}

	@Step("User enters email and click button")
	public void enter_email_click_button(String user_email) throws Exception {

		LOGGER.info("User register email: " + user_email);

		inputEmail.waitUntilClickable().clear();
		inputEmail.type(user_email);
		comFun.clickElement(Button);
		jsonUtils.update_JSONValue("flow_reg_cust_EMAIL.json", "email", user_email);
	}

	@Step("Fill user data")
	public void fill_user_data(String suffix, String role, String place, String orgName, String country) {

		String fName = tUtil.AppendTimestamp("firstName");
		String lName = tUtil.AppendTimestamp("lastName");

		createData(fName, lName, suffix, role, place, orgName, country);
	}

	private void createData(String fName, String lName, String suffix, String role, String place, String orgName,
			String country) {

		firstName.waitUntilClickable().clear();
		firstName.type(fName);
		lastName.waitUntilClickable().clear();
		lastName.sendKeys(lName);
		suffixDropDown.waitUntilClickable().selectByValue(suffix);
		roleDropDown.waitUntilClickable().selectByValue(role);
		placeOfWorkDropDown.waitUntilClickable().selectByValue(place);
		nameOfOrg.waitUntilClickable().clear();
		nameOfOrg.type(orgName);
		countryDropDown.waitUntilClickable().selectByValue(country);
	}

	@Step("Click download button")
	public void click_download() {
		comFun.clickElement(downloadNowBtn);
	}

	@Step("Click embedded link")
	public void click_embedded() {
		EmbeddedLink.waitUntilClickable().click();
	}

	@Step("Click modal link")
	public void click_modal() {
		ModalLink.waitUntilClickable().click();
	}

	@Step("Click signup link")
	public void click_signup() {
		SignUp.waitUntilClickable().click();
	}

	@Step("Get text on page")
	public String get_text_on_page() {
		return textOnPage.waitUntilVisible().getText();
	}

	public boolean is_modal_visible() {
		return modalContainer.isVisible();
	}

	@Step("Get input email address")
	public String get_email_input_text() {
		return inputEmail.waitUntilClickable().getAttribute("value");
	}

	@Step("Click Not you link")
	public void click_notyou() {
		NotYouLink.waitUntilClickable().click();
	}

	@Step("User validate soi visibility and its selection status")
	public boolean checkSOIVisibility(boolean statusExp, boolean visibExp) {
		printEmailPrefrences("SOI", statusExp, visibExp, soiActual, soiCheckbox);
		tUtil.putToSession("soiValue", statusExp);
		return (elementPresence(soiActual) == statusExp && soiCheckbox.isCurrentlyVisible() == visibExp) ? true : false;
	}

	@Step("User validate connect visibility and its selection status")
	public boolean checkConnectVisibility(boolean statusExp, boolean visibExp) {
		printEmailPrefrences("CONNECT", statusExp, visibExp, connectActual, conCheckbox);
		tUtil.putToSession("connectValue", statusExp);
		return (elementPresence(connectActual) == statusExp && conCheckbox.isCurrentlyVisible() == visibExp) ? true
				: false;
	}

	@Step("User click on continue button")
	public void clickContinueBtn() {
		comFun.clickElement(Button);
	}

	@Step("User select soi and connect checkboxes on the form")
	public void selectSoi_ConnectCheckBox(boolean soiStatus, boolean connectStatus) {
		selectSOI_Connect(soiStatus, connectStatus);
	}

	@Step("User validates update in akamai for email preferences")
	public boolean validatePreferencesAkamai(Response resp) {

		boolean soiAk = resp.jsonPath().get("emailPreferences.catalystSOI.optIn") == null ? false : true;
		boolean connectAk = resp.jsonPath().get("emailPreferences.catalystConnect.optIn") == null ? false : true;

		LOGGER.info("=======EXPECTED VALUES AKAMAI=====");
		LOGGER.info("SOI OPT IN - " + (boolean) tUtil.getFromSession("soiValue"));
		LOGGER.info("CONNECT OPT IN - " + (boolean) tUtil.getFromSession("connectValue"));
		LOGGER.info("====================================");
		LOGGER.info("=======ACTUAL VALUES AKAMAI=======");
		LOGGER.info("SOI OPT IN - " + soiAk);
		LOGGER.info("CONNECT OPT IN - " + connectAk);
		LOGGER.info("====================================");
		return soiAk == (boolean) tUtil.getFromSession("soiValue")
				&& connectAk == (boolean) tUtil.getFromSession("connectValue") ? true : false;

	}

	public void selectSOI_Connect(boolean soiStatus, boolean conStatus) {
		if (soiStatus && conStatus) {
			comFun.clickElement(soiCheckbox);
			comFun.clickElement(conCheckbox);
		} else if (soiStatus && conStatus == false) {
			comFun.clickElement(soiCheckbox);
		} else if (soiStatus == false && conStatus) {
			comFun.clickElement(conCheckbox);
		}
		LOGGER.info("===============================");
		LOGGER.info("INITIAL STATE - SOI & CONNECT BOTH OPTED IN");
		LOGGER.info("SOI CHECKBOX CLICKED - SOI OPTED OUT -" + soiStatus);
		LOGGER.info("CONNECT CHECKBOX CLICKED - CONNECT OPTED OUT -" + conStatus);
		LOGGER.info("===============================");
	}

	public void clickDownloadPdf() {
		comFun.clickElement(downloadPdfBtn);
	}

	public boolean elementPresence(By element) {
		return (comFun.getElementSize(element) > 0) ? true : false;
	}

	public void printEmailPrefrences(String type, boolean statusExp, boolean visibExp, By statusActual,
			WebElementFacade visibActual) {
		LOGGER.info("***************EXPECTED VALUES******************");
		LOGGER.info(type+" "+"CHECKBOX SELECTION : " + " " + statusExp);
		LOGGER.info(type+" "+"IS CURRENTLY VISIBILE - " + visibExp);
		LOGGER.info("***********************************************");
		LOGGER.info("***************ACTUAL VALUES******************");
		LOGGER.info(type+" "+"CHECKBOX SELECTION : " + " " + elementPresence(statusActual));
		LOGGER.info(type+" "+"IS CURRENTLY VISIBILE - " + visibActual.isCurrentlyVisible());
		LOGGER.info("***********************************************");
	}

	@Step("User validates downloaded or launched pdf")
	public boolean validatePDfUrl(String url, String expectedText) {
		LOGGER.info("==================PDF LAUNCHED=======================");
		LOGGER.info("PDF URL ---->>>>>>***** MENTIONED BELOW *******");
		LOGGER.info(url);
		LOGGER.info("====================================================");
		return url.contains(expectedText) || url.contains("EBook") ? true : false ;
	}
}
