package ucc.cj.pages.ui;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import ucc.pages.ui.CommonFunc;

public class CJCookiePage extends PageObject {

	CommonFunc comFun = new CommonFunc();

	// Close
	@FindBy(xpath = "//div[@class='gdpr-msg__close']")
	private WebElementFacade Close;

	// UserName

	@FindBy(xpath = "//input[@name='user']")
	private WebElementFacade Username;

	// PassWord

	@FindBy(xpath = "//input[@name='pass']")
	private WebElementFacade Pass;

	//
	@FindBy(xpath = "//input[@type='submit']")
	private WebElementFacade submit;

	@Step("cookies notice message and click on close icon")
	public void verify_the_cookies_notice_message_and_click_on_close_icon() {
		Close.waitUntilClickable().click();
	}

	// Enter Username and Password OCLC

	@Step("Enter UserName Password and click")
	public void user_enter_user_name_passwordand_click_on_login_button(String UserName, String Password) {
		Username.waitUntilVisible().type(UserName);
		Pass.waitUntilVisible().type(Password);
		submit.waitUntilClickable().click();
	}

}
