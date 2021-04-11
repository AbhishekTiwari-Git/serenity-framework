package ucc.cj.pages.ui;

import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;

import io.cucumber.datatable.DataTable;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import ucc.cr.pages.catalyst.ui.MyAccountPage;
import ucc.pages.ui.CommonFunc;
import ucc.utils.CucumberUtils.CucumberUtils;

public class CJFooterPage extends PageObject {
	CommonFunc comFun = new CommonFunc();
	@Steps
	MyAccountPage MyAccount;
	private Logger LOGGER = Logger.getLogger(CJFooterPage.class.getName());
	public Map<String, String> kMap;

	@FindBy(linkText = "In Depth")
	private WebElement Indepth;

	@FindBy(id = "jw-close")
	private WebElement close;

	@FindBy(linkText = "Case Studies")
	private WebElement CaseStudies;

	@FindBy(xpath = "//a[contains(text(),'Insights Reports')]")
	private WebElement InsightsReports;

	@FindBy(xpath = "//a[contains(text(),'Articles')]")
	private WebElement Article;

	@FindBy(xpath = "//a[contains(text(),'Insights Interviews')]")
	private WebElement InsightsInterviews;

	@FindBy(xpath = "//a[contains(text(),'Talks')]")
	private WebElement Talks;

	@FindBy(xpath = "//a[contains(text(),'Clips')]")
	private WebElement Clips;

	@FindBy(xpath = "//a[contains(text(),'Conversations')]")
	private WebElement Conversations;

	@FindBy(xpath = "//div[@class='footer__box footer__box--site-map']//a[contains(text(),'Current Issue')]")
	private WebElement CurrentIssue;

	@FindBy(xpath = "//a[contains(text(),'Issue Index')]")
	private WebElement IssueIndex;

	// Contact
	@FindBy(xpath = "(//a[contains(@title,'Contact Us')])[2]")
	private WebElementFacade ContactUs_link;

	@FindBy(xpath = "//h1[contains(.,'Contact Us')]")
	private WebElementFacade contactuspage_heading;

	@FindBy(xpath = "//select[@id='subject']")
	private WebElementFacade Subjectfield;

	@FindBy(xpath = "//textarea[@id='comment']")
	private WebElementFacade QuestionComment;

	@FindBy(xpath = "//input[@id='email']")
	private WebElementFacade Emailaddress;

	@FindBy(xpath = "//input[@id='firstName']")
	private WebElementFacade Firstname;

	@FindBy(xpath = "//input[@id='lastName']")
	private WebElementFacade Lastname;

	@FindBy(xpath = "//select[@id='suffix']")
	private WebElementFacade Suffix;

	@FindBy(xpath = "//input[@id='companyName']")
	private WebElementFacade Companyname;

	@FindBy(xpath = "//select[@id='country']")
	private WebElementFacade Country;

	@FindBy(xpath = "//input[@id='address1']")
	private WebElementFacade first_Address;

	@FindBy(xpath = "//input[@id='address2']")
	private WebElementFacade second_Address;

	@FindBy(xpath = "//input[@id='city']")
	private WebElementFacade City_field;

	@FindBy(xpath = "//select[@name='state' and @class='jcf jcf-hidden']")
	private WebElementFacade State_field;

	@FindBy(xpath = "//input[@id='postalCode']")
	private WebElementFacade PostalCode_field;

	@FindBy(xpath = "//div[@class='recaptcha-checkbox-border']")
	private WebElementFacade Capchacheckbox;

	@FindBy(xpath = "//input[@name='submit']")
	private WebElementFacade SubmitButton;

	@FindBy(xpath = "//i[@class='icon-facebook']")
	private WebElementFacade Facebook;

	@FindBy(xpath = "//a[@class='facebook-info' and @title='Facebook']")
	private WebElementFacade facebook;

	@FindBy(xpath = "//i[@class='icon-twitter']")
	private WebElementFacade Twitter;

