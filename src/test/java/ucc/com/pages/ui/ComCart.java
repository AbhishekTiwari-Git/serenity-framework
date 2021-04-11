package ucc.com.pages.ui;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import ucc.com.steps.GuestOrderSteps;
import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;

public class ComCart extends PageObject {

	private static final Logger LOGGER = LoggerFactory.getLogger(GuestOrderSteps.class);

	CommonFunc comFun = new CommonFunc();
	JsonUtils jsonUtils = new JsonUtils();
	String CartPriceWithCurrency = "";
	String subtotalPriceWithCurrency = "";
	String orderTotalWithCurrency = "";
	String FreeGiftWithCurrency = "";

	@FindBy(xpath = "//button[@class='action primary checkout']")
	WebElementFacade ProceedCheckoutButton;

	@FindBy(xpath = "//span[@class='product-image-container']")
	WebElementFacade ProductImage;

	@FindBy(xpath = "//a[@class='action continue']")
	WebElementFacade GoBackButton;

	@FindBy(xpath = "//td[@data-th='Order Total']")
	WebElementFacade orderTotal;

	@FindBy(xpath = "//span[@data-th='Subtotal']")
	WebElementFacade subtotal;

	@FindBy(xpath = "//strong[@class='product-item-name']")
	WebElementFacade productName;

	@FindBy(xpath = "//span[@data-bind='html: cart().subtotal_excl_tax']")
	WebElementFacade cartPopUpSubstotal;

	@FindBy(xpath = "//strong[@class='product-item-name']")
	WebElementFacade cartPopUpProductName;

	@FindBy(xpath = "//dl[@class='item-options']")
	WebElementFacade termDuration;

	@FindBy(xpath = "//span[@data-bind='text: option.value']")
	WebElementFacade cartPopUpTerm;

	@FindBy(xpath = "//div[@role='alert']")
	WebElementFacade AddedSuccessMessage;

	@FindBy(xpath = "//span[@class='cart-price']")
	WebElementFacade CartPrice;

	@FindBy(xpath = "//div[@class='after-cart-section-1']")
	WebElementFacade AfterCartSection;

	@FindBy(xpath = "//span[contains(text(), 'Your information is secure. Please see our ')]")
	WebElementFacade yourInfoSecureMesg;

	@FindBy(xpath = "//span[contains(text(), 'If you are not')]")
	WebElementFacade yourSafeMesg;
	
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
	
	@FindBy(xpath = "(//span[@class='cart-price'])[2]")
	WebElementFacade freeGiftPrice;
	
	//
    
	@Step("Verify Cart page product price")
	public void Verify_Cart_Price(final Map<String, String> row) throws InterruptedException {

		Assert.assertEquals(row.get("CartPrice"), CartPrice.waitUntilVisible().getText());
		Assert.assertEquals(row.get("Amount"), subtotal.waitUntilVisible().getText());
		Assert.assertEquals(row.get("OrderTotal"), orderTotal.waitUntilVisible().getText());
	}
	
	
	@Step("Verify GoBack button")
	public boolean Verify_GoBack_Button() throws InterruptedException {

		return GoBackButton.isPresent();
	}

	@Step("Verify productName")
	public String yourInfoSecureMesg() throws InterruptedException {

		return yourInfoSecureMesg.waitUntilVisible().getText();
	}

	@Step("Verify productName")
	public String yourSafeMesg() throws InterruptedException {

		return yourSafeMesg.waitUntilVisible().getText();
	}

	@Step("Verify productName")
	public String VerifyProductName() throws InterruptedException {

		return productName.waitUntilVisible().getText().toLowerCase();
	}

	@Step("Verify Subtotal")
	public String VerifySubtotal() throws InterruptedException {

		return subtotal.waitUntilVisible().getText();
	}

	@Step("Verify OrderTotal")
	public String VerifyOrderTotal() throws InterruptedException {

		return orderTotal.waitUntilVisible().getText();
	}

	@Step("Verify cartPopUpSubtotal")
	public boolean VerifyCartPopUpSubtotal() throws InterruptedException {

		return cartPopUpSubstotal.isPresent();
	}

	@Step("Verify cartPopUpTerm")
	public String VerifyCartPopUpTerm() throws InterruptedException {

		cartPopUpTerm.waitUntilVisible();
		return cartPopUpTerm.waitUntilVisible().getText().toLowerCase();
	}

	@Step("Verify cartPopUpProductName")
	public boolean VerifyCartPopUpProductName() throws InterruptedException {

		return cartPopUpProductName.isPresent();
	}

	@Step("Click Proceed To Checkout button")
	public void Click_ProceedCheckout_button() throws InterruptedException {

		ProceedCheckoutButton.waitUntilClickable();
		comFun.scrollAndClickElement(ProceedCheckoutButton);
	}

	@Step("Verify Proceed To Checkout button")
	public boolean Verify_ProceedCheckout_button() throws InterruptedException {

		return ProceedCheckoutButton.isDisplayed();
	}

