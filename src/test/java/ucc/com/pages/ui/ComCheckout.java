package ucc.com.pages.ui;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.response.Response;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.com.steps.RegisteredOrderSteps;
import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;
import ucc.utils.YamlUtils;


public class ComCheckout extends PageObject {
        
	CommonFunc comFun = new CommonFunc();
	TestUtils tUtil = new TestUtils();	
	YamlUtils YamlUtil = new YamlUtils();	
	JsonUtils jsonUtils = new JsonUtils();
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();	
	
	static String pwd =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("password");
	
	static String Org_name =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("organization.name");	
	
	public static Map<String,String> Usermap = new HashMap<String,String>();
	private static final Logger LOGGER = LoggerFactory.getLogger(ComCheckout.class);	
	private static List<Object> TaxCountries = new ArrayList<Object>();
	static String BillCountry = null;
	static String BillState = null;
	static String BillingInfo = null;
	static DecimalFormat df = new DecimalFormat("#.00");
	String ExpectedTaxPricewithCurrency = "";
	
	
	@FindBy(xpath="//span[contains(text(),'Personal Information')]")
    WebElementFacade PersonalInfo;
	
	@FindBy(xpath="//legend[@class='legend']//span[contains(text(),'Billing Address')]")
    WebElementFacade BillingAddress;
	
    @FindBy(xpath="//div[@class='input-email-wrapper']//input[@id='customer-email']")
    WebElementFacade EmailAddress;
    
    @FindBy(id="pi_firstname")
    WebElementFacade Firstname;
    
    @FindBy(id="pi_lastname")
    WebElementFacade Lastname;
    
    @FindBy(id="pi_suffix")
    WebElementFacade Suffix;
    
    @FindBy(id="pi_professional_category")
    WebElementFacade prof_category;
    
    @FindBy(id="pi_student_type")
    WebElementFacade Student_Type;
    
    @FindBy(id="pi_primaryspecialty")
    WebElementFacade PrimarySpeciality;
    
    @FindBy(id="pi_role")
    WebElementFacade Role;
    
    @FindBy(id="pi_organization")
    WebElementFacade OrganizationName;    
    
    @FindBy(xpath="//div[@name='billingAddressshared.country_id']/div[1]/select[1]")
    WebElementFacade BillingCountry;
    
    @FindBy(xpath="//div[@name='billingAddressshared.street.0']/div[1]/input[1]") 
    WebElementFacade StreetAddress;
    
    
    @FindBy(xpath="//div[@name='billingAddressshared.city']/div[1]/input[1]") 
    WebElementFacade BillingCity;
    
   
    @FindBy(xpath="//div[@name='billingAddressshared.region_id']/div[1]/select[1]")    
    WebElementFacade StateProvinceSelect;
    
    @FindBy(xpath="//div[@name='billingAddressshared.region']//div//input[@name='region']")    
    WebElementFacade StateProvinceType;
    
    @FindBy(xpath="//input[@type='text'][@name='region']")    
    WebElementFacade StateProvinceInput;
    
    @FindBy(xpath="//div[@name='billingAddressshared.postcode']/div[1]/input[1]")   
    WebElementFacade ZipPostalcode;
    
    @FindBy(xpath="//div[@name='billingAddressshared.vat_id']/div[1]/input[1]")   
    WebElementFacade VATnumber;
    
    @FindBy(id="ba_password")
    WebElementFacade CreatePassword;
    
    @FindBy(id="#ba_password_confirm")
    WebElementFacade ConfirmPassword;
    
    @FindBy(xpath="//input[@id='md_firstdata_cc_number']")
    WebElementFacade CCnumber;
    
    @FindBy(xpath="//select[@name='payment[cc_exp_month]']")
    WebElementFacade CCexpirationmonth;
    
    @FindBy(xpath="//select[@name='payment[cc_exp_year]']")
    WebElementFacade CCexpirationyear;
    
    @FindBy(xpath="//input[@id='md_firstdata_cc_cid']")
    WebElementFacade CVV;
    
    @FindBy(xpath="//button[@class='action primary checkout btn-placeorder']//span[contains(text(),'Place Order')]")
    WebElementFacade PlaceorderButton;
    
    @FindBy(xpath="//div[@class='loading-mask']")
    WebElementFacade Loadingmask;
    
