package com.azunitech.rest.domain;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class MemcachedConfig {
	private static final Logger s_logger = Logger.getLogger(MemcachedConfig.class);
	private static final String PROP_FILE_NAME = "memcached.properties";
	private static final String PARAM_URL = "MEMCACHED.SERVER.URL";
	private static final String PARAM_PORTS = "MEMCACHED.SERVER.PORTS";
	private Properties prop = new Properties();

	public String getUrl() throws IOException{
	    prop.load(this.getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME));
		String url = prop.getProperty("MEMCACHED.SERVER.URL");
		String ports = prop.getProperty("MEMCACHED.SERVER.PORTS");
		
		String[] portList = ports.split(" ");
		StringBuffer buffer = new StringBuffer();
		for( String port : portList){
			buffer.append(url).append(":").append(port).append(" ");
		}
		
		s_logger.info(buffer.toString());
		return buffer.toString();
	}
	
}
