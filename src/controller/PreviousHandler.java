package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import ressource.Data;
import ressource.References;

public class PreviousHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		if(References.mediaPlayer != null) {
			if(Data.SONG_QUEUE_POSITION > 0) {
				Data.SONG_QUEUE_POSITION--;
			}
			
			PlayActionHandler ah = new PlayActionHandler();
			ah.playMethod();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION, "Please play a song first", ButtonType.OK);
			alert.show();
		}
		
	}

}
