package ucc.cj.pages.ui;

import java.util.logging.Logger;


import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import ucc.pages.ui.CommonFunc;

public class CJToolTip extends PageObject {

	private static final String String = null;

	Logger LOGGER = Logger.getLogger(CJToolTip.class.getName());

	CommonFunc commfun = new CommonFunc();

	// Mouse Hover Contents

	@FindBy(xpath = "//a[@class='icon-nav-bar']")
	private WebElementFacade ContentsNavBar;

	@FindBy(xpath = "//span[@class='article_toolbar-tooltip'][contains(text(),'Contents')]")
	private WebElementFacade Contentstooltip;

	// Mouse Hover Download PDF
	@FindBy(xpath = "//a[@class='icon-pdf-download']")
	private WebElementFacade Pdf;

	@FindBy(xpath = "//span[contains(text(),'Download PDF')]")
	private WebElementFacade DownloadPdf;

	// Mouse Hover Share

	@FindBy(xpath = "//a[@class='share__ctrl icon-share']")
	private WebElementFacade ShareIcon;

	@FindBy(xpath = "//span[@class='share__tooltip']")
	private WebElementFacade Share;

	// Mouse Hover Permissions

	@FindBy(xpath = "//a[@class='icon-permissions']")
	private WebElementFacade PermissionIcon;

	@FindBy(xpath = "//span[@class='article_toolbar-tooltip'][contains(text(),'Permissions')]")
	private WebElementFacade Permissions;

	// Mouse hover More Icon

	@FindBy(xpath = "//a[@class='icon-dots-three-horizontal']")
	private WebElementFacade MoreIcon;

	@FindBy(xpath = "//span[@class='article_toolbar-tooltip'][contains(text(),'More')]")
	private WebElementFacade More;

	@FindBy(linkText = "Articles")
	private WebElementFacade Articles;

	@FindBy(xpath = "//i[@class='icon-gizmo-search']")
	private WebElementFacade searchButton;

	@FindBy(xpath = "//li[@class='main-menu_item main-menu_item-search']//a")
	private WebElementFacade search;

	@FindBy(xpath = "(//img[@alt='NEJM Catalyst'])[2]")
	private WebElementFacade nejmlogo;

	@FindBy(xpath = "//img[@class='header_additional-journal_logo_1']")
	private WebElementFacade caredeliverylogo;

	@FindBy(xpath = "//div[@class='header_logo']//a")
	private WebElementFacade caredeliverytootip;

	@FindBy(xpath = "//a[contains(text(),'A Practical Guide to Leading Change at Scale')]")
	private WebElementFacade clickArticle;

	@FindBy(xpath = "//a[@class=\"article_toolbar-save-btn isLoggedIn icon-save\"]")
	private WebElementFacade SaveArticle;

	@FindBy(xpath = "//span[contains(text(),'Save article')]")
	private WebElementFacade Savearticle;
	
	@FindBy(linkText = "Facebook")
	private WebElementFacade Facebook;
	
	@FindBy(linkText = "Linked In")
	private WebElementFacade LinkedIn;
	
	@FindBy(linkText = "Twitter")
	private WebElementFacade Twitter;
	
	@FindBy(linkText = "Copy URL")
	private WebElementFacade CopyURL;
	
	@FindBy(linkText = "Email")
	private WebElementFacade Email;

	// Navigate to Article

	@Step("navigate to article page")
	public void navigate_article() throws InterruptedException {

		commfun.scrollAndClickElement(Articles);
	}

	// Contents Steps

	@Step("Mouse hover on Contents field")
	public void contents_navbar() {

		ContentsNavBar.waitUntilClickable();

		commfun.mousehover(ContentsNavBar);

	}

	@Step("verify Mouse hover on content nav bar tootip")
	public String Mousehover_content_tooltip() throws Throwable {
		LOGGER.info(Contentstooltip.getText());
		return Contentstooltip.getText();
	}

	// Search Magnifying Glass

	@Step("Mouse hover on search magnifying glass")
	public void search_magnifying_glass() {

		searchButton.waitUntilClickable();

		commfun.mousehover(searchButton);
	}

	@Step("verify search Mouse hover on Search logo")
	public String verify_search_ToolTip() throws Throwable {

		return search.getAttribute("title");
	}

	// NEJM logo

