package com.azunitech.rest;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.glassfish.grizzly.http.server.HttpServer;

public class HelpCommand extends Command{
	private Map<String, Command> m_commands;
	
	public HelpCommand(HttpServer srv) {
		super(srv);
		m_commands = new HashMap<String, Command>();
	}
	
	public void setCommand(Map<String, Command> commands) {
		m_commands = commands;
	}
	
	public void exec(String args, PrintStream out, PrintStream err) throws Exception {
		Command cmd = this.m_commands.get(args);
		if ( cmd == null){
			Set<Entry<String, Command>> set = this.m_commands.entrySet();
			for(Entry<String, Command> entry : set ){
				out.println(entry.getValue().getHelp());
			}
		}
		else {
			cmd.exec(args, out, err);
		}
	}
}
