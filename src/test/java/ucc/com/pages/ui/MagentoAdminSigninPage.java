package ucc.com.pages.ui;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.pages.ui.CommonFunc;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import java.util.ArrayList;

public class MagentoAdminSigninPage extends PageObject {	
	
	
	
	@FindBy(xpath = "//a[contains(text(),'Login via Identity Provider')]")
	private WebElementFacade  LoginIdentityProvider;
	
	@FindBy(xpath = "//input[@name='loginfmt']")
	private WebElementFacade  LoginEmail;
	
	@FindBy(xpath = "//input[@name='passwd']")
	private WebElementFacade  LoginPassword;
	
	
	
	public void click_login_via_identity_provider() {
		
		LoginIdentityProvider.waitUntilClickable().click();	
		
		
	}
	
	
}
