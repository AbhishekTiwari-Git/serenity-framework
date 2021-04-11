package ucc.cj.steps;

import java.util.logging.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import ucc.cj.pages.ui.CJToolTip;
import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;

public class CJToolTipSteps {

	@Managed
	WebDriver driver;

	@Managed
	CJToolTip ToolTip;

	@Steps
	HomePage homepage;

	@Steps
	CommonFunc ComFun;

	private static final Logger LOGGER = Logger.getLogger(CJToolTipSteps.class.getName());
	
	// URL

	@Given("^User is on catalyst Journal Page$")
	public void user_is_on_catalyst_Journal_Article_Page() throws Throwable {
		LOGGER.info("User is on catalyst Journal Page");
		homepage.launchHomePage();
	}

	@Then("^Catalyst home page (.*) gets displayed$")
	public void Then_Catalyst_home_page_gets_displayed(String Title) throws Throwable {
		LOGGER.info("Catalyst home page Title gets displayed");
		ComFun.Verify_the_current_Title(Title);
	}

	// Article page ToolTip

	@When("^user navigates to Articles from sitemap$")
	public void the_user_mouse_hover_on_article_tool_bar() throws InterruptedException {
		LOGGER.info("user navigates to Articles from sitemap");
		ToolTip.navigate_article();
	}

	@Then("^Article page (.*) should gets displayed$")
	public void verify_the_ArticlePage_Title(String Title) throws Throwable {
		LOGGER.info("Article page title should gets displayed");
		ComFun.Verify_the_current_Title(Title);
	}

	// Download PDF ToolTip

	@When("^the user mouse hover on Download PDF Icon from article tool bar$")
	public void download_PDF_Icon_MouseHover() {
		LOGGER.info("the user mouse hover on Download PDF Icon from article tool bar");
		ToolTip.mouse_hover_on_Download_PDF_Icon_article_tool_bar();
	}

	@Then("^verify the tooltip message (.*) Download PDF will appear$")
	public void tooltip_message_on_Download_PDF(String Message) {
		LOGGER.info("verify the tooltip message Message Download PDF will appear");
		Assert.assertEquals(Message, ToolTip.verify_tooltip_message_on_PDF_Download());
	}

	// Share ToolTip

	@When("^the user mouse hover on Share Icon from article tool bar$")
	public void the_user_mouse_hover_on_Share_Icon_from_article_tool_bar() {
		LOGGER.info("the user mouse hover on Share Icon from article tool bar");
		ToolTip.the_user_mouse_hover_on_Share_Icon_from_article_tool_bar();
	}

	@Then("^verify the tooltip message (.*) Share will appear$")
	public void verify_the_tooltip_message_Share_will_appear(String Message) {
		LOGGER.info("^verify the tooltip message Message Share will appear");
		Assert.assertEquals(Message, ToolTip.verify_the_tooltip_message_Share_will_appear());
	}

	// Permissions Tooltip

	@When("^the user mouse hover on Permissions Icon from article tool bar$")
	public void the_user_mouse_hover_on_Permissions_Icon_from_article_tool_bar() {
		LOGGER.info("the user mouse hover on Permissions Icon from article tool bar");
		ToolTip.the_user_mouse_hover_on_Permissions_Icon_from_article_tool_bar();
	}

	@Then("^verify the tooltip message (.*) Permissions will appear$")
	public void verify_the_tooltip_message_Permissions_will_appear(String Message) {
		LOGGER.info("verify the tooltip message Message Permissions will appear");
		Assert.assertEquals(Message, ToolTip.verify_the_tooltip_message_Permissions_will_appear());
	}

	// More Tooltip

	@When("^the user mouse hover on More Icon from article tool bar$")
	public void the_user_mouse_hover_on_More_Icon_from_article_tool_bar() {
		LOGGER.info("the user mouse hover on More Icon from article tool bar");
		ToolTip.the_user_mouse_hover_on_More_Icon_from_article_tool_bar();
	}

	@Then("^verify the tooltip message (.*) More will appear$")
	public void verify_the_More_Tootip(String Message) {
		LOGGER.info("verify the tooltip message More will appear");
		Assert.assertEquals(Message, ToolTip.verify_the_tooltip_message_More_will_appear());
	}

	// Search Tooltip

