package ucc.cj.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import ucc.cj.pages.ui.SearchFilterPage;
import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;


public class SearchFilterSteps {
	
	@Managed
	WebDriver driver;
	
	@Steps
	HomePage homepage;
	
	@Steps
	CommonFunc comFun;
	
	@Steps 
	SearchFilterPage search;
	
	@Given("^user is on Catalyst Journal Page$")
	public void launch_homepage() throws Throwable {
		homepage.launchHomePage();
		comFun.dismissVideoWindow();
	}
	
	@When("^user clicks on the search icon on the header$")
	public void click_search() throws Throwable {
		search.clickSearch();
	}
	
	@When("^user enters a keyword (.*)$")
	public void enter_searchKeyword(String searchKey) throws Throwable {
		search.enterSearch(searchKey);
	}
	
	@And("^selects (.*)$")
	public void select_sort_option(String sortOption) throws Throwable {
		search.selectSortOption(sortOption);
	}
	
	@Then("^the search results should be sorted according to the sort option$")
	public void search_results_sorted() throws Throwable {
		search.searchResultsSorted();
	}
	
	@And("^user clicks filter$")
	public void click_filter() throws Throwable {
		search.clickFilter();
	}
	
	@When("^user selects By Date as (.*)$")
	public void select_by_date(String dateOption) throws Throwable {
		search.selectByDate(dateOption);
	}
	
	@Then("^the search results should be from within the given date option$")
	public void verify_results_by_date() throws Throwable {
		search.verifyResultsByDate();
	}
	
	@When("^user selects By Content Type as (.*)$")
	public void select_by_content_type(String contentType) throws Throwable {
		search.selectByContentType(contentType);
	}
	
	@Then("^the search results should all be of that type$")
	public void verify_results_by_content_type() throws Throwable {
		search.verifyResultsByContentType();
	}
	
	@When("^user selects By Topic as (.*)$")
	public void select_by_topic(String topic) throws Throwable {
		search.selectByTopic(topic);
	}
	
	@Then("^the search results should be related to the topic$")
	public void search_results_relevant_to_topic() throws Throwable {
		Assert.assertTrue(search.searchResultsRelevantToTopic());
	}
	
	@Then("^user should get the article associated to that heading$")
	public void search_results_relevant_to_article_heading() {
		search.searchResultsRelevantToArticleHeading();
	}
	
	@When("^user clicks on Advanced Search$")
	public void click_advanced_search() throws Throwable {
		search.clickAdvancedSearch();
	}
	
	@Then("^user should be navigated to the Advanced Search page$")
	public void verify_advanced_search_page() throws Throwable {
		comFun.Verify_the_current_Title("Advanced Search - NEJM Catalyst");
	}

}