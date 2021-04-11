package ucc.i.method.store;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

import java.util.HashMap;
import java.util.Map;

public class UpdateStoreFiles {

	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	public static final String AUTO_EMAIL = EnvironmentSpecificConfiguration.from(env_var).getProperty("autoEmail");
	public static final String PROMO_CODE = EnvironmentSpecificConfiguration.from(env_var).getProperty("promoCode");
	static TestUtils tUtil = new TestUtils();
	static JsonUtils jsonUtils = new JsonUtils();
	static String filePath = env_var.getProperty("json.body.path");

	public Map<String, String> update_invoice_file(String filename, Map<String, String> row) throws Exception {

		Map<String, String> kmap = new HashMap<>();

		jsonUtils.update_JSONValue(filename, "$.order.totalDue", Double.parseDouble(row.get("totalDue")));
		jsonUtils.update_JSONValue(filename, "$.order.orderEntityId", tUtil.generateRandomUcid(6));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].brand", row.get("brand"));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].sku", row.get("sku"));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].term", row.get("term"));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].autoRenew",Boolean.parseBoolean(row.get("autoRenew")));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].promoCode", PROMO_CODE);
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].priceCode", row.get("priceCode"));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].tax", Double.parseDouble(row.get("tax")));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].price", Double.parseDouble(row.get("price")));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.address", row.get("address"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.address2", row.get("address1"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.city", row.get("city"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.state", row.get("state"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.country", row.get("country"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.postalCode", row.get("zip"));
		jsonUtils.update_JSONValue(filename, "$.order.storeCurrencyCode", row.get("storeCurrencyCode"));
		jsonUtils.update_JSONValue(filename, "$.order.vatNumber", handleVat(row.get("vatNumber"), row.get("country")));
		jsonUtils.update_JSONValue(filename, "$.order.isComp", Boolean.parseBoolean(row.get("isComp")));
		jsonUtils.update_JSONValue(filename, "$.payment.paymentAmount", Double.parseDouble(row.get("totalDue")));

		kmap.put("totalDue", row.get("totalDue"));
		kmap.put("price", row.get("price"));
		kmap.put("brand", row.get("brand"));
		kmap.put("sku", row.get("sku"));
		kmap.put("autoRenew", row.get("autorenew"));
		kmap.put("tax", row.get("tax"));
		kmap.put("address", row.get("address"));
		kmap.put("city", row.get("city"));
		kmap.put("state", row.get("state"));
		kmap.put("country", row.get("country"));
		kmap.put("paymentType", "CashOnDelivery");
		kmap.put("postalCode", row.get("zip"));
		kmap.put("storeCurrencyCode", row.get("storeCurrencyCode"));
		kmap.put("priceCode", row.get("priceCode"));
		kmap.put("isComp", row.get("isComp"));

		return kmap;
	}

	public Map<String, String> update_cc_annual_file(String filename, Map<String, String> row) throws Exception {

		String path = filePath + "/" + filename;

		Map<String, String> kmap = new HashMap<>();

		jsonUtils.update_JSONValue(filename, "$.order.totalDue", Double.parseDouble(row.get("totalDue")));
		jsonUtils.update_JSONValue(filename, "$.order.orderEntityId", tUtil.generateRandomUcid(6));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].brand", row.get("brand"));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].sku", row.get("sku"));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].term", row.get("term"));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].autoRenew",Boolean.parseBoolean(row.get("autoRenew")));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].promoCode", PROMO_CODE);
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].priceCode", row.get("priceCode"));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].tax", Double.parseDouble(row.get("tax")));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].price", Double.parseDouble(row.get("price")));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.address", row.get("address"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.address2", row.get("address1"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.city", row.get("city"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.state", row.get("state"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.country", row.get("country"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.postalCode", row.get("zip"));
		jsonUtils.update_JSONValue(filename, "$.order.storeCurrencyCode", row.get("storeCurrencyCode"));
		jsonUtils.update_JSONValue(filename, "$.order.vatNumber", handleVat(row.get("vatNumber"), row.get("country")));
		jsonUtils.update_JSONValue(filename, "$.order.isComp", Boolean.parseBoolean(row.get("isComp")));
		jsonUtils.update_JSONValue(filename, "$.payment.paymentAmount", Double.parseDouble(row.get("totalDue")));

		kmap.put("totalDue", row.get("totalDue"));
		kmap.put("price", row.get("price"));
		kmap.put("brand", row.get("brand"));
		kmap.put("sku", row.get("sku"));
		kmap.put("autoRenew", row.get("autorenew"));
		kmap.put("tax", row.get("tax"));
		kmap.put("address", row.get("address"));
		kmap.put("city", row.get("city"));
		kmap.put("state", row.get("state"));
		kmap.put("country", row.get("country"));
		kmap.put("paymentType", "CC");
		kmap.put("postalCode", row.get("zip"));
		kmap.put("storeCurrencyCode", row.get("storeCurrencyCode"));
		kmap.put("priceCode", row.get("priceCode"));
		kmap.put("isComp", row.get("isComp"));

		// kmap = jsonUtils.jsonToMap(path);
		return kmap;
	}

	public Map<String, String> update_Paypal(String filename, Map<String, String> row) throws Exception {

		String path = filePath + "/" + filename;

		Map<String, String> kmap = new HashMap<>();

		jsonUtils.update_JSONValue(filename, "$.order.totalDue", Double.parseDouble(row.get("totalDue")));
		jsonUtils.update_JSONValue(filename, "$.order.orderEntityId", tUtil.generateRandomUcid(6));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].brand", row.get("brand"));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].sku", row.get("sku"));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].term", row.get("term"));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].autoRenew",Boolean.parseBoolean(row.get("autoRenew")));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].promoCode", PROMO_CODE);
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].priceCode", row.get("priceCode"));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].tax", Double.parseDouble(row.get("tax")));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].price", Double.parseDouble(row.get("price")));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.address", row.get("address"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.address2", row.get("address1"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.city", row.get("city"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.state", row.get("state"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.country", row.get("country"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.postalCode", row.get("zip"));
		jsonUtils.update_JSONValue(filename, "$.order.storeCurrencyCode", row.get("storeCurrencyCode"));
		jsonUtils.update_JSONValue(filename, "$.order.vatNumber", handleVat(row.get("vatNumber"), row.get("country")));
		jsonUtils.update_JSONValue(filename, "$.order.isComp", Boolean.parseBoolean(row.get("isComp")));
		jsonUtils.update_JSONValue(filename, "$.payment.paymentAmount", Double.parseDouble(row.get("totalDue")));

		kmap.put("totalDue", row.get("totalDue"));
		kmap.put("price", row.get("price"));
		kmap.put("brand", row.get("brand"));
		kmap.put("sku", row.get("sku"));
		kmap.put("autoRenew", row.get("autorenew"));
		kmap.put("tax", row.get("tax"));
		kmap.put("address", row.get("address"));
		kmap.put("city", row.get("city"));
		kmap.put("state", row.get("state"));
		kmap.put("country", row.get("country"));
		kmap.put("paymentType", "Paypal");
		kmap.put("postalCode", row.get("zip"));
		kmap.put("storeCurrencyCode", row.get("storeCurrencyCode"));
		kmap.put("priceCode", row.get("priceCode"));
		kmap.put("isComp", row.get("isComp"));

		// kmap = jsonUtils.jsonToMap(path);
		return kmap;
	}

	public Map<String, String> update_store_file(String filename, Map<String, String> row) throws Exception {

		Map<String, String> kmap = new HashMap<>();

		jsonUtils.update_JSONValue(filename, "$.order.totalDue", Double.parseDouble(row.get("tdue")));
		jsonUtils.update_JSONValue(filename, "$.order.orderEntityId", tUtil.generateRandomUcid(6));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].brand", row.get("brand"));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].sku", row.get("sku"));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].autoRenew",Boolean.parseBoolean(row.get("autorenew")));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].promoCode", PROMO_CODE);
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].tax", Double.parseDouble(row.get("tax")));
		jsonUtils.update_JSONValue(filename, "$.order.orderLines[0].price", Double.parseDouble(row.get("tdue")));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.address", row.get("addr"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.city", row.get("city"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.state", row.get("state"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.country", row.get("country"));
		jsonUtils.update_JSONValue(filename, "$.order.billToAddress.postalCode", row.get("zip"));
		jsonUtils.update_JSONValue(filename, "$.payment.creditCardInfo.cardType", row.get("cc"));

		kmap.put("totalDue", row.get("tdue"));
		kmap.put("price", row.get("tdue"));
		kmap.put("brand", row.get("brand"));
		kmap.put("sku", row.get("sku"));
		kmap.put("autoRenew", row.get("autorenew"));
		kmap.put("tax", row.get("tax"));
		kmap.put("address", row.get("addr"));
		kmap.put("city", row.get("city"));
		kmap.put("state", row.get("state"));
		kmap.put("country", row.get("country"));
		kmap.put("cardType", row.get("cc"));

		return kmap;

	}
	
	public Map<String, String> update_payment_file(String filename, Map<String, String> row) throws Exception {

        Map<String, String> kmap = new HashMap<>();

        jsonUtils.update_JSONValue(filename, "$.orderPromoCode", PROMO_CODE);
        jsonUtils.update_JSONValue(filename, "$.extOrderNumber", tUtil.generateRandomUcid(6));
        jsonUtils.update_JSONValue(filename, "$.orderId", row.get("orderNumber"));
        jsonUtils.update_JSONValue(filename, "$.customerNumber", row.get("customerNumber"));
        jsonUtils.update_JSONValue(filename, "$.creditCardInfo.customerName", row.get("firstName") + " " + row.get("lastName"));
        jsonUtils.update_JSONValue(filename, "$.customerNumber", row.get("customerNumber"));
       
        kmap.put("cardType", row.get("cc"));
        kmap.put("orderId", row.get("orderNumber"));
        kmap.put("customerNumber", row.get("customerNumber"));
        kmap.put("customerName", row.get("firstName") + " " + row.get("lastName"));

        return kmap;

    }
	
	public Map<String, String> update_paypal_payment_file(String filename, Map<String, String> row) throws Exception {

        Map<String, String> kmap = new HashMap<>();

        jsonUtils.update_JSONValue(filename, "$.orderPromoCode", PROMO_CODE);
        jsonUtils.update_JSONValue(filename, "$.extOrderNumber", tUtil.generateRandomUcid(6));
        jsonUtils.update_JSONValue(filename, "$.orderId", row.get("orderNumber"));
        jsonUtils.update_JSONValue(filename, "$.customerNumber", row.get("customerNumber"));
        jsonUtils.update_JSONValue(filename, "$.customerNumber", row.get("customerNumber"));
       
        kmap.put("cardType", row.get("PayPal"));
        kmap.put("orderId", row.get("orderNumber"));
        kmap.put("customerNumber", row.get("customerNumber"));

        return kmap;

    }

	public void updateCustomerData(String filename, String firstName, String lastName, String emailValue,
			Map<String, String> row) throws Exception {
	//	jsonUtils.update_JSONValue(filename, "$.customer.suffix", row.get("suffix"));
		jsonUtils.update_JSONValue(filename, "$.customer.email", emailValue);
	//	jsonUtils.update_JSONValue(filename, "$.customer.role", row.get("role"));
		jsonUtils.update_JSONValue(filename, "$.customer.firstName", firstName);
		jsonUtils.update_JSONValue(filename, "$.customer.lastName", lastName);
	}

	public String handleVat(String val, String country) {
		if (country.equalsIgnoreCase("usa") || country.equalsIgnoreCase("CAN")) {
			val = "";
		} else if (val.equalsIgnoreCase("empty") || val == "") {
			val = "";
		} else {
			System.out.println("Please specify valid VatID or country to continue the flow");
		}
		return val;
	}

}
