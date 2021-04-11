package ucc.i.method.literatum.utils;

import io.restassured.path.json.JsonPath;
import net.thucydides.core.annotations.Steps;
import ucc.i.method.literatum.LiteratumGET;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.restassured.response.Response;
import ucc.utils.TestUtils;

public class LiteratumHelper {

    private static final TestUtils tUtil = new TestUtils();

    public static Boolean checkBillingUnpaidStatus(Response response) {
        boolean tagCodeTrue = false;
        boolean tagLabelTrue = false;

        List<Map<String, String>> maps = response.jsonPath().getList("tag");
        for(Map<String, String> map: maps) {
            tagCodeTrue = map.containsValue("unpaid");
            tagLabelTrue = map.containsValue("Unpaid");
            if (tagCodeTrue && tagLabelTrue) {
                return true;
            }
        }
        return false;
    }

    public static Boolean checkBillingPaidStatus(Response response) {
        boolean tagCodeTrue = false;
        boolean tagLabelTrue = false;

        List<Map<String, String>> maps = response.jsonPath().getList("tag");
        for(Map<String, String> map: maps) {
            tagCodeTrue = map.containsValue("paid");
            tagLabelTrue = map.containsValue("Paid");
            if (tagCodeTrue && tagLabelTrue) {
                return true;
            }
        }
        return false;
    }

    public static boolean verifyLicenseAndTag(Response respIdentity) {

        JsonPath resp = respIdentity.jsonPath();

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

        boolean emailTrue = resp.getString("email").equals(tUtil.getFromSession("email"));
        boolean fNameTrue = resp.getString("firstName").equals(tUtil.getFromSession("firstName"));
        boolean lNameTrue = resp.getString("lastName").equals(tUtil.getFromSession("lastName"));


        return emailTrue && fNameTrue && lNameTrue && tagCodeTrue && tagLabelTrue;
    }
}
