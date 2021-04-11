package ucc.cj.steps;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import ucc.cj.pages.ui.CJEventFAQPage;
import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;




public class CJEventFAQSteps {
		
	
	@Managed
	WebDriver driver;

	@Managed
	CJEventFAQPage EventFAQ;

	@Steps
	HomePage homepage;
	
	@Steps
	CommonFunc ComFun;


	     //URL	
		@Given("^user is on ICV Event FAQs Page (.*)$")
		public void user_is_on_ICV_Event_FAQs_Page(String URL) throws Throwable {
			ComFun.Launch_URL(URL);
		}

		@Then("^Catalyst Event page (.*) gets displayed$")
		public void Catalyst_Event_page_gets_displayed(String Title) throws Throwable {
			ComFun.Verify_the_current_Title(Title);
		}		
		
		//Pre-event Streaming test link		
		@When("^user mouse hover and click on pre-event streaming link$")
		public void verify_preevent_streaming_link() {
			EventFAQ.mousehover_click_preevent_streaming_test_link();
		}
	
		@Then("^pre-event streaming test contents and Test Stream link should gets displayed$")
		public void teststreamlink_preevent_streaming() {			
			Assert.assertTrue(EventFAQ.check_teststream_preevent_streaming_test_link());
		}		
		
		//Webcast Event		
		@When("^user mouse hover and click on web cast link$")
		public void verify_webcast_link() {
			EventFAQ.mousehover_click_webcast_event_link();
		}
		
		@Then("^web cast contents and speedtest link should gets displayed$")
		public void speedtest_link_webcast() {
			Assert.assertTrue(EventFAQ.check_speedtest_webcast_event_link());
		}				
		
		//Browser requirement		
		@When("^user mouse hover and click on browser requirement link$")
		public void verify_browserrequirement() {
			EventFAQ.mousehover_click_browser_requirement_link();
		}
		
		@Then("^browser requirement link contents should gets displayed$")
		public void browserrequirement_content() {
			EventFAQ.check_Browser_requirement_link_content();
		}		
		
		//Login Error		
		@When("^user mouse hover and click on Login Error link$")
		public void verify_login_error_link() {
			EventFAQ.mousehover_click_login_error_link();
		}
		
		@Then("^Login Error content and contact us link contents should gets displayed$")
		public void loginerror_contactus_link() {
			EventFAQ.check_contactus_loginerror_link();
		}				
		
		//Sound or video stop		
		@When("^user mouse hover on sound or video stop link$")
		public void verify_sound_or_video_stop_link() {
			EventFAQ.mousehover_click_sound_or_video_stop_link();
		}
		
		@Then("^sound or video stop link contents and Youtube link should gets displayed$")
		public void youtube_link() {
			EventFAQ.check_youtube_sound_or_video_stop_link();
		}				
		
		//Completely black video		
		@When("^user mouse hover and click on completely black video link$")
		public void verify_completely_black_video_link() {
			EventFAQ.mousehover_click_completely_black_video();
		}
		
		@Then("^completely black video link contents should gets displayed$")
		public void completely_black_video_link_contents() {
			EventFAQ.check_Completely_black_video_link_content();
		}				
		
		//See What missed		
		@When("^user mouse hover and click on see what missed link$")
		public void verify_see_what_missed_link() {
			EventFAQ.mousehover_click_what_missed();
		}
		
		@Then("^see what missed link contents should gets displayed$")
		public void what_missed_link_contents() {
			Assert.assertTrue(EventFAQ.check_what_missed_contents());
		}
						
		//View Live stream on mobile		
		@When("^user mouse hover and click on live stream on mobile device link$")
		public void verify_live_stream_on_mobile() {
			EventFAQ.mousehover_live_stream_on_mobile();
		}
		
		@Then("^live stream on mobile device link contents should gets displayed$")
		public void live_stream_on_mobile() {
			Assert.assertTrue(EventFAQ.check_view_livestream_on_mobile_contents());
		}
}
