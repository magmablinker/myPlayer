package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ressource.References;

public class PauseActionHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		if(References.mediaPlayer != null)
			References.mediaPlayer.pause();
	}

}
