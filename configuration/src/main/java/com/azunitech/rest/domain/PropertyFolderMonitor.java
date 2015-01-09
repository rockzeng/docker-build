package com.azunitech.rest.domain;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class PropertyFolderMonitor {
	private static final Logger s_Logger = Logger.getLogger(PropertyFolderMonitor.class);
	private String m_path;
	private CopyOnWriteArrayList<FileDeleted> deletedList = new CopyOnWriteArrayList<FileDeleted>();
	private CopyOnWriteArrayList<FileAdded> addedList = new CopyOnWriteArrayList<FileAdded>();
	private CopyOnWriteArrayList<FileModified> modifiedList = new CopyOnWriteArrayList<FileModified>();
	
	public PropertyFolderMonitor( String path){
		this.m_path = path;
	}
	
	public void start() throws IOException{
		Path path = Paths.get(m_path);
		WatchService watchService = FileSystems.getDefault().newWatchService();
		WatchKey watchKey = path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		boolean notDone = true;
		while(notDone){
		    try{
				watchKey = watchService.poll(60,TimeUnit.SECONDS);
				List<WatchEvent<?>> events = watchKey.pollEvents();
				for(WatchEvent<?> event : events) {
					 Path watchedPath = (Path) watchKey.watchable();
					 Kind<?> eventKind = event.kind();
					 Path target = (Path)event.context();
				
					 FolderEvent fEvent = new FolderEvent();
					 fEvent.fileName = target.getFileName();
					 fEvent.path = watchedPath.toAbsolutePath();
					 
					 switch(eventKind.name()){
				    	 case "ENTRY_CREATE":{
				    		 fireAdded(fEvent);
					     }
						 case "ENTRY_DELETE":{
							 fireDeleted(fEvent);
						 }
						 case "ENTRY_MODIFY":{
							 fireModified(fEvent);
				    	 }
					 }
				}
				if(!watchKey.reset()){
					s_Logger.info("reset");
				}
		    }
		    catch(InterruptedException e){
		    	Thread.currentThread().interrupt();
		    }
		}
	}
	
	private void fireDeleted( FolderEvent event ){
		s_Logger.info("delete " + event);
	}

	private void fireAdded( FolderEvent event ){
		s_Logger.info("Added " + event);
	}

	private void fireModified( FolderEvent event ){
		s_Logger.info("Modified " + event);
	}

	static class FolderEvent{
		Path path;
		Path fileName;
		public String toString(){
			StringBuffer buffer = new StringBuffer();
			buffer.append("path: ").append(path.toString()).append(" ");
			buffer.append("fileName: ").append(fileName.toString());
			return buffer.toString();
		}
	}
	
	static interface FileDeleted{
		boolean process( FolderEvent event);
	}
	
	static interface FileAdded{
		boolean process( FolderEvent event);
	}

	static interface FileModified{
		boolean process( FolderEvent event);
	}
}
