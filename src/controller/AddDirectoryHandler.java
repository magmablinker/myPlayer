package controller;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.TreeItem;
import javafx.stage.DirectoryChooser;
import model.AllowedFileFilter;
import model.FileTreeItem;
import ressource.Data;
import ressource.References;
import util.Util;

public class AddDirectoryHandler implements EventHandler<ActionEvent> {
	
	// TODO: Add progress bar
	@Override
	public void handle(ActionEvent e) {
		DirectoryChooser chooser = new DirectoryChooser();
		File selectedFile = chooser.showDialog(References.stage);

		if (selectedFile != null) {
			
			if(!Util.isAlreadyInTreeView(References.directoryView, selectedFile)) {
				Data.DIRECTORIES.add(selectedFile.getAbsolutePath());
				TreeItem<String> node = new FileTreeItem(selectedFile);
				
				Task<Void> task = new Task<Void>() {

					@Override
					protected Void call() throws Exception {
						References.stage.getScene().setCursor(Cursor.WAIT);
						Util.createDirectoryView(selectedFile.listFiles(new AllowedFileFilter()), node);
						References.directoryView.getRoot().getChildren().add(node);	
						References.stage.getScene().setCursor(Cursor.DEFAULT);
						return null;
					}
					
				};
				
				ExecutorService ex = Executors.newSingleThreadExecutor();
				
				PopupTextBuilder builder = new PopupTextBuilder(References.stage, String.format("Adding the directory '%s', please be patient.", selectedFile.getPath()), 3, "orange");
				
				ex.submit(task);
			} else {
				PopupTextBuilder builder = new PopupTextBuilder(References.stage, "Directory " + selectedFile.getName() + " got already imported.", 3, "red");
			}
			
		}

	}

}
