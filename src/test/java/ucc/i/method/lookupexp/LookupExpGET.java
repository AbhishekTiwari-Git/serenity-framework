package ucc.i.method.lookupexp;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.utils.RestUtil;
import ucc.utils.ReuseableSpecifications;

public class LookupExpGET {
	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String webserviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("api.exp.url");
	static String serviceEndpoint =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("lookupExpAPI.basePath");
	public static String LookupExp_url = webserviceEndpoint+serviceEndpoint+"/api";   
	
	
	
	public ValidatableResponse get(String endpoint) {
		
		RestUtil.setBaseURI(LookupExp_url);
		
		return	SerenityRest.rest()
				.given()
				.spec(ReuseableSpecifications.getGenericExpRequestSpec())
				.when()
				.log().all()
				.get(endpoint)
				.then()
				.log().all();

	}

	public String setEndpointToCountries() {
		  String endpoint = "/countries";
		  return endpoint;
	}
	
	public String setEndpointToStates() {
		  String endpoint = "/states";
		  return endpoint;
	}
	
	public String setEndpointToPlaceOfWorkOrStudy() {
		  String endpoint = "/places-of-work-or-study";
		  return endpoint;
	}
	
	public String setEndpointToPrimarySpecialities() {
		  String endpoint = "/primary-specialities";
		  return endpoint;
	}
	
	public String setEndpointToProfessions() {
		  String endpoint = "/professions";
		  return endpoint;
	}
	
	public String setEndpointToProfessionalCategories() {
		  String endpoint = "/professional-categories";
		  return endpoint;
	}
	
	public String setEndpointToStudentTypes() {
		  String endpoint = "/student-types";
		  return endpoint;
	}
	
	public String setEndpointToSuffixes() {
		  String endpoint = "/suffixes";
		  return endpoint;
	}
	
	public String setEndpointToCountryDomainGDPR() {
		  String endpoint = "/country-domain-gdpr";
		  return endpoint;
	}
	
	public String setEndpointToTitles() {
		  String endpoint = "/titles";
		  return endpoint;
	}
	
	public String setEndpointToCatalyst_buyingInfluences() {
		  String endpoint = "/catalyst/buying-influences";
		  return endpoint;
	}
	
	public String setEndpointToCatalyst_healthSystemSizes() {
		  String endpoint = "/catalyst/health-system-sizes";
		  return endpoint;
	}
	
	public String setEndpointToCatalyst_hospitalSizes() {
		  String endpoint = "/catalyst/hospital-sizes";
		  return endpoint;
	}
	
	public String setEndpointToCatalyst_netPatientRevenues() {
		  String endpoint = "/catalyst/net-patient-revenues";
		  return endpoint;
	}
	
	public String setEndpointToCatalystPhysicianOrgSizes() {
		  String endpoint = "/catalyst/physician-org-sizes";
		  return endpoint;
	}
	
	public String setEndpointToCatalyst_profitStatuses() {
		  String endpoint = "/catalyst/profit-statuses";
		  return endpoint;
	}
	
	public String setEndpointToCatalystRoles() {
		  String endpoint = "/catalyst/roles";
		  return endpoint;
	}
	
	public String setEndpointToBrand() {
		  String endpoint = "/brand";
		  return endpoint;
	}
}
