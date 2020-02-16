package controller;

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.FileTreeItem;
import ressource.Icons;
import ressource.References;

public class SongQueue {

	private ArrayList<FileTreeItem> songList;
	private int songQueuePosition;
	private TreeView<String> currentTreeView;

	public SongQueue(TreeView<String> currentTreeView) {
		super();

		this.songList = new ArrayList<FileTreeItem>();
		this.songQueuePosition = 0;
		this.currentTreeView = currentTreeView;
	}

	public void generateSongQueue() {
		if (currentTreeView.getSelectionModel().getSelectedIndex() > -1) {
			TreeItem<String> selectedItem = currentTreeView.getSelectionModel().getSelectedItem();

			if (!currentTreeView.getRoot().equals(selectedItem)) {
				TreeItem<String> realSelectedItem = currentTreeView.getSelectionModel().getSelectedItem();
				
				// Check if item is song or playlist/directory
				if (selectedItem.getChildren().isEmpty()) {
					if (!selectedItem.getParent().equals(currentTreeView.getRoot())) {
						selectedItem = selectedItem.getParent();
					} 
				}
				
				if(this.size() > 0) {
					realSelectedItem = this.get(songQueuePosition);
					this.songList.clear();
				}
				
				int index = 0;
				int finalIndex = 0;

				for (TreeItem<String> child : selectedItem.getChildren()) {
					
					if(!((FileTreeItem) child).isDirectory()) {
						this.songList.add(((FileTreeItem) child));
						
						if (child.equals(realSelectedItem)) {
							finalIndex = index;
						} else {
							index++;
						}	
					}
					
				}
				
				References.currentlyPlayingItem = selectedItem;
				
				songQueuePosition = finalIndex;
				
				if(References.checkBoxShuffle.isSelected()) {
					this.shuffle();
				} 
				
				addPlayingIcon();
				
			}

		}

	}

	public void shuffle() {
		FileTreeItem currentItem = songList.get(songQueuePosition);
		
		Collections.shuffle(songList);

		// Put the currentItem on the start of the list
		int i = 0;
		for (FileTreeItem song : songList) {
			if (song.equals(currentItem)) {
				FileTreeItem replace = songList.get(0);

				if (!replace.equals(currentItem)) {
					songList.set(0, currentItem);
					songList.set(i, replace);
					
					songQueuePosition = 0;
				}

				break;
			}
			
			i++;
		}

	}

	public void next() {
		removePlayingIcon();
		
		if (songQueuePosition < songList.size() - 1) {
			songQueuePosition++;
		} else {
			songQueuePosition = 0;
		}
		
		addPlayingIcon();
	}

	public void previous() {
		removePlayingIcon();
		
		if (songQueuePosition > 0) {
			songQueuePosition--;
		} else {
			songQueuePosition = songList.size() - 1;
		}
		
		addPlayingIcon();
	}
	
	public void addPlayingIcon() {	
		
		if(!getCurrentItem().isDirectory()) {
			ImageView icon = new ImageView(new Image(Icons.class.getResourceAsStream(Icons.ICON_SPEAKER)));
			icon.setFitWidth(16);
			icon.setFitHeight(16);
			
			this.getCurrentItem().setGraphic(icon);
			this.getCurrentTreeView().refresh();	
		}
		
	}
	
	public void removePlayingIcon() {
		if(References.mediaPlayer != null) {
			if(this.size() > 0) {
				ImageView icon = new ImageView(new Image(Icons.class.getResourceAsStream(Icons.ICON_FILE)));
				icon.setFitWidth(16);
				icon.setFitHeight(16);
				this.getCurrentItem().setGraphic(icon);
				this.currentTreeView.refresh();
			}
		}
	}

	public FileTreeItem getCurrentItem() {
		return this.songList.get(songQueuePosition);
	}

	public int size() {
		return this.songList.size();
	}

	public TreeView<String> getCurrentTreeView() {
		return this.currentTreeView;
	}

	public void add(FileTreeItem newItem) {
		this.songList.add(newItem);
	}

	public FileTreeItem get(int index) {
		return this.songList.get(index);
	}
	
	public void remove(int index) {
		this.songList.remove(index);
	}
	
	public int getPosition() {
		return this.songQueuePosition;
	}
	
}
