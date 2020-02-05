package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.MediaPlayer.Status;
import ressource.References;
import util.Util;

public class CheckBoxCheckListener implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		if(!References.mediaPlayer.getStatus().equals(Status.PLAYING)) {
			References.SONG_QUEUE.removePlayingIcon();
		}
		
		if(References.checkBoxShuffle.isSelected()) {
			if(References.SONG_QUEUE != null)
				References.SONG_QUEUE.shuffle();	
		} else {
			References.SONG_QUEUE.generateSongQueue();
		}
		
	}

}
