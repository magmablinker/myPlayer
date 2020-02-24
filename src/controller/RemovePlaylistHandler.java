package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import model.Database;
import model.FileTreeItem;
import ressource.References;

public class RemovePlaylistHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {

		if (References.playlistView.getSelectionModel().getSelectedIndex() > -1) {
			TreeItem<String> item = References.playlistView.getSelectionModel().getSelectedItem();

			if (!References.playlistView.getRoot().equals(item)) {
				String sql;

				try {
					Connection conn = Database.getInstance().getConn();
					Statement stmt = conn.createStatement();
					PreparedStatement pst;

					if (item.getParent().equals(References.playlistView.getRoot())) {
						// It's a playlist -> delete from playlist
						ResultSet rs = stmt
								.executeQuery("SELECT id FROM playlist WHERE name = '" + item.getValue() + "'");

						if (rs.next()) {
							sql = "UPDATE playlist SET deleted = 1 WHERE id = ?";
							pst = conn.prepareStatement(sql);
							pst.setInt(1, rs.getInt("id"));
							pst.executeUpdate();
							pst.close();
						}

					} else {
						// It's a song -> delete from playlistSong
						sql = "SELECT playlistSong.sid FROM playlistSong JOIN song ON playlistSong.sid = song.id WHERE song.path = ?";
						PreparedStatement upst = conn.prepareStatement(sql);
						upst.setString(1, ((FileTreeItem) item).getPath());
						ResultSet rs = upst.executeQuery();

						if (rs.next()) {
							sql = "UPDATE playlistSong SET deleted = 1 WHERE sid = ?";
							pst = conn.prepareStatement(sql);
							pst.setInt(1, rs.getInt("sid"));
							pst.executeUpdate();
							pst.close();
						}

						upst.close();
					}

					// Check if current playing item is in deleted playlist
					if (References.SONG_QUEUE != null) {
						if (References.SONG_QUEUE.getCurrentItem().getParent().equals(item)) {
							if (References.mediaPlayer != null) {
								References.SONG_QUEUE.getPlayActionHandler().reset();
							}
						}
					}

					// Delete the item from the view
					item.getParent().getChildren().remove(item);
				} catch (Exception ex) {
					ex.printStackTrace();
					PopupTextBuilder ptb = new PopupTextBuilder(References.stage,
							String.format("Failed to delete the item %s", item.getValue()), 2, "red");
				}

			}

		}

	}

}
