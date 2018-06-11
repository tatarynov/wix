package elements;

import org.openqa.selenium.By;
import pageobjects.ShopPage;
import support.IFrameTools;

import static com.codeborne.selenide.Selenide.$;

public class NavigationMenu {

	private IFrameTools iFrameTools = IFrameTools.getInstance();
	private final By storesItemMenu = By.xpath("//*[@id='SITE_HEADERinlineContent']//li[3]");

	public NavigationMenu() {
		iFrameTools.switchToDefaultContent();
	}

	public ShopPage goToShop() {
		$(storesItemMenu).click();
		return new ShopPage();
	}
}
