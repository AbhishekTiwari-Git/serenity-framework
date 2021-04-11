package ucc.cj.pages.ui;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import ucc.pages.ui.CommonFunc;

public class CJUserAccessPage extends PageObject {

	CommonFunc comFun = new CommonFunc();

	// Anonymous User NOT from Institution
	@FindBy(xpath = "//a[@title='Case Studies']")
	private WebElementFacade CaseStudies;

	@FindBy(xpath = "//a[contains(.,'Making Workplace Civility Go Viral')]")
	private WebElementFacade CaseStudyarticle;

	@FindBy(xpath = "//option[contains(text(),'United States')]")
	private WebElementFacade Country;

	// Insights Council NOT from Institution tries to access In Depth Articles
	@FindBy(xpath = "//h1[contains(.,'You qualify to join the')]")
	private WebElementFacade HeaderMessageText;

	@FindBy(xpath = "//select[@name='uccOrganization']")
	private WebElementFacade OrganisationType;

	@FindBy(xpath = "//select[@name='uccHealthAffiliation']")
	private WebElementFacade AffiliatedOrganisation;

	@FindBy(xpath = "//select[@name='uccNoofbeds']")
	private WebElementFacade NumberOfBeds;

	@FindBy(xpath = "//select[@name='uccNetpatientRevenue']")
	private WebElementFacade NetPatientRevenue;

	@FindBy(xpath = "//span[contains(.,'Determine the business need')]")
	private WebElementFacade BussinessNeed;

	@FindBy(xpath = "//span[contains(.,'Create the strategy and determine the solution')]")
	private WebElementFacade StrategyforSolution;

	@FindBy(xpath = "//span[contains(.,'Evaluate vendors that can provide the solution')]")
	private WebElementFacade VendorsSolution;

	@FindBy(xpath = "//span[contains(.,'Create the short list of products/vendors to review')]")
	private WebElementFacade ListOfProducts_Vendors;

	@FindBy(xpath = "//span[contains(.,'Recommend/specify the brands/vendors for purchase')]")
	private WebElementFacade Recommend_Brands_Vendor;

	@FindBy(xpath = "//span[contains(.,'Authorize/approve the purchase of the solution')]")
	private WebElementFacade Purchase_Authorize_Approve;

	@FindBy(xpath = "//select[@name='uccestimatedDollor']")
	private WebElementFacade Estimated_ProductandServices_amount;

	@FindBy(xpath = "//select[@name='uccYearofbirth']")
	private WebElementFacade BirthYear;

	@FindBy(xpath = "//select[@name='uccState']")
	private WebElementFacade State;

	@FindBy(xpath = "//button[contains(.,'Join')]")
	private WebElementFacade JoinBtn;

	@FindBy(xpath = "//strong[contains(.,'This article is available to subscribers.')]")
	private WebElementFacade Subscriptionmessage;

	@FindBy(xpath = "//div[@class=' ucc-form-container']")
	private WebElementFacade Catalystconnectcontainer;

	@FindBy(xpath = "//button[contains(.,'CONTINUE')]")
	private WebElementFacade ContinueButton;

	@FindBy(xpath = "//button[contains(.,'SIGN UP')]")
	private WebElementFacade SignUpBtn;

	@FindBy(xpath = "//div[@id='ucc-widgets-msg-modal']//div[@class='ucc-modal-container']")
	private WebElementFacade Messagepopup;

	@FindBy(xpath = "(//button[@class='ucc-modal-close'])[2]")
	private WebElementFacade Popupclosebtn;

	// Lead User NOT from Institution tries to access In Depth Articles

	@FindBy(xpath = "//div[@class='footer__box footer__box--site-map']//a[contains(text(),'In Depth')]")
	private WebElementFacade InDepthArticlelink;

	@FindBy(linkText = "Professionalism Revealed: Rethinking Quality Improvement in the Wake of a Pandemic")
	private WebElementFacade InDeptharticle;

	@FindBy(xpath = "//a[contains(text(),\"Pay Bill\")]")
	private WebElementFacade Subscribebutton;

	@FindBy(xpath = "//a[text()='Go to homepage >']")
	private WebElementFacade GoToHome;

	@FindBy(xpath = "//input[contains(@name,'uccEmail')]")
	WebElementFacade emailId;

	@FindBy(xpath = "//input[contains(@name,'uccPwd')]")
	WebElementFacade password;

	@FindBy(xpath = "//button[contains(@class, 'ucc-btn ucc-btn-primary')]")
	WebElementFacade Button;

