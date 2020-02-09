package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ressource.Data;
import ressource.Icons;
import ressource.References;
import util.Util;

public class PreviousHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		if (References.mediaPlayer != null) {
			References.SONG_QUEUE.previous();

			PlayActionHandler ah = new PlayActionHandler();
			ah.playMethod();
		}
		
	}

}
