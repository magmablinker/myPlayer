package view;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import ressource.References;

public class VisualizerPane extends HBox {

	private GraphicsContext gc;
	private XYChart.Data[] seriesData;

	public VisualizerPane() {
		super();
		this.getChildren().add(createVisualizer());
		this.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
	}

	private Node createVisualizer() {
		Canvas canvas = new Canvas(References.stage.getWidth(), References.stage.getHeight());
		this.gc = canvas.getGraphicsContext2D();

		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
		
		XYChart.Series series = new XYChart.Series();
		seriesData = new XYChart.Data[References.mediaPlayer.getAudioEqualizer().getBands().size()];

		for (int i = 0; i < seriesData.length; i++) {
			seriesData[i] = new XYChart.Data<>(i + 1, 0);
			series.getData().add(seriesData[i]);
		}

		lineChart.setMinSize(References.stage.getWidth(), References.stage.getHeight());
		lineChart.setPrefSize(References.stage.getWidth(), References.stage.getHeight());
		//lineChart.setMaxSize(References.stage.getWidth(), References.stage.getHeight());
		
		lineChart.getData().add(series);
		
		lineChart.getYAxis().setTickLabelsVisible(false);
		lineChart.getYAxis().setOpacity(0);
		
		lineChart.getXAxis().setTickLabelsVisible(false);
		lineChart.getXAxis().setOpacity(0);

		return lineChart;
	}

	public GraphicsContext getGc() {
		return this.gc;
	}

	public XYChart.Data[] getSeriesData() {
		return this.seriesData;
	}

}
