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

public class MicrosoftSignInPassword extends PageObject {
	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String Magentopwd = EnvironmentSpecificConfiguration.from(env_var).getProperty("MagentoPassword");
	
	@FindBy(xpath = "//input[@id='i0118']")
	private WebElementFacade  LoginPassword;
	
	@FindBy(xpath = "//input[@id='idSIButton9']")
	private WebElementFacade  staysignedin;		
	
	
	public void enter_password() {		
	   
		if (LoginPassword.isCurrentlyVisible())
		{
	       LoginPassword.waitUntilVisible().typeAndEnter(Magentopwd);
	       staysignedin.waitUntilClickable().click();
		}
	}
	
	
}
