package model;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.TreeItem;
import ressource.Data;
import ressource.References;
import util.Util;

public class DirectoryHandler implements ITreeDataHandler {

	public DirectoryHandler() {
		super();
	}

	@Override
	public void load(TreeItem<String> root) {

		try {
			Connection conn = Database.getInstance().getConn();
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT path FROM directories");

			while (rs.next()) {
				Data.DIRECTORIES.add(rs.getString("path"));
			}

		} catch (SQLException e) {
			// POPUP FAILED TO LOAD
		}

		for (int i = 0; i < Data.DIRECTORIES.size(); i++) {
			File directory = new File(Data.DIRECTORIES.get(i));

			if (directory.isDirectory()) {
				if (directory.listFiles(new AllowedFileFilter()).length > 0) {
					TreeItem<String> node = new FileTreeItem(directory);

					root.getChildren().add(node);

					File[] fileList = directory.listFiles(new AllowedFileFilter());

					Util.createDirectoryView(fileList, node);
				}
			} else {
				root.getChildren().add(new TreeItem<String>("Directory '" + directory.getName() + "' not found"));
			}
		}

	}

	@Override
	public void save() {
		try {
			Connection conn = Database.getInstance().getConn();

			for (TreeItem<String> c : References.directoryView.getRoot().getChildren()) {
				FileTreeItem child = (FileTreeItem) c;

				if (!isAlreadySaved(child.getPath())) {
					String sql = "INSERT INTO directories(path) VALUES(?)";
					PreparedStatement pst = conn.prepareStatement(sql);
					pst.setString(1, child.getPath());
					pst.executeUpdate();
					pst.close();
				}
			}
	
		} catch (Exception e) {

		}
		
	}

	@Override
	public boolean isAlreadySaved(String item) {
		boolean isAlreadySaved = false;

		try {
			Connection conn = Database.getInstance().getConn();
			String sql = "SELECT path FROM directories WHERE path = ?";
			
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, item);

			ResultSet res = pst.executeQuery();
			
			if(res.next())
				isAlreadySaved = true;
			
			pst.close();
		} catch (Exception e) {
		}
		
		System.out.println(item + " isAlreadySaved " + isAlreadySaved);
		
		return isAlreadySaved;
	}

}
