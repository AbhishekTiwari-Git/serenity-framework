package ucc.i.method.productsystem;

import org.junit.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;
import ucc.utils.TestUtils;

public class ProductSystemHelper {
	
	private TestUtils tUtil = new TestUtils();
	
	public void checkProductPrices(Response sysResp) 
			throws JsonMappingException, JsonProcessingException{
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode prodecureJson = mapper.readTree((String) tUtil.getFromSession("productPrices"));
		JsonNode httResponseJson = mapper.readTree(sysResp.getBody().asString());
		
		TestUtils.NumericNodeComparator cmp = new TestUtils.NumericNodeComparator();
		Assert.assertTrue(prodecureJson.equals(cmp, httResponseJson));
	}
}
