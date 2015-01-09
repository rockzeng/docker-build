package com.azunitech.rest;

import java.io.PrintStream;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

public class StopCommand extends Command{
	
	public StopCommand(HttpServer srv) {
		super(srv);
	}

	public void exec(String line, PrintStream out, PrintStream err) throws Exception {
		this.m_HttpServer.stop();
		System.exit(0);
	}
}
