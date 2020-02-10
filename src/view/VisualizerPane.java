package view;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import ressource.References;

public class VisualizerPane extends HBox {

	private GraphicsContext gc;
	private Canvas canvas;

	public VisualizerPane() {
		super();
		this.getChildren().add(createVisualizer());
		this.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
	}

	private Node createVisualizer() {
		this.canvas = new Canvas(References.stage.getWidth(), References.stage.getHeight());
		this.gc = canvas.getGraphicsContext2D();
		this.canvas.isResizable();
		
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
