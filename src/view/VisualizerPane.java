package view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;

public class VisualizerPane extends HBox {

	private GraphicsContext gc;
	private Canvas canvas;

	public VisualizerPane() {
		super();
		this.setAlignment(Pos.CENTER);
		this.getChildren().add(createVisualizer());
		this.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
	}

	private Node createVisualizer() {
		this.canvas = new Canvas(750, 550);
		this.gc = canvas.getGraphicsContext2D();
		
		return canvas;
	}

	public GraphicsContext getGc() {
		return this.gc;
	}
	
	public Canvas getCanvas() {
		return this.canvas;
	}
	
	public void clearCanvas() {
		this.gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

}
