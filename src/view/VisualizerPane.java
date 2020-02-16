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
		this.setStyle("-fx-background-image: url(" +
				Data.class.getResource("img/visualizer_bg.jpg").toExternalForm() +
            "); " +
            "-fx-background-size: cover;");
	}

	private Node createVisualizer() {
		this.canvas = new Canvas(750, 550);
		this.gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.web("#031C1D"));

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
