package support;

public class PropertiesManager {

	private static PropertiesManager instance;

	public String getEnv() {
		String env = System.getProperty("env", "test");
		String value = null;
		if (env.equals("test"))
			value = "https://www.aitwix.com/";
		else if (env.equals("staging"))
			value = "https://www.example.com/";
		else if (env.equals("local"))
			value = "http://localhost:8080";
		return value;
	}

	public int getTimeout() {
		return Integer.valueOf(System.getProperty("timeout", "20000"));
	}

	public String getBrowser() {
		return System.getProperty("browser", "chrome");
	}

	public static PropertiesManager getInstance() {
		if (instance == null) instance = new PropertiesManager();
		return instance;
	}
}
