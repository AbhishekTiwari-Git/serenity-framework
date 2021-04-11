package ucc.nejm.mcadmin.pages.ui;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import ucc.utils.CucumberUtils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.cucumber.datatable.DataTable;

public class MCAdminIssuesPage extends PageObject {

	private static final Logger LOGGER = LoggerFactory.getLogger(MCAdminIssuesPage.class);

	@FindBy(xpath = "//div //p[contains(text(),'Issues')]")
	WebElementFacade issuesWidget;

	@FindBy(xpath = " //p[contains(text(),\"of\")]")
	WebElementFacade records;

	@FindBy(xpath = "//div //h2[contains(text(),'Issues')]")
	WebElementFacade issuesTableName;

	@FindBy(xpath = "(//div //label[contains(text(),'Select a date')])[2]")
	WebElementFacade selectDate;

	@FindBy(xpath = "//input[@name=\"operator-issues-date\"]")
	WebElementFacade currentDate;

	@FindBy(xpath = "(//span[text()='View This Table'])[2]")
	WebElementFacade viewThisTable;

	@FindBy(xpath = "(//span[@class=\"MuiIconButton-label\"])[2]")
	WebElementFacade calenderIcon;

	@FindBy(xpath = "(//button[@type=\"button\"])[4]")
	WebElementFacade selectCalender;

	@FindBy(xpath = "((//div //button[@type=\"button\"])//span //p)[5]")
	WebElementFacade choseDate;

	@FindBy(xpath = "(//button[@type=\"button\"])[5]")
	WebElementFacade viewThisTableButton;

	@FindBy(css = "div#mui-component-select-articles_publication_method")
	WebElementFacade issuesTableWidget;

	@FindBy(id = "mui-component-select-journal")
	WebElementFacade journalValue;

	@FindBy(id = "mui-component-select-Content")
	WebElementFacade contentValue;

	@FindBy(xpath = "//div[contains(text(),\"Issues\")]")
	WebElementFacade publication_method;

	@FindBy(id = "mui-component-select-Title")
	WebElementFacade titleValue;

	@FindBy(id = "mui-component-select-Author")
	WebElementFacade authorValue;

	@FindBy(id = "mui-component-select-AuthorUpdateDT")
	WebElementFacade authorUpdatedValue;

	@FindBy(id = "mui-component-select-Article_Type")
	WebElementFacade articleTypeValue;

	@FindBy(xpath = "//select[@aria-label=\"rows per page\"]")
	WebElementFacade rowsperchange;

	@FindBy(xpath = "//option[contains(text(),\"5\")][1]")
	WebElementFacade fiveRows;

	@FindBy(xpath = "//option[contains(text(),\"10\")]")
	WebElementFacade tenRows;

	@FindBy(xpath = "//option[contains(text(),\"25\")]")
	WebElementFacade twentyFiveRows;

	@FindBy(xpath = "//option[contains(text(),\"All\")]")
	WebElementFacade allrows;

	@FindBy(xpath = "//button[@aria-label=\"next page\"]")
	WebElementFacade rightArrow;

	@FindBy(xpath = "//button[@aria-label=\"first page\"]")
	WebElementFacade leftMostArrow;

	@FindBy(xpath = "//button[@aria-label=\"last page\"]")
	WebElementFacade rightMostArrow;

	@FindBy(xpath = "//input[@aria-label=\"search\"]")
	WebElementFacade searchField;

	@FindBy(xpath = "//button[@aria-label=\"previous page\"]")
	WebElementFacade leftArrow;

	public static Map<String, String> kMap_expected;
	public static List<WebElementFacade> titleElements;
	public static List<String> list_titles = new ArrayList<String>();
	static String count;
	static String[] value;
	static String recordCount;
	static String count1;
	static String[] value1;
	static String recordCount1;
	static int number;
	static int number1;
	static String recordsNumber;

	@Step("Verify all the content displayed on Issues widget")
	public boolean verifyIssuesWidgetContent(String content) {

		content = StringUtils.remove(content, "(");
		content = StringUtils.remove(content, ")");
		switch (content) {
		case "Issues label":
			issuesWidget.waitUntilVisible().isDisplayed();
			LOGGER.info("Issues label is displayed");
			break;
		case "Issues table name":
			issuesTableName.waitUntilVisible().isDisplayed();
			LOGGER.info("Issues table name is displayed:");
			break;
		case "Select a date":
			selectDate.waitUntilVisible().isDisplayed();
			LOGGER.info("Select a date is displayed:");
			break;
		case "Current date":
			currentDate.waitUntilVisible().isDisplayed();
			LOGGER.info("Current date is displayed:");
			break;
		case "Calendar icon":
			calenderIcon.waitUntilVisible().isDisplayed();
			LOGGER.info("Calender icon is displayed:");
			break;
		case "VIEW THIS TABLE":
			viewThisTable.waitUntilVisible().isDisplayed();
			LOGGER.info("VIEW THIS TABLE is displayed:");
			break;
		default:
			LOGGER.info("Please check the content you are trying to verify");
			break;
		}
		return true;

	}

	@Step("Select view this table button")
	public void selectViewTableButton() {
		viewThisTableButton.waitUntilPresent().click();
	}

