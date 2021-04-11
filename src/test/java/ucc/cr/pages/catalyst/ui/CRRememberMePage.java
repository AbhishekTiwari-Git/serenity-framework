package ucc.cr.pages.catalyst.ui;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucc.pages.ui.CommonFunc;
import ucc.utils.TestUtils;

public class CRRememberMePage extends PageObject {

	private static final Logger LOGGER = LoggerFactory.getLogger(CRRememberMePage.class);
	TestUtils tUtil = new TestUtils();
	CommonFunc commonFunc = new CommonFunc();

	@Step("Validate cookie expiry date is 180 days when remember me is checked")
	public boolean validateCookieExpiryDate_RememberMeChecked(String actualExpiry) {

		String expectedDate = commonFunc.getCustomDateTime("yyyy-MM-dd", "rememberMeExp");
		LOGGER.info("==========Expected Expiry Date (Remember Me : Checked)==========");
		LOGGER.info(expectedDate);
		LOGGER.info("================================================================");
		LOGGER.info("==========Actual Expiry Date (Remember Me : Checked)============");
		LOGGER.info(actualExpiry);
		LOGGER.info("================================================================");
		return (actualExpiry.equals(expectedDate)) ? true : false;

	}

	@Step("Validate cookie expiry date is 1 day when remember me is unchecked")
	public boolean validateCookieExpiryDate_RememberMeUnChecked(String actualExpiry) {

		String expectedDate = "Session";
		LOGGER.info("==========Expected Expiry Date (Remember Me : UnChecked)==========");
		LOGGER.info(expectedDate);
		LOGGER.info("================================================================");
		LOGGER.info("==========Actual Expiry Date (Remember Me : UnChecked)============");
		LOGGER.info(actualExpiry);
		LOGGER.info("================================================================");
		return (actualExpiry.equals(expectedDate)) ? true : false;

	}

}
