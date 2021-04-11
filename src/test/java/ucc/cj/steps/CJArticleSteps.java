package ucc.cj.steps;

import java.io.IOException;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import ucc.cj.pages.ui.CJArticlePage;
import ucc.pages.ui.CommonFunc;
import ucc.pages.ui.HomePage;

public class CJArticleSteps {

	
	@Managed
	WebDriver driver;

	@Managed
	CJArticlePage articlepdf;

	@Steps
	HomePage homepage;
	
	@Steps
	CommonFunc ComFun;



	
	@Given("^user is on any article page URL (.*)$")
	public void user_is_on_any_article_page_URL(String URL) throws Throwable {
		ComFun.Launch_URL(URL);
	}	
	
	@When("^user checks the heading present for the article$")
	public void verify_article_heading() {
		Assert.assertTrue(articlepdf.check_article_heading_present());
	}
	
	@Then("^article PDF icon and Tooltip message (.*) should get displayed$")
	public void access_pdf(String Message) {
		Assert.assertTrue(articlepdf.check_article_Download_PDF_Icon());
		Assert.assertEquals(articlepdf.verify_tooltip_message_on_PDF_Download(), Message);
	}
	
	@And("^user click on PDF icon from Article Toolbar$")
	public void click_on_PDF_icon() {
		articlepdf.click_Download_PDF_Icon();
	}	
	
	@Then("^user verify the previous url and pdf url$")
	public void check_pdf() throws IOException	{
		Assert.assertTrue(articlepdf.verify_before_and_after_url());
	}	
	
	@When("^user click on  pdf icon from article tool bar$")
	public void click_pdfbutton() {
		articlepdf.click_Download_PDF_Icon();
	}
	
	@Then("^article page should be open and verify texts$")
	public void verify_pdf() {
		Assert.assertTrue(articlepdf.verify_before_and_after_url());
	}
}
