package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.PopupTextBuilder;
import javafx.scene.control.ComboBox;
import ressource.Data;
import ressource.References;

public class EqualizerDataHandler extends DataHandler {
	
	private ComboBox<EqualizerPreset> comboBox;
	
	public EqualizerDataHandler(ComboBox<EqualizerPreset> comboBox) {
		super();
		this.comboBox = comboBox;
	}
	
	@Override
	public void load() {
		
		try {
			Connection conn = Database.getInstance().getConn();
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT name, preset FROM equalizerPreset WHERE deleted = 0");
			
			// Load default preset
			rs.next();
			
			EqualizerPreset defaultPreset = new EqualizerPreset(rs.getString("name"), rs.getString("preset"));
			
			if(Data.currentPreset.getName().equals("Default"))
				Data.currentPreset = defaultPreset;
			
			this.comboBox.getItems().add(defaultPreset);
			
			while (rs.next()) {
				String name = rs.getString("name");
				String preset = rs.getString("preset");
				
				EqualizerPreset eqPreset = new EqualizerPreset(name, preset);
				this.comboBox.getItems().add(eqPreset);
				
				if(Data.currentPreset.getName().equals(name))
					this.comboBox.getSelectionModel().select(eqPreset);
			}

			stmt.close();
		} catch (SQLException e) {
			// MOVE THIS SHIT SOMEHOW?!
			new PopupTextBuilder(References.stage, "Failed to load the equalizer presets!", 3, "red");
		}
		
	}

	@Override
	public void save() {
		// TODO: ??? ADD SAVE ALL METHOD ???
		if(this.comboBox.getSelectionModel().getSelectedIndex() > -1) {
			EqualizerPreset preset = this.comboBox.getSelectionModel().getSelectedItem();
			
			try {
				Connection conn = Database.getInstance().getConn();
				
				boolean isAlreadySaved = isAlreadySaved("name", "equalizerPreset", preset.getName());
				
				String sql;
				if(!isAlreadySaved) {
					sql = "INSERT INTO equalizerPreset(name, preset) VALUES(?, ?)";
				} else {
					sql = "UPDATE equalizerPreset SET preset = ? WHERE name = ? AND deleted = 0";
				}
				
				PreparedStatement pst = conn.prepareStatement(sql);
				
				if(!isAlreadySaved) {
					pst.setString(1, preset.getName());
					pst.setString(2, preset.getStringPreset());
				} else {
					pst.setString(1, preset.getStringPreset());
					pst.setString(2, preset.getName());
				}
				
				pst.executeUpdate();
				pst.close();
			} catch (SQLException e) {
				// MOVE THIS SHIT SOMEHOW?!
				new PopupTextBuilder(References.stage, "Failed to save the equalizer preset.", 3, "red");
			}
		}
	}

}
