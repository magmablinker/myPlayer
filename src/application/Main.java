package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.DirectoryLoader;
import ressource.Data;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			GridPane root = new GridPane();
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			root.add(generateContent(), 0, 0);
			stage.setTitle("MyPlayer");
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private MenuBar generateMenuBar() {
		MenuBar menuBar = new MenuBar();
		
		Menu menuFile = new Menu("_File");
		menuFile.setMnemonicParsing(true);
		
		MenuItem exit = new MenuItem("E_xit");
		exit.setMnemonicParsing(true);
		exit.setOnAction(event -> Platform.exit());
		
		menuFile.getItems().add(exit);
		
		menuBar.getMenus().add(menuFile);
		
		return menuBar;
	}
	
	private Node generateContent() {
		BorderPane main = new BorderPane();
	
		main.setTop(generateMenuBar());
		main.setLeft(generateTreeViews());
		
		return main;
	}

	private Node generateTreeViews() {
		GridPane grid = new GridPane();
		
		TreeView<String> playlistView = new TreeView<String>();
		
		TreeView<String> directoryView = new TreeView<String>();
		
		// Directory Context Menu
		MenuItem mAdd = new MenuItem("Add Directory");
		mAdd.setOnAction(event -> Data.DIRECTORIES.add("yeee"));
		
		MenuItem mReload = new MenuItem("Reload Directories");
		mReload.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				
			}
		});
		
		MenuItem mRemove = new MenuItem("Remove Directory");
		
		directoryView.setContextMenu(new ContextMenu(mAdd, mReload, mRemove));
		
		TreeItem<String> playlistViewRoot = new TreeItem<String>("Playlists");
		
		TreeItem<String> directoryViewRoot = new TreeItem<String>("Directories");
		
		DirectoryLoader dl = new DirectoryLoader();
		dl.load(directoryViewRoot);
		
		playlistView.setRoot(playlistViewRoot);
		
		directoryView.setRoot(directoryViewRoot);
		
		grid.add(playlistView, 1, 1);
		grid.add(directoryView, 1, 2);
		
		return grid;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
