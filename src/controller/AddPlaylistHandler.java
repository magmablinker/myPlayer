package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import model.EqualizerPreset;
import ressource.References;

public class AddPlaylistHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		// TODO: Custom Dialog, preserve newly added items
		dialog.initStyle(References.stage.getStyle());
		dialog.setContentText("Playlist Name");
		dialog.showAndWait().ifPresent(text -> {
			TreeItem<String> newPlaylist = new TreeItem<String>(text);
			References.playlistView.getRoot().getChildren().add(newPlaylist);
			References.playlistView.getSelectionModel().select(newPlaylist);
		});
	}

}
