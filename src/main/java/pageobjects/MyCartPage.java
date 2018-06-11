package pageobjects;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import support.exceptions.NoCartItemFound;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MyCartPage extends BasePage<MyCartPage> {

	private final By item = By.cssSelector("section[data-hook='item']");
	private final By checkoutButton = By.cssSelector("button[data-hook=\"checkout-button-button\"]");

	public MyCartPage() {
		$$("iframe").shouldHave(CollectionCondition.sizeGreaterThanOrEqual(5));
		switchToFrame(3);
		$("div[data-hook=\"item-list\"]").waitUntil(Condition.visible, 10000);
	}

	public CartItem getCartItemByName(String name) throws NoCartItemFound {
		ElementsCollection selenideElements = $$(item).filterBy(Condition.text(name));
		if (selenideElements.size() == 0) throw new NoCartItemFound(String.format("No cart items with %s found", name));
		return new CartItem(selenideElements.get(0));
	}

	public int getNumberOfItems() {
		return $$(item).size();
	}

	public boolean isCheckoutButtonDisplayed() {
		return $$(checkoutButton).size() == 2;
	}

	@Override
	void setUrl() {
		getPathFromUrl();
	}

	public static class CartItem extends MyCartPage {

		private final SelenideElement parentElement;
		private final By removeButton = By.cssSelector("button[data-hook=\"remove-button\"]");
		private final By quantityInput = By.cssSelector("input[type=\"number\"]");
		private final By price = By.cssSelector("span[data-hook=\"product-price\"]");

		public CartItem(SelenideElement parentElement) {
			this.parentElement = parentElement;
		}

		public void removeItem() {
			parentElement.$(removeButton).click();
			parentElement.waitUntil(Condition.disappear, 5000);
		}

		public void setQuantity(int quantityValue) {
			SelenideElement element = parentElement.$(quantityInput);
			element.clear();
			element.setValue(String.valueOf(quantityValue));
		}

		public int getQuantity() {
			return Integer.valueOf(parentElement.$(quantityInput).getValue());
		}
	}
}
