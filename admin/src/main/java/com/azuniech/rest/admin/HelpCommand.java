package com.azuniech.rest.admin;

import java.io.PrintStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HelpCommand extends Command{
	private final Map<String, Command> m_commands;

	public HelpCommand(Map<String, Command> commands) {
		m_commands = commands;
	}
	
	public void exec(String args, PrintStream out, PrintStream err) throws Exception {
		Set<Entry<String, Command>> set = this.m_commands.entrySet();
		for(Entry<String, Command> entry : set ){
			out.println(entry.getValue().getHelp());
		}
	}
}
