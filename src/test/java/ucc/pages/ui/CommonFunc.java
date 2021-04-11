package ucc.pages.ui;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.ClearCookiesPolicy;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.json.simple.JSONArray;
import ucc.i.method.aic.AICPOST;
import ucc.i.method.literatum.LiteratumGET;
import ucc.utils.JiraConnect;
import ucc.utils.TestUtils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.text.DateFormat;

public class CommonFunc extends PageObject {

    @FindBy(id = "jw-close")
    private WebElementFacade close;

    TestUtils tUtil = new TestUtils();
    JiraConnect jiraConn = new JiraConnect();

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonFunc.class);
    EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
    String baseEmail = EnvironmentSpecificConfiguration.from(env_var).getProperty("autoEmail");
    String parentWindowHandler;
    LiteratumGET litGET = new LiteratumGET();
    AICPOST aicPOST = new AICPOST();

    public void scrollAndClickElement(WebElement element) throws InterruptedException {
        waitFor(ExpectedConditions.elementToBeClickable(element));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        waitFor(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public void scrollAndClickElement(WebElementFacade element) throws InterruptedException {
        element.waitUntilPresent();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        element.waitUntilClickable().click();
    }

    public void dismissVideoWindow() {
        try {
            waitFor(ExpectedConditions.elementToBeClickable(close));
            close.click();
        } catch (Exception e) {
            LOGGER.info("Cannot dismiss modal window with video");
        }
    }

    public void clickElement(WebElementFacade element) {
        element.waitUntilPresent();
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", element);
    }

    public void Launch_URL(String URL) throws Throwable {
        getDriver().get(URL);

    }

    public void verify_the_current_URL(String URL) throws Throwable {
        String currentUrl = getDriver().getCurrentUrl();
        LOGGER.info(currentUrl);
        Assert.assertEquals(currentUrl, URL);

    }

    public void Verify_the_current_Title(String Title) throws Throwable {
        String actualTitle = getDriver().getTitle();
        LOGGER.info("Actual Title :  " + actualTitle);
        LOGGER.info("Expected Title : " + Title);
        Assert.assertEquals("Mismatch in Title", actualTitle, Title);

    }

    @Step("verify the all broken links current page")
    public void verify_the_all_broken_links_current_page() {

        List<WebElement> links = getDriver().findElements(By.tagName("a"));

        LOGGER.info("Total links are " + links.size());

        for (int i = 0; i < links.size(); i++) {

            WebElement ele = links.get(i);

            String url = ele.getAttribute("href");

            verifyLinkActive(url);

        }

    }

    @Title("Current url: {0}")
    @Step("Current url verification: {0}")
    public static void verifyLinkActive(String linkUrl) {
        try {
            URL url = new URL(linkUrl);

            HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();

            httpURLConnect.setConnectTimeout(3000);

            httpURLConnect.connect();

            if (httpURLConnect.getResponseCode() == 200) {
                LOGGER.info(linkUrl + " - " + httpURLConnect.getResponseMessage());

            }
            if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                LOGGER.info(linkUrl + " - " + httpURLConnect.getResponseMessage() + " - "
                        + HttpURLConnection.HTTP_NOT_FOUND);
            }
        } catch (Exception e) {

        }
    }

    public void switchToSecondWindow() {
        waitFor(ExpectedConditions.numberOfWindowsToBe(2));
        for (String winHandle : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(winHandle);
        }
        waitForLoadPage();
    }

    public void waitForLoadPage() {
        WebDriver driver = getDriver();
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
    }

    public String getConfEmail() {

        return tUtil.AppendTimestamp(baseEmail);
    }

    public void DriverClose() {
        getDriver().close();
    }

    public void scroll_Mousehover(WebElement element) {

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("scrollBy(0, 4000)");
        Actions act = new Actions(getDriver());
        act.moveToElement(element).perform();
    }

    public void mousehover(WebElement element) {

        Actions act = new Actions(getDriver());
        act.moveToElement(element).perform();
    }

    public void verify_WebelementMessage(WebElement element, String Message) throws Throwable {
        String elementmessage = element.getText();

        LOGGER.info("Actual Title :  " + elementmessage);
        LOGGER.info("Expected Title : " + Message);
        Assert.assertEquals("Mismatch in Title", elementmessage, Message);

    }

    public void assertfun(String element, String Message) {
        Assert.assertEquals(element, Message);
    }

    public void pdfhandle(String pdfurl, String ElementorText) throws IOException {
        URL url = new URL(pdfurl);
        InputStream is = url.openStream();
        BufferedInputStream fileparse = new BufferedInputStream(is);
        PDDocument document = null;

        document = PDDocument.load(fileparse);
        String pdfcontent = new PDFTextStripper().getText(document);
        LOGGER.info(pdfcontent);
        Assert.assertTrue(pdfcontent.contains(ElementorText));

    }

    public void switchToOriginalWindow() {
        String mainWindowHandle = getDriver().getWindowHandles().iterator().next();
        getDriver().switchTo().window(mainWindowHandle);
    }

    public void refreshBrowser() {
        getDriver().navigate().refresh();
    }

    public void clearCookies() {

        getDriver().manage().deleteAllCookies();
    }

    /**
     * getDateTimeUTC function below fetches UTC current date and time
     * 
     * @format - user can define any date/time format, example like - yyyy/MM/dd
     *         HH:mm:ss
     * @returns - string value of date/time in desired format
     * @timeZone - String value - "UTC" or "GMT"
     */
    public String getDateTimeUTC(String format, String timeZone) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        String utc_gmtDateTime = sdf.format(date).toString();
        return utc_gmtDateTime;
    }

    /**
     * getCustomDateTime function below fetches custom date and time
     * 
     * @format - user can define any date/time format, example like -
     *         yyyy/MM/dd'T'HH:mm:ss
     * @dateType - string value "to", "from", "yesterday", "year"
     * @Argument 1 - "to" - date is current date/time
     * @Argument 2 - "from" - date is a day ahead of today's date/time
     * @Argument 3 - "year" - date is a year ahead from today's date/time
     * @Argument 4 - "yesterday" - date is a day back from today's date/time
     * @returns - string value of date/time in desired format
     */
    public String getCustomDateTime(String format, String dateType) {
        String date = "null";
        switch (dateType) {

        case "from":
            date = LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
            break;

        case "to":
            date = LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern(format));
            break;

        case "year":
            date = LocalDateTime.now().plusYears(1).format(DateTimeFormatter.ofPattern(format));
            break;

        case "yesterday":
            date = LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern(format));
            break;

        case "rememberMeExp":
            date = LocalDateTime.now().plusDays(180).format(DateTimeFormatter.ofPattern(format));
            break;
        default:
            LOGGER.info("Please specify dateType");
        }
        return date;
    }

    /**
     * getLocalStorageObjectValue function provides Local Storage Object value
     * 
     * @throws ParseException
     * @localStoreObjName - user needs to pass localStoreObjName in string type
     * @returns - JSON Object containing the value (k/v pairs) of LocalStorageObject
     */
    public JSONObject getLocalStorageObjectValue(String localStoreObjName) throws ParseException {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String localObjVal = (String) js
                .executeScript(String.format("return window.localStorage.getItem('%s');", localStoreObjName));
        LOGGER.info("============== Object (" + localStoreObjName + ") Value ====================");
        LOGGER.info(localObjVal);
        LOGGER.info("========================================================================");
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(localObjVal);
        return jsonObject;
    }

    /**
     * deleteCookieWithName function delete specific cookie from application
     * 
     * @cookieName - user needs to pass cookieName in string type
     */
    public void deleteCookieWithName(String cookieName) {
        getDriver().manage().deleteCookieNamed(cookieName);
    }

    /**
     * getSimpleLocalStorageObjectValue function provides Local Storage Object
     * values (which are not in JSON format)
     * 
     * @throws ParseException
     * @localStoreObjName - user needs to pass localStoreObjName in string type
     * @returns - String containing the entireValue of LocalStorageObject
     */
    public String getSimpleLocalStorageObjectValue(String localStoreObjName) throws ParseException {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String localObjVal = (String) js
                .executeScript(String.format("return window.localStorage.getItem('%s');", localStoreObjName));
        LOGGER.info("============== Object (" + localStoreObjName + ") Value ====================");
        LOGGER.info(localObjVal);
        LOGGER.info("========================================================================");
        return localObjVal;
    }

    public void launchNewBrowserTab_SameWindow() {
        ((JavascriptExecutor) getDriver()).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs.get(1));
    }

    /**
     * checkIfCookiePresent function validates specific cookie in application local
     * storage
     * 
     * @cookieName - user needs to pass cookieName in string type
     * @returns - true if cookie is present or false if cookie is not present
     */
    public boolean checkIfCookiePresent(String cookieName) {
        return (getDriver().manage().getCookieNamed(cookieName) != null) ? true : false;
    }

    /**
     * saveBrowserSession_Cookies function saves all the browser cookies on
     * cookies.data file Cookies.data file is deleted and created new each time this
     * function is called
     */
    public void saveBrowserSession_Cookies() {

        JSONArray cookiesList = new JSONArray();

        for (Cookie ck : getDriver().manage().getCookies()) {

            JSONObject cookieDetails = new JSONObject();
            cookieDetails.put("name", ck.getName());
            cookieDetails.put("value", ck.getValue());
            cookieDetails.put("domain", ck.getDomain());
            cookieDetails.put("path", ck.getPath());
            cookieDetails.put("expiry", getValidExpiry(cookieDetails, ck));
            cookieDetails.put("secure", ck.isSecure());

            cookiesList.add(cookieDetails);
        }

        File fileOld = new File("Cookies.data");
        fileOld.delete();

        try (FileWriter file = new FileWriter("Cookies.data")) {
            file.write(cookiesList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getValidExpiry(JSONObject cookieDetails, Cookie ck) {
        Date expDate = new Date(0);
        DateFormat df = new SimpleDateFormat("E MMM dd HH:mm:ss 'EST' yyy");
        String expiryDate = df.format(expDate);
        return (ck.getExpiry() != null) ? df.format(ck.getExpiry()) : expiryDate;
    }

    /**
     * addSavedSessionCookiesToBrowser function adds all the saved cookies to new
     * browser session For UCC - this function only works when domain is launched
     * first before adding cookies Cookies are read from Cookies.data file which is
     * created by above function - saveBrowserSession_Cookies()
     */
    @SuppressWarnings("deprecation")
    public void addSavedSessionCookiesToBrowser() {

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("Cookies.data")) {
            Object parseObject = jsonParser.parse(reader);
            JSONArray cookiesList = (JSONArray) parseObject;
            LOGGER.info("Cookies - {}", cookiesList);

            cookiesList.forEach(cookie -> parseCookieObject((JSONObject) cookie));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void parseCookieObject(JSONObject cookie) {

        try {
            String name = (String) cookie.get("name");
            String value = (String) cookie.get("value");
            String domain = (String) cookie.get("domain");
            String path = (String) cookie.get("path");
            Date expiry = (Date) new SimpleDateFormat("E MMM dd HH:mm:ss 'EST' yyy")
                    .parse(cookie.get("expiry").toString());
            Boolean secure = (Boolean) cookie.get("secure");
            Cookie ck = new Cookie(name, value, domain, path, expiry, secure);
            LOGGER.info("Cookie will add to driver - {}", ck.toJson());
            getDriver().manage().addCookie(ck);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * getCookieExpiryDate function fetch cookie expiry date in any format and in
     * any timezone - GMT or UTC
     * 
     * @cookieName - user need to pass cookie name for which expiry date will be
     *             fetched
     * @format - user can define any date/time format, example like - yyyy/MM/dd
     *         HH:mm:ss
     * @returns - string value of cookie expiry date in desired format
     * @timeZone - String value - "UTC" or "GMT"
     */
    public String getCookieExpiryDate(String cookieName, String format, String timeZone) {

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = getDriver().manage().getCookieNamed(cookieName).getExpiry();
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        String cookieExpDate = sdf.format(date).toString();
        return cookieExpDate;
    }

    /**
     * getSessionCookieExpiry function check if expiry date is present/not present
     * 
     * @if expiry date present - returns cookie expiry date/Max-Age
     * @if expiry date not present - returns cookie value - session
     * @cookieName - user need to pass cookie name for which session expiry date
     *             will be fetched
     */
    public String getSessionCookieExpiry(String cookieName) {
        return (getDriver().manage().getCookieNamed(cookieName).getExpiry() == null) ? "Session"
                : getDriver().manage().getCookieNamed(cookieName).getExpiry().toString();
    }

    /**
     * driverQuit will terminate all the sessions of the browser and launched
     * windows It will also clean the cookies so next time fresh browser is launched
     */

    public void driverQuit() {

        jiraConn.getVersionId();
        Boolean flag = Boolean.parseBoolean(System.getProperty("versionFlag"));
               
        if (!flag) {
            LOGGER.info("UI based execution ");
            getDriver().manage().deleteAllCookies();
            getDriver().quit();
        } else {
            LOGGER.info("No driver specific code needed");
        }
        
    }

    public int getElementSize(By element) {
        return getDriver().findElements(element).size();
    }
    
	public String getCurrentBrowserUrl() {
		return getDriver().getCurrentUrl();
	}
	

	public String getCssValue(WebElementFacade element, String cssVal) {
		return element.waitUntilVisible().getCssValue(cssVal);
	}
			
	public String getAttribute(WebElementFacade element, String attrib) {
		return element.waitUntilVisible().getAttribute(attrib);
	}
	
	public void switchToDefaultContent() {
		getDriver().switchTo().defaultContent();
	}

	 public void switchSecondTab() {
		 String parentHandle = getDriver().getWindowHandle();
		 for (String handle: getDriver().getWindowHandles()) {
		     if (!parentHandle.equals(handle))
		    	 getDriver().switchTo().window(handle);
		 }     
	    }
	 
	 public WebElementFacade findElementByXpath(String elementName, String substitutionValue) {
	      WebElementFacade element = find(By.xpath(String.format(elementName, substitutionValue)));
	      return element;
		}

     public Boolean checkPresenceOfElementWithDynamicXpath(String elementName, String substitutionValue) {
		  WebElementFacade webElement = findElementByXpath(elementName, substitutionValue);
		  waitFor(ExpectedConditions.visibilityOf(webElement));
		  return webElement.isDisplayed();
		}
     
     public boolean validateText_InPageSource(String expectedText) {
    	 return (getDriver().getPageSource().contains(expectedText)) ? true : false;
    	 }

}

