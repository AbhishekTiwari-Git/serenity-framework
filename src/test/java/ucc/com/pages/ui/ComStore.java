package ucc.com.pages.ui;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import ucc.com.steps.GuestOrderStepsRefactored;
import ucc.pages.ui.CommonFunc;
import ucc.utils.JsonUtils;
import ucc.utils.TestUtils;
import ucc.utils.YamlUtils;
import org.json.JSONObject;

public class ComStore extends PageObject {

	CommonFunc comFun = new CommonFunc();
	private static final Logger LOGGER = LoggerFactory.getLogger(GuestOrderStepsRefactored.class);
	static EnvironmentVariables env_var = SystemEnvironmentVariables.createEnvironmentVariables();
	static String storeTitle = EnvironmentSpecificConfiguration.from(env_var).getProperty("storePageTitle");
	static String Choice_country = "";
	static String MonthlyPriceWithCurrency = "";
	static String YearlyPriceWithCurrency = "";
	static String Country_Code = "";
	public static String Product_Type = "";
	public String ExpectedMonthlyPriceWithCurrency = null;
	public String ExpectedYearlyPriceWithCurrency = null;
	public String ExpectedFreegftPriceWithCurrency = null;
	TestUtils tUtil = new TestUtils();		
	JsonUtils jsonUtils = new JsonUtils();
	YamlUtils YamlUtil = new YamlUtils();
	static DecimalFormat df = new DecimalFormat("#.00");

	@FindBy(xpath = "//button[@class='action tocart primary']")
	List<WebElementFacade> SubscribeButton;

	@FindBy(xpath = "//div[@class='page-wrapper']//div[3]//form[1]//button[1]")
	WebElementFacade AnnualSubscribeButton;

	@FindBy(xpath = "//text()[contains(.,'My Cart')]/ancestor::a[1]")
	WebElementFacade CartIcon;

	@FindBy(className = "items-total")
	WebElementFacade totalItems;

	@FindBy(xpath = "//span[@class='count']")
	WebElementFacade countItem;

	@FindBy(className = "product list")
	WebElementFacade productList;

	@FindBy(xpath = "//button[@class='action primary checkout']")
	WebElementFacade ProceedToCheckOut;

	@FindBy(xpath = "//img[@alt='Annual Subscription']")
	WebElementFacade AnnualSubImage;

	@FindBy(xpath = "//img[@class='pagebuilder-mobile-hidden']")
	WebElementFacade FreeGiftImage;

	@FindBy(xpath = "//img[@alt='Monthly Subscription']")
	WebElementFacade MonthlySubImage;

	@FindBy(id = "sign_in_akamai_link")
	private WebElementFacade Signinlink;

	@FindBy(id = "janrainModal")
	private WebElementFacade SigninPage;

	@FindBy(xpath = "//a[contains(text(),'Monthly Subscription')]")
	private WebElementFacade Monthly_Subscription_Link;

	@FindBy(id = "product-price-3")
	private WebElementFacade Monthly_Product_Price;

	@FindBy(xpath = "//button[@type='submit'][@title='Add to Cart']")
	private List<WebElementFacade> Subscribe_Buttons;

	@FindBy(xpath = "//a[contains(text(),'Learn More')][@title='Annual Subscription']")
	private WebElementFacade Annual_Learn_More_Link;

	@FindBy(partialLinkText = "Annual Subscription")
	private WebElementFacade Annual_Subscription_Link;

	@FindBy(id = "product-price-2")
	private WebElementFacade Annual_Product_Price;

	@FindBy(xpath = "//a[contains(text(),'Learn More')][@title='Monthly Subscription']")
	private WebElementFacade Monthly_Learn_More_Link;

	@FindBy(xpath = "//div[@class='badge_wrapper']")
	private WebElementFacade Annual_Banner_Text;

	@FindBy(xpath = "//img[@class='product-image-photo'][@alt='Monthly Subscription']")
	private WebElementFacade Left_Widget;

	@FindBy(xpath = "//img[@class='product-image-photo'][@alt='Annual Subscription']")
	private WebElementFacade Right_Widget;

	@FindBy(xpath = "//div[@class=' ucc-spinner']")
	private WebElementFacade Store_Load_page;

	@FindBy(xpath = "//span[@class='welcome_message'][contains(text(),'Sign In')]")
	private WebElementFacade Store_SignIn_link;

	@FindBy(xpath = "//span[@class='customername']")
	private WebElementFacade SignedInUsername;

	@FindBy(xpath = "//a[@class='logo']")
	private WebElementFacade nejmCatalystLogo;

	@FindBy(xpath = "//*[@id=\"sign_in_akamai_link\"]")
	private WebElementFacade UserblackShadow;

	@FindBy(xpath = "//p[@class='fg_text']")
	private WebElementFacade freeGiftWithAnnualSubscriptionMesg;

