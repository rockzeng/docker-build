package com.azuniech.rest.admin;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class Server 
{
    public static void main( String[] args ) throws IOException
    {
        String httpPort = System.getProperty("jersey.admin.port");
        if (null == httpPort) {
        	httpPort = "8080";
        }
        
        ServerSocket socket = new ServerSocket( Integer.parseInt(httpPort));
        Map<String, Command>  commands = getExecutableCommand();
    	RestServer s = getRestServer(socket, commands);
    	s.start();
    }
    
    private static Map<String, Command> getExecutableCommand(){
    	Map<String, Command> map = new HashMap<String, Command>();
    	StartCommand start = new StartCommand();
    	start.setHelp("start: start restful server");
    	map.put(start.getClass().getCanonicalName(), start);
    	return map;
    }
    
    private static RestServer getRestServer( ServerSocket socket, Map<String, Command> commands){
    	HelpCommand help = new HelpCommand(commands);
    	RestServer restServer = new RestServer( socket, help);
    	return restServer;
    }
}
