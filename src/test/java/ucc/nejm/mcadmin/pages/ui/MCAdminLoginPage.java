package ucc.nejm.mcadmin.pages.ui;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.WebElementFacadeImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.pages.ui.CommonFunc;

public class MCAdminLoginPage extends PageObject {

	static EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
	static String mediaCenterUrl = EnvironmentSpecificConfiguration.from(variables).getProperty("mediacenter.base.url");
	CommonFunc commonFunc = new CommonFunc();
	private static final Logger LOGGER = LoggerFactory.getLogger(MCAdminLoginPage.class);

	@FindBy(css = ".MuiButton-fullWidth .MuiButton-label")
	WebElementFacade loginButton;

	@FindBy(name = "loginfmt")
	WebElementFacade email;

	@FindBy(id = "idSIButton9")
	WebElementFacade submit;

	@FindBy(name = "passwd")
	WebElementFacade password;

	@FindBy(css = "[aria-label='media center admin user']")
	WebElementFacade fnLnButton;

	@FindBy(css = "[role='menuitem']")
	WebElementFacade logoutButton;

	@Step("Launch the Media Center Admin Tool URL")
	public void launchMediaCenterAdminTool() throws Throwable {
		LOGGER.info("Media Center Admin Tool URL" + mediaCenterUrl);
		commonFunc.Launch_URL(mediaCenterUrl);
	}

	@Step("Login into the Media Center Admin Tool")
	public void loginMediaCenterAdminTool() {
		loginButton.click();
		commonFunc.switchToSecondWindow();
		LOGGER.info("Window switched to second window");
		email.waitUntilPresent().type(variables.getProperty("username"));
		submit.waitUntilClickable().click();
		password.waitUntilPresent().type(variables.getProperty("password"));
		submit.waitUntilClickable().click();
		submit.waitUntilVisible().click();
		LOGGER.info("Username and password submitted successfully");
		commonFunc.switchToOriginalWindow();
		LOGGER.info("Window switched to parent window");
	}

	@Step("Click on the Lastname, Frstname displayed at the top right corner")
	public void clickLnFn() {
		fnLnButton.waitUntilPresent().click();
	}

	@Step("Verify Logout Button is displayed")
	public Boolean verifyLogoutButton() {
		return logoutButton.isDisplayed();
	}

}
