package MediaServiceUtils;

import java.util.Properties;

public class ConfigReader {
	Properties configFile;

	public ConfigReader() {
		try {
			this.configFile = new Properties();
			this.configFile.load(this.getClass().getClassLoader().getResourceAsStream("confi.cfg"));
		} catch (Exception eta) {
			eta.printStackTrace();
		}
	}
	
	public ConfigReader(String configFileName) {
		try {
			this.configFile = new Properties();
			this.configFile.load(this.getClass().getClassLoader().getResourceAsStream(configFileName));
		} catch (Exception eta) {
			eta.printStackTrace();
		}
	}

	public String getProperty(String key) {
		String value = this.configFile.getProperty(key);
		return value;
	}
}