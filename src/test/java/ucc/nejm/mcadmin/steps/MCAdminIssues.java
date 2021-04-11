package ucc.nejm.mcadmin.steps;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import ucc.nejm.mcadmin.pages.ui.MCAdminIssuesPage;

public class MCAdminIssues {

	@Managed
	WebDriver driver;

	@Steps
	MCAdminIssuesPage issuespage;

	@Then("^I observe the following content displayed on Issues Widget:$")
	public void i_observe_the_following_content_displayed_on_issues_widget(DataTable dataTable) {
		List<String> contents = dataTable.asList();
		for (String content : contents) {
			Assert.assertTrue(content + "is not displayed", issuespage.verifyIssuesWidgetContent(content));
		}
	}

	@When("^I click the view this table button$")
	public void i_click_the_view_this_table_button() {
		issuespage.selectViewTableButton();
	}

	@Then("^I observe following columns are displayed along with dropdown menu:$")
	public void i_observe_following_columns_are_displayed_along_with_dropdown_menu(DataTable dataTable) {
		Assert.assertTrue(dataTable + "is not displayed", issuespage.verifyColumnsIssuesWidget(dataTable));
	}

	@Then("^I navigate to the Issues table view$")
	public void i_navigate_to_the_issues_table_view() {
		Assert.assertTrue("Navigation is not correct", issuespage.navigateIssuesPage());
	}

	@When("^I select a date, such as (.*) from the Issues widget calendar$")
	public void i_select_a_date_such_as_from_the_issues_widget_calendar(String date) {
		issuespage.selectDate();
	}

	@Then("^I verify the subheadings are set as follows:$")
	public void i_verify_the_subheadings_are_set_as_follows(DataTable dataTable) {
		Assert.assertTrue(dataTable + "is not displayed", issuespage.verifySubHeadingsIssuesWidget(dataTable));
	}

	@When("^I click the dropdown list for Rows per page$")
	public void i_click_the_dropdown_list_for_rows_per_page() {
		issuespage.selectDropdown();
	}

	@Then("^The following options are listed:$")
	public void the_following_options_are_listed(DataTable dataTable) {
		List<String> contents = dataTable.asList();
		for (String content : contents) {
			Assert.assertTrue(content + "is not displayed", issuespage.verifyDropdownList(content));
		}
	}

	@When("^I select the option of (.*) rows per page$")
	public void i_select_the_option_of_rows_per_page(Integer rows) {
		issuespage.selectFiveRows();
	}

	@Then("^(.*) record rows per page are displayed$")
	public void record_rows_per_page_are_displayed(Integer rows) {
		Assert.assertTrue(rows + "rows are not displayed", issuespage.verifyFiveRows(rows));
	}

	@When("^I click the right arrow$")
	public void i_click_the_right_arrow() {
		issuespage.selectRightArrow();
	}

	@Then("^I am navigated to the next page of results.$")
	public void i_am_navigated_to_the_next_page_of_results() {
		Assert.assertTrue("Next page rows are not displayed", issuespage.verifyNextPage());
	}

	@When("^I click the left most arrow beside count of rows$")
	public void i_click_the_left_most_arrow_beside_count_of_rows() {
		issuespage.selectLeftMostArrow();
	}

	@Then("^I am navigated to the first page of results$")
	public void i_am_navigated_to_the_first_page_of_results() {
		Assert.assertTrue("First page rows are not displayed", issuespage.verifyFirstPage());
	}

	@When("^I click the right most arrow beside scroll bar$")
	public void i_click_the_right_most_arrow_beside_scroll_bar() {
		issuespage.selectRightMostArrow();
	}

	@Then("^I am navigated to the last page of results$")
	public void i_am_navigated_to_the_last_page_of_results() {
		Assert.assertTrue("First page rows are not displayed", issuespage.verifyLastPage());
	}

	@When("^I click the left arrow$")
	public void i_click_the_left_arrow() {
		issuespage.selectLeftArrow();
	}

	@Then("^I am navigated to the previous page of results$")
	public void i_am_navigated_to_the_previous_page_of_results() {
		Assert.assertTrue("Pervious page rows are not displayed", issuespage.verifyPreviousPage());
	}

	@Then("^left most arrow is disabled$")
	public void left_most_arrow_is_disabled() {
		Assert.assertTrue("First page is displayed", issuespage.verifyLeftMostArrowDisabled());
	}
	
	@Then("^previous page arrow is disabled$")
	public void previous_page_arrow_is_disabled() {
		Assert.assertTrue("First page is displayed", issuespage.verifyLeftArrowDisabled());
	}
}