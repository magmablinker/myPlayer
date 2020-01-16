package controller;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;

public class DirectoryWatchService implements Runnable {
	
	private boolean isRunning = true;
	private WatchService watchService;
	HashMap<WatchKey, Path> directoryMap = new HashMap<WatchKey, Path>();
	
	public DirectoryWatchService() {
		super();
		
		try {
			this.watchService = FileSystems.getDefault().newWatchService();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		while(this.isRunning) {	
			WatchKey key;
						
			try {
				key = this.watchService.take();
			} catch (Exception e) {
				return;
			}
			
			Path dir = this.directoryMap.get(key);
			
			for(WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind<?> kind = event.kind();
				
				if(kind == OVERFLOW) {
					continue;
				}
				
				WatchEvent<Path> ev = (WatchEvent<Path>) event;
				Path fileName = ev.context();
				
				try {
					Path child = dir.resolve(fileName);
					
					switch (kind.toString()) {
					case "ENTRY_DELETE":
						System.out.println("File " + child.toString() + " just got deleted");
						break;
					case "ENTRY_MODIFY":
						System.out.println("File " + child.toString() + " just got changed");
						break;
					case "ENTRY_CREATE":
						System.out.println("File " + child.toString() + " just got created");
						break;
					default:
						break;
					}
				} catch(Exception e) {
					continue;
				}
				
			}
			
			boolean isValid = key.reset();
			
			if(!isValid) {
				this.setRunning(false);
			}
		}
		
	}

	public void put(WatchKey key, Path value) {
		this.directoryMap.put(key, value);
	}
	
	public WatchService getWatcher() {
		return this.watchService;
	}
	
	public void setRunning(boolean bool) {
		this.isRunning = bool;
	}
	
}
