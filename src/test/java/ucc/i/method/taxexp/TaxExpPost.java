package ucc.i.method.taxexp;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.JsonUtils;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;
import ucc.utils.TestUtils;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;

public class TaxExpPost {
    static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
    static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.exp.url");
    static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("taxAPI.basePath");
    public static String TaxApi_url = webserviceEndpoint+serviceEndpoint+"/api";
    TestUtils tUtil = new TestUtils();
    JsonUtils jsonUtils = new JsonUtils();
    String file_path = env_var.getProperty("json.body.path");
    String tax_ship = "Tax_post_ship.json";

    public ValidatableResponse postTax(String endpoint, String filename) {

        String path = file_path + "/" + filename;
        File file = new File(path);

        RestUtil.setBaseURI(TaxApi_url);

        return SerenityRest.rest()
                .given()
                .spec(ReuseableSpecifications.getGenericExpRequestSpec())
                .when()
                .body(file)
                .log().all()
                .post(endpoint)
                .then()
                .log().all();
    }


    public void updateTaxJson(String product, String fname, String lname,
            String Sstreet, String currency, String Scountry, String Sstate, String Scity, String Szip,
            String Bstreet, String Bcountry, String Bstate, String Bcity, String Bzip,
            String vatId, Double amount) throws Exception {
    	
    	if(!vatId.equals("")) {
            jsonUtils.add_JSONPathJsonValue(tax_ship, "$.invoices[0].lineItems[0].vatId", vatId);
        }
    	else {
    		jsonUtils.add_JSONPathJsonValue(tax_ship, "$.invoices[0].lineItems[0].vatId", "");
    	}

        jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].invoiceId", tUtil.generateRandomUcid(10));
        jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].currency", currency);
        jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].custName", fname + " "  +lname);
        jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].shipTo.city", Scity);
        
        if(Scountry.equals("")) {
        	jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].shipTo.state", "");
        }
        else if(Scountry.equals("CAN")) {
            checkProvince(Sstate, "shipTo");
        } else {
            checkState(Sstate, "shipTo");
        }
        jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].shipTo.country", Scountry);
        jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].shipTo.postcode", Szip);

        // Bill to
    //    jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].billTo.address1", Bstreet);
        jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].billTo.city", Bcity);
        if(Bcountry.equals("")) {
        	jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].billTo.state", "");
        }
        else if(Bcountry.equals("CAN")) {
            checkProvince(Bstate, "billTo");
        } else {
            checkState(Bstate, "shipTo");
        }
        jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].billTo.country", Bcountry);
        jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].billTo.postcode", Bzip);

   //     jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].lineItems[0].shipTo.address1", Sstreet);
        jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].lineItems[0].shipTo.city", Scity);
        
        if(Scountry.equals("")) {
        	jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].lineItems[0].shipTo.state", "");
        }
        else if(Scountry.equals("CAN")) {
            jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].lineItems[0].shipTo.province", Sstate);
        } else {
            jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].lineItems[0].shipTo.state", Sstate);
        }
        jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].lineItems[0].shipTo.country", Scountry);
        jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].lineItems[0].shipTo.postcode", Szip);
        jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].lineItems[0].productCode", product);


        jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].lineItems[0].total", amount);
        jsonUtils.update_JSONValue(tax_ship, "$.invoices[0].lineItems[0].unitPrice", amount);
        jsonUtils.add_JSONPathJsonValue(tax_ship, "$.source", "NEJMCDF");


    }

    private void checkState(String state, String path) throws Exception {
        boolean isState = jsonUtils.isPathExists(tax_ship, "$.invoices[0]." + path + ".state");
        if (!isState) {
            jsonUtils.remove_JSONPath(tax_ship, "$.invoices[0]." + path + ".province");
        }
        jsonUtils.add_JSONPathJsonValue(tax_ship, "$.invoices[0]." + path + ".state", state);
    }

    private void checkProvince(String state, String path) throws Exception {
        boolean isProvince = jsonUtils.isPathExists(tax_ship, "$.invoices[0]." + path + ".province");
        if (!isProvince) {
            jsonUtils.remove_JSONPath(tax_ship, "$.invoices[0]." + path + ".state");
        }
        jsonUtils.add_JSONPathJsonValue(tax_ship, "$.invoices[0]." + path + ".province", state);
    }

    public void userCheckStatusAndTotalTax(Response resp) {
        Map<String, String> map = new LinkedHashMap<>();
        JsonPath jsonPathEvaluator = resp.jsonPath();
        Boolean status = jsonPathEvaluator.getBoolean("status.isSuccess");
        Assert.assertEquals(status, true);

        map.put("product", jsonUtils.getFromJSON(tax_ship, "$.invoices[0].lineItems[0].productCode"));

        map.put("Fname", jsonUtils.getFromJSON(tax_ship, "$.invoices[0].custName").split(" ")[0]);
        map.put("Lname", jsonUtils.getFromJSON(tax_ship, "$.invoices[0].custName").split(" ")[1]);

        map.put("Sstree", jsonUtils.getFromJSON(tax_ship, "$.invoices[0].shipTo.address1"));
        map.put("Scity", jsonUtils.getFromJSON(tax_ship, "$.invoices[0].shipTo.city"));
        
        if(jsonUtils.getFromJSON(tax_ship, "$.invoices[0].shipTo.country").equals("CAN")) {
            map.put("Sstate", jsonUtils.getFromJSON(tax_ship, "$.invoices[0].shipTo.province"));
        }
        else {
            map.put("Sstate", jsonUtils.getFromJSON(tax_ship, "$.invoices[0].shipTo.state"));
        }
        map.put("Scountry", jsonUtils.getFromJSON(tax_ship, "$.invoices[0].shipTo.country"));
        map.put("Szip", jsonUtils.getFromJSON(tax_ship, "$.invoices[0].shipTo.postcode"));

        map.put("Bstree", jsonUtils.getFromJSON(tax_ship, "$.invoices[0].billTo.address1"));
        map.put("Bcity", jsonUtils.getFromJSON(tax_ship, "$.invoices[0].billTo.city"));
        
        if(jsonUtils.getFromJSON(tax_ship, "$.invoices[0].billTo.country").equals("CAN")) {
            map.put("Bstate", jsonUtils.getFromJSON(tax_ship, "$.invoices[0].billTo.province"));
        }
        else {
            map.put("Bstate", jsonUtils.getFromJSON(tax_ship, "$.invoices[0].billTo.state"));
        }
        
        map.put("Bcountry", jsonUtils.getFromJSON(tax_ship, "$.invoices[0].billTo.country"));
        map.put("Bzip", jsonUtils.getFromJSON(tax_ship, "$.invoices[0].billTo.postcode"));

        map.put("vatId", jsonUtils.getFromJSON(tax_ship, "$.invoices[0].lineItems[0].vatId"));
        map.put("amount", jsonUtils.getFromJSON(tax_ship, "$.invoices[0].lineItems[0].total"));
        map.put("totalTax", String.valueOf(jsonPathEvaluator.getDouble("invoices.totalTax[0]")));
        map.put("taxRate", String.valueOf(jsonPathEvaluator.getDouble("invoices[0].lineItems[0].summary.taxRate")));
       
        tUtil.writeTaxExcelFile(map);

    }


    public String setEndpointTax() {
        String endpoint = "/calculate-tax";
        return endpoint;
    }
}
