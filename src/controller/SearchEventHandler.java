package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import ressource.References;

public class SearchEventHandler implements EventHandler<ActionEvent> {

	private TextField fSearch;

	public SearchEventHandler(TextField fSearch) {
		this.fSearch = fSearch;
	}

	@Override
	public void handle(ActionEvent event) {

		if (!fSearch.getText().isEmpty()) {
			recursiveSearch(References.directoryView.getRoot());
		}

	}

	private void recursiveSearch(TreeItem<String> parent) {
		for (TreeItem<String> child : parent.getChildren()) {

			if (child.getChildren().size() > 0) {
				recursiveSearch(child);
			} else {
				
				if (child.getValue().contains(fSearch.getText())) {
					System.out.println(child.getValue());
					parent.setExpanded(true);
					child.getGraphic().setStyle("-fx-background: yellow;");
				} 
				
			}

		}
		
	}

}
