package ucc.i.method.nejmexperience;

import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class UpdateNejmExpFiles {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String filePath = env_var.getProperty("json.body.path");
	
    public static final String AUTO_EMAIL = EnvironmentSpecificConfiguration.from(env_var).getProperty("autoEmail");
    static TestUtils tUtil = new TestUtils();
    static JsonUtils jsonUtils = new JsonUtils();
    
    
    
    public static void update_saved_searches(String file) 
    		throws Exception{
    	
    	String name = tUtil.AppendTimestamp("NEJM Test");
    	String description = tUtil.AppendTimestamp(" Description");
    	String searchQuery = tUtil.AppendTimestamp(" SearchQuery");
    	String tags1 = tUtil.AppendTimestamp(" Tag1");
    	String tags2 = tUtil.AppendTimestamp(" Tag2");
    	
    	jsonUtils.update_JSONValue(file, "$.search.name", name);
    	jsonUtils.update_JSONValue(file, "$.search.description", description);
    	jsonUtils.update_JSONValue(file, "$.search.searchQuery", searchQuery);
    	jsonUtils.update_JSONValue(file, "$.search.tags[0]", tags1);
    	jsonUtils.update_JSONValue(file, "$.search.tags[1]", tags2);

    	tUtil.putToSession("name", name);
    	tUtil.putToSession("description", description);
    	tUtil.putToSession("searchQuery", searchQuery);
    	tUtil.putToSession("tags1", tags1);
    	tUtil.putToSession("tags2", tags2);
    }
    
    public static void update_saved_searches_empty_tags(String file) 
    		throws Exception{
    	
    	String name = tUtil.AppendTimestamp("NEJM Test");
    	String description = "";
    	String searchQuery = tUtil.AppendTimestamp(" SearchQuery");
    	String tags1 = "";
    	String tags2 = "";
    	
    	jsonUtils.update_JSONValue(file, "$.search.name", name);
    	jsonUtils.update_JSONValue(file, "$.search.description", description);
    	jsonUtils.update_JSONValue(file, "$.search.searchQuery", searchQuery);
    	jsonUtils.update_JSONValue(file, "$.search.tags[0]", tags1);
    	jsonUtils.update_JSONValue(file, "$.search.tags[1]", tags2);

    	tUtil.putToSession("name", name);
    	tUtil.putToSession("description", description);
    	tUtil.putToSession("searchQuery", searchQuery);
    	tUtil.putToSession("tags1", tags1);
    	tUtil.putToSession("tags2", tags2);
    }
    
    public static void update_saved_searches_empty_name(String file) 
    		throws Exception{
    	
    	String name = "";
    	String description = tUtil.AppendTimestamp(" Description");
    	String searchQuery = tUtil.AppendTimestamp(" SearchQuery");
    	String tags1 = tUtil.AppendTimestamp(" Tag1");
    	String tags2 = tUtil.AppendTimestamp(" Tag2");
    	
    	jsonUtils.update_JSONValue(file, "$.search.name", name);
    	jsonUtils.update_JSONValue(file, "$.search.description", description);
    	jsonUtils.update_JSONValue(file, "$.search.searchQuery", searchQuery);
    	jsonUtils.update_JSONValue(file, "$.search.tags[0]", tags1);
    	jsonUtils.update_JSONValue(file, "$.search.tags[1]", tags2);

    	tUtil.putToSession("name", name);
    	tUtil.putToSession("description", description);
    	tUtil.putToSession("searchQuery", searchQuery);
    	tUtil.putToSession("tags1", tags1);
    	tUtil.putToSession("tags2", tags2);
    }
}
