package view;

import java.awt.Desktop;
import java.io.File;

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
		mRevealInExplorer.setOnAction(event -> {
			/*
			int selectedIndex = directoryView.getSelectionModel().getSelectedIndex();

			if (selectedIndex > 0) {
				FileTreeItem selectedItem = (FileTreeItem) directoryView.getSelectionModel().getSelectedItem();

				// TODO: find a way to get files by unique identifier
				try {
					String path = selectedItem.getPath();
					if(path != null)
						Desktop.getDesktop().open(new File(path));
				} catch (Exception e1) {

				}
			}
			*/
			System.out.println("Not implemented yet");
		});
		
		this.getItems().addAll(mAdd, mRemove, mRevealInExplorer);
	}
	
}
