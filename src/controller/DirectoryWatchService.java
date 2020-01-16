package controller;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
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
import ressource.Data;

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
		while (this.isRunning) {
			WatchKey key;

			try {
				key = this.watchService.take();
			} catch (Exception e) {
				return;
			}

			Path dir = this.directoryMap.get(key);

			for (WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind<?> kind = event.kind();

				if (kind == OVERFLOW) {
					continue;
				}

				WatchEvent<Path> ev = (WatchEvent<Path>) event;
				Path fileName = ev.context();

				try {
					Path child = dir.resolve(fileName);
					this.resolveTreeViewAction(child, kind, key);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

			}

			boolean isValid = key.reset();

			if (!isValid) {
				System.out.println("!!!! Key is invalid !!!!");
				System.out.println(key.toString());
				//this.registerWatchService(dir, this.treeItemMap.get(dir.toFile().getName()));
				//this.setRunning(false);
			}
		}

	}

	private void resolveTreeViewAction(Path child, WatchEvent.Kind<?> kind, WatchKey key) {
		// Check if file got deleted, edited or created
		File file = child.toFile();
		String parentDir = file.getParent();
		TreeItem<String> nodeChanged = this.treeItemMap.get(parentDir.substring(parentDir.lastIndexOf(File.separator) + 1, parentDir.length()));
		
		System.out.println("name: " + file.getName());
		System.out.println("isDirectory: " + file.isDirectory());
		System.out.println("kind: " + kind.toString());
		
		switch (kind.toString()) {
			case "ENTRY_DELETE":
				if(file.isDirectory()) {
					key.cancel();
					System.out.println(file.getName());
				} else {
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
				}	
				
				break;
			case "ENTRY_CREATE":
				if(nodeChanged != null) {
					TreeItem<String> node = new TreeItem<String>(file.getName());
					
					if(file.isDirectory()) {
						this.registerWatchService(child, node);
					}
					
					nodeChanged.getChildren().add(node);	
				}
				
				break;
			default:
				break;
		}
	}

	public void registerWatchService(Path path, TreeItem<String> node) {
		try {
			System.out.println("Registering watchservice for " + path.toString());
			WatchKey key = path.register(this.watchService, ENTRY_CREATE, ENTRY_DELETE);
			this.putDirectoryMap(key, path);
			this.putTreeViewMap(path.toFile().getName(), node);
		} catch (IOException e) {
			e.printStackTrace();
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
