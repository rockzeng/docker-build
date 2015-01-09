package com.azunitech.rest;

import static org.junit.Assert.*;

import javax.ws.rs.core.MultivaluedMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.header.MediaTypes;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;
import com.azunitech.rest.Main;
import com.azunitech.rest.domain.Inventory;
import com.azunitech.rest.repos.CollectionAdapter;
import com.azunitech.rest.repos.IDataAdapter;
import com.azunitech.rest.repos.IRepository;
import com.azunitech.rest.repos.InventoryMemcachedAdapter;
import com.azunitech.rest.repos.InventoryRepository;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;

public class InventoryServiceTest {
	private static Logger s_Logger = Logger.getLogger(InventoryServiceTest.class);
    private HttpServer httpServer;
    private WebResource r;

    @Before
    public void setUp() throws Exception {
    	Injector injector = Guice.createInjector( new ServletModule() {
    		@Override
    		protected void configureServlets() {
    			bind(IRepository.class).to(InventoryRepository.class).asEagerSingleton();
    			bind(IDataAdapter.class).annotatedWith(Names.named("memcached")).to(InventoryMemcachedAdapter.class).asEagerSingleton();
    			bind(IDataAdapter.class).annotatedWith(Names.named("collection")).to(CollectionAdapter.class).asEagerSingleton();
    		}
		});
    	
		ResourceConfig rc = new PackagesResourceConfig( "com.azunitech.rest" );
		IoCComponentProviderFactory ioc = new GuiceComponentProviderFactory( rc, injector );
		httpServer = GrizzlyServerFactory.createHttpServer( Main.BASE_URI, rc, ioc );    	
        Client c = Client.create();
        r = c.resource(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        httpServer.stop();
    }
    
    @Test
    public void getInventory() {
    	createInventory();
    	Inventory item = r.path("/api/inventory").path("100").get(Inventory.class);
        s_Logger.info(item);
    }


    @Test
    public void createInventory() {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
        formData.add("id", "100");
        formData.add("name", "Disk Door");
        formData.add("price", "100");
        formData.add("description", "description");
        String xml = r.path("/api/inventory").
        post(String.class, formData);
        s_Logger.info(xml);
    }
    
    @Test
    public void updataProduct(){
    	String name = RandomStringUtils.random(5, "abcdefgh");
    	String description = RandomStringUtils.random(5, "abcdefgh");
    	float price = RandomUtils.nextFloat(10, 1000);
      	Inventory item = r.path("/api/inventory").path("100").path("name").path("Disk Door").path("price").path( String.valueOf(price)).path("description").path(description).put(Inventory.class);
    }

    @Test
    public void testApplicationWadl() {
        String serviceWadl = r.path("application.wadl").accept(MediaTypes.WADL).get(String.class);
        assertTrue(serviceWadl.length() > 0);
    }
}
