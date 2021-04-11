package ucc.i.method.nejmmediacenterexp;

import java.io.File;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class NejmMediaCenterExpPATCH {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String apiUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("api.exp.url");
	static String servicePath = EnvironmentSpecificConfiguration.from(env_var).getProperty("accountEXpAPI");

	public static String mediacenterExpUrl = apiUrl + servicePath + "/api";

	TestUtils tUtil = new TestUtils();
	String file_path = env_var.getProperty("json.body.path");

	public ValidatableResponse patchexpMediaFirstClassSubscription(String endpoint, String filename) {

		String path = file_path + "/" + filename;
		File file = new File(path);

		RestUtil.setBaseURI(mediacenterExpUrl);

		return SerenityRest.rest().
				given().spec(ReuseableSpecifications.getGenericExpRequestSpec()).
				when().
				body(file).
				log().all().
				patch(endpoint).
				then().log().all();
	}

	public String setNejmMediafirstclasssubscriptions(String custnum) {

		String endpoint = "/media-center/first-class-subscription/"+custnum;
		return endpoint;

	}
	
	
	public String setNejmMediapocdata(String pocid) {

		String endpoint = "/media-center/poc-data/"+pocid;
		return endpoint;

	}
	
	
	
	
}
