package controller;

import java.io.File;
import java.nio.file.WatchKey;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import model.FileTreeItem;
import ressource.Data;
import ressource.References;

public class RemoveDirectoryHandler implements EventHandler<ActionEvent> {

	private WatchKey itemToRemove = null;
	
	@Override
	public void handle(ActionEvent e) {

		if (References.directoryView.getSelectionModel().getSelectedIndex() > -1) {
			TreeItem<String> selectedItem = References.directoryView.getSelectionModel().getSelectedItem();
			if (!References.directoryView.getRoot().equals(selectedItem)) {
				File file = new File(((FileTreeItem) selectedItem).getPath());
				
				if (file.isDirectory()) {
					
					
					References.directoryWatchService.getDirectoryMap().forEach((key, value) -> {
					    if (value.equals(file.toPath())) {
					    	selectedItem.getParent().getChildren().remove(selectedItem);
					    	
					    	if(Data.DIRECTORIES.contains(file.getAbsolutePath())) {
					    		Data.DIRECTORIES.remove(file.getAbsolutePath());
					    	}
					    	
					    	itemToRemove = key;
					    	
					    	return;
					    }
					});
					
					if(itemToRemove != null)
						References.directoryWatchService.removeWatchKey(itemToRemove);
					
				} else {
					PopupTextBuilder builder = new PopupTextBuilder(References.stage, "Can't remove files!", 2, "red");
				}

			}
			
		}

	}

}
