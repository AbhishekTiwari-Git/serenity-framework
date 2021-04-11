package ucc.com.pages.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import io.restassured.response.Response;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.i.method.accountexp.AccountExpGET;
import ucc.pages.ui.CommonFunc;
import ucc.utils.TestUtils;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;

public class ComOrderConfirmation extends PageObject {

	CommonFunc comFun = new CommonFunc();
	private static TestUtils tUtil = new TestUtils();
	JsonUtils jsonUtils = new JsonUtils();
	private AccountExpGET AccExpGET = new AccountExpGET();
	static String endPt = null;	
	@FindBy(xpath = "//span[contains(text(),'Thank you for your order!')]")
	private WebElementFacade Orderconfirmation;

	@FindBy(xpath = "//p[contains(text(),'NEJM Catalyst Innovations in Care Delivery')]")
	private WebElementFacade OrderDetailName;

	@FindBy(className = "payment-method")
	private WebElementFacade PaymentMethod;

	@FindBy(className = "item-price")
	private WebElementFacade OrderDetailPrice;

	@FindBy(className = "order-subtotal")
	private WebElementFacade OrderSubtotal;

	@FindBy(className = "order-tax")
	private WebElementFacade OrderTax;

	@FindBy(className = "order-amount-paid")
	private WebElementFacade OrderAmountPaid;

	@FindBy(className = "item-months")
	private WebElementFacade OrderDetailTerm;

	@FindBy(xpath = "//span[contains(text(),'Any questions about your order?')]")
	private WebElementFacade AnyQuestions;

	@FindBy(xpath = "//span[contains(text(),'Please contact our customer service department:')]")
	private WebElementFacade PleaseContact;

	@FindBy(xpath = "//p[contains(text(),'U.S. & Canada 800-843-6356 (8am - 4pm ET Mon-Fri)')]")
	private WebElementFacade ContactDetails;

	@FindBy(className = "order-billing")
	private WebElementFacade OrderBilling;

	@FindBy(xpath = "//a[contains(text(),'DOWNLOAD FREE GIFT')]")
	private WebElementFacade DownloadFreeGift;

	@FindBy(xpath = "//a[contains(text(),'View Receipt')]")
	private WebElementFacade ViewReceipt;

	@FindBy(xpath = "//a[contains(text(),'Manage Your Email Profile')]")
	private WebElementFacade ManageEmailProfile;

	@FindBy(xpath = "//a[contains(text(),'Go to Latest Issue')]")
	private WebElementFacade LatestIssueButton;

	@FindBy(xpath = "//h4[contains(text(),'TABLE OF CONTENTS')]")
	private WebElementFacade LatestIssueelement;
	
	//
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
		
	@FindBy(xpath = "//span[contains(text(),'Thank you for your payment.')]")
	WebElementFacade Paymentconfirmation;
	
	@FindBy(xpath = "//span[text()='Thank you for renewing your subscription!']")
	WebElementFacade Renewconfirmation;
	
	public static Map<String, String> Confirmationmap = new HashMap<String, String>();
	ComPaymentConfirmation comPayment = new ComPaymentConfirmation();
	
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();		
	static String Order_conf_msg =  EnvironmentSpecificConfiguration.from(env_var)
            .getProperty("Order.Conf.Message");

    
	public void Order_Confirmation_Message_Price_Verification(final Map<String, String> row) throws InterruptedException {

		Assert.assertEquals(row.get("orderdetailprice"), OrderDetailPrice.waitUntilVisible().getText());
		Assert.assertEquals(row.get("subtotal"), OrderSubtotal.waitUntilVisible().getText());
		Assert.assertEquals(row.get("tax"), OrderTax.waitUntilVisible().getText());
		Assert.assertEquals(row.get("amountpaid"), OrderAmountPaid.waitUntilVisible().getText());
		Assert.assertEquals(row.get("OrderConfirmationMessage").trim(), Orderconfirmation.waitUntilVisible().getText());		

	}
	public String Order_Confirmation_message() {

		String OrderConf = Orderconfirmation.waitUntilVisible().getText();

		return OrderConf;

	}

