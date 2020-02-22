package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.scene.control.TreeItem;
import ressource.References;

public class PlaylistDataHandler extends DataHandler {

	@Override
	public void load() {

		try {
			
		} catch(Exception e) {
			
		}
		
	}

	@Override
	public void save() {
		
		try {
			Connection conn = Database.getInstance().getConn();
			
			for (TreeItem<String> child : References.playlistView.getRoot().getChildren()) {
				int playlistId = 0;
				
				if(isAlreadySaved("name", "playlist", child.getValue())) {
					String sql = "SELECT id FROM playlist WHERE name = ? AND deleted != 1";
					PreparedStatement pst = conn.prepareStatement(sql);
					
					pst.setString(1, child.getValue());
					
					ResultSet rs = pst.executeQuery();
					
					if(rs.next())
						playlistId = rs.getInt("id");
					
					pst.close();
				} else {
					String sql = "INSERT INTO playlist(name) VALUES(?)";
					PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					
					pst.setString(1, child.getValue());
					
					pst.executeUpdate();
					ResultSet rs = pst.getGeneratedKeys();
					
					if(rs.next())
						playlistId = rs.getInt(1);
					
					pst.close();
						
				}
			
				
				if(child.getChildren().size() > 0) {
					
					for(TreeItem<String> grandChild : child.getChildren()) {
						FileTreeItem playlistSong = (FileTreeItem) grandChild;
						
						// Is not saved yet
						if(!isSongInserted(playlistSong.getPath(), playlistId)) {
							String sql = "INSERT INTO playlistSong(pid, songPath) VALUES(?, ?)";
							PreparedStatement pst = conn.prepareStatement(sql);
							
							pst.setInt(1, playlistId);
							pst.setString(2, playlistSong.getPath());
							
							pst.executeUpdate();

							pst.close();
						} 
						
					}
					
				}
				
			}	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private boolean isSongInserted(String path, int pid) {
		boolean isInserted = false;
		
		try {
			Connection conn = Database.getInstance().getConn();
			String sql = "SELECT id FROM playlistSong WHERE songPath = ? AND deleted != 1 AND pid = ?";
			
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, path);
			pst.setInt(2, pid);

			ResultSet res = pst.executeQuery();
			
			if(res.next())
				isInserted = true;
			
			pst.close();
		} catch (Exception e) {}
		
		return isInserted;
	}

}
