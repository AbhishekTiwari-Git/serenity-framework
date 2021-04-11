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

public class AccountHelper {

    static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
    static String autoEmail =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("autoEmail");
    private static TestUtils tUtil = new TestUtils();
    private static JsonUtils jsonUtils = new JsonUtils();
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static String date = dateFormat.format(new Date());
    public static Map<String,String> Acc_helper_kmap = new HashMap<String,String>();

    public static void update_File_Reg_Catalyst(String file, Map<String, String> row) throws Exception {

        jsonUtils.update_JSONValue(file, "$.email", tUtil.AppendTimestamp(autoEmail));
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
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptIn", Boolean.parseBoolean(row.get("catalystConnectOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptInDate", date);
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptIn", Boolean.parseBoolean(row.get("catalystSOIOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptInDate", date);
    }
    
    public static void update_File_Reg_Catalyst_All_fields(String file, Map<String, String> row) throws Exception {
        
    	String email = tUtil.AppendTimestamp(autoEmail);
        String fname = tUtil.AppendTimestamp(row.get("firstName"));
        String lname = tUtil.AppendTimestamp(row.get("lastName"));
        jsonUtils.update_JSONValue(file, "$.email", email );
        jsonUtils.update_JSONValue(file, "$.firstName",fname);
        jsonUtils.update_JSONValue(file, "$.lastName",lname);
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
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptIn", Boolean.parseBoolean(row.get("catalystConnectOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptInDate", date);
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptIn", Boolean.parseBoolean(row.get("catalystSOIOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptInDate", date);
    }
    
    public static void update_File_Reg_Catalyst_Mandatory_fields(String file, Map<String, String> row) throws Exception {
        String email = tUtil.AppendTimestamp(autoEmail);
        String fname = tUtil.AppendTimestamp(row.get("firstName"));
        String lname = tUtil.AppendTimestamp(row.get("lastName"));
        jsonUtils.update_JSONValue(file, "$.email", email );
        jsonUtils.update_JSONValue(file, "$.firstName",fname);
        jsonUtils.update_JSONValue(file, "$.lastName",lname);
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
    
    
    public static void update_File_Reg_Catalyst_Mandatory_Lead_to_Register(String file, Map<String, String> row, String ucid) throws Exception {

        jsonUtils.update_JSONValue(file, "$.email", Acc_helper_kmap.get("Email") );
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
    
    public static void update_File_Reg_Catalyst_AllFields_Lead_to_Register(String file, Map<String, String> row, String ucid) throws Exception {

        jsonUtils.update_JSONValue(file, "$.email", Acc_helper_kmap.get("Email") );
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
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptIn", Boolean.parseBoolean(row.get("catalystConnectOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptInDate", date);
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptIn", Boolean.parseBoolean(row.get("catalystSOIOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptInDate", date);
        
    }

    public static void update_File_Reg_Email(String file, Map<String, String> row) throws Exception {

        jsonUtils.update_JSONValue(file, "$.email", tUtil.AppendTimestamp(autoEmail));
        jsonUtils.update_JSONValue(file, "$.firstName", tUtil.AppendTimestamp(row.get("firstName")));
        jsonUtils.update_JSONValue(file, "$.lastName", tUtil.AppendTimestamp(row.get("lastName")));
        jsonUtils.update_JSONValue(file, "$.country", row.get("country"));
        jsonUtils.update_JSONValue(file, "$.registrationSource", row.get("registrationSource"));
        jsonUtils.update_JSONValue(file, "$.nameOfOrganization", row.get("nameOfOrganization"));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptIn", Boolean.parseBoolean(row.get("catalystConnectOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptInDate", date);
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptIn", Boolean.parseBoolean(row.get("catalystSOIOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptInDate", date);
    }

    public static void update_File_Reg_Mandatory_Fields_Email(String file, Map<String, String> row) throws Exception {

        jsonUtils.update_JSONValue(file, "$.email", tUtil.AppendTimestamp(autoEmail));
        jsonUtils.update_JSONValue(file, "$.firstName", tUtil.AppendTimestamp(row.get("firstName")));
        jsonUtils.update_JSONValue(file, "$.lastName", tUtil.AppendTimestamp(row.get("lastName")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptIn", Boolean.parseBoolean(row.get("catalystConnectOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptInDate", date);
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptIn", Boolean.parseBoolean(row.get("catalystSOIOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptInDate", date);
    }

    public static void update_File_Reg_PDF(String file, Map<String, String> row) throws Exception {

        jsonUtils.update_JSONValue(file, "$.email", tUtil.AppendTimestamp(autoEmail));
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
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptIn", Boolean.parseBoolean(row.get("catalystConnectOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystConnectOptInDate", date);
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptIn", Boolean.parseBoolean(row.get("catalystSOIOptIn")));
        jsonUtils.update_JSONValue(file, "$.emailPreferences.catalystSOIOptInDate", date);
        jsonUtils.update_JSONValue(file, "$.catalyst.pdfPremiums.registrationDate", date);
    }

	public static void PATCH_updateAddressFile(String file, String address1, String address2) throws Exception {

		String updatedAdd1 = tUtil.AppendTimestamp(address1);
		String updatedAdd2 = tUtil.AppendTimestamp(address2);
		jsonUtils.update_JSONValue(file, "$.address..address1", updatedAdd1);
		jsonUtils.update_JSONValue(file, "$.address..address2", updatedAdd2);
	}
}
