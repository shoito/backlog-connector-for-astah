package com.github.astah.connector.backlog;


import java.io.IOException;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.astah.connector.backlog.updater.AutoUpdater;
import com.github.astah.connector.backlog.util.ConfigurationUtils;

public class Activator implements BundleActivator {
	private static final Logger logger = LoggerFactory.getLogger(Activator.class);
	
	public void start(BundleContext context) {
		Map<String, String> config = ConfigurationUtils.load();
		String updateCheckStr = config.get(ConfigurationUtils.UPDATE_CHECK);
		logger.info("Are there newer versions available? " + updateCheckStr);
		
		if ("false".equalsIgnoreCase(updateCheckStr)) {
			return;
		}
		
		runAutoUpdater();
	}

	public void stop(BundleContext context) {
	}
	
	private void runAutoUpdater() {
		new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(300000);
				} catch (InterruptedException e) {
					logger.warn(e.getMessage(), e);
				}
				
				AutoUpdater autoUpdater = new AutoUpdater();
				try {
					autoUpdater.check();
				} catch (IOException e) {
					logger.warn(e.getMessage(), e);
				}
			}
		};
	}
}
