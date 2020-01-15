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
	
	private Thread thread;
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
	
	public void start() {
		if(this.thread == null) {
			this.thread = new Thread(this, "DirectoryWatchService");
			this.thread.run();
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

			System.out.println(dir.toString());
			
			for(WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind<?> kind = event.kind();
				
				if(kind == OVERFLOW) {
					continue;
				}
				
				WatchEvent<Path> ev = (WatchEvent<Path>) event;
				Path fileName = ev.context();
				
				try {
					Path child = dir.resolve(fileName);
					System.out.println("File " + child.toString() + " just changed");
				} catch(Exception e) {
					e.printStackTrace();
				}
				
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
