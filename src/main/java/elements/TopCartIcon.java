package elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import support.IFrameTools;

import static com.codeborne.selenide.Selenide.$;

public class TopCartIcon {

	private IFrameTools iFrameTools = IFrameTools.getInstance();
	private int position;
	private final By storesItemMenu = By.cssSelector("text.quantity");
	private final By cartWidget = By.cssSelector("body>cart-widget");

	public TopCartIcon() {
		if (iFrameTools.isFrame()) position = iFrameTools.getFramePosition();
		iFrameTools.switchToFrame(0);
	}

	public int getQuantity() {
		String elementText = $(storesItemMenu).getText();
		if (iFrameTools.isFrame()) iFrameTools.switchToFrame(position);
		return Integer.valueOf(elementText);
	}

	public Cart openCart() {
		Selenide.sleep(5000); //FIXME: workaround for bug on Stores Page
		$(cartWidget).waitUntil(Condition.visible, 5000).click();
		return new Cart();
	}
}
