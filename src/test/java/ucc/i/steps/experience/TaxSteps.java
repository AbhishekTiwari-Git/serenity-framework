package ucc.i.steps.experience;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import ucc.i.method.tax.TaxPOST;
import ucc.i.method.taxexp.TaxExpPost;
import ucc.i.method.taxproc.TaxProcPOST;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

public class TaxSteps {

	TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	public static Response taxResp = null;
	static String end_pt = null;

	@Steps
	TaxExpPost taxexpPost;
	
	@Steps
	TaxProcPOST taxprocPost;

	@Steps
	TaxPOST taxsysPost;

	String tax_ship = "Tax_post_ship.json";
	String tax_sys = "SysTax_post_ship.json";
	String tax_proc = "ProcThomson_tax.json";

	@Then("^user check status and total tax$")
	public void verifyWriteTax() {
		taxexpPost.userCheckStatusAndTotalTax(taxResp);
	}

	@When("^user sends a request to calculate tax with shipTo and (.*) (.*) (.*) (.*) (.*) (.*) (.*) \"(.*)\" \"(.*)\" (.*) (.*) (.*) \"(.*)\" \"(.*)\" (.*) (.*)$")
	public void userSendsAPOSTRequestToCalculateTaxWithShipToAndProductFnameLnameStreetCountryStateCityZipVatIdWithoutShipTo(
			String product, String fname, String lname, String Sstreet, String currency, String Scountry, String Sstate,
			String Scity, String Szip, String Bstreet, String Bcountry, String Bstate, String Bcity, String Bzip,
			String vatId, Double amount) throws Throwable {

		stateTaxReq(product, fname, lname, Sstreet, currency, Scountry, Sstate, Scity, Szip, Bstreet, Bcountry, Bstate,
				Bcity, Bzip, vatId, amount, tax_ship);

		taxexpPost.updateTaxJson(product, fname, lname, Sstreet, currency, Scountry, Sstate, Scity, Szip, Bstreet,
				Bcountry, Bstate, Bcity, Bzip, vatId, amount);

		end_pt = taxexpPost.setEndpointTax();
		taxResp = taxexpPost.postTax(end_pt, tax_ship).extract().response();

	}

	@When("^user sends a sys-thomson request to calculate tax (.*) (.*) (.*) (.*) (.*) (.*) (.*) \"(.*)\" \"(.*)\" (.*) (.*) (.*) \"(.*)\" \"(.*)\" (.*) (.*)$")
	public void systaxreq(String product, String fname, String lname, String Sstreet, String currency, String Scountry,
			String Sstate, String Scity, String Szip, String Bstreet, String Bcountry, String Bstate, String Bcity,
			String Bzip, String vatId, Double amount) throws Throwable {

		stateTaxReq(product, fname, lname, Sstreet, currency, Scountry, Sstate, Scity, Szip, Bstreet, Bcountry, Bstate,
				Bcity, Bzip, vatId, amount, tax_sys);

		taxsysPost.updateSysTaxJson(product, fname, lname, Sstreet, currency, Scountry, Sstate, Scity, Szip, Bstreet,
				Bcountry, Bstate, Bcity, Bzip, vatId, amount);

		end_pt = taxsysPost.setEndpoint();
		taxResp = taxsysPost.post(end_pt, tax_sys).extract().response();

	}

	@When("^user sends a proc-thomson request to calculate tax with shipTo and (.*) (.*) (.*) (.*) (.*) (.*) (.*) \"(.*)\" \"(.*)\" (.*) (.*) (.*) \"(.*)\" \"(.*)\" (.*) (.*)$")
	public void proctaxreq(String product, String fname, String lname, String Sstreet, String currency,
			String Scountry, String Sstate, String Scity, String Szip, String Bstreet, String Bcountry, String Bstate,
			String Bcity, String Bzip, String vatId, Double amount) throws Throwable {
		
		stateTaxReq(product, fname, lname, Sstreet, currency, Scountry, Sstate, Scity, Szip, Bstreet, Bcountry, Bstate,
				Bcity, Bzip, vatId, amount, tax_proc);
		
		taxprocPost.updateProcTaxJson(product, fname, lname, Sstreet, currency, Scountry, Sstate, Scity, Szip, Bstreet,
				Bcountry, Bstate, Bcity, Bzip, vatId, amount);
		
		end_pt = taxprocPost.setEndpointTax();
		taxResp = taxprocPost.postProcTax(end_pt, tax_proc).extract().response();

	}

	public void stateTaxReq(String product, String fname, String lname, String Sstreet, String currency,
			String Scountry, String Sstate, String Scity, String Szip, String Bstreet, String Bcountry, String Bstate,
			String Bcity, String Bzip, String vatId, Double amount, String jsonName) throws Throwable {

		if (Scountry.equals("CAN")) {
			jsonUtils.remove_JSONPath(jsonName, "$.invoices[0].shipTo.state");
			jsonUtils.add_JSONPathJsonValue(jsonName, "$.invoices[0].shipTo.province", Sstate);

			jsonUtils.remove_JSONPath(jsonName, "$.invoices[0].lineItems[0].shipTo.state");
			jsonUtils.add_JSONPathJsonValue(jsonName, "$.invoices[0].lineItems[0].shipTo.province", Sstate);

			jsonUtils.remove_JSONPath(jsonName, "$.invoices[0].billTo.state");
			jsonUtils.add_JSONPathJsonValue(jsonName, "$.invoices[0].billTo.province", Bstate);
		} else {
			jsonUtils.remove_JSONPath(jsonName, "$.invoices[0].shipTo.province");
			jsonUtils.add_JSONPathJsonValue(jsonName, "$.invoices[0].shipTo.state", Sstate);

			jsonUtils.remove_JSONPath(jsonName, "$.invoices[0].lineItems[0].shipTo.province");
			jsonUtils.add_JSONPathJsonValue(jsonName, "$.invoices[0].lineItems[0].shipTo.state", Sstate);

			jsonUtils.remove_JSONPath(jsonName, "$.invoices[0].billTo.province");
			jsonUtils.add_JSONPathJsonValue(jsonName, "$.invoices[0].billTo.state", Bstate);
		}

	}

}
