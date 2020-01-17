package controller;

import java.io.File;

import javafx.collections.MapChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import ressource.References;
import view.FileTreeItem;

public class PlayActionHandler implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent e) {
		TreeView<String> view = References.directoryView;
		
		int selectedIndex = view.getSelectionModel().getSelectedIndex();
		
		if(selectedIndex > 0) {
			FileTreeItem selectedItem = (FileTreeItem) view.getSelectionModel().getSelectedItem();
			
			System.out.println(selectedItem.getPath());
			File file = new File(selectedItem.getPath());
			
			if(!file.isDirectory()) {
				// Stop the current playing media
				if(References.mediaPlayer != null)
					References.mediaPlayer.stop();
				
				Media audioFile = new Media(file.toURI().toString());
				References.songPlayingLabel.setText(selectedItem.getValue());
				
				// TODO: Better implementation for metadata change listener
				// reason: metadata gets loaded asynchronous
			    audioFile.getMetadata().addListener((Change<? extends String, ? extends Object> c) -> {
			        if (c.wasAdded()) {
			            if ("artist".equals(c.getKey())) {
			                String artist = c.getValueAdded().toString();
			            } else if ("title".equals(c.getKey())) {
			            	References.songPlayingLabel.setText((String) c.getValueAdded().toString());
			                String title = c.getValueAdded().toString();
			            } else if ("album".equals(c.getKey())) {
			                String album = c.getValueAdded().toString();
			            } else if("image".equals(c.getKey())) {
			            	System.out.println("GOT IMAGE");
			            	References.coverImage.setImage((Image) c.getValueAdded());
			            }
			        }
			    });
				
			    MediaPlayer player = new MediaPlayer(audioFile);
				player.setVolume(References.volumeSlider.getValue() / 100);
				player.play();
				
				player.setOnEndOfMedia(() -> {
					if(References.checkBoxRepeat.isSelected()) {
						player.seek(Duration.ZERO);
					} else if(References.checkBoxShuffle.isSelected()) {
						
					} else {
						// Just play next track
					}
				});
				
				References.mediaPlayer = player;	
			}
		}
		
	}

}
