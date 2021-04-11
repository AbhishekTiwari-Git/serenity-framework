package ucc.i.method.nejmmediacenterprocess;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class NejmMediaCenterProcessGET {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String apiUrl = EnvironmentSpecificConfiguration.from(env_var).getProperty("api.base.url");
	static String servicePath = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("nejmmediacenterproc.basePath");

	public static String mediacenterProcUrl = apiUrl + servicePath + "/api";

	TestUtils tUtil = new TestUtils();

	public ValidatableResponse getprocmedia(String endpoint, String queryParam, String queryParam1) {

		RestUtil.setBaseURI(mediacenterProcUrl);

		return SerenityRest.rest().
				given().
				spec(ReuseableSpecifications.getGenericExpRequestSpec()).
				when()
				.queryParam("codeType", queryParam).queryParam("codeDesc", queryParam1).
				log().all().get(endpoint).
				then()
				.log().all();

	}

	public ValidatableResponse getprocall(String endpoint, String queryParam, String queryParam1) {

		RestUtil.setBaseURI(mediacenterProcUrl);

		return SerenityRest.rest().given().spec(ReuseableSpecifications.getGenericExpRequestSpec()).when()
				.queryParam(queryParam, queryParam1).log().all().get(endpoint).then().log().all();

	}

	public String setDynamicprocEndpoint(String de)

	{
		String endpoint = "/" + de;
		return endpoint;

	}

}
