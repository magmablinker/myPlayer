package view;

import controller.AddDirectoryHandler;
import controller.RemoveDirectoryHandler;
import controller.RevealInExplorerHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class DirectoryContextMenu extends ContextMenu {

	public DirectoryContextMenu() {
		super();
		this.generateMenuItems();
	}
	
	public void generateMenuItems() {
		// Directory Context Menu
		MenuItem mAdd = new MenuItem("Add Directory");
		mAdd.setOnAction(new AddDirectoryHandler());

		MenuItem mRemove = new MenuItem("Remove Directory");
		mRemove.setOnAction(new RemoveDirectoryHandler());

		MenuItem mRevealInExplorer = new MenuItem("Reveal Directory In System Explorer");
		mRevealInExplorer.setOnAction(new RevealInExplorerHandler());
		
		this.getItems().addAll(mAdd, mRemove, mRevealInExplorer);
	}
	
}
