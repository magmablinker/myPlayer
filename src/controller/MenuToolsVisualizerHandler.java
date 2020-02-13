package controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import ressource.Icons;
import ressource.References;
import view.VisualizerPane;

public class MenuToolsVisualizerHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		if(References.visualizerPaneStage == null) {
			Stage stage = new Stage();
			References.visualizerPaneStage = stage;
			
			VisualizerPane root = new VisualizerPane();
			
			Scene scene = new Scene(root, 850, 550);
					
			FadeTransition fadeIn = new FadeTransition(Duration.millis(500), root);
			fadeIn.setFromValue(0.0);
			fadeIn.setToValue(1.0);
			
			//stage.setResizable(false);
			
			stage.setOnCloseRequest(event -> {
				if(References.mediaPlayer != null) {
					References.mediaPlayer.setAudioSpectrumListener(null);
				}
				References.spectrumListener = null;
				References.visualizerPaneStage = null;
			});
			
			// Center the equalizer window on primaryStage
			Bounds mainBounds = References.stage.getScene().getRoot().getLayoutBounds();
			Bounds rootBounds = scene.getRoot().getLayoutBounds();
			
			stage.setX(References.stage.getX() + (mainBounds.getWidth() - rootBounds.getWidth()) - 60);
			stage.setY(References.stage.getY() + (mainBounds.getHeight() - rootBounds.getHeight()) / - 20);
			stage.setTitle("Visualizer");
			stage.getIcons().add(new Image(Icons.class.getResourceAsStream("img/visualizer-icon.png")));
			stage.setScene(scene);
			stage.show();
			fadeIn.play();
			
			SpectrumListener spectrumListener = new SpectrumListener(root);
			
			if(References.mediaPlayer != null) {
				References.mediaPlayer.setAudioSpectrumListener(spectrumListener);
			} else {
				spectrumListener.setToZero();
			}
			
			References.spectrumListener = spectrumListener;
		} else {
			if(References.visualizerPaneStage.isIconified())
				References.visualizerPaneStage.setIconified(false);
			References.visualizerPaneStage.toFront();
		}
	}

}
