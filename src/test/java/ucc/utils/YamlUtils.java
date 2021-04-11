package ucc.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import java.io.File;
import java.io.IOException;


public class YamlUtils {

    private static final EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();

    public JsonNode convertYmlToJson(String ymlFileName) throws IOException {
        String file_path = envVar.getProperty("yaml.body.path");
        String path = file_path + "/" + ymlFileName;
        System.out.println(path);
        File file = new File(path);

        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        Object yaml = yamlReader.readValue(file, Object.class);

        ObjectMapper jsonWriter = new ObjectMapper();
        return jsonWriter.convertValue(yaml, JsonNode.class);
    }

    public String getValueFromYml(String ymlFileName, String pathValue) throws IOException {
        String file_path = envVar.getProperty("yaml.body.path");
        String path = file_path + "/" + ymlFileName;
        System.out.println(path);
        File file = new File(path);

        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        Object yaml = yamlReader.readValue(file, Object.class);

        ObjectMapper jsonWriter = new ObjectMapper();
        JsonNode jsonNode = jsonWriter.convertValue(yaml, JsonNode.class);
        return String.valueOf(jsonNode.at(pathValue));
    }
}
