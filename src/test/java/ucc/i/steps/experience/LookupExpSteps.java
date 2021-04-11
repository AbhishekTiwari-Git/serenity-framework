package ucc.i.steps.experience;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ucc.i.method.lookupexp.LookupExpGET;
import ucc.pages.ui.CommonFunc;
import ucc.utils.ResponseCode;
import ucc.utils.TestUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class LookupExpSteps {
	
	private static EnvironmentVariables envVar = SystemEnvironmentVariables.createEnvironmentVariables();
    private static String autoEmail =  EnvironmentSpecificConfiguration.from(envVar)
            .getProperty("autoEmail");
    
    private static Response expResp = null;
    CommonFunc commonFunc = new CommonFunc();
    public static Map<String, String> kmap = new HashMap<String, String>();
    private TestUtils tUtil = new TestUtils();
    private static String endPoint = null;
    
    
    
    @Steps
    LookupExpGET lookupExpGET;
    
    
    
    private final String countries = "LookupExp/countries.json";
    private final String catalyst_roles = "LookupExp/catalyst_roles.json";
    private final String catalyst_profitStatuses = "LookupExp/catalyst_profitStatuses.json";
    private final String catalystPhysicianOrgSizes = "LookupExp/catalystPhysicianOrgSizes.json";
    private final String catalyst_netPatientRevenues = "LookupExp/catalyst_netPatientRevenues.json";
    private final String catalyst_hospitalSizes = "LookupExp/catalyst_hospitalSizes.json";
    private final String catalyst_healthSystemSizes = "LookupExp/catalyst_healthSystemSizes.json";
    private final String catalyst_buyingInfluences = "LookupExp/catalyst_buyingInfluences.json";
    private final String countryDomainGDPR = "LookupExp/countryDomainGDPR.json";
    private final String titles = "LookupExp/titles.json";
    private final String suffixes = "LookupExp/suffixes.json";
    private final String studentTypes = "LookupExp/studentTypes.json";
    private final String professionalCategories = "LookupExp/professionalCategories.json";
    private final String professions = "LookupExp/professions.json";
    private final String primarySpecialities = "LookupExp/primarySpecialities.json";
    private final String placeOfWorkOrStudy = "LookupExp/placeOfWorkOrStudy.json";
    private final String states = "LookupExp/states.json";
    private final String brand = "LookupExp/brand.json";
    
    
 
    @When("^user sends a GET request to Lookup Exp API for invalid_value (.*)$")
    public void user_sends_a_get_request_to_lookup_exp_api_for_invalid_value(String invalid_value) {
    	expResp = lookupExpGET.get(invalid_value)
    			.extract().response();
    	
    	tUtil.putToSession("response", expResp);
    }
    
    @When("I do a GET call to countries")
    public void i_do_a_get_call_to_countries() {
    	endPoint = lookupExpGET.setEndpointToCountries();
    	expResp = lookupExpGET.get(endPoint)
    			.extract().response();
    }

    @Then("I see all countries in the response")
    public void i_see_all_countries_in_the_response() throws IOException {
        tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_json_to_response(expResp, countries);
    }
    
    @When("I do a GET call to catalyst_roles")
    public void i_do_a_get_call_to_catalyst_roles() {
    	endPoint = lookupExpGET.setEndpointToCatalystRoles();
    	expResp = lookupExpGET.get(endPoint)
    			.extract().response();
    }

    @Then("I see all catalyst_roles in the response")
    public void i_see_all_catalyst_roles_in_the_response() throws IOException {
    	tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_json_to_response(expResp, catalyst_roles);
    }

    @When("I do a GET call to catalyst_profitStatuses")
    public void i_do_a_get_call_to_catalyst_profit_statuses() {
    	endPoint = lookupExpGET.setEndpointToCatalyst_profitStatuses();
    	expResp = lookupExpGET.get(endPoint)
    			.extract().response();
    }

    @Then("I see all catalyst_profitStatuses in the response")
    public void i_see_all_catalyst_profit_statuses_in_the_response() throws IOException {
    	tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_json_to_response(expResp, catalyst_profitStatuses);
    }

    @When("I do a GET call to catalystPhysicianOrgSizes")
    public void i_do_a_get_call_to_catalyst_physician_org_sizes() {
    	endPoint = lookupExpGET.setEndpointToCatalystPhysicianOrgSizes();
    	expResp = lookupExpGET.get(endPoint)
    			.extract().response();
    }

    @Then("I see all catalystPhysicianOrgSizes in the response")
    public void i_see_all_catalyst_physician_org_sizes_in_the_response() throws IOException {
    	tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_json_to_response(expResp, catalystPhysicianOrgSizes);
    }

    @When("I do a GET call to catalyst_netPatientRevenues")
    public void i_do_a_get_call_to_catalyst_net_patient_revenues() {
    	endPoint = lookupExpGET.setEndpointToCatalyst_netPatientRevenues();
    	expResp = lookupExpGET.get(endPoint)
    			.extract().response();
    }

    @Then("I see all catalyst_netPatientRevenues in the response")
    public void i_see_all_catalyst_net_patient_revenues_in_the_response() throws IOException {
    	tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_json_to_response(expResp, catalyst_netPatientRevenues);
    }

    @When("I do a GET call to catalyst_hospitalSizes")
    public void i_do_a_get_call_to_catalyst_hospital_sizes() {
    	endPoint = lookupExpGET.setEndpointToCatalyst_hospitalSizes();
    	expResp = lookupExpGET.get(endPoint)
    			.extract().response();
    }

    @Then("I see all catalyst_hospitalSizes in the response")
    public void i_see_all_catalyst_hospital_sizes_in_the_response() throws IOException {
    	tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_json_to_response(expResp, catalyst_hospitalSizes);
    }

    @When("I do a GET call to catalyst_healthSystemSizes")
    public void i_do_a_get_call_to_catalyst_health_system_sizes() {
    	endPoint = lookupExpGET.setEndpointToCatalyst_healthSystemSizes();
    	expResp = lookupExpGET.get(endPoint)
    			.extract().response();
    }

    @Then("I see all catalyst_healthSystemSizes in the response")
    public void i_see_all_catalyst_health_system_sizes_in_the_response() throws IOException {
    	tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_json_to_response(expResp, catalyst_healthSystemSizes);
    }

    @When("I do a GET call to catalyst_buyingInfluences")
    public void i_do_a_get_call_to_catalyst_buying_influences() {
    	endPoint = lookupExpGET.setEndpointToCatalyst_buyingInfluences();
    	expResp = lookupExpGET.get(endPoint)
    			.extract().response();
    }

    @Then("I see all catalyst_buyingInfluences in the response")
    public void i_see_all_catalyst_buying_influences_in_the_response() throws IOException {
    	tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_json_to_response(expResp, catalyst_buyingInfluences);
    }

    @When("I do a GET call to titles")
    public void i_do_a_get_call_to_titles() {
    	endPoint = lookupExpGET.setEndpointToTitles();
    	expResp = lookupExpGET.get(endPoint)
    			.extract().response();
    }

    @Then("I see all titles in the response")
    public void i_see_all_titles_in_the_response() throws IOException {
    	tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_json_to_response(expResp, titles);
    }

    @When("I do a GET call to countryDomainGDPR")
    public void i_do_a_get_call_to_country_domain_gdpr() {
    	endPoint = lookupExpGET.setEndpointToCountryDomainGDPR();
    	expResp = lookupExpGET.get(endPoint)
    			.extract().response();
    }

    @Then("I see all countryDomainGDPR in the response")
    public void i_see_all_country_domain_gdpr_in_the_response() throws IOException {
    	tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_json_to_response(expResp, countryDomainGDPR);
    }

    @When("I do a GET call to suffixes")
    public void i_do_a_get_call_to_suffixes() {
    	endPoint = lookupExpGET.setEndpointToSuffixes();
    	expResp = lookupExpGET.get(endPoint)
    			.extract().response();
    }

    @Then("I see all suffixes in the response")
    public void i_see_all_suffixes_in_the_response() throws IOException {
    	tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_json_to_response(expResp, suffixes);
    }

    @When("I do a GET call to studentTypes")
    public void i_do_a_get_call_to_student_types() {
    	endPoint = lookupExpGET.setEndpointToStudentTypes();
    	expResp = lookupExpGET.get(endPoint)
    			.extract().response();
    }

    @Then("I see all studentTypes in the response")
    public void i_see_all_student_types_in_the_response() throws IOException {
    	tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_json_to_response(expResp, studentTypes);
    }

    @When("I do a GET call to professionalCategories")
    public void i_do_a_get_call_to_professional_categories() {
    	endPoint = lookupExpGET.setEndpointToProfessionalCategories();
    	expResp = lookupExpGET.get(endPoint)
    			.extract().response();
    }

    @Then("I see all professionalCategories in the response")
    public void i_see_all_professional_categories_in_the_response() throws IOException {
    	tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_json_to_response(expResp, professionalCategories);
    }

    @When("I do a GET call to professions")
    public void i_do_a_get_call_to_professions() {
    	endPoint = lookupExpGET.setEndpointToProfessions();
    	expResp = lookupExpGET.get(endPoint)
    			.extract().response();
    }

    @Then("I see all professions in the response")
    public void i_see_all_professions_in_the_response() throws IOException {
    	tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_json_to_response(expResp, professions);
    }

    @When("I do a GET call to primarySpecialities")
    public void i_do_a_get_call_to_primary_specialities() {
    	endPoint = lookupExpGET.setEndpointToPrimarySpecialities();
    	expResp = lookupExpGET.get(endPoint)
    			.extract().response();
    }

    @Then("I see all primarySpecialities in the response")
    public void i_see_all_primary_specialities_in_the_response() throws IOException {
    	tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_json_to_response(expResp, primarySpecialities);
    }

    @When("I do a GET call to placeOfWorkOrStudy")
    public void i_do_a_get_call_to_place_of_work_or_study() {
    	endPoint = lookupExpGET.setEndpointToPlaceOfWorkOrStudy();
    	expResp = lookupExpGET.get(endPoint)
    			.extract().response();
    }

    @Then("I see all placeOfWorkOrStudy in the response")
    public void i_see_all_place_of_work_or_study_in_the_response() throws IOException {
    	tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_json_to_response(expResp, placeOfWorkOrStudy);
    }

    @When("I do a GET call to states")
    public void i_do_a_get_call_to_states() {
    	endPoint = lookupExpGET.setEndpointToStates();
    	expResp = lookupExpGET.get(endPoint)
    			.extract().response();
    }

    @Then("I see all states in the response")
    public void i_see_all_states_in_the_response() throws IOException {
    	tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_json_to_response(expResp, states);
    }
    
    @When("I do a GET call to brand")
    public void i_do_a_get_call_to_brand() {
    	endPoint = lookupExpGET.setEndpointToBrand();
    	expResp = lookupExpGET.get(endPoint)
    			.extract().response();
    }

    @Then("I see all the brand in the response")
    public void i_see_all_the_brand_in_the_response() throws IOException {
    	tUtil.verifyStatus(expResp, ResponseCode.OK);
        tUtil.verify_json_to_response(expResp, brand);
    }
}
