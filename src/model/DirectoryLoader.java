package model;

import java.io.File;

import javafx.scene.control.TreeItem;

public class DirectoryLoader implements IDataHandler {

	@Override
	public void load(TreeItem<String> root) {
		String[] directories = { "D:\\sample", "Z:\\musik" };
		
		for (String dir : directories) {
			File directory = new File(dir);
			
			if(directory.isDirectory()) {
				TreeItem<String> node = new TreeItem<String>(directory.getName());
				root.getChildren().add(node);
				
				File[] fileList = directory.listFiles();
				
				for (File file : fileList) {
					createTreeView(node, file);
				}
				
			} else {
				root.getChildren().add(new TreeItem<String>(directory.getName()));
			}
				
		}
	
	}
	
	private void createTreeView(TreeItem<String> root, File file) {
		
		if(file.isDirectory()) {
			TreeItem<String> node = new TreeItem<String>(file.getName());
			root.getChildren().add(node);
			
			for (File f : file.listFiles()) {
				createTreeView(node, f);	
			}
		} else {
			root.getChildren().add(new TreeItem<String>(file.getName()));
		}
		
	}

	@Override
	public boolean save() {
		return false;
	}

}
