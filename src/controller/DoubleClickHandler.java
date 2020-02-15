package controller;

import javafx.event.EventHandler;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import model.FileTreeItem;
import ressource.References;

public class DoubleClickHandler implements EventHandler<MouseEvent> {

	private TreeView<String> currentView;
	
	public DoubleClickHandler(TreeView<String> currentView) {
		this.currentView = currentView;
	}
	
	@Override
	public void handle(MouseEvent e) {
		if(e.getClickCount() == 2) {
			if(References.SONG_QUEUE != null)
				References.SONG_QUEUE.removePlayingIcon();
			
			if(currentView.equals(References.directoryView)) {
				if(References.directoryView.getSelectionModel().getSelectedIndex() > -1) {
					if(((FileTreeItem) References.directoryView.getSelectionModel().getSelectedItem()).isDirectory() &&
					   References.directoryView.getSelectionModel().getSelectedItem().getParent().equals(References.directoryView.getRoot()))
						return;
				}				
			}
			
			// TODO: FIX BUG WHERE PAUSED SONG JUST KEEPS PLAYING IF ANOTHER SONG IS DOUBLE CLICKED
			// EDIT: This should be the fix
			if(References.mediaPlayer != null)
				if(References.mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED))
					References.mediaPlayer = null;

			SongQueue queue = new SongQueue(currentView);
			
			References.SONG_QUEUE = queue;
				
			queue.generateSongQueue();
			
			PlayActionHandler ah = new PlayActionHandler();
			ah.playMethod();
		}
	}

}