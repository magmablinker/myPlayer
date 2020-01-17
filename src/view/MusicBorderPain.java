package view;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import controller.DirectoryClickHandler;
import controller.DirectoryWatchService;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeItem.TreeModificationEvent;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.DirectoryHandler;
import ressource.References;

public class MusicBorderPain extends BorderPane {

	private DirectoryWatchService directoryWatchService = new DirectoryWatchService();
	private TreeView<String> directoryView;
	private ExecutorService exServiceDirectoryWatchService;
	//private ExecutorService exMediaPlayerService;

	public MusicBorderPain() {
		super();

		MenuBar menuBar = new MusicMenuBar();
		this.setTop(menuBar);

		this.setLeft(createTreeView());
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

		TreeView<String> playlistView = new TreeView<String>();
		playlistView.getStyleClass().add("margin-8");

		directoryView = new TreeView<String>();
		directoryView.getStyleClass().add("margin-8");
		
		References.directoryView = directoryView;

		TreeItem<String> playlistViewRoot = new TreeItem<String>("Playlists");

		TreeItem<String> directoryViewRoot = new TreeItem<String>("Directories");
		
		DirectoryHandler dl = new DirectoryHandler(this.directoryWatchService);
		dl.load(directoryViewRoot);

		playlistView.setRoot(playlistViewRoot);

		directoryView.setRoot(directoryViewRoot);
		directoryViewRoot.setExpanded(true);
		// Make root always expanded => bad hack
		directoryViewRoot.addEventHandler(TreeItem.branchCollapsedEvent(),
				new EventHandler<TreeModificationEvent<String>>() {

					@Override
					public void handle(TreeModificationEvent<String> e) {
						if (e.getTreeItem().getValue().equals("Directories"))
							e.getTreeItem().setExpanded(true);
					}

				});

		directoryView.setContextMenu(new DirectoryContextMenu());
		directoryView.setOnMouseClicked(new DirectoryClickHandler());

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
