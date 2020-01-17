package view;

import controller.RevealInExplorerHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import ressource.Data;

public class DirectoryContextMenu extends ContextMenu {

	public DirectoryContextMenu() {
		super();
		this.generateMenuItems();
	}
	
	public void generateMenuItems() {
		// Directory Context Menu
		MenuItem mAdd = new MenuItem("Add Directory");
		mAdd.setOnAction(event -> Data.DIRECTORIES.add("yeee"));

		MenuItem mRemove = new MenuItem("Remove Directory");

		MenuItem mRevealInExplorer = new MenuItem("Reveal Directory In System Explorer");
		mRevealInExplorer.setOnAction(new RevealInExplorerHandler());
		
		this.getItems().addAll(mAdd, mRemove, mRevealInExplorer);
	}
	
}
