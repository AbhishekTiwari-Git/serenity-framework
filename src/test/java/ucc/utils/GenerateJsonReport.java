package ucc.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class GenerateJsonReport {
    
    static EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
    static String jiraUrl = variables.getProperty("jira1.url");
    
    static Map<String, String> convertStatus = new HashMap<String, String>() {/**
         * 
         */
        private static final long serialVersionUID = 1;

    {
        put("PASS", "testPass");
        put("FAIL", "testFail");
        put("WIP", "testWip");
        put("UNEXECUTED", "testUnexecuted");
        put("BLOCKED", "testBlocked");
        put("N/A", "testN/A");
        put("PASS W/ EXCEPTION", "testPassW/Exception");
        put("DEFER", "testDefer");
    }};
    
    @SuppressWarnings("unchecked")
    public static void generateJsonReport() throws IOException {   
        JiraConnect jiraConct = new JiraConnect();
        
        LinkedHashMap<String, String> scenarioMap = new LinkedHashMap<>();
        scenarioMap.put("eventType", "UccZephyrAutomationExecution");

        Map<String, String> cycleMap = jiraConct.getCycleInfo();

        Response response = jiraConct.getCyclesStatuses(cycleMap);
        JsonPath jsonPath = response.jsonPath();

        scenarioMap.put("product", System.getProperty("versionId").equals("19241") ? "INT" : "UCC");
        scenarioMap.put("apiName", System.getProperty("versionId").equals("19241") ? "ExpAPI" : "NA");
        scenarioMap.put("testEnvironment", System.getProperty("environment"));
        scenarioMap.put("testCycle", cycleMap.get("test_cycle_name"));
        scenarioMap.put("releaseVersion", "16.0");
        scenarioMap.put("buildVersion", "16.8.9.0rt");

        try {
            scenarioMap.put("Browser", variables.getProperty("webdriver.driver"));
        }
        catch (Exception e) {
            scenarioMap.put("Browser", "");
        }

        scenarioMap.put("totalExecuted", jsonPath.getString("totalExecutions"));

        List<HashMap<String, Object>> lstStatus = (List<HashMap<String, Object>>) jsonPath.getMap("executionSummaries").get("executionSummary");

        for (HashMap<String, Object> status: lstStatus) {
            scenarioMap.put(
                    convertStatus.get(status.get("statusName")),
                    String.valueOf(status.get("count"))
            );
        }

        File root = new File("target/site/reports/");
        FilenameFilter beginswithSERENITY = new FilenameFilter()
        {
            public boolean accept(File directory, String filename) {
                return filename.startsWith("SERENITY");
            }
        };

        File[] files = root.listFiles(beginswithSERENITY);
        if(files.length > 0) {
            for (File f: files) {
                try {
                    Double durationSec = Double.valueOf(TestUtils.parseSerenityXml(f));
                    scenarioMap.put("automationExecTimeInMins", String.valueOf(durationSec/60));
                } catch (ParserConfigurationException | SAXException e) {
                    e.printStackTrace();
                }
            }
        } else {
            scenarioMap.put("automationExecTimeInMins", "");
        }

        Map<String, String> map = TestUtils.getMapManualCycle();

        try {
            String value = map.get(cycleMap.get("test_cycle_name"));
            scenarioMap.put("ManualExecutionTime", value);
        } catch (Exception e) {
            scenarioMap.put("ManualExecutionTime", "");
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("scenario.json"), scenarioMap);
    }

}
