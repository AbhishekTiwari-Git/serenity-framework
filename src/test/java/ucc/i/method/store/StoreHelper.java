package ucc.i.method.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.json.simple.parser.JSONParser;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class StoreHelper {

    private static final TestUtils tUtil = new TestUtils();
    private static final JsonUtils jUtil = new JsonUtils();
    private static final ObjectMapper mapper = new ObjectMapper();
    public static Map<String, String> kmap = new HashMap<String, String>();
    static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    public static final String promoCode =  EnvironmentSpecificConfiguration.from(envVar)
        .getProperty("promoCode");

    static JSONParser parser = new JSONParser();

    public static void updateCreatedAt(String fileName) throws Exception {
        jUtil.update_JSONValue(
            fileName,
            "$.order.createdAt",
            getDateTimeFormatNow());
    }
    public static void updateAuthDateCreatedAt(String fileName) throws Exception {
        jUtil.update_JSONValue(
            fileName,
            "$.payment.creditCardInfo.authorization.authDate",
            getDateTimeFormatNow());
        jUtil.update_JSONValue(
            fileName,
            "$.order.createdAt",
            getDateTimeFormatNow());
    }

    public static void updateAuthDatePaymentDate(String fileName) throws Exception {
        jUtil.update_JSONValue(
            fileName,
            "$.payment.digitalWalletInfo.paymentDate",
            getDateTimeFormatNow());
        jUtil.update_JSONValue(
            fileName,
            "$.order.createdAt",
            getDateTimeFormatNow());
    }

    public static void updateCustomerFNameLNameEmail(String fileName) throws Exception {
        jUtil.update_JSONValue(
            fileName,
            "$.customer.firstName",
            (String) tUtil.getFromSession("firstName"));
        jUtil.update_JSONValue(
            fileName,
            "$.customer.lastName",
            (String) tUtil.getFromSession("lastName"));
        jUtil.update_JSONValue(
            fileName,
            "$.customer.email",
            (String) tUtil.getFromSession("email"));
    }

    public static void updatePromoCode(String fileName) throws Exception {
        String filePath = envVar.getProperty("json.body.path");
        String path = filePath + "/" + fileName;
        System.out.println(path);
        File file = new File(path);

        Map map = (Map) mapper.readValue(file, Map.class).get("order");
        List lines = (List) map.get("orderLines");
        for (int i = 0; i < lines.size(); i++) {
            String promoPath = String.format("$.order.orderLines[%s].promoCode", i);
            jUtil.update_JSONValue(fileName, promoPath, promoCode);
        }
    }

    public static void updateOrderEntityId(String fileName) throws Exception {
        int n = 9;
        Random randGen = new Random();

        int startNum = (int) Math.pow(10, n-1);
        int range = (int) (Math.pow(10, n) - startNum + 1);

        int randomNum = randGen.nextInt(range) + startNum;
        jUtil.update_JSONValue(
            fileName,
            "$.order.orderEntityId",
            String.valueOf(randomNum));
    }

    private static String getDateTimeFormatNow() {
        String pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());
    }
    
    public static Map<String, String> updateKmapFromSession() {
        kmap.put("firstName", (String) tUtil.getFromSession("firstName"));
        kmap.put("lastName", (String) tUtil.getFromSession("lastName"));
        kmap.put("email", (String) tUtil.getFromSession("email"));
        return kmap;
    }
    
    
    public String getEnv() {
        
        String envVal = System.getProperty("environment");
        return envVal;
        
    }
    
    
    public String setPayBillDbfile(String environment) {
        
        String fileName = environment+"_paymybill.sql";
        return fileName;
    
    }
    
}
