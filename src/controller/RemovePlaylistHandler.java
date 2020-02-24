package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import ressource.References;

public class RemovePlaylistHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		
		// TODO: STOP PLAYING SONG IF SONG IS PLAYING IN PLAYLIST THAT HAS TO BE REMOVED
		if(References.playlistView.getSelectionModel().getSelectedIndex() > -1) {
			TreeItem<String> item = References.playlistView.getSelectionModel().getSelectedItem();
			
			if(!References.playlistView.getRoot().equals(item)) {
				item.getParent().getChildren().remove(item);
				
				if(References.SONG_QUEUE.getCurrentItem().getParent().equals(item)) {
					if(References.mediaPlayer != null) {
						References.SONG_QUEUE.getPlayActionHandler().reset();
					}
				}
				
			} 
			
		}
		
	}

}
