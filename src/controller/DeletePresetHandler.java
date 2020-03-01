package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Database;
import model.EqualizerPreset;
import ressource.References;
import view.EqualizerPane;

public class DeletePresetHandler implements EventHandler<ActionEvent> {

	private EqualizerPane equalizerPane;
	
	public DeletePresetHandler(EqualizerPane equalizerPane) {
		this.equalizerPane = equalizerPane;
	}

	@Override
	public void handle(ActionEvent e) {
		if(equalizerPane.getComboPreset().getSelectionModel().getSelectedIndex() > -1) {
			if(!equalizerPane.getComboPreset().getSelectionModel().getSelectedItem().getName().equals("Default")) {
				try {
					EqualizerPreset selectedPreset = equalizerPane.getComboPreset().getSelectionModel().getSelectedItem();
					
					Connection conn = Database.getInstance().getConn();
					String sql = "UPDATE equalizerPreset SET deleted = 1 WHERE name = ?";
					PreparedStatement pst = conn.prepareStatement(sql);
					pst.setString(1, selectedPreset.getName());
					
					pst.executeUpdate();
					pst.close();
					
					equalizerPane.getComboPreset().getItems().remove(equalizerPane.getComboPreset().getSelectionModel().getSelectedIndex());
				} catch (Exception ex) {
					PopupTextBuilder builder = new PopupTextBuilder(References.equalizerPaneStage, "Failed to deleted the preset", 2, "red");
				}
			}
		}
	}

}
