package com.azunitech.rest;

import java.io.PrintStream;

import org.glassfish.grizzly.http.server.HttpServer;

public abstract class Command {
	private String m_Help;
	protected HttpServer m_HttpServer;
	
	public Command( HttpServer srv){
		this.m_HttpServer = srv;
	}
	
	public String getHelp(){
		return this.m_Help;
	}
	
	public void setHelp( String help){
		this.m_Help = help;
	}
	abstract public void exec(String args, PrintStream out, PrintStream err) throws Exception;
}
