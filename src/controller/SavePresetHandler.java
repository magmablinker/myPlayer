package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.EqualizerDataHandler;
import model.EqualizerPreset;
import ressource.References;
import view.EqualizerPane;

public class SavePresetHandler implements EventHandler<ActionEvent> {

	private EqualizerPane equalizerPane;
	
	public SavePresetHandler(EqualizerPane equalizerPane) {
		this.equalizerPane = equalizerPane;
	}

	@Override
	public void handle(ActionEvent e) {
		if (equalizerPane.getComboPreset().getSelectionModel().getSelectedIndex() > -1) {
			EqualizerPreset selectedPreset = equalizerPane.getComboPreset().getSelectionModel().getSelectedItem();
			selectedPreset.setPreset(equalizerPane.getPresetString());
			
			EqualizerDataHandler edh = new EqualizerDataHandler(equalizerPane.getComboPreset());
			edh.save();
			
			PopupTextBuilder builder = new PopupTextBuilder(References.equalizerPaneStage, "Config has been saved", 2, "green");
		}
	}
	
}
