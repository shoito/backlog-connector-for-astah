package com.github.astah.connector.backlog.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationUtils {
	private static final Logger logger = LoggerFactory.getLogger(ConfigurationUtils.class);
	
	public static final File CONFIG_FILE = new File(System.getProperty("user.home") + File.separator + ".astah" + File.separator + "plugins", "backlog-connector.properties");
	private static final String ENCRYPT_KEY = "Backlog";
	private static final String ENCODING = "iso-8859-1";
	public static final String SPACE = "space";
	public static final String USER_NAME = "userName";
	public static final String PASSWORD = "password";
	public static final String UPDATE_CHECK = "updateCheck";
	
	public static Map<String, String> load() {
		Map<String, String> ret = new HashMap<String, String>();
		
		FileInputStream fis = null;
    	Properties config = null;
    	try {
			config = new Properties();
			if(!CONFIG_FILE.canRead()) {
				logger.error("Can't load configuration");
				return ret;
			}
			
			fis = new FileInputStream(CONFIG_FILE);
			config.load(fis);
			if (!config.isEmpty()) {
				String space = (String) config.getProperty(SPACE);
				String userName = (String) config.getProperty(USER_NAME);
				String encryptedPassword = config.getProperty(PASSWORD);
				String updateCheck = config.getProperty(UPDATE_CHECK);
				
				ret.put(SPACE, space);
				ret.put(USER_NAME, userName);
				ret.put(PASSWORD, PasswordUtils.decrypt(ENCRYPT_KEY, encryptedPassword.getBytes(ENCODING)));
				ret.put(UPDATE_CHECK, (String) ObjectUtils.defaultIfNull(updateCheck, "true"));
			}
		} catch (Exception e) {
			logger.error("Can't load configuration", e);
		} finally {
			if (fis != null) {
				try { fis.close(); } catch (IOException e) {}
			}
		}
    	
    	return ret;
	}
	
	public static void save(String space, String userName, String password) {
		if (!CONFIG_FILE.exists() && !CONFIG_FILE.getParentFile().mkdirs()) {
			logger.error("Can't save configuration");
			return;
		}
		
		FileOutputStream fis = null;
		Properties config = null;
    	try {
    		fis = new FileOutputStream(CONFIG_FILE);
			config = new Properties();
			config.setProperty(SPACE, space);
			config.setProperty(USER_NAME, userName);
			config.setProperty(PASSWORD, new String(PasswordUtils.encrypt(ENCRYPT_KEY, password), ENCODING));
			config.store(fis, "");
		} catch (Exception e) {
			logger.warn("Can't save configuration", e);
		} finally {
			if (fis != null) {
				try { fis.close(); } catch (IOException e) {}
			}
		}
	}
}