	@Step("Click To GoBack button")
	public void Click_GoBack_button() throws InterruptedException {

		GoBackButton.waitUntilClickable();
		comFun.scrollAndClickElement(GoBackButton);
	}

	@Step("Verify Cart page")
	public boolean Verify_Cart_Page() throws InterruptedException {

		return ProductImage.waitUntilVisible().isPresent();
	}

	@Step("Verify Added Cart Message")
	public boolean VerifyAddedSuccessMessage() throws InterruptedException {

		return AddedSuccessMessage.isDisplayed();
	}

	@Step("Verify Product Image")
	public boolean VerifyProductImage() throws InterruptedException {

		return ProductImage.isDisplayed();
	}

	@Step("Verify Term Duration")
	public String VerifyTermDuration() throws InterruptedException {

		return termDuration.waitUntilVisible().getText().toLowerCase();
	}

	@Step("Verify Cart Price")
	public String VerifyCartPrice() throws InterruptedException {

		return CartPrice.waitUntilVisible().getText();
	}

	@Step("verify_subscriptionnameTermdurationCartpriceAmountOrdertotalPrivacysecmesgSatisfactionguarmesg")
	public boolean verify_subscriptionnameTermdurationCartpriceAmountOrdertotalPrivacysecmesgSatisfactionguarmesg(
			String subscription_name, String termduration, String cartprice, String amount, String ordertotal,
			String privacySecMesg, String satisfactionGuarMesg) throws InterruptedException {

		LOGGER.info("real productName = " + VerifyProductName() + "  excel productName = " + subscription_name);
		boolean isProductnameCorrect = VerifyProductName().equals(subscription_name);

		LOGGER.info("real termDuration = " + VerifyTermDuration() + "  excel termDuration = " + termduration);
		boolean isTermCorrect = VerifyTermDuration().equals(termduration);

		LOGGER.info("real cartPrice = " + VerifyCartPrice() + "  excel cartPrice = " + cartprice);
		boolean isCartpriceCorrect = VerifyCartPrice().equals(cartprice);

		LOGGER.info("real subTotal = " + VerifySubtotal() + "  excel subTotal = " + amount);
		boolean isSubtotalCorrect = VerifySubtotal().equals(amount);

		LOGGER.info("real orderTotal = " + VerifyOrderTotal() + "  excel orderTotal = " + ordertotal);
		boolean isOrdertotalCorrect = VerifyOrderTotal().equals(ordertotal);

		return (isProductnameCorrect == true && isTermCorrect == true && isCartpriceCorrect == true
				&& isSubtotalCorrect == true && isOrdertotalCorrect == true);
	}

	@Step("Assert Cart PopUp")
	public boolean assertCart(String productNameFromPage) throws InterruptedException {

		boolean DisplayCartPopUpSubtotal = VerifyCartPopUpSubtotal();
		boolean DisplayCartPopUpProductName = VerifyCartPopUpProductName();
		boolean DisplayCartPopUpTerm_result = false;
		String DisplayCartPopUpTerm = VerifyCartPopUpTerm();
		if (productNameFromPage.contains("month")) {
			DisplayCartPopUpTerm_result = DisplayCartPopUpTerm.contains("month");
		} else {
			DisplayCartPopUpTerm_result = DisplayCartPopUpTerm.contains("year");
		}

		return (DisplayCartPopUpSubtotal == true && DisplayCartPopUpProductName == true
				&& DisplayCartPopUpTerm_result == true);
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
	
	@Step("Verify product Price on Cart Page for selected Subscription")
	public boolean verifyCartPricingData(String ExpectedCartPrice) throws IOException {

		CartPriceWithCurrency = jsonUtils.convertToUTF8(CartPrice.waitUntilVisible().getText());				
        return ExpectedCartPrice.equals(CartPriceWithCurrency);		
		
	}
	
	@Step("Verify subtotal Price on Cart Page for selected Subscription")
	public boolean verifySubTtlPricingData(String ExpectedCartPrice) throws IOException {
		
		subtotalPriceWithCurrency = jsonUtils.convertToUTF8(subtotal.waitUntilVisible().getText());					
        return ExpectedCartPrice.equals(subtotalPriceWithCurrency);		
		
	}
	
	@Step("Verify Total Price on Cart Page for selected Subscription")
	public boolean verifyOrdTtlPagePricingData(String ExpectedCartPrice) throws IOException {
		
		orderTotalWithCurrency = jsonUtils.convertToUTF8(orderTotal.waitUntilVisible().getText());			
        return ExpectedCartPrice.equals(orderTotalWithCurrency);		
		
	}
	
	@Step("Verify Free Gift Price on Cart Page for selected Subscription")
	public boolean verifyFreegftPricingData(String ExpectedFreeGftPrice) throws IOException {
	
		FreeGiftWithCurrency = jsonUtils.convertToUTF8(freeGiftPrice.waitUntilVisible().getText());			
        return ExpectedFreeGftPrice.equals(FreeGiftWithCurrency);		
		
	}	
	
	
	
	
}