	@Step("Mouse hover on nejm logo")
	public void the_hover_tooltip() throws Throwable {

		commfun.mousehover(nejmlogo);
	}

	@Step("verify Mouse hover on nejm catalyst logo")
	public String nejmcatalystlogo_ToolTip() throws Throwable {

		return nejmlogo.getAttribute("alt");
	}

	// Download PDF Steps

	@Step("the user mouse hover on Download PDF Icon from article tool bar")
	public void mouse_hover_on_Download_PDF_Icon_article_tool_bar() {

		commfun.mousehover(Pdf);
	}

	@Step("verify the tooltip message will appear PDF Download")
	public String verify_tooltip_message_on_PDF_Download() {
		LOGGER.info(DownloadPdf.waitUntilVisible().getText());
		return DownloadPdf.waitUntilVisible().getText();
	}

	// Share Steps

	@Step("the user mouse hover on Share Icon from article tool bar")
	public void the_user_mouse_hover_on_Share_Icon_from_article_tool_bar() {

		commfun.mousehover(ShareIcon);
	}

	@Step("verify the tooltip message Share Share will appear")
	public String verify_the_tooltip_message_Share_will_appear() {
		LOGGER.info(Share.waitUntilVisible().getText());
		return Share.waitUntilVisible().getText();
	}

	// Permission Steps

	@Step("the user mouse hover on Permissions Icon from article tool bar")
	public void the_user_mouse_hover_on_Permissions_Icon_from_article_tool_bar() {

		commfun.mousehover(PermissionIcon);

	}

	@Step("verify the tooltip message Permissions will appear")
	public String verify_the_tooltip_message_Permissions_will_appear() {
		LOGGER.info(Permissions.waitUntilVisible().getText());
		return Permissions.waitUntilVisible().getText();
	}

	// More steps

	@Step("the user mouse hover on More Icon from article tool bar")
	public void the_user_mouse_hover_on_More_Icon_from_article_tool_bar() {

		commfun.mousehover(MoreIcon);
	}

	@Step("verify the tooltip message More will appear")
	public String verify_the_tooltip_message_More_will_appear() {
		LOGGER.info(More.waitUntilVisible().getText());
		return More.waitUntilVisible().getText();
	}

	// Care Delivery Logo

	@Step("the user mouse hover on NEJM Catalyst logo")
	public void innovation_caredelivery_logo() {

		commfun.mousehover(caredeliverylogo);
	}

	@Step("verify the tooltip message NEJM Catalyst Innovations in Care Delivery will appear")
	public String tooltip_message_InnovationsinCareDelivery_will_appear() {

		return caredeliverytootip.getAttribute("title");
	}

	// Save Article

	@Step("user clicks on Article")
	public void user_clicks_on_a_article() {
		clickArticle.click();
	}

	@Step("Mouse hover on SaveArticle field")
	public void the_user_mouse_hover_on_save_article_icon_from_article_tool_bar() {

		commfun.mousehover(SaveArticle);
	}

	@Step("verify Mouse hover on content nav bar tootip")
	public String verify_the_tooltip_message_Save_Article_will_appear() throws Throwable {
		
		LOGGER.info(Savearticle.waitUntilVisible().getText());
		return Savearticle.waitUntilVisible().getText();
		
	}
	
	// Validation of the Article tool bar Share links
	
		@Step("the user click on the Share icon on the Article toolbar")
		public void the_user_click_on_the_share_icon_on_the_article_toolbar() {
		 ShareIcon.waitUntilClickable().click(); 
		}
		
		@Step("verify the visible links Facebook twitter LinkedIn Email and Copy URL")
	public boolean verify_the_visible_links_facebook_twitter_linked_in_email_and_copy_url() {
			commfun.waitForLoadPage();
			LOGGER.info("FaceBook Link Visible" + Facebook);
			LOGGER.info("Twitter Link Visible" + Twitter);
			LOGGER.info("Email Link Visible" + Email);
			LOGGER.info("CopyURl Link Visible" + CopyURL);
			boolean facebook = Facebook.isCurrentlyVisible();
			boolean twitter = Twitter.isCurrentlyVisible();
			boolean email = Email.isCurrentlyVisible();
			boolean copyUrl = CopyURL.isCurrentlyVisible();
			return (facebook && twitter && email && copyUrl) ? true : false;
		}

}
