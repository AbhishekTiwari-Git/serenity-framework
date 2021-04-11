package ucc.cj.pages.ui;

import java.util.logging.Logger;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import ucc.pages.ui.CommonFunc;

public class CJHeaderPage extends PageObject {

	CommonFunc comFun = new CommonFunc();
	private static final Logger LOGGER = Logger.getLogger(CJHeaderPage.class.getName());

	// Validate the header section "SIGN IN >"dropdown when user is NOT signed for

	@FindBy(xpath = "//span[contains(.,'Sign in')]")
	private WebElementFacade SignIn;

	@FindBy(xpath = "//a[@class='litSsoLogin']")
	private WebElementFacade SignIn_drp;

	@FindBy(xpath = "//a[contains(.,'OpenAthens/Shibboleth')]")
	private WebElementFacade OpenAthens_Shibboleth_drp;

	@FindBy(xpath = "(//a[contains(.,'Create Account')])[2]")
	private WebElementFacade CreateAccount_drp;

	@FindBy(xpath = "//div[contains(@class,'ucc-form-container')]")
	private WebElementFacade CreteAccount_page;

	@FindBy(xpath = "//h1[contains(@class,'ucc-form-header')]")
	private WebElementFacade CreteAccount_headermessage;

	// Validate the OpenAthens / Shibboleth Sign In Page_User click on "Login via

	@FindBy(xpath = "//h2[contains(.,'OpenAthens / Shibboleth Sign In')]")
	private WebElementFacade OpenAthens_pageheader;

	@FindBy(xpath = "//a[contains(text(),'Login via OpenAthens')]")
	private WebElementFacade LoginViaOpenAthens;

	@FindBy(xpath = "(//input[@name='username'])[1]")
	private WebElementFacade Username;

	@FindBy(xpath = "(//input[contains(@id,'password')])[1]")
	private WebElementFacade Password;

	@FindBy(xpath = "//button[contains(.,'Sign in')]")
	private WebElementFacade OpenAthens_SignIn;

	@FindBy(xpath = "//div[@class='institution-info-wrapper']")
	private WebElementFacade OpenAthens_AccessMessage;

	@FindBy(xpath = "//span[contains(text(),'Follow Us')]")
	private WebElementFacade FollowUs;

	@FindBy(xpath = "//a[contains(text(),'Facebook')]")
	private WebElementFacade Facebook;

	@FindBy(xpath = "//a[contains(text(),'Twitter')]")
	private WebElementFacade Twitter;

	@FindBy(xpath = "//a[contains(text(),'Instagram')]")
	private WebElementFacade Instagram;

	@FindBy(xpath = "//a[contains(text(),'YouTube')]")
	private WebElementFacade YouTube;

	@FindBy(xpath = "//span[contains(text(),'Events')]")
	private WebElementFacade Events;

	@FindBy(linkText = "Next Event")
	private WebElementFacade NextEvent;

	@FindBy(xpath = "//a[contains(text(),'Register for Next Event')]")
	private WebElementFacade RegisterforNextEvent;

	@FindBy(xpath = "//a[contains(text(),'About Events')]")
	private WebElementFacade AboutEvents;

	@FindBy(xpath = "//a[contains(text(),'Past Events')]")
	private WebElementFacade PastEvents;

	@FindBy(xpath = "//a[contains(text(),'Video Highlights')]")
	private WebElementFacade VideoHighlights;

	@FindBy(xpath = "//span[contains(text(),'Insights Council')]")
	private WebElementFacade InsightsCouncil;

	@FindBy(xpath = "//a[contains(text(),'About Insights Council')]")
	private WebElementFacade AboutInsightsCouncil;

	@FindBy(xpath = "//a[contains(text(),'Latest Insights')]")
	private WebElementFacade LatestInsights;

	@FindBy(xpath = "//a[contains(text(),'Apply to join')]")
	private WebElementFacade Applytojoin;

	@FindBy(linkText = "Sign in")
	private WebElementFacade Signin;

