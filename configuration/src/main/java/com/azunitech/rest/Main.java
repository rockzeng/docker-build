
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Main {
	private static final Logger s_logger = Logger.getLogger(Main.class);
    private static int getPort(int defaultPort) {
        String httpPort = System.getProperty(REST.JERSEY_SERVER_PORT);
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
        HttpServer httpServer = GrizzlyServerFactory.createHttpServer(BASE_URI, resourceConfig);
        httpServer.getServerConfiguration().setJmxEnabled(true); 
        return httpServer;
    }
    
    public static void main(String[] args) throws IOException {
    	s_logger.debug(args);
    	printEnvironmentVariable();
    	HttpServer httpServer = startServer();
        	
        String httpPort = System.getProperty(REST.JERSEY_ADMIN_PORT);
        if (null == httpPort) {
        	httpPort = "9997";
        }
        
        //Admin Server socker
        ServerSocket socket = new ServerSocket( Integer.parseInt(httpPort));
        Map<String, Command>  commands = getExecutableCommand(httpServer);
        HelpCommand help = new HelpCommand(httpServer);
        help.setCommand(commands);
    	RestServer s = getRestServer(socket, help);
    	s.start();
    }  
    
    private static void printEnvironmentVariable(){
    	String[] set = new String[]{REST.JERSEY_ADMIN_PORT, REST.JERSEY_SERVER_PORT};
    	List<String> setList = new ArrayList<String>(Arrays.asList(set)); 
    	Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
        	if ( setList.contains(envName)){
        		System.setProperty(envName, env.get(envName));
        		s_logger.info(String.format("%s=%s%n", envName, env.get(envName)));
        	}
        }
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
