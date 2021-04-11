package ucc.utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import static org.hamcrest.Matchers.*;

import java.net.URI;
import java.util.concurrent.TimeUnit;

public class ReuseableSpecifications {
	
	
	static EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
 	public static ResponseSpecBuilder respec;
 	public static ResponseSpecification responseSpecification;
	
	public static RequestSpecBuilder rspec;
	public static RequestSpecification requestSpecification;
	
	
	public static RequestSpecification getGenericRequestSpec(){
		
		rspec = new RequestSpecBuilder();
		rspec.setContentType(ContentType.JSON);
		requestSpecification = rspec.build();
		return requestSpecification;
		
	}
	
	public static RequestSpecification getAkamaiQaRequestSpec() {
	  rspec = new RequestSpecBuilder();
    rspec.setContentType(ContentType.JSON);
    rspec.setAuth(RestAssured.preemptive().basic("tky8cfpxvxuxvz5jzk7hkfrt8vdd5zr7", "xwsxe6gtnra3f6qxp4curr25wp794ng7"));
    requestSpecification = rspec.build();
    return requestSpecification;
	}
	
	public static RequestSpecification getAkamaiDevRequestSpec(){
		
		rspec = new RequestSpecBuilder();
		rspec.setContentType(ContentType.JSON);
		rspec.setAuth(RestAssured.preemptive().basic("x6yabstzh4ddnur8cegys27va4dgmf2k", "2jnaxx39kv437xht2jn9t2dawkqxadc7"));
 		requestSpecification = rspec.build();
		return requestSpecification;
		
	}
	
	public static RequestSpecification getAkamaiProdRequestSpec(){
    
    rspec = new RequestSpecBuilder();
    rspec.setContentType(ContentType.JSON);
    rspec.setAuth(RestAssured.preemptive().basic("vgp7zkfrx744kbfb3cc723raszjmrnd7", "kn5wn5cdrhq5cbfg7nttvbm6qsk8c5us"));
    requestSpecification = rspec.build();
    return requestSpecification;
    
  }

	 public static RequestSpecification getGenericJiraRequestSpec(){
		 		
		 		rspec = new RequestSpecBuilder();
		 		rspec.setContentType(ContentType.JSON);
		 		rspec.setAuth(RestAssured.preemptive().basic(System.getProperty("jira.username"), System.getProperty("jira.password")));
		 		rspec.addHeader("X-Atlassian-Token","no-check");
		 		requestSpecification = rspec.build();
		 		return requestSpecification;
		 		
		 	}
	
	 public static RequestSpecification getGenericExpRequestSpec(){
	 		
	 		rspec = new RequestSpecBuilder();
	 		rspec.setContentType(ContentType.JSON);
	 		rspec.addHeader("Content-Type","application/json;charset=UTF-8");
	 		rspec.addHeader("client_id",System.getProperty("client.id"));
	 		rspec.addHeader("client_secret",System.getProperty("client.secret"));
	 		requestSpecification = rspec.build();
	 		return requestSpecification;
	 		
	 	}
	
	public static RequestSpecification getGenericRequestSpec(URI url){
	
		requestSpecification = new RequestSpecBuilder().setBaseUri(url).build();
		rspec.setContentType(ContentType.JSON);
		requestSpecification = rspec.build();
		return requestSpecification;
		
	}
	
	public static ResponseSpecification getGenericResponseSpec(){
		respec = new ResponseSpecBuilder();
		respec.expectHeader("Content-Type","application/json;charset=UTF-8");
		respec.expectHeader("Transfer-Encoding","chunked");
		respec.expectResponseTime(lessThan(5L),TimeUnit.SECONDS);
		responseSpecification = respec.build();
		return responseSpecification;
		
	}
	
	 public static RequestSpecification getGenericNewRelicRequestSpec(){
	 		
	 		rspec = new RequestSpecBuilder();
	 		rspec.setContentType(ContentType.JSON);
	 		rspec.addHeader("Content-Encoding", "application/gzip");
	 		rspec.addHeader("Content-Type", "application/json");
	 		rspec.addHeader("X-Insert-Key", variables.getProperty("newrelic.token"));
	 		requestSpecification = rspec.build();
	 		return requestSpecification;
	 		
	 	}
	
}
