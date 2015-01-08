
package com.azunitech.rest;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.azunitech.rest.domain.Result;
import com.sun.jersey.core.header.MediaTypes;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class MainTest {
	private static final Logger s_logger = Logger.getLogger(MainTest.class);
    private HttpServer httpServer;
    private WebResource r;

    @Before
    public void setUp() throws IOException {
        httpServer = Main.startServer();
        Client c = Client.create();
        r = c.resource(getBaseURI());
    }

    @After
    public void tearDown() {
        httpServer.stop();
    }

    @Test
    public void testMyResource() {
    	Result result = r.path("/v1/key").path("memcached").get(Result.class);
    	s_logger.info(result);
    }

    public void testApplicationWadl() {
        String serviceWadl = r.path("application.wadl").accept(MediaTypes.WADL).get(String.class);
        Assert.assertTrue(serviceWadl.length() > 0);
    }
    
    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://192.168.56.101/").port(9998).build();
    }
}