	@FindBy(xpath = "//a[@class='twitter-info' and @title='Twitter']")
	private WebElementFacade twitter;

	@FindBy(xpath = "//i[@class='icon-instagram']")
	private WebElementFacade Instagram;

	@FindBy(xpath = "//a[@class='instagram-info' and @title='Instagram']")
	private WebElementFacade instagram;

	@FindBy(xpath = "//i[@class='icon-youtube']")
	private WebElementFacade Youtube;

	@FindBy(xpath = "//a[@class='youtube-info' and @title='Youtube']")
	private WebElementFacade youtube;

	@FindBy(xpath = "//i[@class='icon-linkedin']")
	private WebElementFacade Linkedin;

	@FindBy(xpath = "//a[@class='linkedin-info' and @title='Linkedin']")
	private WebElementFacade linkedin;

	@Step("User click on Footer link In Depth")
	public void clickFooterInDepth() throws InterruptedException {

		comFun.scrollAndClickElement(Indepth);

	}

	/////// 2. Case Study//////////

	@Step("Closing the pop up alert")
	public void closeAlert() throws InterruptedException {
		try {
			waitFor(ExpectedConditions.elementToBeClickable(close));
			close.click();
		} catch (Exception e) {

		}
	}

	@Step("User click on Footer link Case Study")
	public void Click_CaseStudy() throws InterruptedException {
		comFun.scrollAndClickElement(CaseStudies);
	}

	// Articles Footer Link

	@Step("User click on Footer link Articles")

	public void Click_Articles() throws InterruptedException {
		comFun.scrollAndClickElement(Article);
	}

	// Insights_Reports

	@Step("User click on Footer link InsightsReports")

	public void Click_Insights_Reports() throws InterruptedException {
		comFun.scrollAndClickElement(InsightsReports);
	}

	// Insights Interviews

	@Step("User click on footer link Insights Interviews")
	public void Click_Insights_Interviews() throws InterruptedException {
		comFun.scrollAndClickElement(InsightsInterviews);

	}

	// Talks

	@Step("User click on footer link Talks")
	public void Click_Talks() throws InterruptedException {
		comFun.scrollAndClickElement(Talks);
	}

	// Clips

	@Step("User click on footer link Clips")
	public void Click_Clips() throws InterruptedException {
		comFun.scrollAndClickElement(Clips);

	}

	// Conversations
	@Step("User click on footer link Conversations")
	public void Click_Conversations() throws InterruptedException {
		comFun.scrollAndClickElement(Conversations);

	}

	// CurrentIssue
	@Step("User click on footer link Conversations")
	public void Click_CurrentIssue() throws InterruptedException {
		comFun.scrollAndClickElement(CurrentIssue);
	}

	// IssueIndex

	@Step("User click on footer link IssueIndex")
	public void Click_IssueIndex() throws InterruptedException {
		comFun.scrollAndClickElement(IssueIndex);
	}

	// Verify the valid tooltips message Facebook will appear

	@Step("the user mouse hover on Facebook social media Icons")
	public void the_user_mouse_hover_on_facebook_social_media_icons() {
		comFun.waitForLoadPage();
		LOGGER.info("The user mouse hover on Facebook social media Icons");
		comFun.mousehover(Facebook);
	}

	@Step("Verify the valid tooltips message  Facebook will appear")
	public String verify_the_valid_tooltips_message_facebook_facebook_will_appear() {
		comFun.waitForLoadPage();
		LOGGER.info("The user mouse hover on Facebook social media Icons");
		return facebook.waitUntilVisible().getAttribute("title");

	}

	// Verify the valid tooltips message Twitter will appear

	@Step("the user mouse hover on Twitter social media Icons")
	public void the_user_mouse_hover_on_twitter_social_media_icons() {
		comFun.waitForLoadPage();
		LOGGER.info("The user mouse hover on Twitter social media Icons");
		comFun.mousehover(Twitter);
	}

