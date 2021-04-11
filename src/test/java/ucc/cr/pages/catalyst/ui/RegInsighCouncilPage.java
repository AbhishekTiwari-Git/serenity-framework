package ucc.cr.pages.catalyst.ui;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ucc.pages.ui.CommonFunc;
import ucc.utils.TestUtils;

public class RegInsighCouncilPage extends PageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegInsighCouncilPage.class);
    CommonFunc commonFunc = new CommonFunc();
   

    TestUtils tUtil=new TestUtils();

    @FindBy(xpath="//span[contains(text(),'Insights')]")
    WebElementFacade InsightsCouncilLink;

    @FindBy(xpath="//span[contains(text(),'Apply now')]")
    WebElementFacade ApplyButton;

    @FindBy(xpath="//*[starts-with(@id,'uccEmail')]")
    WebElementFacade inputEmail;
    
    @FindBy(xpath="//button[contains(text(), 'Continue')]")
    WebElementFacade ContinueButton;

    @FindBy(xpath="//*[starts-with(@id,'uccFirstName')]")
    WebElementFacade firstName;

    @FindBy(xpath="//*[starts-with(@id,'uccLastName')]")
    WebElementFacade lastName;

    @FindBy(xpath="//*[starts-with(@id,'uccSuffix')]")
    WebElementFacade suffixDropDown;

    @FindBy(xpath="//*[starts-with(@id,'uccRole')]")
    WebElementFacade roleDropDown;

    @FindBy(xpath="//*[starts-with(@id,'uccPlaceOfWork')]")
    WebElementFacade placeOfWorkDropDown;

    @FindBy(xpath="//*[starts-with(@id,'uccNameOfOrg')]")
    WebElementFacade nameOfOrg;

    @FindBy(xpath="//*[starts-with(@id,'uccCountry')]")
    WebElementFacade countryDropDown;

    @FindBy(xpath="//h1[contains(text(),'Congratulations!')]")
    WebElementFacade textOnPage;
    
    @FindBy(xpath="//h1[contains(text(),'Apply Now')]")
    WebElementFacade applyNowText;
    
    @FindBy(xpath="//a[contains(text(),'Apply to join')]")
    WebElementFacade applyToJoin;
    

    @Step("Enter user email")
    public void enter_email_click_button(String user_email) {
    	LOGGER.info("User register email: " + user_email);
    	applyNowText.waitUntilVisible();
    	inputEmail.waitUntilClickable().clear();
        inputEmail.waitUntilClickable().sendKeys(user_email);
        commonFunc.clickElement(ContinueButton);
    }

    @Step("Click to Insights Council link")
    public void clickInsightsCouncil() {
    	
    	InsightsCouncilLink.waitUntilClickable().click();
    }

    @Step("Click apply button")
    public void clickApplyBtn() {
        commonFunc.clickElement(ApplyButton);
    }

    @Step("Click apply button")
    public void clickContinue() {
        ContinueButton.waitUntilClickable().click();
    }

    @Step("Enter insight council data")
    public void enter_user_data(
            String suffix, String role, String place, String orgName, String country) {

        String fName = tUtil.AppendTimestamp("firstName");
        String lName = tUtil.AppendTimestamp("lastName");

        createData(fName, lName, suffix, role, place, orgName, country);
    }

    @Step("Enter insight council data")
    public void enter_lead_user_data(
            String suffix, String role, String place) {
        createLeadData(suffix, role, place);
    }

    private void createLeadData(String suffix, String role, String place) {
        suffixDropDown.waitUntilClickable().selectByValue(suffix);
        roleDropDown.waitUntilClickable().selectByValue(role);
        placeOfWorkDropDown.waitUntilClickable().selectByVisibleText(place);
        ContinueButton.waitUntilClickable().click();
    }

    private void createData(
            String fName, String lName, String suffix, String role, String place, String orgName, String country) {

        firstName.waitUntilClickable().clear();
        firstName.type(fName);
        lastName.waitUntilClickable().clear();
        lastName.type(lName);
        suffixDropDown.waitUntilClickable().selectByValue(suffix);
        roleDropDown.waitUntilClickable().selectByValue(role);
        placeOfWorkDropDown.waitUntilClickable().selectByVisibleText(place);
        nameOfOrg.waitUntilClickable().clear();
        nameOfOrg.type(orgName);
        countryDropDown.waitUntilClickable().selectByValue(country);
        ContinueButton.waitUntilClickable().click();
    }

    @Step("Account created")
    public String account_created() {
        return textOnPage.waitUntilVisible().getText();
    }

    public String getUserEmail() {
        return inputEmail.waitUntilClickable().getAttribute("value");
    }
    
    public void hoverICLink() {
    	commonFunc.mousehover(InsightsCouncilLink);
    }
    
    public void selectApplyToJoin() {
    	commonFunc.clickElement(applyToJoin);
    }
 }
