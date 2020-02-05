package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import ressource.References;

public class DirectoryClickHandler implements EventHandler<MouseEvent> {

	@Override
	public void handle(MouseEvent e) {
		if(e.getClickCount() == 2) {
			if(References.SONG_QUEUE != null)
				References.SONG_QUEUE.removePlayingIcon();
			
			SongQueue queue = new SongQueue(References.directoryView);
			
			queue.generateSongQueue();
			
			References.SONG_QUEUE = queue;
			
			PlayActionHandler ah = new PlayActionHandler();
			ah.playMethod();
		}
		
	}

}
