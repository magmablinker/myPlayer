package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.MediaPlayer;
import ressource.References;

public class PreviousHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		if (References.mediaPlayer != null) {
			References.SONG_QUEUE.previous();
			
			// TODO: Find better fix for prev/next on paused
			if(References.mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED))
				References.mediaPlayer = null;

			PlayActionHandler ah = new PlayActionHandler();
			ah.playMethod();
		}
		
	}

}
