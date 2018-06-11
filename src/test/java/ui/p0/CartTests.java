package ui.p0;

import elements.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import pageobjects.MainPage;
import pageobjects.MyCartPage;
import pageobjects.ProductPage;
import pageobjects.ShopPage;
import support.exceptions.NoCartItemFound;
import ui.BaseTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class CartTests extends BaseTest {

	private static final Logger LOG = LoggerFactory.getLogger(CartTests.class);

	@Test(groups = {"P0", "Cart", "Shop"}, description = "ID: 123123 - Test cart")
	public void testCart() throws Exception {
		String productOne = "glasses";
		String productTwo = "I'm a product";

		LOG.info("Step 1: Go to Shop");
		ShopPage shopPage = new MainPage()
				.openPage()
				.goToShopPage();
		assertThat(shopPage.isGalleryDisplayed()).as("Gallery is displayed").isTrue();
		assertThat(shopPage.getNumberOfItemsInCart()).as("Number of items equals 0").isEqualTo(0);

		LOG.info("Step 2: Select product GLASSES from gallery shown");
		ProductPage glassesPage = shopPage.chooseProduct(productOne);
		assertThat(glassesPage.isProductNameDisplayed()).as("Product name is displayed").isTrue();
		assertThat(glassesPage.isPriceDisplayed()).as("Product price is displayed").isTrue();
		assertThat(glassesPage.isImageDisplayed()).as("Product image is displayed").isTrue();

		LOG.info("Step 3: Add item to Cart");
		Cart itemCart = glassesPage.addItemToCart(1);
		assertThat(itemCart.isDisplayed()).as("Cart is displayed").isTrue();
		assertThat(itemCart.getNumberOfItemsByProduct(productOne))
				.as("Number of items in cart with name '{}' equals 1", productOne)
				.isEqualTo(1);

		LOG.info("Step 4: Remove item from Cart");
		itemCart.removeItem(productOne);
		assertThat(itemCart.isEmpty()).as("Cart is empty").isTrue();
		assertThat(itemCart.getCartItemsNumber()).as("Number of cart items equals 0").isEqualTo(0);

		LOG.info("Step 5: Minimize the Cart");
		itemCart.minimizeCart();
		assertThat(itemCart.isDisplayed()).as("Cart is not displayed after minimizing the Cart").isFalse();
		ProductPage glassesPageAfterMinimizing = new ProductPage().waitForProductInfoDisplayed();
		assertThat(glassesPageAfterMinimizing.isAddToCartButtonDisplayed())
				.as("Add to Cart button is displayed after minimizing the Cart").isTrue();

		LOG.info("Step 6: Add product to Cart again");
		Cart cartOpenedAgain = glassesPageAfterMinimizing.addItemToCart(1);
		assertThat(cartOpenedAgain.isDisplayed()).as("Cart is displayed").isTrue();
		assertThat(cartOpenedAgain.getNumberOfItemsByProduct(productOne))
				.as("Number of items in cart with name '{}' equals 1", productOne)
				.isEqualTo(1);

		LOG.info("Step 7: Minimize the Cart");
		cartOpenedAgain.minimizeCart();
		assertThat(cartOpenedAgain.isDisplayed()).as("Cart is not displayed after minimizing").isFalse();
		ProductPage glassesPageAfterSecondMinimizing = new ProductPage().waitForProductInfoDisplayed();
		assertThat(glassesPageAfterSecondMinimizing.isAddToCartButtonDisplayed())
				.as("Add to Cart button is displayed after minimizing the Cart").isTrue();

		LOG.info("Step 8: Go back to main STORES gallery");
		ShopPage shopPageWithAddedItem = glassesPageAfterSecondMinimizing.goToShopPage();
		assertThat(shopPageWithAddedItem.isGalleryDisplayed()).as("Gallery is displayed").isTrue();
		assertThat(shopPageWithAddedItem.getNumberOfItemsInCart()).as("Added item is displayed in top cart icon").isEqualTo(1);

		// FIXME: There is a bug with cart "Cart becomes opened after second click on 'Bag' icon", STR:
		// FIXME: 1. Go to any product page
		// FIXME: 2. Open cart and then minimize it
		// FIXME: 3. Go to Stores Page
		// FIXME: 4. Click on Cart icon
		// FIXME: 5. Observe the result
		// FIXME: ER: Cart is opened; AR: cart is not opened

		LOG.info("Step 9: Go to Bag of Items and expect glasses items");
		Cart cart = shopPageWithAddedItem.openCartFromTopIcon();
		assertThat(cart.isDisplayed()).as("Cart is displayed").isTrue();
		assertThat(cart.getNumberOfItemsByProduct(productOne)).as("Glasses is displayed in cart").isEqualTo(1);

		LOG.info("Step 10: Minimize the Cart");
		cart.minimizeCart();
		assertThat(cartOpenedAgain.isDisplayed()).as("Cart is not displayed after minimizing").isFalse();
		ShopPage shopPageStep10 = new ShopPage();
		assertThat(shopPageStep10.isGalleryDisplayed()).as("Gallery is displayed").isTrue();

		LOG.info("Step 11: Select product SCARF from the gallery shown");
		ProductPage scarfPage = shopPageStep10.chooseProduct(productTwo);
		assertThat(scarfPage.isProductNameDisplayed()).as("Product name is displayed").isTrue();
		assertThat(scarfPage.isPriceDisplayed()).as("Product price is displayed").isTrue();
		assertThat(scarfPage.isImageDisplayed()).as("Product image is displayed").isTrue();

		LOG.info("Step 12: Add item to Cart");
		Cart cartWithScarf = scarfPage.addItemToCart(1);
		assertThat(cartWithScarf.isDisplayed()).as("Cart is displayed").isTrue();
		assertThat(cartWithScarf.getNumberOfItemsByProduct(productTwo))
				.as("Number of items in cart with name '{}' equals 1", productTwo).isEqualTo(1);

		LOG.info("Step 13: Go to Cart View");
		MyCartPage myCartPage = cartWithScarf.viewCart();
		assertThat(myCartPage.getNumberOfItems()).as("Number of items in cart equals 2").isEqualTo(2);
		assertThat(myCartPage.isCheckoutButtonDisplayed()).as("Checkout buttons are displayed").isTrue();

		LOG.info("Step 14: Change quantity of glasses to 3");
		MyCartPage.CartItem glasses = myCartPage.getCartItemByName(productOne);
		glasses.setQuantity(3);
		assertThat(glasses.getQuantity()).as("Quantity of {} equals 3", productOne).isEqualTo(3);
		MyCartPage.CartItem scarf = myCartPage.getCartItemByName(productTwo);
		assertThat(scarf.getQuantity()).as("Quantity of {} equals 3", productTwo).isEqualTo(1);

		LOG.info("Step 15: Remove scarf from the Cart view");
		scarf.removeItem();
		assertThat(myCartPage.getNumberOfItems()).as("Number of items in cart equals 1").isEqualTo(1);
		Throwable noSuchItem = catchThrowable(() -> myCartPage.getCartItemByName(productTwo));
		assertThat(noSuchItem).as("Item {} is not displayed in cart", productTwo).isInstanceOf(NoCartItemFound.class);

	}
}
