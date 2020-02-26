package controller;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;
import model.AllowedFileFilter;
import model.FileTreeItem;
import ressource.Icons;
import ressource.References;
import util.Util;
import view.DirectoryContextMenu;

public class DirectoryViewCell extends TreeCell<String> {

	static final DataFormat FILE_TREE_ITEM = new DataFormat("FileTreeItem");

	public DirectoryViewCell() {
		super();
		this.setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				TreeView<String> treeView = ((DirectoryViewCell) event.getSource()).getTreeView();
				int selectedIndex = treeView.getSelectionModel().getSelectedIndex();

				if (selectedIndex < 0) {
					if (References.searchResultPane == null) {
						event.consume();
						return;
					}
				}

				if (!treeView.getSelectionModel().getSelectedItem().equals(treeView.getRoot())) {
					try {
						Dragboard db = treeView.startDragAndDrop(TransferMode.COPY_OR_MOVE);
						FileTreeItem selectedItem = (FileTreeItem) treeView.getSelectionModel().getSelectedItem();

						Label label = new Label(String.format("%s", getTreeItem().getValue()));
						new Scene(label);
						db.setDragView(label.snapshot(null, null));

						ClipboardContent content = new ClipboardContent();
						content.put(FILE_TREE_ITEM, selectedItem);

						db.setContent(content);
					} catch (Exception e) {} // Fuck exceptions gang
				}

				event.consume();
			}

		});

		this.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				
				if(event.getDragboard().hasContent(FILE_TREE_ITEM)) {
					if (!event.getTarget().equals((FileTreeItem) db.getContent(FILE_TREE_ITEM))) {
						event.acceptTransferModes(TransferMode.MOVE);
					} 
				} else if(event.getDragboard().hasFiles()) {
					event.acceptTransferModes(TransferMode.COPY);
				}
				
				event.consume();
			}

		});

		this.setOnDragDropped(e -> {
			boolean dragCompleted = false;
						
			Dragboard db = e.getDragboard();

			if (db.hasContent(FILE_TREE_ITEM)) {
				FileTreeItem item = (FileTreeItem) db.getContent(FILE_TREE_ITEM);

				if (!item.isDirectory()) {
					TreeItem<String> targetItem = null;
					DirectoryViewCell target;
					
					if(e.getTarget() instanceof DirectoryViewCell) {
						target = (DirectoryViewCell) e.getTarget();
					} else {
						target = (DirectoryViewCell) ((Node) e.getTarget()).getParent();
					}
					
					// THIS IS A FUCKING MESS DON'T TOUCH IT!!!!
					if(!target.getTreeItem().getParent().equals(References.playlistView.getRoot())) {
						if(target.getTreeItem().getParent().getParent().equals(References.playlistView.getRoot())) {
							targetItem = ((DirectoryViewCell) target).getTreeItem().getParent();
						}
					} else {
						targetItem = target.getTreeItem();
					}
					
					if(targetItem != null) {
						boolean alreadyInPlaylist = false;
						for (TreeItem<String> child : targetItem.getChildren()) {
							if (((FileTreeItem) child).getPath().equals(item.getPath())) {
								alreadyInPlaylist = true;
								break;
							}
						}
						
						if(!alreadyInPlaylist) {
							FileTreeItem newItem = new FileTreeItem(new File(item.getPath()));
							
							targetItem.getChildren().add(newItem);
							targetItem.setExpanded(true);
							
							dragCompleted = true;
						}
					}
				}

			} else if (db.hasFiles()) {
				File file = db.getFiles().get(0);

				// TODO: Fix bug where you can drop directories on the playlistTreeView
				if (file.isDirectory()) {
					if (!Util.isAlreadyInTreeView(References.directoryView, file)) {
						FileTreeItem node = new FileTreeItem(file);
						References.directoryView.getRoot().getChildren().add(node);

						if (file.listFiles().length > 0) {
							Util.createDirectoryView(file.listFiles(new AllowedFileFilter()), node);
							dragCompleted = true;
						}
					} else {
						PopupTextBuilder ptb = new PopupTextBuilder(References.stage,
								"The dragged directory got already imported", 2, "orange");
					}
				} else {
					PopupTextBuilder ptb = new PopupTextBuilder(References.stage, "Only directories allowed", 2,
							"orange");
				}

			}

			e.setDropCompleted(dragCompleted);
			e.consume();
		});

		this.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {

			if (isNowEmpty) {
				this.setContextMenu(null);
			} else {
				if (getTreeItem().equals(References.directoryView.getRoot()) || (getTreeItem().getParent() != null
						&& getTreeItem().getParent().equals(References.directoryView.getRoot())))
					this.setContextMenu(new DirectoryContextMenu());
			}

		});

	}

	@Override
	protected void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);

		if (!empty && item != null) {
			setText(item == null ? "" : item);
			setGraphic(getTreeItem().getGraphic());
		} else {
			setText(null);
			setGraphic(null);
		}
	}

}
