package controller;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.FileTreeItem;
import ressource.Data;
import ressource.References;
import view.EqualizerPane;
import view.SearchResultPane;

public class SearchEventHandler implements EventHandler<ActionEvent> {

	private TextField fSearch;
	private ArrayList<FileTreeItem> resultList = new ArrayList<FileTreeItem>();

	public SearchEventHandler(TextField fSearch) {
		this.fSearch = fSearch;
	}

	@Override
	public void handle(ActionEvent event) {

		if (!fSearch.getText().isEmpty()) {
			resultList.clear();
			recursiveSearch(References.directoryView.getRoot());
			
			if(resultList.size() > 0) {
				SearchResultPane root;
				
				if(References.searchResultPane == null) {
					root = new SearchResultPane();
					
					Scene scene = new Scene(root, 500, 300);
					Stage stage = new Stage();
					
					stage.setOnCloseRequest(e -> {
						stage.close();
						References.searchResultPane = null;
					});
					
					scene.getStylesheets().add(EqualizerPane.class.getResource("css/application.css").toExternalForm());
					
					stage.setTitle("Search Results");
					stage.getIcons().add(new Image(Data.class.getResourceAsStream("img/search.png")));
					stage.setResizable(false);
					stage.setScene(scene);
					
					// Center the equalizer window on primaryStage
					Bounds mainBounds = References.stage.getScene().getRoot().getLayoutBounds();
					Bounds rootBounds = scene.getRoot().getLayoutBounds();
					
					References.searchResultPane = root;
					
					stage.setX(References.stage.getX() + (mainBounds.getWidth() - rootBounds.getWidth()) / 2);
					stage.setY(References.stage.getY() + (mainBounds.getHeight() - rootBounds.getHeight()) / 2);
					stage.show();
				} else {
					root = References.searchResultPane;
				}
				
				root.setSearchResultList(resultList);
				root.displaySearchResultList();
			} else {
				PopupTextBuilder ptb = new PopupTextBuilder(References.stage, "No matching content has been found.", 3, "orange");
			}
			
		}

	}

	private void recursiveSearch(TreeItem<String> parent) {
		for (TreeItem<String> child : parent.getChildren()) {

			if (child.getChildren().size() > 0) {
				recursiveSearch(child);
			} else {
		
				if (child.getValue().toLowerCase().contains(fSearch.getText().toLowerCase())) {
					resultList.add(((FileTreeItem) child));
				} 
				
			}

		}
		
	}

}