	@Step("Verify the valid tooltips message Twitter will appear")
	public String verify_the_valid_tooltips_message_twitter_will_appear() {
		comFun.waitForLoadPage();
		LOGGER.info("Verify the valid tooltips message Twitter will appear");
		return twitter.waitUntilVisible().getAttribute("title");

	}

	// "Verify the valid tooltips message Instagram will appear

	@Step("the user mouse hover on Instagram social media Icons")
	public void the_user_mouse_hover_on_instagram_social_media_icons() {
		comFun.waitForLoadPage();
		LOGGER.info("The user mouse hover on Instagram social media Icons");
		comFun.mousehover(Instagram);
	}

	@Step("Verify the valid tooltips message Instagram will appear")
	public String verify_the_valid_tooltips_message_instagram_will_appear() {
		comFun.waitForLoadPage();
		LOGGER.info("Verify the valid tooltips message Instagram will appear");
		return instagram.waitUntilVisible().getAttribute("title");

	}

	// "Verify the valid tooltips message Youtube will appear

	@Step("the user mouse hover on Youtube social media Icons")
	public void the_user_mouse_hover_on_youtube_social_media_icons() {
		comFun.waitForLoadPage();
		LOGGER.info("The user mouse hover on Youtube social media Icons");
		comFun.mousehover(Youtube);
	}

	@Step("Verify the valid tooltips message Youtube will appear")
	public String verify_the_valid_tooltips_message_youtube_will_appear() {
		comFun.waitForLoadPage();
		LOGGER.info("Verify the valid tooltips message Youtube will appear");
		return youtube.waitUntilVisible().getAttribute("title");

	}

	// "Verify the valid tooltips message Linkedin will appear

	@Step("the user mouse hover on Linkedin social media Icons")
	public void the_user_mouse_hover_on_linkedin_social_media_icons() {
		comFun.waitForLoadPage();
		LOGGER.info("The user mouse hover on Linkedin social media Icons");
		comFun.mousehover(Linkedin);
	}

	@Step("Verify the valid tooltips message Linkedin will appear")
	public String verify_the_valid_tooltips_message_linkedin_will_appear() {
		comFun.waitForLoadPage();
		LOGGER.info("Verify the valid tooltips message Linkedin will appear");
		return linkedin.waitUntilVisible().getAttribute("title");

	}

	// Contact Us

	@Step("click on contactus link")
	public void click_contactus() throws Throwable {
		comFun.scrollAndClickElement(ContactUs_link);

	}

	@Step("user enter detalis")
	public void Userdetails(DataTable dt) throws Throwable {
		kMap = CucumberUtils.convert(dt);
		MyAccount.selectHiddenDropDownVal(kMap.get("Subject"), ("Subject"));
		QuestionComment.waitUntilEnabled().type(kMap.get("QuestionOrComment"));
		Emailaddress.waitUntilEnabled().type(kMap.get("Email"));
		Firstname.waitUntilEnabled().type(kMap.get("FirstName"));
		Lastname.waitUntilEnabled().type(kMap.get("LastName"));
		MyAccount.selectHiddenDropDownVal(kMap.get("Suffix"), ("Suffix"));
		Companyname.waitUntilEnabled().type(kMap.get("Company"));
		MyAccount.selectHiddenDropDownVal(kMap.get("Country"), ("Country"));
		first_Address.waitUntilEnabled().type(kMap.get("AddressFirst"));
		second_Address.waitUntilEnabled().type(kMap.get("AddessSecond"));
		City_field.waitUntilEnabled().type(kMap.get("City"));
		MyAccount.selectHiddenDropDownVal(kMap.get("State"), ("State"));
		PostalCode_field.waitUntilEnabled().type(kMap.get("PostalCode"));

	}

	@Step("check the submit button")
	public void verify_submitbtn() {
		SubmitButton.waitUntilEnabled().isPresent();
	}

}
