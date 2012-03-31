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

import com.change_vision.jude.api.inf.project.ProjectAccessorFactory;

public class ConfigurationUtils {
	private static final Logger logger = LoggerFactory.getLogger(ConfigurationUtils.class);
	
	private static final String ENCRYPT_KEY = "Backlog";
	private static final String ENCODING = "iso-8859-1";
	public static final String SPACE = "space";
	public static final String USER_NAME = "userName";
	public static final String PASSWORD = "password";
	public static final String UPDATE_CHECK = "updateCheck";
	
	public static String EDITION;
	public static File CONFIG_FILE;
	
	static {
		try {
			EDITION = ProjectAccessorFactory.getProjectAccessor().getAstahEdition();
		} catch (ClassNotFoundException e) {
			EDITION = "professional";
		}
		
		CONFIG_FILE = new File(
				System.getProperty("user.home") + File.separator + ".astah" + File.separator + EDITION,
				"backlog-connector.properties");
	}
	
	public static Map<String, String> load() {
		Map<String, String> ret = new HashMap<String, String>();

    	Properties config = null;
    	try {
    		CONFIG_FILE.createNewFile();
			config = new Properties();
			config.load(new FileInputStream(CONFIG_FILE));
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
			logger.warn("Can't load configuration", e);
		}
    	
    	return ret;
	}
	
	public static void save(String space, String userName, String password) {
    	try {
    		CONFIG_FILE.createNewFile();
    		Properties config = new Properties();
			config.setProperty(SPACE, space);
			config.setProperty(USER_NAME, userName);
			config.setProperty(PASSWORD, new String(PasswordUtils.encrypt(ENCRYPT_KEY, password), ENCODING));
			config.store(new FileOutputStream(CONFIG_FILE), "");
		} catch (Exception e) {
			logger.warn("Can't save configuration", e);
		}
	}
}
