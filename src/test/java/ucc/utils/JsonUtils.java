package ucc.utils;

import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import io.restassured.response.Response;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {

    EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    String filePath = envVar.getProperty("json.body.path");
    public static List<String> putVals = new ArrayList<String>();
    private ObjectMapper mapper = new ObjectMapper();
    private static String procJson = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    public void modify_JSONAppendTimestampfunction(String fileName, String mainKey, String replaceValueKey)
            throws Exception {

        String path = filePath + "/" + fileName;
        System.out.println(path);
        File file = new File(path);
        FileReader reader = new FileReader(file);

        try {
            JSONObject root = mapper.readValue(file, JSONObject.class);

            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

            replaceValueKey = timeStamp + replaceValueKey;

            String oldValue = (String) root.get(mainKey);

            root.remove(mainKey);
            root.put(mainKey, replaceValueKey);

            System.out.println(
                    "Replaced old value:............. " + oldValue + "  with .............." + replaceValueKey);
            putVals.add(replaceValueKey);

            FileWriter fw = new FileWriter(file);
            try {
                fw.write(root.toString());
                System.out.println("Successfully updated json object to file...!!");
            } catch (IOException ioe) {
                System.out.println("Exception opening or accessing the file......!!" + ioe.getMessage());
                System.out.println("Exception opening or accessing the file......!!" + ioe.getStackTrace());

            } finally {
                fw.flush();
                fw.close();
                reader.close();
            }
        } catch (Exception e) {
            System.out.println("Exception executing the function ......!!" + e.getStackTrace());
        }
    }

    public void update_JSONValue(String fileName, String JsonPath, String replaceValue) throws Exception {
        String path = filePath + "/" + fileName;
        System.out.println(path);
        File file = new File(path);
        FileReader reader = new FileReader(file);

        try {
            JSONObject root = mapper.readValue(file, JSONObject.class);

            String jso = root.toJSONString();
            System.out.println(jso);

            Configuration configuration = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
                    .mappingProvider(new JacksonMappingProvider()).build();

            JsonNode updatedJson = com.jayway.jsonpath.JsonPath.using(configuration).parse(jso)
                    .set(JsonPath, replaceValue).json();

           // jso = updatedJson.toString();
          
           
            jso = updatedJson.toString().replaceAll("\\\\", "");
           
            System.out.println(jso);

            FileWriter fw = new FileWriter(file);       
            try {  	
                fw.write(jso);
                System.out.println("Successfully updated json object to file...!!");
            } catch (IOException ioe) {
                System.out.println("Exception opening or accessing the file......!!" + ioe.getMessage());
                System.out.println("Exception opening or accessing the file......!!" + ioe.getStackTrace());

            } finally {
                fw.flush();
                fw.close();
                reader.close();
            }
        }

        catch (Exception e) {
            System.out.println("Exception executing the function ......!!" + e.getStackTrace());
        }
    }

    public void update_JSONValue(String fileName, String JsonPath, double replaceValue) throws Exception {
        String path = filePath + "/" + fileName;
        System.out.println(path);
        File file = new File(path);
        FileReader reader = new FileReader(file);

        try {

            JSONObject root = mapper.readValue(file, JSONObject.class);

            String jso = root.toJSONString();
            System.out.println(jso);

            Configuration configuration = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
                    .mappingProvider(new JacksonMappingProvider()).build();

            JsonNode updatedJson = com.jayway.jsonpath.JsonPath.using(configuration).parse(jso)
                    .set(JsonPath, replaceValue).json();

            jso = updatedJson.toString();

            System.out.println(jso);

            FileWriter fw = new FileWriter(file);
            try {
                fw.write(jso);
                System.out.println("Successfully updated json object to file...!!");
            } catch (IOException ioe) {
                System.out.println("Exception opening or accessing the file......!!" + ioe.getMessage());
                System.out.println("Exception opening or accessing the file......!!" + ioe.getStackTrace());

            } finally {
                fw.flush();
                fw.close();
                reader.close();
            }
        }

        catch (Exception e) {
            System.out.println("Exception executing the function ......!!" + e.getStackTrace());
        }
    }

    public void update_JSONValue(String fileName, String JsonPath, int replaceValue) throws Exception {
        String path = filePath + "/" + fileName;
        System.out.println(path);
        File file = new File(path);
        FileReader reader = new FileReader(file);

        try {

            JSONObject root = mapper.readValue(file, JSONObject.class);

            String jso = root.toJSONString();
            System.out.println(jso);

            Configuration configuration = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
                    .mappingProvider(new JacksonMappingProvider()).build();

            JsonNode updatedJson = com.jayway.jsonpath.JsonPath.using(configuration).parse(jso)
                    .set(JsonPath, replaceValue).json();

            jso = updatedJson.toString();

            System.out.println(jso);

            FileWriter fw = new FileWriter(file);
            try {
                fw.write(jso);
                System.out.println("Successfully updated json object to file...!!");
            } catch (IOException ioe) {
                System.out.println("Exception opening or accessing the file......!!" + ioe.getMessage());
                System.out.println("Exception opening or accessing the file......!!" + ioe.getStackTrace());

            } finally {
                fw.flush();
                fw.close();
                reader.close();
            }
        }

        catch (Exception e) {
            System.out.println("Exception executing the function ......!!" + e.getStackTrace());
        } finally {

        }

    }

    public void update_JSONValue(String fileName, String JsonPath, boolean replaceValue) throws Exception {
        String path = filePath + "/" + fileName;
        System.out.println(path);
        File file = new File(path);
        FileReader reader = new FileReader(file);

        try {

            JSONObject root = mapper.readValue(file, JSONObject.class);

            String jso = root.toJSONString();
            System.out.println(jso);

            Configuration configuration = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
                    .mappingProvider(new JacksonMappingProvider()).build();

            JsonNode updatedJson = com.jayway.jsonpath.JsonPath.using(configuration).parse(jso)
                    .set(JsonPath, replaceValue).json();

            jso = updatedJson.toString();

            System.out.println(jso);

            FileWriter fw = new FileWriter(file);
            try {
                fw.write(jso);
                System.out.println("Successfully updated json object to file...!!");
            } catch (IOException ioe) {
                System.out.println("Exception opening or accessing the file......!!" + ioe.getMessage());
                System.out.println("Exception opening or accessing the file......!!" + ioe.getStackTrace());

            } finally {
                fw.flush();
                fw.close();
                reader.close();
            }
        }

        catch (Exception e) {
            System.out.println("Exception executing the function ......!!" + e.getStackTrace());
        }
    }

    public void update_JSONArrayfunction(String fileName, String ArrayJsonPath, String replaceValueKey)
            throws Exception {

        String path = filePath + "/" + fileName;
        System.out.println(path);
        File file = new File(path);
        FileReader reader = new FileReader(file);

        try {

            JSONObject[] root = mapper.readValue(file, JSONObject[].class);

            String jString = mapper.writeValueAsString(root);
            System.out.println(jString);

            net.minidev.json.JSONArray jArray = com.jayway.jsonpath.JsonPath.read(jString, ArrayJsonPath);

            String oldValue = (String) jArray.get(0);
            System.out.println(oldValue);

            String newValueKey = replaceValueKey;

            Configuration configuration = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
                    .mappingProvider(new JacksonMappingProvider()).build();

            JsonNode updatedJson = com.jayway.jsonpath.JsonPath.using(configuration).parse(jString)
                    .set(ArrayJsonPath, newValueKey).json();
            jString = updatedJson.toString();

            System.out.println(jString);
            System.out.println("Replaced old value:............. " + oldValue + "  with .............." + newValueKey);
            putVals.add(newValueKey);

            FileWriter fw = new FileWriter(file);
            try {
                fw.write(jString);
                System.out.println("Successfully updated json object to file...!!");
            } catch (IOException ioe) {
                System.out.println("Exception opening or accessing the file......!!" + ioe.getMessage());
                System.out.println("Exception opening or accessing the file......!!" + ioe.getStackTrace());

            } finally {
                fw.flush();
                fw.close();
                reader.close();
            }
        } catch (Exception e) {
            System.out.println("Exception executing the function ......!!" + e.getStackTrace());
        }
    }

    public void JSONArrayAppendTimestampfunction(String fileName, String ArrayJsonPath, String replaceValueKey)
            throws Exception {

        String path = filePath + "/" + fileName;
        System.out.println(path);
        File file = new File(path);
        FileReader reader = new FileReader(file);

        try {

            JSONObject[] root = mapper.readValue(file, JSONObject[].class);
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

            String jString = mapper.writeValueAsString(root);
            System.out.println(jString);

            net.minidev.json.JSONArray jArray = com.jayway.jsonpath.JsonPath.read(jString, ArrayJsonPath);

            String oldValue = (String) jArray.get(0);
            System.out.println(oldValue);

            String newValueKey = timeStamp + replaceValueKey;

            // JSONArray jsonRoutes= (JSONArray) obj.get("addresses");
            Configuration configuration = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
                    .mappingProvider(new JacksonMappingProvider()).build();

            JsonNode updatedJson = com.jayway.jsonpath.JsonPath.using(configuration).parse(jString)
                    .set(ArrayJsonPath, newValueKey).json();
            jString = updatedJson.toString();

            System.out.println(jString);

            System.out.println("Replaced old value:............. " + oldValue + "  with .............." + newValueKey);
            putVals.add(newValueKey);

            FileWriter fw = new FileWriter(file);
            try {
                fw.write(jString);
                System.out.println("Successfully updated json object to file...!!");
            } catch (IOException ioe) {
                System.out.println("Exception opening or accessing the file......!!" + ioe.getMessage());
                System.out.println("Exception opening or accessing the file......!!" + ioe.getStackTrace());

            } finally {
                fw.flush();
                fw.close();
                reader.close();
            }
        } catch (Exception e) {
            System.out.println("Exception executing the function ......!!" + e.getStackTrace());
        }
    }

    public String getFromJSON(String fileName, String pathJson) {
        String path = filePath + "/" + fileName;
        System.out.println(path);
        File file = new File(path);

        String jso = "";
        try {

            JSONObject root = mapper.readValue(file, JSONObject.class);
            jso = root.toJSONString();
            System.out.println(jso);

            Configuration configuration = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
                    .mappingProvider(new JacksonMappingProvider()).build();

            JsonNode updatedJson = com.jayway.jsonpath.JsonPath.using(configuration).parse(jso).read(pathJson);
            jso = updatedJson.toString().replaceAll("\"", "");
            System.out.println(jso);
        } catch (Exception e) {
            System.out.println("Exception executing the function ......!!" + e.getStackTrace());
        }
        return jso;
    }

    public void add_JSONPathJsonValue(String fileName, String jsonPath, String jsonValue) throws Exception {
        String path = filePath + "/" + fileName;
        System.out.println(path);
        File file = new File(path);
        FileReader reader = new FileReader(file);

        try {

            JSONObject root = mapper.readValue(file, JSONObject.class);

            String jso = root.toJSONString();
            System.out.println(jso);

            Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL)
                    .addOptions(Option.SUPPRESS_EXCEPTIONS);

            DocumentContext documentContext = com.jayway.jsonpath.JsonPath.using(conf).parse(jso);
            documentContext.set(com.jayway.jsonpath.JsonPath.compile(jsonPath), jsonValue);
            jso = documentContext.jsonString();

            System.out.println(jso);

            FileWriter fw = new FileWriter(file);
            try {
                fw.write(jso.toString());
                System.out.println("Successfully updated json object to file...!!");
            } catch (IOException ioe) {
                System.out.println("Exception opening or accessing the file......!!" + ioe.getMessage());
                System.out.println("Exception opening or accessing the file......!!" + ioe.getStackTrace());

            } finally {
                fw.flush();
                fw.close();
                reader.close();
            }
        }

        catch (Exception e) {
            System.out.println("Exception executing the function ......!!" + e.getStackTrace());
        }
    }

    public void add_JSONPathJsonValue(String fileName, String jsonPath, Boolean jsonValue) throws Exception {
        String path = filePath + "/" + fileName;
        System.out.println(path);
        File file = new File(path);
        FileReader reader = new FileReader(file);

        try {

            JSONObject root = mapper.readValue(file, JSONObject.class);

            String jso = root.toJSONString();
            System.out.println(jso);

            Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL)
                    .addOptions(Option.SUPPRESS_EXCEPTIONS);

            DocumentContext documentContext = com.jayway.jsonpath.JsonPath.using(conf).parse(jso);
            documentContext.set(com.jayway.jsonpath.JsonPath.compile(jsonPath), jsonValue);
            jso = documentContext.jsonString();

            System.out.println(jso);

            FileWriter fw = new FileWriter(file);
            try {
                fw.write(jso);
                System.out.println("Successfully updated json object to file...!!");
            } catch (IOException ioe) {
                System.out.println("Exception opening or accessing the file......!!" + ioe.getMessage());
                System.out.println("Exception opening or accessing the file......!!" + ioe.getStackTrace());
            } finally {
                fw.flush();
                fw.close();
                reader.close();
            }
        } catch (Exception e) {
            System.out.println("Exception executing the function ......!!" + e.getStackTrace());
        }
    }

    public void remove_JSONPath(String fileName, String JsonPath) throws Exception {
        String path = filePath + "/" + fileName;
        System.out.println(path);
        File file = new File(path);
        FileReader reader = new FileReader(file);

        try {

            JSONObject root = mapper.readValue(file, JSONObject.class);

            String jso = root.toJSONString();
            System.out.println(jso);

            Configuration configuration = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
                    .mappingProvider(new JacksonMappingProvider()).build();

            JsonNode updatedJson = com.jayway.jsonpath.JsonPath.using(configuration).parse(jso).delete(JsonPath).json();

            jso = updatedJson.toString();

            System.out.println(jso);

            FileWriter fw = new FileWriter(file);
            try {
                fw.write(jso);
                System.out.println("Successfully updated json object to file...!!");
            } catch (IOException ioe) {
                System.out.println("Exception opening or accessing the file......!!" + ioe.getMessage());
                System.out.println("Exception opening or accessing the file......!!" + ioe.getStackTrace());

            } finally {
                fw.flush();
                fw.close();
                reader.close();
            }
        } catch (Exception e) {
            System.out.println("Exception executing the function ......!!" + e.getStackTrace());
        }
    }

    public org.json.JSONObject getJsonFromArray(String fileName) throws IOException {
        String path = filePath + "/" + fileName;
        String jString = "";

        jString = new String(Files.readAllBytes(Paths.get(path)));
        JSONArray jsonArr = new JSONArray(jString);
        org.json.JSONObject jObject = (org.json.JSONObject) jsonArr.get(0);

        return jObject;
    }

    public void updateJSONFileValues(String fileName, String email, String firstName, String lastName)
            throws Exception {
        update_JSONValue(fileName, "$.email", email);
        update_JSONValue(fileName, "$.firstName", firstName);
        update_JSONValue(fileName, "$.lastName", lastName);
    }

    public boolean isPathExists(String fileName, String jsonPath) {
        String path = filePath + "/" + fileName;
        File file = new File(path);

        try {
            JSONObject root = mapper.readValue(file, JSONObject.class);
            String jso = root.toJSONString();
            Configuration configuration = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
                    .mappingProvider(new JacksonMappingProvider()).build();

            com.jayway.jsonpath.JsonPath.using(configuration).parse(jso).read(jsonPath);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void verifyJSONObject(String path, Response resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path);

        JSONObject root = mapper.readValue(file, JSONObject.class);
        procJson = root.toString();
        System.out.println(root.toString());

        System.out.println(resp.getBody().asString());

        Map<String, Object> m1 = (Map<String, Object>) (mapper.readValue(procJson, Map.class));
        Map<String, Object> m2 = (Map<String, Object>) (mapper.readValue(resp.getBody().asString(), Map.class));
        System.out.println(m1);
        System.out.println(m2);
        System.out.println(m1.equals(m2));

        assertTrue(m1.equals(m2));
    }

    public static void verifyJSONArray(String path, Response resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(path);

        JSONArray m1 = mapper.readValue(file, JSONArray.class);
        JSONArray m2 = mapper.readValue(resp.getBody().asString(), JSONArray.class);
        assertTrue(m1.equals(m2));
    }

    // Convert Json String to Hash Map using Google GSON
    public static HashMap<String, String> jsonToHash(String json) {
        System.out.println("GSON function Json string " + json);
        HashMap<String, String> map = new HashMap<String, String>();
        map = new Gson().fromJson(json, new TypeToken<HashMap<String, String>>() {
        }.getType());
        System.out.println("Json to MAP: " + map);
        return map;
    }

    // Convert Json file to String
    public String toJsonString(String filePath) {
        String json = null;
        try {
            JSONParser parser = new JSONParser();
            // Use JSONObject for simple JSON and JSONArray for array of JSON.
            JSONObject data = (JSONObject) parser.parse(new FileReader(filePath));// path to the JSON file.

            json = data.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * This function converts text of any encoding to utf-8
     * @param text
     * @return
     */
    public String convertToUTF8(String text) {
        String utfString = new String(text.getBytes(Charset.forName("utf-8")));
        return utfString;
    }
    
    public String getFromJSONInUTF8(String fileName, String pathJson) {
        String path = filePath + "/" + fileName;
        LOGGER.info("File path is"+path);
        File file = new File(path);

        String jso = "";
        String jsoInUTF8="";
        try {

            JSONObject root = mapper.readValue(file, JSONObject.class);
            jso = root.toJSONString();

            Configuration configuration = Configuration.builder().jsonProvider(new JacksonJsonNodeJsonProvider())
                    .mappingProvider(new JacksonMappingProvider()).build();

            JsonNode updatedJson = com.jayway.jsonpath.JsonPath.using(configuration).parse(jso).read(pathJson);
            jso = updatedJson.toString().replaceAll("\"", "");
            jsoInUTF8=convertToUTF8(jso);
            LOGGER.info("Json in UFT8 format is"+jsoInUTF8);
        }
        catch (Exception e) {
            LOGGER.error("Exception executing the function ......!!" + e.getStackTrace());
        }
        return jsoInUTF8;
    }


    public void update_JSONPaymentDetails(String fileName, String paymentId, String currencyCode, HashMap cardDetails) throws Exception {
        if (paymentId  == null) {
            remove_JSONPath(fileName, "paymentId");
        } else {
            add_JSONPathJsonValue(fileName, "paymentId", paymentId);
        }
        update_JSONValue(fileName, "currencyCode", currencyCode);
        update_JSONValue(fileName, "creditCardInfo.expirationMonth", (int) cardDetails.get("month"));
        update_JSONValue(fileName, "creditCardInfo.expirationYear", (int) cardDetails.get("year"));
        update_JSONValue(fileName, "creditCardInfo.lastFour", String.valueOf(cardDetails.get("lastFour")));
    }
}
