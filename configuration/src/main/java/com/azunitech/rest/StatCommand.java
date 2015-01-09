package com.azunitech.rest;

import java.io.PrintStream;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.monitoring.jmx.JmxObject;

public class StatCommand extends Command{
	public StatCommand(HttpServer srv) {
		super(srv);
	}

	public void exec(String line, PrintStream out, PrintStream err) throws Exception {
		JmxObject jmx = this.m_HttpServer.getManagementObject(false);
		ServerConfiguration config = this.m_HttpServer.getServerConfiguration();		
		out.println(config.getHttpServerName());
	}
}