    @FindBy(xpath = "//span[text()='Credit Card with Automatic Renewal']")
    WebElementFacade CC_With_AutoRenewal;
    
    @FindBy(xpath = "//span[text()='Send Invoice']")
    WebElementFacade Send_Invoice;
    
    @FindBy(xpath = "//span[contains(text(),'I agree to the Automatic Renewal Convenience terms')]")
    WebElementFacade IagreeCheckbox;
    
    @FindBy(xpath = "//strong//span[@class='price']")
    WebElementFacade OrderTotal;
    
    @FindBy(xpath = "//div[contains(text(),'We recognize an account associated with this email')]")
    WebElementFacade existing_usr_msg;
    
    @FindBy(xpath = "//span[contains(text(),'SIGN IN')]")
    WebElementFacade signin_button;
    
    @FindBy(xpath = "//input[@id='capture_signIn_currentPassword']")
    WebElementFacade existing_password;   
    
    @FindBy(xpath = "//button[@id='ucc-sign-in-submit']")
    WebElementFacade signin_submit_button;
    
    @FindBy(className = "customername")
    WebElementFacade Customername;
    
	@FindBy(xpath = "//a[@class='logo']")
	WebElementFacade nejmCatalystLogo;
	
	@FindBy(xpath = "//figure[@class='copyright-logo']")
	WebElementFacade nejmGroupCopyrightLogo;
	
	@FindBy(xpath = "//span[@class='cr-row cr-1']")
	WebElementFacade nejmGroupProductText;
	
	@FindBy(xpath = "//span[@class='cr-row cr-2']")
	WebElementFacade nejmGroupCopyrightText;
	
	@FindBy(linkText = "Terms of Use")
	WebElementFacade termsOfUseLink;
	
	@FindBy(xpath = "//h1[@class='longWords']")
	WebElementFacade termsOfUsePageHeader;
	
	@FindBy(linkText = "Privacy Policy")
	WebElementFacade privacyPolicyLink;
	
	@FindBy(xpath = "//h1[@class='longWords']")
	WebElementFacade privacyPolicyPageHeader;
	
	@FindBy(xpath = "//div[@class='email_me_section']/label[1]")
	WebElementFacade emailMeCheckbox;

	@FindBy(xpath = "//div[@class='email_me_section']/label[2]")
	WebElementFacade stayInformedCheckbox;

	@FindBy(xpath = "//div[@class='email_me_section']/label/span/img")
	List<WebElementFacade> checkboxImages;
	
	@FindBy(xpath = "//tr[@class='totals-tax']//span[@class='price']")
    WebElementFacade tax;
	
	@FindBy(xpath = "//td[@class='amount']/span[@class='price']")
    WebElementFacade cartSubtotal;

