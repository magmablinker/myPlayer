package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ressource.References;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			References.stage = stage;
			MusicBorderPain root = new MusicBorderPain();
			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
			stage.setTitle("Soumd Symstem");
			stage.setScene(scene);
			stage.getIcons().add(new Image(Main.class.getResourceAsStream("../ressource/img/chems.png")));
			stage.show();
			
			stage.setMinWidth(750);
			stage.setMinHeight(650);
			
			stage.setOnHiding(event -> {
				System.out.println("Stopping watchservice");
				root.getExServiceDirectoryWatchService().shutdownNow();
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
