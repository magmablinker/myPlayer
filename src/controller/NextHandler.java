package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.MediaPlayer;
import ressource.References;

public class NextHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		if (References.mediaPlayer != null) {
			References.SONG_QUEUE.next();
			
			if(References.mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)) 
				References.mediaPlayer = null;	
			
			PlayActionHandler ah = new PlayActionHandler();
			ah.playMethod();
		} 
	}

}
