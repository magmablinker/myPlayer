package view;

import java.io.File;

import javafx.scene.control.TreeItem;

public class FileTreeItem extends TreeItem<String> {

	private String path;
	private boolean isPlayed;
	private FileTreeItem previous = null;
	private FileTreeItem next = null;
	
	public FileTreeItem(File file) {
		// Ternary operator to remove file extension if file is a file
		super((file.isDirectory()) ? file.getName() : file.getName().substring(0, file.getName().lastIndexOf(".")));
		this.path = file.getAbsolutePath();
		this.isPlayed = false;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public boolean isPlayed() {
		return this.isPlayed;
	}
	
	public void setPlayed(boolean bool) {
		this.isPlayed = bool;
	}

	public FileTreeItem getPrevious() {
		return previous;
	}

	public void setPrevious(FileTreeItem previous) {
		this.previous = previous;
	}
	
	public FileTreeItem getNext() {
		return this.next;
	}
	
	public void setNext(FileTreeItem item) {
		this.next = item;
	}
}
