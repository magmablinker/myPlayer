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

public class MenuToolsVisualizerHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		if(References.visualizerPaneStage == null) {
			Stage stage = new Stage();
			References.visualizerPaneStage = stage;
			
			VisualizerPane root = new VisualizerPane();
			
			Scene scene = new Scene(root, 750, 550);
					
			FadeTransition fadeIn = new FadeTransition(Duration.millis(500), root);
			fadeIn.setFromValue(0.0);
			fadeIn.setToValue(1.0);
			
			stage.setResizable(false);
			
			stage.setOnCloseRequest(event -> {
				if(References.mediaPlayer != null) {
					References.mediaPlayer.setAudioSpectrumListener(null);
				}
				References.visualizerPaneStage = null;
			});
			
			stage.setScene(scene);
			stage.show();
			fadeIn.play();
			
			if(References.mediaPlayer != null) {
				References.mediaPlayer.setAudioSpectrumListener(new SpectrumListener(root));
			}	
		} else {
			if(References.visualizerPaneStage.isIconified())
				References.visualizerPaneStage.setIconified(false);
			References.visualizerPaneStage.toFront();
		}
	}

}
