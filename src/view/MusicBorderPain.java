package view;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import controller.DirectoryWatchService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem.TreeModificationEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.DirectoryHandler;
import ressource.Data;

public class MusicBorderPain extends BorderPane {
	
	private DirectoryWatchService directoryWatchService = new DirectoryWatchService();
	private ExecutorService exService;
	
	public MusicBorderPain() {
		super();
		
		MenuBar menuBar = new MusicMenuBar();
		this.setTop(menuBar);
		
		this.setLeft(createTreeView());
		// TODO interrupt watchservice on exit
		System.out.println("Starting watchservice");
		
		Task<Void> watchService = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				directoryWatchService.run();
				return null;
			}
			
		};
		
		this.exService = Executors.newSingleThreadExecutor();
		this.exService.submit(watchService);
	}

	private Node createTreeView() {
		GridPane grid = new GridPane();

		TreeView<String> playlistView = new TreeView<String>();
		playlistView.getStyleClass().add("margin-8");
		
		TreeView<String> directoryView = new TreeView<String>();
		directoryView.getStyleClass().add("margin-8");
		
		// Directory Context Menu
		MenuItem mAdd = new MenuItem("Add Directory");
		mAdd.setOnAction(event -> Data.DIRECTORIES.add("yeee"));

		MenuItem mReload = new MenuItem("Reload Directories");
		mReload.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				//directoryView.getRoot().getChildren().clear();
				//DirectoryHandler dl = new DirectoryHandler();
				//dl.load(directoryView.getRoot());
			}
		});

		MenuItem mRemove = new MenuItem("Remove Directory");

		directoryView.setContextMenu(new ContextMenu(mAdd, mReload, mRemove));

		TreeItem<String> playlistViewRoot = new TreeItem<String>("Playlists");

		TreeItem<String> directoryViewRoot = new TreeItem<String>("Directories");

		DirectoryHandler dl = new DirectoryHandler(this.directoryWatchService);
		dl.load(directoryViewRoot);

		playlistView.setRoot(playlistViewRoot);

		directoryView.setRoot(directoryViewRoot);
		directoryViewRoot.setExpanded(true);
		// Make root always expanded => bad hack
		directoryViewRoot.addEventHandler(TreeItem.branchCollapsedEvent(), new EventHandler<TreeModificationEvent<String>>() {

			@Override
			public void handle(TreeModificationEvent<String> e) {
				if(e.getTreeItem().getValue().equals("Directories"))
					e.getTreeItem().setExpanded(true);
			}
			
		});

		grid.add(playlistView, 1, 1);
		grid.add(directoryView, 1, 2);

		return grid;
	}
	
	public ExecutorService getExecutorService() {
		return this.exService;
	}

}
