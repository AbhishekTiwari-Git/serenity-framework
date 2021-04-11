package ucc.nejm.mcadmin.steps;

import org.openqa.selenium.WebDriver;
import org.junit.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import ucc.nejm.mcadmin.pages.ui.MCAdminLoginPage;

public class MCAdminLoginSteps {

	@Managed
	WebDriver driver;

	@Steps
	MCAdminLoginPage loginpage;

	@Given("I launch the Media Center Admin tool URL")
	public void i_launch_the_media_center_admin_tool_url() throws Throwable {
		loginpage.launchMediaCenterAdminTool();
	}

	@When("I login to the Media Center Admin tool")
	public void i_login_to_the_media_center_admin_tool() {
		loginpage.loginMediaCenterAdminTool();
	}

	@When("I click Ln,Fn")
	public void i_click_ln_fn() {
		loginpage.clickLnFn();
	}

	@Then("Logout button is displayed")
	public void logout_button_is_displayed() {
		Assert.assertTrue("Logout button is not displayed", loginpage.verifyLogoutButton());
	}

}
