package ucc.cj.pages.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;

public class SearchFilterPage extends PageObject {
	
	CJSearchPage cjSearch = new CJSearchPage();

	@FindBy(css = "i.icon-gizmo-search")
	private WebElement searchHeader;

	@FindBy(xpath = "//div[@class='col-xs-12']//button[@class='icon-gizmo-search']")
	private WebElement searchButton;

	@FindBy(xpath = "//input[@placeholder='Enter keyword, author, title, or citation']")
	private WebElement searchBar;

	@FindBy(className = "jcf-select-text")
	private WebElement opsDrop;

	@FindBy(xpath = "//*[@id='pb-page-content']/div/main/div/div[2]/div[1]/div/div/div[1]/ul/li")
	private List<WebElement> searchResults;

	@FindBy(className = "issue-item_vol")
	private List<WebElement> WEDates;

	@FindBy(xpath = "//a[@title='Filter']")
	private WebElement filterButton;

	@FindBy(css = "div[data-db-target-for=custom-input_date]")
	private WebElement byDateDrop;

	@FindBy(xpath = "//*[@id='pb-page-content']/div/main/div/div[2]/div[1]/div/div/form/div[3]/div[4]/div/div[2]/div[2]/div[1]/span[1]/span[1]")
	private WebElement fromMonthSelect;

	@FindBy(xpath = "//*[@id='pb-page-content']/div/main/div/div[2]/div[1]/div/div/form/div[3]/div[4]/div/div[2]/div[2]/div[1]/span[2]/span[1]")
	private WebElement fromYearSelect;

	@FindBy(xpath = "//*[@id='pb-page-content']/div/main/div/div[2]/div[1]/div/div/form/div[3]/div[4]/div/div[2]/div[2]/div[1]/span[3]/span[1]")
	private WebElement toMonthSelect;

	@FindBy(xpath = "//*[@id='pb-page-content']/div/main/div/div[2]/div[1]/div/div/form/div[3]/div[4]/div/div[2]/div[2]/div[1]/span[4]/span[1]")
	private WebElement toYearSelect;

	@FindBy(css = "div[data-db-target-for=custom-input_contentType")
	private WebElement byContentDrop;

	@FindBy(css = "div[data-db-target-for=custom-input_topic]")
	private WebElement byTopicDrop;

	@FindBy(linkText = "Advanced Search")
	private WebElement advancedSearchButton;
	
	@FindBy(xpath = "//div[@id='slider-control-SI_dbt3GCYzIF72kjH']")
	private WebElement feedback;

	// clicks the search icon on the header
	public void clickSearch() throws InterruptedException {
		waitFor(ExpectedConditions.elementToBeClickable(searchHeader));
		searchHeader.click();
	}

	// types the given searchKey into the search bar, clicks the search ico on the search bar
	public void enterSearch(String searchKey) {
		searchBar.clear();
		searchBar.sendKeys(searchKey.trim());
		searchButton.click();
	}

	// clicks the dropdown of sort options, selects that which matches the given sortOption
	public void selectSortOption(String sortOption) {
		waitFor(ExpectedConditions.elementToBeClickable(opsDrop));
		opsDrop.click();

		List<WebElement> optns = getDriver().findElements(By.className("jcf-option"));

		for(WebElement we : optns) {
			if (we.getText().equals(sortOption)) {
				we.click();
				break;
			}
		}
	}

	/*
	 * asserts that the search results are sorted according to the sort option displayed on the dropdown 
	 * metrics mostly inaccessible on qa, would not access citation information to verify sorting by most cited
	 */
	public void searchResultsSorted() {		
		switch (opsDrop.getText()) {
		case "Oldest" : 
			Assert.assertTrue(this.sortedByOldest());
			break;
		case "Newest" : 
			Assert.assertTrue(this.sortedByNewest());
			break;
		/*case "Most Cited" : 
			Assert.assertTrue(this.sortedByMostCited());
			break;*/
		case "Most Relevant" : 
			Assert.assertTrue(this.sortedByMostRelevant());
			break;
		default : 
		}
	}

	// clicks the + opening the filter dropdown
	public void clickFilter() {
		waitFor(ExpectedConditions.visibilityOf(feedback));
		filterButton.click();
	}

