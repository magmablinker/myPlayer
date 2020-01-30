package model;

import java.io.File;
import java.io.Serializable;

import javafx.scene.control.TreeItem;

public class FileTreeItem extends TreeItem<String> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String path;
	
	public FileTreeItem(File file) {
		// Ternary operator to remove file extension if file is a file
		super((file.isDirectory()) ? file.getName() : file.getName().substring(0, file.getName().lastIndexOf(".")));
		this.path = file.getAbsolutePath();
	}
	
	public String getPath() {
		return this.path;
	}
	
	@Override
	public String toString() {
		return this.getValue() + ":" + this.getPath();
	}
	
}
