package ucc.i.method.kinesys;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;
import ucc.utils.TestUtils;

public class KinesisHelper {

	KinesysPATCH kPatch = new KinesysPATCH();

	KinesysGET kGet = new KinesysGET();
	
	TestUtils tUtil = new TestUtils();	
	
	public static Response helper_resp = null;
	public static String helper_ukv = null;
	public static String helper_uvv = null;
	public static String helper_Id = null;
	
	public static Map<String,String> helper_kmap = new HashMap<String,String>();

	
	public void patch_allFields(String endPt , String upVal, String upKey) throws URISyntaxException {
		
		if (upVal.contains("@")) {
			upVal = tUtil.AppendTimestamp(upVal);
		}
		if (upKey.equals("yearOfBirth")) {
			helper_resp = kPatch.patchSysPanelist(endPt, upKey, upVal).extract().response();
		} else {
			try {
				Integer.parseInt(upVal);
				int intUpdateVal = Integer.parseInt(upVal);
				helper_resp = kPatch.patchSysPanelist(endPt, upKey, intUpdateVal).extract().response();
			} catch (NumberFormatException e) {
				helper_resp = kPatch.patchSysPanelist(endPt, upKey, upVal).extract().response();
			}
		}
		
		helper_ukv = upKey;
		helper_uvv = upVal;
		helper_kmap = kGet.store_KinesysValues(helper_resp);
		helper_Id = kPatch.getID(helper_resp);
	}
	
}
