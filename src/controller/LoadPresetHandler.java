package controller;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.media.EqualizerBand;
import model.EqualizerPreset;
import ressource.Data;
import ressource.References;
import view.EqualizerPane;

public class LoadPresetHandler implements EventHandler<ActionEvent> {

	private EqualizerPane equalizerPane;
	
	public LoadPresetHandler(EqualizerPane equalizerPane) {
		this.equalizerPane = equalizerPane;
	}

	@Override
	public void handle(ActionEvent e) {
		if (equalizerPane.getComboPreset().getSelectionModel().getSelectedIndex() > -1) {
			EqualizerPreset selectedPreset = equalizerPane.getComboPreset().getSelectionModel().getSelectedItem();
			
			selectedPreset.loadStringPreset(selectedPreset.getStringPreset());
			
			ArrayList<EqualizerBand> bands = selectedPreset.getBands();
			Data.currentPreset = selectedPreset;

			Slider[] sliders = equalizerPane.getSliders();
			
			for (int i = 0; i < sliders.length; i++) {
				sliders[i].setValue(bands.get(i).getGain());

				if (References.mediaPlayer != null)
					References.mediaPlayer.getAudioEqualizer().getBands().get(i).setGain(bands.get(i).getGain());
			}
		}

	}

}
