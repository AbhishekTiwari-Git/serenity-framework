package ucc.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.api.SoftAssertions;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ucc.i.method.aic.AICPOST;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.format.DateTimeFormatter.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestUtils {

	static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	public static List<String> put_Vals = new ArrayList<String>();
	private static final EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
	String filePath = envVar.getProperty("json.body.path");
	
	
	
	public void responseContainsTheString(Response response, String field){
	 
	 // To check for sub string presence get the Response body as a String.
	 // Do a String.contains
	 String bodyAsString = response.getBody().asString();
	 Assert.assertTrue(bodyAsString.contains(field));
	}

	public String getYesterdayDateString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return dateFormat.format(cal.getTime());
	}

	public static String getRandomValue() {
		Random random = new Random();
		int radomInt = random.nextInt(100000);
		return Integer.toString(radomInt);
	}

	/*
	 * public Map<String, String> testDecodeJWT(String token) { String jwtToken =
	 * token; System.out.println("------------ Decode JWT ------------"); String[]
	 * split_string = jwtToken.split("\\."); String base64EncodedHeader =
	 * split_string[0]; String base64EncodedBody = split_string[1]; // String
	 * base64EncodedSignature = split_string[2];
	 *
	 * System.out.println("~~~~~~~~~ JWT Header ~~~~~~~"); Base64 base64Url = new
	 * Base64(true); String header = new
	 * String(base64Url.decode(base64EncodedHeader));
	 * System.out.println("JWT Header : " + header);
	 * jwtTokenDecoded.put("jwtTokenHeader", header);
	 *
	 * System.out.println("~~~~~~~~~ JWT Body ~~~~~~~"); String body = new
	 * String(base64Url.decode(base64EncodedBody)); System.out.println("JWT Body : "
	 * + body); jwtTokenDecoded.put("jwtTokenBody", body);
	 *
	 * return jwtTokenDecoded; }
	 */

	public String returnToken(Response res) {
		JsonPath jsonPathEvaluator = res.jsonPath();
		String token = jsonPathEvaluator.get("data.sessionToken");

		System.out.println(token);
		return token;
	}

	public String AppendTimestamp(String value) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		value = timeStamp + value;
		return value;
	}
	
	public String AppendTimestampForAMB(String value) {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        value = value + timeStamp + RandomStringUtils.randomAlphabetic(6);
        return value;
    }
	

	public Map<String, String> store_jsonValues(Response res, List<String> storeValue) {

		JsonPath jsonPathEvaluator = res.jsonPath();
		Map<String, String> storedValues = new HashMap<String, String>();

		for (int i = 0; i < storeValue.size(); i++) {
			String value = jsonPathEvaluator.getString(storeValue.get(i));
			storedValues.put(storeValue.get(i), value);
		}

		System.out.println("Stored Values " + storedValues);

		return storedValues;

	}

	public Map<String, String> store_jsonValues(List<String> storeKey, List<String> storeValue) {

		Map<String, String> storedValues = new HashMap<String, String>();

		for (int i = 0; i < storeValue.size(); i++) {
			String value = storeValue.get(i);
			storedValues.put(storeKey.get(i), value);
		}

		System.out.println("Stored Values " + storedValues);

		return storedValues;

	}

	public Map<String, String> store_Put_jsonValues(List<String> storeValue) {

		Map<String, String> storedValues = new HashMap<String, String>();
		String key = "email";

		for (int i = 0; i < storeValue.size(); i++) {
			storedValues.put(key, storeValue.get(i));
		}

		System.out.println("Stored Values " + storedValues);

		return storedValues;

	}

	public List<String> returnTimestampValue() {

		System.out.println(put_Vals);
		return put_Vals;
	}

	public float round(float number, int decimalPlace) {
		BigDecimal bd = new BigDecimal(number);
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	public String generateRandomUcid(int length) {
		Random random = new Random();
		StringBuilder builder = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			builder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
		}

		return builder.toString();
	}
	
	public String generateRandomInid() {
		char[] chars = "0123456789".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)) + "2");
		for (int id = 0; id < 5; id++)
			sb.append(chars[rnd.nextInt(chars.length)]);
		return sb.toString();
	}

	// Adds specified amount of time unit to the given date. Takes date as a string
	// and returns its representation based
	// on the provided pattern
	public String addTime(String date, String pattern, int unit, int offset) throws ParseException {
		Date expDate = new SimpleDateFormat(pattern).parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(expDate);
		cal.add(unit, offset);
		Date newDate = cal.getTime();
		return new SimpleDateFormat(pattern).format(newDate);
	}

	public void writeOrdersemailUcid(Map<String, String> map1) {

		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter out = null;

		try {

			fw = new FileWriter("Outorder.txt", true);
			bw = new BufferedWriter(fw);

			// iterate map entries
			bw.newLine();
			for (Map.Entry<String, String> entry : map1.entrySet()) {

				// put key and value separated by a colon
				bw.write(entry.getKey() + ":" + entry.getValue());

				// new line
				bw.newLine();
			}

			bw.write(".....................................................");
			bw.newLine();
			bw.write(".....................................................");
			bw.newLine();
			bw.flush();

		}

		catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				// always close the writer
				bw.close();
			} catch (Exception e) {
			}
		}
	}

	public static String parseSerenityXml(File xmlFile) throws ParserConfigurationException, IOException, SAXException {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);

		NodeList nList = doc.getElementsByTagName("testsuite");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				return eElement.getAttribute("time");
			}
		}
		return "";
	}

	public static Map<String, String> getMapManualCycle() throws IOException {
		String filePath = "ManualCycle.txt";
		HashMap<String, String> map = new HashMap<>();

		String line;
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		while ((line = reader.readLine()) != null) {
			String[] parts = line.split("=", 2);
			if (parts.length >= 2) {
				String key = parts[0];
				String value = parts[1];
				map.put(key.trim(), value.trim());
			}
		}
		return map;
	}

	public String encodeValue(String value) {
		try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex.getCause());
		}
	}

	//
	// Verify methods
	public void verify_json_to_response(Response resp, String jsonFile) throws IOException {

		String path = filePath + "/" + jsonFile;
		String exampleRequest = FileUtils.readFileToString(new File(path), StandardCharsets.UTF_8);

		ObjectMapper om = new ObjectMapper();

		try {
			Map<String, Object> m1 = (Map<String, Object>) (om.readValue(exampleRequest, Map.class));
			Map<String, Object> m2 = (Map<String, Object>) (om.readValue(resp.getBody().asString(), Map.class));

			assertTrue(m1.equals(m2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verifyStatus(Response res) {
		int code = res.getStatusCode();
		assertEquals("The status code is not equal to 200", 200, code);
		System.out.println(code);
	}

	public void verifyStatus(Response res, int status) {
		int code = res.getStatusCode();
		assertEquals(status, code);
		System.out.println(code);
	}

	public void verifyResponseUri_AllowValue(Response res, String Uri, Boolean Allow_value) {

		String article = null;
		Boolean value = null;

		JsonPath jsonPathEvaluator = res.jsonPath();
		article = jsonPathEvaluator.get("data.accessRights.uri");
		value = jsonPathEvaluator.get("data.accessRights.allow");

		System.out.println(article + "" + value);

		assertEquals(Uri, article);
		assertEquals(Allow_value, value);
	}

	public void verify_msgCode(Response resp, String msg, int code) {

		ResponseBody body = resp.getBody();

		// Get Response Body as String
		String bodyStringValue = body.asString();

		Assert.assertTrue("The response should contain message: " + msg + " but found " + bodyStringValue,
				bodyStringValue.contains(msg));
		Assert.assertEquals(resp.getStatusCode(), code);

	}

	public void verifyCountInResponse(Response resp, int count) {

		JsonPath jsonPathEvaluator = resp.jsonPath();
		int countInResp = jsonPathEvaluator.getInt("count");

		Assert.assertEquals("The response should contain count: " + count + " but found " + countInResp, count,
				countInResp);
	}

	public void verify_StsAndMsg(Response resp, String msg, int code) {

		@SuppressWarnings("rawtypes")
		ResponseBody body = resp.getBody();

		// Get Response Body as String
		String bodyStringValue = body.asString();

		Assert.assertTrue("The response should contain message: " + msg + " but found " + bodyStringValue,
				bodyStringValue.contains(msg));
		Assert.assertEquals(resp.getStatusCode(), code);

	}

	public void verify_emptyArray(Response resp) {
		JSONArray jsonArr = new JSONArray(resp.getBody().asString());
		Assert.assertTrue("The list should be empty but got " + resp.getBody().asString(), jsonArr.isEmpty());
	}

	// Verifies the existence of the given fields in JSONObject
	// NOTE: that this method should be used with JSONObject and NOT JSONArray
	public void verifyKeysInObject(Response resp, String[] keys) {
		JsonPath jsonpathEvaluator = resp.jsonPath();
		List<String> loKeys = Arrays.asList(keys);

		for (String k : loKeys) {
			// Since some values might be null, assertNotNull is not the best option.
			// Rather, user get method
			// which throws exception if the key is not found
			// Assert.assertNotNull(jsonpathEvaluator.get(k));
			jsonpathEvaluator.get(k);
		}
	}

	// Verifies the existence of the given fields and that their values are not null
	// in JSONArray
	// NOTE: that this method should be used with JSONArray and NOT JSONObject
	public void verifyKeysInArray(Response resp, String[] keys) {
		JsonPath jsonpathEvaluator = resp.jsonPath();
		List<String> loKeys = Arrays.asList(keys);

		for (String k : loKeys) {
			Assert.assertTrue(!jsonpathEvaluator.getList(k).contains(null));
		}
	}

	public void verifyUpdatedValues(Response resp, String ukk, String uvv) {

		JsonPath jsonPathEvaluator = resp.jsonPath();
		String updatedval = jsonPathEvaluator.getString(ukk).trim();

		Assert.assertEquals(uvv.trim(), updatedval);
	}

	//
	// Serenity Session
	public Object getFromSession(Object object) {
		return Serenity.getCurrentSession().get(object);
	}

	public Object getFromSessionWithDefault(Object object, Object defaultObj) {
		return Serenity.getCurrentSession().getOrDefault(object, defaultObj);
	}

	public <T> T checkSession(String key, T value) {
		return this.isSessionContainsKey(key) ? (T) this.getFromSession(key) : value;
	}

	public void putToSession(Object key, Object value) {
		Serenity.getCurrentSession().put(key, value);
	}

	public boolean isSessionContainsKey(Object key) {
		return Serenity.getCurrentSession().containsKey(key);
	}

	public void clearSession() {
		Serenity.getCurrentSession().clear();
	}

	//
	// Work with excel files
	public static void createTaxExcelFile() throws FileNotFoundException {
		File f = new File("OrderDetails.xlsx");
		if (f.exists()) {
			f.delete();
		}
		try {
			String filename = "OrderDetails.xlsx";
			String[] headers = new String[] { "product", "fname", "lname", "Sstreet", "Scity", "Sstate", "Scountry",
					"Szip", "Bstreet", "Bcity", "Bstate", "Bcountry", "Bzip", "vatId", "amount", "taxamount" };

			XSSFWorkbook wrkBook = new XSSFWorkbook();
			XSSFSheet mySheet = wrkBook.createSheet("sheet1");
			XSSFRow row = mySheet.createRow(0);

			for (int rn = 0; rn < headers.length; rn++) {
				row.createCell(rn).setCellValue(headers[rn]);
			}

			FileOutputStream fileOut = new FileOutputStream(filename);
			wrkBook.write(fileOut);
			fileOut.close();
			wrkBook.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public static void createExcelFile() throws FileNotFoundException {
		File f = new File("OrderDetails.xlsx");
		if (f.exists()) {
			f.delete();
		}
		try {
			String filename = "OrderDetails.xlsx";
			String[] headers = new String[] { "lastName", "country", "address", "city", "totalDue", "postalCode", "tax",
					"customerNumber", "firstName", "price", "autoRenew", "state", "sku", "brand", "email", "ucid" };

			XSSFWorkbook wrkBook = new XSSFWorkbook();
			XSSFSheet mySheet = wrkBook.createSheet("sheet1");
			XSSFRow row = mySheet.createRow(0);

			for (int rn = 0; rn < headers.length; rn++) {
				row.createCell(rn).setCellValue(headers[rn]);
			}

			FileOutputStream fileOut = new FileOutputStream(filename);
			wrkBook.write(fileOut);
			fileOut.close();
			wrkBook.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public static void createExcelFile(String fileName) throws FileNotFoundException {
		File f = new File(fileName);
		if (f.exists()) {
			f.delete();
		}
		try {
			XSSFWorkbook wrkBook = new XSSFWorkbook();
			XSSFSheet mySheet = wrkBook.createSheet("sheet1");
			XSSFRow row = mySheet.createRow(0);

			FileOutputStream fileOut = new FileOutputStream(fileName);
			wrkBook.write(fileOut);
			fileOut.close();
			wrkBook.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void writeTaxExcelFile(Map<String, String> mapData) {

		try {
			String filename = "OrderDetails.xlsx";
			FileInputStream file = new FileInputStream(filename);

			XSSFWorkbook wrkBook = new XSSFWorkbook(file);
			XSSFSheet mySheet = wrkBook.getSheetAt(0);
			int lastRow = mySheet.getLastRowNum();
			XSSFRow row = mySheet.createRow(++lastRow);

			int i = 0;
			for (String value : mapData.values()) {
				row.createCell(i++).setCellValue(value);
			}

			file.close();

			FileOutputStream fileOut = new FileOutputStream(new File(filename));
			wrkBook.write(fileOut);
			fileOut.close();
			wrkBook.close();
			System.out.println(" is successfully written");
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void writeExcelFile(Map<String, String> mapData, String fileName) {

		try {
			FileInputStream file = new FileInputStream(fileName);

			XSSFWorkbook wrkBook = new XSSFWorkbook(file);
			XSSFSheet mySheet = wrkBook.getSheetAt(0);
			
			int lastRow = mySheet.getLastRowNum();
			XSSFRow row = mySheet.createRow(++lastRow);

			int i = 0;
			for (String value : mapData.values()) {
				row.createCell(i++).setCellValue(value);
			}

			file.close();

			FileOutputStream fileOut = new FileOutputStream(new File(fileName));
			wrkBook.write(fileOut);
			fileOut.close();
			wrkBook.close();
			System.out.println(" is successfully written");
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	//
	// NewRelic
	public void uploadFileNewRelic() throws IOException {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {

			HttpPost uploadFile = new HttpPost(envVar.getProperty("newrelic.url"));

			uploadFile.addHeader("X-Insert-Key", envVar.getProperty("newrelic.token"));

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			File jFile = new File("scenario.json");
			builder.addBinaryBody("file", new FileInputStream(jFile), ContentType.APPLICATION_OCTET_STREAM,
					jFile.getName());

			HttpEntity multipart = builder.build();
			uploadFile.setEntity(multipart);
			httpClient.execute(uploadFile);
		} catch (Exception e) {
		} finally {
			httpClient.close();
		}

	}

	public void postNewRelic() throws IOException {
		RestAssured.baseURI = envVar.getProperty("newrelic.url");
		File file = new File("scenario.json");
		byte[] array = Files.readAllBytes(file.toPath());

		SerenityRest.rest()
					.given()
					.spec(ReuseableSpecifications.getGenericNewRelicRequestSpec())
					.when()
					.body(array)
					.post()
					.then()
					.log().all()
					.assertThat().statusCode(ResponseCode.OK);

	}

	//
	// Comparator
	public static class NumericNodeComparator implements Comparator<JsonNode> {
		@Override
		public int compare(JsonNode o1, JsonNode o2) {
			if (o1.equals(o2)) {
				return 0;
			}
			if ((o1 instanceof NumericNode) && (o2 instanceof NumericNode)) {
				Double d1 = ((NumericNode) o1).asDouble();
				Double d2 = ((NumericNode) o2).asDouble();
				if (d1.compareTo(d2) == 0) {
					return 0;
				}
			}
			return 1;
		}
	}

	public boolean isBoolean(String value) {
		return value != null
				&& Arrays.stream(new String[] { "true", "false", "1", "0" }).anyMatch(b -> b.equalsIgnoreCase(value));
	}

	public boolean isBooleanOrNull(String value) {
		return value == null
				|| Arrays.stream(new String[] { "true", "false", "1", "0" }).anyMatch(b -> b.equalsIgnoreCase(value));
	}

	public String getToken(final String Ucid) {
		AICPOST aicPOSTSteps = new AICPOST();
		Response aicResp = aicPOSTSteps.createToken(Ucid)
			.extract().response();
		return aicPOSTSteps.getToken(aicResp);
	}
	
	public String generateToken(Map kmap) {
		AICPOST aicPOSTSteps = new AICPOST();
		Response aicResp = aicPOSTSteps.createToken((String)kmap.get("ucid"))
				.extract().response();
		return aicResp.jsonPath().getString("accessToken");
	}

	public String generateToken() {
		AICPOST aicPOSTSteps = new AICPOST();
		Response aicResp = aicPOSTSteps.createToken((String) getFromSession("ucid"))
						.extract().response();
		return aicResp.jsonPath().getString("accessToken");
    }

	public HashMap getActiveSubscriptionWithPaymentId(List<HashMap> subscriptions) {
		if (subscriptions != null) {
			for (HashMap subscription : subscriptions) {
				boolean isActive = "R".equals(getNestedFromJsonMap(subscription, "status", "code"));
				boolean isExpired = isExpired(subscription.get("accessExpires"), ISO_LOCAL_DATE);
				boolean isPaid = "P".equals(subscription.get("billingStatusCode"));
				String paymentId = (String) getNestedFromJsonMap(subscription, "relatedCredits[0]", "electronicPaymentId");
				if (isActive && !isExpired && isPaid && StringUtils.isNotBlank(paymentId)) {
					return subscription;
				}
			}
		}
		return null;
	}

	public void putActiveSubscriptionWithPaymentIdToSession(HashMap subscription) {
		putToSession("subscriptionId", subscription.get("subscriptionId"));
		putToSession("currencyCode", subscription.get("billingCurrencyCode"));
		putToSession("billOrg", subscription.get("billingOrganizationCode"));
		String paymentId = (String) getNestedFromJsonMap(subscription, "relatedCredits[0]", "electronicPaymentId");
		putToSession("paymentId", paymentId);
	}


	public HashMap getActiveAgreement(List<HashMap> agreements) {
		if (agreements != null) {
			for (HashMap agreement : agreements) {
				boolean isActive = "A".equals(getNestedFromJsonMap(agreement, "status", "code"));
				boolean isExpired = isExpired(agreement.get("accessExpires"), ISO_LOCAL_DATE_TIME);
				if (isActive && !isExpired) {
					return agreement;
				}
			}
		}
		return null;
	}

	public boolean isExpired(Object dateObject, DateTimeFormatter formatter) {
		if (dateObject instanceof String) {
			return isExpired((String) dateObject, formatter);
		}
		return true;
	}

	public boolean isExpired(String date, DateTimeFormatter formatter) {
		try {
			LocalDate expiresDate = LocalDate.parse(date, formatter);
			return expiresDate.isBefore(LocalDate.now().plusDays(1));
		} catch (Exception ignored) {
			return true; //date was not parsed
		}
	}

	public Object getNestedFromJsonMap(HashMap jsonMap, String path, String nestedPath) {
		try {
			JsonPath jsonPath = JsonPath.from(new Gson().toJson(jsonMap));
			return jsonPath.get(path + "." + nestedPath);
		} catch (Exception ignored) {
			return null;
		}
	}

	public HashMap<String, Integer> generateCardDetails() {
		Random random = new Random();
		int randMonth = random.nextInt(12) + 1;
		int randYear = Year.now().getValue() + randMonth;
		int randLastFour = randYear * 3;
		HashMap<String, Integer> cardDetail = new HashMap<String, Integer>()
		{{
			put("month", randMonth);
			put("year", randYear);
			put("lastFour", randLastFour);
		}};
		return cardDetail;
	}
	
	public SoftAssertions resetSoftAssert(SoftAssertions SA) {

		SA = new SoftAssertions();
		return SA;
	}
}