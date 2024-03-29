package model;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ressource.Icons;
import ressource.References;

public class PlaylistDataHandler extends DataHandler {

	@Override
	public void load() {

		try {
			Connection conn = Database.getInstance().getConn();

			String sql = "SELECT name, id FROM playlist WHERE deleted = 0";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			String name = "";
			int playlistId = 0;
			while (rs.next()) {
				name = rs.getString("name");
				playlistId = rs.getInt("id");

				TreeItem<String> playlist = new TreeItem<String>(name);
				ImageView playlistIcon = new ImageView(new Image(Icons.class.getResourceAsStream(Icons.ICON_PLAYLIST)));
				playlistIcon.setFitWidth(28);
				playlistIcon.setFitHeight(28);
				playlist.setGraphic(playlistIcon);

				sql = "SELECT song.path FROM song JOIN playlistSong ON song.id = playlistSong.sid JOIN playlist ON playlist.id = playlistSong.pid WHERE pid = ? AND playlistSong.deleted != 1 AND song.deleted != 1";
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setInt(1, playlistId);

				ResultSet plrs = pst.executeQuery();

				while (plrs.next()) {
					File songFile = new File(plrs.getString("path"));
					
					if(songFile.exists() && songFile.isFile()) {
						FileTreeItem song = new FileTreeItem(songFile);
						playlist.getChildren().add(song);	
					} else {
						sql = "UPDATE song SET deleted = 1 WHERE path = ?";
						PreparedStatement deletepst = conn.prepareStatement(sql);
						deletepst.setString(1, plrs.getString("path"));
						deletepst.executeUpdate();
						deletepst.close();
					}
					
				}

				References.playlistView.getRoot().getChildren().add(playlist);
			}

			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void save() {

		try {
			Connection conn = Database.getInstance().getConn();

			for (TreeItem<String> child : References.playlistView.getRoot().getChildren()) {
				int playlistId = 0;

				// GET OR SAVE THE PLAYLIST
				if (isAlreadySaved("name", "playlist", child.getValue())) {
					String sql = "SELECT id, deleted FROM playlist WHERE name = ?";
					PreparedStatement pst = conn.prepareStatement(sql);

					pst.setString(1, child.getValue());

					ResultSet rs = pst.executeQuery();

					if (rs.next()) {
						playlistId = rs.getInt("id");
						
						if(rs.getInt("deleted") > 0) {
							pst.close();
							sql = "UPDATE playlist SET deleted = 0 WHERE id = ?";
							pst = conn.prepareStatement(sql);
							pst.setInt(1, playlistId);
							pst.executeUpdate();
						}
						
					} 
						

					pst.close();
				} else {
					String sql = "INSERT INTO playlist(name) VALUES(?)";
					PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					
					pst.setString(1, child.getValue());

					pst.executeUpdate();
					ResultSet rs = pst.getGeneratedKeys();

					if (rs.next())
						playlistId = rs.getInt(1);

					pst.close();

				}
				
				if (child.getChildren().size() > 0) {

					for (TreeItem<String> grandChild : child.getChildren()) {
						FileTreeItem playlistSong = (FileTreeItem) grandChild;

						int songId = getSongId(playlistSong.getPath());
						
						// Is not saved yet, save it
						if (songId == -1) {
							String sql = "INSERT INTO song(path) VALUES(?)";
							PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
							pst.setString(1, playlistSong.getPath());
							pst.executeUpdate();
							ResultSet rs = pst.getGeneratedKeys();

							if (rs.next())
								songId = rs.getInt(1);

							pst.close();
						}

						if (songId > -1) {
							if (!songIsInPlaylist(playlistId, songId)) {
								String sql = "INSERT INTO playlistSong(pid, sid) VALUES(?, ?)";
								PreparedStatement pst = conn.prepareStatement(sql);
								pst.setInt(1, playlistId);
								pst.setInt(2, songId);
								pst.executeUpdate();
								pst.close();
							}
						}

					}

				}

			}
		} catch (Exception e) {e.printStackTrace();}

	}

	private boolean songIsInPlaylist(int pid, int sid) {
		boolean songIsInPlaylist = false;

		try {
			Connection conn = Database.getInstance().getConn();
			String sql = "SELECT id FROM playlistSong WHERE pid = ? AND sid = ? AND deleted != 1";

			PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setInt(1, pid);
			pst.setInt(2, sid);

			ResultSet rs = pst.executeQuery();

			if (rs.next())
				songIsInPlaylist = true;

			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return songIsInPlaylist;
	}

	private int getSongId(String path) {
		int songId = -1;

		try {
			Connection conn = Database.getInstance().getConn();
			String sql = "SELECT id, deleted FROM song WHERE path = ?";
			
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, path);
			
			ResultSet rs = pst.executeQuery();

			if (rs.next())
				songId = rs.getInt("id");
				
			pst.close();
		} catch (Exception e) {}

		return songId;
	}

}
