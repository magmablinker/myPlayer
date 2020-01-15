package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import static java.nio.file.StandardWatchEventKinds.*;
import java.util.Arrays;

import javafx.scene.control.TreeItem;
import ressource.Data;
import ressource.Permissions;

public class DirectoryHandler implements IDataHandler {
	
	private WatchService watcher;

	@Override
	public void load(TreeItem<String> root) {
		
		Data.DIRECTORIES.add("C:\\Users\\laurent\\Desktop\\music\\Techno");
		
		try {
			this.watcher = FileSystems.getDefault().newWatchService();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < Data.DIRECTORIES.size(); i++) {
			File directory = new File(Data.DIRECTORIES.get(i));
			
			if(directory.isDirectory()) {
				TreeItem<String> node = new TreeItem<String>(directory.getName());
				root.getChildren().add(node);
				
				File[] fileList = directory.listFiles();
				
				for (File file : fileList) {
					createTreeView(node, file);
				}
				
			} else if(directory.exists()) {
				root.getChildren().add(new TreeItem<String>(directory.getName()));
			} else {
				root.getChildren().add(new TreeItem<String>("Directory '" + directory.getName() + "' not found"));
			}	
		}
		
		this.startWatchService();

	}
	
	private void startWatchService() {
		WatchKey key;
		
		// https://docs.oracle.com/javase/tutorial/essential/io/notification.html
		for(;;) {
			try {
				key = watcher.take();
			} catch (InterruptedException e) {
				return;
			}
			
			for(WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind<?> kind = event.kind();
				
				if(kind == OVERFLOW) {
					continue;
				}
				
				WatchEvent<Path> ev = (WatchEvent<Path>) event;
				Path fileName = ev.context();
				
				try {
					Path child = dir.resolve(fileName);
				} catch(Exception e) {
					
				}
				
			}
		}
	}

	private void createTreeView(TreeItem<String> root, File file) {
		
		if(file.isDirectory()) {
			Path dir = file.toPath();
			
			try {
				WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
			} catch (Exception e) {
				
			}
			
			TreeItem<String> node = new TreeItem<String>(file.getName());
			root.getChildren().add(node);
			
			for (File f : file.listFiles()) {
				createTreeView(node, f);	
			}
		} else if(Arrays.asList(Permissions.FILETYPES_ALLOWED).contains(file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()))) {
			root.getChildren().add(new TreeItem<String>(file.getName()));
		}
	}

	@Override
	public boolean save() {
		return false;
	}

}
