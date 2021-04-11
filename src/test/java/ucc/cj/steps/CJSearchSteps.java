package ucc.cj.steps;

import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import java.util.logging.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ucc.cj.pages.ui.CJSearchPage;
import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;

public class CJSearchSteps {

	Logger LOGGER = Logger.getLogger(CJSearchPage.class.getName());

	@Managed
	WebDriver driver;

	@Steps
	HomePage homepage;

	@Steps
	CommonFunc comFun;

	@Steps
	CJSearchPage search;

	@Given("^user is on Catalyst home page$")
	public void launch_home_page() throws Throwable {
		LOGGER.info("Launch the Homepage");
		homepage.launchHomePage();
		comFun.dismissVideoWindow();

	}

	@When("^user clicks the search icon$")
	public void click_search() throws Throwable {
		LOGGER.info("Click on Search Icon");
		search.clickSearch();

	}

	@When("^user enters a (.*) into the search bar$")
	public void enter_search(String searchKey) throws Throwable {
		LOGGER.info("Enter Search Data");
		search.enterSearch(searchKey);

	}

	@Then("^the search results are relevant to the search key$")
	public void search_results_relevant() throws Throwable {
		LOGGER.info("Check Search Result");
		search.searchResultsRelevant();
		Assert.assertTrue(search.searchResultsRelevant());

	}

	@Then("^the search bar is outlined in red$")
	public void search_bar_red() throws Throwable {
		LOGGER.info("Check Search bar");
		search.searchBarRed();
		Assert.assertTrue(search.searchBarRed());

	}

	@Then("^there are no search results$")
	public void no_search_results() throws Throwable {
		LOGGER.info("Check Search Result presnt or not");
		search.noSearchResults();
		Assert.assertTrue(search.noSearchResults());

	}

	// Search via Article Heading
	@When("^user clicks on the search icon$")
	public void click_on_the_searchicon() {
		LOGGER.info("Click on Search Magnifying glass icon");
		search.clickSearch();
	}

	@Then("^search text box should be displayed$")
	public void search_textbox_gets_displayed() {
		LOGGER.info("Check Search Text box is getting displayed");
		Assert.assertTrue(search.check_searchtextbox_displayed());
	}

	@When("^user enters the heading of any article (.*) and click on search icon$")
	public void enters_heading_of_article_and_click_on_searchicon(String SearchText) {
		LOGGER.info("Enter the heading of any article and click on search icon");
		search.clear_and_enter_text(SearchText);
	}

	@Then("^user should get the article associated to that heading (.*)$")
	public void user_should_get_article_associated_to_that_heading(String SearchText) {
		LOGGER.info("Check the article associated to the heading");
		Assert.assertEquals(search.verify_content(), SearchText);
	}

	// Advanced Search via Search Within_Title
	@When("^user clicks on the search icon and then Advanced Search Link$")
	public void click_searchicon_and_advancedsearchlink() {
		LOGGER.info("click on the search icon and then Advanced Search Link");
		search.clickSearch();
		search.click_advancedsearchlink();
	}

	@Then("^Advanced Search page title (.*) gets displayed$")
	public void verify_advancedsearch_pagetitle(String PageHeaderTitle) {
		LOGGER.info("Verify that Advanced Search page Title should gets displayed");
		Assert.assertEquals(search.verify_AdvancedSearch_pageheader(), PageHeaderTitle);
	}

	@When("^user enters search data (.*) in All words field and click on Search button$")
	public void enter_searchdata_in_allwords_and_click_on_Searchbutton(String SearchText) {
		LOGGER.info("User is able to enter Search data in All the Words field and Click on Search button");
		search.clear_and_enter_alltheword(SearchText);
	}

}
