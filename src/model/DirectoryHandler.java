package model;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;

import controller.DirectoryWatchService;
import javafx.scene.control.TreeItem;
import ressource.Data;
import ressource.Permissions;

public class DirectoryHandler implements IDataHandler {
	
	private DirectoryWatchService directoryWatchService;
	private WatchService watchService;
	
	public DirectoryHandler(DirectoryWatchService watchService) {
		super();
		this.directoryWatchService = watchService;
		this.watchService = this.directoryWatchService.getWatcher();
	}
	
	@Override
	public void load(TreeItem<String> root) {
		
		Data.DIRECTORIES.add("D:\\Projekte\\test");
		
		for (int i = 0; i < Data.DIRECTORIES.size(); i++) {
			File directory = new File(Data.DIRECTORIES.get(i));
			
			if(directory.isDirectory()) {
				TreeItem<String> node = new TreeItem<String>(directory.getName());
				System.out.println("Adding node " + directory.getName());
				root.getChildren().add(node);
				
				File[] fileList = directory.listFiles();
				
				for (File file : fileList) {
					System.out.println("Adding " + file.getName());
					createTreeView(node, file);
				}
				
			} else if(directory.exists()) {
				root.getChildren().add(new TreeItem<String>(directory.getName()));
			} else {
				root.getChildren().add(new TreeItem<String>("Directory '" + directory.getName() + "' not found"));
			}	
		}
		
	}
	
	private void createTreeView(TreeItem<String> root, File file) {
		
		if(file.isDirectory()) {
			Path dir = file.toPath();
			
			try {
				WatchKey key = dir.register(watchService, 
										ENTRY_CREATE, 
										ENTRY_DELETE, 
										ENTRY_MODIFY);
				this.directoryWatchService.put(key, dir);
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