	@FindBy(xpath = "//span[contains(text(),'About')]")
	private WebElementFacade About;

	@FindBy(linkText = "NEJM Catalyst")
	private WebElementFacade NEJMCatalyst;

	@FindBy(xpath = "//a[contains(text(),'Journal')]")
	private WebElementFacade Journal;

	@FindBy(linkText = "Events")
	private WebElementFacade Event;

	@FindBy(linkText = "Insights Council")
	private WebElementFacade Insightscouncil;

	@FindBy(linkText = "Contact Us")
	private WebElementFacade ContactUs;

	@FindBy(xpath = "//span[contains(text(),'Topics')]")
	private WebElementFacade Topics;

	@FindBy(xpath = "//a[contains(text(),'Alternative Payment Models')]")
	private WebElementFacade AlternativePaymentModels;

	@FindBy(xpath = "//a[contains(text(),'Analytics and Outcomes')]")
	private WebElementFacade AnalyticsandOutcomes;

	@FindBy(xpath = "//a[contains(text(),'Coronavirus')]")
	private WebElementFacade Coronavirus;

	@FindBy(xpath = "//a[contains(text(),'Culture of Health')]")
	private WebElementFacade CultureofHealth;

	@FindBy(xpath = "//a[contains(text(),'Health Care Leadership')]")
	private WebElementFacade HealthCareLeadership;

	@FindBy(xpath = "//a[contains(text(),'Health Technology')]")
	private WebElementFacade HealthTechnology;

	@FindBy(xpath = "//a[contains(text(),'Market Landscape')]")
	private WebElementFacade MarketLandscape;

	@FindBy(xpath = "//a[contains(text(),'New Models of Care')]")
	private WebElementFacade NewModelsofCare;

	@FindBy(xpath = "//a[contains(text(),'Patient-Centered Care')]")
	private WebElementFacade PatientCenteredCare;

	@FindBy(xpath = "//a[contains(text(),'View all topics')]")
	private WebElementFacade Viewalltopics;

	@Step("Verify and mouse hover on SignIn button")
	public void verify_mousehover_signin() {
		LOGGER.info("Verify and mouse hover on SignIn button");
		SignIn.waitUntilClickable();
		comFun.mousehover(SignIn);
	}

	@Step("Verify after mouse hover SignIn,OpenAthens/Shibboleth and Create Account fields should be present")
	public boolean verify_SignIn_OpenAthensShibboleth_CreateAccount_present() {

		boolean signIn = SignIn_drp.isCurrentlyVisible();
		LOGGER.info("SignIn Link Visible" + signIn);

		boolean OpenAthens = OpenAthens_Shibboleth_drp.isCurrentlyVisible();
		LOGGER.info("OpenAthens Link Visible" + OpenAthens);

		boolean createAcc = CreateAccount_drp.isCurrentlyVisible();
		LOGGER.info("create Account Link Visible" + createAcc);

		return (signIn && OpenAthens && createAcc) ? true : false;

	}

	@Step("Mouse hover and click on Create account")
	public void mousehover_click_createaccount() {
		LOGGER.info("Mouse hover and click on Create account");
		CreateAccount_drp.waitUntilClickable();
		comFun.mousehover(CreateAccount_drp);
	}

	@Step("Verify Create Account page")
	public boolean verify_createaccount_page() {
		LOGGER.info("Create Account page displayed" + CreteAccount_page.isCurrentlyVisible());
		return CreteAccount_page.isCurrentlyVisible();
	}

	@Step("check create account header message Text")
	public String check_createaccount_headermessage() {
		LOGGER.info("check create account header message Text");
		return CreteAccount_headermessage.waitUntilVisible().getText();
	}

	// Validate the OpenAthens / Shibboleth Sign In Page_User click on "Login via

	@Step("Click on OpenAthens/Shibboleth")
	public void click_openathens_shibboleth() {
		LOGGER.info("Click on OpenAthens/Shibboleth");
		OpenAthens_Shibboleth_drp.click();
	}

