package ucc.cj.steps;

import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import ucc.cj.pages.ui.CJFooterPage;
import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;

public class LinkValidationSteps {

	@Steps
	HomePage homepage;
	
	@Steps
	CJFooterPage footerpage;
	
	@Steps
	CommonFunc comFun;
	
	@Managed                                                            
	WebDriver driver;
	
	
	@Given("^User click on the About Page URL (.*)$")
	public void User_click_on_About_Page_URL(String URL)throws Throwable {
		comFun.Launch_URL(URL);
	}
	
	@Then("^Verify the valid url gets accessible with valid Title (.*)$")
	public void Verify_the_current_Title_All_Home_Page_links(String Title)throws Throwable {
		comFun.Verify_the_current_Title(Title);
	}
	
	@Given("^User click on the catalyst site URL (.*)$")
	public void User_click_on_Home_Page_URL(String URL)throws Throwable {
		comFun.Launch_URL(URL);
	}
	
	@Given("^User click on the Article Page URL (.*)$")
	public void User_click_on_Article_Page_URL(String URL)throws Throwable {
		comFun.Launch_URL(URL);
	}
	
	@Given("^User click on the Author Center URL (.*)$")
	public void User_click_on_Authoe_Center_URL(String URL)throws Throwable {
		comFun.Launch_URL(URL);
	}
	
	
	@Then("^Verify the URL matches with the correct URL (.*) format$")
	public void verify_URL(String URL) throws Throwable{
		comFun.verify_the_current_URL(URL);
	}
	
	
	@Then("^verify the all broken links current page$")
	public void verify_the_all_broken_links_current_page() {
		comFun.verify_the_all_broken_links_current_page();
	}
	
}
