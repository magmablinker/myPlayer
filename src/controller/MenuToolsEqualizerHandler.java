package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ressource.Data;
import ressource.References;
import view.EqualizerPane;

public class MenuToolsEqualizerHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		if(References.equalizerPaneStage == null) {
			EqualizerPane root = new EqualizerPane();
			Scene scene = new Scene(root, 500, 300);
			Stage stage = new Stage();
			
			References.equalizerPaneStage = stage;
			
			scene.getStylesheets().add(EqualizerPane.class.getResource("css/application.css").toExternalForm());
			
			stage.setTitle("Equalizer");
			stage.getIcons().add(new Image(Data.class.getResourceAsStream("img/equalizer.png")));
			stage.setResizable(false);
			stage.setScene(scene);
			
			// Center the equalizer window on primaryStage
			Bounds mainBounds = References.stage.getScene().getRoot().getLayoutBounds();
			Bounds rootBounds = scene.getRoot().getLayoutBounds();
			
			stage.setX(References.stage.getX() + (mainBounds.getWidth() - rootBounds.getWidth()) / 2);
			stage.setY(References.stage.getY() + (mainBounds.getHeight() - rootBounds.getHeight()) / 2);
			stage.show();
			
			stage.setOnCloseRequest(event -> {
				References.equalizerPaneStage = null;
				stage.close();
			});	
		} else {
			if(References.equalizerPaneStage.isIconified())
				References.equalizerPaneStage.setIconified(false);
			References.equalizerPaneStage.toFront();
		}
		
	}
	
}
