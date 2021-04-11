package ucc.cj.steps;

import java.util.logging.Logger;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import ucc.cj.pages.ui.CJFooterPage;
import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;

public class CJFooterSteps {

	@Steps
	CJFooterPage footerpage;

	@Steps
	HomePage homepage;

	@Steps
	CommonFunc comFun;

	@Managed
	WebDriver driver;

	private Logger LOGGER = Logger.getLogger(CJFooterPage.class.getName());

	@Given("^User is on Catalyst WebSite$")
	public void user_is_on_Catalyst_WebSite() throws Throwable {
		homepage.launchHomePage();
		footerpage.closeAlert();
	}

	@When("^User click on footer link In Depth$")
	public void user_click_on_footer_link_In_Depth() throws Throwable {
		footerpage.clickFooterInDepth();
	}

	@Then("^Verify the current URL (.*) In-Depth page$")
	public void verify_the_current_URL_In_Depth_page(String URL) throws Throwable {

		comFun.verify_the_current_URL(URL);
	}

	// Case Study

	@Given("^User is on Catalyst Web Site$")
	public void user_is_on_Catalyst_Web_Site() throws Throwable {
		homepage.launchHomePage();
		footerpage.closeAlert();
	}

	@When("^User click on footer link Case Study$")
	public void user_click_on_footer_link_Case_Study() throws Throwable {
		footerpage.Click_CaseStudy();
	}

	@Then("^Verify the current URL (.*) Case Study page$")
	public void verify_the_current_URL_Case_Study_page(String URL) throws Throwable {

		comFun.verify_the_current_URL(URL);
	}

	// Articles
	@When("^User click on footer link Articles$")
	public void user_click_on_footer_link_Articles() throws Throwable {
		footerpage.Click_Articles();
	}

	@Then("^Verify the current URL (.*) In Articles$")
	public void verify_the_current_URL_In_Articles(String URL) throws Throwable {
		comFun.verify_the_current_URL(URL);
	}

	// Insights Reports
	@When("^User click on footer link Insights Reports$")
	public void user_click_on_footer_link_Insights_Reports() throws Throwable {
		footerpage.Click_Insights_Reports();
	}

	@Then("^Verify the current URL (.*) In Insights Reports$")
	public void verify_the_current_URL_In_Insights_Reports(String URL) throws Throwable {
		comFun.verify_the_current_URL(URL);
	}

	// Insights Interviews
	@When("^User click on footer link Insights Interviews$")
	public void user_click_on_footer_link_Insights_Interviews() throws InterruptedException {
		footerpage.Click_Insights_Interviews();
	}

	@Then("^Verify the current URL (.*) In Insights Interviews$")
	public void verify_the_current_URL_In_Insights_Interviews(String URL) throws Throwable {
		comFun.verify_the_current_URL(URL);
	}

	// Talks
	@When("^User click on footer link Talks$")
	public void user_click_on_footer_link_Talks() throws InterruptedException {
		footerpage.Click_Talks();
	}

	@Then("^Verify the current URL (.*) In Talks$")
	public void verify_the_current_URL_In_Talks(String URL) throws Throwable {
		comFun.verify_the_current_URL(URL);
	}

	// Clips
	@When("^User click on footer link Clips$")
	public void user_click_on_footer_link_Clips() throws InterruptedException {
		footerpage.Click_Clips();
	}

	@Then("^Verify the current URL (.*) In Clips$")
	public void verify_the_current_URL_In_Clips(String URL) throws Throwable {
		comFun.verify_the_current_URL(URL);
	}

	// Conversations
	@When("^User click on footer link Conversations$")
	public void user_click_on_footer_link_Conversations() throws InterruptedException {
		footerpage.Click_Conversations();
	}

	@Then("^Verify the current URL (.*) In Conversations$")
	public void verify_the_current_URL_In_Conversations(String URL) throws Throwable {
		comFun.verify_the_current_URL(URL);
	}

	// Current_Issue
	@When("^User click on footer link Current Issue$")
	public void user_click_on_footer_link_Current_Issue() throws InterruptedException {
		footerpage.Click_CurrentIssue();
	}

	@Then("^Verify the current URL (.*) In Current Issue$")
	public void verify_the_current_URL_In_Current_Issue(String URL) throws Throwable {
		comFun.verify_the_current_URL(URL);
	}