	@FindBy(xpath = "//p[@class='fg_stext']")
	private WebElementFacade transformingHealthCareDeliveryMesg;

	@FindBy(xpath = "//img[@src='https://store.nejm-qa.org/media/wysiwyg/Catalyst-Premium-100.jpg'][1]")
	private WebElementFacade freeGiftImage;
	
	@FindBy(xpath = "//div[@class='product-square-description']/div/div/div")
	List<WebElementFacade> includesMessages;
	
	@FindBy(xpath = "//p[contains(text(),'SATISFACTION GUARANTEED')]")
	WebElementFacade satisfactionTextTop;
	
	@FindBy(xpath = "//div[@class='satis-text-bottom']")
	WebElementFacade satisfactionTextBottom;
	
	@FindBy(className = "badge_wrapper")
	WebElementFacade bestValueFreeGift;
	
	//
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
	
	@FindBy(id = "country")
	WebElementFacade Choicecountrydropdown;
	
	@FindBy(xpath = "//span[@id='product-price-2']")
	List<WebElementFacade> Product_Price_with_Currency;
	
	// 
	
	@Step("Verify monthly and annual subscription price")
	public void Subscription_Price_Verification(String title,final Map<String, String> row) {

		Assert.assertTrue(title.equalsIgnoreCase(storeTitle));		
		Assert.assertEquals(row.get("MonthlyPrice"), Monthly_Product_Price.getText().trim());	
		Assert.assertEquals(row.get("AnnualPrice"), Annual_Product_Price.getText().trim());		
	}
	
	@Step("Click Monthly or Annual subscribenow button")
	public void Click_subscribenow_button(final Map<String, String> row) throws InterruptedException {
		String subType = row.get("SubscriptionType").trim();
		switch (subType) {

		case "Monthly":
			 Click_Monthly_subscribenow_button();
			break;

		case "Annual":
			Click_Annual_subscribenow_button();
			break;

		default:
			LOGGER.info("Value returned not equal to Monthly and Annual" + subType);
			break;

		}
		

	}
	
	@Step("Verify Store Page with 2 image of products")
	public boolean Verify_store_page_twoProduct() throws InterruptedException {

		return (Store_Load_page.waitUntilNotVisible().isPresent() == true
				&& Left_Widget.waitUntilVisible().isDisplayed() == true
				&& Right_Widget.waitUntilVisible().isDisplayed() == true);
	}

	@Step("isThere_nejmCatalystLogo")
	public boolean isThere_nejmCatalystLogo() {

		return nejmCatalystLogo.waitUntilVisible().isDisplayed();
	}

	@Step("freeGiftImage")
	public boolean freeGiftImageDisplayed() {

		return freeGiftImage.waitUntilVisible().isDisplayed();
	}

	@Step("transformingHealthCareDeliveryMesg")
	public String transformingHealthCareDeliveryMesg() {

		return transformingHealthCareDeliveryMesg.waitUntilVisible().getText();
	}

	@Step("freeGiftWithAnnualSubscriptionMesg")
	public String freeGiftWithAnnualSubscriptionMesg() {

		return freeGiftWithAnnualSubscriptionMesg.waitUntilVisible().getText();
	}

	@Step("is_miniCartIconDisplayed")
	public boolean is_miniCartIconDisplayed() {

		return CartIcon.waitUntilVisible().isDisplayed();
	}

	@Step("UserblackShadow")
	public boolean UserblackShadow() {

		return UserblackShadow.waitUntilVisible().isDisplayed();
	}

	@Step("SignInButton")
	public boolean signInButtonClickable() {

		return Store_SignIn_link.waitUntilVisible().isClickable();
	}

	@Step("Verify Store Page")
	public boolean Verify_store_page() throws InterruptedException {

		return Monthly_Subscription_Link.waitUntilVisible().isDisplayed();
	}

	@Step("Verify monthly subscription widget")
	public Map<String, String> Monthly_Subscription_Verification() {

		Map<String, String> MonthyWidget_map = new HashMap<String, String>();
		String Monthly_link_Text[] = Monthly_Subscription_Link.getText().split("\n");
		String[] M_Button_Text = (Subscribe_Buttons.get(0).getAttribute("innerHTML")).split("<|>");
		MonthyWidget_map.put("Monthly_linktext", Monthly_link_Text[0].trim());
		MonthyWidget_map.put("Monthly_duration", Monthly_link_Text[1].trim());
		MonthyWidget_map.put("Monthly_price", Monthly_Product_Price.getText().trim());
		MonthyWidget_map.put("Monthly_Buttontext", M_Button_Text[2].toUpperCase().trim());
		MonthyWidget_map.put("Monthly_LearnMore", Monthly_Learn_More_Link.getText().trim());
		return MonthyWidget_map;
	}
	
	
	
	
    