	@Step("Check OpenAthens/Shibboleth page header")
	public String check_openathens_shibboleth_header() {
		LOGGER.info("Check OpenAthens/Shibboleth page header");
		return OpenAthens_pageheader.waitUntilVisible().getText();
	}

	@Step("Click on Login Via OpenAthens")
	public void click_login_via_openathes() {
		LOGGER.info("Click on Login Via OpenAthens");
		LoginViaOpenAthens.click();
	}

	@Step("Enter Email address or Password and click on the SignIn Button")
	public void enter_email_or_username_and_password(String username, String password) {
		LOGGER.info("Enter the User Name");
		Username.waitUntilVisible().type(username);
		LOGGER.info("Enter the Password");
		Password.waitUntilVisible().type(password);
		LOGGER.info("Click on SignIn Button");
		OpenAthens_SignIn.waitUntilClickable().click();
	}

	@Step("Check Access Message visibilty")
	public String check_accessmessage() {
		LOGGER.info("Check Access Message visibilty");
		return OpenAthens_AccessMessage.waitUntilVisible().getText();
	}

	// Validate the header section "Follow Us" Dropdown

	@Step("User mouse hover on Follow Us")
	public void user_mouse_hover_on_follow_us() {
		comFun.waitForLoadPage();
		LOGGER.info("User mouse hover on Follow Us");
		comFun.mousehover(FollowUs);

	}

	@Step("dropdown gets open having value Facebook, Twitter, Instagram, YouTube, LinkedIn")
	public boolean dropdown_gets_open_having_value_facebook_twitter_instagram_you_tube_linked_in() {
		comFun.waitForLoadPage();

		boolean facebook = Facebook.isCurrentlyVisible();
		LOGGER.info("FaceBook Link Visible" + facebook);

		boolean twitter = Twitter.isCurrentlyVisible();
		LOGGER.info("Twitter Link Visible" + twitter);

		boolean instagram = Instagram.isCurrentlyVisible();
		LOGGER.info("Instagram Link Visible" + instagram);

		boolean youtube = YouTube.isCurrentlyVisible();
		LOGGER.info("YouTube Link Visible" + youtube);

		return (facebook && twitter && instagram && youtube) ? true : false;

	}

	// Validate the header section "Events" Dropdown

	@Step("User mouse hover on Events")
	public void user_mouse_hover_on_events() {
		comFun.waitForLoadPage();
		LOGGER.info("User mouse hover on Events");
		comFun.mousehover(Events);
	}

	@Step("verify the dropdown values")
	public boolean verify_the_dropdown_values() {
		comFun.waitForLoadPage();

		boolean nextEvent = NextEvent.isCurrentlyVisible();
		LOGGER.info("NextEvent Link Visible" + nextEvent);

		boolean registerforNextEvent = RegisterforNextEvent.isCurrentlyVisible();
		LOGGER.info("Register for Next Event Link Visible" + registerforNextEvent);

		boolean aboutEvents = AboutEvents.isCurrentlyVisible();
		LOGGER.info("About Events Link Visible" + aboutEvents);

		boolean pastEvent = PastEvents.isCurrentlyVisible();
		LOGGER.info("Past Event Link Visible" + pastEvent);

		boolean videoHighlights = VideoHighlights.isCurrentlyVisible();
		LOGGER.info("videoHighlights Link Visible" + videoHighlights);

		return (nextEvent && registerforNextEvent && aboutEvents && pastEvent && videoHighlights) ? true : false;
	}

	// Validate the header section "INSIGHTS COUNCIL" dropdown menu

	@Step("User mouse hover on INSIGHTS COUNCIL")
	public void user_mouse_hover_on_insights_council() {
		comFun.waitForLoadPage();
		LOGGER.info("User mouse hover on INSIGHTS COUNCIL");
		comFun.mousehover(InsightsCouncil);

	}

