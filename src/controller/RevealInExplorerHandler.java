package controller;

import java.awt.Desktop;
import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ressource.References;
import view.FileTreeItem;

public class RevealInExplorerHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		int selectedIndex = References.directoryView.getSelectionModel().getSelectedIndex();

		if (selectedIndex > 0) {
			FileTreeItem selectedItem = (FileTreeItem) References.directoryView.getSelectionModel().getSelectedItem();

			try {
				String path = selectedItem.getPath();
				
				if(path != null) {
					File file = new File(path);

					if(file.isDirectory()) 
						Desktop.getDesktop().open(file);
				}
			} catch (Exception e1) {

			}
		}
	}

}