    @FindBy(xpath = "//tr[@class='grand totals']//span[@class='price']")
    WebElementFacade Ordertotal1;
    
    
	@Step("Enter Personal Information")
    public void Enter_Personal_Information(String Testemail,String fname, String lname, String suffix, String Primaryspecial, String role, 
    		String organization, String Billcountry, String Address, 
    		String Billcity, String state, String zip
    		 ) throws InterruptedException {
                  
         Loadingmask.waitUntilNotVisible();
        
         EmailAddress.waitUntilVisible().type(Testemail);
        
         Firstname.waitUntilVisible().type(fname);        
        
         Lastname.waitUntilVisible().type(lname);      
             
        Suffix.waitUntilVisible().selectByValue(suffix);
              
        Loadingmask.waitUntilNotVisible(); 
        
        selectPrimarySpeciality(suffix,Primaryspecial);
               
        Role.waitUntilVisible().selectByVisibleText(role);
                
        OrganizationName.waitUntilVisible().typeAndTab(organization);        
     
        Loadingmask.waitUntilNotVisible(); 	
        comFun.scrollAndClickElement(BillingCountry);
        
        BillingCountry.waitUntilVisible().selectByVisibleText(Billcountry);
        
        StreetAddress.waitUntilVisible().type(Address);
               
        BillingCity.waitUntilVisible().type(Billcity); 
        
        if (StateProvinceSelect.isCurrentlyVisible())
        {
        	StateProvinceSelect.waitUntilVisible().selectByVisibleText(state);        	
        } else
        	
        {
        	StateProvinceType.waitUntilVisible().type(state);
        }
                
        ZipPostalcode.waitUntilVisible().type(zip);
      
        CreatePassword.waitUntilVisible().type(pwd);        
        
        ConfirmPassword.waitUntilVisible().type(pwd);
        
        Loadingmask.waitUntilNotVisible();
    
            
    }
	public void selectPrimarySpeciality(String Suffix, String primaryspecial) throws InterruptedException {	   
		if(Suffix.equals("MD") || Suffix.equals("DO") || Suffix.equals("MBBS")) {    	
    	  
			PrimarySpeciality.waitUntilVisible().selectByVisibleText(primaryspecial);
    	}
    }
    @Step("Enter Personal Information")
    public void Enter_Personal_Information_Refactored(Map<String, String> kmap, Map<String, String> row) throws InterruptedException {
                  
         Loadingmask.waitUntilNotVisible();
        
         EmailAddress.waitUntilVisible().type(kmap.get("email"));
        
         Firstname.waitUntilVisible().type(kmap.get("firstname"));        
        
         Lastname.waitUntilVisible().type(kmap.get("lastname"));      
             
        Suffix.waitUntilVisible().selectByValue(row.get("suffix"));
              
        Loadingmask.waitUntilNotVisible();
        
        PrimarySpeciality.waitUntilVisible().selectByVisibleText(row.get("Primary Specialty"));     
                
        Role.waitUntilVisible().selectByVisibleText(row.get("Role"));
                
        OrganizationName.waitUntilVisible().typeAndTab(row.get("Name of organization"));        
     
        Loadingmask.waitUntilNotVisible();	
        comFun.scrollAndClickElement(BillingCountry);
        
        BillingCountry.waitUntilVisible().selectByVisibleText(row.get("country"));
        
        StreetAddress.waitUntilVisible().type(row.get("street address"));
               
        BillingCity.waitUntilVisible().type(row.get("city")); 
        
        if (StateProvinceSelect.isCurrentlyVisible())
        {
        	StateProvinceSelect.waitUntilVisible().selectByVisibleText(row.get("state province"));        	
        } else
        	
        {
        	StateProvinceType.waitUntilVisible().type(row.get("state province"));
        }
                
        ZipPostalcode.waitUntilVisible().type(row.get("Zip Postal code"));
      
        CreatePassword.waitUntilVisible().type(pwd);        
        
        ConfirmPassword.waitUntilVisible().type(pwd);
        
        Loadingmask.waitUntilNotVisible();
    
            
    }
    
    @Step("Enter existing email")
    public void Enter_existing_email(String Testemail) throws InterruptedException {
    		                    
         Loadingmask.waitUntilNotVisible();
        
         EmailAddress.waitUntilVisible().type(Testemail);
       
    }
    
       
   @Step("Credit Card with AutoRenewal Option")
    
    public void Select_CCwithAutoRenewal_Option () throws InterruptedException
    
    {
         Loadingmask.waitUntilNotVisible();
         CC_With_AutoRenewal.waitUntilVisible().click();
                
    }
   
   @Step("Send Invoice Option")
   
   public void Select_Send_Invoice_Option() throws InterruptedException
   
   {
        Loadingmask.waitUntilNotVisible();
        Send_Invoice.waitUntilVisible().click();
       
       
   }
    
    @Step("Enter Credit Card Information")
    
    public void Enter_CC_Details (String BillState,String CCnum, String CCexpmonth, String CCexpyear, String CCcvv) throws InterruptedException
    
    { 
         Loadingmask.waitUntilNotVisible();
         
        CCnumber.waitUntilVisible().type(CCnum);
        
       
        comFun.scrollAndClickElement(CVV);        
        
         
        CCexpirationmonth.waitUntilClickable().click();       
        CCexpirationmonth.selectByValue(CCexpmonth);
                  
     
        CCexpirationyear.waitUntilClickable().click();        
        CCexpirationyear.selectByValue(CCexpyear);
                 
        CVV.waitUntilVisible().type(CCcvv);
        
        if (BillState.equalsIgnoreCase("Vermont"))
		 {  
        	Loadingmask.waitUntilNotVisible();
        	IagreeCheckbox.waitUntilVisible();   
        	IagreeCheckbox.click();
		 } 
    }
    
