package model;

import javafx.scene.control.TreeItem;
import ressource.Data;

public class PlaylistHandler implements IDataHandler {

	@Override
	public void load(TreeItem<String> root) {
		Data.PLAYLISTS.add("test");
		Data.PLAYLISTS.add("CoolList");
		
		for (int i = 0; i < Data.PLAYLISTS.size(); i++) {
			TreeItem<String> node = new TreeItem<String>(Data.PLAYLISTS.get(i));
			root.getChildren().add(node);
		}
		
	}

	@Override
	public boolean save() {
		return false;
	}

}
