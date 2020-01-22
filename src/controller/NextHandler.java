package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ButtonType;
import ressource.Data;
import ressource.Icons;
import ressource.References;
import util.Util;

public class NextHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		if (References.mediaPlayer != null) {
			Util.removePlayingIcon();
			
			if(Data.SONG_QUEUE_POSITION < (Data.SONG_QUEUE.size() - 1)) {
				Data.SONG_QUEUE_POSITION++;			
			} else {
				Data.SONG_QUEUE_POSITION = 0;
			}
			
			PlayActionHandler ah = new PlayActionHandler();
			ah.playMethod();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION, "Please play a song first", ButtonType.OK);
			alert.show();
		}
	}

}
