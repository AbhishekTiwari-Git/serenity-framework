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

public class MicrosoftSignInEmail extends PageObject {
	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String MagentoId = EnvironmentSpecificConfiguration.from(env_var).getProperty("MagentoUsername");
	
	@FindBy(xpath = "//input[@id='i0116']")
	private WebElementFacade  LoginEmail;	
	
	
	public void enter_email_id() {
	  
		if (LoginEmail.isCurrentlyVisible())
		{
	       LoginEmail.waitUntilVisible().typeAndEnter(MagentoId);  
		}
	}
	
	
}
