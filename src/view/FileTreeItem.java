package view;

import java.io.File;

import javafx.scene.control.TreeItem;

public class FileTreeItem extends TreeItem<String> {

	private String path;
	
	public FileTreeItem(File file) {
		// Ternary operator to remove file extension if file is a file
		super((file.isDirectory()) ? file.getName() : file.getName().substring(0, file.getName().lastIndexOf(".")));
		this.path = file.getAbsolutePath();
	}
	
	public String getPath() {
		return this.path;
	}
	
}
