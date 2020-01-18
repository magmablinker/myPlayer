package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import ressource.References;

public class ProgressMouseEventHandler implements EventHandler<MouseEvent> {

	@Override
	public void handle(MouseEvent e) {

		if (References.mediaPlayer != null) {
			try {
				double value;
				value = (e.getX() / References.mediaProgressBar.getWidth())
						* (References.mediaPlayer.getTotalDuration().toMillis());
				References.mediaProgressBar.setProgress(value / References.mediaPlayer.getTotalDuration().toMillis());
				References.mediaPlayer.seek(new Duration(value));
			} catch (Exception ex) {

			}
		}

	}

}
