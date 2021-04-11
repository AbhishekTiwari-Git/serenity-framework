package ucc.pages.ui;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.com.pages.ui.ComPaybill;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage extends PageObject {
	
	CommonFunc comFun;
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String baseUrl =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("catalyst.base.url");
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ComPaybill.class);
	
	@FindBy(xpath = "//div[@class='userLoginBar_subscribe simple-menu_item simple-menu_aligned-to-right']/a[1]")
	private WebElementFacade  SubscribeButton;
	
	@FindBy(xpath = "//div[@class='userLoginBar_subscribe simple-menu_item simple-menu_aligned-to-right']//span[contains(text(),'SUBSCRIBE')]")
	private WebElementFacade  LoginSubscribeButton;
	
	@FindBy(xpath = "//div[@class='footer__box footer__box--site-map']//a[contains(text(),'Subscribe')]")
	private WebElementFacade  FooterSubscribe;
	
	@FindBy(xpath = "//li[@class='main-menu_item simple-menu_item']//span[contains(text(),'Journal')]")
	private WebElementFacade  Journal;
	
	@FindBy(xpath = "//a[@class='cat-link-primary toc-header_viewIssue-link'][contains(text(),'Subscribe')]")
	private WebElementFacade  JournalSubscribe;
	
	@FindBy(xpath = "//div[@class='ucc-modal-overlay']")	
	private WebElementFacade  CreateAccountLink;
	
	@FindBy(xpath = "//div[@class='userLoginBar_user simple-menu_item simple-menu_aligned-to-right']/a[1]/span")
	private WebElementFacade  Username;
	
	@FindBy(xpath = "//span[contains(text(),'Sign in')]")
	private WebElementFacade  SignInLink;
	
	@FindBy(xpath = "//div[@class='userLoginBar_user simple-menu_item simple-menu_aligned-to-right']//a[1]/span[1]")
	private WebElementFacade  SignedInusername;
	
	@FindBy(xpath = "//a[contains(text(),'Renew')]")
	private WebElementFacade  RenewLink;
	
	@FindBy(xpath = "//a[contains(text(),'Pay Bill')]")
	private WebElementFacade  PayBillLink;
	
	@FindBy(xpath = "//a[contains(text(),'My Account')]")
	private WebElementFacade  MyAccountLink;
	
	@FindBy(xpath = "/html/body/div[12]/div/div[1]")
    private WebElementFacade close;
	
	
	@FindBy(id = "//div[@id='slider-control-SI_dbt3GCYzIF72kjH']")
    private WebElementFacade feedback;
	
	@FindBy(xpath = "//i[@class='icon-user']")
    private WebElementFacade UserIcon;
	
	@FindBy(xpath = "//a[text()='Sign Out']")
    private WebElementFacade SignOut;

	
	/*  Launching the HomePage  */
	
	public void launchHomePage() {
		getDriver().get(baseUrl);
		getDriver().manage().window().maximize();
	}
	
	// clicking subscribe button
	public void clicksubscribe() throws InterruptedException {
		
		SubscribeButton.waitUntilVisible().click();
	}

	// clicking Footer subscribe link
	public void clickFootersubscribe() throws InterruptedException {
		FooterSubscribe.waitUntilVisible();
		comFun.scrollAndClickElement(FooterSubscribe);
	}

	// Hovering mouse over Journal link
	public void HoverJournal() throws InterruptedException {

		Journal.waitUntilVisible();
		withAction().moveToElement(Journal).perform();

	}

	// Click on Subscribe link of Journal
	public void ClickJournalSubscribe() throws InterruptedException {

		JournalSubscribe.waitUntilVisible().click();

	}

	// Click on Create Account link
	public void ClickCreateAccount() throws InterruptedException {

		CreateAccountLink.waitUntilClickable().click();

	}

	// Verify user name
	public String VerifyUserName() throws InterruptedException {

		String username = Username.waitUntilVisible().getText();
		return username;

	}

	// clicking subscribe button
	public void clickLoginsubscribe() throws InterruptedException {
		LoginSubscribeButton.waitUntilVisible().click();
	}

	// clicking SignIn Link
	public void clickSignInLink() throws InterruptedException {

		SignInLink.waitUntilVisible().click();
	}

	// Retrieving signed in user name
	public String RetrieveUserName() throws InterruptedException {

		return SignedInusername.waitUntilVisible().getText();
	}

	// clicking SignIn Link
	public void clickRenewLink() throws InterruptedException {

		RenewLink.waitUntilVisible();
		comFun.scrollAndClickElement(RenewLink);
	}
	
	// User signout on Homepage
			public void ClickUserSignOut() throws InterruptedException {

				UserIcon.waitUntilClickable();
				withAction().moveToElement(UserIcon).perform();
				SignOut.waitUntilClickable().click();
				SignInLink.waitUntilVisible();			
				
			}

	// clicking PayBill Link
	public void clickPayBillLink() throws InterruptedException {

		PayBillLink.waitUntilVisible();
		comFun.scrollAndClickElement(PayBillLink);
	}

	// Hovering mouse over user name
	public void HoverUserName() throws InterruptedException {

		SignedInusername.waitUntilVisible();
		withAction().moveToElement(SignedInusername).perform();

	}

	// Click on My Account link
	public void ClickMyAccountLink() throws InterruptedException {

		MyAccountLink.waitUntilVisible().click();

	}

	// closing the home page
	public void closeHomePage() {
		getDriver().quit();
	}

	public void launchHomePagewithCookie() {
		WebDriver driver = getDriver();
		((JavascriptExecutor)driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(0));
		driver.close();
		driver.switchTo().window(tabs.get(1));
		driver.get(baseUrl);
	}
	
	/**
	 * This Function is for clicking on subscribe button based on the entry point
	 * @param EntryPoint
	 * @throws InterruptedException
	 * @author mshinde
	 * @date 24-Mar-2021
	 */
	//examples of entry point for store page
	public void ClickonSubscribe(String EntryPoint) throws InterruptedException {
			
		switch (EntryPoint) {

		case "HeaderButton":
			SubscribeButton.waitUntilVisible().click();			
			break;
        
		case "FooterLink":
			FooterSubscribe.waitUntilVisible();
			comFun.scrollAndClickElement(FooterSubscribe);			
			break;
			
		case "JournalSectionSubscribe":
			Journal.waitUntilVisible();
			withAction().moveToElement(Journal).perform();
			JournalSubscribe.waitUntilVisible().click();
			break;

		default:
			LOGGER.info("Invalid Entry Point" + EntryPoint);
			break;
		}
		
		LOGGER.info("Clicked on " + EntryPoint);
		
	}
}
