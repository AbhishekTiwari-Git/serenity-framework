package ucc.utils;

import io.restassured.RestAssured;

public class RestUtil {

	
    public RestUtil(String uurl) {
    	RestAssured.baseURI = uurl;
    	// RestUtil.setBaseURI(uurl);
	}

    public RestUtil() {
  	
      	
  	}
	public static void setBaseURI (String baseURI){
        RestAssured.baseURI = baseURI;
        RestAssured.urlEncodingEnabled = false;
    }
 
    /*
    ***Sets base path***
    Before starting the test, we should set the RestAssured.basePath
    */
    public static void setBasePath(String basePathTerm){
        RestAssured.basePath = basePathTerm;
    }
 
    /*
    ***Reset Base URI (after test)***
    After the test, we should reset the RestAssured.baseURI
    */
    public static void resetBaseURI (){
        RestAssured.baseURI = null;
    }
 
    /*
    ***Reset base path (after test)***
    After the test, we should reset the RestAssured.basePath
    */
    public static void resetBasePath(){
        RestAssured.basePath = null;
    }
 
   
  
}
