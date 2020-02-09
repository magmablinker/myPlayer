package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ressource.References;

public class NextHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		if (References.mediaPlayer != null) {
			References.SONG_QUEUE.next();
			
			PlayActionHandler ah = new PlayActionHandler();
			ah.playMethod();
		} 
	}

}
