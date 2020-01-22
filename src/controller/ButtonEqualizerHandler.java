package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ressource.References;
import view.EqualizerPane;

public class ButtonEqualizerHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		EqualizerPane root = new EqualizerPane();
		Scene scene = new Scene(root, 400, 600);
		Stage stage = new Stage();
		
		stage.setTitle("Equalizer");
		stage.setScene(scene);
		stage.show();
	}
	
}
