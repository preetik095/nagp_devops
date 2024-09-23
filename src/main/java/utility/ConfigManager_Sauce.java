package utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigManager_Sauce {
	
	private static ConfigManager_Sauce manager;
	
	private static final Properties prop = new Properties();
	private static final Logger log = LogManager.getLogger(ConfigManager.class);
	
	private ConfigManager_Sauce() throws IOException {
		InputStream configInputStream = ConfigManager.class.getResourceAsStream("/config/config_sauceLab.properties");

		
		//loading config.properties file
		try {
			
			prop.load(configInputStream);	
			log.debug("config_sauceLab.proerties file found");
			
		} catch (FileNotFoundException e) {
			log.error("Unable to find config properties file on classpath "+configInputStream);
			e.printStackTrace();
		}
	
		
		log.debug("ConfigManager exit");
	}
	
	
	
	public static ConfigManager_Sauce getInstance() {
		//only one instance of the class is created
		if(manager == null) {
			
			//no other thread will not able to access this class
			synchronized (ConfigManager.class) {
				try {
					manager = new ConfigManager_Sauce();
				} catch (IOException e) {
					log.error("IOException occured");
					e.printStackTrace();
				}
			}
		}
		return manager;
	}
	
	
	//function to get property value
	public String getString(String key) {
		return System.getProperty(key, prop.getProperty(key));
	}
	
	
	
}
