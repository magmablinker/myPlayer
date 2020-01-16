package view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import controller.DirectoryWatchService;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeItem.TreeModificationEvent;
import javafx.scene.control.TreeView;
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

		// Directory Context Menu
		MenuItem mAdd = new MenuItem("Add Directory");
		mAdd.setOnAction(event -> Data.DIRECTORIES.add("yeee"));

		MenuItem mRemove = new MenuItem("Remove Directory");

		MenuItem mRevealInExplorer = new MenuItem("Reveal Directory In Explorer");
		mRevealInExplorer.setOnAction(event -> {
			int selectedIndex = directoryView.getSelectionModel().getSelectedIndex();

			if (selectedIndex > -1) {
				FileTreeItem selectedItem = (FileTreeItem) directoryView.getSelectionModel().getSelectedItem();

				// TODO: find a way to get files by unique identifier
				try {
					String path = selectedItem.getPath();
					if(path != null)
						Desktop.getDesktop().open(new File(path));
				} catch (Exception e1) {

				}
			}

		});

		directoryView.setContextMenu(new ContextMenu(mAdd, mRemove, mRevealInExplorer));

		grid.add(playlistView, 1, 1);
		grid.add(directoryView, 1, 2);

		return grid;
	}

	public ExecutorService getExecutorService() {
		return this.exService;
	}

}