	public Map<String, String> Order_Confirmation_Page_Details() throws InterruptedException {

		Map<String, String> OrderConfirmationmap = new HashMap<String, String>();

		OrderConfirmationmap.put("orderdetailname", OrderDetailName.waitUntilVisible().getText());
		OrderConfirmationmap.put("orderdetailprice", OrderDetailPrice.waitUntilVisible().getText());
		OrderConfirmationmap.put("term", OrderDetailTerm.waitUntilVisible().getText());
		OrderConfirmationmap.put("paymentmethod", PaymentMethod.waitUntilVisible().getText());
		OrderConfirmationmap.put("anyquestions", AnyQuestions.waitUntilVisible().getText());
		OrderConfirmationmap.put("pleasecontact", PleaseContact.waitUntilVisible().getText());
		OrderConfirmationmap.put("contactdetails", ContactDetails.waitUntilVisible().getText());
		OrderConfirmationmap.put("subtotal", OrderSubtotal.waitUntilVisible().getText());
		OrderConfirmationmap.put("tax", OrderTax.waitUntilVisible().getText());
		OrderConfirmationmap.put("amountpaid", OrderAmountPaid.waitUntilVisible().getText());
		OrderConfirmationmap.put("billinginfo", OrderBilling.waitUntilVisible().getText());
		return OrderConfirmationmap;

	}

	public void Click_View_Receipt() throws InterruptedException {

		comFun.scrollAndClickElement(ViewReceipt);

		TimeUnit.SECONDS.sleep(3);
	}

	public void Click_Manage_Email_Profile() throws InterruptedException {

		comFun.scrollAndClickElement(ManageEmailProfile);

	}

	public void Click_latest_issue() throws InterruptedException {

		LatestIssueButton.waitUntilVisible().click();

	}

	public String get_latest_issue() throws InterruptedException {

		LatestIssueelement.waitUntilVisible();
		return getDriver().getCurrentUrl();

	}

	public void Download_Free_Gift() {

		DownloadFreeGift.waitUntilClickable().click();

	}

	public String verify_View_Receipt() throws InterruptedException {

		List<String> browserTabs = new ArrayList<String>(getDriver().getWindowHandles());
		getDriver().switchTo().window(browserTabs.get(1));
		String ReceiptURL = getDriver().getCurrentUrl();		
		getDriver().close();		
		getDriver().switchTo().window(browserTabs.get(0));		
		return ReceiptURL;

	}
	
	//
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
	
	@Step("get_privacyPolicyPageHeaderText")
	public void verify_payment_confirmation_messages(Map<String, String> row) throws InterruptedException
	{ 	Confirmationmap = comPayment.Order_Confirmation_Page_Details();	
		Assert.assertEquals(row.get("Payment_ConfirmationMessage"), Paymentconfirmation.waitUntilVisible().getText());			
		Assert.assertEquals(row.get("Order_details_payment_method"), Confirmationmap.get("paymentmethod"));		
		
	}
	
	
	@Step("verify renew confirmation message")
	public void verify_renew_confirmation_messages(Map<String, String> row) throws InterruptedException
	{ 	
		Confirmationmap = comPayment.Order_Confirmation_Page_Details();	
		Assert.assertEquals(row.get("Payment_ConfirmationMessage"), Renewconfirmation.waitUntilVisible().getText());			
		Assert.assertEquals(row.get("Order_details_payment_method"), Confirmationmap.get("paymentmethod"));		
		
	}
	
	/**
	 * This function is for verifying details on confirmation page
	 * @param Map
	 * @throws InterruptedException
	 * @author mshinde
	 * @date 24-Mar-2021
	 */
	
	public boolean Order_Confirmation_Page_Verify() throws InterruptedException {
        
		String OrderSubtotalPrice[] = (OrderSubtotal.waitUntilVisible().getText()).split(": ");
		String OrderTaxAmt[] = (OrderTax.waitUntilVisible().getText()).split(": ");
		String OrderAmountPaidAmt[] = (OrderAmountPaid.waitUntilVisible().getText()).split(": ");		
		ComCheckout.BillingInfo.equalsIgnoreCase(OrderBilling.waitUntilVisible().getText());		
		return ((tUtil.getFromSession("ExpectedCartPricewithCurrency")).equals(jsonUtils.convertToUTF8(OrderDetailPrice.waitUntilVisible().getText()))) &&					
		        ((tUtil.getFromSession("ExpectedCartPricewithCurrency")).equals(jsonUtils.convertToUTF8(OrderSubtotalPrice[1]))) &&
                ((tUtil.getFromSession("ExpectedTaxPricewithCurrency")).equals(jsonUtils.convertToUTF8(OrderTaxAmt[1]))) &&
                ((tUtil.getFromSession("ExpectedOrderTotalPriceWithCurrency")).equals(jsonUtils.convertToUTF8(OrderAmountPaidAmt[1]))) &&
				Order_conf_msg.trim().equals(Orderconfirmation.waitUntilVisible().getText());
	
	}

	
	

}