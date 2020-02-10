package view;

import controller.AddPlaylistHandler;
import controller.DelPlaylistHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class PlaylistContextMenu extends ContextMenu {

	public PlaylistContextMenu() {
		super();
		this.createContextMenu();
	}

	private void createContextMenu() {
		MenuItem mNew = new MenuItem("New Playlist");
		mNew.setOnAction(new AddPlaylistHandler());
		
		MenuItem mDel = new MenuItem("Delete Playlist");
		mDel.setOnAction(new DelPlaylistHandler());
		
		this.getItems().addAll(mNew, mDel);
	}
	
}
