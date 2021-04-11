package ucc.nejm.mcadmin.pages.ui;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;

public class MCAdminEmailLogPage extends PageObject {

	private static final Logger LOGGER = LoggerFactory.getLogger(MCAdminEmailLogPage.class);

	@FindBy(xpath = "//p[text()='Email Log']")
	WebElementFacade emailLogLabel;

	@FindBy(xpath = "//h2[text()='Email Log']")
	WebElementFacade emailLogTableName;

	@FindBy(xpath = "(//span[text()='View This Table'])[3]")
	WebElementFacade viewThisTable;

	@Step("Verify all the content displayed on Email Log widget")
	public boolean verifyEmailLogWidgetContent(String content) {

		content = StringUtils.remove(content, "(");
		content = StringUtils.remove(content, ")");
		switch (content) {
		case "Email Log label":
			emailLogLabel.waitUntilVisible().isDisplayed();
			LOGGER.info("Email Log label is displayed");
			break;
		case "Email Log table name":
			emailLogTableName.waitUntilVisible().isDisplayed();
			LOGGER.info("Email Log Table Name is displayed:");
			break;
		case "VIEW THIS TABLE":
			viewThisTable.waitUntilVisible().isDisplayed();
			LOGGER.info("VIEW THIS TABLE is displayed:");
			break;
		default:
			LOGGER.info("Please check the content you are trying to verify");
			break;
		}
		return true;

	}

}
