package ucc.i.method.aic;

import java.util.ArrayList;
import java.util.List;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AICHelper {

	public boolean verifyAlternateID(Response resp) {
		
		boolean flag = false;
		
		JsonPath jsonPathEvaluator = resp.jsonPath();
		List<String> a = jsonPathEvaluator.getList("alternateID.IDType");
		
		System.out.println(a);
		
//		boolean atype = jsonPathEvaluator.getString("audienceType").equals("SUBSCRIBER");
    //    boolean catsubsrp = jsonPathEvaluator.getString("Catalyst.audienceType").equals("SUBSCRIBER");
		
        List<String> expected = new ArrayList<String>();
        expected.add("ICV");
        expected.add("ACS");
        
		if (a.containsAll(expected)) {
		    flag = true;  
		    System.out.println("Successfully validated ICV, ACS in alternateId of Akamai...!!");
		}
		else{
			flag = false;
		}
		return flag ;
	}
}
