package util;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Date;

import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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
			iconFilePath = "img/directory.png";
		} else {
			iconFilePath = "img/file.png";
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
	
	public static void generateSongQueue() {
		TreeView<String> view = References.directoryView;
		
		if(view.getSelectionModel().getSelectedIndex() > -1) {
			TreeItem<String> selectedItem = view.getSelectionModel().getSelectedItem();
			
			if (!References.directoryView.getRoot().equals(selectedItem)) {
				if(selectedItem.getChildren().isEmpty() && !selectedItem.getParent().equals(References.directoryView.getRoot())) {
					selectedItem = selectedItem.getParent();
				}
				
				TreeItem<String> selectedItemSong = view.getSelectionModel().getSelectedItem();
				
				if(Data.SONG_QUEUE.size() > 0) {
					Data.SONG_QUEUE.clear();
				}
				
				int index = 0;
				int finalIndex = 0;
				
				for (TreeItem<String> child : selectedItem.getChildren()) {				
					Data.SONG_QUEUE.add((FileTreeItem) child);
					
					if(child.equals(selectedItemSong)) {
						finalIndex = index;
					} else {
						index++;
					}
				}
				
				References.currentlyPlayingItem = (FileTreeItem) selectedItem;
				
				if (References.checkBoxShuffle.isSelected()) {
					Collections.shuffle(Data.SONG_QUEUE);
					
					for (int i = 0; i < Data.SONG_QUEUE.size(); i++) {
						if(Data.SONG_QUEUE.get(i).equals(selectedItemSong)) {
							Data.SONG_QUEUE.add(0, Data.SONG_QUEUE.get(i));
							Data.SONG_QUEUE.remove(i);
							break;
						}
					}
					
				} else {
					Data.SONG_QUEUE_POSITION = finalIndex;
				}

			}
			
		}
	
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

}
