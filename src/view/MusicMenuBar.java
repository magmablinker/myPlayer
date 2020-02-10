package view;

import controller.MenuSettingsEqualizerHandler;
import controller.MenuSettingsVisualizerHandler;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MusicMenuBar extends MenuBar {

	public MusicMenuBar() {
		Menu menuFile = createFileMenu();
		Menu menuSettings = createMenuTools();
		this.getMenus().addAll(menuFile, menuSettings);
	}

	private Menu createMenuTools() {
		Menu menuSettings = new Menu("_Tools");
		menuSettings.setMnemonicParsing(true);
		
		MenuItem mEqualizer = new MenuItem("Equalizer");
		mEqualizer.setOnAction(new MenuSettingsEqualizerHandler());
		
		MenuItem mVisualizer = new MenuItem("Visualizer");
		mVisualizer.setOnAction(new MenuSettingsVisualizerHandler());
		
		menuSettings.getItems().addAll(mEqualizer, mVisualizer);
		
		return menuSettings;
	}

	private Menu createFileMenu() {
		Menu menuFile = new Menu("_File");
		menuFile.setMnemonicParsing(true);

		MenuItem exit = new MenuItem("E_xit");
		exit.setMnemonicParsing(true);
		exit.setOnAction(event -> {
			Platform.exit();
		});

		menuFile.getItems().add(exit);
		
		return menuFile;
	}
	
}
