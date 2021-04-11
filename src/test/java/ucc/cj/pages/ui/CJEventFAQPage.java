package ucc.cj.pages.ui;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import ucc.pages.ui.CommonFunc;



public class CJEventFAQPage extends PageObject{
	
	CommonFunc comFun = new CommonFunc();

	@FindBy(linkText = "Event FAQs")
	private WebElementFacade PageHeading;
	
	@FindBy(linkText = "More about Events")
	private WebElementFacade MoreaboutEvents;
	
	@FindBy(linkText = "Have you run the Pre-event streaming test?")
	private WebElementFacade PreeventStreamingTest;
	
	@FindBy(xpath = "(//span[@class='vert-icon float-right'])[1]")
	private WebElementFacade Arrowone;
	
	@FindBy(xpath = "//a[@href='http://www.conferencewebcasting.com/webcast/stream-test-4portals/test/']")
	private WebElementFacade TestStream_Link;
	
	@FindBy(linkText = "Q: What kind of Internet connection do I need to view the webcast event?")
	private WebElementFacade ViewWebcastEvent;
	
	@FindBy(xpath = "(//span[@class='vert-icon float-right'])[2]")
	private WebElementFacade ArrowTwo;
	
	@FindBy(xpath = "//a[@href='http://speedtest.net']")
	private WebElementFacade SpeedTesttool_Link;
	
	@FindBy(linkText = "Q: What are the browser requirements?")
	private WebElementFacade BrowserReq;
	
	@FindBy(xpath = "(//span[@class='vert-icon float-right'])[3]")
	private WebElementFacade ArrowThree;
	
    @FindBy(linkText = "Q: Why am I getting a Login error?")
	private WebElementFacade LoginError;
	
	@FindBy(xpath = "(//span[@class='vert-icon float-right'])[4]")
	private WebElementFacade ArrowFour;
	
	@FindBy(xpath = "//a[@href='https://catalyst.nejm.org/about/contact-us']")
	private WebElementFacade ContactUs_Link;
	
	@FindBy(linkText = "Q: After logging in to view the web event, what makes the sound or video stop, skip, cut out, or buffer?")
	private WebElementFacade SoundOrVideoIssue;
	
	@FindBy(xpath = "(//span[@class='vert-icon float-right'])[5]")
	private WebElementFacade ArrowFive;
	
	@FindBy(xpath = "//a[@href='http://youtube.com']")
	private WebElementFacade YouTube_Link;
	
	@FindBy(linkText = "Q: Why is my video completely black?")
	private WebElementFacade VideoBlackIssue;
	
	@FindBy(xpath = "(//span[@class='vert-icon float-right'])[6]")
	private WebElementFacade ArrowSix;
		
	@FindBy(linkText = "Q: Will I be able to see what I missed?")
	private WebElementFacade SeeWhatMissed;
	
	@FindBy(xpath = "(//span[@class='vert-icon float-right'])[7]")
	private WebElementFacade ArrowSeven;	
	
	@FindBy(linkText = "Q: Can I view the live stream on a mobile device?")
	private WebElementFacade MobileStreaming;
	
	@FindBy(xpath = "(//span[@class='vert-icon float-right'])[8]")
	private WebElementFacade ArrowEight;	
	
	@FindBy(xpath = "//div[@class='slide pt-10 relative']")
	private WebElementFacade LinkContent;
	
	
	
	// Page Heading
	@Step("verify the page heading")
	public boolean verify_page_heading(String Heading)	{
		return PageHeading.isDisplayed();
	}	
	
	@Step("verify All links present in a page")
	public void verify_all_links() {		
		comFun.verify_the_all_broken_links_current_page();	
	}	
	
	//More About Events
	@Step("Mouse hover on more about Events link")
	public void more_about_events()	{
		withAction().moveToElement(MoreaboutEvents).build().perform();
	}
		
	//Pre-event Streaming test link
	@Step("Mouse hover and click on Pre-event Streaming test and check contents")
	public boolean mousehover_click_preevent_streaming_test_link()	{
		withAction().moveToElement(PreeventStreamingTest).click().build().perform();
		return LinkContent.isDisplayed();
	}
	
	@Step("verify the Test Stream link in contents of Pre-event Streaming test link")
	public boolean check_teststream_preevent_streaming_test_link()	{		
		return TestStream_Link.isDisplayed();
	}
		
	//Webcast event link
	@Step("Mouse hover and click on Webcast event link and check contents")
	public boolean mousehover_click_webcast_event_link()	{
		withAction().moveToElement(ViewWebcastEvent).click().build().perform();
		return LinkContent.isDisplayed();
	}
	
	@Step("verify speed test link in contents of Webcast event link")
	public boolean check_speedtest_webcast_event_link()	{
		return SpeedTesttool_Link.isDisplayed();
	}
		
	//Browser requirements
	@Step("Mouse hover and click on Browser requirement link")
	public void mousehover_click_browser_requirement_link()	{
		withAction().moveToElement(BrowserReq).click().build().perform();
	}

	@Step("check contents on Browser requirement link")
	public boolean check_Browser_requirement_link_content()	{
        return LinkContent.isDisplayed();
	}
		
	//Login Error
	@Step("Mouse hover and click on Login Error link and check contents")
	public boolean mousehover_click_login_error_link()	{		
		withAction().moveToElement(LoginError).click().build().perform();	
		return LinkContent.isDisplayed();
	}
	
	@Step("verify contactus link in contents of login error link")
	public boolean check_contactus_loginerror_link()	{
		return ContactUs_Link.isDisplayed();
	}
			
	//Sound or video stop	
	@Step("Mouse hover and click on Sound or video stop link and check contents")
	public boolean mousehover_click_sound_or_video_stop_link()	{
		withAction().moveToElement(SoundOrVideoIssue).click().build().perform();
		return LinkContent.isDisplayed();
	}
		
	@Step("verify youtube link in contents of Sound or video stop link")
	public boolean check_youtube_sound_or_video_stop_link()	{
		return YouTube_Link.isDisplayed();
	}	
	
    //Completely black video	
	@Step("Mouse hover on Completely black video link")
	public void mousehover_click_completely_black_video() {
		withAction().moveToElement(VideoBlackIssue).click().build().perform();
	}
		
	@Step("check contents on Completely black video link")
	public boolean check_Completely_black_video_link_content()	{
		return LinkContent.isDisplayed();
	}
			
	//See What missed	
	@Step("Mouse hover and click on See What missed link")
	public void mousehover_click_what_missed()	{
		withAction().moveToElement(SeeWhatMissed).click().build().perform();
    }
	
	@Step("check contents of see What missed link")
	public boolean check_what_missed_contents() {
		return LinkContent.isDisplayed();
	}
		
	//Live stream on mobile	
	@Step("Mouse hover and click on Live stream on mobile link")
	public void mousehover_live_stream_on_mobile()	{
		withAction().moveToElement(MobileStreaming).click().build().perform();
	}
		
	@Step("check contents of view Live stream on mobile linkk")
	public boolean check_view_livestream_on_mobile_contents() {
		return LinkContent.isDisplayed();
	}

}
