package util;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ressource.Data;
import ressource.References;
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

		icon.setFitWidth(22);
		icon.setFitHeight(22);
		treeItem.setGraphic(icon);

		return treeItem;
	}

	public static String formatDecimalToMinutes(double val) {
		int hours = (int) val / 3600;
		int remainder = (int) val - hours * 3600;
		int mins = remainder / 60;
		remainder = remainder - mins * 60;
		int secs = remainder;

		return (hours > 0) ? String.format("%d:%d:%02d", hours, mins, secs) : String.format("%d:%02d", mins, secs);
	}
	
	public static TreeItem<String> selectRandomTreeItem(FileTreeItem item) {
		// bad approach
		// TODO: create arraylist with all tracks in them and just shuffle them
		// when the shuffle checkbox gets selected do this asap
		TreeItem<String> parent = item.getParent();
		FileTreeItem oldItem = item;
		item.setPlayed(true);

		int size = parent.getChildren().size();

		int randomIndex = 0;
		while (item.isPlayed()) {
			randomIndex = (int) (Math.random() * size);
			item = (FileTreeItem) parent.getChildren().get(randomIndex);
		}
		
		References.directoryView.getSelectionModel().select(item);
		
		item.setPrevious(oldItem);
		
		return item;
	}

}
