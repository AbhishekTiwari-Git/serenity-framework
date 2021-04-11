package ucc.cj.pages.ui;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import ucc.pages.ui.CommonFunc;

public class CJSubscriberAccessPage extends PageObject {

	CommonFunc comFun = new CommonFunc();

	// Not At Institution_Subscriber Access Validation

	@FindBy(xpath = "//a[contains(text(),'How Different Payment Models Support (or Undermine')]")
	private WebElementFacade indeptharticlelink;

	@FindBy(xpath = "(//strong[contains(text(),'This article is available to subscribers.')]")
	private WebElementFacade SubscribeNowfield;

	@Step("Click on InDepth article link")
	public void click_indeptharticlelink() throws InterruptedException {
		comFun.refreshBrowser();
		comFun.clickElement(indeptharticlelink);
	}

	@Step("Check Subscribe Now field")
	public void check_subscribenowfield() {
		SubscribeNowfield.shouldNotBeVisible();
	}

}
