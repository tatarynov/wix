package support;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {

	private DriverManagerType type;

	public DriverManager() {
		setBrowser();
	}

	public void setBrowser() {
		String browser = PropertiesManager.getInstance().getBrowser();
		switch (browser) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				// TODO: add additional options
				type = DriverManagerType.CHROME;
				break;
			case "ff":
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				// TODO: add additional options
				type = DriverManagerType.FIREFOX;
				break;
			case "opera":
				WebDriverManager.operadriver().setup();
				type = DriverManagerType.OPERA;
				break;
		}
	}

	public String getType() {
		return type.toString().toLowerCase();
	}
}
