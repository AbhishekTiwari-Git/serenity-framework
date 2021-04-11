package ucc.i.method.literatum;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.junit.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LiteratumGET {
    static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
    static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.base.url");
    static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("literatum.basePath");
    public static String literatum_url = webserviceEndpoint+serviceEndpoint+"/api";
    TestUtils tUtil = new TestUtils();
    RestUtil literatum = new RestUtil(literatum_url);
    public static Response lit_RespIdentity = null;
    public static Response lit_RespLicense = null;
    static String end_pt = null;

    public String setEndpointIdentity(String ucid) {
        String endpoint = "/identities/" + ucid;
        return endpoint;
    }
    
    public String setEndpointPersonIdentity(String ucid) {
        String endpoint = "/identities/person/" + ucid;
        return endpoint;
    }

    public String setEndpointLicenses(String ucid) {
        String endpoint = "/identities/" + ucid + "/licenses";
        return endpoint;
    }
    
    public String setEndpointInstitutionIdentities(String inid) {
        String endpoint = "/identities/institution/" + inid;
        return endpoint;
    }
    
    public String setEndpointInstitutionLicense(String inid) {
        String endpoint = "/identities/institution/" + inid+"/licenses";
        return endpoint;
    }

    public ValidatableResponse get(String endpoint) {

        RestUtil.setBaseURI(literatum_url);

        return SerenityRest.rest()
                .given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .when()
                .log().all()
                .get(endpoint)
                .then().log().all();

    }

    public boolean Verify_license_and_tag_information(Map<String,String> kmap, Response respIdentity) {

        boolean tagCodeTrue = false;
        boolean tagLabelTrue = false;
        List<Map<String, String>> maps = respIdentity.jsonPath().getList("tag");
        for(Map<String, String> map: maps) {
            tagCodeTrue = map.containsValue("subscriber");
            tagLabelTrue = map.containsValue("Subscriber");
            if (tagCodeTrue && tagLabelTrue) {
                break;
            }
        }

        String firstName = kmap.get("fname") != null ? kmap.get("fname") : kmap.get("firstName");
        String lastName = kmap.get("fname") != null ? kmap.get("lname") : kmap.get("lastName");
        boolean emailTrue = respIdentity.jsonPath().getString("email").equals(kmap.get("email"));
        boolean fNameTrue = respIdentity.jsonPath().getString("firstName").equals(firstName);
        boolean lNameTrue = respIdentity.jsonPath().getString("lastName").equals(lastName);
 
        
        return emailTrue && fNameTrue && lNameTrue && tagCodeTrue && tagLabelTrue;
    }
    
    public boolean Verify_license_and_tag_information_Magento_Comp_Order(Response respIdentity) {

        boolean tagCodeTrue = false;
        boolean tagLabelTrue = false;
        List<Map<String, String>> maps = respIdentity.jsonPath().getList("tag");
        for(Map<String, String> map: maps) {
            tagCodeTrue = map.containsValue("complimentary");
            tagLabelTrue = map.containsValue("Complimentary");
            if (tagCodeTrue && tagLabelTrue) {
                break;
            }
        }
   
        return  tagCodeTrue && tagLabelTrue;
    }
    
    public boolean Verify_license_and_tag_information_SI_paybill_user(Map<String,String> kmap, Response respIdentity) {

    	 boolean tagCodepaidTrue = false;
         boolean tagLabelpaidTrue = false;
         
         List<Map<String, String>> maps = respIdentity.jsonPath().getList("tag");
         for(Map<String, String> map: maps) {
        	 tagCodepaidTrue = map.containsValue("paid");
        	 tagLabelpaidTrue = map.containsValue("Paid");
        	 
             if (tagCodepaidTrue && tagLabelpaidTrue) {
                 break;
             }
         }
         
         return  tagCodepaidTrue && tagLabelpaidTrue;
    }
    
    public boolean Verify_tag_information_registered_user(Map<String,String> kmap, Response respIdentity) {

    	 boolean tagCodeTrue = false;
         boolean tagLabelTrue = false;
        
        List<Map<String, String>> maps = respIdentity.jsonPath().getList("tag");
        for(Map<String, String> map: maps) {
        	tagCodeTrue = map.containsValue("registered-user");
        	tagLabelTrue = map.containsValue("Registered User");
       	 
            if (tagCodeTrue && tagLabelTrue) {
                break;
            }
        }
        
        return  tagCodeTrue && tagLabelTrue;
   }
}
