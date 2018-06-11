package pageobjects;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import elements.Cart;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductPage extends BasePage<ProductPage> {

	private final By price = By.cssSelector("span[data-hook='product-price']");
	private final By addToCartButton = By.cssSelector("button.button-add-to-cart");
	private final By productName = By.cssSelector("product-name>h2.product-name");
	private final By productImage = By.cssSelector("img.current-displayed-image");
	private final By itemsQuantity = By.cssSelector(".input-group>input");
	private final By productInfo = By.cssSelector("product-page.product-page-container");

	public ProductPage() {
		$$("iframe").shouldHave(CollectionCondition.size(5));
		switchToFrame(2);
	}

	public boolean isPriceDisplayed() {
		SelenideElement element = $(price);
		return element.isDisplayed() && !element.getText().isEmpty();
	}

	public boolean isImageDisplayed() {
		SelenideElement element = $(productImage);
		return element.isImage() && element.isDisplayed();
	}

	public boolean isAddToCartButtonDisplayed() {
		SelenideElement element = $(addToCartButton);
		return element.isDisplayed();
	}

	public boolean isProductNameDisplayed() {
		SelenideElement element = $(productName);
		return element.isDisplayed();
	}

	public ProductPage waitForProductInfoDisplayed() {
		$(productInfo).waitUntil(Condition.visible, 10000);
		return this;
	}

	public Cart addItemToCart(int quantity) {
		$(itemsQuantity).setValue(String.valueOf(quantity));
		$(addToCartButton).click();
		return new Cart();
	}

	@Override
	void setUrl() {
		this.url = getPathFromUrl();
	}
}
