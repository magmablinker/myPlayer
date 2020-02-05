package util;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.FileTreeItem;
import ressource.Data;
import ressource.Icons;
import ressource.Permissions;
import ressource.References;

public class Util {

	public static TreeItem<String> generateTreeNode(File file) {
		FileTreeItem treeItem = new FileTreeItem(file);
		
		String iconFile;
		
		if (file.isDirectory()) {
			iconFile = Icons.ICON_DIR;
			References.directoryWatchService.registerWatchService(file.toPath(), treeItem);
		} else {
			iconFile = Icons.ICON_FILE;
		}
		
		ImageView icon = new ImageView(new Image(Icons.class.getResourceAsStream(iconFile)));

		if(file.isDirectory()) {
			icon.setFitWidth(28);
			icon.setFitHeight(28);	 
		} else {
			icon.setFitWidth(16);
			icon.setFitHeight(16);
		}
		
		treeItem.setGraphic(icon);

		return treeItem;
	}
	
	public static void createDirectoryView(File[] fileList, TreeItem<String> node) {
		for (File file : fileList) {
			Util.createDirectoryTreeView(node, file);
		}
	}
	
	private static void createDirectoryTreeView(TreeItem<String> root, File file) {
		if (file.isDirectory()) {
			TreeItem<String> node = Util.generateTreeNode(file);
			root.getChildren().add(node);

			for (File f : file.listFiles()) {
				createDirectoryTreeView(node, f);
			}
		} else if (Arrays.asList(Permissions.FILETYPES_ALLOWED)
				.contains(file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()))) {
			root.getChildren().add(Util.generateTreeNode(file));
		}
	}

	public static String formatDecimalToMinutes(double val) {
		int hours = (int) val / 3600;
		int remainder = (int) val - hours * 3600;
		int mins = remainder / 60;
		remainder = remainder - mins * 60;
		int secs = remainder;

		return (hours > 0) ? String.format("%d:%d:%02d", hours, mins, secs) : String.format("%d:%02d", mins, secs);
	}
	
	public static boolean checkIfPlaylistOrDirChanged() {
		boolean isChanged = false;
		
		MultipleSelectionModel<TreeItem<String>> sm = References.directoryView.getSelectionModel();
		if(!sm.getSelectedItem().getChildren().isEmpty()) {
			if(!References.directoryView.getRoot().equals(sm.getSelectedItem())) {
				if(!References.currentlyPlayingItem.equals(sm.getSelectedItem())) {
					isChanged = true;
				}
			}
		}
		
		return isChanged;
	}
	
	/*
	public static void removePlayingIcon() {
		
		if(References.SONG_QUEUE.size() > 0) {
			ImageView icon = new ImageView(new Image(Icons.class.getResourceAsStream(Icons.ICON_FILE)));
			icon.setFitWidth(16);
			icon.setFitHeight(16);
			References.SONG_QUEUE.getCurrentItem().setGraphic(icon);
		}
		
	}
	*/

	public static boolean isAlreadyInTreeView(TreeView<String> directoryView, File file) {
		for(TreeItem<String> item : directoryView.getRoot().getChildren()) {
			if(((FileTreeItem) item).getPath().equals(file.getAbsolutePath()))
				return true;
		}
		
		return false;
	}

}
