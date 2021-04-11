package ucc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewRelicJson {

    private static Boolean jiraFlag = false;
    private static final Logger LOGGER = LoggerFactory.getLogger(NewRelicJson.class);

    public static void main(String[] args) throws Throwable {
        TestUtils tUtils = new TestUtils();

        
        jiraFlag = checkJira();
        if (jiraFlag) {
            GenerateJsonReport.generateJsonReport();
            tUtils.postNewRelic();
            LOGGER.info("New Relic Posting is Successful");
        } else {
            LOGGER.info("New Relic Posting can not be done due to Local Execution");
        }
    }

    public static Boolean checkJira() {

        if (System.getProperty("cucumber.filter.tags") != null) {
            String fName = System.getProperty("cucumber.filter.tags");

            if (fName.contains("@Cycle")) {
                jiraFlag = true;
            } else {
                jiraFlag = false;
            }
        }
        return jiraFlag;

    }
}