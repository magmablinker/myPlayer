package controller;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.media.EqualizerBand;
import javafx.stage.Stage;
import model.EqualizerPreset;
import ressource.Data;
import ressource.References;
import view.EqualizerPane;
import view.Main;

public class NewPresetHandler implements EventHandler<ActionEvent> {

	private EqualizerPane equalizerPane;
	
	public NewPresetHandler(EqualizerPane equalizerPane) {
		this.equalizerPane = equalizerPane;
	}

	@Override
	public void handle(ActionEvent e) {
		TextInputDialog dialog = new TextInputDialog();
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getStylesheets().add(Main.class.getResource("css/application.css").toExternalForm());
		((Stage) dialogPane.getScene().getWindow()).getIcons().add(new Image(Data.class.getResourceAsStream("img/cheems.png")));
		dialog.setContentText("Preset Name");
		dialog.setHeaderText("Add Preset");
		dialog.showAndWait().ifPresent(text -> {
			
			EqualizerPreset newPreset = new EqualizerPreset(text, "0;0;0;0;0;0;0;0;0;0;");
			equalizerPane.getComboPreset().getItems().add(newPreset);
			equalizerPane.getComboPreset().getSelectionModel().select(newPreset);
			
			ArrayList<EqualizerBand> bands = newPreset.getBands();
			
			Slider[] sliders = equalizerPane.getSliders();
			
			for (int i = 0; i < sliders.length; i++) {
				sliders[i].setValue(bands.get(i).getGain());

				if (References.mediaPlayer != null)
					References.mediaPlayer.getAudioEqualizer().getBands().get(i).setGain(bands.get(i).getGain());

			}
			
		});
	}

}
