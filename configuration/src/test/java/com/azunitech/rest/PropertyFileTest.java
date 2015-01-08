package com.azunitech.rest;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class PropertyFileTest {
	private static final Logger s_logger = Logger.getLogger(PropertyFileTest.class);
	private Properties prop = new Properties();
	
	@Before
	public void setup(){
		File folder = new File("src/main/resources");
		File[] listOfFiles = folder.listFiles();
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	        System.out.println("File " + listOfFiles[i].getName());
	      } else if (listOfFiles[i].isDirectory()) {
	        System.out.println("Directory " + listOfFiles[i].getName());
	      }
	    }
	}
	
	@Test
	public void general(){
		Enumeration<?> enums = prop.propertyNames();
		while( enums.hasMoreElements()){
			Object obj = enums.nextElement();
			s_logger.info(obj != null? obj.toString(): "null");
		}
	}
}