    public void Click_PlaceOrder_button() throws InterruptedException {
    	
    	Loadingmask.waitUntilNotVisible();
        comFun.scrollAndClickElement(PlaceorderButton);              
        
    }
    
  @Step("Enter VAT number")    
    public void Enter_VAT_number (String VAT_num) throws InterruptedException {    
    
         Loadingmask.waitUntilNotVisible();
         VATnumber.waitUntilVisible().type(VAT_num);
         Loadingmask.waitUntilVisible();
         
    }
    
    @Step("Verify display of checkout page")
    public boolean Verify_Checkout_Page()  {
   
    	  	boolean CheckoutDisplay = PersonalInfo.waitUntilVisible().isDisplayed();
    	  	return CheckoutDisplay;
    	  	
    }
    
    @Step("Verify display of checkout page for Billing address")
    public boolean Verify_Checkout_Page_Billing_address() {
          
    	   boolean CheckoutBillingDisplay = BillingAddress.waitUntilVisible().isDisplayed();
  	        return CheckoutBillingDisplay;
    	 
    }
    
    @Step("Verify Order Total on checkout page")
    public String Verify_Order_Total() {
       
    	Loadingmask.waitUntilNotVisible();
    	String ordertotalamount = OrderTotal.waitUntilVisible().getText();
	  	return ordertotalamount;
    	  	
    }
    
    @Step("Checkout page assertion")
    public void checkout_page_assertions(final Map<String, String> row) {
       
    	Loadingmask.waitUntilNotVisible();    	
    	Assert.assertEquals(row.get("Expected Order Total"), OrderTotal.waitUntilVisible().getText());	  	
    	  	
    }
    
    @Step("Enter Billing Address")
    public void Enter_Billing_Address(String Billcountry, String Address, 
    		String Billcity, String state, String zip) throws InterruptedException {
                  
         Loadingmask.waitUntilNotVisible();
        
        comFun.scrollAndClickElement(BillingCountry);       
       
        BillingCountry.waitUntilVisible().selectByValue(Billcountry);
               
        StreetAddress.waitUntilVisible().type(Address);
        
                     
        BillingCity.waitUntilVisible().type(Billcity);
        
        ZipPostalcode.waitUntilVisible().type(zip);
        
        Loadingmask.waitUntilNotVisible();  
        
        if (StateProvinceSelect.isCurrentlyVisible())
        {
        	StateProvinceSelect.waitUntilVisible().selectByVisibleText(state);
        	
        } else
        {
        	StateProvinceType.waitUntilVisible().type(state);
        }
        
        Loadingmask.waitUntilVisible(); 
      
    }
    
    @Step("Verify existing user message")    
    public String verify_existing_user_message() throws InterruptedException {    
    
         Loadingmask.waitUntilNotVisible();
          return existing_usr_msg.waitUntilVisible().getText();
         
    }
    
    @Step("clicking signin button")    
    public void click_signin_button() throws InterruptedException {    
    
          Loadingmask.waitUntilNotVisible();
          signin_button.waitUntilVisible().click();
         
    }
    
    @Step("Enter existing password")
    public void Enter_existing_password(String pwd) throws InterruptedException {
    		                    
         Loadingmask.waitUntilNotVisible();
        
         existing_password.waitUntilVisible().type(pwd);
       
    }
    
    
    
    @Step("click signin button on signin modal")
    public void click_signin_button_signin_modal() throws InterruptedException {
    		                    
         Loadingmask.waitUntilNotVisible();
        
         signin_submit_button.waitUntilVisible().click();
       
    }
    
    @Step("get signedin customer name")
    public String Get_signedin_customer_name() throws InterruptedException {
    		                    
         Loadingmask.waitUntilNotVisible();
        
         return Customername.waitUntilVisible().getText();
       
    }
    
	@Step("isThere_nejmCatalystLogo")
	public boolean isThere_nejmCatalystLogo() {
		return nejmCatalystLogo.waitUntilVisible().isDisplayed();
	}
	
	@Step("isThere_nejmGroupCopyrightLogo")
	public boolean isThere_nejmGroupCopyrightLogo() {
		return nejmGroupCopyrightLogo.waitUntilVisible().isDisplayed();
	}
	
