package view;

import java.io.File;

import javafx.collections.MapChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PlayActionHandler implements EventHandler<ActionEvent> {

	MediaPlayer player;
	
	@Override
	public void handle(ActionEvent e) {
		Button b = (Button) e.getSource();
		GridPane gp = (GridPane) b.getParent();
		MusicPanel p = (MusicPanel) gp.getParent();
		MusicBorderPain mbp = (MusicBorderPain) p.getParent();
		
		TreeView<String> view = mbp.getDirectoryTreeView();
		
		int selectedIndex = view.getSelectionModel().getSelectedIndex();
		
		if(selectedIndex > 0) {
			FileTreeItem selectedItem = (FileTreeItem) view.getSelectionModel().getSelectedItem();
			
			System.out.println(selectedItem.getPath());
			Media mp3File = new Media(new File(selectedItem.getPath()).toURI().toString());
			
			// TODO: Better implementation for metadata change listener
			// reason: metadata gets loaded asynchronous
		    mp3File.getMetadata().addListener((Change<? extends String, ? extends Object> c) -> {
		        if (c.wasAdded()) {
		            if ("artist".equals(c.getKey())) {
		                String artist = c.getValueAdded().toString();
		            } else if ("title".equals(c.getKey())) {
		            	p.getSongPlayingLabel().setText((String) c.getValueAdded().toString());
		                String title = c.getValueAdded().toString();
		            } else if ("album".equals(c.getKey())) {
		                String album = c.getValueAdded().toString();
		            }
		        }
		    });
			
			player = new MediaPlayer(mp3File);
			player.play();
		}
		
	}

}
