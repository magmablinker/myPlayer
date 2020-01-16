package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			MusicBorderPain root = new MusicBorderPain();
			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
			stage.setTitle("Soumd Symstem");
			stage.setScene(scene);
			stage.show();
			
			stage.setOnHiding(event -> {
				System.out.println("Stopping watchservice");
				root.getExecutorService().shutdownNow();
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
