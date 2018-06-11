package pageobjects;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import elements.Cart;
import elements.NavigationMenu;
import elements.TopCartIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.IFrameTools;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.sleep;

public abstract class BasePage<T> {

	protected IFrameTools iFrameTools = IFrameTools.getInstance();
	String url;
	private T context;
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	// COMMON ELEMENTS FOR ALL PAGES

	@SuppressWarnings("unchecked")
	BasePage() {
		setUrl();
		switchToDefaultContent();
		context = (T) this;
	}

	abstract void setUrl();

	public T openPage() {
		LOG.info("Openning {}{}", baseUrl, url);
		if (url == null) throw new IllegalStateException(String.format("URL is not set for %s page", this.getClass()));
		Selenide.open(url);
		return context;
	}

	public T switchToFrame(String nameOrId) {
		iFrameTools.switchToFrame(nameOrId);
		return context;
	}

	public T switchToFrame(int framePosition) {
		iFrameTools.switchToFrame(framePosition);
		return context;
	}

	public T switchToDefaultContent() {
		iFrameTools.switchToDefaultContent();
		return context;
	}

	public boolean isFrame() {
		return iFrameTools.isFrame();
	}

	public void scrollToBottom() {
		Selenide.executeJavaScript("window.scrollTo(0,document.body.scrollHeight)");
		Selenide.sleep(300);
	}

	String getPathFromUrl() {
		return "/" + WebDriverRunner.url().split(Configuration.baseUrl)[1];
	}


	public ShopPage goToShopPage() {
		return new NavigationMenu().goToShop();
	}

	public int getNumberOfItemsInCart() {
		return new TopCartIcon().getQuantity();
	}

	public Cart openCartFromTopIcon() {
		return new TopCartIcon().openCart();
	}

}
