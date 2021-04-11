package ucc.nejm.mcadmin.steps;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.en.Then;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import ucc.nejm.mcadmin.pages.ui.MCAdminHomePage;
import io.cucumber.datatable.DataTable;
import java.util.List;

public class MCAdminHomeSteps {

	@Managed
	WebDriver driver;

	@Steps
	MCAdminHomePage homepage;

	@Then("the NEJM Dashboard presents")
	public void the_nejm_dashboard_presents() {
		Assert.assertTrue("NEJM Dashboard is not displayed", homepage.verifyNejmDashboard());
	}

	@Then("the following text is displayed")
	public void the_following_text_is_displayed(String message) {
		Assert.assertEquals(message + "text is not displayed", message, homepage.verifyTextMessage());
	}

	@Then("the following table options are displayed:")
	public void the_following_table_options_are_displayed(DataTable dataTable) {
		List<String> widgets = dataTable.asList();
		for (String widget : widgets) {
			Assert.assertTrue(widget + "is not diplayed", homepage.verifyTableWidget(widget));
		}
	}
}