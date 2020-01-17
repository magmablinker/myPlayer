package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import ressource.References;
import util.Util;
import view.FileTreeItem;

public class NextHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		if (References.mediaPlayer != null) {
			TreeItem<String> item;
			if (References.directoryView.getSelectionModel().getSelectedItem().getChildren().size() < 1) {
				item = References.directoryView.getSelectionModel().getSelectedItem().nextSibling();
				if (!References.checkBoxShuffle.isSelected()) {
					References.directoryView.getSelectionModel().select(item);
				} else {
					System.out.println("Shuffle");
					Util.selectRandomTreeItem((FileTreeItem) item);
				}
			} else {
				item = References.directoryView.getSelectionModel().getSelectedItem().getChildren().get(1);
				if (!References.checkBoxShuffle.isSelected()) {
					References.directoryView.getSelectionModel().select(item);
				} else {
					Util.selectRandomTreeItem((FileTreeItem) item);
				}
			}

			PlayActionHandler ah = new PlayActionHandler();
			ah.playMethod();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION, "Please play a song first", ButtonType.OK);
			alert.show();
		}
	}

}
