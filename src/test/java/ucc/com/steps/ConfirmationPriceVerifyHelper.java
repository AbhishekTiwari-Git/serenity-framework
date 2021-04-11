package ucc.com.steps;

import java.util.Map;
import org.junit.Assert;
import ucc.com.pages.ui.ComOrderConfirmation;

public class ConfirmationPriceVerifyHelper {

	ComOrderConfirmation comOrder = new ComOrderConfirmation();

	public void verify_pice_confirmation_page(String confirmation_message, String order_details_price,
			String order_Amount_subtotal, String order_amount_tax, String order_total_amount_paid)
			throws InterruptedException

	{
		String Confirmationmsg = comOrder.Order_Confirmation_message();
		Assert.assertEquals(confirmation_message, Confirmationmsg);
		Map<String, String> Confirmationmap = comOrder.Order_Confirmation_Page_Details();
		Assert.assertEquals(order_details_price, Confirmationmap.get("orderdetailprice"));
		Assert.assertEquals(order_Amount_subtotal, Confirmationmap.get("subtotal"));
		Assert.assertEquals(order_amount_tax, Confirmationmap.get("tax"));
		Assert.assertEquals(order_total_amount_paid, Confirmationmap.get("amountpaid"));

	}

}
