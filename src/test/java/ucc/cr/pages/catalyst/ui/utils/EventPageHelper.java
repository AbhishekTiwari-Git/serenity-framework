package ucc.cr.pages.catalyst.ui.utils;

import ucc.cr.pages.catalyst.ui.CRWidgetsPage;
import ucc.cr.pages.catalyst.ui.RegInsighCouncilPage;
import ucc.utils.TestUtils;
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

public class EventPageHelper {

	public static Map<String, String> event_helper_kmap = new HashMap<String, String>();
	public static Map<String, String> kMap_exp;
	private static final Logger LOGGER = LoggerFactory.getLogger(EventPageHelper.class);
	CRWidgetsPage widget = new CRWidgetsPage();
	TestUtils tUtil = new TestUtils();

	public boolean akamaiValidations(Response res, DataTable dt) {
		event_helper_kmap.clear();
		kMap_exp = CucumberUtils.convert(dt);
		event_helper_kmap.put("Audience Type", res.jsonPath().getString("audienceType"));
		event_helper_kmap.put("Audience Segment", res.jsonPath().getString("Catalyst.audienceSegment"));
		event_helper_kmap.put("IC Qualification", res.jsonPath().getString("Catalyst.qualifiedForCouncil"));
		String icMember = (res.jsonPath().getString("Catalyst.insightsCouncilMember") == null) ? "null"
				: res.jsonPath().getString("Catalyst.insightsCouncilMember");
		event_helper_kmap.put("IC Member", icMember);
		event_helper_kmap.put("Professional Category", res.jsonPath().getString("professionalCategoryCode"));
		event_helper_kmap.put("Alternate Id", getAlternateId(res, kMap_exp));
		String uuid = (res.jsonPath().getString("uuid") == null) ? "Not-Present" : "Present";
		event_helper_kmap.put("UUID", uuid);
		event_helper_kmap.put("Event Name", getEventName(res));
		event_helper_kmap.put("Original Brand", res.jsonPath().getString("originalBrand"));
		event_helper_kmap.put("Subscription Status", res.jsonPath().getString("Catalyst.subscriptionStatus"));
		kMap_exp.put("Event Name", (String) tUtil.getFromSession("Event Name"));
		
		LOGGER.info("===============Back End Validation : Akamai====================");
		LOGGER.info("*************Actual Values : ********************************");
		LOGGER.info(event_helper_kmap.toString());
		LOGGER.info("-------------------------------------------------------------");
		LOGGER.info("*************Expected Values : ******************************");
		LOGGER.info(kMap_exp.toString());
		LOGGER.info("--------------------------------------------------------------");
		return event_helper_kmap.equals(kMap_exp) ? true : false;
	}

	private String getAlternateId(Response res, Map<String, String> kMap_exp) {
		String alternateIdActual= res.jsonPath().getString("alternateID.IDType");
		String alternateIDExp = kMap_exp.get("Alternate Id");
		return (alternateIdActual.contains(alternateIDExp) ? alternateIDExp : alternateIdActual);
	}
	
	private String getEventName(Response res) {
		String eventNameActual = res.jsonPath().getString("Catalyst.events.name");
		String expEventName =  (String) tUtil.getFromSession("Event Name");
		return (eventNameActual.contains(expEventName)) ? expEventName : eventNameActual;
		
	}
}
