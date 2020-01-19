package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import ressource.Data;
import ressource.References;
import util.Util;
import view.FileTreeItem;

public class NextHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		if (References.mediaPlayer != null) {
			if(Data.SONG_QUEUE_POSITION < (Data.SONG_QUEUE.size() - 1)) {
				Data.SONG_QUEUE_POSITION++;
				System.out.println(Data.SONG_QUEUE_POSITION);
			}

			PlayActionHandler ah = new PlayActionHandler();
			ah.playMethod();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION, "Please play a song first", ButtonType.OK);
			alert.show();
		}
	}

}
