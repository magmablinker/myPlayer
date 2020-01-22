package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import ressource.References;
import util.Util;

public class DirectoryClickHandler implements EventHandler<MouseEvent> {

	@Override
	public void handle(MouseEvent e) {
		if(e.getClickCount() == 2) {
			if(References.mediaPlayer != null) {
				Util.removePlayingIcon();
			}
			
			Util.generateSongQueue();
			PlayActionHandler ah = new PlayActionHandler();
			ah.playMethod();
		}
		
	}

}
