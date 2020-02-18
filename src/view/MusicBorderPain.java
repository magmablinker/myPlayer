package view;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import controller.DirectoryViewCell;
import controller.DirectoryWatchService;
import controller.DoubleClickHandler;
import controller.SearchEventHandler;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import model.DirectoryDataHandler;
import model.PlaylistDataHandler;
import ressource.Icons;
import ressource.References;

public class MusicBorderPain extends BorderPane {

	private DirectoryWatchService directoryWatchService = new DirectoryWatchService();
	private TreeView<String> directoryView;
	private ExecutorService exServiceDirectoryWatchService;

	public MusicBorderPain() {
		super();

		MenuBar menuBar = new MusicMenuBar();
		this.setTop(menuBar);

		GridPane leftPane = (GridPane) createTreeView();
		leftPane.setPrefWidth(300);
		leftPane.prefHeightProperty().bind(this.prefHeightProperty());
		GridPane.setHgrow(leftPane, Priority.ALWAYS);
		
		this.setLeft(leftPane);
		this.setCenter(createCenter());

		System.out.println("Starting watchservice");

		Task<Void> watchService = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				directoryWatchService.run();
				return null;
			}
			
		};

		this.exServiceDirectoryWatchService = Executors.newSingleThreadExecutor();
		this.exServiceDirectoryWatchService.submit(watchService);
	}

	private Node createTreeView() {
		GridPane grid = new GridPane();
		GridPane.setVgrow(grid, Priority.ALWAYS);

		TreeView<String> playlistView = new TreeView<String>();
		playlistView.prefWidthProperty().bind(grid.prefWidthProperty());
		playlistView.prefHeightProperty().bind(grid.prefHeightProperty());
		playlistView.getStyleClass().add("margin-8");

		playlistView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
			@Override
			public TreeCell<String> call(TreeView<String> param) {
				TreeCell<String> cell = new DirectoryViewCell();
				return cell;
			}
		});
		
		BorderPane directoryViewPane = new BorderPane();
		
		directoryView = new TreeView<String>();
		directoryView.prefWidthProperty().bind(grid.prefWidthProperty());
		directoryView.prefHeightProperty().bind(grid.prefHeightProperty());
		directoryView.getStyleClass().add("margin-8");
		
		directoryView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
			@Override
			public TreeCell<String> call(TreeView<String> param) {
				TreeCell<String> cell = new DirectoryViewCell();
				return cell;
			}
		});
		
		References.directoryView = directoryView;
		References.playlistView = playlistView;

		playlistView.setOnMouseClicked(new DoubleClickHandler(playlistView));
		
		TreeItem<String> playlistViewRoot = new TreeItem<String>("Playlists");
		playlistViewRoot.setExpanded(true);		

		TreeItem<String> directoryViewRoot = new TreeItem<String>("Directories");
		
		References.directoryWatchService = directoryWatchService;

		playlistView.setRoot(playlistViewRoot);

		directoryView.setRoot(directoryViewRoot);
		directoryViewRoot.setExpanded(true);

		directoryView.setContextMenu(new DirectoryContextMenu());
		directoryView.setOnMouseClicked(new DoubleClickHandler(directoryView));
		
		playlistView.setContextMenu(new PlaylistContextMenu());
		
		PlaylistDataHandler plh = new PlaylistDataHandler();
		plh.load();
		
		DirectoryDataHandler dh = new DirectoryDataHandler();
		dh.load();
		
		BorderPane searchPane = new BorderPane();
		
		TextField fSearch = new TextField();
		fSearch.setPromptText("Search...");
		fSearch.getStyleClass().add("textfield");
		fSearch.setPrefHeight(40);
		fSearch.setMinHeight(40);
		fSearch.setMaxHeight(40);
		
		Button bSearch = new Button();
		bSearch.getStyleClass().addAll("margin-8-no-border", "button-icon");
		bSearch.setOnAction(new SearchEventHandler(fSearch));
		
		ImageView searchIcon = new ImageView(new Image(Icons.ICON_SEARCH));
		searchIcon.setFitWidth(28);
		searchIcon.setFitHeight(28);
		
		bSearch.setGraphic(searchIcon);
		
		searchPane.setCenter(fSearch);
		searchPane.setRight(bSearch);
		
		directoryViewPane.setCenter(directoryView);
		directoryViewPane.setBottom(searchPane);
		
		grid.add(playlistView, 1, 1);
		grid.add(directoryViewPane, 1, 2);

		return grid;
	}
	
	private Node createCenter() {
		MusicPanel musicPanel = new MusicPanel();
		return musicPanel;
	}

	public ExecutorService getExServiceDirectoryWatchService() {
		return this.exServiceDirectoryWatchService;
	}
	
	public TreeView<String> getDirectoryTreeView() {
		return this.directoryView;
	}

}
