package model;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;
import ressource.References;

public class DirectoryViewCell extends TreeCell<String> {

	// https://examples.javacodegeeks.com/desktop-java/javafx/event-javafx/javafx-drag-drop-example/
	private TreeView<String> parentTree;
	private TreeItem<String> item;

	static final DataFormat FILE_TREE_ITEM = new DataFormat("FileTreeItem");

	public DirectoryViewCell(final TreeView<String> parentTree) {
		this.parentTree = parentTree;

		this.setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				int selectedIndex = References.directoryView.getSelectionModel().getSelectedIndex();

				if (selectedIndex < 0) {
					System.out.println("HERERER");
					event.consume();
					return;
				}

				System.out.println("YEEE");

				Dragboard db = References.directoryView.startDragAndDrop(TransferMode.COPY_OR_MOVE);
				FileTreeItem selectedItem = (FileTreeItem) References.directoryView.getSelectionModel().getSelectedItem();

				Label label = new Label(String.format("%s", getTreeItem().getValue()));
				new Scene(label);
				db.setDragView(label.snapshot(null, null));

				ClipboardContent content = new ClipboardContent();
				content.put(FILE_TREE_ITEM, selectedItem);

				db.setContent(content);
				event.consume();
			}

		});

		this.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();

				if (!event.getTarget().equals((FileTreeItem) db.getContent(FILE_TREE_ITEM))
						&& db.hasContent(FILE_TREE_ITEM)) {
					event.acceptTransferModes(TransferMode.MOVE);
				}

				event.consume();
			}

		});

		this.setOnDragDropped(e -> {
			boolean dragCompleted = false;

			Dragboard db = e.getDragboard();

			if (db.hasContent(FILE_TREE_ITEM)) {
				FileTreeItem item = (FileTreeItem) db.getContent(FILE_TREE_ITEM);
				
				if (!(new File(item.getPath()).isDirectory())) {
					for (TreeItem<String> it : References.playlistView.getRoot().getChildren()) {
						if (it.getValue().equals(((Text) e.getTarget()).getText())) {
							FileTreeItem newItem = new FileTreeItem(new File(item.getPath()));
							it.getChildren().add(newItem);
							it.setExpanded(true);
							break;
						}
					}

					dragCompleted = true;
				}

			}

			e.setDropCompleted(dragCompleted);
			e.consume();
		});
	}

	@Override
	protected void updateItem(String item, boolean empty) {
		// System.out.format("nupdateItem - [%s]n\n", item);
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
