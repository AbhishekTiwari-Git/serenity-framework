package ucc.cr.pages.catalyst.ui;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucc.pages.ui.CommonFunc;
import ucc.utils.TestUtils;



public class CreateLead extends PageObject {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateLead.class);

	TestUtils tUtil=new TestUtils();
	CommonFunc commonFunc = new CommonFunc();

	@FindBy(xpath="//a[contains(text(),'Newsletter')]")
	WebElementFacade newsLetterLink;

	@FindBy(xpath="//*[starts-with(@id,'uccEmail')]")
	WebElementFacade emailId;

	@FindBy(xpath="//button[contains(text(), 'CONTINUE') or contains(text(), 'Continue')]")
	WebElementFacade continueButton;

	@FindBy(xpath="//*[starts-with(@id,'uccFirstName')]")
	WebElementFacade firstName;

	@FindBy(xpath="//*[starts-with(@id,'uccLastName')]")
	WebElementFacade lastName;

	@FindBy(xpath="//*[starts-with(@id,'uccNameOfOrg')]")
	WebElementFacade orgName;

	@FindBy(xpath="//*[starts-with(@id,'uccCountry')]")
	WebElementFacade countryDropDown;

	@FindBy(xpath="//button[contains(text(), 'SIGN UP')]")
	WebElementFacade signUpBtm;

	@FindBy(xpath="//h1[contains(text(),'Thank you')]")
	WebElementFacade signUpMsgBox;
	
	@FindBy(xpath = "//div[starts-with(@id,'slider-control')]")
	WebElementFacade feedback;
	
	@FindBy(xpath = "//div[@id='ucc-widgets-msg-modal']//div//div//header//button")
	WebElementFacade close_modal_window;



	@Step( "The user gets the input for the lead users and goes through the connect news letter flow")
	public void UserCreation(String country) throws Exception {

			String timestamp_value = tUtil.AppendTimestamp("automation");
			String fName = timestamp_value+"fn";
			String lName = timestamp_value+"ln";
			String orgName = timestamp_value+"mms";

		   runLead(fName, lName, orgName, country);
	}
	
	


	public void runLead(String fName, String lName, String oName, String country) throws InterruptedException {

		// Enter First Name
		//waitFor(ExpectedConditions.elementToBeClickable(firstName));
		//firstName.clear();
		//firstName.sendKeys(fName);
		
		firstName.waitUntilClickable().type(fName);

		// Enter Last Name
		lastName.waitUntilClickable().type(lName);

		// Select the Country
	
		countryDropDown.waitUntilClickable().selectByValue(country);

		// Enter Organization Name
		
		orgName.waitUntilClickable().type(oName);

		// Click Sign Up
		signUpBtm.waitUntilClickable().click();

	}

	@Step("Check modal window text")
	public String account_created() throws Throwable {
		return signUpMsgBox.waitUntilVisible().getText();
	}

	@Step("close modal window")
	public void close_modal_window() throws Throwable {
		close_modal_window.waitUntilVisible().click();
	}

	@Step("Scroll page down and click newletter")
	public void click_newsletter() throws InterruptedException {

		
		commonFunc.scrollAndClickElement(newsLetterLink);

		
	//	feedback.waitUntilVisible();
	//	((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView();", newsLetterLink);
 //		newsLetterLink.waitUntilClickable().click();

	}

	@Step("Enter user email")
	public void enter_email_click_button(String user_email) {

		LOGGER.info("User register email: " + user_email);

		
		emailId.waitUntilClickable().type(user_email);
		continueButton.waitUntilClickable().click();
	}
	
	@Step("Enter user email")
	public void enter_email(String user_email) {

		LOGGER.info("User register email: " + user_email);

		waitFor(ExpectedConditions.elementToBeClickable(emailId));
		emailId.type(user_email);
	}
	
	@Step("Click continue button")
	public void enter_email_continue_button() {
		continueButton.waitUntilClickable().click();
	}
	

	@Step("Get user email")
	public String get_email() {

	//	waitFor(ExpectedConditions.elementToBeClickable(emailId));
		return emailId.waitUntilClickable().getAttribute("value");
	}
}
	
	

