package util;

import java.io.File;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ressource.Data;
import view.FileTreeItem;

public class Util {

	public static TreeItem<String> generateTreeNode(File file) {
		FileTreeItem treeItem = new FileTreeItem(file);
		String iconFilePath;

		if (file.isDirectory()) {
			iconFilePath = "../ressource/img/directory.png";
		} else {
			iconFilePath = "../ressource/img/file.png";
		}
		
		ImageView icon = new ImageView(new Image(Data.class.getResourceAsStream(iconFilePath)));
		
		icon.setFitWidth(18);
		icon.setFitHeight(18);
		treeItem.setGraphic(icon);

		return treeItem;
	}

}
