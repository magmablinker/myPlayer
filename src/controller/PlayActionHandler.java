package controller;

import java.io.File;
import java.util.ArrayList;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.EqualizerBand;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import model.FileTreeItem;
import ressource.Data;
import ressource.Icons;
import ressource.References;
import util.Util;

public class PlayActionHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {

		if (References.SONG_QUEUE == null) {
			// BAD!!!!
			TreeView<String> currentView = null;
			if (References.directoryView.getSelectionModel().getSelectedIndex() > -1)
				currentView = References.directoryView;
			else if (References.playlistView.getSelectionModel().getSelectedIndex() > -1)
				currentView = References.playlistView;

			if(currentView == null)
				return;
			
			References.SONG_QUEUE = new SongQueue(currentView);
			
		}

		this.playMethod();
	}

	public void playMethod() {
		SongQueue queue = References.SONG_QUEUE;

		if (queue.size() == 0) {
			queue.generateSongQueue();
		}

		if (queue.size() > 0) {
			FileTreeItem currentItem = queue.getCurrentItem();

			if (!currentItem.isDirectory()) {
				// Change play button
				ImageView imageView = new ImageView(new Image(Icons.class.getResourceAsStream(Icons.ICON_PAUSE)));
				imageView.setFitHeight(50);
				imageView.setFitWidth(50);

				References.bPlay.setGraphic(imageView);
				References.bPlay.setOnAction(new PauseActionHandler());

				// Stop the currently playing media
				if (References.mediaPlayer != null) {
					if (References.mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)) {
						References.mediaPlayer.play();
						References.SONG_QUEUE.addPlayingIcon();
						return;
					} else {
						References.mediaPlayer.stop();
						References.mediaPlayer.dispose();
					}
				}
				
				try {
					File file = new File(currentItem.getPath());
					Media audioFile = new Media(file.toURI().toString());
					References.songPlayingTitleLabel.setText(currentItem.getValue());
					References.songPlayingArtistLabel.setText("Unknown Artist");
					References.songPlayingAlbum.setText("Unknown Album");
					References.coverImage.setImage(new Image(Icons.class.getResourceAsStream(Icons.DEFAULT_COVER)));

					audioFile.getMetadata().addListener(new MetaDataChangeListener());

					MediaPlayer player = new MediaPlayer(audioFile);
					player.setAudioSpectrumNumBands(10);
					
					if(References.spectrumListener != null)
						player.setAudioSpectrumListener(References.spectrumListener);

					// Preserve the equalizer
					// TODO: BETTER SOLUTION RETARD
					ArrayList<EqualizerBand> bands = Data.currentPreset.getBands();
					ObservableList<EqualizerBand> bandsObs = player.getAudioEqualizer().getBands();

					for (int i = 0; i < bands.size(); i++) {
						bandsObs.get(i).setGain(bands.get(i).getGain());
					}

					player.currentTimeProperty().addListener(
							(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
								References.labelTimeIndicator
										.setText(Util.formatDecimalToMinutes(player.getCurrentTime().toSeconds()) + " / "
												+ Util.formatDecimalToMinutes(player.getTotalDuration().toSeconds()));
								References.mediaProgressBar.setProgress(
										player.getCurrentTime().toMillis() / player.getTotalDuration().toMillis());
							});

					player.setVolume(References.volumeSlider.getValue() / 100);
					player.play();

					player.setOnEndOfMedia(() -> {
						if (References.checkBoxRepeat.isSelected()) {
							player.seek(Duration.ZERO);
						} else {
							References.SONG_QUEUE.removePlayingIcon();

							queue.next();

							PlayActionHandler ah = new PlayActionHandler();
							ah.playMethod();
						}
					});

					References.mediaPlayer = player;
				} catch(Exception ex) {
					ex.printStackTrace();
					PopupTextBuilder builder = new PopupTextBuilder(References.stage, String.format("Playing the media file '%s' failed.", currentItem.getValue()), 3, "red");
					this.reset();
				}

			} else {
				this.reset();
			}

		}

	}

	private void reset() {
		// TODO: WHY IS IS NOT RESETTING WHEN SONG FAILS TO PLAY?!?!
		References.mediaProgressBar.setProgress(0);
		References.labelTimeIndicator.setText("00:00 / 00:00");
		References.songPlayingTitleLabel.setText("No song playing");
		References.songPlayingAlbum.setText("");
		References.songPlayingArtistLabel.setText("");
		References.coverImage.setImage(new Image(Icons.class.getResourceAsStream(Icons.DEFAULT_COVER)));
		References.SONG_QUEUE.removePlayingIcon();
	}

}
