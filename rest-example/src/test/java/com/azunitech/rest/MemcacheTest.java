package com.azunitech.rest;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.CASMutation;
import net.spy.memcached.CASMutator;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.Transcoder;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MemcacheTest {
	private Logger s_Logger = Logger.getLogger(MemcacheTest.class);
	private MemcachedClient m_Client;
	
	@Before
	public void setup() throws IOException{
		m_Client = new MemcachedClient(AddrUtil.getAddresses(
				"192.168.0.78:11211 192.168.0.78:11222 192.168.0.78:11233 192.168.0.78:11244"));
	}
	
	@After
	public void tearDown(){
		this.m_Client.shutdown();
	}

	
	@Test
	public void syncSetAndGet() throws Exception{
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		map.put("test", "testv");
		m_Client.set("someKey",3600, map);
		// Retrieve a value (synchronously).
		Object myObject= m_Client.get("someKey");
		s_Logger.info(myObject);
	}
	
	@Test
	public void syncSetAndGetWithTTL() throws Exception{
		m_Client.set("someKey", 10, "someObject");
		// Retrieve a value (synchronously).
		Object myObject= m_Client.get("someKey");
		s_Logger.info(myObject);
	}
	
	@Test
	public void AsyncSetAndGet() throws InterruptedException, ExecutionException{
		m_Client.set("someKey", 10, "someObject");
		// Try to get a value, for up to 5 seconds, and cancel if it doesn't return
		Object myObj=null;
		Future<Object> f = m_Client.asyncGet("someKey");
		try {
		    myObj=f.get(5, TimeUnit.SECONDS);
		    s_Logger.info(myObj);
		} 
		catch(TimeoutException e) {
		    // Since we don't need this, go ahead and cancel the operation.  This
		    // is not strictly necessary, but it'll save some work on the server.
		    f.cancel(false);
		    // Do other timeout related stuff
		}		
	}
}
