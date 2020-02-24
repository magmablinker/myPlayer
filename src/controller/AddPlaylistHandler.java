package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ressource.Data;
import ressource.Icons;
import ressource.References;
import view.Main;

public class AddPlaylistHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.initStyle(References.stage.getStyle());
		dialog.setContentText("Playlist Name");
		dialog.setHeaderText("Add Playlist");
		((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Data.class.getResourceAsStream("img/cheems.png")));
		dialog.getDialogPane().getStylesheets().add(Main.class.getResource("css/application.css").toExternalForm());
		dialog.showAndWait().ifPresent(text -> {
			TreeItem<String> newPlaylist = new TreeItem<String>(text);
			ImageView playlistIcon = new ImageView(new Image(Icons.class.getResourceAsStream(Icons.ICON_PLAYLIST)));
			playlistIcon.setFitWidth(28);
			playlistIcon.setFitHeight(28);
			newPlaylist.setGraphic(playlistIcon);
			References.playlistView.getRoot().getChildren().add(newPlaylist);
			References.playlistView.getSelectionModel().select(newPlaylist);
		});
	}

}
