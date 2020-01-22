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

public class NextHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		if (References.mediaPlayer != null) {
			if(Data.SONG_QUEUE_POSITION < (Data.SONG_QUEUE.size() - 1)) {
				ImageView icon = new ImageView(new Image(Icons.class.getResourceAsStream(Icons.ICON_FILE)));
				icon.setFitWidth(16);
				icon.setFitHeight(16);
				
				Data.SONG_QUEUE.get(Data.SONG_QUEUE_POSITION).setGraphic(icon);
				Data.SONG_QUEUE_POSITION++;
			}

			PlayActionHandler ah = new PlayActionHandler();
			ah.playMethod();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION, "Please play a song first", ButtonType.OK);
			alert.show();
		}
	}

}
