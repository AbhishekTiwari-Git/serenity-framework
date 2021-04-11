package ucc.com.pages.ui;

import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ucc.com.steps.ComSqlQueryFile;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComCJRenewHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComCJRenewHelper.class);
	private List<String> emailList = new ArrayList<>();
	private TestUtils tUtil = new TestUtils();
	public static Map<String, String> kmap = new HashMap<String, String>();
	public static Map<String, String> row = new HashMap<String, String>();
	static String end_pt = null;
	static String ucid = null;
	private static final DbUtils dbUtils = new DbUtils();
	public static Response expResp = null;
	public static Response sysResp = null;
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
		String CJRenewSQL = null;
		switch (row.get("Renew_user_type")) {

		case "annual-NoAR-Paid-Greaterthan-3months":
			switch (row.get("Country")) {

			case "Canada":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Paid_Exp_greater3Months_Canada;
				break;

			case "India":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Paid_Exp_greater3Months_India;
				break;

			case "MA":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Paid_Exp_greater3Months_MA;
				break;

			case "VT":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Paid_Exp_greater3Months_VT;
				break;

			case "WA":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Paid_Exp_greater3Months_WA;
				break;

			case "GDPR":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Paid_Exp_greater3Months_PrtAusItaly;
				break;
			}

			break;
			
		case "Annual_NoAR_Paid_Exp_lessthan3Months":
			switch (row.get("Country")) {

			case "Canada":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Paid_Exp_lessthan3Months_Canada;
				break;

			case "India":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Paid_Exp_lessthan3Months_India;
				break;

			case "MA":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Paid_Exp_lessthan3Months_MA;
				break;

			case "VT":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Paid_Exp_lessthan3Months_VT;
				break;

			case "WA":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Paid_Exp_lessthan3Months_WA;
				break;

			case "GDPR":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Paid_Exp_lessthan3Months_PrtAusItaly;
				break;
			}

			break;
			
		case "Annual_NoAR_Paid_futurePaid_greater3Months":
			switch (row.get("Country")) {

			case "Canada":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Paid_futurePaid_greater3Months_Canada;
				break;

			case "India":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Paid_futurePaid_greater3Months_India;
				break;

			case "MA":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Paid_futurePaid_greater3Months_MA;
				break;

			case "VT":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Paid_futurePaid_greater3Months_VT;
				break;

			case "WA":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Paid_futurePaid_greater3Months_WA;
				break;

			case "GDPR":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Paid_futurePaid_greater3Months_PrtAusItaly;
				break;
			}

			break;
			
		case "Annual_NoAR_Exp_lessthan6Months":
			switch (row.get("Country")) {

			case "Canada":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Exp_lessthan6Months_Canada;
				break;

			case "India":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Exp_lessthan6Months_India;
				break;

			case "MA":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Exp_lessthan6Months_MA;
				break;

			case "VT":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Exp_lessthan6Months_VT;
				break;

			case "WA":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Exp_lessthan6Months_WA;
				break;

			case "GDPR":

				CJRenewSQL = ComSqlQueryFile.Annual_NoAR_Exp_lessthan6Months_PrtAusItaly;
				break;
			}

			break;

		}

		LOGGER.info("Renew sql is" + CJRenewSQL);
		String sqlQuery = dbUtils.buildQuery(CJRenewSQL);
		LOGGER.info("SQL query is" + sqlQuery);
		String dbURL = dbUtils.buildDbUrl("ACSDbUrl");
		Connection conn = ConnectionFactory.getConnectionACS(dbURL, dbUtils.acsDBUser(), dbUtils.acsDBPass());
		Statement ps = conn.createStatement();
		ResultSet resultSet = ps.executeQuery(sqlQuery);
		while (resultSet.next()) {
			emailList.add(resultSet.getString("adr_eml"));
		}

		if (emailList.size() == 0) {
			LOGGER.info(
					"**************************************************No valid user found in the database for required paybill condition***********************************************");
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
