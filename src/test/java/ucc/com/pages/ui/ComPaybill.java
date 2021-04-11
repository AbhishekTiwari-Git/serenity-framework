package ucc.com.pages.ui;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import ucc.pages.ui.CommonFunc;
import ucc.utils.YamlUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComPaybill extends PageObject {

	CommonFunc comFun = new CommonFunc();
	YamlUtils YamlUtil = new YamlUtils();
	private static final Logger LOGGER = LoggerFactory.getLogger(ComPaybill.class);

	@FindBy(xpath = "//div[@class=' ucc-spinner']")
	private WebElementFacade Paybill_Signin_Load_page;

	@FindBy(xpath = "//h1[contains(text(),'Pay Your Bill')]")
	WebElementFacade PayBillHeader;

	@FindBy(xpath = "//span[contains(text(),'Pay Your Bill')]")
	WebElementFacade PayBillSICheckoutHeader;

	@FindBy(xpath = "//p[contains(text(),'Our records show ')]")
	WebElementFacade NotEligibleMessage1;

	@FindBy(xpath = "//p[contains(text(),'You can review your account ')]")
	WebElementFacade NotEligibleMessage2;

	@FindBy(xpath = "//a[contains(text(),'Back to NEJM Catalyst home page')]")
	WebElementFacade NotEligibleMessage3;

	@FindBy(xpath = "//h3[contains(text(),'Our records show that you do not have')]")
	WebElementFacade NotEligibleMessage4;

	@FindBy(xpath = "//h3[contains(text(),'If you would like to subscribe')]")
	WebElementFacade SubscribeMessage;

	@FindBy(xpath = "//h3[contains(text(),'No Amount Due')]")
	WebElementFacade NoPaymentDue;
	
	@FindBy(xpath = "//input[@id='md_firstdata']")
	WebElementFacade PaybillCreditCardOption;
	
	@FindBy(xpath = "//input[@id='paypal_express']")
	WebElementFacade PaybillPayPalOption;

	@FindBy(xpath = "//input[@type='number'][@name='payment[cc_number]']")
	WebElementFacade PaybillUserCCNumber;

	@FindBy(xpath = "//select[@name='payment[cc_exp_month]']")
	WebElementFacade PaybillUserCCExpMonth;

	@FindBy(xpath = "//select[@name='payment[cc_exp_year]']")
	WebElementFacade PaybillUserCCExpYear;

	@FindBy(xpath = "//input[@type='number'][@name='payment[cc_cid]']")
	WebElementFacade PaybillUserCCCvvNumber;

	@FindBy(xpath = "//div[@id='checkout-payment-method-load']/div[3]/div[1]/button[1]")
	WebElementFacade PaybillUserSubmitPaymentButton;

	@FindBy(xpath = "//span[text()='Billing Address']")
	WebElementFacade PaybillBillingAddress;

	@FindBy(xpath = "//div[contains(text(),'Payment Method')]")
	WebElementFacade PaybillPaymentMethod;

	@FindBy(xpath = "//span[text()='Credit Card']")
	WebElementFacade PaybillCreditCard;
	
	@FindBy(xpath = "//span[text()='Send Invoice']")
	WebElementFacade RenewSI;

	@FindBy(className = "customername")
	WebElementFacade Customername;

	@Step("Verify PayBill page")
	public String Verify_PayBill_Page() throws InterruptedException {

		return PayBillHeader.waitUntilVisible().getText();

	}
	
	@Step("Verify Paybill Page sections")
	public void Verify_PayBill_Page_Sections(Map<String, String> row) throws InterruptedException {			

		Assert.assertEquals(row.get("CC_message"),PaybillCreditCard.waitUntilVisible().getText());

		Assert.assertEquals(row.get("Billing_address_section"),PaybillBillingAddress.waitUntilVisible().getText());
		
		Assert.assertEquals(row.get("Payment_method_section"),PaybillPaymentMethod.waitUntilVisible().getText());	

	}

	@Step("Verify Paybill not eligible message")
	public String Verify_PayBill_Not_Eligible_Message() throws InterruptedException {

		String NotEligibleMSG1 = NotEligibleMessage1.waitUntilVisible().getText();
		String NotEligibleMSG2 = NotEligibleMessage2.waitUntilVisible().getText();
		String NotEligibleMSG3 = NotEligibleMessage3.waitUntilVisible().getText();

		List<String> listString = Arrays.asList(NotEligibleMSG1, NotEligibleMSG2, NotEligibleMSG3);
		StringBuffer sb = new StringBuffer();
		for (String s : listString) {
			sb.append(s);
			sb.append("\n\n");
		}
		return sb.toString();

	}

	@Step("Verify Paybill not eligible message")
	public String Verify_PayBill_Not_Eligible_Message2() throws InterruptedException {

		String NotEligibleMSG1 = NotEligibleMessage4.waitUntilVisible().getText();
		String NotEligibleMSG2 = SubscribeMessage.waitUntilVisible().getText();
		String NotEligibleMSG3 = NotEligibleMessage2.waitUntilVisible().getText();
		String NotEligibleMSG4 = NotEligibleMessage3.waitUntilVisible().getText();

		List<String> listString = Arrays.asList(NotEligibleMSG1, NotEligibleMSG2, NotEligibleMSG3, NotEligibleMSG4);
		StringBuffer sb = new StringBuffer();
		for (String s : listString) {
			sb.append(s);
			sb.append("\n\n");
		}
		return sb.toString();

	}

	@Step("Verify Paybill no payment due message")
	public String Verify_PayBill_No_Payment_Due_Message() throws InterruptedException {

		return NoPaymentDue.waitUntilVisible().getText();

	}

	@Step("Verify Pay Your Bill page")
	public String Verify_Pay_Your_Bill_Message() throws InterruptedException {

		return PayBillSICheckoutHeader.waitUntilVisible().getText();

	}

	@Step("Verify Billing address section displayed")
	public String Verify_Billing_Address_section_displayed() throws InterruptedException {

		return PaybillBillingAddress.waitUntilVisible().getText();

	}

	@Step("Verify Payment method section displayed")
	public String Verify_payment_method_section_displayed() throws InterruptedException {

		return PaybillPaymentMethod.waitUntilVisible().getText();

	}

	@Step("Verify Credit card option displayed")
	public String Verify_credit_card_option_displayed() throws InterruptedException {

		return PaybillCreditCard.waitUntilVisible().getText();

	}

	@Step("Enter CC details")
	public void Enter_CC_details(String CCnum, String CCexpmonth, String CCexpyear, String CCcvv)
			throws InterruptedException {

		PaybillUserCCNumber.waitUntilVisible().type(CCnum);
		PaybillUserCCExpMonth.waitUntilVisible().selectByValue(CCexpmonth);
		PaybillUserCCExpYear.waitUntilVisible().selectByVisibleText(CCexpyear);
		PaybillUserCCCvvNumber.waitUntilVisible().type(CCcvv);

	}
	
	@Step("Paybill User checkout")
	public void User_checkout(final Map<String, String> row)
			throws InterruptedException, JSONException, IOException {
			String PaymentType = row.get("pay_method").trim();
		switch (PaymentType) {

		case "CC":
			JSONObject CardInfo = new JSONObject(YamlUtil.getValueFromYml("card.yml", "/" + row.get("CreditCardType")));		
			String ExpiryDate[] = CardInfo.getString("expiry").split("/");
			PaybillCreditCardOption.waitUntilEnabled();
			PaybillCreditCardOption.waitUntilClickable().click();
			PaybillUserCCNumber.waitUntilVisible().type(CardInfo.getNumber("number").toString());
			PaybillUserCCExpMonth.waitUntilVisible().selectByValue(ExpiryDate[0]);
			PaybillUserCCExpYear.waitUntilVisible().selectByVisibleText(ExpiryDate[1]);
			PaybillUserCCCvvNumber.waitUntilVisible().type(CardInfo.getNumber("cvv").toString());
			break;
        
		case "Send Invoice":
			Paybill_Signin_Load_page.waitUntilNotVisible();
			RenewSI.waitUntilEnabled();
			RenewSI.waitUntilClickable().click();
			break;
			
		case "PayPal":
			PaybillPayPalOption.waitUntilEnabled();
			PaybillPayPalOption.waitUntilClickable().click();
			break;

		default:
			LOGGER.info("Invalid payment type" + PaymentType);
			break;
		}	
		
		PaybillUserSubmitPaymentButton.waitUntilClickable();
		comFun.clickElement(PaybillUserSubmitPaymentButton);
		}

	@Step("click submit payment button")
	public void clicking_submit_payment_button() throws InterruptedException {

		PaybillUserSubmitPaymentButton.waitUntilClickable();
		comFun.clickElement(PaybillUserSubmitPaymentButton);

	}

	@Step("get signedin customer name")
	public String Get_signedin_customer_name() throws InterruptedException {

		Paybill_Signin_Load_page.waitUntilNotVisible();
		return Customername.waitUntilVisible().getText();

	}

}
