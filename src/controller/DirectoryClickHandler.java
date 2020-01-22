package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import util.Util;

public class DirectoryClickHandler implements EventHandler<MouseEvent> {

	@Override
	public void handle(MouseEvent e) {
		if(e.getClickCount() == 2) {
			Util.generateSongQueue();
			PlayActionHandler ah = new PlayActionHandler();
			ah.playMethod();
		}
		
	}

}
