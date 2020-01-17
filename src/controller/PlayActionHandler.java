package controller;

import java.io.File;

import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import ressource.References;
import util.Util;
import view.FileTreeItem;

public class PlayActionHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		this.playMethod();
	}

	public void playMethod() {
		TreeView<String> view = References.directoryView;

		int selectedIndex = view.getSelectionModel().getSelectedIndex();

		if (selectedIndex > 0) {
			FileTreeItem selectedItem = (FileTreeItem) view.getSelectionModel().getSelectedItem();

			File file = new File(selectedItem.getPath());

			if (!file.isDirectory()) {
				// Stop the current playing media
				if (References.mediaPlayer != null)
					References.mediaPlayer.stop();

				Media audioFile = new Media(file.toURI().toString());
				References.songPlayingTitleLabel.setText(selectedItem.getValue());
				References.songPlayingArtistLabel.setText("Unknown Artist");
				References.coverImage
						.setImage(new Image(getClass().getResourceAsStream("../ressource/img/defaultcover.jpg")));

				// TODO: Better implementation for metadata change listener
				// reason: metadata gets loaded asynchronous
				audioFile.getMetadata().addListener((Change<? extends String, ? extends Object> c) -> {
					if (c.wasAdded()) {
						if ("artist".equals(c.getKey())) {
							References.songPlayingArtistLabel.setText(c.getValueAdded().toString());
						} else if ("title".equals(c.getKey())) {
							References.songPlayingTitleLabel.setText((String) c.getValueAdded().toString());
							String title = c.getValueAdded().toString();
						} else if ("album".equals(c.getKey())) {
							String album = c.getValueAdded().toString();
						} else if ("image".equals(c.getKey())) {
							System.out.println("GOT IMAGE");
							References.coverImage.setImage((Image) c.getValueAdded());
						}
					}
				});

				MediaPlayer player = new MediaPlayer(audioFile);

				player.currentTimeProperty().addListener(
						(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
							References.labelTimeIndicator
									.setText(Util.formatDecimalToMinutes(player.getCurrentTime().toSeconds()) + " / "
											+ Util.formatDecimalToMinutes(player.getTotalDuration().toSeconds()));
							References.mediaProgressBar.setProgress(
									player.getCurrentTime().toSeconds() / player.getTotalDuration().toSeconds());
						});

				player.setVolume(References.volumeSlider.getValue() / 100);
				player.play();

				player.setOnEndOfMedia(() -> {
					if (References.checkBoxRepeat.isSelected()) {
						player.seek(Duration.ZERO);
					} else {
						if (References.checkBoxShuffle.isSelected()) {
							System.out.println("Playing Shuffle");
							selectedItem.setPlayed(true);
							view.getSelectionModel().select(getRandomTreeItem(selectedItem));

						} else {
							// Just play next track
							view.getSelectionModel().select(selectedItem.nextSibling());
						}

						this.playMethod();
					}
				});

				References.mediaPlayer = player;
			} else {
				References.mediaProgressBar.setProgress(0);
				References.labelTimeIndicator.setText("00:00");
				References.songPlayingTitleLabel.setText("No song playing");
			}
		}

	}

	private TreeItem<String> getRandomTreeItem(FileTreeItem item) {
		// bad approach
		// TODO: create arraylist with all tracks in them and just shuffle them
		// when the shuffle checkbox gets selected
		TreeItem<String> parent = item.getParent();

		int size = parent.getChildren().size();

		int randomIndex;
		while (item.isPlayed()) {
			randomIndex = (int) (Math.random() * size) + 1;
			item = (FileTreeItem) parent.getChildren().get(randomIndex);
		}

		return item;
	}

}
