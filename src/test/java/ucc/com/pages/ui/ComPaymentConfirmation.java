package ucc.com.pages.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import ucc.pages.ui.CommonFunc;

public class ComPaymentConfirmation extends PageObject {

	CommonFunc comFun = new CommonFunc();
	@FindBy(xpath = "//span[contains(text(),'Thank you for your payment.')]")
	private WebElementFacade Paymentconfirmation;

	@FindBy(xpath = "//p[contains(text(),'NEJM Catalyst Innovations in Care Delivery')]")
	private WebElementFacade PaymentDetailName;

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

	@FindBy(xpath = "//a[contains(text(),'View Receipt')]")
	private WebElementFacade ViewReceipt;

	@FindBy(xpath = "//a[contains(text(),'Visit My Account')]")
	private WebElementFacade VisitMyAccount;

	@FindBy(xpath = "//a[contains(text(),'Go to Latest Issue')]")
	private WebElementFacade LatestIssueButton;

	@FindBy(xpath = "//h4[contains(text(),'TABLE OF CONTENTS')]")
	private WebElementFacade LatestIssueelement;

	public String Order_Confirmation_message() {

		String OrderConf = Paymentconfirmation.waitUntilVisible().getText();       
		return OrderConf;

	}

	public Map<String, String> Order_Confirmation_Page_Details() throws InterruptedException {

		Map<String, String> OrderConfirmationmap = new HashMap<String, String>();
		OrderConfirmationmap.put("orderdetailname", PaymentDetailName.waitUntilVisible().getText());
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
		
	}

	public void Click_Visit_My_Account() throws InterruptedException {

		comFun.scrollAndClickElement(VisitMyAccount);

	}

	public void Click_latest_issue() throws InterruptedException {

		LatestIssueButton.waitUntilVisible().click();

	}

	public String get_latest_issue() throws InterruptedException {

		//LatestIssueelement.waitUntilVisible();
		return getDriver().getCurrentUrl();

	}

	public String verify_View_Receipt() throws InterruptedException {

		List<String> browserTabs = new ArrayList<String>(getDriver().getWindowHandles());
		comFun.switchToSecondWindow();
		getDriver().switchTo().window(browserTabs.get(1));
		String ReceiptURL = getDriver().getCurrentUrl();
		getDriver().close();
		getDriver().switchTo().window(browserTabs.get(0));
		return ReceiptURL;

	}

}
