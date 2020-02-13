package view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import ressource.Data;

public class VisualizerPane extends HBox {

	private GraphicsContext gc;
	private Canvas canvas;

	public VisualizerPane() {
		super();
		this.setAlignment(Pos.CENTER);
		this.getChildren().add(createVisualizer());
		//this.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());

		Image image = new Image(Data.class.getResourceAsStream("img/visualizer.jpg"));
		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.SPACE,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		Background background = new Background(backgroundImage);
		this.setBackground(background);
	}

	private Node createVisualizer() {
		this.canvas = new Canvas(750, 550);
		this.gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.FORESTGREEN);

		return canvas;
	}

	public Canvas getCanvas() {
		return this.canvas;
	}

	public void fillRect(double x, double y, double w, double h) {
		this.gc.fillRect(x, y, w, h);
	}

	public void clearCanvas() {
		this.gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

}
