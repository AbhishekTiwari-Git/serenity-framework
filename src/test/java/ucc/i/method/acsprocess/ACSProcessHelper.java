package ucc.i.method.acsprocess;

import java.util.HashMap;
import java.util.Map;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ACSProcessHelper {
	
	private static final TestUtils tUtil = new TestUtils();
    private static final JsonUtils jUtil = new JsonUtils();
    private static final ObjectMapper mapper = new ObjectMapper();
    public static Map<String, String> kmap = new HashMap<String, String>();
    static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    
    
    
	public String getEnv() {
        
        String envVal = System.getProperty("environment");
        return envVal;
    }
	
	public String setPayBillDbfile(String environment) {
        
        String fileName = "ACSProc/" +environment+"_acsNejmUser.sql";
        return fileName;
    }
}
