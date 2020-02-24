package model;

import java.io.File;
import java.io.Serializable;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ressource.Icons;
import ressource.References;

public class FileTreeItem extends TreeItem<String> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String path;
	private boolean isDirectory;

	public FileTreeItem(File file) {
		super((file.isDirectory()) ? file.getName() : file.getName().substring(0, file.getName().lastIndexOf(".")));
		this.path = file.getAbsolutePath();
		this.isDirectory = file.isDirectory();

		String iconFile;
		if (this.isDirectory) {
			iconFile = Icons.ICON_DIR;
			References.directoryWatchService.registerWatchService(file.toPath(), this);
		} else {
			iconFile = Icons.ICON_FILE;
		}
		
		ImageView icon = new ImageView(new Image(Icons.class.getResourceAsStream(iconFile)));

		if (this.isDirectory()) {
			icon.setFitWidth(28);
			icon.setFitHeight(28);
		} else {
			icon.setFitWidth(16);
			icon.setFitHeight(16);
		}

		this.setGraphic(icon);
		
	}

	public String getPath() {
		return this.path;
	}

	public boolean isDirectory() {
		return this.isDirectory;
	}

	@Override
	public String toString() {
		return this.getPath();
	}

}
