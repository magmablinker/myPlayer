package view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
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
		// width - 100
		this.canvas = new Canvas(900, 550);
		this.gc = canvas.getGraphicsContext2D();

		return canvas;
	}

	public Canvas getCanvas() {
		return this.canvas;
	}

	public void fillRect(double x, double y, double w, double h, LinearGradient lg) {
		gc.setLineWidth(2);
		gc.setStroke(Color.CORAL);
		
		gc.setFill(lg);
		
		gc.strokeRect(x, y, w, h);
		gc.save();
		
		gc.setGlobalAlpha(0.7);
		gc.fillRect(x, y, w, h);
		
		gc.restore();
	}

	public void clearCanvas() {
		this.gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

}
