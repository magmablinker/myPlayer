package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ressource.References;

public class PauseActionHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		if(References.mediaPlayer != null) {
			ImageView imageView = new ImageView(new Image(PlayActionHandler.class.getResourceAsStream("../ressource/img/play.png")));
			imageView.setFitHeight(50);
			imageView.setFitWidth(50);
			
			References.bPlay.setGraphic(imageView);
			References.bPlay.setOnAction(new PlayActionHandler());
			References.mediaPlayer.pause();
		}
			
	}

}
