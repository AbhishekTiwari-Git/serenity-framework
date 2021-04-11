package ucc.i.method.accountexp;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UpdateAccountFiles {

    static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
    public static final String AUTO_EMAIL = EnvironmentSpecificConfiguration.from(env_var).getProperty("autoEmail");
    public static final String PROMO_CODE = EnvironmentSpecificConfiguration.from(env_var).getProperty("promoCode");
    static TestUtils tUtil = new TestUtils();
    static JsonUtils jsonUtils = new JsonUtils();
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static String date = dateFormat.format(new Date());
    public static Map<String, String> Acc_helper_kmap = new HashMap<String, String>();
    static String filePath = env_var.getProperty("json.body.path");

    public static void update_File_Reg_Catalyst(String file, Map<String, String> row) throws Exception {

        String path = filePath + "/" + file;

        Map<String, String> kmap = new HashMap<>();

        jsonUtils.update_JSONValue(file, "$.email", tUtil.AppendTimestamp(AUTO_EMAIL));
        jsonUtils.update_JSONValue(file, "$.firstName", tUtil.AppendTimestamp(row.get("firstName")));
        jsonUtils.update_JSONValue(file, "$.lastName", tUtil.AppendTimestamp(row.get("lastName")));
        jsonUtils.update_JSONValue(file, "$.suffix", row.get("suffix"));
        jsonUtils.update_JSONValue(file, "$.placeOfWorkOrStudy", row.get("placeOfWorkOrStudy"));
        // jsonUtils.update_JSONValue(file, "$.specialty", row.get("specialty"));
        jsonUtils.update_JSONValue(file, "$.country", row.get("country"));
        // jsonUtils.update_JSONValue(file, "$.audienceSegment",
        // row.get("audienceSegment"));
        // jsonUtils.update_JSONValue(file, "$.registrationSource",
        // row.get("registrationSource"));
        jsonUtils.update_JSONValue(file, "$.professionalCategory", row.get("professionalCategory"));
        jsonUtils.update_JSONValue(file, "$.nameOfOrganization", row.get("nameOfOrganization"));
        jsonUtils.update_JSONValue(file, "$.role", row.get("role"));
        jsonUtils.update_JSONValue(file, "$.qualifiedForCouncil", Boolean.parseBoolean(row.get("qualifiedForCouncil")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptIn",
                Boolean.parseBoolean(row.get("catalystConnectOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptInDate", date);
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptIn",
                Boolean.parseBoolean(row.get("catalystSOIOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptInDate", date);

        // kmap = jsonUtils.jsonToMap(path);

    }

    public static void update_File_Reg_Catalyst_excel(String file, Map<String, String> row) throws Exception {

        String path = filePath + "/" + file;

        Map<String, String> kmap = new HashMap<>();

        jsonUtils.update_JSONValue(file, "$.email", tUtil.AppendTimestamp(AUTO_EMAIL));
        jsonUtils.update_JSONValue(file, "$.firstName", tUtil.AppendTimestamp("fname"));
        jsonUtils.update_JSONValue(file, "$.lastName", tUtil.AppendTimestamp("lname"));
        jsonUtils.update_JSONValue(file, "$.suffix", row.get("suffix"));
        jsonUtils.update_JSONValue(file, "$.placeOfWorkOrStudy", row.get("placeOfWorkOrStudy"));
        jsonUtils.update_JSONValue(file, "$.specialty", row.get("specialty"));
        jsonUtils.update_JSONValue(file, "$.country", row.get("country"));
        // jsonUtils.update_JSONValue(file, "$.audienceSegment",
        // row.get("audienceSegment"));
        // jsonUtils.update_JSONValue(file, "$.registrationSource",
        // row.get("registrationSource"));
        jsonUtils.update_JSONValue(file, "$.professionalCategory", row.get("professionalCategory"));
        jsonUtils.update_JSONValue(file, "$.nameOfOrganization", row.get("nameOfOrganization"));
        jsonUtils.update_JSONValue(file, "$.role", row.get("role"));
        jsonUtils.update_JSONValue(file, "$.qualifiedForCouncil", Boolean.parseBoolean(row.get("qualifiedForCouncil")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptIn",
                Boolean.parseBoolean(row.get("catalystConnectOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptInDate", date);
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptIn",
                Boolean.parseBoolean(row.get("catalystSOIOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptInDate", date);

        // kmap = jsonUtils.jsonToMap(path);

    }

    public static Map<String, String> update_File_Reg_Catalyst_All_fields(String file, Map<String, String> row)
            throws Exception {

        Map<String, String> kmap = new HashMap<>();

        String email = tUtil.AppendTimestamp(AUTO_EMAIL);
        String fname = tUtil.AppendTimestamp(row.get("firstName"));
        String lname = tUtil.AppendTimestamp(row.get("lastName"));
        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.firstName", fname);
        jsonUtils.update_JSONValue(file, "$.lastName", lname);
        jsonUtils.update_JSONValue(file, "$.suffix", row.get("suffix"));
        jsonUtils.update_JSONValue(file, "$.placeOfWorkOrStudy", row.get("placeOfWorkOrStudy"));
        jsonUtils.update_JSONValue(file, "$.specialty", row.get("specialty"));
        jsonUtils.update_JSONValue(file, "$.country", row.get("country"));
        jsonUtils.update_JSONValue(file, "$.audienceSegment", row.get("audienceSegment"));
        jsonUtils.update_JSONValue(file, "$.registrationSource", row.get("registrationSource"));
        jsonUtils.update_JSONValue(file, "$.professionalCategory", row.get("professionalCategory"));
        jsonUtils.update_JSONValue(file, "$.nameOfOrganization", row.get("nameOfOrganization"));
        jsonUtils.update_JSONValue(file, "$.role", row.get("role"));
        jsonUtils.update_JSONValue(file, "$.qualifiedForCouncil", Boolean.parseBoolean(row.get("qualifiedForCouncil")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptIn",
                Boolean.parseBoolean(row.get("catalystConnectOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptInDate", date);
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptIn",
                Boolean.parseBoolean(row.get("catalystSOIOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptInDate", date);

        kmap = JsonUtils.jsonToHash(file);

        return kmap;

    }

    public static void update_File_Reg_Catalyst_Mandatory_fields(String file, Map<String, String> row)
            throws Exception {
        String email = tUtil.AppendTimestamp(AUTO_EMAIL);
        String fname = tUtil.AppendTimestamp(row.get("firstName"));
        String lname = tUtil.AppendTimestamp(row.get("lastName"));
        jsonUtils.update_JSONValue(file, "$.email", email);
        jsonUtils.update_JSONValue(file, "$.firstName", fname);
        jsonUtils.update_JSONValue(file, "$.lastName", lname);
        jsonUtils.update_JSONValue(file, "$.suffix", row.get("suffix"));
        jsonUtils.update_JSONValue(file, "$.placeOfWorkOrStudy", row.get("placeOfWorkOrStudy"));
        jsonUtils.update_JSONValue(file, "$.country", row.get("country"));
        jsonUtils.update_JSONValue(file, "$.professionalCategory", row.get("professionalCategory"));
        jsonUtils.update_JSONValue(file, "$.nameOfOrganization", row.get("nameOfOrganization"));
        jsonUtils.update_JSONValue(file, "$.role", row.get("role"));
        Acc_helper_kmap.put("Email", email);
        Acc_helper_kmap.put("Fname", fname);
        Acc_helper_kmap.put("Lname", lname);

    }

    public static void update_File_Reg_Catalyst_Mandatory_Lead_to_Register(String file, Map<String, String> row,
            String ucid) throws Exception {

        jsonUtils.update_JSONValue(file, "$.email", Acc_helper_kmap.get("Email"));
        jsonUtils.update_JSONValue(file, "$.firstName", Acc_helper_kmap.get("Fname"));
        jsonUtils.update_JSONValue(file, "$.lastName", Acc_helper_kmap.get("Lname"));
        jsonUtils.update_JSONValue(file, "$.suffix", row.get("suffix"));
        jsonUtils.update_JSONValue(file, "$.placeOfWorkOrStudy", row.get("placeOfWorkOrStudy"));
        jsonUtils.update_JSONValue(file, "$.country", row.get("country"));
        jsonUtils.update_JSONValue(file, "$.professionalCategory", row.get("professionalCategory"));
        jsonUtils.update_JSONValue(file, "$.nameOfOrganization", row.get("nameOfOrganization"));
        jsonUtils.update_JSONValue(file, "$.role", row.get("role"));
        jsonUtils.update_JSONValue(file, "$.ucid", ucid);

    }

    public static void update_File_Reg_Catalyst_AllFields_Lead_to_Register(String file, Map<String, String> row,
            String ucid) throws Exception {

        jsonUtils.update_JSONValue(file, "$.email", Acc_helper_kmap.get("Email"));
        jsonUtils.update_JSONValue(file, "$.firstName", Acc_helper_kmap.get("Fname"));
        jsonUtils.update_JSONValue(file, "$.lastName", Acc_helper_kmap.get("Lname"));
        jsonUtils.update_JSONValue(file, "$.suffix", row.get("suffix"));
        jsonUtils.update_JSONValue(file, "$.placeOfWorkOrStudy", row.get("placeOfWorkOrStudy"));
        jsonUtils.update_JSONValue(file, "$.specialty", row.get("specialty"));
        jsonUtils.update_JSONValue(file, "$.country", row.get("country"));
        jsonUtils.update_JSONValue(file, "$.audienceSegment", row.get("audienceSegment"));
        jsonUtils.update_JSONValue(file, "$.registrationSource", row.get("registrationSource"));
        jsonUtils.update_JSONValue(file, "$.professionalCategory", row.get("professionalCategory"));
        jsonUtils.update_JSONValue(file, "$.nameOfOrganization", row.get("nameOfOrganization"));
        jsonUtils.update_JSONValue(file, "$.role", row.get("role"));
        jsonUtils.update_JSONValue(file, "$.qualifiedForCouncil", Boolean.parseBoolean(row.get("qualifiedForCouncil")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptIn",
                Boolean.parseBoolean(row.get("catalystConnectOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptInDate", date);
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptIn",
                Boolean.parseBoolean(row.get("catalystSOIOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptInDate", date);

    }

    public static void update_File_Reg_Email(String file, Map<String, String> row) throws Exception {

        jsonUtils.update_JSONValue(file, "$.email", tUtil.AppendTimestamp(AUTO_EMAIL));
        jsonUtils.update_JSONValue(file, "$.firstName", tUtil.AppendTimestamp(row.get("firstName")));
        jsonUtils.update_JSONValue(file, "$.lastName", tUtil.AppendTimestamp(row.get("lastName")));
        jsonUtils.update_JSONValue(file, "$.country", row.get("country"));
        jsonUtils.update_JSONValue(file, "$.registrationSource", row.get("registrationSource"));
        jsonUtils.update_JSONValue(file, "$.nameOfOrganization", row.get("nameOfOrganization"));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptIn",
                Boolean.parseBoolean(row.get("catalystConnectOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptInDate", date);
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptIn",
                Boolean.parseBoolean(row.get("catalystSOIOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptInDate", date);
    }

    public static void update_File_Reg_Mandatory_Fields_Email(String file, Map<String, String> row) throws Exception {

        jsonUtils.update_JSONValue(file, "$.email", tUtil.AppendTimestamp(AUTO_EMAIL));
        jsonUtils.update_JSONValue(file, "$.firstName", tUtil.AppendTimestamp(row.get("firstName")));
        jsonUtils.update_JSONValue(file, "$.lastName", tUtil.AppendTimestamp(row.get("lastName")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptIn",
                Boolean.parseBoolean(row.get("catalystConnectOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptInDate", date);
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptIn",
                Boolean.parseBoolean(row.get("catalystSOIOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptInDate", date);
    }

    public static void update_File_Reg_PDF(String file, Map<String, String> row) throws Exception {

        jsonUtils.update_JSONValue(file, "$.email", tUtil.AppendTimestamp(AUTO_EMAIL));
        jsonUtils.update_JSONValue(file, "$.firstName", tUtil.AppendTimestamp(row.get("firstName")));
        jsonUtils.update_JSONValue(file, "$.lastName", tUtil.AppendTimestamp(row.get("lastName")));
        jsonUtils.update_JSONValue(file, "$.suffix", row.get("suffix"));
        jsonUtils.update_JSONValue(file, "$.placeOfWorkOrStudy", row.get("placeOfWorkOrStudy"));
        jsonUtils.update_JSONValue(file, "$.specialty", row.get("specialty"));
        jsonUtils.update_JSONValue(file, "$.country", row.get("country"));
        jsonUtils.update_JSONValue(file, "$.audienceSegment", row.get("audienceSegment"));
        jsonUtils.update_JSONValue(file, "$.registrationSource", row.get("registrationSource"));
        jsonUtils.update_JSONValue(file, "$.professionalCategory", row.get("professionalCategory"));
        jsonUtils.update_JSONValue(file, "$.nameOfOrganization", row.get("nameOfOrganization"));
        jsonUtils.update_JSONValue(file, "$.role", row.get("role"));
        jsonUtils.update_JSONValue(file, "$.qualifiedForCouncil", Boolean.parseBoolean(row.get("qualifiedForCouncil")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptIn",
                Boolean.parseBoolean(row.get("catalystConnectOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptInDate", date);
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptIn",
                Boolean.parseBoolean(row.get("catalystSOIOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptInDate", date);
        jsonUtils.update_JSONValue(file, "$.catalyst.pdfPremiums.registrationDate", date);
    }

    public void update_jsonFile_for_activate(String file) throws Exception {

        jsonUtils.update_JSONValue(file, "email", (String) tUtil.getFromSession("email"));
        jsonUtils.update_JSONValue(file, "lastName", (String) tUtil.getFromSession("lastName"));
        jsonUtils.update_JSONValue(file, "country", (String) tUtil.getFromSession("countryCode"));
        jsonUtils.update_JSONValue(file, "postalCode", (String) tUtil.getFromSession("postalCode"));
        jsonUtils.update_JSONValue(file, "subscriptionId", (String) tUtil.getFromSession("customerNumber"));
    }

    public static void update_File_AMB_Reg_Catalyst_excel(String file, Map<String, String> row) throws Exception {

        String path = filePath + "/" + file;

        Map<String, String> kmap = new HashMap<>();

        jsonUtils.update_JSONValue(file, "$.email", tUtil.AppendTimestamp(AUTO_EMAIL));
        jsonUtils.update_JSONValue(file, "$.firstName", row.get("firstName"));
        jsonUtils.update_JSONValue(file, "$.lastName", tUtil.AppendTimestampForAMB(row.get("lastName")));
        jsonUtils.update_JSONValue(file, "$.suffix", row.get("suffix"));
        jsonUtils.update_JSONValue(file, "$.placeOfWorkOrStudy", row.get("placeOfWorkOrStudy"));
        jsonUtils.update_JSONValue(file, "$.specialty", row.get("specialty"));
        jsonUtils.update_JSONValue(file, "$.country", row.get("country"));
        // jsonUtils.update_JSONValue(file, "$.audienceSegment",
        // row.get("audienceSegment"));
        // jsonUtils.update_JSONValue(file, "$.registrationSource",
        // row.get("registrationSource"));
        jsonUtils.update_JSONValue(file, "$.professionalCategory", row.get("professionalCategory"));
        jsonUtils.update_JSONValue(file, "$.nameOfOrganization", row.get("nameOfOrganization"));
        jsonUtils.update_JSONValue(file, "$.role", row.get("role"));
        jsonUtils.update_JSONValue(file, "$.qualifiedForCouncil", Boolean.parseBoolean(row.get("qualifiedForCouncil")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptIn",
                Boolean.parseBoolean(row.get("catalystConnectOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptInDate", date);
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptIn",
                Boolean.parseBoolean(row.get("catalystSOIOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptInDate", date);

        // kmap = jsonUtils.jsonToMap(path);

    }

}
