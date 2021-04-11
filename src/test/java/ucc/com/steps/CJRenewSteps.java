package ucc.com.steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.cj.pages.ui.CJFooterPage;
import ucc.com.pages.ui.ComCJRenewHelper;
import ucc.com.pages.ui.ComOrderConfirmation;
import ucc.com.pages.ui.ComPaybill;
import ucc.com.pages.ui.ComRenew;
import ucc.pages.ui.HomePage;
import ucc.utils.TestUtils;
import io.cucumber.datatable.DataTable;
import ucc.utils.CucumberUtils.CucumberUtils;

public class CJRenewSteps {

	private TestUtils tUtil = new TestUtils();
	public static Map<String, String> kmap = new HashMap<String, String>();
	public static Map<String, String> row = new HashMap<String, String>();
	static String end_pt = null;
	static String ucid = null;
	public static Response expResp = null;	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String pwd = EnvironmentSpecificConfiguration.from(env_var).getProperty("password");
	private static final Logger LOGGER = LoggerFactory.getLogger(CJRenewSteps.class);
	
	@Steps
	ComRenew comRenew;

	@Steps
	ComPaybill comPaybill;

	@Steps
	ComOrderConfirmation comOrder;	

	@Steps
	ComCJRenewHelper renewhelper;
	
	@Steps
	HomePage homepage;
	
	@Steps
	CJFooterPage footerpage;

	@Managed
	WebDriver driver;

	@When("^I call acsdb to fetch renew eligible users for catalyst$")
	public void getPaybillUsers(DataTable dataTable) throws SQLException, IOException {
		row = CucumberUtils.convert(dataTable);
		renewhelper.getEmailList(row);
		LOGGER.info("fetched required renew users from DB");

	}	
    
	@When("^renew user launch Catalyst Website$")
	public void user_launch_Catalyst_WebSite() throws Throwable {
		homepage.launchHomePage();	
		LOGGER.info("Launched catalyst home page");
	}
	
	@And("^performs the renew sign in with the fetched user$")
	public void Renew_Page_Enter_valid_email_password() throws Throwable {		
		renewhelper.ResetPwd();
		homepage.clickRenewLink();
		comRenew.Sign_In_Paybill((String) tUtil.getFromSession("email"), pwd);
		LOGGER.info("performed renew signin");
		
	}

	@When("^renew user sees required details on checkout page$")
	public void checkout_page_verification(DataTable dataTable) throws Throwable {
		row = CucumberUtils.convert(dataTable);
		comRenew.Verify_Renew_Page_Sections(row);
		LOGGER.info("verified renew checkout page details");

	}
	
	@When("^renew user selects new address and enters new billing address$")
	public void select_new_address(DataTable dataTable) throws Throwable {
		row = CucumberUtils.convert(dataTable);
		comRenew.select_new_address(row.get("select_address"));
		comRenew.Enter_new_billing_address(row);
		LOGGER.info("renew user selected new address from dropdown and Entered new billing address");

	}

	@And("^renew user selects renewal rate and performs payment$")
	public void Payment_details_and_submit_payment(DataTable dataTable) throws Throwable {
		row = CucumberUtils.convert(dataTable);
		comRenew.Select_Renew_Rate(row.get("Renew_Rate"));
		comPaybill.User_checkout(row);
		LOGGER.info("User performed payment");

	}

	@Then("^renewal payment confirmation page should display all the valid information$")
	public void verify_payment_confirmation_page_messages(DataTable dataTable) throws Throwable {
		row = CucumberUtils.convert(dataTable);
		comOrder.verify_renew_confirmation_messages(row);
		LOGGER.info("Verified renew payment confirmation page");

	}
	

}