	@Step("get_nejmGroupProductText")
	public String get_nejmGroupProductText() {
		return nejmGroupProductText.waitUntilVisible().getText();
	}
	
	@Step("get_nejmGroupCopyrightText")
	public String get_nejmGroupCopyrightText() {
		return nejmGroupCopyrightText.waitUntilVisible().getText();
	}
	
	@Step("click_termsOfUseLink")
	public void click_termsOfUseLink() {
		termsOfUseLink.waitUntilClickable().click();
	}
	
	@Step("get_termsOfUsePageHeaderText")
	public String get_termsOfUsePageHeaderText() {
		return termsOfUsePageHeader.waitUntilVisible().getText();
	}
	
	@Step("click_privacyPolicyLink")
	public void click_privacyPolicyLink() {
		privacyPolicyLink.waitUntilClickable().click();
	}
	
	@Step("get_privacyPolicyPageHeaderText")
	public String get_privacyPolicyPageHeaderText() {
		return privacyPolicyPageHeader.waitUntilVisible().getText();
	}
	
	@Step("are_EmailInfoCheckboxesChecked")
	public boolean are_EmailInfoCheckboxesChecked() {
		boolean emailMe;
		boolean stayInf;
		comFun.scroll_Mousehover(stayInformedCheckbox);

		emailMeCheckbox.waitUntilClickable();
		LOGGER.info("Email me checkbox is clickable.");
		try {
			if (checkboxImages.get(0).isDisplayed()) {
				LOGGER.info("Email me checkbox is selected.");
				emailMe = true;
			} else {
				LOGGER.info("Email me checkbox is not selected.");
				emailMe = false;
			}
		}
		catch (Exception e) {
			LOGGER.info("isDisplayed threw error: " + e.getMessage().replaceAll("/n", " "));
			LOGGER.info("Email me checkbox is not selected.");
			emailMe = false;
		}

		stayInformedCheckbox.waitUntilClickable();
		LOGGER.info("Stay informed checkbox is clickable.");
		try {
			if (checkboxImages.get(1).isDisplayed()) {
				LOGGER.info("Stay informed checkbox is selected.");
				stayInf = true;
			} else {
				LOGGER.info("Stay informed checkbox is not selected.");
				stayInf = false;
			}
		}
		catch (Exception e) {
			LOGGER.info("isDisplayed threw error: " + e.getMessage());
			LOGGER.info("Stay informed checkbox is not selected.");
			stayInf =  false;
		}

		if (emailMe && stayInf) {
			return true;
		} else {
			return false;
		}
	}
	
	@Step("Enter Personal info, select required billing method and enter billing details")
	public void Enter_checkout_details(Map<String, String> kmap, final Map<String, String> row) throws InterruptedException, IOException {
	Enter_Personal_Information_Refactored(kmap,row);
	String PaymentType = row.get("payment method").trim();
	switch (PaymentType) {

	case "CC":
		JSONObject CardInfo = new JSONObject(YamlUtil.getValueFromYml("card.yml", "/" + row.get("CreditCardType")));		
		String ExpiryDate[] = CardInfo.getString("expiry").split("/");
		Select_CCwithAutoRenewal_Option();
		Enter_CC_Details(row.get("state province"), CardInfo.getNumber("number").toString(), ExpiryDate[0],ExpiryDate[1], CardInfo.getNumber("cvv").toString());		
		break;

	case "Send Invoice":
		Loadingmask.waitUntilNotVisible();
		Select_Send_Invoice_Option();
		break;

	default:
		LOGGER.info("Invalid payment type" + PaymentType);
		break;
	}	
	}
	
	/**
	 * This Step is for selecting payment method and fill required checkout details
	 * @param Maps
	 * @throws InterruptedException, IOException
	 * @author mshinde
	 * @date 24-Mar-2021
	 */
	
