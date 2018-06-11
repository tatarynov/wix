package ui;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeSuite;
import support.DriverManager;
import support.PropertiesManager;

public class SetEnvironment {

	private DriverManager driverManager = new DriverManager();
	private PropertiesManager propertiesManager = PropertiesManager.getInstance();

	@BeforeSuite
	public void setEnvironment() throws Exception {
		Configuration.browser = driverManager.getType();
		Configuration.baseUrl = propertiesManager.getEnv();
		Configuration.startMaximized = true;
		Configuration.screenshots = true;
		Configuration.timeout = propertiesManager.getTimeout();
	}
}
