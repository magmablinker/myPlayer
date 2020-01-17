package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class DirectoryClickHandler implements EventHandler<MouseEvent> {

	@Override
	public void handle(MouseEvent e) {
		
		if(e.getClickCount() == 2) {
			PlayActionHandler ah = new PlayActionHandler();
			ah.playMethod();
		}
		
	}

}
