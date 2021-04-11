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

public class MagentoAdminHomePage extends PageObject {	
	
	
	@FindBy(xpath = "//text()[.='Sales']/ancestor::a[1]")
	private WebElementFacade  SalesOption;
	
	@FindBy(xpath = "//text()[.='Orders']/ancestor::a[1]")
	private WebElementFacade  OrdersOption;
	
	
		
	public void click_sales_option() {
		
		SalesOption.waitUntilClickable().click();	
				
	}
	
public void click_orders_option() {
		
	    OrdersOption.waitUntilClickable().click();	
				
	}
	
	
}
