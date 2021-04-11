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

public class NejmMediaCenterExpPOST {
	
	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
    static String apiUrl =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.exp.url");
    static String servicePath =  EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("accountEXpAPI");
	
	public static String mediacenterExpUrl = apiUrl+servicePath+"/api";
	
	TestUtils tUtil = new TestUtils();
	String file_path = env_var.getProperty("json.body.path");

	public ValidatableResponse postexpMediaSubscription(String endpoint, String filename) {

		String path = file_path + "/" + filename;
		File file = new File(path);

		RestUtil.setBaseURI(mediacenterExpUrl);

		return SerenityRest.rest().
				given().spec(ReuseableSpecifications.getGenericExpRequestSpec()).
				when().body(file).log()
				.all().
				post(endpoint).
				then().log().all();
	}
	
	public String setNejmMediaSubscriptions() {

		String endpoint = "/media-center/subscriptions";
		return endpoint;

	}

	public String setNejmMediaapplications() {

		String endpoint = "/media-center/applications";
		return endpoint;

	}
	
	

}
