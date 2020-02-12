package controller;

import javafx.event.EventHandler;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
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
			
			// TODO: FIX BUG WHERE PAUSED SONG JUST KEEPS PLAYING IF ANOTHER SONG IS DOUBLE CLICKED
			if(currentView.equals(References.directoryView)) {
				if(References.directoryView.getSelectionModel().getSelectedIndex() > -1) {
					if(((FileTreeItem) References.directoryView.getSelectionModel().getSelectedItem()).isDirectory() &&
					   References.directoryView.getSelectionModel().getSelectedItem().getParent().equals(References.directoryView.getRoot()))
						return;
				}				
			}

			SongQueue queue = new SongQueue(currentView);
			
			References.SONG_QUEUE = queue;
				
			queue.generateSongQueue();
			
			PlayActionHandler ah = new PlayActionHandler();
			ah.playMethod();
		}
	}

}
