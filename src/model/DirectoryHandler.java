package model;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

import controller.DirectoryWatchService;
import javafx.scene.control.TreeItem;
import ressource.Data;
import ressource.Permissions;

public class DirectoryHandler implements IDataHandler {
	
	private DirectoryWatchService directoryWatchService;
	
	public DirectoryHandler(DirectoryWatchService watchService) {
		super();
		this.directoryWatchService = watchService;
	}
	
	@Override
	public void load(TreeItem<String> root) {
		
		//Data.DIRECTORIES.add("D:\\Projekte\\test");
		Data.DIRECTORIES.add("C:\\Users\\laurent\\Desktop\\music");
		
		for (int i = 0; i < Data.DIRECTORIES.size(); i++) {
			File directory = new File(Data.DIRECTORIES.get(i));
			
			if(directory.isDirectory()) {
				TreeItem<String> node = new TreeItem<String>(directory.getName());
				root.getChildren().add(node);
				
				File[] fileList = directory.listFiles();
				
				// TODO fix weird bug with empty directory appearing as parent???
				// WASNT REPRODUCABLE???
				for (File file : fileList) {
					createTreeView(node, file);
				}
				
				this.directoryWatchService.registerWatchService(directory.toPath(), node);
			} else {
				root.getChildren().add(new TreeItem<String>("Directory '" + directory.getName() + "' not found"));
			}	
		}
		
	}
	
	private void createTreeView(TreeItem<String> root, File file) {
		
		if(file.isDirectory()) {
			Path dir = file.toPath();
			
			/*
			try {
				System.out.println("Registering directory " + file.getName());
				WatchKey key = dir.register(watchService, 
										ENTRY_CREATE, 
										ENTRY_DELETE, 
										ENTRY_MODIFY);
				this.directoryWatchService.putDirectoryMap(key, dir);
			} catch (Exception e) {
				
			}
			*/
			
			TreeItem<String> node = new TreeItem<String>(file.getName());
			root.getChildren().add(node);
			
			this.directoryWatchService.registerWatchService(dir, node);
			
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