	/* 
	 * clicks the By Date filter dropdown, selects that which matches the given dateOption
	 * if dateOption is not one of the 3 radio options, it follows the format 
	 * "From xxx xxxx To xxx xxxx" where xxx is the first 3 letters of a month in 
	 * captial camel case and xxxx is the year given in 4 digits 
	 */
	public void selectByDate(String dateOption) {
		waitFor(ExpectedConditions.elementToBeClickable(byDateDrop));
		byDateDrop.click();
      
		switch (dateOption) {
		case "Past 30 Days" : 
			
			getDriver().findElement(By.xpath("//span[contains(text(), 'Past 30 Days')]")).click();
			break;
		case "Past 6 Months" : 
			
			getDriver().findElement(By.xpath("//span[contains(text(), 'Past 6 Months')]")).click();
			break;
		case "Past Year" : 
			
			getDriver().findElement(By.xpath("//span[contains(text(), 'Past Year')]")).click();
			break;
		// custom range
		default : 
			getDriver().findElement(By.xpath("//a[contains(text(), 'Custom Range')]")).click();

			// from month val
			fromMonthSelect.click();
			getDriver().findElement(By.xpath("//div[contains(@class,'jcf-select-drop jcf-select-jcf "
					+ "jcf-select-custom-input_fromMonth')]//span[text()='" + dateOption.substring(5, 8) + "']")).click();

			// from year val
			fromYearSelect.click();
			getDriver().findElement(By.xpath("//div[contains(@class,'jcf-select-drop jcf-select-jcf "
					+ "jcf-select-custom-input_fromYear')]//span[text()='" + dateOption.substring(9, 13) + "']")).click();

			// from month val
			toMonthSelect.click();
			getDriver().findElement(By.xpath("//div[contains(@class,'jcf-select-drop jcf-select-jcf "
					+ "jcf-select-custom-input_toMonth')]//span[text()='" + dateOption.substring(17, 20) + "']")).click();

			// from year val
			toYearSelect.click();
			getDriver().findElement(By.xpath("//div[contains(@class,'jcf-select-drop jcf-select-jcf "
					+ "jcf-select-custom-input_toYear')]//span[text()='" + dateOption.substring(21) + "']")).click();

			getDriver().findElement(By.linkText("Apply")).click();
			waitFor(ExpectedConditions.stalenessOf(WEDates.get(0)));
		}
	}

	/*
	 *  asserts that the search results were all published within the 
	 *  range according to the date displayed on the dropdown 
	 */
	public void verifyResultsByDate() {

		List<int[]> dates = this.toDates();
		String dateOption = byDateDrop.getText();
		int[] from, to;
		boolean between = true;
		LocalDate today = LocalDate.now();
		LocalDate fromLD;
		
		switch (dateOption) {
		case "Past 30 Days" : 
			to = new int[] {today.getMonthValue(), 0, today.getDayOfMonth(), today.getYear()};
			fromLD = today.minusDays(30);
			from = new int[] {fromLD.getMonthValue(), 0, fromLD.getDayOfMonth(), fromLD.getYear()};
			break;
		case "Past 6 Months" : 
			to = new int[] {today.getMonthValue(), 0, today.getDayOfMonth(), today.getYear()};
			fromLD = today.minusMonths(6);
			from = new int[] {fromLD.getMonthValue(), 0, fromLD.getDayOfMonth(), fromLD.getYear()};
			break;
		case "Past Year" : 
			to = new int[] {today.getMonthValue(), 0, today.getDayOfMonth(), today.getYear()};
			fromLD = today.minusYears(1);
			from = new int[] {fromLD.getMonthValue(), 0, fromLD.getDayOfMonth(), fromLD.getYear()};
			break;
		// custom range
		default : 
			from = this.toDate(dateOption.substring(0, dateOption.indexOf("-")).split(" "));
			to = this.toDate(dateOption.substring(dateOption.indexOf("-")).split(" "));
		}
		
		for (int[] date : dates) {
			if ((this.compareDates(date, from) == -1) || (this.compareDates(date, to) == 1)) {
				between = false;
			}
		}

		Assert.assertTrue(between);

	}

	// clicks the By Content Type filter dropdown, selects that which matches the given contentType
	public void selectByContentType(String contentType) {
		waitFor(ExpectedConditions.elementToBeClickable(byContentDrop));
		byContentDrop.click();
		WebElement content = getDriver().findElement(By.xpath("//span[contains(text(), '" + contentType + "')]"));
		waitFor(ExpectedConditions.elementToBeClickable(content));
		content.click();
	}

	// asserts that the search results are all valid according to that displayed on the dropdown
	public void verifyResultsByContentType() {
		String contentType = byContentDrop.getText();
		boolean correctContentType = true;

		for (WebElement we : searchResults) {
			if (!we.getText().startsWith(contentType.toUpperCase())) {
				correctContentType = false;
			}
		}

		Assert.assertTrue(correctContentType);
	}

	// clicks the By Topic filter dropdown, selects that which matches the given topic
	public void selectByTopic(String topic) {
		waitFor(ExpectedConditions.elementToBeClickable(byTopicDrop));
		byTopicDrop.click();
		WebElement topicElem = getDriver().findElement(By.xpath("//span[contains(text(), '" + topic + "')]"));
		waitFor(ExpectedConditions.elementToBeClickable(topicElem));
		topicElem.click();
	}