	@Step("Verify headings and their values in Issues Widget table")
	public boolean verifyColumnsIssuesWidget(DataTable dataTable) {
		kMap_expected = CucumberUtils.convert(dataTable);
		LOGGER.info("Expected:" + kMap_expected.toString());
		Map<String, String> kMap_actual = new HashMap<String, String>();
		kMap_actual.put("Journal", journalValue.waitUntilVisible().getText());
		kMap_actual.put("Content", contentValue.waitUntilVisible().getText());
		kMap_actual.put("Publication Method", issuesTableWidget.waitUntilVisible().getText());
		LOGGER.info("Actual:" + kMap_actual.toString());
		return kMap_actual.equals(kMap_expected) ? true : false;
	}

	@Step("Select a date under issues widget from calender")
	public void selectDate() {
		selectCalender.waitUntilPresent().click();
		choseDate.waitUntilPresent().click();
	}

	@Step("Navigated to Issues Widget table")
	public boolean navigateIssuesPage() {

		return publication_method.waitUntilVisible().isDisplayed();

	}

	@Step("Verify Sub-headings and their values in Issues Widget table")
	public boolean verifySubHeadingsIssuesWidget(DataTable dataTable) {
		kMap_expected = CucumberUtils.convert(dataTable);
		LOGGER.info("Expected:" + kMap_expected.toString());
		Map<String, String> kMap_actual = new HashMap<String, String>();
		kMap_actual.put("Author", authorValue.waitUntilVisible().getText());
		kMap_actual.put("Title", titleValue.waitUntilVisible().getText());
		kMap_actual.put("Author Updated", authorUpdatedValue.waitUntilVisible().getText());
		kMap_actual.put("Article Type", articleTypeValue.waitUntilVisible().getText());
		LOGGER.info("Actual:" + kMap_actual.toString());
		return kMap_actual.equals(kMap_expected) ? true : false;
	}

	@Step("Select the dropdown for rows per page")
	public void selectDropdown() {
		 LOGGER.info("Waiting for Value to be prepopulated in Element : - "+ rowsperchange);
		 rowsperchange.waitUntilPresent().click();
	}

	@Step("Verify all the content displayed on Issues widget")
	public boolean verifyDropdownList(String content) {

		content = StringUtils.remove(content, "(");
		content = StringUtils.remove(content, ")");
		switch (content) {
		case "5":
			fiveRows.waitUntilVisible().isDisplayed();
			LOGGER.info("5 rows are displayed");
			break;
		case "10":
			tenRows.waitUntilVisible().isDisplayed();
			LOGGER.info("10 rows are displayed:");
			break;
		case "25":
			twentyFiveRows.waitUntilVisible().isDisplayed();
			LOGGER.info("25 rows are displayed:");
			break;
		case "All":
			allrows.waitUntilVisible().isDisplayed();
			LOGGER.info("All rows are displayed:");
			break;
		default:
			LOGGER.info("Please check the content you are trying to verify");
			break;
		}
		return true;

	}

	@Step("Select 5 options for 5 rows per page")
	public void selectFiveRows() {
		fiveRows.waitUntilPresent().click();
	}

	@Step("Verify 5 articles for 5 rows per page are present")
	public boolean verifyFiveRows(Integer rows) {
		titleElements = findAll(By.cssSelector("td:nth-child(3)"));
		for (WebElementFacade title : titleElements) {
			list_titles.add(title.getText());
		}
		return list_titles.size() == 5 ? true : false;
	}

	@Step("Select Right arrow on footer")
	public void selectRightArrow() {
		count = records.getText();
		value = count.split("-");
		recordCount = value[0];
		number = Integer.parseInt(recordCount);
		rightArrow.waitUntilPresent().click();
	}

	@Step("Verify next page results")
	public boolean verifyNextPage() {
		count1 = records.getText();
		value1 = count1.split("-");
		recordCount1 = value1[0];
		number1 = Integer.parseInt(recordCount1);
		return number1>number ? true : false;
	}

	@Step("Select left most arrow on footer")
	public void selectLeftMostArrow() {
		leftMostArrow.waitUntilPresent().click();
	}

	@Step("Verify first page results")
	public boolean verifyFirstPage() {
		count1 = records.getText();
		return count1.contains("1") ? true : false;
	}

	@Step("Select right most arrow on footer")
	public void selectRightMostArrow() {
		rightMostArrow.waitUntilPresent().click();
	}

	@Step("Verify last page results")
	public boolean verifyLastPage() {
		recordsNumber = searchField.getAttribute("placeholder").split(" ")[0];
		count1 = records.getText();
		return count1.contains(recordsNumber) ? true : false;
	}

	@Step("Select left arrow on footer")
	public void selectLeftArrow() {
		count = records.getText();
		value = count.split("-");
		recordCount = value[0];
		number = Integer.parseInt(recordCount);
		leftArrow.waitUntilPresent().click();
	}

	@Step("Verify previous page results")
	public boolean verifyPreviousPage() {
		count1 = records.getText();
		value1 = count1.split("-");
		recordCount1 = value1[0];
		number1 = Integer.parseInt(recordCount1);
		return number1<number ? true : false;
	}

	@Step("Verify first page is available")
	public boolean verifyLeftMostArrowDisabled() {
		return leftMostArrow.isDisabled();
	}
	
	@Step("Verify first page is available")
	public boolean verifyLeftArrowDisabled() {
		return leftArrow.isDisabled();
	}
}

