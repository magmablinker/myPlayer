package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.Alert.AlertType;
import ressource.References;

public class PreviousHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		// TODO: fix bug where previous gets null pointer
		// if next was last treeitem
		if(References.mediaPlayer != null) {
			TreeItem<String> item;
			if(References.checkBoxShuffle.isSelected()) {
				if(References.currentlyPlayingItem != null) {
					item = References.currentlyPlayingItem.getPrevious();
				} else {
					if(References.directoryView.getSelectionModel().getSelectedItem().getChildren().size() < 1) {
						item = References.directoryView.getSelectionModel().getSelectedItem().previousSibling();
					} else {
						item = References.directoryView.getSelectionModel().getSelectedItem().getChildren().get(References.directoryView.getSelectionModel().getSelectedItem().getChildren().size() - 1);
					}
				}
			} else {
				if(References.directoryView.getSelectionModel().getSelectedItem().getChildren().size() < 1) {
					item = References.directoryView.getSelectionModel().getSelectedItem().previousSibling();
				} else {
					item = References.directoryView.getSelectionModel().getSelectedItem().getChildren().get(References.directoryView.getSelectionModel().getSelectedItem().getChildren().size() - 1);
				}
			}
			
			References.directoryView.getSelectionModel().select(item);
			
			PlayActionHandler ah = new PlayActionHandler();
			ah.playMethod();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION, "Please play a song first", ButtonType.OK);
			alert.show();
		}
		
	}

}
