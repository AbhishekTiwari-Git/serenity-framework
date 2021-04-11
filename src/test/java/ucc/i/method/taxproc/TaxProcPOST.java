package ucc.i.method.taxproc;

import java.io.File;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.i.method.taxexp.TaxExpPost;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class TaxProcPOST {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint = EnvironmentSpecificConfiguration.from(env_var).getProperty("api.base.url");
	static String serviceEndpoint = EnvironmentSpecificConfiguration.from(env_var).getProperty("procthomson.basePath");
	public static String ProcThomson_url = webserviceEndpoint + serviceEndpoint + "/api";
	TestUtils tUtil = new TestUtils();
	String file_path = env_var.getProperty("json.body.path");
	String tax_ship = "ProcThomson_tax.json";
	TaxExpPost postTax = new TaxExpPost();

	public ValidatableResponse postProcTax(String endpoint, String filename) {

		String path = file_path + "/" + filename;
		File file = new File(path);

		RestUtil.setBaseURI(ProcThomson_url);

		return SerenityRest.rest().given().spec(ReuseableSpecifications.getGenericExpRequestSpec()).when().body(file)
				.log().all().post(endpoint).then().log().all();
	}

	public void updateProcTaxJson(String product, String fname, String lname, String Sstreet, String currency,
			String Scountry, String Sstate, String Scity, String Szip, String Bstreet, String Bcountry, String Bstate,
			String Bcity, String Bzip, String vatId, Double amount) throws Exception {

		postTax.updateTaxJson(product, fname, lname, Sstreet, currency, Scountry, Sstate, Scity, Szip, Bstreet,
				Bcountry, Bstate, Bcity, Bzip, vatId, amount);

	}

	public String setEndpointTax() {
		String endpoint = "/tax/calculate";
		return endpoint;
	}

}
