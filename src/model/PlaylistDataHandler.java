package model;

import javafx.scene.control.TreeItem;
import ressource.Data;
import ressource.References;

public class PlaylistDataHandler implements IDataHandler {

	@Override
	public void load() {
		Data.PLAYLISTS.add("test");
		Data.PLAYLISTS.add("CoolList");
		
		for (int i = 0; i < Data.PLAYLISTS.size(); i++) {
			TreeItem<String> node = new TreeItem<String>(Data.PLAYLISTS.get(i));
			References.playlistView.getRoot().getChildren().add(node);
		}
		
	}

	@Override
	public void save() {
		// TODO
	}

	@Override
	public boolean isAlreadySaved(String item) {
		// TODO Auto-generated method stub
		return false;
	}

}
