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

public class EqualizerDataHandler implements IDataHandler {
	
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
			Data.currentPreset = new EqualizerPreset(rs.getString("name"), rs.getString("preset"));
			this.comboBox.getItems().add(Data.currentPreset);
			
			while (rs.next()) {
				this.comboBox.getItems().add(new EqualizerPreset(rs.getString("name"), rs.getString("preset")));
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
				
				boolean isAlreadySaved = isAlreadySaved(preset.getName());
				
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

	@Override
	public boolean isAlreadySaved(String item) {
		boolean isAlreadySaved = false;
		
		try {
			Connection conn = Database.getInstance().getConn();
			String sql = "SELECT name FROM equalizerPreset WHERE name = ? AND deleted = 0";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, item);
			
			ResultSet res = pst.executeQuery();
			
			if(res.next())
				isAlreadySaved = true;
			
			pst.close();
		} catch(Exception e) {}
		
		return isAlreadySaved;
	}

}