	// IssueIndex
	@When("^User click on footer link Issue Index$")
	public void user_click_on_footer_link_IssueIndex() throws InterruptedException {
		footerpage.Click_IssueIndex();
	}

	@Then("^Verify the current URL (.*) In Issue Index$")
	public void verify_the_current_URL_In_IssueIndex(String URL) throws Throwable {
		comFun.verify_the_current_URL(URL);
	}

	// All Footer Links
	@When("^User click on the Footer links URL (.*)$")
	public void user_click_on_the_Footer_links_URL(String URL) throws Throwable {
		comFun.Launch_URL(URL);
	}

	// Contact Us
	@Given("^User is on Catalyst contact us Web page(.*)$")
	public void launch_contactuspage(String URL) throws Throwable {
		comFun.Launch_URL(URL);
	}

	// Contact Us page

	@When("^the user enter details  data:$")
	public void the_user_enter_details_data(DataTable dt) throws Throwable {
		footerpage.Userdetails(dt);

	}

	@Then("^user verify the submit button is enabled$")
	public void check_submitbutton() {
		footerpage.verify_submitbtn();
	}

	// Verify the valid tooltips message Facebook

	@When("the user mouse hover on Facebook social media Icons")
	public void the_user_mouse_hover_on_facebook_social_media_icons() {
		footerpage.the_user_mouse_hover_on_facebook_social_media_icons();
	}

	@Then("^Verify the valid tooltips message Facebook (.*) will appear$")
	public void verify_the_valid_tooltips_message_facebook_will_appear(String facebook) throws Throwable {
		LOGGER.info("Verify the valid tooltips message Facebook will appear");
		Assert.assertEquals(facebook, footerpage.verify_the_valid_tooltips_message_facebook_facebook_will_appear());
	}

	// Verify the valid tooltips message Twitter

	@When("the user mouse hover on Twitter social media Icons")
	public void the_user_mouse_hover_on_twitter_social_media_icons() {
		footerpage.the_user_mouse_hover_on_twitter_social_media_icons();
	}

	@Then("^Verify the valid tooltips message Twitter (.*) will appear$")
	public void verify_the_valid_tooltips_message_twitter_will_appear(String twitter) {
		LOGGER.info("Verify the valid tooltips message Twitter will appear");
		Assert.assertEquals(twitter, footerpage.verify_the_valid_tooltips_message_twitter_will_appear());
	}

	// Verify the valid tooltips message Instagram

	@When("the user mouse hover on Instagram social media Icons")
	public void the_user_mouse_hover_on_instagram_social_media_icons() {
		footerpage.the_user_mouse_hover_on_instagram_social_media_icons();
	}

	@Then("^Verify the valid tooltips message Instagram (.*) will appear$")
	public void verify_the_valid_tooltips_message_Instagram_will_appear(String instagram) {
		LOGGER.info("Verify the valid tooltips message Instagram will appear");
		Assert.assertEquals(instagram, footerpage.verify_the_valid_tooltips_message_instagram_will_appear());
	}

	// Verify the valid tooltips message Youtube

	@When("the user mouse hover on Youtube social media Icons")
	public void the_user_mouse_hover_on_youtube_social_media_icons() {
		footerpage.the_user_mouse_hover_on_youtube_social_media_icons();
	}

	@Then("^Verify the valid tooltips message Youtube (.*) will appear$")
	public void verify_the_valid_tooltips_message_youtube_will_appear(String youtube) {
		LOGGER.info("Verify the valid tooltips message Youtube will appear");
		Assert.assertEquals(youtube, footerpage.verify_the_valid_tooltips_message_youtube_will_appear());
	}

	// Verify the valid tooltips message Linkedin

	@When("the user mouse hover on Linkedin social media Icons")
	public void the_user_mouse_hover_on_linkedin_social_media_icons() {
		footerpage.the_user_mouse_hover_on_linkedin_social_media_icons();
	}

	@Then("^Verify the valid tooltips message Linkedin (.*) will appear$")
	public void verify_the_valid_tooltips_message_linkedin_will_appear(String linkedin) {
		LOGGER.info("Verify the valid tooltips message Linkedin will appear");
		Assert.assertEquals(linkedin, footerpage.verify_the_valid_tooltips_message_linkedin_will_appear());
	}

}