	// asserts that the search results are relevant according to the topic displayed on the dropdown 
	public boolean searchResultsRelevantToTopic() {
		String topic = byTopicDrop.getText();
		List<String> searchResultsStrings = new ArrayList<String>();

		for (WebElement we : searchResults) {
			searchResultsStrings.add(we.getText());
		}

		double relevant = ((double)cjSearch.stringsContainSearch(topic, searchResultsStrings) / 
				(double)searchResultsStrings.size());

	//	Assert.assertTrue(relevant > 0.65);
		
		return relevant > 0.65;
	}

	// asserts that the search results match the article heading from the search bar
	public void searchResultsRelevantToArticleHeading() {
		String articleHeading = searchBar.getText();
		Assert.assertTrue(searchResults.get(0).getText().contains(articleHeading));
	}

	// clicks the Advanced Search link from the search heading
	public void clickAdvancedSearch() {
		waitFor(ExpectedConditions.elementToBeClickable(advancedSearchButton));
		advancedSearchButton.click();
	}
	
	
	// helper methods 


	/* 
	 * takes the text of every search result, parses through and returns a corresponding
	 * list of the publication dates in {month1, month2, dayOfMonth, year} format as arrays
	 */
	public List<int[]> toDates() {
		List<int[]> dates = new ArrayList<int[]>();

		for (WebElement we : WEDates) {

			int[] date = this.toDate(we.getText().split(" "));

			if (date[3] != 0) {
				dates.add(date);
			}
		}

		return dates;
	}
	
	/* 
	 * converts a String[] to a corresponding int[] representing a numerical date 
	 * dates may take any of the following formats: 
	 * - Jan 2020 : {1, 0, 0, 2020}
	 * - JANUARY—FEBRUARY 2020 : {1, 2, 0, 2020}
	 * - Jan 01, 2020 : {1, 0, 1, 2020}
	 * where indices 0 and 1 are months, 2 is day of month, and 3 is year
	 * 0 is a sentinel value indicating a date lacks an aspect
	 */
	public int[] toDate(String[] strs) {
		// {month1, month2, dayOfMonth, year}
		int[] date = new int[4];

		for (String s : strs) {
			s = s.toUpperCase();
			if (s.contains("JAN")) {
				if (date[0] == 0) {
					date[0] = 1;
				} else {
					date[1] = 1;
				}
			} else if (s.contains("FEB")) {
				if (date[0] == 0) {
					date[0] = 2;
				} else {
					date[1] = 2;
				}
			} else if (s.contains("MAR")) {
				if (date[0] == 0) {
					date[0] = 3;
				} else {
					date[1] = 3;
				}
			} else if (s.contains("APR")) {
				if (date[0] == 0) {
					date[0] = 4;
				} else {
					date[1] = 4;
				}
			} else if (s.contains("MAY")) {
				if (date[0] == 0) {
					date[0] = 5;
				} else {
					date[1] = 5;
				}
			} else if (s.contains("JUN")) {
				if (date[0] == 0) {
					date[0] = 6;
				} else {
					date[1] = 6;
				}
			} else if (s.contains("JUL")) {
				if (date[0] == 0) {
					date[0] = 7;
				} else {
					date[1] = 7;
				}
			} else if (s.contains("AUG")) {
				if (date[0] == 0) {
					date[0] = 8;
				} else {
					date[1] = 8;
				}
			} else if (s.contains("SEP")) {
				if (date[0] == 0) {
					date[0] = 9;
				} else {
					date[1] = 9;
				}
			} else if (s.contains("OCT")) {
				if (date[0] == 0) {
					date[0] = 10;
				} else {
					date[1] = 10;
				}
			} else if (s.contains("NOV")) {
				if (date[0] == 0) {
					date[0] = 11;
				} else {
					date[1] = 11;
				}
			} else if (s.contains("DEC")) {
				if (date[0] == 0) {
					date[0] = 12;
				} else {
					date[1] = 12;
				}
			} else if (s.length() == 3) {
				try {
					date[2] = Integer.parseInt(s.substring(0,2));
				} catch (NumberFormatException e) {
					continue;
				}
			} else if (s.length() == 4) {
				try {
					date[3] = Integer.parseInt(s);
				} catch (NumberFormatException e) {
					continue;
				}
			}  
		}

		return date;
	}
	
