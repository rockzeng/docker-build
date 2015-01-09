package com.azuniech.rest.admin;

import java.io.PrintStream;

public abstract class Command {
	private String m_Help;
	public String getHelp(){
		return this.m_Help;
	}
	
	public void setHelp( String help){
		this.m_Help = help;
	}
	abstract public void exec(String args, PrintStream out, PrintStream err) throws Exception;
}
