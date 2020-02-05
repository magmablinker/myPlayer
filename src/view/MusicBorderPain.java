package view;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import controller.DirectoryClickHandler;
import controller.DirectoryWatchService;
import controller.PlayActionHandler;
import controller.SongQueue;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import model.DirectoryHandler;
import model.DirectoryViewCell;
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

		playlistView.setOnMouseClicked(e -> {
			if(e.getClickCount() == 2) {
				if(References.SONG_QUEUE != null)
					References.SONG_QUEUE.removePlayingIcon();

				SongQueue queue = new SongQueue(playlistView);
				
				References.SONG_QUEUE = queue;
					
				queue.generateSongQueue();
				
				PlayActionHandler ah = new PlayActionHandler();
				ah.playMethod();
			}
		});
		
		TreeItem<String> playlistViewRoot = new TreeItem<String>("Playlists");
		playlistViewRoot.setExpanded(true);		
		playlistViewRoot.getChildren().add(new TreeItem<String>("PLAYLIT"));

		TreeItem<String> directoryViewRoot = new TreeItem<String>("Directories");
		
		DirectoryHandler dl = new DirectoryHandler(this.directoryWatchService);
		dl.load(directoryViewRoot);

		playlistView.setRoot(playlistViewRoot);

		directoryView.setRoot(directoryViewRoot);
		directoryViewRoot.setExpanded(true);

		directoryView.setContextMenu(new DirectoryContextMenu());
		directoryView.setOnMouseClicked(new DirectoryClickHandler());
		
		playlistView.setContextMenu(new PlaylistContextMenu());

		grid.add(playlistView, 1, 1);
		grid.add(directoryView, 1, 2);

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
