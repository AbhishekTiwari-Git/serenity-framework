package ucc.i.steps;

import java.io.IOException;
import org.openqa.selenium.WebDriver;


import io.restassured.response.Response;
import net.thucydides.core.annotations.Managed;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import ucc.utils.JiraConnect;

public class JiraSteps {
	public static String sToken=null;
	static String resp=null;
	static Response response = null;
	EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
    JiraConnect jiraConct = new JiraConnect();
	
	
	
	
	@Managed                                                            
	WebDriver driver;


	public void verify_site() throws InterruptedException, IOException{
	//	jiraConct.jiraInit(Integer.parseInt(variables.getProperty("projectId")),Integer.parseInt(variables.getProperty("versionId")));
		jiraConct.readIssueDetails();
		jiraConct.getFeatureDetails();
		
	}
	
}