	@FindBy(xpath = "//div[@id='ucc-modal-form']/div/div/div[2]//div[@class=' panelContent']//button[contains(.,'CONTINUE')]")
	WebElementFacade PersonalInfo_ContinueBtn;

	@FindBy(xpath = "//div[@id='ucc-modal-form']/div/div/div[3]//div[@class=' panelContent']//button[contains(.,'CONTINUE')]")
	WebElementFacade ProfessionalInfo_ContinueBtn;

	@FindBy(xpath = "//button[contains(.,'CREATE ACCOUNT')]")
	WebElementFacade CreateAccountBtn;

	@Step("check catalyst connect pop up")
	public boolean check_catalystconnect_popup() {
		return Catalystconnectcontainer.isDisplayed();
	}

	@Step("click on Continue button")
	public void click_continuebtn() {
		ContinueButton.waitUntilEnabled().click();
	}

	@Step("Click on Signup button")
	public void click_signupBtn() {
		SignUpBtn.click();
	}

	@Step("check message pop up")
	public boolean check_messagepopup() {
		return Messagepopup.isDisplayed();
	}

	@Step("click on pop up message close button")
	public void click_popupclosebtn() {
		Popupclosebtn.click();
	}

	@Step("Click on InDepth link")
	public void click_indepthlink() throws InterruptedException {
		comFun.scrollAndClickElement(InDepthArticlelink);
	}

	@Step("click on InDepth Article")
	public void click_indepthdarticle() {
		InDeptharticle.waitUntilVisible();
		withAction().moveToElement(InDeptharticle).click().build().perform();
	}

	@Step("check Subscribe button")
	public boolean check_subscribeBtn() {
		return Subscribebutton.isDisplayed();
	}

	@Step("Check Subscribe message")
	public String check_subscriptionmessage() {
		return Subscriptionmessage.waitUntilVisible().getText();
	}

	@Step("Click on Case Studies link")
	public void click_casestudies() throws InterruptedException {
		comFun.scrollAndClickElement(CaseStudies);
	}

	@Step("click on Case Study Article")
	public void click_casestudyarticle() throws InterruptedException {
		comFun.scrollAndClickElement(CaseStudyarticle);
	}

	@Step("check header pop up message Text")
	public String check_headermessage() {
		return HeaderMessageText.waitUntilVisible().getText();
	}

	@Step("Select the type of organization")
	public void select_organisationtype(String TypeofOrganisation) {
		OrganisationType.waitUntilClickable().selectByValue(TypeofOrganisation);
	}

	@Step("Select organization affiliated with a Health System")
	public void select_organisation_affiliation(String OrgAffiliated) {
		AffiliatedOrganisation.waitUntilClickable().selectByValue(OrgAffiliated);
	}

	@Step("select employment by the number of beds")
	public void select_numberofbeds(String NoOfBeds) {
		NumberOfBeds.waitUntilClickable().selectByValue(NoOfBeds);
	}

	@Step("Select patient revenue of your combined facilities")
	public void select_patientrevenue(String revenue) {
		NetPatientRevenue.waitUntilClickable().selectByValue(revenue);
	}

	@Step("Click to select vendors and buying products ")
	public void check_vendors_products() {
		BussinessNeed.waitUntilClickable().click();
		StrategyforSolution.waitUntilClickable().click();
		VendorsSolution.waitUntilClickable().click();
		ListOfProducts_Vendors.waitUntilClickable().click();
		Recommend_Brands_Vendor.waitUntilClickable().click();
		Purchase_Authorize_Approve.waitUntilClickable().click();
	}

	@Step("Select amount of products and services purchases")
	public void select_products_and_services_amount(String ProductandServices_amount) {
		Estimated_ProductandServices_amount.waitUntilClickable().selectByValue(ProductandServices_amount);
	}

	@Step("Select Year of Birth")
	public void select_yearofbirth(String YearofBirth) {
		BirthYear.waitUntilClickable().selectByValue(YearofBirth);
	}

	@Step("Select the State")
	public void select_state(String state) {
		State.waitUntilClickable().selectByValue(state);
	}

	@Step("Click on Join button")
	public void click_joinbutton() {
		JoinBtn.click();
	}

	// Insights Council NOT User from Institution tries to access In Depth

	@Step("Click on Go To HomePage Link")
	public void click_Go_to_homepage() {

		GoToHome.waitUntilPresent().click();

	}

}
