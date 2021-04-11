package ucc.cr.steps.catalyst;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ucc.cr.pages.catalyst.ui.CRSavedPage;
import ucc.cr.pages.catalyst.ui.JoinIC;
import ucc.cr.pages.catalyst.ui.MyAccountPage;
import ucc.cr.pages.catalyst.ui.RegInsighCouncilPage;
import ucc.cr.pages.catalyst.ui.utils.MyAccountPageHelper;
import ucc.i.method.aic.AICGET;
import ucc.i.method.kinesys.KinesysGET;
import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.RestUtil;
import ucc.utils.TestUtils;
import ucc.utils.CucumberUtils.CucumberUtils;
import io.cucumber.datatable.DataTable;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.SplittableRandom;

public class CRSavedSteps {

	CommonFunc commonFunc = new CommonFunc();
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();

	@Steps
	CRSavedPage savedPage;

	@Steps
	KinesysGET kinesysGETSteps;

	@Steps
	AICGET aicGETSteps;

	@Managed
	WebDriver driver;

	@When("^user save an article on catalyst site and navigate to my account using data:$")
	public void saveArticle(DataTable dt) throws Throwable {
		savedPage.saveArticles(dt);
	}

	@Then("^articles should be saved under articles section of library - articles and searches$")
	public void validateSavedArticles() {
		Assert.assertEquals(true, savedPage.validateSavedArticles());
	}

	@And("^user validate fields on saved article$")
	public void validateSavedArticlesFields() {
		Assert.assertEquals(true, savedPage.validateSavedArticleFields());
	}

	@When("^user validate article navigation from my account to catalyst site$")
	public void launchArticleMyAccountSite() {
		Assert.assertEquals(true, savedPage.launchArticleMyAcc());
	}

	@When("^user apply keyword filter from my account and delete saved article$")
	public void applyKeywordFilter() throws InterruptedException {
		savedPage.applyKeywordFilter();
		savedPage.deleteSavedArticle();
	}

	@Then("^user validate pre-selected filter is not removed and filter items count is updated$")
	public void validatePreSelectedFilter() {
		Assert.assertEquals(true, savedPage.preSelectedFilter());
	}

	@When("^user enter long value in description (.*) and keyword (.*)$")
	public void enterLongDescription(String longDesc, String longKey) {
		savedPage.enterLongDescriptionKeyword(longDesc, longKey);
	}

	@Then("^user is not allowed to save long description values beyond (.*) and (.*)$")
	public void validateLongDescriptionInput(String expLimitDesc, String expLimitKeyw) {
		Assert.assertEquals(true, savedPage.validateLongDescrpKeywordInput(expLimitDesc, expLimitKeyw));
	}

	@When("^user update existing keyword (.*) or description (.*)$")
	public void updateExistingDescripKeyword(String keyword, String descrip) {
		savedPage.updateExistingKeyword_Descrip(keyword, descrip);
	}

	@Then("^existing keyword or description should be updated$")
	public void validateExistingKeyword_Descrip() {
		Assert.assertEquals(true, savedPage.validateUpdateExistingKeyword_Descrip());
	}

	@And("^user validate pagination link at the bottom of saved items page for 11 articles$")
	public void validatePaginationLink() {
		Assert.assertEquals(true, savedPage.validatePaginationLink());
	}

	@And("^user validate pagination link appear if saved articles are less than 11 with disabled buttons$")
	public void validatePaginationLink_lessArticles() {
		Assert.assertEquals(true, savedPage.validatePaginationLink_LessArticles());
	}

	@And("^user validate saved itmes are loaded for user having auth token url$")
	public void validateSavedItmes_AuthToken() throws Throwable {
		Assert.assertEquals(true, savedPage.validateSavedItems_AuthToken());
	}

}
