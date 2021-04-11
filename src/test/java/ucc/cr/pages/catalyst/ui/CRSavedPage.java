package ucc.cr.pages.catalyst.ui;

import io.restassured.response.Response;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;
import ucc.utils.CucumberUtils.CucumberUtils;
import io.cucumber.datatable.DataTable;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CRSavedPage extends PageObject {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String catalystBaseUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("catalyst.base.url");
	static String myAccBaseUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("myaccount.base.url");
	String doiFile = "doi/catalystDoi.json";
	String saved = "/saved";

	public Response sysResp = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(CRSavedPage.class);
	public Map<String, String> kMap;
	public Map<String, String> kMap_actual = new HashMap<String, String>();
	CreateAccount crAcc = new CreateAccount();
	CommonFunc commonFunc = new CommonFunc();
	JoinIC joinIc = new JoinIC();
	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	String filePath = env_var.getProperty("json.body.path");
	int addedArticle;
	int setCount = 0;
	String myAcc, catJl;
	boolean fields;

	@FindBy(xpath = "//div[contains(@class, 'ucc-widget-accordion')]")
	WebElementFacade accFormDisplay;

	@FindBy(xpath = "//*[starts-with(@id, 'uccFirstName')]")
	private WebElementFacade enterFirstName;

	@FindBy(xpath = "//*[starts-with(@id, 'uccLastName')]")
	private WebElementFacade enterLastName;

	@FindBy(xpath = "//*[starts-with(@id, 'uccCountry')]")
	private WebElementFacade selectCountry;

	@FindBy(xpath = "//*[starts-with(@id, 'uccSuffix')]")
	private WebElementFacade selectSuffix;

	@FindBy(xpath = "//*[starts-with(@id, 'uccPlaceOfWork')]")
	private WebElementFacade selectPlace;

	@FindBy(xpath = "//*[starts-with(@id, 'uccRole')]")
	private WebElementFacade selectRole;

	@FindBy(xpath = "//*[starts-with(@id, 'uccNameOfOrg')]")
	private WebElementFacade enterOrg;

	@FindBy(xpath = "//*[starts-with(@id, 'uccSpecialty')]")
	private WebElementFacade enterSpecialty;

	@FindBy(xpath = "//div[contains(text(),'Personal')]/ancestor::div[contains(@class, 'header')]/following-sibling::div//button")
	private WebElementFacade continueButton1;

	@FindBy(xpath = "//div[contains(text(),'Professional')]/ancestor::div[contains(@class, 'header')]/following-sibling::div//button")
	private WebElementFacade continueButton2;

	@FindBy(xpath = "//*[starts-with(@id, 'uccEmail')]")
	private WebElementFacade enterEmail;

	@FindBy(xpath = "//*[starts-with(@id, 'uccPwd')]")
	private WebElementFacade enterPass;

	@FindBy(xpath = "//button[contains(text(), 'CREATE ACCOUNT')]")
	private WebElementFacade createAccountBtn;

	@FindBy(xpath = "//button[contains(@class, 'ucc-btn ucc-btn-primary')]")
	private WebElementFacade Button;

	@FindBy(xpath = "//p[contains(text(),'Your account was successfully created.')]")
	private WebElementFacade SuccessText;

	@FindBy(xpath = "//a[starts-with(@class,'litSsoCreate')]/span")
	private WebElementFacade CreateAccountLink;

	@FindBy(xpath = "//span[contains(text(),' Create Account')]")
	private WebElementFacade CreateAccountInnerLink;

	@FindBy(xpath = "//*[starts-with(text(), '404')]")
	private WebElementFacade pageNotFound;

	@FindBy(xpath = "//*[starts-with(@class, 'article_toolbar-save-btn isLoggedIn icon-save')]")
	private WebElementFacade loggedIn_SaveLink;

	@FindBy(xpath = "//*[starts-with(text(), 'Save Article')]")
	private WebElementFacade saveArticleHeading_CJSite;

	@FindBy(xpath = "//*[starts-with(@id, 'ssDescription')]")
	private WebElementFacade savedArticleDescrip_CJSite;

	@FindBy(xpath = "//*[starts-with(@id, 'ssKeywords')]")
	private WebElementFacade savedArticleKeyword_CJSite;

	@FindBy(xpath = "//*[starts-with(@class, 'article_title')]")
	private WebElementFacade articleTitle_CJSite;

	@FindBy(xpath = "//*[starts-with(@id, 'content1')]/li[1]//a[@class='artTitle']")
	private WebElementFacade articleTitle_MyAccSite;

	@FindBy(xpath = "//*[starts-with(@class, 'cat-btn_primary save_form-saveBtn')]")
	private WebElementFacade saveButton;

	@FindBy(xpath = "//*[starts-with(text(), 'This article has been successfully saved.')]")
	private WebElementFacade saveArticleSuccessMsg;

	@FindBy(xpath = "//*[starts-with(text(), 'See your saved items')]")
	private WebElementFacade seeYourSavedItemsLink;

	@FindBy(xpath = "//*[starts-with(@class, 'side-nav-list')]//a[contains(text(), 'Library – Articles & Searches')]")
	private WebElementFacade myAccArticlesSearchesLink;

	@FindBy(xpath = "//h2[starts-with(text(), 'Library – Articles & Searches')]")
	private WebElementFacade myAccLibraryHeading;

	@FindBy(xpath = "//a[contains(text(),'Articles (')]")
	private WebElementFacade actualSavedArticles;

	@FindBy(xpath = "//*[starts-with(@id, 'content1')]/li[1]//span[contains(text(),'catalyst')]")
	private WebElementFacade myAccProductHeading;

	@FindBy(xpath = "//*[starts-with(@id, 'content1')]/li[1]//span[contains(@class,'issue-item_date')]")
	private WebElementFacade myAccPubDate;

	@FindBy(xpath = "//*[starts-with(@id, 'content1')]/li[1]//div[contains(@class,'issue-item_authors')]//li")
	private WebElementFacade authors_MyAccSite;

	@FindBy(xpath = "//*[starts-with(@class, 'article_content_right')]//ul/li/span[@typeof]")
	private WebElementFacade authors_CatJLSite;

	@FindBy(xpath = "//div[@class='article_content_right']//a[contains(@href,'/toc/catalyst')]")
	private WebElementFacade pubDate_CatJLSite;

	@FindBy(xpath = "//*[starts-with(@class, 'article_content_right')]//div[contains(@class,'cat-body5')]/div[not(contains(@class,'doi')) and not(contains(@class,'pb'))]")
	private WebElementFacade citation_CatJLSite;

	@FindBy(xpath = "//*[starts-with(@id, 'content1')]/li[1]//div[@class='issue-item_citation']")
	private WebElementFacade citation_MyAccSite;

	@FindBy(xpath = "//*[starts-with(@id, 'content1')]/li[1]//div[starts-with(@class,'issue-item_description-value')]")
	private WebElementFacade descrip_MyAccSite;

	@FindBy(xpath = "//*[starts-with(@id, 'content1')]/li[1]//div[starts-with(@class,'issue-item_keywords-value')]/a[1]")
	private WebElementFacade keyword_MyAccSite;

	@FindBy(xpath = "//i[@class='icon-section-expand']")
	private WebElementFacade myAccKeyWordButton;

	@FindBy(xpath = "//span[contains(text(),'AutomationA')]")
	private WebElementFacade selectKeyword;

	@FindBy(xpath = "//*[starts-with(@class, 'custom-btn-selected')]")
	private WebElementFacade appliedFilter;

	@FindBy(xpath = "//*[starts-with(@class, 'custom-btn-selected')]//small")
	private WebElementFacade appliedFilterCount;

	@FindBy(xpath = "//*[starts-with(@id, 'content1')]/li[1]//i[@class='icon-delete']")
	private WebElementFacade deleteSavedArticle;

	@FindBy(xpath = "//*[starts-with(@id, 'content1')]/li[1]//i[@class='icon-edit']")
	private WebElementFacade editSavedArticle;

	@FindBy(xpath = "//*[starts-with(@id, 'descriptionEdit') and not(contains(@id, 'Search'))]")
	private WebElementFacade inputDescription;

	@FindBy(xpath = "//*[starts-with(@id, 'keywordsEdit') and not(contains(@id, 'Search'))]")
	private WebElementFacade inputKeyword;

	@FindBy(xpath = "//*[starts-with(@id, 'editSave') and not(contains(@id, 'Search'))]")
	private WebElementFacade editSaveBtn;

	@FindBy(xpath = "//*[starts-with(@id, 'content1')]/li[1]//div[@class='issue-item_description-value breakWord']")
	private WebElementFacade myAccDescripValue;

	@FindBy(xpath = "//*[starts-with(@id, 'content1')]/li[1]//div[@class='issue-item_keywords-value breakWord']/a[1]")
	private WebElementFacade myAccKeywordValue;

	@FindBy(xpath = "//span[contains(text(),'Prev')]")
	private WebElementFacade paginationPreviousLink;

	@FindBy(xpath = "//span[contains(text(),'Next')]")
	private WebElementFacade paginationNextLink;

	@FindBy(xpath = "//button[contains(@class,'paging_next paging_disabled')]")
	private WebElementFacade paginationNextLinkDisabled;

	@FindBy(xpath = "//button[contains(@class,'paging_prev paging_disabled')]")
	private WebElementFacade paginationPrevLinkDisabled;

	@FindBy(xpath = "//p[contains(text(),'As a benefit of creating an account')]")
	private WebElementFacade noSavedItems_Msg;

	@Step("User save articles on catalyst site")
	public void saveArticles(DataTable dt) throws Throwable {
		kMap = CucumberUtils.convert(dt);
		launchCatalystSite();
		commonFunc.mousehover(CreateAccountLink);
		commonFunc.clickElement(CreateAccountInnerLink);
		registerUser(kMap);
		waitForPrepopulation(SuccessText);
		saveArticle(kMap);
		LOGGER.info("================================================");
		LOGGER.info("Total Article Added on My Account" + addedArticle);
		LOGGER.info("================================================");
	}

	/*
	 * Save Article function by default adds article on my account page & checks max
	 * of 25 doi's (some may throw 404) and out of these it adds article as per user
	 * input.
	 * 
	 * @param - Pass Map with Saved Article Description and Keywords
	 * 
	 * @Map - Also includes addArticle - if its value is passed as 5 from excel then
	 * 5 articles will be added on my account section.
	 */

	public void saveArticle(Map<String, String> map) throws Throwable {

		List<String> myList = new ArrayList<String>(
				Arrays.asList(jsonUtils.getFromJSON(doiFile, "catalystDoi").split(",")));
		LOGGER.info("Checking Number Of DOI's ---->>>" + myList.size());
		int length = myList.size(); // Size of DOI's as per above JSON.
		while (length != 0) {
			String doi = jsonUtils.getFromJSON(doiFile, "catalystDoi[" + (length - 1) + "]");
			commonFunc.Launch_URL(catalystBaseUrl + doi);
			commonFunc.waitForLoadPage();
			if (!pageNotFound.isCurrentlyVisible()) {
				setArticle_Details(); // set actual article details as per CatJL Site
				addArticle_ViaSaveLink(map);
				seeYourSavedItems();
				setSavedArticle_Details(); // set details as per my Acc Site
				if (Integer.parseInt(map.get("AddArticle")) == (Integer) tUtil.getFromSession("Added Article")) {
					break;
				}
			}
			length--;
		}

	}

	public void setArticle_Details() throws InterruptedException {
		waitForPrepopulation(articleTitle_CJSite);
		tUtil.putToSession("title_CatJL", articleTitle_CJSite.getText());
		tUtil.putToSession("Authors_CatJL", authors_CatJLSite.getText());
		tUtil.putToSession("pubDate_CatJL", pubDate_CatJLSite.getText().toLowerCase()
				.replaceAll("\\A[^abc][^abc][^abc].\\s\\d\\s[^abc][^abc].\\s\\d\\s.\\s", ""));
		tUtil.putToSession("citation_CatJL", citation_CatJLSite.waitUntilVisible().getText().replaceAll("\\d", ""));
	}

	public void setSavedArticle_Details() {
		setTitle();
		setAuthors();
		setProduct();
		setPubDate();
		setCitation();
		setDescription();
		setKeywords();
		LOGGER.info("^^^^^^^^^^^^^^^^^^^^^^TEST ENDED^^^^^^^^^^^^^^^^^^^^^^^^^^^");
	}

	private void setTitle() {
		LOGGER.info("----------------CatalystJL Site Article --> Title-----------");
		LOGGER.info((String) tUtil.getFromSession("title_CatJL"));
		LOGGER.info("----------------MyAccount Site Article --> Title------------");
		LOGGER.info(articleTitle_MyAccSite.getText());
		LOGGER.info("------------------------------------------------------------");
		myAcc = articleTitle_MyAccSite.getText();
		catJl = (String) tUtil.getFromSession("title_CatJL");
		setCount++;
		String sessionVar = "Title" + setCount;
		tUtil.putToSession(sessionVar, myAcc.equals(catJl) ? true : false);

	}

	private void setAuthors() {
		LOGGER.info("----------------CatalystJL Site Article --> Author----------");
		LOGGER.info((String) tUtil.getFromSession("Authors_CatJL"));
		LOGGER.info("----------------MyAccount Site Article --> Author-----------");
		LOGGER.info(authors_MyAccSite.waitUntilVisible().getText().replaceAll("[&].*", ""));
		LOGGER.info("------------------------------------------------------------");
		myAcc = authors_MyAccSite.waitUntilVisible().getText().replaceAll("[&].*", "");
		catJl = (String) tUtil.getFromSession("Authors_CatJL");
		String sessionVar = "Authors" + setCount;
		tUtil.putToSession(sessionVar, catJl.contains(myAcc) ? true : false);
	}

	private void setProduct() {
		LOGGER.info("----------------MyAccount Site Article --> Product---------");
		LOGGER.info(myAccProductHeading.getText());
		LOGGER.info("------------------------------------------------------------");
		tUtil.putToSession("Product", myAccProductHeading.getText());
	}

	private void setPubDate() {
		LOGGER.info("----------------CatalystJL Site Article --> Pub Date--------");
		LOGGER.info((String) tUtil.getFromSession("pubDate_CatJL"));
		LOGGER.info("----------------MyAccount Site Article --> Pub Date---------");
		LOGGER.info(myAccPubDate.waitUntilVisible().getText().toLowerCase());
		LOGGER.info("------------------------------------------------------------");
		myAcc = myAccPubDate.waitUntilVisible().getText().toLowerCase();
		catJl = (String) tUtil.getFromSession("pubDate_CatJL");
		String sessionVar = "PubDate" + setCount;
		tUtil.putToSession(sessionVar, catJl.contains(myAcc) ? true : false);
	}

	private void setCitation() {
		LOGGER.info("----------------CatalystJL Site Article --> CITATION--------");
		LOGGER.info((String) tUtil.getFromSession("citation_CatJL"));
		LOGGER.info("----------------MyAccount Site Article --> CITATION---------");
		LOGGER.info(citation_MyAccSite.waitUntilVisible().getText().replaceAll("\\d*-\\d*-\\d*;\\s\\d\\d", ""));
		LOGGER.info("------------------------------------------------------------");
		myAcc = citation_MyAccSite.getText().replaceAll("\\d*-\\d*-\\d*;\\s\\d\\d", "");
		catJl = (String) tUtil.getFromSession("citation_CatJL");
		String sessionVar = "Citation" + setCount;
		tUtil.putToSession(sessionVar, catJl.contains(myAcc) ? true : false);
	}

	private void setDescription() {
		LOGGER.info("----------------CatalystJL Site Article --> Description--------");
		LOGGER.info((String) tUtil.getFromSession("descrip_CatJL"));
		LOGGER.info("----------------MyAccount Site Article --> Description---------");
		LOGGER.info(descrip_MyAccSite.waitUntilVisible().getText());
		LOGGER.info("------------------------------------------------------------");
		myAcc = descrip_MyAccSite.waitUntilVisible().getText();
		catJl = (String) tUtil.getFromSession("descrip_CatJL");
		String sessionVar = "Description" + setCount;
		tUtil.putToSession(sessionVar, catJl.contains(myAcc) ? true : false);
	}

	private void setKeywords() {
		LOGGER.info("----------------CatalystJL Site Article --> Keywords--------");
		LOGGER.info((String) tUtil.getFromSession("keywords_CatJL"));
		LOGGER.info("----------------MyAccount Site Article --> Keywords---------");
		LOGGER.info(keyword_MyAccSite.waitUntilVisible().getText().replace(",", ""));
		LOGGER.info("------------------------------------------------------------");
		myAcc = keyword_MyAccSite.getText().replace(",", "");
		catJl = (String) tUtil.getFromSession("keywords_CatJL");
		String sessionVar = "Keywords" + setCount;
		tUtil.putToSession(sessionVar, catJl.contains(myAcc) ? true : false);
	}

	private void addArticle_ViaSaveLink(Map<String, String> arMap) {
		LOGGER.info("=======================TEST STARTED FOR SAVED ARTICLES===========================");
		LOGGER.info("*********TEST Will be Running for : - " + arMap.get("AddArticle") + "--->" + "Articles****");
		commonFunc.waitForLoadPage();
		commonFunc.clickElement(loggedIn_SaveLink);
		saveArticleHeading_CJSite.waitUntilPresent();
		savedArticleDescrip_CJSite.waitUntilClickable().type(arMap.get("ArticleDescrip"));
		tUtil.putToSession("descrip_CatJL", arMap.get("ArticleDescrip"));
		String keywords = arMap.get("KeywordA") + ";" + arMap.get("KeywordB") + ";" + arMap.get("KeywordC");
		savedArticleKeyword_CJSite.waitUntilClickable().type(keywords);
		tUtil.putToSession("keywords_CatJL", keywords);
		saveButton.waitUntilClickable().click();
		saveArticleSuccessMsg.waitUntilVisible();
		tUtil.putToSession("Added Article", addedArticle += 1);
	}

	public void waitForInputFieldsToBe_NotBlank(WebElementFacade element, String value) throws InterruptedException {
		int timeOutMin = 12;
		do {
			LOGGER.info("Waiting for input field to be filled and not be blank : - " + element);
			TimeUnit.SECONDS.sleep(2);
			element.waitUntilVisible().type(value);
			String fieldVal = element.getValue();
			if (fieldVal != null) {
				break;
			}
			timeOutMin--;
		} while (timeOutMin != 0);
	}

	public void launchCatalystSite() throws Throwable {
		commonFunc.Launch_URL(catalystBaseUrl);
		commonFunc.waitForLoadPage();
	}

	public void registerUser(Map<String, String> map) throws Throwable {
		if (accFormDisplay.isCurrentlyVisible()) {
			submitAccordionForm(map);
		} else {
			submitCreateAccountForm(map);
		}
	}

	private void submitAccordionForm(Map<String, String> map) throws Throwable {
		waitForInputFieldsToBe_NotBlank(enterFirstName, map.get("firstName"));
		waitForInputFieldsToBe_NotBlank(enterLastName, map.get("lastName"));
		selectCountry.waitUntilVisible().selectByValue(map.get("country"));
		commonFunc.clickElement(continueButton1);
		selectSuffix.waitUntilVisible().selectByValue(map.get("suffix"));
		crAcc.selectPrimarySpeciality(map.get("suffix"), map.get("specialty"));
		selectRole.selectByValue(map.get("role"));
		selectPlace.waitUntilVisible().selectByValue(map.get("place"));
		enterOrg.waitUntilVisible().type(map.get("org"));
		commonFunc.clickElement(continueButton2);
		waitForInputFieldsToBe_NotBlank(enterEmail, registerEmailId(map));
		waitForInputFieldsToBe_NotBlank(enterPass, map.get("Pass"));
		commonFunc.clickElement(createAccountBtn);
	}

	private void submitCreateAccountForm(Map<String, String> map) throws Throwable {
		waitForInputFieldsToBe_NotBlank(enterEmail, registerEmailId(map));
		waitForInputFieldsToBe_NotBlank(enterPass, map.get("Pass"));
		enterFirstName.waitUntilVisible().type(map.get("firstName"));
		enterLastName.waitUntilVisible().type(map.get("lastName"));
		selectSuffix.waitUntilVisible().selectByValue(map.get("suffix"));
		crAcc.selectPrimarySpeciality(map.get("suffix"), map.get("specialty"));
		selectRole.selectByValue(map.get("role"));
		selectPlace.waitUntilVisible().selectByValue(map.get("place"));
		enterOrg.waitUntilVisible().type(map.get("org"));
		selectCountry.waitUntilVisible().selectByValue(map.get("country"));
		commonFunc.clickElement(Button);
	}

	private String registerEmailId(Map<String, String> map) {
		String email = tUtil.AppendTimestamp(map.get("email"));
		LOGGER.info("============User Register Emails=================");
		LOGGER.info(email);
		LOGGER.info("=================================================");
		tUtil.putToSession("Email", email);
		return email;
	}

	public void waitForPrepopulation(WebElementFacade element) throws InterruptedException {

		int timeOutMin = 12;
		do {
			LOGGER.info("Waiting for Value to be prepopulated in Element : - " + element);
			TimeUnit.SECONDS.sleep(2);
			String fieldVal = element.getValue();
			if (fieldVal != null) {
				break;
			}
			timeOutMin--;
		} while (timeOutMin != 0);
	}

	@Step("User validate Saved articles")
	public boolean validateSavedArticles() {
		LOGGER.info("================Actual Number Of Saved Articles===================");
		LOGGER.info("Added on My Account -->" + actualSavedArticles.getText());
		LOGGER.info("==================================================================");
		LOGGER.info("================Expected Number Of Saved Articles===================");
		LOGGER.info("Expected -->" + String.valueOf(tUtil.getFromSession("Added Article")));
		LOGGER.info("==================================================================");
		String actualArticles = actualSavedArticles.getText();
		String expectedArticles = String.valueOf(tUtil.getFromSession("Added Article"));
		return (actualArticles.contains(expectedArticles)) ? true : false;
	}

	@Step("User validate saved articles fields")
	public boolean validateSavedArticleFields() {
		LOGGER.info("================Fields Validation Result======================");
		checkFields();
		boolean product = ((String) tUtil.getFromSession("Product")).equals("CATALYST") ? true : false;
		LOGGER.info("Validation for Product : - " + product);
		LOGGER.info("===============================================================");
		return (fields && product) ? true : false;
	}

	private void seeYourSavedItems() {
		seeYourSavedItemsLink.waitUntilClickable().click();
		commonFunc.waitForLoadPage();
		commonFunc.clickElement(myAccArticlesSearchesLink);
		myAccLibraryHeading.waitUntilVisible();
	}

	@Step("User launch artice from my account site")
	public boolean launchArticleMyAcc() {
		LOGGER.info("**************ARTICLE NAVIGATION***************");
		LOGGER.info("Expected - Article Launched from My Account --> ");
		LOGGER.info(articleTitle_MyAccSite.waitUntilVisible().getText());
		String expected = articleTitle_MyAccSite.waitUntilVisible().getText();
		articleTitle_MyAccSite.waitUntilClickable().click();
		commonFunc.waitForLoadPage();
		LOGGER.info("Actual Article Appearing on CatalystJL Site --> ");
		LOGGER.info(articleTitle_CJSite.waitUntilVisible().getText());
		LOGGER.info("***********************************************");
		String actual = articleTitle_CJSite.waitUntilVisible().getText();
		return expected.equals(actual) ? true : false;
	}

	private void checkFields() {
		while (setCount != 0) {
			LOGGER.info("----------------------------------------");
			String sessionVar = "Title" + setCount;
			boolean title = (boolean) tUtil.getFromSession(sessionVar);
			LOGGER.info("Validation for Title : - " + title);
			sessionVar = "Authors" + setCount;
			boolean authors = (boolean) tUtil.getFromSession(sessionVar);
			LOGGER.info("Validation for Authors : - " + authors);
			sessionVar = "PubDate" + setCount;
			boolean pubdate = (boolean) tUtil.getFromSession(sessionVar);
			LOGGER.info("Validation for Pub Date : - " + pubdate);
			sessionVar = "Citation" + setCount;
			boolean citation = (boolean) tUtil.getFromSession(sessionVar);
			LOGGER.info("Validation for Citation : - " + citation);
			sessionVar = "Description" + setCount;
			boolean descrip = (boolean) tUtil.getFromSession(sessionVar);
			LOGGER.info("Validation for Description : - " + descrip);
			sessionVar = "Keywords" + setCount;
			boolean keyword = (boolean) tUtil.getFromSession(sessionVar);
			LOGGER.info("Validation for Keywords : - " + keyword);
			LOGGER.info("----------------------------------------");
			fields = title && authors && pubdate && citation && descrip && keyword ? true : false;
			if (fields == false) {
				break;
			}
			setCount--;
		}
	}

	@Step("User apply keyword filter from my account")
	public void applyKeywordFilter() throws InterruptedException {
		commonFunc.waitForLoadPage();
		myAccKeyWordButton.waitUntilClickable().click();
		commonFunc.clickElement(selectKeyword);
		commonFunc.waitForLoadPage();
		waitForPrepopulation(myAccKeyWordButton);
		myAccKeyWordButton.waitUntilClickable().click();
		tUtil.putToSession("AppliedFilter", appliedFilter.waitUntilPresent().isCurrentlyVisible());
		tUtil.putToSession("FilterCount", Integer.parseInt(appliedFilterCount.getText()));
	}

	@Step("User delete saved article")
	public void deleteSavedArticle() throws InterruptedException {
		waitForPrepopulation(deleteSavedArticle);
		commonFunc.clickElement(deleteSavedArticle);
		commonFunc.waitForLoadPage();
		waitForPrepopulation(myAccKeyWordButton);
		myAccKeyWordButton.waitUntilClickable().click();
		tUtil.putToSession("AppliedFilterUpdated", appliedFilter.waitUntilPresent().isCurrentlyVisible());
		tUtil.putToSession("FilterCountUpdated", Integer.parseInt(appliedFilterCount.waitUntilPresent().getText()));
	}

	@Step("User validate pre-selected filter and updated count")
	public boolean preSelectedFilter() {
		boolean appliedFilter = (boolean) tUtil.getFromSession("AppliedFilter");
		boolean appliedFilterUpdated = (boolean) tUtil.getFromSession("AppliedFilterUpdated");
		LOGGER.info("==============FILTER STATUS==================");
		LOGGER.info("==============FILTER APPLIED=======>>>>>==========>>" + appliedFilter);
		LOGGER.info("==============FILTER POST Deletion - PRE SELECTED====>>>>===>>" + appliedFilterUpdated);
		int filterCount = (Integer) tUtil.getFromSession("FilterCount");
		int filterCountUpdated = (Integer) tUtil.getFromSession("FilterCountUpdated");
		LOGGER.info("==============COUNT STATUS==================");
		LOGGER.info("==============WHEN FILTER APPLIED=>>>>===>>=>>====>>" + filterCount);
		LOGGER.info("==============WHEN FILTER ITEM DELETED=>>>>>======>>" + filterCountUpdated);
		boolean filterStatus = (appliedFilter && appliedFilterUpdated) ? true : false;
		boolean countStatus = (filterCount > filterCountUpdated) ? true : false;
		return filterStatus && countStatus ? true : false;
	}

	@Step("User enters long description and keyword")
	public void enterLongDescriptionKeyword(String longDescription, String longKeyword) {
		commonFunc.clickElement(editSavedArticle);
		submitKeyword_Description(longDescription, longKeyword);
		tUtil.putToSession("UpdatedDescription", myAccDescripValue.waitUntilPresent().getText());
		tUtil.putToSession("UpdatedKeyword", myAccKeywordValue.waitUntilPresent().getText());
	}

	private void submitKeyword_Description(String description, String keyword) {
		inputDescription.waitUntilClickable().clear();
		inputDescription.type(description);
		inputKeyword.waitUntilClickable().clear();
		inputKeyword.type(keyword);
		editSaveBtn.waitUntilClickable().click();
		commonFunc.waitForLoadPage();
	}

	@Step("User validate if application allows long description & keyword values or not")
	public boolean validateLongDescrpKeywordInput(String expLimitDescrip, String expLimitKeywords) {
		LOGGER.info("==========LONG DESCRIPTION VALIDATION==================");
		LOGGER.info("==========ACTUAL DESCRIPTION============");
		LOGGER.info((String) tUtil.getFromSession("UpdatedDescription"));
		LOGGER.info("---------------------------------------");
		LOGGER.info("==========EXPECTED DESCRIPTION===========");
		LOGGER.info(expLimitDescrip);
		LOGGER.info("-----------------------------------------");
		LOGGER.info("==========LONG KEYWORD VALIDATION==================");
		LOGGER.info("==========ACTUAL KEYWORD============");
		LOGGER.info((String) tUtil.getFromSession("UpdatedKeyword"));
		LOGGER.info("---------------------------------------");
		LOGGER.info("==========EXPECTED KEYWORD===========");
		LOGGER.info(expLimitKeywords);
		LOGGER.info("-----------------------------------------");
		boolean descrip = (expLimitDescrip.equals(tUtil.getFromSession("UpdatedDescription"))) ? true : false;
		boolean keyword = (expLimitKeywords.equals(tUtil.getFromSession("UpdatedKeyword"))) ? true : false;
		return (descrip && keyword) ? true : false;
	}

	@Step("User update existing keyword or description")
	public void updateExistingKeyword_Descrip(String keyword, String descrip) {
		tUtil.putToSession("OriginalDescription", myAccDescripValue.getText());
		tUtil.putToSession("OriginaldKeyword", myAccKeywordValue.getText());
		LOGGER.info("============ORIGINAL VALUES=============");
		LOGGER.info("Description --> " + " " + myAccDescripValue.getText());
		LOGGER.info("Keyword --> " + " " + myAccKeywordValue.getText());
		LOGGER.info("========================================");
		commonFunc.clickElement(editSavedArticle);
		submitKeyword_Description(descrip, keyword);
		tUtil.putToSession("UpdatedDescription", myAccDescripValue.getText());
		tUtil.putToSession("UpdatedKeyword", myAccKeywordValue.getText());
		LOGGER.info("============VALUES AFTER UPDATE==============");
		LOGGER.info("Description --> " + " " + myAccDescripValue.getText());
		LOGGER.info("Keyword --> " + " " + myAccKeywordValue.getText());
		LOGGER.info("========================================");
	}

	@Step("User validate if existing description or keywords can be updated")
	public boolean validateUpdateExistingKeyword_Descrip() {
		boolean description = tUtil.getFromSession("UpdatedDescription").equals("testDescription") ? true : false;
		boolean keyword = tUtil.getFromSession("UpdatedKeyword").equals("testKey") ? true : false;
		return description && keyword ? true : false;
	}

	@Step("User validate pagination link at the bottom of the page")
	public boolean validatePaginationLink() {
		boolean prevLink = paginationPreviousLink.isCurrentlyVisible();
		boolean nextLink = paginationNextLink.isCurrentlyVisible();
		LOGGER.info("==========PAGINATION ITEMS FOR MORE THAN 10 SAVED ITEMS=========");
		LOGGER.info("Pagination Previous Button Present :-" + prevLink);
		LOGGER.info("Pagination Next Button Present :-" + nextLink);
		commonFunc.mousehover(paginationNextLink);
		paginationNextLink.waitUntilClickable().click();
		boolean nextLinkDisabled = paginationNextLinkDisabled.isCurrentlyVisible();
		paginationPreviousLink.waitUntilClickable().click();
		boolean prevLinkDisabled = paginationPrevLinkDisabled.isCurrentlyVisible();
		LOGGER.info("Pagination Next Button disabled with user on Last Page :-" + " " + prevLink);
		LOGGER.info("Pagination Prev Button disabled when user on First Page :- Present :-" + " " + nextLink);
		LOGGER.info("=================================================");
		return (prevLink && nextLink && nextLinkDisabled && prevLinkDisabled) ? true : false;
	}

	@Step("User validate pagination link for less articles")
	public boolean validatePaginationLink_LessArticles() {
		commonFunc.clickElement(deleteSavedArticle);
		commonFunc.waitForLoadPage();
		boolean nextLinkDisabled = paginationNextLinkDisabled.isCurrentlyVisible();
		boolean prevLinkDisabled = paginationPrevLinkDisabled.isCurrentlyVisible();
		LOGGER.info("============PAGINATION ITEMS FOR LESS THAN 11 SAVED ITEMS=======");
		LOGGER.info("Pagination Previous Button Disabled :-" + prevLinkDisabled);
		LOGGER.info("Pagination Next Button Disabled :-" + nextLinkDisabled);
		LOGGER.info("=================================================");
		return (nextLinkDisabled && prevLinkDisabled) ? true : false;
	}

	@Step("User validate if saved article feature working with auth token SSO access")
	public boolean validateSavedItems_AuthToken() throws Throwable {
		commonFunc.Launch_URL(myAccBaseUrl.replace("/signin", saved));
		commonFunc.waitForLoadPage();
		LOGGER.info("===============AUTH TOKEN ACCESS===================");
		LOGGER.info("Saved Section Access -->" + noSavedItems_Msg.waitUntilVisible().isCurrentlyVisible());
		LOGGER.info("===================================================");
		boolean noSavedItemsMsg = noSavedItems_Msg.waitUntilVisible().isCurrentlyVisible();
		return (noSavedItemsMsg) ? true : false;
	}
}
