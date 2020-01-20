package controller;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import ressource.Data;
import ressource.References;
import util.Util;

public class AddDirectoryHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		DirectoryChooser chooser = new DirectoryChooser();
		File selectedFile = chooser.showDialog(References.stage);

		if (selectedFile != null) {
			Data.DIRECTORIES.add(selectedFile.getAbsolutePath());
			TreeItem<String> node = Util.generateTreeNode(selectedFile);
			Util.createDirectoryView(selectedFile.listFiles(), node);
			References.directoryView.getRoot().getChildren().add(node);
		}

	}

}
