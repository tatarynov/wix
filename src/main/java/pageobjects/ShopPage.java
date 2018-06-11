package pageobjects;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ShopPage extends BasePage<ShopPage> {

	private final By gallery = By.cssSelector("div.gallery");
	private final By productItem = By.cssSelector("a.product-item");
	private final By homeButton = By.cssSelector("#comp-jh99ppou0linkElement");

	@Override
	void setUrl() {
		url = "/shop";
	}

	public ShopPage() {
		$$("iframe").shouldHave(CollectionCondition.sizeGreaterThanOrEqual(4));
		switchToFrame(1);
	}

	public boolean isGalleryDisplayed() {
		return $(gallery).isDisplayed();
	}

	public ProductPage chooseProduct(String product) {
		ElementsCollection elements = $$(productItem);
		ElementsCollection filteredElements = elements.filterBy(Condition.text(product));
		filteredElements.get(0).click();
		return new ProductPage();
	}
}
