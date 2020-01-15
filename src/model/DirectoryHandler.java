package model;

import java.io.File;
import java.util.Arrays;

import javafx.scene.control.TreeItem;
import ressource.Data;
import ressource.Permissions;

public class DirectoryHandler implements IDataHandler {

	@Override
	public void load(TreeItem<String> root) {
		
		Data.DIRECTORIES.add("C:\\Users\\laurent\\Desktop\\music\\Techno");
		
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

	}
	
	private void createTreeView(TreeItem<String> root, File file) {
		
		if(file.isDirectory()) {
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
