package view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ressource.Data;
import ressource.References;

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

	public void drawCurrentlyPlayingItemText() {
		gc.save();
		gc.setFill(Color.WHITE);
		gc.setLineWidth(2);
		gc.setStroke(Color.BLACK);
		String text = References.SONG_QUEUE.getCurrentItem().getValue();
		gc.setFont(new Font("Arial", findFontSizeThatCanFit(Font.font("Arial", 25), text, (int) canvas.getWidth())));
		gc.strokeText(text, 0, 40);
		gc.fillText(text, 0, 40);
		gc.restore();
	}
	
	private static double findFontSizeThatCanFit(Font font, String s, int maxWidth) {
        double fontSize = font.getSize();
        double width = textWidth(font, s);
        if (width > maxWidth) {
            return fontSize * maxWidth / width;
        }
        return fontSize;
    }

    private static double textWidth(Font font, String s) {
        Text text = new Text(s);
        text.setFont(font);
        return text.getBoundsInLocal().getWidth();
    }

}
