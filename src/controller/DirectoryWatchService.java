package controller;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;

import javafx.scene.control.TreeItem;

public class DirectoryWatchService implements Runnable {
	
	private boolean isRunning = true;
	private WatchService watchService;
	HashMap<WatchKey, Path> directoryMap = new HashMap<WatchKey, Path>();
	HashMap<String, TreeItem<String>> treeItemMap = new HashMap<String, TreeItem<String>>(); 
	
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
					
					// Check if file got deleted, edited or created
					File file = child.toFile();
					String parentDir = file.getParent();
					parentDir = parentDir.substring(parentDir.lastIndexOf(File.separator) + 1, parentDir.length());
					TreeItem<String> nodeChanged = this.treeItemMap.get(parentDir);
					
					switch (kind.toString()) {
					case "ENTRY_DELETE":
						// TODO remove from treeview
						System.out.println("File " + child.toString() + " just got deleted");						
						
						if(nodeChanged != null) {
							int i = 0;
							for(TreeItem<String> node: nodeChanged.getChildren()) {
								if(node.getValue().equals(file.getName())) {
									nodeChanged.getChildren().remove(i);
									break;
								}
								i++;
							}
						}
						
						break;
					case "ENTRY_MODIFY":
						// TODO refresh on treeview
						System.out.println("File " + child.toString() + " just got changed");
						break;
					case "ENTRY_CREATE":
						if(nodeChanged != null) {
							nodeChanged.getChildren().add(new TreeItem<String>(file.getName()));	
						}
						
						break;
					default:
						break;
					}
				} catch(Exception e) {
					e.printStackTrace();
					continue;
				}
				
			}
			
			boolean isValid = key.reset();
			
			if(!isValid) {
				this.setRunning(false);
			}
		}
		
	}

	public void putDirectoryMap(WatchKey key, Path value) {
		this.directoryMap.put(key, value);
	}
	
	public void putTreeViewMap(String key, TreeItem<String> value) {
		this.treeItemMap.put(key, value);
	}
	
	public WatchService getWatcher() {
		return this.watchService;
	}
	
	public void setRunning(boolean bool) {
		this.isRunning = bool;
	}
	
}
