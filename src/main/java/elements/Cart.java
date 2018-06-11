package elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import pageobjects.MyCartPage;
import support.IFrameTools;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Cart {

	private IFrameTools iFrameTools = IFrameTools.getInstance();
	private final By closeCartButton = By.cssSelector("#cart-widget-close");
	private final By itemQuantity = By.cssSelector(".item-quantity>span");
	private final By removeItemButton = By.cssSelector("button.remove-item");
	private final By cartPopup = By.cssSelector(".minicart");
	private final By emptyCart = By.cssSelector(".mini-cart-empty-user-text>span");
	private final By cartListItem = By.cssSelector("li[ng-repeat*='item']");
	private final By viewCart = By.cssSelector("footer a.button-primary");

	public Cart() {
		iFrameTools.switchToDefaultContent();
		iFrameTools.switchToFrame(3);
		$(cartPopup).waitUntil(Condition.visible, 10000);
	}

	public void minimizeCart() {
		$(closeCartButton).click();
		$(cartPopup).waitUntil(Condition.disappear, 5000);
	}

	public int getNumberOfItemsByProduct(String productName) {
		ElementsCollection items = $$(cartListItem).filterBy(Condition.text(productName));
		return Integer.valueOf(items.get(0).$(itemQuantity).getText());
	}

	public int getCartItemsNumber() {
		return $$(cartListItem).size();
	}

	public Cart removeItem(String productName) {
		ElementsCollection items = $$(cartListItem).filterBy(Condition.text(productName));
		items.get(0).$(removeItemButton)
				.hover()
				.waitUntil(Condition.visible, 5000)
				.click();
		return this;
	}

	public boolean isDisplayed() {
		return $(cartPopup).isDisplayed();
	}

	public boolean isEmpty() {
		SelenideElement cart = $(emptyCart).waitUntil(Condition.visible, 5000);
		return cart.getText().equals("Cart is empty") && cart.isDisplayed();
	}

	public MyCartPage viewCart() {
		$(viewCart).click();
		return new MyCartPage();
	}
}
