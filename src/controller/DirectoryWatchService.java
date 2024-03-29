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
import java.util.Arrays;
import java.util.HashMap;

import javafx.scene.control.TreeItem;
import model.AllowedFileFilter;
import model.FileTreeItem;
import ressource.Permissions;
import ressource.References;
import util.Util;

public class DirectoryWatchService implements Runnable {

	private boolean isRunning = true;
	private WatchService watchService;
	private HashMap<WatchKey, Path> directoryMap = new HashMap<WatchKey, Path>();
	private HashMap<String, TreeItem<String>> treeItemMap = new HashMap<String, TreeItem<String>>();

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
			
			try {
				// TODO: FIX FUCKING SHIT BUG WHERE NEUER ORDNER GETS ADDED TWICE
				// EDIT: THIS FIXES IT LOL
				Thread.sleep(50);
			} catch (InterruptedException e1) {
			}
			
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

			if (!key.reset()) {
				this.removeWatchKey(key);
			}
		}

	}

	private void resolveTreeViewAction(Path child, WatchEvent.Kind<?> kind, WatchKey key) {
		File file = child.toFile().getAbsoluteFile();
		String parentDir = file.getParent();
		TreeItem<String> nodeChanged = this.treeItemMap.get(parentDir);
		
		/*
		System.out.println("\n*************************");
		System.out.println("name: " + file.getName());
		System.out.println("path: " + file.getAbsolutePath());
		System.out.println("isDirectory: " + file.isDirectory());
		System.out.println("kind: " + kind.toString());
		System.out.println("*************************");
		*/

		// Check if file got deleted or created
		switch (kind.toString()) {
		case "ENTRY_DELETE":
			if (nodeChanged != null) {
				FileTreeItem n = null;
				int i = 0;
				for (TreeItem<String> node : nodeChanged.getChildren()) {
					n = (FileTreeItem) node;
					if (n.getPath().equals(file.getAbsolutePath())) {
						nodeChanged.getChildren().remove(i);
						break;
					}
					i++;
				}
				
				if(References.SONG_QUEUE != null) {
					for (int j = 0; j < References.SONG_QUEUE.size(); j++) {
						if (References.SONG_QUEUE.get(j).equals(n)) {
							References.SONG_QUEUE.remove(j);
							break;
						}
					}					
				}

			}

			this.fileGotDeletedAction(file);
			break;
		case "ENTRY_CREATE":
			if (file.isDirectory() || Arrays.asList(Permissions.FILETYPES_ALLOWED)
					.contains(file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()))) {
				if (nodeChanged != null) {
					TreeItem<String> node = new FileTreeItem(file);
					
					if (!file.isDirectory()) {
						if (nodeChanged.equals(References.currentlyPlayingItem)) {
							References.SONG_QUEUE.add((FileTreeItem) node);
						}
					} else {
						AllowedFileFilter filter = new AllowedFileFilter();
						
						if(file.listFiles(filter).length > 0) {
							Util.createDirectoryView(file.listFiles(filter), node);
						}
						
					}

					nodeChanged.getChildren().add(node);
				}
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
			//System.out.println("Registering watchservice for " + path.toString());
			WatchKey key = path.register(this.watchService, ENTRY_CREATE, ENTRY_DELETE);
			this.putDirectoryMap(key, path);
			this.putTreeViewMap(path.toFile().getAbsolutePath(), node);
		} catch (IOException e) {
			// User doesn't care about this
			//e.printStackTrace();
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
	
	public HashMap<WatchKey, Path> getDirectoryMap() {
		return this.directoryMap;
	}
	
	public void removeWatchKey(WatchKey key) {
		key.cancel();
		this.directoryMap.remove(key);
	}

}