	@Step("Verify monthly subscription price on pricing page")
	public Map<String, String> Monthly_Subscription_price_Verification_pricing_page() {

		Map<String, String> MonthyWidget_map = new HashMap<String, String>();		
		MonthyWidget_map.put("Monthly_price", Monthly_Product_Price.getText().trim());		
		return MonthyWidget_map;
	}
	
	@Step("Verify annual subscription price on pricing page")
	public Map<String, String> Annual_Subscription_price_Verification_pricing_page() {

		Map<String, String> AnnualWidget_map = new HashMap<String, String>();		
		AnnualWidget_map.put("Annual_price", Annual_Product_Price.getText().trim());		
		return AnnualWidget_map;
	}
	
	@Step("Verify annual subscription widget")
	public Map<String, String> Annual_Subscription_Verification() {

		Map<String, String> AnnualWidget_map = new HashMap<String, String>();
		String Annual_link_Text[] = Annual_Subscription_Link.getText().split("\n");
		String[] A_Button_Text = (Subscribe_Buttons.get(1).getAttribute("innerHTML")).split("<|>");
		AnnualWidget_map.put("Annual_linktext", Annual_link_Text[0].trim());
		AnnualWidget_map.put("Annual_duration", Annual_link_Text[1].trim());
		AnnualWidget_map.put("Annual_price", Annual_Product_Price.getText().trim());
		AnnualWidget_map.put("Annual_Buttontext", A_Button_Text[2].toUpperCase().trim());
		AnnualWidget_map.put("Annual_LearnMore", Annual_Learn_More_Link.getText().trim());
		AnnualWidget_map.put("Annual_FreeGift", Annual_Banner_Text.getText().trim());
		return AnnualWidget_map;
	}

	@Step("Click Monthly subscribenow button")
	public void Click_Monthly_subscribenow_button() throws InterruptedException {

		Store_Load_page.waitUntilNotVisible();
		if (Subscribe_Buttons.get(0).isEnabled()) 
		    {
		    	comFun.scrollAndClickElement(Subscribe_Buttons.get(0));
			}
		else 
		    {				
				comFun.refreshBrowser();
				comFun.scrollAndClickElement(Subscribe_Buttons.get(0));			
			}
	}

	@Step("Click To Cart Icon")
	public void ClickToCartIcon() throws InterruptedException {

		comFun.scrollAndClickElement(CartIcon);
	}

	@Step("Click To Monthly Suscription Image")
	public void ClickToMonthlySubscriptionImage() throws InterruptedException {

		comFun.scrollAndClickElement(MonthlySubImage);
	}

	@Step("Click To Annual Suscription Image")
	public void ClickToAnnualSubscriptionImage() throws InterruptedException {

		comFun.scrollAndClickElement(AnnualSubImage);
	}

	@Step("Get the Item Number")
	public String GetItemNumber() throws InterruptedException {

		return countItem.waitUntilVisible().getText();
	}

	@Step("Assert the Item Number")
	public boolean AssertItemNumber() throws InterruptedException {

		return (GetItemNumber().contains("1"));

	}

	@Step("Proceed To Checkout Button")
	public boolean proceedToCheckout() throws InterruptedException {

		return ProceedToCheckOut.waitUntilVisible().isClickable();
	}

	@Step("Monthly Subscription")
	public String monthlySubscription() throws InterruptedException {

		return Monthly_Subscription_Link.waitUntilVisible().getText();
	}

	@Step("Click Annual subscribenow button")
	public void Click_Annual_subscribenow_button() throws InterruptedException {
        
		Store_Load_page.waitUntilNotVisible();
		
		if (Subscribe_Buttons.get(1).isEnabled()) 
		   {
			   comFun.scrollAndClickElement(Subscribe_Buttons.get(1));
		   } 
		else 
		   {
			   comFun.refreshBrowser();
			   comFun.scrollAndClickElement(Subscribe_Buttons.get(1));
		   }
		/*Store_Load_page.waitUntilNotVisible();
		comFun.scrollAndClickElement(AnnualSubscribeButton);*/

	}

	@Step("Verify Sign in Link on Store Page")
	public boolean Verify_signin_link_on_store_page() throws InterruptedException {

		Store_Load_page.waitUntilNotVisible();
		return Store_SignIn_link.waitUntilVisible().isDisplayed();

	}

	@Step("Clicking Sign in Link on Store Page")
	public void Click_signin_link_on_store_page() throws InterruptedException {

		Store_Load_page.waitUntilNotVisible();
		Store_SignIn_link.waitUntilVisible().click();

	}

	@Step("Retrieving signed in user name")
	public String Retrieve_signed_username() throws InterruptedException {

		Store_Load_page.waitUntilNotVisible();
		return SignedInUsername.waitUntilVisible().getText();

	}
	