	@Step("Enter Personal info, select required billing method and enter billing details")
	public void Enter_billing_details_refactored(Map<String, String> kmap, final Map<String, String> row) throws InterruptedException, IOException {			
	String [] PaymentType = row.get("payment method").trim().split("-");
	switch (PaymentType[0]) {

	case "CC":
		JSONObject CardInfo = new JSONObject(YamlUtil.getValueFromYml("card.yml", "/" + PaymentType[1]));		
		String ExpiryDate[] = CardInfo.getString("expiry").split("/");
		Select_CCwithAutoRenewal_Option();
		Enter_CC_Details(row.get("state"), CardInfo.getNumber("number").toString(), ExpiryDate[0],ExpiryDate[1], CardInfo.getNumber("cvv").toString());	
		Click_PlaceOrder_button();	
		break;

	case "Send Invoice":
		Loadingmask.waitUntilNotVisible();
		Select_Send_Invoice_Option();
		Click_PlaceOrder_button();		
		break;
	
	case "PayPal":
		Loadingmask.waitUntilNotVisible();		
		LOGGER.info("Entered paypal email id and password");
		break;

	default:
		LOGGER.info("Invalid payment type" + PaymentType);
		break;
	}	
	
	
	}
	
	/**
	 * This Step is for filling personal info and billing address details
	 * @param Maps
	 * @throws InterruptedException, IOException
	 * @author mshinde
	 * @date 24-Mar-2021
	 */
	
	@Step("Enter Personal Information refactored")
    public void Enter_Personal_Info_Refactored(Map<String, String> kmap, Map<String, String> row) throws InterruptedException, JSONException, IOException {
                  
         Loadingmask.waitUntilNotVisible();         
          
         EmailAddress.waitUntilVisible().type((String)tUtil.getFromSession("email"));
        
         Firstname.waitUntilVisible().type((String)tUtil.getFromSession("firstName"));        
        
         Lastname.waitUntilVisible().type( (String)tUtil.getFromSession("lastName"));      
             
        Suffix.waitUntilVisible().selectByValue(row.get("suffix"));
        
        Loadingmask.waitUntilNotVisible();   
        
        comFun.scrollAndClickElement(prof_category);
        String Profes_Catg = row.get("Prof_cat");
        
        prof_category.waitUntilVisible().selectByVisibleText(row.get("Prof_cat"));
        
        if(Profes_Catg.equals("Physician")|| Profes_Catg.equals("Resident")) {
         
        	 Loadingmask.waitUntilNotVisible();
             
             PrimarySpeciality.waitUntilVisible().selectByVisibleText(row.get("Primary Specialty"));     
        } else if (Profes_Catg.equals("Student")){
            
       	    Loadingmask.waitUntilNotVisible();            
       	    Student_Type.waitUntilVisible().selectByVisibleText(row.get("Student_Type"));     
       }       
       
        if ((Profes_Catg.equals("PA"))||(Profes_Catg.equals("Nurse"))||(Profes_Catg.equals("Nurse Practitioner"))||(Profes_Catg.equals("Physician"))) {   
        	
           Role.waitUntilVisible().selectByVisibleText(row.get("Role"));
        }
        Loadingmask.waitUntilNotVisible();
                
        OrganizationName.waitUntilVisible().typeAndTab(Org_name);        
     
        Loadingmask.waitUntilNotVisible();	
        comFun.scrollAndClickElement(BillingCountry);
        
        BillingCountry.waitUntilVisible().selectByVisibleText(row.get("country"));
        BillCountry = row.get("country");
        BillState = row.get("state");
        BillingInfo = "BILLING INFORMATION" + "\n" + (String)tUtil.getFromSession("firstname") + " " + (String)tUtil.getFromSession("lastname") + "\n" + row.get("street address") + "\n"+ row.get("city") + ", "	+ row.get("state") + ", " + row.get("Zip"); 
        Loadingmask.waitUntilNotVisible();
        
        StreetAddress.waitUntilVisible().type(row.get("street address"));
                       
        BillingCity.waitUntilVisible().type(row.get("city")); 
        
        if (StateProvinceSelect.isCurrentlyVisible())
        {
        	StateProvinceSelect.waitUntilVisible().selectByVisibleText(row.get("state"));        	
        } else
        	
        {
        	StateProvinceType.waitUntilVisible().type(row.get("state"));
        }
        
       
        ZipPostalcode.waitUntilVisible().type(row.get("Zip"));        
      
        CreatePassword.waitUntilVisible().type(pwd);        
        
        ConfirmPassword.waitUntilVisible().type(pwd);
        
        Loadingmask.waitUntilNotVisible();       
        
            
    }
	
	/**
	 * Below 3 steps are for verifying amount on checkout page
	 * @param none
	 * @throws JSONException, IOException
	 * @author mshinde
	 * @date 24-Mar-2021
	 */
    
