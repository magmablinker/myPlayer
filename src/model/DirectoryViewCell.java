package model;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart.Data;
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

public class DirectoryViewCell extends TreeCell<String> {

	private TreeView<String> parentTree;
	private TreeItem<String> item;

	public DirectoryViewCell(final TreeView<String> parentTree) {
		this.parentTree = parentTree;

		this.setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (!isEmpty()) {
					Dragboard db = startDragAndDrop(TransferMode.MOVE);
					ClipboardContent cc = new ClipboardContent();
					cc.put(DataFormat.PLAIN_TEXT, getTreeItem().getValue());
					db.setContent(cc);
					Label label = new Label(String.format("%s", getTreeItem().getValue()));
					new Scene(label);
					db.setDragView(label.snapshot(null, null));
				}
			}

		});

		this.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				event.acceptTransferModes(TransferMode.MOVE);
				/*
				 * if(event.getDragboard().hasString()) {
				 * if(!event.getDragboard().getString().matches(item.getValue())) {
				 * System.out.println("NEW DRAG OVER");
				 * event.acceptTransferModes(TransferMode.MOVE); } }
				 */
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

		this.setOnDragDropped(e -> {
			Dragboard db = e.getDragboard();
			if (db.hasContent(DataFormat.PLAIN_TEXT)) {
				System.out.println("HERE");
				System.out.println(db.getContent(DataFormat.PLAIN_TEXT));
				e.setDropCompleted(true);
			}
		});
		
		/*
		this.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
			if (getTreeItem() != null) {
				Object target = e.getTarget();
				if (target instanceof Node && target instanceof FileTreeItem && ((FileTreeItem) target).getGraphic()
						.equals(new ImageView(new Image(Data.class.getResourceAsStream("img/directory.png"))))) {
					getTreeItem().setExpanded(true);
					System.out.println("HJERERE");
				}
			}
			
			e.consume();
		});
		*/

	}

	@Override
	protected void updateItem(String item, boolean empty) {
		// System.out.format("nupdateItem - [%s]n\n", item);
		super.updateItem(item, empty);

		if (!empty && item != null) {
			setText(item == null ? "" : item);

			setGraphic(getTreeItem().getGraphic());
		}
	}

}
