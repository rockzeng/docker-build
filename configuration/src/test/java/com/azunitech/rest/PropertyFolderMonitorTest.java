package com.azunitech.rest;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;
import java.util.concurrent.FutureTask;

import org.junit.Test;

import com.azunitech.rest.domain.PropertyFolderMonitor;

public class PropertyFolderMonitorTest {
	private static final String url = "c://data";
	private PropertyFolderMonitor monitor;
	private WatchService watchService = null;
	private FutureTask watchTask = null;
	private boolean keepWatching = true;

	@Test
	public void setup() throws IOException{
		watchService = FileSystems.getDefault().newWatchService();	
	}
	
	
	@Test
	public void general() throws IOException{
		monitor = new PropertyFolderMonitor(url);
		monitor.start();
	}
}
