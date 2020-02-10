package controller;

import java.util.Arrays;

import javafx.scene.chart.XYChart;
import javafx.scene.media.AudioSpectrumListener;
import ressource.References;
import view.VisualizerPane;

public class SpektrumListener implements AudioSpectrumListener {

	private float[] buffer = createFilledBuffer(References.mediaPlayer.getAudioEqualizer().getBands().size(),
			References.mediaPlayer.getAudioSpectrumThreshold());
	private VisualizerPane pane;
	private XYChart.Data[] seriesData;

	public SpektrumListener(VisualizerPane pane) {
		this.pane = pane;
		this.seriesData = pane.getSeriesData();
	}

	@Override
	public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
		for (int i = 0; i < magnitudes.length; i++)
			if (magnitudes[i] >= buffer[i]) {
				buffer[i] = magnitudes[i];
				seriesData[i].setYValue(magnitudes[i] - References.mediaPlayer.getAudioSpectrumThreshold());
			} else {
				seriesData[i].setYValue(buffer[i] - References.mediaPlayer.getAudioSpectrumThreshold());
				buffer[i] -= 0.25;
			}

	}

	private float[] createFilledBuffer(int size, float fillValue) {
		float[] floats = new float[size];
		Arrays.fill(floats, fillValue);
		return floats;
	}

}
