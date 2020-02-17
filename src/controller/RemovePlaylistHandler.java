package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import ressource.References;

public class RemovePlaylistHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		
		if(References.playlistView.getSelectionModel().getSelectedIndex() > -1) {
			TreeItem<String> item = References.playlistView.getSelectionModel().getSelectedItem();
			
			if(!References.playlistView.getRoot().equals(item)) {
				item.getParent().getChildren().remove(item);
			} 
			
		}
		
	}

}
