package view;

import controller.MenuSettingsEqualizerHandler;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MusicMenuBar extends MenuBar {

	public MusicMenuBar() {
		Menu menuFile = createFileMenu();
		Menu menuSettings = createMenuSettings();
		this.getMenus().addAll(menuFile, menuSettings);
	}

	private Menu createMenuSettings() {
		Menu menuSettings = new Menu("_Settings");
		menuSettings.setMnemonicParsing(true);
		menuSettings.setOnAction(new MenuSettingsEqualizerHandler());
		
		MenuItem equalizer = new MenuItem("Equalizer");
		
		menuSettings.getItems().add(equalizer);
		
		return menuSettings;
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
