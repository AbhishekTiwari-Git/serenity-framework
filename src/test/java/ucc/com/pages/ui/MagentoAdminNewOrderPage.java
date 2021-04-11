package ucc.com.pages.ui;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import ucc.pages.ui.CommonFunc;
import java.util.List;

public class MagentoAdminNewOrderPage extends PageObject {	
	
	CommonFunc comFun = new CommonFunc();
	
	@FindBy(xpath = "//span[contains(text(),'Create New Customer')]")
	private WebElementFacade CreateNewCustomer;

	@FindBy(xpath = "//text()[.='Add Products']/ancestor::button[1]")
	private WebElementFacade AddProducts;

	@FindBy(xpath = "//input[@type='checkbox'][@id='id_53']")
	private WebElementFacade FreeGiftCheckbox;
	
	@FindBy(xpath = "//input[@type='checkbox'][@id='id_41']")
	private WebElementFacade MonthlySubscriptionCheckbox;
	
	@FindBy(xpath = "//input[@type='checkbox'][@id='id_29']")
	private WebElementFacade AnnualSubscriptionCheckbox;
	
	@FindBy(xpath = "//input[@id='email']")
	private WebElementFacade emailid;
	
	@FindBy(xpath = "//select[@id='role']")
	private WebElementFacade UserRole;

	@FindBy(xpath = "//select[@name='order[account][customersuffix]']")
	private WebElementFacade UserSuffix;
	
	@FindBy(xpath = "//select[@name='order[account][primaryspecialty]']")
	private WebElementFacade Primeryspeciality;
	
	@FindBy(xpath = "//input[@type='text'][@name='order[account][nameoforganization]']")
	private WebElementFacade NameofOrg;
	
	@FindBy(xpath = "//input[@id='order-billing_address_firstname']")
	private WebElementFacade firstname;
	
	@FindBy(xpath = "//input[@id='order-billing_address_lastname']")
	private WebElementFacade lastname;
	
	@FindBy(xpath = "//input[@id='order-billing_address_street0']")
	private WebElementFacade streetaddress;
	
	@FindBy(xpath = "//input[@id='order-billing_address_city']")
	private WebElementFacade City;
	
	@FindBy(xpath = "//select[@id='order-billing_address_country_id']")
	private WebElementFacade Country;
	
	@FindBy(xpath = "//select[@id='order-billing_address_region_id']")
	private WebElementFacade Stateselect;
	
	@FindBy(xpath = "//input[@id='order-billing_address_region'")
	private WebElementFacade Statetype;
	
	@FindBy(xpath = "//input[@id='order-billing_address_postcode']")
	private WebElementFacade zipcode;
	
	@FindBy(xpath = "//label[contains(text(),'Send Invoice')]")
	private WebElementFacade MagentoOrderSendInvoice;
	
	@FindBy(xpath = "//span[text()='Add Selected Product(s) to Order']")
	private WebElementFacade AddSelectedProductButton;
	
	@FindBy(xpath = "//span[text()='Submit Order']")
	private WebElementFacade SubmitOrderButton;
	
	@FindBy(xpath = "//div[contains(text(),'You created the order.')]")
	private WebElementFacade OrderCreatedMsg;
	
	@FindBy(css = "span[class='price']")
	private List<WebElementFacade> OrderTotalDue;
	
	@FindBy(xpath="//div[@class='page-actions _fixed']")
    WebElementFacade Loadingmask;
	
	@FindBy(xpath = "//input[@type='checkbox'][@id='id_3']")
	private WebElementFacade PaidMonthlySubscriptionCheckbox;
	
	@FindBy(xpath = "//input[@type='checkbox'][@id='id_2']")
	private WebElementFacade PaidAnnualSubscriptionCheckbox;
	
	@FindBy(xpath = "//input[@id='options_11_2']")
	private WebElementFacade PaidMonthlySubscriptionTerm;
	
	@FindBy(xpath = "//span[contains(text(),'1 month')]")
	private WebElementFacade CompMonthlySubscriptionTerm;
	
	@FindBy(xpath = "//span[contains(text(),'1 year')]")
	private WebElementFacade CompAnnualSubscriptionTerm;
	
	@FindBy(xpath = "//input[@id='options_3_2']")
	private WebElementFacade PaidAnnual1YearSubscriptionTerm;
	
