package view;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MusicMenuBar extends MenuBar {

	public MusicMenuBar() {
		Menu menuFile = createFileMenu();
		this.getMenus().add(menuFile);
	}

	private Menu createFileMenu() {
		Menu menuFile = new Menu("_File");
		menuFile.setMnemonicParsing(true);

		MenuItem exit = new MenuItem("E_xit");
		exit.setMnemonicParsing(true);
		exit.setOnAction(event -> Platform.exit());

		menuFile.getItems().add(exit);
		
		return menuFile;
	}
	
}