	@Step("monthlyIncludesMesg")
	public String monthlyIncludesMesg() {
		return includesMessages.get(0).waitUntilVisible().getText();
	}
	
	@Step("is_monthlyIncludesMesgDisplayed")
	public boolean is_monthlyIncludesMesgDisplayed() {
		return includesMessages.get(0).waitUntilVisible().isDisplayed();
	}
	
	@Step("annualIncludesMesg")
	public String annualIncludesMesg() {
		return includesMessages.get(1).waitUntilVisible().getText();
	}
	
	@Step("is_annualIncludesMesgDisplayed")
	public boolean is_annualIncludesMesgDisplayed() {
		return includesMessages.get(1).waitUntilVisible().isDisplayed();
	}
	
	@Step("satisfactionTextTop")
	public String satisfactionTextTop() {
		return satisfactionTextTop.waitUntilVisible().getText();
	}
	
	@Step("is_satisfactionTextTopDisplayed")
	public boolean is_satisfactionTextTopDisplayed() {
		return satisfactionTextTop.waitUntilVisible().isDisplayed();
	}
	
	@Step("satisfactionTextBottom")
	public String satisfactionTextBottom() {
		return satisfactionTextBottom.waitUntilVisible().getText();
	}
	
	@Step("is_satisfactionTextBottomDisplayed")
	public boolean is_satisfactionTextBottomDisplayed() {
		return satisfactionTextBottom.waitUntilVisible().isDisplayed();
	}
	
	@Step("bestValueFreeGift")
	public String bestValueFreeGift() {
		return bestValueFreeGift.waitUntilVisible().getText();
	}
	
	@Step("is_bestValueFreeGiftDisplayed")
	public boolean is_bestValueFreeGiftDisplayed() {
		return bestValueFreeGift.waitUntilVisible().isDisplayed();
	}
	
	//
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
	
	@Step("ChoicePage_country_selection")
	public void CountrySelect(String Choicecountry) throws InterruptedException {
		Country_Code = Choicecountry;
		Choice_country = jsonUtils.getFromJSON("countryCodeList.json", Choicecountry);		
		Choicecountrydropdown.waitUntilVisible().selectByVisibleText(Choice_country);	

		}
	
	@Step("Verify Prices for Country on Store Page ")
	public boolean StorePagePriceVerification() throws IOException {
			
		JSONObject Country = new JSONObject(YamlUtil.getValueFromYml("price.yml", "/" + Country_Code));
		       String ExpectedMonthlyPrice = jsonUtils.convertToUTF8(String.valueOf(df.format(Country.getDouble("monthly"))));
		       String ExpectedYearlyPrice = jsonUtils.convertToUTF8(String.valueOf(df.format(Country.getDouble("yearly"))));
		       String ExpectedFreegftPrice = jsonUtils.convertToUTF8(String.valueOf(df.format(Country.getDouble("freegift"))));
		String CurrencySymbol = jsonUtils.getFromJSONInUTF8("Currency/Currency_Code.json", "$['" + Country_Code + "'].symbol");
		String ExpectedMonthlyPriceWithCurrency = CurrencySymbol + ExpectedMonthlyPrice;
		String ExpectedYearlyPriceWithCurrency = CurrencySymbol + ExpectedYearlyPrice;
		String ExpectedFreegftPriceWithCurrency = CurrencySymbol + ExpectedFreegftPrice;
		tUtil.putToSession("ExpectedMonthlyPriceWithCurrency", ExpectedMonthlyPriceWithCurrency);
		tUtil.putToSession("ExpectedYearlyPriceWithCurrency", ExpectedYearlyPriceWithCurrency);
		tUtil.putToSession("ExpectedFreegftPriceWithCurrency", ExpectedFreegftPriceWithCurrency);
		
		String ActualMonthlyPricewithCurrency = jsonUtils.convertToUTF8(Product_Price_with_Currency.get(0).waitUntilVisible().getText().trim());
		String ActualYearlyPricewithCurrency = jsonUtils.convertToUTF8(Product_Price_with_Currency.get(1).waitUntilVisible().getText().trim());			
		return (ExpectedMonthlyPriceWithCurrency.equals(ActualMonthlyPricewithCurrency) && ExpectedYearlyPriceWithCurrency.equals(ActualYearlyPricewithCurrency));		
        
	}
	
	@Step("ChoicePage_product_selection")
	public void ProdSelect(String SubType) throws InterruptedException {
		
		Product_Type = SubType;
		
		tUtil.putToSession("Product_Type", Product_Type);
		
		switch (SubType) {

		case "product-monthly":
			Click_Monthly_subscribenow_button();
			break;

		case "product-yearly":
			Click_Annual_subscribenow_button();
			break;

		default:
			LOGGER.info("Value returned not equal to Monthly and Annual" + SubType);
			break;

		}
}

}