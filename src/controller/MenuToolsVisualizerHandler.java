package controller;

import javafx.animation.FadeTransition;
import javafx.animation.Animation.Status;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
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
			
			Scene scene = new Scene(root, 1000, 550);
					
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
				
				if(References.mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED))
					spectrumListener.setToZero();
				
			} else {
				spectrumListener.setToZero();
			}
			
			References.spectrumListener = spectrumListener;
			
			scene.heightProperty().addListener(ev -> {
				root.getCanvas().setHeight(scene.getHeight());
				
				if(References.mediaPlayer == null)
					References.spectrumListener.setToZero();
				
			});
			
			scene.widthProperty().addListener(ev -> {
				root.getCanvas().setWidth(scene.getWidth() - 100);
				
				if(References.mediaPlayer == null)
					References.spectrumListener.setToZero();
			});
		} else {
			if(References.visualizerPaneStage.isIconified())
				References.visualizerPaneStage.setIconified(false);
			References.visualizerPaneStage.toFront();
		}
	}

}
