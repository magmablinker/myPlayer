package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.DirectoryDataHandler;
import ressource.Data;
import ressource.References;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			References.stage = stage;
			MusicBorderPain root = new MusicBorderPain();
			Scene scene = new Scene(root, 750, 550);
			scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
			stage.setTitle("Soumd Symstem");
			stage.setScene(scene);
			stage.getIcons().add(new Image(Data.class.getResourceAsStream("img/cheems.png")));
			stage.show();
			
			stage.setMinWidth(650);
			stage.setMinHeight(600);
			
			stage.setOnCloseRequest(event -> {
				root.getExServiceDirectoryWatchService().shutdownNow();
				DirectoryDataHandler dh = new DirectoryDataHandler();
				dh.save();
				Platform.exit();
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
