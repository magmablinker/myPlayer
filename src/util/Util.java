package util;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ressource.Data;

public class Util {

	public static TreeItem<String> generateTreeNode(String value, boolean isDirectory) {
		TreeItem<String> treeItem = new TreeItem<String>(value);
		String iconFilePath;

		if (isDirectory) {
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
