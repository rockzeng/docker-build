
package com.azunitech.rest;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.core.header.MediaTypes;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import junit.framework.TestCase;

public class MainTest extends TestCase {
    private HttpServer httpServer;
    private WebResource r;

    public MainTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        httpServer = Main.startServer();
        Client c = Client.create();
        r = c.resource(Main.BASE_URI);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        httpServer.stop();
    }

    public void testMyResource() {
        String responseMsg = r.path("myresource").get(String.class);
        assertEquals("Got it!", responseMsg);
    }

    public void testApplicationWadl() {
        String serviceWadl = r.path("application.wadl").
                accept(MediaTypes.WADL).get(String.class);
        assertTrue(serviceWadl.length() > 0);
    }
}
