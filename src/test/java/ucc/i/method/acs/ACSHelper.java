package ucc.i.method.acs;

import io.restassured.response.Response;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ucc.utils.TestUtils;

public class ACSHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ACSHelper.class);
	TestUtils tUtil = new TestUtils();
	String email = null;
	String firstName = null;
	String lastName = null;
	String postalCode = null;
	String countryCode = null;
	
	

	public boolean isUserHasUniqueLastName_andPostalCode(Response response){
        
    //lastName should't be null
    //lastName should be unique 
    //Email shouldn't be null
	//PostalCode should be unique

        List addresses = response.jsonPath().getList("addresses");
        Set <String> setLastNames = new HashSet <String> ();
        
        Set <String> setPostalCode = new HashSet <String> ();
        
        LOGGER.info("addresses.size() " + addresses.size() );

        if(addresses.size()>1){
        	
        	for (int i = 0; i < addresses.size(); i++) {
				
        		email = response.jsonPath().getString("addresses[" + i + "].email");
        		
				lastName = response.jsonPath().getString("addresses[" + i + "].name.lastName");
				setLastNames.add(response.jsonPath().getString("addresses[" + i + "].name.lastName"));
				
				postalCode = response.jsonPath().getString("addresses[" + i + "].postalAddress.postalCode");
				setPostalCode.add(response.jsonPath().getString("addresses[" + i + "].postalAddress.postalCode"));
				
				LOGGER.info("email --> " + email);
		        LOGGER.info("setLastNames.size() --> " + setLastNames.size() + " and lastNames in the set --> " + setLastNames.toString());
		        LOGGER.info("setPostalCode.size() --> " + setPostalCode.size() + " and postalCode in the set --> " + setPostalCode.toString());
			}
        	
        	return (email!=null && (setLastNames.size()==1 && setLastNames!=null && setPostalCode!=null && setPostalCode.size()==1) ? true : false);
        }
        
        else{
        	email = response.jsonPath().getString("addresses[0].email");
        	lastName = response.jsonPath().getString("addresses[0].name.lastName");
        	postalCode = response.jsonPath().getString("addresses[0].postalAddress.postalCode");
        	
        	LOGGER.info("there is only 1 email --> " + email);
        	LOGGER.info("lastName --> " + lastName);
        	LOGGER.info("postalCode --> " + postalCode);
        	
        	return ((email!=null && lastName!=null && postalCode!=null) ? true : false);
        }        
	}
	
	public void getTheDetails(Response response){
		
    	tUtil.putToSession("email", response.jsonPath().getString("addresses[0].email"));
    	tUtil.putToSession("firstName", response.jsonPath().getString("addresses[0].name.firstName"));
    	tUtil.putToSession("lastName", response.jsonPath().getString("addresses[0].name.lastName"));
    	tUtil.putToSession("postalCode", response.jsonPath().getString("addresses[0].postalAddress.postalCode"));
    	
    	if(response.jsonPath().getString("addresses[0].postalAddress.countryCode").equals("")){
    		tUtil.putToSession("countryCode", "US");
    	}
    	else{
    		tUtil.putToSession("countryCode", response.jsonPath().getString("addresses[0].postalAddress.countryCode"));
    	}
	}
}