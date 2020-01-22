package controller;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
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
				TreeItem<String> node = Util.generateTreeNode(selectedFile);
				Util.createDirectoryView(selectedFile.listFiles(), node);
				References.directoryView.getRoot().getChildren().add(node);	
			} else {
				Alert alert = new Alert(AlertType.ERROR, "Directory " + selectedFile.getName() + " got already imported.", ButtonType.OK);
				alert.show();
			}
			
		}

	}

}
