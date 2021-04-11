package ucc.com.pages.ui;


import java.util.Map;

import org.junit.Assert;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import ucc.pages.ui.CommonFunc;
import ucc.utils.TestUtils;

public class ComRenew extends PageObject {

	CommonFunc comFun = new CommonFunc();

	@FindBy(xpath = "//h1[contains(text(),'Renew')]")
	WebElementFacade RenewHeader;
	
	@FindBy(xpath = "//span[@class='welcome_message'][contains(text(),'Sign In')]")
	WebElementFacade RenewSignIn;
	
	@FindBy(xpath = "//input[@id='capture_signInEmbedded_signInEmailAddress']")
	WebElementFacade RenewSignInEmail;
	
	@FindBy(xpath = "//input[@id='capture_signInEmbedded_currentPassword']")
	WebElementFacade RenewSignInPassword;
	
	@FindBy(xpath = "//form[@id='capture_signInEmbedded_signInForm']//button[@id='ucc-sign-in-submit-embedded']")
	WebElementFacade RenewSignInButton;
	
	@FindBy(xpath = "//h1[contains(text(),'Pay Your Bill')]")
	WebElementFacade PayBillHeader;
	
	@FindBy(xpath ="//span[contains(text(),'Welcome')]")
	WebElementFacade Username;
	
	@FindBy(xpath = "//a[@id='sign_out_akamai_link']")
	WebElementFacade Logoutlink;
	
	@FindBy(xpath = "//input[@id='option_3_3']")
	WebElementFacade RenewRate1Year;
	
	@FindBy(xpath = "//input[@id='option_3_6']")
	WebElementFacade RenewRate2Year;
	
	@FindBy(xpath = "//div[@class=' ucc-spinner']")
	private WebElementFacade RenewSignin_Load_page;
	
	@FindBy(xpath = "//span[text()='Billing Address']")
	WebElementFacade RenewBillingAddress;

	@FindBy(xpath = "//div[contains(text(),'Payment Method')]")
	WebElementFacade RenewPaymentMethod;

	@FindBy(xpath = "//span[text()='Credit Card with Automatic Renewal']")
	WebElementFacade RenewCreditCard;
	
	@FindBy(xpath = "//span[text()='Renewal Rate']")
	WebElementFacade RenewalRate;
	
	@FindBy(xpath = "//select[@name='billing_address_id']")
	WebElementFacade RenewBillAdress;
	
	@FindBy(xpath = "//div[@name='billingAddressshared.country_id']/div[1]/select[1]")
	WebElementFacade BillingCountry;

	@FindBy(xpath = "//div[@name='billingAddressshared.street.0']/div[1]/input[1]")
	WebElementFacade StreetAddress;

	@FindBy(xpath = "//div[@name='billingAddressshared.city']/div[1]/input[1]")
	WebElementFacade BillingCity;

	@FindBy(xpath = "//div[@name='billingAddressshared.region_id']/div[1]/select[1]")
	WebElementFacade StateProvinceSelect;

	@FindBy(xpath = "//div[@name='billingAddressshared.region']//div//input[@name='region']")
	WebElementFacade StateProvinceType;

	@FindBy(xpath = "//input[@type='text'][@name='region']")
	WebElementFacade StateProvinceInput;

	@FindBy(xpath = "//div[@name='billingAddressshared.postcode']/div[1]/input[1]")
	WebElementFacade ZipPostalcode;
	
	 @FindBy(xpath="//div[@class='loading-mask']")
	 WebElementFacade Loadingmask;
	 
	 @FindBy(xpath = "//span[@class='customername']")
	 WebElementFacade Signedinusername;
	 
	 private static final TestUtils tUtil = new TestUtils();

	@Step("Verify Renew page")
	public String Verify_Renew_Page() throws InterruptedException {

		return RenewHeader.waitUntilVisible().getText();

	}
	
	@Step("Verify PayBill page")
	public String Verify_PayBill_Page() throws InterruptedException {

		return PayBillHeader.waitUntilVisible().getText();

	}
	
	@Step("Sign in Paybill")
	public void Sign_In_Paybill(String EmailID,String pwd) throws InterruptedException {		
		RenewSignInEmail.waitUntilVisible().type(EmailID);
		RenewSignInPassword.waitUntilVisible().type(pwd);
		RenewSignin_Load_page.waitUntilNotVisible();		
		comFun.scrollAndClickElement(RenewSignInButton);

	}
	
	@Step("Enter Email Address")
	public void Enter_Email_ID(String EmailID) throws InterruptedException {

		RenewSignInEmail.waitUntilVisible().type(EmailID);

	}

	@Step("Enter Password")
	public void Enter_Password(String pwd) throws InterruptedException {

		RenewSignInPassword.waitUntilVisible().type(pwd);

	}

	@Step("Click Signin")
	public void Click_Signin() throws InterruptedException {
		
		RenewSignin_Load_page.waitUntilNotVisible();
		RenewSignInButton.waitUntilVisible().click();

	}

	@Step("Verify Renew SignIn page")
	public String Verify_Renew_SignIn_Page() throws InterruptedException {

		return RenewSignIn.waitUntilVisible().getText();

	}

	@Step("Clicking Username")
	public void Click_username() throws InterruptedException {
		comFun.clickElement(Username);

	}

	@Step("Clicking Logout link")
	public void Click_Logout_link() throws InterruptedException {

		Logoutlink.waitUntilVisible().click();

	}
	
	@Step("Selecting renewal rate")
	public void Select_Renew_Rate(String RenewRate) throws InterruptedException {
		Loadingmask.waitUntilNotVisible();
		switch (RenewRate)
        {               
            case "1 Year":            	
            	comFun.scrollAndClickElement(RenewRate1Year);            	
            	break;
            	
            case "2 Year":            	          
            	comFun.scrollAndClickElement(RenewRate2Year);            	
            	Loadingmask.waitUntilNotVisible();            	
            	break;
           
        }
		
	}
	
	@Step("Verify Renew Page sections")
	public void Verify_Renew_Page_Sections(Map<String, String> row) throws InterruptedException {			
        
		Assert.assertEquals(row.get("CC_message"),RenewCreditCard.waitUntilVisible().getText());

		Assert.assertEquals(row.get("Billing_address_section"),RenewBillingAddress.waitUntilVisible().getText());
		
		Assert.assertEquals(row.get("Payment_method_section"),RenewPaymentMethod.waitUntilVisible().getText());	
		
		Assert.assertEquals(row.get("Renewal_rate_section"),RenewalRate.waitUntilVisible().getText());
		
		String [] username = Signedinusername.waitUntilVisible().getText().split(" ");
		tUtil.putToSession("firstname", username[0]);
		tUtil.putToSession("lastname", username[1]);
		
	}
	
	@Step("Select new address")
	public void select_new_address(String Newaddress) throws InterruptedException {

		RenewBillAdress.waitUntilVisible().selectByVisibleText(Newaddress);

	}
	
	@Step("Enter new address details")
	public void Enter_new_billing_address(Map<String, String> row) {

		BillingCountry.waitUntilVisible().selectByVisibleText(row.get("country"));

		StreetAddress.waitUntilVisible().type(row.get("street address"));

		BillingCity.waitUntilVisible().type(row.get("city"));

		if (StateProvinceSelect.isCurrentlyVisible()) {
			StateProvinceSelect.waitUntilVisible().selectByVisibleText(row.get("state province"));
		} else

		{
			StateProvinceType.waitUntilVisible().type(row.get("state province"));
		}

		ZipPostalcode.waitUntilVisible().type(row.get("Zip Postal code"));
	}


}
