package com.github.astah.connector.backlog.updater;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AutoUpdaterTest {
	private AutoUpdater autoUpdater;
	
	@Before
	public void setUp() throws Exception {
		autoUpdater = new AutoUpdater();
	}

	@After
	public void tearDown() throws Exception {
		autoUpdater = null;
	}

	@Test
	public void versionCompareTo() throws Exception {
		 String aVersion = "1.6.20-SNAPSHOT";
		 String bVersion = "1.6.10";
		 int i = autoUpdater.versionCompareTo(aVersion, bVersion);
		 assertThat(i, is(1));
		
		 aVersion = "1.6.20";
		 bVersion = "1.6.10";
		 i = autoUpdater.versionCompareTo(aVersion, bVersion);
		 assertThat(i, is(1));
		
		 aVersion = "1.6.2";
		 bVersion = "1.6.10";
		 i = autoUpdater.versionCompareTo(aVersion, bVersion);
		 assertThat(i, is(-1));
		 
		
		 aVersion = "0.6.20-SNAPSHOT";
		 bVersion = "0.6.10";
		 i = autoUpdater.versionCompareTo(aVersion, bVersion);
		 assertThat(i, is(1));
		
		 aVersion = "0.6.20";
		 bVersion = "0.6.10";
		 i = autoUpdater.versionCompareTo(aVersion, bVersion);
		 assertThat(i, is(1));
		
		 aVersion = "0.6.2";
		 bVersion = "0.6.10";
		 i = autoUpdater.versionCompareTo(aVersion, bVersion);
		 assertThat(i, is(-1));
		 
		
		 aVersion = "0.6-SNAPSHOT";
		 bVersion = "0.6";
		 i = autoUpdater.versionCompareTo(aVersion, bVersion);
		 assertThat(i, is(0));
		
		 aVersion = "0.6-SNAPSHOT";
		 bVersion = "0.6.10";
		 i = autoUpdater.versionCompareTo(aVersion, bVersion);
		 assertThat(i, is(-1));
		
		 aVersion = "0.6";
		 bVersion = "0.6.10";
		 i = autoUpdater.versionCompareTo(aVersion, bVersion);
		 assertThat(i, is(-1));
	}
}
