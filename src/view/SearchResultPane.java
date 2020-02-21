package view;

import java.util.ArrayList;

import controller.DirectoryViewCell;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import model.FileTreeItem;

public class SearchResultPane extends StackPane {

	private ArrayList<FileTreeItem> searchResultList = null;
	private TreeView<String> resultTreeView;
	private TreeItem<String> root;
	
	public SearchResultPane() {
		this.getChildren().add(this.createContent());
	}
	
	private Node createContent() {
		StackPane pane = new StackPane();
		
		resultTreeView = new TreeView<String>();
		root = new TreeItem<String>("Search Results");
		resultTreeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
			@Override
			public TreeCell<String> call(TreeView<String> param) {
				TreeCell<String> cell = new DirectoryViewCell();
				return cell;
			}
		});
		
		resultTreeView.setRoot(root);
		pane.getChildren().add(resultTreeView);
		
		return pane;
	}

	public void displaySearchResultList() {
		this.root.getChildren().clear();
		
		if(searchResultList != null) {
			for (FileTreeItem item : searchResultList) {
				this.root.getChildren().add(item);
			}
			this.root.setExpanded(true);
		}

		this.resultTreeView.refresh();
	}
	
	public void setSearchResultList(ArrayList<FileTreeItem> resultList) {		
		this.searchResultList = resultList;
	}
	
	public TreeView<String> getResultTreeView() {
		return this.resultTreeView;
	}

}
