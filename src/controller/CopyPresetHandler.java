package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import ressource.References;
import view.EqualizerPane;

public class CopyPresetHandler implements EventHandler<ActionEvent> {

	private EqualizerPane equalizerPane;
	
	public CopyPresetHandler(EqualizerPane equalizerPane) {
		this.equalizerPane = equalizerPane;
	}

	@Override
	public void handle(ActionEvent e) {
		if(equalizerPane.getComboPreset().getSelectionModel().getSelectedIndex() > -1) {
			Clipboard clipboard = Clipboard.getSystemClipboard();
			ClipboardContent content = new ClipboardContent();
			content.putString(equalizerPane.getPresetString());
			clipboard.setContent(content);
			
			PopupTextBuilder builder = new PopupTextBuilder(References.equalizerPaneStage, "Config has been copied to clipboard", 2, "green");
		}
	}

}
