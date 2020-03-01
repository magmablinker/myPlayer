package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.input.Clipboard;
import ressource.Data;
import ressource.References;
import view.EqualizerPane;

public class PastePresetHandler implements EventHandler<ActionEvent> {

	private EqualizerPane equalizerPane;
	
	public PastePresetHandler(EqualizerPane equalizerPane) {
		this.equalizerPane = equalizerPane;
	}

	@Override
	public void handle(ActionEvent e) {
		String configString = Clipboard.getSystemClipboard().getString();
		
		if(!configString.isEmpty()) {
			try {
				Data.currentPreset.loadStringPreset(configString);
				
				Slider[] sliders = equalizerPane.getSliders();
				
				for(int i = 0; i < 10; i++) {
					Double gain = Data.currentPreset.getBands().get(i).getGain();
					sliders[i].setValue(gain);
					if(References.mediaPlayer != null)
						References.mediaPlayer.getAudioEqualizer().getBands().get(i).setGain(gain);
				}
				
				PopupTextBuilder builder = new PopupTextBuilder(References.equalizerPaneStage, "Successfully loaded config", 2, "green");
			} catch(Exception ex) {
				Data.currentPreset.loadStringPreset(Data.currentPreset.getStringPreset());
				PopupTextBuilder builder = new PopupTextBuilder(References.equalizerPaneStage, "Invalid config string!", 2, "red");
			}	
		}
		
	}

}
