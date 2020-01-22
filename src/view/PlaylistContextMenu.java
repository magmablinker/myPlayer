package view;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class PlaylistContextMenu extends ContextMenu {

	public PlaylistContextMenu() {
		super();
		this.createContextMenu();
	}

	private void createContextMenu() {
		MenuItem mAdd = new MenuItem("Add Playlist");
		//mAdd.setOnAction(new AddDirectoryHandler());
		
		this.getItems().addAll(mAdd);
	}
	
}
