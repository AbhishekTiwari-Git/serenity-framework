package ucc.i.steps.experience;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import ucc.i.method.nejmexperience.NejmExpPOST;
import ucc.i.method.nejmexperience.UpdateNejmExpFiles;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class NejmExpSteps {

	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    private static String autoEmail =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("autoEmail");
    
    
    
    private static String endPoint = null;
    private static Response expResp = null;
    private static TestUtils tUtil = new TestUtils();
    private static JsonUtils jsonUtils = new JsonUtils();
    
    
    
    @Steps
    NejmExpPOST nejmExpPost;
    
    @Steps
    UpdateNejmExpFiles updateNejm;
    
    
    
    private final String savedSearches = "NejmExp/savedSearches.json";
    
    
    
    @When("I do POST for saved_items searches")
    public void i_do_post_activate_via_my_acc_exp_with_wrong_last_name() 
    		throws Exception {
    	
    	updateNejm.update_saved_searches(savedSearches);

    	endPoint = nejmExpPost.setEndpointSavedItemsSearches((String) tUtil.getFromSession("ucid"));
    	expResp = nejmExpPost.postToken(savedSearches, endPoint, tUtil.getToken((String) tUtil.getFromSession("ucid")))
    			.extract().response();

    	tUtil.putToSession("response", expResp);
    }
    
    @When("I do POST for saved_items searches with empty description and tags")
    public void i_do_post_for_saved_items_searches_with_empty_description_and_tags() 
    		throws Exception {

    	updateNejm.update_saved_searches_empty_tags(savedSearches);

    	endPoint = nejmExpPost.setEndpointSavedItemsSearches((String) tUtil.getFromSession("ucid"));
    	expResp = nejmExpPost.postToken(savedSearches, endPoint, tUtil.getToken((String) tUtil.getFromSession("ucid")))
    			.extract().response();

    	tUtil.putToSession("response", expResp);
    }
    
    @When("I do POST for saved_items searches with invalid token")
    public void i_do_post_for_saved_items_searches_with_invalid_token() 
    		throws Exception {

    	updateNejm.update_saved_searches(savedSearches);

    	endPoint = nejmExpPost.setEndpointSavedItemsSearches((String) tUtil.getFromSession("ucid"));
    	expResp = nejmExpPost.postToken(savedSearches, endPoint, "invalid")
    			.extract().response();

    	tUtil.putToSession("response", expResp);
    }
    
    @When("I do POST for saved_items searches with no search name")
    public void i_do_post_for_saved_items_searches_with_no_search_name() 
    		throws Exception {
    	
    	updateNejm.update_saved_searches_empty_name(savedSearches);

    	endPoint = nejmExpPost.setEndpointSavedItemsSearches((String) tUtil.getFromSession("ucid"));
    	expResp = nejmExpPost.postToken(savedSearches, endPoint, tUtil.getToken((String) tUtil.getFromSession("ucid")))
    			.extract().response();

    	tUtil.putToSession("response", expResp);
    }
    
    @When("I do POST for saved_items searches again with same tags and fields")
    public void i_do_post_for_saved_items_searches_again_with_same_tags_and_fields() {

    	endPoint = nejmExpPost.setEndpointSavedItemsSearches((String) tUtil.getFromSession("ucid"));
    	expResp = nejmExpPost.postToken(savedSearches, endPoint, tUtil.getToken((String) tUtil.getFromSession("ucid")))
    			.extract().response();

    	tUtil.putToSession("response", expResp);
    }
}
