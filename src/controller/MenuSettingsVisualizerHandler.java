package controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ressource.References;
import sun.applet.Main;
import view.VisualizerPane;

public class MenuSettingsVisualizerHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		Stage stage = new Stage();
		
		VisualizerPane root = new VisualizerPane();
		
		Scene scene = new Scene(root);
				
		FadeTransition fadeIn = new FadeTransition(Duration.millis(250), root);
		fadeIn.setFromValue(0.0);
		fadeIn.setToValue(1.0);
		
		stage.setWidth(References.stage.getWidth());
		stage.setHeight(References.stage.getHeight());
		
		stage.setOnCloseRequest(event -> {
			if(References.mediaPlayer != null) {
				References.mediaPlayer.setAudioSpectrumListener(null);
			}
		});
		
		stage.setScene(scene);
		stage.show();
		fadeIn.play();
		
		if(References.mediaPlayer != null) {
			References.mediaPlayer.setAudioSpectrumListener(new SpektrumListener(root));
		}
	}

}