	@When("^the user mouse hover on Search Magnifying Glass$")
	public void search_Magnifying_Glass() {
		LOGGER.info("the user mouse hover on Search Magnifying Glass");
		ToolTip.search_magnifying_glass();
	}

	@Then("^verify the search tooltip message (.*) will appear$")
	public void verify_the_searchtooltip_message_More_will_appear(String Message) throws Throwable {
		LOGGER.info("verify the search tooltip message will appear");
		Assert.assertEquals(Message, ToolTip.verify_search_ToolTip());
	}

	// NEJM Logo Tooltip

	@When("the user mouse hover on NEJMCatalyst logo")
	public void the_user_mouse_hover_on_NEJMCatalyst_logo() throws Throwable {
		LOGGER.info("the user mouse hover on NEJMCatalyst logo");
		ToolTip.the_hover_tooltip();
	}

	@Then("^verify the valid tooltip message (.*) will appear$")
	public void verify_the_tooltip_message_More_will_appear(String Message) throws Throwable {
		LOGGER.info("verify the valid tooltip message will appear");
		Assert.assertEquals(Message, ToolTip.nejmcatalystlogo_ToolTip());
		
	}

	// Content ToolTip

	@Given("^User is on catalyst Journal Article Page URL(.*)$")
	public void user_is_on_catalyst_Journal_Article_Page_URL(String URL) throws Throwable {
		LOGGER.info("User is on catalyst Journal Article Page URL");
		ComFun.Launch_URL(URL);
	}

	@When("^the user mouse hover on contents Icon from article tool bar$")
	public void the_user_mouse_hover_on_contents_Icon_from_article_tool_bar() {
		LOGGER.info("the user mouse hover on contents Icon from article tool bar");
		ToolTip.contents_navbar();
	}

	@Then("^verify the tooltip message (.*) contents will appear$")
	public void tooltip_message_Contents(String Message) throws Throwable {
		LOGGER.info("verify the tooltip message contents will appear");
		Assert.assertEquals(Message, ToolTip.Mousehover_content_tooltip());
	}

	// Care delivery ToolTip

	@When("^the user mouse hover on NEJM Catalyst logo$")
	public void the_user_mouse_hover_on_NEJMCatalystlogo() {
		LOGGER.info("the user mouse hover on NEJM Catalyst logo");
		ToolTip.innovation_caredelivery_logo();
	}

	@Then("^verify the tooltip message (.*) NEJM Catalyst Innovations in Care Delivery will appear$")
	public void tooltip_message_NEJMCatalystInnovationsinCareDelivery(String Message) throws Throwable {
		LOGGER.info("verify the tooltip message NEJM Catalyst Innovations in Care Delivery will appear");
		Assert.assertEquals(Message, ToolTip.tooltip_message_InnovationsinCareDelivery_will_appear());
	}

	// Save Article

	@Then("User clicks on a Article")
	public void user_clicks_on_a_article() {
		LOGGER.info("User clicks on a Article");
		ToolTip.user_clicks_on_a_article();
	}

	@When("the user mouse hover on Save Article Icon from article tool bar")
	public void the_user_mouse_hover_on_save_article_icon_from_article_tool_bar() {
		LOGGER.info("the user mouse hover on Save Article Icon from article tool bar");
		ToolTip.the_user_mouse_hover_on_save_article_icon_from_article_tool_bar();
	}

	@Then("^verify the tooltip message (.*) Save Article will appear$")
	public void verify_the_tooltip_message_Save_Article_will_appear(String Message) throws Throwable {
		LOGGER.info("verify the tooltip message Save Article will appear");
		Assert.assertEquals(Message, ToolTip.verify_the_tooltip_message_Save_Article_will_appear());
	}

	// AValidation of the Article tool bar Share links

	@When("the user click on the Share icon on the Article toolbar")
	public void the_user_click_on_the_share_icon_on_the_article_toolbar() {
		LOGGER.info("the user click on the Share icon on the Article toolbar");
		ToolTip.the_user_click_on_the_share_icon_on_the_article_toolbar();
	}

	@Then("verify the visible links Facebook twitter LinkedIn Email and Copy URL")
	public void verify_the_visible_links_facebook_twitter_linked_in_email_and_copy_url() {
		LOGGER.info("verify the visible links Facebook twitter LinkedIn Email and Copy URL");
		Assert.assertTrue(ToolTip.verify_the_visible_links_facebook_twitter_linked_in_email_and_copy_url());
	}
}
