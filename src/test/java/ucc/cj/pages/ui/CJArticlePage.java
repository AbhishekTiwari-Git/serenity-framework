package ucc.cj.pages.ui;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import ucc.pages.ui.CommonFunc;


public class CJArticlePage extends PageObject {

	CommonFunc comFun = new CommonFunc();
	
	//Mouse Hover Download PDF
	@FindBy(xpath = "//a[@class='icon-pdf-download']")
	private WebElementFacade Pdficon;
	
	@FindBy(xpath = "//span[contains(text(),'Download PDF')]")
	private WebElementFacade DownloadPdf;
	
	@FindBy(xpath = "//h1[@property='name headline']")
	private WebElementFacade Article_heading;
	
	@FindBy(xpath ="//h1[@property='name headline']")
	private WebElementFacade Articleheadline;
	
		
	
	// Download PDF Steps
	
	@Step("check heading present for the article")
	public boolean check_article_heading_present() {
		return Article_heading.isPresent();
	}
	
	@Step("check Download PDF Icon from article tool bar is displayed")
	public boolean check_article_Download_PDF_Icon()	{		
		return Pdficon.isDisplayed();
	}
	
	@Step("verify the tooltip message will appear PDF Download")
	public String verify_tooltip_message_on_PDF_Download()	{
		withAction().moveToElement(Pdficon).build().perform();
		return DownloadPdf.waitUntilVisible().getText();		
	}

	@Step("the user click on Download PDF Icon from article tool bar")
	public void click_Download_PDF_Icon() {		
		withAction().moveToElement(Pdficon).click().build().perform();
	}
	
	@Step("verify the url before and after opening the pdf")
	public boolean verify_before_and_after_url() {
		String getURL = getDriver().getCurrentUrl();
		return getURL.contains("pdf");		
	}
}
