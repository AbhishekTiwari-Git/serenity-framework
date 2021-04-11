package ucc.com.pages.ui;

import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucc.i.method.accountexp.AccountExpGET;
import ucc.i.method.aic.AICGET;
import ucc.i.method.aic.AICPATCH;
import ucc.utils.ConnectionFactory;
import ucc.utils.DbUtils;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComPayBillHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComPayBillHelper.class);
	private List<String> emailList = new ArrayList<>();
	private TestUtils tUtil = new TestUtils();
	public static Map<String, String> kmap = new HashMap<String, String>();
	public static Map<String, String> row = new HashMap<String, String>();
	static String end_pt = null;
	static String ucid = null;
	private static final DbUtils dbUtils = new DbUtils();
	public static Response expResp = null;
	public static Response sysResp = null;
	private static String endPt = null;
	private static Response aicResp = null;
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String pwd = EnvironmentSpecificConfiguration.from(env_var).getProperty("password");

	ComRenew comRenew = new ComRenew();

	ComPaybill comPaybill = new ComPaybill();

	ComOrderConfirmation comOrder = new ComOrderConfirmation();

	ComPaymentConfirmation comPayment = new ComPaymentConfirmation();

	AccountExpGET AccExpget = new AccountExpGET();

	AICGET aicGet = new AICGET();

	AICPATCH aicpatch = new AICPATCH();

	public void getEmailList(Map<String, String> row) throws IOException, SQLException {
		String PayBillSQL = null;
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dateTo = localDate.format(formatter);
		switch (row.get("Paybill_user_type")) {

		case "annual-NoAR-billed":
			switch (row.get("Country")) {

			case "Canada":

				PayBillSQL = "annual-NoAR-billed-CAN.sql";
				break;

			case "India":

				PayBillSQL = "annual-NoAR-billed-IND.sql";
				break;

			case "MA":

				PayBillSQL = "annual-NoAR-billed-MA.sql";
				break;

			case "VT":

				PayBillSQL = "annual-NoAR-billed-VT.sql";
				break;
				
			case "WA":

				PayBillSQL = "annual-NoAR-billed-WA.sql";
				break;

			case "GDPR":

				PayBillSQL = "annual-NoAR-billed-PRT-Italy-Austria.sql";
				break;

			}
			break;
		case "monthly-AR-billed-CCexpired":

			PayBillSQL = "monthly-AR-billed-CCexpired.sql";
			break;

		case "annual-NoAR-suspended":

			PayBillSQL = "annual-NoAR-suspended.sql";
			break;

		case "annual-NoAR-paid-future-billed":

			PayBillSQL = "annual-NoAR-paid-future-billed.sql";
			break;

		case "annual-NoAR-Paid-Greaterthan-3months":

			PayBillSQL = "annualNoARpaidexpgrterthan3Months.sql";
			break;

		case "annual-AR-billed-CCexpired":
			switch (row.get("Country")) {

			case "Canada":

				PayBillSQL = "annual-AR-billed-CCexpired-Canada.sql";
				break;

			case "India":

				PayBillSQL = "annual-AR-billed-CCexpired-IND.sql";
				break;
			case "MA":

				PayBillSQL = "annual-AR-billed-CCexpired-MA.sql";
				break;

			case "VT":

				PayBillSQL = "annual-AR-billed-CCexpired-VT.sql";
				break;
				
			case "WA":

				PayBillSQL = "annual-AR-billed-CCexpired-WA.sql";
				break;

			case "GDPR":

				PayBillSQL = "annual-AR-billed-CCexpired-PRT-Italy-Austria.sql";
				break;
			}
			break;

		}

		LOGGER.info("Pay bill sql is" + PayBillSQL);
		String sqlQuery = dbUtils.buildQuery(PayBillSQL);
		LOGGER.info("SQL query is" + sqlQuery);
		String dbURL = dbUtils.buildDbUrl("ACSDbUrl");
		Connection conn = ConnectionFactory.getConnectionACS(
			dbURL, dbUtils.acsDBUser(), dbUtils.acsDBPass());
		Statement ps = conn.createStatement();
		ResultSet resultSet = ps.executeQuery(sqlQuery);
		while (resultSet.next()) {
			emailList.add(resultSet.getString("adr_eml"));
		}
		
		if (emailList.size() == 0) {
			LOGGER.info("**************************************************No valid user found in the database for required paybill condition***********************************************");
		}
		tUtil.putToSession("emailList", emailList);

	}

	public void getUcid() {

		end_pt = AccExpget.setEndpointCustomers();
		expResp = AccExpget.getUserByEmail(end_pt, (String) tUtil.getFromSession("email")).extract().response();
		tUtil.verifyStatus(expResp, ResponseCode.OK);
		tUtil.putToSession("ucid", expResp.jsonPath().getString("ucId"));
		tUtil.putToSession("expResp", expResp);

	}

	public void ResetPwd() throws URISyntaxException {

		end_pt = aicpatch.setEndpointUserID((String) tUtil.getFromSession("ucid"));
		sysResp = aicpatch.updatePassword(pwd, end_pt).extract().response();
		tUtil.verifyStatus(sysResp, ResponseCode.UPDATED);
	}
}
