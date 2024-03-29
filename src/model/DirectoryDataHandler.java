package model;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.PopupTextBuilder;
import javafx.scene.control.TreeItem;
import ressource.Data;
import ressource.References;
import util.Util;

public class DirectoryDataHandler extends DataHandler {

	public DirectoryDataHandler() {
		super();
	}

	@Override
	public void load() {

		try {
			Database db = Database.getInstance();
			Connection conn = db.getConn();
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, path FROM directories WHERE deleted = 0");

			while (rs.next()) {
				Data.DIRECTORIES.add(rs.getString("path"));

				File directory = new File(rs.getString("path"));

				if (directory.exists()) {
					if(directory.isDirectory()) {
						if (directory.listFiles(new AllowedFileFilter()).length > 0) {
							TreeItem<String> node = new FileTreeItem(directory);

							References.directoryView.getRoot().getChildren().add(node);

							File[] fileList = directory.listFiles(new AllowedFileFilter());

							Util.createDirectoryView(fileList, node);
						}	
					}
				} else {
					String sql = "UPDATE directories SET deleted = 1 WHERE id = ?";
					PreparedStatement pst = conn.prepareStatement(sql);
					pst.setInt(1, rs.getInt("id"));
					pst.executeUpdate();
					pst.close();
				}

			}

			stmt.close();
		} catch (SQLException e) {
			// MOVE THIS SHIT SOMEHOW?!
			new PopupTextBuilder(References.stage, "Failed to load directories", 3, "red");
		}

	}

	@Override
	public void save() {
		try {
			Connection conn = Database.getInstance().getConn();

			for (String path : Data.DIRECTORIES) {
				String sql;
				
				if (!isAlreadySaved("path", "directories", path)) {
					sql = "INSERT INTO directories(path) VALUES(?)";
				} else {
					sql = "UPDATE directories SET deleted = 0 WHERE path = ?";
				}
			
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, path);
				pst.executeUpdate();
				pst.close();
			}

		} catch (Exception e) {} // We straight up don't care about exceptions

	}

}
