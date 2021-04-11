package ucc.nejm.mcadmin.steps;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import ucc.nejm.mcadmin.pages.ui.MCAdminEmailLogPage;

public class MCAdminEmailLogSteps {

	@Managed
	WebDriver driver;

	@Steps
	MCAdminEmailLogPage emailLogPage;

	@Then("I observe the following content displayed on Email log table widget:")
	public void i_observe_the_following_content_displayed_on_email_log_table_widget(DataTable dataTable) {
		List<String> contents = dataTable.asList();
		for (String content : contents) {
			Assert.assertTrue(content + "is not diplayed", emailLogPage.verifyEmailLogWidgetContent(content));
		}
	}

}
