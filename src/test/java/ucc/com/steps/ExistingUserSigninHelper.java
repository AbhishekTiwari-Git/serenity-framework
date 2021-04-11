package ucc.com.steps;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import ucc.com.pages.ui.ComPaymentConfirmation;
import ucc.i.method.accountexp.AccountExpGET;
import ucc.utils.ResponseCode;

public class ExistingUserSigninHelper {
	
	
	 static ComPaymentConfirmation comPayment = new ComPaymentConfirmation();
	static AccountExpGET AccExpGETSteps = new AccountExpGET();
	static String endPt = null;	
	public static Response verify_payment_confirmation_messages(String payment_confirmation_message,String order_details_name, String Subscription_type,String order_details_price,  
			String order_details_term, String order_details_payment_method, String order_Amount_subtotal,
			String order_amount_tax, String order_total_amount_paid, String billing_country_name,String email, String OrderConfirmation, Map<String, String> Confirmationmap) throws InterruptedException
	{ 		
		Assert.assertEquals(payment_confirmation_message, OrderConfirmation);		
		Assert.assertEquals(order_details_name + " — " + Subscription_type, Confirmationmap.get("orderdetailname"));
		Assert.assertEquals(order_details_price, Confirmationmap.get("orderdetailprice"));
		Assert.assertEquals(order_details_term, Confirmationmap.get("term"));
		Assert.assertEquals(order_details_payment_method, Confirmationmap.get("paymentmethod"));
		Assert.assertEquals(order_Amount_subtotal, Confirmationmap.get("subtotal"));
		Assert.assertEquals(order_amount_tax, Confirmationmap.get("tax"));
		Assert.assertEquals(order_total_amount_paid, Confirmationmap.get("amountpaid"));
		endPt = AccExpGETSteps.setEndpointCustomers();
		return  AccExpGETSteps.getUserByEmail(endPt, email).assertThat().statusCode(ResponseCode.OK).extract()
				.response();
	}
	
	


}
