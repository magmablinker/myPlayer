package controller;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import ressource.References;
import model.FileTreeItem;

public class DirectoryClickHandler implements EventHandler<MouseEvent> {

	@Override
	public void handle(MouseEvent e) {
		if(e.getClickCount() == 2) {
			if(References.SONG_QUEUE != null)
				References.SONG_QUEUE.removePlayingIcon();
			
			if(References.directoryView.getSelectionModel().getSelectedIndex() > -1) {
				File file = new File(((FileTreeItem) References.directoryView.getSelectionModel().getSelectedItem()).getPath());
				if(file.isDirectory() &&
				   References.directoryView.getSelectionModel().getSelectedItem().getParent().equals(References.directoryView.getRoot()))
					return;
			}
			
			SongQueue queue = new SongQueue(References.directoryView);
			
			queue.generateSongQueue();
			
			References.SONG_QUEUE = queue;
			
			PlayActionHandler ah = new PlayActionHandler();
			ah.playMethod();
		}
		
	}

}
