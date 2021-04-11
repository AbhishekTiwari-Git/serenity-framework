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

public class MagentoAdminOrdersPage extends PageObject {	
	
	
	@FindBy(xpath = "//span[contains(text(),'Create New Order')]")
	private WebElementFacade  CreateNewOrderButton;
	
			
	public void click_create_new_order_button() {
		
		CreateNewOrderButton.waitUntilClickable().click();	
				
	}	

	
}
