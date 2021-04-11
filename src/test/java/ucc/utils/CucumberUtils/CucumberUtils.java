package ucc.utils.CucumberUtils;

import io.cucumber.datatable.DataTable;

import java.util.Map;
import java.util.stream.Collectors;

public class CucumberUtils {
    public static Map<String, String> convert(DataTable dataTable) {
        Map<Object, Object> tmp = (dataTable.asMaps(String.class, String.class).get(0));
        return tmp.entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                entry -> entry.getKey().toString(),
                                entry -> entry.getValue().toString()
                        )
                );
    }
}
