package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ressource.Icons;
import ressource.References;
import util.Util;

public class PauseActionHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		if(References.mediaPlayer != null) {
			References.SONG_QUEUE.removePlayingIcon();
			ImageView imageView = new ImageView(new Image(Icons.class.getResourceAsStream(Icons.ICON_PLAY)));
			imageView.setFitHeight(50);
			imageView.setFitWidth(50);
			
			References.bPlay.setGraphic(imageView);
			References.bPlay.setOnAction(new PlayActionHandler());
			References.mediaPlayer.pause();
		}
			
	}

}
