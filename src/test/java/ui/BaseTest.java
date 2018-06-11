package ui;

import com.codeborne.selenide.Selenide;
import org.testng.annotations.AfterMethod;

public class BaseTest extends SetEnvironment {

	@AfterMethod
	public void cloweBrowser() throws Exception {
		Selenide.close();
	}
}
