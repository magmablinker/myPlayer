package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ressource.Data;
import ressource.References;
import sun.applet.Main;
import view.EqualizerPane;

public class MenuSettingsEqualizerHandler implements EventHandler<ActionEvent> {

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
			stage.setX(References.stage.getX() + (References.stage.getHeight() / 4));
			stage.setY(References.stage.getY() + (References.stage.getWidth() / 6));
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
