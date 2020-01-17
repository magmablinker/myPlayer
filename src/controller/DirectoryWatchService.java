package controller;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
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
import util.Util;
import view.FileTreeItem;

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

			// Should get checked but I don't care
			key.reset();
			
		}

	}

	private void resolveTreeViewAction(Path child, WatchEvent.Kind<?> kind, WatchKey key) {
		// Check if file got deleted, edited or created
		File file = child.toFile().getAbsoluteFile();
		String parentDir = file.getParent();
		System.out.println(parentDir);
		TreeItem<String> nodeChanged = this.treeItemMap.get(parentDir);
		
		System.out.println("name: " + file.getName());
		System.out.println("isDirectory: " + file.isDirectory());
		System.out.println("kind: " + kind.toString());
		
		switch (kind.toString()) {
			case "ENTRY_DELETE":
				if(file.isDirectory()) {
					key.reset();
				} else {
					if(nodeChanged != null) {
						int i = 0;
						for(TreeItem<String> node: nodeChanged.getChildren()) {
							FileTreeItem n = (FileTreeItem) node;
							if(n.getPath().equals(file.getAbsolutePath())) {
								nodeChanged.getChildren().remove(i);
								break;
							}
							i++;
						}					
					}
				}	
				
				this.fileGotDeletedAction(file);
				break;
			case "ENTRY_CREATE":
				if(nodeChanged != null) {
					TreeItem<String> node = Util.generateTreeNode(file);
					
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

	private void fileGotDeletedAction(File file) {
		this.treeItemMap.remove(file.getAbsolutePath());
	}

	public void registerWatchService(Path path, TreeItem<String> node) {
		try {
			// System.out.println("Registering watchservice for " + path.toString());
			WatchKey key = path.register(this.watchService, ENTRY_CREATE, ENTRY_DELETE);
			this.putDirectoryMap(key, path);
			this.putTreeViewMap(path.toFile().getAbsolutePath(), node);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void putDirectoryMap(WatchKey key, Path value) {
		this.directoryMap.put(key, value);
	}

	private void putTreeViewMap(String key, TreeItem<String> value) {
		this.treeItemMap.put(key, value);
	}

	public WatchService getWatcher() {
		return this.watchService;
	}

	public void setRunning(boolean bool) {
		this.isRunning = bool;
	}

}
