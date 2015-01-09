
package com.azunitech.rest;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;

import javax.ws.rs.core.UriBuilder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
public class Main {
	private static final Logger s_logger = Logger.getLogger(Main.class);
    private static int getPort(int defaultPort) {
        String httpPort = System.getProperty("jersey.server.port");
        if (null != httpPort) {
            try {
                return Integer.parseInt(httpPort);
            } 
            catch (NumberFormatException e) {
            	s_logger.warn(e);
            }
        }
        return defaultPort;
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://0.0.0.0/").port(getPort(9998)).build();
    }

    public static final URI BASE_URI = getBaseURI();
    
    protected static HttpServer startServer() throws IOException {
        ResourceConfig resourceConfig = new PackagesResourceConfig("com.azunitech.rest");

        s_logger.info("Starting grizzly2...");
        return GrizzlyServerFactory.createHttpServer(BASE_URI, resourceConfig);
    }
    
    public static void main(String[] args) throws IOException {
        HttpServer httpServer = startServer();
        String httpPort = System.getProperty("jersey.admin.port");
        if (null == httpPort) {
        	httpPort = "9997";
        }
        
        ServerSocket socket = new ServerSocket( Integer.parseInt(httpPort));
        Map<String, Command>  commands = getExecutableCommand(httpServer);
        HelpCommand help = new HelpCommand(httpServer);
        help.setCommand(commands);
    	RestServer s = getRestServer(socket, help);
    	s.start();
    }  
    
    private static Map<String, Command> getExecutableCommand(HttpServer svr){
    	Map<String, Command> map = new HashMap<String, Command>();
    	StopCommand stop = new StopCommand(svr);
    	stop.setHelp("stop -- stop restful server");
    	map.put("stop", stop);
    	
    	StatCommand stat = new StatCommand(svr);
    	stat.setHelp("stat -- the restful server stat");
    	map.put("stat", stat);
    	return map;
    }
    
    private static RestServer getRestServer( ServerSocket socket, HelpCommand help){
    	RestServer restServer = new RestServer( socket, help);
    	return restServer;
    }
}
