package com.azuniech.rest.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RestServer implements Runnable{
	private final ServerSocket m_socket;
	private int m_maxConnections = 2;
	private final Thread m_thread = new Thread(this);	
	private final Command m_command;
	private final List<StoppableShellThread> m_threads = new ArrayList<StoppableShellThread>();
	 
	public RestServer(ServerSocket socket, Command command) {
		m_socket = socket;
		m_command = command;
	}	
	
	public void run() {
		while (!Thread.interrupted()) {
			final Socket socket;
			try {
				socket = m_socket.accept();
			} 
			catch (IOException ex) {
				if (Thread.interrupted()) {
					break;
	        }
	        throw new RuntimeException(ex);
	      }

	      synchronized (m_threads) {
	        if (m_threads.size() >= m_maxConnections) {
	          try {
	            socket.close();
	          } catch (IOException e) {
	            // Ignore
	          }
	          continue;
	        }
	      }

	      Shell shell;

	      try {
	        shell = new Shell(m_command, 
	        		new BufferedReader(new InputStreamReader(socket.getInputStream())),
	        		new PrintStream(socket.getOutputStream()), new PrintStream(socket.getOutputStream()));
	      } catch (IOException e) {
	        e.printStackTrace();
	        try {
	          socket.close();
	        } catch (IOException ex) {
	          // Ignore
	        }
	        continue;
	      }

	      StoppableShellThread thread = new StoppableShellThread(shell, socket);

	      thread.start();
	    }

	    List<StoppableShellThread> threads;

	    synchronized (m_threads) {
	      threads = new ArrayList<StoppableShellThread>(m_threads);
	    }

	    for (StoppableShellThread thread : threads) {
	      try {
	        thread.stopAndWait();
	      } catch (InterruptedException e) {
	        // Ignore
	      }
	    }
	    Thread.currentThread().interrupt();
	  }

	  public void start() {
	    m_thread.start();
	  }

	  public void stop() throws InterruptedException {
	    m_thread.interrupt();
	    try {
	      m_socket.close();
	    } catch (IOException e) {
	      // Ignore
	    }
	    m_thread.join();
	  }
	  private final class StoppableShellThread extends Thread {
		    private final Socket m_socket;

		    public StoppableShellThread(Shell shell, Socket socket) {
		      super(shell);
		      m_socket = socket;
		    }

		    public void start() {
		      synchronized (m_threads) {
		        m_threads.add(this);
		      }

		      super.start();
		    }

		    void stopAndWait() throws InterruptedException {
		      if (!isAlive()) {
		        return;
		      }
		      interrupt();
		      try {
		        m_socket.close();
		      } catch (IOException e) {
		        // Ignore
		      }
		      join();
		    }

		    public void run() {
		      try {
		        super.run();
		      } finally {
		        synchronized (m_threads) {
		          m_threads.remove(this);
		        }
		      }
		    }
		  }	  
}
