package view;

import java.io.File;

import javafx.scene.control.TreeItem;

public class FileTreeItem extends TreeItem<String> {

	private String path;
	
	public FileTreeItem(File file) {
		super(file.getName());
		this.path = file.getAbsolutePath();
	}
	
	public String getPath() {
		return this.path;
	}
	
}
