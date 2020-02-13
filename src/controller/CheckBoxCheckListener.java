package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.MediaPlayer.Status;
import ressource.References;

public class CheckBoxCheckListener implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		// TODO: Fix bug where playing icon stays when box gets unchecked and skipped to next song
		// EDIT: THINK I FIXED IT????
		if(References.mediaPlayer != null) {
			if(!References.mediaPlayer.getStatus().equals(Status.PLAYING)) {
				References.SONG_QUEUE.removePlayingIcon();
			}	
		}
		
		if(References.SONG_QUEUE != null) {
			if(References.checkBoxShuffle.isSelected()) {
				References.SONG_QUEUE.shuffle();	
			} else {
				References.SONG_QUEUE.generateSongQueue();
			}			
		}
		
	}

}