	@Step("Checkout page cart amount assertion")
	public boolean checkout_page_cartprice_assert(String ExpectedCartPrice) throws JSONException, IOException {		
		tUtil.putToSession("ExpectedCartPricewithCurrency",ExpectedCartPrice);
		return ExpectedCartPrice.equals(jsonUtils.convertToUTF8(cartSubtotal.waitUntilVisible().getText()));
		
	
    }
	
	@Step("Checkout page tax amount assertion")
	public boolean checkout_page_tax_assert(JSONObject Country) throws JSONException, IOException {
		boolean taxmatching = true;
		String CurrencySymbol = jsonUtils.getFromJSONInUTF8("Currency/Currency_Code.json",
				"$['" + ComStore.Country_Code + "'].symbol");	

		String jsonCountries = jsonUtils.getFromJSON("Tax_Countries_List.json", "countries");
		jsonCountries = jsonCountries.replace("[", "");
		jsonCountries = jsonCountries.replace("]", "");
		String[] arrayCountries = jsonCountries.split(",");
		TaxCountries = Arrays.asList(arrayCountries);
		if ((TaxCountries.contains(BillCountry) || (TaxCountries.contains(BillState)))) {
			tUtil.putToSession("TaxApplicable", "yes");
			String ActualTaxPriceWithCurrency = tax.waitUntilVisible().getText();
			String ExpectedTaxPrice = "";
			if (((String)tUtil.getFromSession("Product_Type")).equalsIgnoreCase("product-monthly"))
			{  
			  ExpectedTaxPrice = jsonUtils.convertToUTF8(String.valueOf(df.format(Country.getDouble("taxmonthly"))));
			 
			} else
			{
				 ExpectedTaxPrice = jsonUtils.convertToUTF8(String.valueOf(df.format(Country.getDouble("taxyearly"))));
			}
	 	           ExpectedTaxPricewithCurrency = CurrencySymbol + ExpectedTaxPrice;			
			taxmatching = ExpectedTaxPricewithCurrency.equalsIgnoreCase(jsonUtils.convertToUTF8(ActualTaxPriceWithCurrency));
			
		}  else { 
			      tUtil.putToSession("TaxApplicable", "no");
			      ExpectedTaxPricewithCurrency = CurrencySymbol + "0.00";
		}
		
		tUtil.putToSession("ExpectedTaxPricewithCurrency",ExpectedTaxPricewithCurrency);	               
		
		return taxmatching;							
		
    }
	
	@Step("Checkout page total amount assertion")
	public boolean checkout_page_total_assert(String CurrencySymbol,JSONObject Country) throws JSONException, IOException {
		String ExpectedOrderTotalPrice;
		if (((String)tUtil.getFromSession("TaxApplicable")).equalsIgnoreCase("yes")) {
			if (((String)tUtil.getFromSession("Product_Type")).equalsIgnoreCase("product-monthly")) { 	
			     ExpectedOrderTotalPrice = jsonUtils.convertToUTF8(String.valueOf(df.format(Country.getDouble("totalmonthly"))));
			} else {
				 ExpectedOrderTotalPrice = jsonUtils.convertToUTF8(String.valueOf(df.format(Country.getDouble("totalyearly"))));
			}
		} else 
		{
			if (((String)tUtil.getFromSession("Product_Type")).equalsIgnoreCase("product-monthly")) { 	
			     ExpectedOrderTotalPrice = jsonUtils.convertToUTF8(String.valueOf(df.format(Country.getDouble("monthly"))));
			} else {
				 ExpectedOrderTotalPrice = jsonUtils.convertToUTF8(String.valueOf(df.format(Country.getDouble("yearly"))));
			}
		}
		String ExpectedOrderTotalPriceWithCurrency = CurrencySymbol + ExpectedOrderTotalPrice;
		String ActualOrderTotalPriceWithCurrency = Ordertotal1.waitUntilVisible().getText();
		tUtil.putToSession("ExpectedOrderTotalPriceWithCurrency",ExpectedOrderTotalPriceWithCurrency);		
		return  ExpectedOrderTotalPriceWithCurrency.equals(jsonUtils.convertToUTF8(ActualOrderTotalPriceWithCurrency));							
		
    }
	
	
	
	
	
}