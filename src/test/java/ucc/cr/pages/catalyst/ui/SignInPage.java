package ucc.cr.pages.catalyst.ui;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import ucc.com.steps.RegisteredOrderSteps;
import ucc.pages.ui.CommonFunc;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.logging.LogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignInPage extends PageObject {

	CommonFunc commonFunc = new CommonFunc();
	public static String localStorage;
	private static final Logger LOGGER = LoggerFactory.getLogger(SignInPage.class);

	@FindBy(xpath = "//input[@name='signInEmailAddress']")
	WebElementFacade emailAddress;

	@FindBy(xpath = "//input[@name='currentPassword']")
	WebElementFacade pswdInput;

	@FindBy(xpath = "//button[contains(text(), 'Sign In')]")
	List<WebElementFacade> signInBtn;

	@FindBy(id = "ucc-sign-in-submit")
	WebElementFacade StoresignInBtn;

	@FindBy(xpath = "//a[contains(text(),'Sign Out')]")
	WebElementFacade signOutBtn;

	@FindBy(xpath = "//div[@class='header_top-bar_right']//div[@class='userLoginBar_user simple-menu_item simple-menu_aligned-to-right']")
	WebElementFacade userMenu;

	@FindBy(xpath = "//div[@class='header_top-bar_right']//span[contains(text(),'Sign')]")
	WebElementFacade signInHeader;

	@FindBy(xpath = "//div[@id='signIn']//h1[contains(text(),'Sign In')]")
	WebElementFacade signInPageHeader;

	@FindBy(xpath = "//p[contains(text(),'We do not recognize that combination. Please try a')]")
	WebElementFacade invalidloginmessage;

	@FindBy(xpath = "//div[@class='userLoginBar']//span[contains(text(),'Sign in')]")
	WebElementFacade signInHeaderLink;

	@FindBy(xpath = "//div[@class='header_top-bar_right']/div[@class='userLoginBar']//span[contains(text(),'Sign')]")
	WebElementFacade signInHeaderLink_MyAccount;

	@FindBy(xpath = "//div[@id='signIn']//button[contains(text(),'Sign In')]")
	WebElementFacade submitSignIn;

	@FindBy(xpath = "//i[@class='icon-user']/following-sibling::span[contains(text(),'firstName')]")
	WebElementFacade automationSignedInUser;

	@FindBy(xpath = "//input[@id='ucc-remember-me-cb']/following-sibling::span")
	WebElementFacade rememberMeCheckbox;

	@FindBy(xpath = "//div[@class='widget-placeholder']//input[@id='ucc-remember-me-cb-embedded']/following-sibling::span")
	WebElementFacade rememberMeCheckbox_MyAccount;

	public void inputEmail(String email) {
		emailAddress.waitUntilPresent().type(email);
	}

	public void inputPassword(String password) {

		pswdInput.waitUntilPresent().type(password);
	}

	public void clickSignIn() {
		signInBtn.get(0).waitUntilClickable().click();
	}

	public void clickSignInLinkInCatalystHeader() {
		commonFunc.clickElement(signInHeaderLink);
	}

	public void clickSignInLinkInMyAccountHeader() {
		signInHeaderLink_MyAccount.waitUntilClickable().click();
		// commonFunc.clickElement(signInHeaderLink_MyAccount);
	}

	public void clickStoreSignIn() {
		StoresignInBtn.waitUntilClickable().click();
	}

	public void submitSignIn() {
		submitSignIn.waitUntilClickable().click();
	}

	public void clickSignOut() throws InterruptedException {
		// Thread.sleep(4000L);
		withAction().moveToElement(userMenu).build().perform();
		// Thread.sleep(1000L);
		commonFunc.clickElement(signOutBtn);
	}

	public Boolean headerSingInAvailable() {
		return signInHeader.waitUntilVisible().isVisible();
	}

	public Boolean SignInPagedisplay() {
		return signInPageHeader.waitUntilVisible().isVisible();
	}

	public String getUserProfileName() {
		signInHeader.waitUntilVisible().getValue();
		return signInHeader.waitUntilVisible().getText();
	}

	public String InvalidSigninMessage() {
		return invalidloginmessage.waitUntilVisible().getText();
	}

	@Step("user check/unchecks remember me on catalyst sign in pop up as per data file")
	public void checkUncheckRememberMe(String rememberMe) {
		if (!rememberMe.equals("yes")) {
			rememberMeCheckbox.waitUntilClickable().click();
		}
	}

	@Step("user check/unchecks remember me on myaccount sign in page as per data file")
	public void checkUncheckRememberMe_MyAccount(String rememberMe) {
		if (!rememberMe.equals("yes")) {
			commonFunc.clickElement(rememberMeCheckbox_MyAccount);
		}
	}

	@Step("^user check if local storage object exists$")
	public boolean ifLocalStorageObjectExists(String localStoreObjName) throws ParseException {
		localStorage = commonFunc.getSimpleLocalStorageObjectValue(localStoreObjName);
		return (localStorage != null) ? true : false;
	}

	@Step("user parse local storage object value")
	public void parseLocalStorageObjectValue(String localStorageObjectName) throws ParseException {
		localStorage = commonFunc.getSimpleLocalStorageObjectValue(localStorageObjectName).toString();
	}

	@Step("user validate mms auth state event/callback - value")
	public boolean validate_EventCallback(String authStateEventValue) {
		return (localStorage.equals(authStateEventValue)) ? true : false;
	}

	public boolean userDisplayedSignedIn() {
		boolean userFnameLname = automationSignedInUser.waitUntilVisible().isDisplayed();
		return (userFnameLname) ? true : false;
	}

}
