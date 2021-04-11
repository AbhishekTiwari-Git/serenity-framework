package ucc.com.steps;

import static ucc.com.steps.ExistingUserSigninHelper.verify_payment_confirmation_messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.slf4j.LoggerFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.cj.pages.ui.CJFooterPage;
import ucc.com.pages.ui.ComOrderConfirmation;
import ucc.com.pages.ui.ComPayBillHelper;
import ucc.com.pages.ui.ComPaybill;
import ucc.com.pages.ui.ComRenew;
import ucc.i.method.accountexp.AccountExpGET;
import ucc.i.method.aic.AICGET;
import ucc.i.method.aic.AICPATCH;
import ucc.pages.ui.HomePage;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;
import io.cucumber.datatable.DataTable;
import ucc.utils.CucumberUtils.CucumberUtils;

public class PayBillSteps {

	private TestUtils tUtil = new TestUtils();
	public static Map<String, String> kmap = new HashMap<String, String>();
	public static Map<String, String> row = new HashMap<String, String>();
	static String end_pt = null;
	static String ucid = null;
	public static Response expResp = null;
	private static String endPt = null;
	private static Response aicResp = null;
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String pwd = EnvironmentSpecificConfiguration.from(env_var).getProperty("password");
	private List<String> emailList = new ArrayList<>();
	private static final Logger LOGGER = LoggerFactory.getLogger(PayBillSteps.class);
	
	@Steps
	ComRenew comRenew;

	@Steps
	ComPaybill comPaybill;

	@Steps
	ComOrderConfirmation comOrder;

	@Steps
	AccountExpGET AccExpGETSteps;

	@Steps
	AICGET aicGETSteps;

	@Steps
	AICPATCH AICPATCHSteps;

	@Steps
	ComPayBillHelper payBillhelper;
	
	@Steps
	HomePage homepage;
	
	@Steps
	CJFooterPage footerpage;

	@Managed
	WebDriver driver;

	@When("^I call acsdb to fetch payBill eligible users for catalyst$")
	public void getPaybillUsers(DataTable dataTable) throws SQLException, IOException {
		row = CucumberUtils.convert(dataTable);
		payBillhelper.getEmailList(row);
		LOGGER.info("fetched required paymybill users from DB");

	}

	
    
	@When("^I launch Catalyst Website$")
	public void user_launch_Catalyst_WebSite() throws Throwable {
		homepage.launchHomePage();	
		LOGGER.info("Launched catalyst home page");
	}
	@And("^performs the paymybill sign in with the fetched user$")
	public void Paybill_Page_Enter_valid_email_password() throws Throwable {		
		payBillhelper.ResetPwd();
		homepage.clickPayBillLink();
		comRenew.Sign_In_Paybill((String) tUtil.getFromSession("email"), pwd);
		LOGGER.info("performed paymybill signin");

	}

	@When("^user sees required details on checkout page$")
	public void checkout_page_verification(DataTable dataTable) throws Throwable {
		row = CucumberUtils.convert(dataTable);
		comPaybill.Verify_PayBill_Page_Sections(row);
		LOGGER.info("verified checkout page details");

	}

	@When("^user performs payment$")
	public void Payment_details_and_submit_payment(DataTable dataTable) throws Throwable {
		row = CucumberUtils.convert(dataTable);
		comPaybill.User_checkout(row);
		LOGGER.info("User performed payment");

	}

	@Then("^payment confirmation page should display all the valid information$")
	public void verify_payment_confirmation_page_messages(DataTable dataTable) throws Throwable {
		row = CucumberUtils.convert(dataTable);
		comOrder.verify_payment_confirmation_messages(row);
		LOGGER.info("Verified payment confirmation page");

	}
	

}