	// returns -1 if date1 < date2, 0 if dates are equal (or overlap), 1 if date1 > date2
	public int compareDates(int[] date1, int[] date2) {
		int compVal; 
		
		if (date1[1] != 0 && date1[2] == 0 && date2[1] == 0 && date2[2] != 0) { // MM0Y, M0DY
			if ((date1[3] == date2[3] && date1[0] == date2[0]) || 
					(date1[3] == date2[3] && date1[1] == date2[0])) {
				compVal = 0; 
			} else if (date1[3] < date2[3] || (date1[3] == date2[3] && date1[0] < date2[0] && date1[1] < date2[0])) {
				compVal = -1;
			} else {//if (date1[3] > date2[3] || (date1[3] == date2[3] && date1[0] > date2[0] && date1[1] > date2[0])) {
				compVal = 1;
			}
		} else if (date1[1] == 0 && date1[2] != 0 && date2[1] != 0 && date2[2] == 0) { // M0DY, MM0Y
			if ((date1[3] == date2[3] && date1[0] == date2[0]) || 
					(date1[3] == date2[3] && date1[0] == date2[1])) {
				compVal = 0; 
			} else if (date1[3] < date2[3] || (date1[3] == date2[3] && date1[0] < date2[0] && date1[0] < date2[1])) {
				compVal = -1;
			} else {//if (date1[3] > date2[3] || (date1[3] == date2[3] && date1[0] > date2[0] && date1[0] > date2[1])) {
				compVal = 1;
			}
		} else if (date1[1] != 0 && date1[2] == 0 && date2[1] == 0 && date2[2] == 0) { // MM0Y, M00Y
			if (date1[3] < date2[3] || (date1[3] == date2[3] && date1[0] < date2[0] && date1[1] < date2[0])) {
				compVal = -1;
			} else if (date1[3] > date2[3] || (date1[3] == date2[3] && date1[0] > date2[0] && date1[1] > date2[0])) {
				compVal = 1;
			} else {//if((date1[3] == date23] && date1[0] == date2[0]) || (date1[3] == date2[3] && date1[1] == date2[0])) {
				compVal = 0;
			}
		} else if (date1[1] == 0 && date1[2] != 0 && date2[1] == 0 && date2[2] == 0) { // M0DY, M00Y
			if (date1[3] < date2[3] || 
					(date1[3] == date2[3] && date1[0] < date2[0])) {
				compVal = -1;
			} else if (date1[3] > date2[3] || 
					(date1[3] == date2[3] && date1[0] > date2[0])) {
				compVal = 1;
			} else {
				compVal = 0;
			}
		} else if (date1[1] != 0 && date1[2] == 0 && date2[1] != 0 && date2[2] == 0) { // MM0Y, MM0Y
			if (date1[3] == date2[3] && 
					((date1[0] == date2[0] || date1[0] == date2[1]) || 
							(date1[1] == date2[0] || date1[1] == date2[1]))) {
				compVal = 0;
			} else if (date1[3] < date2[3] || 
					(date1[3] == date2[3] && 
					date1[0] < date2[0] && date1[0] < date2[1] && date1[1] < date2[0] && date1[1] < date2[1])) {
				compVal = -1;
			} else {
				compVal = 1;
			}
		} else { // M0DY, M0DY
			if (date1[3] < date2[3] || 
					(date1[3] == date2[3] && date1[0] < date2[0]) || 
					(date1[3] == date2[3] && date1[0] == date2[0] && date1[2] < date2[2])) {
				compVal = -1;
			} else if (date1[3] > date2[3] || 
					(date1[3] == date2[3] && date1[0] > date2[0]) || 
					(date1[3] == date2[3] && date1[0] == date2[0] && date1[2] > date2[2])) {
				compVal = 1;
			} else {
				compVal = 0;
			}
		}
		
		return compVal;
	}

	// returns whether or not the search results are sorted oldest first
	public boolean sortedByOldest() {
		List<int[]> dates = this.toDates();
		boolean increasing = true; 

		for (int i = 1; i < dates.size() - 1; i++) {
			if (this.compareDates(dates.get(i - 1), (dates.get(i))) == 1) {
				increasing = false;
			}
		}

		return increasing;
	}

	// returns whether or not the search results are sorted newest first
	public boolean sortedByNewest() {
		List<int[]> dates = this.toDates();
		boolean decreasing = true;

		for (int i = 1; i < dates.size() - 1; i++) {
			if (this.compareDates(dates.get(i - 1), (dates.get(i))) == -1) {
				decreasing = false;
			}
		}

		return decreasing;
	}

	/*
	 * returns whether or not the search results are sorted most cited first
	 * !! metrics inaccessible, could not implement !!
	 */
	/*public boolean sortedByMostCited() {
		return true;
	}*/

	// returns whether or not the search results are sorted most relevent first
	public boolean sortedByMostRelevant() {
		String searchKey = searchBar.getAttribute("value");
		List<String> results = new ArrayList<String>();

		for (WebElement result : searchResults) {
			String text = result.getText();
			results.add(text);
		}

		double decreasingRelevancy = (double)cjSearch.stringsContainSearch(searchKey, results) / (double)results.size();

		return decreasingRelevancy > 0.65;
	}

}
