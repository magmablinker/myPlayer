package controller;

import java.awt.Desktop;
import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Region;
import ressource.References;
import view.FileTreeItem;

public class RevealInExplorerHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		int selectedIndex = References.directoryView.getSelectionModel().getSelectedIndex();

		if (selectedIndex > 0) {
			FileTreeItem selectedItem = (FileTreeItem) References.directoryView.getSelectionModel().getSelectedItem();
			File file = null;
			try {
				String path = selectedItem.getPath();
				
				if(path != null) {
					file = new File(path);

					if(file.isDirectory()) 
						Desktop.getDesktop().open(file);
				}
			} catch (Exception e1) {
				Alert alert = new Alert(AlertType.ERROR, "Failed to open directory " + file.getName(), ButtonType.OK);
				alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
				alert.show();
			}
		}
	}

}