	@Step("verify the dropdown values")
	public boolean verify_the_Insights_Council_dropdown_values() {

		comFun.waitForLoadPage();
		boolean aboutInsightsCouncil = AboutInsightsCouncil.isCurrentlyVisible();
		LOGGER.info("About Insights Council Link Visible" + aboutInsightsCouncil);

		boolean latestInsights = LatestInsights.isCurrentlyVisible();
		LOGGER.info("Latest Insights Link Visible" + latestInsights);

		boolean applytojoin = Applytojoin.isCurrentlyVisible();
		LOGGER.info("Apply to join Link Visible" + applytojoin);

		boolean signin = Signin.isCurrentlyVisible();
		LOGGER.info("Signin Link Visible" + signin);

		return (aboutInsightsCouncil && latestInsights && applytojoin && signin) ? true : false;

	}

	// Validate the header section "About" dropdown menu

	@Step("User mouse hover on About")
	public void user_mouse_hover_on_about() {

		comFun.waitForLoadPage();
		LOGGER.info("User mouse hover on About");
		comFun.mousehover(About);

	}

	@Step("verify the About dropdown all values")
	public boolean verify_the_about_dropdown_all_values() {

		comFun.waitForLoadPage();
		boolean NEJMcatalyst = NEJMCatalyst.isCurrentlyVisible();
		LOGGER.info("NEJM Catalyst Council Link Visible" + NEJMcatalyst);

		boolean journal = Journal.isCurrentlyVisible();
		LOGGER.info("Journal Link Visible" + journal);

		boolean event = Event.isCurrentlyVisible();
		LOGGER.info("Event Link Visible" + event);

		boolean insightscouncil = Insightscouncil.isCurrentlyVisible();
		LOGGER.info("Insights Council Visible" + insightscouncil);

		boolean Contactus = ContactUs.isCurrentlyVisible();
		LOGGER.info("Contact Us Visible" + Contactus);

		return (NEJMcatalyst && journal && event && insightscouncil && Contactus) ? true : false;

	}

	@Step("User mouse hover on About")
	public void user_mouse_hover_on_Topics() {

		comFun.waitForLoadPage();
		LOGGER.info("User mouse hover on Topics");
		comFun.mousehover(Topics);

	}

	@Step("verify the About dropdown all values")
	public boolean verify_the_Topics_dropdown_all_values() {

		comFun.waitForLoadPage();

		LOGGER.info("Alternative Payment models Link Visible" + AlternativePaymentModels.isCurrentlyVisible());

		LOGGER.info("Analytics And Outcomes Link Visible" + AnalyticsandOutcomes.isCurrentlyVisible());

		LOGGER.info("Coronavirus Link Visible" + Coronavirus.isCurrentlyVisible());

		LOGGER.info("Culture Of Health Link Visible" + CultureofHealth.isCurrentlyVisible());

		LOGGER.info("Health care Leadership Link Visible" + HealthCareLeadership.isCurrentlyVisible());

		LOGGER.info("Health Technology Link Visible" + HealthTechnology.isCurrentlyVisible());

		LOGGER.info("Market Landscape Link Visible" + MarketLandscape.isCurrentlyVisible());

		LOGGER.info("New Models Of Care Link Visible" + NewModelsofCare.isCurrentlyVisible());

		LOGGER.info("Patient Centered Care Link Visible" + PatientCenteredCare.isCurrentlyVisible());

		LOGGER.info("View All Topics Care Link Visible" + Viewalltopics.isCurrentlyVisible());

		return (AlternativePaymentModels.isCurrentlyVisible() && AnalyticsandOutcomes.isCurrentlyVisible()
				&& Coronavirus.isCurrentlyVisible() && CultureofHealth.isCurrentlyVisible()
				&& HealthCareLeadership.isCurrentlyVisible() && HealthTechnology.isCurrentlyVisible()
				&& MarketLandscape.isCurrentlyVisible() && NewModelsofCare.isCurrentlyVisible()
				&& PatientCenteredCare.isCurrentlyVisible() && Viewalltopics.isCurrentlyVisible()) ? true : false;
	}

}
