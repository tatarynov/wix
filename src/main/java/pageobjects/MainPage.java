package pageobjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainPage extends BasePage<MainPage> {

	private static final Logger LOG = LoggerFactory.getLogger(MainPage.class);

	@Override
	void setUrl() {
		url = ""; // main page
	}
}
