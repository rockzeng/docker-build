package com.azunitech.rest;

import java.io.PrintStream;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.http.server.jmx.JmxEventListener;
import org.glassfish.grizzly.monitoring.jmx.JmxObject;

public class StatCommand extends Command{
	public StatCommand(HttpServer srv) {
		super(srv);
	}

	public void exec(String line, PrintStream out, PrintStream err) throws Exception {
		JmxObject jmx = this.m_HttpServer.getManagementObject(false);
		if ( jmx instanceof org.glassfish.grizzly.http.server.jmx.HttpServer){
			org.glassfish.grizzly.http.server.jmx.HttpServer s = (org.glassfish.grizzly.http.server.jmx.HttpServer)jmx;
		}
		
		out.println(jmx.getJmxName() + " " + jmx.toString());
		ServerConfiguration config = this.m_HttpServer.getServerConfiguration();
		out.println("isJmxEnabled: " + config.isJmxEnabled());
		out.println(config.getHttpServerName());
		out.println("isTraceEnabled:" + config.isTraceEnabled());
	}
}
