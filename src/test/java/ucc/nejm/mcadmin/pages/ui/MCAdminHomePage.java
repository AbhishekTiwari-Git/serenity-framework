package ucc.nejm.mcadmin.pages.ui;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import ucc.pages.ui.CommonFunc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.serenitybdd.core.annotations.findby.FindBy;

public class MCAdminHomePage extends PageObject {
	CommonFunc commonFunc = new CommonFunc();
	private static final Logger LOGGER = LoggerFactory.getLogger(MCAdminHomePage.class);

	@FindBy(xpath = "//h1[contains(text(),'NEJM')]")
	WebElementFacade nejmDashboard;

	@FindBy(xpath = "//p[contains(text(),'Select from the available tables below')]")
	WebElementFacade message;

	@Step("Verify NEJM Dashboard of Media Center Admin Tool")
	public boolean verifyNejmDashboard() {
		LOGGER.info("NEJM Dashboard is displayed");
		return nejmDashboard.waitUntilVisible().isDisplayed();
	}

	@Step("Verify message displayed on NEJM Dashboard")
	public String verifyTextMessage() {
		LOGGER.info("NEJM text is displayed:" + message.getText());
		return message.waitUntilVisible().getText();
	}

	@Step("Verify the table widgets displayed on NEJM Dashboard")
	public boolean verifyTableWidget(String widgetName) {
		String widget_element = "//p[contains(text(),'%s')]/..";
		LOGGER.info(widgetName + "is displayed on NEJM Dashboard");
		return commonFunc.checkPresenceOfElementWithDynamicXpath(widget_element, widgetName);
	}

}
