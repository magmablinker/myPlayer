package controller;

import javafx.event.EventHandler;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import model.FileTreeItem;
import ressource.References;
import util.Util;

public class DoubleClickHandler implements EventHandler<MouseEvent> {

	private TreeView<String> currentView;
	
	public DoubleClickHandler(TreeView<String> currentView) {
		this.currentView = currentView;
	}
	
	@Override
	public void handle(MouseEvent e) {
		if(e.getClickCount() == 2) {
			if(currentView.equals(References.directoryView)) {
				
				if(!References.directoryView.getRoot().equals(References.directoryView.getSelectionModel().getSelectedItem())) {
					if(References.directoryView.getSelectionModel().getSelectedIndex() > -1) {
						if(((FileTreeItem) References.directoryView.getSelectionModel().getSelectedItem()).isDirectory() &&
						   References.directoryView.getSelectionModel().getSelectedItem().getParent().equals(References.directoryView.getRoot()))
							return;
						if(((FileTreeItem) References.directoryView.getSelectionModel().getSelectedItem()).isDirectory())
							if(!Util.hasFiles((FileTreeItem) References.directoryView.getSelectionModel().getSelectedItem()))
								return;
					}				
				} else {
					return;
				}
							
			}
			
			if(currentView.equals(References.playlistView)) {
				if(References.playlistView.getSelectionModel().getSelectedIndex() > -1)
					if(References.playlistView.getSelectionModel().getSelectedItem().equals(References.playlistView.getRoot()))
						return;
			}
			
			if(References.SONG_QUEUE != null) {
				References.SONG_QUEUE.removePlayingIcon();
			}
			
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
