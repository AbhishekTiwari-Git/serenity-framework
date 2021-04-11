package ucc.cr.pages.catalyst.ui.utils;

import ucc.cr.pages.catalyst.ui.CRWidgetsPage;
import ucc.cr.pages.catalyst.ui.RegInsighCouncilPage;
import ucc.utils.CucumberUtils.CucumberUtils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;

public class CRWidgetsPageHelper {

	public static Map<String, String> widget_helper_kmap = new HashMap<String, String>();
	public static Map<String, String> kMap_exp;
	private static final Logger LOGGER = LoggerFactory.getLogger(CRWidgetsPageHelper.class);
	CRWidgetsPage widget = new CRWidgetsPage();

	public boolean akamaiValidations(Response res, DataTable dt, String promoRegisteration) {
		widget_helper_kmap.clear();
		kMap_exp = CucumberUtils.convert(dt);
		if (!kMap_exp.get("Insights Council Member").equals("true")) {
			kMap_exp.put("Year of Birth", "null");
		}
		kMap_exp.put("Registeration Promo Code", promoRegisteration);
		widget_helper_kmap.put("Registeration Promo Code", widget.validatePromo_InAkamai(promoRegisteration, res));
		widget_helper_kmap.put("Audience Type", res.jsonPath().getString("audienceType"));
		widget_helper_kmap.put("Audience Segment", res.jsonPath().getString("Catalyst.audienceSegment"));
		widget_helper_kmap.put("Insights Council Qualification",
				res.jsonPath().getString("Catalyst.qualifiedForCouncil"));
		String icMember = (res.jsonPath().getString("Catalyst.insightsCouncilMember") == null) ? "null"
				: res.jsonPath().getString("Catalyst.insightsCouncilMember");
		String birthYear = (res.jsonPath().getString("birthYear") == null) ? "null"
				: res.jsonPath().getString("birthYear");
		widget_helper_kmap.put("Insights Council Member", icMember);
		widget_helper_kmap.put("Professional Category", res.jsonPath().getString("professionalCategoryCode"));
		widget_helper_kmap.put("Suffix", res.jsonPath().getString("suffix"));
		widget_helper_kmap.put("Organization", res.jsonPath().getString("organizationName"));
		widget_helper_kmap.put("Year of Birth", birthYear);
		widget_helper_kmap.put("Default IC Promo Code", widget.validateICPromo_InAkamai(
				kMap_exp.get("Insights Council Member"), res, kMap_exp.get("Default IC Promo Code")));
		LOGGER.info("===============Back End Validation : Akamai====================");
		LOGGER.info("*************Actual Values : ********************************");
		LOGGER.info(widget_helper_kmap.toString());
		LOGGER.info("-------------------------------------------------------------");
		LOGGER.info("*************Expected Values : ******************************");
		LOGGER.info(kMap_exp.toString());
		LOGGER.info("--------------------------------------------------------------");
		return widget_helper_kmap.equals(kMap_exp) ? true : false;
	}

	public boolean kinesisValidations(String member, Response res, DataTable dt) throws Exception {
		if (!member.equals("null")) {
			widget_helper_kmap.clear();
			kMap_exp.clear();
			kMap_exp = CucumberUtils.convert(dt);

			widget_helper_kmap.put("Audience Segment",
					widget.validateAudienceSegment_Kinesis(kMap_exp.get("Audience Segment"), res));
			widget_helper_kmap.put("Professional Category",
					widget.validateProfessionalCategory_Kinesis(kMap_exp.get("Professional Category"), res));
			widget_helper_kmap.put("Suffix", widget.validateSuffix_Kinesis(kMap_exp.get("Suffix"), res));
			widget_helper_kmap.put("Organization", res.jsonPath().getString("organizationName"));
			widget_helper_kmap.put("Year of Birth", res.jsonPath().getString("yearOfBirth"));
			LOGGER.info("===============Back End Validation : Kinesis====================");
			LOGGER.info("***************Actual Values : *********************************");
			LOGGER.info(widget_helper_kmap.toString());
			LOGGER.info("---------------Actual Values : --------------------------------");
			LOGGER.info("***************Expected Values : *******************************");
			LOGGER.info(kMap_exp.toString());
			LOGGER.info("----------------------------------------------------------------");
			return widget_helper_kmap.equals(kMap_exp) ? true : false;
		}
		LOGGER.info("===============Back End Validation : Kinesis====================");
		;
		LOGGER.info("Qualified and Non Qualified User does not goes in to Kinesis");
		LOGGER.info("================================================================");
		return true;
	}

	public boolean literatumValidations(Response res, DataTable dt) throws Exception {
		widget_helper_kmap.clear();
		kMap_exp.clear();
		kMap_exp = CucumberUtils.convert(dt);
		widget_helper_kmap.put("Role", widget.validateRole_Literatum(kMap_exp.get("Role"), res));
		widget_helper_kmap.put("Audience Segment",
				widget.validateAudienceSegment_Literatum(kMap_exp.get("Audience Segment"), res));
		widget_helper_kmap.put("Connect", widget.validateConnectSubscriber_Literatum(kMap_exp.get("Connect"), res));
		kMap_exp.remove("Member");
		kMap_exp.remove("Qualification");
		LOGGER.info("===============Back End Validation : Literatum====================");
		LOGGER.info("*******************Actual Values : *****************************");
		LOGGER.info(widget_helper_kmap.toString());
		LOGGER.info("----------------------------------------------------------------");
		LOGGER.info("===============Expected Values : ===========================");
		LOGGER.info(kMap_exp.toString());
		LOGGER.info("----------------------------------------------------------------");
		return widget_helper_kmap.equals(kMap_exp) ? true : false;
	}
}
