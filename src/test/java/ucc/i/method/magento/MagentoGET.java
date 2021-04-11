package ucc.i.method.magento;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

public class MagentoGET {
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("megento.basePath");
	public static String magento_url = webserviceEndpoint+serviceEndpoint+"/api"; 
	String file_path = env_var.getProperty("json.body.path");
	TestUtils tUtil = new TestUtils();
	RestUtil Magento = new RestUtil(magento_url);
	
	 public ValidatableResponse get(String endpoint) throws Exception {
		 return SerenityRest.rest()
				.given()
		    	.spec(ReuseableSpecifications.getGenericExpRequestSpec())
		    	.when()
		    	.log().all()
		    	.get(magento_url + endpoint)
		    	.then()
		    	.log().all();
	 }
	 
	 public String setEndpointOrderHistory(String fromDate, String toDate, String curPage, String pgSize) {
		 String endpoint = String.format("/orderhistory?fromDate=%s&toDate=%s&currentPage=%s&pageSize=%s",
				 fromDate, toDate, curPage, pgSize);
		 return endpoint;
	 }
	 
}
