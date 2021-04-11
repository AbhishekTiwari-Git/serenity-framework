package ucc.cr.pages.catalyst.ui;

import io.cucumber.datatable.DataTable;
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
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CRSessionPage extends PageObject {

	CommonFunc commonFunc = new CommonFunc();
	JsonUtils jsonUtils = new JsonUtils();
	JSONObject localStorage;
	public static String file;
	public static String uccLastUpdateDate_Actual, uccLastUpdateDate_Expected;
	private static final Logger LOGGER = LoggerFactory.getLogger(CRSessionPage.class);

	@FindBy(xpath = "//*[starts-with(@class,'header_top-bar_right')]//i[@class='icon-user']")
	WebElementFacade userIcon;

	@Step("Check if local Storage object key exists")
	public boolean ifLocalStorageObjectExists(String localStoreObjName) throws ParseException {
		localStorage = commonFunc.getLocalStorageObjectValue(localStoreObjName);
		return (localStorage != null) ? true : false;
	}

	@Step("Parse uccLastUpdate date value from local storage object")
	public void parseUccLastUpdateDateValue(String key) {
		uccLastUpdateDate_Actual = localStorage.get(key).toString();
		LOGGER.info("=======uccLastUpdateDate Actual Value========");
		LOGGER.info(uccLastUpdateDate_Actual);
		LOGGER.info("==============================================");
	}

	@Step("User validate uccLastUpdateDate value with current UTC date value")
	public boolean validate_uccLastUpdateDate() {
		uccLastUpdateDate_Expected = commonFunc.getDateTimeUTC("yyyy-MM-dd", "UTC");
		LOGGER.info("=======uccLastUpdateDate Expected Value========");
		LOGGER.info(uccLastUpdateDate_Expected);
		LOGGER.info("===============================================");
		return (uccLastUpdateDate_Actual.contains(uccLastUpdateDate_Expected)) ? true : false;
	}

	@Step("User validate ucclastupdated date in Akamai")
	public boolean validate_uccLastUpdateDate_InAkamai(Response res) throws java.text.ParseException {
		String uccAkamaiDate = res.jsonPath().getString("uccLastUpdatedDate").toString();
		LOGGER.info("=======uccLastUpdateDate Expected Value in Akamai========");
		LOGGER.info(uccAkamaiDate);
		LOGGER.info("========================================================");
		return (uccAkamaiDate.contains(uccLastUpdateDate_Actual)) ? true : false;
	}

	@Step("User parse SSO Auth token url on the basis of Environment and return auth token url")
	public String parseSSOAuthTokenUrl() {

		file = "AccExp_POST_SSO_Auth_Token_Url.json";
		String token = jsonUtils.getFromJSON(file, "myacctTestUrl").toString();
		LOGGER.info("================AUTH TOKEN URL : ==========================");
		LOGGER.info(token);
		LOGGER.info("===========================================================");
		return token;
	}

	@Step("User is signed in Automatically to My Account Page when using Auth Token Url")
	public boolean automaticSignIn() {
		boolean isUserIconDisplayed = userIcon.waitUntilVisible().isDisplayed();
		return (isUserIconDisplayed) ? true : false;
	}

}
