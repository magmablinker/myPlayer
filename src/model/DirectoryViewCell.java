package model;

import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class DirectoryViewCell extends TreeCell<String> {

	private TreeView<String> parentTree;
	private TreeItem<String> item;

	public DirectoryViewCell(final TreeView<String> parentTree) {
		this.parentTree = parentTree;

		this.setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				TreeItem<String> item = getTreeItem();

				if (isEmpty() || item == null || item.getParent() == null) {
					return;
				}
				
				System.out.println("NEW DRAG");

				Dragboard dragBoard = startDragAndDrop(TransferMode.MOVE);
				ClipboardContent content = new ClipboardContent();
				content.put(DataFormat.PLAIN_TEXT, item.getValue());
				dragBoard.setContent(content);
				event.consume();
			}

		});
		
		this.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if(event.getDragboard().hasString()) {
					if(!event.getDragboard().getString().matches(item.getValue())) {
						event.acceptTransferModes(TransferMode.MOVE);
					}
				}
			}
			
		});

		this.setOnDragDone(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (!(event.getSource() instanceof TreeCell)
						|| (((TreeCell<String>) event.getSource()).getTreeView() != getTreeView())) {
					return;
				}
				System.out.println("DRAG DONE");

				TreeItem<String> sourceItem = ((TreeCell<String>) event.getGestureSource()).getTreeItem();
				TreeItem<String> item = getTreeItem();

				while (item != null && !item.equals(sourceItem)) {
					System.out.println(item.getValue());
					item = item.getParent();
				}
				
				if (item == null) {
					event.acceptTransferModes(TransferMode.MOVE);
				}

				event.consume();
			}

		});

		this.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				System.out.println("DRAG DROPPEd");
				TreeItem<String> itemToMove = ((TreeCell<String>) event.getGestureSource()).getTreeItem();
				TreeItem<String> newParent = getTreeItem();

				itemToMove.getParent().getChildren().remove(itemToMove);

				newParent.getChildren().add(itemToMove);
				newParent.setExpanded(true);
				event.setDropCompleted(true);
				event.consume();
			}

		});

	}

	@Override
	protected void updateItem(String item, boolean empty) {
		System.out.format("nupdateItem - [%s]n\n", item);
		super.updateItem(item, empty);
		setText(item == null ? "" : item);
	}
	
}
