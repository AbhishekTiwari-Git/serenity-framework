package ucc.cr.pages.catalyst.ui;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ucc.pages.ui.CommonFunc;
import ucc.utils.TestUtils;

public class CreateAccount extends PageObject {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateAccount.class);

    TestUtils tUtil=new TestUtils();
    CommonFunc comFun = new CommonFunc();
    CRConnectPage connect= new CRConnectPage();
    static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
    static Map<String, String> kMap = new HashMap<String, String>();
    static Map<String, String> optIn = new HashMap<String, String>();
    static String pwd =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("password");

    

    @FindBy(xpath="//span[contains(text(),'Create Account')]")
    WebElementFacade CreateAccountLink;
    
    @FindBy(xpath="//div[@class=' ucc-form-container']")
    WebElementFacade LegacyCreateAccount;
    
    @FindBy(xpath="//div[@class=' ucc-form-container ucc-widget-accordion-form']")
    WebElementFacade NewCreateAccount;
    
    
    @FindBy(name="uccEmail")
    WebElementFacade emailId;

    @FindBy(name="uccPwd")
    WebElementFacade Password;

    @FindBy(xpath="//button[contains(@class, 'ucc-btn ucc-btn-primary')]")
    WebElementFacade Button;      
    
    @FindBy(xpath="//*[starts-with(@class,' ucc-form-container ucc-widget-accordion-form')]/div/div[2]//button[contains(text(),'CONTINUE')]")
    WebElementFacade ContinueButton1; 
    
    @FindBy(xpath="//*[starts-with(@class,' ucc-form-container ucc-widget-accordion-form')]/div/div[3]//button[contains(text(),'CONTINUE')]")
    WebElementFacade ContinueButton2;    
    
    @FindBy(xpath="//button[contains(text(),'CREATE ACCOUNT')]")
    WebElementFacade CreateAccountFormButton;   
   
    @FindBy(name="uccFirstName")
    WebElementFacade firstName;
    
    @FindBy(name="uccLastName")
    WebElementFacade lastName;

    @FindBy(name="uccSuffix")
    WebElementFacade suffixDropDown;

    @FindBy(name="uccRole")
    WebElementFacade roleDropDown;
    
    @FindBy(name="uccSpecialty")
	WebElementFacade PrimaryspecialityDropDown;

    @FindBy(name="uccPlaceOfWork")	
    WebElementFacade placeOfWorkDropDown;

    @FindBy(name="uccNameOfOrg")
    WebElementFacade nameOfOrg;

    @FindBy(name="uccCountry")
    WebElementFacade countryDropDown;

    @FindBy(xpath="//p[contains(text(),'Your account was successfully created.')]")
    WebElementFacade SuccessText;

    @FindBy(xpath="//*[starts-with(@id,'uccOrganization')]")
    WebElementFacade typeOrg;

    @FindBy(xpath="//*[starts-with(@id,'uccHealthAffiliation')]")
    WebElementFacade isHealth;

    @FindBy(xpath="//*[starts-with(@id,'uccNetpatientRevenue')]")
    WebElementFacade netPatientRevenue;

    @FindBy(xpath="//span[contains(text(),'None of the above')]")
    WebElementFacade Vendors;

    @FindBy(xpath="//*[starts-with(@id,'uccestimatedDollor')]")
    WebElementFacade estimatedDollor;

    @FindBy(xpath="//*[starts-with(@id,'uccYearofbirth')]")
    WebElementFacade yearBirth;

    @FindBy(xpath="//*[starts-with(@id,'uccState')]")
    WebElementFacade State;

    @FindBy(xpath="//h1[contains(text(),'Thank You')]")
    WebElementFacade ThankYouText;
    
    @FindBy(xpath="//button[@class='ucc-modal-close']")
	WebElementFacade CloseInsightCouncil;
    
    @FindBy(xpath="//a[starts-with(@class,'litSsoCreate')]/span")
	WebElementFacade CreateAccountHeader;
    
    @FindBy(xpath="//p[contains(text(),'Please review our')]")
	WebElementFacade pleaseReview;
    
    @FindBy(xpath="//p[contains(text(),'Please review our')]/a[contains(text(),'Privacy Policy')]")
  	WebElementFacade privacyPolicyLink;
    
	@FindBy(xpath = "//h1[contains(text(),'Privacy Policy')]")
	WebElementFacade gdprPrivacyNejmPage;
	
	@FindBy(css = "input[name=uccSoi]:checked[type=checkbox]")
	WebElementFacade regSoi;

	@FindBy(css = "input[name=uccCatalystConnect]:checked[type=checkbox]")
	WebElementFacade regConnect;

	@FindBy(css = "input[name=uccRememberMe]:checked[type=checkbox]")
	WebElementFacade regRemember;

	@FindBy(xpath = "//button[@class='ucc-modal-close']")
	WebElementFacade ICThankyouclosebutton;
	
	@FindBy(xpath = "//div[contains(@class, 'ucc-widget-accordion')]")
	WebElementFacade accFormDisplay;
	
	@FindBy(xpath = "//div[contains(text(),'Personal')]/ancestor::div[contains(@class, 'header')]/following-sibling::div//button")
	public WebElementFacade continueButton1;

	@FindBy(xpath = "//div[contains(text(),'Professional')]/ancestor::div[contains(@class, 'header')]/following-sibling::div//button")
	public WebElementFacade continueButton2;

	@FindBy(xpath = "//*[starts-with(@id, 'uccEmail')]")
	public WebElementFacade enterEmail;

	@FindBy(xpath = "//*[starts-with(@id, 'uccPwd')]")
	public WebElementFacade enterPass;

	@FindBy(xpath = "//button[contains(text(), 'CREATE ACCOUNT')]")
	public WebElementFacade createAccountBtn;

	@FindBy(xpath = "//*[starts-with(@id, 'uccFirstName')]")
	public WebElementFacade enterFirstName;

	@FindBy(xpath = "//*[starts-with(@id, 'uccLastName')]")
	public WebElementFacade enterLastName;

	@FindBy(xpath = "//*[starts-with(@id, 'uccCountry')]")
	public WebElementFacade selectCountry;

	@FindBy(xpath = "//*[starts-with(@id, 'uccSuffix')]")
	public WebElementFacade selectSuffix;

	@FindBy(xpath = "//*[starts-with(@id, 'uccPlaceOfWork')]")
	public WebElementFacade selectPlace;

	@FindBy(xpath = "//*[starts-with(@id, 'uccRole')]")
	public WebElementFacade selectRole;

	@FindBy(xpath = "//*[starts-with(@id, 'uccNameOfOrg')]")
	public WebElementFacade enterOrg;

	@FindBy(xpath = "//*[starts-with(@id, 'uccSpecialty')]")
	public WebElementFacade enterSpecialty;



    public void click_create_account() {
    	comFun.clickElement(CreateAccountHeader);
    }
    
    @Step("Verify Create Account page")
    public String Verify_Create_Account_Page() throws InterruptedException {
    	
        String CreateAccHeader = CreateAccountHeader.waitUntilVisible().getText();
        return CreateAccHeader;
    	
        
    }
    public void create_account(
            String email, String password, String suffix, String role, String place, String orgName, String country
    ) {
        String fName = tUtil.AppendTimestamp("FN");
        String lName = tUtil.AppendTimestamp("LN");

        fillAccountData(email, password, fName, lName, suffix, role, place, orgName, country);
    }

    private void fillAccountData(
            String email, String password, String fName, String lName,
            String suffix, String role, String place, String orgName, String country
    ) {
 
        emailId.waitUntilClickable();
        emailId.type(email);

        Password.waitUntilClickable();
        Password.type(password);
        
        firstName.waitUntilClickable();
        firstName.type(fName);
        
        lastName.waitUntilClickable();
        lastName.type(lName);


        waitFor(ExpectedConditions.elementToBeClickable(suffixDropDown));
        suffixDropDown.selectByValue(suffix);

        waitFor(ExpectedConditions.elementToBeClickable(roleDropDown));
        roleDropDown.selectByValue(role);

        waitFor(ExpectedConditions.elementToBeClickable(placeOfWorkDropDown));
        placeOfWorkDropDown.selectByVisibleText(place);

        waitFor(ExpectedConditions.elementToBeClickable(nameOfOrg));
        nameOfOrg.clear();
        nameOfOrg.sendKeys(orgName);

        waitFor(ExpectedConditions.elementToBeClickable(countryDropDown));
        countryDropDown.selectByValue(country);

        Button.waitUntilClickable().click();
    }
    
    @Step("Enter all user details for create account form")
    public void Enter_User_all_Details(String Testemail,String fname, String lname, String suffix, String Primaryspecial, 
    		 String role, String placeofwork, String organization, String country) throws InterruptedException {
    	 	 
      	
    	if (accFormDisplay.isCurrentlyVisible())
    	       {    		
   		         Enter_All_User_Details_Alternate_CA_Form(Testemail,fname,lname,suffix,Primaryspecial, role,placeofwork,organization,country);
    	       }
    	
    	else   {
    		      LOGGER.info("Legacy Flow");
    		      Enter_All_User_Details_legacy(Testemail,fname,lname,suffix,Primaryspecial, role,placeofwork,organization,country);
    	       }
    }
    
    @Step("Enter all user details for create account Legacy form")
    public void Enter_All_User_Details_legacy(String testemail,String Fname, String Lname, String Suffix, String primaryspecial, 
    		 String Role, String Placeofwork, String Organization, String Country) throws InterruptedException {
    	    	
    	emailId.waitUntilVisible().type(testemail);
    	Password.waitUntilVisible().type(pwd);
    	firstName.waitUntilVisible().type(Fname);
    	lastName.waitUntilVisible().type(Lname);
    	suffixDropDown.waitUntilVisible().selectByVisibleText(Suffix);
    	selectPrimarySpeciality(Suffix, primaryspecial);
    	selectRole(Role);		
    	placeOfWorkDropDown.waitUntilVisible().selectByVisibleText(Placeofwork);		
    	nameOfOrg.waitUntilVisible().type(Organization);
    	countryDropDown.waitUntilVisible().selectByVisibleText(Country);
    	validateGDPR_Accordian_StepTwo();
    	setSOIConnect_RememberMe(Country);
    	comFun.clickElement(Button);
    }
    
    @Step("Enter user details for create account alternate form")
    public void Enter_All_User_Details_Alternate_CA_Form(String testemail,String Fname, String Lname, String Suffix, String primaryspecial, 
   		 String Role, String Placeofwork, String Organization, String Country) throws InterruptedException {
    	    enterFirstName.waitUntilVisible().type(Fname);
    	    enterLastName.waitUntilVisible().type(Lname);
        	selectCountry.waitUntilVisible().selectByVisibleText(Country);
        	validateGDPR_Accordian_StepOne();
        	comFun.clickElement(continueButton1);
        	selectSuffix.waitUntilVisible().selectByVisibleText(Suffix);
        	selectPrimarySpeciality(Suffix, primaryspecial);
        	selectRole(Role);
        	selectPlace.waitUntilVisible().selectByVisibleText(Placeofwork);
        	enterOrg.waitUntilVisible().type(Organization);
        	comFun.clickElement(continueButton2);
        	enterEmail.waitUntilVisible().type(testemail);
        	enterPass.waitUntilVisible().type(pwd);    	    
    	    validateGDPR_Accordian_StepTwo();
    	    setSOIConnect_RememberMe(Country);
    	    comFun.clickElement(createAccountBtn);
    }
    
    @Step("User clicks register button")
    public void Click_Register_Button() throws InterruptedException {
        
    }
    
    public void selectRole(String Role)
    {
    	if(Role.equals("Business Executive")){
    	roleDropDown.waitUntilVisible().selectByValue("BEO");	
    	}
    	else {
    	roleDropDown.waitUntilVisible().selectByVisibleText(Role);
    	}
    }

    public String getSuccessText() {
    	
        return SuccessText.waitUntilVisible().getText();
    }

    public String getThanYouText() {

        return ThankYouText.waitUntilVisible().getText();
    }

    @Step("Get accoutn email")
    public String get_account_email() {

        return emailId.waitUntilClickable().getAttribute("value");
    }
    
    @Step("Close Insight Council Page")
    public  void Close_Insight_Council_form() throws InterruptedException {
		
		CloseInsightCouncil.waitUntilVisible().click();
        
    }

    public void fillDataToJoin(
            String typeOfOrg, String health, String netPatient, String amount, String yearOfBith, String state
    ) {
    	
        typeOrg.waitUntilVisible().selectByVisibleText(typeOfOrg);
       
        isHealth.waitUntilVisible().selectByVisibleText(health);
        
        netPatientRevenue.waitUntilVisible().selectByVisibleText(netPatient);

        Vendors.waitUntilClickable().click();
        
        estimatedDollor.waitUntilVisible().selectByVisibleText(amount);
       
        yearBirth.waitUntilVisible().selectByVisibleText(yearOfBith);
        selectState(state);
        
    }

    public void clickBtn() {
    	
    	Button.waitUntilClickable().click();
    }
    
    public void ICThankyouMsgClose() {
    	
    	ICThankyouclosebutton.waitUntilClickable().click();
    }

    public void create_lead_account(String password, String suffix, String role, String place) {
        fillLeadAccountData(password, suffix, role, place);
    }

    private void fillLeadAccountData(
            String password, String suffix, String role, String place
    ) {
        waitFor(ExpectedConditions.elementToBeClickable(Password));
        Password.clear();
        Password.sendKeys(password);

        waitFor(ExpectedConditions.elementToBeClickable(suffixDropDown));
        suffixDropDown.selectByValue(suffix);

        waitFor(ExpectedConditions.elementToBeClickable(roleDropDown));
        roleDropDown.selectByValue(role);

        waitFor(ExpectedConditions.elementToBeClickable(placeOfWorkDropDown));
        placeOfWorkDropDown.selectByVisibleText(place);

        waitFor(ExpectedConditions.elementToBeClickable(Button));
        Button.click();
    }
    
    public void selectState(String state){
    	if (!(state.equals("null"))) {
    		State.waitUntilClickable().selectByVisibleText(state);
    	}    		
    } 
    
    public void selectPrimarySpeciality(String Suffix, String primaryspecial) throws InterruptedException {	   
		if(Suffix.equals("MD") || Suffix.equals("DO") || Suffix.equals("MBBS")) {
    	comFun.scrollAndClickElement(PrimaryspecialityDropDown);
    	PrimaryspecialityDropDown.waitUntilVisible().type(primaryspecial);
    	}
   }
    
    public void validateGDPR_Accordian_StepOne() {
    String review = (pleaseReview.isCurrentlyVisible()) ? "Present" : "Not-Present";
    String privacyPolicy= (privacyPolicyLink.isCurrentlyVisible()) ? "Active" : "Non Functional";
    String navigation ="Not Present";
    if(privacyPolicy.equals("Active")){
    navigation = (stepOneNavigation()) ? "Working" : "Not Working";
    } 
     	kMap.put("Review Message", review);
    	kMap.put("Review Privacy Policy Link", privacyPolicy);
    	kMap.put("Review Privacy Policy Link Navigation", navigation);
    }
    
    public boolean stepOneNavigation() {
    	privacyPolicyLink.waitUntilClickable().click();
    	comFun.switchToSecondWindow();
    	boolean	nejmPrivacy = gdprPrivacyNejmPage.waitUntilVisible().isDisplayed();
    	comFun.DriverClose();
    	comFun.switchToOriginalWindow();
    	return nejmPrivacy;
    }
    
    public void validateGDPR_Accordian_StepTwo() {
    String gdpr= (connect.gdprMessaging.isCurrentlyVisible()) ? "Present":"Not Present";
    String termsLink= (connect.gdprTermsLink.isCurrentlyVisible()) ? "Active":"Non functional";
	String privacy = (connect.gdprPrivacyPolicyLink.isCurrentlyVisible()) ? "Active" : "Non functional";
	String mainNav ="Not Present";
	if(privacy.equals("Active")){
	 mainNav= (mainNavigation()) ? "Working" : "Not Working";
	}
     kMap.put("GDPR Message", gdpr);
     kMap.put("Terms link", termsLink);
     kMap.put("Privacy Policy Link", privacy);
     kMap.put("Terms & Privacy Policy Navigation", mainNav);     
    }
    
    public boolean mainNavigation() {
    	connect.gdprTermsLink.waitUntilClickable().click();
    	comFun.switchToSecondWindow();
    	boolean nejmTerms = connect.gdprTermsNejmPage.waitUntilVisible().isDisplayed();
    	comFun.DriverClose();
    	comFun.switchToOriginalWindow();
    	connect.gdprPrivacyPolicyLink.waitUntilClickable().click();
    	comFun.switchToSecondWindow();
    	boolean nejmPrivacy = gdprPrivacyNejmPage.waitUntilVisible().isDisplayed();
    	comFun.DriverClose();
    	comFun.switchToOriginalWindow();
    	return (nejmTerms && nejmPrivacy) ? true : false;
    }
    
	public void setSOIConnect_RememberMe(String Country) {		
			String catalystSoi = (regSoi.isCurrentlyVisible()) ? "Checked" : "Un-Checked";
			String catalstConnect = (regConnect.isCurrentlyVisible()) ? "Checked" : "Un-Checked";
			String rememberMe =  (regRemember.isCurrentlyVisible()) ? "Checked" : "Un-Checked";
			optIn.put("Catalst SOI", catalystSoi);
			optIn.put("Catalst Connect", catalstConnect);
			optIn.put("Remember Me", rememberMe);		
		}  
    
}
