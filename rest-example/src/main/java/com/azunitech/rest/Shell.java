package com.azunitech.rest;

import java.io.*;

import org.apache.log4j.Logger;

public class Shell implements Runnable {
	private static final Logger s_logger = Logger.getLogger(Shell.class);
	private final Command m_command;
	private final BufferedReader m_in;
	private final PrintStream m_out;
	private final PrintStream m_err;

	public Shell(Command command, BufferedReader in, PrintStream out, PrintStream err) {
		m_command = command;
		m_in = in;
		m_out = out;
		m_err = err;
	}

	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			m_out.print("-> ");
			String cmdLine;
			try {
				cmdLine = m_in.readLine();
			} 
			catch (IOException ex) 
			{
				if (!Thread.currentThread().isInterrupted()) {
					ex.printStackTrace(m_err);
					m_err.println("Unable to read from stdin - exiting now");
				}
				return;
			}
			s_logger.info(cmdLine);
			char ctrlB = 0x2;  
	        String controlB = Character.toString(ctrlB);  
	        char ctrlC = 0x3;  
	        String controlC = Character.toString(ctrlC);  
	        if (cmdLine == null || cmdLine.trim().length() == 0 || cmdLine == controlC || cmdLine.startsWith(controlC)) {
				m_out.println("Bye bye");
				break;
			}

			try {
				m_command.exec(cmdLine, m_out, m_err);
			} 
			catch (Throwable t) {
				t.printStackTrace(m_err);
			}
		}
	}
}
