package ucc.cj.pages.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import ucc.pages.ui.CommonFunc;

public class CJSearchPage extends PageObject {

	Logger LOGGER = Logger.getLogger(CJSearchPage.class.getName());
	
	CommonFunc comFun = new CommonFunc();

	@FindBy(css = "i.icon-gizmo-search")
	private WebElementFacade homeSearchIcon;

	@FindBy(xpath = "//div[@class='col-xs-12']//button[@class='icon-gizmo-search']")
	private WebElementFacade searchButton;

	@FindBy(xpath = "//input[@placeholder='Enter keyword, author, title, or citation']")
	private WebElementFacade searchBar;

	@FindBy(xpath = "(//div[contains(@class,'issue-item clearfix issue-item_sm-img')])")
	private List<WebElementFacade> searchResults;

	private By searchError = new By.ByXPath("//*[@id='pb-page-content']/div/header/div[5]/div/div/div/div");

	@FindBy(className = "search-results_message")
	private WebElementFacade searchResultsMsg;

	// Search via Article Heading
	@FindBy(xpath = "//input[@placeholder='Enter keyword, author, title, or citation']")
	private WebElementFacade SearchTextbox;

	@FindBy(xpath = "//a[contains(text(),'How Different Payment Models Support (or Undermine')]")
	private WebElementFacade Searchcontent;

	// Advanced Search via Search Within_Title
	@FindBy(xpath = "(//a[contains(.,'Advanced Search')])[2]")
	private WebElementFacade AdvancedSearch;

	@FindBy(xpath = "//h1[contains(text(),'Advanced Search')]")
	private WebElementFacade AdvancedSearchpageheader;

	@FindBy(xpath = "//input[@id='allWords']")
	private WebElementFacade AllWordsfield;

	public void clickSearch() {
		homeSearchIcon.click();
	}

	public void enterSearch(String searchKey) throws InterruptedException {
		searchBar.sendKeys(searchKey.trim());
		searchButton.click();
	}

	public boolean searchResultsRelevant() {
		List<String> results = new ArrayList<String>();

		for (WebElement result : searchResults) {
			String text = result.getText();
			results.add(text);
			LOGGER.info(text);

		}

		int resultsSize = results.size();
		int numRelevantSearches = this.stringsContainSearch(searchBar.getAttribute("value"), results);
		int percent = (int) (((double) numRelevantSearches / (double) resultsSize) * 100);

		LOGGER.info("There are " + resultsSize + " results, " + numRelevantSearches + " of which are relevant, or "
				+ percent + " percent.");

		return (percent > numRelevantSearches) ? true : false;

	}

	public int stringsContainSearch(String search, List<String> strs) {

		boolean resultContainsSearchStr;
		int i = 0;

		String[] splitSearch = search.split(" ", 0);
		Iterable<String> splitSearchIter = Arrays.asList(splitSearch);

		for (String str : strs) {

			resultContainsSearchStr = false;

			for (String searchStr : splitSearchIter) {

				if (str.toLowerCase().contains(searchStr.toLowerCase()))
					resultContainsSearchStr = true;

			}

			if (resultContainsSearchStr)
				i++;

		}

		return i;
	}

	public boolean searchBarRed() {

		return (comFun.getElementSize(searchError) > 0) ? true : false ;
	}

	public boolean noSearchResults() {

		return (searchResultsMsg.getText().trim().equals("No Results")) ? true : false;
	}

	@Step("Check Search Text box displayed")
	public boolean check_searchtextbox_displayed() {
		return SearchTextbox.waitUntilVisible().isDisplayed();
	}

	@Step("Clear and Enter Text in Search Textbox")
	public void clear_and_enter_text(String SearchText) {
		SearchTextbox.typeAndEnter(SearchText);
	}

	@Step("verify the search content")
	public String verify_content() {
		return Searchcontent.waitUntilVisible().getText();
	}

	@Step("Click on Advanced Search")
	public void click_advancedsearchlink() {
		AdvancedSearch.waitUntilVisible().click();
	}

	@Step("Verify Advanced Search page Title")
	public String verify_AdvancedSearch_pageheader() {
		return AdvancedSearchpageheader.waitUntilVisible().getText();
	}

	@Step("Clear and Enter search data in All ths words fields")
	public void clear_and_enter_alltheword(String SearchText) {
		AllWordsfield.waitUntilVisible().typeAndEnter(SearchText);
	}

}
