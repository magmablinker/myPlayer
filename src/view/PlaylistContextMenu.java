package view;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class PlaylistContextMenu extends ContextMenu {

	public PlaylistContextMenu() {
		super();
		this.createContextMenu();
	}

	private void createContextMenu() {
		MenuItem mNew = new MenuItem("New Playlist");
		//mAdd.setOnAction(new AddDirectoryHandler());
		
		MenuItem mDel = new MenuItem("Delete Playlist");
		
		this.getItems().addAll(mNew, mDel);
	}
	
}
