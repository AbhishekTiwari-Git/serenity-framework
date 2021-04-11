package ucc.com.pages.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import ucc.com.steps.GuestOrderSteps;
import ucc.pages.ui.CommonFunc;

public class ComProductDetails extends PageObject {

	private static final Logger LOGGER = LoggerFactory.getLogger(GuestOrderSteps.class);

	CommonFunc comFun = new CommonFunc();

	@FindBy(xpath = "//button[@class='action primary tocart']")
	WebElementFacade SubscribeButtonOnDetailsPage;

	@FindBy(xpath = "//span[contains(@id, 'product-price')]")
	WebElementFacade productPriceOnDetailsPage;

	@FindBy(xpath = "//img[@class='fotorama__img']")
	WebElementFacade productImageOnProductPage;

	@FindBy(xpath = "//span[@data-ui-id='page-title-wrapper']")
	WebElementFacade productNameOnProductPage;

	@FindBy(xpath = "//a[@class='action delete']")
	WebElementFacade trashIcon;

	@FindBy(xpath = "//div[@itemprop='description']")
	WebElementFacade productDetailsPageDescription;

	@FindBy(xpath = "//strong[@class='subtitle empty']")
	WebElementFacade noItemMessage;

	@FindBy(xpath = "//button[@class='action-primary action-accept']")
	WebElementFacade okayToDelete;

	@FindBy(xpath = "//div[@class='message-error error message']")
	WebElementFacade ErrorMessageOnStorePage;

	@FindBy(xpath = "//span[@class='readMore']")
	WebElementFacade readMore_button;

	@Step("Verify ProductPage")
	public boolean verify_product_details_page(String ProductName, String productPriceMonthly,
			String productPriceAnnual) throws InterruptedException {

		boolean DisplayProductDetails = product_details_page();
		boolean DisplayProductImage = product_image_productPage();
		boolean ppriceOnproductPage = false;
		if (ProductName.contains("monthly")) {
			ppriceOnproductPage = (product_price_productPage().contains(productPriceMonthly));
		} else {
			ppriceOnproductPage = (product_price_productPage().contains(productPriceAnnual));
		}

		return (DisplayProductDetails == true && DisplayProductImage == true
				&& product_name_productPage().contains(ProductName) == true && ppriceOnproductPage == true);
	}

	@Step("Product Details ProductPage")
	public boolean product_details_page() throws InterruptedException {

		return productDetailsPageDescription.waitUntilVisible().isPresent();
	}

	@Step("Product Price ProductPage")
	public String product_price_productPage() throws InterruptedException {

		return productPriceOnDetailsPage.waitUntilVisible().getText();
	}

	@Step("Product Image ProductPage")
	public boolean product_image_productPage() throws InterruptedException {

		return productImageOnProductPage.waitUntilVisible().isPresent();
	}

	@Step("Product Name ProductPage")
	public String product_name_productPage() throws InterruptedException {

		return productNameOnProductPage.waitUntilVisible().getText().toLowerCase();
	}

	@Step("Click To Trash Icon")
	public void ClickToTrashIcon() throws InterruptedException {

		comFun.scrollAndClickElement(trashIcon);
	}

	@Step("Click To OK delete")
	public void ClickToOKdelete() throws InterruptedException {

		comFun.scrollAndClickElement(okayToDelete);
	}

	@Step("No Item Message")
	public String noItemMessage() throws InterruptedException {

		return noItemMessage.waitUntilVisible().getText();
	}

	@Step("Get the Error Message")
	public String GetErrorMessage() throws InterruptedException {

		return ErrorMessageOnStorePage.waitUntilVisible().getText().toLowerCase();
	}

	@Step("Verifies Error Message")
	public boolean VerifyErrorMessage() throws InterruptedException {

		return ErrorMessageOnStorePage.getText().toLowerCase().contains("already");
	}

	@Step("Click Add To Cart button on details page")
	public void Click_Add_To_Cart_button_onDetailsPage() throws InterruptedException {

		comFun.scrollAndClickElement(SubscribeButtonOnDetailsPage);
	}

	@Step("verify_readMoreButton_productPrice_productImage_subscriptionName")
	public boolean verify_readMoreButton_productPrice_productImage_subscriptionName(String productPrice,
			String subscriptionname) throws InterruptedException {

		LOGGER.info("isReadMoreButtonclickable =  " + readMore_button.waitUntilVisible().isClickable());
		boolean isReadMoreButtonclickable = readMore_button.waitUntilVisible().isClickable();

		LOGGER.info("isProductPriceCorrect =  " + product_price_productPage().contains(productPrice));
		boolean isProductPriceCorrect = product_price_productPage().equals(productPrice);

		LOGGER.info("isProductNamecorrect =  " + product_name_productPage().contains(subscriptionname));
		boolean isProductNamecorrect = product_name_productPage().equals(subscriptionname);

		return (isReadMoreButtonclickable == true && isProductPriceCorrect == true
				&& product_image_productPage() == true && isProductNamecorrect == true);
	}

}
