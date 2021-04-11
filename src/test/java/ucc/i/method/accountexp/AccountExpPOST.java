package ucc.i.method.accountexp;

import java.io.File;

import org.json.simple.JSONObject;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.ResponseCode;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

public class AccountExpPOST {
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("api.exp.url");
	static String serviceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("accountEXpAPI");
	public static String AccountExp_url = webserviceEndpoint + serviceEndpoint + "/api";
	static String amakeiServiceEndpoint = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("akamai.basePath");
	public static String Akamai_url = webserviceEndpoint + amakeiServiceEndpoint + "/api";
	RestUtil AEA = new RestUtil(AccountExp_url);
	private JSONObject requestParams = new JSONObject();
	String file_path = env_var.getProperty("json.body.path");
	String myAccountWebApiUrl = EnvironmentSpecificConfiguration.from(env_var)
			.getProperty("myaccount.base.url").replace("/signin","");

	
	
	public ValidatableResponse post(String file_name, String endpoint) {
		String path = file_path + "/" + file_name;
		File file = new File(path);

		RestUtil.setBaseURI(AccountExp_url);

		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.body(file)
				.post(endpoint)
				.then()
				.log().all();
	}

	public ValidatableResponse postToken(String file_name, String endpoint, String token) {
		String path = file_path + "/" + file_name;
		File file = new File(path);

		RestUtil.setBaseURI(AccountExp_url);

		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.header("akamai-auth-token", token)
				.when()
				.body(file)
				.post(endpoint)
				.then()
				.log().all();
	}

	public ValidatableResponse validateToken(String ucId, String token) {

		String endpoint = "/access-token/validate";

		JSONObject requestParams = new JSONObject();
		requestParams.put("ucId", ucId);
		requestParams.put("token", token);

		RestUtil.setBaseURI(AccountExp_url);

		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.header("akamai-auth-token", token)
				.when()
				.body(requestParams)
				.post(endpoint)
				.then()
				.log().all();
	}

	public ValidatableResponse postTickets(String file_name) {

		String endpoint = "/tickets";

		String path = file_path + "/" + file_name;
		File file = new File(path);

		RestUtil.setBaseURI(AccountExp_url);

		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.body(file)
				.post(endpoint)
				.then()
				.log().all();
	}
	
	public ValidatableResponse postSsoToken(String file_name, String endpoint) {
		 
		String path = file_path + "/" + file_name;
		File file = new File(path);
		RestUtil.setBaseURI(myAccountWebApiUrl);
		
		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.header("Content-Type", "application/json")
				.when()
				.body(file)
				.post(endpoint)
				.then()
				.log().all();
	}

	public ValidatableResponse postWebHook(String endpoint, String query) {

		RestUtil.setBaseURI(Akamai_url);

		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.body(query)
				.post(endpoint)
				.then()
				.log().all();
	}
	
	public ValidatableResponse postCaptchaSettings(String file_name, String endpoint) {
		
		String path = file_path + "/" + file_name;
		File file = new File(path);
		
		RestUtil.setBaseURI(myAccountWebApiUrl);
		
		return SerenityRest.rest().given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.header("Content-Type", "application/json")
		 		.when()
				.body(file)
				.post(endpoint)
				.then()
				.log().all();
	}
	
	public ValidatableResponse institutionalActivate(String endpoint,String token, String subscrptnID) {
    	
        RestUtil.setBaseURI(AccountExp_url);
		requestParams.put("subscriptionId",subscrptnID );
		requestParams.put("institutionType","INS");

        return SerenityRest.rest()
                .given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .header("akamai-auth-token", token)
                .when()
                .log().all()
                .body(requestParams)
                .post(endpoint)
                .then()
                .assertThat().statusCode(ResponseCode.CREATED)
                .log().all();
    }
	
	public String setEndpointNewUser() {
		String endpoint = "/users";
		return endpoint;
	}

	public String setEndpointRegisterForEvent() {
		String endpoint = "/registration";
		return endpoint;
	}

	public String setEndpointRegistrations() {
		String endpoint = "/registrations";
		return endpoint;
	}

	public String setEndpoint_customersRegistrationsCatalystInsightCouncil() {
		String endpoint = "/customers/registrations/catalyst/insights-council";
		return endpoint;
	}

	public String setEndpointWebHook() {
		String endpoint = "/webhook";
		return endpoint;
	}

	public String setEndpontCustRegPDF() {
		String endpoint = "/customers/registrations/catalyst/pdf";
		return endpoint;
	}

	public String setEndpontCustRegEmail() {
		String endpoint = "/customers/registrations/catalyst/email";
		return endpoint;
	}
	
	public String setEndpontSsoUrlToken() {
		String endpoint = "/qa/generateSsoUrlToken";
		return endpoint;
	}

	public String setEndpontCustRegCatalyst() {
		String endpoint = "/customers/registrations/catalyst";
		return endpoint;
	}
	
	public String setEndpointCustRegNEJM() {
		String endpoint = "/customers/registrations/nejm";
		return endpoint;
	}

	public String setEndpontCustRegCatalystEvent() {
		String endpoint = "/customers/registrations/catalyst/event/";
		return endpoint;
	}

	public String setEndpontCustRegCatalystEvent(final String event) {
		String endpoint = "/customers/registrations/catalyst/event/" + event;
		return endpoint;
	}
	
	public String setEndpontCustRegCatalyst_IC() {
		String endpoint = "customers/registrations/catalyst/insights-council";
		return endpoint;
	}
	
	public String setEndpoint_activate(String ucid) {
		String endpoint = "customers/" + ucid +"/activate";
		return endpoint;
	}
	
	public String setEndpointCaptchaSettings() {
		String endpoint = "/qa/settings";
		return endpoint;
	}
	
	public String setEndpointInstitutionalActivate(String ucid) {
		String endpoint = "/customers/" + ucid + "/institutional-activate";
		return endpoint;
	}
}
