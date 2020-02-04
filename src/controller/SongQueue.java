package controller;

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import model.FileTreeItem;
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
				
				if(this.size() > 0)
					this.songList.clear();
				
				int index = 0;
				int finalIndex = 0;

				for (TreeItem<String> child : selectedItem.getChildren()) {
					System.out.println("ADDING: " + child.getValue());
					this.songList.add(((FileTreeItem) child));
					
					if (child.equals(realSelectedItem)) {
						finalIndex = index;
					} else {
						index++;
					}
				}
				
				References.currentlyPlayingItem = selectedItem;
				
				if(References.checkBoxShuffle.isSelected()) {
					songQueuePosition = finalIndex;
					this.shuffleQueue();
				} else {
					songQueuePosition = finalIndex;
				}

			}

		}

	}

	public void shuffleQueue() {
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
		if (songQueuePosition < songList.size() - 1) {
			songQueuePosition++;
		} else {
			songQueuePosition = 0;
		}
	}

	public void previous() {
		if (songQueuePosition > 0) {
			songQueuePosition--;
		} else {
			songQueuePosition = songList.size() - 1;
		}
	}

	public FileTreeItem getCurrentItem() {
		return this.songList.get(songQueuePosition);
	}

	public int size() {
		return this.songList.size();
	}

	/*
	 * public static void generateSongQueue() { TreeView<String> view =
	 * References.directoryView;
	 * 
	 * if (view.getSelectionModel().getSelectedIndex() > -1) { TreeItem<String>
	 * selectedItem = view.getSelectionModel().getSelectedItem();
	 * 
	 * if (!References.directoryView.getRoot().equals(selectedItem)) { if
	 * (selectedItem.getChildren().isEmpty() &&
	 * !selectedItem.getParent().equals(References.directoryView.getRoot())) {
	 * selectedItem = selectedItem.getParent(); }
	 * 
	 * TreeItem<String> selectedItemSong =
	 * view.getSelectionModel().getSelectedItem();
	 * 
	 * if (Data.SONG_QUEUE.size() > 0) { Data.SONG_QUEUE.clear(); }
	 * 
	 * int index = 0; int finalIndex = 0;
	 * 
	 * for (TreeItem<String> child : selectedItem.getChildren()) {
	 * Data.SONG_QUEUE.add((FileTreeItem) child);
	 * 
	 * if (child.equals(selectedItemSong)) { finalIndex = index; } else { index++; }
	 * }
	 * 
	 * References.currentlyPlayingItem = (FileTreeItem) selectedItem;
	 * 
	 * if (References.checkBoxShuffle.isSelected()) {
	 * Collections.shuffle(Data.SONG_QUEUE);
	 * 
	 * for (int i = 0; i < Data.SONG_QUEUE.size(); i++) { if
	 * (Data.SONG_QUEUE.get(i).equals(selectedItemSong)) { FileTreeItem replace =
	 * Data.SONG_QUEUE.get(0); Data.SONG_QUEUE.add(0, Data.SONG_QUEUE.get(i));
	 * Data.SONG_QUEUE.remove(i); Data.SONG_QUEUE.add(i, replace);
	 * Data.SONG_QUEUE_POSITION = 0; break; } }
	 * 
	 * } else { Data.SONG_QUEUE_POSITION = finalIndex; }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * }
	 */

}