	@FindBy(xpath = "//span[text()='OK']")
	private WebElementFacade PaidSubscriptionTermOKbutton;
	
	@FindBy(xpath = "//a[@onclick='return order.loadPaymentMethods();']")
	private WebElementFacade GetPaymentMethod;
	
	@FindBy(xpath = "//select[@id='giftwrapping_design']")
	private WebElementFacade GiftDesign;
	
	@FindBy(xpath = "//text()[contains(.,'mshinde@mms.org')]/ancestor::a[1]")
	private WebElementFacade useraccounticon;
	
	
	
	
	public void click_create_new_customer_button() {

		CreateNewCustomer.waitUntilClickable().click();

	}

	public void click_Add_Products_button() {

		AddProducts.waitUntilClickable().click();

	}
	
	public void click_Free_gift_checkbox() {

		FreeGiftCheckbox.waitUntilClickable().click();

	}
	
	public void click_monthly_subscription() {

		MonthlySubscriptionCheckbox.waitUntilClickable().click();

	}
	
	public void click_annual_subscription() {

		AnnualSubscriptionCheckbox.waitUntilClickable().click();

	}
	
	public void click_add_selected_product_to_order() {		

		AddSelectedProductButton.waitUntilClickable().click();		

	}
	
	public void click_term_of_selected_product() {

		PaidMonthlySubscriptionTerm.waitUntilClickable().click();
	}
	
	public void click_term_of_selected_product_Comp_Monthly() {

		CompMonthlySubscriptionTerm.waitUntilVisible().click();
	}
	
	public void click_term_of_selected_product_Comp_Annual() {

		CompAnnualSubscriptionTerm.waitUntilVisible().click();
	}
	
		
	public void click_term_of_selected_product_1Year() {

		PaidAnnual1YearSubscriptionTerm.waitUntilClickable().click();
	}
	
	public void click_ok_button_of_custom_options() {

		PaidSubscriptionTermOKbutton.waitUntilClickable().click();
		useraccounticon.waitUntilVisible().click();
	}
	
	public void click_ok_button_of_custom_options_paid_subscription() {

		PaidSubscriptionTermOKbutton.waitUntilClickable().click();		
	}
	
	public void click_payment_method() throws InterruptedException {
		GiftDesign.waitUntilVisible().click();		
		comFun.clickElement(GetPaymentMethod);	
	}
		
	
	public void Enter_Magento_order_details(String Testemail,String fname, String lname, String suffix, String Primaryspecial, String role, 
    		String organization, String country, String Address, 
    		String city, String state, String zip) throws InterruptedException {
        
		
		emailid.waitUntilVisible().type(Testemail);
        
		UserRole.waitUntilVisible().selectByVisibleText(role);
		
		UserSuffix.waitUntilVisible().selectByVisibleText(suffix);

		Primeryspeciality.waitUntilVisible().selectByVisibleText(Primaryspecial);

		NameofOrg.waitUntilVisible().typeAndTab(organization);
		firstname.waitUntilVisible().type(fname);

		lastname.waitUntilVisible().type(lname);
		streetaddress.waitUntilVisible().type(Address);        
		
		City.waitUntilVisible().type(city);

		Country.waitUntilVisible().selectByValue(country);

		if (Stateselect.waitUntilVisible().isDisplayed()) {
			
			Stateselect.waitUntilVisible().selectByVisibleText(state);
			
		} else

		{
			Statetype.waitUntilVisible().type(state);
		}

		zipcode.waitUntilVisible().type(zip);

	}
	
	public void click_send_invoice_in_payment_shipping_information() {
				
		MagentoOrderSendInvoice.waitUntilClickable().click();		

	}
	
	public void click_submit_order_button() throws InterruptedException {

		comFun.scrollAndClickElement(SubmitOrderButton);

	}
	
	public String verify_order_created_message() throws InterruptedException {

		return OrderCreatedMsg.waitUntilVisible().getText();

	}
	
	public String verify_order_total_due() throws InterruptedException {

		String ordertotdue = OrderTotalDue.get(11).waitUntilVisible().getText();
		return ordertotdue;
		

	}
	
	public void click_paid_monthly_subscription() {

		PaidMonthlySubscriptionCheckbox.waitUntilClickable().click();

	}
	
	public void click_paid_annual_subscription() {

		PaidAnnualSubscriptionCheckbox.waitUntilClickable().click();

	}
	

}
